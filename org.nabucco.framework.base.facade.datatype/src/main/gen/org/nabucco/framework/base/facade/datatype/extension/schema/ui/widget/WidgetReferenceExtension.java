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
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * WidgetReferenceExtension<p/>NABUCCO User Interface StatusBar extension.<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2011-15-09
 */
public class WidgetReferenceExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;" };

    public static final String WIDGET = "widget";

    public static final String POSITION = "position";

    public static final String HINT = "hint";

    /** Widget identifier */
    private StringProperty widget;

    /** The Control position in the grid. */
    private StringProperty position;

    /** The Control positioning hint. */
    private StringProperty hint;

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
        if ((this.getWidget() != null)) {
            clone.setWidget(this.getWidget().cloneObject());
        }
        if ((this.getPosition() != null)) {
            clone.setPosition(this.getPosition().cloneObject());
        }
        if ((this.getHint() != null)) {
            clone.setHint(this.getHint().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(UiExtension.class).getPropertyMap());
        propertyMap.put(WIDGET, PropertyDescriptorSupport.createDatatype(WIDGET, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(POSITION, PropertyDescriptorSupport.createDatatype(POSITION, StringProperty.class, 5,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(HINT, PropertyDescriptorSupport.createDatatype(HINT, StringProperty.class, 6,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(WidgetReferenceExtension.getPropertyDescriptor(WIDGET), this.getWidget(),
                null));
        properties.add(super.createProperty(WidgetReferenceExtension.getPropertyDescriptor(POSITION),
                this.getPosition(), null));
        properties
                .add(super.createProperty(WidgetReferenceExtension.getPropertyDescriptor(HINT), this.getHint(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(WIDGET) && (property.getType() == StringProperty.class))) {
            this.setWidget(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(POSITION) && (property.getType() == StringProperty.class))) {
            this.setPosition(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(HINT) && (property.getType() == StringProperty.class))) {
            this.setHint(((StringProperty) property.getInstance()));
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
        if ((this.widget == null)) {
            if ((other.widget != null))
                return false;
        } else if ((!this.widget.equals(other.widget)))
            return false;
        if ((this.position == null)) {
            if ((other.position != null))
                return false;
        } else if ((!this.position.equals(other.position)))
            return false;
        if ((this.hint == null)) {
            if ((other.hint != null))
                return false;
        } else if ((!this.hint.equals(other.hint)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.widget == null) ? 0 : this.widget.hashCode()));
        result = ((PRIME * result) + ((this.position == null) ? 0 : this.position.hashCode()));
        result = ((PRIME * result) + ((this.hint == null) ? 0 : this.hint.hashCode()));
        return result;
    }

    @Override
    public WidgetReferenceExtension cloneObject() {
        WidgetReferenceExtension clone = new WidgetReferenceExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Widget identifier
     *
     * @param widget the StringProperty.
     */
    public void setWidget(StringProperty widget) {
        this.widget = widget;
    }

    /**
     * Widget identifier
     *
     * @return the StringProperty.
     */
    public StringProperty getWidget() {
        return this.widget;
    }

    /**
     * The Control position in the grid.
     *
     * @param position the StringProperty.
     */
    public void setPosition(StringProperty position) {
        this.position = position;
    }

    /**
     * The Control position in the grid.
     *
     * @return the StringProperty.
     */
    public StringProperty getPosition() {
        return this.position;
    }

    /**
     * The Control positioning hint.
     *
     * @param hint the StringProperty.
     */
    public void setHint(StringProperty hint) {
        this.hint = hint;
    }

    /**
     * The Control positioning hint.
     *
     * @return the StringProperty.
     */
    public StringProperty getHint() {
        return this.hint;
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
