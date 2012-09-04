/*
 * Copyright 2012 PRODYNA AG
 * 
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.work;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemActionExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * WorkItemActionsExtension<p/>NABUCCO The set of references to the special actions used for working items<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2011-09-27
 */
public class WorkItemActionsExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;" };

    public static final String ACTIONS = "actions";

    /** the list of the workarea specific actions */
    private NabuccoList<WorkItemActionExtension> actions;

    /** Constructs a new WorkItemActionsExtension instance. */
    public WorkItemActionsExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the WorkItemActionsExtension.
     */
    protected void cloneObject(WorkItemActionsExtension clone) {
        super.cloneObject(clone);
        if ((this.actions != null)) {
            clone.actions = this.actions.cloneCollection();
        }
    }

    /**
     * Getter for the ActionsJPA.
     *
     * @return the List<WorkItemActionExtension>.
     */
    List<WorkItemActionExtension> getActionsJPA() {
        if ((this.actions == null)) {
            this.actions = new NabuccoListImpl<WorkItemActionExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<WorkItemActionExtension>) this.actions).getDelegate();
    }

    /**
     * Setter for the ActionsJPA.
     *
     * @param actions the List<WorkItemActionExtension>.
     */
    void setActionsJPA(List<WorkItemActionExtension> actions) {
        if ((this.actions == null)) {
            this.actions = new NabuccoListImpl<WorkItemActionExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<WorkItemActionExtension>) this.actions).setDelegate(actions);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(UiExtension.class).getPropertyMap());
        propertyMap.put(ACTIONS, PropertyDescriptorSupport.createCollection(ACTIONS, WorkItemActionExtension.class, 4,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties
                .add(super.createProperty(WorkItemActionsExtension.getPropertyDescriptor(ACTIONS), this.actions, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(ACTIONS) && (property.getType() == WorkItemActionExtension.class))) {
            this.actions = ((NabuccoList<WorkItemActionExtension>) property.getInstance());
            return true;
        }
        return false;
    }

    @Override
    public WorkItemActionsExtension cloneObject() {
        WorkItemActionsExtension clone = new WorkItemActionsExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * the list of the workarea specific actions
     *
     * @return the NabuccoList<WorkItemActionExtension>.
     */
    public NabuccoList<WorkItemActionExtension> getActions() {
        if ((this.actions == null)) {
            this.actions = new NabuccoListImpl<WorkItemActionExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.actions;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(WorkItemActionsExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(WorkItemActionsExtension.class).getAllProperties();
    }
}
