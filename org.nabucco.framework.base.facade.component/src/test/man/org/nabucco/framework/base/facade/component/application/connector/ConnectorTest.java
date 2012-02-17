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

import org.junit.Assert;
import org.junit.Test;
import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.service.signature.ServiceOperationSignature;

/**
 * ConnectorTest
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ConnectorTest {

    @Test
    public void testConnector() throws Exception {

        ServiceConnector serviceConnector = new DummyHandlerImpl();

        serviceConnector.setSourceRequest(null);
        serviceConnector.setSourceResponse(null);

        serviceConnector.connect();
    }

    abstract class DummyConnector extends ServiceConnectorSupport implements ServiceConnector {

        private static final long serialVersionUID = 1L;

        public DummyConnector() {
            super(ConnectorStrategy.BEFORE);
        }

        @Override
        public void internalConnect() throws NabuccoException {
            ServiceMessage request = null;
            this.preInvokeX(request);

            ServiceMessage response = this.invoke(request);
            this.postInvokeX(response);
        }

        private ServiceMessage invoke(ServiceMessage requestMessage) throws NabuccoException {
            // ComponentLocator locator = ComponentLocator.getInstance();
            // Component component = locator.getComponent();
            // Service service = component.getService();

            ServiceRequest<ServiceMessage> request = new ServiceRequest<ServiceMessage>(super.getServiceContext());

            request.setRequestMessage(requestMessage);

            // ServiceResponse<ServiceMessage> response = service.invoke(request);
            // return response.getResponseMessage();

            return null;
        }

        @Override
        public ServiceOperationSignature getSourceOperationSignature() {
            return null;
        }

        protected abstract void preInvokeX(ServiceMessage requestMessage) throws ConnectorException;

        protected abstract void postInvokeX(ServiceMessage responseMessage) throws ConnectorException;

    }

    class DummyHandlerImpl extends DummyConnector implements ServiceConnector {

        private static final long serialVersionUID = 1L;

        @Override
        public void preInvokeX(ServiceMessage requestMessage) throws ConnectorException {
            Assert.assertNotNull(requestMessage);
        }

        @Override
        public void postInvokeX(ServiceMessage responseMessage) throws ConnectorException {
            Assert.assertNotNull(responseMessage);
        }
    }

}
