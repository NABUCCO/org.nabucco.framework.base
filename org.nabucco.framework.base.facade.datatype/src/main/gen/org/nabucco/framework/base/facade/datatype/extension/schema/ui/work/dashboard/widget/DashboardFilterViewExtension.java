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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.dashboard.widget;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.filter.FilterReferenceExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * DashboardFilterViewExtension<p/>Reference to the Filter with the definition of the evaluation type<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-07-23
 */
public class DashboardFilterViewExtension extends FilterReferenceExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;" };

    public static final String ID = "id";

    public static final String VIEWNAME = "viewName";

    /** The if of the reference */
    private StringProperty id;

    /** The name of the view (the type of evaluation) */
    private StringProperty viewName;

    /** Constructs a new DashboardFilterViewExtension instance. */
    public DashboardFilterViewExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the DashboardFilterViewExtension.
     */
    protected void cloneObject(DashboardFilterViewExtension clone) {
        super.cloneObject(clone);
        if ((this.getId() != null)) {
            clone.setId(this.getId().cloneObject());
        }
        if ((this.getViewName() != null)) {
            clone.setViewName(this.getViewName().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(FilterReferenceExtension.class).getPropertyMap());
        propertyMap.put(ID, PropertyDescriptorSupport.createDatatype(ID, StringProperty.class, 9,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(VIEWNAME, PropertyDescriptorSupport.createDatatype(VIEWNAME, StringProperty.class, 10,
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
        properties
                .add(super.createProperty(DashboardFilterViewExtension.getPropertyDescriptor(ID), this.getId(), null));
        properties.add(super.createProperty(DashboardFilterViewExtension.getPropertyDescriptor(VIEWNAME),
                this.getViewName(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(ID) && (property.getType() == StringProperty.class))) {
            this.setId(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(VIEWNAME) && (property.getType() == StringProperty.class))) {
            this.setViewName(((StringProperty) property.getInstance()));
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
        final DashboardFilterViewExtension other = ((DashboardFilterViewExtension) obj);
        if ((this.id == null)) {
            if ((other.id != null))
                return false;
        } else if ((!this.id.equals(other.id)))
            return false;
        if ((this.viewName == null)) {
            if ((other.viewName != null))
                return false;
        } else if ((!this.viewName.equals(other.viewName)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.id == null) ? 0 : this.id.hashCode()));
        result = ((PRIME * result) + ((this.viewName == null) ? 0 : this.viewName.hashCode()));
        return result;
    }

    @Override
    public DashboardFilterViewExtension cloneObject() {
        DashboardFilterViewExtension clone = new DashboardFilterViewExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The if of the reference
     *
     * @param id the StringProperty.
     */
    public void setId(StringProperty id) {
        this.id = id;
    }

    /**
     * The if of the reference
     *
     * @return the StringProperty.
     */
    public StringProperty getId() {
        return this.id;
    }

    /**
     * The name of the view (the type of evaluation)
     *
     * @param viewName the StringProperty.
     */
    public void setViewName(StringProperty viewName) {
        this.viewName = viewName;
    }

    /**
     * The name of the view (the type of evaluation)
     *
     * @return the StringProperty.
     */
    public StringProperty getViewName() {
        return this.viewName;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(DashboardFilterViewExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(DashboardFilterViewExtension.class).getAllProperties();
    }
}
