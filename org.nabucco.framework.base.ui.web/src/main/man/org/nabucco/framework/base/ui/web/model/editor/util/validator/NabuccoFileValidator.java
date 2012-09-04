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
package org.nabucco.framework.base.ui.web.model.editor.util.validator;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.content.ContentEntry;
import org.nabucco.framework.base.facade.datatype.content.ContentEntryType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.validation.constraint.parser.ConstraintContainer;
import org.nabucco.framework.base.ui.web.component.WebComponent;
import org.nabucco.framework.base.ui.web.component.work.editor.control.FileControl;

/**
 * NabuccoFileValidator
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class NabuccoFileValidator extends NabuccoAbstractValidator<ContentEntry> {

    private static final String DATA_PROPERTY_NAME = "data";

    /**
     * Creates a new {@link NabuccoFileValidator} instance.
     * 
     * @param component
     */
    public NabuccoFileValidator(WebComponent component) {
        super(component);
    }

    @Override
    public boolean validateElement(ContentEntry input) {

        FileControl fileControl = (FileControl) this.getComponent();
        ConstraintContainer constraints = fileControl.getModel().getProperty().getConstraints();
        int minMultiplicity = constraints.getMinMultiplicity();
        if (minMultiplicity == 1) {
            ContentEntryType type = input.getType();
            switch (type) {
            case INTERNAL_DATA: {
                NabuccoProperty contentDataProperty = input.getProperty(DATA_PROPERTY_NAME);
                if (input.getDatatypeState() == DatatypeState.INITIALIZED && contentDataProperty.getInstance() == null) {
                    return false;
                }

                break;
            }
            case EXTERNAL_DATA: {
                NabuccoProperty dataProperty = input.getProperty(DATA_PROPERTY_NAME);
                if (dataProperty == null) {
                    return false;
                }
                if (dataProperty.getInstance() == null) {
                    return false;
                }
                break;
            }
            default: {
                throw new IllegalArgumentException("Cannot validate a folder");
            }
            }
        }
        return true;
    }
}
