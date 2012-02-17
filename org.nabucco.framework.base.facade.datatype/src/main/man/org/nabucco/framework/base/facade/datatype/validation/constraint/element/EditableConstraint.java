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
package org.nabucco.framework.base.facade.datatype.validation.constraint.element;

import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.validation.Validatable;
import org.nabucco.framework.base.facade.datatype.validation.ValidationResult;

/**
 * EditableConstraint
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class EditableConstraint extends Constraint {

    /** The editable constraint type. */
    static final char TYPE = 'e';

    private boolean editable;

    /**
     * Creates a new {@link EditableConstraint} instance.
     * 
     * @param editable
     *            whether the property is editable or not
     */
    EditableConstraint(boolean editable) {
        super(ConstraintType.EDIT);

        this.editable = editable;
    }

    /**
     * Creates a new {@link EditableConstraint} instance.
     * 
     * @param constraint
     */
    EditableConstraint(String constraint) {
        super(ConstraintType.EDIT);

        if (constraint.length() < 2) {
            throw new IllegalArgumentException("Cannot parse EditableConstraint.");
        }
        if (!Character.isDigit(constraint.charAt(1))) {
            throw new IllegalArgumentException("Cannot parse EditableConstraint.");
        }

        char flag = constraint.charAt(1);

        if (flag == '0') {
            this.editable = false;
        } else {
            this.editable = true;
        }
    }

    @Override
    public void check(Validatable validatable, NabuccoProperty property, ValidationResult result) {
        // Nothing to check!
    }

    /**
     * Getter for the editable flag.
     * 
     * @return Returns the editable flag.
     */
    public boolean isEditable() {
        return this.editable;
    }

    @Override
    public String format() {
        StringBuilder result = new StringBuilder();
        result.append(TYPE);
        result.append(this.editable ? 1 : 0);
        return result.toString();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Editable (");
        result.append(this.editable);
        result.append(")");
        return result.toString();
    }

    @Override
    public boolean isValidRestriction(Constraint constraint) {
        if (constraint instanceof EditableConstraint) {
            EditableConstraint newConstraint = (EditableConstraint) constraint;
            boolean valid = !(newConstraint.isEditable() && !this.isEditable());
            return valid;
        }
        return false;
    }

}
