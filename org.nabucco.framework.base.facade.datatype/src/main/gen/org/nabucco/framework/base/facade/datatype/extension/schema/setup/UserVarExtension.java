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
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * UserVarExtension<p/>Schema definition configuration for User Variables.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-04-08
 */
public class UserVarExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;" };

    public static final String USERVARLIST = "userVarList";

    /** The list of user variables. */
    private NabuccoList<UserVariableExtension> userVarList;

    /** Constructs a new UserVarExtension instance. */
    public UserVarExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the UserVarExtension.
     */
    protected void cloneObject(UserVarExtension clone) {
        super.cloneObject(clone);
        if ((this.userVarList != null)) {
            clone.userVarList = this.userVarList.cloneCollection();
        }
    }

    /**
     * Getter for the UserVarListJPA.
     *
     * @return the List<UserVariableExtension>.
     */
    List<UserVariableExtension> getUserVarListJPA() {
        if ((this.userVarList == null)) {
            this.userVarList = new NabuccoListImpl<UserVariableExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<UserVariableExtension>) this.userVarList).getDelegate();
    }

    /**
     * Setter for the UserVarListJPA.
     *
     * @param userVarList the List<UserVariableExtension>.
     */
    void setUserVarListJPA(List<UserVariableExtension> userVarList) {
        if ((this.userVarList == null)) {
            this.userVarList = new NabuccoListImpl<UserVariableExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<UserVariableExtension>) this.userVarList).setDelegate(userVarList);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoExtensionComposite.class).getPropertyMap());
        propertyMap.put(USERVARLIST, PropertyDescriptorSupport.createCollection(USERVARLIST,
                UserVariableExtension.class, 2, PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties
                .add(super.createProperty(UserVarExtension.getPropertyDescriptor(USERVARLIST), this.userVarList, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(USERVARLIST) && (property.getType() == UserVariableExtension.class))) {
            this.userVarList = ((NabuccoList<UserVariableExtension>) property.getInstance());
            return true;
        }
        return false;
    }

    @Override
    public UserVarExtension cloneObject() {
        UserVarExtension clone = new UserVarExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The list of user variables.
     *
     * @return the NabuccoList<UserVariableExtension>.
     */
    public NabuccoList<UserVariableExtension> getUserVarList() {
        if ((this.userVarList == null)) {
            this.userVarList = new NabuccoListImpl<UserVariableExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.userVarList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(UserVarExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(UserVarExtension.class).getAllProperties();
    }
}
