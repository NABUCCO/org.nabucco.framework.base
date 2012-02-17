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
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.WorkflowSignalExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.WorkflowStateExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * WorkflowDefinitionExtension<p/>Extension for configuring a NABUCCO workflow.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-03-15
 */
public class WorkflowDefinitionExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m0,n;", "m0,n;",
            "m0,n;" };

    public static final String NAME = "name";

    public static final String OWNER = "owner";

    public static final String DESCRIPTION = "description";

    public static final String TYPE = "type";

    public static final String STATELIST = "stateList";

    public static final String TRANSITIONLIST = "transitionList";

    public static final String SIGNALLIST = "signalList";

    /** The name of the workflow. */
    private StringProperty name;

    /** The owner of the workflow. */
    private StringProperty owner;

    /** The description of the workflow. */
    private StringProperty description;

    /** The type of the workflow. */
    private EnumerationProperty type;

    /** The list of workflow states. */
    private NabuccoList<WorkflowStateExtension> stateList;

    /** The list of workflow transitions. */
    private NabuccoList<WorkflowTransitionExtension> transitionList;

    /** The list of workflow signals. */
    private NabuccoList<WorkflowSignalExtension> signalList;

    /** Constructs a new WorkflowDefinitionExtension instance. */
    public WorkflowDefinitionExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the WorkflowDefinitionExtension.
     */
    protected void cloneObject(WorkflowDefinitionExtension clone) {
        super.cloneObject(clone);
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        if ((this.getType() != null)) {
            clone.setType(this.getType().cloneObject());
        }
        if ((this.stateList != null)) {
            clone.stateList = this.stateList.cloneCollection();
        }
        if ((this.transitionList != null)) {
            clone.transitionList = this.transitionList.cloneCollection();
        }
        if ((this.signalList != null)) {
            clone.signalList = this.signalList.cloneCollection();
        }
    }

    /**
     * Getter for the StateListJPA.
     *
     * @return the List<WorkflowStateExtension>.
     */
    List<WorkflowStateExtension> getStateListJPA() {
        if ((this.stateList == null)) {
            this.stateList = new NabuccoListImpl<WorkflowStateExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<WorkflowStateExtension>) this.stateList).getDelegate();
    }

    /**
     * Setter for the StateListJPA.
     *
     * @param stateList the List<WorkflowStateExtension>.
     */
    void setStateListJPA(List<WorkflowStateExtension> stateList) {
        if ((this.stateList == null)) {
            this.stateList = new NabuccoListImpl<WorkflowStateExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<WorkflowStateExtension>) this.stateList).setDelegate(stateList);
    }

    /**
     * Getter for the TransitionListJPA.
     *
     * @return the List<WorkflowTransitionExtension>.
     */
    List<WorkflowTransitionExtension> getTransitionListJPA() {
        if ((this.transitionList == null)) {
            this.transitionList = new NabuccoListImpl<WorkflowTransitionExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<WorkflowTransitionExtension>) this.transitionList).getDelegate();
    }

    /**
     * Setter for the TransitionListJPA.
     *
     * @param transitionList the List<WorkflowTransitionExtension>.
     */
    void setTransitionListJPA(List<WorkflowTransitionExtension> transitionList) {
        if ((this.transitionList == null)) {
            this.transitionList = new NabuccoListImpl<WorkflowTransitionExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<WorkflowTransitionExtension>) this.transitionList).setDelegate(transitionList);
    }

    /**
     * Getter for the SignalListJPA.
     *
     * @return the List<WorkflowSignalExtension>.
     */
    List<WorkflowSignalExtension> getSignalListJPA() {
        if ((this.signalList == null)) {
            this.signalList = new NabuccoListImpl<WorkflowSignalExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<WorkflowSignalExtension>) this.signalList).getDelegate();
    }

    /**
     * Setter for the SignalListJPA.
     *
     * @param signalList the List<WorkflowSignalExtension>.
     */
    void setSignalListJPA(List<WorkflowSignalExtension> signalList) {
        if ((this.signalList == null)) {
            this.signalList = new NabuccoListImpl<WorkflowSignalExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<WorkflowSignalExtension>) this.signalList).setDelegate(signalList);
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
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createDatatype(DESCRIPTION, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(TYPE, PropertyDescriptorSupport.createDatatype(TYPE, EnumerationProperty.class, 5,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(STATELIST, PropertyDescriptorSupport.createCollection(STATELIST, WorkflowStateExtension.class,
                6, PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(TRANSITIONLIST, PropertyDescriptorSupport.createCollection(TRANSITIONLIST,
                WorkflowTransitionExtension.class, 7, PROPERTY_CONSTRAINTS[5], false,
                PropertyAssociationType.COMPOSITION));
        propertyMap.put(SIGNALLIST, PropertyDescriptorSupport.createCollection(SIGNALLIST,
                WorkflowSignalExtension.class, 8, PROPERTY_CONSTRAINTS[6], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(WorkflowDefinitionExtension.getPropertyDescriptor(NAME), this.getName(),
                null));
        properties.add(super.createProperty(WorkflowDefinitionExtension.getPropertyDescriptor(OWNER), this.getOwner(),
                null));
        properties.add(super.createProperty(WorkflowDefinitionExtension.getPropertyDescriptor(DESCRIPTION),
                this.getDescription(), null));
        properties.add(super.createProperty(WorkflowDefinitionExtension.getPropertyDescriptor(TYPE), this.getType(),
                null));
        properties.add(super.createProperty(WorkflowDefinitionExtension.getPropertyDescriptor(STATELIST),
                this.stateList, null));
        properties.add(super.createProperty(WorkflowDefinitionExtension.getPropertyDescriptor(TRANSITIONLIST),
                this.transitionList, null));
        properties.add(super.createProperty(WorkflowDefinitionExtension.getPropertyDescriptor(SIGNALLIST),
                this.signalList, null));
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
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == StringProperty.class))) {
            this.setDescription(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TYPE) && (property.getType() == EnumerationProperty.class))) {
            this.setType(((EnumerationProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(STATELIST) && (property.getType() == WorkflowStateExtension.class))) {
            this.stateList = ((NabuccoList<WorkflowStateExtension>) property.getInstance());
            return true;
        } else if ((property.getName().equals(TRANSITIONLIST) && (property.getType() == WorkflowTransitionExtension.class))) {
            this.transitionList = ((NabuccoList<WorkflowTransitionExtension>) property.getInstance());
            return true;
        } else if ((property.getName().equals(SIGNALLIST) && (property.getType() == WorkflowSignalExtension.class))) {
            this.signalList = ((NabuccoList<WorkflowSignalExtension>) property.getInstance());
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
        final WorkflowDefinitionExtension other = ((WorkflowDefinitionExtension) obj);
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
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.type == null) ? 0 : this.type.hashCode()));
        return result;
    }

    @Override
    public WorkflowDefinitionExtension cloneObject() {
        WorkflowDefinitionExtension clone = new WorkflowDefinitionExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The name of the workflow.
     *
     * @param name the StringProperty.
     */
    public void setName(StringProperty name) {
        this.name = name;
    }

    /**
     * The name of the workflow.
     *
     * @return the StringProperty.
     */
    public StringProperty getName() {
        return this.name;
    }

    /**
     * The owner of the workflow.
     *
     * @param owner the StringProperty.
     */
    public void setOwner(StringProperty owner) {
        this.owner = owner;
    }

    /**
     * The owner of the workflow.
     *
     * @return the StringProperty.
     */
    public StringProperty getOwner() {
        return this.owner;
    }

    /**
     * The description of the workflow.
     *
     * @param description the StringProperty.
     */
    public void setDescription(StringProperty description) {
        this.description = description;
    }

    /**
     * The description of the workflow.
     *
     * @return the StringProperty.
     */
    public StringProperty getDescription() {
        return this.description;
    }

    /**
     * The type of the workflow.
     *
     * @param type the EnumerationProperty.
     */
    public void setType(EnumerationProperty type) {
        this.type = type;
    }

    /**
     * The type of the workflow.
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getType() {
        return this.type;
    }

    /**
     * The list of workflow states.
     *
     * @return the NabuccoList<WorkflowStateExtension>.
     */
    public NabuccoList<WorkflowStateExtension> getStateList() {
        if ((this.stateList == null)) {
            this.stateList = new NabuccoListImpl<WorkflowStateExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.stateList;
    }

    /**
     * The list of workflow transitions.
     *
     * @return the NabuccoList<WorkflowTransitionExtension>.
     */
    public NabuccoList<WorkflowTransitionExtension> getTransitionList() {
        if ((this.transitionList == null)) {
            this.transitionList = new NabuccoListImpl<WorkflowTransitionExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.transitionList;
    }

    /**
     * The list of workflow signals.
     *
     * @return the NabuccoList<WorkflowSignalExtension>.
     */
    public NabuccoList<WorkflowSignalExtension> getSignalList() {
        if ((this.signalList == null)) {
            this.signalList = new NabuccoListImpl<WorkflowSignalExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.signalList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(WorkflowDefinitionExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(WorkflowDefinitionExtension.class).getAllProperties();
    }
}
