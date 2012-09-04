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
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContextType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * InitializationDetailContext<p/>For transporting of Initialization type.<p/>
 *
 * @version 1
 * @author Leonid Agranovskiy, PRODYNA AG, 2011-05-11
 */
public class InitializationDetailContext extends ServiceSubContext implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final ServiceSubContextType CONTEXTTYPE_DEFAULT = ServiceSubContextType.INITIALIZATION_DETAIL;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m0,1;" };

    public static final String INITIALIZATIONTYPENAME = "initializationTypeName";

    /** Instanciation detail name */
    private Name initializationTypeName;

    /** Constructs a new InitializationDetailContext instance. */
    public InitializationDetailContext() {
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
     * @param clone the InitializationDetailContext.
     */
    protected void cloneObject(InitializationDetailContext clone) {
        super.cloneObject(clone);
        clone.setContextType(this.getContextType());
        if ((this.getInitializationTypeName() != null)) {
            clone.setInitializationTypeName(this.getInitializationTypeName().cloneObject());
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
        propertyMap.put(INITIALIZATIONTYPENAME, PropertyDescriptorSupport.createBasetype(INITIALIZATIONTYPENAME,
                Name.class, 4, PROPERTY_CONSTRAINTS[0], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(InitializationDetailContext.getPropertyDescriptor(INITIALIZATIONTYPENAME),
                this.initializationTypeName, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(INITIALIZATIONTYPENAME) && (property.getType() == Name.class))) {
            this.setInitializationTypeName(((Name) property.getInstance()));
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
        final InitializationDetailContext other = ((InitializationDetailContext) obj);
        if ((this.initializationTypeName == null)) {
            if ((other.initializationTypeName != null))
                return false;
        } else if ((!this.initializationTypeName.equals(other.initializationTypeName)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.initializationTypeName == null) ? 0 : this.initializationTypeName
                .hashCode()));
        return result;
    }

    @Override
    public InitializationDetailContext cloneObject() {
        InitializationDetailContext clone = new InitializationDetailContext();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Instanciation detail name
     *
     * @return the Name.
     */
    public Name getInitializationTypeName() {
        return this.initializationTypeName;
    }

    /**
     * Instanciation detail name
     *
     * @param initializationTypeName the Name.
     */
    public void setInitializationTypeName(Name initializationTypeName) {
        this.initializationTypeName = initializationTypeName;
    }

    /**
     * Instanciation detail name
     *
     * @param initializationTypeName the String.
     */
    public void setInitializationTypeName(String initializationTypeName) {
        if ((this.initializationTypeName == null)) {
            if ((initializationTypeName == null)) {
                return;
            }
            this.initializationTypeName = new Name();
        }
        this.initializationTypeName.setValue(initializationTypeName);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(InitializationDetailContext.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(InitializationDetailContext.class).getAllProperties();
    }
}
