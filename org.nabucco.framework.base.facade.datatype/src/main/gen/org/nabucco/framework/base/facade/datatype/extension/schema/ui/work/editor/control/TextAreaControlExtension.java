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
package org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.TextControlExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * TextAreaControlExtension<p/>Text area control field<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-01-06
 */
public class TextAreaControlExtension extends TextControlExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,1;" };

    public static final String UPLOADPATH = "uploadPath";

    /** The path where text have to be stored on the server */
    private StringProperty uploadPath;

    /** Constructs a new TextAreaControlExtension instance. */
    public TextAreaControlExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TextAreaControlExtension.
     */
    protected void cloneObject(TextAreaControlExtension clone) {
        super.cloneObject(clone);
        if ((this.getUploadPath() != null)) {
            clone.setUploadPath(this.getUploadPath().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(TextControlExtension.class).getPropertyMap());
        propertyMap.put(UPLOADPATH, PropertyDescriptorSupport.createDatatype(UPLOADPATH, StringProperty.class, 14,
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
        properties.add(super.createProperty(TextAreaControlExtension.getPropertyDescriptor(UPLOADPATH),
                this.getUploadPath(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(UPLOADPATH) && (property.getType() == StringProperty.class))) {
            this.setUploadPath(((StringProperty) property.getInstance()));
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
        final TextAreaControlExtension other = ((TextAreaControlExtension) obj);
        if ((this.uploadPath == null)) {
            if ((other.uploadPath != null))
                return false;
        } else if ((!this.uploadPath.equals(other.uploadPath)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.uploadPath == null) ? 0 : this.uploadPath.hashCode()));
        return result;
    }

    @Override
    public TextAreaControlExtension cloneObject() {
        TextAreaControlExtension clone = new TextAreaControlExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The path where text have to be stored on the server
     *
     * @param uploadPath the StringProperty.
     */
    public void setUploadPath(StringProperty uploadPath) {
        this.uploadPath = uploadPath;
    }

    /**
     * The path where text have to be stored on the server
     *
     * @return the StringProperty.
     */
    public StringProperty getUploadPath() {
        return this.uploadPath;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TextAreaControlExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TextAreaControlExtension.class).getAllProperties();
    }
}
