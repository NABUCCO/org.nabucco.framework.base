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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.perspective;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.IntegerProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * PerspectiveExtension<p/>NABUCCO User Interface Perspective extension.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-07-08
 */
public class PerspectiveExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m0,1;", "m1,1;", "m1,1;", "m1,1;",
            "m1,1;" };

    public static final String LABEL = "label";

    public static final String TOOLTIP = "tooltip";

    public static final String IMAGE = "image";

    public static final String INDEX = "index";

    public static final String NAVIGATIONAREA = "navigationArea";

    public static final String BROWSERAREA = "browserArea";

    public static final String WORKAREA = "workArea";

    /** The Perspective label. */
    private StringProperty label;

    /** The Perspective tooltip. */
    private StringProperty tooltip;

    /** The Perspective image. */
    private StringProperty image;

    /** The Perspective image. */
    private IntegerProperty index;

    /** The Perspective Navigation Area. */
    private StringProperty navigationArea;

    /** The Perspective Browser Area. */
    private StringProperty browserArea;

    /** The Perspective Work Area. */
    private StringProperty workArea;

    /** Constructs a new PerspectiveExtension instance. */
    public PerspectiveExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the PerspectiveExtension.
     */
    protected void cloneObject(PerspectiveExtension clone) {
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
        if ((this.getIndex() != null)) {
            clone.setIndex(this.getIndex().cloneObject());
        }
        if ((this.getNavigationArea() != null)) {
            clone.setNavigationArea(this.getNavigationArea().cloneObject());
        }
        if ((this.getBrowserArea() != null)) {
            clone.setBrowserArea(this.getBrowserArea().cloneObject());
        }
        if ((this.getWorkArea() != null)) {
            clone.setWorkArea(this.getWorkArea().cloneObject());
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
        propertyMap.put(IMAGE, PropertyDescriptorSupport.createDatatype(IMAGE, StringProperty.class, 6,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(INDEX, PropertyDescriptorSupport.createDatatype(INDEX, IntegerProperty.class, 7,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(NAVIGATIONAREA, PropertyDescriptorSupport.createDatatype(NAVIGATIONAREA, StringProperty.class,
                8, PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(BROWSERAREA, PropertyDescriptorSupport.createDatatype(BROWSERAREA, StringProperty.class, 9,
                PROPERTY_CONSTRAINTS[5], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(WORKAREA, PropertyDescriptorSupport.createDatatype(WORKAREA, StringProperty.class, 10,
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
        properties.add(super.createProperty(PerspectiveExtension.getPropertyDescriptor(LABEL), this.getLabel(), null));
        properties.add(super.createProperty(PerspectiveExtension.getPropertyDescriptor(TOOLTIP), this.getTooltip(),
                null));
        properties.add(super.createProperty(PerspectiveExtension.getPropertyDescriptor(IMAGE), this.getImage(), null));
        properties.add(super.createProperty(PerspectiveExtension.getPropertyDescriptor(INDEX), this.getIndex(), null));
        properties.add(super.createProperty(PerspectiveExtension.getPropertyDescriptor(NAVIGATIONAREA),
                this.getNavigationArea(), null));
        properties.add(super.createProperty(PerspectiveExtension.getPropertyDescriptor(BROWSERAREA),
                this.getBrowserArea(), null));
        properties.add(super.createProperty(PerspectiveExtension.getPropertyDescriptor(WORKAREA), this.getWorkArea(),
                null));
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
        } else if ((property.getName().equals(IMAGE) && (property.getType() == StringProperty.class))) {
            this.setImage(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(INDEX) && (property.getType() == IntegerProperty.class))) {
            this.setIndex(((IntegerProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NAVIGATIONAREA) && (property.getType() == StringProperty.class))) {
            this.setNavigationArea(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(BROWSERAREA) && (property.getType() == StringProperty.class))) {
            this.setBrowserArea(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(WORKAREA) && (property.getType() == StringProperty.class))) {
            this.setWorkArea(((StringProperty) property.getInstance()));
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
        final PerspectiveExtension other = ((PerspectiveExtension) obj);
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
        if ((this.index == null)) {
            if ((other.index != null))
                return false;
        } else if ((!this.index.equals(other.index)))
            return false;
        if ((this.navigationArea == null)) {
            if ((other.navigationArea != null))
                return false;
        } else if ((!this.navigationArea.equals(other.navigationArea)))
            return false;
        if ((this.browserArea == null)) {
            if ((other.browserArea != null))
                return false;
        } else if ((!this.browserArea.equals(other.browserArea)))
            return false;
        if ((this.workArea == null)) {
            if ((other.workArea != null))
                return false;
        } else if ((!this.workArea.equals(other.workArea)))
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
        result = ((PRIME * result) + ((this.index == null) ? 0 : this.index.hashCode()));
        result = ((PRIME * result) + ((this.navigationArea == null) ? 0 : this.navigationArea.hashCode()));
        result = ((PRIME * result) + ((this.browserArea == null) ? 0 : this.browserArea.hashCode()));
        result = ((PRIME * result) + ((this.workArea == null) ? 0 : this.workArea.hashCode()));
        return result;
    }

    @Override
    public PerspectiveExtension cloneObject() {
        PerspectiveExtension clone = new PerspectiveExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The Perspective label.
     *
     * @param label the StringProperty.
     */
    public void setLabel(StringProperty label) {
        this.label = label;
    }

    /**
     * The Perspective label.
     *
     * @return the StringProperty.
     */
    public StringProperty getLabel() {
        return this.label;
    }

    /**
     * The Perspective tooltip.
     *
     * @param tooltip the StringProperty.
     */
    public void setTooltip(StringProperty tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * The Perspective tooltip.
     *
     * @return the StringProperty.
     */
    public StringProperty getTooltip() {
        return this.tooltip;
    }

    /**
     * The Perspective image.
     *
     * @param image the StringProperty.
     */
    public void setImage(StringProperty image) {
        this.image = image;
    }

    /**
     * The Perspective image.
     *
     * @return the StringProperty.
     */
    public StringProperty getImage() {
        return this.image;
    }

    /**
     * The Perspective image.
     *
     * @param index the IntegerProperty.
     */
    public void setIndex(IntegerProperty index) {
        this.index = index;
    }

    /**
     * The Perspective image.
     *
     * @return the IntegerProperty.
     */
    public IntegerProperty getIndex() {
        return this.index;
    }

    /**
     * The Perspective Navigation Area.
     *
     * @param navigationArea the StringProperty.
     */
    public void setNavigationArea(StringProperty navigationArea) {
        this.navigationArea = navigationArea;
    }

    /**
     * The Perspective Navigation Area.
     *
     * @return the StringProperty.
     */
    public StringProperty getNavigationArea() {
        return this.navigationArea;
    }

    /**
     * The Perspective Browser Area.
     *
     * @param browserArea the StringProperty.
     */
    public void setBrowserArea(StringProperty browserArea) {
        this.browserArea = browserArea;
    }

    /**
     * The Perspective Browser Area.
     *
     * @return the StringProperty.
     */
    public StringProperty getBrowserArea() {
        return this.browserArea;
    }

    /**
     * The Perspective Work Area.
     *
     * @param workArea the StringProperty.
     */
    public void setWorkArea(StringProperty workArea) {
        this.workArea = workArea;
    }

    /**
     * The Perspective Work Area.
     *
     * @return the StringProperty.
     */
    public StringProperty getWorkArea() {
        return this.workArea;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(PerspectiveExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(PerspectiveExtension.class).getAllProperties();
    }
}
