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
package org.nabucco.framework.base.impl.service.resource;

import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.nabucco.framework.base.facade.component.handler.PingHandler;

/**
 * ResourcePingHandler
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class ResourcePingHandler extends PingHandler {

    private static final String JNDI_EJBCONTEXT = "java:comp/EJBContext";

    private static final long serialVersionUID = 1L;

    private ResourceManager resourceManager;

    /**
     * Getter for the resourceManager.
     * 
     * @return Returns the resourceManager.
     */
    public synchronized ResourceManager getResourceManager() {
        if (this.resourceManager == null) {
            try {
                InitialContext initialContext = new InitialContext();
                SessionContext sessionContext = (SessionContext) initialContext.lookup(JNDI_EJBCONTEXT);

                this.resourceManager = ResourceManagerFactory.getInstance().createResourceManager(sessionContext,
                        super.getLogger());

            } catch (NamingException ne) {
                super.getLogger().error(ne, "Error locating session context from JNDI.");
            } catch (Exception e) {
                super.getLogger().error(e, "Error locating session context from JNDI.");
            }
        }
        return this.resourceManager;
    }

}
