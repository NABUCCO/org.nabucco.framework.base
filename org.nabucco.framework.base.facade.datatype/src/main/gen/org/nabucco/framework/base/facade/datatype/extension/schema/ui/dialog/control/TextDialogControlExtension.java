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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.BooleanProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.control.DialogControlExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * TextDialogControlExtension<p/>NABUCCO User Interface Dialog extension control.<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2011-11-08
 */
public class TextDialogControlExtension extends DialogControlExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m0,1;" };

    public static final String EDITABLE = "editable";

    public static final String DEFAULTVALUE = "defaultValue";

    /** Indicates if the control is editable */
    private BooleanProperty editable;

    /** Defines the default value showed in the control */
    private StringProperty defaultValue;

    /** Constructs a new TextDialogControlExtension instance. */
    public TextDialogControlExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TextDialogControlExtension.
     */
    protected void cloneObject(TextDialogControlExtension clone) {
        super.cloneObject(clone);
        if ((this.getEditable() != null)) {
            clone.setEditable(this.getEditable().cloneObject());
        }
        if ((this.getDefaultValue() != null)) {
            clone.setDefaultValue(this.getDefaultValue().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(DialogControlExtension.class).getPropertyMap());
        propertyMap.put(EDITABLE, PropertyDescriptorSupport.createDatatype(EDITABLE, BooleanProperty.class, 9,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(DEFAULTVALUE, PropertyDescriptorSupport.createDatatype(DEFAULTVALUE, StringProperty.class, 10,
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
        properties.add(super.createProperty(TextDialogControlExtension.getPropertyDescriptor(EDITABLE),
                this.getEditable(), null));
        properties.add(super.createProperty(TextDialogControlExtension.getPropertyDescriptor(DEFAULTVALUE),
                this.getDefaultValue(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(EDITABLE) && (property.getType() == BooleanProperty.class))) {
            this.setEditable(((BooleanProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DEFAULTVALUE) && (property.getType() == StringProperty.class))) {
            this.setDefaultValue(((StringProperty) property.getInstance()));
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
        final TextDialogControlExtension other = ((TextDialogControlExtension) obj);
        if ((this.editable == null)) {
            if ((other.editable != null))
                return false;
        } else if ((!this.editable.equals(other.editable)))
            return false;
        if ((this.defaultValue == null)) {
            if ((other.defaultValue != null))
                return false;
        } else if ((!this.defaultValue.equals(other.defaultValue)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.editable == null) ? 0 : this.editable.hashCode()));
        result = ((PRIME * result) + ((this.defaultValue == null) ? 0 : this.defaultValue.hashCode()));
        return result;
    }

    @Override
    public TextDialogControlExtension cloneObject() {
        TextDialogControlExtension clone = new TextDialogControlExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Indicates if the control is editable
     *
     * @param editable the BooleanProperty.
     */
    public void setEditable(BooleanProperty editable) {
        this.editable = editable;
    }

    /**
     * Indicates if the control is editable
     *
     * @return the BooleanProperty.
     */
    public BooleanProperty getEditable() {
        return this.editable;
    }

    /**
     * Defines the default value showed in the control
     *
     * @param defaultValue the StringProperty.
     */
    public void setDefaultValue(StringProperty defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * Defines the default value showed in the control
     *
     * @return the StringProperty.
     */
    public StringProperty getDefaultValue() {
        return this.defaultValue;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TextDialogControlExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TextDialogControlExtension.class).getAllProperties();
    }
}
