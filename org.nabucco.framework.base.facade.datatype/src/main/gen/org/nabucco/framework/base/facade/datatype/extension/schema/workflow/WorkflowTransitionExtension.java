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
package org.nabucco.framework.base.facade.datatype.extension.schema.workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.WorkflowConditionExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.WorkflowEffectExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.WorkflowStateExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.WorkflowTriggerExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * WorkflowTransitionExtension<p/>Extension for configuring a workflow transition.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-03-15
 */
public class WorkflowTransitionExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m1,1;",
            "m1,1;", "m0,1;", "m0,n;" };

    public static final String NAME = "name";

    public static final String OWNER = "owner";

    public static final String COMMENTTYPE = "commentType";

    public static final String DESCRIPTION = "description";

    public static final String SOURCE = "source";

    public static final String TARGET = "target";

    public static final String TRIGGER = "trigger";

    public static final String CONDITION = "condition";

    public static final String EFFECTLIST = "effectList";

    /** The name of the transition. */
    private StringProperty name;

    /** The owner of the transition. */
    private StringProperty owner;

    /** The comment type of the transition. */
    private EnumerationProperty commentType;

    /** The description of the transition. */
    private StringProperty description;

    /** The source state. */
    private WorkflowStateExtension source;

    /** The target state. */
    private WorkflowStateExtension target;

    /** The configured trigger. */
    private WorkflowTriggerExtension trigger;

    /** The configured condition. */
    private WorkflowConditionExtension condition;

    /** The configured effects. */
    private NabuccoList<WorkflowEffectExtension> effectList;

    /** Constructs a new WorkflowTransitionExtension instance. */
    public WorkflowTransitionExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the WorkflowTransitionExtension.
     */
    protected void cloneObject(WorkflowTransitionExtension clone) {
        super.cloneObject(clone);
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getCommentType() != null)) {
            clone.setCommentType(this.getCommentType().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        if ((this.getSource() != null)) {
            clone.setSource(this.getSource().cloneObject());
        }
        if ((this.getTarget() != null)) {
            clone.setTarget(this.getTarget().cloneObject());
        }
        if ((this.getTrigger() != null)) {
            clone.setTrigger(this.getTrigger().cloneObject());
        }
        if ((this.getCondition() != null)) {
            clone.setCondition(this.getCondition().cloneObject());
        }
        if ((this.effectList != null)) {
            clone.effectList = this.effectList.cloneCollection();
        }
    }

    /**
     * Getter for the EffectListJPA.
     *
     * @return the List<WorkflowEffectExtension>.
     */
    List<WorkflowEffectExtension> getEffectListJPA() {
        if ((this.effectList == null)) {
            this.effectList = new NabuccoListImpl<WorkflowEffectExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<WorkflowEffectExtension>) this.effectList).getDelegate();
    }

    /**
     * Setter for the EffectListJPA.
     *
     * @param effectList the List<WorkflowEffectExtension>.
     */
    void setEffectListJPA(List<WorkflowEffectExtension> effectList) {
        if ((this.effectList == null)) {
            this.effectList = new NabuccoListImpl<WorkflowEffectExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<WorkflowEffectExtension>) this.effectList).setDelegate(effectList);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoExtensionComposite.class).getPropertyMap());
        propertyMap.put(NAME, PropertyDescriptorSupport.createDatatype(NAME, StringProperty.class, 2,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(OWNER, PropertyDescriptorSupport.createDatatype(OWNER, StringProperty.class, 3,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(COMMENTTYPE, PropertyDescriptorSupport.createDatatype(COMMENTTYPE, EnumerationProperty.class,
                4, PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createDatatype(DESCRIPTION, StringProperty.class, 5,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(SOURCE, PropertyDescriptorSupport.createDatatype(SOURCE, WorkflowStateExtension.class, 6,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(TARGET, PropertyDescriptorSupport.createDatatype(TARGET, WorkflowStateExtension.class, 7,
                PROPERTY_CONSTRAINTS[5], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(TRIGGER, PropertyDescriptorSupport.createDatatype(TRIGGER, WorkflowTriggerExtension.class, 8,
                PROPERTY_CONSTRAINTS[6], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(CONDITION, PropertyDescriptorSupport.createDatatype(CONDITION,
                WorkflowConditionExtension.class, 9, PROPERTY_CONSTRAINTS[7], false,
                PropertyAssociationType.COMPOSITION));
        propertyMap
                .put(EFFECTLIST, PropertyDescriptorSupport.createCollection(EFFECTLIST, WorkflowEffectExtension.class,
                        10, PROPERTY_CONSTRAINTS[8], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(WorkflowTransitionExtension.getPropertyDescriptor(NAME), this.getName(),
                null));
        properties.add(super.createProperty(WorkflowTransitionExtension.getPropertyDescriptor(OWNER), this.getOwner(),
                null));
        properties.add(super.createProperty(WorkflowTransitionExtension.getPropertyDescriptor(COMMENTTYPE),
                this.getCommentType(), null));
        properties.add(super.createProperty(WorkflowTransitionExtension.getPropertyDescriptor(DESCRIPTION),
                this.getDescription(), null));
        properties.add(super.createProperty(WorkflowTransitionExtension.getPropertyDescriptor(SOURCE),
                this.getSource(), null));
        properties.add(super.createProperty(WorkflowTransitionExtension.getPropertyDescriptor(TARGET),
                this.getTarget(), null));
        properties.add(super.createProperty(WorkflowTransitionExtension.getPropertyDescriptor(TRIGGER),
                this.getTrigger(), null));
        properties.add(super.createProperty(WorkflowTransitionExtension.getPropertyDescriptor(CONDITION),
                this.getCondition(), null));
        properties.add(super.createProperty(WorkflowTransitionExtension.getPropertyDescriptor(EFFECTLIST),
                this.effectList, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(NAME) && (property.getType() == StringProperty.class))) {
            this.setName(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(OWNER) && (property.getType() == StringProperty.class))) {
            this.setOwner(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(COMMENTTYPE) && (property.getType() == EnumerationProperty.class))) {
            this.setCommentType(((EnumerationProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == StringProperty.class))) {
            this.setDescription(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SOURCE) && (property.getType() == WorkflowStateExtension.class))) {
            this.setSource(((WorkflowStateExtension) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TARGET) && (property.getType() == WorkflowStateExtension.class))) {
            this.setTarget(((WorkflowStateExtension) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TRIGGER) && (property.getType() == WorkflowTriggerExtension.class))) {
            this.setTrigger(((WorkflowTriggerExtension) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CONDITION) && (property.getType() == WorkflowConditionExtension.class))) {
            this.setCondition(((WorkflowConditionExtension) property.getInstance()));
            return true;
        } else if ((property.getName().equals(EFFECTLIST) && (property.getType() == WorkflowEffectExtension.class))) {
            this.effectList = ((NabuccoList<WorkflowEffectExtension>) property.getInstance());
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
        final WorkflowTransitionExtension other = ((WorkflowTransitionExtension) obj);
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
        if ((this.commentType == null)) {
            if ((other.commentType != null))
                return false;
        } else if ((!this.commentType.equals(other.commentType)))
            return false;
        if ((this.description == null)) {
            if ((other.description != null))
                return false;
        } else if ((!this.description.equals(other.description)))
            return false;
        if ((this.source == null)) {
            if ((other.source != null))
                return false;
        } else if ((!this.source.equals(other.source)))
            return false;
        if ((this.target == null)) {
            if ((other.target != null))
                return false;
        } else if ((!this.target.equals(other.target)))
            return false;
        if ((this.trigger == null)) {
            if ((other.trigger != null))
                return false;
        } else if ((!this.trigger.equals(other.trigger)))
            return false;
        if ((this.condition == null)) {
            if ((other.condition != null))
                return false;
        } else if ((!this.condition.equals(other.condition)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.commentType == null) ? 0 : this.commentType.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.source == null) ? 0 : this.source.hashCode()));
        result = ((PRIME * result) + ((this.target == null) ? 0 : this.target.hashCode()));
        result = ((PRIME * result) + ((this.trigger == null) ? 0 : this.trigger.hashCode()));
        result = ((PRIME * result) + ((this.condition == null) ? 0 : this.condition.hashCode()));
        return result;
    }

    @Override
    public WorkflowTransitionExtension cloneObject() {
        WorkflowTransitionExtension clone = new WorkflowTransitionExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The name of the transition.
     *
     * @param name the StringProperty.
     */
    public void setName(StringProperty name) {
        this.name = name;
    }

    /**
     * The name of the transition.
     *
     * @return the StringProperty.
     */
    public StringProperty getName() {
        return this.name;
    }

    /**
     * The owner of the transition.
     *
     * @param owner the StringProperty.
     */
    public void setOwner(StringProperty owner) {
        this.owner = owner;
    }

    /**
     * The owner of the transition.
     *
     * @return the StringProperty.
     */
    public StringProperty getOwner() {
        return this.owner;
    }

    /**
     * The comment type of the transition.
     *
     * @param commentType the EnumerationProperty.
     */
    public void setCommentType(EnumerationProperty commentType) {
        this.commentType = commentType;
    }

    /**
     * The comment type of the transition.
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getCommentType() {
        return this.commentType;
    }

    /**
     * The description of the transition.
     *
     * @param description the StringProperty.
     */
    public void setDescription(StringProperty description) {
        this.description = description;
    }

    /**
     * The description of the transition.
     *
     * @return the StringProperty.
     */
    public StringProperty getDescription() {
        return this.description;
    }

    /**
     * The source state.
     *
     * @param source the WorkflowStateExtension.
     */
    public void setSource(WorkflowStateExtension source) {
        this.source = source;
    }

    /**
     * The source state.
     *
     * @return the WorkflowStateExtension.
     */
    public WorkflowStateExtension getSource() {
        return this.source;
    }

    /**
     * The target state.
     *
     * @param target the WorkflowStateExtension.
     */
    public void setTarget(WorkflowStateExtension target) {
        this.target = target;
    }

    /**
     * The target state.
     *
     * @return the WorkflowStateExtension.
     */
    public WorkflowStateExtension getTarget() {
        return this.target;
    }

    /**
     * The configured trigger.
     *
     * @param trigger the WorkflowTriggerExtension.
     */
    public void setTrigger(WorkflowTriggerExtension trigger) {
        this.trigger = trigger;
    }

    /**
     * The configured trigger.
     *
     * @return the WorkflowTriggerExtension.
     */
    public WorkflowTriggerExtension getTrigger() {
        return this.trigger;
    }

    /**
     * The configured condition.
     *
     * @param condition the WorkflowConditionExtension.
     */
    public void setCondition(WorkflowConditionExtension condition) {
        this.condition = condition;
    }

    /**
     * The configured condition.
     *
     * @return the WorkflowConditionExtension.
     */
    public WorkflowConditionExtension getCondition() {
        return this.condition;
    }

    /**
     * The configured effects.
     *
     * @return the NabuccoList<WorkflowEffectExtension>.
     */
    public NabuccoList<WorkflowEffectExtension> getEffectList() {
        if ((this.effectList == null)) {
            this.effectList = new NabuccoListImpl<WorkflowEffectExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.effectList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(WorkflowTransitionExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(WorkflowTransitionExtension.class).getAllProperties();
    }
}
