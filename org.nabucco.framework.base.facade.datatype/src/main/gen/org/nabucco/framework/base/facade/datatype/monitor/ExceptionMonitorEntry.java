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
package org.nabucco.framework.base.facade.datatype.monitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.monitor.MonitorEntry;
import org.nabucco.framework.base.facade.datatype.monitor.MonitorEntryType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * ExceptionMonitorEntry<p/>Monitor entry for exception monitoring.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-04-06
 */
public class ExceptionMonitorEntry extends MonitorEntry implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final MonitorEntryType TYPE_DEFAULT = MonitorEntryType.EXCEPTION;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m1,1;" };

    public static final String EXCEPTIONNAME = "exceptionName";

    /** Name of the raised exception. */
    private Name exceptionName;

    /** Constructs a new ExceptionMonitorEntry instance. */
    public ExceptionMonitorEntry() {
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
     * @param clone the ExceptionMonitorEntry.
     */
    protected void cloneObject(ExceptionMonitorEntry clone) {
        super.cloneObject(clone);
        clone.setType(this.getType());
        if ((this.getExceptionName() != null)) {
            clone.setExceptionName(this.getExceptionName().cloneObject());
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
        propertyMap.put(EXCEPTIONNAME,
                PropertyDescriptorSupport.createBasetype(EXCEPTIONNAME, Name.class, 7, PROPERTY_CONSTRAINTS[0], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ExceptionMonitorEntry.getPropertyDescriptor(EXCEPTIONNAME),
                this.exceptionName, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(EXCEPTIONNAME) && (property.getType() == Name.class))) {
            this.setExceptionName(((Name) property.getInstance()));
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
        final ExceptionMonitorEntry other = ((ExceptionMonitorEntry) obj);
        if ((this.exceptionName == null)) {
            if ((other.exceptionName != null))
                return false;
        } else if ((!this.exceptionName.equals(other.exceptionName)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.exceptionName == null) ? 0 : this.exceptionName.hashCode()));
        return result;
    }

    @Override
    public ExceptionMonitorEntry cloneObject() {
        ExceptionMonitorEntry clone = new ExceptionMonitorEntry();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Name of the raised exception.
     *
     * @return the Name.
     */
    public Name getExceptionName() {
        return this.exceptionName;
    }

    /**
     * Name of the raised exception.
     *
     * @param exceptionName the Name.
     */
    public void setExceptionName(Name exceptionName) {
        this.exceptionName = exceptionName;
    }

    /**
     * Name of the raised exception.
     *
     * @param exceptionName the String.
     */
    public void setExceptionName(String exceptionName) {
        if ((this.exceptionName == null)) {
            if ((exceptionName == null)) {
                return;
            }
            this.exceptionName = new Name();
        }
        this.exceptionName.setValue(exceptionName);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ExceptionMonitorEntry.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ExceptionMonitorEntry.class).getAllProperties();
    }
}
