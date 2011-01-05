/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.business.person;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.StatusType;
import org.nabucco.framework.base.facade.datatype.business.person.FamilyState;
import org.nabucco.framework.base.facade.datatype.business.person.Gender;
import org.nabucco.framework.base.facade.datatype.business.person.Nationality;
import org.nabucco.framework.base.facade.datatype.business.person.PersonTitle;
import org.nabucco.framework.base.facade.datatype.date.Date;
import org.nabucco.framework.base.facade.datatype.image.ImageFile;
import org.nabucco.framework.base.facade.datatype.property.BasetypeProperty;
import org.nabucco.framework.base.facade.datatype.property.DatatypeProperty;
import org.nabucco.framework.base.facade.datatype.property.EnumProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;

/**
 * Person
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-30
 */
public class Person extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "forename", "middlename", "surname",
            "birthday", "owner", "image", "statusType", "nationality", "gender", "familyState",
            "personTitle" };

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;m1,1;", "l0,n;m1,1;",
            "l0,n;m1,1;", "l0,n;m1,1;", "l0,n;m1,1;", "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m1,1;",
            "m1,1;" };

    private Name forename;

    private Name middlename;

    private Name surname;

    private Date birthday;

    private Owner owner;

    private ImageFile image;

    private Long imageRefId;

    private StatusType statusType;

    private Nationality nationality;

    private Gender gender;

    private FamilyState familyState;

    private PersonTitle personTitle;

    /** Constructs a new Person instance. */
    public Person() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the Person.
     */
    protected void cloneObject(Person clone) {
        super.cloneObject(clone);
        if ((this.getForename() != null)) {
            clone.setForename(this.getForename().cloneObject());
        }
        if ((this.getMiddlename() != null)) {
            clone.setMiddlename(this.getMiddlename().cloneObject());
        }
        if ((this.getSurname() != null)) {
            clone.setSurname(this.getSurname().cloneObject());
        }
        if ((this.getBirthday() != null)) {
            clone.setBirthday(this.getBirthday().cloneObject());
        }
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getImage() != null)) {
            clone.setImage(this.getImage().cloneObject());
        }
        clone.setStatusType(this.getStatusType());
        clone.setNationality(this.getNationality());
        clone.setGender(this.getGender());
        clone.setFamilyState(this.getFamilyState());
        clone.setPersonTitle(this.getPersonTitle());
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new BasetypeProperty<Name>(PROPERTY_NAMES[0], Name.class,
                PROPERTY_CONSTRAINTS[0], this.forename));
        properties.add(new BasetypeProperty<Name>(PROPERTY_NAMES[1], Name.class,
                PROPERTY_CONSTRAINTS[1], this.middlename));
        properties.add(new BasetypeProperty<Name>(PROPERTY_NAMES[2], Name.class,
                PROPERTY_CONSTRAINTS[2], this.surname));
        properties.add(new BasetypeProperty<Date>(PROPERTY_NAMES[3], Date.class,
                PROPERTY_CONSTRAINTS[3], this.birthday));
        properties.add(new BasetypeProperty<Owner>(PROPERTY_NAMES[4], Owner.class,
                PROPERTY_CONSTRAINTS[4], this.owner));
        properties.add(new DatatypeProperty<ImageFile>(PROPERTY_NAMES[5], ImageFile.class,
                PROPERTY_CONSTRAINTS[5], this.image));
        properties.add(new EnumProperty<StatusType>(PROPERTY_NAMES[6], StatusType.class,
                PROPERTY_CONSTRAINTS[6], this.statusType));
        properties.add(new EnumProperty<Nationality>(PROPERTY_NAMES[7], Nationality.class,
                PROPERTY_CONSTRAINTS[7], this.nationality));
        properties.add(new EnumProperty<Gender>(PROPERTY_NAMES[8], Gender.class,
                PROPERTY_CONSTRAINTS[8], this.gender));
        properties.add(new EnumProperty<FamilyState>(PROPERTY_NAMES[9], FamilyState.class,
                PROPERTY_CONSTRAINTS[9], this.familyState));
        properties.add(new EnumProperty<PersonTitle>(PROPERTY_NAMES[10], PersonTitle.class,
                PROPERTY_CONSTRAINTS[10], this.personTitle));
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
        final Person other = ((Person) obj);
        if ((this.forename == null)) {
            if ((other.forename != null))
                return false;
        } else if ((!this.forename.equals(other.forename)))
            return false;
        if ((this.middlename == null)) {
            if ((other.middlename != null))
                return false;
        } else if ((!this.middlename.equals(other.middlename)))
            return false;
        if ((this.surname == null)) {
            if ((other.surname != null))
                return false;
        } else if ((!this.surname.equals(other.surname)))
            return false;
        if ((this.birthday == null)) {
            if ((other.birthday != null))
                return false;
        } else if ((!this.birthday.equals(other.birthday)))
            return false;
        if ((this.owner == null)) {
            if ((other.owner != null))
                return false;
        } else if ((!this.owner.equals(other.owner)))
            return false;
        if ((this.image == null)) {
            if ((other.image != null))
                return false;
        } else if ((!this.image.equals(other.image)))
            return false;
        if ((this.imageRefId == null)) {
            if ((other.imageRefId != null))
                return false;
        } else if ((!this.imageRefId.equals(other.imageRefId)))
            return false;
        if ((this.statusType == null)) {
            if ((other.statusType != null))
                return false;
        } else if ((!this.statusType.equals(other.statusType)))
            return false;
        if ((this.nationality == null)) {
            if ((other.nationality != null))
                return false;
        } else if ((!this.nationality.equals(other.nationality)))
            return false;
        if ((this.gender == null)) {
            if ((other.gender != null))
                return false;
        } else if ((!this.gender.equals(other.gender)))
            return false;
        if ((this.familyState == null)) {
            if ((other.familyState != null))
                return false;
        } else if ((!this.familyState.equals(other.familyState)))
            return false;
        if ((this.personTitle == null)) {
            if ((other.personTitle != null))
                return false;
        } else if ((!this.personTitle.equals(other.personTitle)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.forename == null) ? 0 : this.forename.hashCode()));
        result = ((PRIME * result) + ((this.middlename == null) ? 0 : this.middlename.hashCode()));
        result = ((PRIME * result) + ((this.surname == null) ? 0 : this.surname.hashCode()));
        result = ((PRIME * result) + ((this.birthday == null) ? 0 : this.birthday.hashCode()));
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.image == null) ? 0 : this.image.hashCode()));
        result = ((PRIME * result) + ((this.imageRefId == null) ? 0 : this.imageRefId.hashCode()));
        result = ((PRIME * result) + ((this.statusType == null) ? 0 : this.statusType.hashCode()));
        result = ((PRIME * result) + ((this.nationality == null) ? 0 : this.nationality.hashCode()));
        result = ((PRIME * result) + ((this.gender == null) ? 0 : this.gender.hashCode()));
        result = ((PRIME * result) + ((this.familyState == null) ? 0 : this.familyState.hashCode()));
        result = ((PRIME * result) + ((this.personTitle == null) ? 0 : this.personTitle.hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<Person>\n");
        appendable.append(super.toString());
        appendable.append((("<forename>" + this.forename) + "</forename>\n"));
        appendable.append((("<middlename>" + this.middlename) + "</middlename>\n"));
        appendable.append((("<surname>" + this.surname) + "</surname>\n"));
        appendable.append((("<birthday>" + this.birthday) + "</birthday>\n"));
        appendable.append((("<owner>" + this.owner) + "</owner>\n"));
        appendable.append((("<image>" + this.image) + "</image>\n"));
        appendable.append((("<imageRefId>" + this.imageRefId) + "</imageRefId>\n"));
        appendable.append((("<statusType>" + this.statusType) + "</statusType>\n"));
        appendable.append((("<nationality>" + this.nationality) + "</nationality>\n"));
        appendable.append((("<gender>" + this.gender) + "</gender>\n"));
        appendable.append((("<familyState>" + this.familyState) + "</familyState>\n"));
        appendable.append((("<personTitle>" + this.personTitle) + "</personTitle>\n"));
        appendable.append("</Person>\n");
        return appendable.toString();
    }

    @Override
    public Person cloneObject() {
        Person clone = new Person();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Missing description at method getForename.
     *
     * @return the Name.
     */
    public Name getForename() {
        return this.forename;
    }

    /**
     * Missing description at method setForename.
     *
     * @param forename the Name.
     */
    public void setForename(Name forename) {
        this.forename = forename;
    }

    /**
     * Missing description at method setForename.
     *
     * @param forename the String.
     */
    public void setForename(String forename) {
        if ((this.forename == null)) {
            this.forename = new Name();
        }
        this.forename.setValue(forename);
    }

    /**
     * Missing description at method getMiddlename.
     *
     * @return the Name.
     */
    public Name getMiddlename() {
        return this.middlename;
    }

    /**
     * Missing description at method setMiddlename.
     *
     * @param middlename the Name.
     */
    public void setMiddlename(Name middlename) {
        this.middlename = middlename;
    }

    /**
     * Missing description at method setMiddlename.
     *
     * @param middlename the String.
     */
    public void setMiddlename(String middlename) {
        if ((this.middlename == null)) {
            this.middlename = new Name();
        }
        this.middlename.setValue(middlename);
    }

    /**
     * Missing description at method getSurname.
     *
     * @return the Name.
     */
    public Name getSurname() {
        return this.surname;
    }

    /**
     * Missing description at method setSurname.
     *
     * @param surname the Name.
     */
    public void setSurname(Name surname) {
        this.surname = surname;
    }

    /**
     * Missing description at method setSurname.
     *
     * @param surname the String.
     */
    public void setSurname(String surname) {
        if ((this.surname == null)) {
            this.surname = new Name();
        }
        this.surname.setValue(surname);
    }

    /**
     * Missing description at method getBirthday.
     *
     * @return the Date.
     */
    public Date getBirthday() {
        return this.birthday;
    }

    /**
     * Missing description at method setBirthday.
     *
     * @param birthday the Date.
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * Missing description at method setBirthday.
     *
     * @param birthday the java.util.Date.
     */
    public void setBirthday(java.util.Date birthday) {
        if ((this.birthday == null)) {
            this.birthday = new Date();
        }
        this.birthday.setValue(birthday);
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
     * Missing description at method setImage.
     *
     * @param image the ImageFile.
     */
    public void setImage(ImageFile image) {
        this.image = image;
        if ((image != null)) {
            this.setImageRefId(image.getId());
        } else {
            this.setImageRefId(null);
        }
    }

    /**
     * Missing description at method getImage.
     *
     * @return the ImageFile.
     */
    public ImageFile getImage() {
        return this.image;
    }

    /**
     * Getter for the ImageRefId.
     *
     * @return the Long.
     */
    public Long getImageRefId() {
        return this.imageRefId;
    }

    /**
     * Setter for the ImageRefId.
     *
     * @param imageRefId the Long.
     */
    public void setImageRefId(Long imageRefId) {
        this.imageRefId = imageRefId;
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

    /**
     * Missing description at method getNationality.
     *
     * @return the Nationality.
     */
    public Nationality getNationality() {
        return this.nationality;
    }

    /**
     * Missing description at method setNationality.
     *
     * @param nationality the Nationality.
     */
    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }

    /**
     * Missing description at method setNationality.
     *
     * @param nationality the String.
     */
    public void setNationality(String nationality) {
        if ((nationality == null)) {
            this.nationality = null;
        } else {
            this.nationality = Nationality.valueOf(nationality);
        }
    }

    /**
     * Missing description at method getGender.
     *
     * @return the Gender.
     */
    public Gender getGender() {
        return this.gender;
    }

    /**
     * Missing description at method setGender.
     *
     * @param gender the Gender.
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Missing description at method setGender.
     *
     * @param gender the String.
     */
    public void setGender(String gender) {
        if ((gender == null)) {
            this.gender = null;
        } else {
            this.gender = Gender.valueOf(gender);
        }
    }

    /**
     * Missing description at method getFamilyState.
     *
     * @return the FamilyState.
     */
    public FamilyState getFamilyState() {
        return this.familyState;
    }

    /**
     * Missing description at method setFamilyState.
     *
     * @param familyState the FamilyState.
     */
    public void setFamilyState(FamilyState familyState) {
        this.familyState = familyState;
    }

    /**
     * Missing description at method setFamilyState.
     *
     * @param familyState the String.
     */
    public void setFamilyState(String familyState) {
        if ((familyState == null)) {
            this.familyState = null;
        } else {
            this.familyState = FamilyState.valueOf(familyState);
        }
    }

    /**
     * Missing description at method getPersonTitle.
     *
     * @return the PersonTitle.
     */
    public PersonTitle getPersonTitle() {
        return this.personTitle;
    }

    /**
     * Missing description at method setPersonTitle.
     *
     * @param personTitle the PersonTitle.
     */
    public void setPersonTitle(PersonTitle personTitle) {
        this.personTitle = personTitle;
    }

    /**
     * Missing description at method setPersonTitle.
     *
     * @param personTitle the String.
     */
    public void setPersonTitle(String personTitle) {
        if ((personTitle == null)) {
            this.personTitle = null;
        } else {
            this.personTitle = PersonTitle.valueOf(personTitle);
        }
    }
}
