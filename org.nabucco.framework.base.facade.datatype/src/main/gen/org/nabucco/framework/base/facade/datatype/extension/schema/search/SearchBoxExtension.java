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
import org.nabucco.framework.base.facade.datatype.extension.property.IntegerProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.search.SearchPrefixExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * SearchBoxExtension<p/>Extension for configuring the NABUCCO search box.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-03-09
 */
public class SearchBoxExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,n;" };

    public static final String DEFAULTINDEX = "defaultIndex";

    public static final String MAXRESULTS = "maxResults";

    public static final String PREFIXLIST = "prefixList";

    /** The index which has to be used when no prefix is used in the searchbox. */
    private StringProperty defaultIndex;

    /** The maximum number of results for the Searchbox. */
    private IntegerProperty maxResults;

    /** The list of prefix definitions and their associations to search indexes. */
    private NabuccoList<SearchPrefixExtension> prefixList;

    /** Constructs a new SearchBoxExtension instance. */
    public SearchBoxExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the SearchBoxExtension.
     */
    protected void cloneObject(SearchBoxExtension clone) {
        super.cloneObject(clone);
        if ((this.getDefaultIndex() != null)) {
            clone.setDefaultIndex(this.getDefaultIndex().cloneObject());
        }
        if ((this.getMaxResults() != null)) {
            clone.setMaxResults(this.getMaxResults().cloneObject());
        }
        if ((this.prefixList != null)) {
            clone.prefixList = this.prefixList.cloneCollection();
        }
    }

    /**
     * Getter for the PrefixListJPA.
     *
     * @return the List<SearchPrefixExtension>.
     */
    List<SearchPrefixExtension> getPrefixListJPA() {
        if ((this.prefixList == null)) {
            this.prefixList = new NabuccoListImpl<SearchPrefixExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<SearchPrefixExtension>) this.prefixList).getDelegate();
    }

    /**
     * Setter for the PrefixListJPA.
     *
     * @param prefixList the List<SearchPrefixExtension>.
     */
    void setPrefixListJPA(List<SearchPrefixExtension> prefixList) {
        if ((this.prefixList == null)) {
            this.prefixList = new NabuccoListImpl<SearchPrefixExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<SearchPrefixExtension>) this.prefixList).setDelegate(prefixList);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoExtensionComposite.class).getPropertyMap());
        propertyMap.put(DEFAULTINDEX, PropertyDescriptorSupport.createDatatype(DEFAULTINDEX, StringProperty.class, 2,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(MAXRESULTS, PropertyDescriptorSupport.createDatatype(MAXRESULTS, IntegerProperty.class, 3,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(PREFIXLIST, PropertyDescriptorSupport.createCollection(PREFIXLIST, SearchPrefixExtension.class,
                4, PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(SearchBoxExtension.getPropertyDescriptor(DEFAULTINDEX),
                this.getDefaultIndex(), null));
        properties.add(super.createProperty(SearchBoxExtension.getPropertyDescriptor(MAXRESULTS), this.getMaxResults(),
                null));
        properties
                .add(super.createProperty(SearchBoxExtension.getPropertyDescriptor(PREFIXLIST), this.prefixList, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(DEFAULTINDEX) && (property.getType() == StringProperty.class))) {
            this.setDefaultIndex(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MAXRESULTS) && (property.getType() == IntegerProperty.class))) {
            this.setMaxResults(((IntegerProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PREFIXLIST) && (property.getType() == SearchPrefixExtension.class))) {
            this.prefixList = ((NabuccoList<SearchPrefixExtension>) property.getInstance());
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
        final SearchBoxExtension other = ((SearchBoxExtension) obj);
        if ((this.defaultIndex == null)) {
            if ((other.defaultIndex != null))
                return false;
        } else if ((!this.defaultIndex.equals(other.defaultIndex)))
            return false;
        if ((this.maxResults == null)) {
            if ((other.maxResults != null))
                return false;
        } else if ((!this.maxResults.equals(other.maxResults)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.defaultIndex == null) ? 0 : this.defaultIndex.hashCode()));
        result = ((PRIME * result) + ((this.maxResults == null) ? 0 : this.maxResults.hashCode()));
        return result;
    }

    @Override
    public SearchBoxExtension cloneObject() {
        SearchBoxExtension clone = new SearchBoxExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The index which has to be used when no prefix is used in the searchbox.
     *
     * @param defaultIndex the StringProperty.
     */
    public void setDefaultIndex(StringProperty defaultIndex) {
        this.defaultIndex = defaultIndex;
    }

    /**
     * The index which has to be used when no prefix is used in the searchbox.
     *
     * @return the StringProperty.
     */
    public StringProperty getDefaultIndex() {
        return this.defaultIndex;
    }

    /**
     * The maximum number of results for the Searchbox.
     *
     * @param maxResults the IntegerProperty.
     */
    public void setMaxResults(IntegerProperty maxResults) {
        this.maxResults = maxResults;
    }

    /**
     * The maximum number of results for the Searchbox.
     *
     * @return the IntegerProperty.
     */
    public IntegerProperty getMaxResults() {
        return this.maxResults;
    }

    /**
     * The list of prefix definitions and their associations to search indexes.
     *
     * @return the NabuccoList<SearchPrefixExtension>.
     */
    public NabuccoList<SearchPrefixExtension> getPrefixList() {
        if ((this.prefixList == null)) {
            this.prefixList = new NabuccoListImpl<SearchPrefixExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.prefixList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(SearchBoxExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(SearchBoxExtension.class).getAllProperties();
    }
}
