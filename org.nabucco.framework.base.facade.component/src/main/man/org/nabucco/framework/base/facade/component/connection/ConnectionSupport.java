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

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.nabucco.framework.base.facade.component.locator.Locatable;

/**
 * ConnectionSupport
 * <p/>
 * Support class for JNDI lookups over {@link Connection} instances.
 * 
 * @param <C>
 *            the component type
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class ConnectionSupport<L extends Locatable> {

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
     *             when the component cannot be located
     */
    protected L lookupLocatable(Connection connection, String jndiName, Class<L> narrowTo) throws ConnectionException {
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
     *             when the component cannot be located
     */
    @SuppressWarnings("unchecked")
    protected L lookupLocatable(String jndiName, Class<L> narrowTo) throws ConnectionException {
        try {
            InitialContext context = new InitialContext();
            Object ref = context.lookup(jndiName);
            return (L) PortableRemoteObject.narrow(ref, narrowTo);
        } catch (NamingException e) {
            throw new ConnectionException(e);
        }
    }
}
