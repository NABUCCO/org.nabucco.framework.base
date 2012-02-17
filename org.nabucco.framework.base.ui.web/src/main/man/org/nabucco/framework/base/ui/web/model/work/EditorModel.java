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
package org.nabucco.framework.base.ui.web.model.work;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyResolver;
import org.nabucco.framework.base.facade.datatype.property.PropertyOwner;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.browser.BindableBrowserEntry;
import org.nabucco.framework.base.ui.web.model.control.ControlModel;
import org.nabucco.framework.base.ui.web.model.control.util.dependency.DependencyController;
import org.nabucco.framework.base.ui.web.model.relation.RelationTabModel;
import org.nabucco.framework.base.ui.web.model.work.workflow.WorkflowModel;

/**
 * Model for editor work items holding a single datatype.
 * 
 * @param <D>
 *            the wrapped datatype
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class EditorModel extends WorkItemModel {

    public static final String PROPERTY_DATATYPE = "datatype";

    /** The represented datatype. */
    private Datatype datatype;

    /** List with bound browser entries (one bound property -> one entry) */
    private List<BindableBrowserEntry> browserEntryList = new ArrayList<BindableBrowserEntry>();

    /** Relation Tab Table Models by their properties */
    private Map<String, RelationTabModel> relationTables = new HashMap<String, RelationTabModel>();

    /** Control Models by their properties. */
    private Map<String, ControlModel<?>> controlMap = new HashMap<String, ControlModel<?>>();

    /** Workflow Model */
    private WorkflowModel workflowModel;

    /** Logger */
    protected static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(EditorModel.class);

    /** Indicates if the model changes its state so the ui refresh is needed */
    private boolean modelRefreshNeeded = false;

    /**
     * Creates a new {@link EditorModel} instance.
     * 
     * @param id
     *            the editor id
     * @param label
     *            the editor label
     */
    public EditorModel(String label) {
        this(label, null, null);
    }

    /**
     * Creates a new {@link EditorModel} instance.
     * 
     * @param label
     *            the editor label
     * @param tooltip
     *            the editor tooltip
     */
    public EditorModel(String label, String tooltip) {
        this(label, tooltip, null);
    }

    /**
     * Creates a new {@link EditorModel} instance.
     * 
     * @param label
     *            the editor label
     * @param tooltip
     *            the editor tooltip
     * @param image
     *            the editor image
     */
    public EditorModel(String label, String tooltip, String image) {
        super(label, tooltip, image);
    }

    /**
     * Add a relation table model to the editor model.
     * 
     * @param property
     *            the property of the table model
     * @param relationTableModel
     *            the relation table model
     */
    public void addRelationTable(String property, RelationTabModel relationTableModel) {
        if (property == null) {
            throw new IllegalArgumentException("Cannot add relation table model for property [null].");
        }
        if (relationTableModel == null) {
            throw new IllegalArgumentException("Cannot add relation table model [null].");
        }
        this.relationTables.put(property, relationTableModel);

        relationTableModel.getTableModel().setPropertyName(property);
        relationTableModel.getTableModel().addPropertyChangeListener(property, this);
    }

    /**
     * Adds a new browser entry to the list
     * 
     * @param entry
     *            the browser entry to add
     */
    public void addBrowserEntry(BindableBrowserEntry entry) {
        if (entry == null) {
            throw new IllegalArgumentException("Cannot add browser entry because the entry is [null].");
        }
        this.browserEntryList.add(entry);
    }

    @Override
    public boolean isInitialized() {
        for (ControlModel<?> controlModel : this.controlMap.values()) {
            if (!controlModel.isInitialized()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Getter for the datatype.
     * 
     * @return Returns the datatype.
     */
    public Datatype getDatatype() {
        return this.datatype;
    }

    /**
     * Setter for the datatype.
     * 
     * @param newValue
     *            The new datatype to set.
     */
    public void setDatatype(Datatype newValue) {
        if (newValue == null) {
            throw new IllegalArgumentException("Cannot set Datatype [null] into edit model.");
        }

        EditorStateSavingVisitor stateSaver = new EditorStateSavingVisitor();
        stateSaver.save(newValue);

        // Notify all dependency listeners that they dont need to check dependencies as far the
        // whole datatype is updating
        this.fireDatatypeReloadingEvent(true);

        Datatype oldValue = this.datatype;

        this.datatype = newValue;

        this.updateControls();

        stateSaver.apply(this.datatype);

        this.updateRelations();

        NabuccoPropertyResolver<?> resolver = new NabuccoPropertyResolver<Datatype>(this.datatype);

        for (BindableBrowserEntry entry : this.browserEntryList) {
            String boundProperty = entry.getPropertyPath();
            NabuccoProperty property = resolver.resolveProperty(boundProperty);

            if (property != null) {
                entry.setProperty(property);
            }
        }

        if (this.workflowModel != null) {
            this.workflowModel.setDatatype(newValue);
        }

        super.updateProperty(PROPERTY_DATATYPE, oldValue, newValue);

        // Notify all dependency listeners that they need to check dependencies
        this.fireDatatypeReloadingEvent(false);
    }

    /**
     * Notifies listeners that the datatype is in reloading state
     * 
     * @param status
     *            reloading state is active or not
     */
    private void fireDatatypeReloadingEvent(boolean status) {
        super.updateProperty(DependencyController.RELOADING_DATATYPE_EVENT_NAME, !status, status);
    }

    /**
     * Setter for the workflowModel.
     * 
     * @param workflowModel
     *            The workflowModel to set.
     */
    public void setWorkflowModel(WorkflowModel workflowModel) {
        this.workflowModel = workflowModel;
    }

    /**
     * Getter for the workflowModel.
     * 
     * @return Returns the workflowModel.
     */
    public WorkflowModel getWorkflowModel() {
        return this.workflowModel;
    }

    /**
     * Update the editor controls and fire property change events for the modified properties.
     */
    private void updateControls() {
        NabuccoPropertyResolver<Datatype> resolver = new NabuccoPropertyResolver<Datatype>(this.datatype);

        Map<String, NabuccoProperty> resolvedPropertiesMap = new HashMap<String, NabuccoProperty>();

        for (String id : this.controlMap.keySet()) {
            ControlModel<?> controlModel = this.controlMap.get(id);
            String propertyPath = controlModel.getPropertyPath();

            NabuccoProperty resolvedProperty = null;

            if (resolvedPropertiesMap.containsKey(propertyPath)) {
                resolvedProperty = resolvedPropertiesMap.get(propertyPath);
            } else {
                resolvedProperty = resolver.resolveProperty(propertyPath);
                resolvedPropertiesMap.put(propertyPath, resolvedProperty);
            }

            if (resolvedProperty == null) {
                String datatypeName = this.datatype != null ? this.datatype.getClass().getName() : "null";
                logger.warning("No property for path '", propertyPath, "' defined in datatype ", datatypeName, ".");
                continue;
            }

            controlModel.setProperty(resolvedProperty);
        }
    }

    @Override
    public void refresh() {
        this.setDatatype(this.datatype);
    }

    /**
     * Update the editor relation tables.
     */
    @SuppressWarnings("unchecked")
    private void updateRelations() {
        NabuccoPropertyResolver<Datatype> resolver = new NabuccoPropertyResolver<Datatype>(this.datatype);

        for (Entry<String, RelationTabModel> entry : this.relationTables.entrySet()) {

            NabuccoProperty property = resolver.resolveProperty(entry.getKey());
            if (property == null) {
                continue;
            }

            Object instance = property.getInstance();

            switch (property.getPropertyType()) {

            case COLLECTION:

                // TODO: Remove Workaround for Collections!

                if (instance == null) {
                    instance = new NabuccoListImpl<Datatype>();
                    NabuccoProperty newProperty = property.createProperty(instance);
                    newProperty.getParent().setProperty(newProperty);
                }

                entry.getValue().setContent((List<Datatype>) instance);
                break;

            case COMPONENT_RELATION:
                entry.getValue().setContent((List<Datatype>) instance);
                break;

            }

            entry.getValue().setProperty(property);

        }
    }

    /**
     * Validates Controls if the actual state is valid
     * 
     * @return true if valid or false if any things are not valid
     */
    public boolean validate() {
        boolean retVal = true;

        for (ControlModel<?> model : this.controlMap.values()) {
            model.setValidating(true);
            if (!model.validate().isEmpty()) {
                retVal = false;
            }
        }

        for (RelationTabModel model : this.relationTables.values()) {
            model.setValidating(true);
            if (!model.validate().isEmpty()) {
                retVal = false;
            }
        }

        return retVal;
    }

    /**
     * Add a property to the mapped controls.
     * 
     * @param propertyPath
     *            name of the property to add to the mapped properties
     * @param type
     *            the control type
     */
    public void addControl(ControlModel<?> controlModel) {
        if (controlModel == null) {
            throw new IllegalArgumentException("Cannot add control model [null] to editor model.");
        }

        controlModel.addPropertyChangeListener(controlModel.getPropertyPath(), this);

        this.controlMap.put(controlModel.getId(), controlModel);

        // Let control to have reference on its editor
        controlModel.setEditorModel(this);
    }

    /**
     * Returns the list of control id that need to be refreshed
     * 
     * @return list of ids
     */
    public List<String> getRefreshNeededControlIds() {
        List<String> retVal = new ArrayList<String>();
        for (ControlModel<?> control : this.controlMap.values()) {
            if (control.isRefreshNeeded()) {
                retVal.add(control.getId());
            }
        }
        return retVal;
    }

    @Override
    public boolean isDirty() {
        if (this.datatype == null) {
            return false;
        }

        DatatypeDirtyVisitor visitor = new DatatypeDirtyVisitor();

        try {
            this.datatype.accept(visitor);
            return visitor.isDirty();
        } catch (VisitorException ve) {
            logger.error(ve, "Error checking datatype state.");
            return false;
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        boolean dirtyStateBefore = this.isDirty();

        super.propertyChange(event);

        if (event.getSource() != this) {

            // Delegate Property Change Event
            boolean propertyChanged = super.updateProperty(event.getPropertyName(), event.getOldValue(),
                    event.getNewValue());

            if (propertyChanged && this.isInitialized()) {

                if (this.datatype != null) {
                    this.modifyDatatype(event.getPropertyName());
                }
            }
        }

        boolean dirtyStateAfter = this.isDirty();

        // theck if dirty state changed during the property change event and mark model for refresh
        // if the state changed
        if (dirtyStateBefore != dirtyStateAfter) {
            this.modelRefreshNeeded = true;
        }
    }

    /**
     * Getter for the modelRefreshNeeded.
     * 
     * @return Returns the modelRefreshNeeded.
     */
    public boolean isModelRefreshNeeded() {
        return this.modelRefreshNeeded;
    }

    /**
     * Change the Datatype state of the changed child datatype.
     * 
     * @param propertyPath
     *            the property path pointing to the child
     */
    private void modifyDatatype(String propertyPath) {

        // Resolve changed child Property.
        NabuccoPropertyResolver<Datatype> resolver = new NabuccoPropertyResolver<Datatype>(this.datatype);
        NabuccoProperty property = resolver.resolveProperty(propertyPath);

        PropertyOwner parent = property.getParent();

        if (parent instanceof Datatype) {
            Datatype instance = (Datatype) parent;

            // Only Persistent Datatypes must be set to MODIFIED.
            if (instance.getDatatypeState() == DatatypeState.PERSISTENT) {
                instance.setDatatypeState(DatatypeState.MODIFIED);
            }
        }
    }





    @Override
    public JsonElement toJson() {
        JsonMap json = (JsonMap) super.toJson();
        json.add(JSON_DIRTY, this.isDirty());

        this.modelRefreshNeeded = false;
        return json;
    }

}
