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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.messageset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * MessageSetMessageExtension<p/>NABUCCO Message set Extension<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-01-30
 */
public class MessageSetMessageExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m1,1;" };

    public static final String CONTROL = "control";

    public static final String CONTROLTYPE = "controlType";

    public static final String CONSTRAINT = "constraint";

    public static final String SEVERITY = "severity";

    public static final String TEXT = "text";

    /** The control id where the message is written for */
    private StringProperty control;

    /** The type of the control where the message is written for */
    private EnumerationProperty controlType;

    /** The constraint */
    private EnumerationProperty constraint;

    /** The severity of the message */
    private EnumerationProperty severity;

    /** The text to be shown */
    private StringProperty text;

    /** Constructs a new MessageSetMessageExtension instance. */
    public MessageSetMessageExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the MessageSetMessageExtension.
     */
    protected void cloneObject(MessageSetMessageExtension clone) {
        super.cloneObject(clone);
        if ((this.getControl() != null)) {
            clone.setControl(this.getControl().cloneObject());
        }
        if ((this.getControlType() != null)) {
            clone.setControlType(this.getControlType().cloneObject());
        }
        if ((this.getConstraint() != null)) {
            clone.setConstraint(this.getConstraint().cloneObject());
        }
        if ((this.getSeverity() != null)) {
            clone.setSeverity(this.getSeverity().cloneObject());
        }
        if ((this.getText() != null)) {
            clone.setText(this.getText().cloneObject());
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
        propertyMap.put(CONTROL, PropertyDescriptorSupport.createDatatype(CONTROL, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(CONTROLTYPE, PropertyDescriptorSupport.createDatatype(CONTROLTYPE, EnumerationProperty.class,
                5, PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(CONSTRAINT, PropertyDescriptorSupport.createDatatype(CONSTRAINT, EnumerationProperty.class, 6,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(SEVERITY, PropertyDescriptorSupport.createDatatype(SEVERITY, EnumerationProperty.class, 7,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(TEXT, PropertyDescriptorSupport.createDatatype(TEXT, StringProperty.class, 8,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(MessageSetMessageExtension.getPropertyDescriptor(CONTROL),
                this.getControl(), null));
        properties.add(super.createProperty(MessageSetMessageExtension.getPropertyDescriptor(CONTROLTYPE),
                this.getControlType(), null));
        properties.add(super.createProperty(MessageSetMessageExtension.getPropertyDescriptor(CONSTRAINT),
                this.getConstraint(), null));
        properties.add(super.createProperty(MessageSetMessageExtension.getPropertyDescriptor(SEVERITY),
                this.getSeverity(), null));
        properties.add(super.createProperty(MessageSetMessageExtension.getPropertyDescriptor(TEXT), this.getText(),
                null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(CONTROL) && (property.getType() == StringProperty.class))) {
            this.setControl(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CONTROLTYPE) && (property.getType() == EnumerationProperty.class))) {
            this.setControlType(((EnumerationProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CONSTRAINT) && (property.getType() == EnumerationProperty.class))) {
            this.setConstraint(((EnumerationProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SEVERITY) && (property.getType() == EnumerationProperty.class))) {
            this.setSeverity(((EnumerationProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TEXT) && (property.getType() == StringProperty.class))) {
            this.setText(((StringProperty) property.getInstance()));
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
        final MessageSetMessageExtension other = ((MessageSetMessageExtension) obj);
        if ((this.control == null)) {
            if ((other.control != null))
                return false;
        } else if ((!this.control.equals(other.control)))
            return false;
        if ((this.controlType == null)) {
            if ((other.controlType != null))
                return false;
        } else if ((!this.controlType.equals(other.controlType)))
            return false;
        if ((this.constraint == null)) {
            if ((other.constraint != null))
                return false;
        } else if ((!this.constraint.equals(other.constraint)))
            return false;
        if ((this.severity == null)) {
            if ((other.severity != null))
                return false;
        } else if ((!this.severity.equals(other.severity)))
            return false;
        if ((this.text == null)) {
            if ((other.text != null))
                return false;
        } else if ((!this.text.equals(other.text)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.control == null) ? 0 : this.control.hashCode()));
        result = ((PRIME * result) + ((this.controlType == null) ? 0 : this.controlType.hashCode()));
        result = ((PRIME * result) + ((this.constraint == null) ? 0 : this.constraint.hashCode()));
        result = ((PRIME * result) + ((this.severity == null) ? 0 : this.severity.hashCode()));
        result = ((PRIME * result) + ((this.text == null) ? 0 : this.text.hashCode()));
        return result;
    }

    @Override
    public MessageSetMessageExtension cloneObject() {
        MessageSetMessageExtension clone = new MessageSetMessageExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The control id where the message is written for
     *
     * @param control the StringProperty.
     */
    public void setControl(StringProperty control) {
        this.control = control;
    }

    /**
     * The control id where the message is written for
     *
     * @return the StringProperty.
     */
    public StringProperty getControl() {
        return this.control;
    }

    /**
     * The type of the control where the message is written for
     *
     * @param controlType the EnumerationProperty.
     */
    public void setControlType(EnumerationProperty controlType) {
        this.controlType = controlType;
    }

    /**
     * The type of the control where the message is written for
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getControlType() {
        return this.controlType;
    }

    /**
     * The constraint
     *
     * @param constraint the EnumerationProperty.
     */
    public void setConstraint(EnumerationProperty constraint) {
        this.constraint = constraint;
    }

    /**
     * The constraint
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getConstraint() {
        return this.constraint;
    }

    /**
     * The severity of the message
     *
     * @param severity the EnumerationProperty.
     */
    public void setSeverity(EnumerationProperty severity) {
        this.severity = severity;
    }

    /**
     * The severity of the message
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getSeverity() {
        return this.severity;
    }

    /**
     * The text to be shown
     *
     * @param text the StringProperty.
     */
    public void setText(StringProperty text) {
        this.text = text;
    }

    /**
     * The text to be shown
     *
     * @return the StringProperty.
     */
    public StringProperty getText() {
        return this.text;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(MessageSetMessageExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(MessageSetMessageExtension.class).getAllProperties();
    }
}
