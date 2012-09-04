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
package org.nabucco.framework.base.facade.datatype.issuetracking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DateTime;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Number;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * Version
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2011-02-17
 */
public class Version extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;u0,n;m0,1;", "l0,255;u0,n;m0,1;", "l0,255;u0,n;m0,1;",
            "l0,n;u0,n;m0,1;", "l0,n;u0,n;m0,1;" };

    public static final String VERSIONID = "versionId";

    public static final String NAME = "name";

    public static final String DESCRIPTION = "description";

    public static final String RELEASEDATE = "releaseDate";

    public static final String SEQUENCE = "sequence";

    private Identifier versionId;

    private Name name;

    private Description description;

    private DateTime releaseDate;

    private Number sequence;

    /** Constructs a new Version instance. */
    public Version() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the Version.
     */
    protected void cloneObject(Version clone) {
        super.cloneObject(clone);
        if ((this.getVersionId() != null)) {
            clone.setVersionId(this.getVersionId().cloneObject());
        }
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        if ((this.getReleaseDate() != null)) {
            clone.setReleaseDate(this.getReleaseDate().cloneObject());
        }
        if ((this.getSequence() != null)) {
            clone.setSequence(this.getSequence().cloneObject());
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
        propertyMap.put(VERSIONID, PropertyDescriptorSupport.createBasetype(VERSIONID, Identifier.class, 3,
                PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(NAME,
                PropertyDescriptorSupport.createBasetype(NAME, Name.class, 4, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createBasetype(DESCRIPTION, Description.class, 5,
                PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(RELEASEDATE, PropertyDescriptorSupport.createBasetype(RELEASEDATE, DateTime.class, 6,
                PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(SEQUENCE,
                PropertyDescriptorSupport.createBasetype(SEQUENCE, Number.class, 7, PROPERTY_CONSTRAINTS[4], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(Version.getPropertyDescriptor(VERSIONID), this.versionId, null));
        properties.add(super.createProperty(Version.getPropertyDescriptor(NAME), this.name, null));
        properties.add(super.createProperty(Version.getPropertyDescriptor(DESCRIPTION), this.description, null));
        properties.add(super.createProperty(Version.getPropertyDescriptor(RELEASEDATE), this.releaseDate, null));
        properties.add(super.createProperty(Version.getPropertyDescriptor(SEQUENCE), this.sequence, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(VERSIONID) && (property.getType() == Identifier.class))) {
            this.setVersionId(((Identifier) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NAME) && (property.getType() == Name.class))) {
            this.setName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == Description.class))) {
            this.setDescription(((Description) property.getInstance()));
            return true;
        } else if ((property.getName().equals(RELEASEDATE) && (property.getType() == DateTime.class))) {
            this.setReleaseDate(((DateTime) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SEQUENCE) && (property.getType() == Number.class))) {
            this.setSequence(((Number) property.getInstance()));
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
        final Version other = ((Version) obj);
        if ((this.versionId == null)) {
            if ((other.versionId != null))
                return false;
        } else if ((!this.versionId.equals(other.versionId)))
            return false;
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
        if ((this.releaseDate == null)) {
            if ((other.releaseDate != null))
                return false;
        } else if ((!this.releaseDate.equals(other.releaseDate)))
            return false;
        if ((this.sequence == null)) {
            if ((other.sequence != null))
                return false;
        } else if ((!this.sequence.equals(other.sequence)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.versionId == null) ? 0 : this.versionId.hashCode()));
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.releaseDate == null) ? 0 : this.releaseDate.hashCode()));
        result = ((PRIME * result) + ((this.sequence == null) ? 0 : this.sequence.hashCode()));
        return result;
    }

    @Override
    public Version cloneObject() {
        Version clone = new Version();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Missing description at method getVersionId.
     *
     * @return the Identifier.
     */
    public Identifier getVersionId() {
        return this.versionId;
    }

    /**
     * Missing description at method setVersionId.
     *
     * @param versionId the Identifier.
     */
    public void setVersionId(Identifier versionId) {
        this.versionId = versionId;
    }

    /**
     * Missing description at method setVersionId.
     *
     * @param versionId the Long.
     */
    public void setVersionId(Long versionId) {
        if ((this.versionId == null)) {
            if ((versionId == null)) {
                return;
            }
            this.versionId = new Identifier();
        }
        this.versionId.setValue(versionId);
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
            if ((name == null)) {
                return;
            }
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
            if ((description == null)) {
                return;
            }
            this.description = new Description();
        }
        this.description.setValue(description);
    }

    /**
     * Missing description at method getReleaseDate.
     *
     * @return the DateTime.
     */
    public DateTime getReleaseDate() {
        return this.releaseDate;
    }

    /**
     * Missing description at method setReleaseDate.
     *
     * @param releaseDate the DateTime.
     */
    public void setReleaseDate(DateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Missing description at method setReleaseDate.
     *
     * @param releaseDate the Long.
     */
    public void setReleaseDate(Long releaseDate) {
        if ((this.releaseDate == null)) {
            if ((releaseDate == null)) {
                return;
            }
            this.releaseDate = new DateTime();
        }
        this.releaseDate.setValue(releaseDate);
    }

    /**
     * Missing description at method getSequence.
     *
     * @return the Number.
     */
    public Number getSequence() {
        return this.sequence;
    }

    /**
     * Missing description at method setSequence.
     *
     * @param sequence the Number.
     */
    public void setSequence(Number sequence) {
        this.sequence = sequence;
    }

    /**
     * Missing description at method setSequence.
     *
     * @param sequence the Integer.
     */
    public void setSequence(Integer sequence) {
        if ((this.sequence == null)) {
            if ((sequence == null)) {
                return;
            }
            this.sequence = new Number();
        }
        this.sequence.setValue(sequence);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(Version.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(Version.class).getAllProperties();
    }
}
