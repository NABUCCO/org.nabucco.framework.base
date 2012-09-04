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
package org.nabucco.framework.base.facade.datatype.extension.schema.authorization.password.policy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.property.BooleanProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.IntegerProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * PasswordPolicyExtension<p/>Password Policy Schema for Auhorization.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-23
 */
public class PasswordPolicyExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;", "m1,1;" };

    public static final String PASSWORDPATTERN = "passwordPattern";

    public static final String CASESENSITIVE = "caseSensitive";

    public static final String REUSECOUNT = "reuseCount";

    public static final String DURATION = "duration";

    /** The regular expression for validating the password. */
    private StringProperty passwordPattern;

    /** Defines that a password is case sentive (value == true) or case insensitive (value == false). */
    private BooleanProperty caseSensitive;

    /** The amount of passwords which has to unique for one user. */
    private IntegerProperty reuseCount;

    /** The duration of the password in days. */
    private IntegerProperty duration;

    /** Constructs a new PasswordPolicyExtension instance. */
    public PasswordPolicyExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the PasswordPolicyExtension.
     */
    protected void cloneObject(PasswordPolicyExtension clone) {
        super.cloneObject(clone);
        if ((this.getPasswordPattern() != null)) {
            clone.setPasswordPattern(this.getPasswordPattern().cloneObject());
        }
        if ((this.getCaseSensitive() != null)) {
            clone.setCaseSensitive(this.getCaseSensitive().cloneObject());
        }
        if ((this.getReuseCount() != null)) {
            clone.setReuseCount(this.getReuseCount().cloneObject());
        }
        if ((this.getDuration() != null)) {
            clone.setDuration(this.getDuration().cloneObject());
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
        propertyMap.put(PASSWORDPATTERN, PropertyDescriptorSupport.createDatatype(PASSWORDPATTERN,
                StringProperty.class, 2, PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(CASESENSITIVE, PropertyDescriptorSupport.createDatatype(CASESENSITIVE, BooleanProperty.class,
                3, PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(REUSECOUNT, PropertyDescriptorSupport.createDatatype(REUSECOUNT, IntegerProperty.class, 4,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(DURATION, PropertyDescriptorSupport.createDatatype(DURATION, IntegerProperty.class, 5,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(PasswordPolicyExtension.getPropertyDescriptor(PASSWORDPATTERN),
                this.getPasswordPattern(), null));
        properties.add(super.createProperty(PasswordPolicyExtension.getPropertyDescriptor(CASESENSITIVE),
                this.getCaseSensitive(), null));
        properties.add(super.createProperty(PasswordPolicyExtension.getPropertyDescriptor(REUSECOUNT),
                this.getReuseCount(), null));
        properties.add(super.createProperty(PasswordPolicyExtension.getPropertyDescriptor(DURATION),
                this.getDuration(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(PASSWORDPATTERN) && (property.getType() == StringProperty.class))) {
            this.setPasswordPattern(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CASESENSITIVE) && (property.getType() == BooleanProperty.class))) {
            this.setCaseSensitive(((BooleanProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(REUSECOUNT) && (property.getType() == IntegerProperty.class))) {
            this.setReuseCount(((IntegerProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DURATION) && (property.getType() == IntegerProperty.class))) {
            this.setDuration(((IntegerProperty) property.getInstance()));
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
        final PasswordPolicyExtension other = ((PasswordPolicyExtension) obj);
        if ((this.passwordPattern == null)) {
            if ((other.passwordPattern != null))
                return false;
        } else if ((!this.passwordPattern.equals(other.passwordPattern)))
            return false;
        if ((this.caseSensitive == null)) {
            if ((other.caseSensitive != null))
                return false;
        } else if ((!this.caseSensitive.equals(other.caseSensitive)))
            return false;
        if ((this.reuseCount == null)) {
            if ((other.reuseCount != null))
                return false;
        } else if ((!this.reuseCount.equals(other.reuseCount)))
            return false;
        if ((this.duration == null)) {
            if ((other.duration != null))
                return false;
        } else if ((!this.duration.equals(other.duration)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.passwordPattern == null) ? 0 : this.passwordPattern.hashCode()));
        result = ((PRIME * result) + ((this.caseSensitive == null) ? 0 : this.caseSensitive.hashCode()));
        result = ((PRIME * result) + ((this.reuseCount == null) ? 0 : this.reuseCount.hashCode()));
        result = ((PRIME * result) + ((this.duration == null) ? 0 : this.duration.hashCode()));
        return result;
    }

    @Override
    public PasswordPolicyExtension cloneObject() {
        PasswordPolicyExtension clone = new PasswordPolicyExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The regular expression for validating the password.
     *
     * @param passwordPattern the StringProperty.
     */
    public void setPasswordPattern(StringProperty passwordPattern) {
        this.passwordPattern = passwordPattern;
    }

    /**
     * The regular expression for validating the password.
     *
     * @return the StringProperty.
     */
    public StringProperty getPasswordPattern() {
        return this.passwordPattern;
    }

    /**
     * Defines that a password is case sentive (value == true) or case insensitive (value == false).
     *
     * @param caseSensitive the BooleanProperty.
     */
    public void setCaseSensitive(BooleanProperty caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    /**
     * Defines that a password is case sentive (value == true) or case insensitive (value == false).
     *
     * @return the BooleanProperty.
     */
    public BooleanProperty getCaseSensitive() {
        return this.caseSensitive;
    }

    /**
     * The amount of passwords which has to unique for one user.
     *
     * @param reuseCount the IntegerProperty.
     */
    public void setReuseCount(IntegerProperty reuseCount) {
        this.reuseCount = reuseCount;
    }

    /**
     * The amount of passwords which has to unique for one user.
     *
     * @return the IntegerProperty.
     */
    public IntegerProperty getReuseCount() {
        return this.reuseCount;
    }

    /**
     * The duration of the password in days.
     *
     * @param duration the IntegerProperty.
     */
    public void setDuration(IntegerProperty duration) {
        this.duration = duration;
    }

    /**
     * The duration of the password in days.
     *
     * @return the IntegerProperty.
     */
    public IntegerProperty getDuration() {
        return this.duration;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(PasswordPolicyExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(PasswordPolicyExtension.class).getAllProperties();
    }
}
