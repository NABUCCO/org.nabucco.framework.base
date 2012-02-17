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
package org.nabucco.framework.base.facade.datatype.setup.userprefs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeSupport;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.security.UserId;
import org.nabucco.framework.base.facade.datatype.setup.userprefs.PreferenceType;

/**
 * UserPreference<p/>One user preference element.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-03-15
 */
public class UserPreference extends DatatypeSupport implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l3,32;u0,n;m1,1;", "l3,255;u0,n;m1,1;", "m1,1;",
            "l0,1000;u0,n;m1,1;" };

    public static final String USERID = "userId";

    public static final String CATEGORY = "category";

    public static final String TYPE = "type";

    public static final String VALUE = "value";

    /** The id of the user. */
    private UserId userId;

    /** The category of the preference. */
    private PreferenceCategory category;

    /** The type of the preference value. */
    private PreferenceType type;

    /** The values of the preference. */
    private PreferenceValue value;

    /** Constructs a new UserPreference instance. */
    public UserPreference() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the UserPreference.
     */
    protected void cloneObject(UserPreference clone) {
        super.cloneObject(clone);
        if ((this.getUserId() != null)) {
            clone.setUserId(this.getUserId().cloneObject());
        }
        if ((this.getCategory() != null)) {
            clone.setCategory(this.getCategory().cloneObject());
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
        propertyMap.put(USERID,
                PropertyDescriptorSupport.createBasetype(USERID, UserId.class, 0, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(CATEGORY, PropertyDescriptorSupport.createBasetype(CATEGORY, PreferenceCategory.class, 1,
                PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(TYPE, PropertyDescriptorSupport.createEnumeration(TYPE, PreferenceType.class, 2,
                PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(VALUE, PropertyDescriptorSupport.createBasetype(VALUE, PreferenceValue.class, 3,
                PROPERTY_CONSTRAINTS[3], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(UserPreference.getPropertyDescriptor(USERID), this.userId, null));
        properties.add(super.createProperty(UserPreference.getPropertyDescriptor(CATEGORY), this.category, null));
        properties.add(super.createProperty(UserPreference.getPropertyDescriptor(TYPE), this.getType(), null));
        properties.add(super.createProperty(UserPreference.getPropertyDescriptor(VALUE), this.value, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(USERID) && (property.getType() == UserId.class))) {
            this.setUserId(((UserId) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CATEGORY) && (property.getType() == PreferenceCategory.class))) {
            this.setCategory(((PreferenceCategory) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TYPE) && (property.getType() == PreferenceType.class))) {
            this.setType(((PreferenceType) property.getInstance()));
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
        final UserPreference other = ((UserPreference) obj);
        if ((this.userId == null)) {
            if ((other.userId != null))
                return false;
        } else if ((!this.userId.equals(other.userId)))
            return false;
        if ((this.category == null)) {
            if ((other.category != null))
                return false;
        } else if ((!this.category.equals(other.category)))
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
        result = ((PRIME * result) + ((this.userId == null) ? 0 : this.userId.hashCode()));
        result = ((PRIME * result) + ((this.category == null) ? 0 : this.category.hashCode()));
        result = ((PRIME * result) + ((this.type == null) ? 0 : this.type.hashCode()));
        result = ((PRIME * result) + ((this.value == null) ? 0 : this.value.hashCode()));
        return result;
    }

    @Override
    public UserPreference cloneObject() {
        UserPreference clone = new UserPreference();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The id of the user.
     *
     * @return the UserId.
     */
    public UserId getUserId() {
        return this.userId;
    }

    /**
     * The id of the user.
     *
     * @param userId the UserId.
     */
    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    /**
     * The id of the user.
     *
     * @param userId the String.
     */
    public void setUserId(String userId) {
        if ((this.userId == null)) {
            if ((userId == null)) {
                return;
            }
            this.userId = new UserId();
        }
        this.userId.setValue(userId);
    }

    /**
     * The category of the preference.
     *
     * @return the PreferenceCategory.
     */
    public PreferenceCategory getCategory() {
        return this.category;
    }

    /**
     * The category of the preference.
     *
     * @param category the PreferenceCategory.
     */
    public void setCategory(PreferenceCategory category) {
        this.category = category;
    }

    /**
     * The category of the preference.
     *
     * @param category the String.
     */
    public void setCategory(String category) {
        if ((this.category == null)) {
            if ((category == null)) {
                return;
            }
            this.category = new PreferenceCategory();
        }
        this.category.setValue(category);
    }

    /**
     * The type of the preference value.
     *
     * @return the PreferenceType.
     */
    public PreferenceType getType() {
        return this.type;
    }

    /**
     * The type of the preference value.
     *
     * @param type the PreferenceType.
     */
    public void setType(PreferenceType type) {
        this.type = type;
    }

    /**
     * The type of the preference value.
     *
     * @param type the String.
     */
    public void setType(String type) {
        if ((type == null)) {
            this.type = null;
        } else {
            this.type = PreferenceType.valueOf(type);
        }
    }

    /**
     * The values of the preference.
     *
     * @return the PreferenceValue.
     */
    public PreferenceValue getValue() {
        return this.value;
    }

    /**
     * The values of the preference.
     *
     * @param value the PreferenceValue.
     */
    public void setValue(PreferenceValue value) {
        this.value = value;
    }

    /**
     * The values of the preference.
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
        return PropertyCache.getInstance().retrieve(UserPreference.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(UserPreference.class).getAllProperties();
    }
}
