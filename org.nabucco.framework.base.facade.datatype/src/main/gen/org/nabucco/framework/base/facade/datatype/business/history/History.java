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
package org.nabucco.framework.base.facade.datatype.business.history;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.FunctionalIdentifier;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.code.CodePath;
import org.nabucco.framework.base.facade.datatype.date.Timestamp;
import org.nabucco.framework.base.facade.datatype.mail.Body;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.security.UserId;
import org.nabucco.framework.base.facade.datatype.xml.XmlDocument;

/**
 * History<p/>A history entry that is created for business historization.<p/>
 *
 * @version 1.0
 * @author Raffael Bieniek, PRODYNA AG, 2011-04-07
 */
public class History extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l3,12;u0,n;m1,1;", "l0,255;u0,n;m1,1;",
            "l0,4000;u0,n;m0,1;", "l3,32;u0,n;m1,1;", "l0,n;u0,n;m1,1;", "l0,255;u0,n;m0,1;", "l0,n;u0,n;m0,1;",
            "l0,255;u0,n;m0,1;", "l0,2147483647;u0,n;m0,1;", "l0,n;u0,n;m0,1;", "m0,1;" };

    public static final String OWNER = "owner";

    public static final String TITLE = "title";

    public static final String BODY = "body";

    public static final String CREATEDUSERID = "createdUserId";

    public static final String CREATEDTIMESTAMP = "createdTimestamp";

    public static final String TARGETTYPE = "targetType";

    public static final String TARGETID = "targetId";

    public static final String TARGETMETA = "targetMeta";

    public static final String PAYLOAD = "payload";

    public static final String FUNCTIONALID = "functionalId";

    public static final String FUNCTIONALTYPE = "functionalType";

    /** Owner of the History. */
    private Owner owner;

    /** Title of the History. */
    private Name title;

    /** Body of the History. */
    private Body body;

    /** ID of the user that created the history. */
    private UserId createdUserId;

    /** Timestamp of the history creation. */
    private Timestamp createdTimestamp;

    /** Type of the Target Datatype. */
    private Name targetType;

    /** ID of the Target Datatype. */
    private Identifier targetId;

    /** Metadata of the Target Datatype. */
    private Name targetMeta;

    /** The XML Payload. */
    private XmlDocument payload;

    /** The functional History ID. */
    private FunctionalIdentifier functionalId;

    /** The functional History type. */
    private Code functionalType;

    protected static final String FUNCTIONALTYPE_CODEPATH = "nabucco.business.history.functionaltype";

    /** Constructs a new History instance. */
    public History() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the History.
     */
    protected void cloneObject(History clone) {
        super.cloneObject(clone);
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getTitle() != null)) {
            clone.setTitle(this.getTitle().cloneObject());
        }
        if ((this.getBody() != null)) {
            clone.setBody(this.getBody().cloneObject());
        }
        if ((this.getCreatedUserId() != null)) {
            clone.setCreatedUserId(this.getCreatedUserId().cloneObject());
        }
        if ((this.getCreatedTimestamp() != null)) {
            clone.setCreatedTimestamp(this.getCreatedTimestamp().cloneObject());
        }
        if ((this.getTargetType() != null)) {
            clone.setTargetType(this.getTargetType().cloneObject());
        }
        if ((this.getTargetId() != null)) {
            clone.setTargetId(this.getTargetId().cloneObject());
        }
        if ((this.getTargetMeta() != null)) {
            clone.setTargetMeta(this.getTargetMeta().cloneObject());
        }
        if ((this.getPayload() != null)) {
            clone.setPayload(this.getPayload().cloneObject());
        }
        if ((this.getFunctionalId() != null)) {
            clone.setFunctionalId(this.getFunctionalId().cloneObject());
        }
        if ((this.getFunctionalType() != null)) {
            clone.setFunctionalType(this.getFunctionalType().cloneObject());
        }
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
        propertyMap.put(TITLE,
                PropertyDescriptorSupport.createBasetype(TITLE, Name.class, 4, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(BODY,
                PropertyDescriptorSupport.createBasetype(BODY, Body.class, 5, PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(CREATEDUSERID, PropertyDescriptorSupport.createBasetype(CREATEDUSERID, UserId.class, 6,
                PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(CREATEDTIMESTAMP, PropertyDescriptorSupport.createBasetype(CREATEDTIMESTAMP, Timestamp.class,
                7, PROPERTY_CONSTRAINTS[4], false));
        propertyMap.put(TARGETTYPE,
                PropertyDescriptorSupport.createBasetype(TARGETTYPE, Name.class, 8, PROPERTY_CONSTRAINTS[5], false));
        propertyMap
                .put(TARGETID, PropertyDescriptorSupport.createBasetype(TARGETID, Identifier.class, 9,
                        PROPERTY_CONSTRAINTS[6], false));
        propertyMap.put(TARGETMETA,
                PropertyDescriptorSupport.createBasetype(TARGETMETA, Name.class, 10, PROPERTY_CONSTRAINTS[7], false));
        propertyMap.put(PAYLOAD, PropertyDescriptorSupport.createBasetype(PAYLOAD, XmlDocument.class, 11,
                PROPERTY_CONSTRAINTS[8], false));
        propertyMap.put(FUNCTIONALID, PropertyDescriptorSupport.createBasetype(FUNCTIONALID,
                FunctionalIdentifier.class, 12, PROPERTY_CONSTRAINTS[9], false));
        propertyMap.put(FUNCTIONALTYPE, PropertyDescriptorSupport.createDatatype(FUNCTIONALTYPE, Code.class, 13,
                PROPERTY_CONSTRAINTS[10], false, PropertyAssociationType.COMPOSITION, FUNCTIONALTYPE_CODEPATH));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(History.getPropertyDescriptor(OWNER), this.owner, null));
        properties.add(super.createProperty(History.getPropertyDescriptor(TITLE), this.title, null));
        properties.add(super.createProperty(History.getPropertyDescriptor(BODY), this.body, null));
        properties.add(super.createProperty(History.getPropertyDescriptor(CREATEDUSERID), this.createdUserId, null));
        properties.add(super.createProperty(History.getPropertyDescriptor(CREATEDTIMESTAMP), this.createdTimestamp,
                null));
        properties.add(super.createProperty(History.getPropertyDescriptor(TARGETTYPE), this.targetType, null));
        properties.add(super.createProperty(History.getPropertyDescriptor(TARGETID), this.targetId, null));
        properties.add(super.createProperty(History.getPropertyDescriptor(TARGETMETA), this.targetMeta, null));
        properties.add(super.createProperty(History.getPropertyDescriptor(PAYLOAD), this.payload, null));
        properties.add(super.createProperty(History.getPropertyDescriptor(FUNCTIONALID), this.functionalId, null));
        properties.add(super.createProperty(History.getPropertyDescriptor(FUNCTIONALTYPE), this.getFunctionalType(),
                null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(OWNER) && (property.getType() == Owner.class))) {
            this.setOwner(((Owner) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TITLE) && (property.getType() == Name.class))) {
            this.setTitle(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(BODY) && (property.getType() == Body.class))) {
            this.setBody(((Body) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CREATEDUSERID) && (property.getType() == UserId.class))) {
            this.setCreatedUserId(((UserId) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CREATEDTIMESTAMP) && (property.getType() == Timestamp.class))) {
            this.setCreatedTimestamp(((Timestamp) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TARGETTYPE) && (property.getType() == Name.class))) {
            this.setTargetType(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TARGETID) && (property.getType() == Identifier.class))) {
            this.setTargetId(((Identifier) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TARGETMETA) && (property.getType() == Name.class))) {
            this.setTargetMeta(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PAYLOAD) && (property.getType() == XmlDocument.class))) {
            this.setPayload(((XmlDocument) property.getInstance()));
            return true;
        } else if ((property.getName().equals(FUNCTIONALID) && (property.getType() == FunctionalIdentifier.class))) {
            this.setFunctionalId(((FunctionalIdentifier) property.getInstance()));
            return true;
        } else if ((property.getName().equals(FUNCTIONALTYPE) && (property.getType() == Code.class))) {
            this.setFunctionalType(((Code) property.getInstance()));
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
        final History other = ((History) obj);
        if ((this.owner == null)) {
            if ((other.owner != null))
                return false;
        } else if ((!this.owner.equals(other.owner)))
            return false;
        if ((this.title == null)) {
            if ((other.title != null))
                return false;
        } else if ((!this.title.equals(other.title)))
            return false;
        if ((this.body == null)) {
            if ((other.body != null))
                return false;
        } else if ((!this.body.equals(other.body)))
            return false;
        if ((this.createdUserId == null)) {
            if ((other.createdUserId != null))
                return false;
        } else if ((!this.createdUserId.equals(other.createdUserId)))
            return false;
        if ((this.createdTimestamp == null)) {
            if ((other.createdTimestamp != null))
                return false;
        } else if ((!this.createdTimestamp.equals(other.createdTimestamp)))
            return false;
        if ((this.targetType == null)) {
            if ((other.targetType != null))
                return false;
        } else if ((!this.targetType.equals(other.targetType)))
            return false;
        if ((this.targetId == null)) {
            if ((other.targetId != null))
                return false;
        } else if ((!this.targetId.equals(other.targetId)))
            return false;
        if ((this.targetMeta == null)) {
            if ((other.targetMeta != null))
                return false;
        } else if ((!this.targetMeta.equals(other.targetMeta)))
            return false;
        if ((this.payload == null)) {
            if ((other.payload != null))
                return false;
        } else if ((!this.payload.equals(other.payload)))
            return false;
        if ((this.functionalId == null)) {
            if ((other.functionalId != null))
                return false;
        } else if ((!this.functionalId.equals(other.functionalId)))
            return false;
        if ((this.functionalType == null)) {
            if ((other.functionalType != null))
                return false;
        } else if ((!this.functionalType.equals(other.functionalType)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.title == null) ? 0 : this.title.hashCode()));
        result = ((PRIME * result) + ((this.body == null) ? 0 : this.body.hashCode()));
        result = ((PRIME * result) + ((this.createdUserId == null) ? 0 : this.createdUserId.hashCode()));
        result = ((PRIME * result) + ((this.createdTimestamp == null) ? 0 : this.createdTimestamp.hashCode()));
        result = ((PRIME * result) + ((this.targetType == null) ? 0 : this.targetType.hashCode()));
        result = ((PRIME * result) + ((this.targetId == null) ? 0 : this.targetId.hashCode()));
        result = ((PRIME * result) + ((this.targetMeta == null) ? 0 : this.targetMeta.hashCode()));
        result = ((PRIME * result) + ((this.payload == null) ? 0 : this.payload.hashCode()));
        result = ((PRIME * result) + ((this.functionalId == null) ? 0 : this.functionalId.hashCode()));
        result = ((PRIME * result) + ((this.functionalType == null) ? 0 : this.functionalType.hashCode()));
        return result;
    }

    @Override
    public History cloneObject() {
        History clone = new History();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Owner of the History.
     *
     * @return the Owner.
     */
    public Owner getOwner() {
        return this.owner;
    }

    /**
     * Owner of the History.
     *
     * @param owner the Owner.
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * Owner of the History.
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
     * Title of the History.
     *
     * @return the Name.
     */
    public Name getTitle() {
        return this.title;
    }

    /**
     * Title of the History.
     *
     * @param title the Name.
     */
    public void setTitle(Name title) {
        this.title = title;
    }

    /**
     * Title of the History.
     *
     * @param title the String.
     */
    public void setTitle(String title) {
        if ((this.title == null)) {
            if ((title == null)) {
                return;
            }
            this.title = new Name();
        }
        this.title.setValue(title);
    }

    /**
     * Body of the History.
     *
     * @return the Body.
     */
    public Body getBody() {
        return this.body;
    }

    /**
     * Body of the History.
     *
     * @param body the Body.
     */
    public void setBody(Body body) {
        this.body = body;
    }

    /**
     * Body of the History.
     *
     * @param body the String.
     */
    public void setBody(String body) {
        if ((this.body == null)) {
            if ((body == null)) {
                return;
            }
            this.body = new Body();
        }
        this.body.setValue(body);
    }

    /**
     * ID of the user that created the history.
     *
     * @return the UserId.
     */
    public UserId getCreatedUserId() {
        return this.createdUserId;
    }

    /**
     * ID of the user that created the history.
     *
     * @param createdUserId the UserId.
     */
    public void setCreatedUserId(UserId createdUserId) {
        this.createdUserId = createdUserId;
    }

    /**
     * ID of the user that created the history.
     *
     * @param createdUserId the String.
     */
    public void setCreatedUserId(String createdUserId) {
        if ((this.createdUserId == null)) {
            if ((createdUserId == null)) {
                return;
            }
            this.createdUserId = new UserId();
        }
        this.createdUserId.setValue(createdUserId);
    }

    /**
     * Timestamp of the history creation.
     *
     * @return the Timestamp.
     */
    public Timestamp getCreatedTimestamp() {
        return this.createdTimestamp;
    }

    /**
     * Timestamp of the history creation.
     *
     * @param createdTimestamp the Timestamp.
     */
    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    /**
     * Timestamp of the history creation.
     *
     * @param createdTimestamp the Long.
     */
    public void setCreatedTimestamp(Long createdTimestamp) {
        if ((this.createdTimestamp == null)) {
            if ((createdTimestamp == null)) {
                return;
            }
            this.createdTimestamp = new Timestamp();
        }
        this.createdTimestamp.setValue(createdTimestamp);
    }

    /**
     * Type of the Target Datatype.
     *
     * @return the Name.
     */
    public Name getTargetType() {
        return this.targetType;
    }

    /**
     * Type of the Target Datatype.
     *
     * @param targetType the Name.
     */
    public void setTargetType(Name targetType) {
        this.targetType = targetType;
    }

    /**
     * Type of the Target Datatype.
     *
     * @param targetType the String.
     */
    public void setTargetType(String targetType) {
        if ((this.targetType == null)) {
            if ((targetType == null)) {
                return;
            }
            this.targetType = new Name();
        }
        this.targetType.setValue(targetType);
    }

    /**
     * ID of the Target Datatype.
     *
     * @return the Identifier.
     */
    public Identifier getTargetId() {
        return this.targetId;
    }

    /**
     * ID of the Target Datatype.
     *
     * @param targetId the Identifier.
     */
    public void setTargetId(Identifier targetId) {
        this.targetId = targetId;
    }

    /**
     * ID of the Target Datatype.
     *
     * @param targetId the Long.
     */
    public void setTargetId(Long targetId) {
        if ((this.targetId == null)) {
            if ((targetId == null)) {
                return;
            }
            this.targetId = new Identifier();
        }
        this.targetId.setValue(targetId);
    }

    /**
     * Metadata of the Target Datatype.
     *
     * @return the Name.
     */
    public Name getTargetMeta() {
        return this.targetMeta;
    }

    /**
     * Metadata of the Target Datatype.
     *
     * @param targetMeta the Name.
     */
    public void setTargetMeta(Name targetMeta) {
        this.targetMeta = targetMeta;
    }

    /**
     * Metadata of the Target Datatype.
     *
     * @param targetMeta the String.
     */
    public void setTargetMeta(String targetMeta) {
        if ((this.targetMeta == null)) {
            if ((targetMeta == null)) {
                return;
            }
            this.targetMeta = new Name();
        }
        this.targetMeta.setValue(targetMeta);
    }

    /**
     * The XML Payload.
     *
     * @return the XmlDocument.
     */
    public XmlDocument getPayload() {
        return this.payload;
    }

    /**
     * The XML Payload.
     *
     * @param payload the XmlDocument.
     */
    public void setPayload(XmlDocument payload) {
        this.payload = payload;
    }

    /**
     * The XML Payload.
     *
     * @param payload the String.
     */
    public void setPayload(String payload) {
        if ((this.payload == null)) {
            if ((payload == null)) {
                return;
            }
            this.payload = new XmlDocument();
        }
        this.payload.setValue(payload);
    }

    /**
     * The functional History ID.
     *
     * @return the FunctionalIdentifier.
     */
    public FunctionalIdentifier getFunctionalId() {
        return this.functionalId;
    }

    /**
     * The functional History ID.
     *
     * @param functionalId the FunctionalIdentifier.
     */
    public void setFunctionalId(FunctionalIdentifier functionalId) {
        this.functionalId = functionalId;
    }

    /**
     * The functional History ID.
     *
     * @param functionalId the String.
     */
    public void setFunctionalId(String functionalId) {
        if ((this.functionalId == null)) {
            if ((functionalId == null)) {
                return;
            }
            this.functionalId = new FunctionalIdentifier();
        }
        this.functionalId.setValue(functionalId);
    }

    /**
     * The functional History type.
     *
     * @param functionalType the Code.
     */
    public void setFunctionalType(Code functionalType) {
        this.functionalType = functionalType;
    }

    /**
     * The functional History type.
     *
     * @return the Code.
     */
    public Code getFunctionalType() {
        return this.functionalType;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(History.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(History.class).getAllProperties();
    }

    /**
     * Getter for the FunctionalTypeCodePath.
     *
     * @return the CodePath.
     */
    public static CodePath getFunctionalTypeCodePath() {
        return new CodePath(FUNCTIONALTYPE_CODEPATH);
    }
}
