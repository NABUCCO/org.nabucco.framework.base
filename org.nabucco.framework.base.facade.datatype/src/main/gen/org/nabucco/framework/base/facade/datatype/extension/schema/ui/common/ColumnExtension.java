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
import org.nabucco.framework.base.facade.datatype.extension.property.BooleanProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.ClassProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * ColumnExtension<p/>NABUCCO User Interface List Column extension.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-08-05
 */
public class ColumnExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m0,1;",
            "m0,1;", "m0,1;" };

    public static final String PROPERTY = "property";

    public static final String LABEL = "label";

    public static final String TOOLTIP = "tooltip";

    public static final String VISIBLE = "visible";

    public static final String SORTABLE = "sortable";

    public static final String RENDERER = "renderer";

    public static final String LAYOUT = "layout";

    public static final String WIDTH = "width";

    /** The Displayed Datatype Property. */
    private StringProperty property;

    /** The Column Label. */
    private StringProperty label;

    /** The Column Tooltip. */
    private StringProperty tooltip;

    /** Whether the column is visible or not. */
    private BooleanProperty visible;

    /** Whether the column is sortable or not. */
    private BooleanProperty sortable;

    /** Renderer for the displaying of data */
    private ClassProperty renderer;

    /** Visualisation type (Percentage etc.) */
    private EnumerationProperty layout;

    /** The width of the column 0..100 (%) */
    private StringProperty width;

    /** Constructs a new ColumnExtension instance. */
    public ColumnExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ColumnExtension.
     */
    protected void cloneObject(ColumnExtension clone) {
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
        if ((this.getVisible() != null)) {
            clone.setVisible(this.getVisible().cloneObject());
        }
        if ((this.getSortable() != null)) {
            clone.setSortable(this.getSortable().cloneObject());
        }
        if ((this.getRenderer() != null)) {
            clone.setRenderer(this.getRenderer().cloneObject());
        }
        if ((this.getLayout() != null)) {
            clone.setLayout(this.getLayout().cloneObject());
        }
        if ((this.getWidth() != null)) {
            clone.setWidth(this.getWidth().cloneObject());
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
        propertyMap.put(VISIBLE, PropertyDescriptorSupport.createDatatype(VISIBLE, BooleanProperty.class, 7,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(SORTABLE, PropertyDescriptorSupport.createDatatype(SORTABLE, BooleanProperty.class, 8,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(RENDERER, PropertyDescriptorSupport.createDatatype(RENDERER, ClassProperty.class, 9,
                PROPERTY_CONSTRAINTS[5], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(LAYOUT, PropertyDescriptorSupport.createDatatype(LAYOUT, EnumerationProperty.class, 10,
                PROPERTY_CONSTRAINTS[6], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(WIDTH, PropertyDescriptorSupport.createDatatype(WIDTH, StringProperty.class, 11,
                PROPERTY_CONSTRAINTS[7], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ColumnExtension.getPropertyDescriptor(PROPERTY), this.getProperty(), null));
        properties.add(super.createProperty(ColumnExtension.getPropertyDescriptor(LABEL), this.getLabel(), null));
        properties.add(super.createProperty(ColumnExtension.getPropertyDescriptor(TOOLTIP), this.getTooltip(), null));
        properties.add(super.createProperty(ColumnExtension.getPropertyDescriptor(VISIBLE), this.getVisible(), null));
        properties.add(super.createProperty(ColumnExtension.getPropertyDescriptor(SORTABLE), this.getSortable(), null));
        properties.add(super.createProperty(ColumnExtension.getPropertyDescriptor(RENDERER), this.getRenderer(), null));
        properties.add(super.createProperty(ColumnExtension.getPropertyDescriptor(LAYOUT), this.getLayout(), null));
        properties.add(super.createProperty(ColumnExtension.getPropertyDescriptor(WIDTH), this.getWidth(), null));
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
        } else if ((property.getName().equals(VISIBLE) && (property.getType() == BooleanProperty.class))) {
            this.setVisible(((BooleanProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SORTABLE) && (property.getType() == BooleanProperty.class))) {
            this.setSortable(((BooleanProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(RENDERER) && (property.getType() == ClassProperty.class))) {
            this.setRenderer(((ClassProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LAYOUT) && (property.getType() == EnumerationProperty.class))) {
            this.setLayout(((EnumerationProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(WIDTH) && (property.getType() == StringProperty.class))) {
            this.setWidth(((StringProperty) property.getInstance()));
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
        final ColumnExtension other = ((ColumnExtension) obj);
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
        if ((this.visible == null)) {
            if ((other.visible != null))
                return false;
        } else if ((!this.visible.equals(other.visible)))
            return false;
        if ((this.sortable == null)) {
            if ((other.sortable != null))
                return false;
        } else if ((!this.sortable.equals(other.sortable)))
            return false;
        if ((this.renderer == null)) {
            if ((other.renderer != null))
                return false;
        } else if ((!this.renderer.equals(other.renderer)))
            return false;
        if ((this.layout == null)) {
            if ((other.layout != null))
                return false;
        } else if ((!this.layout.equals(other.layout)))
            return false;
        if ((this.width == null)) {
            if ((other.width != null))
                return false;
        } else if ((!this.width.equals(other.width)))
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
        result = ((PRIME * result) + ((this.visible == null) ? 0 : this.visible.hashCode()));
        result = ((PRIME * result) + ((this.sortable == null) ? 0 : this.sortable.hashCode()));
        result = ((PRIME * result) + ((this.renderer == null) ? 0 : this.renderer.hashCode()));
        result = ((PRIME * result) + ((this.layout == null) ? 0 : this.layout.hashCode()));
        result = ((PRIME * result) + ((this.width == null) ? 0 : this.width.hashCode()));
        return result;
    }

    @Override
    public ColumnExtension cloneObject() {
        ColumnExtension clone = new ColumnExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The Displayed Datatype Property.
     *
     * @param property the StringProperty.
     */
    public void setProperty(StringProperty property) {
        this.property = property;
    }

    /**
     * The Displayed Datatype Property.
     *
     * @return the StringProperty.
     */
    public StringProperty getProperty() {
        return this.property;
    }

    /**
     * The Column Label.
     *
     * @param label the StringProperty.
     */
    public void setLabel(StringProperty label) {
        this.label = label;
    }

    /**
     * The Column Label.
     *
     * @return the StringProperty.
     */
    public StringProperty getLabel() {
        return this.label;
    }

    /**
     * The Column Tooltip.
     *
     * @param tooltip the StringProperty.
     */
    public void setTooltip(StringProperty tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * The Column Tooltip.
     *
     * @return the StringProperty.
     */
    public StringProperty getTooltip() {
        return this.tooltip;
    }

    /**
     * Whether the column is visible or not.
     *
     * @param visible the BooleanProperty.
     */
    public void setVisible(BooleanProperty visible) {
        this.visible = visible;
    }

    /**
     * Whether the column is visible or not.
     *
     * @return the BooleanProperty.
     */
    public BooleanProperty getVisible() {
        return this.visible;
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
     * Renderer for the displaying of data
     *
     * @param renderer the ClassProperty.
     */
    public void setRenderer(ClassProperty renderer) {
        this.renderer = renderer;
    }

    /**
     * Renderer for the displaying of data
     *
     * @return the ClassProperty.
     */
    public ClassProperty getRenderer() {
        return this.renderer;
    }

    /**
     * Visualisation type (Percentage etc.)
     *
     * @param layout the EnumerationProperty.
     */
    public void setLayout(EnumerationProperty layout) {
        this.layout = layout;
    }

    /**
     * Visualisation type (Percentage etc.)
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getLayout() {
        return this.layout;
    }

    /**
     * The width of the column 0..100 (%)
     *
     * @param width the StringProperty.
     */
    public void setWidth(StringProperty width) {
        this.width = width;
    }

    /**
     * The width of the column 0..100 (%)
     *
     * @return the StringProperty.
     */
    public StringProperty getWidth() {
        return this.width;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ColumnExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ColumnExtension.class).getAllProperties();
    }
}
