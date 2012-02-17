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
package org.nabucco.framework.base.impl.service.aspect;

import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;

/**
 * Utility class for accessing service request/response parameters.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class AspectExecutorSupport {

    /**
     * Getter for the service message of the given service request.
     * 
     * @param request
     *            the service request holding the message
     * 
     * @return the service message or null if no request or message is set
     */
    protected <M extends ServiceMessage> M getRequestMessage(ServiceRequest<M> request) {
        if (request == null) {
            return null;
        }
        return request.getRequestMessage();
    }

    /**
     * Getter for the service message of the given service response.
     * 
     * @param response
     *            the service response holding the message
     * 
     * @return the service message or null if no response or message is set
     */
    protected <M extends ServiceMessage> M getResponseMessage(ServiceResponse<M> response) {
        if (response == null) {
            return null;
        }
        return response.getResponseMessage();
    }

    /**
     * Getter for the service context of the given service request.
     * 
     * @param request
     *            the service request holding the context
     * 
     * @return the service context or null if no request or context is set
     */
    protected ServiceMessageContext getServiceContext(ServiceRequest<?> request) {
        if (request == null) {
            return null;
        }
        return request.getContext();
    }

    /**
     * Getter for the service context of the given service response.
     * 
     * @param response
     *            the service response holding the context
     * 
     * @return the service context or null if no response or context is set
     */
    protected ServiceMessageContext getServiceContext(ServiceResponse<?> response) {
        if (response == null) {
            return null;
        }
        return response.getContext();
    }
}
