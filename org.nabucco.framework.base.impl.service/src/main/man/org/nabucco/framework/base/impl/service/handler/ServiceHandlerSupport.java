/*
 * Copyright 2010 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco-source.org/nabucco-license.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.impl.service.handler;

import javax.persistence.EntityManager;

import org.nabucco.framework.base.impl.service.maintain.NabuccoMaintainHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceCleaner;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.exception.validation.ValidationException;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;

/**
 * ServiceHandlerSupport
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class ServiceHandlerSupport implements ServiceHandler {

    private static final long serialVersionUID = 1L;

    private EntityManager em;

    private ServiceMessageContext ctx;

    private NabuccoLogger logger;

    @Override
    public void init() {
    }

    @Override
    public void finish() {
    }

    @Override
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public void setLogger(NabuccoLogger logger) {
        this.logger = logger;
    }

    @Override
    public void setContext(ServiceMessageContext context) {
        this.ctx = context;
    }

    /**
     * Getter for the entity manager (persistence provider implementation).
     * 
     * @return the entity manager
     */
    protected EntityManager getEntityManager() {
        return this.em;
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
        if (this.ctx == null) {
            this.getLogger().warning("No service context defined.");
        }
        return this.ctx;
    }

    /**
     * Getter for the maintain handler utility for CRUD operation suport.
     * 
     * @param <T>
     *            the {@link Datatype}
     * @param datatypeClass
     *            the class of this datatype
     * 
     * @return the maintain handler
     */
    protected <T extends Datatype> NabuccoMaintainHandler<T> getMaintainHandler(
            Class<T> datatypeClass) {
        return new NabuccoMaintainHandler<T>(datatypeClass, this.em);
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
        ServiceRequest<T> serviceRequest = new ServiceRequest<T>(this.ctx);
        serviceRequest.setRequestMessage(message);
        return serviceRequest;
    }

    /**
     * Cleans a given service message from persistence provider specific lazy collections.
     * 
     * @param <T>
     *            a service message
     * @param message
     *            the message to clean
     * 
     * @throws ServiceException
     */
    protected <T extends ServiceMessage> void cleanServiceMessage(T message)
            throws ServiceException {
        if (message == null) {
            throw new IllegalArgumentException("Cannot clean service message [null].");
        }

        if (this.em != null) {
            this.em.clear();
        }

        try {
            PersistenceCleaner cleaner = new PersistenceCleaner();
            message.accept(cleaner);
        } catch (VisitorException e) {
            String name = message.getClass().getName();
            throw new ServiceException("Cannot clean service message '" + name + "'.");
        }
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
