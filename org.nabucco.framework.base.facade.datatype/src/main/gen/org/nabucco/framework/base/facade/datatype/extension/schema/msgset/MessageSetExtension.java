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
package org.nabucco.framework.base.facade.datatype.extension.schema.msgset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.msgset.MessageCodeExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.msgset.MessageExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * MessageSetExtension<p/>Extension for providing error messages for exceptions.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-04-05
 */
public class MessageSetExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;", "m1,n;", "m0,n;" };

    public static final String LANGUAGE = "language";

    public static final String PKG = "pkg";

    public static final String SERVICE = "service";

    public static final String MESSAGELIST = "messageList";

    public static final String CODELIST = "codeList";

    /** The language of the message set. */
    private StringProperty language;

    /** The package of the service. */
    private StringProperty pkg;

    /** The name of the service or '*' for matching all services */
    private StringProperty service;

    /** List of messages in a message set. */
    private NabuccoList<MessageExtension> messageList;

    /** The list of error codes for each message in the list. */
    private NabuccoList<MessageCodeExtension> codeList;

    /** Constructs a new MessageSetExtension instance. */
    public MessageSetExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the MessageSetExtension.
     */
    protected void cloneObject(MessageSetExtension clone) {
        super.cloneObject(clone);
        if ((this.getLanguage() != null)) {
            clone.setLanguage(this.getLanguage().cloneObject());
        }
        if ((this.getPkg() != null)) {
            clone.setPkg(this.getPkg().cloneObject());
        }
        if ((this.getService() != null)) {
            clone.setService(this.getService().cloneObject());
        }
        if ((this.messageList != null)) {
            clone.messageList = this.messageList.cloneCollection();
        }
        if ((this.codeList != null)) {
            clone.codeList = this.codeList.cloneCollection();
        }
    }

    /**
     * Getter for the MessageListJPA.
     *
     * @return the List<MessageExtension>.
     */
    List<MessageExtension> getMessageListJPA() {
        if ((this.messageList == null)) {
            this.messageList = new NabuccoListImpl<MessageExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<MessageExtension>) this.messageList).getDelegate();
    }

    /**
     * Setter for the MessageListJPA.
     *
     * @param messageList the List<MessageExtension>.
     */
    void setMessageListJPA(List<MessageExtension> messageList) {
        if ((this.messageList == null)) {
            this.messageList = new NabuccoListImpl<MessageExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<MessageExtension>) this.messageList).setDelegate(messageList);
    }

    /**
     * Getter for the CodeListJPA.
     *
     * @return the List<MessageCodeExtension>.
     */
    List<MessageCodeExtension> getCodeListJPA() {
        if ((this.codeList == null)) {
            this.codeList = new NabuccoListImpl<MessageCodeExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<MessageCodeExtension>) this.codeList).getDelegate();
    }

    /**
     * Setter for the CodeListJPA.
     *
     * @param codeList the List<MessageCodeExtension>.
     */
    void setCodeListJPA(List<MessageCodeExtension> codeList) {
        if ((this.codeList == null)) {
            this.codeList = new NabuccoListImpl<MessageCodeExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<MessageCodeExtension>) this.codeList).setDelegate(codeList);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoExtensionComposite.class).getPropertyMap());
        propertyMap.put(LANGUAGE, PropertyDescriptorSupport.createDatatype(LANGUAGE, StringProperty.class, 2,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(PKG, PropertyDescriptorSupport.createDatatype(PKG, StringProperty.class, 3,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(SERVICE, PropertyDescriptorSupport.createDatatype(SERVICE, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(MESSAGELIST, PropertyDescriptorSupport.createCollection(MESSAGELIST, MessageExtension.class, 5,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(CODELIST, PropertyDescriptorSupport.createCollection(CODELIST, MessageCodeExtension.class, 6,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(MessageSetExtension.getPropertyDescriptor(LANGUAGE), this.getLanguage(),
                null));
        properties.add(super.createProperty(MessageSetExtension.getPropertyDescriptor(PKG), this.getPkg(), null));
        properties
                .add(super.createProperty(MessageSetExtension.getPropertyDescriptor(SERVICE), this.getService(), null));
        properties.add(super.createProperty(MessageSetExtension.getPropertyDescriptor(MESSAGELIST), this.messageList,
                null));
        properties.add(super.createProperty(MessageSetExtension.getPropertyDescriptor(CODELIST), this.codeList, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(LANGUAGE) && (property.getType() == StringProperty.class))) {
            this.setLanguage(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PKG) && (property.getType() == StringProperty.class))) {
            this.setPkg(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SERVICE) && (property.getType() == StringProperty.class))) {
            this.setService(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MESSAGELIST) && (property.getType() == MessageExtension.class))) {
            this.messageList = ((NabuccoList<MessageExtension>) property.getInstance());
            return true;
        } else if ((property.getName().equals(CODELIST) && (property.getType() == MessageCodeExtension.class))) {
            this.codeList = ((NabuccoList<MessageCodeExtension>) property.getInstance());
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
        final MessageSetExtension other = ((MessageSetExtension) obj);
        if ((this.language == null)) {
            if ((other.language != null))
                return false;
        } else if ((!this.language.equals(other.language)))
            return false;
        if ((this.pkg == null)) {
            if ((other.pkg != null))
                return false;
        } else if ((!this.pkg.equals(other.pkg)))
            return false;
        if ((this.service == null)) {
            if ((other.service != null))
                return false;
        } else if ((!this.service.equals(other.service)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.language == null) ? 0 : this.language.hashCode()));
        result = ((PRIME * result) + ((this.pkg == null) ? 0 : this.pkg.hashCode()));
        result = ((PRIME * result) + ((this.service == null) ? 0 : this.service.hashCode()));
        return result;
    }

    @Override
    public MessageSetExtension cloneObject() {
        MessageSetExtension clone = new MessageSetExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The language of the message set.
     *
     * @param language the StringProperty.
     */
    public void setLanguage(StringProperty language) {
        this.language = language;
    }

    /**
     * The language of the message set.
     *
     * @return the StringProperty.
     */
    public StringProperty getLanguage() {
        return this.language;
    }

    /**
     * The package of the service.
     *
     * @param pkg the StringProperty.
     */
    public void setPkg(StringProperty pkg) {
        this.pkg = pkg;
    }

    /**
     * The package of the service.
     *
     * @return the StringProperty.
     */
    public StringProperty getPkg() {
        return this.pkg;
    }

    /**
     * The name of the service or '*' for matching all services
     *
     * @param service the StringProperty.
     */
    public void setService(StringProperty service) {
        this.service = service;
    }

    /**
     * The name of the service or '*' for matching all services
     *
     * @return the StringProperty.
     */
    public StringProperty getService() {
        return this.service;
    }

    /**
     * List of messages in a message set.
     *
     * @return the NabuccoList<MessageExtension>.
     */
    public NabuccoList<MessageExtension> getMessageList() {
        if ((this.messageList == null)) {
            this.messageList = new NabuccoListImpl<MessageExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.messageList;
    }

    /**
     * The list of error codes for each message in the list.
     *
     * @return the NabuccoList<MessageCodeExtension>.
     */
    public NabuccoList<MessageCodeExtension> getCodeList() {
        if ((this.codeList == null)) {
            this.codeList = new NabuccoListImpl<MessageCodeExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.codeList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(MessageSetExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(MessageSetExtension.class).getAllProperties();
    }
}
