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
import org.nabucco.framework.base.ui.web.action.WebActionHandlerSupport;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.dialog.PickerDialog;
import org.nabucco.framework.base.ui.web.component.work.list.FilterItem;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * SelectPickerFilter
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class SelectPickerFilter extends WebActionHandlerSupport {

    private final static String DEFAULT_APPLY_FILTER_ACTION = ApplyPickerFilterHandler.ID;

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ClientException {
        String pickerDialogInstanceId = parameter.get(NabuccoServletPathType.PICKER_DIALOG);
        String filterId = parameter.get(NabuccoServletPathType.FILTERID);

        PickerDialog pickerDialog = NabuccoServletUtil.getPickerDialogController().getPickerDialog(
                pickerDialogInstanceId);

        if (pickerDialog == null) {
            throw new ClientException("Cannot allocate the Picker dialog item with id " + pickerDialogInstanceId);
        }
        pickerDialog.setActiveFilterId(filterId);
        FilterItem filterItem = pickerDialog.getFilter(filterId);

        if (filterItem == null) {
            throw new ClientException("Cannot load values of the selected filter item. Filter item is not found.");
        }
        String filterLoadAction = filterItem.getCustomLoadAction();

        if (filterLoadAction == null || filterLoadAction.isEmpty()) {
            filterLoadAction = DEFAULT_APPLY_FILTER_ACTION;
        }

        // Process filter
        WebActionResult result = new WebActionResult();
        WebActionResult executeActionResult = super.executeAction(filterLoadAction, parameter);
        result.addResult(executeActionResult);

        result.addItem(new RefreshItem(WebElementType.PICKERDIALOG, pickerDialogInstanceId));

        return result;
    }
}
