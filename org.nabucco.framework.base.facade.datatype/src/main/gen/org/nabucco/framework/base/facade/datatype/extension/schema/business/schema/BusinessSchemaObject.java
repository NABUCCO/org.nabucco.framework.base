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
package org.nabucco.framework.base.facade.datatype.extension.schema.business.schema;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.property.ClassProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * BusinessSchemaObject<p/>Configuration for business schema objects.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-06-26
 */
public class BusinessSchemaObject extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;", "m0,1;" };

    public static final String ID = "id";

    public static final String BUSINESSCLASS = "businessClass";

    public static final String COMPONENT = "component";

    public static final String DESCRIPTION = "description";

    /** The business object id. */
    private StringProperty id;

    /** The business object class. */
    private ClassProperty businessClass;

    /** The business object component. */
    private ClassProperty component;

    /** An optional description of the business object. */
    private StringProperty description;

    /** Constructs a new BusinessSchemaObject instance. */
    public BusinessSchemaObject() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the BusinessSchemaObject.
     */
    protected void cloneObject(BusinessSchemaObject clone) {
        super.cloneObject(clone);
        if ((this.getId() != null)) {
            clone.setId(this.getId().cloneObject());
        }
        if ((this.getBusinessClass() != null)) {
            clone.setBusinessClass(this.getBusinessClass().cloneObject());
        }
        if ((this.getComponent() != null)) {
            clone.setComponent(this.getComponent().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
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
        propertyMap.put(ID, PropertyDescriptorSupport.createDatatype(ID, StringProperty.class, 2,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(BUSINESSCLASS, PropertyDescriptorSupport.createDatatype(BUSINESSCLASS, ClassProperty.class, 3,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(COMPONENT, PropertyDescriptorSupport.createDatatype(COMPONENT, ClassProperty.class, 4,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createDatatype(DESCRIPTION, StringProperty.class, 5,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(BusinessSchemaObject.getPropertyDescriptor(ID), this.getId(), null));
        properties.add(super.createProperty(BusinessSchemaObject.getPropertyDescriptor(BUSINESSCLASS),
                this.getBusinessClass(), null));
        properties.add(super.createProperty(BusinessSchemaObject.getPropertyDescriptor(COMPONENT), this.getComponent(),
                null));
        properties.add(super.createProperty(BusinessSchemaObject.getPropertyDescriptor(DESCRIPTION),
                this.getDescription(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(ID) && (property.getType() == StringProperty.class))) {
            this.setId(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(BUSINESSCLASS) && (property.getType() == ClassProperty.class))) {
            this.setBusinessClass(((ClassProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(COMPONENT) && (property.getType() == ClassProperty.class))) {
            this.setComponent(((ClassProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == StringProperty.class))) {
            this.setDescription(((StringProperty) property.getInstance()));
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
        final BusinessSchemaObject other = ((BusinessSchemaObject) obj);
        if ((this.id == null)) {
            if ((other.id != null))
                return false;
        } else if ((!this.id.equals(other.id)))
            return false;
        if ((this.businessClass == null)) {
            if ((other.businessClass != null))
                return false;
        } else if ((!this.businessClass.equals(other.businessClass)))
            return false;
        if ((this.component == null)) {
            if ((other.component != null))
                return false;
        } else if ((!this.component.equals(other.component)))
            return false;
        if ((this.description == null)) {
            if ((other.description != null))
                return false;
        } else if ((!this.description.equals(other.description)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.id == null) ? 0 : this.id.hashCode()));
        result = ((PRIME * result) + ((this.businessClass == null) ? 0 : this.businessClass.hashCode()));
        result = ((PRIME * result) + ((this.component == null) ? 0 : this.component.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        return result;
    }

    @Override
    public BusinessSchemaObject cloneObject() {
        BusinessSchemaObject clone = new BusinessSchemaObject();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The business object id.
     *
     * @param id the StringProperty.
     */
    public void setId(StringProperty id) {
        this.id = id;
    }

    /**
     * The business object id.
     *
     * @return the StringProperty.
     */
    public StringProperty getId() {
        return this.id;
    }

    /**
     * The business object class.
     *
     * @param businessClass the ClassProperty.
     */
    public void setBusinessClass(ClassProperty businessClass) {
        this.businessClass = businessClass;
    }

    /**
     * The business object class.
     *
     * @return the ClassProperty.
     */
    public ClassProperty getBusinessClass() {
        return this.businessClass;
    }

    /**
     * The business object component.
     *
     * @param component the ClassProperty.
     */
    public void setComponent(ClassProperty component) {
        this.component = component;
    }

    /**
     * The business object component.
     *
     * @return the ClassProperty.
     */
    public ClassProperty getComponent() {
        return this.component;
    }

    /**
     * An optional description of the business object.
     *
     * @param description the StringProperty.
     */
    public void setDescription(StringProperty description) {
        this.description = description;
    }

    /**
     * An optional description of the business object.
     *
     * @return the StringProperty.
     */
    public StringProperty getDescription() {
        return this.description;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(BusinessSchemaObject.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(BusinessSchemaObject.class).getAllProperties();
    }
}
