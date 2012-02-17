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
 * AdapterLocatorSupport
 * <p/>
 * Support class for adapter lookup.
 * 
 * @param <C>
 *            the adapter type
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class AdapterLocatorSupport<A extends Adapter> extends LocatorSupport<A> implements AdapterLocator<A> {

    /**
     * Creates a new {@link AdapterLocatorSupport} instance for an appropriate adapter and its JNDI
     * name.
     * 
     * @param jndiName
     *            the JNDI name
     * @param adapter
     *            the adapter class
     */
    public AdapterLocatorSupport(String jndiName, Class<A> adapter) {
        super(jndiName, adapter);
    }

    @Override
    public A getAdapter() throws ConnectionException {
        return super.lookup();
    }

    @Override
    public A getAdapter(Connection connection) throws ConnectionException {
        return super.lookup(connection);
    }

}
