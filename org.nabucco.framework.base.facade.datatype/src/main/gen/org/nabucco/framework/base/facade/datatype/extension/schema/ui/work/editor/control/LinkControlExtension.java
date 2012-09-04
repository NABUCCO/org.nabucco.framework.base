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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.EditorControlExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * LinkControlExtension<p/>Link control extension<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2011-07-28
 */
public class LinkControlExtension extends EditorControlExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,1;", "m0,1;", "m1,1;", "m1,1;" };

    public static final String ACTION = "action";

    public static final String URL = "url";

    public static final String LINKTYPE = "linkType";

    public static final String ICON = "icon";

    /** The Action Reference ID. */
    private StringProperty action;

    /** The Referenced Url. */
    private StringProperty url;

    /** The type of the link (internal or external) */
    private EnumerationProperty linkType;

    /** The icon to show */
    private StringProperty icon;

    /** Constructs a new LinkControlExtension instance. */
    public LinkControlExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the LinkControlExtension.
     */
    protected void cloneObject(LinkControlExtension clone) {
        super.cloneObject(clone);
        if ((this.getAction() != null)) {
            clone.setAction(this.getAction().cloneObject());
        }
        if ((this.getUrl() != null)) {
            clone.setUrl(this.getUrl().cloneObject());
        }
        if ((this.getLinkType() != null)) {
            clone.setLinkType(this.getLinkType().cloneObject());
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
        propertyMap.putAll(PropertyCache.getInstance().retrieve(EditorControlExtension.class).getPropertyMap());
        propertyMap.put(ACTION, PropertyDescriptorSupport.createDatatype(ACTION, StringProperty.class, 12,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(URL, PropertyDescriptorSupport.createDatatype(URL, StringProperty.class, 13,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(LINKTYPE, PropertyDescriptorSupport.createDatatype(LINKTYPE, EnumerationProperty.class, 14,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ICON, PropertyDescriptorSupport.createDatatype(ICON, StringProperty.class, 15,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
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
                .add(super.createProperty(LinkControlExtension.getPropertyDescriptor(ACTION), this.getAction(), null));
        properties.add(super.createProperty(LinkControlExtension.getPropertyDescriptor(URL), this.getUrl(), null));
        properties.add(super.createProperty(LinkControlExtension.getPropertyDescriptor(LINKTYPE), this.getLinkType(),
                null));
        properties.add(super.createProperty(LinkControlExtension.getPropertyDescriptor(ICON), this.getIcon(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(ACTION) && (property.getType() == StringProperty.class))) {
            this.setAction(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(URL) && (property.getType() == StringProperty.class))) {
            this.setUrl(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LINKTYPE) && (property.getType() == EnumerationProperty.class))) {
            this.setLinkType(((EnumerationProperty) property.getInstance()));
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
        final LinkControlExtension other = ((LinkControlExtension) obj);
        if ((this.action == null)) {
            if ((other.action != null))
                return false;
        } else if ((!this.action.equals(other.action)))
            return false;
        if ((this.url == null)) {
            if ((other.url != null))
                return false;
        } else if ((!this.url.equals(other.url)))
            return false;
        if ((this.linkType == null)) {
            if ((other.linkType != null))
                return false;
        } else if ((!this.linkType.equals(other.linkType)))
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
        result = ((PRIME * result) + ((this.action == null) ? 0 : this.action.hashCode()));
        result = ((PRIME * result) + ((this.url == null) ? 0 : this.url.hashCode()));
        result = ((PRIME * result) + ((this.linkType == null) ? 0 : this.linkType.hashCode()));
        result = ((PRIME * result) + ((this.icon == null) ? 0 : this.icon.hashCode()));
        return result;
    }

    @Override
    public LinkControlExtension cloneObject() {
        LinkControlExtension clone = new LinkControlExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The Action Reference ID.
     *
     * @param action the StringProperty.
     */
    public void setAction(StringProperty action) {
        this.action = action;
    }

    /**
     * The Action Reference ID.
     *
     * @return the StringProperty.
     */
    public StringProperty getAction() {
        return this.action;
    }

    /**
     * The Referenced Url.
     *
     * @param url the StringProperty.
     */
    public void setUrl(StringProperty url) {
        this.url = url;
    }

    /**
     * The Referenced Url.
     *
     * @return the StringProperty.
     */
    public StringProperty getUrl() {
        return this.url;
    }

    /**
     * The type of the link (internal or external)
     *
     * @param linkType the EnumerationProperty.
     */
    public void setLinkType(EnumerationProperty linkType) {
        this.linkType = linkType;
    }

    /**
     * The type of the link (internal or external)
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getLinkType() {
        return this.linkType;
    }

    /**
     * The icon to show
     *
     * @param icon the StringProperty.
     */
    public void setIcon(StringProperty icon) {
        this.icon = icon;
    }

    /**
     * The icon to show
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
        return PropertyCache.getInstance().retrieve(LinkControlExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(LinkControlExtension.class).getAllProperties();
    }
}
