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
package org.nabucco.framework.base.facade.datatype.extension.schema.aspect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.property.ClassProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.property.PropertyExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * PointcutExtension<p/>Aspect extension for pointcuts.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-03-11
 */
public class PointcutExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m1,1;" };

    public static final String PROPERTYEXTENSION = "propertyExtension";

    public static final String SERVICE = "service";

    public static final String OPERATION = "operation";

    public static final String ADVICE = "advice";

    public static final String IMPLCLASS = "implClass";

    /** The Pointcut Properties */
    private PropertyExtension propertyExtension;

    /** The Pointcut Service */
    private StringProperty service;

    /** The Pointcut Service Operation */
    private StringProperty operation;

    /** The Pointcut Invocation Advice */
    private EnumerationProperty advice;

    /** The Pointcut Implementation */
    private ClassProperty implClass;

    /** Constructs a new PointcutExtension instance. */
    public PointcutExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the PointcutExtension.
     */
    protected void cloneObject(PointcutExtension clone) {
        super.cloneObject(clone);
        if ((this.getPropertyExtension() != null)) {
            clone.setPropertyExtension(this.getPropertyExtension().cloneObject());
        }
        if ((this.getService() != null)) {
            clone.setService(this.getService().cloneObject());
        }
        if ((this.getOperation() != null)) {
            clone.setOperation(this.getOperation().cloneObject());
        }
        if ((this.getAdvice() != null)) {
            clone.setAdvice(this.getAdvice().cloneObject());
        }
        if ((this.getImplClass() != null)) {
            clone.setImplClass(this.getImplClass().cloneObject());
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
        propertyMap.put(PROPERTYEXTENSION, PropertyDescriptorSupport.createDatatype(PROPERTYEXTENSION,
                PropertyExtension.class, 2, PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(SERVICE, PropertyDescriptorSupport.createDatatype(SERVICE, StringProperty.class, 3,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(OPERATION, PropertyDescriptorSupport.createDatatype(OPERATION, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ADVICE, PropertyDescriptorSupport.createDatatype(ADVICE, EnumerationProperty.class, 5,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(IMPLCLASS, PropertyDescriptorSupport.createDatatype(IMPLCLASS, ClassProperty.class, 6,
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
        properties.add(super.createProperty(PointcutExtension.getPropertyDescriptor(PROPERTYEXTENSION),
                this.getPropertyExtension(), null));
        properties.add(super.createProperty(PointcutExtension.getPropertyDescriptor(SERVICE), this.getService(), null));
        properties.add(super.createProperty(PointcutExtension.getPropertyDescriptor(OPERATION), this.getOperation(),
                null));
        properties.add(super.createProperty(PointcutExtension.getPropertyDescriptor(ADVICE), this.getAdvice(), null));
        properties.add(super.createProperty(PointcutExtension.getPropertyDescriptor(IMPLCLASS), this.getImplClass(),
                null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(PROPERTYEXTENSION) && (property.getType() == PropertyExtension.class))) {
            this.setPropertyExtension(((PropertyExtension) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SERVICE) && (property.getType() == StringProperty.class))) {
            this.setService(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(OPERATION) && (property.getType() == StringProperty.class))) {
            this.setOperation(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ADVICE) && (property.getType() == EnumerationProperty.class))) {
            this.setAdvice(((EnumerationProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(IMPLCLASS) && (property.getType() == ClassProperty.class))) {
            this.setImplClass(((ClassProperty) property.getInstance()));
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
        final PointcutExtension other = ((PointcutExtension) obj);
        if ((this.propertyExtension == null)) {
            if ((other.propertyExtension != null))
                return false;
        } else if ((!this.propertyExtension.equals(other.propertyExtension)))
            return false;
        if ((this.service == null)) {
            if ((other.service != null))
                return false;
        } else if ((!this.service.equals(other.service)))
            return false;
        if ((this.operation == null)) {
            if ((other.operation != null))
                return false;
        } else if ((!this.operation.equals(other.operation)))
            return false;
        if ((this.advice == null)) {
            if ((other.advice != null))
                return false;
        } else if ((!this.advice.equals(other.advice)))
            return false;
        if ((this.implClass == null)) {
            if ((other.implClass != null))
                return false;
        } else if ((!this.implClass.equals(other.implClass)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.propertyExtension == null) ? 0 : this.propertyExtension.hashCode()));
        result = ((PRIME * result) + ((this.service == null) ? 0 : this.service.hashCode()));
        result = ((PRIME * result) + ((this.operation == null) ? 0 : this.operation.hashCode()));
        result = ((PRIME * result) + ((this.advice == null) ? 0 : this.advice.hashCode()));
        result = ((PRIME * result) + ((this.implClass == null) ? 0 : this.implClass.hashCode()));
        return result;
    }

    @Override
    public PointcutExtension cloneObject() {
        PointcutExtension clone = new PointcutExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The Pointcut Properties
     *
     * @param propertyExtension the PropertyExtension.
     */
    public void setPropertyExtension(PropertyExtension propertyExtension) {
        this.propertyExtension = propertyExtension;
    }

    /**
     * The Pointcut Properties
     *
     * @return the PropertyExtension.
     */
    public PropertyExtension getPropertyExtension() {
        return this.propertyExtension;
    }

    /**
     * The Pointcut Service
     *
     * @param service the StringProperty.
     */
    public void setService(StringProperty service) {
        this.service = service;
    }

    /**
     * The Pointcut Service
     *
     * @return the StringProperty.
     */
    public StringProperty getService() {
        return this.service;
    }

    /**
     * The Pointcut Service Operation
     *
     * @param operation the StringProperty.
     */
    public void setOperation(StringProperty operation) {
        this.operation = operation;
    }

    /**
     * The Pointcut Service Operation
     *
     * @return the StringProperty.
     */
    public StringProperty getOperation() {
        return this.operation;
    }

    /**
     * The Pointcut Invocation Advice
     *
     * @param advice the EnumerationProperty.
     */
    public void setAdvice(EnumerationProperty advice) {
        this.advice = advice;
    }

    /**
     * The Pointcut Invocation Advice
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getAdvice() {
        return this.advice;
    }

    /**
     * The Pointcut Implementation
     *
     * @param implClass the ClassProperty.
     */
    public void setImplClass(ClassProperty implClass) {
        this.implClass = implClass;
    }

    /**
     * The Pointcut Implementation
     *
     * @return the ClassProperty.
     */
    public ClassProperty getImplClass() {
        return this.implClass;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(PointcutExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(PointcutExtension.class).getAllProperties();
    }
}
