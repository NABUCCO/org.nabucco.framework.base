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
 * Group<p/>Represents a group within the authorization component<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2010-01-18
 */
public class Group extends NabuccoDatatype implements Datatype {

    private static final String[] CONSTRAINTS = { "l0,n;m1,1;", "l0,n;m1,1;", "l0,n;m1,1;",
            "l0,n;m1,1;" };

    private static final long serialVersionUID = 1L;

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

    /**
     * CloneObject.
     *
     * @param clone the Group.
     */
    protected void cloneObject(Group clone) {
        super.cloneObject(clone);
        if ((this.groupname != null)) {
            clone.groupname = this.groupname.cloneObject();
        }
        if ((this.owner != null)) {
            clone.owner = this.owner.cloneObject();
        }
        if ((this.description != null)) {
            clone.description = this.description.cloneObject();
        }
        if ((this.groupType != null)) {
            clone.groupType = this.groupType.cloneObject();
        }
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    @Override
    public Group cloneObject() {
        Group clone = new Group();
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
    public String[] getPropertyNames() {
        return VisitorUtility.merge(super.getPropertyNames(), new String[] { "groupname", "owner",
                "description", "groupType" });
    }

    @Override
    public Object[] getProperties() {
        return VisitorUtility.merge(
                super.getProperties(),
                new Object[] { this.getGroupname(), this.getOwner(), this.getDescription(),
                        this.getGroupType() });
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
