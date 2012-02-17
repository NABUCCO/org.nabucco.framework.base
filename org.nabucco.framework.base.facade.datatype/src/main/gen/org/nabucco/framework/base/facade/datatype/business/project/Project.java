/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.facade.datatype.business.project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.FunctionalIdentifier;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.StatusType;
import org.nabucco.framework.base.facade.datatype.business.ErpId;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.code.CodePath;
import org.nabucco.framework.base.facade.datatype.date.Date;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * Project<p/>A Project in the Project Component<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-11-30
 */
public class Project extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final StatusType STATUSTYPE_DEFAULT = StatusType.ACTIVE;

    private static final String[] PROPERTY_CONSTRAINTS = { "l3,12;u0,n;m1,1;", "l0,255;u0,n;m1,1;",
            "l0,255;u0,n;m0,1;", "l0,n;u0,n;m1,1;", "l0,n;u0,n;m0,1;", "m1,1;", "l3,20;u0,n;m0,1;", "l0,n;u0,n;m0,1;",
            "m0,1;" };

    public static final String OWNER = "owner";

    public static final String NAME = "name";

    public static final String DESCRIPTION = "description";

    public static final String STARTDATE = "startDate";

    public static final String ENDDATE = "endDate";

    public static final String STATUSTYPE = "statusType";

    public static final String ERPID = "erpId";

    public static final String PROJECTID = "projectId";

    public static final String PROJECTSCHEMA = "projectSchema";

    /** The owner of the project. */
    private Owner owner;

    /** The name of the project. */
    private Name name;

    /** The description of the project. */
    private Description description;

    /** The start date of the project. */
    private Date startDate;

    /** The end date of the project. */
    private Date endDate;

    /** The projects status type. */
    private StatusType statusType;

    /** Id of the ERP system section. */
    private ErpId erpId;

    /** The functional Project ID. */
    private FunctionalIdentifier projectId;

    /** The functional Project Type. */
    private Code projectSchema;

    protected static final String PROJECTSCHEMA_CODEPATH = "nabucco.business.project.projectschema";

    /** Constructs a new Project instance. */
    public Project() {
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
     * @param clone the Project.
     */
    protected void cloneObject(Project clone) {
        super.cloneObject(clone);
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        if ((this.getStartDate() != null)) {
            clone.setStartDate(this.getStartDate().cloneObject());
        }
        if ((this.getEndDate() != null)) {
            clone.setEndDate(this.getEndDate().cloneObject());
        }
        clone.setStatusType(this.getStatusType());
        if ((this.getErpId() != null)) {
            clone.setErpId(this.getErpId().cloneObject());
        }
        if ((this.getProjectId() != null)) {
            clone.setProjectId(this.getProjectId().cloneObject());
        }
        if ((this.getProjectSchema() != null)) {
            clone.setProjectSchema(this.getProjectSchema().cloneObject());
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
        propertyMap.put(NAME,
                PropertyDescriptorSupport.createBasetype(NAME, Name.class, 4, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createBasetype(DESCRIPTION, Description.class, 5,
                PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(STARTDATE,
                PropertyDescriptorSupport.createBasetype(STARTDATE, Date.class, 6, PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(ENDDATE,
                PropertyDescriptorSupport.createBasetype(ENDDATE, Date.class, 7, PROPERTY_CONSTRAINTS[4], false));
        propertyMap.put(STATUSTYPE, PropertyDescriptorSupport.createEnumeration(STATUSTYPE, StatusType.class, 8,
                PROPERTY_CONSTRAINTS[5], true));
        propertyMap.put(ERPID,
                PropertyDescriptorSupport.createBasetype(ERPID, ErpId.class, 9, PROPERTY_CONSTRAINTS[6], false));
        propertyMap.put(PROJECTID, PropertyDescriptorSupport.createBasetype(PROJECTID, FunctionalIdentifier.class, 10,
                PROPERTY_CONSTRAINTS[7], false));
        propertyMap.put(PROJECTSCHEMA, PropertyDescriptorSupport.createDatatype(PROJECTSCHEMA, Code.class, 11,
                PROPERTY_CONSTRAINTS[8], false, PropertyAssociationType.COMPOSITION, PROJECTSCHEMA_CODEPATH));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(Project.getPropertyDescriptor(OWNER), this.owner, null));
        properties.add(super.createProperty(Project.getPropertyDescriptor(NAME), this.name, null));
        properties.add(super.createProperty(Project.getPropertyDescriptor(DESCRIPTION), this.description, null));
        properties.add(super.createProperty(Project.getPropertyDescriptor(STARTDATE), this.startDate, null));
        properties.add(super.createProperty(Project.getPropertyDescriptor(ENDDATE), this.endDate, null));
        properties.add(super.createProperty(Project.getPropertyDescriptor(STATUSTYPE), this.getStatusType(), null));
        properties.add(super.createProperty(Project.getPropertyDescriptor(ERPID), this.erpId, null));
        properties.add(super.createProperty(Project.getPropertyDescriptor(PROJECTID), this.projectId, null));
        properties
                .add(super.createProperty(Project.getPropertyDescriptor(PROJECTSCHEMA), this.getProjectSchema(), null));
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
        } else if ((property.getName().equals(NAME) && (property.getType() == Name.class))) {
            this.setName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == Description.class))) {
            this.setDescription(((Description) property.getInstance()));
            return true;
        } else if ((property.getName().equals(STARTDATE) && (property.getType() == Date.class))) {
            this.setStartDate(((Date) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ENDDATE) && (property.getType() == Date.class))) {
            this.setEndDate(((Date) property.getInstance()));
            return true;
        } else if ((property.getName().equals(STATUSTYPE) && (property.getType() == StatusType.class))) {
            this.setStatusType(((StatusType) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ERPID) && (property.getType() == ErpId.class))) {
            this.setErpId(((ErpId) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PROJECTID) && (property.getType() == FunctionalIdentifier.class))) {
            this.setProjectId(((FunctionalIdentifier) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PROJECTSCHEMA) && (property.getType() == Code.class))) {
            this.setProjectSchema(((Code) property.getInstance()));
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
        final Project other = ((Project) obj);
        if ((this.owner == null)) {
            if ((other.owner != null))
                return false;
        } else if ((!this.owner.equals(other.owner)))
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
        if ((this.erpId == null)) {
            if ((other.erpId != null))
                return false;
        } else if ((!this.erpId.equals(other.erpId)))
            return false;
        if ((this.projectId == null)) {
            if ((other.projectId != null))
                return false;
        } else if ((!this.projectId.equals(other.projectId)))
            return false;
        if ((this.projectSchema == null)) {
            if ((other.projectSchema != null))
                return false;
        } else if ((!this.projectSchema.equals(other.projectSchema)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.startDate == null) ? 0 : this.startDate.hashCode()));
        result = ((PRIME * result) + ((this.endDate == null) ? 0 : this.endDate.hashCode()));
        result = ((PRIME * result) + ((this.statusType == null) ? 0 : this.statusType.hashCode()));
        result = ((PRIME * result) + ((this.erpId == null) ? 0 : this.erpId.hashCode()));
        result = ((PRIME * result) + ((this.projectId == null) ? 0 : this.projectId.hashCode()));
        result = ((PRIME * result) + ((this.projectSchema == null) ? 0 : this.projectSchema.hashCode()));
        return result;
    }

    @Override
    public Project cloneObject() {
        Project clone = new Project();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The owner of the project.
     *
     * @return the Owner.
     */
    public Owner getOwner() {
        return this.owner;
    }

    /**
     * The owner of the project.
     *
     * @param owner the Owner.
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * The owner of the project.
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
     * The name of the project.
     *
     * @return the Name.
     */
    public Name getName() {
        return this.name;
    }

    /**
     * The name of the project.
     *
     * @param name the Name.
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * The name of the project.
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
     * The description of the project.
     *
     * @return the Description.
     */
    public Description getDescription() {
        return this.description;
    }

    /**
     * The description of the project.
     *
     * @param description the Description.
     */
    public void setDescription(Description description) {
        this.description = description;
    }

    /**
     * The description of the project.
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
     * The start date of the project.
     *
     * @return the Date.
     */
    public Date getStartDate() {
        return this.startDate;
    }

    /**
     * The start date of the project.
     *
     * @param startDate the Date.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * The start date of the project.
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
     * The end date of the project.
     *
     * @return the Date.
     */
    public Date getEndDate() {
        return this.endDate;
    }

    /**
     * The end date of the project.
     *
     * @param endDate the Date.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * The end date of the project.
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
     * The projects status type.
     *
     * @return the StatusType.
     */
    public StatusType getStatusType() {
        return this.statusType;
    }

    /**
     * The projects status type.
     *
     * @param statusType the StatusType.
     */
    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    /**
     * The projects status type.
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
     * Id of the ERP system section.
     *
     * @return the ErpId.
     */
    public ErpId getErpId() {
        return this.erpId;
    }

    /**
     * Id of the ERP system section.
     *
     * @param erpId the ErpId.
     */
    public void setErpId(ErpId erpId) {
        this.erpId = erpId;
    }

    /**
     * Id of the ERP system section.
     *
     * @param erpId the String.
     */
    public void setErpId(String erpId) {
        if ((this.erpId == null)) {
            if ((erpId == null)) {
                return;
            }
            this.erpId = new ErpId();
        }
        this.erpId.setValue(erpId);
    }

    /**
     * The functional Project ID.
     *
     * @return the FunctionalIdentifier.
     */
    public FunctionalIdentifier getProjectId() {
        return this.projectId;
    }

    /**
     * The functional Project ID.
     *
     * @param projectId the FunctionalIdentifier.
     */
    public void setProjectId(FunctionalIdentifier projectId) {
        this.projectId = projectId;
    }

    /**
     * The functional Project ID.
     *
     * @param projectId the String.
     */
    public void setProjectId(String projectId) {
        if ((this.projectId == null)) {
            if ((projectId == null)) {
                return;
            }
            this.projectId = new FunctionalIdentifier();
        }
        this.projectId.setValue(projectId);
    }

    /**
     * The functional Project Type.
     *
     * @param projectSchema the Code.
     */
    public void setProjectSchema(Code projectSchema) {
        this.projectSchema = projectSchema;
    }

    /**
     * The functional Project Type.
     *
     * @return the Code.
     */
    public Code getProjectSchema() {
        return this.projectSchema;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(Project.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(Project.class).getAllProperties();
    }

    /**
     * Getter for the ProjectSchemaCodePath.
     *
     * @return the CodePath.
     */
    public static CodePath getProjectSchemaCodePath() {
        return new CodePath(PROJECTSCHEMA_CODEPATH);
    }
}
