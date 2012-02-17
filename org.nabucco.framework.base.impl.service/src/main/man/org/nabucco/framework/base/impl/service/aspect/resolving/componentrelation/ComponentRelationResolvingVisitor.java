/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.impl.service.aspect.resolving.componentrelation;

import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.component.Component;
import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.component.locator.LookupUtility;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionAccessor;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.extension.business.schema.BusinessSchemaResolver;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.business.schema.BusinessSchemaObject;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.property.ComponentRelationProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyType;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationListMsg;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationSearchRq;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.base.facade.message.visitor.ServiceMessageVisitor;
import org.nabucco.framework.base.facade.service.componentrelation.ComponentRelationService;

/**
 * Visitor resolving all component relation properties.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ComponentRelationResolvingVisitor extends ServiceMessageVisitor {

    /** Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            ComponentRelationResolvingVisitor.class);

    /** The service context */
    private ServiceMessageContext context;

    /**
     * Creates a new {@link ComponentRelationResolvingVisitor} instance.
     * 
     * @param context
     *            the service context
     */
    public ComponentRelationResolvingVisitor(ServiceMessageContext context) {
        this.context = context;
    }

    @Override
    public void visit(Datatype datatype) throws VisitorException {

        if (datatype instanceof NabuccoDatatype) {
            this.resolveDatatype(datatype);
        }

        super.visit(datatype);
    }

    /**
     * Resolve all codes of the given datatype.
     * 
     * @param datatype
     *            the datatype to resolve
     * 
     * @throws VisitorException
     *             when the datatype cannot be resolved
     */
    private void resolveDatatype(Datatype datatype) throws VisitorException {
        String extensionName = datatype.getClass().getSimpleName();

        for (NabuccoProperty property : datatype.getProperties()) {

            if (property.getPropertyType() != NabuccoPropertyType.COMPONENT_RELATION) {
                continue;
            }

            try {
                ComponentRelationProperty relationProperty = (ComponentRelationProperty) property;

                BusinessSchemaResolver schemaResolver = new BusinessSchemaResolver(extensionName);
                BusinessSchemaObject target = schemaResolver.getTarget(property.getName());
                Class<Component> componentClass = PropertyLoader.loadProperty(target.getComponent());

                List<ComponentRelation<?>> relations = this.resolveRelations((NabuccoDatatype) datatype,
                        relationProperty, componentClass);

                NabuccoProperty newProperty = relationProperty.createProperty(relations);
                datatype.setProperty(newProperty);

            } catch (ExtensionException ee) {
                logger.warning("No business schema available for datatype '", datatype.getClass().getName(), "'.");
                continue;
            } catch (ServiceException se) {
                logger.error(se, "Error resolving component-relations for '", property.getName(), "'.");
            } catch (ConnectionException ce) {
                logger.error(ce, "Error connecting to Component.");
                throw new VisitorException("Error connecting to Component.", ce);
            } catch (Exception e) {
                logger.error(e, "Error resolving component-relations for '", property.getName(), "'.");
                throw new VisitorException("Error resolving component-relations for '" + property.getName() + "'.", e);
            }
        }
    }

    /**
     * Resolve the component relations from the {@link ComponentRelationService}.
     * 
     * @param parent
     *            the parent datatype holding the component relations
     * @param property
     *            the component relation property
     * @param componentClass
     *            the component class
     * 
     * @return the list of component relations
     * 
     * @throws ConnectionException
     *             when the target component is not reachable
     * @throws ServiceException
     *             when the component relation service fails
     */
    private List<ComponentRelation<?>> resolveRelations(NabuccoDatatype parent, ComponentRelationProperty property,
            Class<Component> componentClass) throws ConnectionException, ServiceException {

        ComponentRelationService relationService = this.locateService(componentClass);

        Name relationName = new Name(property.getType().getCanonicalName());
        Identifier sourceId = new Identifier(parent.getId());

        ComponentRelationSearchRq msg = new ComponentRelationSearchRq();
        msg.setComponentRelationClass(relationName);
        msg.setSourceId(sourceId);
        msg.setRelationType(property.getComponentRelationType());

        ServiceRequest<ComponentRelationSearchRq> rq = new ServiceRequest<ComponentRelationSearchRq>(this.context);
        rq.setRequestMessage(msg);

        ServiceResponse<ComponentRelationListMsg> rs = relationService.searchComponentRelation(rq);

        if (rs == null || rs.getResponseMessage() == null) {
            throw new ServiceException("Component-Relation Service did not end up normally.");
        }

        List<ComponentRelation<?>> relations = rs.getResponseMessage().getComponentRelationList();

        NabuccoCollectionAccessor.getInstance().clearAssignments(relations);

        return relations;
    }

    /**
     * Locate the component relation service from the given component name JNDI.
     * 
     * @param componentClass
     *            the component class to find the appropriate component-relation service
     * 
     * @return the component relation service
     * 
     * @throws ConnectionException
     *             when the component relation service is not bound
     * @throws ServiceException
     *             when the component does not provide a valid component-relation service
     */
    private ComponentRelationService locateService(Class<Component> componentClass) throws ConnectionException,
            ServiceException {

        if (componentClass == null) {
            throw new ConnectionException("Component 'null' not bound.");
        }

        Component component = LookupUtility.getComponentLocal(componentClass);

        if (component == null) {
            throw new ConnectionException("Component '" + componentClass.getName() + "' not bound.");
        }

        ComponentRelationService relationService = component.getComponentRelationService();

        if (relationService == null) {
            throw new ConnectionException("No Component-Relation Service bound in component '"
                    + componentClass.getName() + "'");
        }

        return relationService;
    }
}
