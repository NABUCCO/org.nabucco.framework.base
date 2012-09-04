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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.dashboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget.WidgetReferenceExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * DashboardExtension<p/>NABUCCO User Interface Dashboard extension.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-07-28
 */
public class DashboardExtension extends WorkItemExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;", "m1,1;" };

    public static final String WIDGETS = "widgets";

    public static final String GRID = "grid";

    /** The widgets to show on the dashboard */
    public NabuccoList<WidgetReferenceExtension> widgets;

    public StringProperty grid;

    /** Constructs a new DashboardExtension instance. */
    public DashboardExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the DashboardExtension.
     */
    protected void cloneObject(DashboardExtension clone) {
        super.cloneObject(clone);
        if ((this.widgets != null)) {
            clone.widgets = this.widgets.cloneCollection();
        }
        if ((this.getGrid() != null)) {
            clone.setGrid(this.getGrid().cloneObject());
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
        propertyMap.putAll(PropertyCache.getInstance().retrieve(WorkItemExtension.class).getPropertyMap());
        propertyMap.put(WIDGETS, PropertyDescriptorSupport.createCollection(WIDGETS, WidgetReferenceExtension.class,
                11, PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(GRID, PropertyDescriptorSupport.createDatatype(GRID, StringProperty.class, 12,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(DashboardExtension.getPropertyDescriptor(WIDGETS), this.widgets, null));
        properties.add(super.createProperty(DashboardExtension.getPropertyDescriptor(GRID), this.getGrid(), null));
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
        } else if ((property.getName().equals(GRID) && (property.getType() == StringProperty.class))) {
            this.setGrid(((StringProperty) property.getInstance()));
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if ((this == obj)) {
            return true;
        }
        if ((obj == null)) {
            return false;
        }
        if ((this.getClass() != obj.getClass())) {
            return false;
        }
        if ((!super.equals(obj))) {
            return false;
        }
        final DashboardExtension other = ((DashboardExtension) obj);
        if ((this.grid == null)) {
            if ((other.grid != null))
                return false;
        } else if ((!this.grid.equals(other.grid)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.grid == null) ? 0 : this.grid.hashCode()));
        return result;
    }

    @Override
    public DashboardExtension cloneObject() {
        DashboardExtension clone = new DashboardExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The widgets to show on the dashboard
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
     * Missing description at method setGrid.
     *
     * @param grid the StringProperty.
     */
    public void setGrid(StringProperty grid) {
        this.grid = grid;
    }

    /**
     * Missing description at method getGrid.
     *
     * @return the StringProperty.
     */
    public StringProperty getGrid() {
        return this.grid;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(DashboardExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(DashboardExtension.class).getAllProperties();
    }
}
