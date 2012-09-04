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
package org.nabucco.framework.base.facade.datatype.business.address;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.FunctionalIdentifier;
import org.nabucco.framework.base.facade.datatype.MultiTenantDatatype;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.StatusType;
import org.nabucco.framework.base.facade.datatype.business.address.AddressType;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.code.CodePath;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.text.LongDescription;

/**
 * Address<p/>An address in the Address Component.<p/>
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-12-07
 */
public class Address extends MultiTenantDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final StatusType STATUSTYPE_DEFAULT = StatusType.ACTIVE;

    private static final String[] PROPERTY_CONSTRAINTS = { "l3,12;u0,n;m1,1;", "l0,4000;u0,n;m0,1;", "m1,1;", "m1,1;",
            "l0,n;u0,n;m0,1;", "m0,1;" };

    public static final String OWNER = "owner";

    public static final String DESCRIPTION = "description";

    public static final String TYPE = "type";

    public static final String STATUSTYPE = "statusType";

    public static final String FUNCTIONALID = "functionalId";

    public static final String FUNCTIONALTYPE = "functionalType";

    /** The Address owner. */
    private Owner owner;

    /** The Address description. */
    private LongDescription description;

    /** Type of the address. */
    protected AddressType type;

    /** The Address status type. */
    private StatusType statusType;

    /** The functional Address ID. */
    private FunctionalIdentifier functionalId;

    /** The functional Address Type. */
    private Code functionalType;

    protected static final String FUNCTIONALTYPE_CODEPATH = "nabucco.business.address.functionaltype";

    /** Constructs a new Address instance. */
    public Address() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
        statusType = STATUSTYPE_DEFAULT;
    }

    /**
     * CloneObject.
     *
     * @param clone the Address.
     */
    protected void cloneObject(Address clone) {
        super.cloneObject(clone);
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        clone.setType(this.getType());
        clone.setStatusType(this.getStatusType());
        if ((this.getFunctionalId() != null)) {
            clone.setFunctionalId(this.getFunctionalId().cloneObject());
        }
        if ((this.getFunctionalType() != null)) {
            clone.setFunctionalType(this.getFunctionalType().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(MultiTenantDatatype.class).getPropertyMap());
        propertyMap.put(OWNER,
                PropertyDescriptorSupport.createBasetype(OWNER, Owner.class, 4, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createBasetype(DESCRIPTION, LongDescription.class, 5,
                PROPERTY_CONSTRAINTS[1], false));
        propertyMap
                .put(TYPE, PropertyDescriptorSupport.createEnumeration(TYPE, AddressType.class, 6,
                        PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(STATUSTYPE, PropertyDescriptorSupport.createEnumeration(STATUSTYPE, StatusType.class, 7,
                PROPERTY_CONSTRAINTS[3], true));
        propertyMap.put(FUNCTIONALID, PropertyDescriptorSupport.createBasetype(FUNCTIONALID,
                FunctionalIdentifier.class, 8, PROPERTY_CONSTRAINTS[4], false));
        propertyMap.put(FUNCTIONALTYPE, PropertyDescriptorSupport.createDatatype(FUNCTIONALTYPE, Code.class, 9,
                PROPERTY_CONSTRAINTS[5], false, PropertyAssociationType.COMPOSITION, FUNCTIONALTYPE_CODEPATH));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(Address.getPropertyDescriptor(OWNER), this.owner, null));
        properties.add(super.createProperty(Address.getPropertyDescriptor(DESCRIPTION), this.description, null));
        properties.add(super.createProperty(Address.getPropertyDescriptor(TYPE), this.getType(), null));
        properties.add(super.createProperty(Address.getPropertyDescriptor(STATUSTYPE), this.getStatusType(), null));
        properties.add(super.createProperty(Address.getPropertyDescriptor(FUNCTIONALID), this.functionalId, null));
        properties.add(super.createProperty(Address.getPropertyDescriptor(FUNCTIONALTYPE), this.getFunctionalType(),
                null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(OWNER) && (property.getType() == Owner.class))) {
            this.setOwner(((Owner) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == LongDescription.class))) {
            this.setDescription(((LongDescription) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TYPE) && (property.getType() == AddressType.class))) {
            this.setType(((AddressType) property.getInstance()));
            return true;
        } else if ((property.getName().equals(STATUSTYPE) && (property.getType() == StatusType.class))) {
            this.setStatusType(((StatusType) property.getInstance()));
            return true;
        } else if ((property.getName().equals(FUNCTIONALID) && (property.getType() == FunctionalIdentifier.class))) {
            this.setFunctionalId(((FunctionalIdentifier) property.getInstance()));
            return true;
        } else if ((property.getName().equals(FUNCTIONALTYPE) && (property.getType() == Code.class))) {
            this.setFunctionalType(((Code) property.getInstance()));
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
        final Address other = ((Address) obj);
        if ((this.owner == null)) {
            if ((other.owner != null))
                return false;
        } else if ((!this.owner.equals(other.owner)))
            return false;
        if ((this.description == null)) {
            if ((other.description != null))
                return false;
        } else if ((!this.description.equals(other.description)))
            return false;
        if ((this.type == null)) {
            if ((other.type != null))
                return false;
        } else if ((!this.type.equals(other.type)))
            return false;
        if ((this.statusType == null)) {
            if ((other.statusType != null))
                return false;
        } else if ((!this.statusType.equals(other.statusType)))
            return false;
        if ((this.functionalId == null)) {
            if ((other.functionalId != null))
                return false;
        } else if ((!this.functionalId.equals(other.functionalId)))
            return false;
        if ((this.functionalType == null)) {
            if ((other.functionalType != null))
                return false;
        } else if ((!this.functionalType.equals(other.functionalType)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.type == null) ? 0 : this.type.hashCode()));
        result = ((PRIME * result) + ((this.statusType == null) ? 0 : this.statusType.hashCode()));
        result = ((PRIME * result) + ((this.functionalId == null) ? 0 : this.functionalId.hashCode()));
        result = ((PRIME * result) + ((this.functionalType == null) ? 0 : this.functionalType.hashCode()));
        return result;
    }

    @Override
    public Address cloneObject() {
        Address clone = new Address();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The Address owner.
     *
     * @return the Owner.
     */
    public Owner getOwner() {
        return this.owner;
    }

    /**
     * The Address owner.
     *
     * @param owner the Owner.
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * The Address owner.
     *
     * @param owner the String.
     */
    public void setOwner(String owner) {
        if ((this.owner == null)) {
            if ((owner == null)) {
                return;
            }
            this.owner = new Owner();
        }
        this.owner.setValue(owner);
    }

    /**
     * The Address description.
     *
     * @return the LongDescription.
     */
    public LongDescription getDescription() {
        return this.description;
    }

    /**
     * The Address description.
     *
     * @param description the LongDescription.
     */
    public void setDescription(LongDescription description) {
        this.description = description;
    }

    /**
     * The Address description.
     *
     * @param description the String.
     */
    public void setDescription(String description) {
        if ((this.description == null)) {
            if ((description == null)) {
                return;
            }
            this.description = new LongDescription();
        }
        this.description.setValue(description);
    }

    /**
     * Type of the address.
     *
     * @return the AddressType.
     */
    public AddressType getType() {
        return this.type;
    }

    /**
     * Type of the address.
     *
     * @param type the AddressType.
     */
    public void setType(AddressType type) {
        this.type = type;
    }

    /**
     * Type of the address.
     *
     * @param type the String.
     */
    public void setType(String type) {
        if ((type == null)) {
            this.type = null;
        } else {
            this.type = AddressType.valueOf(type);
        }
    }

    /**
     * The Address status type.
     *
     * @return the StatusType.
     */
    public StatusType getStatusType() {
        return this.statusType;
    }

    /**
     * The Address status type.
     *
     * @param statusType the StatusType.
     */
    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    /**
     * The Address status type.
     *
     * @param statusType the String.
     */
    public void setStatusType(String statusType) {
        if ((statusType == null)) {
            this.statusType = null;
        } else {
            this.statusType = StatusType.valueOf(statusType);
        }
    }

    /**
     * The functional Address ID.
     *
     * @return the FunctionalIdentifier.
     */
    public FunctionalIdentifier getFunctionalId() {
        return this.functionalId;
    }

    /**
     * The functional Address ID.
     *
     * @param functionalId the FunctionalIdentifier.
     */
    public void setFunctionalId(FunctionalIdentifier functionalId) {
        this.functionalId = functionalId;
    }

    /**
     * The functional Address ID.
     *
     * @param functionalId the String.
     */
    public void setFunctionalId(String functionalId) {
        if ((this.functionalId == null)) {
            if ((functionalId == null)) {
                return;
            }
            this.functionalId = new FunctionalIdentifier();
        }
        this.functionalId.setValue(functionalId);
    }

    /**
     * The functional Address Type.
     *
     * @param functionalType the Code.
     */
    public void setFunctionalType(Code functionalType) {
        this.functionalType = functionalType;
    }

    /**
     * The functional Address Type.
     *
     * @return the Code.
     */
    public Code getFunctionalType() {
        return this.functionalType;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(Address.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(Address.class).getAllProperties();
    }

    /**
     * Getter for the FunctionalTypeCodePath.
     *
     * @return the CodePath.
     */
    public static CodePath getFunctionalTypeCodePath() {
        return new CodePath(FUNCTIONALTYPE_CODEPATH);
    }
}
