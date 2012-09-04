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
package org.nabucco.framework.base.facade.datatype.business.person;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.FunctionalIdentifier;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.StatusType;
import org.nabucco.framework.base.facade.datatype.business.person.FamilyState;
import org.nabucco.framework.base.facade.datatype.business.person.Gender;
import org.nabucco.framework.base.facade.datatype.business.person.PersonTitle;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.code.CodePath;
import org.nabucco.framework.base.facade.datatype.date.Date;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.security.UserId;
import org.nabucco.framework.base.facade.datatype.text.LongDescription;

/**
 * Person<p/>A natural person in the Person Component.<p/>
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-30
 */
public class Person extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final StatusType STATUSTYPE_DEFAULT = StatusType.ACTIVE;

    private static final String[] PROPERTY_CONSTRAINTS = { "l3,12;u0,n;m1,1;", "l0,4000;u0,n;m0,1;",
            "l0,255;u0,n;m1,1;", "l0,255;u0,n;m0,1;", "l0,255;u0,n;m1,1;", "l0,255;u0,n;m0,1;", "l0,n;u0,n;m0,1;",
            "l0,255;u0,n;m0,1;", "m0,1;", "m0,1;", "m0,1;", "m0,1;", "m0,1;", "m0,1;", "l3,32;u0,n;m0,1;", "m1,1;",
            "l0,n;u0,n;m0,1;", "m0,1;" };

    public static final String OWNER = "owner";

    public static final String DESCRIPTION = "description";

    public static final String FORENAME = "forename";

    public static final String MIDDLENAME = "middlename";

    public static final String SURNAME = "surname";

    public static final String BIRTHNAME = "birthname";

    public static final String BIRTHDAY = "birthday";

    public static final String BIRTHCITY = "birthcity";

    public static final String GENDER = "gender";

    public static final String FAMILYSTATE = "familyState";

    public static final String PERSONTITLE = "personTitle";

    public static final String NATIONALITY = "nationality";

    public static final String BIRTHCOUNTRY = "birthcountry";

    public static final String RELIGION = "religion";

    public static final String USERID = "userId";

    public static final String STATUSTYPE = "statusType";

    public static final String FUNCTIONALID = "functionalId";

    public static final String FUNCTIONALTYPE = "functionalType";

    /** The owner of the Person. */
    private Owner owner;

    /** The description of the Person. */
    private LongDescription description;

    /** The Persons forename. */
    private Name forename;

    /** An optional middlename of the Person. */
    private Name middlename;

    /** The Persons surname. */
    private Name surname;

    /** The Persons birthname, when married. */
    private Name birthname;

    /** The Persons day of birth. */
    private Date birthday;

    /** The Persons city of birth. */
    private Name birthcity;

    /** The Persons gender. */
    private Gender gender;

    /** The Persons family state. */
    private FamilyState familyState;

    /** The Persons title. */
    private PersonTitle personTitle;

    /** The Persons nationality. */
    private Code nationality;

    protected static final String NATIONALITY_CODEPATH = "nabucco.framework.nationality";

    /** The Persons birth country. */
    private Code birthcountry;

    protected static final String BIRTHCOUNTRY_CODEPATH = "nabucco.framework.country";

    /** The Persons religion. */
    private Code religion;

    protected static final String RELIGION_CODEPATH = "nabucco.business.person.religion";

    /** The ID of the responsible user. */
    private UserId userId;

    /** The Persons status type. */
    private StatusType statusType;

    /** The functional Person ID. */
    private FunctionalIdentifier functionalId;

    /** The functional Person Type. */
    private Code functionalType;

    protected static final String FUNCTIONALTYPE_CODEPATH = "nabucco.business.person.functionaltype";

    /** Constructs a new Person instance. */
    public Person() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
        statusType = STATUSTYPE_DEFAULT;
    }

    /**
     * CloneObject.
     *
     * @param clone the Person.
     */
    protected void cloneObject(Person clone) {
        super.cloneObject(clone);
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        if ((this.getForename() != null)) {
            clone.setForename(this.getForename().cloneObject());
        }
        if ((this.getMiddlename() != null)) {
            clone.setMiddlename(this.getMiddlename().cloneObject());
        }
        if ((this.getSurname() != null)) {
            clone.setSurname(this.getSurname().cloneObject());
        }
        if ((this.getBirthname() != null)) {
            clone.setBirthname(this.getBirthname().cloneObject());
        }
        if ((this.getBirthday() != null)) {
            clone.setBirthday(this.getBirthday().cloneObject());
        }
        if ((this.getBirthcity() != null)) {
            clone.setBirthcity(this.getBirthcity().cloneObject());
        }
        clone.setGender(this.getGender());
        clone.setFamilyState(this.getFamilyState());
        clone.setPersonTitle(this.getPersonTitle());
        if ((this.getNationality() != null)) {
            clone.setNationality(this.getNationality().cloneObject());
        }
        if ((this.getBirthcountry() != null)) {
            clone.setBirthcountry(this.getBirthcountry().cloneObject());
        }
        if ((this.getReligion() != null)) {
            clone.setReligion(this.getReligion().cloneObject());
        }
        if ((this.getUserId() != null)) {
            clone.setUserId(this.getUserId().cloneObject());
        }
        clone.setStatusType(this.getStatusType());
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
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createBasetype(DESCRIPTION, LongDescription.class, 4,
                PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(FORENAME,
                PropertyDescriptorSupport.createBasetype(FORENAME, Name.class, 5, PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(MIDDLENAME,
                PropertyDescriptorSupport.createBasetype(MIDDLENAME, Name.class, 6, PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(SURNAME,
                PropertyDescriptorSupport.createBasetype(SURNAME, Name.class, 7, PROPERTY_CONSTRAINTS[4], false));
        propertyMap.put(BIRTHNAME,
                PropertyDescriptorSupport.createBasetype(BIRTHNAME, Name.class, 8, PROPERTY_CONSTRAINTS[5], false));
        propertyMap.put(BIRTHDAY,
                PropertyDescriptorSupport.createBasetype(BIRTHDAY, Date.class, 9, PROPERTY_CONSTRAINTS[6], false));
        propertyMap.put(BIRTHCITY,
                PropertyDescriptorSupport.createBasetype(BIRTHCITY, Name.class, 10, PROPERTY_CONSTRAINTS[7], false));
        propertyMap.put(GENDER,
                PropertyDescriptorSupport.createEnumeration(GENDER, Gender.class, 11, PROPERTY_CONSTRAINTS[8], false));
        propertyMap.put(FAMILYSTATE, PropertyDescriptorSupport.createEnumeration(FAMILYSTATE, FamilyState.class, 12,
                PROPERTY_CONSTRAINTS[9], false));
        propertyMap.put(PERSONTITLE, PropertyDescriptorSupport.createEnumeration(PERSONTITLE, PersonTitle.class, 13,
                PROPERTY_CONSTRAINTS[10], false));
        propertyMap.put(NATIONALITY, PropertyDescriptorSupport.createDatatype(NATIONALITY, Code.class, 14,
                PROPERTY_CONSTRAINTS[11], false, PropertyAssociationType.COMPOSITION, NATIONALITY_CODEPATH));
        propertyMap.put(BIRTHCOUNTRY, PropertyDescriptorSupport.createDatatype(BIRTHCOUNTRY, Code.class, 15,
                PROPERTY_CONSTRAINTS[12], false, PropertyAssociationType.COMPOSITION, BIRTHCOUNTRY_CODEPATH));
        propertyMap.put(RELIGION, PropertyDescriptorSupport.createDatatype(RELIGION, Code.class, 16,
                PROPERTY_CONSTRAINTS[13], false, PropertyAssociationType.COMPOSITION, RELIGION_CODEPATH));
        propertyMap.put(USERID,
                PropertyDescriptorSupport.createBasetype(USERID, UserId.class, 17, PROPERTY_CONSTRAINTS[14], false));
        propertyMap.put(STATUSTYPE, PropertyDescriptorSupport.createEnumeration(STATUSTYPE, StatusType.class, 18,
                PROPERTY_CONSTRAINTS[15], true));
        propertyMap.put(FUNCTIONALID, PropertyDescriptorSupport.createBasetype(FUNCTIONALID,
                FunctionalIdentifier.class, 19, PROPERTY_CONSTRAINTS[16], false));
        propertyMap.put(FUNCTIONALTYPE, PropertyDescriptorSupport.createDatatype(FUNCTIONALTYPE, Code.class, 20,
                PROPERTY_CONSTRAINTS[17], false, PropertyAssociationType.COMPOSITION, FUNCTIONALTYPE_CODEPATH));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(Person.getPropertyDescriptor(OWNER), this.owner, null));
        properties.add(super.createProperty(Person.getPropertyDescriptor(DESCRIPTION), this.description, null));
        properties.add(super.createProperty(Person.getPropertyDescriptor(FORENAME), this.forename, null));
        properties.add(super.createProperty(Person.getPropertyDescriptor(MIDDLENAME), this.middlename, null));
        properties.add(super.createProperty(Person.getPropertyDescriptor(SURNAME), this.surname, null));
        properties.add(super.createProperty(Person.getPropertyDescriptor(BIRTHNAME), this.birthname, null));
        properties.add(super.createProperty(Person.getPropertyDescriptor(BIRTHDAY), this.birthday, null));
        properties.add(super.createProperty(Person.getPropertyDescriptor(BIRTHCITY), this.birthcity, null));
        properties.add(super.createProperty(Person.getPropertyDescriptor(GENDER), this.getGender(), null));
        properties.add(super.createProperty(Person.getPropertyDescriptor(FAMILYSTATE), this.getFamilyState(), null));
        properties.add(super.createProperty(Person.getPropertyDescriptor(PERSONTITLE), this.getPersonTitle(), null));
        properties.add(super.createProperty(Person.getPropertyDescriptor(NATIONALITY), this.getNationality(), null));
        properties.add(super.createProperty(Person.getPropertyDescriptor(BIRTHCOUNTRY), this.getBirthcountry(), null));
        properties.add(super.createProperty(Person.getPropertyDescriptor(RELIGION), this.getReligion(), null));
        properties.add(super.createProperty(Person.getPropertyDescriptor(USERID), this.userId, null));
        properties.add(super.createProperty(Person.getPropertyDescriptor(STATUSTYPE), this.getStatusType(), null));
        properties.add(super.createProperty(Person.getPropertyDescriptor(FUNCTIONALID), this.functionalId, null));
        properties.add(super.createProperty(Person.getPropertyDescriptor(FUNCTIONALTYPE), this.getFunctionalType(),
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
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == LongDescription.class))) {
            this.setDescription(((LongDescription) property.getInstance()));
            return true;
        } else if ((property.getName().equals(FORENAME) && (property.getType() == Name.class))) {
            this.setForename(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MIDDLENAME) && (property.getType() == Name.class))) {
            this.setMiddlename(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SURNAME) && (property.getType() == Name.class))) {
            this.setSurname(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(BIRTHNAME) && (property.getType() == Name.class))) {
            this.setBirthname(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(BIRTHDAY) && (property.getType() == Date.class))) {
            this.setBirthday(((Date) property.getInstance()));
            return true;
        } else if ((property.getName().equals(BIRTHCITY) && (property.getType() == Name.class))) {
            this.setBirthcity(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(GENDER) && (property.getType() == Gender.class))) {
            this.setGender(((Gender) property.getInstance()));
            return true;
        } else if ((property.getName().equals(FAMILYSTATE) && (property.getType() == FamilyState.class))) {
            this.setFamilyState(((FamilyState) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PERSONTITLE) && (property.getType() == PersonTitle.class))) {
            this.setPersonTitle(((PersonTitle) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NATIONALITY) && (property.getType() == Code.class))) {
            this.setNationality(((Code) property.getInstance()));
            return true;
        } else if ((property.getName().equals(BIRTHCOUNTRY) && (property.getType() == Code.class))) {
            this.setBirthcountry(((Code) property.getInstance()));
            return true;
        } else if ((property.getName().equals(RELIGION) && (property.getType() == Code.class))) {
            this.setReligion(((Code) property.getInstance()));
            return true;
        } else if ((property.getName().equals(USERID) && (property.getType() == UserId.class))) {
            this.setUserId(((UserId) property.getInstance()));
            return true;
        } else if ((property.getName().equals(STATUSTYPE) && (property.getType() == StatusType.class))) {
            this.setStatusType(((StatusType) property.getInstance()));
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
        final Person other = ((Person) obj);
        if ((this.owner == null)) {
            if ((other.owner != null))
                return false;
        } else if ((!this.owner.equals(other.owner)))
            return false;
        if ((this.description == null)) {
            if ((other.description != null))
                return false;
        } else if ((!this.description.equals(other.description)))
            return false;
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
        if ((this.birthname == null)) {
            if ((other.birthname != null))
                return false;
        } else if ((!this.birthname.equals(other.birthname)))
            return false;
        if ((this.birthday == null)) {
            if ((other.birthday != null))
                return false;
        } else if ((!this.birthday.equals(other.birthday)))
            return false;
        if ((this.birthcity == null)) {
            if ((other.birthcity != null))
                return false;
        } else if ((!this.birthcity.equals(other.birthcity)))
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
        if ((this.nationality == null)) {
            if ((other.nationality != null))
                return false;
        } else if ((!this.nationality.equals(other.nationality)))
            return false;
        if ((this.birthcountry == null)) {
            if ((other.birthcountry != null))
                return false;
        } else if ((!this.birthcountry.equals(other.birthcountry)))
            return false;
        if ((this.religion == null)) {
            if ((other.religion != null))
                return false;
        } else if ((!this.religion.equals(other.religion)))
            return false;
        if ((this.userId == null)) {
            if ((other.userId != null))
                return false;
        } else if ((!this.userId.equals(other.userId)))
            return false;
        if ((this.statusType == null)) {
            if ((other.statusType != null))
                return false;
        } else if ((!this.statusType.equals(other.statusType)))
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
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.forename == null) ? 0 : this.forename.hashCode()));
        result = ((PRIME * result) + ((this.middlename == null) ? 0 : this.middlename.hashCode()));
        result = ((PRIME * result) + ((this.surname == null) ? 0 : this.surname.hashCode()));
        result = ((PRIME * result) + ((this.birthname == null) ? 0 : this.birthname.hashCode()));
        result = ((PRIME * result) + ((this.birthday == null) ? 0 : this.birthday.hashCode()));
        result = ((PRIME * result) + ((this.birthcity == null) ? 0 : this.birthcity.hashCode()));
        result = ((PRIME * result) + ((this.gender == null) ? 0 : this.gender.hashCode()));
        result = ((PRIME * result) + ((this.familyState == null) ? 0 : this.familyState.hashCode()));
        result = ((PRIME * result) + ((this.personTitle == null) ? 0 : this.personTitle.hashCode()));
        result = ((PRIME * result) + ((this.nationality == null) ? 0 : this.nationality.hashCode()));
        result = ((PRIME * result) + ((this.birthcountry == null) ? 0 : this.birthcountry.hashCode()));
        result = ((PRIME * result) + ((this.religion == null) ? 0 : this.religion.hashCode()));
        result = ((PRIME * result) + ((this.userId == null) ? 0 : this.userId.hashCode()));
        result = ((PRIME * result) + ((this.statusType == null) ? 0 : this.statusType.hashCode()));
        result = ((PRIME * result) + ((this.functionalId == null) ? 0 : this.functionalId.hashCode()));
        result = ((PRIME * result) + ((this.functionalType == null) ? 0 : this.functionalType.hashCode()));
        return result;
    }

    @Override
    public Person cloneObject() {
        Person clone = new Person();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The owner of the Person.
     *
     * @return the Owner.
     */
    public Owner getOwner() {
        return this.owner;
    }

    /**
     * The owner of the Person.
     *
     * @param owner the Owner.
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * The owner of the Person.
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
     * The description of the Person.
     *
     * @return the LongDescription.
     */
    public LongDescription getDescription() {
        return this.description;
    }

    /**
     * The description of the Person.
     *
     * @param description the LongDescription.
     */
    public void setDescription(LongDescription description) {
        this.description = description;
    }

    /**
     * The description of the Person.
     *
     * @param description the String.
     */
    public void setDescription(String description) {
        if ((this.description == null)) {
            if ((description == null)) {
                return;
            }
            this.description = new LongDescription();
        }
        this.description.setValue(description);
    }

    /**
     * The Persons forename.
     *
     * @return the Name.
     */
    public Name getForename() {
        return this.forename;
    }

    /**
     * The Persons forename.
     *
     * @param forename the Name.
     */
    public void setForename(Name forename) {
        this.forename = forename;
    }

    /**
     * The Persons forename.
     *
     * @param forename the String.
     */
    public void setForename(String forename) {
        if ((this.forename == null)) {
            if ((forename == null)) {
                return;
            }
            this.forename = new Name();
        }
        this.forename.setValue(forename);
    }

    /**
     * An optional middlename of the Person.
     *
     * @return the Name.
     */
    public Name getMiddlename() {
        return this.middlename;
    }

    /**
     * An optional middlename of the Person.
     *
     * @param middlename the Name.
     */
    public void setMiddlename(Name middlename) {
        this.middlename = middlename;
    }

    /**
     * An optional middlename of the Person.
     *
     * @param middlename the String.
     */
    public void setMiddlename(String middlename) {
        if ((this.middlename == null)) {
            if ((middlename == null)) {
                return;
            }
            this.middlename = new Name();
        }
        this.middlename.setValue(middlename);
    }

    /**
     * The Persons surname.
     *
     * @return the Name.
     */
    public Name getSurname() {
        return this.surname;
    }

    /**
     * The Persons surname.
     *
     * @param surname the Name.
     */
    public void setSurname(Name surname) {
        this.surname = surname;
    }

    /**
     * The Persons surname.
     *
     * @param surname the String.
     */
    public void setSurname(String surname) {
        if ((this.surname == null)) {
            if ((surname == null)) {
                return;
            }
            this.surname = new Name();
        }
        this.surname.setValue(surname);
    }

    /**
     * The Persons birthname, when married.
     *
     * @return the Name.
     */
    public Name getBirthname() {
        return this.birthname;
    }

    /**
     * The Persons birthname, when married.
     *
     * @param birthname the Name.
     */
    public void setBirthname(Name birthname) {
        this.birthname = birthname;
    }

    /**
     * The Persons birthname, when married.
     *
     * @param birthname the String.
     */
    public void setBirthname(String birthname) {
        if ((this.birthname == null)) {
            if ((birthname == null)) {
                return;
            }
            this.birthname = new Name();
        }
        this.birthname.setValue(birthname);
    }

    /**
     * The Persons day of birth.
     *
     * @return the Date.
     */
    public Date getBirthday() {
        return this.birthday;
    }

    /**
     * The Persons day of birth.
     *
     * @param birthday the Date.
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * The Persons day of birth.
     *
     * @param birthday the java.util.Date.
     */
    public void setBirthday(java.util.Date birthday) {
        if ((this.birthday == null)) {
            if ((birthday == null)) {
                return;
            }
            this.birthday = new Date();
        }
        this.birthday.setValue(birthday);
    }

    /**
     * The Persons city of birth.
     *
     * @return the Name.
     */
    public Name getBirthcity() {
        return this.birthcity;
    }

    /**
     * The Persons city of birth.
     *
     * @param birthcity the Name.
     */
    public void setBirthcity(Name birthcity) {
        this.birthcity = birthcity;
    }

    /**
     * The Persons city of birth.
     *
     * @param birthcity the String.
     */
    public void setBirthcity(String birthcity) {
        if ((this.birthcity == null)) {
            if ((birthcity == null)) {
                return;
            }
            this.birthcity = new Name();
        }
        this.birthcity.setValue(birthcity);
    }

    /**
     * The Persons gender.
     *
     * @return the Gender.
     */
    public Gender getGender() {
        return this.gender;
    }

    /**
     * The Persons gender.
     *
     * @param gender the Gender.
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * The Persons gender.
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
     * The Persons family state.
     *
     * @return the FamilyState.
     */
    public FamilyState getFamilyState() {
        return this.familyState;
    }

    /**
     * The Persons family state.
     *
     * @param familyState the FamilyState.
     */
    public void setFamilyState(FamilyState familyState) {
        this.familyState = familyState;
    }

    /**
     * The Persons family state.
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
     * The Persons title.
     *
     * @return the PersonTitle.
     */
    public PersonTitle getPersonTitle() {
        return this.personTitle;
    }

    /**
     * The Persons title.
     *
     * @param personTitle the PersonTitle.
     */
    public void setPersonTitle(PersonTitle personTitle) {
        this.personTitle = personTitle;
    }

    /**
     * The Persons title.
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

    /**
     * The Persons nationality.
     *
     * @param nationality the Code.
     */
    public void setNationality(Code nationality) {
        this.nationality = nationality;
    }

    /**
     * The Persons nationality.
     *
     * @return the Code.
     */
    public Code getNationality() {
        return this.nationality;
    }

    /**
     * The Persons birth country.
     *
     * @param birthcountry the Code.
     */
    public void setBirthcountry(Code birthcountry) {
        this.birthcountry = birthcountry;
    }

    /**
     * The Persons birth country.
     *
     * @return the Code.
     */
    public Code getBirthcountry() {
        return this.birthcountry;
    }

    /**
     * The Persons religion.
     *
     * @param religion the Code.
     */
    public void setReligion(Code religion) {
        this.religion = religion;
    }

    /**
     * The Persons religion.
     *
     * @return the Code.
     */
    public Code getReligion() {
        return this.religion;
    }

    /**
     * The ID of the responsible user.
     *
     * @return the UserId.
     */
    public UserId getUserId() {
        return this.userId;
    }

    /**
     * The ID of the responsible user.
     *
     * @param userId the UserId.
     */
    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    /**
     * The ID of the responsible user.
     *
     * @param userId the String.
     */
    public void setUserId(String userId) {
        if ((this.userId == null)) {
            if ((userId == null)) {
                return;
            }
            this.userId = new UserId();
        }
        this.userId.setValue(userId);
    }

    /**
     * The Persons status type.
     *
     * @return the StatusType.
     */
    public StatusType getStatusType() {
        return this.statusType;
    }

    /**
     * The Persons status type.
     *
     * @param statusType the StatusType.
     */
    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    /**
     * The Persons status type.
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
     * The functional Person ID.
     *
     * @return the FunctionalIdentifier.
     */
    public FunctionalIdentifier getFunctionalId() {
        return this.functionalId;
    }

    /**
     * The functional Person ID.
     *
     * @param functionalId the FunctionalIdentifier.
     */
    public void setFunctionalId(FunctionalIdentifier functionalId) {
        this.functionalId = functionalId;
    }

    /**
     * The functional Person ID.
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
     * The functional Person Type.
     *
     * @param functionalType the Code.
     */
    public void setFunctionalType(Code functionalType) {
        this.functionalType = functionalType;
    }

    /**
     * The functional Person Type.
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
        return PropertyCache.getInstance().retrieve(Person.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(Person.class).getAllProperties();
    }

    /**
     * Getter for the NationalityCodePath.
     *
     * @return the CodePath.
     */
    public static CodePath getNationalityCodePath() {
        return new CodePath(NATIONALITY_CODEPATH);
    }

    /**
     * Getter for the BirthcountryCodePath.
     *
     * @return the CodePath.
     */
    public static CodePath getBirthcountryCodePath() {
        return new CodePath(BIRTHCOUNTRY_CODEPATH);
    }

    /**
     * Getter for the ReligionCodePath.
     *
     * @return the CodePath.
     */
    public static CodePath getReligionCodePath() {
        return new CodePath(RELIGION_CODEPATH);
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
