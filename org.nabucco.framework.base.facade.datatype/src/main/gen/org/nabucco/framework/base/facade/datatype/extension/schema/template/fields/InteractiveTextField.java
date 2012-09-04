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
package org.nabucco.framework.base.facade.datatype.extension.schema.template.fields;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.IntegerProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.TemplateComposite;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * InteractiveTextField<p/>Field for interactive (user input) text.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinki, PRODYNA AG, 2011-02-24
 */
public class InteractiveTextField extends TemplateComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;" };

    public static final String MINLENGTH = "minLength";

    public static final String MAXLENGTH = "maxLength";

    /** The minimum length of the text. */
    private IntegerProperty minLength;

    /** The maximum length of the text. */
    private IntegerProperty maxLength;

    /** Constructs a new InteractiveTextField instance. */
    public InteractiveTextField() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the InteractiveTextField.
     */
    protected void cloneObject(InteractiveTextField clone) {
        super.cloneObject(clone);
        if ((this.getMinLength() != null)) {
            clone.setMinLength(this.getMinLength().cloneObject());
        }
        if ((this.getMaxLength() != null)) {
            clone.setMaxLength(this.getMaxLength().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(TemplateComposite.class).getPropertyMap());
        propertyMap.put(MINLENGTH, PropertyDescriptorSupport.createDatatype(MINLENGTH, IntegerProperty.class, 6,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(MAXLENGTH, PropertyDescriptorSupport.createDatatype(MAXLENGTH, IntegerProperty.class, 7,
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
        properties.add(super.createProperty(InteractiveTextField.getPropertyDescriptor(MINLENGTH), this.getMinLength(),
                null));
        properties.add(super.createProperty(InteractiveTextField.getPropertyDescriptor(MAXLENGTH), this.getMaxLength(),
                null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(MINLENGTH) && (property.getType() == IntegerProperty.class))) {
            this.setMinLength(((IntegerProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MAXLENGTH) && (property.getType() == IntegerProperty.class))) {
            this.setMaxLength(((IntegerProperty) property.getInstance()));
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
        final InteractiveTextField other = ((InteractiveTextField) obj);
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
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.minLength == null) ? 0 : this.minLength.hashCode()));
        result = ((PRIME * result) + ((this.maxLength == null) ? 0 : this.maxLength.hashCode()));
        return result;
    }

    @Override
    public InteractiveTextField cloneObject() {
        InteractiveTextField clone = new InteractiveTextField();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The minimum length of the text.
     *
     * @param minLength the IntegerProperty.
     */
    public void setMinLength(IntegerProperty minLength) {
        this.minLength = minLength;
    }

    /**
     * The minimum length of the text.
     *
     * @return the IntegerProperty.
     */
    public IntegerProperty getMinLength() {
        return this.minLength;
    }

    /**
     * The maximum length of the text.
     *
     * @param maxLength the IntegerProperty.
     */
    public void setMaxLength(IntegerProperty maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * The maximum length of the text.
     *
     * @return the IntegerProperty.
     */
    public IntegerProperty getMaxLength() {
        return this.maxLength;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(InteractiveTextField.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(InteractiveTextField.class).getAllProperties();
    }
}
