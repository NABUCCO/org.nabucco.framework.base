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
package org.nabucco.framework.base.facade.datatype.extension.schema.setup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.property.IntegerProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * JournalExtension<p/>Configuration for the journaling part of setup.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-03-21
 */
public class JournalExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;" };

    public static final String MAXENTRIES = "maxEntries";

    /** The maximum amount of journal entries. */
    private IntegerProperty maxEntries;

    /** Constructs a new JournalExtension instance. */
    public JournalExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the JournalExtension.
     */
    protected void cloneObject(JournalExtension clone) {
        super.cloneObject(clone);
        if ((this.getMaxEntries() != null)) {
            clone.setMaxEntries(this.getMaxEntries().cloneObject());
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
        propertyMap.put(MAXENTRIES, PropertyDescriptorSupport.createDatatype(MAXENTRIES, IntegerProperty.class, 2,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(JournalExtension.getPropertyDescriptor(MAXENTRIES), this.getMaxEntries(),
                null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(MAXENTRIES) && (property.getType() == IntegerProperty.class))) {
            this.setMaxEntries(((IntegerProperty) property.getInstance()));
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
        final JournalExtension other = ((JournalExtension) obj);
        if ((this.maxEntries == null)) {
            if ((other.maxEntries != null))
                return false;
        } else if ((!this.maxEntries.equals(other.maxEntries)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.maxEntries == null) ? 0 : this.maxEntries.hashCode()));
        return result;
    }

    @Override
    public JournalExtension cloneObject() {
        JournalExtension clone = new JournalExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The maximum amount of journal entries.
     *
     * @param maxEntries the IntegerProperty.
     */
    public void setMaxEntries(IntegerProperty maxEntries) {
        this.maxEntries = maxEntries;
    }

    /**
     * The maximum amount of journal entries.
     *
     * @return the IntegerProperty.
     */
    public IntegerProperty getMaxEntries() {
        return this.maxEntries;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(JournalExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(JournalExtension.class).getAllProperties();
    }
}
