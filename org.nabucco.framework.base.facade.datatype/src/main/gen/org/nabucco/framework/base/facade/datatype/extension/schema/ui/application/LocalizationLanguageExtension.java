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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.application;

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
 * LocalizationLanguageExtension<p/>NABUCCO User Interface defining the Localization language.<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-03-09
 */
public class LocalizationLanguageExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;" };

    public static final String LANGUAGE = "language";

    public static final String COUNTRY = "country";

    public static final String ICON = "icon";

    /** The language ISO short name like en, de etc */
    private StringProperty language;

    /** The Country ISO short name like US, DE etc */
    private StringProperty country;

    /** The icon to show on the UI */
    private StringProperty icon;

    /** Constructs a new LocalizationLanguageExtension instance. */
    public LocalizationLanguageExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the LocalizationLanguageExtension.
     */
    protected void cloneObject(LocalizationLanguageExtension clone) {
        super.cloneObject(clone);
        if ((this.getLanguage() != null)) {
            clone.setLanguage(this.getLanguage().cloneObject());
        }
        if ((this.getCountry() != null)) {
            clone.setCountry(this.getCountry().cloneObject());
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
        propertyMap.put(LANGUAGE, PropertyDescriptorSupport.createDatatype(LANGUAGE, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(COUNTRY, PropertyDescriptorSupport.createDatatype(COUNTRY, StringProperty.class, 5,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ICON, PropertyDescriptorSupport.createDatatype(ICON, StringProperty.class, 6,
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
        properties.add(super.createProperty(LocalizationLanguageExtension.getPropertyDescriptor(LANGUAGE),
                this.getLanguage(), null));
        properties.add(super.createProperty(LocalizationLanguageExtension.getPropertyDescriptor(COUNTRY),
                this.getCountry(), null));
        properties.add(super.createProperty(LocalizationLanguageExtension.getPropertyDescriptor(ICON), this.getIcon(),
                null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(LANGUAGE) && (property.getType() == StringProperty.class))) {
            this.setLanguage(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(COUNTRY) && (property.getType() == StringProperty.class))) {
            this.setCountry(((StringProperty) property.getInstance()));
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
        final LocalizationLanguageExtension other = ((LocalizationLanguageExtension) obj);
        if ((this.language == null)) {
            if ((other.language != null))
                return false;
        } else if ((!this.language.equals(other.language)))
            return false;
        if ((this.country == null)) {
            if ((other.country != null))
                return false;
        } else if ((!this.country.equals(other.country)))
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
        result = ((PRIME * result) + ((this.language == null) ? 0 : this.language.hashCode()));
        result = ((PRIME * result) + ((this.country == null) ? 0 : this.country.hashCode()));
        result = ((PRIME * result) + ((this.icon == null) ? 0 : this.icon.hashCode()));
        return result;
    }

    @Override
    public LocalizationLanguageExtension cloneObject() {
        LocalizationLanguageExtension clone = new LocalizationLanguageExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The language ISO short name like en, de etc
     *
     * @param language the StringProperty.
     */
    public void setLanguage(StringProperty language) {
        this.language = language;
    }

    /**
     * The language ISO short name like en, de etc
     *
     * @return the StringProperty.
     */
    public StringProperty getLanguage() {
        return this.language;
    }

    /**
     * The Country ISO short name like US, DE etc
     *
     * @param country the StringProperty.
     */
    public void setCountry(StringProperty country) {
        this.country = country;
    }

    /**
     * The Country ISO short name like US, DE etc
     *
     * @return the StringProperty.
     */
    public StringProperty getCountry() {
        return this.country;
    }

    /**
     * The icon to show on the UI
     *
     * @param icon the StringProperty.
     */
    public void setIcon(StringProperty icon) {
        this.icon = icon;
    }

    /**
     * The icon to show on the UI
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
        return PropertyCache.getInstance().retrieve(LocalizationLanguageExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(LocalizationLanguageExtension.class).getAllProperties();
    }
}
