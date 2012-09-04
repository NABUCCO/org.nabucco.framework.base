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
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.application.LocalizationLanguageExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * LocalizationExtension<p/>NABUCCO User Interface Localization extension.<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-03-09
 */
public class LocalizationExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;", "l1,512;u0,n;m0,1;" };

    public static final String LOCALIZATIONLANGUAGELIST = "localizationLanguageList";

    public static final String DEFAULTLANGUAGEIDENTIFIER = "defaultLanguageIdentifier";

    /** The list with supported localizations */
    private NabuccoList<LocalizationLanguageExtension> localizationLanguageList;

    /** The id of the default system language extension */
    private ExtensionId defaultLanguageIdentifier;

    /** Constructs a new LocalizationExtension instance. */
    public LocalizationExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the LocalizationExtension.
     */
    protected void cloneObject(LocalizationExtension clone) {
        super.cloneObject(clone);
        if ((this.localizationLanguageList != null)) {
            clone.localizationLanguageList = this.localizationLanguageList.cloneCollection();
        }
        if ((this.getDefaultLanguageIdentifier() != null)) {
            clone.setDefaultLanguageIdentifier(this.getDefaultLanguageIdentifier().cloneObject());
        }
    }

    /**
     * Getter for the LocalizationLanguageListJPA.
     *
     * @return the List<LocalizationLanguageExtension>.
     */
    List<LocalizationLanguageExtension> getLocalizationLanguageListJPA() {
        if ((this.localizationLanguageList == null)) {
            this.localizationLanguageList = new NabuccoListImpl<LocalizationLanguageExtension>(
                    NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<LocalizationLanguageExtension>) this.localizationLanguageList).getDelegate();
    }

    /**
     * Setter for the LocalizationLanguageListJPA.
     *
     * @param localizationLanguageList the List<LocalizationLanguageExtension>.
     */
    void setLocalizationLanguageListJPA(List<LocalizationLanguageExtension> localizationLanguageList) {
        if ((this.localizationLanguageList == null)) {
            this.localizationLanguageList = new NabuccoListImpl<LocalizationLanguageExtension>(
                    NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<LocalizationLanguageExtension>) this.localizationLanguageList)
                .setDelegate(localizationLanguageList);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(UiExtension.class).getPropertyMap());
        propertyMap.put(LOCALIZATIONLANGUAGELIST, PropertyDescriptorSupport.createCollection(LOCALIZATIONLANGUAGELIST,
                LocalizationLanguageExtension.class, 4, PROPERTY_CONSTRAINTS[0], false,
                PropertyAssociationType.COMPOSITION));
        propertyMap.put(DEFAULTLANGUAGEIDENTIFIER, PropertyDescriptorSupport.createBasetype(DEFAULTLANGUAGEIDENTIFIER,
                ExtensionId.class, 5, PROPERTY_CONSTRAINTS[1], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(LocalizationExtension.getPropertyDescriptor(LOCALIZATIONLANGUAGELIST),
                this.localizationLanguageList, null));
        properties.add(super.createProperty(LocalizationExtension.getPropertyDescriptor(DEFAULTLANGUAGEIDENTIFIER),
                this.defaultLanguageIdentifier, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(LOCALIZATIONLANGUAGELIST) && (property.getType() == LocalizationLanguageExtension.class))) {
            this.localizationLanguageList = ((NabuccoList<LocalizationLanguageExtension>) property.getInstance());
            return true;
        } else if ((property.getName().equals(DEFAULTLANGUAGEIDENTIFIER) && (property.getType() == ExtensionId.class))) {
            this.setDefaultLanguageIdentifier(((ExtensionId) property.getInstance()));
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
        final LocalizationExtension other = ((LocalizationExtension) obj);
        if ((this.defaultLanguageIdentifier == null)) {
            if ((other.defaultLanguageIdentifier != null))
                return false;
        } else if ((!this.defaultLanguageIdentifier.equals(other.defaultLanguageIdentifier)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.defaultLanguageIdentifier == null) ? 0 : this.defaultLanguageIdentifier
                .hashCode()));
        return result;
    }

    @Override
    public LocalizationExtension cloneObject() {
        LocalizationExtension clone = new LocalizationExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The list with supported localizations
     *
     * @return the NabuccoList<LocalizationLanguageExtension>.
     */
    public NabuccoList<LocalizationLanguageExtension> getLocalizationLanguageList() {
        if ((this.localizationLanguageList == null)) {
            this.localizationLanguageList = new NabuccoListImpl<LocalizationLanguageExtension>(
                    NabuccoCollectionState.INITIALIZED);
        }
        return this.localizationLanguageList;
    }

    /**
     * The id of the default system language extension
     *
     * @return the ExtensionId.
     */
    public ExtensionId getDefaultLanguageIdentifier() {
        return this.defaultLanguageIdentifier;
    }

    /**
     * The id of the default system language extension
     *
     * @param defaultLanguageIdentifier the ExtensionId.
     */
    public void setDefaultLanguageIdentifier(ExtensionId defaultLanguageIdentifier) {
        this.defaultLanguageIdentifier = defaultLanguageIdentifier;
    }

    /**
     * The id of the default system language extension
     *
     * @param defaultLanguageIdentifier the String.
     */
    public void setDefaultLanguageIdentifier(String defaultLanguageIdentifier) {
        if ((this.defaultLanguageIdentifier == null)) {
            if ((defaultLanguageIdentifier == null)) {
                return;
            }
            this.defaultLanguageIdentifier = new ExtensionId();
        }
        this.defaultLanguageIdentifier.setValue(defaultLanguageIdentifier);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(LocalizationExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(LocalizationExtension.class).getAllProperties();
    }
}
