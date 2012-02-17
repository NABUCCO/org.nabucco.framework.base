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
package org.nabucco.framework.base.facade.datatype.importing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * ImportContextEntry<p/>Context Entry Information for Deserialization of types<p/>
 *
 * @version 1.0
 * @author Silas Schwarz, PRODYNA AG, 2011-02-09
 */
public class ImportContextEntry extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m1,1;", "l0,n;u0,n;m1,1;", "l0,n;u0,n;m1,1;" };

    public static final String TYPENAME = "typeName";

    public static final String OLDID = "oldId";

    public static final String NEWID = "newId";

    /** Link to a ExportConfiguration */
    private Name typeName;

    /** old database id (in serialized form) */
    private Identifier oldId;

    /** new database id (if newly added to db) */
    private Identifier newId;

    /** Constructs a new ImportContextEntry instance. */
    public ImportContextEntry() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ImportContextEntry.
     */
    protected void cloneObject(ImportContextEntry clone) {
        super.cloneObject(clone);
        if ((this.getTypeName() != null)) {
            clone.setTypeName(this.getTypeName().cloneObject());
        }
        if ((this.getOldId() != null)) {
            clone.setOldId(this.getOldId().cloneObject());
        }
        if ((this.getNewId() != null)) {
            clone.setNewId(this.getNewId().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoDatatype.class).getPropertyMap());
        propertyMap.put(TYPENAME,
                PropertyDescriptorSupport.createBasetype(TYPENAME, Name.class, 3, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(OLDID,
                PropertyDescriptorSupport.createBasetype(OLDID, Identifier.class, 4, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(NEWID,
                PropertyDescriptorSupport.createBasetype(NEWID, Identifier.class, 5, PROPERTY_CONSTRAINTS[2], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ImportContextEntry.getPropertyDescriptor(TYPENAME), this.typeName, null));
        properties.add(super.createProperty(ImportContextEntry.getPropertyDescriptor(OLDID), this.oldId, null));
        properties.add(super.createProperty(ImportContextEntry.getPropertyDescriptor(NEWID), this.newId, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(TYPENAME) && (property.getType() == Name.class))) {
            this.setTypeName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(OLDID) && (property.getType() == Identifier.class))) {
            this.setOldId(((Identifier) property.getInstance()));
            return true;
        } else if ((property.getName().equals(NEWID) && (property.getType() == Identifier.class))) {
            this.setNewId(((Identifier) property.getInstance()));
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
        final ImportContextEntry other = ((ImportContextEntry) obj);
        if ((this.typeName == null)) {
            if ((other.typeName != null))
                return false;
        } else if ((!this.typeName.equals(other.typeName)))
            return false;
        if ((this.oldId == null)) {
            if ((other.oldId != null))
                return false;
        } else if ((!this.oldId.equals(other.oldId)))
            return false;
        if ((this.newId == null)) {
            if ((other.newId != null))
                return false;
        } else if ((!this.newId.equals(other.newId)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.typeName == null) ? 0 : this.typeName.hashCode()));
        result = ((PRIME * result) + ((this.oldId == null) ? 0 : this.oldId.hashCode()));
        result = ((PRIME * result) + ((this.newId == null) ? 0 : this.newId.hashCode()));
        return result;
    }

    @Override
    public ImportContextEntry cloneObject() {
        ImportContextEntry clone = new ImportContextEntry();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Link to a ExportConfiguration
     *
     * @return the Name.
     */
    public Name getTypeName() {
        return this.typeName;
    }

    /**
     * Link to a ExportConfiguration
     *
     * @param typeName the Name.
     */
    public void setTypeName(Name typeName) {
        this.typeName = typeName;
    }

    /**
     * Link to a ExportConfiguration
     *
     * @param typeName the String.
     */
    public void setTypeName(String typeName) {
        if ((this.typeName == null)) {
            if ((typeName == null)) {
                return;
            }
            this.typeName = new Name();
        }
        this.typeName.setValue(typeName);
    }

    /**
     * old database id (in serialized form)
     *
     * @return the Identifier.
     */
    public Identifier getOldId() {
        return this.oldId;
    }

    /**
     * old database id (in serialized form)
     *
     * @param oldId the Identifier.
     */
    public void setOldId(Identifier oldId) {
        this.oldId = oldId;
    }

    /**
     * old database id (in serialized form)
     *
     * @param oldId the Long.
     */
    public void setOldId(Long oldId) {
        if ((this.oldId == null)) {
            if ((oldId == null)) {
                return;
            }
            this.oldId = new Identifier();
        }
        this.oldId.setValue(oldId);
    }

    /**
     * new database id (if newly added to db)
     *
     * @return the Identifier.
     */
    public Identifier getNewId() {
        return this.newId;
    }

    /**
     * new database id (if newly added to db)
     *
     * @param newId the Identifier.
     */
    public void setNewId(Identifier newId) {
        this.newId = newId;
    }

    /**
     * new database id (if newly added to db)
     *
     * @param newId the Long.
     */
    public void setNewId(Long newId) {
        if ((this.newId == null)) {
            if ((newId == null)) {
                return;
            }
            this.newId = new Identifier();
        }
        this.newId.setValue(newId);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ImportContextEntry.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ImportContextEntry.class).getAllProperties();
    }
}
