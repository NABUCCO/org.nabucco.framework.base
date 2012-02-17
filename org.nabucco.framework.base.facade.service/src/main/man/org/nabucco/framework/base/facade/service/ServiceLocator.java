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
package org.nabucco.framework.base.facade.service;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;

/**
 * ServiceLocator
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class ServiceLocator {

    private static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(ServiceLocator.class);

    /**
     * Locate a service from JNDI.
     * 
     * @param serviceJNDIName
     *            the service JNDI name
     * @param ctx
     *            the initial context
     * 
     * @return the located service
     * 
     * @throws ServiceLocationException
     *             when the service cannot be looked-up in the JNDI tree
     */
    protected Service locateService(String serviceJNDIName, Context ctx) throws ServiceLocationException {
        try {
            if (ctx == null) {
                ctx = new InitialContext();
            }
            Object serviceObject = ctx.lookup(serviceJNDIName);
            Service service = (Service) serviceObject;

            return service;
        } catch (Exception e) {
            logger.error(e, "Cannot lookup service ", "[", serviceJNDIName, "].");
            throw new ServiceLocationException("Cannot lookup service with JNDI name [" + serviceJNDIName + "].", e);
        }
    }
}
