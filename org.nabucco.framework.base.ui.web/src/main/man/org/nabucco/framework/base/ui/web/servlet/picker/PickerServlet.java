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
package org.nabucco.framework.base.ui.web.servlet.picker;

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.component.dialog.PickerDialog;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServlet;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletRequest;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletResponse;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.handler.ActionHandler;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPath;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * PickerServlet
 * 
 * Servlet that process communication picker picker dialogs
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class PickerServlet extends NabuccoServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void restGet(NabuccoServletRequest request, NabuccoServletResponse response) throws ClientException {
        NabuccoServletPath path = request.getServletPath();
        String pickerDialogId = path.getId(NabuccoServletPathType.PICKER_DIALOG);

        PickerDialog pickerDialog = NabuccoServletUtil.getPickerDialogController().getPickerDialog(pickerDialogId);

        // If dialog not initialized => create an instance of it
        if (pickerDialog == null) {
            throw new ClientException("PickerDialog with id '"
                    + pickerDialogId + "'is not instanciated! Cannot process request.");
        }

        response.sendResponseParameter(pickerDialog.toJson());
    }

    @Override
    protected void restPost(NabuccoServletRequest request, NabuccoServletResponse response) throws ClientException {

        NabuccoServletPath path = request.getServletPath();
        String instancePickerDialogId = path.getId(NabuccoServletPathType.PICKER_DIALOG);

        PickerDialog pickerDialog = NabuccoServletUtil.getPickerDialogController().getPickerDialog(instancePickerDialogId);

        // If dialog not initialized => create an instance of it
        if (pickerDialog == null) {
            throw new ClientException("PickerDialog with id '"
                    + instancePickerDialogId + "'is not instanciated! Cannot process request.");
        }

        ActionHandler actionHandler = new ActionHandler(request, response);
        actionHandler.execute(pickerDialog);
    }
}
