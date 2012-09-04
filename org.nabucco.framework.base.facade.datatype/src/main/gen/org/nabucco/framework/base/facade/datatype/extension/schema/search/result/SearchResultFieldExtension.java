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
package org.nabucco.framework.base.facade.datatype.extension.schema.search.result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionElement;
import org.nabucco.framework.base.facade.datatype.extension.property.BooleanProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * SearchResultFieldExtension<p/>Extension for configuring a search result field.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-11-23
 */
public class SearchResultFieldExtension extends NabuccoExtensionElement implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m1,1;" };

    public static final String FIELDNAME = "fieldName";

    public static final String FIELDLABEL = "fieldLabel";

    public static final String FIELDDESCRIPTION = "fieldDescription";

    public static final String VISIBLE = "visible";

    public static final String SORTABLE = "sortable";

    /** The name of the search field. */
    private StringProperty fieldName;

    /** The label of the search field. */
    private StringProperty fieldLabel;

    /** The description of the search field. */
    private StringProperty fieldDescription;

    /** Whether the search field is visible or not. */
    private BooleanProperty visible;

    /** Whether the search field is sortable or not. */
    private BooleanProperty sortable;

    /** Constructs a new SearchResultFieldExtension instance. */
    public SearchResultFieldExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the SearchResultFieldExtension.
     */
    protected void cloneObject(SearchResultFieldExtension clone) {
        super.cloneObject(clone);
        if ((this.getFieldName() != null)) {
            clone.setFieldName(this.getFieldName().cloneObject());
        }
        if ((this.getFieldLabel() != null)) {
            clone.setFieldLabel(this.getFieldLabel().cloneObject());
        }
        if ((this.getFieldDescription() != null)) {
            clone.setFieldDescription(this.getFieldDescription().cloneObject());
        }
        if ((this.getVisible() != null)) {
            clone.setVisible(this.getVisible().cloneObject());
        }
        if ((this.getSortable() != null)) {
            clone.setSortable(this.getSortable().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoExtensionElement.class).getPropertyMap());
        propertyMap.put(FIELDNAME, PropertyDescriptorSupport.createDatatype(FIELDNAME, StringProperty.class, 1,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(FIELDLABEL, PropertyDescriptorSupport.createDatatype(FIELDLABEL, StringProperty.class, 2,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(FIELDDESCRIPTION, PropertyDescriptorSupport.createDatatype(FIELDDESCRIPTION,
                StringProperty.class, 3, PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(VISIBLE, PropertyDescriptorSupport.createDatatype(VISIBLE, BooleanProperty.class, 4,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(SORTABLE, PropertyDescriptorSupport.createDatatype(SORTABLE, BooleanProperty.class, 5,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(SearchResultFieldExtension.getPropertyDescriptor(FIELDNAME),
                this.getFieldName(), null));
        properties.add(super.createProperty(SearchResultFieldExtension.getPropertyDescriptor(FIELDLABEL),
                this.getFieldLabel(), null));
        properties.add(super.createProperty(SearchResultFieldExtension.getPropertyDescriptor(FIELDDESCRIPTION),
                this.getFieldDescription(), null));
        properties.add(super.createProperty(SearchResultFieldExtension.getPropertyDescriptor(VISIBLE),
                this.getVisible(), null));
        properties.add(super.createProperty(SearchResultFieldExtension.getPropertyDescriptor(SORTABLE),
                this.getSortable(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(FIELDNAME) && (property.getType() == StringProperty.class))) {
            this.setFieldName(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(FIELDLABEL) && (property.getType() == StringProperty.class))) {
            this.setFieldLabel(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(FIELDDESCRIPTION) && (property.getType() == StringProperty.class))) {
            this.setFieldDescription(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(VISIBLE) && (property.getType() == BooleanProperty.class))) {
            this.setVisible(((BooleanProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SORTABLE) && (property.getType() == BooleanProperty.class))) {
            this.setSortable(((BooleanProperty) property.getInstance()));
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
        final SearchResultFieldExtension other = ((SearchResultFieldExtension) obj);
        if ((this.fieldName == null)) {
            if ((other.fieldName != null))
                return false;
        } else if ((!this.fieldName.equals(other.fieldName)))
            return false;
        if ((this.fieldLabel == null)) {
            if ((other.fieldLabel != null))
                return false;
        } else if ((!this.fieldLabel.equals(other.fieldLabel)))
            return false;
        if ((this.fieldDescription == null)) {
            if ((other.fieldDescription != null))
                return false;
        } else if ((!this.fieldDescription.equals(other.fieldDescription)))
            return false;
        if ((this.visible == null)) {
            if ((other.visible != null))
                return false;
        } else if ((!this.visible.equals(other.visible)))
            return false;
        if ((this.sortable == null)) {
            if ((other.sortable != null))
                return false;
        } else if ((!this.sortable.equals(other.sortable)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.fieldName == null) ? 0 : this.fieldName.hashCode()));
        result = ((PRIME * result) + ((this.fieldLabel == null) ? 0 : this.fieldLabel.hashCode()));
        result = ((PRIME * result) + ((this.fieldDescription == null) ? 0 : this.fieldDescription.hashCode()));
        result = ((PRIME * result) + ((this.visible == null) ? 0 : this.visible.hashCode()));
        result = ((PRIME * result) + ((this.sortable == null) ? 0 : this.sortable.hashCode()));
        return result;
    }

    @Override
    public SearchResultFieldExtension cloneObject() {
        SearchResultFieldExtension clone = new SearchResultFieldExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The name of the search field.
     *
     * @param fieldName the StringProperty.
     */
    public void setFieldName(StringProperty fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * The name of the search field.
     *
     * @return the StringProperty.
     */
    public StringProperty getFieldName() {
        return this.fieldName;
    }

    /**
     * The label of the search field.
     *
     * @param fieldLabel the StringProperty.
     */
    public void setFieldLabel(StringProperty fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    /**
     * The label of the search field.
     *
     * @return the StringProperty.
     */
    public StringProperty getFieldLabel() {
        return this.fieldLabel;
    }

    /**
     * The description of the search field.
     *
     * @param fieldDescription the StringProperty.
     */
    public void setFieldDescription(StringProperty fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    /**
     * The description of the search field.
     *
     * @return the StringProperty.
     */
    public StringProperty getFieldDescription() {
        return this.fieldDescription;
    }

    /**
     * Whether the search field is visible or not.
     *
     * @param visible the BooleanProperty.
     */
    public void setVisible(BooleanProperty visible) {
        this.visible = visible;
    }

    /**
     * Whether the search field is visible or not.
     *
     * @return the BooleanProperty.
     */
    public BooleanProperty getVisible() {
        return this.visible;
    }

    /**
     * Whether the search field is sortable or not.
     *
     * @param sortable the BooleanProperty.
     */
    public void setSortable(BooleanProperty sortable) {
        this.sortable = sortable;
    }

    /**
     * Whether the search field is sortable or not.
     *
     * @return the BooleanProperty.
     */
    public BooleanProperty getSortable() {
        return this.sortable;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(SearchResultFieldExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(SearchResultFieldExtension.class).getAllProperties();
    }
}
