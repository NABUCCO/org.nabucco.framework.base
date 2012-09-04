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
import org.nabucco.framework.base.facade.datatype.DatatypeSupport;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * NabuccoExtension<p/>Datatype for a NABUCCO extension configuration.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-02-21
 */
public abstract class NabuccoExtension extends DatatypeSupport implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l1,512;u0,n;m1,1;" };

    public static final String IDENTIFIER = "identifier";

    /** The Extension ID */
    protected ExtensionId identifier;

    /** Constructs a new NabuccoExtension instance. */
    public NabuccoExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the NabuccoExtension.
     */
    protected void cloneObject(NabuccoExtension clone) {
        super.cloneObject(clone);
        if ((this.getIdentifier() != null)) {
            clone.setIdentifier(this.getIdentifier().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(IDENTIFIER, PropertyDescriptorSupport.createBasetype(IDENTIFIER, ExtensionId.class, 0,
                PROPERTY_CONSTRAINTS[0], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(NabuccoExtension.getPropertyDescriptor(IDENTIFIER), this.identifier, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(IDENTIFIER) && (property.getType() == ExtensionId.class))) {
            this.setIdentifier(((ExtensionId) property.getInstance()));
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
        final NabuccoExtension other = ((NabuccoExtension) obj);
        if ((this.identifier == null)) {
            if ((other.identifier != null))
                return false;
        } else if ((!this.identifier.equals(other.identifier)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.identifier == null) ? 0 : this.identifier.hashCode()));
        return result;
    }

    @Override
    public abstract NabuccoExtension cloneObject();

    /**
     * The Extension ID
     *
     * @return the ExtensionId.
     */
    public ExtensionId getIdentifier() {
        return this.identifier;
    }

    /**
     * The Extension ID
     *
     * @param identifier the ExtensionId.
     */
    public void setIdentifier(ExtensionId identifier) {
        this.identifier = identifier;
    }

    /**
     * The Extension ID
     *
     * @param identifier the String.
     */
    public void setIdentifier(String identifier) {
        if ((this.identifier == null)) {
            if ((identifier == null)) {
                return;
            }
            this.identifier = new ExtensionId();
        }
        this.identifier.setValue(identifier);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(NabuccoExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(NabuccoExtension.class).getAllProperties();
    }
}
