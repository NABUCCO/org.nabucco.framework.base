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
 * Group<p/>Represents a group within the authorization component<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2010-01-18
 */
public class Group extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "groupname", "owner", "description",
            "groupType" };

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;m1,1;", "l0,n;m1,1;",
            "l0,n;m1,1;", "l0,n;m1,1;" };

    /** Name of the group */
    private Name groupname;

    /** Owner of the group */
    private Owner owner;

    /** of the group */
    private Description description;

    /** Code of the group */
    private CodeType groupType;

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
        if ((this.getGroupname() != null)) {
            clone.setGroupname(this.getGroupname().cloneObject());
        }
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        if ((this.getGroupType() != null)) {
            clone.setGroupType(this.getGroupType().cloneObject());
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
                PROPERTY_CONSTRAINTS[0], this.groupname));
        properties.add(new BasetypeProperty<Owner>(PROPERTY_NAMES[1], Owner.class,
                PROPERTY_CONSTRAINTS[1], this.owner));
        properties.add(new BasetypeProperty<Description>(PROPERTY_NAMES[2], Description.class,
                PROPERTY_CONSTRAINTS[2], this.description));
        properties.add(new BasetypeProperty<CodeType>(PROPERTY_NAMES[3], CodeType.class,
                PROPERTY_CONSTRAINTS[3], this.groupType));
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
        final Group other = ((Group) obj);
        if ((this.groupname == null)) {
            if ((other.groupname != null))
                return false;
        } else if ((!this.groupname.equals(other.groupname)))
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
        result = ((PRIME * result) + ((this.groupname == null) ? 0 : this.groupname.hashCode()));
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.groupType == null) ? 0 : this.groupType.hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<Group>\n");
        appendable.append(super.toString());
        appendable.append((("<groupname>" + this.groupname) + "</groupname>\n"));
        appendable.append((("<owner>" + this.owner) + "</owner>\n"));
        appendable.append((("<description>" + this.description) + "</description>\n"));
        appendable.append((("<groupType>" + this.groupType) + "</groupType>\n"));
        appendable.append("</Group>\n");
        return appendable.toString();
    }

    @Override
    public Group cloneObject() {
        Group clone = new Group();
        this.cloneObject(clone);
        return clone;
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
            this.groupname = new Name();
        }
        this.groupname.setValue(groupname);
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
            this.owner = new Owner();
        }
        this.owner.setValue(owner);
    }

    /**
     * of the group
     *
     * @return the Description.
     */
    public Description getDescription() {
        return this.description;
    }

    /**
     * of the group
     *
     * @param description the Description.
     */
    public void setDescription(Description description) {
        this.description = description;
    }

    /**
     * of the group
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
     * Code of the group
     *
     * @return the CodeType.
     */
    public CodeType getGroupType() {
        return this.groupType;
    }

    /**
     * Code of the group
     *
     * @param groupType the CodeType.
     */
    public void setGroupType(CodeType groupType) {
        this.groupType = groupType;
    }

    /**
     * Code of the group
     *
     * @param groupType the String.
     */
    public void setGroupType(String groupType) {
        if ((this.groupType == null)) {
            this.groupType = new CodeType();
        }
        this.groupType.setValue(groupType);
    }
}
