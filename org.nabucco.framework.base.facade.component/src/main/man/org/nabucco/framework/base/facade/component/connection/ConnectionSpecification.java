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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;

/**
 * ConnectionSpecification
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ConnectionSpecification implements ConnectionConstants {

    /** The current connection specification. */
    private static ConnectionSpecification currentSpecification;

    private static List<ConnectionSpecification> connections = new ArrayList<ConnectionSpecification>();

    private ConnectionType connectionType;

    private String name;

    private String environment;

    private String protocol;

    private String host;

    private String port;

    private Properties properties;

    /** Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(ConnectionSpecification.class);

    /**
     * Creates a new {@link ConnectionSpecification} instance using default URL information.
     * 
     * @param type
     *            the connection type specifying the default values
     */
    public ConnectionSpecification(ConnectionType type) {
        this(type, (String) null);
    }

    /**
     * Creates a new {@link ConnectionSpecification} instance using default URL information.
     * 
     * @param type
     *            the connection type specifying the default values
     * @param environment
     *            the connection environment
     */
    public ConnectionSpecification(ConnectionType type, String environment) {
        this(type, environment, null, null, null);
    }

    /**
     * Creates a new {@link ConnectionSpecification} instance using default URL information.
     * 
     * @param type
     *            the connection type specifying the default values
     * @param environment
     *            the connection environment
     * @param host
     *            the connection host (default=localhost)
     */
    public ConnectionSpecification(ConnectionType type, String environment, String host) {
        this(type, environment, host, null, null);
    }

    /**
     * Creates a new {@link ConnectionSpecification} instance using default URL information.
     * 
     * @param type
     *            the connection type specifying the default values
     * @param environment
     *            the connection environment
     * @param host
     *            the connection host (default=localhost)
     * @param port
     *            the connection port
     */
    public ConnectionSpecification(ConnectionType type, String environment, String host, String port) {
        this(type, environment, host, port, null);
    }

    /**
     * Creates a new {@link ConnectionSpecification} instance using specified URL information.
     * 
     * @param type
     *            the connection type.
     * @param environment
     *            the connection environment (for identification)
     * @param host
     *            the connection host (default=localhost)
     * @param port
     *            the connection port
     * @param name
     *            the connection name
     */
    public ConnectionSpecification(ConnectionType type, String environment, String host, String port, String name) {
        if (type == null) {
            throw new IllegalArgumentException("ConnectionType must be defined.");
        }

        this.connectionType = type;
        this.name = name;
        this.environment = environment != null ? environment : type.name();
        this.protocol = type.getDefaultProtocol();
        this.host = host != null ? host : LOCALHOST;
        this.port = (port != null) ? port : type.getDefaultPort();

        StringBuilder url = new StringBuilder();
        url.append(this.protocol);
        url.append("://");
        url.append(this.host);
        url.append(":");
        url.append(this.port);

        this.properties = new Properties();
        this.properties.put(Context.PROVIDER_URL, url.toString());
    }

    /**
     * Creates a new {@link ConnectionSpecification} instance with connection properties.
     * 
     * @param connectionType
     *            the connection type
     * @param properties
     *            the connection propeties
     */
    public ConnectionSpecification(ConnectionType connectionType, Properties properties) {
        this.connectionType = connectionType;
        this.properties = properties;
    }

    /**
     * Returns all connection specifications defined for this client.
     * 
     * @return the list of possible connections.
     */
    public static List<ConnectionSpecification> getAllConnectionSpecifications() {
        return connections;
    }

    /**
     * Getter for the current specification.
     * 
     * @return the current specification
     */
    public static ConnectionSpecification getCurrentSpecification() {
        return currentSpecification;
    }

    /**
     * Setter for the current specification.
     * 
     * @param specification
     *            the current specification to set
     */
    public static void setCurrentSpecification(ConnectionSpecification specification) {
        currentSpecification = specification;
    }

    /**
     * Parses properties for the connection specifications.
     * 
     * @param properties
     *            the properties to parse
     * 
     * @return the configured connections
     */
    public static List<ConnectionSpecification> parse(Properties properties) {
        if (properties == null) {
            return Collections.emptyList();
        }

        List<ConnectionSpecification> specifications = new ArrayList<ConnectionSpecification>();

        for (int i = 0; i < 10; i++) {

            final String INDEX = "[" + i + "]";

            String type = properties.getProperty(CONNECTION_TYPE + INDEX);
            String name = properties.getProperty(CONNECTION_NAME + INDEX);
            String env = properties.getProperty(CONNECTION_ENVIRONMENT + INDEX);
            String host = properties.getProperty(CONNECTION_HOST + INDEX);
            String port = properties.getProperty(CONNECTION_PORT + INDEX);

            if (type == null || env == null || host == null || port == null) {
                logger.debug("Skipping configuration of connection '" + env + "'.");
                continue;
            }

            try {
                ConnectionType target = ConnectionType.valueOf(type);
                specifications.add(new ConnectionSpecification(target, env, host, port, name));
            } catch (Exception e) {
                // Skip Connection!
                logger.debug(e, "Skipping configuration of connection '" + env + "'.");
            }
        }

        return specifications;
    }

    /**
     * Getter for the connection type.
     * 
     * @return Returns the connectionType.
     */
    public ConnectionType getConnectionType() {
        return this.connectionType;
    }

    /**
     * Getter for the name.
     * 
     * @return Returns the name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter for the connection environment.
     * 
     * @return Returns the environment.
     */
    public String getEnvironment() {
        return this.environment;
    }

    /**
     * Getter for the connection protocol.
     * 
     * @return Returns the protocol.
     */
    public String getProtocol() {
        return this.protocol;
    }

    /**
     * Getter for the connection host.
     * 
     * @return Returns the host.
     */
    public String getHost() {
        return this.host;
    }

    /**
     * Getter for the connection port.
     * 
     * @return Returns the port.
     */
    public String getPort() {
        return this.port;
    }

    /**
     * Getter for the connection properties.
     * 
     * @return Returns the properties.
     */
    Properties getProperties() {
        return this.properties;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.connectionType);
        builder.append(" (");
        builder.append(this.environment);
        builder.append(", url=");
        builder.append(this.protocol);
        builder.append("://");
        builder.append(this.host);
        builder.append(":");
        builder.append(this.port);
        builder.append(")");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.connectionType == null) ? 0 : this.connectionType.hashCode());
        result = prime * result + ((this.environment == null) ? 0 : this.environment.hashCode());
        result = prime * result + ((this.properties == null) ? 0 : this.properties.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof ConnectionSpecification))
            return false;
        ConnectionSpecification other = (ConnectionSpecification) obj;
        if (this.connectionType == null) {
            if (other.connectionType != null)
                return false;
        } else if (!this.connectionType.equals(other.connectionType))
            return false;
        if (this.environment == null) {
            if (other.environment != null)
                return false;
        } else if (!this.environment.equals(other.environment))
            return false;
        if (this.properties == null) {
            if (other.properties != null)
                return false;
        } else if (!this.properties.equals(other.properties))
            return false;
        return true;
    }

}
