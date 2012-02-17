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
package org.nabucco.framework.base.facade.datatype.monitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.date.Timestamp;
import org.nabucco.framework.base.facade.datatype.monitor.MonitorEntryType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * MonitorEntry<p/>An entry that is controlled by the monitor component<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-04-06
 */
public abstract class MonitorEntry extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m1,1;", "l0,255;u0,n;m1,1;", "l0,n;u0,n;m1,1;",
            "m1,1;" };

    public static final String SERVICENAME = "serviceName";

    public static final String OPERATIONNAME = "operationName";

    public static final String TIMESTAMP = "timestamp";

    public static final String TYPE = "type";

    /** Name of the service which produced this entry. */
    private Name serviceName;

    /** Name of the service operation which produced this entry. */
    private Name operationName;

    /** Timestamp of the operation. */
    private Timestamp timestamp;

    /** Type of the monitor entry */
    protected MonitorEntryType type;

    /** Constructs a new MonitorEntry instance. */
    public MonitorEntry() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the MonitorEntry.
     */
    protected void cloneObject(MonitorEntry clone) {
        super.cloneObject(clone);
        if ((this.getServiceName() != null)) {
            clone.setServiceName(this.getServiceName().cloneObject());
        }
        if ((this.getOperationName() != null)) {
            clone.setOperationName(this.getOperationName().cloneObject());
        }
        if ((this.getTimestamp() != null)) {
            clone.setTimestamp(this.getTimestamp().cloneObject());
        }
        clone.setType(this.getType());
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoDatatype.class).getPropertyMap());
        propertyMap.put(SERVICENAME,
                PropertyDescriptorSupport.createBasetype(SERVICENAME, Name.class, 3, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(OPERATIONNAME,
                PropertyDescriptorSupport.createBasetype(OPERATIONNAME, Name.class, 4, PROPERTY_CONSTRAINTS[1], false));
        propertyMap
                .put(TIMESTAMP, PropertyDescriptorSupport.createBasetype(TIMESTAMP, Timestamp.class, 5,
                        PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(TYPE, PropertyDescriptorSupport.createEnumeration(TYPE, MonitorEntryType.class, 6,
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
        properties.add(super.createProperty(MonitorEntry.getPropertyDescriptor(SERVICENAME), this.serviceName, null));
        properties
                .add(super.createProperty(MonitorEntry.getPropertyDescriptor(OPERATIONNAME), this.operationName, null));
        properties.add(super.createProperty(MonitorEntry.getPropertyDescriptor(TIMESTAMP), this.timestamp, null));
        properties.add(super.createProperty(MonitorEntry.getPropertyDescriptor(TYPE), this.getType(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(SERVICENAME) && (property.getType() == Name.class))) {
            this.setServiceName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(OPERATIONNAME) && (property.getType() == Name.class))) {
            this.setOperationName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TIMESTAMP) && (property.getType() == Timestamp.class))) {
            this.setTimestamp(((Timestamp) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TYPE) && (property.getType() == MonitorEntryType.class))) {
            this.setType(((MonitorEntryType) property.getInstance()));
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
        final MonitorEntry other = ((MonitorEntry) obj);
        if ((this.serviceName == null)) {
            if ((other.serviceName != null))
                return false;
        } else if ((!this.serviceName.equals(other.serviceName)))
            return false;
        if ((this.operationName == null)) {
            if ((other.operationName != null))
                return false;
        } else if ((!this.operationName.equals(other.operationName)))
            return false;
        if ((this.timestamp == null)) {
            if ((other.timestamp != null))
                return false;
        } else if ((!this.timestamp.equals(other.timestamp)))
            return false;
        if ((this.type == null)) {
            if ((other.type != null))
                return false;
        } else if ((!this.type.equals(other.type)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.serviceName == null) ? 0 : this.serviceName.hashCode()));
        result = ((PRIME * result) + ((this.operationName == null) ? 0 : this.operationName.hashCode()));
        result = ((PRIME * result) + ((this.timestamp == null) ? 0 : this.timestamp.hashCode()));
        result = ((PRIME * result) + ((this.type == null) ? 0 : this.type.hashCode()));
        return result;
    }

    @Override
    public abstract MonitorEntry cloneObject();

    /**
     * Name of the service which produced this entry.
     *
     * @return the Name.
     */
    public Name getServiceName() {
        return this.serviceName;
    }

    /**
     * Name of the service which produced this entry.
     *
     * @param serviceName the Name.
     */
    public void setServiceName(Name serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * Name of the service which produced this entry.
     *
     * @param serviceName the String.
     */
    public void setServiceName(String serviceName) {
        if ((this.serviceName == null)) {
            if ((serviceName == null)) {
                return;
            }
            this.serviceName = new Name();
        }
        this.serviceName.setValue(serviceName);
    }

    /**
     * Name of the service operation which produced this entry.
     *
     * @return the Name.
     */
    public Name getOperationName() {
        return this.operationName;
    }

    /**
     * Name of the service operation which produced this entry.
     *
     * @param operationName the Name.
     */
    public void setOperationName(Name operationName) {
        this.operationName = operationName;
    }

    /**
     * Name of the service operation which produced this entry.
     *
     * @param operationName the String.
     */
    public void setOperationName(String operationName) {
        if ((this.operationName == null)) {
            if ((operationName == null)) {
                return;
            }
            this.operationName = new Name();
        }
        this.operationName.setValue(operationName);
    }

    /**
     * Timestamp of the operation.
     *
     * @return the Timestamp.
     */
    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    /**
     * Timestamp of the operation.
     *
     * @param timestamp the Timestamp.
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Timestamp of the operation.
     *
     * @param timestamp the Long.
     */
    public void setTimestamp(Long timestamp) {
        if ((this.timestamp == null)) {
            if ((timestamp == null)) {
                return;
            }
            this.timestamp = new Timestamp();
        }
        this.timestamp.setValue(timestamp);
    }

    /**
     * Type of the monitor entry
     *
     * @return the MonitorEntryType.
     */
    public MonitorEntryType getType() {
        return this.type;
    }

    /**
     * Type of the monitor entry
     *
     * @param type the MonitorEntryType.
     */
    public void setType(MonitorEntryType type) {
        this.type = type;
    }

    /**
     * Type of the monitor entry
     *
     * @param type the String.
     */
    public void setType(String type) {
        if ((type == null)) {
            this.type = null;
        } else {
            this.type = MonitorEntryType.valueOf(type);
        }
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(MonitorEntry.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(MonitorEntry.class).getAllProperties();
    }
}
