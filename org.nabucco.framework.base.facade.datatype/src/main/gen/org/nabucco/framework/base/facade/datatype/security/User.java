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
 * User<p/>A Role is a collection of permissions<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2010-01-18
 */
public class User extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "username", "owner", "description", "userType" };

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;m1,1;", "l0,n;m1,1;",
            "l0,n;m1,1;", "l0,n;m1,1;" };

    /** Name of the User */
    private Name username;

    /** Owner of the User */
    private Owner owner;

    /** of the User */
    private Description description;

    /** Code of the User */
    private CodeType userType;

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
        if ((this.getUsername() != null)) {
            clone.setUsername(this.getUsername().cloneObject());
        }
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        if ((this.getUserType() != null)) {
            clone.setUserType(this.getUserType().cloneObject());
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
                PROPERTY_CONSTRAINTS[0], this.username));
        properties.add(new BasetypeProperty<Owner>(PROPERTY_NAMES[1], Owner.class,
                PROPERTY_CONSTRAINTS[1], this.owner));
        properties.add(new BasetypeProperty<Description>(PROPERTY_NAMES[2], Description.class,
                PROPERTY_CONSTRAINTS[2], this.description));
        properties.add(new BasetypeProperty<CodeType>(PROPERTY_NAMES[3], CodeType.class,
                PROPERTY_CONSTRAINTS[3], this.userType));
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
        final User other = ((User) obj);
        if ((this.username == null)) {
            if ((other.username != null))
                return false;
        } else if ((!this.username.equals(other.username)))
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
        result = ((PRIME * result) + ((this.username == null) ? 0 : this.username.hashCode()));
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.userType == null) ? 0 : this.userType.hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<User>\n");
        appendable.append(super.toString());
        appendable.append((("<username>" + this.username) + "</username>\n"));
        appendable.append((("<owner>" + this.owner) + "</owner>\n"));
        appendable.append((("<description>" + this.description) + "</description>\n"));
        appendable.append((("<userType>" + this.userType) + "</userType>\n"));
        appendable.append("</User>\n");
        return appendable.toString();
    }

    @Override
    public User cloneObject() {
        User clone = new User();
        this.cloneObject(clone);
        return clone;
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
            this.username = new Name();
        }
        this.username.setValue(username);
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
            this.owner = new Owner();
        }
        this.owner.setValue(owner);
    }

    /**
     * of the User
     *
     * @return the Description.
     */
    public Description getDescription() {
        return this.description;
    }

    /**
     * of the User
     *
     * @param description the Description.
     */
    public void setDescription(Description description) {
        this.description = description;
    }

    /**
     * of the User
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
     * Code of the User
     *
     * @return the CodeType.
     */
    public CodeType getUserType() {
        return this.userType;
    }

    /**
     * Code of the User
     *
     * @param userType the CodeType.
     */
    public void setUserType(CodeType userType) {
        this.userType = userType;
    }

    /**
     * Code of the User
     *
     * @param userType the String.
     */
    public void setUserType(String userType) {
        if ((this.userType == null)) {
            this.userType = new CodeType();
        }
        this.userType.setValue(userType);
    }
}
