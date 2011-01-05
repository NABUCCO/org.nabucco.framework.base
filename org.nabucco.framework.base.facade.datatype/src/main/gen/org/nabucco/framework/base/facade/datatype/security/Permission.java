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
 * Permission<p/>A Permission, assigned to a Role, User or Group<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2010-01-18
 */
public class Permission extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "permissionname", "owner", "description",
            "permissionType" };

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;m1,1;", "l0,n;m1,1;",
            "l0,n;m1,1;", "l0,n;m1,1;" };

    /** Name of the permission */
    private Name permissionname;

    /** Owner of the permission */
    private Owner owner;

    /** of the permission */
    private Description description;

    /** Code of the permission */
    private CodeType permissionType;

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
        if ((this.getPermissionname() != null)) {
            clone.setPermissionname(this.getPermissionname().cloneObject());
        }
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        if ((this.getPermissionType() != null)) {
            clone.setPermissionType(this.getPermissionType().cloneObject());
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
                PROPERTY_CONSTRAINTS[0], this.permissionname));
        properties.add(new BasetypeProperty<Owner>(PROPERTY_NAMES[1], Owner.class,
                PROPERTY_CONSTRAINTS[1], this.owner));
        properties.add(new BasetypeProperty<Description>(PROPERTY_NAMES[2], Description.class,
                PROPERTY_CONSTRAINTS[2], this.description));
        properties.add(new BasetypeProperty<CodeType>(PROPERTY_NAMES[3], CodeType.class,
                PROPERTY_CONSTRAINTS[3], this.permissionType));
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
        final Permission other = ((Permission) obj);
        if ((this.permissionname == null)) {
            if ((other.permissionname != null))
                return false;
        } else if ((!this.permissionname.equals(other.permissionname)))
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
        result = ((PRIME * result) + ((this.permissionname == null) ? 0 : this.permissionname
                .hashCode()));
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.permissionType == null) ? 0 : this.permissionType
                .hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<Permission>\n");
        appendable.append(super.toString());
        appendable.append((("<permissionname>" + this.permissionname) + "</permissionname>\n"));
        appendable.append((("<owner>" + this.owner) + "</owner>\n"));
        appendable.append((("<description>" + this.description) + "</description>\n"));
        appendable.append((("<permissionType>" + this.permissionType) + "</permissionType>\n"));
        appendable.append("</Permission>\n");
        return appendable.toString();
    }

    @Override
    public Permission cloneObject() {
        Permission clone = new Permission();
        this.cloneObject(clone);
        return clone;
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
            this.permissionname = new Name();
        }
        this.permissionname.setValue(permissionname);
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
            this.owner = new Owner();
        }
        this.owner.setValue(owner);
    }

    /**
     * of the permission
     *
     * @return the Description.
     */
    public Description getDescription() {
        return this.description;
    }

    /**
     * of the permission
     *
     * @param description the Description.
     */
    public void setDescription(Description description) {
        this.description = description;
    }

    /**
     * of the permission
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
     * Code of the permission
     *
     * @return the CodeType.
     */
    public CodeType getPermissionType() {
        return this.permissionType;
    }

    /**
     * Code of the permission
     *
     * @param permissionType the CodeType.
     */
    public void setPermissionType(CodeType permissionType) {
        this.permissionType = permissionType;
    }

    /**
     * Code of the permission
     *
     * @param permissionType the String.
     */
    public void setPermissionType(String permissionType) {
        if ((this.permissionType == null)) {
            this.permissionType = new CodeType();
        }
        this.permissionType.setValue(permissionType);
    }
}
