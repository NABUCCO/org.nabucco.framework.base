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
package org.nabucco.framework.base.impl.service.componentrelation.util;

import java.util.List;

import org.nabucco.framework.base.facade.component.Component;
import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.component.locator.LookupUtility;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionAccessor;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelationType;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationListMsg;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationSearchRq;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.base.facade.service.componentrelation.ComponentRelationService;

/**
 * ComponentRelationServiceDelegate
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
class ComponentRelationServiceDelegate {

    private static final long serialVersionUID = 1L;

    private ComponentRelationService delegate;

    private ServiceMessageContext context;

    /**
     * Creates a new {@link ComponentRelationServiceDelegate} instance.
     * 
     * @param componentClass
     *            the component class holding the component relation
     * @param context
     *            the service context
     * 
     * @throws ConnectionException
     *             when the component relation service cannot be located
     * @throws ServiceException
     *             when the component does not provide a component relation service
     */
    public ComponentRelationServiceDelegate(Class<Component> componentClass, ServiceMessageContext context)
            throws ConnectionException, ServiceException {
        if (context == null) {
            throw new IllegalArgumentException("Cannot use component relations with service context 'null'.");
        }
        if (componentClass == null) {
            throw new IllegalArgumentException("Cannot locate component relation service for component class 'null'.");
        }

        this.context = context;
        this.delegate = this.locateService(componentClass);
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

    /**
     * Find the component relations by source datatype id.
     * 
     * @param componentRelationClass
     *            the component relation class
     * @param componentRelationType
     *            the component relation type
     * @param sourceId
     *            the component relation source id
     * 
     * @return the list of found component relations
     * 
     * @throws SearchException
     *             when the search parameters are not valid
     */
    public List<ComponentRelation<?>> findComponentRelationsBySource(
            Class<? extends ComponentRelation<?>> componentRelationClass, ComponentRelationType componentRelationType,
            Long sourceId) throws SearchException {
        String componentRelationName = componentRelationClass.getCanonicalName();
        return this.findComponentRelations(componentRelationName, componentRelationType, sourceId, null);
    }

    /**
     * Find the component relations by source datatype id.
     * 
     * @param componentRelationName
     *            the component relation name
     * @param componentRelationType
     *            the component relation type
     * @param sourceId
     *            the component relation source id
     * 
     * @return the list of found component relations
     * 
     * @throws SearchException
     *             when the search parameters are not valid
     */
    public List<ComponentRelation<?>> findComponentRelationsBySource(String componentRelationName,
            ComponentRelationType componentRelationType, Long sourceId) throws SearchException {
        return this.findComponentRelations(componentRelationName, componentRelationType, sourceId, null);
    }

    /**
     * Find the component relations by target datatype id.
     * 
     * @param componentRelationClass
     *            the component relation class
     * @param componentRelationType
     *            the component relation type
     * @param targetId
     *            the component relation target id
     * 
     * @return the list of found component relations
     * 
     * @throws SearchException
     *             when the search parameters are not valid
     */
    public List<ComponentRelation<?>> findComponentRelationsByTarget(
            Class<? extends ComponentRelation<?>> componentRelationClass, ComponentRelationType componentRelationType,
            Long targetId) throws SearchException {
        String componentRelationName = componentRelationClass.getCanonicalName();
        return this.findComponentRelations(componentRelationName, componentRelationType, null, targetId);
    }

    /**
     * Find the component relations by target datatype id.
     * 
     * @param componentRelationName
     *            the component relation name
     * @param componentRelationType
     *            the component relation type
     * @param targetId
     *            the component relation target id
     * 
     * @return the list of found component relations
     * 
     * @throws SearchException
     *             when the search parameters are not valid
     */
    public List<ComponentRelation<?>> findComponentRelationsByTarget(String componentRelationName,
            ComponentRelationType componentRelationType, Long targetId) throws SearchException {
        return this.findComponentRelations(componentRelationName, componentRelationType, null, targetId);
    }

    /**
     * Find the component relations for the given parameters.
     * 
     * @param componentRelationName
     *            the component relation name
     * @param componentRelationType
     *            the component relation type
     * @param sourceId
     *            the component relation source id
     * @param targetId
     *            the component relation target id
     * 
     * @return the list of found component relations
     * 
     * @throws SearchException
     *             when the search parameters are not valid
     */
    public List<ComponentRelation<?>> findComponentRelations(String componentRelationName,
            ComponentRelationType componentRelationType, Long sourceId, Long targetId) throws SearchException {

        ComponentRelationSearchRq msg = new ComponentRelationSearchRq();
        msg.setComponentRelationClass(new Name(componentRelationName));
        msg.setRelationType(componentRelationType);

        if (sourceId != null) {
            msg.setSourceId(new Identifier(sourceId));
        }
        if (targetId != null) {
            msg.setTargetId(new Identifier(targetId));
        }

        ServiceRequest<ComponentRelationSearchRq> rq = new ServiceRequest<ComponentRelationSearchRq>(this.context);
        rq.setRequestMessage(msg);

        ServiceResponse<ComponentRelationListMsg> rs = this.delegate.searchComponentRelation(rq);

        if (rs == null || rs.getResponseMessage() == null) {
            throw new SearchException("Component-Relation Service did not return a valid result.");
        }

        List<ComponentRelation<?>> relations = rs.getResponseMessage().getComponentRelationList();

        NabuccoCollectionAccessor.getInstance().clearAssignments(relations);

        return relations;
    }
}
