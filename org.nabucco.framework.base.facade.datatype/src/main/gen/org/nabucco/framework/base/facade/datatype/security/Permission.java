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
 * Permission<p/>A Permission, assigned to a Role, User or Group<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2010-01-18
 */
public class Permission extends NabuccoDatatype implements Datatype {

    private static final String[] CONSTRAINTS = { "l0,n;m1,1;", "l0,n;m1,1;", "l0,n;m1,1;",
            "l0,n;m1,1;" };

    private static final long serialVersionUID = 1L;

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

    /**
     * CloneObject.
     *
     * @param clone the Permission.
     */
    protected void cloneObject(Permission clone) {
        super.cloneObject(clone);
        if ((this.permissionname != null)) {
            clone.permissionname = this.permissionname.cloneObject();
        }
        if ((this.owner != null)) {
            clone.owner = this.owner.cloneObject();
        }
        if ((this.description != null)) {
            clone.description = this.description.cloneObject();
        }
        if ((this.permissionType != null)) {
            clone.permissionType = this.permissionType.cloneObject();
        }
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    @Override
    public Permission cloneObject() {
        Permission clone = new Permission();
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
    public String[] getPropertyNames() {
        return VisitorUtility.merge(super.getPropertyNames(), new String[] { "permissionname",
                "owner", "description", "permissionType" });
    }

    @Override
    public Object[] getProperties() {
        return VisitorUtility.merge(super.getProperties(), new Object[] { this.getPermissionname(),
                this.getOwner(), this.getDescription(), this.getPermissionType() });
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
