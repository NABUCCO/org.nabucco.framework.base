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
 * AgentDurationExtension<p/>Configuration for time durations of agents.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-06-07
 */
public class AgentDurationExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;" };

    public static final String HOURS = "hours";

    public static final String MINUTES = "minutes";

    /** The time in hours. */
    private IntegerProperty hours;

    /** The time in minutes. */
    private IntegerProperty minutes;

    /** Constructs a new AgentDurationExtension instance. */
    public AgentDurationExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the AgentDurationExtension.
     */
    protected void cloneObject(AgentDurationExtension clone) {
        super.cloneObject(clone);
        if ((this.getHours() != null)) {
            clone.setHours(this.getHours().cloneObject());
        }
        if ((this.getMinutes() != null)) {
            clone.setMinutes(this.getMinutes().cloneObject());
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
        propertyMap.put(HOURS, PropertyDescriptorSupport.createDatatype(HOURS, IntegerProperty.class, 2,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(MINUTES, PropertyDescriptorSupport.createDatatype(MINUTES, IntegerProperty.class, 3,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
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
                .add(super.createProperty(AgentDurationExtension.getPropertyDescriptor(HOURS), this.getHours(), null));
        properties.add(super.createProperty(AgentDurationExtension.getPropertyDescriptor(MINUTES), this.getMinutes(),
                null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(HOURS) && (property.getType() == IntegerProperty.class))) {
            this.setHours(((IntegerProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MINUTES) && (property.getType() == IntegerProperty.class))) {
            this.setMinutes(((IntegerProperty) property.getInstance()));
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
        final AgentDurationExtension other = ((AgentDurationExtension) obj);
        if ((this.hours == null)) {
            if ((other.hours != null))
                return false;
        } else if ((!this.hours.equals(other.hours)))
            return false;
        if ((this.minutes == null)) {
            if ((other.minutes != null))
                return false;
        } else if ((!this.minutes.equals(other.minutes)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.hours == null) ? 0 : this.hours.hashCode()));
        result = ((PRIME * result) + ((this.minutes == null) ? 0 : this.minutes.hashCode()));
        return result;
    }

    @Override
    public AgentDurationExtension cloneObject() {
        AgentDurationExtension clone = new AgentDurationExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The time in hours.
     *
     * @param hours the IntegerProperty.
     */
    public void setHours(IntegerProperty hours) {
        this.hours = hours;
    }

    /**
     * The time in hours.
     *
     * @return the IntegerProperty.
     */
    public IntegerProperty getHours() {
        return this.hours;
    }

    /**
     * The time in minutes.
     *
     * @param minutes the IntegerProperty.
     */
    public void setMinutes(IntegerProperty minutes) {
        this.minutes = minutes;
    }

    /**
     * The time in minutes.
     *
     * @return the IntegerProperty.
     */
    public IntegerProperty getMinutes() {
        return this.minutes;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(AgentDurationExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(AgentDurationExtension.class).getAllProperties();
    }
}
