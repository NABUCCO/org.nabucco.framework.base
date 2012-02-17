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
package org.nabucco.framework.base.ui.web.model.widget.dialog;

import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.widget.WidgetModel;
import org.nabucco.framework.base.ui.web.model.widget.WidgetType;

/**
 * DialogWidgetModel
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DialogWidgetModel extends WidgetModel {

    private static final String JSON_DIALOG_ID = "dialogId";

    private static final String JSON_TEXT = "text";

    private String dialogId;

    private String text;

    /**
     * Creates a new {@link DialogWidgetModel} instance.
     * 
     * @param dialogId
     *            the dialog id to open
     * @param text
     *            the text to show
     */
    public DialogWidgetModel(String dialogId, String text) {
        super(WidgetType.DIALOG);

        this.setDialogId(dialogId);
        this.setText(text);
    }

    /**
     * Setter for the dialogId.
     * 
     * @param dialogId
     *            The dialogId to set.
     */
    private void setDialogId(String dialogId) {
        this.dialogId = dialogId;
    }

    /**
     * Getter for the dialogId.
     * 
     * @return Returns the dialogId.
     */
    public String getDialogId() {
        return this.dialogId;
    }

    /**
     * Setter for the text.
     * 
     * @param text
     *            The text to set.
     */
    private void setText(String text) {
        this.text = text;
    }

    /**
     * Getter for the text.
     * 
     * @return Returns the text.
     */
    public String getText() {
        return this.text;
    }

    @Override
    public JsonMap toJson() {
        JsonMap retVal = super.toJson();
        retVal.add(JSON_DIALOG_ID, this.getDialogId());
        retVal.add(JSON_TEXT, this.getText());
        return retVal;
    }
}
