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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.list;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.ColumnExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.ListButtonExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.filter.FilterReferenceExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * ListExtension<p/>NABUCCO User Interface List extension.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-07-28
 */
public class ListExtension extends WorkItemExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,1;", "m0,n;", "m0,n;", "m0,n;" };

    public static final String DOUBLECLICKACTION = "doubleclickAction";

    public static final String BUTTONS = "buttons";

    public static final String COLUMNS = "columns";

    public static final String FILTERS = "filters";

    /** The action to be used to open content items */
    private StringProperty doubleclickAction;

    /** The Editor Actions. */
    private NabuccoList<ListButtonExtension> buttons;

    /** The List Columns. */
    private NabuccoList<ColumnExtension> columns;

    /** The data filters */
    private NabuccoList<FilterReferenceExtension> filters;

    /** Constructs a new ListExtension instance. */
    public ListExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ListExtension.
     */
    protected void cloneObject(ListExtension clone) {
        super.cloneObject(clone);
        if ((this.getDoubleclickAction() != null)) {
            clone.setDoubleclickAction(this.getDoubleclickAction().cloneObject());
        }
        if ((this.buttons != null)) {
            clone.buttons = this.buttons.cloneCollection();
        }
        if ((this.columns != null)) {
            clone.columns = this.columns.cloneCollection();
        }
        if ((this.filters != null)) {
            clone.filters = this.filters.cloneCollection();
        }
    }

    /**
     * Getter for the ButtonsJPA.
     *
     * @return the List<ListButtonExtension>.
     */
    List<ListButtonExtension> getButtonsJPA() {
        if ((this.buttons == null)) {
            this.buttons = new NabuccoListImpl<ListButtonExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<ListButtonExtension>) this.buttons).getDelegate();
    }

    /**
     * Setter for the ButtonsJPA.
     *
     * @param buttons the List<ListButtonExtension>.
     */
    void setButtonsJPA(List<ListButtonExtension> buttons) {
        if ((this.buttons == null)) {
            this.buttons = new NabuccoListImpl<ListButtonExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<ListButtonExtension>) this.buttons).setDelegate(buttons);
    }

    /**
     * Getter for the ColumnsJPA.
     *
     * @return the List<ColumnExtension>.
     */
    List<ColumnExtension> getColumnsJPA() {
        if ((this.columns == null)) {
            this.columns = new NabuccoListImpl<ColumnExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<ColumnExtension>) this.columns).getDelegate();
    }

    /**
     * Setter for the ColumnsJPA.
     *
     * @param columns the List<ColumnExtension>.
     */
    void setColumnsJPA(List<ColumnExtension> columns) {
        if ((this.columns == null)) {
            this.columns = new NabuccoListImpl<ColumnExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<ColumnExtension>) this.columns).setDelegate(columns);
    }

    /**
     * Getter for the FiltersJPA.
     *
     * @return the List<FilterReferenceExtension>.
     */
    List<FilterReferenceExtension> getFiltersJPA() {
        if ((this.filters == null)) {
            this.filters = new NabuccoListImpl<FilterReferenceExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<FilterReferenceExtension>) this.filters).getDelegate();
    }

    /**
     * Setter for the FiltersJPA.
     *
     * @param filters the List<FilterReferenceExtension>.
     */
    void setFiltersJPA(List<FilterReferenceExtension> filters) {
        if ((this.filters == null)) {
            this.filters = new NabuccoListImpl<FilterReferenceExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<FilterReferenceExtension>) this.filters).setDelegate(filters);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(WorkItemExtension.class).getPropertyMap());
        propertyMap.put(DOUBLECLICKACTION, PropertyDescriptorSupport.createDatatype(DOUBLECLICKACTION,
                StringProperty.class, 11, PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(BUTTONS, PropertyDescriptorSupport.createCollection(BUTTONS, ListButtonExtension.class, 12,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(COLUMNS, PropertyDescriptorSupport.createCollection(COLUMNS, ColumnExtension.class, 13,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(FILTERS, PropertyDescriptorSupport.createCollection(FILTERS, FilterReferenceExtension.class,
                14, PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ListExtension.getPropertyDescriptor(DOUBLECLICKACTION),
                this.getDoubleclickAction(), null));
        properties.add(super.createProperty(ListExtension.getPropertyDescriptor(BUTTONS), this.buttons, null));
        properties.add(super.createProperty(ListExtension.getPropertyDescriptor(COLUMNS), this.columns, null));
        properties.add(super.createProperty(ListExtension.getPropertyDescriptor(FILTERS), this.filters, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(DOUBLECLICKACTION) && (property.getType() == StringProperty.class))) {
            this.setDoubleclickAction(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(BUTTONS) && (property.getType() == ListButtonExtension.class))) {
            this.buttons = ((NabuccoList<ListButtonExtension>) property.getInstance());
            return true;
        } else if ((property.getName().equals(COLUMNS) && (property.getType() == ColumnExtension.class))) {
            this.columns = ((NabuccoList<ColumnExtension>) property.getInstance());
            return true;
        } else if ((property.getName().equals(FILTERS) && (property.getType() == FilterReferenceExtension.class))) {
            this.filters = ((NabuccoList<FilterReferenceExtension>) property.getInstance());
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
        final ListExtension other = ((ListExtension) obj);
        if ((this.doubleclickAction == null)) {
            if ((other.doubleclickAction != null))
                return false;
        } else if ((!this.doubleclickAction.equals(other.doubleclickAction)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.doubleclickAction == null) ? 0 : this.doubleclickAction.hashCode()));
        return result;
    }

    @Override
    public ListExtension cloneObject() {
        ListExtension clone = new ListExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The action to be used to open content items
     *
     * @param doubleclickAction the StringProperty.
     */
    public void setDoubleclickAction(StringProperty doubleclickAction) {
        this.doubleclickAction = doubleclickAction;
    }

    /**
     * The action to be used to open content items
     *
     * @return the StringProperty.
     */
    public StringProperty getDoubleclickAction() {
        return this.doubleclickAction;
    }

    /**
     * The Editor Actions.
     *
     * @return the NabuccoList<ListButtonExtension>.
     */
    public NabuccoList<ListButtonExtension> getButtons() {
        if ((this.buttons == null)) {
            this.buttons = new NabuccoListImpl<ListButtonExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.buttons;
    }

    /**
     * The List Columns.
     *
     * @return the NabuccoList<ColumnExtension>.
     */
    public NabuccoList<ColumnExtension> getColumns() {
        if ((this.columns == null)) {
            this.columns = new NabuccoListImpl<ColumnExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.columns;
    }

    /**
     * The data filters
     *
     * @return the NabuccoList<FilterReferenceExtension>.
     */
    public NabuccoList<FilterReferenceExtension> getFilters() {
        if ((this.filters == null)) {
            this.filters = new NabuccoListImpl<FilterReferenceExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.filters;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ListExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ListExtension.class).getAllProperties();
    }
}
