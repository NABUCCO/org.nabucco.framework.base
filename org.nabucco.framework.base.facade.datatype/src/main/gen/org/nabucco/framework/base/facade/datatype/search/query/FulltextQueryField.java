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
import org.nabucco.framework.base.facade.datatype.BasetypeType;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * FulltextQueryField<p/>This datatype represents one field of a document for fulltext indexing.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-21
 */
public abstract class FulltextQueryField extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m1,1;", "m1,1;" };

    public static final String FIELDNAME = "fieldName";

    public static final String FIELDTYPE = "fieldType";

    /** The name of the field. */
    private Name fieldName;

    /** The type (NType) of the field. */
    private BasetypeType fieldType;

    /** Constructs a new FulltextQueryField instance. */
    public FulltextQueryField() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the FulltextQueryField.
     */
    protected void cloneObject(FulltextQueryField clone) {
        super.cloneObject(clone);
        if ((this.getFieldName() != null)) {
            clone.setFieldName(this.getFieldName().cloneObject());
        }
        clone.setFieldType(this.getFieldType());
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoDatatype.class).getPropertyMap());
        propertyMap.put(FIELDNAME,
                PropertyDescriptorSupport.createBasetype(FIELDNAME, Name.class, 3, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(FIELDTYPE, PropertyDescriptorSupport.createEnumeration(FIELDTYPE, BasetypeType.class, 4,
                PROPERTY_CONSTRAINTS[1], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(FulltextQueryField.getPropertyDescriptor(FIELDNAME), this.fieldName, null));
        properties.add(super.createProperty(FulltextQueryField.getPropertyDescriptor(FIELDTYPE), this.getFieldType(),
                null));
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
        final FulltextQueryField other = ((FulltextQueryField) obj);
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
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.fieldName == null) ? 0 : this.fieldName.hashCode()));
        result = ((PRIME * result) + ((this.fieldType == null) ? 0 : this.fieldType.hashCode()));
        return result;
    }

    @Override
    public abstract FulltextQueryField cloneObject();

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
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(FulltextQueryField.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(FulltextQueryField.class).getAllProperties();
    }
}
