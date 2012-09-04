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
package org.nabucco.framework.base.facade.datatype.extension.schema.aspect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.property.ClassProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.IntegerProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * AspectExtension<p/>Aspect extension.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-03-14
 */
public class AspectExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m1,1;" };

    public static final String NAME = "name";

    public static final String POINT = "point";

    public static final String IMPLCLASS = "implClass";

    public static final String INTERFACECLASS = "interfaceClass";

    public static final String SLOTS = "slots";

    /** The Aspect Name. */
    private StringProperty name;

    /** The Aspect Extension Point. */
    private StringProperty point;

    /** The Aspect Implementation. */
    private ClassProperty implClass;

    /** The Aspect Interface. */
    private ClassProperty interfaceClass;

    /** The Aspect Slots. */
    private IntegerProperty slots;

    /** Constructs a new AspectExtension instance. */
    public AspectExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the AspectExtension.
     */
    protected void cloneObject(AspectExtension clone) {
        super.cloneObject(clone);
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getPoint() != null)) {
            clone.setPoint(this.getPoint().cloneObject());
        }
        if ((this.getImplClass() != null)) {
            clone.setImplClass(this.getImplClass().cloneObject());
        }
        if ((this.getInterfaceClass() != null)) {
            clone.setInterfaceClass(this.getInterfaceClass().cloneObject());
        }
        if ((this.getSlots() != null)) {
            clone.setSlots(this.getSlots().cloneObject());
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
        propertyMap.put(POINT, PropertyDescriptorSupport.createDatatype(POINT, StringProperty.class, 3,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(IMPLCLASS, PropertyDescriptorSupport.createDatatype(IMPLCLASS, ClassProperty.class, 4,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(INTERFACECLASS, PropertyDescriptorSupport.createDatatype(INTERFACECLASS, ClassProperty.class,
                5, PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(SLOTS, PropertyDescriptorSupport.createDatatype(SLOTS, IntegerProperty.class, 6,
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
        properties.add(super.createProperty(AspectExtension.getPropertyDescriptor(NAME), this.getName(), null));
        properties.add(super.createProperty(AspectExtension.getPropertyDescriptor(POINT), this.getPoint(), null));
        properties
                .add(super.createProperty(AspectExtension.getPropertyDescriptor(IMPLCLASS), this.getImplClass(), null));
        properties.add(super.createProperty(AspectExtension.getPropertyDescriptor(INTERFACECLASS),
                this.getInterfaceClass(), null));
        properties.add(super.createProperty(AspectExtension.getPropertyDescriptor(SLOTS), this.getSlots(), null));
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
        } else if ((property.getName().equals(POINT) && (property.getType() == StringProperty.class))) {
            this.setPoint(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(IMPLCLASS) && (property.getType() == ClassProperty.class))) {
            this.setImplClass(((ClassProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(INTERFACECLASS) && (property.getType() == ClassProperty.class))) {
            this.setInterfaceClass(((ClassProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SLOTS) && (property.getType() == IntegerProperty.class))) {
            this.setSlots(((IntegerProperty) property.getInstance()));
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
        final AspectExtension other = ((AspectExtension) obj);
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        if ((this.point == null)) {
            if ((other.point != null))
                return false;
        } else if ((!this.point.equals(other.point)))
            return false;
        if ((this.implClass == null)) {
            if ((other.implClass != null))
                return false;
        } else if ((!this.implClass.equals(other.implClass)))
            return false;
        if ((this.interfaceClass == null)) {
            if ((other.interfaceClass != null))
                return false;
        } else if ((!this.interfaceClass.equals(other.interfaceClass)))
            return false;
        if ((this.slots == null)) {
            if ((other.slots != null))
                return false;
        } else if ((!this.slots.equals(other.slots)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.point == null) ? 0 : this.point.hashCode()));
        result = ((PRIME * result) + ((this.implClass == null) ? 0 : this.implClass.hashCode()));
        result = ((PRIME * result) + ((this.interfaceClass == null) ? 0 : this.interfaceClass.hashCode()));
        result = ((PRIME * result) + ((this.slots == null) ? 0 : this.slots.hashCode()));
        return result;
    }

    @Override
    public AspectExtension cloneObject() {
        AspectExtension clone = new AspectExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The Aspect Name.
     *
     * @param name the StringProperty.
     */
    public void setName(StringProperty name) {
        this.name = name;
    }

    /**
     * The Aspect Name.
     *
     * @return the StringProperty.
     */
    public StringProperty getName() {
        return this.name;
    }

    /**
     * The Aspect Extension Point.
     *
     * @param point the StringProperty.
     */
    public void setPoint(StringProperty point) {
        this.point = point;
    }

    /**
     * The Aspect Extension Point.
     *
     * @return the StringProperty.
     */
    public StringProperty getPoint() {
        return this.point;
    }

    /**
     * The Aspect Implementation.
     *
     * @param implClass the ClassProperty.
     */
    public void setImplClass(ClassProperty implClass) {
        this.implClass = implClass;
    }

    /**
     * The Aspect Implementation.
     *
     * @return the ClassProperty.
     */
    public ClassProperty getImplClass() {
        return this.implClass;
    }

    /**
     * The Aspect Interface.
     *
     * @param interfaceClass the ClassProperty.
     */
    public void setInterfaceClass(ClassProperty interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    /**
     * The Aspect Interface.
     *
     * @return the ClassProperty.
     */
    public ClassProperty getInterfaceClass() {
        return this.interfaceClass;
    }

    /**
     * The Aspect Slots.
     *
     * @param slots the IntegerProperty.
     */
    public void setSlots(IntegerProperty slots) {
        this.slots = slots;
    }

    /**
     * The Aspect Slots.
     *
     * @return the IntegerProperty.
     */
    public IntegerProperty getSlots() {
        return this.slots;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(AspectExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(AspectExtension.class).getAllProperties();
    }
}
