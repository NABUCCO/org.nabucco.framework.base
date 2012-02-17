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
package org.nabucco.framework.base.facade.datatype.property;

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Enumeration;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollection;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.validation.constraint.parser.ConstraintContainer;
import org.nabucco.framework.base.facade.datatype.validation.constraint.parser.ConstraintParser;

/**
 * PropertyDescriptorSupport
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public final class PropertyDescriptorSupport implements NabuccoPropertyDescriptor {

    /** The property name. */
    private String name;

    /** The property class. */
    private Class<?> type;

    /** The property constraints. */
    private String constraints;

    /** Type of the property. */
    private NabuccoPropertyType propertyType;

    /** Type of the association. */
    private PropertyAssociationType associationType;

    /** Flag indicating whether the property is technical or functional. */
    private boolean isTechnical;

    /** Code Path for Dynamic Codes */
    private String codePath;

    /** The index of the property in its parent. */
    private int index;

    /**
     * Creates a new {@link PropertyDescriptorSupport} instance.
     * 
     * @param name
     *            the property name
     * @param type
     *            the property class
     * @param index
     *            the property index
     * @param constraints
     *            the property constraint string
     * @param associationType
     *            the association type
     * @param propertyType
     *            the property type
     * @param isTechnical
     *            flag indicating whether the property is functional or technical
     * @param codePath
     *            the code path pointing to the dynamic code group
     */
    private PropertyDescriptorSupport(String name, Class<?> type, int index, String constraints,
            NabuccoPropertyType propertyType, PropertyAssociationType associationType, boolean isTechnical,
            String codePath) {
        this.name = name;
        this.type = type;
        this.index = index;
        this.constraints = constraints;
        this.propertyType = propertyType;
        this.associationType = associationType;
        this.isTechnical = isTechnical;
        this.codePath = codePath;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Class<?> getType() {
        return this.type;
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public ConstraintContainer getConstraints() {
        return ConstraintParser.getInstance().parseConstraints(this.constraints);
    }

    @Override
    public NabuccoPropertyType getPropertyType() {
        return this.propertyType;
    }

    @Override
    public PropertyAssociationType getAssociationType() {
        return this.associationType;
    }

    @Override
    public String getCodePath() {
        return this.codePath;
    }

    @Override
    public boolean isTechnical() {
        return this.isTechnical;
    }

    @Override
    public boolean isCollection() {
        if (this.getPropertyType() == NabuccoPropertyType.COLLECTION) {
            return true;
        }
        if (this.getPropertyType() == NabuccoPropertyType.COMPONENT_RELATION) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.constraints == null) ? 0 : this.constraints.hashCode());
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = prime * result + ((this.propertyType == null) ? 0 : this.propertyType.hashCode());
        result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PropertyDescriptorSupport other = (PropertyDescriptorSupport) obj;
        if (this.constraints == null) {
            if (other.constraints != null)
                return false;
        } else if (!this.constraints.equals(other.constraints))
            return false;
        if (this.name == null) {
            if (other.name != null)
                return false;
        } else if (!this.name.equals(other.name))
            return false;
        if (this.propertyType != other.propertyType)
            return false;
        if (this.type == null) {
            if (other.type != null)
                return false;
        } else if (!this.type.equals(other.type))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.propertyType);
        builder.append('<');
        builder.append(this.type.getSimpleName());
        builder.append("> (");
        builder.append(this.name);
        builder.append(')');
        return builder.toString();
    }

    @Override
    public NabuccoProperty createProperty(PropertyOwner parent, Object instance, String dynamicConstraints) {
        return this.createProperty(parent, instance, dynamicConstraints, null);
    }

    @Override
    @SuppressWarnings({ "unchecked" })
    public NabuccoProperty createProperty(PropertyOwner parent, Object instance, String dynamicConstraints, Long refId) {

        switch (this.propertyType) {

        case BASETYPE:
            if (instance != null && !(instance instanceof Basetype)) {
                throw new IllegalArgumentException("Instance is not of type 'Basetype'.");
            }
            return new BasetypeProperty(this, parent, (Basetype) instance, dynamicConstraints);

        case ENUMERATION:
            if (instance != null && !(instance instanceof Enumeration)) {
                throw new IllegalArgumentException("Instance is not of type 'Enumeration'.");
            }
            return new EnumProperty(this, parent, (Enumeration) instance, dynamicConstraints);

        case DATATYPE:
            if (instance != null && !(instance instanceof Datatype)) {
                throw new IllegalArgumentException("Instance is not of type 'Datatype'.");
            }
            return new DatatypeProperty(this, parent, (Datatype) instance, dynamicConstraints, refId);

        case COLLECTION:
            if (instance != null && !(instance instanceof NabuccoCollection<?>)) {
                throw new IllegalArgumentException("Instance is not of type 'NabuccoCollection'.");
            }
            return new CollectionProperty(this, parent, (NabuccoCollection<?>) instance, dynamicConstraints);

        case COMPONENT_RELATION:
            if (instance != null && !(instance instanceof NabuccoList<?>)) {
                throw new IllegalArgumentException("Instance is not of type 'NabuccoList'.");
            }
            return new ComponentRelationProperty(this, parent, (NabuccoList<ComponentRelation<?>>) instance, dynamicConstraints);

        default:
            return new SimpleProperty(this, instance);
        }
    }

    /**
     * Creates a new {@link PropertyDescriptorSupport} instance.
     * 
     * @param name
     *            the property name
     * @param type
     *            the property class
     * @param index
     *            the property index in its parent
     * @param constraints
     *            the property constraint string
     * @param isTechnical
     *            flag indicating whether the property is functional or technical
     * 
     * @return the property descriptor support
     */
    public static PropertyDescriptorSupport createBasetype(String name, Class<?> type, int index, String constraints,
            boolean isTechnical) {
        if (name == null) {
            throw new IllegalArgumentException("Cannot create NabuccoPropertyDescriptor with name [null].");
        }
        if (type == null) {
            throw new IllegalArgumentException("Cannot create NabuccoPropertyDescriptor with type [null].");
        }
        if (constraints == null) {
            throw new IllegalArgumentException("Cannot create NabuccoPropertyDescriptor with constraints [null].");
        }

        return new PropertyDescriptorSupport(name, type, index, constraints, NabuccoPropertyType.BASETYPE,
                PropertyAssociationType.COMPOSITION, isTechnical, null);
    }

    /**
     * Creates a new {@link PropertyDescriptorSupport} instance.
     * 
     * @param name
     *            the property name
     * @param type
     *            the property class
     * @param index
     *            the property index in its parent
     * @param constraints
     *            the property constraint string
     * @param isTechnical
     *            flag indicating whether the property is functional or technical
     * 
     * @return the property descriptor support
     */
    public static PropertyDescriptorSupport createEnumeration(String name, Class<?> type, int index,
            String constraints, boolean isTechnical) {
        if (name == null) {
            throw new IllegalArgumentException("Cannot create NabuccoPropertyDescriptor with name [null].");
        }
        if (type == null) {
            throw new IllegalArgumentException("Cannot create NabuccoPropertyDescriptor with type [null].");
        }
        if (constraints == null) {
            throw new IllegalArgumentException("Cannot create NabuccoPropertyDescriptor with constraints [null].");
        }

        return new PropertyDescriptorSupport(name, type, index, constraints, NabuccoPropertyType.ENUMERATION,
                PropertyAssociationType.COMPOSITION, isTechnical, null);
    }

    /**
     * Creates a new {@link PropertyDescriptorSupport} instance.
     * 
     * @param name
     *            the property name
     * @param type
     *            the property class
     * @param constraints
     *            the property constraint string
     * 
     * @return the property descriptor support
     */
    public static PropertyDescriptorSupport createSimpletype(String name, Class<?> type, String constraints) {
        if (name == null) {
            throw new IllegalArgumentException("Cannot create NabuccoPropertyDescriptor with name [null].");
        }
        if (type == null) {
            throw new IllegalArgumentException("Cannot create NabuccoPropertyDescriptor with type [null].");
        }
        if (constraints == null) {
            throw new IllegalArgumentException("Cannot create NabuccoPropertyDescriptor with constraints [null].");
        }

        return new PropertyDescriptorSupport(name, type, 0, constraints, NabuccoPropertyType.SIMPLE,
                PropertyAssociationType.COMPOSITION, true, null);
    }

    /**
     * Creates a new {@link PropertyDescriptorSupport} instance.
     * 
     * @param name
     *            the property name
     * @param type
     *            the property class
     * @param index
     *            the property index in its parent
     * @param constraints
     *            the property constraint string
     * @param associationType
     *            the association type
     * @param isTechnical
     *            flag indicating whether the property is functional or technical
     * 
     * @return the property descriptor support
     */
    public static PropertyDescriptorSupport createDatatype(String name, Class<?> type, int index, String constraints,
            boolean isTechnical, PropertyAssociationType associationType) {
        return createDatatype(name, type, index, constraints, isTechnical, associationType, null);
    }

    /**
     * Creates a new {@link PropertyDescriptorSupport} instance.
     * 
     * @param name
     *            the property name
     * @param type
     *            the property class
     * @param index
     *            the property index in its parent
     * @param constraints
     *            the property constraint string
     * @param associationType
     *            the association type
     * @param isTechnical
     *            flag indicating whether the property is functional or technical
     * 
     * @return the property descriptor support
     */
    public static PropertyDescriptorSupport createDatatype(String name, Class<?> type, int index, String constraints,
            boolean isTechnical, PropertyAssociationType associationType, String codePath) {
        if (name == null) {
            throw new IllegalArgumentException("Cannot create NabuccoPropertyDescriptor with name [null].");
        }
        if (type == null) {
            throw new IllegalArgumentException("Cannot create NabuccoPropertyDescriptor with type [null].");
        }
        if (constraints == null) {
            throw new IllegalArgumentException("Cannot create NabuccoPropertyDescriptor with constraints [null].");
        }
        if (associationType == null) {
            throw new IllegalArgumentException("Cannot create NabuccoPropertyDescriptor with associationType [null].");
        }

        return new PropertyDescriptorSupport(name, type, index, constraints, NabuccoPropertyType.DATATYPE,
                associationType, isTechnical, codePath);
    }

    /**
     * Creates a new {@link PropertyDescriptorSupport} instance.
     * 
     * @param name
     *            the property name
     * @param type
     *            the property class
     * @param index
     *            the property index in its parent
     * @param constraints
     *            the property constraint string
     * @param isTechnical
     *            flag indicating whether the property is functional or technical
     * @param associationType
     *            the association type
     * 
     * @return the property descriptor support
     */
    public static PropertyDescriptorSupport createCollection(String name, Class<?> type, int index, String constraints,
            boolean isTechnical, PropertyAssociationType associationType) {
        if (name == null) {
            throw new IllegalArgumentException("Cannot create NabuccoPropertyDescriptor with name [null].");
        }
        if (type == null) {
            throw new IllegalArgumentException("Cannot create NabuccoPropertyDescriptor with type [null].");
        }
        if (constraints == null) {
            throw new IllegalArgumentException("Cannot create NabuccoPropertyDescriptor with constraints [null].");
        }
        if (associationType == null) {
            throw new IllegalArgumentException("Cannot create NabuccoPropertyDescriptor with associationType [null].");
        }

        return new PropertyDescriptorSupport(name, type, index, constraints, NabuccoPropertyType.COLLECTION,
                associationType, isTechnical, null);
    }

    /**
     * Creates a new {@link PropertyDescriptorSupport} instance.
     * 
     * @param name
     *            the property name
     * @param index
     *            the property index in its parent
     * @param type
     *            the property class
     * @param constraints
     *            the property constraint string
     * @param associationType
     *            the property association type
     * 
     * @return the property descriptor support
     */
    public static PropertyDescriptorSupport createComponentRelation(String name, Class<?> type, int index,
            String constraints, PropertyAssociationType associationType) {
        if (name == null) {
            throw new IllegalArgumentException("Cannot create NabuccoPropertyDescriptor with name [null].");
        }
        if (type == null) {
            throw new IllegalArgumentException("Cannot create NabuccoPropertyDescriptor with type [null].");
        }
        if (constraints == null) {
            throw new IllegalArgumentException("Cannot create NabuccoPropertyDescriptor with constraints [null].");
        }

        return new PropertyDescriptorSupport(name, type, index, constraints, NabuccoPropertyType.COMPONENT_RELATION,
                associationType, false, null);
    }

}
