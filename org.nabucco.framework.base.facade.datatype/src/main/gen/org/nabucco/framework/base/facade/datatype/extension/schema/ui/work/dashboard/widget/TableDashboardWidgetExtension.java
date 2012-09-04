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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.dashboard.widget;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.ColumnExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * TableDashboardWidgetExtension<p/>NABUCCO User Interface Dashboard table Widget extension.<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2011-11-10
 */
public class TableDashboardWidgetExtension extends DashboardWidgetExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;" };

    public static final String COLUMNS = "columns";

    /** The List Columns. */
    private NabuccoList<ColumnExtension> columns;

    /** Constructs a new TableDashboardWidgetExtension instance. */
    public TableDashboardWidgetExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TableDashboardWidgetExtension.
     */
    protected void cloneObject(TableDashboardWidgetExtension clone) {
        super.cloneObject(clone);
        if ((this.columns != null)) {
            clone.columns = this.columns.cloneCollection();
        }
    }

    /**
     * Getter for the ColumnsJPA.
     *
     * @return the List<ColumnExtension>.
     */
    List<ColumnExtension> getColumnsJPA() {
        if ((this.columns == null)) {
            this.columns = new NabuccoListImpl<ColumnExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<ColumnExtension>) this.columns).getDelegate();
    }

    /**
     * Setter for the ColumnsJPA.
     *
     * @param columns the List<ColumnExtension>.
     */
    void setColumnsJPA(List<ColumnExtension> columns) {
        if ((this.columns == null)) {
            this.columns = new NabuccoListImpl<ColumnExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<ColumnExtension>) this.columns).setDelegate(columns);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(DashboardWidgetExtension.class).getPropertyMap());
        propertyMap.put(COLUMNS, PropertyDescriptorSupport.createCollection(COLUMNS, ColumnExtension.class, 9,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(TableDashboardWidgetExtension.getPropertyDescriptor(COLUMNS), this.columns,
                null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(COLUMNS) && (property.getType() == ColumnExtension.class))) {
            this.columns = ((NabuccoList<ColumnExtension>) property.getInstance());
            return true;
        }
        return false;
    }

    @Override
    public TableDashboardWidgetExtension cloneObject() {
        TableDashboardWidgetExtension clone = new TableDashboardWidgetExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The List Columns.
     *
     * @return the NabuccoList<ColumnExtension>.
     */
    public NabuccoList<ColumnExtension> getColumns() {
        if ((this.columns == null)) {
            this.columns = new NabuccoListImpl<ColumnExtension>(NabuccoCollectionState.INITIALIZED);
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
        return PropertyCache.getInstance().retrieve(TableDashboardWidgetExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TableDashboardWidgetExtension.class).getAllProperties();
    }
}
