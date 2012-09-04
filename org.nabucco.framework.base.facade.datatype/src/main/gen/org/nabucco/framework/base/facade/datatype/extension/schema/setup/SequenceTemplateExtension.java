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
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.NabuccoExtensionComposite;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.setup.SequenceTemplateEntryExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * SequenceTemplateExtension<p/>Configuration for sequence templates.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-06-01
 */
public class SequenceTemplateExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m0,n;" };

    public static final String LOCALE = "locale";

    public static final String ENTRIES = "entries";

    /** The template locale. */
    private StringProperty locale;

    /** The template entries. */
    private NabuccoList<SequenceTemplateEntryExtension> entries;

    /** Constructs a new SequenceTemplateExtension instance. */
    public SequenceTemplateExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the SequenceTemplateExtension.
     */
    protected void cloneObject(SequenceTemplateExtension clone) {
        super.cloneObject(clone);
        if ((this.getLocale() != null)) {
            clone.setLocale(this.getLocale().cloneObject());
        }
        if ((this.entries != null)) {
            clone.entries = this.entries.cloneCollection();
        }
    }

    /**
     * Getter for the EntriesJPA.
     *
     * @return the List<SequenceTemplateEntryExtension>.
     */
    List<SequenceTemplateEntryExtension> getEntriesJPA() {
        if ((this.entries == null)) {
            this.entries = new NabuccoListImpl<SequenceTemplateEntryExtension>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<SequenceTemplateEntryExtension>) this.entries).getDelegate();
    }

    /**
     * Setter for the EntriesJPA.
     *
     * @param entries the List<SequenceTemplateEntryExtension>.
     */
    void setEntriesJPA(List<SequenceTemplateEntryExtension> entries) {
        if ((this.entries == null)) {
            this.entries = new NabuccoListImpl<SequenceTemplateEntryExtension>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<SequenceTemplateEntryExtension>) this.entries).setDelegate(entries);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoExtensionComposite.class).getPropertyMap());
        propertyMap.put(LOCALE, PropertyDescriptorSupport.createDatatype(LOCALE, StringProperty.class, 2,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ENTRIES, PropertyDescriptorSupport.createCollection(ENTRIES,
                SequenceTemplateEntryExtension.class, 3, PROPERTY_CONSTRAINTS[1], false,
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
        properties.add(super.createProperty(SequenceTemplateExtension.getPropertyDescriptor(LOCALE), this.getLocale(),
                null));
        properties.add(super.createProperty(SequenceTemplateExtension.getPropertyDescriptor(ENTRIES), this.entries,
                null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(LOCALE) && (property.getType() == StringProperty.class))) {
            this.setLocale(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ENTRIES) && (property.getType() == SequenceTemplateEntryExtension.class))) {
            this.entries = ((NabuccoList<SequenceTemplateEntryExtension>) property.getInstance());
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
        final SequenceTemplateExtension other = ((SequenceTemplateExtension) obj);
        if ((this.locale == null)) {
            if ((other.locale != null))
                return false;
        } else if ((!this.locale.equals(other.locale)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.locale == null) ? 0 : this.locale.hashCode()));
        return result;
    }

    @Override
    public SequenceTemplateExtension cloneObject() {
        SequenceTemplateExtension clone = new SequenceTemplateExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The template locale.
     *
     * @param locale the StringProperty.
     */
    public void setLocale(StringProperty locale) {
        this.locale = locale;
    }

    /**
     * The template locale.
     *
     * @return the StringProperty.
     */
    public StringProperty getLocale() {
        return this.locale;
    }

    /**
     * The template entries.
     *
     * @return the NabuccoList<SequenceTemplateEntryExtension>.
     */
    public NabuccoList<SequenceTemplateEntryExtension> getEntries() {
        if ((this.entries == null)) {
            this.entries = new NabuccoListImpl<SequenceTemplateEntryExtension>(NabuccoCollectionState.INITIALIZED);
        }
        return this.entries;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(SequenceTemplateExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(SequenceTemplateExtension.class).getAllProperties();
    }
}
