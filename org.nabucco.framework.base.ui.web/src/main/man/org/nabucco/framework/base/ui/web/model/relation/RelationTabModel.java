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
package org.nabucco.framework.base.ui.web.model.relation;

import java.beans.PropertyChangeEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.validation.Validatable;
import org.nabucco.framework.base.facade.datatype.validation.ValidationError;
import org.nabucco.framework.base.facade.datatype.validation.ValidationResult;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.Constraint;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.ConstraintType;
import org.nabucco.framework.base.ui.web.component.messageset.UiMessageSet;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.WebModel;
import org.nabucco.framework.base.ui.web.model.editor.ConstraintController;
import org.nabucco.framework.base.ui.web.model.editor.util.dependency.DependencyController;
import org.nabucco.framework.base.ui.web.model.table.TableModel;

/**
 * RelationTabModel
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class RelationTabModel extends WebModel {

    private static final String JSON_CONSTRAINTS = "constraints";

    private static final String JSON_ERRORS = "errors";

    private static final String JSON_TABLE = "table";

    private static final String JSON_IS_LAZY = "isLazy";

    private boolean isValid;

    private String id;

    /** Indicates that the constraints have been changed according to dependencies */
    private boolean refreshNeeded = false;

    private NabuccoProperty property;

    private TableModel<Datatype> tableModel;

    private DependencyController dependencyController;

    private ConstraintController constraintController;

    private String selectedValue = null;

    private String propertyPath;

    /** Defines if the validation messages should be sent to the UI */
    private boolean validating = false;

    /** Defines that the table model is lazy loaded and refreshes itself only by clicking on it */
    private boolean lazy;

    /**
     * Creates a new {@link RelationTabModel} instance.
     * 
     * @param id
     *            the relation tab id
     * @param propertyPath
     *            the property path
     * @param dependencyController
     *            the relation tab dependency controller
     * @param tableModel
     *            the table model of the relation tab
     * @param lazy
     *            relation tab is lazy loaded
     */
    public RelationTabModel(String id, String propertyPath, DependencyController dependencyController,
            TableModel<Datatype> tableModel, boolean lazy) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot create relation tab model for id 'null'.");
        }

        if (tableModel == null) {
            throw new IllegalArgumentException("Cannot create relation tab model for tableModel 'null'.");
        }

        this.id = id;
        this.tableModel = tableModel;

        this.dependencyController = dependencyController;

        this.constraintController = new ConstraintController(dependencyController);

        this.propertyPath = propertyPath;
        this.lazy = lazy;
    }

    @Override
    public void init() {
        this.tableModel.init();
        this.dependencyController.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        this.refreshNeeded = true;
    }

    /**
     * Returns true if the model is lazy loaded and the binding is made <b> manually </b>. If lazy,
     * the model will be not filled and updated by the standard binding mechanism.
     * 
     * @return
     */
    public boolean isLazy() {
        return lazy;
    }

    /**
     * Triggers relation validation
     * 
     * @return validation result
     */
    public Set<String> validate() {

        Set<String> retVal = new HashSet<String>();

        if (this.property != null) {
            // Validate configured constraints
            if (retVal.isEmpty() && this.property.getParent() instanceof Validatable) {
                Validatable owner = (Validatable) this.property.getParent();
                UiMessageSet messageSet = UiMessageSet.getInstance();

                Set<ConstraintType> validated = new HashSet<ConstraintType>();
                for (Constraint constraint : this.getProperty().getConstraints()) {
                    if (validated.add(constraint.getType())) {
                        ValidationResult result = new ValidationResult();
                        constraint.check(owner, this.property, result);

                        if (!result.isEmpty()) {
                            ConstraintType type = constraint.getType();
                            ValidationError validationError = result.getErrorList().get(0);
                            String originalMessage = validationError.getMessage();
                            String errorText = messageSet.resolveText(null, this.getId(), type, originalMessage);
                            retVal.add(errorText);
                        }
                    }
                }
            }

        }

        this.setValid(retVal.isEmpty());

        return retVal;

    }

    /**
     * Getter for the table model.
     * 
     * @return Returns the table model.
     */
    public TableModel<Datatype> getTableModel() {
        return this.tableModel;
    }

    /**
     * Getter for the property.
     * 
     * @return Returns the property.
     */
    public NabuccoProperty getProperty() {
        return this.property;
    }

    /**
     * Getter for the property path.
     * 
     * @return Returns the property path.
     */
    public String getPropertyPath() {
        return this.propertyPath;
    }

    /**
     * Set a new property into the model and the parent type (when a property already exists).
     * 
     * @param property
     *            the new property
     */
    public void setProperty(NabuccoProperty property) {
        this.property = property;

        this.tableModel.setPropertyName(property.getName());
        this.constraintController.setServerConstraints(property.getConstraints());
    }

    /**
     * Setter for the selectedValue.
     * 
     * @param selectedValue
     *            The selectedValue to set.
     */
    public void setValue(String selectedValue) {
        this.setValidating(true);

        boolean before = this.hasSelectedValue();
        this.selectedValue = selectedValue;
        boolean after = this.hasSelectedValue();

        if (before != after) {
            this.refreshNeeded = true;
        }

    }

    /**
     * Getter for the selectedValue.
     * 
     * @return Returns the selectedValue.
     */
    private String getValue() {
        return this.selectedValue;
    }

    /**
     * Returns true if any value is selected
     * 
     * @return true if selected, false if not
     */
    public boolean hasSelectedValue() {
        return this.getValue() != null;
    }

    /**
     * Returns tru if the contraints have changed and the refresh or relation tab is needed
     * 
     * @return
     */
    public boolean isRefreshNeeded() {
        return this.refreshNeeded;
    }

    /**
     * Getter for the id.
     * 
     * @return Returns the id.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Sets content of the table
     * 
     * @param values
     */
    public void setContent(List<Datatype> values) {
        this.getTableModel().setContent(values);
    }

    /**
     * Getter for the dependencyController.
     * 
     * @return Returns the dependencyController.
     */
    public DependencyController getDependencyController() {
        return this.dependencyController;
    }

    /**
     * Returns the visiblility constraint value
     * 
     * @return true if visible
     */
    public boolean isVisible() {
        return this.constraintController.isVisible();
    }

    /**
     * Returns if the control is editable
     * 
     * @return true if editable
     */
    public boolean isEditable() {
        return this.constraintController.isEditable();
    }

    /**
     * Setter for the isValid.
     * 
     * @param isValid
     *            The isValid to set.
     */
    private void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    /**
     * Getter for the isValid.
     * 
     * @return Returns the isValid.
     */
    private boolean isValid() {
        return this.isValid;
    }

    /**
     * Setter for the validating.
     * 
     * @param validating
     *            The validating to set.
     */
    public void setValidating(boolean validating) {
        this.validating = validating;
    }

    /**
     * Getter for the validating.
     * 
     * @return Returns the validating.
     */
    public boolean isValidating() {
        return this.validating;
    }

    @Override
    public JsonElement toJson() {
        JsonMap retVal = new JsonMap();

        JsonMap table = (JsonMap) this.tableModel.toJson();
        retVal.add(JSON_TABLE, table);

        retVal.add(JSON_CONSTRAINTS, this.constraintController.toJson());

        /** Selected value (index?) */
        retVal.add(JSON_VALUE, this.getValue());
        retVal.add(JSON_IS_LAZY, this.isLazy());

        if (this.isValidating()) {
            Set<String> validationErrors = this.validate();
            if (!this.isValid()) {
                JsonList errors = new JsonList();
                for (String error : validationErrors) {
                    errors.add(error);
                }
                retVal.add(JSON_ERRORS, errors);
            }
            retVal.add(JSON_VALID, this.isValid());
        }

        this.refreshNeeded = false;

        return retVal;
    }

}
