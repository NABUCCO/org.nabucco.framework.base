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
 * LabelExtension<p/>An internationalized label for user variables.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-04-08
 */
public class LabelExtension extends NabuccoExtensionComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;" };

    public static final String LANG = "lang";

    public static final String LABEL = "label";

    /** The label language */
    private StringProperty lang;

    /** The label */
    private StringProperty label;

    /** Constructs a new LabelExtension instance. */
    public LabelExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the LabelExtension.
     */
    protected void cloneObject(LabelExtension clone) {
        super.cloneObject(clone);
        if ((this.getLang() != null)) {
            clone.setLang(this.getLang().cloneObject());
        }
        if ((this.getLabel() != null)) {
            clone.setLabel(this.getLabel().cloneObject());
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
        propertyMap.put(LANG, PropertyDescriptorSupport.createDatatype(LANG, StringProperty.class, 2,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(LABEL, PropertyDescriptorSupport.createDatatype(LABEL, StringProperty.class, 3,
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
        properties.add(super.createProperty(LabelExtension.getPropertyDescriptor(LANG), this.getLang(), null));
        properties.add(super.createProperty(LabelExtension.getPropertyDescriptor(LABEL), this.getLabel(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(LANG) && (property.getType() == StringProperty.class))) {
            this.setLang(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LABEL) && (property.getType() == StringProperty.class))) {
            this.setLabel(((StringProperty) property.getInstance()));
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
        final LabelExtension other = ((LabelExtension) obj);
        if ((this.lang == null)) {
            if ((other.lang != null))
                return false;
        } else if ((!this.lang.equals(other.lang)))
            return false;
        if ((this.label == null)) {
            if ((other.label != null))
                return false;
        } else if ((!this.label.equals(other.label)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.lang == null) ? 0 : this.lang.hashCode()));
        result = ((PRIME * result) + ((this.label == null) ? 0 : this.label.hashCode()));
        return result;
    }

    @Override
    public LabelExtension cloneObject() {
        LabelExtension clone = new LabelExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The label language
     *
     * @param lang the StringProperty.
     */
    public void setLang(StringProperty lang) {
        this.lang = lang;
    }

    /**
     * The label language
     *
     * @return the StringProperty.
     */
    public StringProperty getLang() {
        return this.lang;
    }

    /**
     * The label
     *
     * @param label the StringProperty.
     */
    public void setLabel(StringProperty label) {
        this.label = label;
    }

    /**
     * The label
     *
     * @return the StringProperty.
     */
    public StringProperty getLabel() {
        return this.label;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(LabelExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(LabelExtension.class).getAllProperties();
    }
}
