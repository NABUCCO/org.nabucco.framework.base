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

import java.util.ArrayList;
import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.control.DropDownDialogControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.control.DropDownSelectionItemExtension;
import org.nabucco.framework.base.ui.web.model.dialog.controls.DialogDropDownControlModel;
import org.nabucco.framework.base.ui.web.model.editor.control.property.dropdown.selection.PlainSelectionItem;
import org.nabucco.framework.base.ui.web.model.editor.control.property.dropdown.selection.SelectionItem;

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
public class DialogDropDownControl extends DialogControl {

    private static final long serialVersionUID = 1L;

    private DropDownDialogControlExtension extension;

    private DialogDropDownControlModel model;

    /**
     * Creates a new {@link DialogDropDownControl} instance.
     * 
     * @param extension
     *            the extension
     */
    public DialogDropDownControl(DropDownDialogControlExtension extension) {
        super(extension);

        this.extension = extension;
    }

    @Override
    public void init() throws ExtensionException {
        String id = this.getId();
        String label = this.getLabel();
        String tooltip = this.getTooltip();
        String position = this.getPosition();
        String defaultId = this.getDefaultId();
        boolean mandatory = this.isMandatory();
        boolean editable = this.isEditable();

        List<SelectionItem<?>> selection = this.getSelection();

        this.model = new DialogDropDownControlModel(id, label, tooltip, position, defaultId, mandatory, editable,
                selection);

    }

    /**
     * Getter for the dialog control model
     */
    @Override
    public DialogDropDownControlModel getModel() {
        return this.model;
    }

    /**
     * Returns the selection of the combobox according to the extension
     * 
     * @return list of selection items
     */
    public List<SelectionItem<?>> getSelection() {
        List<SelectionItem<?>> retVal = new ArrayList<SelectionItem<?>>();

        for (DropDownSelectionItemExtension selection : this.extension.getSelectionList()) {
            String name = PropertyLoader.loadProperty(selection.getName());
            String id = PropertyLoader.loadProperty(selection.getId());
            String path = PropertyLoader.loadProperty(selection.getLocalizationPath());

            SelectionItem<?> item = new PlainSelectionItem(name, id, path);
            retVal.add(item);
        }

        return retVal;
    }

    /**
     * Retuns default value of the control if any or null
     * 
     * @return return the default value of the dialog
     */
    public String getDefaultId() {
        return PropertyLoader.loadProperty(this.extension.getDefaultId());
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
