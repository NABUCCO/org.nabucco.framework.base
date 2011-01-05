/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.security;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.code.CodeType;
import org.nabucco.framework.base.facade.datatype.property.BasetypeProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;

/**
 * Role<p/>A Role is a collection of permissions<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2010-01-18
 */
public class Role extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "rolename", "owner", "description", "roleType" };

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;m1,1;", "l0,n;m1,1;",
            "l0,n;m1,1;", "l0,n;m1,1;" };

    /** Name of the role */
    private Name rolename;

    /** Owner of the Role */
    private Owner owner;

    /** of the role */
    private Description description;

    /** Code of the role */
    private CodeType roleType;

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
        if ((this.getRolename() != null)) {
            clone.setRolename(this.getRolename().cloneObject());
        }
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        if ((this.getRoleType() != null)) {
            clone.setRoleType(this.getRoleType().cloneObject());
        }
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new BasetypeProperty<Name>(PROPERTY_NAMES[0], Name.class,
                PROPERTY_CONSTRAINTS[0], this.rolename));
        properties.add(new BasetypeProperty<Owner>(PROPERTY_NAMES[1], Owner.class,
                PROPERTY_CONSTRAINTS[1], this.owner));
        properties.add(new BasetypeProperty<Description>(PROPERTY_NAMES[2], Description.class,
                PROPERTY_CONSTRAINTS[2], this.description));
        properties.add(new BasetypeProperty<CodeType>(PROPERTY_NAMES[3], CodeType.class,
                PROPERTY_CONSTRAINTS[3], this.roleType));
        return properties;
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
        if ((this.rolename == null)) {
            if ((other.rolename != null))
                return false;
        } else if ((!this.rolename.equals(other.rolename)))
            return false;
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
        result = ((PRIME * result) + ((this.rolename == null) ? 0 : this.rolename.hashCode()));
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.roleType == null) ? 0 : this.roleType.hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<Role>\n");
        appendable.append(super.toString());
        appendable.append((("<rolename>" + this.rolename) + "</rolename>\n"));
        appendable.append((("<owner>" + this.owner) + "</owner>\n"));
        appendable.append((("<description>" + this.description) + "</description>\n"));
        appendable.append((("<roleType>" + this.roleType) + "</roleType>\n"));
        appendable.append("</Role>\n");
        return appendable.toString();
    }

    @Override
    public Role cloneObject() {
        Role clone = new Role();
        this.cloneObject(clone);
        return clone;
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
            this.rolename = new Name();
        }
        this.rolename.setValue(rolename);
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
            this.owner = new Owner();
        }
        this.owner.setValue(owner);
    }

    /**
     * of the role
     *
     * @return the Description.
     */
    public Description getDescription() {
        return this.description;
    }

    /**
     * of the role
     *
     * @param description the Description.
     */
    public void setDescription(Description description) {
        this.description = description;
    }

    /**
     * of the role
     *
     * @param description the String.
     */
    public void setDescription(String description) {
        if ((this.description == null)) {
            this.description = new Description();
        }
        this.description.setValue(description);
    }

    /**
     * Code of the role
     *
     * @return the CodeType.
     */
    public CodeType getRoleType() {
        return this.roleType;
    }

    /**
     * Code of the role
     *
     * @param roleType the CodeType.
     */
    public void setRoleType(CodeType roleType) {
        this.roleType = roleType;
    }

    /**
     * Code of the role
     *
     * @param roleType the String.
     */
    public void setRoleType(String roleType) {
        if ((this.roleType == null)) {
            this.roleType = new CodeType();
        }
        this.roleType.setValue(roleType);
    }
}
