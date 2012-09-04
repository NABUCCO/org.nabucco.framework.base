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
package org.nabucco.framework.base.facade.datatype.extension.schema.workflow.effect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.BooleanProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.IntegerProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.workflow.WorkflowEffectExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * ConstraintEffectExtension<p/>Extension for configuring a constraint effect.<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-05-07
 */
public class ConstraintEffectExtension extends WorkflowEffectExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,1;", "m0,1;", "m0,1;", "m0,1;", "m0,1;", "m0,1;",
            "m0,1;" };

    public static final String PROPERTYNAME = "propertyName";

    public static final String EDITABLE = "editable";

    public static final String VISIBLE = "visible";

    public static final String MINLENGTH = "minLength";

    public static final String MAXLENGTH = "maxLength";

    public static final String MINMULTIPLICITY = "minMultiplicity";

    public static final String MAXMULTIPLICITY = "maxMultiplicity";

    /** Name of the property to add the constraint to. */
    private StringProperty propertyName;

    /** Whether the property should be editable or not. */
    private BooleanProperty editable;

    /** Whether the property should be visible or not. */
    private BooleanProperty visible;

    /** The new property min length. */
    private IntegerProperty minLength;

    /** The new property max length. */
    private IntegerProperty maxLength;

    /** The new property max multiplicity. */
    private IntegerProperty minMultiplicity;

    /** The new property max multiplicity. */
    private IntegerProperty maxMultiplicity;

    /** Constructs a new ConstraintEffectExtension instance. */
    public ConstraintEffectExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ConstraintEffectExtension.
     */
    protected void cloneObject(ConstraintEffectExtension clone) {
        super.cloneObject(clone);
        if ((this.getPropertyName() != null)) {
            clone.setPropertyName(this.getPropertyName().cloneObject());
        }
        if ((this.getEditable() != null)) {
            clone.setEditable(this.getEditable().cloneObject());
        }
        if ((this.getVisible() != null)) {
            clone.setVisible(this.getVisible().cloneObject());
        }
        if ((this.getMinLength() != null)) {
            clone.setMinLength(this.getMinLength().cloneObject());
        }
        if ((this.getMaxLength() != null)) {
            clone.setMaxLength(this.getMaxLength().cloneObject());
        }
        if ((this.getMinMultiplicity() != null)) {
            clone.setMinMultiplicity(this.getMinMultiplicity().cloneObject());
        }
        if ((this.getMaxMultiplicity() != null)) {
            clone.setMaxMultiplicity(this.getMaxMultiplicity().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(WorkflowEffectExtension.class).getPropertyMap());
        propertyMap.put(PROPERTYNAME, PropertyDescriptorSupport.createDatatype(PROPERTYNAME, StringProperty.class, 6,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(EDITABLE, PropertyDescriptorSupport.createDatatype(EDITABLE, BooleanProperty.class, 7,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(VISIBLE, PropertyDescriptorSupport.createDatatype(VISIBLE, BooleanProperty.class, 8,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(MINLENGTH, PropertyDescriptorSupport.createDatatype(MINLENGTH, IntegerProperty.class, 9,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(MAXLENGTH, PropertyDescriptorSupport.createDatatype(MAXLENGTH, IntegerProperty.class, 10,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(MINMULTIPLICITY, PropertyDescriptorSupport.createDatatype(MINMULTIPLICITY,
                IntegerProperty.class, 11, PROPERTY_CONSTRAINTS[5], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(MAXMULTIPLICITY, PropertyDescriptorSupport.createDatatype(MAXMULTIPLICITY,
                IntegerProperty.class, 12, PROPERTY_CONSTRAINTS[6], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ConstraintEffectExtension.getPropertyDescriptor(PROPERTYNAME),
                this.getPropertyName(), null));
        properties.add(super.createProperty(ConstraintEffectExtension.getPropertyDescriptor(EDITABLE),
                this.getEditable(), null));
        properties.add(super.createProperty(ConstraintEffectExtension.getPropertyDescriptor(VISIBLE),
                this.getVisible(), null));
        properties.add(super.createProperty(ConstraintEffectExtension.getPropertyDescriptor(MINLENGTH),
                this.getMinLength(), null));
        properties.add(super.createProperty(ConstraintEffectExtension.getPropertyDescriptor(MAXLENGTH),
                this.getMaxLength(), null));
        properties.add(super.createProperty(ConstraintEffectExtension.getPropertyDescriptor(MINMULTIPLICITY),
                this.getMinMultiplicity(), null));
        properties.add(super.createProperty(ConstraintEffectExtension.getPropertyDescriptor(MAXMULTIPLICITY),
                this.getMaxMultiplicity(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(PROPERTYNAME) && (property.getType() == StringProperty.class))) {
            this.setPropertyName(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(EDITABLE) && (property.getType() == BooleanProperty.class))) {
            this.setEditable(((BooleanProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(VISIBLE) && (property.getType() == BooleanProperty.class))) {
            this.setVisible(((BooleanProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MINLENGTH) && (property.getType() == IntegerProperty.class))) {
            this.setMinLength(((IntegerProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MAXLENGTH) && (property.getType() == IntegerProperty.class))) {
            this.setMaxLength(((IntegerProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MINMULTIPLICITY) && (property.getType() == IntegerProperty.class))) {
            this.setMinMultiplicity(((IntegerProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MAXMULTIPLICITY) && (property.getType() == IntegerProperty.class))) {
            this.setMaxMultiplicity(((IntegerProperty) property.getInstance()));
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
        final ConstraintEffectExtension other = ((ConstraintEffectExtension) obj);
        if ((this.propertyName == null)) {
            if ((other.propertyName != null))
                return false;
        } else if ((!this.propertyName.equals(other.propertyName)))
            return false;
        if ((this.editable == null)) {
            if ((other.editable != null))
                return false;
        } else if ((!this.editable.equals(other.editable)))
            return false;
        if ((this.visible == null)) {
            if ((other.visible != null))
                return false;
        } else if ((!this.visible.equals(other.visible)))
            return false;
        if ((this.minLength == null)) {
            if ((other.minLength != null))
                return false;
        } else if ((!this.minLength.equals(other.minLength)))
            return false;
        if ((this.maxLength == null)) {
            if ((other.maxLength != null))
                return false;
        } else if ((!this.maxLength.equals(other.maxLength)))
            return false;
        if ((this.minMultiplicity == null)) {
            if ((other.minMultiplicity != null))
                return false;
        } else if ((!this.minMultiplicity.equals(other.minMultiplicity)))
            return false;
        if ((this.maxMultiplicity == null)) {
            if ((other.maxMultiplicity != null))
                return false;
        } else if ((!this.maxMultiplicity.equals(other.maxMultiplicity)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.propertyName == null) ? 0 : this.propertyName.hashCode()));
        result = ((PRIME * result) + ((this.editable == null) ? 0 : this.editable.hashCode()));
        result = ((PRIME * result) + ((this.visible == null) ? 0 : this.visible.hashCode()));
        result = ((PRIME * result) + ((this.minLength == null) ? 0 : this.minLength.hashCode()));
        result = ((PRIME * result) + ((this.maxLength == null) ? 0 : this.maxLength.hashCode()));
        result = ((PRIME * result) + ((this.minMultiplicity == null) ? 0 : this.minMultiplicity.hashCode()));
        result = ((PRIME * result) + ((this.maxMultiplicity == null) ? 0 : this.maxMultiplicity.hashCode()));
        return result;
    }

    @Override
    public ConstraintEffectExtension cloneObject() {
        ConstraintEffectExtension clone = new ConstraintEffectExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Name of the property to add the constraint to.
     *
     * @param propertyName the StringProperty.
     */
    public void setPropertyName(StringProperty propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * Name of the property to add the constraint to.
     *
     * @return the StringProperty.
     */
    public StringProperty getPropertyName() {
        return this.propertyName;
    }

    /**
     * Whether the property should be editable or not.
     *
     * @param editable the BooleanProperty.
     */
    public void setEditable(BooleanProperty editable) {
        this.editable = editable;
    }

    /**
     * Whether the property should be editable or not.
     *
     * @return the BooleanProperty.
     */
    public BooleanProperty getEditable() {
        return this.editable;
    }

    /**
     * Whether the property should be visible or not.
     *
     * @param visible the BooleanProperty.
     */
    public void setVisible(BooleanProperty visible) {
        this.visible = visible;
    }

    /**
     * Whether the property should be visible or not.
     *
     * @return the BooleanProperty.
     */
    public BooleanProperty getVisible() {
        return this.visible;
    }

    /**
     * The new property min length.
     *
     * @param minLength the IntegerProperty.
     */
    public void setMinLength(IntegerProperty minLength) {
        this.minLength = minLength;
    }

    /**
     * The new property min length.
     *
     * @return the IntegerProperty.
     */
    public IntegerProperty getMinLength() {
        return this.minLength;
    }

    /**
     * The new property max length.
     *
     * @param maxLength the IntegerProperty.
     */
    public void setMaxLength(IntegerProperty maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * The new property max length.
     *
     * @return the IntegerProperty.
     */
    public IntegerProperty getMaxLength() {
        return this.maxLength;
    }

    /**
     * The new property max multiplicity.
     *
     * @param minMultiplicity the IntegerProperty.
     */
    public void setMinMultiplicity(IntegerProperty minMultiplicity) {
        this.minMultiplicity = minMultiplicity;
    }

    /**
     * The new property max multiplicity.
     *
     * @return the IntegerProperty.
     */
    public IntegerProperty getMinMultiplicity() {
        return this.minMultiplicity;
    }

    /**
     * The new property max multiplicity.
     *
     * @param maxMultiplicity the IntegerProperty.
     */
    public void setMaxMultiplicity(IntegerProperty maxMultiplicity) {
        this.maxMultiplicity = maxMultiplicity;
    }

    /**
     * The new property max multiplicity.
     *
     * @return the IntegerProperty.
     */
    public IntegerProperty getMaxMultiplicity() {
        return this.maxMultiplicity;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ConstraintEffectExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ConstraintEffectExtension.class).getAllProperties();
    }
}
