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
package org.nabucco.framework.base.facade.datatype.search.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.search.fulltext.FieldValue;
import org.nabucco.framework.base.facade.datatype.search.query.FulltextQueryField;

/**
 * FulltextQueryValueField<p/>This datatype represents a single value field of a document for fulltext query.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-07-21
 */
public class FulltextQueryValueField extends FulltextQueryField implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,4000;u0,n;m1,1;" };

    public static final String FIELDVALUE = "fieldValue";

    /** The value of the field, format depends on property fieldType. */
    private FieldValue fieldValue;

    /** Constructs a new FulltextQueryValueField instance. */
    public FulltextQueryValueField() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the FulltextQueryValueField.
     */
    protected void cloneObject(FulltextQueryValueField clone) {
        super.cloneObject(clone);
        if ((this.getFieldValue() != null)) {
            clone.setFieldValue(this.getFieldValue().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(FulltextQueryField.class).getPropertyMap());
        propertyMap.put(FIELDVALUE, PropertyDescriptorSupport.createBasetype(FIELDVALUE, FieldValue.class, 5,
                PROPERTY_CONSTRAINTS[0], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(FulltextQueryValueField.getPropertyDescriptor(FIELDVALUE), this.fieldValue,
                null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(FIELDVALUE) && (property.getType() == FieldValue.class))) {
            this.setFieldValue(((FieldValue) property.getInstance()));
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
        final FulltextQueryValueField other = ((FulltextQueryValueField) obj);
        if ((this.fieldValue == null)) {
            if ((other.fieldValue != null))
                return false;
        } else if ((!this.fieldValue.equals(other.fieldValue)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.fieldValue == null) ? 0 : this.fieldValue.hashCode()));
        return result;
    }

    @Override
    public FulltextQueryValueField cloneObject() {
        FulltextQueryValueField clone = new FulltextQueryValueField();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The value of the field, format depends on property fieldType.
     *
     * @return the FieldValue.
     */
    public FieldValue getFieldValue() {
        return this.fieldValue;
    }

    /**
     * The value of the field, format depends on property fieldType.
     *
     * @param fieldValue the FieldValue.
     */
    public void setFieldValue(FieldValue fieldValue) {
        this.fieldValue = fieldValue;
    }

    /**
     * The value of the field, format depends on property fieldType.
     *
     * @param fieldValue the String.
     */
    public void setFieldValue(String fieldValue) {
        if ((this.fieldValue == null)) {
            if ((fieldValue == null)) {
                return;
            }
            this.fieldValue = new FieldValue();
        }
        this.fieldValue.setValue(fieldValue);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(FulltextQueryValueField.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(FulltextQueryValueField.class).getAllProperties();
    }
}
