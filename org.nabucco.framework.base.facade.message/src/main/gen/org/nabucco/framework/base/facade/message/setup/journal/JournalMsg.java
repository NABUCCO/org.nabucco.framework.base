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
package org.nabucco.framework.base.facade.message.setup.journal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.FullQualifiedClassName;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.date.Timestamp;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;

/**
 * JournalMsg<p/>Message for journaling a business object.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-03-01
 */
public class JournalMsg extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;u0,n;m1,1;", "l3,1000;u0,n;m1,1;", "l0,n;u0,n;m1,1;" };

    public static final String TIMESTAMP = "timestamp";

    public static final String CLASSNAME = "className";

    public static final String DATATYPEID = "datatypeId";

    /** The timestamp of the journal. */
    private Timestamp timestamp;

    /** The name of the datatype. */
    private FullQualifiedClassName className;

    /** The identifier of the datatype. */
    private Identifier datatypeId;

    /** Constructs a new JournalMsg instance. */
    public JournalMsg() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap
                .put(TIMESTAMP, PropertyDescriptorSupport.createBasetype(TIMESTAMP, Timestamp.class, 0,
                        PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(CLASSNAME, PropertyDescriptorSupport.createBasetype(CLASSNAME, FullQualifiedClassName.class, 1,
                PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(DATATYPEID, PropertyDescriptorSupport.createBasetype(DATATYPEID, Identifier.class, 2,
                PROPERTY_CONSTRAINTS[2], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(JournalMsg.getPropertyDescriptor(TIMESTAMP), this.timestamp));
        properties.add(super.createProperty(JournalMsg.getPropertyDescriptor(CLASSNAME), this.className));
        properties.add(super.createProperty(JournalMsg.getPropertyDescriptor(DATATYPEID), this.datatypeId));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(TIMESTAMP) && (property.getType() == Timestamp.class))) {
            this.setTimestamp(((Timestamp) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CLASSNAME) && (property.getType() == FullQualifiedClassName.class))) {
            this.setClassName(((FullQualifiedClassName) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DATATYPEID) && (property.getType() == Identifier.class))) {
            this.setDatatypeId(((Identifier) property.getInstance()));
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
        final JournalMsg other = ((JournalMsg) obj);
        if ((this.timestamp == null)) {
            if ((other.timestamp != null))
                return false;
        } else if ((!this.timestamp.equals(other.timestamp)))
            return false;
        if ((this.className == null)) {
            if ((other.className != null))
                return false;
        } else if ((!this.className.equals(other.className)))
            return false;
        if ((this.datatypeId == null)) {
            if ((other.datatypeId != null))
                return false;
        } else if ((!this.datatypeId.equals(other.datatypeId)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.timestamp == null) ? 0 : this.timestamp.hashCode()));
        result = ((PRIME * result) + ((this.className == null) ? 0 : this.className.hashCode()));
        result = ((PRIME * result) + ((this.datatypeId == null) ? 0 : this.datatypeId.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
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
     * The name of the datatype.
     *
     * @return the FullQualifiedClassName.
     */
    public FullQualifiedClassName getClassName() {
        return this.className;
    }

    /**
     * The name of the datatype.
     *
     * @param className the FullQualifiedClassName.
     */
    public void setClassName(FullQualifiedClassName className) {
        this.className = className;
    }

    /**
     * The identifier of the datatype.
     *
     * @return the Identifier.
     */
    public Identifier getDatatypeId() {
        return this.datatypeId;
    }

    /**
     * The identifier of the datatype.
     *
     * @param datatypeId the Identifier.
     */
    public void setDatatypeId(Identifier datatypeId) {
        this.datatypeId = datatypeId;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(JournalMsg.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(JournalMsg.class).getAllProperties();
    }
}
