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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.dashboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * WidgetReferenceExtension<p/>NABUCCO User Interface Dashboard widget reference extension.<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2011-11-10
 */
public class WidgetReferenceExtension extends WorkItemExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;" };

    public static final String WIDGETID = "widgetId";

    public static final String POSITION = "position";

    /** The widgets id to show on the dashboard */
    public StringProperty widgetId;

    /** The position of the widget on the dashboard grid */
    public StringProperty position;

    /** Constructs a new WidgetReferenceExtension instance. */
    public WidgetReferenceExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the WidgetReferenceExtension.
     */
    protected void cloneObject(WidgetReferenceExtension clone) {
        super.cloneObject(clone);
        if ((this.getWidgetId() != null)) {
            clone.setWidgetId(this.getWidgetId().cloneObject());
        }
        if ((this.getPosition() != null)) {
            clone.setPosition(this.getPosition().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(WorkItemExtension.class).getPropertyMap());
        propertyMap.put(WIDGETID, PropertyDescriptorSupport.createDatatype(WIDGETID, StringProperty.class, 11,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(POSITION, PropertyDescriptorSupport.createDatatype(POSITION, StringProperty.class, 12,
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
        properties.add(super.createProperty(WidgetReferenceExtension.getPropertyDescriptor(WIDGETID),
                this.getWidgetId(), null));
        properties.add(super.createProperty(WidgetReferenceExtension.getPropertyDescriptor(POSITION),
                this.getPosition(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(WIDGETID) && (property.getType() == StringProperty.class))) {
            this.setWidgetId(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(POSITION) && (property.getType() == StringProperty.class))) {
            this.setPosition(((StringProperty) property.getInstance()));
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
        final WidgetReferenceExtension other = ((WidgetReferenceExtension) obj);
        if ((this.widgetId == null)) {
            if ((other.widgetId != null))
                return false;
        } else if ((!this.widgetId.equals(other.widgetId)))
            return false;
        if ((this.position == null)) {
            if ((other.position != null))
                return false;
        } else if ((!this.position.equals(other.position)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.widgetId == null) ? 0 : this.widgetId.hashCode()));
        result = ((PRIME * result) + ((this.position == null) ? 0 : this.position.hashCode()));
        return result;
    }

    @Override
    public WidgetReferenceExtension cloneObject() {
        WidgetReferenceExtension clone = new WidgetReferenceExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The widgets id to show on the dashboard
     *
     * @param widgetId the StringProperty.
     */
    public void setWidgetId(StringProperty widgetId) {
        this.widgetId = widgetId;
    }

    /**
     * The widgets id to show on the dashboard
     *
     * @return the StringProperty.
     */
    public StringProperty getWidgetId() {
        return this.widgetId;
    }

    /**
     * The position of the widget on the dashboard grid
     *
     * @param position the StringProperty.
     */
    public void setPosition(StringProperty position) {
        this.position = position;
    }

    /**
     * The position of the widget on the dashboard grid
     *
     * @return the StringProperty.
     */
    public StringProperty getPosition() {
        return this.position;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(WidgetReferenceExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(WidgetReferenceExtension.class).getAllProperties();
    }
}
