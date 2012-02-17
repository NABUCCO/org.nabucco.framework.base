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

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.WebAction;
import org.nabucco.framework.base.ui.web.action.WebActionHandler;
import org.nabucco.framework.base.ui.web.action.WebActionRegistry;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.OpenItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.dialog.PickerDialog;
import org.nabucco.framework.base.ui.web.component.work.editor.control.PickerControl;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * InitializePickerDialogHandler
 * 
 * Sets the selectedValue to the Picker
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class InitializePickerDialogHandler extends WebActionHandler {

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ClientException {
        if (!PickerControl.class.isAssignableFrom(parameter.getElement().getClass())) {
            throw new ClientException("element is not a Picker Control");
        }

        String selectedValue = parameter.getJsonRequest().getValue();

        PickerControl element = (PickerControl) parameter.getElement();
        PickerDialog pickerDialog = element.getDialog();
        
        if (pickerDialog == null) {
            throw new IllegalStateException("The pickerDialog is not even initialized. Cannot initilize PickerDialog.");
        }

        pickerDialog.setSelectedValue(selectedValue);
        pickerDialog.getTableModel().init();

        // Process filter
        parameter.setParameter(NabuccoServletPathType.PICKER_DIALOG, pickerDialog.getInstanceId());
        WebAction action = WebActionRegistry.getInstance().newAction(ApplyPickerFilterHandler.ID);

        if (action == null) {
            throw new ClientException("Cannot instanciate filter action.");
        }
        action.execute(parameter);

        WebActionResult result = new WebActionResult();

        result.addItem(new OpenItem(WebElementType.PICKERDIALOG, pickerDialog.getInstanceId()));
        return result;
    }

}
