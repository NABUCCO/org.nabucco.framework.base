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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget.WidgetReferenceExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * GridWidgetExtension<p/>NABUCCO User Interface StatusBar extension.<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2011-15-09
 */
public class GridWidgetExtension extends GridExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;" };

    public static final String WIDGETS = "widgets";

    /** Widgets of the grid */
    private NabuccoList<WidgetReferenceExtension> widgets;

    /** Constructs a new GridWidgetExtension instance. */
    public GridWidgetExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the GridWidgetExtension.
     */
    protected void cloneObject(GridWidgetExtension clone) {
        super.cloneObject(clone);
        if ((this.widgets != null)) {
            clone.widgets = this.widgets.cloneCollection();
        }
    }

    /**
     * Getter for the Widthe sJPA.
     *
     * @return the List<WidgetReferenceExtension>.
     */
    List<WidgetReferenceExtension> getWidgetsJPA() {
        if ((this.widgets == null)) {
            this.widgets = new NabuccoListImpl<WidgetReferenceExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<WidgetReferenceExtension>) this.widgets).getDelegate();
    }

    /**
     * Setter for the WidgetsJPA.
     *
     * @param widgets the List<WidgetReferenceExtension>.
     */
    void setWidgetsJPA(List<WidgetReferenceExtension> widgets) {
        if ((this.widgets == null)) {
            this.widgets = new NabuccoListImpl<WidgetReferenceExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<WidgetReferenceExtension>) this.widgets).setDelegate(widgets);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(GridExtension.class).getPropertyMap());
        propertyMap.put(WIDGETS, PropertyDescriptorSupport.createCollection(WIDGETS, WidgetReferenceExtension.class, 5,
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
        properties.add(super.createProperty(GridWidgetExtension.getPropertyDescriptor(WIDGETS), this.widgets, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(WIDGETS) && (property.getType() == WidgetReferenceExtension.class))) {
            this.widgets = ((NabuccoList<WidgetReferenceExtension>) property.getInstance());
            return true;
        }
        return false;
    }

    @Override
    public GridWidgetExtension cloneObject() {
        GridWidgetExtension clone = new GridWidgetExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Widgets of the grid
     *
     * @return the NabuccoList<WidgetReferenceExtension>.
     */
    public NabuccoList<WidgetReferenceExtension> getWidgets() {
        if ((this.widgets == null)) {
            this.widgets = new NabuccoListImpl<WidgetReferenceExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.widgets;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(GridWidgetExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(GridWidgetExtension.class).getAllProperties();
    }
}
