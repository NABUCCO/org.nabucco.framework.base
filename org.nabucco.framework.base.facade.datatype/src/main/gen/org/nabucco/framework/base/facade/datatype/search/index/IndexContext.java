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
package org.nabucco.framework.base.facade.datatype.search.index;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.ComponentRef;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeRef;
import org.nabucco.framework.base.facade.datatype.DatatypeSupport;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.TableRef;
import org.nabucco.framework.base.facade.datatype.geo.GeoLocation;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * IndexContext<p/>Information for indexing a fulltext document.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-03-01
 */
public class IndexContext extends DatatypeSupport implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l3,12;u0,n;m1,1;", "l3,255;u0,n;m1,1;", "l4,4;u0,n;m1,1;",
            "l3,255;u0,n;m1,1;", "l6,255;u0,n;m1,1;", "l0,n;u0,n;m1,1;", "m0,1;" };

    public static final String OWNER = "owner";

    public static final String INDEXNAME = "indexName";

    public static final String COMPONENT = "component";

    public static final String DATATYPE = "datatype";

    public static final String TABLE = "table";

    public static final String IDENTIFIER = "identifier";

    public static final String LOCATION = "location";

    /** The owner of the document. */
    private Owner owner;

    /** The name of the index (Datasource JNDI name) */
    private IndexName indexName;

    /** The reference key (prefix) of the datatypes component. */
    private ComponentRef component;

    /** The reference to the datatype (full qualified class name). */
    private DatatypeRef datatype;

    /** The reference to the table of the datatype. */
    private TableRef table;

    /** The identifier (primary key) of the datatypes object within the table. */
    private Identifier identifier;

    /** Optional geo information of the fulltext document. */
    private GeoLocation location;

    /** Constructs a new IndexContext instance. */
    public IndexContext() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the IndexContext.
     */
    protected void cloneObject(IndexContext clone) {
        super.cloneObject(clone);
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getIndexName() != null)) {
            clone.setIndexName(this.getIndexName().cloneObject());
        }
        if ((this.getComponent() != null)) {
            clone.setComponent(this.getComponent().cloneObject());
        }
        if ((this.getDatatype() != null)) {
            clone.setDatatype(this.getDatatype().cloneObject());
        }
        if ((this.getTable() != null)) {
            clone.setTable(this.getTable().cloneObject());
        }
        if ((this.getIdentifier() != null)) {
            clone.setIdentifier(this.getIdentifier().cloneObject());
        }
        if ((this.getLocation() != null)) {
            clone.setLocation(this.getLocation().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(OWNER,
                PropertyDescriptorSupport.createBasetype(OWNER, Owner.class, 0, PROPERTY_CONSTRAINTS[0], false));
        propertyMap
                .put(INDEXNAME, PropertyDescriptorSupport.createBasetype(INDEXNAME, IndexName.class, 1,
                        PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(COMPONENT, PropertyDescriptorSupport.createBasetype(COMPONENT, ComponentRef.class, 2,
                PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(DATATYPE, PropertyDescriptorSupport.createBasetype(DATATYPE, DatatypeRef.class, 3,
                PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(TABLE,
                PropertyDescriptorSupport.createBasetype(TABLE, TableRef.class, 4, PROPERTY_CONSTRAINTS[4], false));
        propertyMap.put(IDENTIFIER, PropertyDescriptorSupport.createBasetype(IDENTIFIER, Identifier.class, 5,
                PROPERTY_CONSTRAINTS[5], false));
        propertyMap.put(LOCATION, PropertyDescriptorSupport.createDatatype(LOCATION, GeoLocation.class, 6,
                PROPERTY_CONSTRAINTS[6], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(IndexContext.getPropertyDescriptor(OWNER), this.owner, null));
        properties.add(super.createProperty(IndexContext.getPropertyDescriptor(INDEXNAME), this.indexName, null));
        properties.add(super.createProperty(IndexContext.getPropertyDescriptor(COMPONENT), this.component, null));
        properties.add(super.createProperty(IndexContext.getPropertyDescriptor(DATATYPE), this.datatype, null));
        properties.add(super.createProperty(IndexContext.getPropertyDescriptor(TABLE), this.table, null));
        properties.add(super.createProperty(IndexContext.getPropertyDescriptor(IDENTIFIER), this.identifier, null));
        properties.add(super.createProperty(IndexContext.getPropertyDescriptor(LOCATION), this.getLocation(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(OWNER) && (property.getType() == Owner.class))) {
            this.setOwner(((Owner) property.getInstance()));
            return true;
        } else if ((property.getName().equals(INDEXNAME) && (property.getType() == IndexName.class))) {
            this.setIndexName(((IndexName) property.getInstance()));
            return true;
        } else if ((property.getName().equals(COMPONENT) && (property.getType() == ComponentRef.class))) {
            this.setComponent(((ComponentRef) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DATATYPE) && (property.getType() == DatatypeRef.class))) {
            this.setDatatype(((DatatypeRef) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TABLE) && (property.getType() == TableRef.class))) {
            this.setTable(((TableRef) property.getInstance()));
            return true;
        } else if ((property.getName().equals(IDENTIFIER) && (property.getType() == Identifier.class))) {
            this.setIdentifier(((Identifier) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LOCATION) && (property.getType() == GeoLocation.class))) {
            this.setLocation(((GeoLocation) property.getInstance()));
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
        final IndexContext other = ((IndexContext) obj);
        if ((this.owner == null)) {
            if ((other.owner != null))
                return false;
        } else if ((!this.owner.equals(other.owner)))
            return false;
        if ((this.indexName == null)) {
            if ((other.indexName != null))
                return false;
        } else if ((!this.indexName.equals(other.indexName)))
            return false;
        if ((this.component == null)) {
            if ((other.component != null))
                return false;
        } else if ((!this.component.equals(other.component)))
            return false;
        if ((this.datatype == null)) {
            if ((other.datatype != null))
                return false;
        } else if ((!this.datatype.equals(other.datatype)))
            return false;
        if ((this.table == null)) {
            if ((other.table != null))
                return false;
        } else if ((!this.table.equals(other.table)))
            return false;
        if ((this.identifier == null)) {
            if ((other.identifier != null))
                return false;
        } else if ((!this.identifier.equals(other.identifier)))
            return false;
        if ((this.location == null)) {
            if ((other.location != null))
                return false;
        } else if ((!this.location.equals(other.location)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.indexName == null) ? 0 : this.indexName.hashCode()));
        result = ((PRIME * result) + ((this.component == null) ? 0 : this.component.hashCode()));
        result = ((PRIME * result) + ((this.datatype == null) ? 0 : this.datatype.hashCode()));
        result = ((PRIME * result) + ((this.table == null) ? 0 : this.table.hashCode()));
        result = ((PRIME * result) + ((this.identifier == null) ? 0 : this.identifier.hashCode()));
        result = ((PRIME * result) + ((this.location == null) ? 0 : this.location.hashCode()));
        return result;
    }

    @Override
    public IndexContext cloneObject() {
        IndexContext clone = new IndexContext();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The owner of the document.
     *
     * @return the Owner.
     */
    public Owner getOwner() {
        return this.owner;
    }

    /**
     * The owner of the document.
     *
     * @param owner the Owner.
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * The owner of the document.
     *
     * @param owner the String.
     */
    public void setOwner(String owner) {
        if ((this.owner == null)) {
            if ((owner == null)) {
                return;
            }
            this.owner = new Owner();
        }
        this.owner.setValue(owner);
    }

    /**
     * The name of the index (Datasource JNDI name)
     *
     * @return the IndexName.
     */
    public IndexName getIndexName() {
        return this.indexName;
    }

    /**
     * The name of the index (Datasource JNDI name)
     *
     * @param indexName the IndexName.
     */
    public void setIndexName(IndexName indexName) {
        this.indexName = indexName;
    }

    /**
     * The name of the index (Datasource JNDI name)
     *
     * @param indexName the String.
     */
    public void setIndexName(String indexName) {
        if ((this.indexName == null)) {
            if ((indexName == null)) {
                return;
            }
            this.indexName = new IndexName();
        }
        this.indexName.setValue(indexName);
    }

    /**
     * The reference key (prefix) of the datatypes component.
     *
     * @return the ComponentRef.
     */
    public ComponentRef getComponent() {
        return this.component;
    }

    /**
     * The reference key (prefix) of the datatypes component.
     *
     * @param component the ComponentRef.
     */
    public void setComponent(ComponentRef component) {
        this.component = component;
    }

    /**
     * The reference key (prefix) of the datatypes component.
     *
     * @param component the String.
     */
    public void setComponent(String component) {
        if ((this.component == null)) {
            if ((component == null)) {
                return;
            }
            this.component = new ComponentRef();
        }
        this.component.setValue(component);
    }

    /**
     * The reference to the datatype (full qualified class name).
     *
     * @return the DatatypeRef.
     */
    public DatatypeRef getDatatype() {
        return this.datatype;
    }

    /**
     * The reference to the datatype (full qualified class name).
     *
     * @param datatype the DatatypeRef.
     */
    public void setDatatype(DatatypeRef datatype) {
        this.datatype = datatype;
    }

    /**
     * The reference to the datatype (full qualified class name).
     *
     * @param datatype the String.
     */
    public void setDatatype(String datatype) {
        if ((this.datatype == null)) {
            if ((datatype == null)) {
                return;
            }
            this.datatype = new DatatypeRef();
        }
        this.datatype.setValue(datatype);
    }

    /**
     * The reference to the table of the datatype.
     *
     * @return the TableRef.
     */
    public TableRef getTable() {
        return this.table;
    }

    /**
     * The reference to the table of the datatype.
     *
     * @param table the TableRef.
     */
    public void setTable(TableRef table) {
        this.table = table;
    }

    /**
     * The reference to the table of the datatype.
     *
     * @param table the String.
     */
    public void setTable(String table) {
        if ((this.table == null)) {
            if ((table == null)) {
                return;
            }
            this.table = new TableRef();
        }
        this.table.setValue(table);
    }

    /**
     * The identifier (primary key) of the datatypes object within the table.
     *
     * @return the Identifier.
     */
    public Identifier getIdentifier() {
        return this.identifier;
    }

    /**
     * The identifier (primary key) of the datatypes object within the table.
     *
     * @param identifier the Identifier.
     */
    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    /**
     * The identifier (primary key) of the datatypes object within the table.
     *
     * @param identifier the Long.
     */
    public void setIdentifier(Long identifier) {
        if ((this.identifier == null)) {
            if ((identifier == null)) {
                return;
            }
            this.identifier = new Identifier();
        }
        this.identifier.setValue(identifier);
    }

    /**
     * Optional geo information of the fulltext document.
     *
     * @param location the GeoLocation.
     */
    public void setLocation(GeoLocation location) {
        this.location = location;
    }

    /**
     * Optional geo information of the fulltext document.
     *
     * @return the GeoLocation.
     */
    public GeoLocation getLocation() {
        return this.location;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(IndexContext.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(IndexContext.class).getAllProperties();
    }
}
