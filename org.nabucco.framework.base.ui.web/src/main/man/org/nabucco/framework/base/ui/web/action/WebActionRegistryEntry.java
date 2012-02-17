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

import java.util.HashMap;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.exception.client.action.ActionException;

/**
 * WebActionRegistryEntry
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
class WebActionRegistryEntry {

    /** The Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(WebActionRegistryEntry.class);

    /** The map of registered actions by their ID. */
    private Map<String, Class<WebActionHandler>> actionMap = new HashMap<String, Class<WebActionHandler>>();

    /** ID of the action set */
    private String actionSetId;

    /**
     * Creates a new {@link WebActionRegistryEntry} instance.
     * 
     * @param actionSetId
     *            the action set id
     */
    public WebActionRegistryEntry(String actionSetId) {
        this.actionSetId = actionSetId;
    }

    /**
     * Add the action to the registry entry.
     * 
     * @param actionId
     *            the id of the action
     * @param actionClass
     *            the action class to add to the registry
     */
    public void addAction(String actionId, Class<WebActionHandler> actionClass) {
        this.actionMap.put(actionId, actionClass);
    }

    /**
     * Getter for the action with the given id.
     * 
     * @param actionId
     *            the action id of the action to return
     * 
     * @return the action with the given id, or null if the action does not exist
     * 
     * @throws ActionException
     *             when the action cannot be created
     */
    public WebAction newAction(String actionId) throws ActionException {
        WebActionHandler actionHandler = this.instantiateHandler(actionId);
        return new WebAction(this.actionSetId, actionId, actionHandler);
    }

    /**
     * Create a new action handler handler instance.
     * 
     * @param actionId
     *            the id of the action
     * 
     * @return the new action handler instance
     * 
     * @throws ActionException
     *             when the action handler cannot be instantiated
     */
    private WebActionHandler instantiateHandler(String actionId) throws ActionException {

        Class<WebActionHandler> actionHandler = this.actionMap.get(actionId);

        if (actionHandler == null) {
            throw new ActionException("No Action Handler class configured for for action '"
                    + this.actionSetId + "." + actionId + "'.");
        }

        try {
            return actionHandler.newInstance();
        } catch (InstantiationException ie) {
            logger.error(ie, "The configured web action class of action '", this.actionSetId, ".", actionId,
                    "' cannot be instantiated.");

            throw new ActionException("The configured web action class of action '"
                    + this.actionSetId + "." + actionId + "' cannot be instantiated.", ie);
        } catch (IllegalAccessException iae) {
            logger.error(iae, "The configured web action class of action '", this.actionSetId, ".", actionId,
                    "' is not accessable.");

            throw new ActionException("The configured web action class of action '"
                    + this.actionSetId + "." + actionId + "' is not accessable.", iae);
        }
    }
}
