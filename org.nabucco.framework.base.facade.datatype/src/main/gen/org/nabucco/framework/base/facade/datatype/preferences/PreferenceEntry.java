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
package org.nabucco.framework.base.facade.datatype.preferences;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.BasetypeType;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeSupport;
import org.nabucco.framework.base.facade.datatype.preferences.PreferenceValue;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * PreferenceEntry<p/>One preference key value element.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-23
 */
public class PreferenceEntry extends DatatypeSupport implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l3,255;u0,n;m1,1;", "m1,1;", "l0,255;u0,n;m1,1;" };

    public static final String KEY = "key";

    public static final String TYPE = "type";

    public static final String VALUE = "value";

    /** The key of the preference element. */
    private PreferenceKey key;

    /** The type of the preference element. */
    private BasetypeType type;

    /** The value of the preference element as String representation. */
    private PreferenceValue value;

    /** Constructs a new PreferenceEntry instance. */
    public PreferenceEntry() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the PreferenceEntry.
     */
    protected void cloneObject(PreferenceEntry clone) {
        super.cloneObject(clone);
        if ((this.getKey() != null)) {
            clone.setKey(this.getKey().cloneObject());
        }
        clone.setType(this.getType());
        if ((this.getValue() != null)) {
            clone.setValue(this.getValue().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(KEY,
                PropertyDescriptorSupport.createBasetype(KEY, PreferenceKey.class, 0, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(TYPE, PropertyDescriptorSupport.createEnumeration(TYPE, BasetypeType.class, 1,
                PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(VALUE, PropertyDescriptorSupport.createBasetype(VALUE, PreferenceValue.class, 2,
                PROPERTY_CONSTRAINTS[2], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(PreferenceEntry.getPropertyDescriptor(KEY), this.key, null));
        properties.add(super.createProperty(PreferenceEntry.getPropertyDescriptor(TYPE), this.getType(), null));
        properties.add(super.createProperty(PreferenceEntry.getPropertyDescriptor(VALUE), this.value, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(KEY) && (property.getType() == PreferenceKey.class))) {
            this.setKey(((PreferenceKey) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TYPE) && (property.getType() == BasetypeType.class))) {
            this.setType(((BasetypeType) property.getInstance()));
            return true;
        } else if ((property.getName().equals(VALUE) && (property.getType() == PreferenceValue.class))) {
            this.setValue(((PreferenceValue) property.getInstance()));
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
        final PreferenceEntry other = ((PreferenceEntry) obj);
        if ((this.key == null)) {
            if ((other.key != null))
                return false;
        } else if ((!this.key.equals(other.key)))
            return false;
        if ((this.type == null)) {
            if ((other.type != null))
                return false;
        } else if ((!this.type.equals(other.type)))
            return false;
        if ((this.value == null)) {
            if ((other.value != null))
                return false;
        } else if ((!this.value.equals(other.value)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.key == null) ? 0 : this.key.hashCode()));
        result = ((PRIME * result) + ((this.type == null) ? 0 : this.type.hashCode()));
        result = ((PRIME * result) + ((this.value == null) ? 0 : this.value.hashCode()));
        return result;
    }

    @Override
    public PreferenceEntry cloneObject() {
        PreferenceEntry clone = new PreferenceEntry();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The key of the preference element.
     *
     * @return the PreferenceKey.
     */
    public PreferenceKey getKey() {
        return this.key;
    }

    /**
     * The key of the preference element.
     *
     * @param key the PreferenceKey.
     */
    public void setKey(PreferenceKey key) {
        this.key = key;
    }

    /**
     * The key of the preference element.
     *
     * @param key the String.
     */
    public void setKey(String key) {
        if ((this.key == null)) {
            if ((key == null)) {
                return;
            }
            this.key = new PreferenceKey();
        }
        this.key.setValue(key);
    }

    /**
     * The type of the preference element.
     *
     * @return the BasetypeType.
     */
    public BasetypeType getType() {
        return this.type;
    }

    /**
     * The type of the preference element.
     *
     * @param type the BasetypeType.
     */
    public void setType(BasetypeType type) {
        this.type = type;
    }

    /**
     * The type of the preference element.
     *
     * @param type the String.
     */
    public void setType(String type) {
        if ((type == null)) {
            this.type = null;
        } else {
            this.type = BasetypeType.valueOf(type);
        }
    }

    /**
     * The value of the preference element as String representation.
     *
     * @return the PreferenceValue.
     */
    public PreferenceValue getValue() {
        return this.value;
    }

    /**
     * The value of the preference element as String representation.
     *
     * @param value the PreferenceValue.
     */
    public void setValue(PreferenceValue value) {
        this.value = value;
    }

    /**
     * The value of the preference element as String representation.
     *
     * @param value the String.
     */
    public void setValue(String value) {
        if ((this.value == null)) {
            if ((value == null)) {
                return;
            }
            this.value = new PreferenceValue();
        }
        this.value.setValue(value);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(PreferenceEntry.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(PreferenceEntry.class).getAllProperties();
    }
}
