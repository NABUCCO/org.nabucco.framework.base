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
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * WorkflowConditionExtension<p/>Extension for configuring a workflow condition.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-03-15
 */
public class WorkflowConditionExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m0,1;", "m0,1;",
            "m0,n;" };

    public static final String NAME = "name";

    public static final String OWNER = "owner";

    public static final String DESCRIPTION = "description";

    public static final String TYPE = "type";

    public static final String OPERATOR = "operator";

    public static final String VALUE = "value";

    public static final String CHILDREN = "children";

    /** The name of the signal. */
    private StringProperty name;

    /** The owner of the effect. */
    private StringProperty owner;

    /** The description of the signal. */
    private StringProperty description;

    /** The type of the condition. */
    private EnumerationProperty type;

    /** The optional operator type (xor value). */
    private EnumerationProperty operator;

    /** The optional condition value (xor operator). */
    private StringProperty value;

    /** The child conditions. */
    private NabuccoList<WorkflowConditionExtension> children;

    /** Constructs a new WorkflowConditionExtension instance. */
    public WorkflowConditionExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the WorkflowConditionExtension.
     */
    protected void cloneObject(WorkflowConditionExtension clone) {
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
        if ((this.getOperator() != null)) {
            clone.setOperator(this.getOperator().cloneObject());
        }
        if ((this.getValue() != null)) {
            clone.setValue(this.getValue().cloneObject());
        }
        if ((this.children != null)) {
            clone.children = this.children.cloneCollection();
        }
    }

    /**
     * Getter for the ChildrenJPA.
     *
     * @return the List<WorkflowConditionExtension>.
     */
    List<WorkflowConditionExtension> getChildrenJPA() {
        if ((this.children == null)) {
            this.children = new NabuccoListImpl<WorkflowConditionExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<WorkflowConditionExtension>) this.children).getDelegate();
    }

    /**
     * Setter for the ChildrenJPA.
     *
     * @param children the List<WorkflowConditionExtension>.
     */
    void setChildrenJPA(List<WorkflowConditionExtension> children) {
        if ((this.children == null)) {
            this.children = new NabuccoListImpl<WorkflowConditionExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<WorkflowConditionExtension>) this.children).setDelegate(children);
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
        propertyMap.put(OPERATOR, PropertyDescriptorSupport.createDatatype(OPERATOR, EnumerationProperty.class, 6,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(VALUE, PropertyDescriptorSupport.createDatatype(VALUE, StringProperty.class, 7,
                PROPERTY_CONSTRAINTS[5], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(CHILDREN, PropertyDescriptorSupport.createCollection(CHILDREN,
                WorkflowConditionExtension.class, 8, PROPERTY_CONSTRAINTS[6], false,
                PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(WorkflowConditionExtension.getPropertyDescriptor(NAME), this.getName(),
                null));
        properties.add(super.createProperty(WorkflowConditionExtension.getPropertyDescriptor(OWNER), this.getOwner(),
                null));
        properties.add(super.createProperty(WorkflowConditionExtension.getPropertyDescriptor(DESCRIPTION),
                this.getDescription(), null));
        properties.add(super.createProperty(WorkflowConditionExtension.getPropertyDescriptor(TYPE), this.getType(),
                null));
        properties.add(super.createProperty(WorkflowConditionExtension.getPropertyDescriptor(OPERATOR),
                this.getOperator(), null));
        properties.add(super.createProperty(WorkflowConditionExtension.getPropertyDescriptor(VALUE), this.getValue(),
                null));
        properties.add(super.createProperty(WorkflowConditionExtension.getPropertyDescriptor(CHILDREN), this.children,
                null));
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
        } else if ((property.getName().equals(OPERATOR) && (property.getType() == EnumerationProperty.class))) {
            this.setOperator(((EnumerationProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(VALUE) && (property.getType() == StringProperty.class))) {
            this.setValue(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CHILDREN) && (property.getType() == WorkflowConditionExtension.class))) {
            this.children = ((NabuccoList<WorkflowConditionExtension>) property.getInstance());
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
        final WorkflowConditionExtension other = ((WorkflowConditionExtension) obj);
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
        if ((this.operator == null)) {
            if ((other.operator != null))
                return false;
        } else if ((!this.operator.equals(other.operator)))
            return false;
        if ((this.value == null)) {
            if ((other.value != null))
                return false;
        } else if ((!this.value.equals(other.value)))
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
        result = ((PRIME * result) + ((this.operator == null) ? 0 : this.operator.hashCode()));
        result = ((PRIME * result) + ((this.value == null) ? 0 : this.value.hashCode()));
        return result;
    }

    @Override
    public WorkflowConditionExtension cloneObject() {
        WorkflowConditionExtension clone = new WorkflowConditionExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The name of the signal.
     *
     * @param name the StringProperty.
     */
    public void setName(StringProperty name) {
        this.name = name;
    }

    /**
     * The name of the signal.
     *
     * @return the StringProperty.
     */
    public StringProperty getName() {
        return this.name;
    }

    /**
     * The owner of the effect.
     *
     * @param owner the StringProperty.
     */
    public void setOwner(StringProperty owner) {
        this.owner = owner;
    }

    /**
     * The owner of the effect.
     *
     * @return the StringProperty.
     */
    public StringProperty getOwner() {
        return this.owner;
    }

    /**
     * The description of the signal.
     *
     * @param description the StringProperty.
     */
    public void setDescription(StringProperty description) {
        this.description = description;
    }

    /**
     * The description of the signal.
     *
     * @return the StringProperty.
     */
    public StringProperty getDescription() {
        return this.description;
    }

    /**
     * The type of the condition.
     *
     * @param type the EnumerationProperty.
     */
    public void setType(EnumerationProperty type) {
        this.type = type;
    }

    /**
     * The type of the condition.
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getType() {
        return this.type;
    }

    /**
     * The optional operator type (xor value).
     *
     * @param operator the EnumerationProperty.
     */
    public void setOperator(EnumerationProperty operator) {
        this.operator = operator;
    }

    /**
     * The optional operator type (xor value).
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getOperator() {
        return this.operator;
    }

    /**
     * The optional condition value (xor operator).
     *
     * @param value the StringProperty.
     */
    public void setValue(StringProperty value) {
        this.value = value;
    }

    /**
     * The optional condition value (xor operator).
     *
     * @return the StringProperty.
     */
    public StringProperty getValue() {
        return this.value;
    }

    /**
     * The child conditions.
     *
     * @return the NabuccoList<WorkflowConditionExtension>.
     */
    public NabuccoList<WorkflowConditionExtension> getChildren() {
        if ((this.children == null)) {
            this.children = new NabuccoListImpl<WorkflowConditionExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.children;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(WorkflowConditionExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(WorkflowConditionExtension.class).getAllProperties();
    }
}
