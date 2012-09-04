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
package org.nabucco.framework.base.facade.datatype.extension.schema.workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.WorkflowDefinitionExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * WorkflowConfigurationExtension<p/>Extension for configuring a NABUCCO workflow.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-03-15
 */
public class WorkflowConfigurationExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;" };

    public static final String WORKFLOW = "workflow";

    /** The configured workflow. */
    private WorkflowDefinitionExtension workflow;

    /** Constructs a new WorkflowConfigurationExtension instance. */
    public WorkflowConfigurationExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the WorkflowConfigurationExtension.
     */
    protected void cloneObject(WorkflowConfigurationExtension clone) {
        super.cloneObject(clone);
        if ((this.getWorkflow() != null)) {
            clone.setWorkflow(this.getWorkflow().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoExtensionComposite.class).getPropertyMap());
        propertyMap.put(WORKFLOW, PropertyDescriptorSupport.createDatatype(WORKFLOW, WorkflowDefinitionExtension.class,
                2, PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(WorkflowConfigurationExtension.getPropertyDescriptor(WORKFLOW),
                this.getWorkflow(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(WORKFLOW) && (property.getType() == WorkflowDefinitionExtension.class))) {
            this.setWorkflow(((WorkflowDefinitionExtension) property.getInstance()));
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
        final WorkflowConfigurationExtension other = ((WorkflowConfigurationExtension) obj);
        if ((this.workflow == null)) {
            if ((other.workflow != null))
                return false;
        } else if ((!this.workflow.equals(other.workflow)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.workflow == null) ? 0 : this.workflow.hashCode()));
        return result;
    }

    @Override
    public WorkflowConfigurationExtension cloneObject() {
        WorkflowConfigurationExtension clone = new WorkflowConfigurationExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The configured workflow.
     *
     * @param workflow the WorkflowDefinitionExtension.
     */
    public void setWorkflow(WorkflowDefinitionExtension workflow) {
        this.workflow = workflow;
    }

    /**
     * The configured workflow.
     *
     * @return the WorkflowDefinitionExtension.
     */
    public WorkflowDefinitionExtension getWorkflow() {
        return this.workflow;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(WorkflowConfigurationExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(WorkflowConfigurationExtension.class).getAllProperties();
    }
}
