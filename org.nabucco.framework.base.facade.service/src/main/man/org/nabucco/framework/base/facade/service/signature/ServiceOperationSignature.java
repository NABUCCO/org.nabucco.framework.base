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
package org.nabucco.framework.base.facade.service.signature;

import java.lang.reflect.Method;

import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.service.Service;

/**
 * ServiceOperationSignature
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ServiceOperationSignature {

    private String serviceId;

    private String operationId;

    private String requestMessage;

    /* Constants */

    private static final String EMPTY_SERVICE_MESSAGE = "org.nabucco.framework.base.facade.message.EmptyServiceMessage";

    /**
     * Creates a new {@link ServiceOperationSignature} instance.
     * 
     * @param serviceId
     *            the ID of the owning service
     * @param operationId
     *            the name of the service operation
     * @param requestMessage
     *            type of the request message
     */
    public ServiceOperationSignature(String serviceId, String operationId, String requestMessage) {
        if (serviceId == null) {
            throw new IllegalArgumentException(
                    "Cannot create service operation signature for serviceId [null].");
        }
        if (operationId == null) {
            throw new IllegalArgumentException(
                    "Cannot create service operation signature for operationId [null].");
        }

        this.serviceId = serviceId;
        this.operationId = operationId;

        if (this.requestMessage == null) {
            this.requestMessage = EMPTY_SERVICE_MESSAGE;
        }
        this.requestMessage = requestMessage;
    }

    /**
     * Creates a new {@link ServiceOperationSignature} instance.
     * 
     * @param service
     *            the service owning the operation
     * @param operation
     *            the service operation method
     */
    public ServiceOperationSignature(Service service, Method operation, ServiceRequest<?> request) {
        if (service == null) {
            throw new IllegalArgumentException(
                    "Cannot create service operation signature for service [null].");
        }
        if (operation == null) {
            throw new IllegalArgumentException(
                    "Cannot create service operation signature for operation [null].");
        }
        if (request == null || request.getRequestMessage() == null) {
            throw new IllegalArgumentException(
                    "Cannot create service operation signature for request [null].");
        }

        this.serviceId = service.getName();
        this.operationId = operation.getName();
        this.requestMessage = request.getRequestMessage().getClass().getCanonicalName();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.serviceId == null) ? 0 : this.serviceId.hashCode());
        result = prime * result + ((this.operationId == null) ? 0 : this.operationId.hashCode());
        result = prime
                * result + ((this.requestMessage == null) ? 0 : this.requestMessage.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ServiceOperationSignature)) {
            return false;
        }
        ServiceOperationSignature other = (ServiceOperationSignature) obj;
        if (this.serviceId == null) {
            if (other.serviceId != null) {
                return false;
            }
        } else if (!this.serviceId.equals(other.serviceId)) {
            return false;
        }
        if (this.operationId == null) {
            if (other.operationId != null) {
                return false;
            }
        } else if (!this.operationId.equals(other.operationId)) {
            return false;
        }
        if (this.requestMessage == null) {
            if (other.requestMessage != null) {
                return false;
            }
        } else if (!this.requestMessage.equals(other.requestMessage)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("ServiceOperation: ");
        result.append(this.serviceId);
        result.append('.');
        result.append(this.operationId);
        result.append('(');
        result.append(this.requestMessage);
        result.append(')');

        return result.toString();
    }

}
