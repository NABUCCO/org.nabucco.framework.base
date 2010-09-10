/*
* Copyright 2010 PRODYNA AG
*
* Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.opensource.org/licenses/eclipse-1.0.php or
* http://www.nabucco-source.org/nabucco-license.html
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
* Generated with NABUCCO Generator 
*/
package org.nabucco.framework.base.facade.datatype.security;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeSupport;
import org.nabucco.framework.base.facade.datatype.DateTime;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.security.User;
import org.nabucco.framework.base.facade.datatype.security.UserId;
import org.nabucco.framework.base.facade.datatype.security.credential.Token;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorUtility;


/**
 * Subject<p/>A Subject represents an authenticated user.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-06-01
 */
public class Subject extends DatatypeSupport implements Datatype {

    private static final String[] CONSTRAINTS = { "l0,n;m1,1;", "l0,n;m1,1;", "l0,n;m1,1;",
            "l0,n;m1,1;", "m0,1;" };

    private static final long serialVersionUID = 1L;

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

    /** Constructs a new Subject instance. */
    public Subject() {
        super();
        this.initDefaults();
    }

    /**
     * CloneObject.
     *
     * @param clone the Subject.
     */
    protected void cloneObject(Subject clone) {
        super.cloneObject(clone);
        if ((this.userId != null)) {
            clone.userId = this.userId.cloneObject();
        }
        if ((this.owner != null)) {
            clone.owner = this.owner.cloneObject();
        }
        if ((this.token != null)) {
            clone.token = this.token.cloneObject();
        }
        if ((this.loginTime != null)) {
            clone.loginTime = this.loginTime.cloneObject();
        }
        if ((this.user != null)) {
            clone.user = this.user.cloneObject();
        }
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    @Override
    public Subject cloneObject() {
        Subject clone = new Subject();
        this.cloneObject(clone);
        return clone;
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public String[] getConstraints() {
        return CONSTRAINTS.clone();
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
        return true;
    }

    @Override
    public String[] getPropertyNames() {
        return VisitorUtility.merge(super.getPropertyNames(), new String[] { "userId", "owner",
                "token", "loginTime", "user" });
    }

    @Override
    public Object[] getProperties() {
        return VisitorUtility.merge(
                super.getProperties(),
                new Object[] { this.getUserId(), this.getOwner(), this.getToken(),
                        this.getLoginTime(), this.getUser() });
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
        appendable.append("</Subject>\n");
        return appendable.toString();
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
     * @return the User.
     */
    public User getUser() {
        return this.user;
    }

    /**
     * The actual user object
     *
     * @param user the User.
     */
    public void setUser(User user) {
        this.user = user;
    }
}
