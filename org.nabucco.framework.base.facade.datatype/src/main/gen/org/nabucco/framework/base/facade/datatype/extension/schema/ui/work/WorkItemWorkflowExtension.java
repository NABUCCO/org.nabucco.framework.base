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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.work;

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
 * WorkItemWorkflowExtension<p/>NABUCCO User Interface Workflow extension.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-10-12
 */
public class WorkItemWorkflowExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m1,1;" };

    public static final String LABEL = "label";

    public static final String TOOLTIP = "tooltip";

    public static final String NOWORKFLOWLABEL = "noWorkflowLabel";

    public static final String SIGNALICON = "signalIcon";

    public static final String WORKFLOWICON = "workflowIcon";

    public static final String ACTIONID = "actionId";

    /** The Workflow label. */
    private StringProperty label;

    /** The Workflow tooltip. */
    private StringProperty tooltip;

    /** The Label when no workflow is available. */
    private StringProperty noWorkflowLabel;

    /** The Workflow Signal icon. */
    private StringProperty signalIcon;

    /** The Workflow icon. */
    private StringProperty workflowIcon;

    /** The Workflow Action Reference ID. */
    private StringProperty actionId;

    /** Constructs a new WorkItemWorkflowExtension instance. */
    public WorkItemWorkflowExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the WorkItemWorkflowExtension.
     */
    protected void cloneObject(WorkItemWorkflowExtension clone) {
        super.cloneObject(clone);
        if ((this.getLabel() != null)) {
            clone.setLabel(this.getLabel().cloneObject());
        }
        if ((this.getTooltip() != null)) {
            clone.setTooltip(this.getTooltip().cloneObject());
        }
        if ((this.getNoWorkflowLabel() != null)) {
            clone.setNoWorkflowLabel(this.getNoWorkflowLabel().cloneObject());
        }
        if ((this.getSignalIcon() != null)) {
            clone.setSignalIcon(this.getSignalIcon().cloneObject());
        }
        if ((this.getWorkflowIcon() != null)) {
            clone.setWorkflowIcon(this.getWorkflowIcon().cloneObject());
        }
        if ((this.getActionId() != null)) {
            clone.setActionId(this.getActionId().cloneObject());
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
        propertyMap.put(LABEL, PropertyDescriptorSupport.createDatatype(LABEL, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(TOOLTIP, PropertyDescriptorSupport.createDatatype(TOOLTIP, StringProperty.class, 5,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(NOWORKFLOWLABEL, PropertyDescriptorSupport.createDatatype(NOWORKFLOWLABEL,
                StringProperty.class, 6, PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(SIGNALICON, PropertyDescriptorSupport.createDatatype(SIGNALICON, StringProperty.class, 7,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(WORKFLOWICON, PropertyDescriptorSupport.createDatatype(WORKFLOWICON, StringProperty.class, 8,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ACTIONID, PropertyDescriptorSupport.createDatatype(ACTIONID, StringProperty.class, 9,
                PROPERTY_CONSTRAINTS[5], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(WorkItemWorkflowExtension.getPropertyDescriptor(LABEL), this.getLabel(),
                null));
        properties.add(super.createProperty(WorkItemWorkflowExtension.getPropertyDescriptor(TOOLTIP),
                this.getTooltip(), null));
        properties.add(super.createProperty(WorkItemWorkflowExtension.getPropertyDescriptor(NOWORKFLOWLABEL),
                this.getNoWorkflowLabel(), null));
        properties.add(super.createProperty(WorkItemWorkflowExtension.getPropertyDescriptor(SIGNALICON),
                this.getSignalIcon(), null));
        properties.add(super.createProperty(WorkItemWorkflowExtension.getPropertyDescriptor(WORKFLOWICON),
                this.getWorkflowIcon(), null));
        properties.add(super.createProperty(WorkItemWorkflowExtension.getPropertyDescriptor(ACTIONID),
                this.getActionId(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(LABEL) && (property.getType() == StringProperty.class))) {
            this.setLabel(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TOOLTIP) && (property.getType() == StringProperty.class))) {
            this.setTooltip(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NOWORKFLOWLABEL) && (property.getType() == StringProperty.class))) {
            this.setNoWorkflowLabel(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SIGNALICON) && (property.getType() == StringProperty.class))) {
            this.setSignalIcon(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(WORKFLOWICON) && (property.getType() == StringProperty.class))) {
            this.setWorkflowIcon(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ACTIONID) && (property.getType() == StringProperty.class))) {
            this.setActionId(((StringProperty) property.getInstance()));
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
        final WorkItemWorkflowExtension other = ((WorkItemWorkflowExtension) obj);
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
        if ((this.noWorkflowLabel == null)) {
            if ((other.noWorkflowLabel != null))
                return false;
        } else if ((!this.noWorkflowLabel.equals(other.noWorkflowLabel)))
            return false;
        if ((this.signalIcon == null)) {
            if ((other.signalIcon != null))
                return false;
        } else if ((!this.signalIcon.equals(other.signalIcon)))
            return false;
        if ((this.workflowIcon == null)) {
            if ((other.workflowIcon != null))
                return false;
        } else if ((!this.workflowIcon.equals(other.workflowIcon)))
            return false;
        if ((this.actionId == null)) {
            if ((other.actionId != null))
                return false;
        } else if ((!this.actionId.equals(other.actionId)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.label == null) ? 0 : this.label.hashCode()));
        result = ((PRIME * result) + ((this.tooltip == null) ? 0 : this.tooltip.hashCode()));
        result = ((PRIME * result) + ((this.noWorkflowLabel == null) ? 0 : this.noWorkflowLabel.hashCode()));
        result = ((PRIME * result) + ((this.signalIcon == null) ? 0 : this.signalIcon.hashCode()));
        result = ((PRIME * result) + ((this.workflowIcon == null) ? 0 : this.workflowIcon.hashCode()));
        result = ((PRIME * result) + ((this.actionId == null) ? 0 : this.actionId.hashCode()));
        return result;
    }

    @Override
    public WorkItemWorkflowExtension cloneObject() {
        WorkItemWorkflowExtension clone = new WorkItemWorkflowExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The Workflow label.
     *
     * @param label the StringProperty.
     */
    public void setLabel(StringProperty label) {
        this.label = label;
    }

    /**
     * The Workflow label.
     *
     * @return the StringProperty.
     */
    public StringProperty getLabel() {
        return this.label;
    }

    /**
     * The Workflow tooltip.
     *
     * @param tooltip the StringProperty.
     */
    public void setTooltip(StringProperty tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * The Workflow tooltip.
     *
     * @return the StringProperty.
     */
    public StringProperty getTooltip() {
        return this.tooltip;
    }

    /**
     * The Label when no workflow is available.
     *
     * @param noWorkflowLabel the StringProperty.
     */
    public void setNoWorkflowLabel(StringProperty noWorkflowLabel) {
        this.noWorkflowLabel = noWorkflowLabel;
    }

    /**
     * The Label when no workflow is available.
     *
     * @return the StringProperty.
     */
    public StringProperty getNoWorkflowLabel() {
        return this.noWorkflowLabel;
    }

    /**
     * The Workflow Signal icon.
     *
     * @param signalIcon the StringProperty.
     */
    public void setSignalIcon(StringProperty signalIcon) {
        this.signalIcon = signalIcon;
    }

    /**
     * The Workflow Signal icon.
     *
     * @return the StringProperty.
     */
    public StringProperty getSignalIcon() {
        return this.signalIcon;
    }

    /**
     * The Workflow icon.
     *
     * @param workflowIcon the StringProperty.
     */
    public void setWorkflowIcon(StringProperty workflowIcon) {
        this.workflowIcon = workflowIcon;
    }

    /**
     * The Workflow icon.
     *
     * @return the StringProperty.
     */
    public StringProperty getWorkflowIcon() {
        return this.workflowIcon;
    }

    /**
     * The Workflow Action Reference ID.
     *
     * @param actionId the StringProperty.
     */
    public void setActionId(StringProperty actionId) {
        this.actionId = actionId;
    }

    /**
     * The Workflow Action Reference ID.
     *
     * @return the StringProperty.
     */
    public StringProperty getActionId() {
        return this.actionId;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(WorkItemWorkflowExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(WorkItemWorkflowExtension.class).getAllProperties();
    }
}
