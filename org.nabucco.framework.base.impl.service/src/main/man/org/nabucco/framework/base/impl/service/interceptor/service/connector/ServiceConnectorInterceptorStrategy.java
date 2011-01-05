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
package org.nabucco.framework.base.impl.service.interceptor.service.connector;

import java.lang.reflect.Method;

import org.nabucco.framework.base.facade.component.NabuccoInstance;
import org.nabucco.framework.base.facade.component.application.Application;
import org.nabucco.framework.base.facade.component.application.connector.Connector;
import org.nabucco.framework.base.facade.component.application.connector.ConnectorException;
import org.nabucco.framework.base.facade.component.application.connector.ConnectorStrategy;
import org.nabucco.framework.base.facade.component.application.connector.ConnectorType;
import org.nabucco.framework.base.facade.component.application.connector.ServiceConnector;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.framework.base.facade.service.signature.ServiceOperationSignature;
import org.nabucco.framework.base.impl.service.interceptor.context.InterceptorContext;
import org.nabucco.framework.base.impl.service.interceptor.service.ServiceInterceptorStrategy;

/**
 * ServiceConnectorInterceptorStrategy
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ServiceConnectorInterceptorStrategy implements ServiceInterceptorStrategy {

    @Override
    public void beforeInvocation(InterceptorContext context, Service service, Method operation,
            ServiceRequest<?> request, NabuccoLogger logger) throws ServiceException {

        Application application = NabuccoInstance.getInstance().getApplication();

        if (application == null) {
            logger.warning("No application has been configured for NabuccoInstance.");
            return;
        }

        ServiceOperationSignature signature = new ServiceOperationSignature(service, operation,
                request);

        for (Connector connector : application.getConnectors(ConnectorStrategy.BEFORE)) {

            if (connector.getType() == ConnectorType.SERVICE) {
                ServiceConnector serviceConnector = (ServiceConnector) connector;

                if (signature.equals(serviceConnector.getSourceOperationSignature())) {
                    this.connect(request, null, serviceConnector);
                }
            }
        }
    }

    @Override
    public void afterInvocation(InterceptorContext context, Service service, Method operation,
            ServiceRequest<?> request, ServiceResponse<?> response, NabuccoLogger logger,
            Throwable exception) throws ServiceException {

        if (exception != null) {
            logger.warning("Component connection failed. Cannot connect service operations when exception occurs.");
            return;
        }

        Application application = NabuccoInstance.getInstance().getApplication();

        if (application == null) {
            logger.warning("No application has been configured for NabuccoInstance.");
            return;
        }

        ServiceOperationSignature signature = new ServiceOperationSignature(service, operation,
                request);

        for (Connector connector : application.getConnectors(ConnectorStrategy.AFTER)) {

            if (connector.getType() == ConnectorType.SERVICE) {
                ServiceConnector serviceConnector = (ServiceConnector) connector;

                if (signature.equals(serviceConnector.getSourceOperationSignature())) {
                    this.connect(request, response, serviceConnector);
                }
            }
        }

    }

    /**
     * Connect the service operations.
     * 
     * @param request
     *            the source service request
     * @param response
     *            the source service response
     * @param serviceConnector
     *            the connector
     * 
     * @throws ServiceException
     *             when the connection fails or the target service raises an exception
     */
    private void connect(ServiceRequest<?> request, ServiceResponse<?> response,
            ServiceConnector serviceConnector) throws ServiceException {
        try {
            serviceConnector.setSourceRequest(request);
            serviceConnector.setSourceResponse(response);
            serviceConnector.connect();
        } catch (ConnectorException e) {
            throw new ServiceException(e);
        }
    }
}
