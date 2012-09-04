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
package org.nabucco.framework.base.facade.datatype.extension.schema.setup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.property.BooleanProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * GeoRegionExtension<p/>Configuration for geo regions.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-05-06
 */
public class GeoRegionExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;" };

    public static final String ID = "id";

    public static final String TYPE = "type";

    public static final String OPTIONAL = "optional";

    /** The geo region id */
    private StringProperty id;

    /** The geo region type */
    private StringProperty type;

    /** The geo region optional flag */
    private BooleanProperty optional;

    /** Constructs a new GeoRegionExtension instance. */
    public GeoRegionExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the GeoRegionExtension.
     */
    protected void cloneObject(GeoRegionExtension clone) {
        super.cloneObject(clone);
        if ((this.getId() != null)) {
            clone.setId(this.getId().cloneObject());
        }
        if ((this.getType() != null)) {
            clone.setType(this.getType().cloneObject());
        }
        if ((this.getOptional() != null)) {
            clone.setOptional(this.getOptional().cloneObject());
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
        propertyMap.put(TYPE, PropertyDescriptorSupport.createDatatype(TYPE, StringProperty.class, 3,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(OPTIONAL, PropertyDescriptorSupport.createDatatype(OPTIONAL, BooleanProperty.class, 4,
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
        properties.add(super.createProperty(GeoRegionExtension.getPropertyDescriptor(ID), this.getId(), null));
        properties.add(super.createProperty(GeoRegionExtension.getPropertyDescriptor(TYPE), this.getType(), null));
        properties.add(super.createProperty(GeoRegionExtension.getPropertyDescriptor(OPTIONAL), this.getOptional(),
                null));
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
        } else if ((property.getName().equals(TYPE) && (property.getType() == StringProperty.class))) {
            this.setType(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(OPTIONAL) && (property.getType() == BooleanProperty.class))) {
            this.setOptional(((BooleanProperty) property.getInstance()));
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
        final GeoRegionExtension other = ((GeoRegionExtension) obj);
        if ((this.id == null)) {
            if ((other.id != null))
                return false;
        } else if ((!this.id.equals(other.id)))
            return false;
        if ((this.type == null)) {
            if ((other.type != null))
                return false;
        } else if ((!this.type.equals(other.type)))
            return false;
        if ((this.optional == null)) {
            if ((other.optional != null))
                return false;
        } else if ((!this.optional.equals(other.optional)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.id == null) ? 0 : this.id.hashCode()));
        result = ((PRIME * result) + ((this.type == null) ? 0 : this.type.hashCode()));
        result = ((PRIME * result) + ((this.optional == null) ? 0 : this.optional.hashCode()));
        return result;
    }

    @Override
    public GeoRegionExtension cloneObject() {
        GeoRegionExtension clone = new GeoRegionExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The geo region id
     *
     * @param id the StringProperty.
     */
    public void setId(StringProperty id) {
        this.id = id;
    }

    /**
     * The geo region id
     *
     * @return the StringProperty.
     */
    public StringProperty getId() {
        return this.id;
    }

    /**
     * The geo region type
     *
     * @param type the StringProperty.
     */
    public void setType(StringProperty type) {
        this.type = type;
    }

    /**
     * The geo region type
     *
     * @return the StringProperty.
     */
    public StringProperty getType() {
        return this.type;
    }

    /**
     * The geo region optional flag
     *
     * @param optional the BooleanProperty.
     */
    public void setOptional(BooleanProperty optional) {
        this.optional = optional;
    }

    /**
     * The geo region optional flag
     *
     * @return the BooleanProperty.
     */
    public BooleanProperty getOptional() {
        return this.optional;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(GeoRegionExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(GeoRegionExtension.class).getAllProperties();
    }
}
