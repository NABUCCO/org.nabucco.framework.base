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
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.BulkEditorButtonExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.MenuButtonExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.bulkeditor.BulkEditorColumnsExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * BulkEditorExtension<p/>NABUCCO User Interface Bulkeditor extension.<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2011-07-28
 */
public class BulkEditorExtension extends WorkItemExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,1;", "m0,n;", "m1,1;" };

    public static final String MENUBUTTON = "menuButton";

    public static final String BUTTONS = "buttons";

    public static final String COLUMNS = "columns";

    /** The Menu actions. */
    private MenuButtonExtension menuButton;

    /** The Relation actions. */
    private NabuccoList<BulkEditorButtonExtension> buttons;

    /** The Relation columns. */
    private BulkEditorColumnsExtension columns;

    /** Constructs a new BulkEditorExtension instance. */
    public BulkEditorExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the BulkEditorExtension.
     */
    protected void cloneObject(BulkEditorExtension clone) {
        super.cloneObject(clone);
        if ((this.getMenuButton() != null)) {
            clone.setMenuButton(this.getMenuButton().cloneObject());
        }
        if ((this.buttons != null)) {
            clone.buttons = this.buttons.cloneCollection();
        }
        if ((this.getColumns() != null)) {
            clone.setColumns(this.getColumns().cloneObject());
        }
    }

    /**
     * Getter for the ButtonsJPA.
     *
     * @return the List<BulkEditorButtonExtension>.
     */
    List<BulkEditorButtonExtension> getButtonsJPA() {
        if ((this.buttons == null)) {
            this.buttons = new NabuccoListImpl<BulkEditorButtonExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<BulkEditorButtonExtension>) this.buttons).getDelegate();
    }

    /**
     * Setter for the ButtonsJPA.
     *
     * @param buttons the List<BulkEditorButtonExtension>.
     */
    void setButtonsJPA(List<BulkEditorButtonExtension> buttons) {
        if ((this.buttons == null)) {
            this.buttons = new NabuccoListImpl<BulkEditorButtonExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<BulkEditorButtonExtension>) this.buttons).setDelegate(buttons);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(WorkItemExtension.class).getPropertyMap());
        propertyMap.put(MENUBUTTON, PropertyDescriptorSupport.createDatatype(MENUBUTTON, MenuButtonExtension.class, 11,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(BUTTONS, PropertyDescriptorSupport.createCollection(BUTTONS, BulkEditorButtonExtension.class,
                12, PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(COLUMNS, PropertyDescriptorSupport.createDatatype(COLUMNS, BulkEditorColumnsExtension.class,
                13, PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(BulkEditorExtension.getPropertyDescriptor(MENUBUTTON),
                this.getMenuButton(), null));
        properties.add(super.createProperty(BulkEditorExtension.getPropertyDescriptor(BUTTONS), this.buttons, null));
        properties
                .add(super.createProperty(BulkEditorExtension.getPropertyDescriptor(COLUMNS), this.getColumns(), null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(MENUBUTTON) && (property.getType() == MenuButtonExtension.class))) {
            this.setMenuButton(((MenuButtonExtension) property.getInstance()));
            return true;
        } else if ((property.getName().equals(BUTTONS) && (property.getType() == BulkEditorButtonExtension.class))) {
            this.buttons = ((NabuccoList<BulkEditorButtonExtension>) property.getInstance());
            return true;
        } else if ((property.getName().equals(COLUMNS) && (property.getType() == BulkEditorColumnsExtension.class))) {
            this.setColumns(((BulkEditorColumnsExtension) property.getInstance()));
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
        final BulkEditorExtension other = ((BulkEditorExtension) obj);
        if ((this.menuButton == null)) {
            if ((other.menuButton != null))
                return false;
        } else if ((!this.menuButton.equals(other.menuButton)))
            return false;
        if ((this.columns == null)) {
            if ((other.columns != null))
                return false;
        } else if ((!this.columns.equals(other.columns)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.menuButton == null) ? 0 : this.menuButton.hashCode()));
        result = ((PRIME * result) + ((this.columns == null) ? 0 : this.columns.hashCode()));
        return result;
    }

    @Override
    public BulkEditorExtension cloneObject() {
        BulkEditorExtension clone = new BulkEditorExtension();
        this.cloneObject(clone);
        return clone;
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
     * @return the NabuccoList<BulkEditorButtonExtension>.
     */
    public NabuccoList<BulkEditorButtonExtension> getButtons() {
        if ((this.buttons == null)) {
            this.buttons = new NabuccoListImpl<BulkEditorButtonExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.buttons;
    }

    /**
     * The Relation columns.
     *
     * @param columns the BulkEditorColumnsExtension.
     */
    public void setColumns(BulkEditorColumnsExtension columns) {
        this.columns = columns;
    }

    /**
     * The Relation columns.
     *
     * @return the BulkEditorColumnsExtension.
     */
    public BulkEditorColumnsExtension getColumns() {
        return this.columns;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(BulkEditorExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(BulkEditorExtension.class).getAllProperties();
    }
}
