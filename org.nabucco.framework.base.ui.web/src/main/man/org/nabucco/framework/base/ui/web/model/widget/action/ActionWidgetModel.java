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

    private static final String JSON_ICON = "icon";

    private String action;

    private String text;

    private String tooltip;

    private String icon;

    /**
     * 
     * Creates a new {@link ActionWidgetModel} instance.
     * 
     * @param action
     *            action to call
     * @param text
     *            the text to be shown
     * @param tooltip
     *            the tooltip to show
     * @param icon
     *            the icon (if any)
     */
    public ActionWidgetModel(String action, String text, String tooltip, String icon) {
        super(WidgetType.ACTION);

        if (text == null) {
            throw new IllegalArgumentException("Cannot create a Action Widget without text ('null')");
        }

        this.setAction(action);
        this.setText(text);
        this.setTooltip(tooltip);
        this.setIcon(icon);
    }

    /**
     * Setter for the action.
     * 
     * @param action
     *            The action to set.
     */
    private void setAction(String action) {
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

    /**
     * Setter for the tooltip.
     * 
     * @param tooltip
     *            The tooltip to set.
     */
    private void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * Getter for the tooltip.
     * 
     * @return Returns the tooltip.
     */
    public String getTooltip() {
        return tooltip;
    }

    /**
     * Setter for the icon.
     * 
     * @param icon
     *            The icon to set.
     */
    private void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * Getter for the icon.
     * 
     * @return Returns the icon.
     */
    public String getIcon() {
        return icon;
    }

    @Override
    public JsonMap toJson() {
        JsonMap retVal = super.toJson();
        retVal.add(JSON_ACTION, this.getAction());
        retVal.add(JSON_TEXT, this.getText());
        retVal.add(JSON_ICON, this.getIcon());
        retVal.add(JSON_TOOLTIP, this.getTooltip());

        return retVal;
    }

}
