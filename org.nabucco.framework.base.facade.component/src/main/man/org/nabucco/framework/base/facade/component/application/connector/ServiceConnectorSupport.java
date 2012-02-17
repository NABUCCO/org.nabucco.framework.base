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
package org.nabucco.framework.base.facade.component.application.connector;

import org.nabucco.framework.base.facade.exception.NabuccoException;

/**
 * ServiceConnectorSupport
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class ServiceConnectorSupport extends ConnectorSupport implements ServiceConnector {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new {@link ServiceConnectorSupport} instance.
     * 
     * @param strategy
     *            the connector strategy
     */
    protected ServiceConnectorSupport(ConnectorStrategy strategy) {
        super(strategy, ConnectorType.SERVICE);
    }

    @Override
    public final void connect() throws ConnectorException {
        try {
            this.internalConnect();
        } catch (NabuccoException ne) {
            throw new ConnectorException(ne);
        } catch (Exception e) {
            throw new ConnectorException(e);
        }
    }

    /**
     * Executes the actual connection.
     * 
     * @throws NabuccoException
     *             if the connection was not succesful
     */
    protected abstract void internalConnect() throws NabuccoException;

}
