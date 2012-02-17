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
package org.nabucco.framework.base.facade.component.connection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * ConnectionFactory
 * <p/>
 * Creates new connection instances.
 * 
 * @see Connection
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public final class ConnectionFactory {

    /** The pooled connections. */
    private Map<ConnectionSpecification, Connection> connectionPool;

    /** The local connection. */
    private static Connection local;

    /**
     * Singleton instance.
     */
    private static ConnectionFactory instance;

    /**
     * Private constructor.
     */
    private ConnectionFactory() {
        this.connectionPool = Collections.synchronizedMap(new HashMap<ConnectionSpecification, Connection>());
    }

    /**
     * Singleton access.
     * 
     * @return the ConnectionFactory instance.
     */
    public static synchronized ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }

    /**
     * Retrieves the connection to the current system for local calls.
     * 
     * @return the local connection
     * 
     * @throws ConnectionException
     *             when the local connection cannot be created
     */
    public synchronized Connection createLocalConnection() throws ConnectionException {
        if (local == null) {
            local = new LocalConnection();
        }
        return local;
    }

    /**
     * Retrieves the connection of the connection pool or creates a new one if it does not exist.
     * 
     * @param specification
     *            the connection specification
     * 
     * @return the connection
     * 
     * @throws ConnectionException
     *             when the local connection cannot be created
     */
    public synchronized Connection createConnection(ConnectionSpecification specification) throws ConnectionException {

        if (specification == null) {
            throw new IllegalArgumentException("Cannot create connection for specification [null].");
        }

        if (connectionPool.containsKey(specification)) {
            return connectionPool.get(specification);
        }

        Connection connection;
        ConnectionType type = specification.getConnectionType();

        switch (type) {
        case JBOSS:
            connection = new JBossConnection(specification);
            break;

        case GLASSFISH:
            connection = new GlassfishConnection(specification);
            break;

        default:
            return this.createLocalConnection();
        }

        connectionPool.put(specification, connection);
        return connection;
    }

}
