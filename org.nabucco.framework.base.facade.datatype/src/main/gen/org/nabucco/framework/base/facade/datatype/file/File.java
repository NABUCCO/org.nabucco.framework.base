/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.file;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Data;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Owner;
import org.nabucco.framework.base.facade.datatype.file.FileExtension;
import org.nabucco.framework.base.facade.datatype.file.FileName;
import org.nabucco.framework.base.facade.datatype.file.FileSize;
import org.nabucco.framework.base.facade.datatype.file.MimeType;
import org.nabucco.framework.base.facade.datatype.net.Uri;
import org.nabucco.framework.base.facade.datatype.property.BasetypeProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;

/**
 * File
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-12-06
 */
public abstract class File extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "owner", "data", "fileName", "fileExtension",
            "fileSize", "mimeType", "uri" };

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;m1,1;", "l0,n;m1,1;",
            "l0,n;m1,1;", "l0,n;m1,1;", "l0,n;m0,1;", "l0,n;m1,1;", "l0,n;m1,1;" };

    private Owner owner;

    private Data data;

    private FileName fileName;

    private FileExtension fileExtension;

    private FileSize fileSize;

    private MimeType mimeType;

    private Uri uri;

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
        if ((this.getData() != null)) {
            clone.setData(this.getData().cloneObject());
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
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new BasetypeProperty<Owner>(PROPERTY_NAMES[0], Owner.class,
                PROPERTY_CONSTRAINTS[0], this.owner));
        properties.add(new BasetypeProperty<Data>(PROPERTY_NAMES[1], Data.class,
                PROPERTY_CONSTRAINTS[1], this.data));
        properties.add(new BasetypeProperty<FileName>(PROPERTY_NAMES[2], FileName.class,
                PROPERTY_CONSTRAINTS[2], this.fileName));
        properties.add(new BasetypeProperty<FileExtension>(PROPERTY_NAMES[3], FileExtension.class,
                PROPERTY_CONSTRAINTS[3], this.fileExtension));
        properties.add(new BasetypeProperty<FileSize>(PROPERTY_NAMES[4], FileSize.class,
                PROPERTY_CONSTRAINTS[4], this.fileSize));
        properties.add(new BasetypeProperty<MimeType>(PROPERTY_NAMES[5], MimeType.class,
                PROPERTY_CONSTRAINTS[5], this.mimeType));
        properties.add(new BasetypeProperty<Uri>(PROPERTY_NAMES[6], Uri.class,
                PROPERTY_CONSTRAINTS[6], this.uri));
        return properties;
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
        if ((this.data == null)) {
            if ((other.data != null))
                return false;
        } else if ((!this.data.equals(other.data)))
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
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.owner == null) ? 0 : this.owner.hashCode()));
        result = ((PRIME * result) + ((this.data == null) ? 0 : this.data.hashCode()));
        result = ((PRIME * result) + ((this.fileName == null) ? 0 : this.fileName.hashCode()));
        result = ((PRIME * result) + ((this.fileExtension == null) ? 0 : this.fileExtension
                .hashCode()));
        result = ((PRIME * result) + ((this.fileSize == null) ? 0 : this.fileSize.hashCode()));
        result = ((PRIME * result) + ((this.mimeType == null) ? 0 : this.mimeType.hashCode()));
        result = ((PRIME * result) + ((this.uri == null) ? 0 : this.uri.hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<File>\n");
        appendable.append(super.toString());
        appendable.append((("<owner>" + this.owner) + "</owner>\n"));
        appendable.append((("<data>" + this.data) + "</data>\n"));
        appendable.append((("<fileName>" + this.fileName) + "</fileName>\n"));
        appendable.append((("<fileExtension>" + this.fileExtension) + "</fileExtension>\n"));
        appendable.append((("<fileSize>" + this.fileSize) + "</fileSize>\n"));
        appendable.append((("<mimeType>" + this.mimeType) + "</mimeType>\n"));
        appendable.append((("<uri>" + this.uri) + "</uri>\n"));
        appendable.append("</File>\n");
        return appendable.toString();
    }

    @Override
    public abstract File cloneObject();

    /**
     * Missing description at method getOwner.
     *
     * @return the Owner.
     */
    public Owner getOwner() {
        return this.owner;
    }

    /**
     * Missing description at method setOwner.
     *
     * @param owner the Owner.
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * Missing description at method setOwner.
     *
     * @param owner the String.
     */
    public void setOwner(String owner) {
        if ((this.owner == null)) {
            this.owner = new Owner();
        }
        this.owner.setValue(owner);
    }

    /**
     * Missing description at method getData.
     *
     * @return the Data.
     */
    public Data getData() {
        return this.data;
    }

    /**
     * Missing description at method setData.
     *
     * @param data the Data.
     */
    public void setData(Data data) {
        this.data = data;
    }

    /**
     * Missing description at method setData.
     *
     * @param data the byte[].
     */
    public void setData(byte[] data) {
        if ((this.data == null)) {
            this.data = new Data();
        }
        this.data.setValue(data);
    }

    /**
     * Missing description at method getFileName.
     *
     * @return the FileName.
     */
    public FileName getFileName() {
        return this.fileName;
    }

    /**
     * Missing description at method setFileName.
     *
     * @param fileName the FileName.
     */
    public void setFileName(FileName fileName) {
        this.fileName = fileName;
    }

    /**
     * Missing description at method setFileName.
     *
     * @param fileName the String.
     */
    public void setFileName(String fileName) {
        if ((this.fileName == null)) {
            this.fileName = new FileName();
        }
        this.fileName.setValue(fileName);
    }

    /**
     * Missing description at method getFileExtension.
     *
     * @return the FileExtension.
     */
    public FileExtension getFileExtension() {
        return this.fileExtension;
    }

    /**
     * Missing description at method setFileExtension.
     *
     * @param fileExtension the FileExtension.
     */
    public void setFileExtension(FileExtension fileExtension) {
        this.fileExtension = fileExtension;
    }

    /**
     * Missing description at method setFileExtension.
     *
     * @param fileExtension the String.
     */
    public void setFileExtension(String fileExtension) {
        if ((this.fileExtension == null)) {
            this.fileExtension = new FileExtension();
        }
        this.fileExtension.setValue(fileExtension);
    }

    /**
     * Missing description at method getFileSize.
     *
     * @return the FileSize.
     */
    public FileSize getFileSize() {
        return this.fileSize;
    }

    /**
     * Missing description at method setFileSize.
     *
     * @param fileSize the FileSize.
     */
    public void setFileSize(FileSize fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * Missing description at method setFileSize.
     *
     * @param fileSize the Long.
     */
    public void setFileSize(Long fileSize) {
        if ((this.fileSize == null)) {
            this.fileSize = new FileSize();
        }
        this.fileSize.setValue(fileSize);
    }

    /**
     * Missing description at method getMimeType.
     *
     * @return the MimeType.
     */
    public MimeType getMimeType() {
        return this.mimeType;
    }

    /**
     * Missing description at method setMimeType.
     *
     * @param mimeType the MimeType.
     */
    public void setMimeType(MimeType mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * Missing description at method setMimeType.
     *
     * @param mimeType the String.
     */
    public void setMimeType(String mimeType) {
        if ((this.mimeType == null)) {
            this.mimeType = new MimeType();
        }
        this.mimeType.setValue(mimeType);
    }

    /**
     * Missing description at method getUri.
     *
     * @return the Uri.
     */
    public Uri getUri() {
        return this.uri;
    }

    /**
     * Missing description at method setUri.
     *
     * @param uri the Uri.
     */
    public void setUri(Uri uri) {
        this.uri = uri;
    }

    /**
     * Missing description at method setUri.
     *
     * @param uri the String.
     */
    public void setUri(String uri) {
        if ((this.uri == null)) {
            this.uri = new Uri();
        }
        this.uri.setValue(uri);
    }
}
