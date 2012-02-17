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

import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.message.tracing.InvocationIdentifier;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.WebModel;

/**
 * ErrorContainerModel
 * 
 * This model contains the actual server side errors
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ErrorLogContainerModel extends WebModel {

    private static final String JSON_ERRORS = "errors";

    /** The list with errors */
    private List<ServerErrorItem> errorList = new ArrayList<ServerErrorItem>();

    private boolean clearAllowed;

    /**
     * Creates a new {@link ErrorLogContainerModel} instance.
     */
    public ErrorLogContainerModel(boolean clearAllowed) {
        this.clearAllowed = clearAllowed;
    }

    /**
     * Adds a new error to the container
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
    public void addError(String serviceName, String operationName, InvocationIdentifier iid, Exception exception) {
        ServerErrorItem newError = new ServerErrorItem(serviceName, operationName, iid, exception);

        this.errorList.add(newError);
    }

    /**
     * Clear all errors from the log
     */
    public void clearErrors() {
        if (this.clearAllowed) {
            this.errorList.clear();
        }
    }

    @Override
    public void init() {

    }

    @Override
    public JsonElement toJson() {
        JsonMap json = new JsonMap();

        JsonList errors = new JsonList();
        for (ServerErrorItem item : this.errorList) {
            errors.add(item.toJson());
        }
        json.add(JSON_ERRORS, errors);

        return json;
    }

}
