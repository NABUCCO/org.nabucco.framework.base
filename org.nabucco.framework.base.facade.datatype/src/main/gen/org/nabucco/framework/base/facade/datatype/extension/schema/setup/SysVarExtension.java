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
package org.nabucco.framework.base.facade.datatype.extension.schema.setup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.SystemVariableExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * SysVarExtension<p/>Schema definition configuration for System Variables.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-04-08
 */
public class SysVarExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;" };

    public static final String SYSVARLIST = "sysVarList";

    /** The list of system variables. */
    private NabuccoList<SystemVariableExtension> sysVarList;

    /** Constructs a new SysVarExtension instance. */
    public SysVarExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the SysVarExtension.
     */
    protected void cloneObject(SysVarExtension clone) {
        super.cloneObject(clone);
        if ((this.sysVarList != null)) {
            clone.sysVarList = this.sysVarList.cloneCollection();
        }
    }

    /**
     * Getter for the SysVarListJPA.
     *
     * @return the List<SystemVariableExtension>.
     */
    List<SystemVariableExtension> getSysVarListJPA() {
        if ((this.sysVarList == null)) {
            this.sysVarList = new NabuccoListImpl<SystemVariableExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<SystemVariableExtension>) this.sysVarList).getDelegate();
    }

    /**
     * Setter for the SysVarListJPA.
     *
     * @param sysVarList the List<SystemVariableExtension>.
     */
    void setSysVarListJPA(List<SystemVariableExtension> sysVarList) {
        if ((this.sysVarList == null)) {
            this.sysVarList = new NabuccoListImpl<SystemVariableExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<SystemVariableExtension>) this.sysVarList).setDelegate(sysVarList);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoExtensionComposite.class).getPropertyMap());
        propertyMap.put(SYSVARLIST, PropertyDescriptorSupport.createCollection(SYSVARLIST,
                SystemVariableExtension.class, 2, PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(SysVarExtension.getPropertyDescriptor(SYSVARLIST), this.sysVarList, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(SYSVARLIST) && (property.getType() == SystemVariableExtension.class))) {
            this.sysVarList = ((NabuccoList<SystemVariableExtension>) property.getInstance());
            return true;
        }
        return false;
    }

    @Override
    public SysVarExtension cloneObject() {
        SysVarExtension clone = new SysVarExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The list of system variables.
     *
     * @return the NabuccoList<SystemVariableExtension>.
     */
    public NabuccoList<SystemVariableExtension> getSysVarList() {
        if ((this.sysVarList == null)) {
            this.sysVarList = new NabuccoListImpl<SystemVariableExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.sysVarList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(SysVarExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(SysVarExtension.class).getAllProperties();
    }
}
