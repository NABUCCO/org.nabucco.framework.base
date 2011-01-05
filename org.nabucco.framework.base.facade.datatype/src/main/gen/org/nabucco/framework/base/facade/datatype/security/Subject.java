/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.security;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeSupport;
import org.nabucco.framework.base.facade.datatype.DateTime;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.property.BasetypeProperty;
import org.nabucco.framework.base.facade.datatype.property.DatatypeProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.security.User;
import org.nabucco.framework.base.facade.datatype.security.UserId;
import org.nabucco.framework.base.facade.datatype.security.credential.Token;

/**
 * Subject<p/>A Subject represents an authenticated user.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-06-01
 */
public class Subject extends DatatypeSupport implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "userId", "owner", "token", "loginTime",
            "user" };

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;m1,1;", "l0,n;m1,1;",
            "l0,n;m1,1;", "l0,n;m1,1;", "m0,1;" };

    /** ID/Name of the user */
    private UserId userId;

    /** Owner of the subject */
    private Owner owner;

    /** Authentication token, is generated during authentication */
    private Token token;

    /** Authentication time, is generated during authentication */
    private DateTime loginTime;

    /** The actual user object */
    private User user;

    private Long userRefId;

    /** Constructs a new Subject instance. */
    public Subject() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the Subject.
     */
    protected void cloneObject(Subject clone) {
        super.cloneObject(clone);
        if ((this.getUserId() != null)) {
            clone.setUserId(this.getUserId().cloneObject());
        }
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getToken() != null)) {
            clone.setToken(this.getToken().cloneObject());
        }
        if ((this.getLoginTime() != null)) {
            clone.setLoginTime(this.getLoginTime().cloneObject());
        }
        if ((this.getUser() != null)) {
            clone.setUser(this.getUser().cloneObject());
        }
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new BasetypeProperty<UserId>(PROPERTY_NAMES[0], UserId.class,
                PROPERTY_CONSTRAINTS[0], this.userId));
        properties.add(new BasetypeProperty<Owner>(PROPERTY_NAMES[1], Owner.class,
                PROPERTY_CONSTRAINTS[1], this.owner));
        properties.add(new BasetypeProperty<Token>(PROPERTY_NAMES[2], Token.class,
                PROPERTY_CONSTRAINTS[2], this.token));
        properties.add(new BasetypeProperty<DateTime>(PROPERTY_NAMES[3], DateTime.class,
                PROPERTY_CONSTRAINTS[3], this.loginTime));
        properties.add(new DatatypeProperty<User>(PROPERTY_NAMES[4], User.class,
                PROPERTY_CONSTRAINTS[4], this.user));
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
        final Subject other = ((Subject) obj);
        if ((this.userId == null)) {
            if ((other.userId != null))
                return false;
        } else if ((!this.userId.equals(other.userId)))
            return false;
        if ((this.owner == null)) {
            if ((other.owner != null))
                return false;
        } else if ((!this.owner.equals(other.owner)))
            return false;
        if ((this.token == null)) {
            if ((other.token != null))
                return false;
        } else if ((!this.token.equals(other.token)))
            return false;
        if ((this.loginTime == null)) {
            if ((other.loginTime != null))
                return false;
        } else if ((!this.loginTime.equals(other.loginTime)))
            return false;
        if ((this.user == null)) {
            if ((other.user != null))
                return false;
        } else if ((!this.user.equals(other.user)))
            return false;
        if ((this.userRefId == null)) {
            if ((other.userRefId != null))
                return false;
        } else if ((!this.userRefId.equals(other.userRefId)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.userId == null) ? 0 : this.userId.hashCode()));
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.token == null) ? 0 : this.token.hashCode()));
        result = ((PRIME * result) + ((this.loginTime == null) ? 0 : this.loginTime.hashCode()));
        result = ((PRIME * result) + ((this.user == null) ? 0 : this.user.hashCode()));
        result = ((PRIME * result) + ((this.userRefId == null) ? 0 : this.userRefId.hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<Subject>\n");
        appendable.append(super.toString());
        appendable.append((("<userId>" + this.userId) + "</userId>\n"));
        appendable.append((("<owner>" + this.owner) + "</owner>\n"));
        appendable.append((("<token>" + this.token) + "</token>\n"));
        appendable.append((("<loginTime>" + this.loginTime) + "</loginTime>\n"));
        appendable.append((("<user>" + this.user) + "</user>\n"));
        appendable.append((("<userRefId>" + this.userRefId) + "</userRefId>\n"));
        appendable.append("</Subject>\n");
        return appendable.toString();
    }

    @Override
    public Subject cloneObject() {
        Subject clone = new Subject();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * ID/Name of the user
     *
     * @return the UserId.
     */
    public UserId getUserId() {
        return this.userId;
    }

    /**
     * ID/Name of the user
     *
     * @param userId the UserId.
     */
    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    /**
     * ID/Name of the user
     *
     * @param userId the String.
     */
    public void setUserId(String userId) {
        if ((this.userId == null)) {
            this.userId = new UserId();
        }
        this.userId.setValue(userId);
    }

    /**
     * Owner of the subject
     *
     * @return the Owner.
     */
    public Owner getOwner() {
        return this.owner;
    }

    /**
     * Owner of the subject
     *
     * @param owner the Owner.
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * Owner of the subject
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
     * Authentication token, is generated during authentication
     *
     * @return the Token.
     */
    public Token getToken() {
        return this.token;
    }

    /**
     * Authentication token, is generated during authentication
     *
     * @param token the Token.
     */
    public void setToken(Token token) {
        this.token = token;
    }

    /**
     * Authentication token, is generated during authentication
     *
     * @param token the byte[].
     */
    public void setToken(byte[] token) {
        if ((this.token == null)) {
            this.token = new Token();
        }
        this.token.setValue(token);
    }

    /**
     * Authentication time, is generated during authentication
     *
     * @return the DateTime.
     */
    public DateTime getLoginTime() {
        return this.loginTime;
    }

    /**
     * Authentication time, is generated during authentication
     *
     * @param loginTime the DateTime.
     */
    public void setLoginTime(DateTime loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * Authentication time, is generated during authentication
     *
     * @param loginTime the Long.
     */
    public void setLoginTime(Long loginTime) {
        if ((this.loginTime == null)) {
            this.loginTime = new DateTime();
        }
        this.loginTime.setValue(loginTime);
    }

    /**
     * The actual user object
     *
     * @param user the User.
     */
    public void setUser(User user) {
        this.user = user;
        if ((user != null)) {
            this.setUserRefId(user.getId());
        } else {
            this.setUserRefId(null);
        }
    }

    /**
     * The actual user object
     *
     * @return the User.
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Getter for the UserRefId.
     *
     * @return the Long.
     */
    public Long getUserRefId() {
        return this.userRefId;
    }

    /**
     * Setter for the UserRefId.
     *
     * @param userRefId the Long.
     */
    public void setUserRefId(Long userRefId) {
        this.userRefId = userRefId;
    }
}
