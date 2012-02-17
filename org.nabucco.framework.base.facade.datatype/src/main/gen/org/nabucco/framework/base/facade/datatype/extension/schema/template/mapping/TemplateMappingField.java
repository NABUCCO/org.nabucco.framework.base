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
package org.nabucco.framework.base.facade.datatype.extension.schema.template.mapping;

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
 * TemplateMappingField<p/>Template Field to replace in a template.<p/>
 *
 * @version 1.2
 * @author Nicolas Moser, PRODYNA AG, 2011-12-10
 */
public class TemplateMappingField extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m0,1;" };

    public static final String KEY = "key";

    public static final String DEFAULTVALUE = "defaultValue";

    /** Key of the Template Field. */
    private StringProperty key;

    /** Default field value to include. */
    private StringProperty defaultValue;

    /** Constructs a new TemplateMappingField instance. */
    public TemplateMappingField() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TemplateMappingField.
     */
    protected void cloneObject(TemplateMappingField clone) {
        super.cloneObject(clone);
        if ((this.getKey() != null)) {
            clone.setKey(this.getKey().cloneObject());
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
        propertyMap.put(KEY, PropertyDescriptorSupport.createDatatype(KEY, StringProperty.class, 2,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(DEFAULTVALUE, PropertyDescriptorSupport.createDatatype(DEFAULTVALUE, StringProperty.class, 3,
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
        properties.add(super.createProperty(TemplateMappingField.getPropertyDescriptor(KEY), this.getKey(), null));
        properties.add(super.createProperty(TemplateMappingField.getPropertyDescriptor(DEFAULTVALUE),
                this.getDefaultValue(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(KEY) && (property.getType() == StringProperty.class))) {
            this.setKey(((StringProperty) property.getInstance()));
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
        final TemplateMappingField other = ((TemplateMappingField) obj);
        if ((this.key == null)) {
            if ((other.key != null))
                return false;
        } else if ((!this.key.equals(other.key)))
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
        result = ((PRIME * result) + ((this.key == null) ? 0 : this.key.hashCode()));
        result = ((PRIME * result) + ((this.defaultValue == null) ? 0 : this.defaultValue.hashCode()));
        return result;
    }

    @Override
    public TemplateMappingField cloneObject() {
        TemplateMappingField clone = new TemplateMappingField();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Key of the Template Field.
     *
     * @param key the StringProperty.
     */
    public void setKey(StringProperty key) {
        this.key = key;
    }

    /**
     * Key of the Template Field.
     *
     * @return the StringProperty.
     */
    public StringProperty getKey() {
        return this.key;
    }

    /**
     * Default field value to include.
     *
     * @param defaultValue the StringProperty.
     */
    public void setDefaultValue(StringProperty defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * Default field value to include.
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
        return PropertyCache.getInstance().retrieve(TemplateMappingField.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TemplateMappingField.class).getAllProperties();
    }
}
