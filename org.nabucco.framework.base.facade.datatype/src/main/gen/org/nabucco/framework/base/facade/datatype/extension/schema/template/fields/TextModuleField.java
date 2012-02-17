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
package org.nabucco.framework.base.facade.datatype.extension.schema.template.fields;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.BooleanProperty;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.TemplateComposite;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * TextModuleField<p/>Field for a text module.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinki, PRODYNA AG, 2011-02-24
 */
public class TextModuleField extends TemplateComposite implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;" };

    public static final String GLOBAL = "global";

    public static final String TEXTMODULENAME = "textmoduleName";

    /** Defines that the text module is a global one. */
    private BooleanProperty global;

    /** The name reference of the textmodule. */
    private StringProperty textmoduleName;

    /** Constructs a new TextModuleField instance. */
    public TextModuleField() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TextModuleField.
     */
    protected void cloneObject(TextModuleField clone) {
        super.cloneObject(clone);
        if ((this.getGlobal() != null)) {
            clone.setGlobal(this.getGlobal().cloneObject());
        }
        if ((this.getTextmoduleName() != null)) {
            clone.setTextmoduleName(this.getTextmoduleName().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(TemplateComposite.class).getPropertyMap());
        propertyMap.put(GLOBAL, PropertyDescriptorSupport.createDatatype(GLOBAL, BooleanProperty.class, 6,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(TEXTMODULENAME, PropertyDescriptorSupport.createDatatype(TEXTMODULENAME, StringProperty.class,
                7, PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(TextModuleField.getPropertyDescriptor(GLOBAL), this.getGlobal(), null));
        properties.add(super.createProperty(TextModuleField.getPropertyDescriptor(TEXTMODULENAME),
                this.getTextmoduleName(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(GLOBAL) && (property.getType() == BooleanProperty.class))) {
            this.setGlobal(((BooleanProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TEXTMODULENAME) && (property.getType() == StringProperty.class))) {
            this.setTextmoduleName(((StringProperty) property.getInstance()));
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
        final TextModuleField other = ((TextModuleField) obj);
        if ((this.global == null)) {
            if ((other.global != null))
                return false;
        } else if ((!this.global.equals(other.global)))
            return false;
        if ((this.textmoduleName == null)) {
            if ((other.textmoduleName != null))
                return false;
        } else if ((!this.textmoduleName.equals(other.textmoduleName)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.global == null) ? 0 : this.global.hashCode()));
        result = ((PRIME * result) + ((this.textmoduleName == null) ? 0 : this.textmoduleName.hashCode()));
        return result;
    }

    @Override
    public TextModuleField cloneObject() {
        TextModuleField clone = new TextModuleField();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Defines that the text module is a global one.
     *
     * @param global the BooleanProperty.
     */
    public void setGlobal(BooleanProperty global) {
        this.global = global;
    }

    /**
     * Defines that the text module is a global one.
     *
     * @return the BooleanProperty.
     */
    public BooleanProperty getGlobal() {
        return this.global;
    }

    /**
     * The name reference of the textmodule.
     *
     * @param textmoduleName the StringProperty.
     */
    public void setTextmoduleName(StringProperty textmoduleName) {
        this.textmoduleName = textmoduleName;
    }

    /**
     * The name reference of the textmodule.
     *
     * @return the StringProperty.
     */
    public StringProperty getTextmoduleName() {
        return this.textmoduleName;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TextModuleField.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TextModuleField.class).getAllProperties();
    }
}
