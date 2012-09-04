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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget.WidgetExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * CalendarWidgetExtension<p/>NABUCCO Calendar widget<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2011-15-09
 */
public class CalendarWidgetExtension extends WidgetExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;" };

    public static final String DISPLAYTYPE = "displayType";

    /** The calendar type to display */
    private EnumerationProperty displayType;

    /** Constructs a new CalendarWidgetExtension instance. */
    public CalendarWidgetExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the CalendarWidgetExtension.
     */
    protected void cloneObject(CalendarWidgetExtension clone) {
        super.cloneObject(clone);
        if ((this.getDisplayType() != null)) {
            clone.setDisplayType(this.getDisplayType().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(WidgetExtension.class).getPropertyMap());
        propertyMap.put(DISPLAYTYPE, PropertyDescriptorSupport.createDatatype(DISPLAYTYPE, EnumerationProperty.class,
                6, PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(CalendarWidgetExtension.getPropertyDescriptor(DISPLAYTYPE),
                this.getDisplayType(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(DISPLAYTYPE) && (property.getType() == EnumerationProperty.class))) {
            this.setDisplayType(((EnumerationProperty) property.getInstance()));
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
        final CalendarWidgetExtension other = ((CalendarWidgetExtension) obj);
        if ((this.displayType == null)) {
            if ((other.displayType != null))
                return false;
        } else if ((!this.displayType.equals(other.displayType)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.displayType == null) ? 0 : this.displayType.hashCode()));
        return result;
    }

    @Override
    public CalendarWidgetExtension cloneObject() {
        CalendarWidgetExtension clone = new CalendarWidgetExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The calendar type to display
     *
     * @param displayType the EnumerationProperty.
     */
    public void setDisplayType(EnumerationProperty displayType) {
        this.displayType = displayType;
    }

    /**
     * The calendar type to display
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getDisplayType() {
        return this.displayType;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(CalendarWidgetExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(CalendarWidgetExtension.class).getAllProperties();
    }
}
