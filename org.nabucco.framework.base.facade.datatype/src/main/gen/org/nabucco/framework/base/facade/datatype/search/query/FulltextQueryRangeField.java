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
 * FulltextQueryRangeField<p/>This datatype represents a min/max value field of a document for fulltext query.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-07-21
 */
public class FulltextQueryRangeField extends FulltextQueryField implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,4000;u0,n;m1,1;", "l0,4000;u0,n;m1,1;" };

    public static final String MINVALUE = "minValue";

    public static final String MAXVALUE = "maxValue";

    /** The min value of the field, format depends on property fieldType. */
    private FieldValue minValue;

    /** The max value of the field, format depends on property fieldType. */
    private FieldValue maxValue;

    /** Constructs a new FulltextQueryRangeField instance. */
    public FulltextQueryRangeField() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the FulltextQueryRangeField.
     */
    protected void cloneObject(FulltextQueryRangeField clone) {
        super.cloneObject(clone);
        if ((this.getMinValue() != null)) {
            clone.setMinValue(this.getMinValue().cloneObject());
        }
        if ((this.getMaxValue() != null)) {
            clone.setMaxValue(this.getMaxValue().cloneObject());
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
        propertyMap
                .put(MINVALUE, PropertyDescriptorSupport.createBasetype(MINVALUE, FieldValue.class, 5,
                        PROPERTY_CONSTRAINTS[0], false));
        propertyMap
                .put(MAXVALUE, PropertyDescriptorSupport.createBasetype(MAXVALUE, FieldValue.class, 6,
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
        properties.add(super.createProperty(FulltextQueryRangeField.getPropertyDescriptor(MINVALUE), this.minValue,
                null));
        properties.add(super.createProperty(FulltextQueryRangeField.getPropertyDescriptor(MAXVALUE), this.maxValue,
                null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(MINVALUE) && (property.getType() == FieldValue.class))) {
            this.setMinValue(((FieldValue) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MAXVALUE) && (property.getType() == FieldValue.class))) {
            this.setMaxValue(((FieldValue) property.getInstance()));
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
        final FulltextQueryRangeField other = ((FulltextQueryRangeField) obj);
        if ((this.minValue == null)) {
            if ((other.minValue != null))
                return false;
        } else if ((!this.minValue.equals(other.minValue)))
            return false;
        if ((this.maxValue == null)) {
            if ((other.maxValue != null))
                return false;
        } else if ((!this.maxValue.equals(other.maxValue)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.minValue == null) ? 0 : this.minValue.hashCode()));
        result = ((PRIME * result) + ((this.maxValue == null) ? 0 : this.maxValue.hashCode()));
        return result;
    }

    @Override
    public FulltextQueryRangeField cloneObject() {
        FulltextQueryRangeField clone = new FulltextQueryRangeField();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The min value of the field, format depends on property fieldType.
     *
     * @return the FieldValue.
     */
    public FieldValue getMinValue() {
        return this.minValue;
    }

    /**
     * The min value of the field, format depends on property fieldType.
     *
     * @param minValue the FieldValue.
     */
    public void setMinValue(FieldValue minValue) {
        this.minValue = minValue;
    }

    /**
     * The min value of the field, format depends on property fieldType.
     *
     * @param minValue the String.
     */
    public void setMinValue(String minValue) {
        if ((this.minValue == null)) {
            if ((minValue == null)) {
                return;
            }
            this.minValue = new FieldValue();
        }
        this.minValue.setValue(minValue);
    }

    /**
     * The max value of the field, format depends on property fieldType.
     *
     * @return the FieldValue.
     */
    public FieldValue getMaxValue() {
        return this.maxValue;
    }

    /**
     * The max value of the field, format depends on property fieldType.
     *
     * @param maxValue the FieldValue.
     */
    public void setMaxValue(FieldValue maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * The max value of the field, format depends on property fieldType.
     *
     * @param maxValue the String.
     */
    public void setMaxValue(String maxValue) {
        if ((this.maxValue == null)) {
            if ((maxValue == null)) {
                return;
            }
            this.maxValue = new FieldValue();
        }
        this.maxValue.setValue(maxValue);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(FulltextQueryRangeField.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(FulltextQueryRangeField.class).getAllProperties();
    }
}
