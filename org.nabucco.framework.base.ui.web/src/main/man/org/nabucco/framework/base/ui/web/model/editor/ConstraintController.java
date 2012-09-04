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
package org.nabucco.framework.base.ui.web.model.editor;

import java.util.HashMap;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.validation.constraint.element.Constraint;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.EditableConstraint;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.LengthConstraint;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.MultiplicityConstraint;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.VisibilityConstraint;
import org.nabucco.framework.base.facade.datatype.validation.constraint.parser.ConstraintContainer;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.json.Jsonable;
import org.nabucco.framework.base.ui.web.model.editor.util.dependency.DependencyController;
import org.nabucco.framework.base.ui.web.model.editor.util.dependency.DependencySetAffectedConstraintType;

/**
 * ControlConstraintController
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ConstraintController implements Jsonable {

    private Map<String, Object> serverConstraints = new HashMap<String, Object>();

    private DependencyController dependencyController;

    private boolean editableConstraint;

    /**
     * 
     * Creates a new {@link ConstraintController} instance.
     * 
     * @param dependencyController
     *            the dependency controller
     * @param editableConstraint
     *            the editable constraint
     */
    public ConstraintController(DependencyController dependencyController, boolean editableConstraint) {
        if (dependencyController == null) {
            throw new IllegalArgumentException(
                    "Cannot create constrain controller because dependency controller is null");
        }

        this.dependencyController = dependencyController;
        this.editableConstraint = editableConstraint;
    }

    /**
     * 
     * Creates a new {@link ConstraintController} instance.
     * 
     * @param dependencyController
     *            the dependency controller
     */
    public ConstraintController(DependencyController dependencyController) {
        if (dependencyController == null) {
            throw new IllegalArgumentException(
                    "Cannot create constrain controller because dependency controller is null");
        }

        this.dependencyController = dependencyController;
        editableConstraint = true;
    }

    /**
     * Append server side constraints to the constraints evaluation
     * 
     * @param constainer
     *            container to be appended
     */
    public void setServerConstraints(ConstraintContainer constainer) {
        serverConstraints.clear();

        for (Constraint constraint : constainer.getConstraints()) {
            switch (constraint.getType()) {

            case EDIT: {
                EditableConstraint typedConstraint = (EditableConstraint) constraint;
                serverConstraints.put(JSON_EDITABLE, typedConstraint.isEditable());
                break;
            }

            case VISIBILITY: {
                VisibilityConstraint typedConstraint = (VisibilityConstraint) constraint;
                serverConstraints.put(JSON_VISIBLE, typedConstraint.isVisible());
                break;
            }

            case LENGTH: {
                LengthConstraint typedConstraint = (LengthConstraint) constraint;
                serverConstraints.put(JSON_MIN_LENGTH, typedConstraint.getMinLength());
                serverConstraints.put(JSON_MAX_LENGTH, typedConstraint.getMaxLength());
                break;
            }

            case MULTIPLICITY: {
                MultiplicityConstraint typedConstraint = (MultiplicityConstraint) constraint;
                serverConstraints.put(JSON_MIN_MULTIPLICITY, typedConstraint.getMinMultiplicity());
                serverConstraints.put(JSON_MAX_MULTIPLICITY, typedConstraint.getMaxMultiplicity());
                break;
            }

            default:
                break;
            }
        }
    }

    /**
     * Evaluates actually dependency controller constraints
     * 
     * @return map of the throw dependency controller changed constraint
     */
    private Map<String, Boolean> getDependencyControllerConstraints() {

        Map<String, Boolean> annotations = new HashMap<String, Boolean>();

        if (dependencyController.isActive()) {
            DependencySetAffectedConstraintType affectedConstraint = dependencyController.getAffectedConstraint();
            if (affectedConstraint == null) {
                throw new IllegalArgumentException(
                        "Cannot get annotations. The annotation constraint is not configured properly and is 'null'.");
            }

            boolean value = dependencyController.isValid();

            switch (affectedConstraint) {
            case EDITABLE: {
                annotations.put(JSON_EDITABLE, value);
                break;
            }
            case VISIBLE: {
                annotations.put(JSON_VISIBLE, value);
                break;
            }
            default: {
                throw new IllegalArgumentException(
                        "The constraint type is not supported yet. Cannot evaluate control annotataions.");
            }
            }
        }

        return annotations;
    }

    /**
     * Indicates if the control is editable according to the actual constraint state
     * 
     * @return true is editable
     */
    public boolean isEditable() {
        boolean editable = true;

        if (serverConstraints.containsKey(JSON_EDITABLE)) {
            editable = (Boolean) serverConstraints.get(JSON_EDITABLE);
        }

        Map<String, Boolean> controllerConstraints = this.getDependencyControllerConstraints();
        if (controllerConstraints.containsKey(JSON_EDITABLE)) {
            // Allow only restriction
            if (editable == true) {
                editable = controllerConstraints.get(JSON_EDITABLE);
            }
        }

        if (editable == true) {
            editable = editableConstraint;
        }

        return editable;
    }

    /**
     * Indicates if the control is visible
     * 
     * @return true if visible
     */
    public boolean isVisible() {
        boolean visible = true;

        if (serverConstraints.containsKey(JSON_VISIBLE)) {
            visible = (Boolean) serverConstraints.get(JSON_VISIBLE);
        }

        Map<String, Boolean> controllerConstraints = this.getDependencyControllerConstraints();
        if (controllerConstraints.containsKey(JSON_VISIBLE)) {
            // Allow only restriction
            if (visible == true) {
                visible = controllerConstraints.get(JSON_VISIBLE);
            }
        }

        return visible;

    }

    /**
     * Returns the map with actual constraints
     * 
     * @return the map with actual constraints
     */
    public Map<String, Object> getActuallConstraints() {
        Map<String, Object> retVal = new HashMap<String, Object>();

        if (serverConstraints.containsKey(JSON_MIN_LENGTH)) {
            retVal.put(JSON_MIN_LENGTH, serverConstraints.get(JSON_MIN_LENGTH));
        }
        if (serverConstraints.containsKey(JSON_MAX_LENGTH)) {
            retVal.put(JSON_MAX_LENGTH, serverConstraints.get(JSON_MAX_LENGTH));
        }
        if (serverConstraints.containsKey(JSON_MIN_MULTIPLICITY)) {
            retVal.put(JSON_MIN_MULTIPLICITY, serverConstraints.get(JSON_MIN_MULTIPLICITY));
        }
        if (serverConstraints.containsKey(JSON_MAX_MULTIPLICITY)) {
            retVal.put(JSON_MAX_MULTIPLICITY, serverConstraints.get(JSON_MAX_MULTIPLICITY));
        }

        boolean visible = this.isVisible();
        retVal.put(JSON_VISIBLE, visible);
        if (!visible) {
            return retVal;
        }
        boolean editable = this.isEditable();
        retVal.put(JSON_EDITABLE, editable);
        if (!editable) {
            return retVal;
        }

        return retVal;
    }

    @Override
    public JsonElement toJson() {
        JsonMap retVal = new JsonMap();

        Map<String, Object> actuallConstraints = this.getActuallConstraints();

        for (String annotation : actuallConstraints.keySet()) {
            retVal.add(annotation, actuallConstraints.get(annotation));
        }

        return retVal;
    }
}
