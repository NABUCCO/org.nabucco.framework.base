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
package org.nabucco.framework.base.facade.datatype.extension.schema.management;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.property.ClassProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * AdapterManagementExtension<p/>Configuration for adapter monitoring.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-06-19
 */
public class AdapterManagementExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,n;" };

    public static final String ADAPTERINTERFACE = "adapterInterface";

    public static final String JNDINAMES = "jndiNames";

    /** The adapter interface. */
    private ClassProperty adapterInterface;

    /** The configured jndi names. */
    private NabuccoList<StringProperty> jndiNames;

    /** Constructs a new AdapterManagementExtension instance. */
    public AdapterManagementExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the AdapterManagementExtension.
     */
    protected void cloneObject(AdapterManagementExtension clone) {
        super.cloneObject(clone);
        if ((this.getAdapterInterface() != null)) {
            clone.setAdapterInterface(this.getAdapterInterface().cloneObject());
        }
        if ((this.jndiNames != null)) {
            clone.jndiNames = this.jndiNames.cloneCollection();
        }
    }

    /**
     * Getter for the JndiNamesJPA.
     *
     * @return the List<StringProperty>.
     */
    List<StringProperty> getJndiNamesJPA() {
        if ((this.jndiNames == null)) {
            this.jndiNames = new NabuccoListImpl<StringProperty>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<StringProperty>) this.jndiNames).getDelegate();
    }

    /**
     * Setter for the JndiNamesJPA.
     *
     * @param jndiNames the List<StringProperty>.
     */
    void setJndiNamesJPA(List<StringProperty> jndiNames) {
        if ((this.jndiNames == null)) {
            this.jndiNames = new NabuccoListImpl<StringProperty>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<StringProperty>) this.jndiNames).setDelegate(jndiNames);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoExtensionComposite.class).getPropertyMap());
        propertyMap.put(ADAPTERINTERFACE, PropertyDescriptorSupport.createDatatype(ADAPTERINTERFACE,
                ClassProperty.class, 2, PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(JNDINAMES, PropertyDescriptorSupport.createCollection(JNDINAMES, StringProperty.class, 3,
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
        properties.add(super.createProperty(AdapterManagementExtension.getPropertyDescriptor(ADAPTERINTERFACE),
                this.getAdapterInterface(), null));
        properties.add(super.createProperty(AdapterManagementExtension.getPropertyDescriptor(JNDINAMES),
                this.jndiNames, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(ADAPTERINTERFACE) && (property.getType() == ClassProperty.class))) {
            this.setAdapterInterface(((ClassProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(JNDINAMES) && (property.getType() == StringProperty.class))) {
            this.jndiNames = ((NabuccoList<StringProperty>) property.getInstance());
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
        final AdapterManagementExtension other = ((AdapterManagementExtension) obj);
        if ((this.adapterInterface == null)) {
            if ((other.adapterInterface != null))
                return false;
        } else if ((!this.adapterInterface.equals(other.adapterInterface)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.adapterInterface == null) ? 0 : this.adapterInterface.hashCode()));
        return result;
    }

    @Override
    public AdapterManagementExtension cloneObject() {
        AdapterManagementExtension clone = new AdapterManagementExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The adapter interface.
     *
     * @param adapterInterface the ClassProperty.
     */
    public void setAdapterInterface(ClassProperty adapterInterface) {
        this.adapterInterface = adapterInterface;
    }

    /**
     * The adapter interface.
     *
     * @return the ClassProperty.
     */
    public ClassProperty getAdapterInterface() {
        return this.adapterInterface;
    }

    /**
     * The configured jndi names.
     *
     * @return the NabuccoList<StringProperty>.
     */
    public NabuccoList<StringProperty> getJndiNames() {
        if ((this.jndiNames == null)) {
            this.jndiNames = new NabuccoListImpl<StringProperty>(NabuccoCollectionState.INITIALIZED);
        }
        return this.jndiNames;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(AdapterManagementExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(AdapterManagementExtension.class).getAllProperties();
    }
}
