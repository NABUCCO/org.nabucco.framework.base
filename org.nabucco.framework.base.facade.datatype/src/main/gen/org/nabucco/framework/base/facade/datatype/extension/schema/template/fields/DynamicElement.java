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
package org.nabucco.framework.base.facade.datatype.extension.schema.template.fields;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Flag;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.TemplateElement;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * DynamicElement<p/>References dynamic values<p/>
 *
 * @version 1.0
 * @author Holger Librenz, PRODYNA AG, 2011-03-29
 */
public class DynamicElement extends TemplateElement implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;u0,n;m1,1;", "l0,n;u0,n;m1,1;", "l0,255;u0,n;m0,1;",
            "l0,255;u0,n;m0,1;", "l0,255;u0,n;m1,1;" };

    public static final String ISTRANSIENT = "isTransient";

    public static final String ISINPUT = "isInput";

    public static final String LABEL = "label";

    public static final String TYPE = "type";

    public static final String VALUE = "value";

    /** If true, the value is not persisted, only viewed (use it e.g. for references) */
    private Flag isTransient;

    /** If true, the value can be edited in Edit Mode */
    private Flag isInput;

    /** Just a description for the element */
    private Name label;

    /** Type hint for the value */
    private Name type;

    /** Pre-defined value or reference to context value (indicated by '$') */
    private Name value;

    /** Constructs a new DynamicElement instance. */
    public DynamicElement() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the DynamicElement.
     */
    protected void cloneObject(DynamicElement clone) {
        super.cloneObject(clone);
        if ((this.getIsTransient() != null)) {
            clone.setIsTransient(this.getIsTransient().cloneObject());
        }
        if ((this.getIsInput() != null)) {
            clone.setIsInput(this.getIsInput().cloneObject());
        }
        if ((this.getLabel() != null)) {
            clone.setLabel(this.getLabel().cloneObject());
        }
        if ((this.getType() != null)) {
            clone.setType(this.getType().cloneObject());
        }
        if ((this.getValue() != null)) {
            clone.setValue(this.getValue().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(TemplateElement.class).getPropertyMap());
        propertyMap.put(ISTRANSIENT,
                PropertyDescriptorSupport.createBasetype(ISTRANSIENT, Flag.class, 4, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(ISINPUT,
                PropertyDescriptorSupport.createBasetype(ISINPUT, Flag.class, 5, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(LABEL,
                PropertyDescriptorSupport.createBasetype(LABEL, Name.class, 6, PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(TYPE,
                PropertyDescriptorSupport.createBasetype(TYPE, Name.class, 7, PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(VALUE,
                PropertyDescriptorSupport.createBasetype(VALUE, Name.class, 8, PROPERTY_CONSTRAINTS[4], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(DynamicElement.getPropertyDescriptor(ISTRANSIENT), this.isTransient, null));
        properties.add(super.createProperty(DynamicElement.getPropertyDescriptor(ISINPUT), this.isInput, null));
        properties.add(super.createProperty(DynamicElement.getPropertyDescriptor(LABEL), this.label, null));
        properties.add(super.createProperty(DynamicElement.getPropertyDescriptor(TYPE), this.type, null));
        properties.add(super.createProperty(DynamicElement.getPropertyDescriptor(VALUE), this.value, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(ISTRANSIENT) && (property.getType() == Flag.class))) {
            this.setIsTransient(((Flag) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ISINPUT) && (property.getType() == Flag.class))) {
            this.setIsInput(((Flag) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LABEL) && (property.getType() == Name.class))) {
            this.setLabel(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TYPE) && (property.getType() == Name.class))) {
            this.setType(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(VALUE) && (property.getType() == Name.class))) {
            this.setValue(((Name) property.getInstance()));
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
        final DynamicElement other = ((DynamicElement) obj);
        if ((this.isTransient == null)) {
            if ((other.isTransient != null))
                return false;
        } else if ((!this.isTransient.equals(other.isTransient)))
            return false;
        if ((this.isInput == null)) {
            if ((other.isInput != null))
                return false;
        } else if ((!this.isInput.equals(other.isInput)))
            return false;
        if ((this.label == null)) {
            if ((other.label != null))
                return false;
        } else if ((!this.label.equals(other.label)))
            return false;
        if ((this.type == null)) {
            if ((other.type != null))
                return false;
        } else if ((!this.type.equals(other.type)))
            return false;
        if ((this.value == null)) {
            if ((other.value != null))
                return false;
        } else if ((!this.value.equals(other.value)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.isTransient == null) ? 0 : this.isTransient.hashCode()));
        result = ((PRIME * result) + ((this.isInput == null) ? 0 : this.isInput.hashCode()));
        result = ((PRIME * result) + ((this.label == null) ? 0 : this.label.hashCode()));
        result = ((PRIME * result) + ((this.type == null) ? 0 : this.type.hashCode()));
        result = ((PRIME * result) + ((this.value == null) ? 0 : this.value.hashCode()));
        return result;
    }

    @Override
    public DynamicElement cloneObject() {
        DynamicElement clone = new DynamicElement();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * If true, the value is not persisted, only viewed (use it e.g. for references)
     *
     * @return the Flag.
     */
    public Flag getIsTransient() {
        return this.isTransient;
    }

    /**
     * If true, the value is not persisted, only viewed (use it e.g. for references)
     *
     * @param isTransient the Flag.
     */
    public void setIsTransient(Flag isTransient) {
        this.isTransient = isTransient;
    }

    /**
     * If true, the value is not persisted, only viewed (use it e.g. for references)
     *
     * @param isTransient the Boolean.
     */
    public void setIsTransient(Boolean isTransient) {
        if ((this.isTransient == null)) {
            if ((isTransient == null)) {
                return;
            }
            this.isTransient = new Flag();
        }
        this.isTransient.setValue(isTransient);
    }

    /**
     * If true, the value can be edited in Edit Mode
     *
     * @return the Flag.
     */
    public Flag getIsInput() {
        return this.isInput;
    }

    /**
     * If true, the value can be edited in Edit Mode
     *
     * @param isInput the Flag.
     */
    public void setIsInput(Flag isInput) {
        this.isInput = isInput;
    }

    /**
     * If true, the value can be edited in Edit Mode
     *
     * @param isInput the Boolean.
     */
    public void setIsInput(Boolean isInput) {
        if ((this.isInput == null)) {
            if ((isInput == null)) {
                return;
            }
            this.isInput = new Flag();
        }
        this.isInput.setValue(isInput);
    }

    /**
     * Just a description for the element
     *
     * @return the Name.
     */
    public Name getLabel() {
        return this.label;
    }

    /**
     * Just a description for the element
     *
     * @param label the Name.
     */
    public void setLabel(Name label) {
        this.label = label;
    }

    /**
     * Just a description for the element
     *
     * @param label the String.
     */
    public void setLabel(String label) {
        if ((this.label == null)) {
            if ((label == null)) {
                return;
            }
            this.label = new Name();
        }
        this.label.setValue(label);
    }

    /**
     * Type hint for the value
     *
     * @return the Name.
     */
    public Name getType() {
        return this.type;
    }

    /**
     * Type hint for the value
     *
     * @param type the Name.
     */
    public void setType(Name type) {
        this.type = type;
    }

    /**
     * Type hint for the value
     *
     * @param type the String.
     */
    public void setType(String type) {
        if ((this.type == null)) {
            if ((type == null)) {
                return;
            }
            this.type = new Name();
        }
        this.type.setValue(type);
    }

    /**
     * Pre-defined value or reference to context value (indicated by '$')
     *
     * @return the Name.
     */
    public Name getValue() {
        return this.value;
    }

    /**
     * Pre-defined value or reference to context value (indicated by '$')
     *
     * @param value the Name.
     */
    public void setValue(Name value) {
        this.value = value;
    }

    /**
     * Pre-defined value or reference to context value (indicated by '$')
     *
     * @param value the String.
     */
    public void setValue(String value) {
        if ((this.value == null)) {
            if ((value == null)) {
                return;
            }
            this.value = new Name();
        }
        this.value.setValue(value);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(DynamicElement.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(DynamicElement.class).getAllProperties();
    }
}
