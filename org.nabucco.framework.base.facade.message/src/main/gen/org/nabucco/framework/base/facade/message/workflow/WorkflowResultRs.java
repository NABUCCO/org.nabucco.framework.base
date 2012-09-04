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
package org.nabucco.framework.base.facade.message.workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.workflow.context.Context;
import org.nabucco.framework.base.facade.datatype.workflow.instance.Instance;
import org.nabucco.framework.base.facade.datatype.workflow.state.StateConstraint;
import org.nabucco.framework.base.facade.datatype.workflow.transition.TransitionParameter;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;

/**
 * WorkflowResultRs<p/>Result message after a workflow transition.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-03-18
 */
public class WorkflowResultRs extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m1,1;", "l0,n;u0,n;m1,1;", "m1,1;",
            "l0,255;u0,n;m1,1;", "m0,n;", "m0,1;", "m0,n;" };

    public static final String WORKFLOWNAME = "workflowName";

    public static final String INSTANCEID = "instanceId";

    public static final String INSTANCE = "instance";

    public static final String STATENAME = "stateName";

    public static final String NEXTTRANSITIONS = "nextTransitions";

    public static final String CONTEXT = "context";

    public static final String CONSTRAINTLIST = "constraintList";

    /** The name of the processed workflow. */
    private Name workflowName;

    /** The instance id of the processed workflow. */
    private Identifier instanceId;

    /** The workflow instance of the processed workflow. */
    private Instance instance;

    /** Name of the current state. */
    private Name stateName;

    /** The transitions available for the new state. */
    private NabuccoList<TransitionParameter> nextTransitions;

    /** Context for the workflow transition. */
    private Context context;

    /** Constraints for the resulting datatype. */
    private NabuccoList<StateConstraint> constraintList;

    /** Constructs a new WorkflowResultRs instance. */
    public WorkflowResultRs() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(WORKFLOWNAME,
                PropertyDescriptorSupport.createBasetype(WORKFLOWNAME, Name.class, 0, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(INSTANCEID, PropertyDescriptorSupport.createBasetype(INSTANCEID, Identifier.class, 1,
                PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(INSTANCE, PropertyDescriptorSupport.createDatatype(INSTANCE, Instance.class, 2,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(STATENAME,
                PropertyDescriptorSupport.createBasetype(STATENAME, Name.class, 3, PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(NEXTTRANSITIONS, PropertyDescriptorSupport.createCollection(NEXTTRANSITIONS,
                TransitionParameter.class, 4, PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(CONTEXT, PropertyDescriptorSupport.createDatatype(CONTEXT, Context.class, 5,
                PROPERTY_CONSTRAINTS[5], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(CONSTRAINTLIST, PropertyDescriptorSupport.createCollection(CONSTRAINTLIST,
                StateConstraint.class, 6, PROPERTY_CONSTRAINTS[6], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(WorkflowResultRs.getPropertyDescriptor(WORKFLOWNAME), this.workflowName));
        properties.add(super.createProperty(WorkflowResultRs.getPropertyDescriptor(INSTANCEID), this.instanceId));
        properties.add(super.createProperty(WorkflowResultRs.getPropertyDescriptor(INSTANCE), this.getInstance()));
        properties.add(super.createProperty(WorkflowResultRs.getPropertyDescriptor(STATENAME), this.stateName));
        properties.add(super.createProperty(WorkflowResultRs.getPropertyDescriptor(NEXTTRANSITIONS),
                this.nextTransitions));
        properties.add(super.createProperty(WorkflowResultRs.getPropertyDescriptor(CONTEXT), this.getContext()));
        properties
                .add(super.createProperty(WorkflowResultRs.getPropertyDescriptor(CONSTRAINTLIST), this.constraintList));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(WORKFLOWNAME) && (property.getType() == Name.class))) {
            this.setWorkflowName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(INSTANCEID) && (property.getType() == Identifier.class))) {
            this.setInstanceId(((Identifier) property.getInstance()));
            return true;
        } else if ((property.getName().equals(INSTANCE) && (property.getType() == Instance.class))) {
            this.setInstance(((Instance) property.getInstance()));
            return true;
        } else if ((property.getName().equals(STATENAME) && (property.getType() == Name.class))) {
            this.setStateName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NEXTTRANSITIONS) && (property.getType() == TransitionParameter.class))) {
            this.nextTransitions = ((NabuccoList<TransitionParameter>) property.getInstance());
            return true;
        } else if ((property.getName().equals(CONTEXT) && (property.getType() == Context.class))) {
            this.setContext(((Context) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CONSTRAINTLIST) && (property.getType() == StateConstraint.class))) {
            this.constraintList = ((NabuccoList<StateConstraint>) property.getInstance());
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
        final WorkflowResultRs other = ((WorkflowResultRs) obj);
        if ((this.workflowName == null)) {
            if ((other.workflowName != null))
                return false;
        } else if ((!this.workflowName.equals(other.workflowName)))
            return false;
        if ((this.instanceId == null)) {
            if ((other.instanceId != null))
                return false;
        } else if ((!this.instanceId.equals(other.instanceId)))
            return false;
        if ((this.instance == null)) {
            if ((other.instance != null))
                return false;
        } else if ((!this.instance.equals(other.instance)))
            return false;
        if ((this.stateName == null)) {
            if ((other.stateName != null))
                return false;
        } else if ((!this.stateName.equals(other.stateName)))
            return false;
        if ((this.nextTransitions == null)) {
            if ((other.nextTransitions != null))
                return false;
        } else if ((!this.nextTransitions.equals(other.nextTransitions)))
            return false;
        if ((this.context == null)) {
            if ((other.context != null))
                return false;
        } else if ((!this.context.equals(other.context)))
            return false;
        if ((this.constraintList == null)) {
            if ((other.constraintList != null))
                return false;
        } else if ((!this.constraintList.equals(other.constraintList)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.workflowName == null) ? 0 : this.workflowName.hashCode()));
        result = ((PRIME * result) + ((this.instanceId == null) ? 0 : this.instanceId.hashCode()));
        result = ((PRIME * result) + ((this.instance == null) ? 0 : this.instance.hashCode()));
        result = ((PRIME * result) + ((this.stateName == null) ? 0 : this.stateName.hashCode()));
        result = ((PRIME * result) + ((this.nextTransitions == null) ? 0 : this.nextTransitions.hashCode()));
        result = ((PRIME * result) + ((this.context == null) ? 0 : this.context.hashCode()));
        result = ((PRIME * result) + ((this.constraintList == null) ? 0 : this.constraintList.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * The name of the processed workflow.
     *
     * @return the Name.
     */
    public Name getWorkflowName() {
        return this.workflowName;
    }

    /**
     * The name of the processed workflow.
     *
     * @param workflowName the Name.
     */
    public void setWorkflowName(Name workflowName) {
        this.workflowName = workflowName;
    }

    /**
     * The instance id of the processed workflow.
     *
     * @return the Identifier.
     */
    public Identifier getInstanceId() {
        return this.instanceId;
    }

    /**
     * The instance id of the processed workflow.
     *
     * @param instanceId the Identifier.
     */
    public void setInstanceId(Identifier instanceId) {
        this.instanceId = instanceId;
    }

    /**
     * The workflow instance of the processed workflow.
     *
     * @return the Instance.
     */
    public Instance getInstance() {
        return this.instance;
    }

    /**
     * The workflow instance of the processed workflow.
     *
     * @param instance the Instance.
     */
    public void setInstance(Instance instance) {
        this.instance = instance;
    }

    /**
     * Name of the current state.
     *
     * @return the Name.
     */
    public Name getStateName() {
        return this.stateName;
    }

    /**
     * Name of the current state.
     *
     * @param stateName the Name.
     */
    public void setStateName(Name stateName) {
        this.stateName = stateName;
    }

    /**
     * The transitions available for the new state.
     *
     * @return the NabuccoList<TransitionParameter>.
     */
    public NabuccoList<TransitionParameter> getNextTransitions() {
        if ((this.nextTransitions == null)) {
            this.nextTransitions = new NabuccoListImpl<TransitionParameter>(NabuccoCollectionState.INITIALIZED);
        }
        return this.nextTransitions;
    }

    /**
     * Context for the workflow transition.
     *
     * @return the Context.
     */
    public Context getContext() {
        return this.context;
    }

    /**
     * Context for the workflow transition.
     *
     * @param context the Context.
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * Constraints for the resulting datatype.
     *
     * @return the NabuccoList<StateConstraint>.
     */
    public NabuccoList<StateConstraint> getConstraintList() {
        if ((this.constraintList == null)) {
            this.constraintList = new NabuccoListImpl<StateConstraint>(NabuccoCollectionState.INITIALIZED);
        }
        return this.constraintList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(WorkflowResultRs.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(WorkflowResultRs.class).getAllProperties();
    }
}
