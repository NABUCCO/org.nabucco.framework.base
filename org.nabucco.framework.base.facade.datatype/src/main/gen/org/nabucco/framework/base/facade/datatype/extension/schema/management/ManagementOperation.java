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
package org.nabucco.framework.base.facade.datatype.extension.schema.management;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.property.ClassProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.management.ManagementArgument;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * ManagementOperation<p/>Configuration for management operations.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-06-16
 */
public class ManagementOperation extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m0,1;", "m0,1;", "m0,n;" };

    public static final String NAME = "name";

    public static final String DESCRIPTION = "description";

    public static final String RETURNTYPE = "returnType";

    public static final String ACTIONTYPE = "actionType";

    public static final String ARGUMENTS = "arguments";

    /** The operation name. */
    private StringProperty name;

    /** The description of the management operation. */
    private StringProperty description;

    /** The operation return type. */
    private ClassProperty returnType;

    /** The operation action type. */
    private EnumerationProperty actionType;

    /** The operation arguments. */
    private NabuccoList<ManagementArgument> arguments;

    /** Constructs a new ManagementOperation instance. */
    public ManagementOperation() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ManagementOperation.
     */
    protected void cloneObject(ManagementOperation clone) {
        super.cloneObject(clone);
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        if ((this.getReturnType() != null)) {
            clone.setReturnType(this.getReturnType().cloneObject());
        }
        if ((this.getActionType() != null)) {
            clone.setActionType(this.getActionType().cloneObject());
        }
        if ((this.arguments != null)) {
            clone.arguments = this.arguments.cloneCollection();
        }
    }

    /**
     * Getter for the ArgumentsJPA.
     *
     * @return the List<ManagementArgument>.
     */
    List<ManagementArgument> getArgumentsJPA() {
        if ((this.arguments == null)) {
            this.arguments = new NabuccoListImpl<ManagementArgument>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<ManagementArgument>) this.arguments).getDelegate();
    }

    /**
     * Setter for the ArgumentsJPA.
     *
     * @param arguments the List<ManagementArgument>.
     */
    void setArgumentsJPA(List<ManagementArgument> arguments) {
        if ((this.arguments == null)) {
            this.arguments = new NabuccoListImpl<ManagementArgument>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<ManagementArgument>) this.arguments).setDelegate(arguments);
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
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createDatatype(DESCRIPTION, StringProperty.class, 3,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(RETURNTYPE, PropertyDescriptorSupport.createDatatype(RETURNTYPE, ClassProperty.class, 4,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ACTIONTYPE, PropertyDescriptorSupport.createDatatype(ACTIONTYPE, EnumerationProperty.class, 5,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ARGUMENTS, PropertyDescriptorSupport.createCollection(ARGUMENTS, ManagementArgument.class, 6,
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
        properties.add(super.createProperty(ManagementOperation.getPropertyDescriptor(NAME), this.getName(), null));
        properties.add(super.createProperty(ManagementOperation.getPropertyDescriptor(DESCRIPTION),
                this.getDescription(), null));
        properties.add(super.createProperty(ManagementOperation.getPropertyDescriptor(RETURNTYPE),
                this.getReturnType(), null));
        properties.add(super.createProperty(ManagementOperation.getPropertyDescriptor(ACTIONTYPE),
                this.getActionType(), null));
        properties
                .add(super.createProperty(ManagementOperation.getPropertyDescriptor(ARGUMENTS), this.arguments, null));
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
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == StringProperty.class))) {
            this.setDescription(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(RETURNTYPE) && (property.getType() == ClassProperty.class))) {
            this.setReturnType(((ClassProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ACTIONTYPE) && (property.getType() == EnumerationProperty.class))) {
            this.setActionType(((EnumerationProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ARGUMENTS) && (property.getType() == ManagementArgument.class))) {
            this.arguments = ((NabuccoList<ManagementArgument>) property.getInstance());
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
        final ManagementOperation other = ((ManagementOperation) obj);
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
        if ((this.returnType == null)) {
            if ((other.returnType != null))
                return false;
        } else if ((!this.returnType.equals(other.returnType)))
            return false;
        if ((this.actionType == null)) {
            if ((other.actionType != null))
                return false;
        } else if ((!this.actionType.equals(other.actionType)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.returnType == null) ? 0 : this.returnType.hashCode()));
        result = ((PRIME * result) + ((this.actionType == null) ? 0 : this.actionType.hashCode()));
        return result;
    }

    @Override
    public ManagementOperation cloneObject() {
        ManagementOperation clone = new ManagementOperation();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The operation name.
     *
     * @param name the StringProperty.
     */
    public void setName(StringProperty name) {
        this.name = name;
    }

    /**
     * The operation name.
     *
     * @return the StringProperty.
     */
    public StringProperty getName() {
        return this.name;
    }

    /**
     * The description of the management operation.
     *
     * @param description the StringProperty.
     */
    public void setDescription(StringProperty description) {
        this.description = description;
    }

    /**
     * The description of the management operation.
     *
     * @return the StringProperty.
     */
    public StringProperty getDescription() {
        return this.description;
    }

    /**
     * The operation return type.
     *
     * @param returnType the ClassProperty.
     */
    public void setReturnType(ClassProperty returnType) {
        this.returnType = returnType;
    }

    /**
     * The operation return type.
     *
     * @return the ClassProperty.
     */
    public ClassProperty getReturnType() {
        return this.returnType;
    }

    /**
     * The operation action type.
     *
     * @param actionType the EnumerationProperty.
     */
    public void setActionType(EnumerationProperty actionType) {
        this.actionType = actionType;
    }

    /**
     * The operation action type.
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getActionType() {
        return this.actionType;
    }

    /**
     * The operation arguments.
     *
     * @return the NabuccoList<ManagementArgument>.
     */
    public NabuccoList<ManagementArgument> getArguments() {
        if ((this.arguments == null)) {
            this.arguments = new NabuccoListImpl<ManagementArgument>(NabuccoCollectionState.INITIALIZED);
        }
        return this.arguments;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ManagementOperation.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ManagementOperation.class).getAllProperties();
    }
}
