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

import java.text.MessageFormat;
import java.util.regex.Pattern;

import org.nabucco.framework.base.facade.datatype.property.BasetypeProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyType;
import org.nabucco.framework.base.facade.datatype.property.SimpleProperty;
import org.nabucco.framework.base.facade.datatype.validation.Validatable;
import org.nabucco.framework.base.facade.datatype.validation.ValidationError;
import org.nabucco.framework.base.facade.datatype.validation.ValidationResult;

/**
 * LengthConstraint
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class LengthConstraint extends Constraint {

    /** The length constraint type. */
    static final char TYPE = 'l';

    private final int minLength;

    private final int maxLength;

    private static final char MAX_CHAR = 'n';

    private static final String MAX_LENGTH = "max-length";

    private static final String MIN_LENGTH = "min-length";

    private static final String MESSAGE = "Error at {0} with property {1} where length is {2} and {3} constraint is [{4}].";

    private static final Pattern PATTERN = Pattern.compile("\\,");

    /**
     * Creates a new {@link LengthConstraint} instance.
     * 
     * @param minLength
     *            the min length
     * @param maxLength
     *            the max length
     */
    LengthConstraint(int minLength, int maxLength) {
        super(ConstraintType.LENGTH);
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    /**
     * Creates a new {@link LengthConstraint} instance.
     * 
     * @param constraint
     *            the constraint string to parse
     */
    LengthConstraint(String constraint) {
        super(ConstraintType.LENGTH);
        String[] tokens = this.tokenize(constraint.substring(1), 2, 2);

        if (tokens[0].charAt(0) == MAX_CHAR) {
            this.minLength = 0;
        } else {
            this.minLength = Integer.parseInt(tokens[0]);
        }
        if (tokens[1].charAt(0) == MAX_CHAR) {
            this.maxLength = 255;
        } else {
            this.maxLength = Integer.parseInt(tokens[1]);
        }
    }

    /**
     * Tokenizes the string of a length constraint string representation into appropriate slices.
     * 
     * @param constraint
     *            the constraint string to tokenize
     * @param min
     *            the minimum amount of tokens
     * @param max
     *            the maximum amount of tokens
     * 
     * @return the tokenized constraint strings
     */
    private String[] tokenize(String constraint, int min, int max) {
        String[] tokens = PATTERN.split(constraint);
        if (tokens.length < min) {
            throw new IllegalArgumentException("Length of tokens is less than " + min + ": [" + constraint + "]");
        }
        if (max > -1 && tokens.length > max) {
            throw new IllegalArgumentException("Length of tokens is more than " + max + ": [" + constraint + "]");
        }
        return tokens;
    }

    /**
     * Getter for the minLength.
     * 
     * @return Returns the minLength.
     */
    public int getMinLength() {
        return this.minLength;
    }

    /**
     * Getter for the maxLength.
     * 
     * @return Returns the maxLength.
     */
    public int getMaxLength() {
        return this.maxLength;
    }

    @Override
    public void check(Validatable owner, NabuccoProperty property, ValidationResult result) {

        if (property == null || property.getInstance() == null) {
            return;
        }

        if (property.getPropertyType() == NabuccoPropertyType.BASETYPE) {
            BasetypeProperty basetypeProperty = (BasetypeProperty) property;
            Object value = basetypeProperty.getInstance().getValue();

            if (value == null) {
                return;
            }

            if (value instanceof String) {
                this.checkString((String) value, property.getName(), owner, result);
            } else if (value instanceof Number) {
                this.checkString(value.toString(), property.getName(), owner, result);
            }

        } else if (property.getPropertyType() == NabuccoPropertyType.SIMPLE) {
            SimpleProperty simpleProperty = (SimpleProperty) property;
            Object value = simpleProperty.getInstance();

            if (value == null) {
                return;
            }

            if (value instanceof String) {
                this.checkString((String) value, property.getName(), owner, result);
            } else if (value instanceof Number) {
                this.checkString(value.toString(), property.getName(), owner, result);
            }

        } else {
            this.raiseException(owner, property);
        }
    }

    /**
     * Checks a string against the length constraints.
     * 
     * @param propertyValue
     *            the concrete property value
     * @param propertyName
     *            name of the property to validate
     * @param parent
     *            the parent object
     * @param result
     *            the validation result
     */
    private void checkString(String propertyValue, String propertyName, Validatable parent, ValidationResult result) {

        int length = propertyValue.length();
        String parentName = parent.getClass().getSimpleName();

        if (length < this.minLength) {
            Object[] arguments = new Object[] { parentName, propertyName, length, MIN_LENGTH, minLength };

            String message = MessageFormat.format(MESSAGE, arguments);
            result.getErrorList().add(new ValidationError(parent, propertyName, message));
        }
        if (length > this.maxLength) {
            Object[] arguments = new Object[] { parentName, propertyName, length, MAX_LENGTH, maxLength };
            String message = MessageFormat.format(MESSAGE, arguments);
            result.getErrorList().add(new ValidationError(parent, propertyName, message));
        }
    }

    /**
     * Raise an {@link IllegalArgumentException}.
     * 
     * @param owner
     *            the validation owner
     * @param property
     *            the property
     */
    private void raiseException(Validatable owner, NabuccoProperty property) {
        StringBuilder message = new StringBuilder();
        message.append("Can only validate a LengthConstraint against a Basetype. Given property ");
        message.append(property.getName());
        message.append(" in type ");
        message.append(owner.getClass().getSimpleName());
        message.append(" was of type ");
        message.append(property.getPropertyType().getName());
        message.append(".");
        throw new IllegalArgumentException(message.toString());
    }

    @Override
    public String format() {
        StringBuilder result = new StringBuilder();
        result.append(TYPE);
        result.append(this.minLength);
        result.append(',');
        result.append(this.maxLength);
        return result.toString();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Length (");
        result.append(this.minLength);
        result.append(", ");
        result.append(this.maxLength);
        result.append(")");
        return result.toString();
    }

    @Override
    public boolean isValidRestriction(Constraint c) {
        if (c instanceof LengthConstraint) {
            boolean result = true;
            result &= this.getMaxLength() > ((LengthConstraint) c).getMaxLength();
            result &= this.getMinLength() < ((LengthConstraint) c).getMinLength();
            return result;
        }
        return false;
    }

}
