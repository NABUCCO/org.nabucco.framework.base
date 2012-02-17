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

import java.io.PrintWriter;
import java.io.StringWriter;

import org.nabucco.framework.base.ui.web.component.WebApplication;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.WebModel;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;

/**
 * Model for error handling.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ErrorModel extends WebModel {

    private String id;

    private String message;

    private String stackTrace;

    private static final String JSON_MESSAGE = "message";

    private static final String JSON_STACK = "stack";

    private static final String DEFAULT_ERROR_MESSAGE = "A technical error occured, please contact your administrator";

    /**
     * Creates a new {@link ErrorModel} instance.
     * 
     * @param id
     *            the error id
     * @param message
     *            the error message
     */
    public ErrorModel(String id, String message, String stackTrace) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot create error model for id [null].");
        }

        this.id = id;
        this.message = message;
        this.stackTrace = stackTrace;
    }

    /**
     * Creates a new {@link ErrorModel} instance.
     * 
     * @param exception
     *            the causing exception
     */
    public ErrorModel(Exception exception) {
        if (exception == null) {
            throw new IllegalArgumentException("Cannot create error model for exception [null].");
        }

        this.id = exception.getClass().getCanonicalName();
        this.message = exception.getLocalizedMessage();

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        exception.printStackTrace(writer);

        this.stackTrace = stringWriter.getBuffer().toString();
    }

    @Override
    public void init() {
    }

    @Override
    public JsonElement toJson() {
        JsonMap json = new JsonMap();
        json.add(JSON_ID, this.id);

        WebApplication application = NabuccoServletUtil.getApplication();
        if (application != null && application.isDebugEnabled()) {
            json.add(JSON_MESSAGE, this.message);
            json.add(JSON_STACK, this.stackTrace);
        } else {
            json.add(JSON_MESSAGE, DEFAULT_ERROR_MESSAGE);
        }

        return json;
    }
}
