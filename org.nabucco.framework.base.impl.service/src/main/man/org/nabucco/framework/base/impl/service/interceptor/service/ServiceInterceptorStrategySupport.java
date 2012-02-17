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
import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.framework.base.impl.service.interceptor.context.InterceptorContext;

/**
 * ServiceInterceptorStrategySupport
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
abstract class ServiceInterceptorStrategySupport implements ServiceInterceptorStrategy {

    /** Sub strategies to delegate to. */
    private List<ServiceInterceptorStrategy> delegateStrategies = new ArrayList<ServiceInterceptorStrategy>();

    /**
     * Adds a strategy to the current strategy.
     * 
     * @param strategy
     *            the strategy to add
     */
    protected void addStrategy(ServiceInterceptorStrategy strategy) {
        if (strategy != null) {
            this.delegateStrategies.add(strategy);
        }
    }

    @Override
    public void beforeInvocation(InterceptorContext context, Service service, Method method, ServiceRequest<?> request,
            NabuccoLogger logger) throws ServiceException {

        for (ServiceInterceptorStrategy strategy : this.delegateStrategies) {

            // Before Invocation
            strategy.beforeInvocation(context, service, method, request, logger);

            context.incrementDepth();

            if (context.isShorten()) {
                break;
            }

        }
    }

    @Override
    public void afterInvocation(InterceptorContext context, Service service, Method method, ServiceRequest<?> request,
            ServiceResponse<?> response, NabuccoLogger logger, Throwable exception) throws ServiceException {

        ServiceInterceptorStrategy strategy;

        // Starting at last executed position in reverse order
        for (int i = context.getDepth() - 1; i >= 0; i--) {
            strategy = this.delegateStrategies.get(i);

            // After Invocation
            strategy.afterInvocation(context, service, method, request, response, logger, exception);
        }

    }

}
