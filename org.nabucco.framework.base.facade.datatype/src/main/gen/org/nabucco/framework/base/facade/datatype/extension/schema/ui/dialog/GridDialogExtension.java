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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.control.DialogControlExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * GridDialogExtension<p/>NABUCCO User Interface Grid Dialog extension.<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2011-08-05
 */
public class GridDialogExtension extends DialogExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m0,n;" };

    public static final String GRID = "grid";

    public static final String CONTROLS = "controls";

    /** The Tab Grid. */
    private StringProperty grid;

    /** The Dialog controls */
    private NabuccoList<DialogControlExtension> controls;

    /** Constructs a new GridDialogExtension instance. */
    public GridDialogExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the GridDialogExtension.
     */
    protected void cloneObject(GridDialogExtension clone) {
        super.cloneObject(clone);
        if ((this.getGrid() != null)) {
            clone.setGrid(this.getGrid().cloneObject());
        }
        if ((this.controls != null)) {
            clone.controls = this.controls.cloneCollection();
        }
    }

    /**
     * Getter for the ControlsJPA.
     *
     * @return the List<DialogControlExtension>.
     */
    List<DialogControlExtension> getControlsJPA() {
        if ((this.controls == null)) {
            this.controls = new NabuccoListImpl<DialogControlExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<DialogControlExtension>) this.controls).getDelegate();
    }

    /**
     * Setter for the ControlsJPA.
     *
     * @param controls the List<DialogControlExtension>.
     */
    void setControlsJPA(List<DialogControlExtension> controls) {
        if ((this.controls == null)) {
            this.controls = new NabuccoListImpl<DialogControlExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<DialogControlExtension>) this.controls).setDelegate(controls);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(DialogExtension.class).getPropertyMap());
        propertyMap.put(GRID, PropertyDescriptorSupport.createDatatype(GRID, StringProperty.class, 9,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(CONTROLS, PropertyDescriptorSupport.createCollection(CONTROLS, DialogControlExtension.class,
                10, PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(GridDialogExtension.getPropertyDescriptor(GRID), this.getGrid(), null));
        properties.add(super.createProperty(GridDialogExtension.getPropertyDescriptor(CONTROLS), this.controls, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(GRID) && (property.getType() == StringProperty.class))) {
            this.setGrid(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CONTROLS) && (property.getType() == DialogControlExtension.class))) {
            this.controls = ((NabuccoList<DialogControlExtension>) property.getInstance());
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
        final GridDialogExtension other = ((GridDialogExtension) obj);
        if ((this.grid == null)) {
            if ((other.grid != null))
                return false;
        } else if ((!this.grid.equals(other.grid)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.grid == null) ? 0 : this.grid.hashCode()));
        return result;
    }

    @Override
    public GridDialogExtension cloneObject() {
        GridDialogExtension clone = new GridDialogExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The Tab Grid.
     *
     * @param grid the StringProperty.
     */
    public void setGrid(StringProperty grid) {
        this.grid = grid;
    }

    /**
     * The Tab Grid.
     *
     * @return the StringProperty.
     */
    public StringProperty getGrid() {
        return this.grid;
    }

    /**
     * The Dialog controls
     *
     * @return the NabuccoList<DialogControlExtension>.
     */
    public NabuccoList<DialogControlExtension> getControls() {
        if ((this.controls == null)) {
            this.controls = new NabuccoListImpl<DialogControlExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.controls;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(GridDialogExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(GridDialogExtension.class).getAllProperties();
    }
}
