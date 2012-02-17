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
package org.nabucco.framework.base.ui.web.model.dialog;

import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonMap;

/**
 * MessageDialogModel
 * 
 * The standard dialog with the easy message
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class MessageDialogModel extends DialogModel {

    private static final String JSON_MESSAGE = "message";

    /** The message to be shown by the dialog */
    private String message;

    /**
     * Creates a new {@link MessageDialogModel} instance.
     * 
     * Standard DialogModel
     * 
     * @param message
     *            the message to be displayed
     */
    public MessageDialogModel(String message) {
        super();

        if (message == null) {
            throw new IllegalArgumentException("Cannot create Message dialog. The message is 'null'");
        }
        this.setMessage(message);
    }

    @Override
    public void init() {

    }

    /**
     * Setter for the message.
     * 
     * @param message
     *            The message to set.
     */
    private void setMessage(String message) {
        this.message = message;
    }

    /**
     * Getter for the message.
     * 
     * @return Returns the message.
     */
    public String getMessage() {
        return this.message;
    }

    @Override
    public JsonElement toJson() {
        JsonMap json = (JsonMap) super.toJson();
        json.add(JSON_MESSAGE, this.getMessage());
        return json;
    }
}
