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
package org.nabucco.framework.base.ui.web.component.common.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.DialogExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.dialog.Dialog;

/**
 * DialogController
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DialogController {

    private static final long serialVersionUID = 1L;

    private final Map<String, WebElement> elementMap = new LinkedHashMap<String, WebElement>();

    private NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(DialogController.class);

    /**
     * Creates a new {@link DialogController} instance.
     */
    public DialogController() {
    }

    /**
     * Creates a new Dialog with given id and returns the instanceId of it
     * 
     * @param dialogId
     *            id of the dialog
     * 
     * @return instance id of the created dialog or null if not created
     */
    public Dialog createDialog(String dialogId) {
        if (dialogId == null) {
            throw new IllegalArgumentException("Cannot create the dialog for id 'null'");
        }

        Dialog dialog = (Dialog) this.elementMap.get(dialogId);

        if (dialog == null) {
            
            try {
                // TODO: Dialog Stack!

                DialogExtension dialogExtension = (DialogExtension) NabuccoSystem.getExtensionResolver()
                        .resolveExtension(ExtensionPointType.ORG_NABUCCO_UI_DIALOG, dialogId);

                String uuid = NabuccoSystem.createUUID();
                dialog = new Dialog(uuid, dialogExtension);
                dialog.init();

                this.elementMap.put(uuid, dialog);
            } catch (ExtensionException ee) {
                this.logger.error("Cannot create Dialog with id '" + dialogId + "'.", ee);
            }
        }

        return dialog;
    }

    /**
     * Removes the dialog instance from the scope
     * 
     * @param instanceId
     *            the unique instance id of the dialog
     * @return true if deleted or false if not found
     */
    public boolean deleteDialog(String instanceId) {
        if (instanceId == null) {
            throw new IllegalArgumentException("Cannot delete dialog with id 'null'");
        }
        WebElement removedElement = this.elementMap.remove(instanceId);
        if (removedElement == null) {
            return false;
        }

        return true;
    }

    /**
     * Returns a Dialog instance
     * 
     * @param dialogId
     *            instanceid of the dialog
     * 
     * @return the dialog for the specified dialog id or null if not found
     */
    public Dialog getDialog(String instanceId) {
        if (instanceId == null) {
            throw new IllegalArgumentException("Cannot find the dialog for id 'null'");
        }

        Dialog dialog = (Dialog) this.elementMap.get(instanceId);

        return dialog;
    }

}
