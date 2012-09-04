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
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * BusinessSchemaAssociationRole<p/>Role of a business schema association.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-06-26
 */
public class BusinessSchemaAssociationRole extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;" };

    public static final String ID = "id";

    public static final String MULTIPLICITY = "multiplicity";

    /** The referenced business object id. */
    private StringProperty id;

    /** The association type. */
    private EnumerationProperty multiplicity;

    /** Constructs a new BusinessSchemaAssociationRole instance. */
    public BusinessSchemaAssociationRole() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the BusinessSchemaAssociationRole.
     */
    protected void cloneObject(BusinessSchemaAssociationRole clone) {
        super.cloneObject(clone);
        if ((this.getId() != null)) {
            clone.setId(this.getId().cloneObject());
        }
        if ((this.getMultiplicity() != null)) {
            clone.setMultiplicity(this.getMultiplicity().cloneObject());
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
        propertyMap.put(MULTIPLICITY, PropertyDescriptorSupport.createDatatype(MULTIPLICITY, EnumerationProperty.class,
                3, PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
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
                .add(super.createProperty(BusinessSchemaAssociationRole.getPropertyDescriptor(ID), this.getId(), null));
        properties.add(super.createProperty(BusinessSchemaAssociationRole.getPropertyDescriptor(MULTIPLICITY),
                this.getMultiplicity(), null));
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
        } else if ((property.getName().equals(MULTIPLICITY) && (property.getType() == EnumerationProperty.class))) {
            this.setMultiplicity(((EnumerationProperty) property.getInstance()));
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
        final BusinessSchemaAssociationRole other = ((BusinessSchemaAssociationRole) obj);
        if ((this.id == null)) {
            if ((other.id != null))
                return false;
        } else if ((!this.id.equals(other.id)))
            return false;
        if ((this.multiplicity == null)) {
            if ((other.multiplicity != null))
                return false;
        } else if ((!this.multiplicity.equals(other.multiplicity)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.id == null) ? 0 : this.id.hashCode()));
        result = ((PRIME * result) + ((this.multiplicity == null) ? 0 : this.multiplicity.hashCode()));
        return result;
    }

    @Override
    public BusinessSchemaAssociationRole cloneObject() {
        BusinessSchemaAssociationRole clone = new BusinessSchemaAssociationRole();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The referenced business object id.
     *
     * @param id the StringProperty.
     */
    public void setId(StringProperty id) {
        this.id = id;
    }

    /**
     * The referenced business object id.
     *
     * @return the StringProperty.
     */
    public StringProperty getId() {
        return this.id;
    }

    /**
     * The association type.
     *
     * @param multiplicity the EnumerationProperty.
     */
    public void setMultiplicity(EnumerationProperty multiplicity) {
        this.multiplicity = multiplicity;
    }

    /**
     * The association type.
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getMultiplicity() {
        return this.multiplicity;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(BusinessSchemaAssociationRole.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(BusinessSchemaAssociationRole.class).getAllProperties();
    }
}
