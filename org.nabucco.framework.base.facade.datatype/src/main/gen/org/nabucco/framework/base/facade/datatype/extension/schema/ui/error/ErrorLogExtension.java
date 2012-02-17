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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.error;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.BooleanProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * ErrorLogExtension<p/>NABUCCO Error container extension<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2011-08-05
 */
public class ErrorLogExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;" };

    public static final String TITLE = "title";

    public static final String LAYOUT = "layout";

    public static final String ALLOWCLEAR = "allowClear";

    /** The Dialog title. */
    private StringProperty title;

    /** The Dialog message. */
    private EnumerationProperty layout;

    /** The icon of the dialog */
    private BooleanProperty allowClear;

    /** Constructs a new ErrorLogExtension instance. */
    public ErrorLogExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ErrorLogExtension.
     */
    protected void cloneObject(ErrorLogExtension clone) {
        super.cloneObject(clone);
        if ((this.getTitle() != null)) {
            clone.setTitle(this.getTitle().cloneObject());
        }
        if ((this.getLayout() != null)) {
            clone.setLayout(this.getLayout().cloneObject());
        }
        if ((this.getAllowClear() != null)) {
            clone.setAllowClear(this.getAllowClear().cloneObject());
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
        propertyMap.put(TITLE, PropertyDescriptorSupport.createDatatype(TITLE, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(LAYOUT, PropertyDescriptorSupport.createDatatype(LAYOUT, EnumerationProperty.class, 5,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ALLOWCLEAR, PropertyDescriptorSupport.createDatatype(ALLOWCLEAR, BooleanProperty.class, 6,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ErrorLogExtension.getPropertyDescriptor(TITLE), this.getTitle(), null));
        properties.add(super.createProperty(ErrorLogExtension.getPropertyDescriptor(LAYOUT), this.getLayout(), null));
        properties.add(super.createProperty(ErrorLogExtension.getPropertyDescriptor(ALLOWCLEAR), this.getAllowClear(),
                null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(TITLE) && (property.getType() == StringProperty.class))) {
            this.setTitle(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LAYOUT) && (property.getType() == EnumerationProperty.class))) {
            this.setLayout(((EnumerationProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ALLOWCLEAR) && (property.getType() == BooleanProperty.class))) {
            this.setAllowClear(((BooleanProperty) property.getInstance()));
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
        final ErrorLogExtension other = ((ErrorLogExtension) obj);
        if ((this.title == null)) {
            if ((other.title != null))
                return false;
        } else if ((!this.title.equals(other.title)))
            return false;
        if ((this.layout == null)) {
            if ((other.layout != null))
                return false;
        } else if ((!this.layout.equals(other.layout)))
            return false;
        if ((this.allowClear == null)) {
            if ((other.allowClear != null))
                return false;
        } else if ((!this.allowClear.equals(other.allowClear)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.title == null) ? 0 : this.title.hashCode()));
        result = ((PRIME * result) + ((this.layout == null) ? 0 : this.layout.hashCode()));
        result = ((PRIME * result) + ((this.allowClear == null) ? 0 : this.allowClear.hashCode()));
        return result;
    }

    @Override
    public ErrorLogExtension cloneObject() {
        ErrorLogExtension clone = new ErrorLogExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The Dialog title.
     *
     * @param title the StringProperty.
     */
    public void setTitle(StringProperty title) {
        this.title = title;
    }

    /**
     * The Dialog title.
     *
     * @return the StringProperty.
     */
    public StringProperty getTitle() {
        return this.title;
    }

    /**
     * The Dialog message.
     *
     * @param layout the EnumerationProperty.
     */
    public void setLayout(EnumerationProperty layout) {
        this.layout = layout;
    }

    /**
     * The Dialog message.
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getLayout() {
        return this.layout;
    }

    /**
     * The icon of the dialog
     *
     * @param allowClear the BooleanProperty.
     */
    public void setAllowClear(BooleanProperty allowClear) {
        this.allowClear = allowClear;
    }

    /**
     * The icon of the dialog
     *
     * @return the BooleanProperty.
     */
    public BooleanProperty getAllowClear() {
        return this.allowClear;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ErrorLogExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ErrorLogExtension.class).getAllProperties();
    }
}
