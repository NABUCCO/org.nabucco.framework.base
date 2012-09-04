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
package org.nabucco.framework.base.facade.datatype.template.datastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.template.datastructure.TemplateDSInstanceElement;

/**
 * TemplateDSInstanceGroup<p/>The instance of the composite datatastructure<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-07-05
 */
public class TemplateDSInstanceGroup extends TemplateDSInstanceElement implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;" };

    public static final String CHILDRENLIST = "childrenList";

    /** The children list of the element group */
    private NabuccoList<TemplateDSInstanceElement> childrenList;

    /** Constructs a new TemplateDSInstanceGroup instance. */
    public TemplateDSInstanceGroup() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TemplateDSInstanceGroup.
     */
    protected void cloneObject(TemplateDSInstanceGroup clone) {
        super.cloneObject(clone);
        if ((this.childrenList != null)) {
            clone.childrenList = this.childrenList.cloneCollection();
        }
    }

    /**
     * Getter for the ChildrenListJPA.
     *
     * @return the List<TemplateDSInstanceElement>.
     */
    List<TemplateDSInstanceElement> getChildrenListJPA() {
        if ((this.childrenList == null)) {
            this.childrenList = new NabuccoListImpl<TemplateDSInstanceElement>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<TemplateDSInstanceElement>) this.childrenList).getDelegate();
    }

    /**
     * Setter for the ChildrenListJPA.
     *
     * @param childrenList the List<TemplateDSInstanceElement>.
     */
    void setChildrenListJPA(List<TemplateDSInstanceElement> childrenList) {
        if ((this.childrenList == null)) {
            this.childrenList = new NabuccoListImpl<TemplateDSInstanceElement>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<TemplateDSInstanceElement>) this.childrenList).setDelegate(childrenList);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(TemplateDSInstanceElement.class).getPropertyMap());
        propertyMap.put(CHILDRENLIST, PropertyDescriptorSupport
                .createCollection(CHILDRENLIST, TemplateDSInstanceElement.class, 4, PROPERTY_CONSTRAINTS[0], false,
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
        properties.add(super.createProperty(TemplateDSInstanceGroup.getPropertyDescriptor(CHILDRENLIST),
                this.childrenList, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(CHILDRENLIST) && (property.getType() == TemplateDSInstanceElement.class))) {
            this.childrenList = ((NabuccoList<TemplateDSInstanceElement>) property.getInstance());
            return true;
        }
        return false;
    }

    @Override
    public TemplateDSInstanceGroup cloneObject() {
        TemplateDSInstanceGroup clone = new TemplateDSInstanceGroup();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The children list of the element group
     *
     * @return the NabuccoList<TemplateDSInstanceElement>.
     */
    public NabuccoList<TemplateDSInstanceElement> getChildrenList() {
        if ((this.childrenList == null)) {
            this.childrenList = new NabuccoListImpl<TemplateDSInstanceElement>(NabuccoCollectionState.INITIALIZED);
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
        return PropertyCache.getInstance().retrieve(TemplateDSInstanceGroup.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TemplateDSInstanceGroup.class).getAllProperties();
    }
}
