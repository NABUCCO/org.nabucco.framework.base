/*
 * Copyright 2012 PRODYNA AG
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
package org.nabucco.framework.base.ui.web.model.editor;

import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.WebModel;
import org.nabucco.framework.base.ui.web.model.editor.util.dependency.DependencyController;
import org.nabucco.framework.base.ui.web.model.work.EditorModel;

/**
 * GridElementModel
 * 
 * This model controls properties of elements in a grid. It handles the dependency changes etc
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public abstract class EditorGridElementModel extends WebModel {

    private static final String JSON_CONSTRAINTS = "constraints";

    /** The id of the element */
    private String id;

    /** The dependency set */
    private DependencyController dependencyController;

    private ConstraintController constraintController;

    /** Indicates that the model was already initialized **/
    private boolean initialized;

    private EditorModel editorModel;

    /** Indicates that the constraints have been changed according to dependencies */
    private boolean refreshNeeded = false;

    /**
     * 
     * Creates a new {@link EditorGridElementModel} instance.
     * 
     * @param id
     *            the id of the grid element
     * @param dependencyController
     *            the dependency controller to use
     */
    public EditorGridElementModel(String id, DependencyController dependencyController) {
        this(id, dependencyController, true);
    }

    /**
     * 
     * Creates a new {@link EditorGridElementModel} instance.
     * 
     * @param id
     *            the id of the grid element
     * @param dependencyController
     *            the dependency controller to use
     * @param editable
     *            set editable constraint to the concrete value
     */
    public EditorGridElementModel(String id, DependencyController dependencyController, boolean editable) {
        if (dependencyController == null) {
            throw new IllegalArgumentException("Cannot create control model with dependency set [null].");
        }
        if (id == null) {
            throw new IllegalArgumentException("Cannot create control model with id [null].");
        }

        this.id = id;
        this.dependencyController = dependencyController;

        constraintController = new ConstraintController(dependencyController, editable);
    }

    @Override
    public void init() {
        // notify if the constraint status has changed
        dependencyController.addPropertyChangeListener(this);
    }

    /**
     * Getter for the id.
     * 
     * @return Returns the id.
     */
    public final String getId() {
        return id;
    }

    public final void setEditorModel(EditorModel model) {
        if (editorModel == null) {
            editorModel = model;
        } else {
            throw new IllegalStateException("The Editor model is already connected");
        }
    }

    /**
     * Getter for the editor model
     * 
     * @return editor model
     */
    protected final EditorModel getEditorModel() {
        return editorModel;
    }

    /**
     * Setter for the initialized.
     * 
     * @param initialized
     *            The initialized to set.
     */
    public final void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    @Override
    public final boolean isInitialized() {
        return initialized;
    }

    /**
     * Getter for the dependencyController.
     * 
     * @return Returns the dependencyController.
     */
    public final DependencyController getDependencyController() {
        return dependencyController;
    }

    /**
     * Getter for the constraintController.
     * 
     * @return Returns the constraintController.
     */
    protected final ConstraintController getConstraintController() {
        return constraintController;
    }

    /**
     * Returns the visiblility constraint value
     * 
     * @return true if visible
     */
    public final boolean isVisible() {
        return constraintController.isVisible();
    }

    /**
     * Returns if the control is editable
     * 
     * @return true if editable
     */
    public final boolean isEditable() {
        return constraintController.isEditable();
    }

    /**
     * Getter for the refreshNeeded.
     * 
     * @return Returns the refreshNeeded.
     */
    public boolean isRefreshNeeded() {
        return refreshNeeded;
    }

    /**
     * Getter for the refreshNeeded.
     * 
     * @return Returns the refreshNeeded.
     */
    protected void setRefreshNeeded(boolean value) {
        refreshNeeded = value;
    }

    @Override
    public JsonMap toJson() {
        refreshNeeded = false;
        this.setInitialized(true);

        JsonMap jsonMap = new JsonMap();

        jsonMap.add(JSON_CONSTRAINTS, constraintController.toJson());

        return jsonMap;
    }
}
