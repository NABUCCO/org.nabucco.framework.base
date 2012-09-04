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
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.EditorButtonExtension;

/**
 * EditorButton
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class EditorButton extends Button {

    private static final long serialVersionUID = 1L;

    private boolean isDisabled;

    private EditorButtonExtension extension;

    /**
     * Creates a new {@link EditorButton} instance.
     * 
     * @param extension
     */
    public EditorButton(EditorButtonExtension extension) {
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
     * Updates the button status according to the given status parameter
     * 
     * @param valueSelected
     *            is any value selected
     * @param isEditable
     *            is the model editable
     */
    public void updateStatus(boolean isModelDirty) {
        boolean retVal = false;

        if (this.isDirtyStateNeeded() == true && !isModelDirty) {
            retVal = true;
        } else if (this.isDirtyStateNeeded() == false) {
            retVal = false;
        }

        this.setDisabled(retVal);
    }

    /**
     * Setter for the isDisabled.
     * 
     * @param isDisabled
     *            The isDisabled to set.
     */
    private void setDisabled(boolean isDisabled) {
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
