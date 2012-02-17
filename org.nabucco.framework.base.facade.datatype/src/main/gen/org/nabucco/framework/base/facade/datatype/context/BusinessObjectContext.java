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
package org.nabucco.framework.base.facade.datatype.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.FullQualifiedClassName;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContextType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * BusinessObjectContext<p/>For transporting additional business Data.<p/>
 *
 * @version 1
 * @author Silas Schwarz, PRODYNA AG, 2011-06-07
 */
public class BusinessObjectContext extends ServiceSubContext implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final ServiceSubContextType CONTEXTTYPE_DEFAULT = ServiceSubContextType.BUSINESS_OBJECT;

    private static final String[] PROPERTY_CONSTRAINTS = { "l3,1000;u0,n;m1,1;", "l0,n;u0,n;m1,1;",
            "l0,255;u0,n;m0,1;", "l0,255;u0,n;m0,1;", "l0,255;u0,n;m0,1;" };

    public static final String FULLQUALIFIEDCLASSNAME = "fullQualifiedClassName";

    public static final String REFERENCEID = "referenceId";

    public static final String COMPONENTRELATIONTYPE = "componentRelationType";

    public static final String COMPONENT = "component";

    public static final String DESCRIPTOR = "descriptor";

    /** the referenced class type name */
    private FullQualifiedClassName fullQualifiedClassName;

    /** the unique id of the referenced object */
    private Identifier referenceId;

    /** the component relation type */
    private Name componentRelationType;

    /** the component name */
    private Name component;

    /** The description */
    private Name descriptor;

    /** Constructs a new BusinessObjectContext instance. */
    public BusinessObjectContext() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
        contextType = CONTEXTTYPE_DEFAULT;
    }

    /**
     * CloneObject.
     *
     * @param clone the BusinessObjectContext.
     */
    protected void cloneObject(BusinessObjectContext clone) {
        super.cloneObject(clone);
        clone.setContextType(this.getContextType());
        if ((this.getFullQualifiedClassName() != null)) {
            clone.setFullQualifiedClassName(this.getFullQualifiedClassName().cloneObject());
        }
        if ((this.getReferenceId() != null)) {
            clone.setReferenceId(this.getReferenceId().cloneObject());
        }
        if ((this.getComponentRelationType() != null)) {
            clone.setComponentRelationType(this.getComponentRelationType().cloneObject());
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
        propertyMap.putAll(PropertyCache.getInstance().retrieve(ServiceSubContext.class).getPropertyMap());
        propertyMap.put(FULLQUALIFIEDCLASSNAME, PropertyDescriptorSupport.createBasetype(FULLQUALIFIEDCLASSNAME,
                FullQualifiedClassName.class, 4, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(REFERENCEID, PropertyDescriptorSupport.createBasetype(REFERENCEID, Identifier.class, 5,
                PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(COMPONENTRELATIONTYPE, PropertyDescriptorSupport.createBasetype(COMPONENTRELATIONTYPE,
                Name.class, 6, PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(COMPONENT,
                PropertyDescriptorSupport.createBasetype(COMPONENT, Name.class, 7, PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(DESCRIPTOR,
                PropertyDescriptorSupport.createBasetype(DESCRIPTOR, Name.class, 8, PROPERTY_CONSTRAINTS[4], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(BusinessObjectContext.getPropertyDescriptor(FULLQUALIFIEDCLASSNAME),
                this.fullQualifiedClassName, null));
        properties.add(super.createProperty(BusinessObjectContext.getPropertyDescriptor(REFERENCEID), this.referenceId,
                null));
        properties.add(super.createProperty(BusinessObjectContext.getPropertyDescriptor(COMPONENTRELATIONTYPE),
                this.componentRelationType, null));
        properties.add(super.createProperty(BusinessObjectContext.getPropertyDescriptor(COMPONENT), this.component,
                null));
        properties.add(super.createProperty(BusinessObjectContext.getPropertyDescriptor(DESCRIPTOR), this.descriptor,
                null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(FULLQUALIFIEDCLASSNAME) && (property.getType() == FullQualifiedClassName.class))) {
            this.setFullQualifiedClassName(((FullQualifiedClassName) property.getInstance()));
            return true;
        } else if ((property.getName().equals(REFERENCEID) && (property.getType() == Identifier.class))) {
            this.setReferenceId(((Identifier) property.getInstance()));
            return true;
        } else if ((property.getName().equals(COMPONENTRELATIONTYPE) && (property.getType() == Name.class))) {
            this.setComponentRelationType(((Name) property.getInstance()));
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
        final BusinessObjectContext other = ((BusinessObjectContext) obj);
        if ((this.fullQualifiedClassName == null)) {
            if ((other.fullQualifiedClassName != null))
                return false;
        } else if ((!this.fullQualifiedClassName.equals(other.fullQualifiedClassName)))
            return false;
        if ((this.referenceId == null)) {
            if ((other.referenceId != null))
                return false;
        } else if ((!this.referenceId.equals(other.referenceId)))
            return false;
        if ((this.componentRelationType == null)) {
            if ((other.componentRelationType != null))
                return false;
        } else if ((!this.componentRelationType.equals(other.componentRelationType)))
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
        result = ((PRIME * result) + ((this.fullQualifiedClassName == null) ? 0 : this.fullQualifiedClassName
                .hashCode()));
        result = ((PRIME * result) + ((this.referenceId == null) ? 0 : this.referenceId.hashCode()));
        result = ((PRIME * result) + ((this.componentRelationType == null) ? 0 : this.componentRelationType.hashCode()));
        result = ((PRIME * result) + ((this.component == null) ? 0 : this.component.hashCode()));
        result = ((PRIME * result) + ((this.descriptor == null) ? 0 : this.descriptor.hashCode()));
        return result;
    }

    @Override
    public BusinessObjectContext cloneObject() {
        BusinessObjectContext clone = new BusinessObjectContext();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * the referenced class type name
     *
     * @return the FullQualifiedClassName.
     */
    public FullQualifiedClassName getFullQualifiedClassName() {
        return this.fullQualifiedClassName;
    }

    /**
     * the referenced class type name
     *
     * @param fullQualifiedClassName the FullQualifiedClassName.
     */
    public void setFullQualifiedClassName(FullQualifiedClassName fullQualifiedClassName) {
        this.fullQualifiedClassName = fullQualifiedClassName;
    }

    /**
     * the referenced class type name
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
     * the unique id of the referenced object
     *
     * @return the Identifier.
     */
    public Identifier getReferenceId() {
        return this.referenceId;
    }

    /**
     * the unique id of the referenced object
     *
     * @param referenceId the Identifier.
     */
    public void setReferenceId(Identifier referenceId) {
        this.referenceId = referenceId;
    }

    /**
     * the unique id of the referenced object
     *
     * @param referenceId the Long.
     */
    public void setReferenceId(Long referenceId) {
        if ((this.referenceId == null)) {
            if ((referenceId == null)) {
                return;
            }
            this.referenceId = new Identifier();
        }
        this.referenceId.setValue(referenceId);
    }

    /**
     * the component relation type
     *
     * @return the Name.
     */
    public Name getComponentRelationType() {
        return this.componentRelationType;
    }

    /**
     * the component relation type
     *
     * @param componentRelationType the Name.
     */
    public void setComponentRelationType(Name componentRelationType) {
        this.componentRelationType = componentRelationType;
    }

    /**
     * the component relation type
     *
     * @param componentRelationType the String.
     */
    public void setComponentRelationType(String componentRelationType) {
        if ((this.componentRelationType == null)) {
            if ((componentRelationType == null)) {
                return;
            }
            this.componentRelationType = new Name();
        }
        this.componentRelationType.setValue(componentRelationType);
    }

    /**
     * the component name
     *
     * @return the Name.
     */
    public Name getComponent() {
        return this.component;
    }

    /**
     * the component name
     *
     * @param component the Name.
     */
    public void setComponent(Name component) {
        this.component = component;
    }

    /**
     * the component name
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
     * The description
     *
     * @return the Name.
     */
    public Name getDescriptor() {
        return this.descriptor;
    }

    /**
     * The description
     *
     * @param descriptor the Name.
     */
    public void setDescriptor(Name descriptor) {
        this.descriptor = descriptor;
    }

    /**
     * The description
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
        return PropertyCache.getInstance().retrieve(BusinessObjectContext.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(BusinessObjectContext.class).getAllProperties();
    }
}
