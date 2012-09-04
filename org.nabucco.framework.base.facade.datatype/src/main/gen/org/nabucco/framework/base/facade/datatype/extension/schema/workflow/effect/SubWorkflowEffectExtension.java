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
package org.nabucco.framework.base.facade.datatype.extension.schema.workflow.effect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.WorkflowEffectExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * SubWorkflowEffectExtension<p/>Extension for configuring a sub workflow effect.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-11-10
 */
public class SubWorkflowEffectExtension extends WorkflowEffectExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m0,1;", "m0,1;", "m0,1;" };

    public static final String DEFINITIONNAME = "definitionName";

    public static final String SUMMARY = "summary";

    public static final String ASSIGNEDUSER = "assignedUser";

    public static final String ASSIGNEDGROUP = "assignedGroup";

    public static final String ASSIGNEDROLE = "assignedRole";

    /** The name of the workflow definition. */
    private StringProperty definitionName;

    /** The summary of the new workflow instance. */
    private StringProperty summary;

    /** The assigned user of the new instance. */
    private StringProperty assignedUser;

    /** The assigned group of the new instance. */
    private StringProperty assignedGroup;

    /** The assigned role of the new instance. */
    private StringProperty assignedRole;

    /** Constructs a new SubWorkflowEffectExtension instance. */
    public SubWorkflowEffectExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the SubWorkflowEffectExtension.
     */
    protected void cloneObject(SubWorkflowEffectExtension clone) {
        super.cloneObject(clone);
        if ((this.getDefinitionName() != null)) {
            clone.setDefinitionName(this.getDefinitionName().cloneObject());
        }
        if ((this.getSummary() != null)) {
            clone.setSummary(this.getSummary().cloneObject());
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
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(WorkflowEffectExtension.class).getPropertyMap());
        propertyMap.put(DEFINITIONNAME, PropertyDescriptorSupport.createDatatype(DEFINITIONNAME, StringProperty.class,
                6, PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(SUMMARY, PropertyDescriptorSupport.createDatatype(SUMMARY, StringProperty.class, 7,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ASSIGNEDUSER, PropertyDescriptorSupport.createDatatype(ASSIGNEDUSER, StringProperty.class, 8,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ASSIGNEDGROUP, PropertyDescriptorSupport.createDatatype(ASSIGNEDGROUP, StringProperty.class, 9,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ASSIGNEDROLE, PropertyDescriptorSupport.createDatatype(ASSIGNEDROLE, StringProperty.class, 10,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(SubWorkflowEffectExtension.getPropertyDescriptor(DEFINITIONNAME),
                this.getDefinitionName(), null));
        properties.add(super.createProperty(SubWorkflowEffectExtension.getPropertyDescriptor(SUMMARY),
                this.getSummary(), null));
        properties.add(super.createProperty(SubWorkflowEffectExtension.getPropertyDescriptor(ASSIGNEDUSER),
                this.getAssignedUser(), null));
        properties.add(super.createProperty(SubWorkflowEffectExtension.getPropertyDescriptor(ASSIGNEDGROUP),
                this.getAssignedGroup(), null));
        properties.add(super.createProperty(SubWorkflowEffectExtension.getPropertyDescriptor(ASSIGNEDROLE),
                this.getAssignedRole(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(DEFINITIONNAME) && (property.getType() == StringProperty.class))) {
            this.setDefinitionName(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SUMMARY) && (property.getType() == StringProperty.class))) {
            this.setSummary(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ASSIGNEDUSER) && (property.getType() == StringProperty.class))) {
            this.setAssignedUser(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ASSIGNEDGROUP) && (property.getType() == StringProperty.class))) {
            this.setAssignedGroup(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ASSIGNEDROLE) && (property.getType() == StringProperty.class))) {
            this.setAssignedRole(((StringProperty) property.getInstance()));
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
        final SubWorkflowEffectExtension other = ((SubWorkflowEffectExtension) obj);
        if ((this.definitionName == null)) {
            if ((other.definitionName != null))
                return false;
        } else if ((!this.definitionName.equals(other.definitionName)))
            return false;
        if ((this.summary == null)) {
            if ((other.summary != null))
                return false;
        } else if ((!this.summary.equals(other.summary)))
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
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.definitionName == null) ? 0 : this.definitionName.hashCode()));
        result = ((PRIME * result) + ((this.summary == null) ? 0 : this.summary.hashCode()));
        result = ((PRIME * result) + ((this.assignedUser == null) ? 0 : this.assignedUser.hashCode()));
        result = ((PRIME * result) + ((this.assignedGroup == null) ? 0 : this.assignedGroup.hashCode()));
        result = ((PRIME * result) + ((this.assignedRole == null) ? 0 : this.assignedRole.hashCode()));
        return result;
    }

    @Override
    public SubWorkflowEffectExtension cloneObject() {
        SubWorkflowEffectExtension clone = new SubWorkflowEffectExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The name of the workflow definition.
     *
     * @param definitionName the StringProperty.
     */
    public void setDefinitionName(StringProperty definitionName) {
        this.definitionName = definitionName;
    }

    /**
     * The name of the workflow definition.
     *
     * @return the StringProperty.
     */
    public StringProperty getDefinitionName() {
        return this.definitionName;
    }

    /**
     * The summary of the new workflow instance.
     *
     * @param summary the StringProperty.
     */
    public void setSummary(StringProperty summary) {
        this.summary = summary;
    }

    /**
     * The summary of the new workflow instance.
     *
     * @return the StringProperty.
     */
    public StringProperty getSummary() {
        return this.summary;
    }

    /**
     * The assigned user of the new instance.
     *
     * @param assignedUser the StringProperty.
     */
    public void setAssignedUser(StringProperty assignedUser) {
        this.assignedUser = assignedUser;
    }

    /**
     * The assigned user of the new instance.
     *
     * @return the StringProperty.
     */
    public StringProperty getAssignedUser() {
        return this.assignedUser;
    }

    /**
     * The assigned group of the new instance.
     *
     * @param assignedGroup the StringProperty.
     */
    public void setAssignedGroup(StringProperty assignedGroup) {
        this.assignedGroup = assignedGroup;
    }

    /**
     * The assigned group of the new instance.
     *
     * @return the StringProperty.
     */
    public StringProperty getAssignedGroup() {
        return this.assignedGroup;
    }

    /**
     * The assigned role of the new instance.
     *
     * @param assignedRole the StringProperty.
     */
    public void setAssignedRole(StringProperty assignedRole) {
        this.assignedRole = assignedRole;
    }

    /**
     * The assigned role of the new instance.
     *
     * @return the StringProperty.
     */
    public StringProperty getAssignedRole() {
        return this.assignedRole;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(SubWorkflowEffectExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(SubWorkflowEffectExtension.class).getAllProperties();
    }
}
