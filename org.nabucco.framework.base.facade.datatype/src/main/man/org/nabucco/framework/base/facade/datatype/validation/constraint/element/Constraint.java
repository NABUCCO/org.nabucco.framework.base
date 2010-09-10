/*
 * Copyright 2010 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco-source.org/nabucco-license.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.facade.datatype.validation.constraint.element;

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
     * @param propertyName
     *            name of the property to validate
     * @param result
     *            the validation result
     */
    public abstract void check(Validatable validatable, Object property, String propertyName,
            ValidationResult result);

    /**
     * Getter for the constraint type.
     * 
     * @return Returns the type.
     */
    public ConstraintType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        if (this.type != null) {
            return this.type.getName();
        }
        return this.getClass().getSimpleName();
    }

}
