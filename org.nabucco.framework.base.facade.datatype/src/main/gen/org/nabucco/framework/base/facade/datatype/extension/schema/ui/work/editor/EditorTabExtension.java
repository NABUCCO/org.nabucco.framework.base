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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.EditorControlExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * EditorTabExtension<p/>NABUCCO User Interface Editor Tab extension.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-07-28
 */
public class EditorTabExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m0,n;" };

    public static final String LABEL = "label";

    public static final String TOOLTIP = "tooltip";

    public static final String ICON = "icon";

    public static final String GRID = "grid";

    public static final String FIELDS = "fields";

    /** The Tab Label. */
    private StringProperty label;

    /** The Tab Tooltip. */
    private StringProperty tooltip;

    /** The Tab Icon. */
    private StringProperty icon;

    /** The Tab Grid. */
    private StringProperty grid;

    /** The Editor fields. */
    private NabuccoList<EditorControlExtension> fields;

    /** Constructs a new EditorTabExtension instance. */
    public EditorTabExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the EditorTabExtension.
     */
    protected void cloneObject(EditorTabExtension clone) {
        super.cloneObject(clone);
        if ((this.getLabel() != null)) {
            clone.setLabel(this.getLabel().cloneObject());
        }
        if ((this.getTooltip() != null)) {
            clone.setTooltip(this.getTooltip().cloneObject());
        }
        if ((this.getIcon() != null)) {
            clone.setIcon(this.getIcon().cloneObject());
        }
        if ((this.getGrid() != null)) {
            clone.setGrid(this.getGrid().cloneObject());
        }
        if ((this.fields != null)) {
            clone.fields = this.fields.cloneCollection();
        }
    }

    /**
     * Getter for the FieldsJPA.
     *
     * @return the List<EditorControlExtension>.
     */
    List<EditorControlExtension> getFieldsJPA() {
        if ((this.fields == null)) {
            this.fields = new NabuccoListImpl<EditorControlExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<EditorControlExtension>) this.fields).getDelegate();
    }

    /**
     * Setter for the FieldsJPA.
     *
     * @param fields the List<EditorControlExtension>.
     */
    void setFieldsJPA(List<EditorControlExtension> fields) {
        if ((this.fields == null)) {
            this.fields = new NabuccoListImpl<EditorControlExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<EditorControlExtension>) this.fields).setDelegate(fields);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(UiExtension.class).getPropertyMap());
        propertyMap.put(LABEL, PropertyDescriptorSupport.createDatatype(LABEL, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(TOOLTIP, PropertyDescriptorSupport.createDatatype(TOOLTIP, StringProperty.class, 5,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ICON, PropertyDescriptorSupport.createDatatype(ICON, StringProperty.class, 6,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(GRID, PropertyDescriptorSupport.createDatatype(GRID, StringProperty.class, 7,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(FIELDS, PropertyDescriptorSupport.createCollection(FIELDS, EditorControlExtension.class, 8,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(EditorTabExtension.getPropertyDescriptor(LABEL), this.getLabel(), null));
        properties
                .add(super.createProperty(EditorTabExtension.getPropertyDescriptor(TOOLTIP), this.getTooltip(), null));
        properties.add(super.createProperty(EditorTabExtension.getPropertyDescriptor(ICON), this.getIcon(), null));
        properties.add(super.createProperty(EditorTabExtension.getPropertyDescriptor(GRID), this.getGrid(), null));
        properties.add(super.createProperty(EditorTabExtension.getPropertyDescriptor(FIELDS), this.fields, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(LABEL) && (property.getType() == StringProperty.class))) {
            this.setLabel(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TOOLTIP) && (property.getType() == StringProperty.class))) {
            this.setTooltip(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ICON) && (property.getType() == StringProperty.class))) {
            this.setIcon(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(GRID) && (property.getType() == StringProperty.class))) {
            this.setGrid(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(FIELDS) && (property.getType() == EditorControlExtension.class))) {
            this.fields = ((NabuccoList<EditorControlExtension>) property.getInstance());
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
        final EditorTabExtension other = ((EditorTabExtension) obj);
        if ((this.label == null)) {
            if ((other.label != null))
                return false;
        } else if ((!this.label.equals(other.label)))
            return false;
        if ((this.tooltip == null)) {
            if ((other.tooltip != null))
                return false;
        } else if ((!this.tooltip.equals(other.tooltip)))
            return false;
        if ((this.icon == null)) {
            if ((other.icon != null))
                return false;
        } else if ((!this.icon.equals(other.icon)))
            return false;
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
        result = ((PRIME * result) + ((this.label == null) ? 0 : this.label.hashCode()));
        result = ((PRIME * result) + ((this.tooltip == null) ? 0 : this.tooltip.hashCode()));
        result = ((PRIME * result) + ((this.icon == null) ? 0 : this.icon.hashCode()));
        result = ((PRIME * result) + ((this.grid == null) ? 0 : this.grid.hashCode()));
        return result;
    }

    @Override
    public EditorTabExtension cloneObject() {
        EditorTabExtension clone = new EditorTabExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The Tab Label.
     *
     * @param label the StringProperty.
     */
    public void setLabel(StringProperty label) {
        this.label = label;
    }

    /**
     * The Tab Label.
     *
     * @return the StringProperty.
     */
    public StringProperty getLabel() {
        return this.label;
    }

    /**
     * The Tab Tooltip.
     *
     * @param tooltip the StringProperty.
     */
    public void setTooltip(StringProperty tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * The Tab Tooltip.
     *
     * @return the StringProperty.
     */
    public StringProperty getTooltip() {
        return this.tooltip;
    }

    /**
     * The Tab Icon.
     *
     * @param icon the StringProperty.
     */
    public void setIcon(StringProperty icon) {
        this.icon = icon;
    }

    /**
     * The Tab Icon.
     *
     * @return the StringProperty.
     */
    public StringProperty getIcon() {
        return this.icon;
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
     * The Editor fields.
     *
     * @return the NabuccoList<EditorControlExtension>.
     */
    public NabuccoList<EditorControlExtension> getFields() {
        if ((this.fields == null)) {
            this.fields = new NabuccoListImpl<EditorControlExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.fields;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(EditorTabExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(EditorTabExtension.class).getAllProperties();
    }
}
