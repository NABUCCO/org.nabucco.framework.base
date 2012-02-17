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
package org.nabucco.framework.base.impl.service.interceptor.service;

import java.lang.reflect.Method;

import javax.interceptor.InvocationContext;

import org.nabucco.framework.base.facade.datatype.logger.InvocationIdentifierThreadLocal;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.UserIdThreadLocal;
import org.nabucco.framework.base.facade.datatype.security.Subject;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.context.ServiceContextContainer;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.base.facade.message.tracing.InvocationIdentifier;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.interceptor.Interceptor;
import org.nabucco.framework.base.impl.service.interceptor.context.InterceptorContext;
import org.nabucco.framework.base.impl.service.interceptor.context.InterceptorContextType;

/**
 * ServiceInterceptor
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ServiceInterceptor implements Interceptor {

    private static final long serialVersionUID = 1L;

    /** The appropriate invocation strategy holding delegate strategies. */
    private ServiceInterceptorStrategy strategy = new ServiceInterceptorStrategyDelegate();

    @Override
    @SuppressWarnings("unchecked")
    public Object intercept(InvocationContext ctx) throws ServiceException {

        ServiceSupport service = null;
        Method operation = null;
        ServiceRequest<?> request = null;
        ServiceResponse<ServiceMessage> response = null;
        NabuccoLogger logger = null;
        Exception exception = null;
        boolean skipAfterExecution = false;

        InterceptorContext interceptorContext = new InterceptorContext();

        String userId = "";
        String invocationIdentifier = "";

        try {
            service = (ServiceSupport) ctx.getTarget();
            logger = service.getLogger();
            operation = ctx.getMethod();

            request = this.getServiceRequest(ctx.getParameters());

            invocationIdentifier = this.getInvocationIdentifierId(request);
            InvocationIdentifierThreadLocal.setInvocationIdentifier(invocationIdentifier);

            userId = this.getUserId(request);
            UserIdThreadLocal.setUserId(userId);

            this.strategy.beforeInvocation(interceptorContext, service, operation, request, logger);

            if (interceptorContext.isShorten()) {
                // stopp chaining, returning response without proceeding
                response = (ServiceResponse<ServiceMessage>) interceptorContext.get(InterceptorContextType.RESPONSE);
            } else {
                response = this.getServiceResponse(ctx.proceed());
            }

            if (response == null || response.getResponseMessage() == null) {
                logger.warning("Service call of method:", ctx.getMethod().getName(), " did not respond acceptable.");
                return response;
            }

        } catch (ServiceException se) {
            if (logger != null) {
                logger.debug(se, invocationIdentifier, " ServiceException during service call of method: ", ctx
                        .getMethod().getName());
            }

            exception = se;

            throw se;

        } catch (Exception e) {
            if (logger != null) {
                logger.debug(e, invocationIdentifier, " Exception during service call of method: ", ctx.getMethod()
                        .getName());
            }

            exception = e;

            throw new ServiceException(e.getMessage(), e);

        } catch (Throwable t) {
            if (logger != null) {
                logger.fatal(t, invocationIdentifier, " Error during service call of method: ", ctx.getMethod()
                        .getName());
            }

            // Skip after execution when error occurs.
            skipAfterExecution = true;

            throw new ServiceException(t.getMessage(), t);

        } finally {
            if (!skipAfterExecution) {
                this.strategy.afterInvocation(interceptorContext, service, operation, request, response, logger,
                        exception);
            }
        }

        return response;
    }

    /**
     * Converts the service operation parameters NullPointerException-save to the service request.
     * 
     * @param params
     *            the service operation parameters
     * 
     * @return the converted service request
     */
    @SuppressWarnings("unchecked")
    private ServiceRequest<ServiceMessage> getServiceRequest(Object[] params) {
        if (params == null) {
            return null;
        }
        if (params.length < 1) {
            return null;
        }
        Object o = params[0];
        if (o instanceof ServiceRequest<?>) {
            return (ServiceRequest<ServiceMessage>) o;
        }
        return null;
    }

    /**
     * Converts the service operation result NullPointerException-save to the service response.
     * 
     * @param object
     *            the service operation return object
     * 
     * @return the converted service response
     */
    @SuppressWarnings("unchecked")
    private ServiceResponse<ServiceMessage> getServiceResponse(Object object) {
        if (object instanceof ServiceResponse<?>) {
            return (ServiceResponse<ServiceMessage>) object;
        }
        return null;
    }

    /**
     * Extracts the invocation identifier ID from the request/response objects.
     * 
     * @param contextContainer
     *            the request/response object
     * 
     * @return the invocation identifier ID
     */
    private String getInvocationIdentifierId(ServiceContextContainer<ServiceMessageContext> contextContainer) {
        StringBuilder invocationIdentifierId = new StringBuilder();

        if (contextContainer != null) {
            ServiceMessageContext context = contextContainer.getContext();
            if (context != null) {
                InvocationIdentifier invocationIdentifier = context.getInvocationIdentifier();
                if (invocationIdentifier != null) {
                    long id = invocationIdentifier.getId();
                    invocationIdentifierId.append(id);
                }
            }
        }
        return invocationIdentifierId.toString();
    }

    /**
     * Extracts the user ID from the request/response objects.
     * 
     * @param contextContainer
     *            the request/response object
     * 
     * @return the user ID
     */
    private String getUserId(ServiceContextContainer<ServiceMessageContext> contextContainer) {
        StringBuilder userId = new StringBuilder();

        if (contextContainer != null && contextContainer.getContext() != null) {
            ServiceMessageContext context = contextContainer.getContext();
            Subject subject = context.getSubject();

            if (subject != null) {
                if (subject.getUserId() != null && subject.getUserId().getValue() != null) {
                    userId.append(subject.getUserId().getValue());
                }
            }
        }

        return userId.toString();
    }
}
