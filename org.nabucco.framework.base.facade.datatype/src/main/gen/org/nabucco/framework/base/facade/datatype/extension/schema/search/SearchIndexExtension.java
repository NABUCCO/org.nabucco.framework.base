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
package org.nabucco.framework.base.facade.datatype.extension.schema.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.search.SearchFieldExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * SearchIndexExtension<p/>Extension for configuring one search index.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-23
 */
public class SearchIndexExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m0,n;" };

    public static final String NAME = "name";

    public static final String KEY = "key";

    public static final String FIELDLIST = "fieldList";

    /** The name of the search index. */
    private StringProperty name;

    /** The key columns of the index. */
    private StringProperty key;

    /** The fields of the search index. */
    private NabuccoList<SearchFieldExtension> fieldList;

    /** Constructs a new SearchIndexExtension instance. */
    public SearchIndexExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the SearchIndexExtension.
     */
    protected void cloneObject(SearchIndexExtension clone) {
        super.cloneObject(clone);
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getKey() != null)) {
            clone.setKey(this.getKey().cloneObject());
        }
        if ((this.fieldList != null)) {
            clone.fieldList = this.fieldList.cloneCollection();
        }
    }

    /**
     * Getter for the FieldListJPA.
     *
     * @return the List<SearchFieldExtension>.
     */
    List<SearchFieldExtension> getFieldListJPA() {
        if ((this.fieldList == null)) {
            this.fieldList = new NabuccoListImpl<SearchFieldExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<SearchFieldExtension>) this.fieldList).getDelegate();
    }

    /**
     * Setter for the FieldListJPA.
     *
     * @param fieldList the List<SearchFieldExtension>.
     */
    void setFieldListJPA(List<SearchFieldExtension> fieldList) {
        if ((this.fieldList == null)) {
            this.fieldList = new NabuccoListImpl<SearchFieldExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<SearchFieldExtension>) this.fieldList).setDelegate(fieldList);
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
        propertyMap.put(KEY, PropertyDescriptorSupport.createDatatype(KEY, StringProperty.class, 3,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(FIELDLIST, PropertyDescriptorSupport.createCollection(FIELDLIST, SearchFieldExtension.class, 4,
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
        properties.add(super.createProperty(SearchIndexExtension.getPropertyDescriptor(NAME), this.getName(), null));
        properties.add(super.createProperty(SearchIndexExtension.getPropertyDescriptor(KEY), this.getKey(), null));
        properties
                .add(super.createProperty(SearchIndexExtension.getPropertyDescriptor(FIELDLIST), this.fieldList, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(NAME) && (property.getType() == StringProperty.class))) {
            this.setName(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(KEY) && (property.getType() == StringProperty.class))) {
            this.setKey(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(FIELDLIST) && (property.getType() == SearchFieldExtension.class))) {
            this.fieldList = ((NabuccoList<SearchFieldExtension>) property.getInstance());
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
        final SearchIndexExtension other = ((SearchIndexExtension) obj);
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
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
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.key == null) ? 0 : this.key.hashCode()));
        return result;
    }

    @Override
    public SearchIndexExtension cloneObject() {
        SearchIndexExtension clone = new SearchIndexExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The name of the search index.
     *
     * @param name the StringProperty.
     */
    public void setName(StringProperty name) {
        this.name = name;
    }

    /**
     * The name of the search index.
     *
     * @return the StringProperty.
     */
    public StringProperty getName() {
        return this.name;
    }

    /**
     * The key columns of the index.
     *
     * @param key the StringProperty.
     */
    public void setKey(StringProperty key) {
        this.key = key;
    }

    /**
     * The key columns of the index.
     *
     * @return the StringProperty.
     */
    public StringProperty getKey() {
        return this.key;
    }

    /**
     * The fields of the search index.
     *
     * @return the NabuccoList<SearchFieldExtension>.
     */
    public NabuccoList<SearchFieldExtension> getFieldList() {
        if ((this.fieldList == null)) {
            this.fieldList = new NabuccoListImpl<SearchFieldExtension>(NabuccoCollectionState.INITIALIZED);
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
        return PropertyCache.getInstance().retrieve(SearchIndexExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(SearchIndexExtension.class).getAllProperties();
    }
}
