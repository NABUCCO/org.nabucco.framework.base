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
package org.nabucco.framework.base.ui.web.model.control.util.dependency;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.dependency.DependencyExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.dependency.DependencySetExtension;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.json.Jsonable;

/**
 * DependencyController
 * 
 * The controller for control dependencies.
 * 
 * The dependency mechanism is build on property change design pattern. Here is the documentation of
 * listeners and reasons of listening:
 * 
 * 1. EditorModel listens on every ControlModel for its property changing. Reason: notify all
 * dependent elements.
 * 
 * 2. DependencyControllers listen to ControlModel for every property which is controlled by
 * controller. Reason: Changing of property may couse changing of constraint after evaluation.
 * 
 * 2a. DependencyControllers listen to ControlModel for reloading notifier event. This event is
 * fired at the beginning and after setting of datatype to editor. In reloading Datatype state the
 * dependency controller doesn't notify dependent properties by modifiyng of values, only by
 * changing of constraint state.
 * 
 * 3. Every ControlModel listens on its DependencyController for constraintschanging notification.
 * By negative changing (disabling) is the property nulled because the dependency is not ever valid.
 * The nulling of property couses a property change on it that comes to editor model and from there
 * to every other control which is dependent on this one.
 * 
 * Notice: the evaluation of dependency controller happens not by every notification, but only if
 * the vilidity of notificated property dependency has changed.
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DependencyController implements PropertyChangeListener, Jsonable {

    /** The exnsion of controller */
    private DependencySetExtension extension;

    public final static String RELOADING_DATATYPE_EVENT_NAME = "reloadingDatatypeNotifier";

    private static final String JSON_DEPENDECIES = "dependencies";

    private final static String DEPENDENCY_CHANGED_PROPERTY = "dependencyChanged";

    /** The Property Change Delegate */
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    /** Map of the dependencies property name => dependency element */
    private Map<String, DependencyItem> dependencyMap = new HashMap<String, DependencyItem>();

    /** Indicates if the control is valid according to its dependencies */
    private boolean valid = false;

    /**
     * Indicates that the datatype is reloading at the moment and only constraints should be
     * evaluated
     */
    private boolean datatypeReloading = false;

    /**
     * 
     * Creates a new {@link DependencyController} instance.
     * 
     * @param extension
     *            the extension of the set or null for empty container.
     */
    public DependencyController(DependencySetExtension extension) {
        this.extension = extension;
    }

    /**
     * Initializes the dependency set
     */
    public void init() {
        if (this.extension == null) {
            return;
        }

        for (DependencyExtension dependencyExtension : this.extension.getDependencies()) {
            DependencyItem dependencyItem = new DependencyItem(dependencyExtension);
            this.dependencyMap.put(dependencyItem.getTargetProperty(), dependencyItem);
        }
    }

    /**
     * Add a property change listener to the model.
     * 
     * @param listener
     *            the property change listener to add
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Cannot add property change listener 'null'.");
        }

        this.propertyChangeSupport.addPropertyChangeListener(DEPENDENCY_CHANGED_PROPERTY, listener);
    }

    /**
     * Fire a property change event
     * 
     * @param newValue
     *            true if true dependency changed or false if value changed and nulling needed
     */
    private void firePropertyChange(boolean newValue) {
        this.propertyChangeSupport.firePropertyChange(DEPENDENCY_CHANGED_PROPERTY, !newValue, newValue);

    }

    /**
     * Tells if the dependency controller is active and is listening to something or can be ignoren
     * 
     * @return if active
     */
    public boolean isActive() {
        if (this.extension == null) {
            return false;
        }

        if (this.dependencyMap.isEmpty()) {
            return false;
        }

        return true;
    }

    /**
     * Getter for the valid.
     * 
     * @return Returns true if all dependencies are valid.
     */
    public boolean isValid() {
        return this.valid;
    }

    /**
     * Getter for the affected constraint
     * 
     * @return affected constraint (editable, visible) or null if no extension configured
     */
    public DependencySetAffectedConstraintType getAffectedConstraint() {
        if (this.extension == null) {
            return null;
        }

        DependencySetAffectedConstraintType retVal = PropertyLoader.loadProperty(
                DependencySetAffectedConstraintType.class, this.extension.getAffectedConstraint());
        return retVal;
    }

    /**
     * Evaluates the dependencies according to the connection type
     * 
     * @return the evaluation result
     */
    private boolean evaluateDependencies() {
        boolean result;

        switch (this.getDependencySetConnectionType()) {
        case AND: {
            result = true;
            for (DependencyItem dependency : this.dependencyMap.values()) {
                if (dependency.isValid() == false) {
                    result = false;
                    break;
                }
            }
            break;
        }
        case OR: {
            result = false;
            for (DependencyItem dependency : this.dependencyMap.values()) {
                if (dependency.isValid() == true) {
                    result = true;
                    break;
                }
            }
            break;
        }
        default: {
            throw new IllegalArgumentException(
                    "Cannot evaluate Dependencies. The connection type is not supported yet.");
        }
        }

        if (this.valid != result) {
            this.valid = result;
            this.firePropertyChange(this.valid);
        }

        return this.valid;
    }

    /**
     * Getter for the type of the connection between dependencies (AND / OR)
     * 
     * @return DependencySetConnectionType or null
     */
    private DependencySetConnectionType getDependencySetConnectionType() {
        if (this.extension == null) {
            return null;
        }

        DependencySetConnectionType retVal = PropertyLoader.loadProperty(DependencySetConnectionType.class,
                this.extension.getConnectionType());
        return retVal;
    }

    /**
     * Getter for the dependent property names
     * 
     * @return list of string or empty list if no properties
     */
    public List<String> getPropertyNames() {

        return new ArrayList<String>(this.dependencyMap.keySet());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        String propertyName = evt.getPropertyName();
        if (propertyName.equals(RELOADING_DATATYPE_EVENT_NAME)) {
            this.setDatatypeReloading((Boolean) evt.getNewValue());
        } else if (this.dependencyMap.containsKey(propertyName)) {
            DependencyItem dependencyItem = this.dependencyMap.get(propertyName);
            boolean dependencyValueChanged = dependencyItem.checkDependency(evt.getNewValue());
            if (dependencyValueChanged) {
                boolean evaluateDependencies = this.evaluateDependencies();
                if (evaluateDependencies) {
                    this.checkValueModified((NType) evt.getOldValue(), (NType) evt.getNewValue());
                }
            } else {
                this.checkValueModified((NType) evt.getOldValue(), (NType) evt.getNewValue());
            }
        }
    }

    /**
     * Check if the value was modified and the dependencies need to be reseted
     * 
     * @param oldV
     *            old value
     * @param newV
     *            new value
     */
    private void checkValueModified(NType oldV, NType newV) {
        if (this.isDatatypeReloading()) {
            return;
        }

        Datatype oldValue = this.resolveDatatype(oldV);
        Datatype newValue = this.resolveDatatype(newV);

        if (oldValue != null && newValue != null) {
            if (!oldValue.equals(newValue)) {
                this.firePropertyChange(false);
            }
        } else if (oldValue != null || newValue != null) {
            this.firePropertyChange(false);
        } else {

        }
    }

    /**
     * Tryes to resolve given element to a datatype. From Component relations comes only the first
     * target as datatype.
     * 
     * @return target of first component relation, datatype or dirst list element or null if nothing
     *         of it.
     */
    private Datatype resolveDatatype(NType element) {
        if (element instanceof List) {
            List<?> list = (List<?>) element;
            for (Object listelement : list) {
                if (listelement instanceof ComponentRelation) {
                    ComponentRelation<?> relation = (ComponentRelation<?>) listelement;
                    NabuccoDatatype target = relation.getTarget();
                    return target;
                } else if (listelement instanceof Datatype) {
                    return (Datatype) listelement;
                }
            }
        }

        if (element instanceof Datatype) {
            return (Datatype) element;
        }

        return null;
    }

    /**
     * Setter for the datatypeReloading.
     * 
     * @param datatypeReloading
     *            The datatypeReloading to set.
     */
    private void setDatatypeReloading(boolean datatypeReloading) {
        this.datatypeReloading = datatypeReloading;
    }

    /**
     * Getter for the datatypeReloading.
     * 
     * @return Returns the datatypeReloading.
     */
    private boolean isDatatypeReloading() {
        return this.datatypeReloading;
    }

    @Override
    public JsonElement toJson() {
        JsonMap retVal = new JsonMap();

        JsonMap dependencies = new JsonMap();
        for (String key : this.dependencyMap.keySet()) {
            boolean dependencyValid = this.dependencyMap.get(key).isValid();
            dependencies.add(key, dependencyValid);
        }
        retVal.add(JSON_DEPENDECIES, dependencies);
        retVal.add(JSON_VALID, this.isValid());
        retVal.add(RELOADING_DATATYPE_EVENT_NAME, this.isDatatypeReloading());
        return retVal;
    }

    @Override
    public String toString() {
        return this.toJson().toString();
    }

}
