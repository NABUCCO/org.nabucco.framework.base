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
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.code.CodeType;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorUtility;


/**
 * User<p/>A Role is a collection of permissions<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2010-01-18
 */
public class User extends NabuccoDatatype implements Datatype {

    private static final String[] CONSTRAINTS = { "l0,n;m1,1;", "l0,n;m1,1;", "l0,n;m1,1;",
            "l0,n;m1,1;" };

    private static final long serialVersionUID = 1L;

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

    /**
     * CloneObject.
     *
     * @param clone the User.
     */
    protected void cloneObject(User clone) {
        super.cloneObject(clone);
        if ((this.username != null)) {
            clone.username = this.username.cloneObject();
        }
        if ((this.owner != null)) {
            clone.owner = this.owner.cloneObject();
        }
        if ((this.description != null)) {
            clone.description = this.description.cloneObject();
        }
        if ((this.userType != null)) {
            clone.userType = this.userType.cloneObject();
        }
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    @Override
    public User cloneObject() {
        User clone = new User();
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
    public String[] getPropertyNames() {
        return VisitorUtility.merge(super.getPropertyNames(), new String[] { "username", "owner",
                "description", "userType" });
    }

    @Override
    public Object[] getProperties() {
        return VisitorUtility.merge(
                super.getProperties(),
                new Object[] { this.getUsername(), this.getOwner(), this.getDescription(),
                        this.getUserType() });
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
