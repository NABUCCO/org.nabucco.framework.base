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
package org.nabucco.framework.base.facade.datatype.extension.schema.business.schema;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.property.ClassProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.business.schema.BusinessSchemaAssociationRole;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * BusinessSchemaAssociation<p/>Configuration for business schema associations.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-06-26
 */
public class BusinessSchemaAssociation extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m0,1;", "m1,1;",
            "m1,1;" };

    public static final String NAME = "name";

    public static final String TYPE = "type";

    public static final String FETCHTYPE = "fetchType";

    public static final String ASSOCIATIONCLASS = "associationClass";

    public static final String DESCRIPTION = "description";

    public static final String SOURCE = "source";

    public static final String TARGET = "target";

    /** The association name. */
    private StringProperty name;

    /** The association type. */
    private EnumerationProperty type;

    /** The association fetch type. */
    private EnumerationProperty fetchType;

    /** The association class. */
    private ClassProperty associationClass;

    /** An optional association description. */
    private StringProperty description;

    /** The association source. */
    private BusinessSchemaAssociationRole source;

    /** The association target. */
    private BusinessSchemaAssociationRole target;

    /** Constructs a new BusinessSchemaAssociation instance. */
    public BusinessSchemaAssociation() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the BusinessSchemaAssociation.
     */
    protected void cloneObject(BusinessSchemaAssociation clone) {
        super.cloneObject(clone);
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getType() != null)) {
            clone.setType(this.getType().cloneObject());
        }
        if ((this.getFetchType() != null)) {
            clone.setFetchType(this.getFetchType().cloneObject());
        }
        if ((this.getAssociationClass() != null)) {
            clone.setAssociationClass(this.getAssociationClass().cloneObject());
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
        propertyMap.put(TYPE, PropertyDescriptorSupport.createDatatype(TYPE, EnumerationProperty.class, 3,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(FETCHTYPE, PropertyDescriptorSupport.createDatatype(FETCHTYPE, EnumerationProperty.class, 4,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ASSOCIATIONCLASS, PropertyDescriptorSupport.createDatatype(ASSOCIATIONCLASS,
                ClassProperty.class, 5, PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createDatatype(DESCRIPTION, StringProperty.class, 6,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(SOURCE, PropertyDescriptorSupport.createDatatype(SOURCE, BusinessSchemaAssociationRole.class,
                7, PROPERTY_CONSTRAINTS[5], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(TARGET, PropertyDescriptorSupport.createDatatype(TARGET, BusinessSchemaAssociationRole.class,
                8, PROPERTY_CONSTRAINTS[6], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties
                .add(super.createProperty(BusinessSchemaAssociation.getPropertyDescriptor(NAME), this.getName(), null));
        properties
                .add(super.createProperty(BusinessSchemaAssociation.getPropertyDescriptor(TYPE), this.getType(), null));
        properties.add(super.createProperty(BusinessSchemaAssociation.getPropertyDescriptor(FETCHTYPE),
                this.getFetchType(), null));
        properties.add(super.createProperty(BusinessSchemaAssociation.getPropertyDescriptor(ASSOCIATIONCLASS),
                this.getAssociationClass(), null));
        properties.add(super.createProperty(BusinessSchemaAssociation.getPropertyDescriptor(DESCRIPTION),
                this.getDescription(), null));
        properties.add(super.createProperty(BusinessSchemaAssociation.getPropertyDescriptor(SOURCE), this.getSource(),
                null));
        properties.add(super.createProperty(BusinessSchemaAssociation.getPropertyDescriptor(TARGET), this.getTarget(),
                null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(NAME) && (property.getType() == StringProperty.class))) {
            this.setName(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TYPE) && (property.getType() == EnumerationProperty.class))) {
            this.setType(((EnumerationProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(FETCHTYPE) && (property.getType() == EnumerationProperty.class))) {
            this.setFetchType(((EnumerationProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ASSOCIATIONCLASS) && (property.getType() == ClassProperty.class))) {
            this.setAssociationClass(((ClassProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == StringProperty.class))) {
            this.setDescription(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SOURCE) && (property.getType() == BusinessSchemaAssociationRole.class))) {
            this.setSource(((BusinessSchemaAssociationRole) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TARGET) && (property.getType() == BusinessSchemaAssociationRole.class))) {
            this.setTarget(((BusinessSchemaAssociationRole) property.getInstance()));
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
        final BusinessSchemaAssociation other = ((BusinessSchemaAssociation) obj);
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        if ((this.type == null)) {
            if ((other.type != null))
                return false;
        } else if ((!this.type.equals(other.type)))
            return false;
        if ((this.fetchType == null)) {
            if ((other.fetchType != null))
                return false;
        } else if ((!this.fetchType.equals(other.fetchType)))
            return false;
        if ((this.associationClass == null)) {
            if ((other.associationClass != null))
                return false;
        } else if ((!this.associationClass.equals(other.associationClass)))
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
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.type == null) ? 0 : this.type.hashCode()));
        result = ((PRIME * result) + ((this.fetchType == null) ? 0 : this.fetchType.hashCode()));
        result = ((PRIME * result) + ((this.associationClass == null) ? 0 : this.associationClass.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.source == null) ? 0 : this.source.hashCode()));
        result = ((PRIME * result) + ((this.target == null) ? 0 : this.target.hashCode()));
        return result;
    }

    @Override
    public BusinessSchemaAssociation cloneObject() {
        BusinessSchemaAssociation clone = new BusinessSchemaAssociation();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The association name.
     *
     * @param name the StringProperty.
     */
    public void setName(StringProperty name) {
        this.name = name;
    }

    /**
     * The association name.
     *
     * @return the StringProperty.
     */
    public StringProperty getName() {
        return this.name;
    }

    /**
     * The association type.
     *
     * @param type the EnumerationProperty.
     */
    public void setType(EnumerationProperty type) {
        this.type = type;
    }

    /**
     * The association type.
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getType() {
        return this.type;
    }

    /**
     * The association fetch type.
     *
     * @param fetchType the EnumerationProperty.
     */
    public void setFetchType(EnumerationProperty fetchType) {
        this.fetchType = fetchType;
    }

    /**
     * The association fetch type.
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getFetchType() {
        return this.fetchType;
    }

    /**
     * The association class.
     *
     * @param associationClass the ClassProperty.
     */
    public void setAssociationClass(ClassProperty associationClass) {
        this.associationClass = associationClass;
    }

    /**
     * The association class.
     *
     * @return the ClassProperty.
     */
    public ClassProperty getAssociationClass() {
        return this.associationClass;
    }

    /**
     * An optional association description.
     *
     * @param description the StringProperty.
     */
    public void setDescription(StringProperty description) {
        this.description = description;
    }

    /**
     * An optional association description.
     *
     * @return the StringProperty.
     */
    public StringProperty getDescription() {
        return this.description;
    }

    /**
     * The association source.
     *
     * @param source the BusinessSchemaAssociationRole.
     */
    public void setSource(BusinessSchemaAssociationRole source) {
        this.source = source;
    }

    /**
     * The association source.
     *
     * @return the BusinessSchemaAssociationRole.
     */
    public BusinessSchemaAssociationRole getSource() {
        return this.source;
    }

    /**
     * The association target.
     *
     * @param target the BusinessSchemaAssociationRole.
     */
    public void setTarget(BusinessSchemaAssociationRole target) {
        this.target = target;
    }

    /**
     * The association target.
     *
     * @return the BusinessSchemaAssociationRole.
     */
    public BusinessSchemaAssociationRole getTarget() {
        return this.target;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(BusinessSchemaAssociation.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(BusinessSchemaAssociation.class).getAllProperties();
    }
}
