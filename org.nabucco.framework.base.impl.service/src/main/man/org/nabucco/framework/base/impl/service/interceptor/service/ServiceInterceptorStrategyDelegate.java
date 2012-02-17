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

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.impl.service.interceptor.service.aspect.AspectInterceptorStrategy;
import org.nabucco.framework.base.impl.service.interceptor.service.logging.LoggingInterceptorStrategy;
import org.nabucco.framework.base.impl.service.interceptor.service.monitor.MonitorInterceptorStrategy;

/**
 * ServiceInterceptorStrategyDelegate
 * 
 * @author Nicolas Moser, PRODYNA AG
 * @author Frank Ratschinski, PRODYNA AG
 */
class ServiceInterceptorStrategyDelegate extends ServiceInterceptorStrategySupport implements
        ServiceInterceptorStrategy {

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            ServiceInterceptorStrategyDelegate.class);

    /**
     * Creates a new {@link ServiceInterceptorStrategyDelegate} instance.
     */
    public ServiceInterceptorStrategyDelegate() {

        logger.debug("Initializing ServiceInterceptorStrategyDelegate.");

        try {

            // TODO: Move to ExtensionPoint
            
            super.addStrategy(new LoggingInterceptorStrategy());
            logger.trace("Adding LoggingInterceptorStrategy to ServiceInterceptorStrategyDelegate.");

            super.addStrategy(new MonitorInterceptorStrategy());
            logger.trace("Adding MonitorInterceptorStrategy to ServiceInterceptorStrategyDelegate.");

            // super.addStrategy(new DatatypeConnectorInterceptorStrategy());
            // logger.trace("Adding DatatypeConnectorInterceptorStrategy to ServiceInterceptorStrategyDelegate.");
            //
            // super.addStrategy(new ServiceConnectorInterceptorStrategy());
            // logger.trace("Adding ServiceConnectorInterceptorStrategy to ServiceInterceptorStrategyDelegate.");

            super.addStrategy(new AspectInterceptorStrategy());
            logger.trace("Adding AspectInterceptorStrategy to ServiceInterceptorStrategyDelegate.");

        } catch (Exception e) {
            logger.error(e, "Error during service interceptor strategy chain initialization: " + e.getMessage());
        }
    }
}
