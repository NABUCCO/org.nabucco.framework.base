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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.property.BooleanProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.control.DialogControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.control.DropDownSelectionItemExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * DropDownDialogControlExtension<p/>NABUCCO User Interface Dialog extension control.<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2011-11-08
 */
public class DropDownDialogControlExtension extends DialogControlExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m0,1;", "m0,n;" };

    public static final String EDITABLE = "editable";

    public static final String DEFAULTID = "defaultId";

    public static final String SELECTIONLIST = "selectionList";

    /** Indicates if the control is editable */
    private BooleanProperty editable;

    /** Defines the default value showed in the control */
    private StringProperty defaultId;

    /** Defines possible selection items */
    private NabuccoList<DropDownSelectionItemExtension> selectionList;

    /** Constructs a new DropDownDialogControlExtension instance. */
    public DropDownDialogControlExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the DropDownDialogControlExtension.
     */
    protected void cloneObject(DropDownDialogControlExtension clone) {
        super.cloneObject(clone);
        if ((this.getEditable() != null)) {
            clone.setEditable(this.getEditable().cloneObject());
        }
        if ((this.getDefaultId() != null)) {
            clone.setDefaultId(this.getDefaultId().cloneObject());
        }
        if ((this.selectionList != null)) {
            clone.selectionList = this.selectionList.cloneCollection();
        }
    }

    /**
     * Getter for the SelectionListJPA.
     *
     * @return the List<DropDownSelectionItemExtension>.
     */
    List<DropDownSelectionItemExtension> getSelectionListJPA() {
        if ((this.selectionList == null)) {
            this.selectionList = new NabuccoListImpl<DropDownSelectionItemExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<DropDownSelectionItemExtension>) this.selectionList).getDelegate();
    }

    /**
     * Setter for the SelectionListJPA.
     *
     * @param selectionList the List<DropDownSelectionItemExtension>.
     */
    void setSelectionListJPA(List<DropDownSelectionItemExtension> selectionList) {
        if ((this.selectionList == null)) {
            this.selectionList = new NabuccoListImpl<DropDownSelectionItemExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<DropDownSelectionItemExtension>) this.selectionList).setDelegate(selectionList);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(DialogControlExtension.class).getPropertyMap());
        propertyMap.put(EDITABLE, PropertyDescriptorSupport.createDatatype(EDITABLE, BooleanProperty.class, 9,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(DEFAULTID, PropertyDescriptorSupport.createDatatype(DEFAULTID, StringProperty.class, 10,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(SELECTIONLIST, PropertyDescriptorSupport.createCollection(SELECTIONLIST,
                DropDownSelectionItemExtension.class, 11, PROPERTY_CONSTRAINTS[2], false,
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
        properties.add(super.createProperty(DropDownDialogControlExtension.getPropertyDescriptor(EDITABLE),
                this.getEditable(), null));
        properties.add(super.createProperty(DropDownDialogControlExtension.getPropertyDescriptor(DEFAULTID),
                this.getDefaultId(), null));
        properties.add(super.createProperty(DropDownDialogControlExtension.getPropertyDescriptor(SELECTIONLIST),
                this.selectionList, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(EDITABLE) && (property.getType() == BooleanProperty.class))) {
            this.setEditable(((BooleanProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DEFAULTID) && (property.getType() == StringProperty.class))) {
            this.setDefaultId(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SELECTIONLIST) && (property.getType() == DropDownSelectionItemExtension.class))) {
            this.selectionList = ((NabuccoList<DropDownSelectionItemExtension>) property.getInstance());
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
        final DropDownDialogControlExtension other = ((DropDownDialogControlExtension) obj);
        if ((this.editable == null)) {
            if ((other.editable != null))
                return false;
        } else if ((!this.editable.equals(other.editable)))
            return false;
        if ((this.defaultId == null)) {
            if ((other.defaultId != null))
                return false;
        } else if ((!this.defaultId.equals(other.defaultId)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.editable == null) ? 0 : this.editable.hashCode()));
        result = ((PRIME * result) + ((this.defaultId == null) ? 0 : this.defaultId.hashCode()));
        return result;
    }

    @Override
    public DropDownDialogControlExtension cloneObject() {
        DropDownDialogControlExtension clone = new DropDownDialogControlExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Indicates if the control is editable
     *
     * @param editable the BooleanProperty.
     */
    public void setEditable(BooleanProperty editable) {
        this.editable = editable;
    }

    /**
     * Indicates if the control is editable
     *
     * @return the BooleanProperty.
     */
    public BooleanProperty getEditable() {
        return this.editable;
    }

    /**
     * Defines the default value showed in the control
     *
     * @param defaultId the StringProperty.
     */
    public void setDefaultId(StringProperty defaultId) {
        this.defaultId = defaultId;
    }

    /**
     * Defines the default value showed in the control
     *
     * @return the StringProperty.
     */
    public StringProperty getDefaultId() {
        return this.defaultId;
    }

    /**
     * Defines possible selection items
     *
     * @return the NabuccoList<DropDownSelectionItemExtension>.
     */
    public NabuccoList<DropDownSelectionItemExtension> getSelectionList() {
        if ((this.selectionList == null)) {
            this.selectionList = new NabuccoListImpl<DropDownSelectionItemExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.selectionList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(DropDownDialogControlExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(DropDownDialogControlExtension.class).getAllProperties();
    }
}
