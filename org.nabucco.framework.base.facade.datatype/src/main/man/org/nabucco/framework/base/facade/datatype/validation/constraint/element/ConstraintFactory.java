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

/**
 * ConstraintFactory
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ConstraintFactory {

    /**
     * Singleton instance.
     */
    private static ConstraintFactory instance = new ConstraintFactory();

    /**
     * Private constructor.
     */
    private ConstraintFactory() {
    }

    /**
     * Singleton access.
     * 
     * @return the ConstraintFactory instance.
     */
    public static ConstraintFactory getInstance() {
        return instance;
    }

    /**
     * Retrieves the constraint for the given constraint string.
     * 
     * @param constraint
     *            the constraint string
     * 
     * @return the appropriate constraint
     */
    public Constraint getConstraint(String constraint) {
        if (constraint == null || constraint.length() == 0) {
            throw new IllegalArgumentException("Constraint '" + String.valueOf(constraint) + "' is not valid.");
        }

        char type = constraint.charAt(0);

        switch (type) {
        case EditableConstraint.TYPE:
            return new EditableConstraint(constraint);
        case LengthConstraint.TYPE:
            return new LengthConstraint(constraint);
        case MultiplicityConstraint.TYPE:
            return new MultiplicityConstraint(constraint);
        case PatternConstraint.TYPE:
            return new PatternConstraint(constraint);
        case VisibilityConstraint.TYPE:
            return new VisibilityConstraint(constraint);
        case ValueConstraint.TYPE:
            return new ValueConstraint(constraint);
        }

        throw new IllegalArgumentException("Constraint [" + String.valueOf(constraint) + "] is not supported.");
    }

    /**
     * Create a new {@link EditableConstraint} with an appropraite editable flag indicating whether
     * a property is editable or not.
     * 
     * @param isEditable
     *            the editable flag indicating whether a property is editable or not
     * 
     * @return the edit constraint
     */
    public Constraint createEditableConstraint(boolean isEditable) {
        return new EditableConstraint(isEditable);
    }

    /**
     * Create a new {@link LengthConstraint} with appropriate min and max length.
     * 
     * @param minLength
     *            the min length
     * @param maxLength
     *            the max length
     * 
     * @return the length constraint
     */
    public Constraint createLengthConstraint(int minLength, int maxLength) {
        if (minLength < 0) {
            throw new IllegalArgumentException("Cannot create LengthConstraint for min length smaller 0.");
        }
        if (maxLength < minLength) {
            throw new IllegalArgumentException("Cannot create LengthConstraint for max length smaller min length.");
        }
        return new LengthConstraint(minLength, maxLength);
    }

    /**
     * Create a new {@link ValueConstraint} with appropriate min and max length.
     * 
     * @param minValue
     *            the min value
     * @param maxValue
     *            the max value
     * 
     * @return the value constraint
     */
    public Constraint createValueConstraint(long minValue, long maxValue) {
        if (minValue < 0) {
            throw new IllegalArgumentException("Cannot create ValueConstraint for min value smaller 0.");
        }
        if (maxValue < minValue) {
            throw new IllegalArgumentException("Cannot create ValueConstraint for max value smaller min value.");
        }
        return new ValueConstraint(minValue, maxValue);
    }

    /**
     * Create a new {@link MultiplicityConstraint} with appropriate min and max values.
     * 
     * @param minMultiplicity
     *            the minimum multiplicity
     * @param maxMultiplicity
     *            the maximum multiplicity
     * 
     * @return the multiplicity constarint
     */
    public Constraint createMultiplicityConstraint(int minMultiplicity, int maxMultiplicity) {
        if (minMultiplicity < 0) {
            throw new IllegalArgumentException("Cannot create MultiplicityConstraint for min multiplicity smaller 0.");
        }
        if (maxMultiplicity < minMultiplicity) {
            throw new IllegalArgumentException(
                    "Cannot create MultiplicityConstraint for max multiplicity smaller min multiplicity.");
        }

        return new MultiplicityConstraint(minMultiplicity, maxMultiplicity);
    }

    /**
     * Create a new PatternConstraint with appropriate regular expression.
     * 
     * @param pattern
     *            the pattern to compile
     * 
     * @return the pattern constraint
     */
    public Constraint createPatternConstraint(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException("Cannot create PatternConstraint for pattern pattern '" + pattern + "'.");
        }
        return new PatternConstraint(pattern);
    }

    /**
     * Create a new {@link VisibilityConstraint} with appropriate visibility type.
     * 
     * @param visibilityType
     *            the visibility type
     * 
     * @return the visibility constraint
     */
    public Constraint createVisibilityConstraint(VisibilityType visibilityType) {
        if (visibilityType == null) {
            throw new IllegalArgumentException("Cannot create VisibilityConstraint for type [null].");
        }
        return new VisibilityConstraint(visibilityType);
    }

}
