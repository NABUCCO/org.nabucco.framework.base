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
package org.nabucco.framework.base.facade.datatype;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Tenant;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * MultiTenantDatatype<p/>Common datatype for multitenant datatypes.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-05-27
 */
public abstract class MultiTenantDatatype extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l3,12;u0,n;m1,1;" };

    public static final String TENANT = "tenant";

    /** The tenant of the datatype. Default is NABUCCO. */
    private Tenant tenant;

    /** Constructs a new MultiTenantDatatype instance. */
    public MultiTenantDatatype() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the MultiTenantDatatype.
     */
    protected void cloneObject(MultiTenantDatatype clone) {
        super.cloneObject(clone);
        if ((this.getTenant() != null)) {
            clone.setTenant(this.getTenant().cloneObject());
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
        propertyMap.put(TENANT,
                PropertyDescriptorSupport.createBasetype(TENANT, Tenant.class, 3, PROPERTY_CONSTRAINTS[0], true));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(MultiTenantDatatype.getPropertyDescriptor(TENANT), this.tenant, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(TENANT) && (property.getType() == Tenant.class))) {
            this.setTenant(((Tenant) property.getInstance()));
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
        final MultiTenantDatatype other = ((MultiTenantDatatype) obj);
        if ((this.tenant == null)) {
            if ((other.tenant != null))
                return false;
        } else if ((!this.tenant.equals(other.tenant)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.tenant == null) ? 0 : this.tenant.hashCode()));
        return result;
    }

    @Override
    public abstract MultiTenantDatatype cloneObject();

    /**
     * The tenant of the datatype. Default is NABUCCO.
     *
     * @return the Tenant.
     */
    public Tenant getTenant() {
        return this.tenant;
    }

    /**
     * The tenant of the datatype. Default is NABUCCO.
     *
     * @param tenant the Tenant.
     */
    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    /**
     * The tenant of the datatype. Default is NABUCCO.
     *
     * @param tenant the String.
     */
    public void setTenant(String tenant) {
        if ((this.tenant == null)) {
            if ((tenant == null)) {
                return;
            }
            this.tenant = new Tenant();
        }
        this.tenant.setValue(tenant);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(MultiTenantDatatype.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(MultiTenantDatatype.class).getAllProperties();
    }
}
