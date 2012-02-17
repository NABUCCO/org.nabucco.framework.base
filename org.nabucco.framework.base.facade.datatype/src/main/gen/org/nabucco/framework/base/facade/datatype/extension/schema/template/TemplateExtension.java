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
package org.nabucco.framework.base.facade.datatype.extension.schema.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.TemplateStructureItem;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * TemplateExtension<p/>Configuration Extension for Templates.<p/>
 *
 * @version 1.2
 * @author Holger Librenz, PRODYNA AG, 2011-03-10
 */
public class TemplateExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;" };

    public static final String ITEMLIST = "itemList";

    /** The list of template items. */
    private NabuccoList<TemplateStructureItem> itemList;

    /** Constructs a new TemplateExtension instance. */
    public TemplateExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TemplateExtension.
     */
    protected void cloneObject(TemplateExtension clone) {
        super.cloneObject(clone);
        if ((this.itemList != null)) {
            clone.itemList = this.itemList.cloneCollection();
        }
    }

    /**
     * Getter for the ItemListJPA.
     *
     * @return the List<TemplateStructureItem>.
     */
    List<TemplateStructureItem> getItemListJPA() {
        if ((this.itemList == null)) {
            this.itemList = new NabuccoListImpl<TemplateStructureItem>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<TemplateStructureItem>) this.itemList).getDelegate();
    }

    /**
     * Setter for the ItemListJPA.
     *
     * @param itemList the List<TemplateStructureItem>.
     */
    void setItemListJPA(List<TemplateStructureItem> itemList) {
        if ((this.itemList == null)) {
            this.itemList = new NabuccoListImpl<TemplateStructureItem>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<TemplateStructureItem>) this.itemList).setDelegate(itemList);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoExtensionComposite.class).getPropertyMap());
        propertyMap.put(ITEMLIST, PropertyDescriptorSupport.createCollection(ITEMLIST, TemplateStructureItem.class, 2,
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
        properties.add(super.createProperty(TemplateExtension.getPropertyDescriptor(ITEMLIST), this.itemList, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(ITEMLIST) && (property.getType() == TemplateStructureItem.class))) {
            this.itemList = ((NabuccoList<TemplateStructureItem>) property.getInstance());
            return true;
        }
        return false;
    }

    @Override
    public TemplateExtension cloneObject() {
        TemplateExtension clone = new TemplateExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The list of template items.
     *
     * @return the NabuccoList<TemplateStructureItem>.
     */
    public NabuccoList<TemplateStructureItem> getItemList() {
        if ((this.itemList == null)) {
            this.itemList = new NabuccoListImpl<TemplateStructureItem>(NabuccoCollectionState.INITIALIZED);
        }
        return this.itemList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TemplateExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TemplateExtension.class).getAllProperties();
    }
}
