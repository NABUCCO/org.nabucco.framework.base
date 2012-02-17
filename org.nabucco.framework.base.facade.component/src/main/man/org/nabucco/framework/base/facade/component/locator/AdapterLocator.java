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
package org.nabucco.framework.base.facade.component.locator;

import org.nabucco.framework.base.facade.component.adapter.Adapter;
import org.nabucco.framework.base.facade.component.connection.Connection;
import org.nabucco.framework.base.facade.component.connection.ConnectionException;

/**
 * AdapterLocator
 * <p/>
 * Looks up an adapter from JNDI.
 * 
 * @param <A>
 *            the adapter type
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface AdapterLocator<A extends Adapter> extends Locator {

    /**
     * Looks up and returns the adapter for a specific connection. This lookup only returns EJBs
     * with Remote Interfaces.
     * 
     * @param connection
     *            the connection to use for remote connection
     * 
     * @return the remote adapter EJB
     * 
     * @throws ConnectionException
     *             when the connection cannot be established
     */
    A getAdapter(Connection connection) throws ConnectionException;

    /**
     * Looks up and returns the adapter from within the application server's JRE. This lookup only
     * returns EJBs with Local Interfaces.
     * 
     * @return the local adapter EJB
     * 
     * @throws ConnectionException
     *             when the connection cannot be established
     */
    A getAdapter() throws ConnectionException;

}
