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
package org.nabucco.framework.base.facade.datatype.extension;

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

/**
 * NabuccoExtensionComposite<p/>Composite node in the extension composite patten.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-21
 */
public abstract class NabuccoExtensionComposite extends NabuccoExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;" };

    public static final String RELATIONMAP = "relationMap";

    /** Extension Relation Map */
    private NabuccoList<ExtensionRelation> relationMap;

    /** Constructs a new NabuccoExtensionComposite instance. */
    public NabuccoExtensionComposite() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the NabuccoExtensionComposite.
     */
    protected void cloneObject(NabuccoExtensionComposite clone) {
        super.cloneObject(clone);
        if ((this.relationMap != null)) {
            clone.relationMap = this.relationMap.cloneCollection();
        }
    }

    /**
     * Getter for the RelationMapJPA.
     *
     * @return the List<ExtensionRelation>.
     */
    List<ExtensionRelation> getRelationMapJPA() {
        if ((this.relationMap == null)) {
            this.relationMap = new NabuccoListImpl<ExtensionRelation>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<ExtensionRelation>) this.relationMap).getDelegate();
    }

    /**
     * Setter for the RelationMapJPA.
     *
     * @param relationMap the List<ExtensionRelation>.
     */
    void setRelationMapJPA(List<ExtensionRelation> relationMap) {
        if ((this.relationMap == null)) {
            this.relationMap = new NabuccoListImpl<ExtensionRelation>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<ExtensionRelation>) this.relationMap).setDelegate(relationMap);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoExtension.class).getPropertyMap());
        propertyMap.put(RELATIONMAP, PropertyDescriptorSupport.createCollection(RELATIONMAP, ExtensionRelation.class,
                1, PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(NabuccoExtensionComposite.getPropertyDescriptor(RELATIONMAP),
                this.relationMap, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(RELATIONMAP) && (property.getType() == ExtensionRelation.class))) {
            this.relationMap = ((NabuccoList<ExtensionRelation>) property.getInstance());
            return true;
        }
        return false;
    }

    @Override
    public abstract NabuccoExtensionComposite cloneObject();

    /**
     * Extension Relation Map
     *
     * @return the NabuccoList<ExtensionRelation>.
     */
    public NabuccoList<ExtensionRelation> getRelationMap() {
        if ((this.relationMap == null)) {
            this.relationMap = new NabuccoListImpl<ExtensionRelation>(NabuccoCollectionState.INITIALIZED);
        }
        return this.relationMap;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(NabuccoExtensionComposite.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(NabuccoExtensionComposite.class).getAllProperties();
    }
}
