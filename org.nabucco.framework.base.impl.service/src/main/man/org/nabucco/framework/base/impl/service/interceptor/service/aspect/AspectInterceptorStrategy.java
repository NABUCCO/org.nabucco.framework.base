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
package org.nabucco.framework.base.impl.service.interceptor.service.aspect;

import java.lang.reflect.Method;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.framework.base.impl.service.aspect.AspectException;
import org.nabucco.framework.base.impl.service.aspect.AspectExecutionContext;
import org.nabucco.framework.base.impl.service.aspect.AspectHandler;
import org.nabucco.framework.base.impl.service.aspect.AspectHandlerBuilder;
import org.nabucco.framework.base.impl.service.aspect.AspectMap;
import org.nabucco.framework.base.impl.service.interceptor.context.InterceptorContext;
import org.nabucco.framework.base.impl.service.interceptor.context.InterceptorContextType;
import org.nabucco.framework.base.impl.service.interceptor.service.ServiceInterceptorStrategy;

/**
 * Interceptor for adding pointcuts to any service operation call.
 * 
 * @author Frank Ratschiski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class AspectInterceptorStrategy implements ServiceInterceptorStrategy {

    private static final String SERVICE_SEPERATOR = ".";

    private AspectMap handlerMap;

    /**
     * Creates a new {@link AspectInterceptorStrategy} instance.
     */
    public AspectInterceptorStrategy() {
        this.handlerMap = AspectMap.getInstance();
    }

    @Override
    public void beforeInvocation(InterceptorContext context, Service service, Method operation,
            ServiceRequest<?> request, NabuccoLogger logger) throws ServiceException {

        String serviceKey = service.getName() + SERVICE_SEPERATOR + operation.getName();
        String[] aspectList = service.getAspects(operation.getName());

        AspectExecutionContext aspectContext = new AspectExecutionContext(serviceKey);
        aspectContext.setServiceRequest(request);

        AspectHandler handler = this.getAspectHandler(serviceKey, aspectList);

        if (logger.isDebugEnabled()) {
            logger.debug("Calling before aspects for service=[" + serviceKey + "]");
        }

        try {
            handler.handleBeforeAspect(aspectContext, request);
        } catch (AspectException e) {
            throw new ServiceException("Error executing before service operation aspect.", e);
        }

        // Delegate the shorten flag for bypassing the execution.
        if (aspectContext.isShortenExecution()) {
            context.setShorten(true);
            context.put(InterceptorContextType.RESPONSE, aspectContext.getServiceResponse());
        }

        context.put(InterceptorContextType.ASPECT_CONTEXT, aspectContext);
    }

    @Override
    public void afterInvocation(InterceptorContext context, Service service, Method operation,
            ServiceRequest<?> request, ServiceResponse<?> response, NabuccoLogger logger, Throwable exception)
            throws ServiceException {

        String serviceKey = service.getName() + SERVICE_SEPERATOR + operation.getName();
        String[] aspectList = service.getAspects(operation.getName());

        AspectExecutionContext aspectContext = (AspectExecutionContext) context
                .get(InterceptorContextType.ASPECT_CONTEXT);

        aspectContext.setServiceRequest(request);
        aspectContext.setServiceResponse(response);

        AspectHandler handler = getAspectHandler(serviceKey, aspectList);

        if (logger.isDebugEnabled()) {
            logger.debug("Calling after aspects for service=[" + serviceKey + "]");
        }

        try {
            handler.handleAfterAspect(aspectContext, request, response);
        } catch (AspectException e) {
            throw new ServiceException("Error executing after service operation aspect.", e);
        }
    }

    /**
     * Retrieve the aspect handler from the list of configured extensions.
     * 
     * @param serviceKey
     *            the service key
     * @param aspectList
     *            the list of aspects
     * 
     * @return the configured aspect handler
     */
    private AspectHandler getAspectHandler(String serviceKey, String[] aspectList) {

        AspectHandler handler;
        if (this.handlerMap.hasMappedHandler(serviceKey)) {
            handler = handlerMap.getHandler(serviceKey);
        } else {
            AspectHandlerBuilder builder = new AspectHandlerBuilder(aspectList, serviceKey);
            handler = builder.getAspectHandler();
            handlerMap.put(serviceKey, handler);
        }
        return handler;
    }

}
