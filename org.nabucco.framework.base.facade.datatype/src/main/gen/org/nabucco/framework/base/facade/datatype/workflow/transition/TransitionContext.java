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
package org.nabucco.framework.base.facade.datatype.workflow.transition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeSupport;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.documentation.Comment;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.workflow.Signal;
import org.nabucco.framework.base.facade.datatype.workflow.context.Context;
import org.nabucco.framework.base.facade.datatype.workflow.instance.Instance;
import org.nabucco.framework.base.facade.datatype.workflow.transition.TransitionParameter;

/**
 * TransitionContext<p/>The context of a workflow transition.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-03-03
 */
public class TransitionContext extends DatatypeSupport implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m1,1;", "m1,1;", "m0,n;", "l0,n;u0,n;m1,1;",
            "l0,255;u0,n;m1,1;", "m0,1;", "l0,100000;u0,n;m0,1;", "m0,n;", "m0,1;" };

    public static final String NAME = "name";

    public static final String INSTANCE = "instance";

    public static final String SUBINSTANCES = "subInstances";

    public static final String INSTANCEID = "instanceId";

    public static final String STATENAME = "stateName";

    public static final String SIGNAL = "signal";

    public static final String COMMENT = "comment";

    public static final String NEXTTRANSITIONS = "nextTransitions";

    public static final String WORKFLOWCONTEXT = "workflowContext";

    /** The name of the workflow. */
    private Name name;

    /** The workflow instance. */
    private Instance instance;

    /** Sub-Workflow instances. */
    private NabuccoList<Instance> subInstances;

    /** The id of the workflow instance. */
    private Identifier instanceId;

    /** Name of the current state. */
    private Name stateName;

    /** The signal triggering the transition. */
    private Signal signal;

    /** Comment of the transition. */
    private Comment comment;

    /** The list of next available transitions. */
    private NabuccoList<TransitionParameter> nextTransitions;

    /** The context of the transition. */
    private Context workflowContext;

    /** Constructs a new TransitionContext instance. */
    public TransitionContext() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TransitionContext.
     */
    protected void cloneObject(TransitionContext clone) {
        super.cloneObject(clone);
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getInstance() != null)) {
            clone.setInstance(this.getInstance().cloneObject());
        }
        if ((this.subInstances != null)) {
            clone.subInstances = this.subInstances.cloneCollection();
        }
        if ((this.getInstanceId() != null)) {
            clone.setInstanceId(this.getInstanceId().cloneObject());
        }
        if ((this.getStateName() != null)) {
            clone.setStateName(this.getStateName().cloneObject());
        }
        if ((this.getSignal() != null)) {
            clone.setSignal(this.getSignal().cloneObject());
        }
        if ((this.getComment() != null)) {
            clone.setComment(this.getComment().cloneObject());
        }
        if ((this.nextTransitions != null)) {
            clone.nextTransitions = this.nextTransitions.cloneCollection();
        }
        if ((this.getWorkflowContext() != null)) {
            clone.setWorkflowContext(this.getWorkflowContext().cloneObject());
        }
    }

    /**
     * Getter for the SubInstancesJPA.
     *
     * @return the List<Instance>.
     */
    List<Instance> getSubInstancesJPA() {
        if ((this.subInstances == null)) {
            this.subInstances = new NabuccoListImpl<Instance>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<Instance>) this.subInstances).getDelegate();
    }

    /**
     * Setter for the SubInstancesJPA.
     *
     * @param subInstances the List<Instance>.
     */
    void setSubInstancesJPA(List<Instance> subInstances) {
        if ((this.subInstances == null)) {
            this.subInstances = new NabuccoListImpl<Instance>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<Instance>) this.subInstances).setDelegate(subInstances);
    }

    /**
     * Getter for the NextTransitionsJPA.
     *
     * @return the List<TransitionParameter>.
     */
    List<TransitionParameter> getNextTransitionsJPA() {
        if ((this.nextTransitions == null)) {
            this.nextTransitions = new NabuccoListImpl<TransitionParameter>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<TransitionParameter>) this.nextTransitions).getDelegate();
    }

    /**
     * Setter for the NextTransitionsJPA.
     *
     * @param nextTransitions the List<TransitionParameter>.
     */
    void setNextTransitionsJPA(List<TransitionParameter> nextTransitions) {
        if ((this.nextTransitions == null)) {
            this.nextTransitions = new NabuccoListImpl<TransitionParameter>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<TransitionParameter>) this.nextTransitions).setDelegate(nextTransitions);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(NAME,
                PropertyDescriptorSupport.createBasetype(NAME, Name.class, 0, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(INSTANCE, PropertyDescriptorSupport.createDatatype(INSTANCE, Instance.class, 1,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(SUBINSTANCES, PropertyDescriptorSupport.createCollection(SUBINSTANCES, Instance.class, 2,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(INSTANCEID, PropertyDescriptorSupport.createBasetype(INSTANCEID, Identifier.class, 3,
                PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(STATENAME,
                PropertyDescriptorSupport.createBasetype(STATENAME, Name.class, 4, PROPERTY_CONSTRAINTS[4], false));
        propertyMap.put(SIGNAL, PropertyDescriptorSupport.createDatatype(SIGNAL, Signal.class, 5,
                PROPERTY_CONSTRAINTS[5], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(COMMENT,
                PropertyDescriptorSupport.createBasetype(COMMENT, Comment.class, 6, PROPERTY_CONSTRAINTS[6], false));
        propertyMap.put(NEXTTRANSITIONS, PropertyDescriptorSupport.createCollection(NEXTTRANSITIONS,
                TransitionParameter.class, 7, PROPERTY_CONSTRAINTS[7], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(WORKFLOWCONTEXT, PropertyDescriptorSupport.createDatatype(WORKFLOWCONTEXT, Context.class, 8,
                PROPERTY_CONSTRAINTS[8], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(TransitionContext.getPropertyDescriptor(NAME), this.name, null));
        properties
                .add(super.createProperty(TransitionContext.getPropertyDescriptor(INSTANCE), this.getInstance(), null));
        properties.add(super.createProperty(TransitionContext.getPropertyDescriptor(SUBINSTANCES), this.subInstances,
                null));
        properties
                .add(super.createProperty(TransitionContext.getPropertyDescriptor(INSTANCEID), this.instanceId, null));
        properties.add(super.createProperty(TransitionContext.getPropertyDescriptor(STATENAME), this.stateName, null));
        properties.add(super.createProperty(TransitionContext.getPropertyDescriptor(SIGNAL), this.getSignal(), null));
        properties.add(super.createProperty(TransitionContext.getPropertyDescriptor(COMMENT), this.comment, null));
        properties.add(super.createProperty(TransitionContext.getPropertyDescriptor(NEXTTRANSITIONS),
                this.nextTransitions, null));
        properties.add(super.createProperty(TransitionContext.getPropertyDescriptor(WORKFLOWCONTEXT),
                this.getWorkflowContext(), null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(NAME) && (property.getType() == Name.class))) {
            this.setName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(INSTANCE) && (property.getType() == Instance.class))) {
            this.setInstance(((Instance) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SUBINSTANCES) && (property.getType() == Instance.class))) {
            this.subInstances = ((NabuccoList<Instance>) property.getInstance());
            return true;
        } else if ((property.getName().equals(INSTANCEID) && (property.getType() == Identifier.class))) {
            this.setInstanceId(((Identifier) property.getInstance()));
            return true;
        } else if ((property.getName().equals(STATENAME) && (property.getType() == Name.class))) {
            this.setStateName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SIGNAL) && (property.getType() == Signal.class))) {
            this.setSignal(((Signal) property.getInstance()));
            return true;
        } else if ((property.getName().equals(COMMENT) && (property.getType() == Comment.class))) {
            this.setComment(((Comment) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NEXTTRANSITIONS) && (property.getType() == TransitionParameter.class))) {
            this.nextTransitions = ((NabuccoList<TransitionParameter>) property.getInstance());
            return true;
        } else if ((property.getName().equals(WORKFLOWCONTEXT) && (property.getType() == Context.class))) {
            this.setWorkflowContext(((Context) property.getInstance()));
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
        final TransitionContext other = ((TransitionContext) obj);
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        if ((this.instance == null)) {
            if ((other.instance != null))
                return false;
        } else if ((!this.instance.equals(other.instance)))
            return false;
        if ((this.instanceId == null)) {
            if ((other.instanceId != null))
                return false;
        } else if ((!this.instanceId.equals(other.instanceId)))
            return false;
        if ((this.stateName == null)) {
            if ((other.stateName != null))
                return false;
        } else if ((!this.stateName.equals(other.stateName)))
            return false;
        if ((this.signal == null)) {
            if ((other.signal != null))
                return false;
        } else if ((!this.signal.equals(other.signal)))
            return false;
        if ((this.comment == null)) {
            if ((other.comment != null))
                return false;
        } else if ((!this.comment.equals(other.comment)))
            return false;
        if ((this.workflowContext == null)) {
            if ((other.workflowContext != null))
                return false;
        } else if ((!this.workflowContext.equals(other.workflowContext)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.instance == null) ? 0 : this.instance.hashCode()));
        result = ((PRIME * result) + ((this.instanceId == null) ? 0 : this.instanceId.hashCode()));
        result = ((PRIME * result) + ((this.stateName == null) ? 0 : this.stateName.hashCode()));
        result = ((PRIME * result) + ((this.signal == null) ? 0 : this.signal.hashCode()));
        result = ((PRIME * result) + ((this.comment == null) ? 0 : this.comment.hashCode()));
        result = ((PRIME * result) + ((this.workflowContext == null) ? 0 : this.workflowContext.hashCode()));
        return result;
    }

    @Override
    public TransitionContext cloneObject() {
        TransitionContext clone = new TransitionContext();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The name of the workflow.
     *
     * @return the Name.
     */
    public Name getName() {
        return this.name;
    }

    /**
     * The name of the workflow.
     *
     * @param name the Name.
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * The name of the workflow.
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
     * The workflow instance.
     *
     * @param instance the Instance.
     */
    public void setInstance(Instance instance) {
        this.instance = instance;
    }

    /**
     * The workflow instance.
     *
     * @return the Instance.
     */
    public Instance getInstance() {
        return this.instance;
    }

    /**
     * Sub-Workflow instances.
     *
     * @return the NabuccoList<Instance>.
     */
    public NabuccoList<Instance> getSubInstances() {
        if ((this.subInstances == null)) {
            this.subInstances = new NabuccoListImpl<Instance>(NabuccoCollectionState.INITIALIZED);
        }
        return this.subInstances;
    }

    /**
     * The id of the workflow instance.
     *
     * @return the Identifier.
     */
    public Identifier getInstanceId() {
        return this.instanceId;
    }

    /**
     * The id of the workflow instance.
     *
     * @param instanceId the Identifier.
     */
    public void setInstanceId(Identifier instanceId) {
        this.instanceId = instanceId;
    }

    /**
     * The id of the workflow instance.
     *
     * @param instanceId the Long.
     */
    public void setInstanceId(Long instanceId) {
        if ((this.instanceId == null)) {
            if ((instanceId == null)) {
                return;
            }
            this.instanceId = new Identifier();
        }
        this.instanceId.setValue(instanceId);
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
     * Name of the current state.
     *
     * @param stateName the String.
     */
    public void setStateName(String stateName) {
        if ((this.stateName == null)) {
            if ((stateName == null)) {
                return;
            }
            this.stateName = new Name();
        }
        this.stateName.setValue(stateName);
    }

    /**
     * The signal triggering the transition.
     *
     * @param signal the Signal.
     */
    public void setSignal(Signal signal) {
        this.signal = signal;
    }

    /**
     * The signal triggering the transition.
     *
     * @return the Signal.
     */
    public Signal getSignal() {
        return this.signal;
    }

    /**
     * Comment of the transition.
     *
     * @return the Comment.
     */
    public Comment getComment() {
        return this.comment;
    }

    /**
     * Comment of the transition.
     *
     * @param comment the Comment.
     */
    public void setComment(Comment comment) {
        this.comment = comment;
    }

    /**
     * Comment of the transition.
     *
     * @param comment the String.
     */
    public void setComment(String comment) {
        if ((this.comment == null)) {
            if ((comment == null)) {
                return;
            }
            this.comment = new Comment();
        }
        this.comment.setValue(comment);
    }

    /**
     * The list of next available transitions.
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
     * The context of the transition.
     *
     * @param workflowContext the Context.
     */
    public void setWorkflowContext(Context workflowContext) {
        this.workflowContext = workflowContext;
    }

    /**
     * The context of the transition.
     *
     * @return the Context.
     */
    public Context getWorkflowContext() {
        return this.workflowContext;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TransitionContext.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TransitionContext.class).getAllProperties();
    }
}
