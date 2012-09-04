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
package org.nabucco.framework.base.facade.datatype.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * ServiceSubContext<p/>Service Sub Context<p/>
 *
 * @version 1
 * @author Silas Schwarz, PRODYNA AG, 2011-05-11
 */
public abstract class ServiceSubContext extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;" };

    public static final String CONTEXTTYPE = "contextType";

    /** The sub context type. */
    protected ServiceSubContextType contextType;

    /** Constructs a new ServiceSubContext instance. */
    public ServiceSubContext() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ServiceSubContext.
     */
    protected void cloneObject(ServiceSubContext clone) {
        super.cloneObject(clone);
        clone.setContextType(this.getContextType());
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoDatatype.class).getPropertyMap());
        propertyMap.put(CONTEXTTYPE, PropertyDescriptorSupport.createEnumeration(CONTEXTTYPE,
                ServiceSubContextType.class, 3, PROPERTY_CONSTRAINTS[0], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ServiceSubContext.getPropertyDescriptor(CONTEXTTYPE),
                this.getContextType(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(CONTEXTTYPE) && (property.getType() == ServiceSubContextType.class))) {
            this.setContextType(((ServiceSubContextType) property.getInstance()));
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
        final ServiceSubContext other = ((ServiceSubContext) obj);
        if ((this.contextType == null)) {
            if ((other.contextType != null))
                return false;
        } else if ((!this.contextType.equals(other.contextType)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.contextType == null) ? 0 : this.contextType.hashCode()));
        return result;
    }

    @Override
    public abstract ServiceSubContext cloneObject();

    /**
     * The sub context type.
     *
     * @return the ServiceSubContextType.
     */
    public ServiceSubContextType getContextType() {
        return this.contextType;
    }

    /**
     * The sub context type.
     *
     * @param contextType the ServiceSubContextType.
     */
    public void setContextType(ServiceSubContextType contextType) {
        this.contextType = contextType;
    }

    /**
     * The sub context type.
     *
     * @param contextType the String.
     */
    public void setContextType(String contextType) {
        if ((contextType == null)) {
            this.contextType = null;
        } else {
            this.contextType = ServiceSubContextType.valueOf(contextType);
        }
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ServiceSubContext.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ServiceSubContext.class).getAllProperties();
    }
}
