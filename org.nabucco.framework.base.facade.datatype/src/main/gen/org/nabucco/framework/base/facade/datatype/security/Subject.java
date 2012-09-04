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
import org.nabucco.framework.base.facade.datatype.DatatypeSupport;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.Tenant;
import org.nabucco.framework.base.facade.datatype.date.Timestamp;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
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

    private static final String[] PROPERTY_CONSTRAINTS = { "l3,12;u0,n;m1,1;", "l3,12;u0,n;m1,1;", "l3,32;u0,n;m1,1;",
            "l0,n;u0,n;m1,1;", "l0,n;u0,n;m1,1;", "m0,1;" };

    public static final String TENANT = "tenant";

    public static final String OWNER = "owner";

    public static final String USERID = "userId";

    public static final String TOKEN = "token";

    public static final String LOGINTIME = "loginTime";

    public static final String USER = "user";

    /** Tenant of the subject */
    private Tenant tenant;

    /** Owner of the subject */
    private Owner owner;

    /** ID/Name of the user */
    private UserId userId;

    /** Authentication token, is generated during authentication */
    private Token token;

    /** Authentication time, is generated during authentication */
    private Timestamp loginTime;

    /** The actual user object */
    private User user;

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
        if ((this.getTenant() != null)) {
            clone.setTenant(this.getTenant().cloneObject());
        }
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getUserId() != null)) {
            clone.setUserId(this.getUserId().cloneObject());
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

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(TENANT,
                PropertyDescriptorSupport.createBasetype(TENANT, Tenant.class, 0, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(OWNER,
                PropertyDescriptorSupport.createBasetype(OWNER, Owner.class, 1, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(USERID,
                PropertyDescriptorSupport.createBasetype(USERID, UserId.class, 2, PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(TOKEN,
                PropertyDescriptorSupport.createBasetype(TOKEN, Token.class, 3, PROPERTY_CONSTRAINTS[3], false));
        propertyMap
                .put(LOGINTIME, PropertyDescriptorSupport.createBasetype(LOGINTIME, Timestamp.class, 4,
                        PROPERTY_CONSTRAINTS[4], false));
        propertyMap.put(USER, PropertyDescriptorSupport.createDatatype(USER, User.class, 5, PROPERTY_CONSTRAINTS[5],
                false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(Subject.getPropertyDescriptor(TENANT), this.tenant, null));
        properties.add(super.createProperty(Subject.getPropertyDescriptor(OWNER), this.owner, null));
        properties.add(super.createProperty(Subject.getPropertyDescriptor(USERID), this.userId, null));
        properties.add(super.createProperty(Subject.getPropertyDescriptor(TOKEN), this.token, null));
        properties.add(super.createProperty(Subject.getPropertyDescriptor(LOGINTIME), this.loginTime, null));
        properties.add(super.createProperty(Subject.getPropertyDescriptor(USER), this.getUser(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(TENANT) && (property.getType() == Tenant.class))) {
            this.setTenant(((Tenant) property.getInstance()));
            return true;
        } else if ((property.getName().equals(OWNER) && (property.getType() == Owner.class))) {
            this.setOwner(((Owner) property.getInstance()));
            return true;
        } else if ((property.getName().equals(USERID) && (property.getType() == UserId.class))) {
            this.setUserId(((UserId) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TOKEN) && (property.getType() == Token.class))) {
            this.setToken(((Token) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LOGINTIME) && (property.getType() == Timestamp.class))) {
            this.setLoginTime(((Timestamp) property.getInstance()));
            return true;
        } else if ((property.getName().equals(USER) && (property.getType() == User.class))) {
            this.setUser(((User) property.getInstance()));
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
        final Subject other = ((Subject) obj);
        if ((this.tenant == null)) {
            if ((other.tenant != null))
                return false;
        } else if ((!this.tenant.equals(other.tenant)))
            return false;
        if ((this.owner == null)) {
            if ((other.owner != null))
                return false;
        } else if ((!this.owner.equals(other.owner)))
            return false;
        if ((this.userId == null)) {
            if ((other.userId != null))
                return false;
        } else if ((!this.userId.equals(other.userId)))
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
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.tenant == null) ? 0 : this.tenant.hashCode()));
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.userId == null) ? 0 : this.userId.hashCode()));
        result = ((PRIME * result) + ((this.token == null) ? 0 : this.token.hashCode()));
        result = ((PRIME * result) + ((this.loginTime == null) ? 0 : this.loginTime.hashCode()));
        result = ((PRIME * result) + ((this.user == null) ? 0 : this.user.hashCode()));
        return result;
    }

    @Override
    public Subject cloneObject() {
        Subject clone = new Subject();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Tenant of the subject
     *
     * @return the Tenant.
     */
    public Tenant getTenant() {
        return this.tenant;
    }

    /**
     * Tenant of the subject
     *
     * @param tenant the Tenant.
     */
    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    /**
     * Tenant of the subject
     *
     * @param tenant the String.
     */
    public void setTenant(String tenant) {
        if ((this.tenant == null)) {
            if ((tenant == null)) {
                return;
            }
            this.tenant = new Tenant();
        }
        this.tenant.setValue(tenant);
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
            if ((owner == null)) {
                return;
            }
            this.owner = new Owner();
        }
        this.owner.setValue(owner);
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
            if ((userId == null)) {
                return;
            }
            this.userId = new UserId();
        }
        this.userId.setValue(userId);
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
            if ((token == null)) {
                return;
            }
            this.token = new Token();
        }
        this.token.setValue(token);
    }

    /**
     * Authentication time, is generated during authentication
     *
     * @return the Timestamp.
     */
    public Timestamp getLoginTime() {
        return this.loginTime;
    }

    /**
     * Authentication time, is generated during authentication
     *
     * @param loginTime the Timestamp.
     */
    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * Authentication time, is generated during authentication
     *
     * @param loginTime the Long.
     */
    public void setLoginTime(Long loginTime) {
        if ((this.loginTime == null)) {
            if ((loginTime == null)) {
                return;
            }
            this.loginTime = new Timestamp();
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
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(Subject.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(Subject.class).getAllProperties();
    }
}
