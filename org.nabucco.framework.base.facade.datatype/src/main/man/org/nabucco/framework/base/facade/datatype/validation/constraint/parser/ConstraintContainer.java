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
package org.nabucco.framework.base.facade.datatype.validation.constraint.parser;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.validation.Validatable;
import org.nabucco.framework.base.facade.datatype.validation.ValidationResult;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.Constraint;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.ConstraintType;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.EditableConstraint;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.MultiplicityConstraint;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.VisibilityConstraint;

/**
 * ConstraintContainer
 * <p/>
 * Container for all constraints of a single property.
 * 
 * @see ConstraintParser
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ConstraintContainer implements Iterable<Constraint> {

    /** All static constraints mapped by the related fields index. */
    private final Set<Constraint> staticConstraints;

    /** All dynamic constraints mapped by the related fields index. */
    private transient final Set<Constraint> dynamicConstraints;

    /**
     * Creates a new {@link ConstraintContainer} instance.
     */
    ConstraintContainer() {
        this(null);
    }

    /**
     * Creates a new {@link ConstraintContainer} instance.
     * 
     * @param constraints
     *            the list of constraints
     */
    ConstraintContainer(Set<Constraint> constraints) {
        if (constraints != null) {
            this.staticConstraints = Collections.unmodifiableSet(constraints);
        } else {
            this.staticConstraints = Collections.emptySet();
        }

        this.dynamicConstraints = new HashSet<Constraint>();
    }

    /**
     * Add a constraint to the constraint container.
     * 
     * @param constraint
     *            the constraint to add
     */
    public void add(Constraint constraint) {
        if (constraint != null) {
            this.dynamicConstraints.add(constraint);
        }
    }

    /**
     * Add a list of constraints to the constraint container.
     * 
     * @param constraintContainer
     *            the constraints to add
     */
    public void addAll(ConstraintContainer constraintContainer) {
        if (constraintContainer != null) {
            this.dynamicConstraints.addAll(constraintContainer.getConstraints());
        }
    }

    /**
     * Checks the given property against the constraint container.
     * 
     * @param owner
     *            owner of the property
     * @param property
     *            the property to validate
     * @param result
     *            the validation result containing validation errors
     */
    public void check(Validatable owner, NabuccoProperty property, ValidationResult result) {
        Set<Constraint> constraintList = this.getConstraints();

        // Each constraint type must only be visited once.
        Set<ConstraintType> validated = new HashSet<ConstraintType>();
        for (Constraint constraint : constraintList) {
            if (validated.add(constraint.getType())) {
                constraint.check(owner, property, result);
            }
        }
    }

    /**
     * Getter for the list of constraints (static and dynamic).
     * 
     * @return the constraints for the fields index
     */
    public Set<Constraint> getConstraints() {
        int initialCapacity = this.staticConstraints.size() + this.dynamicConstraints.size();
        Set<Constraint> constraints = new HashSet<Constraint>(initialCapacity);

        constraints.addAll(this.dynamicConstraints);
        constraints.addAll(this.staticConstraints);

        return constraints;
    }

    /**
     * Checks whether the constraint container contains values or not.
     * 
     * @return <b>true</b> if the container is empty, <b>false</b> if not
     */
    public boolean isEmpty() {
        return this.staticConstraints.isEmpty() && this.dynamicConstraints.isEmpty();
    }

    /**
     * Checks whether a constraint container holds an editable constraint and whether it is editable
     * or not.
     * 
     * @return <b>true</b> if the property is editable, <b>false</b> if not
     */
    public boolean isEditable() {
        for (Constraint constraint : this.getConstraints()) {
            if (constraint.getType() == ConstraintType.EDIT) {
                return ((EditableConstraint) constraint).isEditable();
            }
        }
        return true;
    }

    /**
     * Returns the minimum multiplicity constraint
     * 
     * 
     * @return multiplicity or 0 is not defined
     */
    public int getMinMultiplicity() {
        for (Constraint constraint : this.getConstraints()) {
            if (constraint.getType() == ConstraintType.MULTIPLICITY) {
                return ((MultiplicityConstraint) constraint).getMinMultiplicity();
            }
        }

        return MultiplicityConstraint.MIN_VALUE;
    }

    /**
     * Returns the maximum multiplicity constraint
     * 
     * 
     * @return multiplicity or MAX_VALUE is not defined
     */
    public int getMaxMultiplicity() {
        for (Constraint constraint : this.getConstraints()) {
            if (constraint.getType() == ConstraintType.MULTIPLICITY) {
                return ((MultiplicityConstraint) constraint).getMaxMultiplicity();
            }
        }

        return MultiplicityConstraint.MAX_VALUE;
    }

    /**
     * Checks whether a constraint container holds an visibility constraint and whether it is
     * visible or not.
     * 
     * @return <b>true</b> if the property is visible, <b>false</b> if not
     */
    public boolean isVisible() {
        for (Constraint constraint : this.getConstraints()) {
            if (constraint.getType() == ConstraintType.VISIBILITY) {
                return ((VisibilityConstraint) constraint).isVisible();
            }
        }
        return true;
    }

    /**
     * Format the dynamic constraint to a parsable string representation.
     * 
     * @return the constraint container as string
     */
    public String format() {
        StringBuilder result = new StringBuilder();

        for (Iterator<Constraint> iterator = this.dynamicConstraints.iterator(); iterator.hasNext();) {
            Constraint constraint = iterator.next();
            result.append(constraint.format());
            if (iterator.hasNext()) {
                result.append(ConstraintParser.CONSTRAINT_SEPARATOR);
            }
        }

        return result.toString();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Static Constraints:\n");

        if (this.staticConstraints.isEmpty()) {
            result.append("\t- None.\n");
        } else {
            for (Constraint constraint : this.staticConstraints) {
                result.append("\t- ");
                result.append(constraint);
                result.append("\n");
            }
        }

        result.append("\nDynamic Constraints:\n");

        if (this.dynamicConstraints.isEmpty()) {
            result.append("\t- None.\n");
        } else {
            for (Constraint constraint : this.dynamicConstraints) {
                result.append("\t- ");
                result.append(constraint);
                result.append("\n");
            }
        }

        return result.toString();
    }

    @Override
    public Iterator<Constraint> iterator() {
        return this.getConstraints().iterator();
    }

    /**
     * Clears the dynamic constraints.
     */
    void clear() {
        this.dynamicConstraints.clear();
    }

    /**
     * Creates an empty constraint container.
     * 
     * @return the empty container.
     */
    public static ConstraintContainer emptyContainer() {
        return new ConstraintContainer();
    }

}
