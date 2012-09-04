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
import org.nabucco.framework.base.facade.datatype.extension.schema.aspect.PointcutExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * PointcutListExtension<p/>Aspect extension for a list of pointcuts.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-09-21
 */
public class PointcutListExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,n;" };

    public static final String POINTCUTEXTENSIONLIST = "pointcutExtensionList";

    /** The Pointcut Extensions */
    private NabuccoList<PointcutExtension> pointcutExtensionList;

    /** Constructs a new PointcutListExtension instance. */
    public PointcutListExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the PointcutListExtension.
     */
    protected void cloneObject(PointcutListExtension clone) {
        super.cloneObject(clone);
        if ((this.pointcutExtensionList != null)) {
            clone.pointcutExtensionList = this.pointcutExtensionList.cloneCollection();
        }
    }

    /**
     * Getter for the PointcutExtensionListJPA.
     *
     * @return the List<PointcutExtension>.
     */
    List<PointcutExtension> getPointcutExtensionListJPA() {
        if ((this.pointcutExtensionList == null)) {
            this.pointcutExtensionList = new NabuccoListImpl<PointcutExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<PointcutExtension>) this.pointcutExtensionList).getDelegate();
    }

    /**
     * Setter for the PointcutExtensionListJPA.
     *
     * @param pointcutExtensionList the List<PointcutExtension>.
     */
    void setPointcutExtensionListJPA(List<PointcutExtension> pointcutExtensionList) {
        if ((this.pointcutExtensionList == null)) {
            this.pointcutExtensionList = new NabuccoListImpl<PointcutExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<PointcutExtension>) this.pointcutExtensionList).setDelegate(pointcutExtensionList);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoExtensionComposite.class).getPropertyMap());
        propertyMap.put(POINTCUTEXTENSIONLIST, PropertyDescriptorSupport.createCollection(POINTCUTEXTENSIONLIST,
                PointcutExtension.class, 2, PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(PointcutListExtension.getPropertyDescriptor(POINTCUTEXTENSIONLIST),
                this.pointcutExtensionList, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(POINTCUTEXTENSIONLIST) && (property.getType() == PointcutExtension.class))) {
            this.pointcutExtensionList = ((NabuccoList<PointcutExtension>) property.getInstance());
            return true;
        }
        return false;
    }

    @Override
    public PointcutListExtension cloneObject() {
        PointcutListExtension clone = new PointcutListExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The Pointcut Extensions
     *
     * @return the NabuccoList<PointcutExtension>.
     */
    public NabuccoList<PointcutExtension> getPointcutExtensionList() {
        if ((this.pointcutExtensionList == null)) {
            this.pointcutExtensionList = new NabuccoListImpl<PointcutExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.pointcutExtensionList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(PointcutListExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(PointcutListExtension.class).getAllProperties();
    }
}
