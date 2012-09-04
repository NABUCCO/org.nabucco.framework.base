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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor;

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
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.ColumnExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.ListButtonExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.MenuButtonExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.dependency.DependencySetExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * EditorRelationExtension<p/>NABUCCO User Interface Editor Relation Tab extension.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-08-01
 */
public class EditorRelationExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m0,1;", "m0,1;", "m0,1;", "m0,1;",
            "m0,n;", "m0,n;", "m0,1;", "m0,1;" };

    public static final String LABEL = "label";

    public static final String TOOLTIP = "tooltip";

    public static final String PROPERTY = "property";

    public static final String LOADACTION = "loadAction";

    public static final String DOUBLECLICKACTION = "doubleclickAction";

    public static final String MENUBUTTON = "menuButton";

    public static final String BUTTONS = "buttons";

    public static final String COLUMNS = "columns";

    public static final String DEPENDENCYSET = "dependencySet";

    public static final String ISTECHNICAL = "isTechnical";

    /** The Relation Label. */
    private StringProperty label;

    /** The Relation Tooltip. */
    private StringProperty tooltip;

    /** The path to the binded property. */
    private StringProperty property;

    /** The action to be used for loading of datatypes. */
    private StringProperty loadAction;

    /** The action to be used to open content items */
    private StringProperty doubleclickAction;

    /** The Menu actions. */
    private MenuButtonExtension menuButton;

    /** The Relation actions. */
    private NabuccoList<ListButtonExtension> buttons;

    /** The Relation columns. */
    private NabuccoList<ColumnExtension> columns;

    /** The set of the dependencies of the control */
    private DependencySetExtension dependencySet;

    /** Describes if the tab should be printed */
    private BooleanProperty isTechnical;

    /** Constructs a new EditorRelationExtension instance. */
    public EditorRelationExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the EditorRelationExtension.
     */
    protected void cloneObject(EditorRelationExtension clone) {
        super.cloneObject(clone);
        if ((this.getLabel() != null)) {
            clone.setLabel(this.getLabel().cloneObject());
        }
        if ((this.getTooltip() != null)) {
            clone.setTooltip(this.getTooltip().cloneObject());
        }
        if ((this.getProperty() != null)) {
            clone.setProperty(this.getProperty().cloneObject());
        }
        if ((this.getLoadAction() != null)) {
            clone.setLoadAction(this.getLoadAction().cloneObject());
        }
        if ((this.getDoubleclickAction() != null)) {
            clone.setDoubleclickAction(this.getDoubleclickAction().cloneObject());
        }
        if ((this.getMenuButton() != null)) {
            clone.setMenuButton(this.getMenuButton().cloneObject());
        }
        if ((this.buttons != null)) {
            clone.buttons = this.buttons.cloneCollection();
        }
        if ((this.columns != null)) {
            clone.columns = this.columns.cloneCollection();
        }
        if ((this.getDependencySet() != null)) {
            clone.setDependencySet(this.getDependencySet().cloneObject());
        }
        if ((this.getIsTechnical() != null)) {
            clone.setIsTechnical(this.getIsTechnical().cloneObject());
        }
    }

    /**
     * Getter for the ButtonsJPA.
     *
     * @return the List<ListButtonExtension>.
     */
    List<ListButtonExtension> getButtonsJPA() {
        if ((this.buttons == null)) {
            this.buttons = new NabuccoListImpl<ListButtonExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<ListButtonExtension>) this.buttons).getDelegate();
    }

    /**
     * Setter for the ButtonsJPA.
     *
     * @param buttons the List<ListButtonExtension>.
     */
    void setButtonsJPA(List<ListButtonExtension> buttons) {
        if ((this.buttons == null)) {
            this.buttons = new NabuccoListImpl<ListButtonExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<ListButtonExtension>) this.buttons).setDelegate(buttons);
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
        propertyMap.putAll(PropertyCache.getInstance().retrieve(UiExtension.class).getPropertyMap());
        propertyMap.put(LABEL, PropertyDescriptorSupport.createDatatype(LABEL, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(TOOLTIP, PropertyDescriptorSupport.createDatatype(TOOLTIP, StringProperty.class, 5,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(PROPERTY, PropertyDescriptorSupport.createDatatype(PROPERTY, StringProperty.class, 6,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(LOADACTION, PropertyDescriptorSupport.createDatatype(LOADACTION, StringProperty.class, 7,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(DOUBLECLICKACTION, PropertyDescriptorSupport.createDatatype(DOUBLECLICKACTION,
                StringProperty.class, 8, PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(MENUBUTTON, PropertyDescriptorSupport.createDatatype(MENUBUTTON, MenuButtonExtension.class, 9,
                PROPERTY_CONSTRAINTS[5], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(BUTTONS, PropertyDescriptorSupport.createCollection(BUTTONS, ListButtonExtension.class, 10,
                PROPERTY_CONSTRAINTS[6], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(COLUMNS, PropertyDescriptorSupport.createCollection(COLUMNS, ColumnExtension.class, 11,
                PROPERTY_CONSTRAINTS[7], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(DEPENDENCYSET, PropertyDescriptorSupport.createDatatype(DEPENDENCYSET,
                DependencySetExtension.class, 12, PROPERTY_CONSTRAINTS[8], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ISTECHNICAL, PropertyDescriptorSupport.createDatatype(ISTECHNICAL, BooleanProperty.class, 13,
                PROPERTY_CONSTRAINTS[9], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties
                .add(super.createProperty(EditorRelationExtension.getPropertyDescriptor(LABEL), this.getLabel(), null));
        properties.add(super.createProperty(EditorRelationExtension.getPropertyDescriptor(TOOLTIP), this.getTooltip(),
                null));
        properties.add(super.createProperty(EditorRelationExtension.getPropertyDescriptor(PROPERTY),
                this.getProperty(), null));
        properties.add(super.createProperty(EditorRelationExtension.getPropertyDescriptor(LOADACTION),
                this.getLoadAction(), null));
        properties.add(super.createProperty(EditorRelationExtension.getPropertyDescriptor(DOUBLECLICKACTION),
                this.getDoubleclickAction(), null));
        properties.add(super.createProperty(EditorRelationExtension.getPropertyDescriptor(MENUBUTTON),
                this.getMenuButton(), null));
        properties
                .add(super.createProperty(EditorRelationExtension.getPropertyDescriptor(BUTTONS), this.buttons, null));
        properties
                .add(super.createProperty(EditorRelationExtension.getPropertyDescriptor(COLUMNS), this.columns, null));
        properties.add(super.createProperty(EditorRelationExtension.getPropertyDescriptor(DEPENDENCYSET),
                this.getDependencySet(), null));
        properties.add(super.createProperty(EditorRelationExtension.getPropertyDescriptor(ISTECHNICAL),
                this.getIsTechnical(), null));
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
        } else if ((property.getName().equals(PROPERTY) && (property.getType() == StringProperty.class))) {
            this.setProperty(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LOADACTION) && (property.getType() == StringProperty.class))) {
            this.setLoadAction(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DOUBLECLICKACTION) && (property.getType() == StringProperty.class))) {
            this.setDoubleclickAction(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MENUBUTTON) && (property.getType() == MenuButtonExtension.class))) {
            this.setMenuButton(((MenuButtonExtension) property.getInstance()));
            return true;
        } else if ((property.getName().equals(BUTTONS) && (property.getType() == ListButtonExtension.class))) {
            this.buttons = ((NabuccoList<ListButtonExtension>) property.getInstance());
            return true;
        } else if ((property.getName().equals(COLUMNS) && (property.getType() == ColumnExtension.class))) {
            this.columns = ((NabuccoList<ColumnExtension>) property.getInstance());
            return true;
        } else if ((property.getName().equals(DEPENDENCYSET) && (property.getType() == DependencySetExtension.class))) {
            this.setDependencySet(((DependencySetExtension) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ISTECHNICAL) && (property.getType() == BooleanProperty.class))) {
            this.setIsTechnical(((BooleanProperty) property.getInstance()));
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
        final EditorRelationExtension other = ((EditorRelationExtension) obj);
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
        if ((this.property == null)) {
            if ((other.property != null))
                return false;
        } else if ((!this.property.equals(other.property)))
            return false;
        if ((this.loadAction == null)) {
            if ((other.loadAction != null))
                return false;
        } else if ((!this.loadAction.equals(other.loadAction)))
            return false;
        if ((this.doubleclickAction == null)) {
            if ((other.doubleclickAction != null))
                return false;
        } else if ((!this.doubleclickAction.equals(other.doubleclickAction)))
            return false;
        if ((this.menuButton == null)) {
            if ((other.menuButton != null))
                return false;
        } else if ((!this.menuButton.equals(other.menuButton)))
            return false;
        if ((this.dependencySet == null)) {
            if ((other.dependencySet != null))
                return false;
        } else if ((!this.dependencySet.equals(other.dependencySet)))
            return false;
        if ((this.isTechnical == null)) {
            if ((other.isTechnical != null))
                return false;
        } else if ((!this.isTechnical.equals(other.isTechnical)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.label == null) ? 0 : this.label.hashCode()));
        result = ((PRIME * result) + ((this.tooltip == null) ? 0 : this.tooltip.hashCode()));
        result = ((PRIME * result) + ((this.property == null) ? 0 : this.property.hashCode()));
        result = ((PRIME * result) + ((this.loadAction == null) ? 0 : this.loadAction.hashCode()));
        result = ((PRIME * result) + ((this.doubleclickAction == null) ? 0 : this.doubleclickAction.hashCode()));
        result = ((PRIME * result) + ((this.menuButton == null) ? 0 : this.menuButton.hashCode()));
        result = ((PRIME * result) + ((this.dependencySet == null) ? 0 : this.dependencySet.hashCode()));
        result = ((PRIME * result) + ((this.isTechnical == null) ? 0 : this.isTechnical.hashCode()));
        return result;
    }

    @Override
    public EditorRelationExtension cloneObject() {
        EditorRelationExtension clone = new EditorRelationExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The Relation Label.
     *
     * @param label the StringProperty.
     */
    public void setLabel(StringProperty label) {
        this.label = label;
    }

    /**
     * The Relation Label.
     *
     * @return the StringProperty.
     */
    public StringProperty getLabel() {
        return this.label;
    }

    /**
     * The Relation Tooltip.
     *
     * @param tooltip the StringProperty.
     */
    public void setTooltip(StringProperty tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * The Relation Tooltip.
     *
     * @return the StringProperty.
     */
    public StringProperty getTooltip() {
        return this.tooltip;
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
     * The action to be used for loading of datatypes.
     *
     * @param loadAction the StringProperty.
     */
    public void setLoadAction(StringProperty loadAction) {
        this.loadAction = loadAction;
    }

    /**
     * The action to be used for loading of datatypes.
     *
     * @return the StringProperty.
     */
    public StringProperty getLoadAction() {
        return this.loadAction;
    }

    /**
     * The action to be used to open content items
     *
     * @param doubleclickAction the StringProperty.
     */
    public void setDoubleclickAction(StringProperty doubleclickAction) {
        this.doubleclickAction = doubleclickAction;
    }

    /**
     * The action to be used to open content items
     *
     * @return the StringProperty.
     */
    public StringProperty getDoubleclickAction() {
        return this.doubleclickAction;
    }

    /**
     * The Menu actions.
     *
     * @param menuButton the MenuButtonExtension.
     */
    public void setMenuButton(MenuButtonExtension menuButton) {
        this.menuButton = menuButton;
    }

    /**
     * The Menu actions.
     *
     * @return the MenuButtonExtension.
     */
    public MenuButtonExtension getMenuButton() {
        return this.menuButton;
    }

    /**
     * The Relation actions.
     *
     * @return the NabuccoList<ListButtonExtension>.
     */
    public NabuccoList<ListButtonExtension> getButtons() {
        if ((this.buttons == null)) {
            this.buttons = new NabuccoListImpl<ListButtonExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.buttons;
    }

    /**
     * The Relation columns.
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
     * Describes if the tab should be printed
     *
     * @param isTechnical the BooleanProperty.
     */
    public void setIsTechnical(BooleanProperty isTechnical) {
        this.isTechnical = isTechnical;
    }

    /**
     * Describes if the tab should be printed
     *
     * @return the BooleanProperty.
     */
    public BooleanProperty getIsTechnical() {
        return this.isTechnical;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(EditorRelationExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(EditorRelationExtension.class).getAllProperties();
    }
}
