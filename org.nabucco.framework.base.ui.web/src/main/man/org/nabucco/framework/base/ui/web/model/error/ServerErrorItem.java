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
package org.nabucco.framework.base.ui.web.model.error;

import org.nabucco.framework.base.facade.exception.exceptionmsg.ExceptionMessage;
import org.nabucco.framework.base.facade.exception.exceptionmsg.MessageResolver;
import org.nabucco.framework.base.facade.message.tracing.InvocationIdentifier;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.WebModel;
import org.nabucco.framework.base.ui.web.session.NabuccoWebSession;
import org.nabucco.framework.base.ui.web.session.NabuccoWebSessionFactory;

/**
 * ErrorContainerItem
 * 
 * The error item that wrap one occured exception
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ServerErrorItem extends WebModel {

    private static final String JSON_MESSAGE = "message";

    private static final String JSON_SEVERITY = "severity";

    /** The full qualified class of the service that threw the exception */
    private String serviceName;

    /** The name of the operation that threw the exception */
    private String operationName;

    /** The exception which was thrown */
    private Exception exception;

    /** The error message translator */
    private MessageResolver messageResolver = MessageResolver.getInstance();

    /** The invocation identifier of the error */
    private InvocationIdentifier iid;

    /** The resolved message */
    private ExceptionMessage message;

    /**
     * The new Error container.
     * 
     * @param serviceName
     *            the name of the service that throws the exception
     * @param operationName
     *            the name of the operation that throws the exception
     * @param iid
     *            the invocation identifier
     * @param exception
     *            the raised exception
     */
    public ServerErrorItem(String serviceName, String operationName, InvocationIdentifier iid, Exception exception) {
        if (serviceName == null) {
            throw new IllegalArgumentException("Cannot create error container item for service 'null'.");
        }
        if (operationName == null) {
            throw new IllegalArgumentException("Cannot create error container item for operation 'null'.");
        }
        if (exception == null) {
            throw new IllegalArgumentException("Cannot create error container item for exception 'null'.");
        }
        if (iid == null) {
            throw new IllegalArgumentException("Cannot create error container item for iid 'null'.");
        }

        this.serviceName = serviceName;
        this.operationName = operationName;
        this.exception = exception;
        this.iid = iid;

        NabuccoWebSession session = NabuccoWebSessionFactory.getCurrentSession();

        this.message = this.messageResolver.resolve(session.getLocale(), this.serviceName, this.operationName,
                this.exception);
    }

    @Override
    public void init() {
    }

    /**
     * Getter for the serviceName.
     * 
     * @return Returns the serviceName.
     */
    public String getServiceName() {
        return this.serviceName;
    }

    /**
     * Getter for the operationName.
     * 
     * @return Returns the operationName.
     */
    public String getOperationName() {
        return this.operationName;
    }

    /**
     * Getter for the iid.
     * 
     * @return Returns the iid.
     */
    public InvocationIdentifier getIid() {
        return this.iid;
    }

    /**
     * Getter for the exception.
     * 
     * @return Returns the exception.
     */
    public Exception getException() {
        return this.exception;
    }

    @Override
    public JsonElement toJson() {
        JsonMap jsonMap = new JsonMap();

        jsonMap.add(JSON_MESSAGE, this.message.getMessage());
        jsonMap.add(JSON_SEVERITY, this.message.getSeverity());
        jsonMap.add(JSON_ID, this.getIid().getId());

        return jsonMap;
    }

}
