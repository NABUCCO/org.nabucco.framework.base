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
package org.nabucco.framework.base.facade.datatype.extension.schema.connection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * ConnectionExtension<p/>Extension for server connection configuration.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-08-24
 */
public class ConnectionExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m1,1;", "m1,1;", "m1,1;" };

    public static final String NAME = "name";

    public static final String SERVERTYPE = "serverType";

    public static final String ENVIRONMENTTYPE = "environmentType";

    public static final String HOST = "host";

    public static final String PORT = "port";

    /** The name of the server connection. */
    private StringProperty name;

    /** The type of the server connection. */
    private EnumerationProperty serverType;

    /** The environment of the server connection. */
    private EnumerationProperty environmentType;

    /** The host name of the server connection. */
    private StringProperty host;

    /** The port of the server connection. */
    private StringProperty port;

    /** Constructs a new ConnectionExtension instance. */
    public ConnectionExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ConnectionExtension.
     */
    protected void cloneObject(ConnectionExtension clone) {
        super.cloneObject(clone);
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getServerType() != null)) {
            clone.setServerType(this.getServerType().cloneObject());
        }
        if ((this.getEnvironmentType() != null)) {
            clone.setEnvironmentType(this.getEnvironmentType().cloneObject());
        }
        if ((this.getHost() != null)) {
            clone.setHost(this.getHost().cloneObject());
        }
        if ((this.getPort() != null)) {
            clone.setPort(this.getPort().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoExtensionComposite.class).getPropertyMap());
        propertyMap.put(NAME, PropertyDescriptorSupport.createDatatype(NAME, StringProperty.class, 2,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(SERVERTYPE, PropertyDescriptorSupport.createDatatype(SERVERTYPE, EnumerationProperty.class, 3,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ENVIRONMENTTYPE, PropertyDescriptorSupport.createDatatype(ENVIRONMENTTYPE,
                EnumerationProperty.class, 4, PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(HOST, PropertyDescriptorSupport.createDatatype(HOST, StringProperty.class, 5,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(PORT, PropertyDescriptorSupport.createDatatype(PORT, StringProperty.class, 6,
                PROPERTY_CONSTRAINTS[4], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ConnectionExtension.getPropertyDescriptor(NAME), this.getName(), null));
        properties.add(super.createProperty(ConnectionExtension.getPropertyDescriptor(SERVERTYPE),
                this.getServerType(), null));
        properties.add(super.createProperty(ConnectionExtension.getPropertyDescriptor(ENVIRONMENTTYPE),
                this.getEnvironmentType(), null));
        properties.add(super.createProperty(ConnectionExtension.getPropertyDescriptor(HOST), this.getHost(), null));
        properties.add(super.createProperty(ConnectionExtension.getPropertyDescriptor(PORT), this.getPort(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(NAME) && (property.getType() == StringProperty.class))) {
            this.setName(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SERVERTYPE) && (property.getType() == EnumerationProperty.class))) {
            this.setServerType(((EnumerationProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ENVIRONMENTTYPE) && (property.getType() == EnumerationProperty.class))) {
            this.setEnvironmentType(((EnumerationProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(HOST) && (property.getType() == StringProperty.class))) {
            this.setHost(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PORT) && (property.getType() == StringProperty.class))) {
            this.setPort(((StringProperty) property.getInstance()));
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
        final ConnectionExtension other = ((ConnectionExtension) obj);
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        if ((this.serverType == null)) {
            if ((other.serverType != null))
                return false;
        } else if ((!this.serverType.equals(other.serverType)))
            return false;
        if ((this.environmentType == null)) {
            if ((other.environmentType != null))
                return false;
        } else if ((!this.environmentType.equals(other.environmentType)))
            return false;
        if ((this.host == null)) {
            if ((other.host != null))
                return false;
        } else if ((!this.host.equals(other.host)))
            return false;
        if ((this.port == null)) {
            if ((other.port != null))
                return false;
        } else if ((!this.port.equals(other.port)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.serverType == null) ? 0 : this.serverType.hashCode()));
        result = ((PRIME * result) + ((this.environmentType == null) ? 0 : this.environmentType.hashCode()));
        result = ((PRIME * result) + ((this.host == null) ? 0 : this.host.hashCode()));
        result = ((PRIME * result) + ((this.port == null) ? 0 : this.port.hashCode()));
        return result;
    }

    @Override
    public ConnectionExtension cloneObject() {
        ConnectionExtension clone = new ConnectionExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The name of the server connection.
     *
     * @param name the StringProperty.
     */
    public void setName(StringProperty name) {
        this.name = name;
    }

    /**
     * The name of the server connection.
     *
     * @return the StringProperty.
     */
    public StringProperty getName() {
        return this.name;
    }

    /**
     * The type of the server connection.
     *
     * @param serverType the EnumerationProperty.
     */
    public void setServerType(EnumerationProperty serverType) {
        this.serverType = serverType;
    }

    /**
     * The type of the server connection.
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getServerType() {
        return this.serverType;
    }

    /**
     * The environment of the server connection.
     *
     * @param environmentType the EnumerationProperty.
     */
    public void setEnvironmentType(EnumerationProperty environmentType) {
        this.environmentType = environmentType;
    }

    /**
     * The environment of the server connection.
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getEnvironmentType() {
        return this.environmentType;
    }

    /**
     * The host name of the server connection.
     *
     * @param host the StringProperty.
     */
    public void setHost(StringProperty host) {
        this.host = host;
    }

    /**
     * The host name of the server connection.
     *
     * @return the StringProperty.
     */
    public StringProperty getHost() {
        return this.host;
    }

    /**
     * The port of the server connection.
     *
     * @param port the StringProperty.
     */
    public void setPort(StringProperty port) {
        this.port = port;
    }

    /**
     * The port of the server connection.
     *
     * @return the StringProperty.
     */
    public StringProperty getPort() {
        return this.port;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ConnectionExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ConnectionExtension.class).getAllProperties();
    }
}
