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
package org.nabucco.framework.base.facade.component.locator;

import org.nabucco.framework.base.facade.component.Component;
import org.nabucco.framework.base.facade.component.connection.Connection;
import org.nabucco.framework.base.facade.component.connection.ConnectionException;

/**
 * ComponentLocator
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface ComponentLocator<C extends Component> {

    final String COMPONENTS = "components";

    /**
     * Looks up and returns the component for a specific connection.
     * 
     * @param connection
     *            the connection
     * 
     * @return the component interface
     * 
     * @throws ConnectionException
     */
    C getComponent(Connection connection) throws ConnectionException;

    /**
     * Looks up and returns the component from within the application server's JRE.
     * 
     * @return the component interface
     * 
     * @throws ConnectionException
     */
    C getComponent() throws ConnectionException;

}
