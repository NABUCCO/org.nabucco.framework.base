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
package org.nabucco.framework.base.facade.datatype.workflow.instance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.code.CodePath;
import org.nabucco.framework.base.facade.datatype.date.Date;
import org.nabucco.framework.base.facade.datatype.date.Timestamp;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.text.TextContent;
import org.nabucco.framework.base.facade.datatype.workflow.instance.WorkflowType;

/**
 * Instance<p/>An instance of a workflow (represents a complete workflow iteration).<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-02-08
 */
public class Instance extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final WorkflowType TYPE_DEFAULT = WorkflowType.DETACHED;

    private static final String[] PROPERTY_CONSTRAINTS = { "l1,255;u0,n;m1,1;", "l3,12;u0,n;m1,1;",
            "l1,255;u0,n;m1,1;", "l0,2147483647;u0,n;m0,1;", "m1,1;", "l0,255;u0,n;m1,1;", "l0,n;u0,n;m1,1;",
            "l0,255;u0,n;m0,1;", "l0,255;u0,n;m0,1;", "l0,255;u0,n;m0,1;", "m1,1;", "m0,1;", "l0,n;u0,n;m0,1;" };

    public static final String NAME = "name";

    public static final String OWNER = "owner";

    public static final String SUMMARY = "summary";

    public static final String DESCRIPTION = "description";

    public static final String TYPE = "type";

    public static final String CREATOR = "creator";

    public static final String CREATIONTIME = "creationTime";

    public static final String ASSIGNEDUSER = "assignedUser";

    public static final String ASSIGNEDGROUP = "assignedGroup";

    public static final String ASSIGNEDROLE = "assignedRole";

    public static final String FUNCTIONALTYPE = "functionalType";

    public static final String PRIORITY = "priority";

    public static final String DUEDATE = "dueDate";

    /** The name of the WorkflowInstance. */
    private Name name;

    /** The owner of the WorkflowInstance. */
    private Owner owner;

    /** A summary of the WorkflowInstance. */
    private Description summary;

    /** A brief description of the WorkflowInstance. */
    private TextContent description;

    /** The type of the workflow instance. */
    private WorkflowType type;

    /** The actual creator */
    private Name creator;

    /** The date and time of creation */
    private Timestamp creationTime;

    /** The user the instance is assigned to. */
    private Name assignedUser;

    /** The group the instance is assigned to. */
    private Name assignedGroup;

    /** The role the instance is assigned to. */
    private Name assignedRole;

    /** Functional type of the workflow. */
    private Code functionalType;

    protected static final String FUNCTIONALTYPE_CODEPATH = "nabucco.workflow.functionaltype";

    /** Priority of the workflow. */
    private Code priority;

    protected static final String PRIORITY_CODEPATH = "nabucco.workflow.priority";

    /** Due date of the instance. */
    private Date dueDate;

    /** Constructs a new Instance instance. */
    public Instance() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
        type = TYPE_DEFAULT;
    }

    /**
     * CloneObject.
     *
     * @param clone the Instance.
     */
    protected void cloneObject(Instance clone) {
        super.cloneObject(clone);
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getSummary() != null)) {
            clone.setSummary(this.getSummary().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        clone.setType(this.getType());
        if ((this.getCreator() != null)) {
            clone.setCreator(this.getCreator().cloneObject());
        }
        if ((this.getCreationTime() != null)) {
            clone.setCreationTime(this.getCreationTime().cloneObject());
        }
        if ((this.getAssignedUser() != null)) {
            clone.setAssignedUser(this.getAssignedUser().cloneObject());
        }
        if ((this.getAssignedGroup() != null)) {
            clone.setAssignedGroup(this.getAssignedGroup().cloneObject());
        }
        if ((this.getAssignedRole() != null)) {
            clone.setAssignedRole(this.getAssignedRole().cloneObject());
        }
        if ((this.getFunctionalType() != null)) {
            clone.setFunctionalType(this.getFunctionalType().cloneObject());
        }
        if ((this.getPriority() != null)) {
            clone.setPriority(this.getPriority().cloneObject());
        }
        if ((this.getDueDate() != null)) {
            clone.setDueDate(this.getDueDate().cloneObject());
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
        propertyMap.put(NAME,
                PropertyDescriptorSupport.createBasetype(NAME, Name.class, 3, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(OWNER,
                PropertyDescriptorSupport.createBasetype(OWNER, Owner.class, 4, PROPERTY_CONSTRAINTS[1], false));
        propertyMap
                .put(SUMMARY, PropertyDescriptorSupport.createBasetype(SUMMARY, Description.class, 5,
                        PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createBasetype(DESCRIPTION, TextContent.class, 6,
                PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(TYPE, PropertyDescriptorSupport.createEnumeration(TYPE, WorkflowType.class, 7,
                PROPERTY_CONSTRAINTS[4], false));
        propertyMap.put(CREATOR,
                PropertyDescriptorSupport.createBasetype(CREATOR, Name.class, 8, PROPERTY_CONSTRAINTS[5], false));
        propertyMap.put(CREATIONTIME, PropertyDescriptorSupport.createBasetype(CREATIONTIME, Timestamp.class, 9,
                PROPERTY_CONSTRAINTS[6], false));
        propertyMap.put(ASSIGNEDUSER,
                PropertyDescriptorSupport.createBasetype(ASSIGNEDUSER, Name.class, 10, PROPERTY_CONSTRAINTS[7], false));
        propertyMap
                .put(ASSIGNEDGROUP, PropertyDescriptorSupport.createBasetype(ASSIGNEDGROUP, Name.class, 11,
                        PROPERTY_CONSTRAINTS[8], false));
        propertyMap.put(ASSIGNEDROLE,
                PropertyDescriptorSupport.createBasetype(ASSIGNEDROLE, Name.class, 12, PROPERTY_CONSTRAINTS[9], false));
        propertyMap.put(FUNCTIONALTYPE, PropertyDescriptorSupport.createDatatype(FUNCTIONALTYPE, Code.class, 13,
                PROPERTY_CONSTRAINTS[10], false, PropertyAssociationType.COMPOSITION, FUNCTIONALTYPE_CODEPATH));
        propertyMap.put(PRIORITY, PropertyDescriptorSupport.createDatatype(PRIORITY, Code.class, 14,
                PROPERTY_CONSTRAINTS[11], false, PropertyAssociationType.COMPOSITION, PRIORITY_CODEPATH));
        propertyMap.put(DUEDATE,
                PropertyDescriptorSupport.createBasetype(DUEDATE, Date.class, 15, PROPERTY_CONSTRAINTS[12], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(Instance.getPropertyDescriptor(NAME), this.name, null));
        properties.add(super.createProperty(Instance.getPropertyDescriptor(OWNER), this.owner, null));
        properties.add(super.createProperty(Instance.getPropertyDescriptor(SUMMARY), this.summary, null));
        properties.add(super.createProperty(Instance.getPropertyDescriptor(DESCRIPTION), this.description, null));
        properties.add(super.createProperty(Instance.getPropertyDescriptor(TYPE), this.getType(), null));
        properties.add(super.createProperty(Instance.getPropertyDescriptor(CREATOR), this.creator, null));
        properties.add(super.createProperty(Instance.getPropertyDescriptor(CREATIONTIME), this.creationTime, null));
        properties.add(super.createProperty(Instance.getPropertyDescriptor(ASSIGNEDUSER), this.assignedUser, null));
        properties.add(super.createProperty(Instance.getPropertyDescriptor(ASSIGNEDGROUP), this.assignedGroup, null));
        properties.add(super.createProperty(Instance.getPropertyDescriptor(ASSIGNEDROLE), this.assignedRole, null));
        properties.add(super.createProperty(Instance.getPropertyDescriptor(FUNCTIONALTYPE), this.getFunctionalType(),
                null));
        properties.add(super.createProperty(Instance.getPropertyDescriptor(PRIORITY), this.getPriority(), null));
        properties.add(super.createProperty(Instance.getPropertyDescriptor(DUEDATE), this.dueDate, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(NAME) && (property.getType() == Name.class))) {
            this.setName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(OWNER) && (property.getType() == Owner.class))) {
            this.setOwner(((Owner) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SUMMARY) && (property.getType() == Description.class))) {
            this.setSummary(((Description) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == TextContent.class))) {
            this.setDescription(((TextContent) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TYPE) && (property.getType() == WorkflowType.class))) {
            this.setType(((WorkflowType) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CREATOR) && (property.getType() == Name.class))) {
            this.setCreator(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CREATIONTIME) && (property.getType() == Timestamp.class))) {
            this.setCreationTime(((Timestamp) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ASSIGNEDUSER) && (property.getType() == Name.class))) {
            this.setAssignedUser(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ASSIGNEDGROUP) && (property.getType() == Name.class))) {
            this.setAssignedGroup(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ASSIGNEDROLE) && (property.getType() == Name.class))) {
            this.setAssignedRole(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(FUNCTIONALTYPE) && (property.getType() == Code.class))) {
            this.setFunctionalType(((Code) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PRIORITY) && (property.getType() == Code.class))) {
            this.setPriority(((Code) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DUEDATE) && (property.getType() == Date.class))) {
            this.setDueDate(((Date) property.getInstance()));
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
        final Instance other = ((Instance) obj);
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        if ((this.owner == null)) {
            if ((other.owner != null))
                return false;
        } else if ((!this.owner.equals(other.owner)))
            return false;
        if ((this.summary == null)) {
            if ((other.summary != null))
                return false;
        } else if ((!this.summary.equals(other.summary)))
            return false;
        if ((this.description == null)) {
            if ((other.description != null))
                return false;
        } else if ((!this.description.equals(other.description)))
            return false;
        if ((this.type == null)) {
            if ((other.type != null))
                return false;
        } else if ((!this.type.equals(other.type)))
            return false;
        if ((this.creator == null)) {
            if ((other.creator != null))
                return false;
        } else if ((!this.creator.equals(other.creator)))
            return false;
        if ((this.creationTime == null)) {
            if ((other.creationTime != null))
                return false;
        } else if ((!this.creationTime.equals(other.creationTime)))
            return false;
        if ((this.assignedUser == null)) {
            if ((other.assignedUser != null))
                return false;
        } else if ((!this.assignedUser.equals(other.assignedUser)))
            return false;
        if ((this.assignedGroup == null)) {
            if ((other.assignedGroup != null))
                return false;
        } else if ((!this.assignedGroup.equals(other.assignedGroup)))
            return false;
        if ((this.assignedRole == null)) {
            if ((other.assignedRole != null))
                return false;
        } else if ((!this.assignedRole.equals(other.assignedRole)))
            return false;
        if ((this.functionalType == null)) {
            if ((other.functionalType != null))
                return false;
        } else if ((!this.functionalType.equals(other.functionalType)))
            return false;
        if ((this.priority == null)) {
            if ((other.priority != null))
                return false;
        } else if ((!this.priority.equals(other.priority)))
            return false;
        if ((this.dueDate == null)) {
            if ((other.dueDate != null))
                return false;
        } else if ((!this.dueDate.equals(other.dueDate)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.summary == null) ? 0 : this.summary.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.type == null) ? 0 : this.type.hashCode()));
        result = ((PRIME * result) + ((this.creator == null) ? 0 : this.creator.hashCode()));
        result = ((PRIME * result) + ((this.creationTime == null) ? 0 : this.creationTime.hashCode()));
        result = ((PRIME * result) + ((this.assignedUser == null) ? 0 : this.assignedUser.hashCode()));
        result = ((PRIME * result) + ((this.assignedGroup == null) ? 0 : this.assignedGroup.hashCode()));
        result = ((PRIME * result) + ((this.assignedRole == null) ? 0 : this.assignedRole.hashCode()));
        result = ((PRIME * result) + ((this.functionalType == null) ? 0 : this.functionalType.hashCode()));
        result = ((PRIME * result) + ((this.priority == null) ? 0 : this.priority.hashCode()));
        result = ((PRIME * result) + ((this.dueDate == null) ? 0 : this.dueDate.hashCode()));
        return result;
    }

    @Override
    public Instance cloneObject() {
        Instance clone = new Instance();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The name of the WorkflowInstance.
     *
     * @return the Name.
     */
    public Name getName() {
        return this.name;
    }

    /**
     * The name of the WorkflowInstance.
     *
     * @param name the Name.
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * The name of the WorkflowInstance.
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
     * The owner of the WorkflowInstance.
     *
     * @return the Owner.
     */
    public Owner getOwner() {
        return this.owner;
    }

    /**
     * The owner of the WorkflowInstance.
     *
     * @param owner the Owner.
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * The owner of the WorkflowInstance.
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
     * A summary of the WorkflowInstance.
     *
     * @return the Description.
     */
    public Description getSummary() {
        return this.summary;
    }

    /**
     * A summary of the WorkflowInstance.
     *
     * @param summary the Description.
     */
    public void setSummary(Description summary) {
        this.summary = summary;
    }

    /**
     * A summary of the WorkflowInstance.
     *
     * @param summary the String.
     */
    public void setSummary(String summary) {
        if ((this.summary == null)) {
            if ((summary == null)) {
                return;
            }
            this.summary = new Description();
        }
        this.summary.setValue(summary);
    }

    /**
     * A brief description of the WorkflowInstance.
     *
     * @return the TextContent.
     */
    public TextContent getDescription() {
        return this.description;
    }

    /**
     * A brief description of the WorkflowInstance.
     *
     * @param description the TextContent.
     */
    public void setDescription(TextContent description) {
        this.description = description;
    }

    /**
     * A brief description of the WorkflowInstance.
     *
     * @param description the String.
     */
    public void setDescription(String description) {
        if ((this.description == null)) {
            if ((description == null)) {
                return;
            }
            this.description = new TextContent();
        }
        this.description.setValue(description);
    }

    /**
     * The type of the workflow instance.
     *
     * @return the WorkflowType.
     */
    public WorkflowType getType() {
        return this.type;
    }

    /**
     * The type of the workflow instance.
     *
     * @param type the WorkflowType.
     */
    public void setType(WorkflowType type) {
        this.type = type;
    }

    /**
     * The type of the workflow instance.
     *
     * @param type the String.
     */
    public void setType(String type) {
        if ((type == null)) {
            this.type = null;
        } else {
            this.type = WorkflowType.valueOf(type);
        }
    }

    /**
     * The actual creator
     *
     * @return the Name.
     */
    public Name getCreator() {
        return this.creator;
    }

    /**
     * The actual creator
     *
     * @param creator the Name.
     */
    public void setCreator(Name creator) {
        this.creator = creator;
    }

    /**
     * The actual creator
     *
     * @param creator the String.
     */
    public void setCreator(String creator) {
        if ((this.creator == null)) {
            if ((creator == null)) {
                return;
            }
            this.creator = new Name();
        }
        this.creator.setValue(creator);
    }

    /**
     * The date and time of creation
     *
     * @return the Timestamp.
     */
    public Timestamp getCreationTime() {
        return this.creationTime;
    }

    /**
     * The date and time of creation
     *
     * @param creationTime the Timestamp.
     */
    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * The date and time of creation
     *
     * @param creationTime the Long.
     */
    public void setCreationTime(Long creationTime) {
        if ((this.creationTime == null)) {
            if ((creationTime == null)) {
                return;
            }
            this.creationTime = new Timestamp();
        }
        this.creationTime.setValue(creationTime);
    }

    /**
     * The user the instance is assigned to.
     *
     * @return the Name.
     */
    public Name getAssignedUser() {
        return this.assignedUser;
    }

    /**
     * The user the instance is assigned to.
     *
     * @param assignedUser the Name.
     */
    public void setAssignedUser(Name assignedUser) {
        this.assignedUser = assignedUser;
    }

    /**
     * The user the instance is assigned to.
     *
     * @param assignedUser the String.
     */
    public void setAssignedUser(String assignedUser) {
        if ((this.assignedUser == null)) {
            if ((assignedUser == null)) {
                return;
            }
            this.assignedUser = new Name();
        }
        this.assignedUser.setValue(assignedUser);
    }

    /**
     * The group the instance is assigned to.
     *
     * @return the Name.
     */
    public Name getAssignedGroup() {
        return this.assignedGroup;
    }

    /**
     * The group the instance is assigned to.
     *
     * @param assignedGroup the Name.
     */
    public void setAssignedGroup(Name assignedGroup) {
        this.assignedGroup = assignedGroup;
    }

    /**
     * The group the instance is assigned to.
     *
     * @param assignedGroup the String.
     */
    public void setAssignedGroup(String assignedGroup) {
        if ((this.assignedGroup == null)) {
            if ((assignedGroup == null)) {
                return;
            }
            this.assignedGroup = new Name();
        }
        this.assignedGroup.setValue(assignedGroup);
    }

    /**
     * The role the instance is assigned to.
     *
     * @return the Name.
     */
    public Name getAssignedRole() {
        return this.assignedRole;
    }

    /**
     * The role the instance is assigned to.
     *
     * @param assignedRole the Name.
     */
    public void setAssignedRole(Name assignedRole) {
        this.assignedRole = assignedRole;
    }

    /**
     * The role the instance is assigned to.
     *
     * @param assignedRole the String.
     */
    public void setAssignedRole(String assignedRole) {
        if ((this.assignedRole == null)) {
            if ((assignedRole == null)) {
                return;
            }
            this.assignedRole = new Name();
        }
        this.assignedRole.setValue(assignedRole);
    }

    /**
     * Functional type of the workflow.
     *
     * @param functionalType the Code.
     */
    public void setFunctionalType(Code functionalType) {
        this.functionalType = functionalType;
    }

    /**
     * Functional type of the workflow.
     *
     * @return the Code.
     */
    public Code getFunctionalType() {
        return this.functionalType;
    }

    /**
     * Priority of the workflow.
     *
     * @param priority the Code.
     */
    public void setPriority(Code priority) {
        this.priority = priority;
    }

    /**
     * Priority of the workflow.
     *
     * @return the Code.
     */
    public Code getPriority() {
        return this.priority;
    }

    /**
     * Due date of the instance.
     *
     * @return the Date.
     */
    public Date getDueDate() {
        return this.dueDate;
    }

    /**
     * Due date of the instance.
     *
     * @param dueDate the Date.
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Due date of the instance.
     *
     * @param dueDate the java.util.Date.
     */
    public void setDueDate(java.util.Date dueDate) {
        if ((this.dueDate == null)) {
            if ((dueDate == null)) {
                return;
            }
            this.dueDate = new Date();
        }
        this.dueDate.setValue(dueDate);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(Instance.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(Instance.class).getAllProperties();
    }

    /**
     * Getter for the FunctionalTypeCodePath.
     *
     * @return the CodePath.
     */
    public static CodePath getFunctionalTypeCodePath() {
        return new CodePath(FUNCTIONALTYPE_CODEPATH);
    }

    /**
     * Getter for the PriorityCodePath.
     *
     * @return the CodePath.
     */
    public static CodePath getPriorityCodePath() {
        return new CodePath(PRIORITY_CODEPATH);
    }
}
