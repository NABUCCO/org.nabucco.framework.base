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
package org.nabucco.framework.base.facade.datatype.file;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Data;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.file.FileExtension;
import org.nabucco.framework.base.facade.datatype.file.FileName;
import org.nabucco.framework.base.facade.datatype.file.FileSize;
import org.nabucco.framework.base.facade.datatype.file.MimeType;
import org.nabucco.framework.base.facade.datatype.net.Uri;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * File<p/>A persistable file.<p/>
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-12-06
 */
public abstract class File extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l3,12;u0,n;m1,1;", "l0,128;u0,n;m1,1;", "l0,n;u0,n;m1,1;",
            "l0,n;u0,n;m0,1;", "l0,128;u0,n;m1,1;", "l0,255;u0,n;m1,1;", "l0,n;u0,n;m1,1;" };

    public static final String OWNER = "owner";

    public static final String FILENAME = "fileName";

    public static final String FILEEXTENSION = "fileExtension";

    public static final String FILESIZE = "fileSize";

    public static final String MIMETYPE = "mimeType";

    public static final String URI = "uri";

    public static final String DATA = "data";

    /** Owner of the file. */
    private Owner owner;

    /** Name of the file. */
    private FileName fileName;

    /** Extension of the file. */
    private FileExtension fileExtension;

    /** Size of the file. */
    private FileSize fileSize;

    /** File Media Type. */
    private MimeType mimeType;

    /** URI to the file. */
    private Uri uri;

    /** Data of the file. */
    private Data data;

    /** Constructs a new File instance. */
    public File() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the File.
     */
    protected void cloneObject(File clone) {
        super.cloneObject(clone);
        if ((this.getOwner() != null)) {
            clone.setOwner(this.getOwner().cloneObject());
        }
        if ((this.getFileName() != null)) {
            clone.setFileName(this.getFileName().cloneObject());
        }
        if ((this.getFileExtension() != null)) {
            clone.setFileExtension(this.getFileExtension().cloneObject());
        }
        if ((this.getFileSize() != null)) {
            clone.setFileSize(this.getFileSize().cloneObject());
        }
        if ((this.getMimeType() != null)) {
            clone.setMimeType(this.getMimeType().cloneObject());
        }
        if ((this.getUri() != null)) {
            clone.setUri(this.getUri().cloneObject());
        }
        if ((this.getData() != null)) {
            clone.setData(this.getData().cloneObject());
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
        propertyMap.put(OWNER,
                PropertyDescriptorSupport.createBasetype(OWNER, Owner.class, 3, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(FILENAME,
                PropertyDescriptorSupport.createBasetype(FILENAME, FileName.class, 4, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(FILEEXTENSION, PropertyDescriptorSupport.createBasetype(FILEEXTENSION, FileExtension.class, 5,
                PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(FILESIZE,
                PropertyDescriptorSupport.createBasetype(FILESIZE, FileSize.class, 6, PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(MIMETYPE,
                PropertyDescriptorSupport.createBasetype(MIMETYPE, MimeType.class, 7, PROPERTY_CONSTRAINTS[4], false));
        propertyMap.put(URI,
                PropertyDescriptorSupport.createBasetype(URI, Uri.class, 8, PROPERTY_CONSTRAINTS[5], false));
        propertyMap.put(DATA,
                PropertyDescriptorSupport.createBasetype(DATA, Data.class, 9, PROPERTY_CONSTRAINTS[6], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(File.getPropertyDescriptor(OWNER), this.owner, null));
        properties.add(super.createProperty(File.getPropertyDescriptor(FILENAME), this.fileName, null));
        properties.add(super.createProperty(File.getPropertyDescriptor(FILEEXTENSION), this.fileExtension, null));
        properties.add(super.createProperty(File.getPropertyDescriptor(FILESIZE), this.fileSize, null));
        properties.add(super.createProperty(File.getPropertyDescriptor(MIMETYPE), this.mimeType, null));
        properties.add(super.createProperty(File.getPropertyDescriptor(URI), this.uri, null));
        properties.add(super.createProperty(File.getPropertyDescriptor(DATA), this.data, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(OWNER) && (property.getType() == Owner.class))) {
            this.setOwner(((Owner) property.getInstance()));
            return true;
        } else if ((property.getName().equals(FILENAME) && (property.getType() == FileName.class))) {
            this.setFileName(((FileName) property.getInstance()));
            return true;
        } else if ((property.getName().equals(FILEEXTENSION) && (property.getType() == FileExtension.class))) {
            this.setFileExtension(((FileExtension) property.getInstance()));
            return true;
        } else if ((property.getName().equals(FILESIZE) && (property.getType() == FileSize.class))) {
            this.setFileSize(((FileSize) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MIMETYPE) && (property.getType() == MimeType.class))) {
            this.setMimeType(((MimeType) property.getInstance()));
            return true;
        } else if ((property.getName().equals(URI) && (property.getType() == Uri.class))) {
            this.setUri(((Uri) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DATA) && (property.getType() == Data.class))) {
            this.setData(((Data) property.getInstance()));
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
        final File other = ((File) obj);
        if ((this.owner == null)) {
            if ((other.owner != null))
                return false;
        } else if ((!this.owner.equals(other.owner)))
            return false;
        if ((this.fileName == null)) {
            if ((other.fileName != null))
                return false;
        } else if ((!this.fileName.equals(other.fileName)))
            return false;
        if ((this.fileExtension == null)) {
            if ((other.fileExtension != null))
                return false;
        } else if ((!this.fileExtension.equals(other.fileExtension)))
            return false;
        if ((this.fileSize == null)) {
            if ((other.fileSize != null))
                return false;
        } else if ((!this.fileSize.equals(other.fileSize)))
            return false;
        if ((this.mimeType == null)) {
            if ((other.mimeType != null))
                return false;
        } else if ((!this.mimeType.equals(other.mimeType)))
            return false;
        if ((this.uri == null)) {
            if ((other.uri != null))
                return false;
        } else if ((!this.uri.equals(other.uri)))
            return false;
        if ((this.data == null)) {
            if ((other.data != null))
                return false;
        } else if ((!this.data.equals(other.data)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.fileName == null) ? 0 : this.fileName.hashCode()));
        result = ((PRIME * result) + ((this.fileExtension == null) ? 0 : this.fileExtension.hashCode()));
        result = ((PRIME * result) + ((this.fileSize == null) ? 0 : this.fileSize.hashCode()));
        result = ((PRIME * result) + ((this.mimeType == null) ? 0 : this.mimeType.hashCode()));
        result = ((PRIME * result) + ((this.uri == null) ? 0 : this.uri.hashCode()));
        result = ((PRIME * result) + ((this.data == null) ? 0 : this.data.hashCode()));
        return result;
    }

    @Override
    public abstract File cloneObject();

    /**
     * Owner of the file.
     *
     * @return the Owner.
     */
    public Owner getOwner() {
        return this.owner;
    }

    /**
     * Owner of the file.
     *
     * @param owner the Owner.
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * Owner of the file.
     *
     * @param owner the String.
     */
    public void setOwner(String owner) {
        if ((this.owner == null)) {
            if ((owner == null)) {
                return;
            }
            this.owner = new Owner();
        }
        this.owner.setValue(owner);
    }

    /**
     * Name of the file.
     *
     * @return the FileName.
     */
    public FileName getFileName() {
        return this.fileName;
    }

    /**
     * Name of the file.
     *
     * @param fileName the FileName.
     */
    public void setFileName(FileName fileName) {
        this.fileName = fileName;
    }

    /**
     * Name of the file.
     *
     * @param fileName the String.
     */
    public void setFileName(String fileName) {
        if ((this.fileName == null)) {
            if ((fileName == null)) {
                return;
            }
            this.fileName = new FileName();
        }
        this.fileName.setValue(fileName);
    }

    /**
     * Extension of the file.
     *
     * @return the FileExtension.
     */
    public FileExtension getFileExtension() {
        return this.fileExtension;
    }

    /**
     * Extension of the file.
     *
     * @param fileExtension the FileExtension.
     */
    public void setFileExtension(FileExtension fileExtension) {
        this.fileExtension = fileExtension;
    }

    /**
     * Extension of the file.
     *
     * @param fileExtension the String.
     */
    public void setFileExtension(String fileExtension) {
        if ((this.fileExtension == null)) {
            if ((fileExtension == null)) {
                return;
            }
            this.fileExtension = new FileExtension();
        }
        this.fileExtension.setValue(fileExtension);
    }

    /**
     * Size of the file.
     *
     * @return the FileSize.
     */
    public FileSize getFileSize() {
        return this.fileSize;
    }

    /**
     * Size of the file.
     *
     * @param fileSize the FileSize.
     */
    public void setFileSize(FileSize fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * Size of the file.
     *
     * @param fileSize the Long.
     */
    public void setFileSize(Long fileSize) {
        if ((this.fileSize == null)) {
            if ((fileSize == null)) {
                return;
            }
            this.fileSize = new FileSize();
        }
        this.fileSize.setValue(fileSize);
    }

    /**
     * File Media Type.
     *
     * @return the MimeType.
     */
    public MimeType getMimeType() {
        return this.mimeType;
    }

    /**
     * File Media Type.
     *
     * @param mimeType the MimeType.
     */
    public void setMimeType(MimeType mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * File Media Type.
     *
     * @param mimeType the String.
     */
    public void setMimeType(String mimeType) {
        if ((this.mimeType == null)) {
            if ((mimeType == null)) {
                return;
            }
            this.mimeType = new MimeType();
        }
        this.mimeType.setValue(mimeType);
    }

    /**
     * URI to the file.
     *
     * @return the Uri.
     */
    public Uri getUri() {
        return this.uri;
    }

    /**
     * URI to the file.
     *
     * @param uri the Uri.
     */
    public void setUri(Uri uri) {
        this.uri = uri;
    }

    /**
     * URI to the file.
     *
     * @param uri the String.
     */
    public void setUri(String uri) {
        if ((this.uri == null)) {
            if ((uri == null)) {
                return;
            }
            this.uri = new Uri();
        }
        this.uri.setValue(uri);
    }

    /**
     * Data of the file.
     *
     * @return the Data.
     */
    public Data getData() {
        return this.data;
    }

    /**
     * Data of the file.
     *
     * @param data the Data.
     */
    public void setData(Data data) {
        this.data = data;
    }

    /**
     * Data of the file.
     *
     * @param data the byte[].
     */
    public void setData(byte[] data) {
        if ((this.data == null)) {
            if ((data == null)) {
                return;
            }
            this.data = new Data();
        }
        this.data.setValue(data);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(File.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(File.class).getAllProperties();
    }
}
