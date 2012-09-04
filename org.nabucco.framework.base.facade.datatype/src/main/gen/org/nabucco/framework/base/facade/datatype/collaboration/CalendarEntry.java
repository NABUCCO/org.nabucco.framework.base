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
import org.nabucco.framework.base.facade.datatype.Key;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.collaboration.Attachment;
import org.nabucco.framework.base.facade.datatype.collaboration.Attendee;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.date.Date;
import org.nabucco.framework.base.facade.datatype.mail.Body;
import org.nabucco.framework.base.facade.datatype.mail.Subject;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * CalendarEntry<p/>The calendar entry item<p/>
 *
 * @version 1.0
 * @author Leonid Agranosvkiy, PRODYNA AG, 2012-06-18
 */
public class CalendarEntry extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;u0,n;m0,1;", "l0,255;u0,n;m1,1;",
            "l0,4000;u0,n;m1,1;", "l0,255;u0,n;m1,1;", "l0,n;u0,n;m1,1;", "l0,n;u0,n;m1,1;", "m0,n;", "m0,n;", "m0,n;",
            "m0,n;" };

    public static final String UNIQUEID = "uniqueId";

    public static final String SUBJECT = "subject";

    public static final String MESSAGE = "message";

    public static final String LOCATION = "location";

    public static final String STARTDATE = "startDate";

    public static final String ENDDATE = "endDate";

    public static final String REQUIREDATTENDEESLIST = "requiredAttendeesList";

    public static final String OPTIONALATTENDEESLIST = "optionalAttendeesList";

    public static final String RESOURCESLIST = "resourcesList";

    public static final String ATTACHMENTLIST = "attachmentList";

    /** The unique id of the email */
    private Key uniqueId;

    /** The subject */
    private Subject subject;

    /** The message of the calendar entry */
    private Body message;

    /** The location of the meeting */
    private Name location;

    /** The start date of the entry */
    private Date startDate;

    /** The end date of the entry */
    private Date endDate;

    /** The list of required attendees */
    private NabuccoList<Attendee> requiredAttendeesList;

    /** The list of optional attendees */
    private NabuccoList<Attendee> optionalAttendeesList;

    /** The list of reserved resources */
    private NabuccoList<Attendee> resourcesList;

    /** The list of attachments */
    private NabuccoList<Attachment> attachmentList;

    /** Constructs a new CalendarEntry instance. */
    public CalendarEntry() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the CalendarEntry.
     */
    protected void cloneObject(CalendarEntry clone) {
        super.cloneObject(clone);
        if ((this.getUniqueId() != null)) {
            clone.setUniqueId(this.getUniqueId().cloneObject());
        }
        if ((this.getSubject() != null)) {
            clone.setSubject(this.getSubject().cloneObject());
        }
        if ((this.getMessage() != null)) {
            clone.setMessage(this.getMessage().cloneObject());
        }
        if ((this.getLocation() != null)) {
            clone.setLocation(this.getLocation().cloneObject());
        }
        if ((this.getStartDate() != null)) {
            clone.setStartDate(this.getStartDate().cloneObject());
        }
        if ((this.getEndDate() != null)) {
            clone.setEndDate(this.getEndDate().cloneObject());
        }
        if ((this.requiredAttendeesList != null)) {
            clone.requiredAttendeesList = this.requiredAttendeesList.cloneCollection();
        }
        if ((this.optionalAttendeesList != null)) {
            clone.optionalAttendeesList = this.optionalAttendeesList.cloneCollection();
        }
        if ((this.resourcesList != null)) {
            clone.resourcesList = this.resourcesList.cloneCollection();
        }
        if ((this.attachmentList != null)) {
            clone.attachmentList = this.attachmentList.cloneCollection();
        }
    }

    /**
     * Getter for the RequiredAttendeesListJPA.
     *
     * @return the List<Attendee>.
     */
    List<Attendee> getRequiredAttendeesListJPA() {
        if ((this.requiredAttendeesList == null)) {
            this.requiredAttendeesList = new NabuccoListImpl<Attendee>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<Attendee>) this.requiredAttendeesList).getDelegate();
    }

    /**
     * Setter for the RequiredAttendeesListJPA.
     *
     * @param requiredAttendeesList the List<Attendee>.
     */
    void setRequiredAttendeesListJPA(List<Attendee> requiredAttendeesList) {
        if ((this.requiredAttendeesList == null)) {
            this.requiredAttendeesList = new NabuccoListImpl<Attendee>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<Attendee>) this.requiredAttendeesList).setDelegate(requiredAttendeesList);
    }

    /**
     * Getter for the OptionalAttendeesListJPA.
     *
     * @return the List<Attendee>.
     */
    List<Attendee> getOptionalAttendeesListJPA() {
        if ((this.optionalAttendeesList == null)) {
            this.optionalAttendeesList = new NabuccoListImpl<Attendee>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<Attendee>) this.optionalAttendeesList).getDelegate();
    }

    /**
     * Setter for the OptionalAttendeesListJPA.
     *
     * @param optionalAttendeesList the List<Attendee>.
     */
    void setOptionalAttendeesListJPA(List<Attendee> optionalAttendeesList) {
        if ((this.optionalAttendeesList == null)) {
            this.optionalAttendeesList = new NabuccoListImpl<Attendee>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<Attendee>) this.optionalAttendeesList).setDelegate(optionalAttendeesList);
    }

    /**
     * Getter for the ResourcesListJPA.
     *
     * @return the List<Attendee>.
     */
    List<Attendee> getResourcesListJPA() {
        if ((this.resourcesList == null)) {
            this.resourcesList = new NabuccoListImpl<Attendee>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<Attendee>) this.resourcesList).getDelegate();
    }

    /**
     * Setter for the ResourcesListJPA.
     *
     * @param resourcesList the List<Attendee>.
     */
    void setResourcesListJPA(List<Attendee> resourcesList) {
        if ((this.resourcesList == null)) {
            this.resourcesList = new NabuccoListImpl<Attendee>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<Attendee>) this.resourcesList).setDelegate(resourcesList);
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
        propertyMap.put(SUBJECT,
                PropertyDescriptorSupport.createBasetype(SUBJECT, Subject.class, 4, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(MESSAGE,
                PropertyDescriptorSupport.createBasetype(MESSAGE, Body.class, 5, PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(LOCATION,
                PropertyDescriptorSupport.createBasetype(LOCATION, Name.class, 6, PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(STARTDATE,
                PropertyDescriptorSupport.createBasetype(STARTDATE, Date.class, 7, PROPERTY_CONSTRAINTS[4], false));
        propertyMap.put(ENDDATE,
                PropertyDescriptorSupport.createBasetype(ENDDATE, Date.class, 8, PROPERTY_CONSTRAINTS[5], false));
        propertyMap.put(REQUIREDATTENDEESLIST, PropertyDescriptorSupport.createCollection(REQUIREDATTENDEESLIST,
                Attendee.class, 9, PROPERTY_CONSTRAINTS[6], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(OPTIONALATTENDEESLIST, PropertyDescriptorSupport.createCollection(OPTIONALATTENDEESLIST,
                Attendee.class, 10, PROPERTY_CONSTRAINTS[7], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(RESOURCESLIST, PropertyDescriptorSupport.createCollection(RESOURCESLIST, Attendee.class, 11,
                PROPERTY_CONSTRAINTS[8], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ATTACHMENTLIST, PropertyDescriptorSupport.createCollection(ATTACHMENTLIST, Attachment.class,
                12, PROPERTY_CONSTRAINTS[9], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(CalendarEntry.getPropertyDescriptor(UNIQUEID), this.uniqueId, null));
        properties.add(super.createProperty(CalendarEntry.getPropertyDescriptor(SUBJECT), this.subject, null));
        properties.add(super.createProperty(CalendarEntry.getPropertyDescriptor(MESSAGE), this.message, null));
        properties.add(super.createProperty(CalendarEntry.getPropertyDescriptor(LOCATION), this.location, null));
        properties.add(super.createProperty(CalendarEntry.getPropertyDescriptor(STARTDATE), this.startDate, null));
        properties.add(super.createProperty(CalendarEntry.getPropertyDescriptor(ENDDATE), this.endDate, null));
        properties.add(super.createProperty(CalendarEntry.getPropertyDescriptor(REQUIREDATTENDEESLIST),
                this.requiredAttendeesList, null));
        properties.add(super.createProperty(CalendarEntry.getPropertyDescriptor(OPTIONALATTENDEESLIST),
                this.optionalAttendeesList, null));
        properties.add(super.createProperty(CalendarEntry.getPropertyDescriptor(RESOURCESLIST), this.resourcesList,
                null));
        properties.add(super.createProperty(CalendarEntry.getPropertyDescriptor(ATTACHMENTLIST), this.attachmentList,
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
        } else if ((property.getName().equals(SUBJECT) && (property.getType() == Subject.class))) {
            this.setSubject(((Subject) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MESSAGE) && (property.getType() == Body.class))) {
            this.setMessage(((Body) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LOCATION) && (property.getType() == Name.class))) {
            this.setLocation(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(STARTDATE) && (property.getType() == Date.class))) {
            this.setStartDate(((Date) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ENDDATE) && (property.getType() == Date.class))) {
            this.setEndDate(((Date) property.getInstance()));
            return true;
        } else if ((property.getName().equals(REQUIREDATTENDEESLIST) && (property.getType() == Attendee.class))) {
            this.requiredAttendeesList = ((NabuccoList<Attendee>) property.getInstance());
            return true;
        } else if ((property.getName().equals(OPTIONALATTENDEESLIST) && (property.getType() == Attendee.class))) {
            this.optionalAttendeesList = ((NabuccoList<Attendee>) property.getInstance());
            return true;
        } else if ((property.getName().equals(RESOURCESLIST) && (property.getType() == Attendee.class))) {
            this.resourcesList = ((NabuccoList<Attendee>) property.getInstance());
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
        final CalendarEntry other = ((CalendarEntry) obj);
        if ((this.uniqueId == null)) {
            if ((other.uniqueId != null))
                return false;
        } else if ((!this.uniqueId.equals(other.uniqueId)))
            return false;
        if ((this.subject == null)) {
            if ((other.subject != null))
                return false;
        } else if ((!this.subject.equals(other.subject)))
            return false;
        if ((this.message == null)) {
            if ((other.message != null))
                return false;
        } else if ((!this.message.equals(other.message)))
            return false;
        if ((this.location == null)) {
            if ((other.location != null))
                return false;
        } else if ((!this.location.equals(other.location)))
            return false;
        if ((this.startDate == null)) {
            if ((other.startDate != null))
                return false;
        } else if ((!this.startDate.equals(other.startDate)))
            return false;
        if ((this.endDate == null)) {
            if ((other.endDate != null))
                return false;
        } else if ((!this.endDate.equals(other.endDate)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.uniqueId == null) ? 0 : this.uniqueId.hashCode()));
        result = ((PRIME * result) + ((this.subject == null) ? 0 : this.subject.hashCode()));
        result = ((PRIME * result) + ((this.message == null) ? 0 : this.message.hashCode()));
        result = ((PRIME * result) + ((this.location == null) ? 0 : this.location.hashCode()));
        result = ((PRIME * result) + ((this.startDate == null) ? 0 : this.startDate.hashCode()));
        result = ((PRIME * result) + ((this.endDate == null) ? 0 : this.endDate.hashCode()));
        return result;
    }

    @Override
    public CalendarEntry cloneObject() {
        CalendarEntry clone = new CalendarEntry();
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
     * The subject
     *
     * @return the Subject.
     */
    public Subject getSubject() {
        return this.subject;
    }

    /**
     * The subject
     *
     * @param subject the Subject.
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    /**
     * The subject
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
     * The message of the calendar entry
     *
     * @return the Body.
     */
    public Body getMessage() {
        return this.message;
    }

    /**
     * The message of the calendar entry
     *
     * @param message the Body.
     */
    public void setMessage(Body message) {
        this.message = message;
    }

    /**
     * The message of the calendar entry
     *
     * @param message the String.
     */
    public void setMessage(String message) {
        if ((this.message == null)) {
            if ((message == null)) {
                return;
            }
            this.message = new Body();
        }
        this.message.setValue(message);
    }

    /**
     * The location of the meeting
     *
     * @return the Name.
     */
    public Name getLocation() {
        return this.location;
    }

    /**
     * The location of the meeting
     *
     * @param location the Name.
     */
    public void setLocation(Name location) {
        this.location = location;
    }

    /**
     * The location of the meeting
     *
     * @param location the String.
     */
    public void setLocation(String location) {
        if ((this.location == null)) {
            if ((location == null)) {
                return;
            }
            this.location = new Name();
        }
        this.location.setValue(location);
    }

    /**
     * The start date of the entry
     *
     * @return the Date.
     */
    public Date getStartDate() {
        return this.startDate;
    }

    /**
     * The start date of the entry
     *
     * @param startDate the Date.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * The start date of the entry
     *
     * @param startDate the java.util.Date.
     */
    public void setStartDate(java.util.Date startDate) {
        if ((this.startDate == null)) {
            if ((startDate == null)) {
                return;
            }
            this.startDate = new Date();
        }
        this.startDate.setValue(startDate);
    }

    /**
     * The end date of the entry
     *
     * @return the Date.
     */
    public Date getEndDate() {
        return this.endDate;
    }

    /**
     * The end date of the entry
     *
     * @param endDate the Date.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * The end date of the entry
     *
     * @param endDate the java.util.Date.
     */
    public void setEndDate(java.util.Date endDate) {
        if ((this.endDate == null)) {
            if ((endDate == null)) {
                return;
            }
            this.endDate = new Date();
        }
        this.endDate.setValue(endDate);
    }

    /**
     * The list of required attendees
     *
     * @return the NabuccoList<Attendee>.
     */
    public NabuccoList<Attendee> getRequiredAttendeesList() {
        if ((this.requiredAttendeesList == null)) {
            this.requiredAttendeesList = new NabuccoListImpl<Attendee>(NabuccoCollectionState.INITIALIZED);
        }
        return this.requiredAttendeesList;
    }

    /**
     * The list of optional attendees
     *
     * @return the NabuccoList<Attendee>.
     */
    public NabuccoList<Attendee> getOptionalAttendeesList() {
        if ((this.optionalAttendeesList == null)) {
            this.optionalAttendeesList = new NabuccoListImpl<Attendee>(NabuccoCollectionState.INITIALIZED);
        }
        return this.optionalAttendeesList;
    }

    /**
     * The list of reserved resources
     *
     * @return the NabuccoList<Attendee>.
     */
    public NabuccoList<Attendee> getResourcesList() {
        if ((this.resourcesList == null)) {
            this.resourcesList = new NabuccoListImpl<Attendee>(NabuccoCollectionState.INITIALIZED);
        }
        return this.resourcesList;
    }

    /**
     * The list of attachments
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
        return PropertyCache.getInstance().retrieve(CalendarEntry.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(CalendarEntry.class).getAllProperties();
    }
}
