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
package org.nabucco.framework.base.facade.datatype.extension.schema.search.result;

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
import org.nabucco.framework.base.facade.datatype.extension.schema.search.result.SearchResultFieldExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * SearchResultExtension<p/>Extension for configuring a search result.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-11-23
 */
public class SearchResultExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m0,n;" };

    public static final String INDEXNAME = "indexName";

    public static final String FIELDLIST = "fieldList";

    /** The name of the search index. */
    private StringProperty indexName;

    /** The list of search result fields. */
    private NabuccoList<SearchResultFieldExtension> fieldList;

    /** Constructs a new SearchResultExtension instance. */
    public SearchResultExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the SearchResultExtension.
     */
    protected void cloneObject(SearchResultExtension clone) {
        super.cloneObject(clone);
        if ((this.getIndexName() != null)) {
            clone.setIndexName(this.getIndexName().cloneObject());
        }
        if ((this.fieldList != null)) {
            clone.fieldList = this.fieldList.cloneCollection();
        }
    }

    /**
     * Getter for the FieldListJPA.
     *
     * @return the List<SearchResultFieldExtension>.
     */
    List<SearchResultFieldExtension> getFieldListJPA() {
        if ((this.fieldList == null)) {
            this.fieldList = new NabuccoListImpl<SearchResultFieldExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<SearchResultFieldExtension>) this.fieldList).getDelegate();
    }

    /**
     * Setter for the FieldListJPA.
     *
     * @param fieldList the List<SearchResultFieldExtension>.
     */
    void setFieldListJPA(List<SearchResultFieldExtension> fieldList) {
        if ((this.fieldList == null)) {
            this.fieldList = new NabuccoListImpl<SearchResultFieldExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<SearchResultFieldExtension>) this.fieldList).setDelegate(fieldList);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoExtensionComposite.class).getPropertyMap());
        propertyMap.put(INDEXNAME, PropertyDescriptorSupport.createDatatype(INDEXNAME, StringProperty.class, 2,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(FIELDLIST, PropertyDescriptorSupport.createCollection(FIELDLIST,
                SearchResultFieldExtension.class, 3, PROPERTY_CONSTRAINTS[1], false,
                PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(SearchResultExtension.getPropertyDescriptor(INDEXNAME),
                this.getIndexName(), null));
        properties.add(super.createProperty(SearchResultExtension.getPropertyDescriptor(FIELDLIST), this.fieldList,
                null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(INDEXNAME) && (property.getType() == StringProperty.class))) {
            this.setIndexName(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(FIELDLIST) && (property.getType() == SearchResultFieldExtension.class))) {
            this.fieldList = ((NabuccoList<SearchResultFieldExtension>) property.getInstance());
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
        final SearchResultExtension other = ((SearchResultExtension) obj);
        if ((this.indexName == null)) {
            if ((other.indexName != null))
                return false;
        } else if ((!this.indexName.equals(other.indexName)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.indexName == null) ? 0 : this.indexName.hashCode()));
        return result;
    }

    @Override
    public SearchResultExtension cloneObject() {
        SearchResultExtension clone = new SearchResultExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The name of the search index.
     *
     * @param indexName the StringProperty.
     */
    public void setIndexName(StringProperty indexName) {
        this.indexName = indexName;
    }

    /**
     * The name of the search index.
     *
     * @return the StringProperty.
     */
    public StringProperty getIndexName() {
        return this.indexName;
    }

    /**
     * The list of search result fields.
     *
     * @return the NabuccoList<SearchResultFieldExtension>.
     */
    public NabuccoList<SearchResultFieldExtension> getFieldList() {
        if ((this.fieldList == null)) {
            this.fieldList = new NabuccoListImpl<SearchResultFieldExtension>(NabuccoCollectionState.INITIALIZED);
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
        return PropertyCache.getInstance().retrieve(SearchResultExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(SearchResultExtension.class).getAllProperties();
    }
}
