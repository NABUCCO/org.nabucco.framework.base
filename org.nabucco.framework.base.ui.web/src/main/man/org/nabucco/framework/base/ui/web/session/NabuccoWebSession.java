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
package org.nabucco.framework.base.ui.web.session;

import static org.nabucco.framework.base.facade.datatype.extension.ExtensionResolver.DEFAULT_EXTENSION;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.component.connection.ConnectionSpecification;
import org.nabucco.framework.base.facade.component.connection.ConnectionType;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionResolver;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.connection.ConnectionExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.application.ApplicationExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.session.NabuccoSession;
import org.nabucco.framework.base.ui.web.component.WebApplication;

/**
 * NabuccoWebSession
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class NabuccoWebSession extends NabuccoSession {

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(NabuccoWebSession.class);

    private WebApplication application;

    private ConnectionSpecification connection;

    private boolean active = true;

    /**
     * Do not invoke manually. Use
     * {@link NabuccoWebSessionFactory#createSession(javax.servlet.http.HttpSession)} instead.
     */
    NabuccoWebSession() {
        this.initConnection();
    }

    /**
     * Initialize the server connection.
     */
    private void initConnection() {

        try {
            ConnectionExtension extension = (ConnectionExtension) NabuccoSystem.getExtensionResolver()
                    .resolveExtension(ExtensionPointType.ORG_NABUCCO_CONNECTION, ExtensionResolver.DEFAULT_EXTENSION);

            ConnectionType connectionType = PropertyLoader
                    .loadProperty(ConnectionType.class, extension.getServerType());

            String name = PropertyLoader.loadProperty(extension.getName());
            String host = PropertyLoader.loadProperty(extension.getHost());
            String port = PropertyLoader.loadProperty(extension.getPort());
            String environment = PropertyLoader.loadProperty(extension.getEnvironmentType());

            this.connection = new ConnectionSpecification(connectionType, environment, host, port, name);

        } catch (ExtensionException ee) {
            logger.fatal(ee, "Cannot configure Server Connection.");
        } catch (Exception e) {
            logger.fatal(e, "Cannot configure Server Connection.");
        }
    }

    /**
     * Initialize the web application.
     */
    private void initApplication() {
        try {
            ApplicationExtension extension = (ApplicationExtension) NabuccoSystem.getExtensionResolver()
                    .resolveExtension(ExtensionPointType.ORG_NABUCCO_UI_APPLICATION, DEFAULT_EXTENSION);

            this.application = new WebApplication(extension);
            this.application.init();

        } catch (ExtensionException e) {
            logger.fatal(e, "Cannot configure WebApplication with ApplicationExtension.");
        }
    }

    /**
     * Getter for the application.
     * 
     * @return Returns the application.
     */
    public WebApplication getWebApplication() {
        if (!this.getSecurityContext().isAuthenticated()) {
            return null;
        }

        if (this.application == null) {
            this.initApplication();
        }

        return this.application;
    }

    /**
     * Getter for the connection.
     * 
     * @return Returns the connection.
     */
    public ConnectionSpecification getConnection() {
        return this.connection;
    }

    /**
     * Reset the session.
     */
    public void reset() {
        this.active = false;
    }

    /**
     * Getter for the isActive.
     * 
     * @return Returns the isActive.
     */
    public boolean isActive() {
        return this.active;
    }
}
