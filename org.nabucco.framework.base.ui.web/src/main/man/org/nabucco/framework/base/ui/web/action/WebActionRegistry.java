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
import java.util.StringTokenizer;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionMap;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtension;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.action.ActionExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.action.ActionSetExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.exception.client.action.ActionException;

/**
 * WebActionRegistry
 * <p/>
 * The registry for configured web actions that are called by the web user interface.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public final class WebActionRegistry {

    private static final String ACTION_DELIMITER = ".";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(WebActionRegistry.class);

    /** Web Action Registry Entry Map */
    private Map<String, WebActionRegistryEntry> entryMap = new HashMap<String, WebActionRegistryEntry>();

    /**
     * Singleton instance.
     */
    private static WebActionRegistry instance;

    /**
     * Private constructor.
     */
    private WebActionRegistry() {
        this.configure();
    }

    /**
     * Configure the action registry by extensions registered on the
     * <code>org.nabucco.ui.action</code> extension point.
     */
    private void configure() {
        try {
            ExtensionMap extensions = NabuccoSystem.getExtensionResolver().resolveExtensions(
                    ExtensionPointType.ORG_NABUCCO_UI_ACTION);

            String[] extensionNames = extensions.getExtensionNames();
            for (String extensionName : extensionNames) {
                NabuccoExtension extension = extensions.getExtension(extensionName);
                if (extension instanceof ActionSetExtension) {
                    this.createRegistryEntry((ActionSetExtension) extension);
                }
            }

        } catch (ExtensionException e) {
            logger.error(e, "Error initializing action registry extension configuration.");
        }
    }

    /**
     * Create a registry entry for the given action set and put it into the registry.
     * 
     * @param extension
     *            the action set extension
     */
    private void createRegistryEntry(ActionSetExtension extension) {
        ExtensionId extensionId = extension.getIdentifier();

        String actionSetId = PropertyLoader.loadProperty(extension.getActionSetId());
        if (actionSetId == null) {
            logger.warning("Cannot configure actionset for extension '", extensionId, "', no action set id defined.");
            return;
        }

        WebActionRegistryEntry entry = new WebActionRegistryEntry(actionSetId);

        for (ActionExtension action : extension.getActions()) {
            String actionId = PropertyLoader.loadProperty(action.getActionId());
            Class<WebActionHandler> actionClass = PropertyLoader.<WebActionHandler> loadProperty(action
                    .getActionClass());

            if (actionId == null) {
                logger.warning("Cannot configure action '", actionSetId, ".", actionId, "' for extension '",
                        extensionId, "', no action id defined.");
                continue;
            }

            if (actionClass == null) {
                logger.warning("Cannot configure action '", actionSetId, ".", actionId, "' for extension '",
                        extensionId, "', no action class defined.");
                continue;
            }

            entry.addAction(actionId, actionClass);
        }

        this.entryMap.put(actionSetId, entry);
    }

    /**
     * Singleton access.
     * 
     * @return the WebActionRegistry instance.
     */
    public static synchronized WebActionRegistry getInstance() {
        if (instance == null) {
            instance = new WebActionRegistry();
        }
        return instance;
    }

    /**
     * Returns the action from the registry with the given actionSet and action id. When no action
     * is found, null is returned.
     * 
     * @param actionPath
     *            the combination of actionSetId and actionId delimited by a '.'
     * 
     * @return the action for the given perspective id and action id
     * 
     * @throws ActionException
     *             when the required action is not configured correctly
     */
    public WebAction newAction(String actionPath) throws ActionException {
        if (actionPath == null || actionPath.isEmpty()) {
            return null;
        }

        StringTokenizer tokenizer = new StringTokenizer(actionPath, ACTION_DELIMITER);

        if (tokenizer.countTokens() != 2) {
            return null;
        }

        return this.newAction(tokenizer.nextToken(), tokenizer.nextToken());
    }

    /**
     * Returns the action from the registry with the given actionSet and action id. When no action
     * is found, null is returned.
     * 
     * @param actionSetId
     *            the id of the action set
     * @param actionId
     *            the id of the action
     * 
     * @return the action for the given perspective id and action id
     * 
     * @throws ActionException
     *             when the required action is not configured correctly
     */
    public WebAction newAction(String actionSetId, String actionId) throws ActionException {
        WebActionRegistryEntry entry = this.entryMap.get(actionSetId);
        if (entry == null) {
            return null;
        }
        return entry.newAction(actionId);
    }

}
