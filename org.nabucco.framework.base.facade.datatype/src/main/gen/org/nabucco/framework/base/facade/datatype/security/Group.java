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
 * Group<p/>Represents a group within the authorization component<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2010-01-18
 */
public class Group extends MultiTenantDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l3,12;u0,n;m1,1;", "l0,30;u0,n;m1,1;", "l0,255;u0,n;m1,1;",
            "m1,1;" };

    public static final String OWNER = "owner";

    public static final String GROUPNAME = "groupname";

    public static final String DESCRIPTION = "description";

    public static final String GROUPTYPE = "groupType";

    /** Owner of the group */
    private Owner owner;

    /** Name of the group */
    private Name groupname;

    /** Description of the group */
    private Description description;

    /** Type of the group */
    private Code groupType;

    protected static final String GROUPTYPE_CODEPATH = "nabucco.authorization.group";

    /** Constructs a new Group instance. */
    public Group() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the Group.
     */
    protected void cloneObject(Group clone) {
        super.cloneObject(clone);
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getGroupname() != null)) {
            clone.setGroupname(this.getGroupname().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        if ((this.getGroupType() != null)) {
            clone.setGroupType(this.getGroupType().cloneObject());
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
        propertyMap.put(GROUPNAME,
                PropertyDescriptorSupport.createBasetype(GROUPNAME, Name.class, 5, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createBasetype(DESCRIPTION, Description.class, 6,
                PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(GROUPTYPE, PropertyDescriptorSupport.createDatatype(GROUPTYPE, Code.class, 7,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION, GROUPTYPE_CODEPATH));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(Group.getPropertyDescriptor(OWNER), this.owner, null));
        properties.add(super.createProperty(Group.getPropertyDescriptor(GROUPNAME), this.groupname, null));
        properties.add(super.createProperty(Group.getPropertyDescriptor(DESCRIPTION), this.description, null));
        properties.add(super.createProperty(Group.getPropertyDescriptor(GROUPTYPE), this.getGroupType(), null));
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
        } else if ((property.getName().equals(GROUPNAME) && (property.getType() == Name.class))) {
            this.setGroupname(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == Description.class))) {
            this.setDescription(((Description) property.getInstance()));
            return true;
        } else if ((property.getName().equals(GROUPTYPE) && (property.getType() == Code.class))) {
            this.setGroupType(((Code) property.getInstance()));
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
        final Group other = ((Group) obj);
        if ((this.owner == null)) {
            if ((other.owner != null))
                return false;
        } else if ((!this.owner.equals(other.owner)))
            return false;
        if ((this.groupname == null)) {
            if ((other.groupname != null))
                return false;
        } else if ((!this.groupname.equals(other.groupname)))
            return false;
        if ((this.description == null)) {
            if ((other.description != null))
                return false;
        } else if ((!this.description.equals(other.description)))
            return false;
        if ((this.groupType == null)) {
            if ((other.groupType != null))
                return false;
        } else if ((!this.groupType.equals(other.groupType)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.groupname == null) ? 0 : this.groupname.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.groupType == null) ? 0 : this.groupType.hashCode()));
        return result;
    }

    @Override
    public Group cloneObject() {
        Group clone = new Group();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Owner of the group
     *
     * @return the Owner.
     */
    public Owner getOwner() {
        return this.owner;
    }

    /**
     * Owner of the group
     *
     * @param owner the Owner.
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * Owner of the group
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
     * Name of the group
     *
     * @return the Name.
     */
    public Name getGroupname() {
        return this.groupname;
    }

    /**
     * Name of the group
     *
     * @param groupname the Name.
     */
    public void setGroupname(Name groupname) {
        this.groupname = groupname;
    }

    /**
     * Name of the group
     *
     * @param groupname the String.
     */
    public void setGroupname(String groupname) {
        if ((this.groupname == null)) {
            if ((groupname == null)) {
                return;
            }
            this.groupname = new Name();
        }
        this.groupname.setValue(groupname);
    }

    /**
     * Description of the group
     *
     * @return the Description.
     */
    public Description getDescription() {
        return this.description;
    }

    /**
     * Description of the group
     *
     * @param description the Description.
     */
    public void setDescription(Description description) {
        this.description = description;
    }

    /**
     * Description of the group
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
     * Type of the group
     *
     * @param groupType the Code.
     */
    public void setGroupType(Code groupType) {
        this.groupType = groupType;
    }

    /**
     * Type of the group
     *
     * @return the Code.
     */
    public Code getGroupType() {
        return this.groupType;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(Group.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(Group.class).getAllProperties();
    }

    /**
     * Getter for the GroupTypeCodePath.
     *
     * @return the CodePath.
     */
    public static CodePath getGroupTypeCodePath() {
        return new CodePath(GROUPTYPE_CODEPATH);
    }
}
