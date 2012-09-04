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
 * IndicatorWidgetExtension<p/>NABUCCO Text Widget<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2011-15-09
 */
public class IndicatorWidgetExtension extends WidgetExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;" };

    public static final String INDICATION = "indication";

    /** The Widget indication type. */
    private EnumerationProperty indication;

    /** Constructs a new IndicatorWidgetExtension instance. */
    public IndicatorWidgetExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the IndicatorWidgetExtension.
     */
    protected void cloneObject(IndicatorWidgetExtension clone) {
        super.cloneObject(clone);
        if ((this.getIndication() != null)) {
            clone.setIndication(this.getIndication().cloneObject());
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
        propertyMap.put(INDICATION, PropertyDescriptorSupport.createDatatype(INDICATION, EnumerationProperty.class, 6,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(IndicatorWidgetExtension.getPropertyDescriptor(INDICATION),
                this.getIndication(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(INDICATION) && (property.getType() == EnumerationProperty.class))) {
            this.setIndication(((EnumerationProperty) property.getInstance()));
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
        final IndicatorWidgetExtension other = ((IndicatorWidgetExtension) obj);
        if ((this.indication == null)) {
            if ((other.indication != null))
                return false;
        } else if ((!this.indication.equals(other.indication)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.indication == null) ? 0 : this.indication.hashCode()));
        return result;
    }

    @Override
    public IndicatorWidgetExtension cloneObject() {
        IndicatorWidgetExtension clone = new IndicatorWidgetExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The Widget indication type.
     *
     * @param indication the EnumerationProperty.
     */
    public void setIndication(EnumerationProperty indication) {
        this.indication = indication;
    }

    /**
     * The Widget indication type.
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getIndication() {
        return this.indication;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(IndicatorWidgetExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(IndicatorWidgetExtension.class).getAllProperties();
    }
}
