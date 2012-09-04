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
package org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure.TemplateDSElement;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * TemplateDSElementGroup<p/>Template Datastructure Element Group.<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-07-03
 */
public class TemplateDSElementGroup extends TemplateDSElement implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;" };

    public static final String CHILDRENLIST = "childrenList";

    /** The children list of the element group */
    private NabuccoList<TemplateDSElement> childrenList;

    /** Constructs a new TemplateDSElementGroup instance. */
    public TemplateDSElementGroup() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TemplateDSElementGroup.
     */
    protected void cloneObject(TemplateDSElementGroup clone) {
        super.cloneObject(clone);
        if ((this.childrenList != null)) {
            clone.childrenList = this.childrenList.cloneCollection();
        }
    }

    /**
     * Getter for the ChildrenListJPA.
     *
     * @return the List<TemplateDSElement>.
     */
    List<TemplateDSElement> getChildrenListJPA() {
        if ((this.childrenList == null)) {
            this.childrenList = new NabuccoListImpl<TemplateDSElement>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<TemplateDSElement>) this.childrenList).getDelegate();
    }

    /**
     * Setter for the ChildrenListJPA.
     *
     * @param childrenList the List<TemplateDSElement>.
     */
    void setChildrenListJPA(List<TemplateDSElement> childrenList) {
        if ((this.childrenList == null)) {
            this.childrenList = new NabuccoListImpl<TemplateDSElement>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<TemplateDSElement>) this.childrenList).setDelegate(childrenList);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(TemplateDSElement.class).getPropertyMap());
        propertyMap.put(CHILDRENLIST, PropertyDescriptorSupport.createCollection(CHILDRENLIST, TemplateDSElement.class,
                3, PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(TemplateDSElementGroup.getPropertyDescriptor(CHILDRENLIST),
                this.childrenList, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(CHILDRENLIST) && (property.getType() == TemplateDSElement.class))) {
            this.childrenList = ((NabuccoList<TemplateDSElement>) property.getInstance());
            return true;
        }
        return false;
    }

    @Override
    public TemplateDSElementGroup cloneObject() {
        TemplateDSElementGroup clone = new TemplateDSElementGroup();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The children list of the element group
     *
     * @return the NabuccoList<TemplateDSElement>.
     */
    public NabuccoList<TemplateDSElement> getChildrenList() {
        if ((this.childrenList == null)) {
            this.childrenList = new NabuccoListImpl<TemplateDSElement>(NabuccoCollectionState.INITIALIZED);
        }
        return this.childrenList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TemplateDSElementGroup.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TemplateDSElementGroup.class).getAllProperties();
    }
}
