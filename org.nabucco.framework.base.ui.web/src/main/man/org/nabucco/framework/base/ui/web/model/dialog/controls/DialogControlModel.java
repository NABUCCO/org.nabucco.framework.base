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
package org.nabucco.framework.base.ui.web.model.dialog.controls;

import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.dialog.DialogControlType;

/**
 * DialogControlModel
 * 
 * The model of the dialog control.
 * 
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public abstract class DialogControlModel extends DialogGridModelElement {

    private static final String JSON_MANDATORY = "mandatory";

    private boolean mandatory;

    private boolean editable;

    private String value;

    private boolean initialized;

    /**
     * 
     * Creates a new {@link DialogControlModel} instance.
     * 
     * @param id
     *            The id of the control
     * @param label
     *            the label of the control
     * @param tooltip
     *            the tooltip of the control
     * @param type
     *            the type of the control
     * @param position
     *            the position of the control
     * @param mandatory
     *            is the field mandatory
     */
    public DialogControlModel(String id, String label, String tooltip, DialogControlType type, String position,
            boolean mandatory, boolean editable) {
        super(id, label, tooltip, type, position);

        this.mandatory = mandatory;
        this.editable = editable;

        initialized = false;
    }

    @Override
    public void init() {

    }

    /**
     * Getter for the initialized
     * 
     * @return Is initialized.
     */
    @Override
    public boolean isInitialized() {
        return initialized;
    }

    /**
     * Checks if the control has a valid state
     * 
     * @return true if valid or false if not
     */
    @Override
    public boolean isValid() {
        if (!this.isInitialized()) {
            return true;
        }

        if (mandatory) {
            if (value != null && value.length() > 0) {
                return true;
            }
            return false;
        }

        if (value != null) {
            return true;
        }

        return false;
    }

    /**
     * Setter for the control value
     * 
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Getter for the control value
     * 
     * @return returns the value of the dialog control
     */
    public String getValue() {
        return value;
    }

    /**
     * Getter for the editable.
     * 
     * @return Returns the editable.
     */
    public boolean isEditable() {
        return editable;
    }

    @Override
    public JsonMap toJson() {
        JsonMap retVal = super.toJson();
        retVal.add(JSON_MANDATORY, mandatory);
        retVal.add(JSON_VALUE, value);
        retVal.add(JSON_VALID, this.isValid());
        retVal.add(JSON_EDITABLE, this.isEditable());

        initialized = true;
        return retVal;
    }

}
