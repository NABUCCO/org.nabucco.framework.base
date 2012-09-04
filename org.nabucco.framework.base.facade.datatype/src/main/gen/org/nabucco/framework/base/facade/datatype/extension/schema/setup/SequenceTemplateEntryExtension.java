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
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * SequenceTemplateEntryExtension<p/>Configuration for sequence template entries.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-06-01
 */
public class SequenceTemplateEntryExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,1;", "m0,1;" };

    public static final String GENERATORID = "generatorId";

    public static final String VALUE = "value";

    /** The generator id. */
    private StringProperty generatorId;

    /** The fixed text value. */
    private StringProperty value;

    /** Constructs a new SequenceTemplateEntryExtension instance. */
    public SequenceTemplateEntryExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the SequenceTemplateEntryExtension.
     */
    protected void cloneObject(SequenceTemplateEntryExtension clone) {
        super.cloneObject(clone);
        if ((this.getGeneratorId() != null)) {
            clone.setGeneratorId(this.getGeneratorId().cloneObject());
        }
        if ((this.getValue() != null)) {
            clone.setValue(this.getValue().cloneObject());
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
        propertyMap.put(GENERATORID, PropertyDescriptorSupport.createDatatype(GENERATORID, StringProperty.class, 2,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(VALUE, PropertyDescriptorSupport.createDatatype(VALUE, StringProperty.class, 3,
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
        properties.add(super.createProperty(SequenceTemplateEntryExtension.getPropertyDescriptor(GENERATORID),
                this.getGeneratorId(), null));
        properties.add(super.createProperty(SequenceTemplateEntryExtension.getPropertyDescriptor(VALUE),
                this.getValue(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(GENERATORID) && (property.getType() == StringProperty.class))) {
            this.setGeneratorId(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(VALUE) && (property.getType() == StringProperty.class))) {
            this.setValue(((StringProperty) property.getInstance()));
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
        final SequenceTemplateEntryExtension other = ((SequenceTemplateEntryExtension) obj);
        if ((this.generatorId == null)) {
            if ((other.generatorId != null))
                return false;
        } else if ((!this.generatorId.equals(other.generatorId)))
            return false;
        if ((this.value == null)) {
            if ((other.value != null))
                return false;
        } else if ((!this.value.equals(other.value)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.generatorId == null) ? 0 : this.generatorId.hashCode()));
        result = ((PRIME * result) + ((this.value == null) ? 0 : this.value.hashCode()));
        return result;
    }

    @Override
    public SequenceTemplateEntryExtension cloneObject() {
        SequenceTemplateEntryExtension clone = new SequenceTemplateEntryExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The generator id.
     *
     * @param generatorId the StringProperty.
     */
    public void setGeneratorId(StringProperty generatorId) {
        this.generatorId = generatorId;
    }

    /**
     * The generator id.
     *
     * @return the StringProperty.
     */
    public StringProperty getGeneratorId() {
        return this.generatorId;
    }

    /**
     * The fixed text value.
     *
     * @param value the StringProperty.
     */
    public void setValue(StringProperty value) {
        this.value = value;
    }

    /**
     * The fixed text value.
     *
     * @return the StringProperty.
     */
    public StringProperty getValue() {
        return this.value;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(SequenceTemplateEntryExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(SequenceTemplateEntryExtension.class).getAllProperties();
    }
}
