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

import java.util.HashMap;
import java.util.Map;

import javax.ejb.SessionContext;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.exception.resource.ResourceException;

/**
 * ResourceManagerImpl
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
class ResourceManagerImpl implements ResourceManager {

    private static final String JAVA_NAMESPACE = "java:";

    private SessionContext context;

    private NabuccoLogger logger;

    private Map<String, ConnectionFactory> connectionFactoryMap = new HashMap<String, ConnectionFactory>();

    /**
     * Creates a new {@link ResourceManagerImpl} instance.
     * 
     * @param context
     *            the session context for datatsource lookup
     * @param logger
     *            the logger
     */
    public ResourceManagerImpl(SessionContext context, NabuccoLogger logger) {
        this.context = context;
        this.logger = logger;
    }

    @Override
    public <C extends ResourceConnection> C getConnection(String jndi) throws ResourceException {

        try {
            this.initConnectionFactory(jndi);
            C connection = this.<C> loadConnection(jndi);
            return connection;
        } catch (Exception e) {
            logger.error(e, "Cannot lookup ConnectionFactory from jndi '" + jndi + "'.");
            throw new ResourceException("Cannot lookup ConnectionFactory.", e);
        }
    }

    /**
     * Load the connection from the connection factory.
     * 
     * @param jndi
     *            the connection factory jndi name
     * 
     * @return the loaded connection
     * 
     * @throws ResourceException
     *             when the connection cannot be loaded
     */
    @SuppressWarnings("unchecked")
    private <C extends ResourceConnection> C loadConnection(String jndi) throws ResourceException {

        ConnectionFactory factory = this.connectionFactoryMap.get(jndi);

        if (factory == null) {
            logger.error("Cannot find ConnectionFactory in jndi '" + jndi + "'.");
            throw new ResourceException("Cannot find ConnectionFactory in jndi '" + jndi + "'.");
        }

        String factoryType = factory.getClass().getCanonicalName();

        if (!(factory instanceof ResourceConnectionFactory)) {
            logger.error("ConnectionFactory '", factoryType,
                    "' is not of type org.nabucco.framework.base.impl.service.resource.ResourceConnectionFactory.");

            throw new ResourceException("ConnectionFactory '"
                    + factoryType
                    + "' is not of type org.nabucco.framework.base.impl.service.resource.ResourceConnectionFactory.");
        }

        Connection connection = null;

        try {
            connection = factory.getConnection();

        } catch (javax.resource.ResourceException jre) {
            try {
                logger.info("Re-initializing connection for jndi '", jndi, "'.");
                this.reInitConnectionFactory(jndi);
            } catch (Exception e) {
                logger.error(e, "Cannot lookup ConnectionFactory from jndi '" + jndi + "'.");
                throw new ResourceException(jre.getMessage());
            }
        }

        if (connection == null) {
            logger.error("Cannot get connection from connection factory '", factoryType, "'.");
            throw new ResourceException("Cannot get connection from connection factory '" + factoryType + "'.");
        }

        String connectionType = connection.getClass().getCanonicalName();

        if (!(connection instanceof ResourceConnection)) {
            logger.error("Connection '", connectionType,
                    "' is not of type org.nabucco.framework.base.impl.service.resource.ResourceConnection.");
            throw new ResourceException("Connection '"
                    + connectionType
                    + "' is not of type org.nabucco.framework.base.impl.service.resource.ResourceConnection.");
        }

        return (C) connection;
    }

    /**
     * Lookup the connection factory from JNDI.
     * 
     * @param jndi
     *            the resource JNDI name
     */
    private void initConnectionFactory(String jndi) {

        String javaJndi = JAVA_NAMESPACE + jndi;

        if (!this.connectionFactoryMap.containsKey(jndi)) {

            try {
                ResourceConnectionFactory factory = (ResourceConnectionFactory) this.context.lookup(javaJndi);
                this.connectionFactoryMap.put(jndi, factory);

            } catch (Exception e) {
                logger.error(e, "Cannot lookup ConnectionFactory from jndi '" + javaJndi + "'.");
            }
        }
    }

    /**
     * Re-Initialize the connection factory if the first initialization failed.
     * 
     * @param jndi
     *            the resource JNDI name
     */
    private void reInitConnectionFactory(String jndi) {

        String javaJndi = JAVA_NAMESPACE + jndi;

        try {
            ResourceConnectionFactory factory = (ResourceConnectionFactory) this.context.lookup(javaJndi);
            this.connectionFactoryMap.put(jndi, factory);

        } catch (Exception e) {
            logger.error(e, "Cannot lookup ConnectionFactory from jndi '" + javaJndi + "'.");
        }
    }
}
