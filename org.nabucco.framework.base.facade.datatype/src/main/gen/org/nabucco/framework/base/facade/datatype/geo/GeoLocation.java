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
package org.nabucco.framework.base.facade.datatype.geo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.geo.Latitude;
import org.nabucco.framework.base.facade.datatype.geo.Longitude;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * GeoLocation<p/>A geo location defined by latitude and longitude.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-04-26
 */
public class GeoLocation extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;u0,n;m1,1;", "l0,n;u0,n;m1,1;" };

    public static final String LATITUDE = "latitude";

    public static final String LONGITUDE = "longitude";

    /** The location latitude. */
    protected Latitude latitude;

    /** The location longitude. */
    protected Longitude longitude;

    /** Constructs a new GeoLocation instance. */
    public GeoLocation() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the GeoLocation.
     */
    protected void cloneObject(GeoLocation clone) {
        super.cloneObject(clone);
        if ((this.getLatitude() != null)) {
            clone.setLatitude(this.getLatitude().cloneObject());
        }
        if ((this.getLongitude() != null)) {
            clone.setLongitude(this.getLongitude().cloneObject());
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
        propertyMap.put(LATITUDE,
                PropertyDescriptorSupport.createBasetype(LATITUDE, Latitude.class, 3, PROPERTY_CONSTRAINTS[0], false));
        propertyMap
                .put(LONGITUDE, PropertyDescriptorSupport.createBasetype(LONGITUDE, Longitude.class, 4,
                        PROPERTY_CONSTRAINTS[1], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(GeoLocation.getPropertyDescriptor(LATITUDE), this.latitude, null));
        properties.add(super.createProperty(GeoLocation.getPropertyDescriptor(LONGITUDE), this.longitude, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(LATITUDE) && (property.getType() == Latitude.class))) {
            this.setLatitude(((Latitude) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LONGITUDE) && (property.getType() == Longitude.class))) {
            this.setLongitude(((Longitude) property.getInstance()));
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
        final GeoLocation other = ((GeoLocation) obj);
        if ((this.latitude == null)) {
            if ((other.latitude != null))
                return false;
        } else if ((!this.latitude.equals(other.latitude)))
            return false;
        if ((this.longitude == null)) {
            if ((other.longitude != null))
                return false;
        } else if ((!this.longitude.equals(other.longitude)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.latitude == null) ? 0 : this.latitude.hashCode()));
        result = ((PRIME * result) + ((this.longitude == null) ? 0 : this.longitude.hashCode()));
        return result;
    }

    @Override
    public GeoLocation cloneObject() {
        GeoLocation clone = new GeoLocation();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The location latitude.
     *
     * @return the Latitude.
     */
    public Latitude getLatitude() {
        return this.latitude;
    }

    /**
     * The location latitude.
     *
     * @param latitude the Latitude.
     */
    public void setLatitude(Latitude latitude) {
        this.latitude = latitude;
    }

    /**
     * The location latitude.
     *
     * @param latitude the Double.
     */
    public void setLatitude(Double latitude) {
        if ((this.latitude == null)) {
            if ((latitude == null)) {
                return;
            }
            this.latitude = new Latitude();
        }
        this.latitude.setValue(latitude);
    }

    /**
     * The location longitude.
     *
     * @return the Longitude.
     */
    public Longitude getLongitude() {
        return this.longitude;
    }

    /**
     * The location longitude.
     *
     * @param longitude the Longitude.
     */
    public void setLongitude(Longitude longitude) {
        this.longitude = longitude;
    }

    /**
     * The location longitude.
     *
     * @param longitude the Double.
     */
    public void setLongitude(Double longitude) {
        if ((this.longitude == null)) {
            if ((longitude == null)) {
                return;
            }
            this.longitude = new Longitude();
        }
        this.longitude.setValue(longitude);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(GeoLocation.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(GeoLocation.class).getAllProperties();
    }
}
