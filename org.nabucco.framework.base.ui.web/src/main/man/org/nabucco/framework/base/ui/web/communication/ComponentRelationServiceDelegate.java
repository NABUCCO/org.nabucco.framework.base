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
package org.nabucco.framework.base.ui.web.communication;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.datatype.session.NabuccoSession;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationListMsg;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationMsg;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationProduceRq;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationSearchRq;
import org.nabucco.framework.base.facade.service.componentrelation.ComponentRelationService;

/**
 * Web Delegate for component relation service calls.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ComponentRelationServiceDelegate extends ServiceDelegateSupport {

    private ComponentRelationService service;

    /**
     * Creates a new {@link ComponentRelationServiceDelegate} instance.
     * 
     * @param service
     *            the delegating service
     */
    ComponentRelationServiceDelegate(ComponentRelationService service) {
        this.service = service;
    }

    /**
     * Maintain the component relation.
     * 
     * @param message
     *            the component relation message holding a single component relation
     * @param session
     *            the web session
     * @param subContexts
     *            the service sub contexts
     * 
     * @return the component relation message holding the maintained component relation
     * 
     * @throws MaintainException
     *             when the maintain fails
     */
    public ComponentRelationMsg maintainComponentRelation(ComponentRelationMsg message, NabuccoSession session,
            ServiceSubContext... subContexts) throws MaintainException {

        ServiceRequest<ComponentRelationMsg> request = new ServiceRequest<ComponentRelationMsg>(
                super.createServiceContext(session, subContexts));
        request.setRequestMessage(message);

        ServiceResponse<ComponentRelationMsg> response = null;
        Exception exception = null;

        if ((this.service != null)) {
            super.handleRequest(request, session);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = this.service.maintainComponentRelation(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ComponentRelationService.class, "maintainComponentRelation", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }

        throw new MaintainException(
                "Cannot execute service operation: ComponentRelationService.maintainComponentRelation");
    }

    /**
     * Maintain the component relation list.
     * 
     * @param message
     *            the component relation message holding a list component relation
     * @param session
     *            the web session
     * @param subContexts
     *            the service sub contexts
     * 
     * @return the component relation message holding the maintained component relation list
     * 
     * @throws MaintainException
     *             when the maintain fails
     */
    public ComponentRelationListMsg maintainComponentRelationList(ComponentRelationListMsg message,
            NabuccoSession session, ServiceSubContext... subContexts) throws MaintainException {

        ServiceRequest<ComponentRelationListMsg> request = new ServiceRequest<ComponentRelationListMsg>(
                super.createServiceContext(session, subContexts));
        request.setRequestMessage(message);

        ServiceResponse<ComponentRelationListMsg> response = null;
        Exception exception = null;

        if ((this.service != null)) {
            super.handleRequest(request, session);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = this.service.maintainComponentRelationList(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ComponentRelationService.class, "maintainComponentRelationList", duration,
                        exception);
            }
            if ((response != null)) {
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }

        throw new MaintainException(
                "Cannot execute service operation: ComponentRelationService.maintainComponentRelation");
    }

    /**
     * Produce a new component relation instance.
     * 
     * @param message
     *            the message holding the component relation prototype
     * @param session
     *            the web session
     * @param subContexts
     *            the service sub contexts
     * 
     * @return the message holding the created component relation instance
     * 
     * @throws ProduceException
     *             when the instance cannot be created
     */
    public ComponentRelationMsg produceComponentRelation(ComponentRelationProduceRq message, NabuccoSession session,
            ServiceSubContext... subContexts) throws ProduceException {

        ServiceRequest<ComponentRelationProduceRq> request = new ServiceRequest<ComponentRelationProduceRq>(
                super.createServiceContext(session, subContexts));
        request.setRequestMessage(message);

        ServiceResponse<ComponentRelationMsg> response = null;
        Exception exception = null;
        if ((this.service != null)) {
            super.handleRequest(request, session);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.produceComponentRelation(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ComponentRelationService.class, "searchComponentRelation", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }

        throw new ProduceException(
                "Cannot execute service operation: ComponentRelationService.produceComponentRelation");
    }

    /**
     * Search for a list of component relation instances.
     * 
     * @param message
     *            the message holding the component relation search request
     * @param session
     *            the web session
     * @param subContexts
     *            the service sub contexts
     * 
     * @return the message holding the list of found component relation instances
     * 
     * @throws SearchException
     *             when the search did not finish successful
     */
    public ComponentRelationListMsg searchComponentRelation(ComponentRelationSearchRq message, NabuccoSession session,
            ServiceSubContext... subContexts) throws SearchException {

        ServiceRequest<ComponentRelationSearchRq> request = new ServiceRequest<ComponentRelationSearchRq>(
                super.createServiceContext(session, subContexts));
        request.setRequestMessage(message);

        ServiceResponse<ComponentRelationListMsg> response = null;
        Exception exception = null;
        if ((this.service != null)) {
            super.handleRequest(request, session);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.searchComponentRelation(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ComponentRelationService.class, "searchComponentRelation", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }

        throw new SearchException("Cannot execute service operation: ComponentRelationService.searchComponentRelation");
    }

}
