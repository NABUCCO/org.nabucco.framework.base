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
import org.nabucco.framework.base.ui.web.json.Jsonable;

/**
 * DialogButton
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DialogButton implements Jsonable {

    // TODO: Use Jsonable#JSON_IMAGE instead!
    private static final String JSON_ICON = "icon";

    /** The id of the Button. */
    private String id;

    /** The label of the button. */
    private String label;

    /** The tooltip */
    private String tooltip;

    /** The icon to be shown for the button. */
    private String icon;

    /** The actionid to be fired if the button is clicked. */
    private String actionId;

    /** Validation needed before processing. */
    private boolean validate;

    /**
     * 
     * Creates a new {@link DialogButton} instance.
     * 
     * Buttons that use the configured actionId
     * 
     * 
     * @param id
     *            the id of the button (0,1,2)
     * @param label
     *            the label of the button
     * @param icon
     *            the icon to be used
     * @param actionId
     *            the actionId to be fired on press
     */
    public DialogButton(String id, String label, String tooltip, String icon, String actionId, boolean validate) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot create a new Button for ID [null].");
        }
        if (label == null) {
            throw new IllegalArgumentException("Cannot create a new Button for ID '" + id + "'. The label is 'null'");
        }
        if (actionId == null) {
            throw new IllegalArgumentException("Cannot create a new Button for ID '" + id + "'. The actionId is 'null'");
        }

        this.setTooltip(tooltip);
        this.setId(id);
        this.setLabel(label);
        this.setIcon(icon);
        this.setActionId(actionId);
        this.setValidate(validate);
    }

    /**
     * Setter for the tooltip
     * 
     * @param tooltip
     *            the tooltip to be set
     */
    private void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * Getter for the tooltip
     * 
     * @return tooltip
     */
    public String getTooltip() {
        return this.tooltip;
    }

    /**
     * Getter for the actionId.
     * 
     * @return Returns the actionId.
     */
    public String getActionId() {
        return this.actionId;
    }

    /**
     * Getter for the icon.
     * 
     * @return Returns the icon.
     */
    public String getIcon() {
        return this.icon;
    }

    /**
     * Getter for the id.
     * 
     * @return Returns the id.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Getter for the label.
     * 
     * @return Returns the label.
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * Getter for the validate.
     * 
     * @return Returns the validate.
     */
    public boolean validationNeeded() {
        return this.validate;
    }

    /**
     * Setter for the actionId.
     * 
     * @param actionId
     *            The actionId to set.
     */
    private void setActionId(String actionId) {
        this.actionId = actionId;
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
     * Setter for the id.
     * 
     * @param id
     *            The id to set.
     */
    private void setId(String id) {
        this.id = id;
    }

    /**
     * Setter for the label.
     * 
     * @param label
     *            The label to set.
     */
    private void setLabel(String label) {
        this.label = label;
    }

    /**
     * Setter for the validate.
     * 
     * @param validate
     *            The validate to set.
     */
    private void setValidate(boolean validate) {
        this.validate = validate;
    }

    @Override
    public JsonElement toJson() {
        JsonMap retVal = new JsonMap();
        retVal.add(JSON_ID, this.getId());
        retVal.add(JSON_LABEL, this.getLabel());
        retVal.add(JSON_ICON, this.getIcon());
        retVal.add(JSON_TOOLTIP, this.getTooltip());
        return retVal;
    }
}
