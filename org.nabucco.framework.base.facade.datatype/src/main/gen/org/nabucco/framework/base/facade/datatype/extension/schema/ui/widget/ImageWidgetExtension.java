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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget.WidgetExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * ImageWidgetExtension<p/>NABUCCO Text Widget<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2011-15-09
 */
public class ImageWidgetExtension extends WidgetExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,1;", "m1,1;", "m0,1;", "m0,1;" };

    public static final String IMAGE = "image";

    public static final String NAME = "name";

    public static final String TOOLTIP = "tooltip";

    public static final String LINK = "link";

    /** The image to show */
    private StringProperty image;

    /** The image name */
    private StringProperty name;

    /** The tooltip for the image */
    private StringProperty tooltip;

    /** The link for the image */
    private StringProperty link;

    /** Constructs a new ImageWidgetExtension instance. */
    public ImageWidgetExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ImageWidgetExtension.
     */
    protected void cloneObject(ImageWidgetExtension clone) {
        super.cloneObject(clone);
        if ((this.getImage() != null)) {
            clone.setImage(this.getImage().cloneObject());
        }
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getTooltip() != null)) {
            clone.setTooltip(this.getTooltip().cloneObject());
        }
        if ((this.getLink() != null)) {
            clone.setLink(this.getLink().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(WidgetExtension.class).getPropertyMap());
        propertyMap.put(IMAGE, PropertyDescriptorSupport.createDatatype(IMAGE, StringProperty.class, 6,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(NAME, PropertyDescriptorSupport.createDatatype(NAME, StringProperty.class, 7,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(TOOLTIP, PropertyDescriptorSupport.createDatatype(TOOLTIP, StringProperty.class, 8,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(LINK, PropertyDescriptorSupport.createDatatype(LINK, StringProperty.class, 9,
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
        properties.add(super.createProperty(ImageWidgetExtension.getPropertyDescriptor(IMAGE), this.getImage(), null));
        properties.add(super.createProperty(ImageWidgetExtension.getPropertyDescriptor(NAME), this.getName(), null));
        properties.add(super.createProperty(ImageWidgetExtension.getPropertyDescriptor(TOOLTIP), this.getTooltip(),
                null));
        properties.add(super.createProperty(ImageWidgetExtension.getPropertyDescriptor(LINK), this.getLink(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(IMAGE) && (property.getType() == StringProperty.class))) {
            this.setImage(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NAME) && (property.getType() == StringProperty.class))) {
            this.setName(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TOOLTIP) && (property.getType() == StringProperty.class))) {
            this.setTooltip(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LINK) && (property.getType() == StringProperty.class))) {
            this.setLink(((StringProperty) property.getInstance()));
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
        final ImageWidgetExtension other = ((ImageWidgetExtension) obj);
        if ((this.image == null)) {
            if ((other.image != null))
                return false;
        } else if ((!this.image.equals(other.image)))
            return false;
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        if ((this.tooltip == null)) {
            if ((other.tooltip != null))
                return false;
        } else if ((!this.tooltip.equals(other.tooltip)))
            return false;
        if ((this.link == null)) {
            if ((other.link != null))
                return false;
        } else if ((!this.link.equals(other.link)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.image == null) ? 0 : this.image.hashCode()));
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.tooltip == null) ? 0 : this.tooltip.hashCode()));
        result = ((PRIME * result) + ((this.link == null) ? 0 : this.link.hashCode()));
        return result;
    }

    @Override
    public ImageWidgetExtension cloneObject() {
        ImageWidgetExtension clone = new ImageWidgetExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The image to show
     *
     * @param image the StringProperty.
     */
    public void setImage(StringProperty image) {
        this.image = image;
    }

    /**
     * The image to show
     *
     * @return the StringProperty.
     */
    public StringProperty getImage() {
        return this.image;
    }

    /**
     * The image name
     *
     * @param name the StringProperty.
     */
    public void setName(StringProperty name) {
        this.name = name;
    }

    /**
     * The image name
     *
     * @return the StringProperty.
     */
    public StringProperty getName() {
        return this.name;
    }

    /**
     * The tooltip for the image
     *
     * @param tooltip the StringProperty.
     */
    public void setTooltip(StringProperty tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * The tooltip for the image
     *
     * @return the StringProperty.
     */
    public StringProperty getTooltip() {
        return this.tooltip;
    }

    /**
     * The link for the image
     *
     * @param link the StringProperty.
     */
    public void setLink(StringProperty link) {
        this.link = link;
    }

    /**
     * The link for the image
     *
     * @return the StringProperty.
     */
    public StringProperty getLink() {
        return this.link;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ImageWidgetExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ImageWidgetExtension.class).getAllProperties();
    }
}
