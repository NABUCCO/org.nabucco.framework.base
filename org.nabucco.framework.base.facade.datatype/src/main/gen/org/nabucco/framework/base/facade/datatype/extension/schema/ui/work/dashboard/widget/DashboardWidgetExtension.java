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
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.dashboard.widget.DashboardFilterViewExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * DashboardWidgetExtension<p/>NABUCCO User Interface Dashboard Widget extension.<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2011-11-10
 */
public class DashboardWidgetExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;", "m1,1;", "m1,1;", "m1,1;", "m1,1;" };

    public static final String FILTERS = "filters";

    public static final String WIDGETTYPE = "widgetType";

    public static final String LABEL = "label";

    public static final String TOOLTIP = "tooltip";

    public static final String ICON = "icon";

    /** References to the configured filters */
    private NabuccoList<DashboardFilterViewExtension> filters;

    /** The type of the widget (BARGRAPF, PIECHART, TABLE etc) */
    public EnumerationProperty widgetType;

    /** The Work Item label. */
    private StringProperty label;

    /** The Work Item tooltip. */
    private StringProperty tooltip;

    /** The Work Item icon. */
    private StringProperty icon;

    /** Constructs a new DashboardWidgetExtension instance. */
    public DashboardWidgetExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the DashboardWidgetExtension.
     */
    protected void cloneObject(DashboardWidgetExtension clone) {
        super.cloneObject(clone);
        if ((this.filters != null)) {
            clone.filters = this.filters.cloneCollection();
        }
        if ((this.getWidgetType() != null)) {
            clone.setWidgetType(this.getWidgetType().cloneObject());
        }
        if ((this.getLabel() != null)) {
            clone.setLabel(this.getLabel().cloneObject());
        }
        if ((this.getTooltip() != null)) {
            clone.setTooltip(this.getTooltip().cloneObject());
        }
        if ((this.getIcon() != null)) {
            clone.setIcon(this.getIcon().cloneObject());
        }
    }

    /**
     * Getter for the FiltersJPA.
     *
     * @return the List<DashboardFilterViewExtension>.
     */
    List<DashboardFilterViewExtension> getFiltersJPA() {
        if ((this.filters == null)) {
            this.filters = new NabuccoListImpl<DashboardFilterViewExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<DashboardFilterViewExtension>) this.filters).getDelegate();
    }

    /**
     * Setter for the FiltersJPA.
     *
     * @param filters the List<DashboardFilterViewExtension>.
     */
    void setFiltersJPA(List<DashboardFilterViewExtension> filters) {
        if ((this.filters == null)) {
            this.filters = new NabuccoListImpl<DashboardFilterViewExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<DashboardFilterViewExtension>) this.filters).setDelegate(filters);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(UiExtension.class).getPropertyMap());
        propertyMap.put(FILTERS, PropertyDescriptorSupport.createCollection(FILTERS,
                DashboardFilterViewExtension.class, 4, PROPERTY_CONSTRAINTS[0], false,
                PropertyAssociationType.COMPOSITION));
        propertyMap.put(WIDGETTYPE, PropertyDescriptorSupport.createDatatype(WIDGETTYPE, EnumerationProperty.class, 5,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(LABEL, PropertyDescriptorSupport.createDatatype(LABEL, StringProperty.class, 6,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(TOOLTIP, PropertyDescriptorSupport.createDatatype(TOOLTIP, StringProperty.class, 7,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ICON, PropertyDescriptorSupport.createDatatype(ICON, StringProperty.class, 8,
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
        properties
                .add(super.createProperty(DashboardWidgetExtension.getPropertyDescriptor(FILTERS), this.filters, null));
        properties.add(super.createProperty(DashboardWidgetExtension.getPropertyDescriptor(WIDGETTYPE),
                this.getWidgetType(), null));
        properties.add(super.createProperty(DashboardWidgetExtension.getPropertyDescriptor(LABEL), this.getLabel(),
                null));
        properties.add(super.createProperty(DashboardWidgetExtension.getPropertyDescriptor(TOOLTIP), this.getTooltip(),
                null));
        properties
                .add(super.createProperty(DashboardWidgetExtension.getPropertyDescriptor(ICON), this.getIcon(), null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(FILTERS) && (property.getType() == DashboardFilterViewExtension.class))) {
            this.filters = ((NabuccoList<DashboardFilterViewExtension>) property.getInstance());
            return true;
        } else if ((property.getName().equals(WIDGETTYPE) && (property.getType() == EnumerationProperty.class))) {
            this.setWidgetType(((EnumerationProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LABEL) && (property.getType() == StringProperty.class))) {
            this.setLabel(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TOOLTIP) && (property.getType() == StringProperty.class))) {
            this.setTooltip(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ICON) && (property.getType() == StringProperty.class))) {
            this.setIcon(((StringProperty) property.getInstance()));
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
        final DashboardWidgetExtension other = ((DashboardWidgetExtension) obj);
        if ((this.widgetType == null)) {
            if ((other.widgetType != null))
                return false;
        } else if ((!this.widgetType.equals(other.widgetType)))
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
        if ((this.icon == null)) {
            if ((other.icon != null))
                return false;
        } else if ((!this.icon.equals(other.icon)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.widgetType == null) ? 0 : this.widgetType.hashCode()));
        result = ((PRIME * result) + ((this.label == null) ? 0 : this.label.hashCode()));
        result = ((PRIME * result) + ((this.tooltip == null) ? 0 : this.tooltip.hashCode()));
        result = ((PRIME * result) + ((this.icon == null) ? 0 : this.icon.hashCode()));
        return result;
    }

    @Override
    public DashboardWidgetExtension cloneObject() {
        DashboardWidgetExtension clone = new DashboardWidgetExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * References to the configured filters
     *
     * @return the NabuccoList<DashboardFilterViewExtension>.
     */
    public NabuccoList<DashboardFilterViewExtension> getFilters() {
        if ((this.filters == null)) {
            this.filters = new NabuccoListImpl<DashboardFilterViewExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.filters;
    }

    /**
     * The type of the widget (BARGRAPF, PIECHART, TABLE etc)
     *
     * @param widgetType the EnumerationProperty.
     */
    public void setWidgetType(EnumerationProperty widgetType) {
        this.widgetType = widgetType;
    }

    /**
     * The type of the widget (BARGRAPF, PIECHART, TABLE etc)
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getWidgetType() {
        return this.widgetType;
    }

    /**
     * The Work Item label.
     *
     * @param label the StringProperty.
     */
    public void setLabel(StringProperty label) {
        this.label = label;
    }

    /**
     * The Work Item label.
     *
     * @return the StringProperty.
     */
    public StringProperty getLabel() {
        return this.label;
    }

    /**
     * The Work Item tooltip.
     *
     * @param tooltip the StringProperty.
     */
    public void setTooltip(StringProperty tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * The Work Item tooltip.
     *
     * @return the StringProperty.
     */
    public StringProperty getTooltip() {
        return this.tooltip;
    }

    /**
     * The Work Item icon.
     *
     * @param icon the StringProperty.
     */
    public void setIcon(StringProperty icon) {
        this.icon = icon;
    }

    /**
     * The Work Item icon.
     *
     * @return the StringProperty.
     */
    public StringProperty getIcon() {
        return this.icon;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(DashboardWidgetExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(DashboardWidgetExtension.class).getAllProperties();
    }
}
