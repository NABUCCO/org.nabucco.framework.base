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
package org.nabucco.framework.base.ui.web.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashSet;
import java.util.Set;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollection;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyResolver;
import org.nabucco.framework.base.ui.web.json.Jsonable;

/**
 * Abstract model for User Interface elements like Tables, Menus or Forms.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class WebModel implements PropertyChangeListener, Jsonable {

    /** The Property Change Delegate */
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    /** Registered Property Names */
    private Set<String> propertyNames = new HashSet<String>();

    /** Default Label */
    protected static final String DEFAULT_LABEL = "";

    /**
     * Initialize the web model. May be called multiple times to re initialize the model.
     */
    public abstract void init();

    /**
     * Getter for the initialized.
     * 
     * @return Returns the initialized.
     */
    public boolean isInitialized() {
        return true;
    }

    /**
     * Add a property change listener to the model.
     * 
     * @param propertyName
     *            the property to listen for
     * @param listener
     *            the property change listener to add
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        if (propertyName == null || propertyName.isEmpty()) {
            throw new IllegalArgumentException("Cannot listen property change for property 'null'.");
        }
        if (listener == null) {
            throw new IllegalArgumentException("Cannot add property change listener 'null'.");
        }

        this.propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
        this.propertyNames.add(propertyName);
    }

    /**
     * Fire the property change event to all registered listeners.
     * 
     * @param propertyName
     *            the name of the changed property
     * @param oldValue
     *            the old property value
     * @param newValue
     *            the new property value
     * 
     * @return <b>true</b> when the property has changed, <b>false</b> when not
     */
    protected boolean updateProperty(String propertyName, Object oldValue, Object newValue) {
        if (propertyName == null) {
            throw new IllegalArgumentException("Cannot fire property change for property 'null'.");
        }

        // Old Value was 'null'
        if (oldValue == null) {
            if (newValue != null) {
                return this.firePropertyChange(propertyName, oldValue, newValue);
            }
            return false;
        }

        // Old Value same as New Value
        if (oldValue == newValue) {

            // NabuccoCollections must always fire a property change event (call by reference)!
            if (oldValue instanceof NabuccoCollection<?>) {
                return this.firePropertyChange(propertyName, null, newValue);
            }

            return false;
        }

        // Old Value unequal New Value
        if (!oldValue.equals(newValue)) {
            return this.firePropertyChange(propertyName, oldValue, newValue);
        }

        return false;
    }

    /**
     * Fire a property change event
     * 
     * @param propertyName
     *            the property name
     * @param oldValue
     *            the old value
     * @param newValue
     *            the new value
     * 
     * @return <b>true</b>
     */
    private boolean firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        this.propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);

        oldValue = this.tryToResolveComponentRelation(oldValue);
        newValue = this.tryToResolveComponentRelation(newValue);

        if (oldValue != null && !(oldValue instanceof Datatype)) {
            return true;
        }
        if (newValue != null && !(newValue instanceof Datatype)) {
            return true;
        }

        this.fireDependencyPropertyChange(propertyName, (Datatype) oldValue, (Datatype) newValue);

        return true;
    }

    /**
     * Try to analyse the content of Object parameter and resolve some bindable datatype. For
     * example returns target from the first component relation found
     * 
     * @param value
     *            the value to be analysed
     * @return resolved datatype or original value
     */
    private Object tryToResolveComponentRelation(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof NabuccoList) {
            NabuccoList<?> list = (NabuccoList<?>) value;
            if (list.size() == 0) {
                return list;
            }

            if (list.size() >= 0) {
                Object listElement = list.get(0);
                if (listElement instanceof ComponentRelation) {
                    ComponentRelation<?> relation = (ComponentRelation<?>) listElement;
                    Object relationTarget = relation.getTarget();
                    return relationTarget;
                }
            }
        }

        return value;
    }

    /**
     * Fire property changes for all registered child dependencies.
     * <p/>
     * Example: <b>"aa"</b> fires property changes for all registered children <b>"aa.bb"</b>,
     * <b>"aa.cc.dd"</b>, etc.
     * 
     * @param propertyName
     *            the root property name
     * @param oldParent
     *            the old root datatype
     * @param newParent
     *            the new root datatype
     */
    private void fireDependencyPropertyChange(String propertyName, Datatype oldParent, Datatype newParent) {

        NabuccoPropertyResolver<Datatype> oldResolver = null;
        if (oldParent != null) {
            oldResolver = new NabuccoPropertyResolver<Datatype>(oldParent);
        }

        NabuccoPropertyResolver<Datatype> newResolver = null;
        if (newParent != null) {
            newResolver = new NabuccoPropertyResolver<Datatype>(newParent);
        }

        for (String registeredProperty : this.propertyNames) {

            if (registeredProperty.equals(propertyName)) {
                continue;
            }

            if (registeredProperty.startsWith(propertyName)) {

                String resolvingProperty = registeredProperty.substring(propertyName.length() + 1);

                Object oldValue = null;
                Object newValue = null;

                if (oldResolver != null) {
                    NabuccoProperty oldProperty = oldResolver.resolveProperty(resolvingProperty);
                    if (oldProperty == null) {
                        return;
                    }
                    oldValue = oldProperty.getInstance();
                }

                if (newResolver != null) {
                    NabuccoProperty newProperty = newResolver.resolveProperty(resolvingProperty);
                    if (newProperty == null) {
                        return;
                    }
                    newValue = newProperty.getInstance();
                }

                this.propertyChangeSupport.firePropertyChange(registeredProperty, oldValue, newValue);
            }

        }

    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
    }

}
