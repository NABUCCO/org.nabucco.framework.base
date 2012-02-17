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
package org.nabucco.framework.base.facade.datatype;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.FullQualifiedClassName;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * DatatypeIdentifier<p/>Identifies a datatype.<p/>
 *
 * @version 1.0
 * @version 1.0
 * @author Markus Jorroch, PRODYNA AG, 2011-01-29
 */
public class DatatypeIdentifier extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;u0,n;m1,1;", "l3,1000;u0,n;m0,1;",
            "l0,255;u0,n;m0,1;", "l0,255;u0,n;m0,1;" };

    public static final String IDENTIFIER = "identifier";

    public static final String FULLQUALIFIEDCLASSNAME = "fullQualifiedClassName";

    public static final String COMPONENT = "component";

    public static final String DESCRIPTOR = "descriptor";

    /** The datatype ID. */
    private Identifier identifier;

    /** The datatype classname. */
    private FullQualifiedClassName fullQualifiedClassName;

    /** The datatype component name. */
    private Name component;

    /** The datatype descriptor. */
    private Name descriptor;

    /** Constructs a new DatatypeIdentifier instance. */
    public DatatypeIdentifier() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the DatatypeIdentifier.
     */
    protected void cloneObject(DatatypeIdentifier clone) {
        super.cloneObject(clone);
        if ((this.getIdentifier() != null)) {
            clone.setIdentifier(this.getIdentifier().cloneObject());
        }
        if ((this.getFullQualifiedClassName() != null)) {
            clone.setFullQualifiedClassName(this.getFullQualifiedClassName().cloneObject());
        }
        if ((this.getComponent() != null)) {
            clone.setComponent(this.getComponent().cloneObject());
        }
        if ((this.getDescriptor() != null)) {
            clone.setDescriptor(this.getDescriptor().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoDatatype.class).getPropertyMap());
        propertyMap.put(IDENTIFIER, PropertyDescriptorSupport.createBasetype(IDENTIFIER, Identifier.class, 3,
                PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(FULLQUALIFIEDCLASSNAME, PropertyDescriptorSupport.createBasetype(FULLQUALIFIEDCLASSNAME,
                FullQualifiedClassName.class, 4, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(COMPONENT,
                PropertyDescriptorSupport.createBasetype(COMPONENT, Name.class, 5, PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(DESCRIPTOR,
                PropertyDescriptorSupport.createBasetype(DESCRIPTOR, Name.class, 6, PROPERTY_CONSTRAINTS[3], false));
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
                .add(super.createProperty(DatatypeIdentifier.getPropertyDescriptor(IDENTIFIER), this.identifier, null));
        properties.add(super.createProperty(DatatypeIdentifier.getPropertyDescriptor(FULLQUALIFIEDCLASSNAME),
                this.fullQualifiedClassName, null));
        properties.add(super.createProperty(DatatypeIdentifier.getPropertyDescriptor(COMPONENT), this.component, null));
        properties
                .add(super.createProperty(DatatypeIdentifier.getPropertyDescriptor(DESCRIPTOR), this.descriptor, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(IDENTIFIER) && (property.getType() == Identifier.class))) {
            this.setIdentifier(((Identifier) property.getInstance()));
            return true;
        } else if ((property.getName().equals(FULLQUALIFIEDCLASSNAME) && (property.getType() == FullQualifiedClassName.class))) {
            this.setFullQualifiedClassName(((FullQualifiedClassName) property.getInstance()));
            return true;
        } else if ((property.getName().equals(COMPONENT) && (property.getType() == Name.class))) {
            this.setComponent(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DESCRIPTOR) && (property.getType() == Name.class))) {
            this.setDescriptor(((Name) property.getInstance()));
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
        final DatatypeIdentifier other = ((DatatypeIdentifier) obj);
        if ((this.identifier == null)) {
            if ((other.identifier != null))
                return false;
        } else if ((!this.identifier.equals(other.identifier)))
            return false;
        if ((this.fullQualifiedClassName == null)) {
            if ((other.fullQualifiedClassName != null))
                return false;
        } else if ((!this.fullQualifiedClassName.equals(other.fullQualifiedClassName)))
            return false;
        if ((this.component == null)) {
            if ((other.component != null))
                return false;
        } else if ((!this.component.equals(other.component)))
            return false;
        if ((this.descriptor == null)) {
            if ((other.descriptor != null))
                return false;
        } else if ((!this.descriptor.equals(other.descriptor)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.identifier == null) ? 0 : this.identifier.hashCode()));
        result = ((PRIME * result) + ((this.fullQualifiedClassName == null) ? 0 : this.fullQualifiedClassName
                .hashCode()));
        result = ((PRIME * result) + ((this.component == null) ? 0 : this.component.hashCode()));
        result = ((PRIME * result) + ((this.descriptor == null) ? 0 : this.descriptor.hashCode()));
        return result;
    }

    @Override
    public DatatypeIdentifier cloneObject() {
        DatatypeIdentifier clone = new DatatypeIdentifier();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The datatype ID.
     *
     * @return the Identifier.
     */
    public Identifier getIdentifier() {
        return this.identifier;
    }

    /**
     * The datatype ID.
     *
     * @param identifier the Identifier.
     */
    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    /**
     * The datatype ID.
     *
     * @param identifier the Long.
     */
    public void setIdentifier(Long identifier) {
        if ((this.identifier == null)) {
            if ((identifier == null)) {
                return;
            }
            this.identifier = new Identifier();
        }
        this.identifier.setValue(identifier);
    }

    /**
     * The datatype classname.
     *
     * @return the FullQualifiedClassName.
     */
    public FullQualifiedClassName getFullQualifiedClassName() {
        return this.fullQualifiedClassName;
    }

    /**
     * The datatype classname.
     *
     * @param fullQualifiedClassName the FullQualifiedClassName.
     */
    public void setFullQualifiedClassName(FullQualifiedClassName fullQualifiedClassName) {
        this.fullQualifiedClassName = fullQualifiedClassName;
    }

    /**
     * The datatype classname.
     *
     * @param fullQualifiedClassName the String.
     */
    public void setFullQualifiedClassName(String fullQualifiedClassName) {
        if ((this.fullQualifiedClassName == null)) {
            if ((fullQualifiedClassName == null)) {
                return;
            }
            this.fullQualifiedClassName = new FullQualifiedClassName();
        }
        this.fullQualifiedClassName.setValue(fullQualifiedClassName);
    }

    /**
     * The datatype component name.
     *
     * @return the Name.
     */
    public Name getComponent() {
        return this.component;
    }

    /**
     * The datatype component name.
     *
     * @param component the Name.
     */
    public void setComponent(Name component) {
        this.component = component;
    }

    /**
     * The datatype component name.
     *
     * @param component the String.
     */
    public void setComponent(String component) {
        if ((this.component == null)) {
            if ((component == null)) {
                return;
            }
            this.component = new Name();
        }
        this.component.setValue(component);
    }

    /**
     * The datatype descriptor.
     *
     * @return the Name.
     */
    public Name getDescriptor() {
        return this.descriptor;
    }

    /**
     * The datatype descriptor.
     *
     * @param descriptor the Name.
     */
    public void setDescriptor(Name descriptor) {
        this.descriptor = descriptor;
    }

    /**
     * The datatype descriptor.
     *
     * @param descriptor the String.
     */
    public void setDescriptor(String descriptor) {
        if ((this.descriptor == null)) {
            if ((descriptor == null)) {
                return;
            }
            this.descriptor = new Name();
        }
        this.descriptor.setValue(descriptor);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(DatatypeIdentifier.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(DatatypeIdentifier.class).getAllProperties();
    }
}
