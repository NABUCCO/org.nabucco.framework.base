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

import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContextType;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.session.NabuccoSession;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.context.ServiceContextFactory;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.base.facade.message.tracing.InvocationIdentifier;
import org.nabucco.framework.base.facade.message.tracing.InvocationIdentifierFactory;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.framework.base.ui.web.component.error.ErrorLogContainer;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;

/**
 * ServiceDelegateSupport
 * <p/>
 * Supporting super-class of all service delegates. Provides service context initialization and
 * manipulation.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class ServiceDelegateSupport {

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(ServiceDelegateSupport.class);

    private InvocationIdentifier identifier;

    /**
     * Creates a new service context for the current user (identified by the session).
     * 
     * @param the
     *            current security subject
     * 
     * @return the service context
     */
    protected ServiceMessageContext createServiceContext(NabuccoSession session, ServiceSubContext... subContexts) {
        if (session == null) {
            throw new IllegalArgumentException("Cannot create service context for session [null].");
        }

        this.identifier = InvocationIdentifierFactory.getInstance().createInvocationIdentifier();

        ServiceMessageContext context = ServiceContextFactory.getInstance().newServiceMessageContext(identifier,
                session.getSecurityContext().getSubject());

        if (subContexts != null) {
            for (ServiceSubContext current : subContexts) {
                if (current != null) {
                    context.put(current.getContextType(), current);
                }
            }
        }

        return context;
    }

    /**
     * Monitor the service execution result.
     * 
     * @param serviceInterface
     *            interface of the executed service
     * @param operationName
     *            name of the executed service operation
     * @param duration
     *            duration of the execution
     * @param exception
     *            raised exception, if any
     */
    protected final <S extends Service> void monitorResult(Class<S> serviceInterface, String operationName,
            long duration, Exception exception) {

        String serviceName = (serviceInterface != null) ? serviceInterface.getSimpleName() : "undefined";
        logger.info("Service: ", serviceName, ".", operationName, " (Time: ", duration, "ms)");

        if (serviceInterface == null || operationName == null) {
            logger.error(exception, "Error monitoring service operation result.");
            return;
        }

        if (exception != null) {
            ErrorLogContainer errorContainer = NabuccoServletUtil.getErrorContainer();

            if (errorContainer != null) {
                errorContainer.getModel().addError(serviceInterface.getCanonicalName(), operationName, this.identifier,
                        exception);
            }
        }
    }

    /**
     * Prepare the service request.
     * 
     * @param rq
     *            the service request to handle
     * @param session
     *            the nabucco session
     */
    protected final void handleRequest(ServiceRequest<?> rq, NabuccoSession session) {
        for (ServiceSubContextType type : ServiceSubContextType.values()) {
            ServiceSubContext subContext = session.getServiceContext().getRequestContext(type);

            if (subContext == null) {
                rq.getContext().remove(type);
            } else {
                rq.getContext().put(type, subContext);
            }
        }
    }

    /**
     * Prepare the service response.
     * 
     * @param rs
     *            the service response to handle
     * @param session
     *            the nabucco session
     */
    protected final void handleResponse(ServiceResponse<?> rs, NabuccoSession session) {
        ServiceMessageContext context = rs.getContext();

        session.getServiceContext().clear();

        for (ServiceSubContextType type : ServiceSubContextType.values()) {
            ServiceSubContext subContext = context.get(type);

            if (subContext != null) {
                session.getServiceContext().putResponseContext(subContext);
            }
        }
    }

}
