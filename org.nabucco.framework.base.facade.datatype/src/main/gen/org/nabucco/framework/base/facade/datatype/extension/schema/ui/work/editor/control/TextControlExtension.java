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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.EnumerationProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.EditorControlExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * TextControlExtension<p/>Text field control extension<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2011-07-28
 */
public class TextControlExtension extends EditorControlExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m0,1;" };

    public static final String REGEX = "regex";

    public static final String FORMATTYPE = "formatType";

    /** regex to validate the text input */
    private StringProperty regex;

    private EnumerationProperty formatType;

    /** Constructs a new TextControlExtension instance. */
    public TextControlExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TextControlExtension.
     */
    protected void cloneObject(TextControlExtension clone) {
        super.cloneObject(clone);
        if ((this.getRegex() != null)) {
            clone.setRegex(this.getRegex().cloneObject());
        }
        if ((this.getFormatType() != null)) {
            clone.setFormatType(this.getFormatType().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(EditorControlExtension.class).getPropertyMap());
        propertyMap.put(REGEX, PropertyDescriptorSupport.createDatatype(REGEX, StringProperty.class, 12,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(FORMATTYPE, PropertyDescriptorSupport.createDatatype(FORMATTYPE, EnumerationProperty.class, 13,
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
        properties.add(super.createProperty(TextControlExtension.getPropertyDescriptor(REGEX), this.getRegex(), null));
        properties.add(super.createProperty(TextControlExtension.getPropertyDescriptor(FORMATTYPE),
                this.getFormatType(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(REGEX) && (property.getType() == StringProperty.class))) {
            this.setRegex(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(FORMATTYPE) && (property.getType() == EnumerationProperty.class))) {
            this.setFormatType(((EnumerationProperty) property.getInstance()));
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
        final TextControlExtension other = ((TextControlExtension) obj);
        if ((this.regex == null)) {
            if ((other.regex != null))
                return false;
        } else if ((!this.regex.equals(other.regex)))
            return false;
        if ((this.formatType == null)) {
            if ((other.formatType != null))
                return false;
        } else if ((!this.formatType.equals(other.formatType)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.regex == null) ? 0 : this.regex.hashCode()));
        result = ((PRIME * result) + ((this.formatType == null) ? 0 : this.formatType.hashCode()));
        return result;
    }

    @Override
    public TextControlExtension cloneObject() {
        TextControlExtension clone = new TextControlExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * regex to validate the text input
     *
     * @param regex the StringProperty.
     */
    public void setRegex(StringProperty regex) {
        this.regex = regex;
    }

    /**
     * regex to validate the text input
     *
     * @return the StringProperty.
     */
    public StringProperty getRegex() {
        return this.regex;
    }

    /**
     * Missing description at method setFormatType.
     *
     * @param formatType the EnumerationProperty.
     */
    public void setFormatType(EnumerationProperty formatType) {
        this.formatType = formatType;
    }

    /**
     * Missing description at method getFormatType.
     *
     * @return the EnumerationProperty.
     */
    public EnumerationProperty getFormatType() {
        return this.formatType;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TextControlExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TextControlExtension.class).getAllProperties();
    }
}
