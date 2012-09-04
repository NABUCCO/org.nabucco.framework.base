/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco-source.org/nabucco-license.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.ui.web.model.dialog;

import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.nabucco.framework.base.ui.web.component.dialog.Dialog;
import org.nabucco.framework.base.ui.web.model.dialog.controls.DialogControlModel;
import org.nabucco.framework.base.ui.web.model.dialog.controls.DialogDateControlModel;
import org.nabucco.framework.base.ui.web.model.dialog.controls.DialogDropDownControlModel;
import org.nabucco.framework.base.ui.web.model.dialog.controls.DialogGridModelElement;
import org.nabucco.framework.base.ui.web.model.editor.control.property.dropdown.selection.SelectionItem;

/**
 * DialogAccessor
 * 
 * Dialog Accessor is used to quick access dialog control values
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DialogAccessor {

    private Dialog dialog;

    /**
     * 
     * Creates a new {@link DialogAccessor} instance.
     * 
     * @param dialog
     */
    public DialogAccessor(Dialog dialog) {
        this.dialog = dialog;
    }

    /**
     * Returns the list of control ids
     * 
     * @return list of control ids
     */
    public List<String> getControlIds() {
        DialogModel model = this.dialog.getModel();
        if (model instanceof GridDialogModel == false) {
            return Collections.emptyList();
        }

        GridDialogModel gridModel = (GridDialogModel) model;

        List<String> controlIds = gridModel.getControlIds();
        return controlIds;
    }

    /**
     * Returns the type of given control
     * 
     * @param controlId
     *            the control to return the type for
     * @return the type or null if control not found
     */
    public DialogControlType getControlType(String controlId) {
        if (controlId == null) {
            return null;
        }
        DialogModel model = this.dialog.getModel();
        if (model instanceof GridDialogModel == false) {
            return null;
        }

        GridDialogModel gridModel = (GridDialogModel) model;
        DialogGridModelElement control = gridModel.getControl(controlId);

        if (control == null) {
            return null;
        }

        DialogControlType type = control.getType();
        return type;
    }

    /**
     * returns the text value given in the control
     * 
     * @param controlId
     *            the control id to return value for
     * @return value or null if not found
     */
    public String getControlTextValue(String controlId) {
        if (controlId == null) {
            return null;
        }

        DialogModel model = this.dialog.getModel();
        if (model instanceof GridDialogModel == false) {
            return null;
        }

        GridDialogModel gridModel = (GridDialogModel) model;
        DialogGridModelElement control = gridModel.getControl(controlId);

        if (control == null) {
            return null;
        }

        if (control instanceof DialogControlModel == false) {
            return null;
        }

        DialogControlModel controlModel = (DialogControlModel) control;

        return controlModel.getValue();
    }

    /**
     * Returns the date value given in the control
     * 
     * @param controlId
     *            the id of the control
     * @return the parsed Date value according to the standard format or null of not found or cannot
     *         be parsed
     */
    public Date getControlDateValue(String controlId) {
        if (controlId == null) {
            return null;
        }

        DialogModel model = this.dialog.getModel();
        if (model instanceof GridDialogModel == false) {
            return null;
        }

        GridDialogModel gridModel = (GridDialogModel) model;
        DialogGridModelElement control = gridModel.getControl(controlId);

        if (control == null) {
            return null;
        }

        if (control instanceof DialogDateControlModel == false) {
            return null;
        }

        DialogDateControlModel controlModel = (DialogDateControlModel) control;
        Date retVal;
        try {
            retVal = controlModel.getValueAsDate();
        } catch (ParseException e) {
            retVal = null;
        }

        return retVal;
    }

    /**
     * Returns the given value as a selection item by DropDown Controls
     * 
     * @param controlId
     *            the id of the control
     * @return the selection item or null if cannot retrieve or nothinf given
     */
    public SelectionItem<?> getValueAsSelectionItem(String controlId) {
        if (controlId == null) {
            return null;
        }

        DialogModel model = this.dialog.getModel();
        if (model instanceof GridDialogModel == false) {
            return null;
        }

        GridDialogModel gridModel = (GridDialogModel) model;
        DialogGridModelElement control = gridModel.getControl(controlId);

        if (control == null) {
            return null;
        }

        if (control instanceof DialogDropDownControlModel == false) {
            return null;
        }

        DialogDropDownControlModel controlModel = (DialogDropDownControlModel) control;
        SelectionItem<?> retVal = controlModel.getValueAsSelectionItem();

        return retVal;
    }
}
