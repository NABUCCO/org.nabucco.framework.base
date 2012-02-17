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

import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollection;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.property.CollectionProperty;
import org.nabucco.framework.base.facade.datatype.property.ComponentRelationProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyType;
import org.nabucco.framework.base.facade.datatype.validation.Validatable;
import org.nabucco.framework.base.facade.datatype.validation.ValidationError;
import org.nabucco.framework.base.facade.datatype.validation.ValidationResult;

/**
 * MultiplicityConstraint
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class MultiplicityConstraint extends Constraint {

    /** Minimum multiplicity constraint value */
    public static final int MIN_VALUE = 0;

    /** Maximum multiplicity constraint value */
    public static final int MAX_VALUE = Integer.MAX_VALUE;

    private final MultiplicityType multiplicity;

    private final int minValue;

    private final int maxValue;

    static final char TYPE = 'm';

    private static final int ZERO = 0;

    private static final int ONE = 1;

    private static final String MESSAGE = "Error at {0} with property {1} where multiplicity is {2} and multiplicity constraint is [{3}].";

    private static final Pattern PATTERN = Pattern.compile("\\,");

    private static final Pattern NUMBERS = Pattern.compile("\\d+");

    /**
     * Creates a new {@link MultiplicityConstraint} instance.
     * 
     * @param multiplicity
     *            the multiplicity type
     */
    MultiplicityConstraint(MultiplicityType multiplicity) {
        super(ConstraintType.MULTIPLICITY);
        switch (multiplicity) {
        case ONE: {
            this.minValue = 1;
            this.maxValue = 1;
            break;
        }
        case ONE_TO_MANY: {
            this.minValue = 1;
            // unbound max => max int
            this.maxValue = MAX_VALUE;
            break;
        }
        case ZERO_TO_MANY: {
            this.minValue = 0;
            // unbound max => max int
            this.maxValue = MAX_VALUE;
            break;
        }
        case ZERO_TO_ONE: {
            this.minValue = 0;
            this.maxValue = 1;
            break;
        }
        case N_TO_M: {
            this.minValue = -1;
            this.maxValue = -1;
            break;
        }
        default: {
            throw new IllegalStateException("undefined multiplicity value for contraint");
        }
        }

        this.multiplicity = multiplicity;
    }

    /**
     * Creates a new {@link MultiplicityConstraint} instance.
     * 
     * @param multiplicity
     *            the multiplicity type
     */
    MultiplicityConstraint(int min, int max) {
        super(ConstraintType.MULTIPLICITY);
        this.minValue = min;
        this.maxValue = max;
        if (min == 0) {
            if (max > 1) {
                this.multiplicity = MultiplicityType.ZERO_TO_MANY;
            } else if (max == 1) {
                this.multiplicity = MultiplicityType.ZERO_TO_ONE;
            } else {
                this.multiplicity = MultiplicityType.N_TO_M;
            }
        } else if (min == 1) {
            if (max > 1) {
                this.multiplicity = MultiplicityType.ONE_TO_MANY;
            } else if (max == 1) {
                this.multiplicity = MultiplicityType.ONE;
            } else {
                this.multiplicity = MultiplicityType.N_TO_M;
            }
        } else {
            this.multiplicity = MultiplicityType.N_TO_M;
        }
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

        if (NUMBERS.matcher(tokens[0]).matches()) {
            Integer min = Integer.valueOf(tokens[0]);

            // default upper unbound
            Integer max = MAX_VALUE;

            if (NUMBERS.matcher(tokens[1]).matches()) {
                max = Integer.parseInt(tokens[1]);
            }

            if (min == ZERO) {
                if (max == ONE) {
                    this.multiplicity = MultiplicityType.ZERO_TO_ONE;
                } else if (max == MAX_VALUE) {
                    this.multiplicity = MultiplicityType.ZERO_TO_MANY;
                } else {
                    this.multiplicity = MultiplicityType.N_TO_M;
                }
            } else if (min == ONE) {
                if (max == ONE) {
                    this.multiplicity = MultiplicityType.ONE;
                } else if (max == MAX_VALUE) {
                    this.multiplicity = MultiplicityType.ONE_TO_MANY;
                } else {
                    this.multiplicity = MultiplicityType.N_TO_M;
                }
            } else {
                this.multiplicity = MultiplicityType.N_TO_M;
            }
            this.minValue = min;
            this.maxValue = max;
        } else {
            throw new IllegalArgumentException("unable to create multiplicty contraint for wildcard min value");
        }

    }

    /**
     * Getter for the multiplicity.
     * 
     * @return Returns the multiplicity.
     */
    public MultiplicityType getMultiplicity() {
        return this.multiplicity;
    }

    /**
     * Getter for the minMultiplicity.
     * 
     * @return Returns the minMultiplicity.
     */
    public int getMinMultiplicity() {
        if (this.multiplicity.isOptional() && this.multiplicity != MultiplicityType.N_TO_M) {
            return 0;
        }
        if (this.multiplicity == MultiplicityType.N_TO_M) {
            return this.minValue;
        }
        return 1;
    }

    /**
     * Getter for the maxMultiplicity.
     * 
     * @return Returns the maxMultiplicity.
     */
    public int getMaxMultiplicity() {
        if (this.multiplicity.isMultiple() && this.multiplicity != MultiplicityType.N_TO_M) {
            return MAX_VALUE;
        }
        if (this.multiplicity == MultiplicityType.N_TO_M) {
            return this.maxValue;
        }
        return 1;
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

    @Override
    public void check(Validatable owner, NabuccoProperty property, ValidationResult result) {

        switch (property.getPropertyType()) {

        case COLLECTION:
            CollectionProperty listProperty = (CollectionProperty) property;
            this.checkPropertyList(owner, listProperty, listProperty.getInstance(), result);
            break;

        case COMPONENT_RELATION:
            ComponentRelationProperty crProperty = (ComponentRelationProperty) property;
            this.checkPropertyList(owner, crProperty, crProperty.getInstance(), result);
            break;

        default:
            this.checkProperty(owner, property, result);
            break;

        }
    }

    /**
     * Validates the single property against the constraint.
     * 
     * @param property
     *            the property to validate
     * @param owner
     *            the property owner
     * @param result
     *            the validation result
     */
    private void checkProperty(Validatable owner, NabuccoProperty property, ValidationResult result) {

        String ownerName = owner.getClass().getSimpleName();

        // Check for mandatory [1] multiplicity!
        if (!this.multiplicity.isOptional() && property.getInstance() == null && property.getReferenceId() == null) {

            Object[] arguments = new Object[] { ownerName, property.getName(), this.getMultiplicity(property),
                    this.multiplicity.getName() };

            String message = MessageFormat.format(MESSAGE, arguments);
            result.getErrorList().add(new ValidationError(owner, property.getName(), message));
        }

        // Single properties must NOT be of multiple!
        if (this.multiplicity.isMultiple()) {

            Object[] arguments = new Object[] { ownerName, property.getName(), this.getMultiplicity(property),
                    this.multiplicity.getName() };

            String message = MessageFormat.format(MESSAGE, arguments);
            result.getErrorList().add(new ValidationError(owner, property.getName(), message));
        }

        if (this.multiplicity == MultiplicityType.N_TO_M) {

            Integer currentMultiplicity = this.getMultiplicity(property);

            if (currentMultiplicity < this.minValue || currentMultiplicity > this.maxValue) {
                Object[] arguments = new Object[] { ownerName, property.getName(), currentMultiplicity,
                        this.minValue + ".." + this.maxValue };

                String message = MessageFormat.format(MESSAGE, arguments);
                result.getErrorList().add(new ValidationError(owner, property.getName(), message));
            }
        }
    }

    /**
     * Validates the property list against the constraint.
     * 
     * @param owner
     *            the property owner
     * @param property
     *            the property to validate
     * @param result
     *            the validation result
     */
    private void checkPropertyList(Validatable owner, NabuccoProperty property, NabuccoCollection<?> collection,
            ValidationResult result) {

        // Do not validate lazy loaded collections.
        if (collection != null) {
            if (collection.getState() == NabuccoCollectionState.LAZY) {
                return;
            }
        }

        boolean isEmpty = collection == null || collection.isEmpty();

        String ownerName = owner.getClass().getSimpleName();

        // Check for mandatory [1..*] multiplicity!
        if (!this.multiplicity.isOptional() && isEmpty) {
            Object[] arguments = new Object[] { ownerName, property.getName(), this.getMultiplicity(property),
                    this.multiplicity.getName() };

            String message = MessageFormat.format(MESSAGE, arguments);
            result.getErrorList().add(new ValidationError(owner, property.getName(), message));
        }

        if (this.multiplicity == MultiplicityType.N_TO_M) {

            Integer currentMultiplicity = this.getMultiplicity(property);

            if (currentMultiplicity < this.minValue || currentMultiplicity > this.maxValue) {
                Object[] arguments = new Object[] { ownerName, property.getName(), currentMultiplicity,
                        this.minValue + ".." + this.maxValue };

                String message = MessageFormat.format(MESSAGE, arguments);
                result.getErrorList().add(new ValidationError(owner, property.getName(), message));
            }
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
    private Integer getMultiplicity(NabuccoProperty property) {
        if (property.getPropertyType() == NabuccoPropertyType.COLLECTION) {
            return this.getMultiplicity((CollectionProperty) property);
        }
        if (property.getPropertyType() == NabuccoPropertyType.COMPONENT_RELATION) {
            return this.getMultiplicity((ComponentRelationProperty) property);
        }

        if (property.getInstance() == null) {
            return 0;
        }

        return 1;
    }

    /**
     * Creates a string representation for the current multiplicity.
     * 
     * @param property
     *            the property to resolve
     * 
     * @return the string representation
     */
    private Integer getMultiplicity(CollectionProperty property) {
        if (property.getInstance() != null) {
            return property.getInstance().size();
        }
        return 0;
    }

    /**
     * Creates a string representation for the current multiplicity.
     * 
     * @param property
     *            the property to resolve
     * 
     * @return the string representation
     */
    private Integer getMultiplicity(ComponentRelationProperty property) {
        if (property.getInstance() != null) {
            return property.getInstance().size();
        }
        return 0;
    }

    @Override
    public String format() {
        StringBuilder result = new StringBuilder();
        result.append(TYPE);
        if (this.multiplicity == MultiplicityType.N_TO_M) {
            result.append(this.minValue);
            result.append(',');
            result.append(this.maxValue);
        } else {
            result.append((this.multiplicity.isOptional()) ? 0 : 1);
            result.append(',');
            result.append((this.multiplicity.isMultiple()) ? "n" : 1);
        }
        return result.toString();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Multiplicity (");
        if (this.multiplicity != MultiplicityType.N_TO_M) {
            result.append(this.multiplicity.getName());
        } else {
            result.append(this.minValue);
            result.append("..");
            result.append(this.maxValue);
        }
        result.append(")");
        return result.toString();
    }

    @Override
    public boolean isValidRestriction(Constraint c) {
        if (c instanceof MultiplicityConstraint) {
            boolean result = true;
            result &= ((MultiplicityConstraint) c).getMinMultiplicity() >= this.getMinMultiplicity();
            result &= ((MultiplicityConstraint) c).getMaxMultiplicity() <= this.getMaxMultiplicity();
            return result;
        }
        return false;
    }
}
