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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.navigator;

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
 * NavigatorEntryExtension<p/>NABUCCO User Interface Navigator Entry extension.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-07-08
 */
public class NavigatorEntryExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m0,n;" };

    public static final String LABEL = "label";

    public static final String TOOLTIP = "tooltip";

    public static final String IMAGE = "image";

    public static final String PATH = "path";

    public static final String ACTION = "action";

    public static final String NAVIGATORENTRYLIST = "navigatorEntryList";

    /** Tha Navigator Entry label. */
    private StringProperty label;

    /** Tha Navigator Entry tooltip. */
    private StringProperty tooltip;

    /** Tha Navigator Entry image. */
    private StringProperty image;

    /** Tha Navigator Entry path. */
    private StringProperty path;

    /** Tha Navigator Entry action. */
    private StringProperty action;

    /** The list of sub navigator entries. */
    private NabuccoList<NavigatorEntryExtension> navigatorEntryList;

    /** Constructs a new NavigatorEntryExtension instance. */
    public NavigatorEntryExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the NavigatorEntryExtension.
     */
    protected void cloneObject(NavigatorEntryExtension clone) {
        super.cloneObject(clone);
        if ((this.getLabel() != null)) {
            clone.setLabel(this.getLabel().cloneObject());
        }
        if ((this.getTooltip() != null)) {
            clone.setTooltip(this.getTooltip().cloneObject());
        }
        if ((this.getImage() != null)) {
            clone.setImage(this.getImage().cloneObject());
        }
        if ((this.getPath() != null)) {
            clone.setPath(this.getPath().cloneObject());
        }
        if ((this.getAction() != null)) {
            clone.setAction(this.getAction().cloneObject());
        }
        if ((this.navigatorEntryList != null)) {
            clone.navigatorEntryList = this.navigatorEntryList.cloneCollection();
        }
    }

    /**
     * Getter for the NavigatorEntryListJPA.
     *
     * @return the List<NavigatorEntryExtension>.
     */
    List<NavigatorEntryExtension> getNavigatorEntryListJPA() {
        if ((this.navigatorEntryList == null)) {
            this.navigatorEntryList = new NabuccoListImpl<NavigatorEntryExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<NavigatorEntryExtension>) this.navigatorEntryList).getDelegate();
    }

    /**
     * Setter for the NavigatorEntryListJPA.
     *
     * @param navigatorEntryList the List<NavigatorEntryExtension>.
     */
    void setNavigatorEntryListJPA(List<NavigatorEntryExtension> navigatorEntryList) {
        if ((this.navigatorEntryList == null)) {
            this.navigatorEntryList = new NabuccoListImpl<NavigatorEntryExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<NavigatorEntryExtension>) this.navigatorEntryList).setDelegate(navigatorEntryList);
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
        propertyMap.put(IMAGE, PropertyDescriptorSupport.createDatatype(IMAGE, StringProperty.class, 6,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(PATH, PropertyDescriptorSupport.createDatatype(PATH, StringProperty.class, 7,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ACTION, PropertyDescriptorSupport.createDatatype(ACTION, StringProperty.class, 8,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(NAVIGATORENTRYLIST, PropertyDescriptorSupport.createCollection(NAVIGATORENTRYLIST,
                NavigatorEntryExtension.class, 9, PROPERTY_CONSTRAINTS[5], false, PropertyAssociationType.COMPOSITION));
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
                .add(super.createProperty(NavigatorEntryExtension.getPropertyDescriptor(LABEL), this.getLabel(), null));
        properties.add(super.createProperty(NavigatorEntryExtension.getPropertyDescriptor(TOOLTIP), this.getTooltip(),
                null));
        properties
                .add(super.createProperty(NavigatorEntryExtension.getPropertyDescriptor(IMAGE), this.getImage(), null));
        properties.add(super.createProperty(NavigatorEntryExtension.getPropertyDescriptor(PATH), this.getPath(), null));
        properties.add(super.createProperty(NavigatorEntryExtension.getPropertyDescriptor(ACTION), this.getAction(),
                null));
        properties.add(super.createProperty(NavigatorEntryExtension.getPropertyDescriptor(NAVIGATORENTRYLIST),
                this.navigatorEntryList, null));
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
        } else if ((property.getName().equals(IMAGE) && (property.getType() == StringProperty.class))) {
            this.setImage(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PATH) && (property.getType() == StringProperty.class))) {
            this.setPath(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ACTION) && (property.getType() == StringProperty.class))) {
            this.setAction(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NAVIGATORENTRYLIST) && (property.getType() == NavigatorEntryExtension.class))) {
            this.navigatorEntryList = ((NabuccoList<NavigatorEntryExtension>) property.getInstance());
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
        final NavigatorEntryExtension other = ((NavigatorEntryExtension) obj);
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
        if ((this.image == null)) {
            if ((other.image != null))
                return false;
        } else if ((!this.image.equals(other.image)))
            return false;
        if ((this.path == null)) {
            if ((other.path != null))
                return false;
        } else if ((!this.path.equals(other.path)))
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
        result = ((PRIME * result) + ((this.label == null) ? 0 : this.label.hashCode()));
        result = ((PRIME * result) + ((this.tooltip == null) ? 0 : this.tooltip.hashCode()));
        result = ((PRIME * result) + ((this.image == null) ? 0 : this.image.hashCode()));
        result = ((PRIME * result) + ((this.path == null) ? 0 : this.path.hashCode()));
        result = ((PRIME * result) + ((this.action == null) ? 0 : this.action.hashCode()));
        return result;
    }

    @Override
    public NavigatorEntryExtension cloneObject() {
        NavigatorEntryExtension clone = new NavigatorEntryExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Tha Navigator Entry label.
     *
     * @param label the StringProperty.
     */
    public void setLabel(StringProperty label) {
        this.label = label;
    }

    /**
     * Tha Navigator Entry label.
     *
     * @return the StringProperty.
     */
    public StringProperty getLabel() {
        return this.label;
    }

    /**
     * Tha Navigator Entry tooltip.
     *
     * @param tooltip the StringProperty.
     */
    public void setTooltip(StringProperty tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * Tha Navigator Entry tooltip.
     *
     * @return the StringProperty.
     */
    public StringProperty getTooltip() {
        return this.tooltip;
    }

    /**
     * Tha Navigator Entry image.
     *
     * @param image the StringProperty.
     */
    public void setImage(StringProperty image) {
        this.image = image;
    }

    /**
     * Tha Navigator Entry image.
     *
     * @return the StringProperty.
     */
    public StringProperty getImage() {
        return this.image;
    }

    /**
     * Tha Navigator Entry path.
     *
     * @param path the StringProperty.
     */
    public void setPath(StringProperty path) {
        this.path = path;
    }

    /**
     * Tha Navigator Entry path.
     *
     * @return the StringProperty.
     */
    public StringProperty getPath() {
        return this.path;
    }

    /**
     * Tha Navigator Entry action.
     *
     * @param action the StringProperty.
     */
    public void setAction(StringProperty action) {
        this.action = action;
    }

    /**
     * Tha Navigator Entry action.
     *
     * @return the StringProperty.
     */
    public StringProperty getAction() {
        return this.action;
    }

    /**
     * The list of sub navigator entries.
     *
     * @return the NabuccoList<NavigatorEntryExtension>.
     */
    public NabuccoList<NavigatorEntryExtension> getNavigatorEntryList() {
        if ((this.navigatorEntryList == null)) {
            this.navigatorEntryList = new NabuccoListImpl<NavigatorEntryExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.navigatorEntryList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(NavigatorEntryExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(NavigatorEntryExtension.class).getAllProperties();
    }
}
