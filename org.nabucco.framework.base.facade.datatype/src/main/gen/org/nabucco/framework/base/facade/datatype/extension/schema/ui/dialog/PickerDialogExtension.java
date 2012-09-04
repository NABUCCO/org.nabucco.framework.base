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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.ButtonExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.ColumnExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.filter.FilterReferenceExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * PickerDialogExtension<p/>NABUCCO User Interface Editor Entry extension.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-07-28
 */
public class PickerDialogExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m0,1;", "m0,n;", "m0,n;", "m0,n;",
            "m1,1;" };

    public static final String TITLE = "title";

    public static final String MESSAGE = "message";

    public static final String DOUBLECLICKACTION = "doubleclickAction";

    public static final String COLUMNS = "columns";

    public static final String BUTTONS = "buttons";

    public static final String FILTERS = "filters";

    public static final String PICKERTYPE = "pickerType";

    /** Title of the dialog */
    private StringProperty title;

    /** Message of the dialog */
    private StringProperty message;

    /** The action to be used to open content items */
    private StringProperty doubleclickAction;

    /** Columns to show in the table */
    private NabuccoList<ColumnExtension> columns;

    /** Actions of the picker */
    private NabuccoList<ButtonExtension> buttons;

    /** The Picker filters */
    private NabuccoList<FilterReferenceExtension> filters;

    /** Type of the picker (Table, Tree etc) */
    private StringProperty pickerType;

    /** Constructs a new PickerDialogExtension instance. */
    public PickerDialogExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the PickerDialogExtension.
     */
    protected void cloneObject(PickerDialogExtension clone) {
        super.cloneObject(clone);
        if ((this.getTitle() != null)) {
            clone.setTitle(this.getTitle().cloneObject());
        }
        if ((this.getMessage() != null)) {
            clone.setMessage(this.getMessage().cloneObject());
        }
        if ((this.getDoubleclickAction() != null)) {
            clone.setDoubleclickAction(this.getDoubleclickAction().cloneObject());
        }
        if ((this.columns != null)) {
            clone.columns = this.columns.cloneCollection();
        }
        if ((this.buttons != null)) {
            clone.buttons = this.buttons.cloneCollection();
        }
        if ((this.filters != null)) {
            clone.filters = this.filters.cloneCollection();
        }
        if ((this.getPickerType() != null)) {
            clone.setPickerType(this.getPickerType().cloneObject());
        }
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
     * Getter for the ButtonsJPA.
     *
     * @return the List<ButtonExtension>.
     */
    List<ButtonExtension> getButtonsJPA() {
        if ((this.buttons == null)) {
            this.buttons = new NabuccoListImpl<ButtonExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<ButtonExtension>) this.buttons).getDelegate();
    }

    /**
     * Setter for the ButtonsJPA.
     *
     * @param buttons the List<ButtonExtension>.
     */
    void setButtonsJPA(List<ButtonExtension> buttons) {
        if ((this.buttons == null)) {
            this.buttons = new NabuccoListImpl<ButtonExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<ButtonExtension>) this.buttons).setDelegate(buttons);
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
        propertyMap.putAll(PropertyCache.getInstance().retrieve(UiExtension.class).getPropertyMap());
        propertyMap.put(TITLE, PropertyDescriptorSupport.createDatatype(TITLE, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(MESSAGE, PropertyDescriptorSupport.createDatatype(MESSAGE, StringProperty.class, 5,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(DOUBLECLICKACTION, PropertyDescriptorSupport.createDatatype(DOUBLECLICKACTION,
                StringProperty.class, 6, PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(COLUMNS, PropertyDescriptorSupport.createCollection(COLUMNS, ColumnExtension.class, 7,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(BUTTONS, PropertyDescriptorSupport.createCollection(BUTTONS, ButtonExtension.class, 8,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(FILTERS, PropertyDescriptorSupport.createCollection(FILTERS, FilterReferenceExtension.class, 9,
                PROPERTY_CONSTRAINTS[5], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(PICKERTYPE, PropertyDescriptorSupport.createDatatype(PICKERTYPE, StringProperty.class, 10,
                PROPERTY_CONSTRAINTS[6], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(PickerDialogExtension.getPropertyDescriptor(TITLE), this.getTitle(), null));
        properties.add(super.createProperty(PickerDialogExtension.getPropertyDescriptor(MESSAGE), this.getMessage(),
                null));
        properties.add(super.createProperty(PickerDialogExtension.getPropertyDescriptor(DOUBLECLICKACTION),
                this.getDoubleclickAction(), null));
        properties.add(super.createProperty(PickerDialogExtension.getPropertyDescriptor(COLUMNS), this.columns, null));
        properties.add(super.createProperty(PickerDialogExtension.getPropertyDescriptor(BUTTONS), this.buttons, null));
        properties.add(super.createProperty(PickerDialogExtension.getPropertyDescriptor(FILTERS), this.filters, null));
        properties.add(super.createProperty(PickerDialogExtension.getPropertyDescriptor(PICKERTYPE),
                this.getPickerType(), null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(TITLE) && (property.getType() == StringProperty.class))) {
            this.setTitle(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MESSAGE) && (property.getType() == StringProperty.class))) {
            this.setMessage(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DOUBLECLICKACTION) && (property.getType() == StringProperty.class))) {
            this.setDoubleclickAction(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(COLUMNS) && (property.getType() == ColumnExtension.class))) {
            this.columns = ((NabuccoList<ColumnExtension>) property.getInstance());
            return true;
        } else if ((property.getName().equals(BUTTONS) && (property.getType() == ButtonExtension.class))) {
            this.buttons = ((NabuccoList<ButtonExtension>) property.getInstance());
            return true;
        } else if ((property.getName().equals(FILTERS) && (property.getType() == FilterReferenceExtension.class))) {
            this.filters = ((NabuccoList<FilterReferenceExtension>) property.getInstance());
            return true;
        } else if ((property.getName().equals(PICKERTYPE) && (property.getType() == StringProperty.class))) {
            this.setPickerType(((StringProperty) property.getInstance()));
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
        final PickerDialogExtension other = ((PickerDialogExtension) obj);
        if ((this.title == null)) {
            if ((other.title != null))
                return false;
        } else if ((!this.title.equals(other.title)))
            return false;
        if ((this.message == null)) {
            if ((other.message != null))
                return false;
        } else if ((!this.message.equals(other.message)))
            return false;
        if ((this.doubleclickAction == null)) {
            if ((other.doubleclickAction != null))
                return false;
        } else if ((!this.doubleclickAction.equals(other.doubleclickAction)))
            return false;
        if ((this.pickerType == null)) {
            if ((other.pickerType != null))
                return false;
        } else if ((!this.pickerType.equals(other.pickerType)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.title == null) ? 0 : this.title.hashCode()));
        result = ((PRIME * result) + ((this.message == null) ? 0 : this.message.hashCode()));
        result = ((PRIME * result) + ((this.doubleclickAction == null) ? 0 : this.doubleclickAction.hashCode()));
        result = ((PRIME * result) + ((this.pickerType == null) ? 0 : this.pickerType.hashCode()));
        return result;
    }

    @Override
    public PickerDialogExtension cloneObject() {
        PickerDialogExtension clone = new PickerDialogExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Title of the dialog
     *
     * @param title the StringProperty.
     */
    public void setTitle(StringProperty title) {
        this.title = title;
    }

    /**
     * Title of the dialog
     *
     * @return the StringProperty.
     */
    public StringProperty getTitle() {
        return this.title;
    }

    /**
     * Message of the dialog
     *
     * @param message the StringProperty.
     */
    public void setMessage(StringProperty message) {
        this.message = message;
    }

    /**
     * Message of the dialog
     *
     * @return the StringProperty.
     */
    public StringProperty getMessage() {
        return this.message;
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
     * Columns to show in the table
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
     * Actions of the picker
     *
     * @return the NabuccoList<ButtonExtension>.
     */
    public NabuccoList<ButtonExtension> getButtons() {
        if ((this.buttons == null)) {
            this.buttons = new NabuccoListImpl<ButtonExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.buttons;
    }

    /**
     * The Picker filters
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
     * Type of the picker (Table, Tree etc)
     *
     * @param pickerType the StringProperty.
     */
    public void setPickerType(StringProperty pickerType) {
        this.pickerType = pickerType;
    }

    /**
     * Type of the picker (Table, Tree etc)
     *
     * @return the StringProperty.
     */
    public StringProperty getPickerType() {
        return this.pickerType;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(PickerDialogExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(PickerDialogExtension.class).getAllProperties();
    }
}
