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

import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;

/**
 * ConnectorSupport
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class ConnectorSupport implements Connector {

    private static final long serialVersionUID = 1L;

    private ConnectorStrategy strategy;

    private ConnectorType type;

    private ServiceRequest<?> request;

    private ServiceResponse<?> response;

    /**
     * Creates a new {@link ConnectorSupport} instance.
     * 
     * @param strategy
     *            the connector strategy
     * @param type
     *            the connector type
     */
    protected ConnectorSupport(ConnectorStrategy strategy, ConnectorType type) {
        this.strategy = strategy;
        this.type = type;
    }

    @Override
    public final ConnectorStrategy getStrategy() {
        return this.strategy;
    }

    @Override
    public final ConnectorType getType() {
        return this.type;
    }

    @Override
    public final <T extends ServiceMessage> void setSourceRequest(ServiceRequest<T> request) {
        this.request = request;
    }

    @Override
    public final <T extends ServiceMessage> void setSourceResponse(ServiceResponse<T> response) {
        this.response = response;
    }

    /**
     * Getter for the request message of the intercepted service.
     * 
     * @return Returns the service request message.
     */
    protected final ServiceMessage getSourceRequestMessage() {
        if (this.request == null) {
            return null;
        }
        return this.request.getRequestMessage();
    }

    /**
     * Getter for the response message of the intercepted service or <b>null</b> for connector
     * strategy BEFORE.
     * 
     * @return Returns the service response message.
     */
    protected final ServiceMessage getSourceResponseMessage() {
        if (this.response == null) {
            return null;
        }
        return this.response.getResponseMessage();
    }

    /**
     * Getter for the service context.
     * 
     * @return Returns the service context.
     */
    protected final ServiceMessageContext getServiceContext() {
        if (this.request == null) {
            return null;
        }
        return this.request.getContext();
    }

}
