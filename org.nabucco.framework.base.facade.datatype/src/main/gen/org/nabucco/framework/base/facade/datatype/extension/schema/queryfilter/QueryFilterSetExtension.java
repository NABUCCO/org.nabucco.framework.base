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
package org.nabucco.framework.base.facade.datatype.extension.schema.queryfilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.property.ClassProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.queryfilter.QueryFilterExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * QueryFilterSetExtension<p/>Extension for a set of filters.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-10-24
 */
public class QueryFilterSetExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m0,1;", "m0,n;", "m1,1;" };

    public static final String FILTERSETID = "filterSetId";

    public static final String NAME = "name";

    public static final String DESCRIPTION = "description";

    public static final String FILTERS = "filters";

    public static final String COMPONENT = "component";

    /** The name of the filter set. */
    private StringProperty filterSetId;

    /** The name of the filter set. */
    private StringProperty name;

    /** An optional description of the filter set. */
    private StringProperty description;

    /** The filters of the filter set. */
    private NabuccoList<QueryFilterExtension> filters;

    /** The component of the filter. */
    private ClassProperty component;

    /** Constructs a new QueryFilterSetExtension instance. */
    public QueryFilterSetExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the QueryFilterSetExtension.
     */
    protected void cloneObject(QueryFilterSetExtension clone) {
        super.cloneObject(clone);
        if ((this.getFilterSetId() != null)) {
            clone.setFilterSetId(this.getFilterSetId().cloneObject());
        }
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        if ((this.filters != null)) {
            clone.filters = this.filters.cloneCollection();
        }
        if ((this.getComponent() != null)) {
            clone.setComponent(this.getComponent().cloneObject());
        }
    }

    /**
     * Getter for the FiltersJPA.
     *
     * @return the List<QueryFilterExtension>.
     */
    List<QueryFilterExtension> getFiltersJPA() {
        if ((this.filters == null)) {
            this.filters = new NabuccoListImpl<QueryFilterExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<QueryFilterExtension>) this.filters).getDelegate();
    }

    /**
     * Setter for the FiltersJPA.
     *
     * @param filters the List<QueryFilterExtension>.
     */
    void setFiltersJPA(List<QueryFilterExtension> filters) {
        if ((this.filters == null)) {
            this.filters = new NabuccoListImpl<QueryFilterExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<QueryFilterExtension>) this.filters).setDelegate(filters);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoExtensionComposite.class).getPropertyMap());
        propertyMap.put(FILTERSETID, PropertyDescriptorSupport.createDatatype(FILTERSETID, StringProperty.class, 2,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(NAME, PropertyDescriptorSupport.createDatatype(NAME, StringProperty.class, 3,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createDatatype(DESCRIPTION, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(FILTERS, PropertyDescriptorSupport.createCollection(FILTERS, QueryFilterExtension.class, 5,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(COMPONENT, PropertyDescriptorSupport.createDatatype(COMPONENT, ClassProperty.class, 6,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(QueryFilterSetExtension.getPropertyDescriptor(FILTERSETID),
                this.getFilterSetId(), null));
        properties.add(super.createProperty(QueryFilterSetExtension.getPropertyDescriptor(NAME), this.getName(), null));
        properties.add(super.createProperty(QueryFilterSetExtension.getPropertyDescriptor(DESCRIPTION),
                this.getDescription(), null));
        properties
                .add(super.createProperty(QueryFilterSetExtension.getPropertyDescriptor(FILTERS), this.filters, null));
        properties.add(super.createProperty(QueryFilterSetExtension.getPropertyDescriptor(COMPONENT),
                this.getComponent(), null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(FILTERSETID) && (property.getType() == StringProperty.class))) {
            this.setFilterSetId(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NAME) && (property.getType() == StringProperty.class))) {
            this.setName(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == StringProperty.class))) {
            this.setDescription(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(FILTERS) && (property.getType() == QueryFilterExtension.class))) {
            this.filters = ((NabuccoList<QueryFilterExtension>) property.getInstance());
            return true;
        } else if ((property.getName().equals(COMPONENT) && (property.getType() == ClassProperty.class))) {
            this.setComponent(((ClassProperty) property.getInstance()));
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
        final QueryFilterSetExtension other = ((QueryFilterSetExtension) obj);
        if ((this.filterSetId == null)) {
            if ((other.filterSetId != null))
                return false;
        } else if ((!this.filterSetId.equals(other.filterSetId)))
            return false;
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        if ((this.description == null)) {
            if ((other.description != null))
                return false;
        } else if ((!this.description.equals(other.description)))
            return false;
        if ((this.component == null)) {
            if ((other.component != null))
                return false;
        } else if ((!this.component.equals(other.component)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.filterSetId == null) ? 0 : this.filterSetId.hashCode()));
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.component == null) ? 0 : this.component.hashCode()));
        return result;
    }

    @Override
    public QueryFilterSetExtension cloneObject() {
        QueryFilterSetExtension clone = new QueryFilterSetExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The name of the filter set.
     *
     * @param filterSetId the StringProperty.
     */
    public void setFilterSetId(StringProperty filterSetId) {
        this.filterSetId = filterSetId;
    }

    /**
     * The name of the filter set.
     *
     * @return the StringProperty.
     */
    public StringProperty getFilterSetId() {
        return this.filterSetId;
    }

    /**
     * The name of the filter set.
     *
     * @param name the StringProperty.
     */
    public void setName(StringProperty name) {
        this.name = name;
    }

    /**
     * The name of the filter set.
     *
     * @return the StringProperty.
     */
    public StringProperty getName() {
        return this.name;
    }

    /**
     * An optional description of the filter set.
     *
     * @param description the StringProperty.
     */
    public void setDescription(StringProperty description) {
        this.description = description;
    }

    /**
     * An optional description of the filter set.
     *
     * @return the StringProperty.
     */
    public StringProperty getDescription() {
        return this.description;
    }

    /**
     * The filters of the filter set.
     *
     * @return the NabuccoList<QueryFilterExtension>.
     */
    public NabuccoList<QueryFilterExtension> getFilters() {
        if ((this.filters == null)) {
            this.filters = new NabuccoListImpl<QueryFilterExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.filters;
    }

    /**
     * The component of the filter.
     *
     * @param component the ClassProperty.
     */
    public void setComponent(ClassProperty component) {
        this.component = component;
    }

    /**
     * The component of the filter.
     *
     * @return the ClassProperty.
     */
    public ClassProperty getComponent() {
        return this.component;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(QueryFilterSetExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(QueryFilterSetExtension.class).getAllProperties();
    }
}
