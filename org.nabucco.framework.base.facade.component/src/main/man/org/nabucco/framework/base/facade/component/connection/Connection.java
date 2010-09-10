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

    private Context context;

    private ConnectionSpecification specification;

    /**
     * Connections may not be created manually. Use {@link ConnectionFactory} instead.
     * 
     * @param context
     *            the context to create the connection
     * @param specification
     *            the connection specification
     * 
     * @throws ConnectionException
     * 
     * @see ConnectionFactory
     */
    Connection(ConnectionSpecification specification) throws ConnectionException {
        if (specification == null) {
            throw new IllegalArgumentException("Connection specification must not be null.");
        }

        Context context = this.initContext(specification.getProperties());

        if (context == null) {
            throw new ConnectionException("Context for '"
                    + this.getClass().getSimpleName() + "' is not initialized.");
        }

        this.context = context;
        this.specification = specification;
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
            String type = String.valueOf(this.specification.getConnectionType());
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
            context.close();
        } catch (NamingException e) {
            String type = String.valueOf(this.specification.getConnectionType());
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
     */
    abstract Context initContext(Properties properties) throws ConnectionException;

    /**
     * @return Returns the specification.
     */
    public ConnectionSpecification getSpecification() {
        return this.specification;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Connection [specification=");
        builder.append(this.specification);
        builder.append("]");
        return builder.toString();
    }

}
