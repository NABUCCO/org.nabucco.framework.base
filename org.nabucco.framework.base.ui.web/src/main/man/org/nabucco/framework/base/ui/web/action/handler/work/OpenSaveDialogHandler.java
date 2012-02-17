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
package org.nabucco.framework.base.ui.web.action.handler.work;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.WebActionHandlerSupport;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.OpenItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.dialog.Dialog;
import org.nabucco.framework.base.ui.web.component.work.WorkItem;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * OpenSaveDialogHandler
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class OpenSaveDialogHandler extends WebActionHandlerSupport {

    public final static String ID = "System.OpenSaveDialogAction";

    /** Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(OpenSaveDialogHandler.class);

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ClientException {
        WebActionResult result = new WebActionResult();
        String workingItemId = parameter.get(NabuccoServletPathType.WORK_AREA);
        WorkItem item = NabuccoServletUtil.getWorkArea().getItem(workingItemId);

        // Close Tab if not dirty
        if (!item.getModel().isDirty()) {
            result.addResult(super.executeAction(CloseWorkItemHandler.ID, parameter));
            return result;
        }

        // Open close dialog item to ask if the user want to save
        String closeDialogId = NabuccoServletUtil.getWorkArea().getCloseDialogId();

        if (closeDialogId == null || closeDialogId.length() == 0) {
            logger.debug("There is no closeDialog action for the working item with id '", item.getId(), "' defined.");
        }

        NabuccoServletPathType elementType;

        switch (item.getType()) {
        case EDITOR: {
            elementType = NabuccoServletPathType.EDITOR;
            break;
        }
        case DASHBOARD: {
            elementType = NabuccoServletPathType.DASHBOARD;
            break;
        }
        default: {
            throw new ClientException("The type of the working Item cannot be saved by SaveWorkItemHandler");
        }
        }

        Dialog dialog = NabuccoServletUtil.getDialogController().createDialog(closeDialogId);

        if (dialog == null) {
            logger.warning("Cannot create a dialog with id '", item.getId(), "'.");
        } else {
            dialog.getModel().addParameter(NabuccoServletPathType.WORK_AREA, workingItemId);
            dialog.getModel().addParameter(elementType, workingItemId);

            result.addItem(new OpenItem(WebElementType.DIALOG, dialog.getInstanceId()));
        }

        return result;
    }
}
