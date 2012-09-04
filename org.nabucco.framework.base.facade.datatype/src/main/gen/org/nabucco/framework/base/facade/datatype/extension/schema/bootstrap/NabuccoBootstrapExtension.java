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
package org.nabucco.framework.base.facade.datatype.extension.schema.bootstrap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.schema.bootstrap.ExtensionConfigurationExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.SystemUserPreferencesExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * NabuccoBootstrapExtension<p/>Extension for configuring the NABUCCO system als well the extension configuration.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-23
 */
public class NabuccoBootstrapExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;", "m1,1;" };

    public static final String EXTENSIONLIST = "extensionList";

    public static final String SYSTEMUSERPREFERENCES = "systemUserPreferences";

    /** Associates the extensionpoints to extensionpoint classes. */
    private NabuccoList<ExtensionConfigurationExtension> extensionList;

    /** The user preferences, defined by NABUCCO */
    private SystemUserPreferencesExtension systemUserPreferences;

    /** Constructs a new NabuccoBootstrapExtension instance. */
    public NabuccoBootstrapExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the NabuccoBootstrapExtension.
     */
    protected void cloneObject(NabuccoBootstrapExtension clone) {
        super.cloneObject(clone);
        if ((this.extensionList != null)) {
            clone.extensionList = this.extensionList.cloneCollection();
        }
        if ((this.getSystemUserPreferences() != null)) {
            clone.setSystemUserPreferences(this.getSystemUserPreferences().cloneObject());
        }
    }

    /**
     * Getter for the ExtensionListJPA.
     *
     * @return the List<ExtensionConfigurationExtension>.
     */
    List<ExtensionConfigurationExtension> getExtensionListJPA() {
        if ((this.extensionList == null)) {
            this.extensionList = new NabuccoListImpl<ExtensionConfigurationExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<ExtensionConfigurationExtension>) this.extensionList).getDelegate();
    }

    /**
     * Setter for the ExtensionListJPA.
     *
     * @param extensionList the List<ExtensionConfigurationExtension>.
     */
    void setExtensionListJPA(List<ExtensionConfigurationExtension> extensionList) {
        if ((this.extensionList == null)) {
            this.extensionList = new NabuccoListImpl<ExtensionConfigurationExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<ExtensionConfigurationExtension>) this.extensionList).setDelegate(extensionList);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoExtensionComposite.class).getPropertyMap());
        propertyMap.put(EXTENSIONLIST, PropertyDescriptorSupport.createCollection(EXTENSIONLIST,
                ExtensionConfigurationExtension.class, 2, PROPERTY_CONSTRAINTS[0], false,
                PropertyAssociationType.COMPOSITION));
        propertyMap.put(SYSTEMUSERPREFERENCES, PropertyDescriptorSupport.createDatatype(SYSTEMUSERPREFERENCES,
                SystemUserPreferencesExtension.class, 3, PROPERTY_CONSTRAINTS[1], false,
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
        properties.add(super.createProperty(NabuccoBootstrapExtension.getPropertyDescriptor(EXTENSIONLIST),
                this.extensionList, null));
        properties.add(super.createProperty(NabuccoBootstrapExtension.getPropertyDescriptor(SYSTEMUSERPREFERENCES),
                this.getSystemUserPreferences(), null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(EXTENSIONLIST) && (property.getType() == ExtensionConfigurationExtension.class))) {
            this.extensionList = ((NabuccoList<ExtensionConfigurationExtension>) property.getInstance());
            return true;
        } else if ((property.getName().equals(SYSTEMUSERPREFERENCES) && (property.getType() == SystemUserPreferencesExtension.class))) {
            this.setSystemUserPreferences(((SystemUserPreferencesExtension) property.getInstance()));
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
        final NabuccoBootstrapExtension other = ((NabuccoBootstrapExtension) obj);
        if ((this.systemUserPreferences == null)) {
            if ((other.systemUserPreferences != null))
                return false;
        } else if ((!this.systemUserPreferences.equals(other.systemUserPreferences)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.systemUserPreferences == null) ? 0 : this.systemUserPreferences.hashCode()));
        return result;
    }

    @Override
    public NabuccoBootstrapExtension cloneObject() {
        NabuccoBootstrapExtension clone = new NabuccoBootstrapExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Associates the extensionpoints to extensionpoint classes.
     *
     * @return the NabuccoList<ExtensionConfigurationExtension>.
     */
    public NabuccoList<ExtensionConfigurationExtension> getExtensionList() {
        if ((this.extensionList == null)) {
            this.extensionList = new NabuccoListImpl<ExtensionConfigurationExtension>(
                    NabuccoCollectionState.INITIALIZED);
        }
        return this.extensionList;
    }

    /**
     * The user preferences, defined by NABUCCO
     *
     * @param systemUserPreferences the SystemUserPreferencesExtension.
     */
    public void setSystemUserPreferences(SystemUserPreferencesExtension systemUserPreferences) {
        this.systemUserPreferences = systemUserPreferences;
    }

    /**
     * The user preferences, defined by NABUCCO
     *
     * @return the SystemUserPreferencesExtension.
     */
    public SystemUserPreferencesExtension getSystemUserPreferences() {
        return this.systemUserPreferences;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(NabuccoBootstrapExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(NabuccoBootstrapExtension.class).getAllProperties();
    }
}
