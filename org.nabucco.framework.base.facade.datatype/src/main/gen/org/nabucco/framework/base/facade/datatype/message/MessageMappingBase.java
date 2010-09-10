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
package org.nabucco.framework.base.facade.datatype.message;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorUtility;


/**
 * MessageMappingBase<p/>TODO<p/>
 *
 * @author Michael Krausse, PRODYNA AG, 2010-04-07
 */
public class MessageMappingBase extends NabuccoDatatype implements Datatype {

    private static final String[] CONSTRAINTS = { "l0,n;m1,1;", "l0,n;m1,1;", "m0,n;", "m0,n;" };

    private static final long serialVersionUID = 1L;

    private Owner owner;

    private CodeNumber codeNumber;

    private List<MessageProperty> messagePropertiyList;

    private List<MessageMappingBase> children;

    /** Constructs a new MessageMappingBase instance. */
    public MessageMappingBase() {
        super();
        this.initDefaults();
    }

    /**
     * CloneObject.
     *
     * @param clone the MessageMappingBase.
     */
    protected void cloneObject(MessageMappingBase clone) {
        super.cloneObject(clone);
        if ((this.owner != null)) {
            clone.owner = this.owner.cloneObject();
        }
        if ((this.codeNumber != null)) {
            clone.codeNumber = this.codeNumber.cloneObject();
        }
        if ((this.messagePropertiyList instanceof NabuccoList<?>)) {
            clone.messagePropertiyList = ((NabuccoList<MessageProperty>) this.messagePropertiyList)
                    .cloneCollection();
        }
        if ((this.children instanceof NabuccoList<?>)) {
            clone.children = ((NabuccoList<MessageMappingBase>) this.children).cloneCollection();
        }
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * Missing description at method setMessagePropertiyList.
     *
     * @param messagePropertiyList the List<MessageProperty>.
     */
    void setMessagePropertiyList(List<MessageProperty> messagePropertiyList) {
        this.messagePropertiyList = new NabuccoList<MessageProperty>(messagePropertiyList,
                NabuccoCollectionState.LAZY);
    }

    /**
     * Missing description at method setChildren.
     *
     * @param children the List<MessageMappingBase>.
     */
    void setChildren(List<MessageMappingBase> children) {
        this.children = new NabuccoList<MessageMappingBase>(children, NabuccoCollectionState.LAZY);
    }

    @Override
    public MessageMappingBase cloneObject() {
        MessageMappingBase clone = new MessageMappingBase();
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
        final MessageMappingBase other = ((MessageMappingBase) obj);
        if ((this.owner == null)) {
            if ((other.owner != null))
                return false;
        } else if ((!this.owner.equals(other.owner)))
            return false;
        if ((this.codeNumber == null)) {
            if ((other.codeNumber != null))
                return false;
        } else if ((!this.codeNumber.equals(other.codeNumber)))
            return false;
        return true;
    }

    @Override
    public String[] getPropertyNames() {
        return VisitorUtility.merge(super.getPropertyNames(), new String[] { "owner", "codeNumber",
                "messagePropertiyList", "children" });
    }

    @Override
    public Object[] getProperties() {
        return VisitorUtility.merge(
                super.getProperties(),
                new Object[] { this.getOwner(), this.getCodeNumber(),
                        this.getMessagePropertiyList(), this.getChildren() });
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.codeNumber == null) ? 0 : this.codeNumber.hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<MessageMappingBase>\n");
        appendable.append(super.toString());
        appendable.append((("<owner>" + this.owner) + "</owner>\n"));
        appendable.append((("<codeNumber>" + this.codeNumber) + "</codeNumber>\n"));
        appendable.append("</MessageMappingBase>\n");
        return appendable.toString();
    }

    /**
     * Missing description at method getOwner.
     *
     * @return the Owner.
     */
    public Owner getOwner() {
        return this.owner;
    }

    /**
     * Missing description at method setOwner.
     *
     * @param owner the Owner.
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * Missing description at method setOwner.
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
     * Missing description at method getCodeNumber.
     *
     * @return the CodeNumber.
     */
    public CodeNumber getCodeNumber() {
        return this.codeNumber;
    }

    /**
     * Missing description at method setCodeNumber.
     *
     * @param codeNumber the CodeNumber.
     */
    public void setCodeNumber(CodeNumber codeNumber) {
        this.codeNumber = codeNumber;
    }

    /**
     * Missing description at method setCodeNumber.
     *
     * @param codeNumber the String.
     */
    public void setCodeNumber(String codeNumber) {
        if ((this.codeNumber == null)) {
            this.codeNumber = new CodeNumber();
        }
        this.codeNumber.setValue(codeNumber);
    }

    /**
     * Missing description at method getMessagePropertiyList.
     *
     * @return the List<MessageProperty>.
     */
    public List<MessageProperty> getMessagePropertiyList() {
        if ((this.messagePropertiyList == null)) {
            this.messagePropertiyList = new NabuccoList<MessageProperty>(
                    NabuccoCollectionState.INITIALIZED);
        }
        return this.messagePropertiyList;
    }

    /**
     * Missing description at method getChildren.
     *
     * @return the List<MessageMappingBase>.
     */
    public List<MessageMappingBase> getChildren() {
        if ((this.children == null)) {
            this.children = new NabuccoList<MessageMappingBase>(NabuccoCollectionState.INITIALIZED);
        }
        return this.children;
    }
}
