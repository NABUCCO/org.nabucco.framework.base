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
package org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Version;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure.TemplateDSElement;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure.TemplateDSKeyExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * TemplateDSExtension<p/>Template Datastructure Extension.<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-07-03
 */
public class TemplateDSExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m1,1;", "l0,n;u0,n;m1,1;", "m1,1;", "m0,n;" };

    public static final String NAME = "name";

    public static final String VERSION = "version";

    public static final String STRUCTURE = "structure";

    public static final String KEYLIST = "keyList";

    /** The name of the extension */
    private Name name;

    /** The version of the extension */
    private Version version;

    /** The datatstructure */
    private TemplateDSElement structure;

    /** The list of the keys that can be referenced in the datastructure */
    private NabuccoList<TemplateDSKeyExtension> keyList;

    /** Constructs a new TemplateDSExtension instance. */
    public TemplateDSExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TemplateDSExtension.
     */
    protected void cloneObject(TemplateDSExtension clone) {
        super.cloneObject(clone);
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getVersion() != null)) {
            clone.setVersion(this.getVersion().cloneObject());
        }
        if ((this.getStructure() != null)) {
            clone.setStructure(this.getStructure().cloneObject());
        }
        if ((this.keyList != null)) {
            clone.keyList = this.keyList.cloneCollection();
        }
    }

    /**
     * Getter for the KeyListJPA.
     *
     * @return the List<TemplateDSKeyExtension>.
     */
    List<TemplateDSKeyExtension> getKeyListJPA() {
        if ((this.keyList == null)) {
            this.keyList = new NabuccoListImpl<TemplateDSKeyExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<TemplateDSKeyExtension>) this.keyList).getDelegate();
    }

    /**
     * Setter for the KeyListJPA.
     *
     * @param keyList the List<TemplateDSKeyExtension>.
     */
    void setKeyListJPA(List<TemplateDSKeyExtension> keyList) {
        if ((this.keyList == null)) {
            this.keyList = new NabuccoListImpl<TemplateDSKeyExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<TemplateDSKeyExtension>) this.keyList).setDelegate(keyList);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoExtensionComposite.class).getPropertyMap());
        propertyMap.put(NAME,
                PropertyDescriptorSupport.createBasetype(NAME, Name.class, 2, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(VERSION,
                PropertyDescriptorSupport.createBasetype(VERSION, Version.class, 3, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(STRUCTURE, PropertyDescriptorSupport.createDatatype(STRUCTURE, TemplateDSElement.class, 4,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(KEYLIST, PropertyDescriptorSupport.createCollection(KEYLIST, TemplateDSKeyExtension.class, 5,
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
        properties.add(super.createProperty(TemplateDSExtension.getPropertyDescriptor(NAME), this.name, null));
        properties.add(super.createProperty(TemplateDSExtension.getPropertyDescriptor(VERSION), this.version, null));
        properties.add(super.createProperty(TemplateDSExtension.getPropertyDescriptor(STRUCTURE), this.getStructure(),
                null));
        properties.add(super.createProperty(TemplateDSExtension.getPropertyDescriptor(KEYLIST), this.keyList, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(NAME) && (property.getType() == Name.class))) {
            this.setName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(VERSION) && (property.getType() == Version.class))) {
            this.setVersion(((Version) property.getInstance()));
            return true;
        } else if ((property.getName().equals(STRUCTURE) && (property.getType() == TemplateDSElement.class))) {
            this.setStructure(((TemplateDSElement) property.getInstance()));
            return true;
        } else if ((property.getName().equals(KEYLIST) && (property.getType() == TemplateDSKeyExtension.class))) {
            this.keyList = ((NabuccoList<TemplateDSKeyExtension>) property.getInstance());
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
        final TemplateDSExtension other = ((TemplateDSExtension) obj);
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        if ((this.version == null)) {
            if ((other.version != null))
                return false;
        } else if ((!this.version.equals(other.version)))
            return false;
        if ((this.structure == null)) {
            if ((other.structure != null))
                return false;
        } else if ((!this.structure.equals(other.structure)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.version == null) ? 0 : this.version.hashCode()));
        result = ((PRIME * result) + ((this.structure == null) ? 0 : this.structure.hashCode()));
        return result;
    }

    @Override
    public TemplateDSExtension cloneObject() {
        TemplateDSExtension clone = new TemplateDSExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The name of the extension
     *
     * @return the Name.
     */
    public Name getName() {
        return this.name;
    }

    /**
     * The name of the extension
     *
     * @param name the Name.
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * The name of the extension
     *
     * @param name the String.
     */
    public void setName(String name) {
        if ((this.name == null)) {
            if ((name == null)) {
                return;
            }
            this.name = new Name();
        }
        this.name.setValue(name);
    }

    /**
     * The version of the extension
     *
     * @return the Version.
     */
    public Version getVersion() {
        return this.version;
    }

    /**
     * The version of the extension
     *
     * @param version the Version.
     */
    public void setVersion(Version version) {
        this.version = version;
    }

    /**
     * The version of the extension
     *
     * @param version the Long.
     */
    public void setVersion(Long version) {
        if ((this.version == null)) {
            if ((version == null)) {
                return;
            }
            this.version = new Version();
        }
        this.version.setValue(version);
    }

    /**
     * The datatstructure
     *
     * @param structure the TemplateDSElement.
     */
    public void setStructure(TemplateDSElement structure) {
        this.structure = structure;
    }

    /**
     * The datatstructure
     *
     * @return the TemplateDSElement.
     */
    public TemplateDSElement getStructure() {
        return this.structure;
    }

    /**
     * The list of the keys that can be referenced in the datastructure
     *
     * @return the NabuccoList<TemplateDSKeyExtension>.
     */
    public NabuccoList<TemplateDSKeyExtension> getKeyList() {
        if ((this.keyList == null)) {
            this.keyList = new NabuccoListImpl<TemplateDSKeyExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.keyList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TemplateDSExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TemplateDSExtension.class).getAllProperties();
    }
}
