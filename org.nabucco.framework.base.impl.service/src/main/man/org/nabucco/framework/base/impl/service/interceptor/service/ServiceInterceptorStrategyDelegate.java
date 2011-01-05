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

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.impl.service.interceptor.service.connector.DatatypeConnectorInterceptorStrategy;
import org.nabucco.framework.base.impl.service.interceptor.service.connector.ServiceConnectorInterceptorStrategy;
import org.nabucco.framework.base.impl.service.interceptor.service.logging.LoggingInterceptorStrategy;

/**
 * ServiceInterceptorStrategyDelegate
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
class ServiceInterceptorStrategyDelegate extends ServiceInterceptorStrategySupport implements
        ServiceInterceptorStrategy {

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            ServiceInterceptorStrategyDelegate.class);

    public ServiceInterceptorStrategyDelegate() {

        logger.debug("Initializing ServiceInterceptorStrategyDelegate");

        try {

            super.addStrategy(new LoggingInterceptorStrategy());
            logger.debug("Adding LoggingInterceptorStrategy to ServiceInterceptorStrategyDelegate");
            
            super.addStrategy(new DatatypeConnectorInterceptorStrategy());
            logger.debug("Adding DatatypeConnectorInterceptorStrategy to ServiceInterceptorStrategyDelegate");

            super.addStrategy(new ServiceConnectorInterceptorStrategy());
            logger.debug("Adding ServiceConnectorInterceptorStrategy to ServiceInterceptorStrategyDelegate");

        } catch (Exception e) {
            logger.warning(e, "Error during service interceptor strategy chain initialization: "
                    + e.getMessage());
        }
    }
}
