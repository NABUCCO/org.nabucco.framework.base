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
package org.nabucco.framework.base.facade.datatype.extension.schema.queryfilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.property.ClassProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * QueryFilterParameterExtension<p/>Extension for a single filter parameter configuration.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-10-24
 */
public class QueryFilterParameterExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m0,1;", "m1,1;", "m1,1;", "m0,1;" };

    public static final String NAME = "name";

    public static final String DESCRIPTION = "description";

    public static final String TYPE = "type";

    public static final String VALUE = "value";

    public static final String SYSTEMVALUE = "systemValue";

    /** The name of the parameter. */
    private StringProperty name;

    /** An optional description of the parameter. */
    private StringProperty description;

    /** The value of the parameter. */
    private ClassProperty type;

    /** The value of the parameter. */
    private StringProperty value;

    /** Name of the system variable holding the parameter value. */
    private EnumerationProperty systemValue;

    /** Constructs a new QueryFilterParameterExtension instance. */
    public QueryFilterParameterExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the QueryFilterParameterExtension.
     */
    protected void cloneObject(QueryFilterParameterExtension clone) {
        super.cloneObject(clone);
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        if ((this.getType() != null)) {
            clone.setType(this.getType().cloneObject());
        }
        if ((this.getValue() != null)) {
            clone.setValue(this.getValue().cloneObject());
        }
        if ((this.getSystemValue() != null)) {
            clone.setSystemValue(this.getSystemValue().cloneObject());
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
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createDatatype(DESCRIPTION, StringProperty.class, 3,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(TYPE, PropertyDescriptorSupport.createDatatype(TYPE, ClassProperty.class, 4,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(VALUE, PropertyDescriptorSupport.createDatatype(VALUE, StringProperty.class, 5,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(SYSTEMVALUE, PropertyDescriptorSupport.createDatatype(SYSTEMVALUE, EnumerationProperty.class,
                6, PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(QueryFilterParameterExtension.getPropertyDescriptor(NAME), this.getName(),
                null));
        properties.add(super.createProperty(QueryFilterParameterExtension.getPropertyDescriptor(DESCRIPTION),
                this.getDescription(), null));
        properties.add(super.createProperty(QueryFilterParameterExtension.getPropertyDescriptor(TYPE), this.getType(),
                null));
        properties.add(super.createProperty(QueryFilterParameterExtension.getPropertyDescriptor(VALUE),
                this.getValue(), null));
        properties.add(super.createProperty(QueryFilterParameterExtension.getPropertyDescriptor(SYSTEMVALUE),
                this.getSystemValue(), null));
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
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == StringProperty.class))) {
            this.setDescription(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TYPE) && (property.getType() == ClassProperty.class))) {
            this.setType(((ClassProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(VALUE) && (property.getType() == StringProperty.class))) {
            this.setValue(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SYSTEMVALUE) && (property.getType() == EnumerationProperty.class))) {
            this.setSystemValue(((EnumerationProperty) property.getInstance()));
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
        final QueryFilterParameterExtension other = ((QueryFilterParameterExtension) obj);
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        if ((this.description == null)) {
            if ((other.description != null))
                return false;
        } else if ((!this.description.equals(other.description)))
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
        if ((this.systemValue == null)) {
            if ((other.systemValue != null))
                return false;
        } else if ((!this.systemValue.equals(other.systemValue)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.type == null) ? 0 : this.type.hashCode()));
        result = ((PRIME * result) + ((this.value == null) ? 0 : this.value.hashCode()));
        result = ((PRIME * result) + ((this.systemValue == null) ? 0 : this.systemValue.hashCode()));
        return result;
    }

    @Override
    public QueryFilterParameterExtension cloneObject() {
        QueryFilterParameterExtension clone = new QueryFilterParameterExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The name of the parameter.
     *
     * @param name the StringProperty.
     */
    public void setName(StringProperty name) {
        this.name = name;
    }

    /**
     * The name of the parameter.
     *
     * @return the StringProperty.
     */
    public StringProperty getName() {
        return this.name;
    }

    /**
     * An optional description of the parameter.
     *
     * @param description the StringProperty.
     */
    public void setDescription(StringProperty description) {
        this.description = description;
    }

    /**
     * An optional description of the parameter.
     *
     * @return the StringProperty.
     */
    public StringProperty getDescription() {
        return this.description;
    }

    /**
     * The value of the parameter.
     *
     * @param type the ClassProperty.
     */
    public void setType(ClassProperty type) {
        this.type = type;
    }

    /**
     * The value of the parameter.
     *
     * @return the ClassProperty.
     */
    public ClassProperty getType() {
        return this.type;
    }

    /**
     * The value of the parameter.
     *
     * @param value the StringProperty.
     */
    public void setValue(StringProperty value) {
        this.value = value;
    }

    /**
     * The value of the parameter.
     *
     * @return the StringProperty.
     */
    public StringProperty getValue() {
        return this.value;
    }

    /**
     * Name of the system variable holding the parameter value.
     *
     * @param systemValue the EnumerationProperty.
     */
    public void setSystemValue(EnumerationProperty systemValue) {
        this.systemValue = systemValue;
    }

    /**
     * Name of the system variable holding the parameter value.
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getSystemValue() {
        return this.systemValue;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(QueryFilterParameterExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(QueryFilterParameterExtension.class).getAllProperties();
    }
}
