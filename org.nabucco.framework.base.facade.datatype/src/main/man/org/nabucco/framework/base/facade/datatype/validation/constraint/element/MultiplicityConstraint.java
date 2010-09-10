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

import java.util.Collection;
import java.util.regex.Pattern;

import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollection;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.validation.Validatable;
import org.nabucco.framework.base.facade.datatype.validation.ValidationError;
import org.nabucco.framework.base.facade.datatype.validation.ValidationResult;

/**
 * MultiplicityConstraint
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class MultiplicityConstraint extends Constraint {

    private final MultiplicityType multiplicity;

    static final char TYPE = 'm';

    private static final char ZERO_CHAR = '0';

    private static final char MANY_CHAR = 'n';

    private static final String MESSAGE = "Error at {0} with property {1} where multiplicity is [{2}] and multiplicity constraint is [{3}].";

    private static final Pattern PATTERN = Pattern.compile("\\,");

    /**
     * Creates a new {@link MultiplicityConstraint} instance.
     * 
     * @param multiplicity
     *            the multiplicity type
     */
    MultiplicityConstraint(MultiplicityType multiplicity) {
        super(ConstraintType.MULTIPLICITY);
        this.multiplicity = multiplicity;
    }

    /**
     * Creates a new {@link MultiplicityConstraint} instance.
     * 
     * @param constraint
     *            the constraint string to parse
     */
    MultiplicityConstraint(String constraint) {
        super(ConstraintType.MULTIPLICITY);

        String[] tokens = this.tokenize(constraint.substring(1), 2, 2);

        if (tokens[0].charAt(0) == ZERO_CHAR) {
            if (tokens[1].charAt(0) == MANY_CHAR) {
                this.multiplicity = MultiplicityType.ZERO_TO_MANY;
            } else {
                this.multiplicity = MultiplicityType.ZERO_TO_ONE;
            }
        } else {
            if (tokens[1].charAt(0) == MANY_CHAR) {
                this.multiplicity = MultiplicityType.ONE_TO_MANY;
            } else {
                this.multiplicity = MultiplicityType.ONE;
            }
        }
    }

    /**
     * Tokenizes the multiplicity constraint into min and max multiplicty.
     * 
     * @param constraint
     *            the constraint string
     * @param min
     *            the minimum lenght of a constraint
     * @param max
     *            the maximum length of a constraint
     * 
     * @return the tokenized string
     */
    protected String[] tokenize(String constraint, int min, int max) {
        String[] tokens = PATTERN.split(constraint);
        if (tokens.length < min) {
            throw new IllegalArgumentException("Length of tokens is less than "
                    + min + ": [" + constraint + "]");
        }
        if (max > -1 && tokens.length > max) {
            throw new IllegalArgumentException("Length of tokens is more than "
                    + max + ": [" + constraint + "]");
        }
        return tokens;
    }

    @Override
    public void check(Validatable owner, Object property, String propertyName,
            ValidationResult result) {

        String ownerName = owner.getClass().getSimpleName();

        if (property instanceof Collection<?>) {
            checkPropertyList((Collection<?>) property, ownerName, propertyName, result);
        } else {
            checkProperty(property, ownerName, propertyName, result);
        }
    }

    /**
     * Validates the single property against the constraint.
     * 
     * @param property
     *            the property to validate
     * @param owner
     *            the property owner
     * @param propertyName
     *            the property name
     * @param result
     *            the validation result
     */
    private void checkProperty(Object property, String owner, String propertyName,
            ValidationResult result) {
        if (!this.multiplicity.isOptional() && property == null) {
            Object[] arguments = new Object[] { owner, propertyName,
                    this.getMultiplicity(property), this.multiplicity.getName() };
            result.getErrorList().add(new ValidationError(MESSAGE, arguments));
        }
        if (this.multiplicity.isMultiple()) {
            Object[] arguments = new Object[] { owner, propertyName,
                    this.getMultiplicity(property), this.multiplicity.getName() };
            result.getErrorList().add(new ValidationError(MESSAGE, arguments));
        }
    }

    /**
     * Validates the property list against the constraint.
     * 
     * @param property
     *            the property to validate
     * @param owner
     *            the property owner
     * @param propertyName
     *            ther property name
     * @param result
     *            the validation result
     */
    private void checkPropertyList(Collection<?> propertyList, String owner, String propertyName,
            ValidationResult result) {

        // Do not validate lazy loaded collections.
        if (propertyList instanceof NabuccoCollection<?>) {
            if (((NabuccoCollection<?>) propertyList).getState() == NabuccoCollectionState.LAZY) {
                return;
            }
        }

        if (!this.multiplicity.isOptional() && propertyList.isEmpty()) {
            Object[] arguments = new Object[] { owner, propertyName,
                    this.getMultiplicity(propertyList), this.multiplicity.getName() };
            result.getErrorList().add(new ValidationError(MESSAGE, arguments));
        }
        if (!this.multiplicity.isMultiple()) {
            Object[] arguments = new Object[] { owner, propertyName,
                    this.getMultiplicity(propertyList), this.multiplicity.getName() };
            result.getErrorList().add(new ValidationError(MESSAGE, arguments));
        }
    }

    /**
     * Creates a string representation for the current multiplicity.
     * 
     * @param property
     *            the property to resolve
     * 
     * @return the string representation
     */
    private String getMultiplicity(Object property) {
        if (property instanceof Collection<?>) {
            if (((Collection<?>) property).isEmpty()) {
                return "0";
            }
            return "1..*";
        }
        if (property == null) {
            return "0";
        }
        return "1";
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("Multiplicity (");
        result.append(this.multiplicity.getName());
        result.append(")");

        return result.toString();
    }
}
