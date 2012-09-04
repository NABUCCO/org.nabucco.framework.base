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
package org.nabucco.framework.base.facade.datatype.history;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.date.Timestamp;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.xml.XmlDocument;

/**
 * DatatypeHistory<p/>History of the datatype<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-04-19
 */
public abstract class DatatypeHistory extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m1,1;", "l0,n;u0,n;m1,1;", "l0,255;u0,n;m1,1;",
            "l0,n;u0,n;m1,1;", "l0,2147483647;u0,n;m0,1;" };

    public static final String TYPE = "type";

    public static final String DATATYPEID = "datatypeId";

    public static final String AUTHOR = "author";

    public static final String TIMESTAMP = "timestamp";

    public static final String XML = "xml";

    /** The dype of the historized datatype (e.g Scheduling, Staffing etc) */
    private Name type;

    /** The id of the referenced datatype */
    private Identifier datatypeId;

    /** The name of the user who triggered the history event */
    private Name author;

    /** The time of the datatype event */
    private Timestamp timestamp;

    /** The serialized datatype. */
    private XmlDocument xml;

    /** Constructs a new DatatypeHistory instance. */
    public DatatypeHistory() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the DatatypeHistory.
     */
    protected void cloneObject(DatatypeHistory clone) {
        super.cloneObject(clone);
        if ((this.getType() != null)) {
            clone.setType(this.getType().cloneObject());
        }
        if ((this.getDatatypeId() != null)) {
            clone.setDatatypeId(this.getDatatypeId().cloneObject());
        }
        if ((this.getAuthor() != null)) {
            clone.setAuthor(this.getAuthor().cloneObject());
        }
        if ((this.getTimestamp() != null)) {
            clone.setTimestamp(this.getTimestamp().cloneObject());
        }
        if ((this.getXml() != null)) {
            clone.setXml(this.getXml().cloneObject());
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
        propertyMap.put(TYPE,
                PropertyDescriptorSupport.createBasetype(TYPE, Name.class, 3, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(DATATYPEID, PropertyDescriptorSupport.createBasetype(DATATYPEID, Identifier.class, 4,
                PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(AUTHOR,
                PropertyDescriptorSupport.createBasetype(AUTHOR, Name.class, 5, PROPERTY_CONSTRAINTS[2], false));
        propertyMap
                .put(TIMESTAMP, PropertyDescriptorSupport.createBasetype(TIMESTAMP, Timestamp.class, 6,
                        PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(XML,
                PropertyDescriptorSupport.createBasetype(XML, XmlDocument.class, 7, PROPERTY_CONSTRAINTS[4], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(DatatypeHistory.getPropertyDescriptor(TYPE), this.type, null));
        properties.add(super.createProperty(DatatypeHistory.getPropertyDescriptor(DATATYPEID), this.datatypeId, null));
        properties.add(super.createProperty(DatatypeHistory.getPropertyDescriptor(AUTHOR), this.author, null));
        properties.add(super.createProperty(DatatypeHistory.getPropertyDescriptor(TIMESTAMP), this.timestamp, null));
        properties.add(super.createProperty(DatatypeHistory.getPropertyDescriptor(XML), this.xml, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(TYPE) && (property.getType() == Name.class))) {
            this.setType(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DATATYPEID) && (property.getType() == Identifier.class))) {
            this.setDatatypeId(((Identifier) property.getInstance()));
            return true;
        } else if ((property.getName().equals(AUTHOR) && (property.getType() == Name.class))) {
            this.setAuthor(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TIMESTAMP) && (property.getType() == Timestamp.class))) {
            this.setTimestamp(((Timestamp) property.getInstance()));
            return true;
        } else if ((property.getName().equals(XML) && (property.getType() == XmlDocument.class))) {
            this.setXml(((XmlDocument) property.getInstance()));
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
        final DatatypeHistory other = ((DatatypeHistory) obj);
        if ((this.type == null)) {
            if ((other.type != null))
                return false;
        } else if ((!this.type.equals(other.type)))
            return false;
        if ((this.datatypeId == null)) {
            if ((other.datatypeId != null))
                return false;
        } else if ((!this.datatypeId.equals(other.datatypeId)))
            return false;
        if ((this.author == null)) {
            if ((other.author != null))
                return false;
        } else if ((!this.author.equals(other.author)))
            return false;
        if ((this.timestamp == null)) {
            if ((other.timestamp != null))
                return false;
        } else if ((!this.timestamp.equals(other.timestamp)))
            return false;
        if ((this.xml == null)) {
            if ((other.xml != null))
                return false;
        } else if ((!this.xml.equals(other.xml)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.type == null) ? 0 : this.type.hashCode()));
        result = ((PRIME * result) + ((this.datatypeId == null) ? 0 : this.datatypeId.hashCode()));
        result = ((PRIME * result) + ((this.author == null) ? 0 : this.author.hashCode()));
        result = ((PRIME * result) + ((this.timestamp == null) ? 0 : this.timestamp.hashCode()));
        result = ((PRIME * result) + ((this.xml == null) ? 0 : this.xml.hashCode()));
        return result;
    }

    @Override
    public abstract DatatypeHistory cloneObject();

    /**
     * The dype of the historized datatype (e.g Scheduling, Staffing etc)
     *
     * @return the Name.
     */
    public Name getType() {
        return this.type;
    }

    /**
     * The dype of the historized datatype (e.g Scheduling, Staffing etc)
     *
     * @param type the Name.
     */
    public void setType(Name type) {
        this.type = type;
    }

    /**
     * The dype of the historized datatype (e.g Scheduling, Staffing etc)
     *
     * @param type the String.
     */
    public void setType(String type) {
        if ((this.type == null)) {
            if ((type == null)) {
                return;
            }
            this.type = new Name();
        }
        this.type.setValue(type);
    }

    /**
     * The id of the referenced datatype
     *
     * @return the Identifier.
     */
    public Identifier getDatatypeId() {
        return this.datatypeId;
    }

    /**
     * The id of the referenced datatype
     *
     * @param datatypeId the Identifier.
     */
    public void setDatatypeId(Identifier datatypeId) {
        this.datatypeId = datatypeId;
    }

    /**
     * The id of the referenced datatype
     *
     * @param datatypeId the Long.
     */
    public void setDatatypeId(Long datatypeId) {
        if ((this.datatypeId == null)) {
            if ((datatypeId == null)) {
                return;
            }
            this.datatypeId = new Identifier();
        }
        this.datatypeId.setValue(datatypeId);
    }

    /**
     * The name of the user who triggered the history event
     *
     * @return the Name.
     */
    public Name getAuthor() {
        return this.author;
    }

    /**
     * The name of the user who triggered the history event
     *
     * @param author the Name.
     */
    public void setAuthor(Name author) {
        this.author = author;
    }

    /**
     * The name of the user who triggered the history event
     *
     * @param author the String.
     */
    public void setAuthor(String author) {
        if ((this.author == null)) {
            if ((author == null)) {
                return;
            }
            this.author = new Name();
        }
        this.author.setValue(author);
    }

    /**
     * The time of the datatype event
     *
     * @return the Timestamp.
     */
    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    /**
     * The time of the datatype event
     *
     * @param timestamp the Timestamp.
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * The time of the datatype event
     *
     * @param timestamp the Long.
     */
    public void setTimestamp(Long timestamp) {
        if ((this.timestamp == null)) {
            if ((timestamp == null)) {
                return;
            }
            this.timestamp = new Timestamp();
        }
        this.timestamp.setValue(timestamp);
    }

    /**
     * The serialized datatype.
     *
     * @return the XmlDocument.
     */
    public XmlDocument getXml() {
        return this.xml;
    }

    /**
     * The serialized datatype.
     *
     * @param xml the XmlDocument.
     */
    public void setXml(XmlDocument xml) {
        this.xml = xml;
    }

    /**
     * The serialized datatype.
     *
     * @param xml the String.
     */
    public void setXml(String xml) {
        if ((this.xml == null)) {
            if ((xml == null)) {
                return;
            }
            this.xml = new XmlDocument();
        }
        this.xml.setValue(xml);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(DatatypeHistory.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(DatatypeHistory.class).getAllProperties();
    }
}
