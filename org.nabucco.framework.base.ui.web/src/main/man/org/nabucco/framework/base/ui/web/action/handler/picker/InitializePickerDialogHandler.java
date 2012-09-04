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

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.WebActionHandlerSupport;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.OpenItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.dialog.PickerDialog;
import org.nabucco.framework.base.ui.web.component.work.bulkeditor.column.BulkEditorPickerColumn;
import org.nabucco.framework.base.ui.web.component.work.editor.control.PickerControl;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * InitializePickerDialogHandler
 * 
 * Sets the selectedValue to the Picker
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class InitializePickerDialogHandler extends WebActionHandlerSupport {

    NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(InitializePickerDialogHandler.class);

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ClientException {
        PickerDialog pickerDialog = this.getPickerDialog(parameter);

        String selectedValue = parameter.getJsonRequest().getValue();

        if (pickerDialog == null) {
            logger.debug("No Picker dialog configured");
            return new WebActionResult();
        }

        pickerDialog.setSelectedValue(selectedValue);
        pickerDialog.getTableModel().init();

        // Process filter
        parameter.setParameter(NabuccoServletPathType.PICKER_DIALOG, pickerDialog.getInstanceId());
        WebActionResult result = new WebActionResult();
        WebActionResult applyingResult = super.executeAction(ApplyPickerFilterHandler.ID, parameter);
        result.addResult(applyingResult);

        result.addItem(new OpenItem(WebElementType.PICKERDIALOG, pickerDialog.getInstanceId()));
        return result;
    }

    /**
     * gets the dialog id to be initialized
     * 
     * @param parameter
     *            parameter
     * @return dialog instance
     * @throws ClientException
     */
    private PickerDialog getPickerDialog(WebActionParameter parameter) throws ClientException {
        WebElement element = parameter.getElement();
        if (element instanceof BulkEditorPickerColumn) {
            BulkEditorPickerColumn pd = (BulkEditorPickerColumn) element;
            return pd.getDialog();
        } else if (element instanceof PickerControl) {
            PickerControl pd = (PickerControl) element;
            return pd.getDialog();
        } else {
            throw new ClientException("element is not a Picker Control");
        }
    }
}
