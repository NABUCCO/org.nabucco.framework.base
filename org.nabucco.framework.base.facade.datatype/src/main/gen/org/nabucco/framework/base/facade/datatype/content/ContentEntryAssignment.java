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
package org.nabucco.framework.base.facade.datatype.content;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Path;
import org.nabucco.framework.base.facade.datatype.content.ContentEntryAssignmentType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.text.TextContent;

/**
 * ContentEntryAssignment<p/>Content Element<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2011-12-11
 */
public abstract class ContentEntryAssignment extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "l0,n;u0,n;m0,1;", "l0,2147483647;u0,n;m0,1;" };

    public static final String TYPE = "type";

    public static final String UPLOADFILEPATH = "uploadFilePath";

    public static final String TEXTCONTENT = "textContent";

    /** Type of the content entry assignement (TEXT, File etc) */
    private ContentEntryAssignmentType type;

    /** The filepath that shows on the loaded temp file */
    private Path uploadFilePath;

    /** The filepath that shows on the loaded temp file */
    private TextContent textContent;

    /** Constructs a new ContentEntryAssignment instance. */
    public ContentEntryAssignment() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ContentEntryAssignment.
     */
    protected void cloneObject(ContentEntryAssignment clone) {
        super.cloneObject(clone);
        clone.setType(this.getType());
        if ((this.getUploadFilePath() != null)) {
            clone.setUploadFilePath(this.getUploadFilePath().cloneObject());
        }
        if ((this.getTextContent() != null)) {
            clone.setTextContent(this.getTextContent().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoDatatype.class).getPropertyMap());
        propertyMap.put(TYPE, PropertyDescriptorSupport.createEnumeration(TYPE, ContentEntryAssignmentType.class, 3,
                PROPERTY_CONSTRAINTS[0], false));
        propertyMap
                .put(UPLOADFILEPATH, PropertyDescriptorSupport.createBasetype(UPLOADFILEPATH, Path.class, 4,
                        PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(TEXTCONTENT, PropertyDescriptorSupport.createBasetype(TEXTCONTENT, TextContent.class, 5,
                PROPERTY_CONSTRAINTS[2], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ContentEntryAssignment.getPropertyDescriptor(TYPE), this.getType(), null));
        properties.add(super.createProperty(ContentEntryAssignment.getPropertyDescriptor(UPLOADFILEPATH),
                this.uploadFilePath, null));
        properties.add(super.createProperty(ContentEntryAssignment.getPropertyDescriptor(TEXTCONTENT),
                this.textContent, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(TYPE) && (property.getType() == ContentEntryAssignmentType.class))) {
            this.setType(((ContentEntryAssignmentType) property.getInstance()));
            return true;
        } else if ((property.getName().equals(UPLOADFILEPATH) && (property.getType() == Path.class))) {
            this.setUploadFilePath(((Path) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TEXTCONTENT) && (property.getType() == TextContent.class))) {
            this.setTextContent(((TextContent) property.getInstance()));
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
        final ContentEntryAssignment other = ((ContentEntryAssignment) obj);
        if ((this.type == null)) {
            if ((other.type != null))
                return false;
        } else if ((!this.type.equals(other.type)))
            return false;
        if ((this.uploadFilePath == null)) {
            if ((other.uploadFilePath != null))
                return false;
        } else if ((!this.uploadFilePath.equals(other.uploadFilePath)))
            return false;
        if ((this.textContent == null)) {
            if ((other.textContent != null))
                return false;
        } else if ((!this.textContent.equals(other.textContent)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.type == null) ? 0 : this.type.hashCode()));
        result = ((PRIME * result) + ((this.uploadFilePath == null) ? 0 : this.uploadFilePath.hashCode()));
        result = ((PRIME * result) + ((this.textContent == null) ? 0 : this.textContent.hashCode()));
        return result;
    }

    @Override
    public abstract ContentEntryAssignment cloneObject();

    /**
     * Type of the content entry assignement (TEXT, File etc)
     *
     * @return the ContentEntryAssignmentType.
     */
    public ContentEntryAssignmentType getType() {
        return this.type;
    }

    /**
     * Type of the content entry assignement (TEXT, File etc)
     *
     * @param type the ContentEntryAssignmentType.
     */
    public void setType(ContentEntryAssignmentType type) {
        this.type = type;
    }

    /**
     * Type of the content entry assignement (TEXT, File etc)
     *
     * @param type the String.
     */
    public void setType(String type) {
        if ((type == null)) {
            this.type = null;
        } else {
            this.type = ContentEntryAssignmentType.valueOf(type);
        }
    }

    /**
     * The filepath that shows on the loaded temp file
     *
     * @return the Path.
     */
    public Path getUploadFilePath() {
        return this.uploadFilePath;
    }

    /**
     * The filepath that shows on the loaded temp file
     *
     * @param uploadFilePath the Path.
     */
    public void setUploadFilePath(Path uploadFilePath) {
        this.uploadFilePath = uploadFilePath;
    }

    /**
     * The filepath that shows on the loaded temp file
     *
     * @param uploadFilePath the String.
     */
    public void setUploadFilePath(String uploadFilePath) {
        if ((this.uploadFilePath == null)) {
            if ((uploadFilePath == null)) {
                return;
            }
            this.uploadFilePath = new Path();
        }
        this.uploadFilePath.setValue(uploadFilePath);
    }

    /**
     * The filepath that shows on the loaded temp file
     *
     * @return the TextContent.
     */
    public TextContent getTextContent() {
        return this.textContent;
    }

    /**
     * The filepath that shows on the loaded temp file
     *
     * @param textContent the TextContent.
     */
    public void setTextContent(TextContent textContent) {
        this.textContent = textContent;
    }

    /**
     * The filepath that shows on the loaded temp file
     *
     * @param textContent the String.
     */
    public void setTextContent(String textContent) {
        if ((this.textContent == null)) {
            if ((textContent == null)) {
                return;
            }
            this.textContent = new TextContent();
        }
        this.textContent.setValue(textContent);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ContentEntryAssignment.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ContentEntryAssignment.class).getAllProperties();
    }
}
