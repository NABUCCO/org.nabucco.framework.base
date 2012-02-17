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

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.WebActionHandlerSupport;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.dialog.Dialog;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * DialogClosedActionHandler
 * 
 * This action removes the dialog instance from the application scope
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DialogClosedActionHandler extends WebActionHandlerSupport {

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance()
            .getLogger(DialogClosedActionHandler.class);

    public static final String ID = "System.DialogClosedAction";

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ClientException {
        String dialogInstanceId = parameter.get(NabuccoServletPathType.DIALOG);

        WebActionResult executeActionResult = new WebActionResult();

        Dialog dialog = NabuccoServletUtil.getDialog(dialogInstanceId);

        if (dialog == null) {
            logger.debug("Cannot find the instance of the dialog with UNIQUE instanceid '"
                    + dialogInstanceId + "'. Maybe you have forgot to instanciate dialog before opening it.");
            return executeActionResult;

        }

        dialog.closeDialog();

        return executeActionResult;
    }
}
