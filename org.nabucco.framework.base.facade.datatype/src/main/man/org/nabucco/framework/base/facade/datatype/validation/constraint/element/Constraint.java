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
 * Constraint
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class Constraint {

    private ConstraintType type;

    /**
     * Creates a new {@link Constraint} instance.
     * 
     * @param type
     *            the constraint type to set.
     */
    public Constraint(ConstraintType type) {
        this.type = type;
    }

    /**
     * Checks a validatable type against this constraint.
     * 
     * @param validatable
     *            the owning validatable
     * @param property
     *            the property to validate
     * @param result
     *            the validation result
     */
    public abstract void check(Validatable validatable, NabuccoProperty property, ValidationResult result);

    /**
     * Format the constraint to a parsable string representation.
     * 
     * @return the constraint as string
     */
    public abstract String format();

    /**
     * Getter for the constraint type.
     * 
     * @return Returns the type.
     */
    public ConstraintType getType() {
        return this.type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Constraint other = (Constraint) obj;
        if (this.type != other.type) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if (this.type != null) {
            return this.type.getName();
        }
        return this.getClass().getSimpleName();
    }

    /**
     * Validates if the restriction by the dynamic constrain is valid according to the static
     * constraints
     * 
     * @param c
     *            constraint to be validated
     * @return
     */
    public abstract boolean isValidRestriction(Constraint constraint);

}
