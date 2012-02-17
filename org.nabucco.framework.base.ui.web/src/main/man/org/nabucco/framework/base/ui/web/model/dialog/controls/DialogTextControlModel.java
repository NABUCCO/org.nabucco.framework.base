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
public class DialogTextControlModel extends DialogControlModel {

    private boolean mandatory;

    private boolean editable;

    private String value;

    /**
     * 
     * Creates a new {@link DialogTextControlModel} instance.
     * 
     * @param id
     *            The id of the control
     * @param label
     *            the label of the control
     * @param tooltip
     *            the tooltip of the control
     * @param type
     *            the type of the control
     * @param defaultValue
     *            default value of the control
     * @param position
     *            the position of the control
     * @param mandatory
     *            is the field mandatory
     * @param editable
     *            is the control editable
     */
    public DialogTextControlModel(String id, String label, String tooltip, DialogControlType type, String position,
            String defaultValue, boolean mandatory, boolean editable) {
        super(id, label, tooltip, type, position, mandatory);
        this.editable = editable;
        this.value = defaultValue;
    }

    @Override
    public void init() {

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

        if (this.mandatory) {
            if (this.value != null && this.value.length() > 0) {
                return true;
            }
            return false;
        }

        if (this.value != null) {
            return true;
        }

        return false;
    }

    /**
     * Setter for the control value
     * 
     * @param value
     */
    @Override
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Getter for the control value
     * 
     * @return returns the value of the dialog control
     */
    @Override
    public String getValue() {
        return this.value;
    }

    /**
     * Indicates if the control is in editable state
     * 
     * @return true if editable
     */
    public boolean isEditable(){
        return this.editable;
    }

    @Override
    public JsonMap toJson() {
        JsonMap retVal = super.toJson();
        retVal.add(JSON_EDITABLE, this.isEditable());
        retVal.add(JSON_VALUE, this.value);

        return retVal;
    }
}
