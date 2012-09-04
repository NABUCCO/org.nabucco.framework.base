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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.filter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.BooleanProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * FilterReferenceExtension<p/>Extension for the reference on the filter item<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2011-11-02
 */
public class FilterReferenceExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m0,1;", "m0,1;", "m1,1;", "m0,1;" };

    public static final String REFID = "refId";

    public static final String LABEL = "label";

    public static final String TOOLTIP = "tooltip";

    public static final String ISDEFAULT = "isDefault";

    public static final String LOADACTION = "loadAction";

    /** The id of the referenced filter. */
    private StringProperty refId;

    /** An optional label of the filter . */
    private StringProperty label;

    /** An optional tooltip of the filter . */
    private StringProperty tooltip;

    /** Define if the filter is default */
    private BooleanProperty isDefault;

    /** An optional load action to use by refreshing the filter data . */
    private StringProperty loadAction;

    /** Constructs a new FilterReferenceExtension instance. */
    public FilterReferenceExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the FilterReferenceExtension.
     */
    protected void cloneObject(FilterReferenceExtension clone) {
        super.cloneObject(clone);
        if ((this.getRefId() != null)) {
            clone.setRefId(this.getRefId().cloneObject());
        }
        if ((this.getLabel() != null)) {
            clone.setLabel(this.getLabel().cloneObject());
        }
        if ((this.getTooltip() != null)) {
            clone.setTooltip(this.getTooltip().cloneObject());
        }
        if ((this.getIsDefault() != null)) {
            clone.setIsDefault(this.getIsDefault().cloneObject());
        }
        if ((this.getLoadAction() != null)) {
            clone.setLoadAction(this.getLoadAction().cloneObject());
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
        propertyMap.put(REFID, PropertyDescriptorSupport.createDatatype(REFID, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(LABEL, PropertyDescriptorSupport.createDatatype(LABEL, StringProperty.class, 5,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(TOOLTIP, PropertyDescriptorSupport.createDatatype(TOOLTIP, StringProperty.class, 6,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ISDEFAULT, PropertyDescriptorSupport.createDatatype(ISDEFAULT, BooleanProperty.class, 7,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(LOADACTION, PropertyDescriptorSupport.createDatatype(LOADACTION, StringProperty.class, 8,
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
        properties.add(super.createProperty(FilterReferenceExtension.getPropertyDescriptor(REFID), this.getRefId(),
                null));
        properties.add(super.createProperty(FilterReferenceExtension.getPropertyDescriptor(LABEL), this.getLabel(),
                null));
        properties.add(super.createProperty(FilterReferenceExtension.getPropertyDescriptor(TOOLTIP), this.getTooltip(),
                null));
        properties.add(super.createProperty(FilterReferenceExtension.getPropertyDescriptor(ISDEFAULT),
                this.getIsDefault(), null));
        properties.add(super.createProperty(FilterReferenceExtension.getPropertyDescriptor(LOADACTION),
                this.getLoadAction(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(REFID) && (property.getType() == StringProperty.class))) {
            this.setRefId(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LABEL) && (property.getType() == StringProperty.class))) {
            this.setLabel(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TOOLTIP) && (property.getType() == StringProperty.class))) {
            this.setTooltip(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ISDEFAULT) && (property.getType() == BooleanProperty.class))) {
            this.setIsDefault(((BooleanProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LOADACTION) && (property.getType() == StringProperty.class))) {
            this.setLoadAction(((StringProperty) property.getInstance()));
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
        final FilterReferenceExtension other = ((FilterReferenceExtension) obj);
        if ((this.refId == null)) {
            if ((other.refId != null))
                return false;
        } else if ((!this.refId.equals(other.refId)))
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
        if ((this.isDefault == null)) {
            if ((other.isDefault != null))
                return false;
        } else if ((!this.isDefault.equals(other.isDefault)))
            return false;
        if ((this.loadAction == null)) {
            if ((other.loadAction != null))
                return false;
        } else if ((!this.loadAction.equals(other.loadAction)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.refId == null) ? 0 : this.refId.hashCode()));
        result = ((PRIME * result) + ((this.label == null) ? 0 : this.label.hashCode()));
        result = ((PRIME * result) + ((this.tooltip == null) ? 0 : this.tooltip.hashCode()));
        result = ((PRIME * result) + ((this.isDefault == null) ? 0 : this.isDefault.hashCode()));
        result = ((PRIME * result) + ((this.loadAction == null) ? 0 : this.loadAction.hashCode()));
        return result;
    }

    @Override
    public FilterReferenceExtension cloneObject() {
        FilterReferenceExtension clone = new FilterReferenceExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The id of the referenced filter.
     *
     * @param refId the StringProperty.
     */
    public void setRefId(StringProperty refId) {
        this.refId = refId;
    }

    /**
     * The id of the referenced filter.
     *
     * @return the StringProperty.
     */
    public StringProperty getRefId() {
        return this.refId;
    }

    /**
     * An optional label of the filter .
     *
     * @param label the StringProperty.
     */
    public void setLabel(StringProperty label) {
        this.label = label;
    }

    /**
     * An optional label of the filter .
     *
     * @return the StringProperty.
     */
    public StringProperty getLabel() {
        return this.label;
    }

    /**
     * An optional tooltip of the filter .
     *
     * @param tooltip the StringProperty.
     */
    public void setTooltip(StringProperty tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * An optional tooltip of the filter .
     *
     * @return the StringProperty.
     */
    public StringProperty getTooltip() {
        return this.tooltip;
    }

    /**
     * Define if the filter is default
     *
     * @param isDefault the BooleanProperty.
     */
    public void setIsDefault(BooleanProperty isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * Define if the filter is default
     *
     * @return the BooleanProperty.
     */
    public BooleanProperty getIsDefault() {
        return this.isDefault;
    }

    /**
     * An optional load action to use by refreshing the filter data .
     *
     * @param loadAction the StringProperty.
     */
    public void setLoadAction(StringProperty loadAction) {
        this.loadAction = loadAction;
    }

    /**
     * An optional load action to use by refreshing the filter data .
     *
     * @return the StringProperty.
     */
    public StringProperty getLoadAction() {
        return this.loadAction;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(FilterReferenceExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(FilterReferenceExtension.class).getAllProperties();
    }
}
