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
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * ButtonExtension<p/>NABUCCO User Interface Action Reference extension.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-07-20
 */
public class ButtonExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m0,1;", "m1,1;", "m0,1;", "m0,1;" };

    public static final String ACTIONID = "actionId";

    public static final String PARAMETER = "parameter";

    public static final String LABEL = "label";

    public static final String TOOLTIP = "tooltip";

    public static final String ICON = "icon";

    /** The Action Reference ID. */
    private StringProperty actionId;

    /** The optional parameter that can be set to the button (url etc.) The parameter is action specifical */
    private StringProperty parameter;

    /** The Action Reference Label. */
    private StringProperty label;

    /** The Action Reference Tooltip. */
    private StringProperty tooltip;

    /** The Action Reference Icon. */
    private StringProperty icon;

    /** Constructs a new ButtonExtension instance. */
    public ButtonExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ButtonExtension.
     */
    protected void cloneObject(ButtonExtension clone) {
        super.cloneObject(clone);
        if ((this.getActionId() != null)) {
            clone.setActionId(this.getActionId().cloneObject());
        }
        if ((this.getParameter() != null)) {
            clone.setParameter(this.getParameter().cloneObject());
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
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(UiExtension.class).getPropertyMap());
        propertyMap.put(ACTIONID, PropertyDescriptorSupport.createDatatype(ACTIONID, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(PARAMETER, PropertyDescriptorSupport.createDatatype(PARAMETER, StringProperty.class, 5,
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
        properties.add(super.createProperty(ButtonExtension.getPropertyDescriptor(ACTIONID), this.getActionId(), null));
        properties
                .add(super.createProperty(ButtonExtension.getPropertyDescriptor(PARAMETER), this.getParameter(), null));
        properties.add(super.createProperty(ButtonExtension.getPropertyDescriptor(LABEL), this.getLabel(), null));
        properties.add(super.createProperty(ButtonExtension.getPropertyDescriptor(TOOLTIP), this.getTooltip(), null));
        properties.add(super.createProperty(ButtonExtension.getPropertyDescriptor(ICON), this.getIcon(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(ACTIONID) && (property.getType() == StringProperty.class))) {
            this.setActionId(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PARAMETER) && (property.getType() == StringProperty.class))) {
            this.setParameter(((StringProperty) property.getInstance()));
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
        final ButtonExtension other = ((ButtonExtension) obj);
        if ((this.actionId == null)) {
            if ((other.actionId != null))
                return false;
        } else if ((!this.actionId.equals(other.actionId)))
            return false;
        if ((this.parameter == null)) {
            if ((other.parameter != null))
                return false;
        } else if ((!this.parameter.equals(other.parameter)))
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
        result = ((PRIME * result) + ((this.actionId == null) ? 0 : this.actionId.hashCode()));
        result = ((PRIME * result) + ((this.parameter == null) ? 0 : this.parameter.hashCode()));
        result = ((PRIME * result) + ((this.label == null) ? 0 : this.label.hashCode()));
        result = ((PRIME * result) + ((this.tooltip == null) ? 0 : this.tooltip.hashCode()));
        result = ((PRIME * result) + ((this.icon == null) ? 0 : this.icon.hashCode()));
        return result;
    }

    @Override
    public ButtonExtension cloneObject() {
        ButtonExtension clone = new ButtonExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The Action Reference ID.
     *
     * @param actionId the StringProperty.
     */
    public void setActionId(StringProperty actionId) {
        this.actionId = actionId;
    }

    /**
     * The Action Reference ID.
     *
     * @return the StringProperty.
     */
    public StringProperty getActionId() {
        return this.actionId;
    }

    /**
     * The optional parameter that can be set to the button (url etc.) The parameter is action specifical
     *
     * @param parameter the StringProperty.
     */
    public void setParameter(StringProperty parameter) {
        this.parameter = parameter;
    }

    /**
     * The optional parameter that can be set to the button (url etc.) The parameter is action specifical
     *
     * @return the StringProperty.
     */
    public StringProperty getParameter() {
        return this.parameter;
    }

    /**
     * The Action Reference Label.
     *
     * @param label the StringProperty.
     */
    public void setLabel(StringProperty label) {
        this.label = label;
    }

    /**
     * The Action Reference Label.
     *
     * @return the StringProperty.
     */
    public StringProperty getLabel() {
        return this.label;
    }

    /**
     * The Action Reference Tooltip.
     *
     * @param tooltip the StringProperty.
     */
    public void setTooltip(StringProperty tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * The Action Reference Tooltip.
     *
     * @return the StringProperty.
     */
    public StringProperty getTooltip() {
        return this.tooltip;
    }

    /**
     * The Action Reference Icon.
     *
     * @param icon the StringProperty.
     */
    public void setIcon(StringProperty icon) {
        this.icon = icon;
    }

    /**
     * The Action Reference Icon.
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
        return PropertyCache.getInstance().retrieve(ButtonExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ButtonExtension.class).getAllProperties();
    }
}
