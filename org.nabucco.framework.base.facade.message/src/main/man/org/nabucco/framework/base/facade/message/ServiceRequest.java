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
package org.nabucco.framework.base.facade.message;

import org.nabucco.framework.base.facade.message.context.ServiceContextContainer;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;

/**
 * ServiceRequest
 * 
 * @param <T>
 *            the request message type
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public final class ServiceRequest<T extends ServiceMessage> extends ServiceContextContainer<ServiceMessageContext> {

    private static final long serialVersionUID = 1L;

    private T requestMessage;

    /**
     * Creates a new service request with an appropriate {@link ServiceMessageContext} instance.
     * 
     * @param context
     *            the service message context
     */
    public ServiceRequest(ServiceMessageContext context) {
        super(context);
    }

    /**
     * Gets the message of the service request.
     * 
     * @param requestMessage
     *            the request message.
     */
    public void setRequestMessage(T requestMessage) {
        this.requestMessage = requestMessage;
    }

    /**
     * Sets the message of the service request.
     * 
     * @return the request message.
     */
    public T getRequestMessage() {
        return requestMessage;
    }

}
