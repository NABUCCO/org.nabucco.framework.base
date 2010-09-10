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
package org.nabucco.framework.base.facade.component.connection;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;

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

    private String environment;

    private String protocol;

    private String host;

    private String port;

    private Properties properties;

    /**
     * Creates a new {@link ConnectionSpecification} instance using default URL information.
     * 
     * @param type
     *            the connection type specifying the default values
     */
    public ConnectionSpecification(ConnectionType type) {
        this(type, null, null, null);
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
     */
    public ConnectionSpecification(ConnectionType type, String environment, String host, String port) {
        if (type == null) {
            throw new IllegalArgumentException("ConnectionType must be defined.");
        }

        this.connectionType = type;
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
     * Getter for the connection type.
     * 
     * @return Returns the connectionType.
     */
    public ConnectionType getConnectionType() {
        return this.connectionType;
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
        result = prime
                * result + ((this.connectionType == null) ? 0 : this.connectionType.hashCode());
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
