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

import org.nabucco.framework.base.facade.component.Component;
import org.nabucco.framework.base.facade.component.connection.Connection;
import org.nabucco.framework.base.facade.component.connection.ConnectionException;

/**
 * ComponentLocatorSupport
 * <p/>
 * Support class for component lookup.
 * 
 * @param <C>
 *            the component type
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class ComponentLocatorSupport<C extends Component> extends LocatorSupport<C> implements
        ComponentLocator<C> {

    /**
     * Creates a new {@link ComponentLocatorSupport} instance for an appropriate component and its
     * JNDI name.
     * 
     * @param jndiName
     *            the JNDI name
     * @param component
     *            the component class
     */
    public ComponentLocatorSupport(String jndiName, Class<C> component) {
        super(jndiName, component);
    }

    @Override
    public C getComponent() throws ConnectionException {
        return super.lookup();
    }

    @Override
    public C getComponent(Connection connection) throws ConnectionException {
        return super.lookup(connection);
    }

}
