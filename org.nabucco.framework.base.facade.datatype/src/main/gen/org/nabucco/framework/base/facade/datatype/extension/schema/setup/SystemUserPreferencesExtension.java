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
package org.nabucco.framework.base.facade.datatype.extension.schema.setup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.PreferencesEntry;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * SystemUserPreferencesExtension<p/>Extension Schema for Component Setup.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-23
 */
public class SystemUserPreferencesExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;" };

    public static final String SYSTEMPREFERENES = "systemPreferenes";

    /** The preferences for the User of the NABUCCO System. */
    private NabuccoList<PreferencesEntry> systemPreferenes;

    /** Constructs a new SystemUserPreferencesExtension instance. */
    public SystemUserPreferencesExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the SystemUserPreferencesExtension.
     */
    protected void cloneObject(SystemUserPreferencesExtension clone) {
        super.cloneObject(clone);
        if ((this.systemPreferenes != null)) {
            clone.systemPreferenes = this.systemPreferenes.cloneCollection();
        }
    }

    /**
     * Getter for the SystemPreferenesJPA.
     *
     * @return the List<PreferencesEntry>.
     */
    List<PreferencesEntry> getSystemPreferenesJPA() {
        if ((this.systemPreferenes == null)) {
            this.systemPreferenes = new NabuccoListImpl<PreferencesEntry>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<PreferencesEntry>) this.systemPreferenes).getDelegate();
    }

    /**
     * Setter for the SystemPreferenesJPA.
     *
     * @param systemPreferenes the List<PreferencesEntry>.
     */
    void setSystemPreferenesJPA(List<PreferencesEntry> systemPreferenes) {
        if ((this.systemPreferenes == null)) {
            this.systemPreferenes = new NabuccoListImpl<PreferencesEntry>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<PreferencesEntry>) this.systemPreferenes).setDelegate(systemPreferenes);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoExtensionComposite.class).getPropertyMap());
        propertyMap.put(SYSTEMPREFERENES, PropertyDescriptorSupport.createCollection(SYSTEMPREFERENES,
                PreferencesEntry.class, 2, PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(SystemUserPreferencesExtension.getPropertyDescriptor(SYSTEMPREFERENES),
                this.systemPreferenes, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(SYSTEMPREFERENES) && (property.getType() == PreferencesEntry.class))) {
            this.systemPreferenes = ((NabuccoList<PreferencesEntry>) property.getInstance());
            return true;
        }
        return false;
    }

    @Override
    public SystemUserPreferencesExtension cloneObject() {
        SystemUserPreferencesExtension clone = new SystemUserPreferencesExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The preferences for the User of the NABUCCO System.
     *
     * @return the NabuccoList<PreferencesEntry>.
     */
    public NabuccoList<PreferencesEntry> getSystemPreferenes() {
        if ((this.systemPreferenes == null)) {
            this.systemPreferenes = new NabuccoListImpl<PreferencesEntry>(NabuccoCollectionState.INITIALIZED);
        }
        return this.systemPreferenes;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(SystemUserPreferencesExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(SystemUserPreferencesExtension.class).getAllProperties();
    }
}
