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
package org.nabucco.framework.base.facade.datatype.extension.schema.aspect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * AspectConfigExtension<p/>Aspect extension for pointcuts.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-03-11
 */
public class AspectConfigExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;" };

    public static final String ASPECTLIST = "aspectList";

    /** The List of Aspects. */
    private NabuccoList<AspectExtension> aspectList;

    /** Constructs a new AspectConfigExtension instance. */
    public AspectConfigExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the AspectConfigExtension.
     */
    protected void cloneObject(AspectConfigExtension clone) {
        super.cloneObject(clone);
        if ((this.aspectList != null)) {
            clone.aspectList = this.aspectList.cloneCollection();
        }
    }

    /**
     * Getter for the AspectListJPA.
     *
     * @return the List<AspectExtension>.
     */
    List<AspectExtension> getAspectListJPA() {
        if ((this.aspectList == null)) {
            this.aspectList = new NabuccoListImpl<AspectExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<AspectExtension>) this.aspectList).getDelegate();
    }

    /**
     * Setter for the AspectListJPA.
     *
     * @param aspectList the List<AspectExtension>.
     */
    void setAspectListJPA(List<AspectExtension> aspectList) {
        if ((this.aspectList == null)) {
            this.aspectList = new NabuccoListImpl<AspectExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<AspectExtension>) this.aspectList).setDelegate(aspectList);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoExtensionComposite.class).getPropertyMap());
        propertyMap.put(ASPECTLIST, PropertyDescriptorSupport.createCollection(ASPECTLIST, AspectExtension.class, 2,
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
        properties.add(super.createProperty(AspectConfigExtension.getPropertyDescriptor(ASPECTLIST), this.aspectList,
                null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(ASPECTLIST) && (property.getType() == AspectExtension.class))) {
            this.aspectList = ((NabuccoList<AspectExtension>) property.getInstance());
            return true;
        }
        return false;
    }

    @Override
    public AspectConfigExtension cloneObject() {
        AspectConfigExtension clone = new AspectConfigExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The List of Aspects.
     *
     * @return the NabuccoList<AspectExtension>.
     */
    public NabuccoList<AspectExtension> getAspectList() {
        if ((this.aspectList == null)) {
            this.aspectList = new NabuccoListImpl<AspectExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.aspectList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(AspectConfigExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(AspectConfigExtension.class).getAllProperties();
    }
}
