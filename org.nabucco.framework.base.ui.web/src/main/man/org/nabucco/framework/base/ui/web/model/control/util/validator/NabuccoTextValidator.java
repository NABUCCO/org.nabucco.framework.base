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
package org.nabucco.framework.base.ui.web.model.control.util.validator;

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.ui.web.component.WebComponent;
import org.nabucco.framework.base.ui.web.component.work.editor.control.EditorControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.TextInputControl;

/**
 * NabuccoDefaultValidator
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class NabuccoTextValidator extends NabuccoAbstractValidator<Basetype> {

    /**
     * 
     * Creates a new {@link NabuccoTextValidator} instance.
     * 
     * @param component
     */
    public NabuccoTextValidator(WebComponent component) {
        super(component);
    }

    @Override
    public boolean validateElement(Basetype input) {
        if (input == null) {
            return true;
        }

        if ((this.getComponent() instanceof EditorControl) == false) {
            throw new IllegalArgumentException("Validator is not able to validate the given component type.");
        }

        boolean retVal = true;

        if (this.getComponent() instanceof TextInputControl) {
            String regex = ((TextInputControl) this.getComponent()).getRegex();

            if (regex.length() > 0) {
                retVal = input.getValueAsString().matches(regex);
            }
        }

        return retVal;
    }

}
