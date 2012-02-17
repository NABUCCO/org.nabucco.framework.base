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
 * Permission<p/>A Permission, assigned to a Role, User or Group<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2010-01-18
 */
public class Permission extends MultiTenantDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l3,12;u0,n;m1,1;", "l0,255;u0,n;m1,1;",
            "l0,255;u0,n;m1,1;", "m1,1;" };

    public static final String OWNER = "owner";

    public static final String PERMISSIONNAME = "permissionname";

    public static final String DESCRIPTION = "description";

    public static final String PERMISSIONTYPE = "permissionType";

    /** Owner of the permission */
    private Owner owner;

    /** Name of the permission */
    private Name permissionname;

    /** Description of the permission */
    private Description description;

    /** Type of the permission */
    private Code permissionType;

    protected static final String PERMISSIONTYPE_CODEPATH = "nabucco.authorization.permission";

    /** Constructs a new Permission instance. */
    public Permission() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the Permission.
     */
    protected void cloneObject(Permission clone) {
        super.cloneObject(clone);
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getPermissionname() != null)) {
            clone.setPermissionname(this.getPermissionname().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        if ((this.getPermissionType() != null)) {
            clone.setPermissionType(this.getPermissionType().cloneObject());
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
        propertyMap
                .put(PERMISSIONNAME, PropertyDescriptorSupport.createBasetype(PERMISSIONNAME, Name.class, 5,
                        PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createBasetype(DESCRIPTION, Description.class, 6,
                PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(PERMISSIONTYPE, PropertyDescriptorSupport.createDatatype(PERMISSIONTYPE, Code.class, 7,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION, PERMISSIONTYPE_CODEPATH));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(Permission.getPropertyDescriptor(OWNER), this.owner, null));
        properties
                .add(super.createProperty(Permission.getPropertyDescriptor(PERMISSIONNAME), this.permissionname, null));
        properties.add(super.createProperty(Permission.getPropertyDescriptor(DESCRIPTION), this.description, null));
        properties.add(super.createProperty(Permission.getPropertyDescriptor(PERMISSIONTYPE), this.getPermissionType(),
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
        } else if ((property.getName().equals(PERMISSIONNAME) && (property.getType() == Name.class))) {
            this.setPermissionname(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == Description.class))) {
            this.setDescription(((Description) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PERMISSIONTYPE) && (property.getType() == Code.class))) {
            this.setPermissionType(((Code) property.getInstance()));
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
        final Permission other = ((Permission) obj);
        if ((this.owner == null)) {
            if ((other.owner != null))
                return false;
        } else if ((!this.owner.equals(other.owner)))
            return false;
        if ((this.permissionname == null)) {
            if ((other.permissionname != null))
                return false;
        } else if ((!this.permissionname.equals(other.permissionname)))
            return false;
        if ((this.description == null)) {
            if ((other.description != null))
                return false;
        } else if ((!this.description.equals(other.description)))
            return false;
        if ((this.permissionType == null)) {
            if ((other.permissionType != null))
                return false;
        } else if ((!this.permissionType.equals(other.permissionType)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.permissionname == null) ? 0 : this.permissionname.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.permissionType == null) ? 0 : this.permissionType.hashCode()));
        return result;
    }

    @Override
    public Permission cloneObject() {
        Permission clone = new Permission();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Owner of the permission
     *
     * @return the Owner.
     */
    public Owner getOwner() {
        return this.owner;
    }

    /**
     * Owner of the permission
     *
     * @param owner the Owner.
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * Owner of the permission
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
     * Name of the permission
     *
     * @return the Name.
     */
    public Name getPermissionname() {
        return this.permissionname;
    }

    /**
     * Name of the permission
     *
     * @param permissionname the Name.
     */
    public void setPermissionname(Name permissionname) {
        this.permissionname = permissionname;
    }

    /**
     * Name of the permission
     *
     * @param permissionname the String.
     */
    public void setPermissionname(String permissionname) {
        if ((this.permissionname == null)) {
            if ((permissionname == null)) {
                return;
            }
            this.permissionname = new Name();
        }
        this.permissionname.setValue(permissionname);
    }

    /**
     * Description of the permission
     *
     * @return the Description.
     */
    public Description getDescription() {
        return this.description;
    }

    /**
     * Description of the permission
     *
     * @param description the Description.
     */
    public void setDescription(Description description) {
        this.description = description;
    }

    /**
     * Description of the permission
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
     * Type of the permission
     *
     * @param permissionType the Code.
     */
    public void setPermissionType(Code permissionType) {
        this.permissionType = permissionType;
    }

    /**
     * Type of the permission
     *
     * @return the Code.
     */
    public Code getPermissionType() {
        return this.permissionType;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(Permission.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(Permission.class).getAllProperties();
    }

    /**
     * Getter for the PermissionTypeCodePath.
     *
     * @return the CodePath.
     */
    public static CodePath getPermissionTypeCodePath() {
        return new CodePath(PERMISSIONTYPE_CODEPATH);
    }
}
