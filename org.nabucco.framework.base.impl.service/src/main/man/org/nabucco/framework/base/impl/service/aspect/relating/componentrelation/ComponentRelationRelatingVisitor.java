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
package org.nabucco.framework.base.impl.service.aspect.relating.componentrelation;

import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.component.Component;
import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.component.locator.LookupUtility;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
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
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.base.facade.message.visitor.ServiceMessageVisitor;
import org.nabucco.framework.base.facade.service.componentrelation.ComponentRelationService;

/**
 * Visitor resolving all component relation properties.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ComponentRelationRelatingVisitor extends ServiceMessageVisitor {

    /** Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            ComponentRelationRelatingVisitor.class);

    /** The service context */
    private ServiceMessageContext context;

    /**
     * Creates a new {@link ComponentRelationRelatingVisitor} instance.
     * 
     * @param context
     *            the service context
     */
    public ComponentRelationRelatingVisitor(ServiceMessageContext context) {
        this.context = context;
    }

    @Override
    public void visit(Datatype datatype) throws VisitorException {

        if (datatype instanceof NabuccoDatatype) {
            this.relateDatatype((NabuccoDatatype) datatype);
        }

        super.visit(datatype);
    }

    /**
     * Maintain the component relations of this datatype.
     * 
     * @param datatype
     *            the datatype to relate
     * 
     * @throws VisitorException
     *             when the datatype cannot be related
     */
    private void relateDatatype(NabuccoDatatype datatype) throws VisitorException {
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

                List<ComponentRelation<?>> relations = this.maintainRelations(datatype, relationProperty,
                        componentClass);

                NabuccoProperty newProperty = relationProperty.createProperty(relations);
                datatype.setProperty(newProperty);

            } catch (ExtensionException ee) {
                logger.warning("No business schema available for datatype '", datatype.getClass().getName(), "'.");
                continue;
            } catch (ServiceException se) {
                logger.error(se, "Error maintaining component-relations for '", property.getName(), "'.");
            } catch (ConnectionException ce) {
                logger.error(ce, "Error connecting to component-relation target component.");
                throw new VisitorException("Error connecting to component-relation target component.", ce);
            } catch (Exception e) {
                logger.error(e, "Error maintaining component-relations for '", property.getName(), "'.");
                throw new VisitorException("Error maintaining component-relations for '" + property.getName() + "'.", e);
            }
        }
    }

    /**
     * Maintain the component relations with the {@link ComponentRelationService}.
     * 
     * @param parent
     *            the parent datatype holding the component relations
     * @param property
     *            the component relation property
     * 
     * @return the list of component relations
     * 
     * @throws ConnectionException
     *             when the target component is not reachable
     * @throws ServiceException
     *             when the component relation service fails
     */
    private List<ComponentRelation<?>> maintainRelations(NabuccoDatatype parent, ComponentRelationProperty property,
            Class<Component> componentClass) throws ConnectionException, ServiceException {

        NabuccoList<ComponentRelation<?>> relations = property.getInstance();
        if (relations == null) {
            return relations;
        }

        // Skip if no assigned and unassigned elements exist!
        if (relations.isEmpty() && relations.getUnassignedElements().isEmpty()) {
            return relations;
        }

        ComponentRelationService relationService = this.locateService(componentClass);

        ComponentRelationListMsg msg = new ComponentRelationListMsg();

        // Current Relations
        for (ComponentRelation<?> relation : relations) {
            relation.setSourceId(new Identifier(parent.getId()));
            msg.getComponentRelationList().add(relation);
        }

        // Unassigned Elements
        List<ComponentRelation<?>> removedList = relations.getUnassignedElements();
        for (ComponentRelation<?> removedRelation : removedList) {
            removedRelation.setDatatypeState(DatatypeState.DELETED);
            msg.getComponentRelationList().add(removedRelation);
        }

        ServiceRequest<ComponentRelationListMsg> rq = new ServiceRequest<ComponentRelationListMsg>(this.context);
        rq.setRequestMessage(msg);

        ServiceResponse<ComponentRelationListMsg> rs = relationService.maintainComponentRelationList(rq);

        if (rs == null || rs.getResponseMessage() == null) {
            throw new ServiceException("Component-Relation Service did not end up normally.");
        }

        List<ComponentRelation<?>> newRelations = new NabuccoListImpl<ComponentRelation<?>>();
        for (ComponentRelation<?> relation : rs.getResponseMessage().getComponentRelationList()) {
            if (relation.getDatatypeState() == DatatypeState.PERSISTENT) {
                newRelations.add(relation);
            }
        }

        return newRelations;
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
