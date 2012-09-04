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
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemActionsExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemBrowserExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * WorkItemExtension<p/>NABUCCO User Interface Work Item extension.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-07-28
 */
public abstract class WorkItemExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m0,1;", "m0,n;",
            "m0,1;" };

    public static final String LABEL = "label";

    public static final String TOOLTIP = "tooltip";

    public static final String ICON = "icon";

    public static final String PERSPECTIVE = "perspective";

    public static final String WORKFLOWEXTENSION = "workflowExtension";

    public static final String BROWSERS = "browsers";

    public static final String ACTIONS = "actions";

    /** The Work Item label. */
    private StringProperty label;

    /** The Work Item tooltip. */
    private StringProperty tooltip;

    /** The Work Item icon. */
    private StringProperty icon;

    /** The Work Item perspective. */
    private StringProperty perspective;

    /** The Workflow Configuration. */
    private WorkItemWorkflowExtension workflowExtension;

    /** The Work Item browsers this item is registered to. */
    private NabuccoList<WorkItemBrowserExtension> browsers;

    /** The Container wirh actions */
    private WorkItemActionsExtension actions;

    /** Constructs a new WorkItemExtension instance. */
    public WorkItemExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the WorkItemExtension.
     */
    protected void cloneObject(WorkItemExtension clone) {
        super.cloneObject(clone);
        if ((this.getLabel() != null)) {
            clone.setLabel(this.getLabel().cloneObject());
        }
        if ((this.getTooltip() != null)) {
            clone.setTooltip(this.getTooltip().cloneObject());
        }
        if ((this.getIcon() != null)) {
            clone.setIcon(this.getIcon().cloneObject());
        }
        if ((this.getPerspective() != null)) {
            clone.setPerspective(this.getPerspective().cloneObject());
        }
        if ((this.getWorkflowExtension() != null)) {
            clone.setWorkflowExtension(this.getWorkflowExtension().cloneObject());
        }
        if ((this.browsers != null)) {
            clone.browsers = this.browsers.cloneCollection();
        }
        if ((this.getActions() != null)) {
            clone.setActions(this.getActions().cloneObject());
        }
    }

    /**
     * Getter for the BrowsersJPA.
     *
     * @return the List<WorkItemBrowserExtension>.
     */
    List<WorkItemBrowserExtension> getBrowsersJPA() {
        if ((this.browsers == null)) {
            this.browsers = new NabuccoListImpl<WorkItemBrowserExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<WorkItemBrowserExtension>) this.browsers).getDelegate();
    }

    /**
     * Setter for the BrowsersJPA.
     *
     * @param browsers the List<WorkItemBrowserExtension>.
     */
    void setBrowsersJPA(List<WorkItemBrowserExtension> browsers) {
        if ((this.browsers == null)) {
            this.browsers = new NabuccoListImpl<WorkItemBrowserExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<WorkItemBrowserExtension>) this.browsers).setDelegate(browsers);
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
        propertyMap.put(ICON, PropertyDescriptorSupport.createDatatype(ICON, StringProperty.class, 6,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(PERSPECTIVE, PropertyDescriptorSupport.createDatatype(PERSPECTIVE, StringProperty.class, 7,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(WORKFLOWEXTENSION, PropertyDescriptorSupport
                .createDatatype(WORKFLOWEXTENSION, WorkItemWorkflowExtension.class, 8, PROPERTY_CONSTRAINTS[4], false,
                        PropertyAssociationType.COMPOSITION));
        propertyMap.put(BROWSERS, PropertyDescriptorSupport.createCollection(BROWSERS, WorkItemBrowserExtension.class,
                9, PROPERTY_CONSTRAINTS[5], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ACTIONS, PropertyDescriptorSupport.createDatatype(ACTIONS, WorkItemActionsExtension.class, 10,
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
        properties.add(super.createProperty(WorkItemExtension.getPropertyDescriptor(LABEL), this.getLabel(), null));
        properties.add(super.createProperty(WorkItemExtension.getPropertyDescriptor(TOOLTIP), this.getTooltip(), null));
        properties.add(super.createProperty(WorkItemExtension.getPropertyDescriptor(ICON), this.getIcon(), null));
        properties.add(super.createProperty(WorkItemExtension.getPropertyDescriptor(PERSPECTIVE),
                this.getPerspective(), null));
        properties.add(super.createProperty(WorkItemExtension.getPropertyDescriptor(WORKFLOWEXTENSION),
                this.getWorkflowExtension(), null));
        properties.add(super.createProperty(WorkItemExtension.getPropertyDescriptor(BROWSERS), this.browsers, null));
        properties.add(super.createProperty(WorkItemExtension.getPropertyDescriptor(ACTIONS), this.getActions(), null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
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
        } else if ((property.getName().equals(ICON) && (property.getType() == StringProperty.class))) {
            this.setIcon(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PERSPECTIVE) && (property.getType() == StringProperty.class))) {
            this.setPerspective(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(WORKFLOWEXTENSION) && (property.getType() == WorkItemWorkflowExtension.class))) {
            this.setWorkflowExtension(((WorkItemWorkflowExtension) property.getInstance()));
            return true;
        } else if ((property.getName().equals(BROWSERS) && (property.getType() == WorkItemBrowserExtension.class))) {
            this.browsers = ((NabuccoList<WorkItemBrowserExtension>) property.getInstance());
            return true;
        } else if ((property.getName().equals(ACTIONS) && (property.getType() == WorkItemActionsExtension.class))) {
            this.setActions(((WorkItemActionsExtension) property.getInstance()));
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
        final WorkItemExtension other = ((WorkItemExtension) obj);
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
        if ((this.perspective == null)) {
            if ((other.perspective != null))
                return false;
        } else if ((!this.perspective.equals(other.perspective)))
            return false;
        if ((this.workflowExtension == null)) {
            if ((other.workflowExtension != null))
                return false;
        } else if ((!this.workflowExtension.equals(other.workflowExtension)))
            return false;
        if ((this.actions == null)) {
            if ((other.actions != null))
                return false;
        } else if ((!this.actions.equals(other.actions)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.label == null) ? 0 : this.label.hashCode()));
        result = ((PRIME * result) + ((this.tooltip == null) ? 0 : this.tooltip.hashCode()));
        result = ((PRIME * result) + ((this.icon == null) ? 0 : this.icon.hashCode()));
        result = ((PRIME * result) + ((this.perspective == null) ? 0 : this.perspective.hashCode()));
        result = ((PRIME * result) + ((this.workflowExtension == null) ? 0 : this.workflowExtension.hashCode()));
        result = ((PRIME * result) + ((this.actions == null) ? 0 : this.actions.hashCode()));
        return result;
    }

    @Override
    public abstract WorkItemExtension cloneObject();

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
     * The Work Item perspective.
     *
     * @param perspective the StringProperty.
     */
    public void setPerspective(StringProperty perspective) {
        this.perspective = perspective;
    }

    /**
     * The Work Item perspective.
     *
     * @return the StringProperty.
     */
    public StringProperty getPerspective() {
        return this.perspective;
    }

    /**
     * The Workflow Configuration.
     *
     * @param workflowExtension the WorkItemWorkflowExtension.
     */
    public void setWorkflowExtension(WorkItemWorkflowExtension workflowExtension) {
        this.workflowExtension = workflowExtension;
    }

    /**
     * The Workflow Configuration.
     *
     * @return the WorkItemWorkflowExtension.
     */
    public WorkItemWorkflowExtension getWorkflowExtension() {
        return this.workflowExtension;
    }

    /**
     * The Work Item browsers this item is registered to.
     *
     * @return the NabuccoList<WorkItemBrowserExtension>.
     */
    public NabuccoList<WorkItemBrowserExtension> getBrowsers() {
        if ((this.browsers == null)) {
            this.browsers = new NabuccoListImpl<WorkItemBrowserExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.browsers;
    }

    /**
     * The Container wirh actions
     *
     * @param actions the WorkItemActionsExtension.
     */
    public void setActions(WorkItemActionsExtension actions) {
        this.actions = actions;
    }

    /**
     * The Container wirh actions
     *
     * @return the WorkItemActionsExtension.
     */
    public WorkItemActionsExtension getActions() {
        return this.actions;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(WorkItemExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(WorkItemExtension.class).getAllProperties();
    }
}
