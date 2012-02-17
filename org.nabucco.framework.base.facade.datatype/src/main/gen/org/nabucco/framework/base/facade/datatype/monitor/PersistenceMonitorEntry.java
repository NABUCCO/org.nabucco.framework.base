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
import org.nabucco.framework.base.facade.datatype.Duration;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Query;
import org.nabucco.framework.base.facade.datatype.monitor.MonitorEntry;
import org.nabucco.framework.base.facade.datatype.monitor.MonitorEntryType;
import org.nabucco.framework.base.facade.datatype.monitor.PersistenceOperationType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * PersistenceMonitorEntry<p/>Monitor entry for persistence operation monitoringss.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-04-06
 */
public class PersistenceMonitorEntry extends MonitorEntry implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final MonitorEntryType TYPE_DEFAULT = MonitorEntryType.PERSISTENCE_OPERATION;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m0,1;", "m1,1;", "l0,100000;u0,n;m0,1;",
            "l0,n;u1,n;m1,1;" };

    public static final String ENTITYNAME = "entityName";

    public static final String OPERATIONTYPE = "operationType";

    public static final String QUERY = "query";

    public static final String DURATION = "duration";

    /** Name of the Entity */
    private Name entityName;

    /** Type of the persistence operation */
    private PersistenceOperationType operationType;

    /** The executed query. */
    private Query query;

    /** Duration of the execution in centiseconds. */
    private Duration duration;

    /** Constructs a new PersistenceMonitorEntry instance. */
    public PersistenceMonitorEntry() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
        type = TYPE_DEFAULT;
    }

    /**
     * CloneObject.
     *
     * @param clone the PersistenceMonitorEntry.
     */
    protected void cloneObject(PersistenceMonitorEntry clone) {
        super.cloneObject(clone);
        clone.setType(this.getType());
        if ((this.getEntityName() != null)) {
            clone.setEntityName(this.getEntityName().cloneObject());
        }
        clone.setOperationType(this.getOperationType());
        if ((this.getQuery() != null)) {
            clone.setQuery(this.getQuery().cloneObject());
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
        propertyMap.putAll(PropertyCache.getInstance().retrieve(MonitorEntry.class).getPropertyMap());
        propertyMap.put(ENTITYNAME,
                PropertyDescriptorSupport.createBasetype(ENTITYNAME, Name.class, 7, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(OPERATIONTYPE, PropertyDescriptorSupport.createEnumeration(OPERATIONTYPE,
                PersistenceOperationType.class, 8, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(QUERY,
                PropertyDescriptorSupport.createBasetype(QUERY, Query.class, 9, PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(DURATION,
                PropertyDescriptorSupport.createBasetype(DURATION, Duration.class, 10, PROPERTY_CONSTRAINTS[3], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(PersistenceMonitorEntry.getPropertyDescriptor(ENTITYNAME), this.entityName,
                null));
        properties.add(super.createProperty(PersistenceMonitorEntry.getPropertyDescriptor(OPERATIONTYPE),
                this.getOperationType(), null));
        properties.add(super.createProperty(PersistenceMonitorEntry.getPropertyDescriptor(QUERY), this.query, null));
        properties.add(super.createProperty(PersistenceMonitorEntry.getPropertyDescriptor(DURATION), this.duration,
                null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(ENTITYNAME) && (property.getType() == Name.class))) {
            this.setEntityName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(OPERATIONTYPE) && (property.getType() == PersistenceOperationType.class))) {
            this.setOperationType(((PersistenceOperationType) property.getInstance()));
            return true;
        } else if ((property.getName().equals(QUERY) && (property.getType() == Query.class))) {
            this.setQuery(((Query) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DURATION) && (property.getType() == Duration.class))) {
            this.setDuration(((Duration) property.getInstance()));
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
        final PersistenceMonitorEntry other = ((PersistenceMonitorEntry) obj);
        if ((this.entityName == null)) {
            if ((other.entityName != null))
                return false;
        } else if ((!this.entityName.equals(other.entityName)))
            return false;
        if ((this.operationType == null)) {
            if ((other.operationType != null))
                return false;
        } else if ((!this.operationType.equals(other.operationType)))
            return false;
        if ((this.query == null)) {
            if ((other.query != null))
                return false;
        } else if ((!this.query.equals(other.query)))
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
        result = ((PRIME * result) + ((this.entityName == null) ? 0 : this.entityName.hashCode()));
        result = ((PRIME * result) + ((this.operationType == null) ? 0 : this.operationType.hashCode()));
        result = ((PRIME * result) + ((this.query == null) ? 0 : this.query.hashCode()));
        result = ((PRIME * result) + ((this.duration == null) ? 0 : this.duration.hashCode()));
        return result;
    }

    @Override
    public PersistenceMonitorEntry cloneObject() {
        PersistenceMonitorEntry clone = new PersistenceMonitorEntry();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Name of the Entity
     *
     * @return the Name.
     */
    public Name getEntityName() {
        return this.entityName;
    }

    /**
     * Name of the Entity
     *
     * @param entityName the Name.
     */
    public void setEntityName(Name entityName) {
        this.entityName = entityName;
    }

    /**
     * Name of the Entity
     *
     * @param entityName the String.
     */
    public void setEntityName(String entityName) {
        if ((this.entityName == null)) {
            if ((entityName == null)) {
                return;
            }
            this.entityName = new Name();
        }
        this.entityName.setValue(entityName);
    }

    /**
     * Type of the persistence operation
     *
     * @return the PersistenceOperationType.
     */
    public PersistenceOperationType getOperationType() {
        return this.operationType;
    }

    /**
     * Type of the persistence operation
     *
     * @param operationType the PersistenceOperationType.
     */
    public void setOperationType(PersistenceOperationType operationType) {
        this.operationType = operationType;
    }

    /**
     * Type of the persistence operation
     *
     * @param operationType the String.
     */
    public void setOperationType(String operationType) {
        if ((operationType == null)) {
            this.operationType = null;
        } else {
            this.operationType = PersistenceOperationType.valueOf(operationType);
        }
    }

    /**
     * The executed query.
     *
     * @return the Query.
     */
    public Query getQuery() {
        return this.query;
    }

    /**
     * The executed query.
     *
     * @param query the Query.
     */
    public void setQuery(Query query) {
        this.query = query;
    }

    /**
     * The executed query.
     *
     * @param query the String.
     */
    public void setQuery(String query) {
        if ((this.query == null)) {
            if ((query == null)) {
                return;
            }
            this.query = new Query();
        }
        this.query.setValue(query);
    }

    /**
     * Duration of the execution in centiseconds.
     *
     * @return the Duration.
     */
    public Duration getDuration() {
        return this.duration;
    }

    /**
     * Duration of the execution in centiseconds.
     *
     * @param duration the Duration.
     */
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    /**
     * Duration of the execution in centiseconds.
     *
     * @param duration the Long.
     */
    public void setDuration(Long duration) {
        if ((this.duration == null)) {
            if ((duration == null)) {
                return;
            }
            this.duration = new Duration();
        }
        this.duration.setValue(duration);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(PersistenceMonitorEntry.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(PersistenceMonitorEntry.class).getAllProperties();
    }
}
