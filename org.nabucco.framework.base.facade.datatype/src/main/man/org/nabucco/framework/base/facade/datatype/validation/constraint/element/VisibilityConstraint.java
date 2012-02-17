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
 * VisibilityConstraint
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class VisibilityConstraint extends Constraint {

    /** The visibility constraint type. */
    static final char TYPE = 'v';

    private VisibilityType visiblityType;

    /**
     * Creates a new {@link VisibilityConstraint} instance.
     * 
     * @param constraint
     *            the constraint to parse
     */
    VisibilityConstraint(String constraint) {
        super(ConstraintType.VISIBILITY);

        if (constraint.length() < 2) {
            throw new IllegalArgumentException("Cannot parse VisibilityConstraint.");
        }
        if (!Character.isDigit(constraint.charAt(1))) {
            throw new IllegalArgumentException("Cannot parse VisibilityConstraint.");
        }

        char flag = constraint.charAt(1);

        if (flag == '1') {
            this.visiblityType = VisibilityType.INVISIBLE;
        } else if (flag == '2') {
            this.visiblityType = VisibilityType.OBFUSCATED;
        } else {
            this.visiblityType = VisibilityType.VISIBLE;
        }
    }

    /**
     * Creates a new {@link VisibilityConstraint} instance.
     * 
     * @param visibilityType
     *            the visibility type
     */
    VisibilityConstraint(VisibilityType visibilityType) {
        super(ConstraintType.VISIBILITY);

        this.visiblityType = visibilityType;
    }

    @Override
    public void check(Validatable validatable, NabuccoProperty property, ValidationResult result) {
        // Nothing to check!
    }

    /**
     * Getter for the visible flag.
     * 
     * @return Returns the visible flag.
     */
    public boolean isVisible() {
        return this.visiblityType.isVisible();
    }

    @Override
    public String format() {
        StringBuilder result = new StringBuilder();
        result.append(TYPE);
        switch (this.visiblityType) {
        case VISIBLE:
            result.append(0);
            break;
        case INVISIBLE:
            result.append(1);
            break;
        case OBFUSCATED:
            result.append(2);
            break;
        }
        return result.toString();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Visible (");
        result.append(this.visiblityType.name());
        result.append(")");
        return result.toString();
    }

    @Override
    public boolean isValidRestriction(Constraint c) {
        if (c instanceof VisibilityConstraint) {
            VisibilityConstraint newConstraint = (VisibilityConstraint) c;
            if (this.visiblityType != null && newConstraint.visiblityType != null) {
                switch (this.visiblityType) {
                case INVISIBLE: {
                    // lowest level of visibility
                    return false;
                }
                case OBFUSCATED: {
                    // only invisible is more restrictiv
                    if (newConstraint.visiblityType == VisibilityType.INVISIBLE) {
                        return true;
                    }
                    return false;
                }
                case VISIBLE: {
                    if (newConstraint.visiblityType != VisibilityType.VISIBLE) {
                        return true;
                    }
                    return false;
                }

                }
            }
        }
        return false;
    }

}
