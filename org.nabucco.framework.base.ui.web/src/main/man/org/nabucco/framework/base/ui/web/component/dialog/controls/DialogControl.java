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
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.control.DialogControlExtension;
import org.nabucco.framework.base.ui.web.component.WebComponent;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.dialog.DialogControlType;
import org.nabucco.framework.base.ui.web.model.dialog.controls.DialogGridModelElement;

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
public abstract class DialogControl extends WebComponent {

    private static final long serialVersionUID = 1L;

    private DialogControlExtension extension;

    /**
     * Creates a new {@link DialogControl} instance.
     * 
     * @param extension
     *            the extension
     */
    public DialogControl(DialogControlExtension extension) {
        super(WebElementType.CONTROL, extension);

        this.extension = extension;
    }

    @Override
    public void init() throws ExtensionException {

    }

    /**
     * Getter for the control label
     * 
     * @return label
     */
    public String getLabel() {
        return PropertyLoader.loadProperty(this.extension.getLabel());
    }

    /**
     * Getter for the tooltip of the control
     * 
     * @return the tooltip
     */
    public String getTooltip() {
        return PropertyLoader.loadProperty(this.extension.getTooltip());
    }

    /**
     * Getter for the control position within the grid
     * 
     * @return position like A0-B0
     */
    public String getPosition() {
        return PropertyLoader.loadProperty(this.extension.getPosition());
    }

    /**
     * Getter for the type of control
     * 
     * @return type of control
     */
    public DialogControlType getDialogControlType() {
        return PropertyLoader.loadProperty(DialogControlType.class, this.extension.getType());
    }

    /**
     * Indicates if the control must be filled to close dialog
     * 
     * @return true if mandatory
     */
    public boolean isMandatory() {
        return PropertyLoader.loadProperty(this.extension.getMandatory());
    }

    public abstract DialogGridModelElement getModel();

    /**
     * returns the id of the dialog control element
     * 
     * @return id of the control element
     */
    public String getId() {
        return PropertyLoader.loadProperty(this.extension.getIdentifier());
    }

    @Override
    public JsonElement toJson() {
        JsonMap retVal = new JsonMap();

        retVal.add(JSON_MODEL, this.getModel().toJson());

        return retVal;
    }

}
