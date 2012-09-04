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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.titlebar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.property.IntegerProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.GridWidgetExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * TitleBarExtension<p/>NABUCCO User Interface TitleBar extension.<p/>
 *
 * @version 1.0
 * @author Nicolas Ratschinski, PRODYNA AG, 2011-07-17
 */
public class TitleBarExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,1;", "m0,n;" };

    public static final String HEIGHT = "height";

    public static final String GRIDS = "grids";

    /** Height of the statusbar */
    private IntegerProperty height;

    /** Grids of titlebar */
    private NabuccoList<GridWidgetExtension> grids;

    /** Constructs a new TitleBarExtension instance. */
    public TitleBarExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TitleBarExtension.
     */
    protected void cloneObject(TitleBarExtension clone) {
        super.cloneObject(clone);
        if ((this.getHeight() != null)) {
            clone.setHeight(this.getHeight().cloneObject());
        }
        if ((this.grids != null)) {
            clone.grids = this.grids.cloneCollection();
        }
    }

    /**
     * Getter for the GridsJPA.
     *
     * @return the List<GridWidgetExtension>.
     */
    List<GridWidgetExtension> getGridsJPA() {
        if ((this.grids == null)) {
            this.grids = new NabuccoListImpl<GridWidgetExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<GridWidgetExtension>) this.grids).getDelegate();
    }

    /**
     * Setter for the GridsJPA.
     *
     * @param grids the List<GridWidgetExtension>.
     */
    void setGridsJPA(List<GridWidgetExtension> grids) {
        if ((this.grids == null)) {
            this.grids = new NabuccoListImpl<GridWidgetExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<GridWidgetExtension>) this.grids).setDelegate(grids);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(UiExtension.class).getPropertyMap());
        propertyMap.put(HEIGHT, PropertyDescriptorSupport.createDatatype(HEIGHT, IntegerProperty.class, 4,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(GRIDS, PropertyDescriptorSupport.createCollection(GRIDS, GridWidgetExtension.class, 5,
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
        properties.add(super.createProperty(TitleBarExtension.getPropertyDescriptor(HEIGHT), this.getHeight(), null));
        properties.add(super.createProperty(TitleBarExtension.getPropertyDescriptor(GRIDS), this.grids, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(HEIGHT) && (property.getType() == IntegerProperty.class))) {
            this.setHeight(((IntegerProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(GRIDS) && (property.getType() == GridWidgetExtension.class))) {
            this.grids = ((NabuccoList<GridWidgetExtension>) property.getInstance());
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
        final TitleBarExtension other = ((TitleBarExtension) obj);
        if ((this.height == null)) {
            if ((other.height != null))
                return false;
        } else if ((!this.height.equals(other.height)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.height == null) ? 0 : this.height.hashCode()));
        return result;
    }

    @Override
    public TitleBarExtension cloneObject() {
        TitleBarExtension clone = new TitleBarExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Height of the statusbar
     *
     * @param height the IntegerProperty.
     */
    public void setHeight(IntegerProperty height) {
        this.height = height;
    }

    /**
     * Height of the statusbar
     *
     * @return the IntegerProperty.
     */
    public IntegerProperty getHeight() {
        return this.height;
    }

    /**
     * Grids of titlebar
     *
     * @return the NabuccoList<GridWidgetExtension>.
     */
    public NabuccoList<GridWidgetExtension> getGrids() {
        if ((this.grids == null)) {
            this.grids = new NabuccoListImpl<GridWidgetExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.grids;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TitleBarExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TitleBarExtension.class).getAllProperties();
    }
}
