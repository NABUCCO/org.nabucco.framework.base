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
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.EditorControlExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * FileControlExtension<p/>File upload extension<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-01-04
 */
public class FileControlExtension extends EditorControlExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,1;", "m0,1;" };

    public static final String UPLOADPATH = "uploadPath";

    public static final String EXTENSIONFILTER = "extensionFilter";

    /** The path where files have to be stored on the server */
    private StringProperty uploadPath;

    /** The extension filter to use */
    private StringProperty extensionFilter;

    /** Constructs a new FileControlExtension instance. */
    public FileControlExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the FileControlExtension.
     */
    protected void cloneObject(FileControlExtension clone) {
        super.cloneObject(clone);
        if ((this.getUploadPath() != null)) {
            clone.setUploadPath(this.getUploadPath().cloneObject());
        }
        if ((this.getExtensionFilter() != null)) {
            clone.setExtensionFilter(this.getExtensionFilter().cloneObject());
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
        propertyMap.put(UPLOADPATH, PropertyDescriptorSupport.createDatatype(UPLOADPATH, StringProperty.class, 12,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(EXTENSIONFILTER, PropertyDescriptorSupport.createDatatype(EXTENSIONFILTER,
                StringProperty.class, 13, PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(FileControlExtension.getPropertyDescriptor(UPLOADPATH),
                this.getUploadPath(), null));
        properties.add(super.createProperty(FileControlExtension.getPropertyDescriptor(EXTENSIONFILTER),
                this.getExtensionFilter(), null));
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
        } else if ((property.getName().equals(EXTENSIONFILTER) && (property.getType() == StringProperty.class))) {
            this.setExtensionFilter(((StringProperty) property.getInstance()));
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
        final FileControlExtension other = ((FileControlExtension) obj);
        if ((this.uploadPath == null)) {
            if ((other.uploadPath != null))
                return false;
        } else if ((!this.uploadPath.equals(other.uploadPath)))
            return false;
        if ((this.extensionFilter == null)) {
            if ((other.extensionFilter != null))
                return false;
        } else if ((!this.extensionFilter.equals(other.extensionFilter)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.uploadPath == null) ? 0 : this.uploadPath.hashCode()));
        result = ((PRIME * result) + ((this.extensionFilter == null) ? 0 : this.extensionFilter.hashCode()));
        return result;
    }

    @Override
    public FileControlExtension cloneObject() {
        FileControlExtension clone = new FileControlExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The path where files have to be stored on the server
     *
     * @param uploadPath the StringProperty.
     */
    public void setUploadPath(StringProperty uploadPath) {
        this.uploadPath = uploadPath;
    }

    /**
     * The path where files have to be stored on the server
     *
     * @return the StringProperty.
     */
    public StringProperty getUploadPath() {
        return this.uploadPath;
    }

    /**
     * The extension filter to use
     *
     * @param extensionFilter the StringProperty.
     */
    public void setExtensionFilter(StringProperty extensionFilter) {
        this.extensionFilter = extensionFilter;
    }

    /**
     * The extension filter to use
     *
     * @return the StringProperty.
     */
    public StringProperty getExtensionFilter() {
        return this.extensionFilter;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(FileControlExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(FileControlExtension.class).getAllProperties();
    }
}
