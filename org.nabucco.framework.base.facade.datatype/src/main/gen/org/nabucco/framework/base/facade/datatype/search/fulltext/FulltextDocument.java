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
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeSupport;
import org.nabucco.framework.base.facade.datatype.Flag;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * FulltextDocument<p/>Datatype containing the fields for fulltext indexing.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-21
 */
public class FulltextDocument extends DatatypeSupport implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;", "l0,n;u0,n;m1,1;" };

    public static final String FIELDLIST = "fieldList";

    public static final String DELETED = "deleted";

    /** The list of fields of the fulltext document. */
    private NabuccoList<FulltextField> fieldList;

    /** Flag when true is indicating that the fulltext document is marked for deletion. */
    private Flag deleted;

    /** Constructs a new FulltextDocument instance. */
    public FulltextDocument() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the FulltextDocument.
     */
    protected void cloneObject(FulltextDocument clone) {
        super.cloneObject(clone);
        if ((this.fieldList != null)) {
            clone.fieldList = this.fieldList.cloneCollection();
        }
        if ((this.getDeleted() != null)) {
            clone.setDeleted(this.getDeleted().cloneObject());
        }
    }

    /**
     * Getter for the FieldListJPA.
     *
     * @return the List<FulltextField>.
     */
    List<FulltextField> getFieldListJPA() {
        if ((this.fieldList == null)) {
            this.fieldList = new NabuccoListImpl<FulltextField>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<FulltextField>) this.fieldList).getDelegate();
    }

    /**
     * Setter for the FieldListJPA.
     *
     * @param fieldList the List<FulltextField>.
     */
    void setFieldListJPA(List<FulltextField> fieldList) {
        if ((this.fieldList == null)) {
            this.fieldList = new NabuccoListImpl<FulltextField>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<FulltextField>) this.fieldList).setDelegate(fieldList);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(FIELDLIST, PropertyDescriptorSupport.createCollection(FIELDLIST, FulltextField.class, 0,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(DELETED,
                PropertyDescriptorSupport.createBasetype(DELETED, Flag.class, 1, PROPERTY_CONSTRAINTS[1], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(FulltextDocument.getPropertyDescriptor(FIELDLIST), this.fieldList, null));
        properties.add(super.createProperty(FulltextDocument.getPropertyDescriptor(DELETED), this.deleted, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(FIELDLIST) && (property.getType() == FulltextField.class))) {
            this.fieldList = ((NabuccoList<FulltextField>) property.getInstance());
            return true;
        } else if ((property.getName().equals(DELETED) && (property.getType() == Flag.class))) {
            this.setDeleted(((Flag) property.getInstance()));
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
        final FulltextDocument other = ((FulltextDocument) obj);
        if ((this.deleted == null)) {
            if ((other.deleted != null))
                return false;
        } else if ((!this.deleted.equals(other.deleted)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.deleted == null) ? 0 : this.deleted.hashCode()));
        return result;
    }

    @Override
    public FulltextDocument cloneObject() {
        FulltextDocument clone = new FulltextDocument();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The list of fields of the fulltext document.
     *
     * @return the NabuccoList<FulltextField>.
     */
    public NabuccoList<FulltextField> getFieldList() {
        if ((this.fieldList == null)) {
            this.fieldList = new NabuccoListImpl<FulltextField>(NabuccoCollectionState.INITIALIZED);
        }
        return this.fieldList;
    }

    /**
     * Flag when true is indicating that the fulltext document is marked for deletion.
     *
     * @return the Flag.
     */
    public Flag getDeleted() {
        return this.deleted;
    }

    /**
     * Flag when true is indicating that the fulltext document is marked for deletion.
     *
     * @param deleted the Flag.
     */
    public void setDeleted(Flag deleted) {
        this.deleted = deleted;
    }

    /**
     * Flag when true is indicating that the fulltext document is marked for deletion.
     *
     * @param deleted the Boolean.
     */
    public void setDeleted(Boolean deleted) {
        if ((this.deleted == null)) {
            if ((deleted == null)) {
                return;
            }
            this.deleted = new Flag();
        }
        this.deleted.setValue(deleted);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(FulltextDocument.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(FulltextDocument.class).getAllProperties();
    }
}
