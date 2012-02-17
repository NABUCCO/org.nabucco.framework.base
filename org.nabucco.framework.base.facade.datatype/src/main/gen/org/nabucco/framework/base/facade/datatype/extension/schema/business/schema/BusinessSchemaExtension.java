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
package org.nabucco.framework.base.facade.datatype.extension.schema.business.schema;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.schema.business.schema.BusinessSchemaAssociation;
import org.nabucco.framework.base.facade.datatype.extension.schema.business.schema.BusinessSchemaObject;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * BusinessSchemaExtension<p/>Configuration for business schama extensions.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-06-26
 */
public class BusinessSchemaExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,n;", "m1,n;" };

    public static final String OBJECTS = "objects";

    public static final String ASSOCIATIONS = "associations";

    /** The configured business objects. */
    private NabuccoList<BusinessSchemaObject> objects;

    /** The configured associations of business objects. */
    private NabuccoList<BusinessSchemaAssociation> associations;

    /** Constructs a new BusinessSchemaExtension instance. */
    public BusinessSchemaExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the BusinessSchemaExtension.
     */
    protected void cloneObject(BusinessSchemaExtension clone) {
        super.cloneObject(clone);
        if ((this.objects != null)) {
            clone.objects = this.objects.cloneCollection();
        }
        if ((this.associations != null)) {
            clone.associations = this.associations.cloneCollection();
        }
    }

    /**
     * Getter for the ObjectsJPA.
     *
     * @return the List<BusinessSchemaObject>.
     */
    List<BusinessSchemaObject> getObjectsJPA() {
        if ((this.objects == null)) {
            this.objects = new NabuccoListImpl<BusinessSchemaObject>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<BusinessSchemaObject>) this.objects).getDelegate();
    }

    /**
     * Setter for the ObjectsJPA.
     *
     * @param objects the List<BusinessSchemaObject>.
     */
    void setObjectsJPA(List<BusinessSchemaObject> objects) {
        if ((this.objects == null)) {
            this.objects = new NabuccoListImpl<BusinessSchemaObject>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<BusinessSchemaObject>) this.objects).setDelegate(objects);
    }

    /**
     * Getter for the AssociationsJPA.
     *
     * @return the List<BusinessSchemaAssociation>.
     */
    List<BusinessSchemaAssociation> getAssociationsJPA() {
        if ((this.associations == null)) {
            this.associations = new NabuccoListImpl<BusinessSchemaAssociation>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<BusinessSchemaAssociation>) this.associations).getDelegate();
    }

    /**
     * Setter for the AssociationsJPA.
     *
     * @param associations the List<BusinessSchemaAssociation>.
     */
    void setAssociationsJPA(List<BusinessSchemaAssociation> associations) {
        if ((this.associations == null)) {
            this.associations = new NabuccoListImpl<BusinessSchemaAssociation>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<BusinessSchemaAssociation>) this.associations).setDelegate(associations);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoExtensionComposite.class).getPropertyMap());
        propertyMap.put(OBJECTS, PropertyDescriptorSupport.createCollection(OBJECTS, BusinessSchemaObject.class, 2,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ASSOCIATIONS, PropertyDescriptorSupport
                .createCollection(ASSOCIATIONS, BusinessSchemaAssociation.class, 3, PROPERTY_CONSTRAINTS[1], false,
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
        properties
                .add(super.createProperty(BusinessSchemaExtension.getPropertyDescriptor(OBJECTS), this.objects, null));
        properties.add(super.createProperty(BusinessSchemaExtension.getPropertyDescriptor(ASSOCIATIONS),
                this.associations, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(OBJECTS) && (property.getType() == BusinessSchemaObject.class))) {
            this.objects = ((NabuccoList<BusinessSchemaObject>) property.getInstance());
            return true;
        } else if ((property.getName().equals(ASSOCIATIONS) && (property.getType() == BusinessSchemaAssociation.class))) {
            this.associations = ((NabuccoList<BusinessSchemaAssociation>) property.getInstance());
            return true;
        }
        return false;
    }

    @Override
    public BusinessSchemaExtension cloneObject() {
        BusinessSchemaExtension clone = new BusinessSchemaExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The configured business objects.
     *
     * @return the NabuccoList<BusinessSchemaObject>.
     */
    public NabuccoList<BusinessSchemaObject> getObjects() {
        if ((this.objects == null)) {
            this.objects = new NabuccoListImpl<BusinessSchemaObject>(NabuccoCollectionState.INITIALIZED);
        }
        return this.objects;
    }

    /**
     * The configured associations of business objects.
     *
     * @return the NabuccoList<BusinessSchemaAssociation>.
     */
    public NabuccoList<BusinessSchemaAssociation> getAssociations() {
        if ((this.associations == null)) {
            this.associations = new NabuccoListImpl<BusinessSchemaAssociation>(NabuccoCollectionState.INITIALIZED);
        }
        return this.associations;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(BusinessSchemaExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(BusinessSchemaExtension.class).getAllProperties();
    }
}
