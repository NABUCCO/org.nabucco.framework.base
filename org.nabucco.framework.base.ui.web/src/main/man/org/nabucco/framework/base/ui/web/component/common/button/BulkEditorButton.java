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
package org.nabucco.framework.base.ui.web.component.common.button;

import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.BulkEditorButtonExtension;

/**
 * EditorButton
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class BulkEditorButton extends Button {

    private static final long serialVersionUID = 1L;

    private boolean isDisabled;

    private BulkEditorButtonExtension extension;

    /**
     * Creates a new {@link BulkEditorButton} instance.
     * 
     * @param extension
     */
    public BulkEditorButton(BulkEditorButtonExtension extension) {
        super(extension);
        this.extension = extension;
    }

    /**
     * Returns true if the button is disabled until the model is in dirty state
     * 
     * @return true is dirty state needed
     */
    public boolean isDirtyStateNeeded() {
        Boolean retVal = PropertyLoader.loadProperty(this.extension.getDirtyStateNeeded());
        return retVal;
    }

    /**
     * Returns if the relation need a selection to proceed
     * 
     * @return
     */
    public boolean getSelectionNeeded() {
        return PropertyLoader.loadProperty(this.extension.getSelection());
    }

    /**
     * Returns the modification type of the button
     */
    public ButtonModificationType getModificationType() {
        return PropertyLoader.loadProperty(ButtonModificationType.class, this.extension.getModification());
    }

    /**
     * Updates the button status according to the given status parameter
     * 
     * @param valueSelected
     *            is any value selected
     * @param isEditable
     *            is the model editable
     */
    public void updateStatus(boolean isDirtyState, boolean valueSelected, boolean isEditable) {
        boolean retVal = false;

        ButtonModificationType moificationType = this.getModificationType();

        if (moificationType == null) {
            return;
        }

        if (this.getSelectionNeeded() && (valueSelected == false)) {
            retVal = true;
        } else if (!moificationType.equals(ButtonModificationType.NONE) && (isEditable == false)) {
            retVal = true;
        } else if (this.isDirtyStateNeeded() && (isDirtyState == false)) {
            retVal = true;
        }

        this.setDisabled(retVal);
    }

    /**
     * Setter for the isDisabled.
     * 
     * @param isDisabled
     *            The isDisabled to set.
     */
    protected void setDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    /**
     * Getter for the isDisabled.
     * 
     * @return Returns the isDisabled.
     */
    @Override
    public boolean isDisabled() {
        return super.isDisabled() || this.isDisabled;
    }
}
