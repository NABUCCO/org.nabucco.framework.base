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
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemBrowserEntryExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * WorkItemBrowserExtension<p/>NABUCCO User Interface Work Item Browser extension.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-08-02
 */
public class WorkItemBrowserExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m0,n;" };

    public static final String BROWSERID = "browserId";

    public static final String ICON = "icon";

    public static final String LABEL = "label";

    public static final String TOOLTIP = "tooltip";

    public static final String ELEMENTS = "elements";

    /** The referenced Work Item Browser ID. */
    private StringProperty browserId;

    /** Root element icon */
    private StringProperty icon;

    /** Root element label */
    private StringProperty label;

    /** Root element Tooltip */
    private StringProperty tooltip;

    /** Browser Items */
    private NabuccoList<WorkItemBrowserEntryExtension> elements;

    /** Constructs a new WorkItemBrowserExtension instance. */
    public WorkItemBrowserExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the WorkItemBrowserExtension.
     */
    protected void cloneObject(WorkItemBrowserExtension clone) {
        super.cloneObject(clone);
        if ((this.getBrowserId() != null)) {
            clone.setBrowserId(this.getBrowserId().cloneObject());
        }
        if ((this.getIcon() != null)) {
            clone.setIcon(this.getIcon().cloneObject());
        }
        if ((this.getLabel() != null)) {
            clone.setLabel(this.getLabel().cloneObject());
        }
        if ((this.getTooltip() != null)) {
            clone.setTooltip(this.getTooltip().cloneObject());
        }
        if ((this.elements != null)) {
            clone.elements = this.elements.cloneCollection();
        }
    }

    /**
     * Getter for the ElementsJPA.
     *
     * @return the List<WorkItemBrowserEntryExtension>.
     */
    List<WorkItemBrowserEntryExtension> getElementsJPA() {
        if ((this.elements == null)) {
            this.elements = new NabuccoListImpl<WorkItemBrowserEntryExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<WorkItemBrowserEntryExtension>) this.elements).getDelegate();
    }

    /**
     * Setter for the ElementsJPA.
     *
     * @param elements the List<WorkItemBrowserEntryExtension>.
     */
    void setElementsJPA(List<WorkItemBrowserEntryExtension> elements) {
        if ((this.elements == null)) {
            this.elements = new NabuccoListImpl<WorkItemBrowserEntryExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<WorkItemBrowserEntryExtension>) this.elements).setDelegate(elements);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(UiExtension.class).getPropertyMap());
        propertyMap.put(BROWSERID, PropertyDescriptorSupport.createDatatype(BROWSERID, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ICON, PropertyDescriptorSupport.createDatatype(ICON, StringProperty.class, 5,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(LABEL, PropertyDescriptorSupport.createDatatype(LABEL, StringProperty.class, 6,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(TOOLTIP, PropertyDescriptorSupport.createDatatype(TOOLTIP, StringProperty.class, 7,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ELEMENTS, PropertyDescriptorSupport.createCollection(ELEMENTS,
                WorkItemBrowserEntryExtension.class, 8, PROPERTY_CONSTRAINTS[4], false,
                PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(WorkItemBrowserExtension.getPropertyDescriptor(BROWSERID),
                this.getBrowserId(), null));
        properties
                .add(super.createProperty(WorkItemBrowserExtension.getPropertyDescriptor(ICON), this.getIcon(), null));
        properties.add(super.createProperty(WorkItemBrowserExtension.getPropertyDescriptor(LABEL), this.getLabel(),
                null));
        properties.add(super.createProperty(WorkItemBrowserExtension.getPropertyDescriptor(TOOLTIP), this.getTooltip(),
                null));
        properties.add(super.createProperty(WorkItemBrowserExtension.getPropertyDescriptor(ELEMENTS), this.elements,
                null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(BROWSERID) && (property.getType() == StringProperty.class))) {
            this.setBrowserId(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ICON) && (property.getType() == StringProperty.class))) {
            this.setIcon(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LABEL) && (property.getType() == StringProperty.class))) {
            this.setLabel(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TOOLTIP) && (property.getType() == StringProperty.class))) {
            this.setTooltip(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ELEMENTS) && (property.getType() == WorkItemBrowserEntryExtension.class))) {
            this.elements = ((NabuccoList<WorkItemBrowserEntryExtension>) property.getInstance());
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
        final WorkItemBrowserExtension other = ((WorkItemBrowserExtension) obj);
        if ((this.browserId == null)) {
            if ((other.browserId != null))
                return false;
        } else if ((!this.browserId.equals(other.browserId)))
            return false;
        if ((this.icon == null)) {
            if ((other.icon != null))
                return false;
        } else if ((!this.icon.equals(other.icon)))
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
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.browserId == null) ? 0 : this.browserId.hashCode()));
        result = ((PRIME * result) + ((this.icon == null) ? 0 : this.icon.hashCode()));
        result = ((PRIME * result) + ((this.label == null) ? 0 : this.label.hashCode()));
        result = ((PRIME * result) + ((this.tooltip == null) ? 0 : this.tooltip.hashCode()));
        return result;
    }

    @Override
    public WorkItemBrowserExtension cloneObject() {
        WorkItemBrowserExtension clone = new WorkItemBrowserExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The referenced Work Item Browser ID.
     *
     * @param browserId the StringProperty.
     */
    public void setBrowserId(StringProperty browserId) {
        this.browserId = browserId;
    }

    /**
     * The referenced Work Item Browser ID.
     *
     * @return the StringProperty.
     */
    public StringProperty getBrowserId() {
        return this.browserId;
    }

    /**
     * Root element icon
     *
     * @param icon the StringProperty.
     */
    public void setIcon(StringProperty icon) {
        this.icon = icon;
    }

    /**
     * Root element icon
     *
     * @return the StringProperty.
     */
    public StringProperty getIcon() {
        return this.icon;
    }

    /**
     * Root element label
     *
     * @param label the StringProperty.
     */
    public void setLabel(StringProperty label) {
        this.label = label;
    }

    /**
     * Root element label
     *
     * @return the StringProperty.
     */
    public StringProperty getLabel() {
        return this.label;
    }

    /**
     * Root element Tooltip
     *
     * @param tooltip the StringProperty.
     */
    public void setTooltip(StringProperty tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * Root element Tooltip
     *
     * @return the StringProperty.
     */
    public StringProperty getTooltip() {
        return this.tooltip;
    }

    /**
     * Browser Items
     *
     * @return the NabuccoList<WorkItemBrowserEntryExtension>.
     */
    public NabuccoList<WorkItemBrowserEntryExtension> getElements() {
        if ((this.elements == null)) {
            this.elements = new NabuccoListImpl<WorkItemBrowserEntryExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.elements;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(WorkItemBrowserExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(WorkItemBrowserExtension.class).getAllProperties();
    }
}
