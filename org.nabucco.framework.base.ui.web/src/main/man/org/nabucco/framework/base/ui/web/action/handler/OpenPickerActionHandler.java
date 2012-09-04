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
package org.nabucco.framework.base.ui.web.action.handler;

import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.client.action.ActionException;
import org.nabucco.framework.base.ui.web.action.WebActionHandlerSupport;
import org.nabucco.framework.base.ui.web.action.handler.picker.ApplyPickerFilterHandler;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.OpenItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.dialog.PickerDialog;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * Action Handler Support for selection of datatypes by aggregation
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public abstract class OpenPickerActionHandler<D extends NabuccoDatatype> extends WebActionHandlerSupport {

    @Override
    public final WebActionResult execute(WebActionParameter parameter) throws ClientException {

        WebElement sourceElement = parameter.getElement();

        String pickerInstanceId = NabuccoServletUtil.getPickerDialogController().createPickerDialog(this.getPickerId(),
                sourceElement);
        PickerDialog pickerDialog = NabuccoServletUtil.getPickerDialogController().getPickerDialog(pickerInstanceId);

        if (pickerDialog == null) {
            throw new IllegalStateException("The pickerDialog is 'null'. Cannot open selection.");
        }

        // Process filter
        this.fillPickerData(parameter, pickerDialog);

        WebActionResult result = new WebActionResult();

        result.addItem(new OpenItem(WebElementType.PICKERDIALOG, pickerDialog.getInstanceId()));
        return result;
    }

    /**
     * Fills the picker dialog with values. Per default use the filter
     * 
     * @param parameter
     *            web parameter element
     * @param pickerDialog
     *            the dialog to be filled
     * @throws ClientException
     *             if problems by filling
     * @throws ActionException
     *             if action not found
     */
    protected void fillPickerData(WebActionParameter parameter, PickerDialog pickerDialog) throws ClientException,
            ActionException {
        parameter.setParameter(NabuccoServletPathType.PICKER_DIALOG, pickerDialog.getInstanceId());

        super.executeAction(ApplyPickerFilterHandler.ID, parameter);
    }

    /**
     * Return the id of the picker to be opened
     * 
     * @return
     */
    protected abstract String getPickerId();

}
