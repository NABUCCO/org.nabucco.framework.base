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
import org.nabucco.framework.base.facade.datatype.geo.GeoLevel;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * GeoRegion<p/>An abstract region for a geo location.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-05-05
 */
public class GeoRegion extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l2,2;u0,n;m1,1;", "l0,n;u0,99;m1,1;", "l2,255;u0,n;m1,1;" };

    public static final String LOCALE = "locale";

    public static final String LEVEL = "level";

    public static final String NAME = "name";

    protected GeoLocale locale;

    protected GeoLevel level;

    protected GeoName name;

    /** Constructs a new GeoRegion instance. */
    public GeoRegion() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the GeoRegion.
     */
    protected void cloneObject(GeoRegion clone) {
        super.cloneObject(clone);
        if ((this.getLocale() != null)) {
            clone.setLocale(this.getLocale().cloneObject());
        }
        if ((this.getLevel() != null)) {
            clone.setLevel(this.getLevel().cloneObject());
        }
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
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
        propertyMap.put(LOCALE,
                PropertyDescriptorSupport.createBasetype(LOCALE, GeoLocale.class, 3, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(LEVEL,
                PropertyDescriptorSupport.createBasetype(LEVEL, GeoLevel.class, 4, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(NAME,
                PropertyDescriptorSupport.createBasetype(NAME, GeoName.class, 5, PROPERTY_CONSTRAINTS[2], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(GeoRegion.getPropertyDescriptor(LOCALE), this.locale, null));
        properties.add(super.createProperty(GeoRegion.getPropertyDescriptor(LEVEL), this.level, null));
        properties.add(super.createProperty(GeoRegion.getPropertyDescriptor(NAME), this.name, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(LOCALE) && (property.getType() == GeoLocale.class))) {
            this.setLocale(((GeoLocale) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LEVEL) && (property.getType() == GeoLevel.class))) {
            this.setLevel(((GeoLevel) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NAME) && (property.getType() == GeoName.class))) {
            this.setName(((GeoName) property.getInstance()));
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
        final GeoRegion other = ((GeoRegion) obj);
        if ((this.locale == null)) {
            if ((other.locale != null))
                return false;
        } else if ((!this.locale.equals(other.locale)))
            return false;
        if ((this.level == null)) {
            if ((other.level != null))
                return false;
        } else if ((!this.level.equals(other.level)))
            return false;
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.locale == null) ? 0 : this.locale.hashCode()));
        result = ((PRIME * result) + ((this.level == null) ? 0 : this.level.hashCode()));
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        return result;
    }

    @Override
    public GeoRegion cloneObject() {
        GeoRegion clone = new GeoRegion();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Missing description at method getLocale.
     *
     * @return the GeoLocale.
     */
    public GeoLocale getLocale() {
        return this.locale;
    }

    /**
     * Missing description at method setLocale.
     *
     * @param locale the GeoLocale.
     */
    public void setLocale(GeoLocale locale) {
        this.locale = locale;
    }

    /**
     * Missing description at method setLocale.
     *
     * @param locale the String.
     */
    public void setLocale(String locale) {
        if ((this.locale == null)) {
            if ((locale == null)) {
                return;
            }
            this.locale = new GeoLocale();
        }
        this.locale.setValue(locale);
    }

    /**
     * Missing description at method getLevel.
     *
     * @return the GeoLevel.
     */
    public GeoLevel getLevel() {
        return this.level;
    }

    /**
     * Missing description at method setLevel.
     *
     * @param level the GeoLevel.
     */
    public void setLevel(GeoLevel level) {
        this.level = level;
    }

    /**
     * Missing description at method setLevel.
     *
     * @param level the Integer.
     */
    public void setLevel(Integer level) {
        if ((this.level == null)) {
            if ((level == null)) {
                return;
            }
            this.level = new GeoLevel();
        }
        this.level.setValue(level);
    }

    /**
     * Missing description at method getName.
     *
     * @return the GeoName.
     */
    public GeoName getName() {
        return this.name;
    }

    /**
     * Missing description at method setName.
     *
     * @param name the GeoName.
     */
    public void setName(GeoName name) {
        this.name = name;
    }

    /**
     * Missing description at method setName.
     *
     * @param name the String.
     */
    public void setName(String name) {
        if ((this.name == null)) {
            if ((name == null)) {
                return;
            }
            this.name = new GeoName();
        }
        this.name.setValue(name);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(GeoRegion.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(GeoRegion.class).getAllProperties();
    }
}
