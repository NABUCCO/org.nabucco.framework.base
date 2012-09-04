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
package org.nabucco.framework.base.facade.datatype.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.MultiTenantDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.code.CodePath;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * User<p/>Represents a user within the authorization component<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2010-01-18
 */
public class User extends MultiTenantDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l3,12;u0,n;m1,1;", "l0,30;u0,n;m1,1;", "l0,255;u0,n;m1,1;",
            "m1,1;" };

    public static final String OWNER = "owner";

    public static final String USERNAME = "username";

    public static final String DESCRIPTION = "description";

    public static final String USERTYPE = "userType";

    /** Owner of the User */
    private Owner owner;

    /** Name of the User */
    private Name username;

    /** Description of the User */
    private Description description;

    /** Type of the User */
    private Code userType;

    protected static final String USERTYPE_CODEPATH = "nabucco.authorization.user";

    /** Constructs a new User instance. */
    public User() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the User.
     */
    protected void cloneObject(User clone) {
        super.cloneObject(clone);
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getUsername() != null)) {
            clone.setUsername(this.getUsername().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        if ((this.getUserType() != null)) {
            clone.setUserType(this.getUserType().cloneObject());
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
        propertyMap.put(USERNAME,
                PropertyDescriptorSupport.createBasetype(USERNAME, Name.class, 5, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createBasetype(DESCRIPTION, Description.class, 6,
                PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(USERTYPE, PropertyDescriptorSupport.createDatatype(USERTYPE, Code.class, 7,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION, USERTYPE_CODEPATH));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(User.getPropertyDescriptor(OWNER), this.owner, null));
        properties.add(super.createProperty(User.getPropertyDescriptor(USERNAME), this.username, null));
        properties.add(super.createProperty(User.getPropertyDescriptor(DESCRIPTION), this.description, null));
        properties.add(super.createProperty(User.getPropertyDescriptor(USERTYPE), this.getUserType(), null));
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
        } else if ((property.getName().equals(USERNAME) && (property.getType() == Name.class))) {
            this.setUsername(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == Description.class))) {
            this.setDescription(((Description) property.getInstance()));
            return true;
        } else if ((property.getName().equals(USERTYPE) && (property.getType() == Code.class))) {
            this.setUserType(((Code) property.getInstance()));
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
        final User other = ((User) obj);
        if ((this.owner == null)) {
            if ((other.owner != null))
                return false;
        } else if ((!this.owner.equals(other.owner)))
            return false;
        if ((this.username == null)) {
            if ((other.username != null))
                return false;
        } else if ((!this.username.equals(other.username)))
            return false;
        if ((this.description == null)) {
            if ((other.description != null))
                return false;
        } else if ((!this.description.equals(other.description)))
            return false;
        if ((this.userType == null)) {
            if ((other.userType != null))
                return false;
        } else if ((!this.userType.equals(other.userType)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.username == null) ? 0 : this.username.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.userType == null) ? 0 : this.userType.hashCode()));
        return result;
    }

    @Override
    public User cloneObject() {
        User clone = new User();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Owner of the User
     *
     * @return the Owner.
     */
    public Owner getOwner() {
        return this.owner;
    }

    /**
     * Owner of the User
     *
     * @param owner the Owner.
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * Owner of the User
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
     * Name of the User
     *
     * @return the Name.
     */
    public Name getUsername() {
        return this.username;
    }

    /**
     * Name of the User
     *
     * @param username the Name.
     */
    public void setUsername(Name username) {
        this.username = username;
    }

    /**
     * Name of the User
     *
     * @param username the String.
     */
    public void setUsername(String username) {
        if ((this.username == null)) {
            if ((username == null)) {
                return;
            }
            this.username = new Name();
        }
        this.username.setValue(username);
    }

    /**
     * Description of the User
     *
     * @return the Description.
     */
    public Description getDescription() {
        return this.description;
    }

    /**
     * Description of the User
     *
     * @param description the Description.
     */
    public void setDescription(Description description) {
        this.description = description;
    }

    /**
     * Description of the User
     *
     * @param description the String.
     */
    public void setDescription(String description) {
        if ((this.description == null)) {
            if ((description == null)) {
                return;
            }
            this.description = new Description();
        }
        this.description.setValue(description);
    }

    /**
     * Type of the User
     *
     * @param userType the Code.
     */
    public void setUserType(Code userType) {
        this.userType = userType;
    }

    /**
     * Type of the User
     *
     * @return the Code.
     */
    public Code getUserType() {
        return this.userType;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(User.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(User.class).getAllProperties();
    }

    /**
     * Getter for the UserTypeCodePath.
     *
     * @return the CodePath.
     */
    public static CodePath getUserTypeCodePath() {
        return new CodePath(USERTYPE_CODEPATH);
    }
}
