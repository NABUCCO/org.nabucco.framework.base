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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.messageset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.messageset.MessageSetMessageExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * MessageSetExtension<p/>NABUCCO Message set Extension<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-01-30
 */
public class MessageSetExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m0,n;" };

    public static final String LANGUAGE = "language";

    public static final String WORKITEMID = "workItemId";

    public static final String MESSAGES = "messages";

    /** The language of the messageset */
    private StringProperty language;

    /** The workitem id of the messageset */
    private StringProperty workItemId;

    /** The list of the messages */
    private NabuccoList<MessageSetMessageExtension> messages;

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
        if ((this.getWorkItemId() != null)) {
            clone.setWorkItemId(this.getWorkItemId().cloneObject());
        }
        if ((this.messages != null)) {
            clone.messages = this.messages.cloneCollection();
        }
    }

    /**
     * Getter for the MessagesJPA.
     *
     * @return the List<MessageSetMessageExtension>.
     */
    List<MessageSetMessageExtension> getMessagesJPA() {
        if ((this.messages == null)) {
            this.messages = new NabuccoListImpl<MessageSetMessageExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<MessageSetMessageExtension>) this.messages).getDelegate();
    }

    /**
     * Setter for the MessagesJPA.
     *
     * @param messages the List<MessageSetMessageExtension>.
     */
    void setMessagesJPA(List<MessageSetMessageExtension> messages) {
        if ((this.messages == null)) {
            this.messages = new NabuccoListImpl<MessageSetMessageExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<MessageSetMessageExtension>) this.messages).setDelegate(messages);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(UiExtension.class).getPropertyMap());
        propertyMap.put(LANGUAGE, PropertyDescriptorSupport.createDatatype(LANGUAGE, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(WORKITEMID, PropertyDescriptorSupport.createDatatype(WORKITEMID, StringProperty.class, 5,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(MESSAGES, PropertyDescriptorSupport.createCollection(MESSAGES,
                MessageSetMessageExtension.class, 6, PROPERTY_CONSTRAINTS[2], false,
                PropertyAssociationType.COMPOSITION));
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
        properties.add(super.createProperty(MessageSetExtension.getPropertyDescriptor(WORKITEMID),
                this.getWorkItemId(), null));
        properties.add(super.createProperty(MessageSetExtension.getPropertyDescriptor(MESSAGES), this.messages, null));
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
        } else if ((property.getName().equals(WORKITEMID) && (property.getType() == StringProperty.class))) {
            this.setWorkItemId(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MESSAGES) && (property.getType() == MessageSetMessageExtension.class))) {
            this.messages = ((NabuccoList<MessageSetMessageExtension>) property.getInstance());
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
        if ((this.workItemId == null)) {
            if ((other.workItemId != null))
                return false;
        } else if ((!this.workItemId.equals(other.workItemId)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.language == null) ? 0 : this.language.hashCode()));
        result = ((PRIME * result) + ((this.workItemId == null) ? 0 : this.workItemId.hashCode()));
        return result;
    }

    @Override
    public MessageSetExtension cloneObject() {
        MessageSetExtension clone = new MessageSetExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The language of the messageset
     *
     * @param language the StringProperty.
     */
    public void setLanguage(StringProperty language) {
        this.language = language;
    }

    /**
     * The language of the messageset
     *
     * @return the StringProperty.
     */
    public StringProperty getLanguage() {
        return this.language;
    }

    /**
     * The workitem id of the messageset
     *
     * @param workItemId the StringProperty.
     */
    public void setWorkItemId(StringProperty workItemId) {
        this.workItemId = workItemId;
    }

    /**
     * The workitem id of the messageset
     *
     * @return the StringProperty.
     */
    public StringProperty getWorkItemId() {
        return this.workItemId;
    }

    /**
     * The list of the messages
     *
     * @return the NabuccoList<MessageSetMessageExtension>.
     */
    public NabuccoList<MessageSetMessageExtension> getMessages() {
        if ((this.messages == null)) {
            this.messages = new NabuccoListImpl<MessageSetMessageExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.messages;
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
