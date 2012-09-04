/*
 * Copyright 2012 PRODYNA AG
 * 
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.nabucco.framework.base.facade.service.authorization;

import javax.naming.Context;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.service.ServiceLocator;

/**
 * AuthorizationServiceLocator<p/>Service for resolving users and permissions from authorization component.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-04-18
 */
public class AuthorizationServiceLocator extends ServiceLocator {

    private static final String JNDI_NAME = "nabucco/org.nabucco.framework.base/org.nabucco.framework.base.facade.service.authorization.AuthorizationService/local";

    private static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            AuthorizationService.class);

    /** Constructs a new AuthorizationServiceLocator instance. */
    public AuthorizationServiceLocator() {
        super();
    }

    /**
     * Getter for the Service.
     *
     * @param ctx the Context.
     * @return the AuthorizationService.
     */
    public AuthorizationService getService(Context ctx) {
        try {
            return ((AuthorizationService) super.locateService(JNDI_NAME, ctx));
        } catch (Exception e) {
            logger.warning(e,
                    (("Cannot locate Service from JNDI \'" + JNDI_NAME) + "\', using a default implementation."));
            return new AuthorizationServiceDefaultImpl();
        }
    }
}
