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
package org.nabucco.framework.base.ui.web.action.handler.dialog;

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.WebActionHandlerSupport;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.CloseItem;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.dialog.Dialog;
import org.nabucco.framework.base.ui.web.model.dialog.DialogButton;
import org.nabucco.framework.base.ui.web.model.dialog.GridDialogModel;
import org.nabucco.framework.base.ui.web.model.dialog.controls.DialogControlModel;
import org.nabucco.framework.base.ui.web.model.dialog.controls.DialogGridModelElement;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * ExecuteDialogActionHandler
 * 
 * This action executes the action that is defined as the result of the dialog event(button click)
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ExecuteDialogActionHandler extends WebActionHandlerSupport {

    private static final String EMPTY_STRING = "";

    public static final String ID = "System.ExecuteDialogAction";

    /*
     * URL: /dialog/SaveDialog/editor/SchedulingEditor_24/action/System.ExecuteDialogAction/signal/0
     */

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ClientException {
        WebActionResult executeActionResult = new WebActionResult();

        String dialogInstanceId = parameter.get(NabuccoServletPathType.DIALOG);
        Dialog dialog = NabuccoServletUtil.getDialog(dialogInstanceId);

        // If dialog not initialized => create an instance of it
        if (dialog == null) {
            throw new ClientException("Cannot find the instance of the dialog with UNIQUE instanceid '"
                    + dialogInstanceId + "'. Maybe you have forgot to instanciate dialog before opening it.");

        }

        // fill dialog with values if needed
        if (dialog.getModel() instanceof GridDialogModel) {
            GridDialogModel model = (GridDialogModel) dialog.getModel();

            this.processGridDialog(parameter, model);
        }

        String signal = parameter.getJsonRequest().getValue(NabuccoServletPathType.SIGNAL.getValue());

        if (signal == null) {
            throw new ClientException("There is no signal for the dialog with id='"
                    + dialogInstanceId + "' defined. Cannot process the action");
        }

        DialogButton button = dialog.getModel().getButtonForId(signal);

        if (button != null) {
            if (button.validationNeeded()) {
                boolean isValid = dialog.getModel().isValid();

                if (!isValid) {
                    executeActionResult.addItem(new RefreshItem(WebElementType.DIALOG, dialog.getInstanceId()));
                    return executeActionResult;
                }
            }

            String actionId = button.getActionId();

            if (actionId != null && actionId.length() > 0) {
                WebActionResult subResult = super.executeAction(actionId, parameter);
                executeActionResult.addResult(subResult);
            }

        }

        executeActionResult.addItem(new CloseItem(WebElementType.DIALOG, dialog.getInstanceId()));
        return executeActionResult;
    }

    /**
     * Fills grid controls with transfered values
     * 
     * @param parameter
     *            the web parameter
     * @param model
     *            the model to be filled
     */
    private void processGridDialog(WebActionParameter parameter, GridDialogModel model) throws ClientException {
        for (String controlId : model.getControlIds()) {

            String value = parameter.getJsonRequest().getValue(controlId);
            DialogGridModelElement controlModelElement = model.getControl(controlId);

            if (controlModelElement == null) {
                throw new IllegalStateException("Cannot find control element with id in dialog model" + controlId);
            }

            if (controlModelElement instanceof DialogControlModel) {
                DialogControlModel controlModel = (DialogControlModel) controlModelElement;

                if (value == null) {
                    value = EMPTY_STRING;
                }

                controlModel.setValue(value);
            }
        }
    }
}
