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
package org.nabucco.framework.base.facade.datatype.extension.schema.setup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.property.IntegerProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.GeoSchemaExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * GeoConfigExtension<p/>Configuration for geo regions.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-05-06
 */
public class GeoConfigExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m0,n;" };

    public static final String MAXENTRIES = "maxEntries";

    public static final String GEOSCHEMALIST = "geoSchemaList";

    /** The maximum amount of entries. */
    private IntegerProperty maxEntries;

    /** The list of geo schema extensions. */
    private NabuccoList<GeoSchemaExtension> geoSchemaList;

    /** Constructs a new GeoConfigExtension instance. */
    public GeoConfigExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the GeoConfigExtension.
     */
    protected void cloneObject(GeoConfigExtension clone) {
        super.cloneObject(clone);
        if ((this.getMaxEntries() != null)) {
            clone.setMaxEntries(this.getMaxEntries().cloneObject());
        }
        if ((this.geoSchemaList != null)) {
            clone.geoSchemaList = this.geoSchemaList.cloneCollection();
        }
    }

    /**
     * Getter for the GeoSchemaListJPA.
     *
     * @return the List<GeoSchemaExtension>.
     */
    List<GeoSchemaExtension> getGeoSchemaListJPA() {
        if ((this.geoSchemaList == null)) {
            this.geoSchemaList = new NabuccoListImpl<GeoSchemaExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<GeoSchemaExtension>) this.geoSchemaList).getDelegate();
    }

    /**
     * Setter for the GeoSchemaListJPA.
     *
     * @param geoSchemaList the List<GeoSchemaExtension>.
     */
    void setGeoSchemaListJPA(List<GeoSchemaExtension> geoSchemaList) {
        if ((this.geoSchemaList == null)) {
            this.geoSchemaList = new NabuccoListImpl<GeoSchemaExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<GeoSchemaExtension>) this.geoSchemaList).setDelegate(geoSchemaList);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoExtensionComposite.class).getPropertyMap());
        propertyMap.put(MAXENTRIES, PropertyDescriptorSupport.createDatatype(MAXENTRIES, IntegerProperty.class, 2,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(GEOSCHEMALIST, PropertyDescriptorSupport.createCollection(GEOSCHEMALIST,
                GeoSchemaExtension.class, 3, PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(GeoConfigExtension.getPropertyDescriptor(MAXENTRIES), this.getMaxEntries(),
                null));
        properties.add(super.createProperty(GeoConfigExtension.getPropertyDescriptor(GEOSCHEMALIST),
                this.geoSchemaList, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(MAXENTRIES) && (property.getType() == IntegerProperty.class))) {
            this.setMaxEntries(((IntegerProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(GEOSCHEMALIST) && (property.getType() == GeoSchemaExtension.class))) {
            this.geoSchemaList = ((NabuccoList<GeoSchemaExtension>) property.getInstance());
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
        final GeoConfigExtension other = ((GeoConfigExtension) obj);
        if ((this.maxEntries == null)) {
            if ((other.maxEntries != null))
                return false;
        } else if ((!this.maxEntries.equals(other.maxEntries)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.maxEntries == null) ? 0 : this.maxEntries.hashCode()));
        return result;
    }

    @Override
    public GeoConfigExtension cloneObject() {
        GeoConfigExtension clone = new GeoConfigExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The maximum amount of entries.
     *
     * @param maxEntries the IntegerProperty.
     */
    public void setMaxEntries(IntegerProperty maxEntries) {
        this.maxEntries = maxEntries;
    }

    /**
     * The maximum amount of entries.
     *
     * @return the IntegerProperty.
     */
    public IntegerProperty getMaxEntries() {
        return this.maxEntries;
    }

    /**
     * The list of geo schema extensions.
     *
     * @return the NabuccoList<GeoSchemaExtension>.
     */
    public NabuccoList<GeoSchemaExtension> getGeoSchemaList() {
        if ((this.geoSchemaList == null)) {
            this.geoSchemaList = new NabuccoListImpl<GeoSchemaExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.geoSchemaList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(GeoConfigExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(GeoConfigExtension.class).getAllProperties();
    }
}
