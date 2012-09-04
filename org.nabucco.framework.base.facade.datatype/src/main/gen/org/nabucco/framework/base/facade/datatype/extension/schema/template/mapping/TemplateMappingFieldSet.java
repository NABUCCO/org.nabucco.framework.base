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
package org.nabucco.framework.base.facade.datatype.extension.schema.template.mapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.mapping.TemplateMappingField;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * TemplateMappingFieldSet<p/>Set of Template Fields to replace in a template.<p/>
 *
 * @version 1.2
 * @author Nicolas Moser, PRODYNA AG, 2011-12-10
 */
public class TemplateMappingFieldSet extends TemplateMappingField implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,1;", "m0,n;" };

    public static final String FRAGMENTID = "fragmentId";

    public static final String FIELDLIST = "fieldList";

    /** Id of the referenced fragment. */
    private StringProperty fragmentId;

    /** Children of this field set. */
    private NabuccoList<TemplateMappingField> fieldList;

    /** Constructs a new TemplateMappingFieldSet instance. */
    public TemplateMappingFieldSet() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TemplateMappingFieldSet.
     */
    protected void cloneObject(TemplateMappingFieldSet clone) {
        super.cloneObject(clone);
        if ((this.getFragmentId() != null)) {
            clone.setFragmentId(this.getFragmentId().cloneObject());
        }
        if ((this.fieldList != null)) {
            clone.fieldList = this.fieldList.cloneCollection();
        }
    }

    /**
     * Getter for the FieldListJPA.
     *
     * @return the List<TemplateMappingField>.
     */
    List<TemplateMappingField> getFieldListJPA() {
        if ((this.fieldList == null)) {
            this.fieldList = new NabuccoListImpl<TemplateMappingField>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<TemplateMappingField>) this.fieldList).getDelegate();
    }

    /**
     * Setter for the FieldListJPA.
     *
     * @param fieldList the List<TemplateMappingField>.
     */
    void setFieldListJPA(List<TemplateMappingField> fieldList) {
        if ((this.fieldList == null)) {
            this.fieldList = new NabuccoListImpl<TemplateMappingField>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<TemplateMappingField>) this.fieldList).setDelegate(fieldList);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(TemplateMappingField.class).getPropertyMap());
        propertyMap.put(FRAGMENTID, PropertyDescriptorSupport.createDatatype(FRAGMENTID, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(FIELDLIST, PropertyDescriptorSupport.createCollection(FIELDLIST, TemplateMappingField.class, 5,
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
        properties.add(super.createProperty(TemplateMappingFieldSet.getPropertyDescriptor(FRAGMENTID),
                this.getFragmentId(), null));
        properties.add(super.createProperty(TemplateMappingFieldSet.getPropertyDescriptor(FIELDLIST), this.fieldList,
                null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(FRAGMENTID) && (property.getType() == StringProperty.class))) {
            this.setFragmentId(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(FIELDLIST) && (property.getType() == TemplateMappingField.class))) {
            this.fieldList = ((NabuccoList<TemplateMappingField>) property.getInstance());
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
        final TemplateMappingFieldSet other = ((TemplateMappingFieldSet) obj);
        if ((this.fragmentId == null)) {
            if ((other.fragmentId != null))
                return false;
        } else if ((!this.fragmentId.equals(other.fragmentId)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.fragmentId == null) ? 0 : this.fragmentId.hashCode()));
        return result;
    }

    @Override
    public TemplateMappingFieldSet cloneObject() {
        TemplateMappingFieldSet clone = new TemplateMappingFieldSet();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Id of the referenced fragment.
     *
     * @param fragmentId the StringProperty.
     */
    public void setFragmentId(StringProperty fragmentId) {
        this.fragmentId = fragmentId;
    }

    /**
     * Id of the referenced fragment.
     *
     * @return the StringProperty.
     */
    public StringProperty getFragmentId() {
        return this.fragmentId;
    }

    /**
     * Children of this field set.
     *
     * @return the NabuccoList<TemplateMappingField>.
     */
    public NabuccoList<TemplateMappingField> getFieldList() {
        if ((this.fieldList == null)) {
            this.fieldList = new NabuccoListImpl<TemplateMappingField>(NabuccoCollectionState.INITIALIZED);
        }
        return this.fieldList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TemplateMappingFieldSet.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TemplateMappingFieldSet.class).getAllProperties();
    }
}
