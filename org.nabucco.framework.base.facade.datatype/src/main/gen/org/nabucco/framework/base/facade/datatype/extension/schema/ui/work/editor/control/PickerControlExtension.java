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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.BooleanProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.EditorControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.queryfilter.QueryParameterMappingExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * PickerControlExtension<p/>Picker conrtol extension<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-07-28
 */
public class PickerControlExtension extends EditorControlExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m0,1;" };

    public static final String OPENACTION = "openAction";

    public static final String PICKERDIALOG = "pickerDialog";

    public static final String MULTIPLESELECTION = "multipleSelection";

    public static final String DISPLAYPATH = "displayPath";

    public static final String QUERYPARAMETERMAPPING = "queryParameterMapping";

    /** The action to be fired to open element */
    private StringProperty openAction;

    /** Pickerdialog to use */
    private StringProperty pickerDialog;

    /** Support of multiple selection */
    private BooleanProperty multipleSelection;

    /** Path of bound element to be displayd in the picker */
    private StringProperty displayPath;

    /** The mapping for query filter parameters if any */
    private QueryParameterMappingExtension queryParameterMapping;

    /** Constructs a new PickerControlExtension instance. */
    public PickerControlExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the PickerControlExtension.
     */
    protected void cloneObject(PickerControlExtension clone) {
        super.cloneObject(clone);
        if ((this.getOpenAction() != null)) {
            clone.setOpenAction(this.getOpenAction().cloneObject());
        }
        if ((this.getPickerDialog() != null)) {
            clone.setPickerDialog(this.getPickerDialog().cloneObject());
        }
        if ((this.getMultipleSelection() != null)) {
            clone.setMultipleSelection(this.getMultipleSelection().cloneObject());
        }
        if ((this.getDisplayPath() != null)) {
            clone.setDisplayPath(this.getDisplayPath().cloneObject());
        }
        if ((this.getQueryParameterMapping() != null)) {
            clone.setQueryParameterMapping(this.getQueryParameterMapping().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(EditorControlExtension.class).getPropertyMap());
        propertyMap.put(OPENACTION, PropertyDescriptorSupport.createDatatype(OPENACTION, StringProperty.class, 12,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(PICKERDIALOG, PropertyDescriptorSupport.createDatatype(PICKERDIALOG, StringProperty.class, 13,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(MULTIPLESELECTION, PropertyDescriptorSupport.createDatatype(MULTIPLESELECTION,
                BooleanProperty.class, 14, PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(DISPLAYPATH, PropertyDescriptorSupport.createDatatype(DISPLAYPATH, StringProperty.class, 15,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(QUERYPARAMETERMAPPING, PropertyDescriptorSupport.createDatatype(QUERYPARAMETERMAPPING,
                QueryParameterMappingExtension.class, 16, PROPERTY_CONSTRAINTS[4], false,
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
        properties.add(super.createProperty(PickerControlExtension.getPropertyDescriptor(OPENACTION),
                this.getOpenAction(), null));
        properties.add(super.createProperty(PickerControlExtension.getPropertyDescriptor(PICKERDIALOG),
                this.getPickerDialog(), null));
        properties.add(super.createProperty(PickerControlExtension.getPropertyDescriptor(MULTIPLESELECTION),
                this.getMultipleSelection(), null));
        properties.add(super.createProperty(PickerControlExtension.getPropertyDescriptor(DISPLAYPATH),
                this.getDisplayPath(), null));
        properties.add(super.createProperty(PickerControlExtension.getPropertyDescriptor(QUERYPARAMETERMAPPING),
                this.getQueryParameterMapping(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(OPENACTION) && (property.getType() == StringProperty.class))) {
            this.setOpenAction(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PICKERDIALOG) && (property.getType() == StringProperty.class))) {
            this.setPickerDialog(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MULTIPLESELECTION) && (property.getType() == BooleanProperty.class))) {
            this.setMultipleSelection(((BooleanProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DISPLAYPATH) && (property.getType() == StringProperty.class))) {
            this.setDisplayPath(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(QUERYPARAMETERMAPPING) && (property.getType() == QueryParameterMappingExtension.class))) {
            this.setQueryParameterMapping(((QueryParameterMappingExtension) property.getInstance()));
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
        final PickerControlExtension other = ((PickerControlExtension) obj);
        if ((this.openAction == null)) {
            if ((other.openAction != null))
                return false;
        } else if ((!this.openAction.equals(other.openAction)))
            return false;
        if ((this.pickerDialog == null)) {
            if ((other.pickerDialog != null))
                return false;
        } else if ((!this.pickerDialog.equals(other.pickerDialog)))
            return false;
        if ((this.multipleSelection == null)) {
            if ((other.multipleSelection != null))
                return false;
        } else if ((!this.multipleSelection.equals(other.multipleSelection)))
            return false;
        if ((this.displayPath == null)) {
            if ((other.displayPath != null))
                return false;
        } else if ((!this.displayPath.equals(other.displayPath)))
            return false;
        if ((this.queryParameterMapping == null)) {
            if ((other.queryParameterMapping != null))
                return false;
        } else if ((!this.queryParameterMapping.equals(other.queryParameterMapping)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.openAction == null) ? 0 : this.openAction.hashCode()));
        result = ((PRIME * result) + ((this.pickerDialog == null) ? 0 : this.pickerDialog.hashCode()));
        result = ((PRIME * result) + ((this.multipleSelection == null) ? 0 : this.multipleSelection.hashCode()));
        result = ((PRIME * result) + ((this.displayPath == null) ? 0 : this.displayPath.hashCode()));
        result = ((PRIME * result) + ((this.queryParameterMapping == null) ? 0 : this.queryParameterMapping.hashCode()));
        return result;
    }

    @Override
    public PickerControlExtension cloneObject() {
        PickerControlExtension clone = new PickerControlExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The action to be fired to open element
     *
     * @param openAction the StringProperty.
     */
    public void setOpenAction(StringProperty openAction) {
        this.openAction = openAction;
    }

    /**
     * The action to be fired to open element
     *
     * @return the StringProperty.
     */
    public StringProperty getOpenAction() {
        return this.openAction;
    }

    /**
     * Pickerdialog to use
     *
     * @param pickerDialog the StringProperty.
     */
    public void setPickerDialog(StringProperty pickerDialog) {
        this.pickerDialog = pickerDialog;
    }

    /**
     * Pickerdialog to use
     *
     * @return the StringProperty.
     */
    public StringProperty getPickerDialog() {
        return this.pickerDialog;
    }

    /**
     * Support of multiple selection
     *
     * @param multipleSelection the BooleanProperty.
     */
    public void setMultipleSelection(BooleanProperty multipleSelection) {
        this.multipleSelection = multipleSelection;
    }

    /**
     * Support of multiple selection
     *
     * @return the BooleanProperty.
     */
    public BooleanProperty getMultipleSelection() {
        return this.multipleSelection;
    }

    /**
     * Path of bound element to be displayd in the picker
     *
     * @param displayPath the StringProperty.
     */
    public void setDisplayPath(StringProperty displayPath) {
        this.displayPath = displayPath;
    }

    /**
     * Path of bound element to be displayd in the picker
     *
     * @return the StringProperty.
     */
    public StringProperty getDisplayPath() {
        return this.displayPath;
    }

    /**
     * The mapping for query filter parameters if any
     *
     * @param queryParameterMapping the QueryParameterMappingExtension.
     */
    public void setQueryParameterMapping(QueryParameterMappingExtension queryParameterMapping) {
        this.queryParameterMapping = queryParameterMapping;
    }

    /**
     * The mapping for query filter parameters if any
     *
     * @return the QueryParameterMappingExtension.
     */
    public QueryParameterMappingExtension getQueryParameterMapping() {
        return this.queryParameterMapping;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(PickerControlExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(PickerControlExtension.class).getAllProperties();
    }
}
