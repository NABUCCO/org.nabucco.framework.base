/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.attachment;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.date.Date;
import org.nabucco.framework.base.facade.datatype.file.File;
import org.nabucco.framework.base.facade.datatype.property.BasetypeProperty;
import org.nabucco.framework.base.facade.datatype.property.DatatypeProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.security.User;

/**
 * Attachment
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-12-06
 */
public class Attachment extends File implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "creator", "creationDate" };

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "l0,n;m1,1;" };

    private User creator;

    private Long creatorRefId;

    private Date creationDate;

    /** Constructs a new Attachment instance. */
    public Attachment() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the Attachment.
     */
    protected void cloneObject(Attachment clone) {
        super.cloneObject(clone);
        if ((this.getCreator() != null)) {
            clone.setCreator(this.getCreator().cloneObject());
        }
        if ((this.getCreationDate() != null)) {
            clone.setCreationDate(this.getCreationDate().cloneObject());
        }
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new DatatypeProperty<User>(PROPERTY_NAMES[0], User.class,
                PROPERTY_CONSTRAINTS[0], this.creator));
        properties.add(new BasetypeProperty<Date>(PROPERTY_NAMES[1], Date.class,
                PROPERTY_CONSTRAINTS[1], this.creationDate));
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
        final Attachment other = ((Attachment) obj);
        if ((this.creator == null)) {
            if ((other.creator != null))
                return false;
        } else if ((!this.creator.equals(other.creator)))
            return false;
        if ((this.creatorRefId == null)) {
            if ((other.creatorRefId != null))
                return false;
        } else if ((!this.creatorRefId.equals(other.creatorRefId)))
            return false;
        if ((this.creationDate == null)) {
            if ((other.creationDate != null))
                return false;
        } else if ((!this.creationDate.equals(other.creationDate)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.creator == null) ? 0 : this.creator.hashCode()));
        result = ((PRIME * result) + ((this.creatorRefId == null) ? 0 : this.creatorRefId
                .hashCode()));
        result = ((PRIME * result) + ((this.creationDate == null) ? 0 : this.creationDate
                .hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<Attachment>\n");
        appendable.append(super.toString());
        appendable.append((("<creator>" + this.creator) + "</creator>\n"));
        appendable.append((("<creatorRefId>" + this.creatorRefId) + "</creatorRefId>\n"));
        appendable.append((("<creationDate>" + this.creationDate) + "</creationDate>\n"));
        appendable.append("</Attachment>\n");
        return appendable.toString();
    }

    @Override
    public Attachment cloneObject() {
        Attachment clone = new Attachment();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Missing description at method setCreator.
     *
     * @param creator the User.
     */
    public void setCreator(User creator) {
        this.creator = creator;
        if ((creator != null)) {
            this.setCreatorRefId(creator.getId());
        } else {
            this.setCreatorRefId(null);
        }
    }

    /**
     * Missing description at method getCreator.
     *
     * @return the User.
     */
    public User getCreator() {
        return this.creator;
    }

    /**
     * Getter for the CreatorRefId.
     *
     * @return the Long.
     */
    public Long getCreatorRefId() {
        return this.creatorRefId;
    }

    /**
     * Setter for the CreatorRefId.
     *
     * @param creatorRefId the Long.
     */
    public void setCreatorRefId(Long creatorRefId) {
        this.creatorRefId = creatorRefId;
    }

    /**
     * Missing description at method getCreationDate.
     *
     * @return the Date.
     */
    public Date getCreationDate() {
        return this.creationDate;
    }

    /**
     * Missing description at method setCreationDate.
     *
     * @param creationDate the Date.
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Missing description at method setCreationDate.
     *
     * @param creationDate the java.util.Date.
     */
    public void setCreationDate(java.util.Date creationDate) {
        if ((this.creationDate == null)) {
            this.creationDate = new Date();
        }
        this.creationDate.setValue(creationDate);
    }
}
