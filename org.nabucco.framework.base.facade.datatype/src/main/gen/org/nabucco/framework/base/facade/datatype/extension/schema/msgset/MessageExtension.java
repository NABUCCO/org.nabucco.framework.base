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
import org.nabucco.framework.base.facade.datatype.extension.property.BooleanProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.msgset.MessageCodeExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * MessageExtension<p/>Extension for providing one error message for exceptions.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-04-05
 */
public class MessageExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m0,n;", "m1,1;" };

    public static final String METHOD = "method";

    public static final String EXCEPTIONPATH = "exceptionPath";

    public static final String MESSAGETEXT = "messageText";

    public static final String SEVERITY = "severity";

    public static final String CODELIST = "codeList";

    public static final String FORCEORIGINALMESSAGE = "forceOriginalMessage";

    /** The name of the method. */
    private StringProperty method;

    /** The exception path. */
    private StringProperty exceptionPath;

    /** The message text. */
    private StringProperty messageText;

    /** The severity of the message. */
    private StringProperty severity;

    /** The list of error codes for the given message. */
    private NabuccoList<MessageCodeExtension> codeList;

    /** Forces the message resolver for the original message. */
    private BooleanProperty forceOriginalMessage;

    /** Constructs a new MessageExtension instance. */
    public MessageExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the MessageExtension.
     */
    protected void cloneObject(MessageExtension clone) {
        super.cloneObject(clone);
        if ((this.getMethod() != null)) {
            clone.setMethod(this.getMethod().cloneObject());
        }
        if ((this.getExceptionPath() != null)) {
            clone.setExceptionPath(this.getExceptionPath().cloneObject());
        }
        if ((this.getMessageText() != null)) {
            clone.setMessageText(this.getMessageText().cloneObject());
        }
        if ((this.getSeverity() != null)) {
            clone.setSeverity(this.getSeverity().cloneObject());
        }
        if ((this.codeList != null)) {
            clone.codeList = this.codeList.cloneCollection();
        }
        if ((this.getForceOriginalMessage() != null)) {
            clone.setForceOriginalMessage(this.getForceOriginalMessage().cloneObject());
        }
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
        propertyMap.put(METHOD, PropertyDescriptorSupport.createDatatype(METHOD, StringProperty.class, 2,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(EXCEPTIONPATH, PropertyDescriptorSupport.createDatatype(EXCEPTIONPATH, StringProperty.class, 3,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(MESSAGETEXT, PropertyDescriptorSupport.createDatatype(MESSAGETEXT, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(SEVERITY, PropertyDescriptorSupport.createDatatype(SEVERITY, StringProperty.class, 5,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(CODELIST, PropertyDescriptorSupport.createCollection(CODELIST, MessageCodeExtension.class, 6,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(FORCEORIGINALMESSAGE, PropertyDescriptorSupport.createDatatype(FORCEORIGINALMESSAGE,
                BooleanProperty.class, 7, PROPERTY_CONSTRAINTS[5], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(MessageExtension.getPropertyDescriptor(METHOD), this.getMethod(), null));
        properties.add(super.createProperty(MessageExtension.getPropertyDescriptor(EXCEPTIONPATH),
                this.getExceptionPath(), null));
        properties.add(super.createProperty(MessageExtension.getPropertyDescriptor(MESSAGETEXT), this.getMessageText(),
                null));
        properties
                .add(super.createProperty(MessageExtension.getPropertyDescriptor(SEVERITY), this.getSeverity(), null));
        properties.add(super.createProperty(MessageExtension.getPropertyDescriptor(CODELIST), this.codeList, null));
        properties.add(super.createProperty(MessageExtension.getPropertyDescriptor(FORCEORIGINALMESSAGE),
                this.getForceOriginalMessage(), null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(METHOD) && (property.getType() == StringProperty.class))) {
            this.setMethod(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(EXCEPTIONPATH) && (property.getType() == StringProperty.class))) {
            this.setExceptionPath(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MESSAGETEXT) && (property.getType() == StringProperty.class))) {
            this.setMessageText(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SEVERITY) && (property.getType() == StringProperty.class))) {
            this.setSeverity(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CODELIST) && (property.getType() == MessageCodeExtension.class))) {
            this.codeList = ((NabuccoList<MessageCodeExtension>) property.getInstance());
            return true;
        } else if ((property.getName().equals(FORCEORIGINALMESSAGE) && (property.getType() == BooleanProperty.class))) {
            this.setForceOriginalMessage(((BooleanProperty) property.getInstance()));
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
        final MessageExtension other = ((MessageExtension) obj);
        if ((this.method == null)) {
            if ((other.method != null))
                return false;
        } else if ((!this.method.equals(other.method)))
            return false;
        if ((this.exceptionPath == null)) {
            if ((other.exceptionPath != null))
                return false;
        } else if ((!this.exceptionPath.equals(other.exceptionPath)))
            return false;
        if ((this.messageText == null)) {
            if ((other.messageText != null))
                return false;
        } else if ((!this.messageText.equals(other.messageText)))
            return false;
        if ((this.severity == null)) {
            if ((other.severity != null))
                return false;
        } else if ((!this.severity.equals(other.severity)))
            return false;
        if ((this.forceOriginalMessage == null)) {
            if ((other.forceOriginalMessage != null))
                return false;
        } else if ((!this.forceOriginalMessage.equals(other.forceOriginalMessage)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.method == null) ? 0 : this.method.hashCode()));
        result = ((PRIME * result) + ((this.exceptionPath == null) ? 0 : this.exceptionPath.hashCode()));
        result = ((PRIME * result) + ((this.messageText == null) ? 0 : this.messageText.hashCode()));
        result = ((PRIME * result) + ((this.severity == null) ? 0 : this.severity.hashCode()));
        result = ((PRIME * result) + ((this.forceOriginalMessage == null) ? 0 : this.forceOriginalMessage.hashCode()));
        return result;
    }

    @Override
    public MessageExtension cloneObject() {
        MessageExtension clone = new MessageExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The name of the method.
     *
     * @param method the StringProperty.
     */
    public void setMethod(StringProperty method) {
        this.method = method;
    }

    /**
     * The name of the method.
     *
     * @return the StringProperty.
     */
    public StringProperty getMethod() {
        return this.method;
    }

    /**
     * The exception path.
     *
     * @param exceptionPath the StringProperty.
     */
    public void setExceptionPath(StringProperty exceptionPath) {
        this.exceptionPath = exceptionPath;
    }

    /**
     * The exception path.
     *
     * @return the StringProperty.
     */
    public StringProperty getExceptionPath() {
        return this.exceptionPath;
    }

    /**
     * The message text.
     *
     * @param messageText the StringProperty.
     */
    public void setMessageText(StringProperty messageText) {
        this.messageText = messageText;
    }

    /**
     * The message text.
     *
     * @return the StringProperty.
     */
    public StringProperty getMessageText() {
        return this.messageText;
    }

    /**
     * The severity of the message.
     *
     * @param severity the StringProperty.
     */
    public void setSeverity(StringProperty severity) {
        this.severity = severity;
    }

    /**
     * The severity of the message.
     *
     * @return the StringProperty.
     */
    public StringProperty getSeverity() {
        return this.severity;
    }

    /**
     * The list of error codes for the given message.
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
     * Forces the message resolver for the original message.
     *
     * @param forceOriginalMessage the BooleanProperty.
     */
    public void setForceOriginalMessage(BooleanProperty forceOriginalMessage) {
        this.forceOriginalMessage = forceOriginalMessage;
    }

    /**
     * Forces the message resolver for the original message.
     *
     * @return the BooleanProperty.
     */
    public BooleanProperty getForceOriginalMessage() {
        return this.forceOriginalMessage;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(MessageExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(MessageExtension.class).getAllProperties();
    }
}
