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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.ListButtonExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * MenuButtonExtension<p/>NABUCCO Menu Button Extension interface<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-02-02
 */
public class MenuButtonExtension extends UiExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;" };

    public static final String BUTTONS = "buttons";

    private NabuccoList<ListButtonExtension> buttons;

    /** Constructs a new MenuButtonExtension instance. */
    public MenuButtonExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the MenuButtonExtension.
     */
    protected void cloneObject(MenuButtonExtension clone) {
        super.cloneObject(clone);
        if ((this.buttons != null)) {
            clone.buttons = this.buttons.cloneCollection();
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
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(UiExtension.class).getPropertyMap());
        propertyMap.put(BUTTONS, PropertyDescriptorSupport.createCollection(BUTTONS, ListButtonExtension.class, 4,
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
        properties.add(super.createProperty(MenuButtonExtension.getPropertyDescriptor(BUTTONS), this.buttons, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(BUTTONS) && (property.getType() == ListButtonExtension.class))) {
            this.buttons = ((NabuccoList<ListButtonExtension>) property.getInstance());
            return true;
        }
        return false;
    }

    @Override
    public MenuButtonExtension cloneObject() {
        MenuButtonExtension clone = new MenuButtonExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Missing description at method getButtons.
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
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(MenuButtonExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(MenuButtonExtension.class).getAllProperties();
    }
}
