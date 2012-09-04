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

import java.util.List;

import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.dialog.DialogControlType;
import org.nabucco.framework.base.ui.web.model.editor.control.property.dropdown.selection.SelectionItem;

/**
 * DialogControlModel
 * 
 * The model of the dialog control.
 * 
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DialogDropDownControlModel extends DialogControlModel {

    private static final String JSON_ITEMS = "items";

    private List<SelectionItem<?>> selectionList;

    /**
     * 
     * Creates a new {@link DialogDropDownControlModel} instance.
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
     * @param selectionList
     *            the list with selection
     */
    public DialogDropDownControlModel(String id, String label, String tooltip, String position, String defaultValue,
            boolean mandatory, boolean editable, List<SelectionItem<?>> selectionList) {
        super(id, label, tooltip, DialogControlType.DROPDOWN, position, mandatory, editable);

        super.setValue(defaultValue);
        this.selectionList = selectionList;
    }

    @Override
    public void init() {

    }

    /**
     * Returns the list with selection possibilities
     * 
     * @return
     */
    public List<SelectionItem<?>> getSelectionList() {
        return selectionList;
    }

    /**
     * Returns the selected value as a selection item
     * 
     * @return item or null if cannot be parsed
     */
    public SelectionItem<?> getValueAsSelectionItem() {
        SelectionItem<?> selectedValue = null;
        String currentValueId = this.getValue();

        for (SelectionItem<?> val : selectionList) {
            if (currentValueId != null && this.getValue().length() > 0) {
                if (val.getLiteralName().equals(currentValueId)) {
                    selectedValue = val;
                    break;
                }
            }
        }

        return selectedValue;
    }

    @Override
    public boolean isValid() {
        boolean valid = super.isValid();
        if (!this.isInitialized()) {
            return true;
        }
        if (valid) {
            String value = this.getValue();
            if (value.length() == 0) {
                return true;
            }
            for (SelectionItem<?> selection : selectionList) {
                if (selection.getLiteralName().equals(value)) {
                    return true;
                }
            }

            return false;
        }
        return valid;
    }

    @Override
    public JsonMap toJson() {
        JsonMap retVal = super.toJson();
        JsonList jsonList = new JsonList();

        String currentValueId = this.getValue();
        SelectionItem<?> selectedValue = null;

        for (SelectionItem<?> val : selectionList) {
            jsonList.add(val.toJson());
            if (currentValueId != null && this.getValue().length() > 0) {
                if (val.getLiteralName().equals(currentValueId)) {
                    selectedValue = val;
                }
            }
        }

        if (selectedValue != null) {
            retVal.add(JSON_VALUE, selectedValue.toJson());
        }

        retVal.add(JSON_ITEMS, jsonList);

        return retVal;
    }
}
