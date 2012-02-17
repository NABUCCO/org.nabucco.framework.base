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
package org.nabucco.framework.base.facade.datatype.search.fulltext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.BasetypeType;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeSupport;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * FulltextField<p/>This datatype represents one field of a document for fulltext indexing.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-21
 */
public class FulltextField extends DatatypeSupport implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m1,1;", "m1,1;", "l0,4000;u0,n;m1,1;" };

    public static final String FIELDNAME = "fieldName";

    public static final String FIELDTYPE = "fieldType";

    public static final String FIELDVALUE = "fieldValue";

    /** The name of the field. */
    private Name fieldName;

    /** The type (NType) of the field. */
    private BasetypeType fieldType;

    /** The value of the field, format depends on property fieldType. */
    private FieldValue fieldValue;

    /** Constructs a new FulltextField instance. */
    public FulltextField() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the FulltextField.
     */
    protected void cloneObject(FulltextField clone) {
        super.cloneObject(clone);
        if ((this.getFieldName() != null)) {
            clone.setFieldName(this.getFieldName().cloneObject());
        }
        clone.setFieldType(this.getFieldType());
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
        propertyMap.put(FIELDNAME,
                PropertyDescriptorSupport.createBasetype(FIELDNAME, Name.class, 0, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(FIELDTYPE, PropertyDescriptorSupport.createEnumeration(FIELDTYPE, BasetypeType.class, 1,
                PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(FIELDVALUE, PropertyDescriptorSupport.createBasetype(FIELDVALUE, FieldValue.class, 2,
                PROPERTY_CONSTRAINTS[2], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(FulltextField.getPropertyDescriptor(FIELDNAME), this.fieldName, null));
        properties.add(super.createProperty(FulltextField.getPropertyDescriptor(FIELDTYPE), this.getFieldType(), null));
        properties.add(super.createProperty(FulltextField.getPropertyDescriptor(FIELDVALUE), this.fieldValue, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(FIELDNAME) && (property.getType() == Name.class))) {
            this.setFieldName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(FIELDTYPE) && (property.getType() == BasetypeType.class))) {
            this.setFieldType(((BasetypeType) property.getInstance()));
            return true;
        } else if ((property.getName().equals(FIELDVALUE) && (property.getType() == FieldValue.class))) {
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
        final FulltextField other = ((FulltextField) obj);
        if ((this.fieldName == null)) {
            if ((other.fieldName != null))
                return false;
        } else if ((!this.fieldName.equals(other.fieldName)))
            return false;
        if ((this.fieldType == null)) {
            if ((other.fieldType != null))
                return false;
        } else if ((!this.fieldType.equals(other.fieldType)))
            return false;
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
        result = ((PRIME * result) + ((this.fieldName == null) ? 0 : this.fieldName.hashCode()));
        result = ((PRIME * result) + ((this.fieldType == null) ? 0 : this.fieldType.hashCode()));
        result = ((PRIME * result) + ((this.fieldValue == null) ? 0 : this.fieldValue.hashCode()));
        return result;
    }

    @Override
    public FulltextField cloneObject() {
        FulltextField clone = new FulltextField();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The name of the field.
     *
     * @return the Name.
     */
    public Name getFieldName() {
        return this.fieldName;
    }

    /**
     * The name of the field.
     *
     * @param fieldName the Name.
     */
    public void setFieldName(Name fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * The name of the field.
     *
     * @param fieldName the String.
     */
    public void setFieldName(String fieldName) {
        if ((this.fieldName == null)) {
            if ((fieldName == null)) {
                return;
            }
            this.fieldName = new Name();
        }
        this.fieldName.setValue(fieldName);
    }

    /**
     * The type (NType) of the field.
     *
     * @return the BasetypeType.
     */
    public BasetypeType getFieldType() {
        return this.fieldType;
    }

    /**
     * The type (NType) of the field.
     *
     * @param fieldType the BasetypeType.
     */
    public void setFieldType(BasetypeType fieldType) {
        this.fieldType = fieldType;
    }

    /**
     * The type (NType) of the field.
     *
     * @param fieldType the String.
     */
    public void setFieldType(String fieldType) {
        if ((fieldType == null)) {
            this.fieldType = null;
        } else {
            this.fieldType = BasetypeType.valueOf(fieldType);
        }
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
        return PropertyCache.getInstance().retrieve(FulltextField.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(FulltextField.class).getAllProperties();
    }
}
