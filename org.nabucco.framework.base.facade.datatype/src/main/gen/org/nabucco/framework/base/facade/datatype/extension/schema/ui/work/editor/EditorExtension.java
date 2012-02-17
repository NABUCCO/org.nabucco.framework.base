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
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.EditorButtonExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.EditorRelationExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.EditorTabExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * EditorExtension<p/>NABUCCO User Interface Editor extension.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-07-28
 */
public class EditorExtension extends WorkItemExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;", "m0,n;", "m0,n;" };

    public static final String BUTTONS = "buttons";

    public static final String TABS = "tabs";

    public static final String RELATIONS = "relations";

    /** The Editor Actions. */
    private NabuccoList<EditorButtonExtension> buttons;

    /** The Editor Tabs. */
    private NabuccoList<EditorTabExtension> tabs;

    /** The Editor Relations. */
    private NabuccoList<EditorRelationExtension> relations;

    /** Constructs a new EditorExtension instance. */
    public EditorExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the EditorExtension.
     */
    protected void cloneObject(EditorExtension clone) {
        super.cloneObject(clone);
        if ((this.buttons != null)) {
            clone.buttons = this.buttons.cloneCollection();
        }
        if ((this.tabs != null)) {
            clone.tabs = this.tabs.cloneCollection();
        }
        if ((this.relations != null)) {
            clone.relations = this.relations.cloneCollection();
        }
    }

    /**
     * Getter for the ButtonsJPA.
     *
     * @return the List<EditorButtonExtension>.
     */
    List<EditorButtonExtension> getButtonsJPA() {
        if ((this.buttons == null)) {
            this.buttons = new NabuccoListImpl<EditorButtonExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<EditorButtonExtension>) this.buttons).getDelegate();
    }

    /**
     * Setter for the ButtonsJPA.
     *
     * @param buttons the List<EditorButtonExtension>.
     */
    void setButtonsJPA(List<EditorButtonExtension> buttons) {
        if ((this.buttons == null)) {
            this.buttons = new NabuccoListImpl<EditorButtonExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<EditorButtonExtension>) this.buttons).setDelegate(buttons);
    }

    /**
     * Getter for the TabsJPA.
     *
     * @return the List<EditorTabExtension>.
     */
    List<EditorTabExtension> getTabsJPA() {
        if ((this.tabs == null)) {
            this.tabs = new NabuccoListImpl<EditorTabExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<EditorTabExtension>) this.tabs).getDelegate();
    }

    /**
     * Setter for the TabsJPA.
     *
     * @param tabs the List<EditorTabExtension>.
     */
    void setTabsJPA(List<EditorTabExtension> tabs) {
        if ((this.tabs == null)) {
            this.tabs = new NabuccoListImpl<EditorTabExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<EditorTabExtension>) this.tabs).setDelegate(tabs);
    }

    /**
     * Getter for the RelationsJPA.
     *
     * @return the List<EditorRelationExtension>.
     */
    List<EditorRelationExtension> getRelationsJPA() {
        if ((this.relations == null)) {
            this.relations = new NabuccoListImpl<EditorRelationExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<EditorRelationExtension>) this.relations).getDelegate();
    }

    /**
     * Setter for the RelationsJPA.
     *
     * @param relations the List<EditorRelationExtension>.
     */
    void setRelationsJPA(List<EditorRelationExtension> relations) {
        if ((this.relations == null)) {
            this.relations = new NabuccoListImpl<EditorRelationExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<EditorRelationExtension>) this.relations).setDelegate(relations);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(WorkItemExtension.class).getPropertyMap());
        propertyMap.put(BUTTONS, PropertyDescriptorSupport.createCollection(BUTTONS, EditorButtonExtension.class, 11,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(TABS, PropertyDescriptorSupport.createCollection(TABS, EditorTabExtension.class, 12,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(RELATIONS, PropertyDescriptorSupport.createCollection(RELATIONS, EditorRelationExtension.class,
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
        properties.add(super.createProperty(EditorExtension.getPropertyDescriptor(BUTTONS), this.buttons, null));
        properties.add(super.createProperty(EditorExtension.getPropertyDescriptor(TABS), this.tabs, null));
        properties.add(super.createProperty(EditorExtension.getPropertyDescriptor(RELATIONS), this.relations, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(BUTTONS) && (property.getType() == EditorButtonExtension.class))) {
            this.buttons = ((NabuccoList<EditorButtonExtension>) property.getInstance());
            return true;
        } else if ((property.getName().equals(TABS) && (property.getType() == EditorTabExtension.class))) {
            this.tabs = ((NabuccoList<EditorTabExtension>) property.getInstance());
            return true;
        } else if ((property.getName().equals(RELATIONS) && (property.getType() == EditorRelationExtension.class))) {
            this.relations = ((NabuccoList<EditorRelationExtension>) property.getInstance());
            return true;
        }
        return false;
    }

    @Override
    public EditorExtension cloneObject() {
        EditorExtension clone = new EditorExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The Editor Actions.
     *
     * @return the NabuccoList<EditorButtonExtension>.
     */
    public NabuccoList<EditorButtonExtension> getButtons() {
        if ((this.buttons == null)) {
            this.buttons = new NabuccoListImpl<EditorButtonExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.buttons;
    }

    /**
     * The Editor Tabs.
     *
     * @return the NabuccoList<EditorTabExtension>.
     */
    public NabuccoList<EditorTabExtension> getTabs() {
        if ((this.tabs == null)) {
            this.tabs = new NabuccoListImpl<EditorTabExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.tabs;
    }

    /**
     * The Editor Relations.
     *
     * @return the NabuccoList<EditorRelationExtension>.
     */
    public NabuccoList<EditorRelationExtension> getRelations() {
        if ((this.relations == null)) {
            this.relations = new NabuccoListImpl<EditorRelationExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.relations;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(EditorExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(EditorExtension.class).getAllProperties();
    }
}
