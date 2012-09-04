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
package org.nabucco.framework.base.facade.datatype;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeSupport;
import org.nabucco.framework.base.facade.datatype.ExtendedAttribute;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.Version;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * NabuccoDatatype<p/>Common datatype for all NABUCCO datatypes, defines technical id and version<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-02-15
 */
public abstract class NabuccoDatatype extends DatatypeSupport implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;u0,n;m0,1;", "l0,n;u0,n;m0,1;", "m0,n;" };

    public static final String ID = "id";

    public static final String VERSION = "version";

    public static final String EXTENDEDATTRIBUTES = "extendedAttributes";

    /** Identifier for all datatypes, represents DB foreign key column */
    private Identifier id;

    /** Version for all datatypes, represents DB version column */
    private Version version;

    /** The list of extended attributes. */
    private NabuccoList<ExtendedAttribute> extendedAttributes;

    /** Constructs a new NabuccoDatatype instance. */
    public NabuccoDatatype() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the NabuccoDatatype.
     */
    protected void cloneObject(NabuccoDatatype clone) {
        super.cloneObject(clone);
        clone.setId(this.getId());
        clone.setVersion(this.getVersion());
        if ((this.extendedAttributes != null)) {
            clone.extendedAttributes = this.extendedAttributes.cloneCollection();
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(ID,
                PropertyDescriptorSupport.createBasetype(ID, Identifier.class, 0, PROPERTY_CONSTRAINTS[0], true));
        propertyMap.put(VERSION,
                PropertyDescriptorSupport.createBasetype(VERSION, Version.class, 1, PROPERTY_CONSTRAINTS[1], true));
        propertyMap.put(EXTENDEDATTRIBUTES, PropertyDescriptorSupport.createCollection(EXTENDEDATTRIBUTES,
                ExtendedAttribute.class, 2, PROPERTY_CONSTRAINTS[2], true, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(NabuccoDatatype.getPropertyDescriptor(ID), this.id, null));
        properties.add(super.createProperty(NabuccoDatatype.getPropertyDescriptor(VERSION), this.version, null));
        properties.add(super.createProperty(NabuccoDatatype.getPropertyDescriptor(EXTENDEDATTRIBUTES),
                this.extendedAttributes, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(ID) && (property.getType() == Identifier.class))) {
            this.setId(((Identifier) property.getInstance()));
            return true;
        } else if ((property.getName().equals(VERSION) && (property.getType() == Version.class))) {
            this.setVersion(((Version) property.getInstance()));
            return true;
        } else if ((property.getName().equals(EXTENDEDATTRIBUTES) && (property.getType() == ExtendedAttribute.class))) {
            this.extendedAttributes = ((NabuccoList<ExtendedAttribute>) property.getInstance());
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
        final NabuccoDatatype other = ((NabuccoDatatype) obj);
        if ((this.id == null)) {
            if ((other.id != null))
                return false;
        } else if ((!this.id.equals(other.id)))
            return false;
        if ((this.version == null)) {
            if ((other.version != null))
                return false;
        } else if ((!this.version.equals(other.version)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.id == null) ? 0 : this.id.hashCode()));
        result = ((PRIME * result) + ((this.version == null) ? 0 : this.version.hashCode()));
        return result;
    }

    @Override
    public abstract NabuccoDatatype cloneObject();

    /**
     * Identifier for all datatypes, represents DB foreign key column
     *
     * @return the Long.
     */
    public Long getId() {
        if ((this.id == null)) {
            return null;
        }
        return this.id.getValue();
    }

    /**
     * Identifier for all datatypes, represents DB foreign key column
     *
     * @param id the Identifier.
     */
    public void setId(Identifier id) {
        this.id = id;
    }

    /**
     * Identifier for all datatypes, represents DB foreign key column
     *
     * @param id the Long.
     */
    public void setId(Long id) {
        if ((this.id == null)) {
            if ((id == null)) {
                return;
            }
            this.id = new Identifier();
        }
        this.id.setValue(id);
    }

    /**
     * Version for all datatypes, represents DB version column
     *
     * @return the Long.
     */
    public Long getVersion() {
        if ((this.version == null)) {
            return null;
        }
        return this.version.getValue();
    }

    /**
     * Version for all datatypes, represents DB version column
     *
     * @param version the Version.
     */
    public void setVersion(Version version) {
        this.version = version;
    }

    /**
     * Version for all datatypes, represents DB version column
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
     * The list of extended attributes.
     *
     * @return the NabuccoList<ExtendedAttribute>.
     */
    public NabuccoList<ExtendedAttribute> getExtendedAttributes() {
        if ((this.extendedAttributes == null)) {
            this.extendedAttributes = new NabuccoListImpl<ExtendedAttribute>(NabuccoCollectionState.INITIALIZED);
        }
        return this.extendedAttributes;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(NabuccoDatatype.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(NabuccoDatatype.class).getAllProperties();
    }
}
