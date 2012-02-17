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
import org.nabucco.framework.base.facade.datatype.DatatypeSupport;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.search.index.IndexName;
import org.nabucco.framework.base.facade.datatype.search.query.MaxResult;

/**
 * FulltextQuery<p/>A Query to search fulltext documents.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-03-08
 */
public class FulltextQuery extends DatatypeSupport implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l3,255;u0,n;m1,1;", "l0,n;u0,n;m0,1;", "m1,n;" };

    public static final String INDEXNAME = "indexName";

    public static final String MAXRESULT = "maxResult";

    public static final String FIELDLIST = "fieldList";

    /** The name of the index used for this query. */
    private IndexName indexName;

    /** The maximum amount of results. No value is interpreted as no limit. */
    private MaxResult maxResult;

    /** The fields of the query */
    private NabuccoList<FulltextQueryField> fieldList;

    /** Constructs a new FulltextQuery instance. */
    public FulltextQuery() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the FulltextQuery.
     */
    protected void cloneObject(FulltextQuery clone) {
        super.cloneObject(clone);
        if ((this.getIndexName() != null)) {
            clone.setIndexName(this.getIndexName().cloneObject());
        }
        if ((this.getMaxResult() != null)) {
            clone.setMaxResult(this.getMaxResult().cloneObject());
        }
        if ((this.fieldList != null)) {
            clone.fieldList = this.fieldList.cloneCollection();
        }
    }

    /**
     * Getter for the FieldListJPA.
     *
     * @return the List<FulltextQueryField>.
     */
    List<FulltextQueryField> getFieldListJPA() {
        if ((this.fieldList == null)) {
            this.fieldList = new NabuccoListImpl<FulltextQueryField>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<FulltextQueryField>) this.fieldList).getDelegate();
    }

    /**
     * Setter for the FieldListJPA.
     *
     * @param fieldList the List<FulltextQueryField>.
     */
    void setFieldListJPA(List<FulltextQueryField> fieldList) {
        if ((this.fieldList == null)) {
            this.fieldList = new NabuccoListImpl<FulltextQueryField>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<FulltextQueryField>) this.fieldList).setDelegate(fieldList);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap
                .put(INDEXNAME, PropertyDescriptorSupport.createBasetype(INDEXNAME, IndexName.class, 0,
                        PROPERTY_CONSTRAINTS[0], false));
        propertyMap
                .put(MAXRESULT, PropertyDescriptorSupport.createBasetype(MAXRESULT, MaxResult.class, 1,
                        PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(FIELDLIST, PropertyDescriptorSupport.createCollection(FIELDLIST, FulltextQueryField.class, 2,
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
        properties.add(super.createProperty(FulltextQuery.getPropertyDescriptor(INDEXNAME), this.indexName, null));
        properties.add(super.createProperty(FulltextQuery.getPropertyDescriptor(MAXRESULT), this.maxResult, null));
        properties.add(super.createProperty(FulltextQuery.getPropertyDescriptor(FIELDLIST), this.fieldList, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(INDEXNAME) && (property.getType() == IndexName.class))) {
            this.setIndexName(((IndexName) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MAXRESULT) && (property.getType() == MaxResult.class))) {
            this.setMaxResult(((MaxResult) property.getInstance()));
            return true;
        } else if ((property.getName().equals(FIELDLIST) && (property.getType() == FulltextQueryField.class))) {
            this.fieldList = ((NabuccoList<FulltextQueryField>) property.getInstance());
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
        final FulltextQuery other = ((FulltextQuery) obj);
        if ((this.indexName == null)) {
            if ((other.indexName != null))
                return false;
        } else if ((!this.indexName.equals(other.indexName)))
            return false;
        if ((this.maxResult == null)) {
            if ((other.maxResult != null))
                return false;
        } else if ((!this.maxResult.equals(other.maxResult)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.indexName == null) ? 0 : this.indexName.hashCode()));
        result = ((PRIME * result) + ((this.maxResult == null) ? 0 : this.maxResult.hashCode()));
        return result;
    }

    @Override
    public FulltextQuery cloneObject() {
        FulltextQuery clone = new FulltextQuery();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The name of the index used for this query.
     *
     * @return the IndexName.
     */
    public IndexName getIndexName() {
        return this.indexName;
    }

    /**
     * The name of the index used for this query.
     *
     * @param indexName the IndexName.
     */
    public void setIndexName(IndexName indexName) {
        this.indexName = indexName;
    }

    /**
     * The name of the index used for this query.
     *
     * @param indexName the String.
     */
    public void setIndexName(String indexName) {
        if ((this.indexName == null)) {
            if ((indexName == null)) {
                return;
            }
            this.indexName = new IndexName();
        }
        this.indexName.setValue(indexName);
    }

    /**
     * The maximum amount of results. No value is interpreted as no limit.
     *
     * @return the MaxResult.
     */
    public MaxResult getMaxResult() {
        return this.maxResult;
    }

    /**
     * The maximum amount of results. No value is interpreted as no limit.
     *
     * @param maxResult the MaxResult.
     */
    public void setMaxResult(MaxResult maxResult) {
        this.maxResult = maxResult;
    }

    /**
     * The maximum amount of results. No value is interpreted as no limit.
     *
     * @param maxResult the Integer.
     */
    public void setMaxResult(Integer maxResult) {
        if ((this.maxResult == null)) {
            if ((maxResult == null)) {
                return;
            }
            this.maxResult = new MaxResult();
        }
        this.maxResult.setValue(maxResult);
    }

    /**
     * The fields of the query
     *
     * @return the NabuccoList<FulltextQueryField>.
     */
    public NabuccoList<FulltextQueryField> getFieldList() {
        if ((this.fieldList == null)) {
            this.fieldList = new NabuccoListImpl<FulltextQueryField>(NabuccoCollectionState.INITIALIZED);
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
        return PropertyCache.getInstance().retrieve(FulltextQuery.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(FulltextQuery.class).getAllProperties();
    }
}
