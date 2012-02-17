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
package org.nabucco.framework.base.ui.web.component.dialog.controls;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.control.TextDialogControlExtension;
import org.nabucco.framework.base.ui.web.model.dialog.DialogControlType;
import org.nabucco.framework.base.ui.web.model.dialog.controls.DialogTextControlModel;

/**
 * DialogControl
 * 
 * Dialog control is the implementation of the control elements for the dialogs. This elements are
 * not same as the editor controls. They have no model, no validation and binding.
 * 
 * The validation of the given entries will happen on the action level.
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DialogTextControl extends DialogControl {

    private static final long serialVersionUID = 1L;

    private TextDialogControlExtension extension;

    private DialogTextControlModel model;

    /**
     * Creates a new {@link DialogTextControl} instance.
     * 
     * @param extension
     *            the extension
     */
    public DialogTextControl(TextDialogControlExtension extension) {
        super(extension);

        this.extension = extension;
    }

    @Override
    public void init() throws ExtensionException {
        String id = this.getId();
        String label = this.getLabel();
        String tooltip = this.getTooltip();
        String position = this.getPosition();
        DialogControlType type = this.getDialogControlType();
        String defaultValue = this.getDefaultValue();
        boolean mandatory = this.isMandatory();
        boolean editable = this.isEditable();

        this.model = new DialogTextControlModel(id, label, tooltip, type, position, defaultValue, mandatory, editable);

    }

    /**
     * Getter for the dialog control model
     */
    @Override
    public DialogTextControlModel getModel() {
        return this.model;
    }

    /**
     * Retuns default value of the control if any or null
     * 
     * @return return the default value of the dialog
     */
    public String getDefaultValue() {
        return PropertyLoader.loadProperty(this.extension.getDefaultValue());
    }

    /**
     * Indicates if the control may be edited on the ui
     * 
     * @return true if editable
     */
    public boolean isEditable() {
        return PropertyLoader.loadProperty(this.extension.getEditable());
    }

}
