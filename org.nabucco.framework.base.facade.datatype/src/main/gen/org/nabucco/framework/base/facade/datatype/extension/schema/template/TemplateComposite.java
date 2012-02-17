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
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.TemplateStructureItem;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * TemplateComposite<p/>Base for template structure composite elements<p/>
 *
 * @version 1.0
 * @author Holger Librenz, PRODYNA AG, 2011-03-10
 */
public abstract class TemplateComposite extends TemplateStructureItem implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m0,n;" };

    public static final String STYLE = "style";

    public static final String ITEMLIST = "itemList";

    /** The style for the in-laying elements */
    protected StringProperty style;

    /** Template elements are compositible */
    protected NabuccoList<TemplateStructureItem> itemList;

    /** Constructs a new TemplateComposite instance. */
    public TemplateComposite() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TemplateComposite.
     */
    protected void cloneObject(TemplateComposite clone) {
        super.cloneObject(clone);
        if ((this.getStyle() != null)) {
            clone.setStyle(this.getStyle().cloneObject());
        }
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
        propertyMap.putAll(PropertyCache.getInstance().retrieve(TemplateStructureItem.class).getPropertyMap());
        propertyMap.put(STYLE, PropertyDescriptorSupport.createDatatype(STYLE, StringProperty.class, 4,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ITEMLIST, PropertyDescriptorSupport.createCollection(ITEMLIST, TemplateStructureItem.class, 5,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(TemplateComposite.getPropertyDescriptor(STYLE), this.getStyle(), null));
        properties.add(super.createProperty(TemplateComposite.getPropertyDescriptor(ITEMLIST), this.itemList, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(STYLE) && (property.getType() == StringProperty.class))) {
            this.setStyle(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ITEMLIST) && (property.getType() == TemplateStructureItem.class))) {
            this.itemList = ((NabuccoList<TemplateStructureItem>) property.getInstance());
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
        final TemplateComposite other = ((TemplateComposite) obj);
        if ((this.style == null)) {
            if ((other.style != null))
                return false;
        } else if ((!this.style.equals(other.style)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.style == null) ? 0 : this.style.hashCode()));
        return result;
    }

    @Override
    public abstract TemplateComposite cloneObject();

    /**
     * The style for the in-laying elements
     *
     * @param style the StringProperty.
     */
    public void setStyle(StringProperty style) {
        this.style = style;
    }

    /**
     * The style for the in-laying elements
     *
     * @return the StringProperty.
     */
    public StringProperty getStyle() {
        return this.style;
    }

    /**
     * Template elements are compositible
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
        return PropertyCache.getInstance().retrieve(TemplateComposite.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TemplateComposite.class).getAllProperties();
    }
}
