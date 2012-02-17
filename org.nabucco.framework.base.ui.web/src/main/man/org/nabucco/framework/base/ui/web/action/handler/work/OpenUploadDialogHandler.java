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
import org.nabucco.framework.base.ui.web.component.work.editor.control.ImageControl;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * OpenSaveDialogHandler
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class OpenUploadDialogHandler extends WebActionHandlerSupport {

    public final static String ID = "System.OpenUploadDialogAction";

    /** Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(OpenUploadDialogHandler.class);

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ClientException {
        WebActionResult result = new WebActionResult();
        ImageControl control = (ImageControl) parameter.getElement();
        String workingTab = parameter.get(NabuccoServletPathType.TAB);
        String editorId = parameter.get(NabuccoServletPathType.EDITOR);

        if (editorId == null) {
            throw new ClientException("Cannot open upload dialog because the editorId is 'null'");
        }

        if (control == null) {
            throw new ClientException("Cannot open upload dialog because the control is 'null'");
        }

        String uploaderDialogId = control.getUploaderDialogId();

        if (uploaderDialogId == null || uploaderDialogId.length() == 0) {
            throw new ClientException("Upload dialog is not configured. Cannot open it.");
        }

        Dialog dialog = NabuccoServletUtil.getDialogController().createDialog(uploaderDialogId);
        if (dialog == null) {
            logger.warning("Cannot create a dialog with id '", uploaderDialogId, "'.");
        } else {
            dialog.getModel().addParameter(NabuccoServletPathType.TAB, workingTab);
            dialog.getModel().addParameter(NabuccoServletPathType.EDITOR, editorId);
            dialog.getModel().addParameter(NabuccoServletPathType.CONTROL, control.getId());

            result.addItem(new OpenItem(WebElementType.DIALOG, dialog.getInstanceId()));
        }

        return result;
    }
}
