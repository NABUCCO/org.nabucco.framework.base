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
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.ListButtonExtension;

/**
 * A pressable action with label, tooltip and icon in the relation.
 * 
 * @author Leonid Agranovskiy PRODYNA AG
 */
public class ListButton extends Button {

    private static final long serialVersionUID = 1L;

    /** The action reference extension */
    private ListButtonExtension extension;

    private boolean isDisabled = false;

    /**
     * Creates a new {@link ListButton} instance.
     * 
     * @param extension
     *            the button extension
     */
    public ListButton(ListButtonExtension extension) {
        super(extension);
        if (extension == null) {
            throw new IllegalArgumentException("Cannot create Button for extension [null].");
        }
        this.extension = extension;
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
    public RelationButtonModificationType getMoificationType() {
        return PropertyLoader.loadProperty(RelationButtonModificationType.class, this.extension.getModification());
    }

    /**
     * Updates the button status according to the given status parameter
     * 
     * @param valueSelected
     *            is any value selected
     * @param isEditable
     *            is the model editable
     */
    public void updateStatus(boolean valueSelected, boolean isEditable) {
        boolean retVal = false;

        RelationButtonModificationType moificationType = this.getMoificationType();

        if (moificationType == null) {
            return;
        }

        if (this.getSelectionNeeded() && (valueSelected == false)) {
            retVal = true;
        } else if (!moificationType.equals(RelationButtonModificationType.NONE) && (isEditable == false)) {
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
