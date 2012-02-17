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
package org.nabucco.framework.base.facade.datatype.security.acl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.security.acl.AccessControlEntry;

/**
 * AccessControlList<p/>A list of permissions attached to an object.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-06-28
 */
public class AccessControlList extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m1,1;", "m0,n;" };

    public static final String NAME = "name";

    public static final String ENTRYLIST = "entryList";

    /** Name of the access control list. */
    private Name name;

    /** Access control list entries. */
    private NabuccoList<AccessControlEntry> entryList;

    /** Constructs a new AccessControlList instance. */
    public AccessControlList() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the AccessControlList.
     */
    protected void cloneObject(AccessControlList clone) {
        super.cloneObject(clone);
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.entryList != null)) {
            clone.entryList = this.entryList.cloneCollection();
        }
    }

    /**
     * Getter for the EntryListJPA.
     *
     * @return the List<AccessControlEntry>.
     */
    List<AccessControlEntry> getEntryListJPA() {
        if ((this.entryList == null)) {
            this.entryList = new NabuccoListImpl<AccessControlEntry>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<AccessControlEntry>) this.entryList).getDelegate();
    }

    /**
     * Setter for the EntryListJPA.
     *
     * @param entryList the List<AccessControlEntry>.
     */
    void setEntryListJPA(List<AccessControlEntry> entryList) {
        if ((this.entryList == null)) {
            this.entryList = new NabuccoListImpl<AccessControlEntry>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<AccessControlEntry>) this.entryList).setDelegate(entryList);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoDatatype.class).getPropertyMap());
        propertyMap.put(NAME,
                PropertyDescriptorSupport.createBasetype(NAME, Name.class, 3, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(ENTRYLIST, PropertyDescriptorSupport.createCollection(ENTRYLIST, AccessControlEntry.class, 4,
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
        properties.add(super.createProperty(AccessControlList.getPropertyDescriptor(NAME), this.name, null));
        properties.add(super.createProperty(AccessControlList.getPropertyDescriptor(ENTRYLIST), this.entryList, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(NAME) && (property.getType() == Name.class))) {
            this.setName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ENTRYLIST) && (property.getType() == AccessControlEntry.class))) {
            this.entryList = ((NabuccoList<AccessControlEntry>) property.getInstance());
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
        final AccessControlList other = ((AccessControlList) obj);
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        return result;
    }

    @Override
    public AccessControlList cloneObject() {
        AccessControlList clone = new AccessControlList();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Name of the access control list.
     *
     * @return the Name.
     */
    public Name getName() {
        return this.name;
    }

    /**
     * Name of the access control list.
     *
     * @param name the Name.
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * Name of the access control list.
     *
     * @param name the String.
     */
    public void setName(String name) {
        if ((this.name == null)) {
            if ((name == null)) {
                return;
            }
            this.name = new Name();
        }
        this.name.setValue(name);
    }

    /**
     * Access control list entries.
     *
     * @return the NabuccoList<AccessControlEntry>.
     */
    public NabuccoList<AccessControlEntry> getEntryList() {
        if ((this.entryList == null)) {
            this.entryList = new NabuccoListImpl<AccessControlEntry>(NabuccoCollectionState.INITIALIZED);
        }
        return this.entryList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(AccessControlList.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(AccessControlList.class).getAllProperties();
    }
}
