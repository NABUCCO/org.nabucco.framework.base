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
package org.nabucco.framework.base.facade.datatype.validation.constraint;

import java.util.Map;

import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.Constraint;
import org.nabucco.framework.base.facade.datatype.validation.constraint.parser.ConstraintContainer;
import org.nabucco.framework.base.facade.datatype.validation.constraint.parser.ConstraintParser;

/**
 * ConstraintFacade
 * <p/>
 * Facade for dynamic constraint operations. Constraints may be added to and removed from
 * PropertyOwners and their properties.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ConstraintFacade {

    /** Index of the parent element. */
    public static final int ROOT_INDEX = -1;

    /**
     * Singleton instance.
     */
    private static ConstraintFacade instance = new ConstraintFacade();

    /**
     * Private constructor.
     */
    private ConstraintFacade() {
    }

    /**
     * Singleton access.
     * 
     * @return the ConstraintFacade instance.
     */
    public static ConstraintFacade getInstance() {
        return instance;
    }

    /**
     * Adds a dynamic constraint and format the existing constraint string.
     * 
     * @param constraint
     *            the constraint to add
     * @param constraints
     *            the existing constraint string to format
     * 
     * @return the formatted string
     */
    public String addConstraint(Constraint constraint, String constraints) {
        if (constraint == null) {
            throw new IllegalArgumentException("Cannot add constraint [null].");
        }

        // TODO: Validate static constraints restriction!

        return this.format(constraint, constraints, ROOT_INDEX);
    }

    /**
     * Adds a dynamic constraint to the given property and format the existing constraint string.
     * 
     * @param property
     *            the property to add the constraint
     * @param constraint
     *            the constraint to add
     * @param constraints
     *            the existing constraint string to format
     * 
     * @return the formatted string
     */
    public String addConstraint(NabuccoPropertyDescriptor property, Constraint constraint, String constraints) {
        if (property == null) {
            throw new IllegalArgumentException("Cannot add constraint [" + property + "] to property [null].");
        }
        if (constraint == null) {
            throw new IllegalArgumentException("Cannot add constraint [null] to property [" + property + "]");
        }

        this.validateRestriction(constraint, property.getConstraints());

        return this.format(constraint, constraints, property.getIndex());
    }

    /**
     * Format the constraint string for the given index.
     * 
     * @param constraint
     *            the constraint to add
     * @param constraints
     *            the existing constraints
     * @param index
     *            the index
     * 
     * @return the formatted string
     */
    private String format(Constraint constraint, String constraints, int index) {

        StringBuilder result = new StringBuilder();

        if (constraints != null) {
            Map<Integer, ConstraintContainer> existingConstraints = ConstraintParser.getInstance()
                    .parseConstraintsWithIndex(constraints);

            ConstraintContainer container = existingConstraints.get(index);

            if (container == null) {
                container = ConstraintContainer.emptyContainer();
                container.addAll(ConstraintParser.getInstance().parseConstraints(constraint.format()));
                existingConstraints.put(index, container);
            } else {
                container.add(constraint);
            }

            for (Integer key : existingConstraints.keySet()) {
                result.append(key);
                result.append(existingConstraints.get(key).format());
                result.append(" ");
            }

        } else {
            result.append(index);
            result.append(constraint.format());
        }

        return result.toString();
    }

    /**
     * Validates restricion. Checks if the restriction of dynamic constraint doesnt make unpermitted
     * allowing on static restriction
     * 
     * @param checkingConstraint
     *            dynamic constrain to be checked
     * @param constrainsContainer
     *            the existing constraint container
     */
    private void validateRestriction(Constraint checkingConstraint, ConstraintContainer constrainsContainer) {
        for (Constraint staticConstraint : constrainsContainer.getConstraints()) {
            if (staticConstraint.getType() == checkingConstraint.getType()) {
                if (!staticConstraint.isValidRestriction(checkingConstraint)) {
                    throw new IllegalArgumentException(
                            "Given constraint is not a valid restriction of existing static constraint.");
                }
            }
        }
    }

    /**
     * Remove a dynamic constraint from the given property and format the existing constraint
     * string.
     * 
     * @param property
     *            the property to remove the constraint
     * @param constraint
     *            the constraint to remove
     * @param constraints
     *            the existing constraint string to format
     * 
     * @return the formatted string
     */
    public String removeConstraint(NabuccoPropertyDescriptor property, Constraint constraint, String constraints) {
        if (property == null) {
            throw new IllegalArgumentException("Cannot add constraint [" + property + "] to property [null].");
        }
        if (constraint == null) {
            throw new IllegalArgumentException("Cannot add constraint [null] to property [" + property + "]");
        }

        return null;
    }

    /**
     * Remove a dynamic constraint and format the existing constraint string.
     * 
     * @param constraint
     *            the constraint to remove
     * @param constraints
     *            the existing constraint string to format
     * 
     * @return the formatted string
     */
    public String removeConstraint(Constraint constraint, String constraints) {
        if (constraint == null) {
            throw new IllegalArgumentException("Cannot add constraint [null].");
        }

        return null;
    }

    /**
     * Checks whether a constraint holds an editable constraint and whether it is editable or not.
     * 
     * @param dynamicConstraints
     *            the dynamic constraints to check
     * 
     * @return <b>true</b> if the property is editable, <b>false</b> if not
     */
    public boolean isEditable(String dynamicConstraints) {
        ConstraintContainer constraints = ConstraintParser.getInstance().parseConstraintsWithIndex(dynamicConstraints)
                .get(ROOT_INDEX);
        if (constraints != null) {
            return constraints.isEditable();
        }
        return true;
    }

    /**
     * Checks whether a constraint string holds an visibility constraint and whether it is visible
     * or not.
     * 
     * @param dynamicConstraints
     *            the dynamic constraints to check
     * 
     * @return <b>true</b> if the property is visible, <b>false</b> if not
     */
    public boolean isVisible(String dynamicConstraints) {
        ConstraintContainer constraints = ConstraintParser.getInstance().parseConstraintsWithIndex(dynamicConstraints)
                .get(ROOT_INDEX);
        if (constraints != null) {
            return constraints.isVisible();
        }
        return true;
    }
}
