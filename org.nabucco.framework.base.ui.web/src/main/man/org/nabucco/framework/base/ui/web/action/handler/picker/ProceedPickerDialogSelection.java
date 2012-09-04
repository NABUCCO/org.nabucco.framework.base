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
package org.nabucco.framework.base.ui.web.action.handler.picker;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.WebActionHandlerSupport;
import org.nabucco.framework.base.ui.web.action.handler.work.RefreshEditorDependenciesHandler;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.CloseItem;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.common.controller.PickerDialogController;
import org.nabucco.framework.base.ui.web.component.dialog.PickerDialog;
import org.nabucco.framework.base.ui.web.component.work.WorkItem;
import org.nabucco.framework.base.ui.web.component.work.editor.EditorItem;
import org.nabucco.framework.base.ui.web.component.work.editor.RelationTab;
import org.nabucco.framework.base.ui.web.component.work.editor.control.PickerControl;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * ProceedPickerDialogSelection
 * 
 * Take the selected values from picker dialog and update model.
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ProceedPickerDialogSelection extends WebActionHandlerSupport {

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ClientException {

        String pickerDialogInstanceId = parameter.get(NabuccoServletPathType.PICKER_DIALOG);
        if (pickerDialogInstanceId == null) {
            throw new ClientException("The instance id of the picker dialog is 'null'");
        }

        String value = parameter.get(NabuccoServletPathType.VALUE);

        PickerDialogController pickerDialogController = NabuccoServletUtil.getPickerDialogController();
        PickerDialog pickerDialog = pickerDialogController.getPickerDialog(pickerDialogInstanceId);

        if (pickerDialog == null) {
            throw new ClientException("There is no picker control registered with instance id '"
                    + pickerDialogInstanceId + "'");
        }
        WebElement sourceElement = pickerDialogController.getPickerDialogSource(pickerDialogInstanceId);
        if (sourceElement == null) {
            throw new ClientException("There is no owner for the picker control with instance id '"
                    + pickerDialogInstanceId + "' registered");
        }

        WebActionResult result = this.addToSource(value, pickerDialog, sourceElement);

        WorkItem selectedWorkItem = NabuccoServletUtil.getSelectedWorkItem();
        if (selectedWorkItem.getType() == WebElementType.EDITOR) {
            selectedWorkItem.getModel().refresh();

            parameter.setParameter(NabuccoServletPathType.EDITOR, selectedWorkItem.getInstanceId());
            WebActionResult refreshItems = super.executeAction(RefreshEditorDependenciesHandler.ID, parameter);
            result.addResult(refreshItems);
        }

        result.addItem(new RefreshItem(WebElementType.BROWSER_AREA));
        result.addItem(new CloseItem(WebElementType.PICKERDIALOG, pickerDialog.getInstanceId()));

        return result;
    }

    /**
     * Add the selection to the source web element.
     * 
     * @param value
     *            the selection value
     * @param pickerDialog
     *            the picker dialog
     * @param sourceElement
     *            the source element
     * 
     * @return the web action result
     * 
     * @throws ClientException
     *             when the element cannot be added to the source
     */
    private WebActionResult addToSource(String value, PickerDialog pickerDialog, WebElement sourceElement)
            throws ClientException {

        WebActionResult result = new WebActionResult();
        if (value == null) {
            return result;
        }

        switch (sourceElement.getType()) {

        case PICKER: {
            PickerControl control = (PickerControl) sourceElement;
            this.addToPicker(value, control, result);
            break;
        }

        case EDITOR_RELATION_TAB: {
            RelationTab relationTab = (RelationTab) sourceElement;
            this.addToEditorRelationTab(value, pickerDialog, relationTab, result);
            break;
        }

        default: {
            throw new ClientException("Not expected type of source for the picker control with instance id '"
                    + pickerDialog.getInstanceId() + "'.");
        }

        }

        return result;
    }

    /**
     * Add the value to the source picker.
     * 
     * @param value
     *            the selection value
     * @param pickerDialog
     *            the picker dialog
     * @param source
     *            the source picker
     * @param result
     *            the web action result
     */
    protected void addToPicker(String value, PickerControl source, WebActionResult result) {
        source.getModel().setValue(value);
        result.addItem(new RefreshItem(WebElementType.CONTROL, source.getId()));
    }

    /**
     * Add the value to the source relation tab.
     * 
     * @param value
     *            the selection value
     * @param pickerDialog
     *            the picker dialog
     * @param source
     *            the source editor relation tab
     * @param result
     *            the web action result
     * 
     * @throws ClientException
     *             when the value cannot be added to the
     */
    protected void addToEditorRelationTab(String value, PickerDialog pickerDialog, RelationTab source,
            WebActionResult result) throws ClientException {

        String propertyName = source.getProperty();
        Datatype newElement = pickerDialog.getTableModel().getDatatypeByObjectId(Long.parseLong(value));
        WorkItem selectedWorkItem = NabuccoServletUtil.getSelectedWorkItem();

        if (selectedWorkItem.getType() != WebElementType.EDITOR) {
            throw new ClientException(
                    "Cannot add new element to the given source item because the type of the source item is not supported.");
        }

        Datatype sourceDatatype = ((EditorItem) selectedWorkItem).getModel().getDatatype();
        super.addProperty(newElement, sourceDatatype, propertyName);

        result.addItem(new RefreshItem(WebElementType.EDITOR_RELATION_AREA, selectedWorkItem.getInstanceId()));
        // result.addItem(new RefreshItem(WebElementType.WORK_AREA));
    }

}
