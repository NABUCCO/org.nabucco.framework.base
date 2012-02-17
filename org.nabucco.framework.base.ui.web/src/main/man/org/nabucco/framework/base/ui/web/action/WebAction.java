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
package org.nabucco.framework.base.ui.web.action;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.exception.client.action.ActionException;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;

/**
 * WebAction
 * <p/>
 * Interface for user interactions on the web interface.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public final class WebAction {

    /** The Action ID */
    private String actionId;

    /** The Action Set ID */
    private String actionSetId;

    /** The Action Handler */
    private WebActionHandler actionHandler;

    /** The Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(WebAction.class);

    /**
     * Creates a new {@link WebAction} instance.
     * 
     * @param actionSetId
     *            the action set id
     * @param actionId
     *            the action id
     */
    WebAction(String actionSetId, String actionId, WebActionHandler actionHandler) {
        if (actionId == null) {
            throw new IllegalArgumentException("Cannot create Action for Action ID [null].");
        }
        if (actionSetId == null) {
            throw new IllegalArgumentException("Cannot create Action for  ActionSet ID [null].");
        }
        if (actionHandler == null) {
            throw new IllegalArgumentException("Cannot create Action for Handler [null].");
        }
        this.actionId = actionId;
        this.actionSetId = actionSetId;
        this.actionHandler = actionHandler;
    }

    /**
     * Getter for the actionId.
     * 
     * @return Returns the actionId.
     */
    public String getActionId() {
        return this.actionId;
    }

    /**
     * Getter for the actionSetId.
     * 
     * @return Returns the actionSetId.
     */
    public String getActionSetId() {
        return this.actionSetId;
    }

    /**
     * Execute the web action by calling the action handler.
     * 
     * @param parameter
     *            the action parameter
     * 
     * @return the action result
     * 
     * @throws ActionException
     *             when the action raises an exception
     */
    public WebActionResult execute(WebActionParameter parameter) throws ActionException {

        try {
            logger.info("Execute Action '", this.actionSetId, ".", this.actionId, "'.");
            WebActionResult result = this.actionHandler.execute(parameter);

            if (result == null) {
                logger.warning("Action '", this.actionSetId, ".", this.actionId, "' did not return a valid result.");
                result = new WebActionResult();
            }

            return result;
        } catch (ActionException ae) {
            logger.error("Error executing action '", this.getActionSetId(), ".", this.getActionId(), "'.");
            throw ae;
        } catch (Exception e) {
            logger.error("Error executing action '", this.getActionSetId(), ".", this.getActionId(), "'.");
            throw new ActionException("Error executing action '"
                    + this.getActionSetId() + "." + this.getActionId() + "'.", e);
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Action:\n");
        result.append(" - Set : ").append(this.actionSetId).append("\n");
        result.append(" - ID : ").append(this.actionId).append("\n");
        result.append(" - Handler : ").append(this.actionHandler.getClass().getName()).append("\n");
        return result.toString();
    }
}
