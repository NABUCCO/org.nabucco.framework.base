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
package org.nabucco.framework.base.facade.datatype.extension.schema.setup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * SystemVariableExtension<p/>Schema definition configuration for System Variables.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-04-08
 */
public class SystemVariableExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;" };

    public static final String NAME = "name";

    public static final String TYPE = "type";

    public static final String DEFAULTVALUE = "defaultValue";

    /** The system variable name */
    private StringProperty name;

    /** The system variable type */
    private StringProperty type;

    /** The system variable default value */
    private StringProperty defaultValue;

    /** Constructs a new SystemVariableExtension instance. */
    public SystemVariableExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the SystemVariableExtension.
     */
    protected void cloneObject(SystemVariableExtension clone) {
        super.cloneObject(clone);
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getType() != null)) {
            clone.setType(this.getType().cloneObject());
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
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoExtensionComposite.class).getPropertyMap());
        propertyMap.put(NAME, PropertyDescriptorSupport.createDatatype(NAME, StringProperty.class, 2,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(TYPE, PropertyDescriptorSupport.createDatatype(TYPE, StringProperty.class, 3,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(DEFAULTVALUE, PropertyDescriptorSupport.createDatatype(DEFAULTVALUE, StringProperty.class, 4,
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
        properties.add(super.createProperty(SystemVariableExtension.getPropertyDescriptor(NAME), this.getName(), null));
        properties.add(super.createProperty(SystemVariableExtension.getPropertyDescriptor(TYPE), this.getType(), null));
        properties.add(super.createProperty(SystemVariableExtension.getPropertyDescriptor(DEFAULTVALUE),
                this.getDefaultValue(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(NAME) && (property.getType() == StringProperty.class))) {
            this.setName(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TYPE) && (property.getType() == StringProperty.class))) {
            this.setType(((StringProperty) property.getInstance()));
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
        final SystemVariableExtension other = ((SystemVariableExtension) obj);
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        if ((this.type == null)) {
            if ((other.type != null))
                return false;
        } else if ((!this.type.equals(other.type)))
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
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.type == null) ? 0 : this.type.hashCode()));
        result = ((PRIME * result) + ((this.defaultValue == null) ? 0 : this.defaultValue.hashCode()));
        return result;
    }

    @Override
    public SystemVariableExtension cloneObject() {
        SystemVariableExtension clone = new SystemVariableExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The system variable name
     *
     * @param name the StringProperty.
     */
    public void setName(StringProperty name) {
        this.name = name;
    }

    /**
     * The system variable name
     *
     * @return the StringProperty.
     */
    public StringProperty getName() {
        return this.name;
    }

    /**
     * The system variable type
     *
     * @param type the StringProperty.
     */
    public void setType(StringProperty type) {
        this.type = type;
    }

    /**
     * The system variable type
     *
     * @return the StringProperty.
     */
    public StringProperty getType() {
        return this.type;
    }

    /**
     * The system variable default value
     *
     * @param defaultValue the StringProperty.
     */
    public void setDefaultValue(StringProperty defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * The system variable default value
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
        return PropertyCache.getInstance().retrieve(SystemVariableExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(SystemVariableExtension.class).getAllProperties();
    }
}
