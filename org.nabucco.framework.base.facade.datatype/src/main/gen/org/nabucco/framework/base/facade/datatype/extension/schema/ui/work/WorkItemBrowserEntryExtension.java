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
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * WorkItemBrowserEntryExtension<p/>NABUCCO User Interface Work Item Browser extension entry.<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2011-09-27
 */
public class WorkItemBrowserEntryExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;", "m1,1;", "m0,1;", "m0,1;", "m1,1;", "m1,1;",
            "m1,1;", "m0,1;", "m1,1;", "m1,1;" };

    public static final String ELEMENTS = "elements";

    public static final String ENTRYID = "entryId";

    public static final String ENTRYLABEL = "entryLabel";

    public static final String ENTRYTOOLTIP = "entryTooltip";

    public static final String ENTRYICON = "entryIcon";

    public static final String PROPERTYLABEL = "propertyLabel";

    public static final String PROPERTYTOOLTIP = "propertyTooltip";

    public static final String PROPERTYGROUPING = "propertyGrouping";

    public static final String PROPERTY = "property";

    public static final String ACTION = "action";

    /** The children entries of the hierarchy */
    private NabuccoList<WorkItemBrowserEntryExtension> elements;

    /** Id of the entry */
    private StringProperty entryId;

    /** Label of the entry */
    private StringProperty entryLabel;

    /** Entry tooltip */
    private StringProperty entryTooltip;

    /** Entry Icon */
    private StringProperty entryIcon;

    /** Label of the property */
    private StringProperty propertyLabel;

    /** Tooltip of the bound property */
    private StringProperty propertyTooltip;

    /** Property grouping */
    private StringProperty propertyGrouping;

    /** Bound property */
    private StringProperty property;

    /** Action to open the property */
    private StringProperty action;

    /** Constructs a new WorkItemBrowserEntryExtension instance. */
    public WorkItemBrowserEntryExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the WorkItemBrowserEntryExtension.
     */
    protected void cloneObject(WorkItemBrowserEntryExtension clone) {
        super.cloneObject(clone);
        if ((this.elements != null)) {
            clone.elements = this.elements.cloneCollection();
        }
        if ((this.getEntryId() != null)) {
            clone.setEntryId(this.getEntryId().cloneObject());
        }
        if ((this.getEntryLabel() != null)) {
            clone.setEntryLabel(this.getEntryLabel().cloneObject());
        }
        if ((this.getEntryTooltip() != null)) {
            clone.setEntryTooltip(this.getEntryTooltip().cloneObject());
        }
        if ((this.getEntryIcon() != null)) {
            clone.setEntryIcon(this.getEntryIcon().cloneObject());
        }
        if ((this.getPropertyLabel() != null)) {
            clone.setPropertyLabel(this.getPropertyLabel().cloneObject());
        }
        if ((this.getPropertyTooltip() != null)) {
            clone.setPropertyTooltip(this.getPropertyTooltip().cloneObject());
        }
        if ((this.getPropertyGrouping() != null)) {
            clone.setPropertyGrouping(this.getPropertyGrouping().cloneObject());
        }
        if ((this.getProperty() != null)) {
            clone.setProperty(this.getProperty().cloneObject());
        }
        if ((this.getAction() != null)) {
            clone.setAction(this.getAction().cloneObject());
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
        propertyMap.put(ELEMENTS, PropertyDescriptorSupport.createCollection(ELEMENTS,
                WorkItemBrowserEntryExtension.class, 4, PROPERTY_CONSTRAINTS[0], false,
                PropertyAssociationType.COMPOSITION));
        propertyMap.put(ENTRYID, PropertyDescriptorSupport.createDatatype(ENTRYID, StringProperty.class, 5,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ENTRYLABEL, PropertyDescriptorSupport.createDatatype(ENTRYLABEL, StringProperty.class, 6,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ENTRYTOOLTIP, PropertyDescriptorSupport.createDatatype(ENTRYTOOLTIP, StringProperty.class, 7,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ENTRYICON, PropertyDescriptorSupport.createDatatype(ENTRYICON, StringProperty.class, 8,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(PROPERTYLABEL, PropertyDescriptorSupport.createDatatype(PROPERTYLABEL, StringProperty.class, 9,
                PROPERTY_CONSTRAINTS[5], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(PROPERTYTOOLTIP, PropertyDescriptorSupport.createDatatype(PROPERTYTOOLTIP,
                StringProperty.class, 10, PROPERTY_CONSTRAINTS[6], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(PROPERTYGROUPING, PropertyDescriptorSupport.createDatatype(PROPERTYGROUPING,
                StringProperty.class, 11, PROPERTY_CONSTRAINTS[7], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(PROPERTY, PropertyDescriptorSupport.createDatatype(PROPERTY, StringProperty.class, 12,
                PROPERTY_CONSTRAINTS[8], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ACTION, PropertyDescriptorSupport.createDatatype(ACTION, StringProperty.class, 13,
                PROPERTY_CONSTRAINTS[9], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(WorkItemBrowserEntryExtension.getPropertyDescriptor(ELEMENTS),
                this.elements, null));
        properties.add(super.createProperty(WorkItemBrowserEntryExtension.getPropertyDescriptor(ENTRYID),
                this.getEntryId(), null));
        properties.add(super.createProperty(WorkItemBrowserEntryExtension.getPropertyDescriptor(ENTRYLABEL),
                this.getEntryLabel(), null));
        properties.add(super.createProperty(WorkItemBrowserEntryExtension.getPropertyDescriptor(ENTRYTOOLTIP),
                this.getEntryTooltip(), null));
        properties.add(super.createProperty(WorkItemBrowserEntryExtension.getPropertyDescriptor(ENTRYICON),
                this.getEntryIcon(), null));
        properties.add(super.createProperty(WorkItemBrowserEntryExtension.getPropertyDescriptor(PROPERTYLABEL),
                this.getPropertyLabel(), null));
        properties.add(super.createProperty(WorkItemBrowserEntryExtension.getPropertyDescriptor(PROPERTYTOOLTIP),
                this.getPropertyTooltip(), null));
        properties.add(super.createProperty(WorkItemBrowserEntryExtension.getPropertyDescriptor(PROPERTYGROUPING),
                this.getPropertyGrouping(), null));
        properties.add(super.createProperty(WorkItemBrowserEntryExtension.getPropertyDescriptor(PROPERTY),
                this.getProperty(), null));
        properties.add(super.createProperty(WorkItemBrowserEntryExtension.getPropertyDescriptor(ACTION),
                this.getAction(), null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(ELEMENTS) && (property.getType() == WorkItemBrowserEntryExtension.class))) {
            this.elements = ((NabuccoList<WorkItemBrowserEntryExtension>) property.getInstance());
            return true;
        } else if ((property.getName().equals(ENTRYID) && (property.getType() == StringProperty.class))) {
            this.setEntryId(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ENTRYLABEL) && (property.getType() == StringProperty.class))) {
            this.setEntryLabel(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ENTRYTOOLTIP) && (property.getType() == StringProperty.class))) {
            this.setEntryTooltip(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ENTRYICON) && (property.getType() == StringProperty.class))) {
            this.setEntryIcon(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PROPERTYLABEL) && (property.getType() == StringProperty.class))) {
            this.setPropertyLabel(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PROPERTYTOOLTIP) && (property.getType() == StringProperty.class))) {
            this.setPropertyTooltip(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PROPERTYGROUPING) && (property.getType() == StringProperty.class))) {
            this.setPropertyGrouping(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PROPERTY) && (property.getType() == StringProperty.class))) {
            this.setProperty(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ACTION) && (property.getType() == StringProperty.class))) {
            this.setAction(((StringProperty) property.getInstance()));
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
        final WorkItemBrowserEntryExtension other = ((WorkItemBrowserEntryExtension) obj);
        if ((this.entryId == null)) {
            if ((other.entryId != null))
                return false;
        } else if ((!this.entryId.equals(other.entryId)))
            return false;
        if ((this.entryLabel == null)) {
            if ((other.entryLabel != null))
                return false;
        } else if ((!this.entryLabel.equals(other.entryLabel)))
            return false;
        if ((this.entryTooltip == null)) {
            if ((other.entryTooltip != null))
                return false;
        } else if ((!this.entryTooltip.equals(other.entryTooltip)))
            return false;
        if ((this.entryIcon == null)) {
            if ((other.entryIcon != null))
                return false;
        } else if ((!this.entryIcon.equals(other.entryIcon)))
            return false;
        if ((this.propertyLabel == null)) {
            if ((other.propertyLabel != null))
                return false;
        } else if ((!this.propertyLabel.equals(other.propertyLabel)))
            return false;
        if ((this.propertyTooltip == null)) {
            if ((other.propertyTooltip != null))
                return false;
        } else if ((!this.propertyTooltip.equals(other.propertyTooltip)))
            return false;
        if ((this.propertyGrouping == null)) {
            if ((other.propertyGrouping != null))
                return false;
        } else if ((!this.propertyGrouping.equals(other.propertyGrouping)))
            return false;
        if ((this.property == null)) {
            if ((other.property != null))
                return false;
        } else if ((!this.property.equals(other.property)))
            return false;
        if ((this.action == null)) {
            if ((other.action != null))
                return false;
        } else if ((!this.action.equals(other.action)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.entryId == null) ? 0 : this.entryId.hashCode()));
        result = ((PRIME * result) + ((this.entryLabel == null) ? 0 : this.entryLabel.hashCode()));
        result = ((PRIME * result) + ((this.entryTooltip == null) ? 0 : this.entryTooltip.hashCode()));
        result = ((PRIME * result) + ((this.entryIcon == null) ? 0 : this.entryIcon.hashCode()));
        result = ((PRIME * result) + ((this.propertyLabel == null) ? 0 : this.propertyLabel.hashCode()));
        result = ((PRIME * result) + ((this.propertyTooltip == null) ? 0 : this.propertyTooltip.hashCode()));
        result = ((PRIME * result) + ((this.propertyGrouping == null) ? 0 : this.propertyGrouping.hashCode()));
        result = ((PRIME * result) + ((this.property == null) ? 0 : this.property.hashCode()));
        result = ((PRIME * result) + ((this.action == null) ? 0 : this.action.hashCode()));
        return result;
    }

    @Override
    public WorkItemBrowserEntryExtension cloneObject() {
        WorkItemBrowserEntryExtension clone = new WorkItemBrowserEntryExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The children entries of the hierarchy
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
     * Id of the entry
     *
     * @param entryId the StringProperty.
     */
    public void setEntryId(StringProperty entryId) {
        this.entryId = entryId;
    }

    /**
     * Id of the entry
     *
     * @return the StringProperty.
     */
    public StringProperty getEntryId() {
        return this.entryId;
    }

    /**
     * Label of the entry
     *
     * @param entryLabel the StringProperty.
     */
    public void setEntryLabel(StringProperty entryLabel) {
        this.entryLabel = entryLabel;
    }

    /**
     * Label of the entry
     *
     * @return the StringProperty.
     */
    public StringProperty getEntryLabel() {
        return this.entryLabel;
    }

    /**
     * Entry tooltip
     *
     * @param entryTooltip the StringProperty.
     */
    public void setEntryTooltip(StringProperty entryTooltip) {
        this.entryTooltip = entryTooltip;
    }

    /**
     * Entry tooltip
     *
     * @return the StringProperty.
     */
    public StringProperty getEntryTooltip() {
        return this.entryTooltip;
    }

    /**
     * Entry Icon
     *
     * @param entryIcon the StringProperty.
     */
    public void setEntryIcon(StringProperty entryIcon) {
        this.entryIcon = entryIcon;
    }

    /**
     * Entry Icon
     *
     * @return the StringProperty.
     */
    public StringProperty getEntryIcon() {
        return this.entryIcon;
    }

    /**
     * Label of the property
     *
     * @param propertyLabel the StringProperty.
     */
    public void setPropertyLabel(StringProperty propertyLabel) {
        this.propertyLabel = propertyLabel;
    }

    /**
     * Label of the property
     *
     * @return the StringProperty.
     */
    public StringProperty getPropertyLabel() {
        return this.propertyLabel;
    }

    /**
     * Tooltip of the bound property
     *
     * @param propertyTooltip the StringProperty.
     */
    public void setPropertyTooltip(StringProperty propertyTooltip) {
        this.propertyTooltip = propertyTooltip;
    }

    /**
     * Tooltip of the bound property
     *
     * @return the StringProperty.
     */
    public StringProperty getPropertyTooltip() {
        return this.propertyTooltip;
    }

    /**
     * Property grouping
     *
     * @param propertyGrouping the StringProperty.
     */
    public void setPropertyGrouping(StringProperty propertyGrouping) {
        this.propertyGrouping = propertyGrouping;
    }

    /**
     * Property grouping
     *
     * @return the StringProperty.
     */
    public StringProperty getPropertyGrouping() {
        return this.propertyGrouping;
    }

    /**
     * Bound property
     *
     * @param property the StringProperty.
     */
    public void setProperty(StringProperty property) {
        this.property = property;
    }

    /**
     * Bound property
     *
     * @return the StringProperty.
     */
    public StringProperty getProperty() {
        return this.property;
    }

    /**
     * Action to open the property
     *
     * @param action the StringProperty.
     */
    public void setAction(StringProperty action) {
        this.action = action;
    }

    /**
     * Action to open the property
     *
     * @return the StringProperty.
     */
    public StringProperty getAction() {
        return this.action;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(WorkItemBrowserEntryExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(WorkItemBrowserEntryExtension.class).getAllProperties();
    }
}
