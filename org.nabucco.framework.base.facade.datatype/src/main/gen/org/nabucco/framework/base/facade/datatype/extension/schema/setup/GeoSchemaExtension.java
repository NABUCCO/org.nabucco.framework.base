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
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.GeoRegionExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * GeoSchemaExtension<p/>Configuration for geo regions.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-05-06
 */
public class GeoSchemaExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m0,n;" };

    public static final String LOCALE = "locale";

    public static final String GEOREGIONLIST = "geoRegionList";

    /** The Geo Schema Locale. */
    private StringProperty locale;

    /** The List of Geo Regions. */
    private NabuccoList<GeoRegionExtension> geoRegionList;

    /** Constructs a new GeoSchemaExtension instance. */
    public GeoSchemaExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the GeoSchemaExtension.
     */
    protected void cloneObject(GeoSchemaExtension clone) {
        super.cloneObject(clone);
        if ((this.getLocale() != null)) {
            clone.setLocale(this.getLocale().cloneObject());
        }
        if ((this.geoRegionList != null)) {
            clone.geoRegionList = this.geoRegionList.cloneCollection();
        }
    }

    /**
     * Getter for the GeoRegionListJPA.
     *
     * @return the List<GeoRegionExtension>.
     */
    List<GeoRegionExtension> getGeoRegionListJPA() {
        if ((this.geoRegionList == null)) {
            this.geoRegionList = new NabuccoListImpl<GeoRegionExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<GeoRegionExtension>) this.geoRegionList).getDelegate();
    }

    /**
     * Setter for the GeoRegionListJPA.
     *
     * @param geoRegionList the List<GeoRegionExtension>.
     */
    void setGeoRegionListJPA(List<GeoRegionExtension> geoRegionList) {
        if ((this.geoRegionList == null)) {
            this.geoRegionList = new NabuccoListImpl<GeoRegionExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<GeoRegionExtension>) this.geoRegionList).setDelegate(geoRegionList);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoExtensionComposite.class).getPropertyMap());
        propertyMap.put(LOCALE, PropertyDescriptorSupport.createDatatype(LOCALE, StringProperty.class, 2,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(GEOREGIONLIST, PropertyDescriptorSupport.createCollection(GEOREGIONLIST,
                GeoRegionExtension.class, 3, PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(GeoSchemaExtension.getPropertyDescriptor(LOCALE), this.getLocale(), null));
        properties.add(super.createProperty(GeoSchemaExtension.getPropertyDescriptor(GEOREGIONLIST),
                this.geoRegionList, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(LOCALE) && (property.getType() == StringProperty.class))) {
            this.setLocale(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(GEOREGIONLIST) && (property.getType() == GeoRegionExtension.class))) {
            this.geoRegionList = ((NabuccoList<GeoRegionExtension>) property.getInstance());
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
        final GeoSchemaExtension other = ((GeoSchemaExtension) obj);
        if ((this.locale == null)) {
            if ((other.locale != null))
                return false;
        } else if ((!this.locale.equals(other.locale)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.locale == null) ? 0 : this.locale.hashCode()));
        return result;
    }

    @Override
    public GeoSchemaExtension cloneObject() {
        GeoSchemaExtension clone = new GeoSchemaExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The Geo Schema Locale.
     *
     * @param locale the StringProperty.
     */
    public void setLocale(StringProperty locale) {
        this.locale = locale;
    }

    /**
     * The Geo Schema Locale.
     *
     * @return the StringProperty.
     */
    public StringProperty getLocale() {
        return this.locale;
    }

    /**
     * The List of Geo Regions.
     *
     * @return the NabuccoList<GeoRegionExtension>.
     */
    public NabuccoList<GeoRegionExtension> getGeoRegionList() {
        if ((this.geoRegionList == null)) {
            this.geoRegionList = new NabuccoListImpl<GeoRegionExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.geoRegionList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(GeoSchemaExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(GeoSchemaExtension.class).getAllProperties();
    }
}
