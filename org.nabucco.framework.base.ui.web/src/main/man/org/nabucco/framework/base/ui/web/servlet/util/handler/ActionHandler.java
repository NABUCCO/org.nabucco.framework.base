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
package org.nabucco.framework.base.ui.web.servlet.util.handler;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.WebAction;
import org.nabucco.framework.base.ui.web.action.WebActionRegistry;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletRequest;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletResponse;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathEntry;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * Process the servlet path starting from the given servlet element until an action occurs, which is
 * subsequently executed. If no action is found nothin is executed.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ActionHandler extends NabuccoServletHandler {

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(ActionHandler.class);

    /**
     * Creates a new {@link NabuccoActionHandler} instance.
     * 
     * @param request
     *            the servlet request
     * @param response
     *            the servlet response
     */
    public ActionHandler(NabuccoServletRequest request, NabuccoServletResponse response) {
        super(request, response);
    }

    @Override
    protected void process(WebElement element, NabuccoServletPathEntry key) throws ClientException {
        if (key == null || key.getType() != NabuccoServletPathType.ACTION) {
            return;
        }

        NabuccoServletPathEntry actionId = key.getNext();
        if (actionId != null) {
            this.executeAction(element, actionId.getValue());
        }
    }

    /**
     * Execute the action with the configured action id.
     * 
     * @param element
     *            the current web element
     * @param action
     *            id of the action to execute
     * 
     * @throws ClientException
     *             when the action execution fails
     */
    private void executeAction(WebElement element, String actionId) throws ClientException {

        if (actionId == null || actionId.isEmpty()) {
            logger.warning("Cannot execute action with id 'null'.");
            return;
        }

        try {
            WebAction action = WebActionRegistry.getInstance().newAction(actionId);

            if (action == null) {
                logger.warning("No action configured for ID '", actionId, "'.");
                return;
            }

            WebActionParameter parameter = new WebActionParameter(super.getRequest(), element);
            WebActionResult result = action.execute(parameter);

            super.getResponse().sendResponseParameter(result.toJson());

        } catch (ClientException ce) {
            logger.error(ce, "Error executing NABUCCO Action '", actionId, "'.");
            throw ce;
        } catch (Exception e) {
            logger.error(e, "Error executing NABUCCO Action '", actionId, "'.");
            throw new ClientException("Error executing NABUCCO Action '" + actionId + "'.", e);
        }
    }
}
