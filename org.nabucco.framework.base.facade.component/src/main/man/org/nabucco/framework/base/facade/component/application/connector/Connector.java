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
import org.nabucco.framework.base.facade.service.injection.Injectable;

/**
 * Connector
 * <p/>
 * A connector is responsible for connecting services together.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface Connector extends Injectable {

    /** Constant for connectors. */
    final String CONNECTOR_ID = "connector";

    /**
     * Getter for the connector strategy.
     * 
     * @return the connector strategy
     */
    ConnectorStrategy getStrategy();

    /**
     * Getter for the connector type.
     * 
     * @return the connector type
     */
    ConnectorType getType();

    /**
     * Setter for the intercepted service request.
     * 
     * @param <T>
     *            the service message type
     * @param request
     *            the request
     */
    <T extends ServiceMessage> void setSourceRequest(ServiceRequest<T> request);

    /**
     * Setter for the intercepted service response.
     * 
     * @param <T>
     *            the service message type
     * @param response
     *            the response
     */
    <T extends ServiceMessage> void setSourceResponse(ServiceResponse<T> response);

}
