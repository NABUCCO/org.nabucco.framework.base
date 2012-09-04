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
package org.nabucco.framework.base.facade.datatype.extension.schema;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.property.ClassProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * ClassExtension<p/>Extension for defining a class with optional superclass and superinterface.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-23
 */
public class ClassExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,1;", "m0,1;", "m1,1;" };

    public static final String SUPERCLASS = "superClass";

    public static final String SUPERINTERFACE = "superInterface";

    public static final String IMPLCLASS = "implClass";

    /** The superclass of the implClass property. */
    private ClassProperty superClass;

    /** The superinterface of the implClass property. */
    private ClassProperty superInterface;

    /** The implementation class. */
    private ClassProperty implClass;

    /** Constructs a new ClassExtension instance. */
    public ClassExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ClassExtension.
     */
    protected void cloneObject(ClassExtension clone) {
        super.cloneObject(clone);
        if ((this.getSuperClass() != null)) {
            clone.setSuperClass(this.getSuperClass().cloneObject());
        }
        if ((this.getSuperInterface() != null)) {
            clone.setSuperInterface(this.getSuperInterface().cloneObject());
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
        propertyMap.put(SUPERCLASS, PropertyDescriptorSupport.createDatatype(SUPERCLASS, ClassProperty.class, 2,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(SUPERINTERFACE, PropertyDescriptorSupport.createDatatype(SUPERINTERFACE, ClassProperty.class,
                3, PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(IMPLCLASS, PropertyDescriptorSupport.createDatatype(IMPLCLASS, ClassProperty.class, 4,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ClassExtension.getPropertyDescriptor(SUPERCLASS), this.getSuperClass(),
                null));
        properties.add(super.createProperty(ClassExtension.getPropertyDescriptor(SUPERINTERFACE),
                this.getSuperInterface(), null));
        properties
                .add(super.createProperty(ClassExtension.getPropertyDescriptor(IMPLCLASS), this.getImplClass(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(SUPERCLASS) && (property.getType() == ClassProperty.class))) {
            this.setSuperClass(((ClassProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SUPERINTERFACE) && (property.getType() == ClassProperty.class))) {
            this.setSuperInterface(((ClassProperty) property.getInstance()));
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
        final ClassExtension other = ((ClassExtension) obj);
        if ((this.superClass == null)) {
            if ((other.superClass != null))
                return false;
        } else if ((!this.superClass.equals(other.superClass)))
            return false;
        if ((this.superInterface == null)) {
            if ((other.superInterface != null))
                return false;
        } else if ((!this.superInterface.equals(other.superInterface)))
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
        result = ((PRIME * result) + ((this.superClass == null) ? 0 : this.superClass.hashCode()));
        result = ((PRIME * result) + ((this.superInterface == null) ? 0 : this.superInterface.hashCode()));
        result = ((PRIME * result) + ((this.implClass == null) ? 0 : this.implClass.hashCode()));
        return result;
    }

    @Override
    public ClassExtension cloneObject() {
        ClassExtension clone = new ClassExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The superclass of the implClass property.
     *
     * @param superClass the ClassProperty.
     */
    public void setSuperClass(ClassProperty superClass) {
        this.superClass = superClass;
    }

    /**
     * The superclass of the implClass property.
     *
     * @return the ClassProperty.
     */
    public ClassProperty getSuperClass() {
        return this.superClass;
    }

    /**
     * The superinterface of the implClass property.
     *
     * @param superInterface the ClassProperty.
     */
    public void setSuperInterface(ClassProperty superInterface) {
        this.superInterface = superInterface;
    }

    /**
     * The superinterface of the implClass property.
     *
     * @return the ClassProperty.
     */
    public ClassProperty getSuperInterface() {
        return this.superInterface;
    }

    /**
     * The implementation class.
     *
     * @param implClass the ClassProperty.
     */
    public void setImplClass(ClassProperty implClass) {
        this.implClass = implClass;
    }

    /**
     * The implementation class.
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
        return PropertyCache.getInstance().retrieve(ClassExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ClassExtension.class).getAllProperties();
    }
}
