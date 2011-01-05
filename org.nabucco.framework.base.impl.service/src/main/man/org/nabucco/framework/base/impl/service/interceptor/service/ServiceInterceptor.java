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
package org.nabucco.framework.base.impl.service.interceptor.service;

import java.lang.reflect.Method;

import javax.interceptor.InvocationContext;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.context.ServiceContextContainer;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.base.facade.message.tracing.InvocationIdentifier;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.interceptor.Interceptor;
import org.nabucco.framework.base.impl.service.interceptor.context.InterceptorContext;

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
    public Object intercept(InvocationContext ctx) throws NabuccoException {
        ServiceSupport service = null;
        Method operation = null;
        ServiceRequest<?> request = null;
        ServiceResponse<ServiceMessage> response = null;

        NabuccoLogger logger = null;
        InterceptorContext interceptorContext = new InterceptorContext();

        String invocationIdentifier = "";
        Throwable exception = null;

        try {

            service = (ServiceSupport) ctx.getTarget();
            operation = ctx.getMethod();
            request = this.getServiceRequest(ctx.getParameters());

            logger = service.getLogger();
            invocationIdentifier = this.getInvocationIdentifier(request);

            strategy.beforeInvocation(interceptorContext, service, operation, request, logger);

            response = this.getServiceResponse(ctx.proceed());

        } catch (NabuccoException ne) {
            if (logger != null) {
                logger.debug(ne, invocationIdentifier,
                        " NabuccoException during service call of method: ", ctx.getMethod()
                                .getName());
            }

            exception = ne;
            throw ne;

        } catch (Exception e) {
            if (logger != null) {
                logger.debug(e, invocationIdentifier, " Exception during service call of method: ",
                        ctx.getMethod().getName());
            }

            exception = e;
            throw new NabuccoException(e.getMessage(), e);

        } catch (Throwable t) {
            if (logger != null) {
                logger.fatal(t, invocationIdentifier, " Error during service call of method: ", ctx
                        .getMethod().getName());
            }
            
        } finally {
            strategy.afterInvocation(interceptorContext, service, operation, request, response,
                    logger, exception);
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
    private ServiceRequest<ServiceMessage> getServiceRequest(Object[] params) {
        if (params == null) {
            return null;
        }
        if (params.length < 1) {
            return null;
        }
        Object o = params[0];
        if (o instanceof ServiceRequest<?>) {
            @SuppressWarnings("unchecked")
            ServiceRequest<ServiceMessage> rq = (ServiceRequest<ServiceMessage>) o;
            return rq;
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
    private ServiceResponse<ServiceMessage> getServiceResponse(Object object) {
        if (object instanceof ServiceResponse<?>) {
            @SuppressWarnings("unchecked")
            ServiceResponse<ServiceMessage> rs = (ServiceResponse<ServiceMessage>) object;
            return rs;
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
    private String getInvocationIdentifier(
            ServiceContextContainer<ServiceMessageContext> contextContainer) {
        StringBuilder invocationIdentifierId = new StringBuilder();

        if (contextContainer != null) {
            ServiceMessageContext context = contextContainer.getContext();
            if (context != null) {
                InvocationIdentifier invocationIdentifier = context.getInvocationIdentifier();
                if (invocationIdentifier != null) {
                    long id = invocationIdentifier.getId();
                    invocationIdentifierId.append("[");
                    invocationIdentifierId.append(id);
                    invocationIdentifierId.append("]");
                }
            }
        }
        return invocationIdentifierId.toString();
    }
}
