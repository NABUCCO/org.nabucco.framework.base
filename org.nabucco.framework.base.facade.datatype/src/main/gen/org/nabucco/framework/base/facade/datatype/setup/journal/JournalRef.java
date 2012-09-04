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
package org.nabucco.framework.base.facade.datatype.setup.journal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeSupport;
import org.nabucco.framework.base.facade.datatype.FullQualifiedClassName;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.date.Timestamp;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * JournalRef<p/>One entry of a journal.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-03-21
 */
public class JournalRef extends DatatypeSupport implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final Name TITLE_DEFAULT = new Name("");

    private static final String[] PROPERTY_CONSTRAINTS = { "l3,1000;u0,n;m1,1;", "l0,n;u0,n;m1,1;", "l0,n;u0,n;m1,1;",
            "l0,255;u0,n;m1,1;" };

    public static final String CLASSNAME = "className";

    public static final String REFID = "refId";

    public static final String TIMESTAMP = "timestamp";

    public static final String TITLE = "title";

    /** The fqn of the references datatype. */
    private FullQualifiedClassName className;

    /** The primary key of the datatype. */
    private Identifier refId;

    /** The timestamp of the journal. */
    private Timestamp timestamp;

    /** The title initended to be displayed with a client */
    private Name title;

    /** Constructs a new JournalRef instance. */
    public JournalRef() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
        title = TITLE_DEFAULT;
    }

    /**
     * CloneObject.
     *
     * @param clone the JournalRef.
     */
    protected void cloneObject(JournalRef clone) {
        super.cloneObject(clone);
        if ((this.getClassName() != null)) {
            clone.setClassName(this.getClassName().cloneObject());
        }
        if ((this.getRefId() != null)) {
            clone.setRefId(this.getRefId().cloneObject());
        }
        if ((this.getTimestamp() != null)) {
            clone.setTimestamp(this.getTimestamp().cloneObject());
        }
        if ((this.getTitle() != null)) {
            clone.setTitle(this.getTitle().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(CLASSNAME, PropertyDescriptorSupport.createBasetype(CLASSNAME, FullQualifiedClassName.class, 0,
                PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(REFID,
                PropertyDescriptorSupport.createBasetype(REFID, Identifier.class, 1, PROPERTY_CONSTRAINTS[1], false));
        propertyMap
                .put(TIMESTAMP, PropertyDescriptorSupport.createBasetype(TIMESTAMP, Timestamp.class, 2,
                        PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(TITLE,
                PropertyDescriptorSupport.createBasetype(TITLE, Name.class, 3, PROPERTY_CONSTRAINTS[3], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(JournalRef.getPropertyDescriptor(CLASSNAME), this.className, null));
        properties.add(super.createProperty(JournalRef.getPropertyDescriptor(REFID), this.refId, null));
        properties.add(super.createProperty(JournalRef.getPropertyDescriptor(TIMESTAMP), this.timestamp, null));
        properties.add(super.createProperty(JournalRef.getPropertyDescriptor(TITLE), this.title, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(CLASSNAME) && (property.getType() == FullQualifiedClassName.class))) {
            this.setClassName(((FullQualifiedClassName) property.getInstance()));
            return true;
        } else if ((property.getName().equals(REFID) && (property.getType() == Identifier.class))) {
            this.setRefId(((Identifier) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TIMESTAMP) && (property.getType() == Timestamp.class))) {
            this.setTimestamp(((Timestamp) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TITLE) && (property.getType() == Name.class))) {
            this.setTitle(((Name) property.getInstance()));
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
        final JournalRef other = ((JournalRef) obj);
        if ((this.className == null)) {
            if ((other.className != null))
                return false;
        } else if ((!this.className.equals(other.className)))
            return false;
        if ((this.refId == null)) {
            if ((other.refId != null))
                return false;
        } else if ((!this.refId.equals(other.refId)))
            return false;
        if ((this.timestamp == null)) {
            if ((other.timestamp != null))
                return false;
        } else if ((!this.timestamp.equals(other.timestamp)))
            return false;
        if ((this.title == null)) {
            if ((other.title != null))
                return false;
        } else if ((!this.title.equals(other.title)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.className == null) ? 0 : this.className.hashCode()));
        result = ((PRIME * result) + ((this.refId == null) ? 0 : this.refId.hashCode()));
        result = ((PRIME * result) + ((this.timestamp == null) ? 0 : this.timestamp.hashCode()));
        result = ((PRIME * result) + ((this.title == null) ? 0 : this.title.hashCode()));
        return result;
    }

    @Override
    public JournalRef cloneObject() {
        JournalRef clone = new JournalRef();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The fqn of the references datatype.
     *
     * @return the FullQualifiedClassName.
     */
    public FullQualifiedClassName getClassName() {
        return this.className;
    }

    /**
     * The fqn of the references datatype.
     *
     * @param className the FullQualifiedClassName.
     */
    public void setClassName(FullQualifiedClassName className) {
        this.className = className;
    }

    /**
     * The fqn of the references datatype.
     *
     * @param className the String.
     */
    public void setClassName(String className) {
        if ((this.className == null)) {
            if ((className == null)) {
                return;
            }
            this.className = new FullQualifiedClassName();
        }
        this.className.setValue(className);
    }

    /**
     * The primary key of the datatype.
     *
     * @return the Identifier.
     */
    public Identifier getRefId() {
        return this.refId;
    }

    /**
     * The primary key of the datatype.
     *
     * @param refId the Identifier.
     */
    public void setRefId(Identifier refId) {
        this.refId = refId;
    }

    /**
     * The primary key of the datatype.
     *
     * @param refId the Long.
     */
    public void setRefId(Long refId) {
        if ((this.refId == null)) {
            if ((refId == null)) {
                return;
            }
            this.refId = new Identifier();
        }
        this.refId.setValue(refId);
    }

    /**
     * The timestamp of the journal.
     *
     * @return the Timestamp.
     */
    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    /**
     * The timestamp of the journal.
     *
     * @param timestamp the Timestamp.
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * The timestamp of the journal.
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
     * The title initended to be displayed with a client
     *
     * @return the Name.
     */
    public Name getTitle() {
        return this.title;
    }

    /**
     * The title initended to be displayed with a client
     *
     * @param title the Name.
     */
    public void setTitle(Name title) {
        this.title = title;
    }

    /**
     * The title initended to be displayed with a client
     *
     * @param title the String.
     */
    public void setTitle(String title) {
        if ((this.title == null)) {
            if ((title == null)) {
                return;
            }
            this.title = new Name();
        }
        this.title.setValue(title);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(JournalRef.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(JournalRef.class).getAllProperties();
    }
}
