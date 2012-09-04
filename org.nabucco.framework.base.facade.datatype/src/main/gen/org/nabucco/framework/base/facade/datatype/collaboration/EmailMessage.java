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
package org.nabucco.framework.base.facade.datatype.collaboration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Flag;
import org.nabucco.framework.base.facade.datatype.Key;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.collaboration.Attachment;
import org.nabucco.framework.base.facade.datatype.collaboration.Attendee;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.mail.Body;
import org.nabucco.framework.base.facade.datatype.mail.Subject;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * EmailMessage<p/>Email message<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-06-18
 */
public class EmailMessage extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;u0,n;m0,1;", "m0,n;", "m0,n;", "l0,n;u0,n;m1,1;",
            "l0,255;u0,n;m1,1;", "l0,4000;u0,n;m1,1;", "m0,n;" };

    public static final String UNIQUEID = "uniqueId";

    public static final String RECIPIENTLIST = "recipientList";

    public static final String COPYRECIPIENTLIST = "copyRecipientList";

    public static final String NEWEMAIL = "newEmail";

    public static final String SUBJECT = "subject";

    public static final String MESSAGEBODY = "messageBody";

    public static final String ATTACHMENTLIST = "attachmentList";

    /** The unique id of the email */
    private Key uniqueId;

    /** The list of the email recipients */
    private NabuccoList<Attendee> recipientList;

    /** The list of the email recipients */
    private NabuccoList<Attendee> copyRecipientList;

    /** The email is new */
    private Flag newEmail;

    /** The subject of the email */
    private Subject subject;

    /** The message body */
    private Body messageBody;

    /** The attachment list */
    private NabuccoList<Attachment> attachmentList;

    /** Constructs a new EmailMessage instance. */
    public EmailMessage() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the EmailMessage.
     */
    protected void cloneObject(EmailMessage clone) {
        super.cloneObject(clone);
        if ((this.getUniqueId() != null)) {
            clone.setUniqueId(this.getUniqueId().cloneObject());
        }
        if ((this.recipientList != null)) {
            clone.recipientList = this.recipientList.cloneCollection();
        }
        if ((this.copyRecipientList != null)) {
            clone.copyRecipientList = this.copyRecipientList.cloneCollection();
        }
        if ((this.getNewEmail() != null)) {
            clone.setNewEmail(this.getNewEmail().cloneObject());
        }
        if ((this.getSubject() != null)) {
            clone.setSubject(this.getSubject().cloneObject());
        }
        if ((this.getMessageBody() != null)) {
            clone.setMessageBody(this.getMessageBody().cloneObject());
        }
        if ((this.attachmentList != null)) {
            clone.attachmentList = this.attachmentList.cloneCollection();
        }
    }

    /**
     * Getter for the RecipientListJPA.
     *
     * @return the List<Attendee>.
     */
    List<Attendee> getRecipientListJPA() {
        if ((this.recipientList == null)) {
            this.recipientList = new NabuccoListImpl<Attendee>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<Attendee>) this.recipientList).getDelegate();
    }

    /**
     * Setter for the RecipientListJPA.
     *
     * @param recipientList the List<Attendee>.
     */
    void setRecipientListJPA(List<Attendee> recipientList) {
        if ((this.recipientList == null)) {
            this.recipientList = new NabuccoListImpl<Attendee>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<Attendee>) this.recipientList).setDelegate(recipientList);
    }

    /**
     * Getter for the CopyRecipientListJPA.
     *
     * @return the List<Attendee>.
     */
    List<Attendee> getCopyRecipientListJPA() {
        if ((this.copyRecipientList == null)) {
            this.copyRecipientList = new NabuccoListImpl<Attendee>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<Attendee>) this.copyRecipientList).getDelegate();
    }

    /**
     * Setter for the CopyRecipientListJPA.
     *
     * @param copyRecipientList the List<Attendee>.
     */
    void setCopyRecipientListJPA(List<Attendee> copyRecipientList) {
        if ((this.copyRecipientList == null)) {
            this.copyRecipientList = new NabuccoListImpl<Attendee>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<Attendee>) this.copyRecipientList).setDelegate(copyRecipientList);
    }

    /**
     * Getter for the AttachmentListJPA.
     *
     * @return the List<Attachment>.
     */
    List<Attachment> getAttachmentListJPA() {
        if ((this.attachmentList == null)) {
            this.attachmentList = new NabuccoListImpl<Attachment>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<Attachment>) this.attachmentList).getDelegate();
    }

    /**
     * Setter for the AttachmentListJPA.
     *
     * @param attachmentList the List<Attachment>.
     */
    void setAttachmentListJPA(List<Attachment> attachmentList) {
        if ((this.attachmentList == null)) {
            this.attachmentList = new NabuccoListImpl<Attachment>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<Attachment>) this.attachmentList).setDelegate(attachmentList);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoDatatype.class).getPropertyMap());
        propertyMap.put(UNIQUEID,
                PropertyDescriptorSupport.createBasetype(UNIQUEID, Key.class, 3, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(RECIPIENTLIST, PropertyDescriptorSupport.createCollection(RECIPIENTLIST, Attendee.class, 4,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(COPYRECIPIENTLIST, PropertyDescriptorSupport.createCollection(COPYRECIPIENTLIST,
                Attendee.class, 5, PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(NEWEMAIL,
                PropertyDescriptorSupport.createBasetype(NEWEMAIL, Flag.class, 6, PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(SUBJECT,
                PropertyDescriptorSupport.createBasetype(SUBJECT, Subject.class, 7, PROPERTY_CONSTRAINTS[4], false));
        propertyMap.put(MESSAGEBODY,
                PropertyDescriptorSupport.createBasetype(MESSAGEBODY, Body.class, 8, PROPERTY_CONSTRAINTS[5], false));
        propertyMap.put(ATTACHMENTLIST, PropertyDescriptorSupport.createCollection(ATTACHMENTLIST, Attachment.class, 9,
                PROPERTY_CONSTRAINTS[6], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(EmailMessage.getPropertyDescriptor(UNIQUEID), this.uniqueId, null));
        properties
                .add(super.createProperty(EmailMessage.getPropertyDescriptor(RECIPIENTLIST), this.recipientList, null));
        properties.add(super.createProperty(EmailMessage.getPropertyDescriptor(COPYRECIPIENTLIST),
                this.copyRecipientList, null));
        properties.add(super.createProperty(EmailMessage.getPropertyDescriptor(NEWEMAIL), this.newEmail, null));
        properties.add(super.createProperty(EmailMessage.getPropertyDescriptor(SUBJECT), this.subject, null));
        properties.add(super.createProperty(EmailMessage.getPropertyDescriptor(MESSAGEBODY), this.messageBody, null));
        properties.add(super.createProperty(EmailMessage.getPropertyDescriptor(ATTACHMENTLIST), this.attachmentList,
                null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(UNIQUEID) && (property.getType() == Key.class))) {
            this.setUniqueId(((Key) property.getInstance()));
            return true;
        } else if ((property.getName().equals(RECIPIENTLIST) && (property.getType() == Attendee.class))) {
            this.recipientList = ((NabuccoList<Attendee>) property.getInstance());
            return true;
        } else if ((property.getName().equals(COPYRECIPIENTLIST) && (property.getType() == Attendee.class))) {
            this.copyRecipientList = ((NabuccoList<Attendee>) property.getInstance());
            return true;
        } else if ((property.getName().equals(NEWEMAIL) && (property.getType() == Flag.class))) {
            this.setNewEmail(((Flag) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SUBJECT) && (property.getType() == Subject.class))) {
            this.setSubject(((Subject) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MESSAGEBODY) && (property.getType() == Body.class))) {
            this.setMessageBody(((Body) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ATTACHMENTLIST) && (property.getType() == Attachment.class))) {
            this.attachmentList = ((NabuccoList<Attachment>) property.getInstance());
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
        final EmailMessage other = ((EmailMessage) obj);
        if ((this.uniqueId == null)) {
            if ((other.uniqueId != null))
                return false;
        } else if ((!this.uniqueId.equals(other.uniqueId)))
            return false;
        if ((this.newEmail == null)) {
            if ((other.newEmail != null))
                return false;
        } else if ((!this.newEmail.equals(other.newEmail)))
            return false;
        if ((this.subject == null)) {
            if ((other.subject != null))
                return false;
        } else if ((!this.subject.equals(other.subject)))
            return false;
        if ((this.messageBody == null)) {
            if ((other.messageBody != null))
                return false;
        } else if ((!this.messageBody.equals(other.messageBody)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.uniqueId == null) ? 0 : this.uniqueId.hashCode()));
        result = ((PRIME * result) + ((this.newEmail == null) ? 0 : this.newEmail.hashCode()));
        result = ((PRIME * result) + ((this.subject == null) ? 0 : this.subject.hashCode()));
        result = ((PRIME * result) + ((this.messageBody == null) ? 0 : this.messageBody.hashCode()));
        return result;
    }

    @Override
    public EmailMessage cloneObject() {
        EmailMessage clone = new EmailMessage();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The unique id of the email
     *
     * @return the Key.
     */
    public Key getUniqueId() {
        return this.uniqueId;
    }

    /**
     * The unique id of the email
     *
     * @param uniqueId the Key.
     */
    public void setUniqueId(Key uniqueId) {
        this.uniqueId = uniqueId;
    }

    /**
     * The unique id of the email
     *
     * @param uniqueId the String.
     */
    public void setUniqueId(String uniqueId) {
        if ((this.uniqueId == null)) {
            if ((uniqueId == null)) {
                return;
            }
            this.uniqueId = new Key();
        }
        this.uniqueId.setValue(uniqueId);
    }

    /**
     * The list of the email recipients
     *
     * @return the NabuccoList<Attendee>.
     */
    public NabuccoList<Attendee> getRecipientList() {
        if ((this.recipientList == null)) {
            this.recipientList = new NabuccoListImpl<Attendee>(NabuccoCollectionState.INITIALIZED);
        }
        return this.recipientList;
    }

    /**
     * The list of the email recipients
     *
     * @return the NabuccoList<Attendee>.
     */
    public NabuccoList<Attendee> getCopyRecipientList() {
        if ((this.copyRecipientList == null)) {
            this.copyRecipientList = new NabuccoListImpl<Attendee>(NabuccoCollectionState.INITIALIZED);
        }
        return this.copyRecipientList;
    }

    /**
     * The email is new
     *
     * @return the Flag.
     */
    public Flag getNewEmail() {
        return this.newEmail;
    }

    /**
     * The email is new
     *
     * @param newEmail the Flag.
     */
    public void setNewEmail(Flag newEmail) {
        this.newEmail = newEmail;
    }

    /**
     * The email is new
     *
     * @param newEmail the Boolean.
     */
    public void setNewEmail(Boolean newEmail) {
        if ((this.newEmail == null)) {
            if ((newEmail == null)) {
                return;
            }
            this.newEmail = new Flag();
        }
        this.newEmail.setValue(newEmail);
    }

    /**
     * The subject of the email
     *
     * @return the Subject.
     */
    public Subject getSubject() {
        return this.subject;
    }

    /**
     * The subject of the email
     *
     * @param subject the Subject.
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    /**
     * The subject of the email
     *
     * @param subject the String.
     */
    public void setSubject(String subject) {
        if ((this.subject == null)) {
            if ((subject == null)) {
                return;
            }
            this.subject = new Subject();
        }
        this.subject.setValue(subject);
    }

    /**
     * The message body
     *
     * @return the Body.
     */
    public Body getMessageBody() {
        return this.messageBody;
    }

    /**
     * The message body
     *
     * @param messageBody the Body.
     */
    public void setMessageBody(Body messageBody) {
        this.messageBody = messageBody;
    }

    /**
     * The message body
     *
     * @param messageBody the String.
     */
    public void setMessageBody(String messageBody) {
        if ((this.messageBody == null)) {
            if ((messageBody == null)) {
                return;
            }
            this.messageBody = new Body();
        }
        this.messageBody.setValue(messageBody);
    }

    /**
     * The attachment list
     *
     * @return the NabuccoList<Attachment>.
     */
    public NabuccoList<Attachment> getAttachmentList() {
        if ((this.attachmentList == null)) {
            this.attachmentList = new NabuccoListImpl<Attachment>(NabuccoCollectionState.INITIALIZED);
        }
        return this.attachmentList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(EmailMessage.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(EmailMessage.class).getAllProperties();
    }
}
