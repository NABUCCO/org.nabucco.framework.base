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
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.control.FileDialogControlExtension;
import org.nabucco.framework.base.ui.web.model.dialog.DialogControlType;
import org.nabucco.framework.base.ui.web.model.dialog.controls.DialogFileControlModel;

/**
 * DialogControl
 * 
 * Filo dialog control is the control that uploads files to the temprorary folder and saves the path
 * as a value
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DialogFileControl extends DialogControl {

    private static final long serialVersionUID = 1L;

    private FileDialogControlExtension extension;

    private DialogFileControlModel model;

    /**
     * Creates a new {@link DialogFileControl} instance.
     * 
     * @param extension
     *            the extension
     */
    public DialogFileControl(FileDialogControlExtension extension) {
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
        boolean mandatory = this.isMandatory();
        String extFilter = this.getExtFilter();

        this.model = new DialogFileControlModel(id, label, tooltip, type, position, mandatory, extFilter);

    }

    /**
     * Getter for the dialog control model
     */
    @Override
    public DialogFileControlModel getModel() {
        return this.model;
    }

    /**
     * Retuns default value of the control if any or null
     * 
     * @return return the default value of the dialog
     */
    public String getExtFilter() {
        return PropertyLoader.loadProperty(this.extension.getExtensionFilter());
    }



}
