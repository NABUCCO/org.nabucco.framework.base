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
package org.nabucco.framework.base.facade.datatype.validation.constraint.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.validation.Validatable;
import org.nabucco.framework.base.facade.datatype.validation.ValidationResult;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.Constraint;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.ConstraintType;

/**
 * ConstraintContainer
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ConstraintContainer {

    /** All constraints mapped by the related fields index. */
    private Map<Integer, List<Constraint>> constraintMap = new HashMap<Integer, List<Constraint>>();

    /**
     * Creates a new {@link ConstraintContainer} instance.
     */
    ConstraintContainer() {
    }

    /**
     * Checks whether the constraint container contains values or not.
     * 
     * @return <b>true</b> if the container is empty, <b>false</b> if not
     */
    public boolean isEmpty() {
        return this.constraintMap.isEmpty();
    }

    /**
     * Checks the given property against the constraint container.
     * 
     * @param owner
     *            owner of the property
     * @param property
     *            the property to validate
     * @param index
     *            index of the property
     * @param result
     *            the validation result containing validation errors
     */
    public void check(Validatable owner, Object property, Integer index, ValidationResult result) {
        List<Constraint> constraintList = this.getConstraints(index);

        if (property instanceof Basetype) {
            this.addBasetypeConstraints((Basetype) property, constraintList);
        }

        // Each constraint type must only be visited once.
        Set<ConstraintType> validated = new HashSet<ConstraintType>();
        for (Constraint constraint : constraintList) {
            if (validated.add(constraint.getType())) {
                String propertyName = this.getPropertyName(owner, index);
                constraint.check(owner, property, propertyName, result);
            }
        }
    }

    /**
     * Getter for the list of constraints for the given field index.
     * 
     * @param index
     *            index of the field
     * 
     * @return the constraints for the fields index
     */
    private List<Constraint> getConstraints(Integer index) {
        List<Constraint> constraints = new ArrayList<Constraint>();
        List<Constraint> value = this.constraintMap.get(index);
        if (value != null && !value.isEmpty()) {
            constraints.addAll(value);
        }
        return constraints;
    }

    /**
     * Extracts constraints of a {@link Basetype}.
     * 
     * @param basetype
     *            the property
     * @param constraintList
     *            the list to add the constraints
     * 
     * @return the extracted basetype constraints
     */
    private void addBasetypeConstraints(Basetype basetype, List<Constraint> constraintList) {
        ConstraintContainer container = ConstraintParser.getInstance().parseConstraint(basetype);
        List<Constraint> basetypeConstraints = container.getConstraints(0);

        List<Constraint> newConstraints = new ArrayList<Constraint>();

        for (Constraint basetypeConstraint : basetypeConstraints) {

            boolean contains = false;
            for (Constraint constraint : constraintList) {
                if (basetypeConstraint.getType() == constraint.getType()) {
                    contains = true;
                }
            }

            if (!contains) {
                newConstraints.add(basetypeConstraint);
            }
        }

        constraintList.addAll(newConstraints);
    }

    /**
     * Extracts the name of the property to validate.
     * 
     * @param owner
     *            owner of the validation
     * @param index
     *            index of the property
     * 
     * @return the name of the property
     */
    private String getPropertyName(Validatable owner, Integer index) {
        String propertyName;
        if (owner == null) {
            propertyName = "undefined";
        } else {
            propertyName = owner.getProperties().get(index).getName();
        }
        return propertyName;
    }

    /**
     * Adds a constraint to the given index.
     * 
     * @param index
     *            the field index
     * @param constraintList
     *            the list of constraints for the given field
     */
    public void put(Integer index, List<Constraint> constraintList) {
        this.constraintMap.put(index, constraintList);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Constraints:\n");
        for (int i = 0; i < this.constraintMap.size(); i++) {
            List<Constraint> constraintList = this.constraintMap.get(i);

            result.append(i);
            result.append(":");

            if (constraintList == null || constraintList.isEmpty()) {
                result.append("\t- None.\n");
                continue;
            }

            for (Constraint constraint : constraintList) {
                result.append("\t- ");
                result.append(constraint);
                result.append("\n");
            }
        }
        return result.toString();
    }

}
