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
import org.nabucco.framework.base.facade.datatype.extension.property.IntegerProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * AgentCronExtension<p/>Configuration for cron entries of agents.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-06-06
 */
public class AgentCronExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,1;", "m0,1;", "m0,1;", "m0,1;", "m0,1;", "m1,1;" };

    public static final String YEAR = "year";

    public static final String WEEKDAY = "weekday";

    public static final String MONTH = "month";

    public static final String DAY = "day";

    public static final String HOUR = "hour";

    public static final String MINUTE = "minute";

    /** The year. */
    private IntegerProperty year;

    /** The day of the week (0-7, Sunday =0 or =7). */
    private IntegerProperty weekday;

    /** The month (1-12). */
    private IntegerProperty month;

    /** The day of the month (1-31). */
    private IntegerProperty day;

    /** The hour (0-23). */
    private IntegerProperty hour;

    /** The minute (0-59). */
    private IntegerProperty minute;

    /** Constructs a new AgentCronExtension instance. */
    public AgentCronExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the AgentCronExtension.
     */
    protected void cloneObject(AgentCronExtension clone) {
        super.cloneObject(clone);
        if ((this.getYear() != null)) {
            clone.setYear(this.getYear().cloneObject());
        }
        if ((this.getWeekday() != null)) {
            clone.setWeekday(this.getWeekday().cloneObject());
        }
        if ((this.getMonth() != null)) {
            clone.setMonth(this.getMonth().cloneObject());
        }
        if ((this.getDay() != null)) {
            clone.setDay(this.getDay().cloneObject());
        }
        if ((this.getHour() != null)) {
            clone.setHour(this.getHour().cloneObject());
        }
        if ((this.getMinute() != null)) {
            clone.setMinute(this.getMinute().cloneObject());
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
        propertyMap.put(YEAR, PropertyDescriptorSupport.createDatatype(YEAR, IntegerProperty.class, 2,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(WEEKDAY, PropertyDescriptorSupport.createDatatype(WEEKDAY, IntegerProperty.class, 3,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(MONTH, PropertyDescriptorSupport.createDatatype(MONTH, IntegerProperty.class, 4,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(DAY, PropertyDescriptorSupport.createDatatype(DAY, IntegerProperty.class, 5,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(HOUR, PropertyDescriptorSupport.createDatatype(HOUR, IntegerProperty.class, 6,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(MINUTE, PropertyDescriptorSupport.createDatatype(MINUTE, IntegerProperty.class, 7,
                PROPERTY_CONSTRAINTS[5], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(AgentCronExtension.getPropertyDescriptor(YEAR), this.getYear(), null));
        properties
                .add(super.createProperty(AgentCronExtension.getPropertyDescriptor(WEEKDAY), this.getWeekday(), null));
        properties.add(super.createProperty(AgentCronExtension.getPropertyDescriptor(MONTH), this.getMonth(), null));
        properties.add(super.createProperty(AgentCronExtension.getPropertyDescriptor(DAY), this.getDay(), null));
        properties.add(super.createProperty(AgentCronExtension.getPropertyDescriptor(HOUR), this.getHour(), null));
        properties.add(super.createProperty(AgentCronExtension.getPropertyDescriptor(MINUTE), this.getMinute(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(YEAR) && (property.getType() == IntegerProperty.class))) {
            this.setYear(((IntegerProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(WEEKDAY) && (property.getType() == IntegerProperty.class))) {
            this.setWeekday(((IntegerProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MONTH) && (property.getType() == IntegerProperty.class))) {
            this.setMonth(((IntegerProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DAY) && (property.getType() == IntegerProperty.class))) {
            this.setDay(((IntegerProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(HOUR) && (property.getType() == IntegerProperty.class))) {
            this.setHour(((IntegerProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MINUTE) && (property.getType() == IntegerProperty.class))) {
            this.setMinute(((IntegerProperty) property.getInstance()));
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
        final AgentCronExtension other = ((AgentCronExtension) obj);
        if ((this.year == null)) {
            if ((other.year != null))
                return false;
        } else if ((!this.year.equals(other.year)))
            return false;
        if ((this.weekday == null)) {
            if ((other.weekday != null))
                return false;
        } else if ((!this.weekday.equals(other.weekday)))
            return false;
        if ((this.month == null)) {
            if ((other.month != null))
                return false;
        } else if ((!this.month.equals(other.month)))
            return false;
        if ((this.day == null)) {
            if ((other.day != null))
                return false;
        } else if ((!this.day.equals(other.day)))
            return false;
        if ((this.hour == null)) {
            if ((other.hour != null))
                return false;
        } else if ((!this.hour.equals(other.hour)))
            return false;
        if ((this.minute == null)) {
            if ((other.minute != null))
                return false;
        } else if ((!this.minute.equals(other.minute)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.year == null) ? 0 : this.year.hashCode()));
        result = ((PRIME * result) + ((this.weekday == null) ? 0 : this.weekday.hashCode()));
        result = ((PRIME * result) + ((this.month == null) ? 0 : this.month.hashCode()));
        result = ((PRIME * result) + ((this.day == null) ? 0 : this.day.hashCode()));
        result = ((PRIME * result) + ((this.hour == null) ? 0 : this.hour.hashCode()));
        result = ((PRIME * result) + ((this.minute == null) ? 0 : this.minute.hashCode()));
        return result;
    }

    @Override
    public AgentCronExtension cloneObject() {
        AgentCronExtension clone = new AgentCronExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The year.
     *
     * @param year the IntegerProperty.
     */
    public void setYear(IntegerProperty year) {
        this.year = year;
    }

    /**
     * The year.
     *
     * @return the IntegerProperty.
     */
    public IntegerProperty getYear() {
        return this.year;
    }

    /**
     * The day of the week (0-7, Sunday =0 or =7).
     *
     * @param weekday the IntegerProperty.
     */
    public void setWeekday(IntegerProperty weekday) {
        this.weekday = weekday;
    }

    /**
     * The day of the week (0-7, Sunday =0 or =7).
     *
     * @return the IntegerProperty.
     */
    public IntegerProperty getWeekday() {
        return this.weekday;
    }

    /**
     * The month (1-12).
     *
     * @param month the IntegerProperty.
     */
    public void setMonth(IntegerProperty month) {
        this.month = month;
    }

    /**
     * The month (1-12).
     *
     * @return the IntegerProperty.
     */
    public IntegerProperty getMonth() {
        return this.month;
    }

    /**
     * The day of the month (1-31).
     *
     * @param day the IntegerProperty.
     */
    public void setDay(IntegerProperty day) {
        this.day = day;
    }

    /**
     * The day of the month (1-31).
     *
     * @return the IntegerProperty.
     */
    public IntegerProperty getDay() {
        return this.day;
    }

    /**
     * The hour (0-23).
     *
     * @param hour the IntegerProperty.
     */
    public void setHour(IntegerProperty hour) {
        this.hour = hour;
    }

    /**
     * The hour (0-23).
     *
     * @return the IntegerProperty.
     */
    public IntegerProperty getHour() {
        return this.hour;
    }

    /**
     * The minute (0-59).
     *
     * @param minute the IntegerProperty.
     */
    public void setMinute(IntegerProperty minute) {
        this.minute = minute;
    }

    /**
     * The minute (0-59).
     *
     * @return the IntegerProperty.
     */
    public IntegerProperty getMinute() {
        return this.minute;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(AgentCronExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(AgentCronExtension.class).getAllProperties();
    }
}
