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
 * ImageControlExtension<p/>Image field of editor<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-01-04
 */
public class ImageControlExtension extends EditorControlExtension implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;" };

    public static final String UPLOADERDIALOGID = "uploaderDialogId";

    public static final String UPLOADPATH = "uploadPath";

    /** The id of dialog to use for upload */
    private StringProperty uploaderDialogId;

    /** The path where images have to be uploaded */
    private StringProperty uploadPath;

    /** Constructs a new ImageControlExtension instance. */
    public ImageControlExtension() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ImageControlExtension.
     */
    protected void cloneObject(ImageControlExtension clone) {
        super.cloneObject(clone);
        if ((this.getUploaderDialogId() != null)) {
            clone.setUploaderDialogId(this.getUploaderDialogId().cloneObject());
        }
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
        propertyMap.putAll(PropertyCache.getInstance().retrieve(EditorControlExtension.class).getPropertyMap());
        propertyMap.put(UPLOADERDIALOGID, PropertyDescriptorSupport.createDatatype(UPLOADERDIALOGID,
                StringProperty.class, 12, PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(UPLOADPATH, PropertyDescriptorSupport.createDatatype(UPLOADPATH, StringProperty.class, 13,
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
        properties.add(super.createProperty(ImageControlExtension.getPropertyDescriptor(UPLOADERDIALOGID),
                this.getUploaderDialogId(), null));
        properties.add(super.createProperty(ImageControlExtension.getPropertyDescriptor(UPLOADPATH),
                this.getUploadPath(), null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(UPLOADERDIALOGID) && (property.getType() == StringProperty.class))) {
            this.setUploaderDialogId(((StringProperty) property.getInstance()));
            return true;
        } else if ((property.getName().equals(UPLOADPATH) && (property.getType() == StringProperty.class))) {
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
        final ImageControlExtension other = ((ImageControlExtension) obj);
        if ((this.uploaderDialogId == null)) {
            if ((other.uploaderDialogId != null))
                return false;
        } else if ((!this.uploaderDialogId.equals(other.uploaderDialogId)))
            return false;
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
        result = ((PRIME * result) + ((this.uploaderDialogId == null) ? 0 : this.uploaderDialogId.hashCode()));
        result = ((PRIME * result) + ((this.uploadPath == null) ? 0 : this.uploadPath.hashCode()));
        return result;
    }

    @Override
    public ImageControlExtension cloneObject() {
        ImageControlExtension clone = new ImageControlExtension();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The id of dialog to use for upload
     *
     * @param uploaderDialogId the StringProperty.
     */
    public void setUploaderDialogId(StringProperty uploaderDialogId) {
        this.uploaderDialogId = uploaderDialogId;
    }

    /**
     * The id of dialog to use for upload
     *
     * @return the StringProperty.
     */
    public StringProperty getUploaderDialogId() {
        return this.uploaderDialogId;
    }

    /**
     * The path where images have to be uploaded
     *
     * @param uploadPath the StringProperty.
     */
    public void setUploadPath(StringProperty uploadPath) {
        this.uploadPath = uploadPath;
    }

    /**
     * The path where images have to be uploaded
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
        return PropertyCache.getInstance().retrieve(ImageControlExtension.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ImageControlExtension.class).getAllProperties();
    }
}
