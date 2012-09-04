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
package org.nabucco.framework.base.facade.datatype.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.message.CodeNumber;
import org.nabucco.framework.base.facade.datatype.message.MessageMappingBase;
import org.nabucco.framework.base.facade.datatype.message.MessageProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * MessageMappingBase<p/>TODO<p/>
 *
 * @author Michael Krausse, PRODYNA AG, 2010-04-07
 */
public class MessageMappingBase extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l3,12;u0,n;m1,1;", "l1,500;u0,n;m1,1;", "m0,n;", "m0,n;" };

    public static final String OWNER = "owner";

    public static final String CODENUMBER = "codeNumber";

    public static final String MESSAGEPROPERTIYLIST = "messagePropertiyList";

    public static final String CHILDREN = "children";

    private Owner owner;

    private CodeNumber codeNumber;

    private NabuccoList<MessageProperty> messagePropertiyList;

    private NabuccoList<MessageMappingBase> children;

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
        if ((this.messagePropertiyList != null)) {
            clone.messagePropertiyList = this.messagePropertiyList.cloneCollection();
        }
        if ((this.children != null)) {
            clone.children = this.children.cloneCollection();
        }
    }

    /**
     * Getter for the MessagePropertiyListJPA.
     *
     * @return the List<MessageProperty>.
     */
    List<MessageProperty> getMessagePropertiyListJPA() {
        if ((this.messagePropertiyList == null)) {
            this.messagePropertiyList = new NabuccoListImpl<MessageProperty>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<MessageProperty>) this.messagePropertiyList).getDelegate();
    }

    /**
     * Setter for the MessagePropertiyListJPA.
     *
     * @param messagePropertiyList the List<MessageProperty>.
     */
    void setMessagePropertiyListJPA(List<MessageProperty> messagePropertiyList) {
        if ((this.messagePropertiyList == null)) {
            this.messagePropertiyList = new NabuccoListImpl<MessageProperty>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<MessageProperty>) this.messagePropertiyList).setDelegate(messagePropertiyList);
    }

    /**
     * Getter for the ChildrenJPA.
     *
     * @return the List<MessageMappingBase>.
     */
    List<MessageMappingBase> getChildrenJPA() {
        if ((this.children == null)) {
            this.children = new NabuccoListImpl<MessageMappingBase>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<MessageMappingBase>) this.children).getDelegate();
    }

    /**
     * Setter for the ChildrenJPA.
     *
     * @param children the List<MessageMappingBase>.
     */
    void setChildrenJPA(List<MessageMappingBase> children) {
        if ((this.children == null)) {
            this.children = new NabuccoListImpl<MessageMappingBase>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<MessageMappingBase>) this.children).setDelegate(children);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoDatatype.class).getPropertyMap());
        propertyMap.put(OWNER,
                PropertyDescriptorSupport.createBasetype(OWNER, Owner.class, 3, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(CODENUMBER, PropertyDescriptorSupport.createBasetype(CODENUMBER, CodeNumber.class, 4,
                PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(MESSAGEPROPERTIYLIST, PropertyDescriptorSupport.createCollection(MESSAGEPROPERTIYLIST,
                MessageProperty.class, 5, PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(CHILDREN, PropertyDescriptorSupport.createCollection(CHILDREN, MessageMappingBase.class, 6,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(MessageMappingBase.getPropertyDescriptor(OWNER), this.owner, null));
        properties
                .add(super.createProperty(MessageMappingBase.getPropertyDescriptor(CODENUMBER), this.codeNumber, null));
        properties.add(super.createProperty(MessageMappingBase.getPropertyDescriptor(MESSAGEPROPERTIYLIST),
                this.messagePropertiyList, null));
        properties.add(super.createProperty(MessageMappingBase.getPropertyDescriptor(CHILDREN), this.children, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(OWNER) && (property.getType() == Owner.class))) {
            this.setOwner(((Owner) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CODENUMBER) && (property.getType() == CodeNumber.class))) {
            this.setCodeNumber(((CodeNumber) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MESSAGEPROPERTIYLIST) && (property.getType() == MessageProperty.class))) {
            this.messagePropertiyList = ((NabuccoList<MessageProperty>) property.getInstance());
            return true;
        } else if ((property.getName().equals(CHILDREN) && (property.getType() == MessageMappingBase.class))) {
            this.children = ((NabuccoList<MessageMappingBase>) property.getInstance());
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
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.codeNumber == null) ? 0 : this.codeNumber.hashCode()));
        return result;
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
            if ((owner == null)) {
                return;
            }
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
            if ((codeNumber == null)) {
                return;
            }
            this.codeNumber = new CodeNumber();
        }
        this.codeNumber.setValue(codeNumber);
    }

    /**
     * Missing description at method getMessagePropertiyList.
     *
     * @return the NabuccoList<MessageProperty>.
     */
    public NabuccoList<MessageProperty> getMessagePropertiyList() {
        if ((this.messagePropertiyList == null)) {
            this.messagePropertiyList = new NabuccoListImpl<MessageProperty>(NabuccoCollectionState.INITIALIZED);
        }
        return this.messagePropertiyList;
    }

    /**
     * Missing description at method getChildren.
     *
     * @return the NabuccoList<MessageMappingBase>.
     */
    public NabuccoList<MessageMappingBase> getChildren() {
        if ((this.children == null)) {
            this.children = new NabuccoListImpl<MessageMappingBase>(NabuccoCollectionState.INITIALIZED);
        }
        return this.children;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(MessageMappingBase.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(MessageMappingBase.class).getAllProperties();
    }
}
