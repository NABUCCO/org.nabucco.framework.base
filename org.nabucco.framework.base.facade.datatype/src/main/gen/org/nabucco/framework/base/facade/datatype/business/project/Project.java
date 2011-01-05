/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.business.project;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.StatusType;
import org.nabucco.framework.base.facade.datatype.date.Date;
import org.nabucco.framework.base.facade.datatype.property.BasetypeProperty;
import org.nabucco.framework.base.facade.datatype.property.EnumProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;

/**
 * Project
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-30
 */
public class Project extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "name", "description", "owner", "startDate",
            "endDate", "statusType" };

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;m1,1;", "l0,n;m1,1;",
            "l0,n;m1,1;", "l0,n;m1,1;", "l0,n;m0,1;", "m1,1;" };

    private Name name;

    private Description description;

    private Owner owner;

    private Date startDate;

    private Date endDate;

    private StatusType statusType;

    /** Constructs a new Project instance. */
    public Project() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the Project.
     */
    protected void cloneObject(Project clone) {
        super.cloneObject(clone);
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getStartDate() != null)) {
            clone.setStartDate(this.getStartDate().cloneObject());
        }
        if ((this.getEndDate() != null)) {
            clone.setEndDate(this.getEndDate().cloneObject());
        }
        clone.setStatusType(this.getStatusType());
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new BasetypeProperty<Name>(PROPERTY_NAMES[0], Name.class,
                PROPERTY_CONSTRAINTS[0], this.name));
        properties.add(new BasetypeProperty<Description>(PROPERTY_NAMES[1], Description.class,
                PROPERTY_CONSTRAINTS[1], this.description));
        properties.add(new BasetypeProperty<Owner>(PROPERTY_NAMES[2], Owner.class,
                PROPERTY_CONSTRAINTS[2], this.owner));
        properties.add(new BasetypeProperty<Date>(PROPERTY_NAMES[3], Date.class,
                PROPERTY_CONSTRAINTS[3], this.startDate));
        properties.add(new BasetypeProperty<Date>(PROPERTY_NAMES[4], Date.class,
                PROPERTY_CONSTRAINTS[4], this.endDate));
        properties.add(new EnumProperty<StatusType>(PROPERTY_NAMES[5], StatusType.class,
                PROPERTY_CONSTRAINTS[5], this.statusType));
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
        final Project other = ((Project) obj);
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        if ((this.description == null)) {
            if ((other.description != null))
                return false;
        } else if ((!this.description.equals(other.description)))
            return false;
        if ((this.owner == null)) {
            if ((other.owner != null))
                return false;
        } else if ((!this.owner.equals(other.owner)))
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
        if ((this.statusType == null)) {
            if ((other.statusType != null))
                return false;
        } else if ((!this.statusType.equals(other.statusType)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.startDate == null) ? 0 : this.startDate.hashCode()));
        result = ((PRIME * result) + ((this.endDate == null) ? 0 : this.endDate.hashCode()));
        result = ((PRIME * result) + ((this.statusType == null) ? 0 : this.statusType.hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<Project>\n");
        appendable.append(super.toString());
        appendable.append((("<name>" + this.name) + "</name>\n"));
        appendable.append((("<description>" + this.description) + "</description>\n"));
        appendable.append((("<owner>" + this.owner) + "</owner>\n"));
        appendable.append((("<startDate>" + this.startDate) + "</startDate>\n"));
        appendable.append((("<endDate>" + this.endDate) + "</endDate>\n"));
        appendable.append((("<statusType>" + this.statusType) + "</statusType>\n"));
        appendable.append("</Project>\n");
        return appendable.toString();
    }

    @Override
    public Project cloneObject() {
        Project clone = new Project();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Missing description at method getName.
     *
     * @return the Name.
     */
    public Name getName() {
        return this.name;
    }

    /**
     * Missing description at method setName.
     *
     * @param name the Name.
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * Missing description at method setName.
     *
     * @param name the String.
     */
    public void setName(String name) {
        if ((this.name == null)) {
            this.name = new Name();
        }
        this.name.setValue(name);
    }

    /**
     * Missing description at method getDescription.
     *
     * @return the Description.
     */
    public Description getDescription() {
        return this.description;
    }

    /**
     * Missing description at method setDescription.
     *
     * @param description the Description.
     */
    public void setDescription(Description description) {
        this.description = description;
    }

    /**
     * Missing description at method setDescription.
     *
     * @param description the String.
     */
    public void setDescription(String description) {
        if ((this.description == null)) {
            this.description = new Description();
        }
        this.description.setValue(description);
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
     * Missing description at method getStartDate.
     *
     * @return the Date.
     */
    public Date getStartDate() {
        return this.startDate;
    }

    /**
     * Missing description at method setStartDate.
     *
     * @param startDate the Date.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Missing description at method setStartDate.
     *
     * @param startDate the java.util.Date.
     */
    public void setStartDate(java.util.Date startDate) {
        if ((this.startDate == null)) {
            this.startDate = new Date();
        }
        this.startDate.setValue(startDate);
    }

    /**
     * Missing description at method getEndDate.
     *
     * @return the Date.
     */
    public Date getEndDate() {
        return this.endDate;
    }

    /**
     * Missing description at method setEndDate.
     *
     * @param endDate the Date.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Missing description at method setEndDate.
     *
     * @param endDate the java.util.Date.
     */
    public void setEndDate(java.util.Date endDate) {
        if ((this.endDate == null)) {
            this.endDate = new Date();
        }
        this.endDate.setValue(endDate);
    }

    /**
     * Missing description at method getStatusType.
     *
     * @return the StatusType.
     */
    public StatusType getStatusType() {
        return this.statusType;
    }

    /**
     * Missing description at method setStatusType.
     *
     * @param statusType the StatusType.
     */
    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    /**
     * Missing description at method setStatusType.
     *
     * @param statusType the String.
     */
    public void setStatusType(String statusType) {
        if ((statusType == null)) {
            this.statusType = null;
        } else {
            this.statusType = StatusType.valueOf(statusType);
        }
    }
}
