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
 * Role<p/>A Role is a collection of permissions<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2010-01-18
 */
public class Role extends MultiTenantDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l3,12;u0,n;m1,1;", "l0,50;u0,n;m1,1;", "l0,255;u0,n;m1,1;",
            "m1,1;" };

    public static final String OWNER = "owner";

    public static final String ROLENAME = "rolename";

    public static final String DESCRIPTION = "description";

    public static final String ROLETYPE = "roleType";

    /** Owner of the Role */
    private Owner owner;

    /** Name of the role */
    private Name rolename;

    /** Description of the role */
    private Description description;

    /** Type of the role */
    private Code roleType;

    protected static final String ROLETYPE_CODEPATH = "nabucco.authorization.role";

    /** Constructs a new Role instance. */
    public Role() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the Role.
     */
    protected void cloneObject(Role clone) {
        super.cloneObject(clone);
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getRolename() != null)) {
            clone.setRolename(this.getRolename().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        if ((this.getRoleType() != null)) {
            clone.setRoleType(this.getRoleType().cloneObject());
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
        propertyMap.put(ROLENAME,
                PropertyDescriptorSupport.createBasetype(ROLENAME, Name.class, 5, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createBasetype(DESCRIPTION, Description.class, 6,
                PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(ROLETYPE, PropertyDescriptorSupport.createDatatype(ROLETYPE, Code.class, 7,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION, ROLETYPE_CODEPATH));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(Role.getPropertyDescriptor(OWNER), this.owner, null));
        properties.add(super.createProperty(Role.getPropertyDescriptor(ROLENAME), this.rolename, null));
        properties.add(super.createProperty(Role.getPropertyDescriptor(DESCRIPTION), this.description, null));
        properties.add(super.createProperty(Role.getPropertyDescriptor(ROLETYPE), this.getRoleType(), null));
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
        } else if ((property.getName().equals(ROLENAME) && (property.getType() == Name.class))) {
            this.setRolename(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == Description.class))) {
            this.setDescription(((Description) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ROLETYPE) && (property.getType() == Code.class))) {
            this.setRoleType(((Code) property.getInstance()));
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
        final Role other = ((Role) obj);
        if ((this.owner == null)) {
            if ((other.owner != null))
                return false;
        } else if ((!this.owner.equals(other.owner)))
            return false;
        if ((this.rolename == null)) {
            if ((other.rolename != null))
                return false;
        } else if ((!this.rolename.equals(other.rolename)))
            return false;
        if ((this.description == null)) {
            if ((other.description != null))
                return false;
        } else if ((!this.description.equals(other.description)))
            return false;
        if ((this.roleType == null)) {
            if ((other.roleType != null))
                return false;
        } else if ((!this.roleType.equals(other.roleType)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.rolename == null) ? 0 : this.rolename.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.roleType == null) ? 0 : this.roleType.hashCode()));
        return result;
    }

    @Override
    public Role cloneObject() {
        Role clone = new Role();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Owner of the Role
     *
     * @return the Owner.
     */
    public Owner getOwner() {
        return this.owner;
    }

    /**
     * Owner of the Role
     *
     * @param owner the Owner.
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * Owner of the Role
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
     * Name of the role
     *
     * @return the Name.
     */
    public Name getRolename() {
        return this.rolename;
    }

    /**
     * Name of the role
     *
     * @param rolename the Name.
     */
    public void setRolename(Name rolename) {
        this.rolename = rolename;
    }

    /**
     * Name of the role
     *
     * @param rolename the String.
     */
    public void setRolename(String rolename) {
        if ((this.rolename == null)) {
            if ((rolename == null)) {
                return;
            }
            this.rolename = new Name();
        }
        this.rolename.setValue(rolename);
    }

    /**
     * Description of the role
     *
     * @return the Description.
     */
    public Description getDescription() {
        return this.description;
    }

    /**
     * Description of the role
     *
     * @param description the Description.
     */
    public void setDescription(Description description) {
        this.description = description;
    }

    /**
     * Description of the role
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
     * Type of the role
     *
     * @param roleType the Code.
     */
    public void setRoleType(Code roleType) {
        this.roleType = roleType;
    }

    /**
     * Type of the role
     *
     * @return the Code.
     */
    public Code getRoleType() {
        return this.roleType;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(Role.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(Role.class).getAllProperties();
    }

    /**
     * Getter for the RoleTypeCodePath.
     *
     * @return the CodePath.
     */
    public static CodePath getRoleTypeCodePath() {
        return new CodePath(ROLETYPE_CODEPATH);
    }
}
