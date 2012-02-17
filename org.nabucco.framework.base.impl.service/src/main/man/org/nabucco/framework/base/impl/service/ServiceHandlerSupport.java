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
package org.nabucco.framework.base.impl.service;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.exception.validation.ValidationException;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;

/**
 * ServiceHandlerSupport
 * <p/>
 * Support class for service invocations.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class ServiceHandlerSupport implements ServiceHandler {

    private static final long serialVersionUID = 1L;

    private ServiceMessageContext context;

    private NabuccoLogger logger;

    @Override
    public void init() {
    }

    @Override
    public void finish() {
    }

    @Override
    public void setLogger(NabuccoLogger logger) {
        this.logger = logger;
    }

    @Override
    public void setContext(ServiceMessageContext context) {
        this.context = context;
    }

    /**
     * Getter for the NABUCCO logger.
     * 
     * @return the NABUCCO logger.
     */
    protected NabuccoLogger getLogger() {
        if (this.logger == null) {
            this.logger = NabuccoLoggingFactory.getInstance().getLogger(this.getClass());
        }
        return this.logger;
    }

    /**
     * Getter for the service context.
     * 
     * @return the service context
     */
    protected ServiceMessageContext getContext() {
        if (this.context == null) {
            this.getLogger().warning("No service context defined.");
        }
        return this.context;
    }

    /**
     * Cleans a given service message from provider specific collections.
     * 
     * @param <T>
     *            a service message
     * @param message
     *            the message to clean
     * 
     * @throws ServiceException
     *             when the message cannot be cleaned
     */
    protected <T extends ServiceMessage> void cleanServiceMessage(T message) throws ServiceException {
        // Nothing to clean here!
    }

    /**
     * Creates a service request for further service calls
     * 
     * @param <T>
     *            the {@link ServiceMessage}
     * 
     * @return the servicerequest
     */
    protected <T extends ServiceMessageSupport> ServiceRequest<T> createRequest(T message) {
        ServiceRequest<T> serviceRequest = new ServiceRequest<T>(this.context);
        serviceRequest.setRequestMessage(message);
        return serviceRequest;
    }

    /**
     * Validates the service request.
     * 
     * @param rq
     *            the request to validate
     * 
     * @throws ValidationException
     */
    protected void validateRequest(ServiceRequest<?> rq) throws ValidationException {
        if (rq == null) {
            throw new ValidationException("No Service-Request defined.");
        }
        if (rq.getContext() == null) {
            throw new ValidationException("No Service-Context defined.");
        }
        if (rq.getRequestMessage() == null) {
            throw new ValidationException("No Request-Message defined.");
        }
    }

}
