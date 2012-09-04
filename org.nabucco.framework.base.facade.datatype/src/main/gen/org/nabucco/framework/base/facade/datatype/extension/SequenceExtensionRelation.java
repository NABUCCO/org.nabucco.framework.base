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
 * SequenceExtensionRelation<p/>Relation for one to many extension elements.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-21
 */
public class SequenceExtensionRelation extends ExtensionRelation implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;" };

    public static final String EXTENSION = "extension";

    /** Points to the list of extensions. */
    private NabuccoList<NabuccoExtension> extension;

    /** Constructs a new SequenceExtensionRelation instance. */
    public SequenceExtensionRelation() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the SequenceExtensionRelation.
     */
    protected void cloneObject(SequenceExtensionRelation clone) {
        super.cloneObject(clone);
        if ((this.extension != null)) {
            clone.extension = this.extension.cloneCollection();
        }
    }

    /**
     * Getter for the ExtensionJPA.
     *
     * @return the List<NabuccoExtension>.
     */
    List<NabuccoExtension> getExtensionJPA() {
        if ((this.extension == null)) {
            this.extension = new NabuccoListImpl<NabuccoExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<NabuccoExtension>) this.extension).getDelegate();
    }

    /**
     * Setter for the ExtensionJPA.
     *
     * @param extension the List<NabuccoExtension>.
     */
    void setExtensionJPA(List<NabuccoExtension> extension) {
        if ((this.extension == null)) {
            this.extension = new NabuccoListImpl<NabuccoExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<NabuccoExtension>) this.extension).setDelegate(extension);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(ExtensionRelation.class).getPropertyMap());
        propertyMap.put(EXTENSION, PropertyDescriptorSupport.createCollection(EXTENSION, NabuccoExtension.class, 1,
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
        properties.add(super.createProperty(SequenceExtensionRelation.getPropertyDescriptor(EXTENSION), this.extension,
                null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(EXTENSION) && (property.getType() == NabuccoExtension.class))) {
            this.extension = ((NabuccoList<NabuccoExtension>) property.getInstance());
            return true;
        }
        return false;
    }

    @Override
    public SequenceExtensionRelation cloneObject() {
        SequenceExtensionRelation clone = new SequenceExtensionRelation();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Points to the list of extensions.
     *
     * @return the NabuccoList<NabuccoExtension>.
     */
    public NabuccoList<NabuccoExtension> getExtension() {
        if ((this.extension == null)) {
            this.extension = new NabuccoListImpl<NabuccoExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.extension;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(SequenceExtensionRelation.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(SequenceExtensionRelation.class).getAllProperties();
    }
}
