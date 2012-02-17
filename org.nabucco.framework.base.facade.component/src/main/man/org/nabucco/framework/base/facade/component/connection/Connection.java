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

import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

/**
 * Connection
 * <p/>
 * Abstract super-class that defines a connection between client and a remote server. Connections
 * may not be created manually. Use {@link ConnectionFactory} instead.
 * 
 * @see ConnectionFactory
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class Connection {

    private static final String LOCAL = "LOCAL";

    private Context context;

    private ConnectionSpecification spec;

    /**
     * Connections may not be created manually. Use {@link ConnectionFactory} instead.
     * 
     * @param spec
     *            the connection spec
     * 
     * @throws ConnectionException
     * 
     * @see ConnectionFactory
     */
    Connection(ConnectionSpecification specification) throws ConnectionException {

        Context context;

        if (specification != null) {
            context = this.initContext(specification.getProperties());
        } else {
            context = this.initContext(null);
        }

        if (context == null) {
            throw new ConnectionException("Context for '" + this.getClass().getSimpleName() + "' is not initialized.");
        }

        this.context = context;
        this.spec = specification;
    }

    /**
     * Looks up a component in the JNDI tree.
     * 
     * @param <C>
     *            the component to narrow to.
     * @param jndiName
     *            the JNDI name
     * @param narrowTo
     *            the type to narrow to
     * 
     * @return the looked-up component.
     * 
     * @throws ConnectionException
     */
    @SuppressWarnings("unchecked")
    <C> C lookup(String jndiName, Class<C> narrowTo) throws ConnectionException {
        try {
            Object ref = this.context.lookup(jndiName);
            return (C) PortableRemoteObject.narrow(ref, narrowTo);
        } catch (NamingException e) {
            String type = this.spec != null ? String.valueOf(this.spec.getConnectionType()) : LOCAL;
            throw new ConnectionException("Error connecting to resource '" + type + "'.", e);
        }
    }

    /**
     * Disconnects the connection to the resource.
     * 
     * @throws ConnectionException
     */
    void disconnect() throws ConnectionException {
        try {
            this.context.close();
        } catch (NamingException e) {
            String type = this.spec != null ? String.valueOf(this.spec.getConnectionType()) : LOCAL;
            throw new ConnectionException("Error disconnecting from resource '" + type + "'.", e);
        }
    }

    /**
     * Initializes the context for the appropriate connection.
     * 
     * @param properties
     *            additional connection properties
     * 
     * @return the context for the connection.
     * 
     * @throws ConnectionException
     *             when the context cannot be created
     */
    abstract Context initContext(Properties properties) throws ConnectionException;

    /**
     * Getter for the connection spec.
     * 
     * @return Returns the spec.
     */
    public ConnectionSpecification getSpec() {
        return this.spec;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Connection [spec=");

        if (this.spec != null) {
            builder.append(this.spec);
        } else {
            builder.append(LOCAL);
        }

        builder.append("]");
        return builder.toString();
    }

}
