/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.message;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.message.CodeNumber;
import org.nabucco.framework.base.facade.datatype.message.MessageMappingBase;
import org.nabucco.framework.base.facade.datatype.message.MessageProperty;
import org.nabucco.framework.base.facade.datatype.property.BasetypeProperty;
import org.nabucco.framework.base.facade.datatype.property.ListProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;

/**
 * MessageMappingBase<p/>TODO<p/>
 *
 * @author Michael Krausse, PRODYNA AG, 2010-04-07
 */
public class MessageMappingBase extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "owner", "codeNumber", "messagePropertiyList",
            "children" };

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;m1,1;", "l0,n;m1,1;", "m0,n;",
            "m0,n;" };

    private Owner owner;

    private CodeNumber codeNumber;

    private List<MessageProperty> messagePropertiyList;

    private Long messagePropertiyListRefId;

    private List<MessageMappingBase> children;

    private Long childrenRefId;

    /** Constructs a new MessageMappingBase instance. */
    public MessageMappingBase() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the MessageMappingBase.
     */
    protected void cloneObject(MessageMappingBase clone) {
        super.cloneObject(clone);
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getCodeNumber() != null)) {
            clone.setCodeNumber(this.getCodeNumber().cloneObject());
        }
        if ((this.messagePropertiyList instanceof NabuccoList<?>)) {
            clone.messagePropertiyList = ((NabuccoList<MessageProperty>) this.messagePropertiyList)
                    .cloneCollection();
        }
        if ((this.children instanceof NabuccoList<?>)) {
            clone.children = ((NabuccoList<MessageMappingBase>) this.children).cloneCollection();
        }
    }

    /**
     * Getter for the MessagePropertiyListJPA.
     *
     * @return the List<MessageProperty>.
     */
    List<MessageProperty> getMessagePropertiyListJPA() {
        if ((this.messagePropertiyList == null)) {
            this.messagePropertiyList = new NabuccoList<MessageProperty>(
                    NabuccoCollectionState.LAZY);
        }
        return ((NabuccoList<MessageProperty>) this.messagePropertiyList).getDelegate();
    }

    /**
     * Setter for the MessagePropertiyListJPA.
     *
     * @param messagePropertiyList the List<MessageProperty>.
     */
    void setMessagePropertiyListJPA(List<MessageProperty> messagePropertiyList) {
        if ((this.messagePropertiyList == null)) {
            this.messagePropertiyList = new NabuccoList<MessageProperty>(
                    NabuccoCollectionState.LAZY);
        }
        ((NabuccoList<MessageProperty>) this.messagePropertiyList)
                .setDelegate(messagePropertiyList);
    }

    /**
     * Getter for the ChildrenJPA.
     *
     * @return the List<MessageMappingBase>.
     */
    List<MessageMappingBase> getChildrenJPA() {
        if ((this.children == null)) {
            this.children = new NabuccoList<MessageMappingBase>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoList<MessageMappingBase>) this.children).getDelegate();
    }

    /**
     * Setter for the ChildrenJPA.
     *
     * @param children the List<MessageMappingBase>.
     */
    void setChildrenJPA(List<MessageMappingBase> children) {
        if ((this.children == null)) {
            this.children = new NabuccoList<MessageMappingBase>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoList<MessageMappingBase>) this.children).setDelegate(children);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new BasetypeProperty<Owner>(PROPERTY_NAMES[0], Owner.class,
                PROPERTY_CONSTRAINTS[0], this.owner));
        properties.add(new BasetypeProperty<CodeNumber>(PROPERTY_NAMES[1], CodeNumber.class,
                PROPERTY_CONSTRAINTS[1], this.codeNumber));
        properties.add(new ListProperty<MessageProperty>(PROPERTY_NAMES[2], MessageProperty.class,
                PROPERTY_CONSTRAINTS[2], this.messagePropertiyList));
        properties.add(new ListProperty<MessageMappingBase>(PROPERTY_NAMES[3],
                MessageMappingBase.class, PROPERTY_CONSTRAINTS[3], this.children));
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
        if ((this.messagePropertiyListRefId == null)) {
            if ((other.messagePropertiyListRefId != null))
                return false;
        } else if ((!this.messagePropertiyListRefId.equals(other.messagePropertiyListRefId)))
            return false;
        if ((this.childrenRefId == null)) {
            if ((other.childrenRefId != null))
                return false;
        } else if ((!this.childrenRefId.equals(other.childrenRefId)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.codeNumber == null) ? 0 : this.codeNumber.hashCode()));
        result = ((PRIME * result) + ((this.messagePropertiyListRefId == null) ? 0
                : this.messagePropertiyListRefId.hashCode()));
        result = ((PRIME * result) + ((this.childrenRefId == null) ? 0 : this.childrenRefId
                .hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<MessageMappingBase>\n");
        appendable.append(super.toString());
        appendable.append((("<owner>" + this.owner) + "</owner>\n"));
        appendable.append((("<codeNumber>" + this.codeNumber) + "</codeNumber>\n"));
        appendable
                .append((("<messagePropertiyListRefId>" + this.messagePropertiyListRefId) + "</messagePropertiyListRefId>\n"));
        appendable.append((("<childrenRefId>" + this.childrenRefId) + "</childrenRefId>\n"));
        appendable.append("</MessageMappingBase>\n");
        return appendable.toString();
    }

    @Override
    public MessageMappingBase cloneObject() {
        MessageMappingBase clone = new MessageMappingBase();
        this.cloneObject(clone);
        return clone;
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
     * Getter for the MessagePropertiyListRefId.
     *
     * @return the Long.
     */
    public Long getMessagePropertiyListRefId() {
        return this.messagePropertiyListRefId;
    }

    /**
     * Setter for the MessagePropertiyListRefId.
     *
     * @param messagePropertiyListRefId the Long.
     */
    public void setMessagePropertiyListRefId(Long messagePropertiyListRefId) {
        this.messagePropertiyListRefId = messagePropertiyListRefId;
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

    /**
     * Getter for the ChildrenRefId.
     *
     * @return the Long.
     */
    public Long getChildrenRefId() {
        return this.childrenRefId;
    }

    /**
     * Setter for the ChildrenRefId.
     *
     * @param childrenRefId the Long.
     */
    public void setChildrenRefId(Long childrenRefId) {
        this.childrenRefId = childrenRefId;
    }
}
