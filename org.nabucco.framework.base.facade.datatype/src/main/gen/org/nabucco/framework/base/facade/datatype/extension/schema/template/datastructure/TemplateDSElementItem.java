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
package org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.BooleanProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure.TemplateDSElement;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * TemplateDSElementItem<p/>Template Datastructure Element Item.<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-07-03
 */
public abstract class TemplateDSElementItem extends TemplateDSElement implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,1;", "m0,1;", "m0,1;", "m0,1;" };

    public static final String DEFAULTVALUE = "defaultValue";

    public static final String DESCRIPTION = "description";

    public static final String MANDATORY = "mandatory";

    public static final String KEY = "key";

    /** The default value of the item */
    private StringProperty defaultValue;

    /** The description of the data node */
    private StringProperty description;

    /** Indicates that the node is mandatory */
    private BooleanProperty mandatory;

    /** Element can be referenced by the key */
    private StringProperty key;

    /** Constructs a new TemplateDSElementItem instance. */
    public TemplateDSElementItem() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TemplateDSElementItem.
     */
    protected void cloneObject(TemplateDSElementItem clone) {
        super.cloneObject(clone);
        if ((this.getDefaultValue() != null)) {
            clone.setDefaultValue(this.getDefaultValue().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        if ((this.getMandatory() != null)) {
            clone.setMandatory(this.getMandatory().cloneObject());
        }
        if ((this.getKey() != null)) {
            clone.setKey(this.getKey().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(TemplateDSElement.class).getPropertyMap());
        propertyMap.put(DEFAULTVALUE, PropertyDescriptorSupport.createDatatype(DEFAULTVALUE, StringProperty.class, 3,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createDatatype(DESCRIPTION, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(MANDATORY, PropertyDescriptorSupport.createDatatype(MANDATORY, BooleanProperty.class, 5,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(KEY, PropertyDescriptorSupport.createDatatype(KEY, StringProperty.class, 6,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(TemplateDSElementItem.getPropertyDescriptor(DEFAULTVALUE),
                this.getDefaultValue(), null));
        properties.add(super.createProperty(TemplateDSElementItem.getPropertyDescriptor(DESCRIPTION),
                this.getDescription(), null));
        properties.add(super.createProperty(TemplateDSElementItem.getPropertyDescriptor(MANDATORY),
                this.getMandatory(), null));
        properties.add(super.createProperty(TemplateDSElementItem.getPropertyDescriptor(KEY), this.getKey(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(DEFAULTVALUE) && (property.getType() == StringProperty.class))) {
            this.setDefaultValue(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == StringProperty.class))) {
            this.setDescription(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MANDATORY) && (property.getType() == BooleanProperty.class))) {
            this.setMandatory(((BooleanProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(KEY) && (property.getType() == StringProperty.class))) {
            this.setKey(((StringProperty) property.getInstance()));
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
        final TemplateDSElementItem other = ((TemplateDSElementItem) obj);
        if ((this.defaultValue == null)) {
            if ((other.defaultValue != null))
                return false;
        } else if ((!this.defaultValue.equals(other.defaultValue)))
            return false;
        if ((this.description == null)) {
            if ((other.description != null))
                return false;
        } else if ((!this.description.equals(other.description)))
            return false;
        if ((this.mandatory == null)) {
            if ((other.mandatory != null))
                return false;
        } else if ((!this.mandatory.equals(other.mandatory)))
            return false;
        if ((this.key == null)) {
            if ((other.key != null))
                return false;
        } else if ((!this.key.equals(other.key)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.defaultValue == null) ? 0 : this.defaultValue.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.mandatory == null) ? 0 : this.mandatory.hashCode()));
        result = ((PRIME * result) + ((this.key == null) ? 0 : this.key.hashCode()));
        return result;
    }

    @Override
    public abstract TemplateDSElementItem cloneObject();

    /**
     * The default value of the item
     *
     * @param defaultValue the StringProperty.
     */
    public void setDefaultValue(StringProperty defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * The default value of the item
     *
     * @return the StringProperty.
     */
    public StringProperty getDefaultValue() {
        return this.defaultValue;
    }

    /**
     * The description of the data node
     *
     * @param description the StringProperty.
     */
    public void setDescription(StringProperty description) {
        this.description = description;
    }

    /**
     * The description of the data node
     *
     * @return the StringProperty.
     */
    public StringProperty getDescription() {
        return this.description;
    }

    /**
     * Indicates that the node is mandatory
     *
     * @param mandatory the BooleanProperty.
     */
    public void setMandatory(BooleanProperty mandatory) {
        this.mandatory = mandatory;
    }

    /**
     * Indicates that the node is mandatory
     *
     * @return the BooleanProperty.
     */
    public BooleanProperty getMandatory() {
        return this.mandatory;
    }

    /**
     * Element can be referenced by the key
     *
     * @param key the StringProperty.
     */
    public void setKey(StringProperty key) {
        this.key = key;
    }

    /**
     * Element can be referenced by the key
     *
     * @return the StringProperty.
     */
    public StringProperty getKey() {
        return this.key;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TemplateDSElementItem.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TemplateDSElementItem.class).getAllProperties();
    }
}
