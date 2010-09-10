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

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.nabucco.framework.base.facade.component.Component;

/**
 * ConnectionSupport
 * <p/>
 * Support class for JNDI lookups over {@link Connection} instances.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class ConnectionSupport<C extends Component> {

    /**
     * Looks up the nabucco component from the connection.
     * 
     * @param connection
     *            the connection to lookup from.
     * @param jndiName
     *            the JNDI name of the component to lookup
     * @param narrowTo
     *            the component's class to narrow to
     * 
     * @return the component.
     * 
     * @throws ConnectionException
     */
    protected C lookupComponent(Connection connection, String jndiName, Class<C> narrowTo)
            throws ConnectionException {
        return connection.lookup(jndiName, narrowTo);
    }

    /**
     * Looks up the nabucco component from within the ejb container
     * 
     * @param jndiName
     *            the JNDI name of the component to lookup
     * @param narrowTo
     *            the component's class to narrow to
     * 
     * @return the component.
     * 
     * @throws ConnectionException
     */
    @SuppressWarnings("unchecked")
    protected C lookupComponent(String jndiName, Class<C> narrowTo) throws ConnectionException {
        try {

            // TODO: Externalize into connection

            InitialContext context = new InitialContext();
            Object ref = context.lookup(jndiName);
            return (C) PortableRemoteObject.narrow(ref, narrowTo);
        } catch (NamingException e) {
            throw new ConnectionException(e);
        }
    }
}
