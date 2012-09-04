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
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.management.ManagementAttribute;
import org.nabucco.framework.base.facade.datatype.extension.schema.management.ManagementOperation;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * ManagementExtension<p/>Configuration for management extensions.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-06-16
 */
public class ManagementExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m0,n;", "m0,n;" };

    public static final String BEANCLASS = "beanClass";

    public static final String DESCRIPTION = "description";

    public static final String ATTRIBUTES = "attributes";

    public static final String OPERATIONS = "operations";

    /** The management bean class. */
    private ClassProperty beanClass;

    /** The description of the management bean. */
    private StringProperty description;

    /** The management attributes. */
    private NabuccoList<ManagementAttribute> attributes;

    /** The management operations. */
    private NabuccoList<ManagementOperation> operations;

    /** Constructs a new ManagementExtension instance. */
    public ManagementExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ManagementExtension.
     */
    protected void cloneObject(ManagementExtension clone) {
        super.cloneObject(clone);
        if ((this.getBeanClass() != null)) {
            clone.setBeanClass(this.getBeanClass().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        if ((this.attributes != null)) {
            clone.attributes = this.attributes.cloneCollection();
        }
        if ((this.operations != null)) {
            clone.operations = this.operations.cloneCollection();
        }
    }

    /**
     * Getter for the AttributesJPA.
     *
     * @return the List<ManagementAttribute>.
     */
    List<ManagementAttribute> getAttributesJPA() {
        if ((this.attributes == null)) {
            this.attributes = new NabuccoListImpl<ManagementAttribute>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<ManagementAttribute>) this.attributes).getDelegate();
    }

    /**
     * Setter for the AttributesJPA.
     *
     * @param attributes the List<ManagementAttribute>.
     */
    void setAttributesJPA(List<ManagementAttribute> attributes) {
        if ((this.attributes == null)) {
            this.attributes = new NabuccoListImpl<ManagementAttribute>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<ManagementAttribute>) this.attributes).setDelegate(attributes);
    }

    /**
     * Getter for the OperationsJPA.
     *
     * @return the List<ManagementOperation>.
     */
    List<ManagementOperation> getOperationsJPA() {
        if ((this.operations == null)) {
            this.operations = new NabuccoListImpl<ManagementOperation>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<ManagementOperation>) this.operations).getDelegate();
    }

    /**
     * Setter for the OperationsJPA.
     *
     * @param operations the List<ManagementOperation>.
     */
    void setOperationsJPA(List<ManagementOperation> operations) {
        if ((this.operations == null)) {
            this.operations = new NabuccoListImpl<ManagementOperation>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<ManagementOperation>) this.operations).setDelegate(operations);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoExtensionComposite.class).getPropertyMap());
        propertyMap.put(BEANCLASS, PropertyDescriptorSupport.createDatatype(BEANCLASS, ClassProperty.class, 2,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createDatatype(DESCRIPTION, StringProperty.class, 3,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ATTRIBUTES, PropertyDescriptorSupport.createCollection(ATTRIBUTES, ManagementAttribute.class,
                4, PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(OPERATIONS, PropertyDescriptorSupport.createCollection(OPERATIONS, ManagementOperation.class,
                5, PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ManagementExtension.getPropertyDescriptor(BEANCLASS), this.getBeanClass(),
                null));
        properties.add(super.createProperty(ManagementExtension.getPropertyDescriptor(DESCRIPTION),
                this.getDescription(), null));
        properties.add(super.createProperty(ManagementExtension.getPropertyDescriptor(ATTRIBUTES), this.attributes,
                null));
        properties.add(super.createProperty(ManagementExtension.getPropertyDescriptor(OPERATIONS), this.operations,
                null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(BEANCLASS) && (property.getType() == ClassProperty.class))) {
            this.setBeanClass(((ClassProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == StringProperty.class))) {
            this.setDescription(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ATTRIBUTES) && (property.getType() == ManagementAttribute.class))) {
            this.attributes = ((NabuccoList<ManagementAttribute>) property.getInstance());
            return true;
        } else if ((property.getName().equals(OPERATIONS) && (property.getType() == ManagementOperation.class))) {
            this.operations = ((NabuccoList<ManagementOperation>) property.getInstance());
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
        final ManagementExtension other = ((ManagementExtension) obj);
        if ((this.beanClass == null)) {
            if ((other.beanClass != null))
                return false;
        } else if ((!this.beanClass.equals(other.beanClass)))
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
        result = ((PRIME * result) + ((this.beanClass == null) ? 0 : this.beanClass.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        return result;
    }

    @Override
    public ManagementExtension cloneObject() {
        ManagementExtension clone = new ManagementExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The management bean class.
     *
     * @param beanClass the ClassProperty.
     */
    public void setBeanClass(ClassProperty beanClass) {
        this.beanClass = beanClass;
    }

    /**
     * The management bean class.
     *
     * @return the ClassProperty.
     */
    public ClassProperty getBeanClass() {
        return this.beanClass;
    }

    /**
     * The description of the management bean.
     *
     * @param description the StringProperty.
     */
    public void setDescription(StringProperty description) {
        this.description = description;
    }

    /**
     * The description of the management bean.
     *
     * @return the StringProperty.
     */
    public StringProperty getDescription() {
        return this.description;
    }

    /**
     * The management attributes.
     *
     * @return the NabuccoList<ManagementAttribute>.
     */
    public NabuccoList<ManagementAttribute> getAttributes() {
        if ((this.attributes == null)) {
            this.attributes = new NabuccoListImpl<ManagementAttribute>(NabuccoCollectionState.INITIALIZED);
        }
        return this.attributes;
    }

    /**
     * The management operations.
     *
     * @return the NabuccoList<ManagementOperation>.
     */
    public NabuccoList<ManagementOperation> getOperations() {
        if ((this.operations == null)) {
            this.operations = new NabuccoListImpl<ManagementOperation>(NabuccoCollectionState.INITIALIZED);
        }
        return this.operations;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ManagementExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ManagementExtension.class).getAllProperties();
    }
}
