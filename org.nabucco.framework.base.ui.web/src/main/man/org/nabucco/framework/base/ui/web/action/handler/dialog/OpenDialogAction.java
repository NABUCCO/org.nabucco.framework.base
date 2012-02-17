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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.client.action.ActionException;
import org.nabucco.framework.base.ui.web.action.WebActionHandlerSupport;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.OpenItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.dialog.Dialog;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * OpenDialogAction
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class OpenDialogAction extends WebActionHandlerSupport {

    /** Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(OpenDialogAction.class);

    @Override
    public final WebActionResult execute(WebActionParameter parameter) throws ClientException {
        WebActionResult result = new WebActionResult();

        String dialogId;

        try {
            dialogId = this.getDialogId(parameter);

            if (dialogId == null) {
                logger.error("No dialog ID defined!");
                throw new ActionException("Error opening dialog with id 'null'.");
            }

        } catch (ClientException ce) {
            logger.error(ce, "Error opening dialog with id 'null'.");
            throw ce;
        } catch (Exception e) {
            logger.error(e, "Error opening dialog with id 'null'.");
            throw new ClientException("Error opening dialog with id 'null'.", e);
        }

        Dialog dialog = NabuccoServletUtil.getDialogController().createDialog(dialogId);

        if (dialog == null) {
            logger.warning("Cannot create dialog with id '", dialogId, "'.");
        } else {

            Map<NabuccoServletPathType, String> parameters = this.getParameters();
            if (parameters == null) {
                logger.warning("Invalid Dialog Parameter Map for Dialog '" + dialogId + "' Returned!");
            } else {
                for (Entry<NabuccoServletPathType, String> entry : parameters.entrySet()) {
                    dialog.getModel().addParameter(entry.getKey(), entry.getValue());
                }
            }

            result.addItem(new OpenItem(WebElementType.DIALOG, dialog.getInstanceId()));
        }

        return result;
    }

    /**
     * Getter for the parameters that will be added to the dialog model.
     * 
     * @return the map of parameters
     */
    protected Map<NabuccoServletPathType, String> getParameters() {
        return new HashMap<NabuccoServletPathType, String>();
    }

    /**
     * Getter for the dialog id to open.
     * 
     * @param parameter
     *            the web action parameter
     * 
     * @return the dialog id to open
     * 
     * @throws ClientException
     *             when the dialog id cannot be retrieved
     */
    protected abstract String getDialogId(WebActionParameter parameter) throws ClientException;

}
