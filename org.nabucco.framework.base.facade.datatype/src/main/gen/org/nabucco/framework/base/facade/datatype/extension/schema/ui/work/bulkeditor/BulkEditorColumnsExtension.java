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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.bulkeditor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.property.BooleanProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.IntegerProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.bulkeditor.columns.BulkEditorColumnExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * BulkEditorColumnsExtension<p/>NABUCCO User Interface Bulkeditor extension.<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-04-11
 */
public class BulkEditorColumnsExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,1;", "m0,1;", "m0,n;" };

    public static final String PAGINGSIZE = "pagingSize";

    public static final String PAGING = "paging";

    public static final String COLUMNS = "columns";

    /** The size of the page */
    private IntegerProperty pagingSize;

    /** Indicates if paging should be enabled or all items should be shown at same time */
    private BooleanProperty paging;

    /** The columns to show on the ui */
    private NabuccoList<BulkEditorColumnExtension> columns;

    /** Constructs a new BulkEditorColumnsExtension instance. */
    public BulkEditorColumnsExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the BulkEditorColumnsExtension.
     */
    protected void cloneObject(BulkEditorColumnsExtension clone) {
        super.cloneObject(clone);
        if ((this.getPagingSize() != null)) {
            clone.setPagingSize(this.getPagingSize().cloneObject());
        }
        if ((this.getPaging() != null)) {
            clone.setPaging(this.getPaging().cloneObject());
        }
        if ((this.columns != null)) {
            clone.columns = this.columns.cloneCollection();
        }
    }

    /**
     * Getter for the ColumnsJPA.
     *
     * @return the List<BulkEditorColumnExtension>.
     */
    List<BulkEditorColumnExtension> getColumnsJPA() {
        if ((this.columns == null)) {
            this.columns = new NabuccoListImpl<BulkEditorColumnExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<BulkEditorColumnExtension>) this.columns).getDelegate();
    }

    /**
     * Setter for the ColumnsJPA.
     *
     * @param columns the List<BulkEditorColumnExtension>.
     */
    void setColumnsJPA(List<BulkEditorColumnExtension> columns) {
        if ((this.columns == null)) {
            this.columns = new NabuccoListImpl<BulkEditorColumnExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<BulkEditorColumnExtension>) this.columns).setDelegate(columns);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(UiExtension.class).getPropertyMap());
        propertyMap.put(PAGINGSIZE, PropertyDescriptorSupport.createDatatype(PAGINGSIZE, IntegerProperty.class, 4,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(PAGING, PropertyDescriptorSupport.createDatatype(PAGING, BooleanProperty.class, 5,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(COLUMNS, PropertyDescriptorSupport.createCollection(COLUMNS, BulkEditorColumnExtension.class,
                6, PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(BulkEditorColumnsExtension.getPropertyDescriptor(PAGINGSIZE),
                this.getPagingSize(), null));
        properties.add(super.createProperty(BulkEditorColumnsExtension.getPropertyDescriptor(PAGING), this.getPaging(),
                null));
        properties.add(super.createProperty(BulkEditorColumnsExtension.getPropertyDescriptor(COLUMNS), this.columns,
                null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(PAGINGSIZE) && (property.getType() == IntegerProperty.class))) {
            this.setPagingSize(((IntegerProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PAGING) && (property.getType() == BooleanProperty.class))) {
            this.setPaging(((BooleanProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(COLUMNS) && (property.getType() == BulkEditorColumnExtension.class))) {
            this.columns = ((NabuccoList<BulkEditorColumnExtension>) property.getInstance());
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
        final BulkEditorColumnsExtension other = ((BulkEditorColumnsExtension) obj);
        if ((this.pagingSize == null)) {
            if ((other.pagingSize != null))
                return false;
        } else if ((!this.pagingSize.equals(other.pagingSize)))
            return false;
        if ((this.paging == null)) {
            if ((other.paging != null))
                return false;
        } else if ((!this.paging.equals(other.paging)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.pagingSize == null) ? 0 : this.pagingSize.hashCode()));
        result = ((PRIME * result) + ((this.paging == null) ? 0 : this.paging.hashCode()));
        return result;
    }

    @Override
    public BulkEditorColumnsExtension cloneObject() {
        BulkEditorColumnsExtension clone = new BulkEditorColumnsExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The size of the page
     *
     * @param pagingSize the IntegerProperty.
     */
    public void setPagingSize(IntegerProperty pagingSize) {
        this.pagingSize = pagingSize;
    }

    /**
     * The size of the page
     *
     * @return the IntegerProperty.
     */
    public IntegerProperty getPagingSize() {
        return this.pagingSize;
    }

    /**
     * Indicates if paging should be enabled or all items should be shown at same time
     *
     * @param paging the BooleanProperty.
     */
    public void setPaging(BooleanProperty paging) {
        this.paging = paging;
    }

    /**
     * Indicates if paging should be enabled or all items should be shown at same time
     *
     * @return the BooleanProperty.
     */
    public BooleanProperty getPaging() {
        return this.paging;
    }

    /**
     * The columns to show on the ui
     *
     * @return the NabuccoList<BulkEditorColumnExtension>.
     */
    public NabuccoList<BulkEditorColumnExtension> getColumns() {
        if ((this.columns == null)) {
            this.columns = new NabuccoListImpl<BulkEditorColumnExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.columns;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(BulkEditorColumnsExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(BulkEditorColumnsExtension.class).getAllProperties();
    }
}
