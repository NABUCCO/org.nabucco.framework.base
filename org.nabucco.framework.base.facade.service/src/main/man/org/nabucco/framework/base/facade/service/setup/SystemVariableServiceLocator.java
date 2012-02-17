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
package org.nabucco.framework.base.facade.service.setup;

import javax.naming.Context;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.framework.base.facade.service.ServiceLocator;

/**
 * Locates the dynamic code service if deployed, a default implementation is return otherwise.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class SystemVariableServiceLocator extends ServiceLocator {

    /** The Logger */
    private static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            SystemVariableServiceLocator.class);

    /**
     * Locate the dynamic code service via JNDI.
     * 
     * @param ctx
     *            the initial context, default context when null
     * 
     * @return the dynamic code service
     */
    public SystemVariableService getSystemVariableService(Context ctx) {
        try {
            Service service = super.locateService(SystemVariableService.JNDI_NAME, ctx);
            return (SystemVariableService) service;
        } catch (Exception e) {
            logger.warning(e, "Cannot locate SystemVariableService, using a default implementation.");
            return new SystemVariableServiceDefaultImpl();
        }
    }
}
