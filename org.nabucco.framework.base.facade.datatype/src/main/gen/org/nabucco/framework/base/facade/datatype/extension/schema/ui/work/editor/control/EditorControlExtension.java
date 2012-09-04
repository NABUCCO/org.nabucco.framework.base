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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.BooleanProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.dependency.DependencySetExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * EditorControlExtension<p/>NABUCCO User Interface Editor Entry extension.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-07-28
 */
public class EditorControlExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m1,1;",
            "m1,1;", "m0,1;" };

    public static final String PROPERTY = "property";

    public static final String LABEL = "label";

    public static final String TOOLTIP = "tooltip";

    public static final String POSITION = "position";

    public static final String HINT = "hint";

    public static final String EDITABLE = "editable";

    public static final String TYPE = "type";

    public static final String DEPENDENCYSET = "dependencySet";

    /** The path to the binded property. */
    private StringProperty property;

    /** The Control label. */
    private StringProperty label;

    /** The Control tooltip. */
    private StringProperty tooltip;

    /** The Control position in the grid. */
    private StringProperty position;

    /** The Control positioning hint. */
    private StringProperty hint;

    /** Is the control editable */
    private BooleanProperty editable;

    /** the type of the control */
    private EnumerationProperty type;

    /** The set of the dependencies of the control */
    private DependencySetExtension dependencySet;

    /** Constructs a new EditorControlExtension instance. */
    public EditorControlExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the EditorControlExtension.
     */
    protected void cloneObject(EditorControlExtension clone) {
        super.cloneObject(clone);
        if ((this.getProperty() != null)) {
            clone.setProperty(this.getProperty().cloneObject());
        }
        if ((this.getLabel() != null)) {
            clone.setLabel(this.getLabel().cloneObject());
        }
        if ((this.getTooltip() != null)) {
            clone.setTooltip(this.getTooltip().cloneObject());
        }
        if ((this.getPosition() != null)) {
            clone.setPosition(this.getPosition().cloneObject());
        }
        if ((this.getHint() != null)) {
            clone.setHint(this.getHint().cloneObject());
        }
        if ((this.getEditable() != null)) {
            clone.setEditable(this.getEditable().cloneObject());
        }
        if ((this.getType() != null)) {
            clone.setType(this.getType().cloneObject());
        }
        if ((this.getDependencySet() != null)) {
            clone.setDependencySet(this.getDependencySet().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(UiExtension.class).getPropertyMap());
        propertyMap.put(PROPERTY, PropertyDescriptorSupport.createDatatype(PROPERTY, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(LABEL, PropertyDescriptorSupport.createDatatype(LABEL, StringProperty.class, 5,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(TOOLTIP, PropertyDescriptorSupport.createDatatype(TOOLTIP, StringProperty.class, 6,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(POSITION, PropertyDescriptorSupport.createDatatype(POSITION, StringProperty.class, 7,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(HINT, PropertyDescriptorSupport.createDatatype(HINT, StringProperty.class, 8,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(EDITABLE, PropertyDescriptorSupport.createDatatype(EDITABLE, BooleanProperty.class, 9,
                PROPERTY_CONSTRAINTS[5], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(TYPE, PropertyDescriptorSupport.createDatatype(TYPE, EnumerationProperty.class, 10,
                PROPERTY_CONSTRAINTS[6], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(DEPENDENCYSET, PropertyDescriptorSupport.createDatatype(DEPENDENCYSET,
                DependencySetExtension.class, 11, PROPERTY_CONSTRAINTS[7], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(EditorControlExtension.getPropertyDescriptor(PROPERTY), this.getProperty(),
                null));
        properties
                .add(super.createProperty(EditorControlExtension.getPropertyDescriptor(LABEL), this.getLabel(), null));
        properties.add(super.createProperty(EditorControlExtension.getPropertyDescriptor(TOOLTIP), this.getTooltip(),
                null));
        properties.add(super.createProperty(EditorControlExtension.getPropertyDescriptor(POSITION), this.getPosition(),
                null));
        properties.add(super.createProperty(EditorControlExtension.getPropertyDescriptor(HINT), this.getHint(), null));
        properties.add(super.createProperty(EditorControlExtension.getPropertyDescriptor(EDITABLE), this.getEditable(),
                null));
        properties.add(super.createProperty(EditorControlExtension.getPropertyDescriptor(TYPE), this.getType(), null));
        properties.add(super.createProperty(EditorControlExtension.getPropertyDescriptor(DEPENDENCYSET),
                this.getDependencySet(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(PROPERTY) && (property.getType() == StringProperty.class))) {
            this.setProperty(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LABEL) && (property.getType() == StringProperty.class))) {
            this.setLabel(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TOOLTIP) && (property.getType() == StringProperty.class))) {
            this.setTooltip(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(POSITION) && (property.getType() == StringProperty.class))) {
            this.setPosition(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(HINT) && (property.getType() == StringProperty.class))) {
            this.setHint(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(EDITABLE) && (property.getType() == BooleanProperty.class))) {
            this.setEditable(((BooleanProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TYPE) && (property.getType() == EnumerationProperty.class))) {
            this.setType(((EnumerationProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DEPENDENCYSET) && (property.getType() == DependencySetExtension.class))) {
            this.setDependencySet(((DependencySetExtension) property.getInstance()));
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
        final EditorControlExtension other = ((EditorControlExtension) obj);
        if ((this.property == null)) {
            if ((other.property != null))
                return false;
        } else if ((!this.property.equals(other.property)))
            return false;
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
        if ((this.position == null)) {
            if ((other.position != null))
                return false;
        } else if ((!this.position.equals(other.position)))
            return false;
        if ((this.hint == null)) {
            if ((other.hint != null))
                return false;
        } else if ((!this.hint.equals(other.hint)))
            return false;
        if ((this.editable == null)) {
            if ((other.editable != null))
                return false;
        } else if ((!this.editable.equals(other.editable)))
            return false;
        if ((this.type == null)) {
            if ((other.type != null))
                return false;
        } else if ((!this.type.equals(other.type)))
            return false;
        if ((this.dependencySet == null)) {
            if ((other.dependencySet != null))
                return false;
        } else if ((!this.dependencySet.equals(other.dependencySet)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.property == null) ? 0 : this.property.hashCode()));
        result = ((PRIME * result) + ((this.label == null) ? 0 : this.label.hashCode()));
        result = ((PRIME * result) + ((this.tooltip == null) ? 0 : this.tooltip.hashCode()));
        result = ((PRIME * result) + ((this.position == null) ? 0 : this.position.hashCode()));
        result = ((PRIME * result) + ((this.hint == null) ? 0 : this.hint.hashCode()));
        result = ((PRIME * result) + ((this.editable == null) ? 0 : this.editable.hashCode()));
        result = ((PRIME * result) + ((this.type == null) ? 0 : this.type.hashCode()));
        result = ((PRIME * result) + ((this.dependencySet == null) ? 0 : this.dependencySet.hashCode()));
        return result;
    }

    @Override
    public EditorControlExtension cloneObject() {
        EditorControlExtension clone = new EditorControlExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The path to the binded property.
     *
     * @param property the StringProperty.
     */
    public void setProperty(StringProperty property) {
        this.property = property;
    }

    /**
     * The path to the binded property.
     *
     * @return the StringProperty.
     */
    public StringProperty getProperty() {
        return this.property;
    }

    /**
     * The Control label.
     *
     * @param label the StringProperty.
     */
    public void setLabel(StringProperty label) {
        this.label = label;
    }

    /**
     * The Control label.
     *
     * @return the StringProperty.
     */
    public StringProperty getLabel() {
        return this.label;
    }

    /**
     * The Control tooltip.
     *
     * @param tooltip the StringProperty.
     */
    public void setTooltip(StringProperty tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * The Control tooltip.
     *
     * @return the StringProperty.
     */
    public StringProperty getTooltip() {
        return this.tooltip;
    }

    /**
     * The Control position in the grid.
     *
     * @param position the StringProperty.
     */
    public void setPosition(StringProperty position) {
        this.position = position;
    }

    /**
     * The Control position in the grid.
     *
     * @return the StringProperty.
     */
    public StringProperty getPosition() {
        return this.position;
    }

    /**
     * The Control positioning hint.
     *
     * @param hint the StringProperty.
     */
    public void setHint(StringProperty hint) {
        this.hint = hint;
    }

    /**
     * The Control positioning hint.
     *
     * @return the StringProperty.
     */
    public StringProperty getHint() {
        return this.hint;
    }

    /**
     * Is the control editable
     *
     * @param editable the BooleanProperty.
     */
    public void setEditable(BooleanProperty editable) {
        this.editable = editable;
    }

    /**
     * Is the control editable
     *
     * @return the BooleanProperty.
     */
    public BooleanProperty getEditable() {
        return this.editable;
    }

    /**
     * the type of the control
     *
     * @param type the EnumerationProperty.
     */
    public void setType(EnumerationProperty type) {
        this.type = type;
    }

    /**
     * the type of the control
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getType() {
        return this.type;
    }

    /**
     * The set of the dependencies of the control
     *
     * @param dependencySet the DependencySetExtension.
     */
    public void setDependencySet(DependencySetExtension dependencySet) {
        this.dependencySet = dependencySet;
    }

    /**
     * The set of the dependencies of the control
     *
     * @return the DependencySetExtension.
     */
    public DependencySetExtension getDependencySet() {
        return this.dependencySet;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(EditorControlExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(EditorControlExtension.class).getAllProperties();
    }
}
