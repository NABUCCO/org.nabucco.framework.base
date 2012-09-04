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
import org.nabucco.framework.base.facade.datatype.extension.schema.search.SearchQualifierExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * SearchIndexRefExtension<p/>Extension for configuring one search index.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-23
 */
public class SearchIndexRefExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m0,n;" };

    public static final String NAME = "name";

    public static final String QUALIFIERLIST = "qualifierList";

    /** The name of the search index. */
    private StringProperty name;

    /** The mapping from a search box entry to the index column */
    private NabuccoList<SearchQualifierExtension> qualifierList;

    /** Constructs a new SearchIndexRefExtension instance. */
    public SearchIndexRefExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the SearchIndexRefExtension.
     */
    protected void cloneObject(SearchIndexRefExtension clone) {
        super.cloneObject(clone);
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.qualifierList != null)) {
            clone.qualifierList = this.qualifierList.cloneCollection();
        }
    }

    /**
     * Getter for the QualifierListJPA.
     *
     * @return the List<SearchQualifierExtension>.
     */
    List<SearchQualifierExtension> getQualifierListJPA() {
        if ((this.qualifierList == null)) {
            this.qualifierList = new NabuccoListImpl<SearchQualifierExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<SearchQualifierExtension>) this.qualifierList).getDelegate();
    }

    /**
     * Setter for the QualifierListJPA.
     *
     * @param qualifierList the List<SearchQualifierExtension>.
     */
    void setQualifierListJPA(List<SearchQualifierExtension> qualifierList) {
        if ((this.qualifierList == null)) {
            this.qualifierList = new NabuccoListImpl<SearchQualifierExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<SearchQualifierExtension>) this.qualifierList).setDelegate(qualifierList);
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
        propertyMap
                .put(QUALIFIERLIST, PropertyDescriptorSupport.createCollection(QUALIFIERLIST,
                        SearchQualifierExtension.class, 3, PROPERTY_CONSTRAINTS[1], false,
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
        properties.add(super.createProperty(SearchIndexRefExtension.getPropertyDescriptor(NAME), this.getName(), null));
        properties.add(super.createProperty(SearchIndexRefExtension.getPropertyDescriptor(QUALIFIERLIST),
                this.qualifierList, null));
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
        } else if ((property.getName().equals(QUALIFIERLIST) && (property.getType() == SearchQualifierExtension.class))) {
            this.qualifierList = ((NabuccoList<SearchQualifierExtension>) property.getInstance());
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
        final SearchIndexRefExtension other = ((SearchIndexRefExtension) obj);
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        return result;
    }

    @Override
    public SearchIndexRefExtension cloneObject() {
        SearchIndexRefExtension clone = new SearchIndexRefExtension();
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
     * The mapping from a search box entry to the index column
     *
     * @return the NabuccoList<SearchQualifierExtension>.
     */
    public NabuccoList<SearchQualifierExtension> getQualifierList() {
        if ((this.qualifierList == null)) {
            this.qualifierList = new NabuccoListImpl<SearchQualifierExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.qualifierList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(SearchIndexRefExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(SearchIndexRefExtension.class).getAllProperties();
    }
}
