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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.bulkeditor.columns;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.BooleanProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.IntegerProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.dependency.DependencySetExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * BulkEditorColumnExtension<p/>NABUCCO User Interface Editor Entry extension.<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-04-11
 */
public abstract class BulkEditorColumnExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m1,1;",
            "m0,1;", "m0,1;" };

    public static final String PROPERTY = "property";

    public static final String LABEL = "label";

    public static final String TOOLTIP = "tooltip";

    public static final String EDITABLE = "editable";

    public static final String SORTABLE = "sortable";

    public static final String TYPE = "type";

    public static final String WIDTH = "width";

    public static final String DEPENDENCYSET = "dependencySet";

    /** The path to the binded property. */
    private StringProperty property;

    /** The Control label. */
    private StringProperty label;

    /** The Control tooltip. */
    private StringProperty tooltip;

    /** Is the control editable */
    private BooleanProperty editable;

    /** Whether the column is sortable or not. */
    private BooleanProperty sortable;

    /** the type of the column */
    private EnumerationProperty type;

    /** the width of the column */
    private IntegerProperty width;

    /** The set of the dependencies of the control */
    private DependencySetExtension dependencySet;

    /** Constructs a new BulkEditorColumnExtension instance. */
    public BulkEditorColumnExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the BulkEditorColumnExtension.
     */
    protected void cloneObject(BulkEditorColumnExtension clone) {
        super.cloneObject(clone);
        if ((this.getProperty() != null)) {
            clone.setProperty(this.getProperty().cloneObject());
        }
        if ((this.getLabel() != null)) {
            clone.setLabel(this.getLabel().cloneObject());
        }
        if ((this.getTooltip() != null)) {
            clone.setTooltip(this.getTooltip().cloneObject());
        }
        if ((this.getEditable() != null)) {
            clone.setEditable(this.getEditable().cloneObject());
        }
        if ((this.getSortable() != null)) {
            clone.setSortable(this.getSortable().cloneObject());
        }
        if ((this.getType() != null)) {
            clone.setType(this.getType().cloneObject());
        }
        if ((this.getWidth() != null)) {
            clone.setWidth(this.getWidth().cloneObject());
        }
        if ((this.getDependencySet() != null)) {
            clone.setDependencySet(this.getDependencySet().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(UiExtension.class).getPropertyMap());
        propertyMap.put(PROPERTY, PropertyDescriptorSupport.createDatatype(PROPERTY, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(LABEL, PropertyDescriptorSupport.createDatatype(LABEL, StringProperty.class, 5,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(TOOLTIP, PropertyDescriptorSupport.createDatatype(TOOLTIP, StringProperty.class, 6,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(EDITABLE, PropertyDescriptorSupport.createDatatype(EDITABLE, BooleanProperty.class, 7,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(SORTABLE, PropertyDescriptorSupport.createDatatype(SORTABLE, BooleanProperty.class, 8,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(TYPE, PropertyDescriptorSupport.createDatatype(TYPE, EnumerationProperty.class, 9,
                PROPERTY_CONSTRAINTS[5], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(WIDTH, PropertyDescriptorSupport.createDatatype(WIDTH, IntegerProperty.class, 10,
                PROPERTY_CONSTRAINTS[6], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(DEPENDENCYSET, PropertyDescriptorSupport.createDatatype(DEPENDENCYSET,
                DependencySetExtension.class, 11, PROPERTY_CONSTRAINTS[7], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(BulkEditorColumnExtension.getPropertyDescriptor(PROPERTY),
                this.getProperty(), null));
        properties.add(super.createProperty(BulkEditorColumnExtension.getPropertyDescriptor(LABEL), this.getLabel(),
                null));
        properties.add(super.createProperty(BulkEditorColumnExtension.getPropertyDescriptor(TOOLTIP),
                this.getTooltip(), null));
        properties.add(super.createProperty(BulkEditorColumnExtension.getPropertyDescriptor(EDITABLE),
                this.getEditable(), null));
        properties.add(super.createProperty(BulkEditorColumnExtension.getPropertyDescriptor(SORTABLE),
                this.getSortable(), null));
        properties
                .add(super.createProperty(BulkEditorColumnExtension.getPropertyDescriptor(TYPE), this.getType(), null));
        properties.add(super.createProperty(BulkEditorColumnExtension.getPropertyDescriptor(WIDTH), this.getWidth(),
                null));
        properties.add(super.createProperty(BulkEditorColumnExtension.getPropertyDescriptor(DEPENDENCYSET),
                this.getDependencySet(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(PROPERTY) && (property.getType() == StringProperty.class))) {
            this.setProperty(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LABEL) && (property.getType() == StringProperty.class))) {
            this.setLabel(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TOOLTIP) && (property.getType() == StringProperty.class))) {
            this.setTooltip(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(EDITABLE) && (property.getType() == BooleanProperty.class))) {
            this.setEditable(((BooleanProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SORTABLE) && (property.getType() == BooleanProperty.class))) {
            this.setSortable(((BooleanProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TYPE) && (property.getType() == EnumerationProperty.class))) {
            this.setType(((EnumerationProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(WIDTH) && (property.getType() == IntegerProperty.class))) {
            this.setWidth(((IntegerProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DEPENDENCYSET) && (property.getType() == DependencySetExtension.class))) {
            this.setDependencySet(((DependencySetExtension) property.getInstance()));
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
        final BulkEditorColumnExtension other = ((BulkEditorColumnExtension) obj);
        if ((this.property == null)) {
            if ((other.property != null))
                return false;
        } else if ((!this.property.equals(other.property)))
            return false;
        if ((this.label == null)) {
            if ((other.label != null))
                return false;
        } else if ((!this.label.equals(other.label)))
            return false;
        if ((this.tooltip == null)) {
            if ((other.tooltip != null))
                return false;
        } else if ((!this.tooltip.equals(other.tooltip)))
            return false;
        if ((this.editable == null)) {
            if ((other.editable != null))
                return false;
        } else if ((!this.editable.equals(other.editable)))
            return false;
        if ((this.sortable == null)) {
            if ((other.sortable != null))
                return false;
        } else if ((!this.sortable.equals(other.sortable)))
            return false;
        if ((this.type == null)) {
            if ((other.type != null))
                return false;
        } else if ((!this.type.equals(other.type)))
            return false;
        if ((this.width == null)) {
            if ((other.width != null))
                return false;
        } else if ((!this.width.equals(other.width)))
            return false;
        if ((this.dependencySet == null)) {
            if ((other.dependencySet != null))
                return false;
        } else if ((!this.dependencySet.equals(other.dependencySet)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.property == null) ? 0 : this.property.hashCode()));
        result = ((PRIME * result) + ((this.label == null) ? 0 : this.label.hashCode()));
        result = ((PRIME * result) + ((this.tooltip == null) ? 0 : this.tooltip.hashCode()));
        result = ((PRIME * result) + ((this.editable == null) ? 0 : this.editable.hashCode()));
        result = ((PRIME * result) + ((this.sortable == null) ? 0 : this.sortable.hashCode()));
        result = ((PRIME * result) + ((this.type == null) ? 0 : this.type.hashCode()));
        result = ((PRIME * result) + ((this.width == null) ? 0 : this.width.hashCode()));
        result = ((PRIME * result) + ((this.dependencySet == null) ? 0 : this.dependencySet.hashCode()));
        return result;
    }

    @Override
    public abstract BulkEditorColumnExtension cloneObject();

    /**
     * The path to the binded property.
     *
     * @param property the StringProperty.
     */
    public void setProperty(StringProperty property) {
        this.property = property;
    }

    /**
     * The path to the binded property.
     *
     * @return the StringProperty.
     */
    public StringProperty getProperty() {
        return this.property;
    }

    /**
     * The Control label.
     *
     * @param label the StringProperty.
     */
    public void setLabel(StringProperty label) {
        this.label = label;
    }

    /**
     * The Control label.
     *
     * @return the StringProperty.
     */
    public StringProperty getLabel() {
        return this.label;
    }

    /**
     * The Control tooltip.
     *
     * @param tooltip the StringProperty.
     */
    public void setTooltip(StringProperty tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * The Control tooltip.
     *
     * @return the StringProperty.
     */
    public StringProperty getTooltip() {
        return this.tooltip;
    }

    /**
     * Is the control editable
     *
     * @param editable the BooleanProperty.
     */
    public void setEditable(BooleanProperty editable) {
        this.editable = editable;
    }

    /**
     * Is the control editable
     *
     * @return the BooleanProperty.
     */
    public BooleanProperty getEditable() {
        return this.editable;
    }

    /**
     * Whether the column is sortable or not.
     *
     * @param sortable the BooleanProperty.
     */
    public void setSortable(BooleanProperty sortable) {
        this.sortable = sortable;
    }

    /**
     * Whether the column is sortable or not.
     *
     * @return the BooleanProperty.
     */
    public BooleanProperty getSortable() {
        return this.sortable;
    }

    /**
     * the type of the column
     *
     * @param type the EnumerationProperty.
     */
    public void setType(EnumerationProperty type) {
        this.type = type;
    }

    /**
     * the type of the column
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getType() {
        return this.type;
    }

    /**
     * the width of the column
     *
     * @param width the IntegerProperty.
     */
    public void setWidth(IntegerProperty width) {
        this.width = width;
    }

    /**
     * the width of the column
     *
     * @return the IntegerProperty.
     */
    public IntegerProperty getWidth() {
        return this.width;
    }

    /**
     * The set of the dependencies of the control
     *
     * @param dependencySet the DependencySetExtension.
     */
    public void setDependencySet(DependencySetExtension dependencySet) {
        this.dependencySet = dependencySet;
    }

    /**
     * The set of the dependencies of the control
     *
     * @return the DependencySetExtension.
     */
    public DependencySetExtension getDependencySet() {
        return this.dependencySet;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(BulkEditorColumnExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(BulkEditorColumnExtension.class).getAllProperties();
    }
}
