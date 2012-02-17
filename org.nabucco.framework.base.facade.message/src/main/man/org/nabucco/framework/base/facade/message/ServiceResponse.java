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

import java.io.Serializable;

import org.nabucco.framework.base.facade.message.context.ServiceContextContainer;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;

/**
 * ServiceResponse
 * 
 * @param <T>
 *            the response message type
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public final class ServiceResponse<T extends ServiceMessage> extends ServiceContextContainer<ServiceMessageContext>
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private T responseMessage;

    /**
     * Creates a new service response with an appropriate {@link ServiceMessageContext} instance.
     * 
     * @param context
     *            the service context
     */
    public ServiceResponse(ServiceMessageContext context) {
        super(context);
    }

    /**
     * Gets the message of the service response.
     * 
     * @return the response message.
     */
    public T getResponseMessage() {
        return responseMessage;
    }

    /**
     * Sets the response message into the response.
     * 
     * @param responseMessage
     *            the response message to set
     */
    public void setResponseMessage(T responseMessage) {
        this.responseMessage = responseMessage;
    }

}
