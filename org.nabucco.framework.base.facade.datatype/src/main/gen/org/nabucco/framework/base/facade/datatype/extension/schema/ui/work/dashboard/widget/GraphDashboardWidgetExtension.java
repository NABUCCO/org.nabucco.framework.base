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
import org.nabucco.framework.base.facade.datatype.extension.property.ClassProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.IntegerProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * GraphDashboardWidgetExtension<p/>NABUCCO User Interface Piechart Dashboard Widget extension.<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2011-11-10
 */
public class GraphDashboardWidgetExtension extends DashboardWidgetExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m0,1;", "m0,n;" };

    public static final String ANALYSERCLASS = "analyserClass";

    public static final String COLORSHEMEID = "colorShemeId";

    public static final String MINPERCENTVALUE = "minPercentValue";

    public static final String SKALEITEMS = "skaleItems";

    /** The reference to the analyser class to analyse to data */
    public ClassProperty analyserClass;

    /** The id of the colorsheme to use */
    public StringProperty colorShemeId;

    /** The minimum percent value to show */
    public IntegerProperty minPercentValue;

    /** The skale of the graph */
    public NabuccoList<DashboardWidgetSkaleItemExtension> skaleItems;

    /** Constructs a new GraphDashboardWidgetExtension instance. */
    public GraphDashboardWidgetExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the GraphDashboardWidgetExtension.
     */
    protected void cloneObject(GraphDashboardWidgetExtension clone) {
        super.cloneObject(clone);
        if ((this.getAnalyserClass() != null)) {
            clone.setAnalyserClass(this.getAnalyserClass().cloneObject());
        }
        if ((this.getColorShemeId() != null)) {
            clone.setColorShemeId(this.getColorShemeId().cloneObject());
        }
        if ((this.getMinPercentValue() != null)) {
            clone.setMinPercentValue(this.getMinPercentValue().cloneObject());
        }
        if ((this.skaleItems != null)) {
            clone.skaleItems = this.skaleItems.cloneCollection();
        }
    }

    /**
     * Getter for the SkaleItemsJPA.
     *
     * @return the List<DashboardWidgetSkaleItemExtension>.
     */
    List<DashboardWidgetSkaleItemExtension> getSkaleItemsJPA() {
        if ((this.skaleItems == null)) {
            this.skaleItems = new NabuccoListImpl<DashboardWidgetSkaleItemExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<DashboardWidgetSkaleItemExtension>) this.skaleItems).getDelegate();
    }

    /**
     * Setter for the SkaleItemsJPA.
     *
     * @param skaleItems the List<DashboardWidgetSkaleItemExtension>.
     */
    void setSkaleItemsJPA(List<DashboardWidgetSkaleItemExtension> skaleItems) {
        if ((this.skaleItems == null)) {
            this.skaleItems = new NabuccoListImpl<DashboardWidgetSkaleItemExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<DashboardWidgetSkaleItemExtension>) this.skaleItems).setDelegate(skaleItems);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(DashboardWidgetExtension.class).getPropertyMap());
        propertyMap.put(ANALYSERCLASS, PropertyDescriptorSupport.createDatatype(ANALYSERCLASS, ClassProperty.class, 9,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(COLORSHEMEID, PropertyDescriptorSupport.createDatatype(COLORSHEMEID, StringProperty.class, 10,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(MINPERCENTVALUE, PropertyDescriptorSupport.createDatatype(MINPERCENTVALUE,
                IntegerProperty.class, 11, PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(SKALEITEMS, PropertyDescriptorSupport.createCollection(SKALEITEMS,
                DashboardWidgetSkaleItemExtension.class, 12, PROPERTY_CONSTRAINTS[3], false,
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
        properties.add(super.createProperty(GraphDashboardWidgetExtension.getPropertyDescriptor(ANALYSERCLASS),
                this.getAnalyserClass(), null));
        properties.add(super.createProperty(GraphDashboardWidgetExtension.getPropertyDescriptor(COLORSHEMEID),
                this.getColorShemeId(), null));
        properties.add(super.createProperty(GraphDashboardWidgetExtension.getPropertyDescriptor(MINPERCENTVALUE),
                this.getMinPercentValue(), null));
        properties.add(super.createProperty(GraphDashboardWidgetExtension.getPropertyDescriptor(SKALEITEMS),
                this.skaleItems, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(ANALYSERCLASS) && (property.getType() == ClassProperty.class))) {
            this.setAnalyserClass(((ClassProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(COLORSHEMEID) && (property.getType() == StringProperty.class))) {
            this.setColorShemeId(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MINPERCENTVALUE) && (property.getType() == IntegerProperty.class))) {
            this.setMinPercentValue(((IntegerProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SKALEITEMS) && (property.getType() == DashboardWidgetSkaleItemExtension.class))) {
            this.skaleItems = ((NabuccoList<DashboardWidgetSkaleItemExtension>) property.getInstance());
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
        final GraphDashboardWidgetExtension other = ((GraphDashboardWidgetExtension) obj);
        if ((this.analyserClass == null)) {
            if ((other.analyserClass != null))
                return false;
        } else if ((!this.analyserClass.equals(other.analyserClass)))
            return false;
        if ((this.colorShemeId == null)) {
            if ((other.colorShemeId != null))
                return false;
        } else if ((!this.colorShemeId.equals(other.colorShemeId)))
            return false;
        if ((this.minPercentValue == null)) {
            if ((other.minPercentValue != null))
                return false;
        } else if ((!this.minPercentValue.equals(other.minPercentValue)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.analyserClass == null) ? 0 : this.analyserClass.hashCode()));
        result = ((PRIME * result) + ((this.colorShemeId == null) ? 0 : this.colorShemeId.hashCode()));
        result = ((PRIME * result) + ((this.minPercentValue == null) ? 0 : this.minPercentValue.hashCode()));
        return result;
    }

    @Override
    public GraphDashboardWidgetExtension cloneObject() {
        GraphDashboardWidgetExtension clone = new GraphDashboardWidgetExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The reference to the analyser class to analyse to data
     *
     * @param analyserClass the ClassProperty.
     */
    public void setAnalyserClass(ClassProperty analyserClass) {
        this.analyserClass = analyserClass;
    }

    /**
     * The reference to the analyser class to analyse to data
     *
     * @return the ClassProperty.
     */
    public ClassProperty getAnalyserClass() {
        return this.analyserClass;
    }

    /**
     * The id of the colorsheme to use
     *
     * @param colorShemeId the StringProperty.
     */
    public void setColorShemeId(StringProperty colorShemeId) {
        this.colorShemeId = colorShemeId;
    }

    /**
     * The id of the colorsheme to use
     *
     * @return the StringProperty.
     */
    public StringProperty getColorShemeId() {
        return this.colorShemeId;
    }

    /**
     * The minimum percent value to show
     *
     * @param minPercentValue the IntegerProperty.
     */
    public void setMinPercentValue(IntegerProperty minPercentValue) {
        this.minPercentValue = minPercentValue;
    }

    /**
     * The minimum percent value to show
     *
     * @return the IntegerProperty.
     */
    public IntegerProperty getMinPercentValue() {
        return this.minPercentValue;
    }

    /**
     * The skale of the graph
     *
     * @return the NabuccoList<DashboardWidgetSkaleItemExtension>.
     */
    public NabuccoList<DashboardWidgetSkaleItemExtension> getSkaleItems() {
        if ((this.skaleItems == null)) {
            this.skaleItems = new NabuccoListImpl<DashboardWidgetSkaleItemExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.skaleItems;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(GraphDashboardWidgetExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(GraphDashboardWidgetExtension.class).getAllProperties();
    }
}
