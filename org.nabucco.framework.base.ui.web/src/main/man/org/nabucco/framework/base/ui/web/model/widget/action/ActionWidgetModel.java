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
package org.nabucco.framework.base.ui.web.model.widget.action;

import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.widget.WidgetModel;
import org.nabucco.framework.base.ui.web.model.widget.WidgetType;

/**
 * TextWidgetModel
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ActionWidgetModel extends WidgetModel {

    private static final String JSON_TEXT = "text";

    private String action;

    private String text;

    /**
     * Creates a new {@link ActionWidgetModel} instance.
     * 
     * @param text
     *            text to show
     * @param link
     *            link for the text
     */
    public ActionWidgetModel(String action, String text) {
        super(WidgetType.ACTION);

        this.setAction(action);
        this.setText(text);
    }

    /**
     * Setter for the action.
     * 
     * @param action
     *            The action to set.
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Getter for the action.
     * 
     * @return Returns the action.
     */
    public String getAction() {
        return this.action;
    }

    /**
     * Setter for the text.
     * 
     * @param text
     *            The text to set.
     */
    public void setText(String text) {
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
        retVal.add(JSON_ACTION, this.getAction());
        retVal.add(JSON_TEXT, this.getText());
        return retVal;
    }
}
