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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.PickerDialogExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.dialog.PickerDialog;

/**
 * DialogController
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class PickerDialogController {

    private static final long serialVersionUID = 1L;

    private NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(PickerDialogController.class);

    /** The map with controlled pickers */
    private final Map<String, WebElement> elementMap = new LinkedHashMap<String, WebElement>();

    private final Map<String, WebElement> pickerOwner = new HashMap<String, WebElement>();


    /**
     * Creates a new {@link PickerDialogController} instance.
     */
    public PickerDialogController() {

    }

    /**
     * Creates a new Dialog with given id and returns the instanceId of it
     * 
     * @param pickerId
     *            id of the dialog
     * @param source
     *            the source of the picker dialog (needed to process selected values)
     * 
     * @return instance id of the created dialog or null if not created
     */
    public String createPickerDialog(String pickerId, WebElement source) {
        if (pickerId == null) {
            throw new IllegalArgumentException("Cannot create the picker for id 'null'");
        }
        if (source == null) {
            throw new IllegalArgumentException("Cannot create the picker for id '"
                    + pickerId + "'. The source is not defined");
        }

        PickerDialog pickerDialog = (PickerDialog) this.elementMap.get(pickerId);

        if (pickerDialog == null) {

            try {

                // TODO: Dialog Stack!

                PickerDialogExtension extension = (PickerDialogExtension) NabuccoSystem.getExtensionResolver()
                        .resolveExtension(ExtensionPointType.ORG_NABUCCO_UI_PICKER_DIALOG, pickerId);

                String uuid = NabuccoSystem.createUUID();
                pickerDialog = new PickerDialog(extension, uuid);
                pickerDialog.init();

                this.pickerOwner.put(uuid, source);
                this.elementMap.put(uuid, pickerDialog);

            } catch (ExtensionException ee) {
                this.logger.debug("Cannot create Dialog with id '" + pickerId + "'.", ee);
                return null;
            }

        }

        return pickerDialog.getInstanceId();
    }

    /**
     * Return the owner of the picker dialog (Control, Editor, Relation...)
     * 
     * @param pickerDialogInstanceId
     *            the id of the dialog
     * @return the owner instance or null if not found
     */
    public WebElement getPickerDialogSource(String pickerDialogInstanceId) {
        return this.pickerOwner.get(pickerDialogInstanceId);
    }

    /**
     * Removes the dialog instance from the scope
     * 
     * @param instanceId
     *            the unique instance id of the dialog
     * @return true if deleted or false if not found
     */
    public boolean deletePickerDialog(String instanceId) {
        if (instanceId == null) {
            throw new IllegalArgumentException("Cannot delete picker dialog with id 'null'");
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
    public PickerDialog getPickerDialog(String instanceId) {
        if (instanceId == null) {
            throw new IllegalArgumentException("Cannot find the dialog for id 'null'");
        }

        PickerDialog dialog = (PickerDialog) this.elementMap.get(instanceId);

        if (dialog == null) {
            return null;
        }

        return dialog;
    }


}
