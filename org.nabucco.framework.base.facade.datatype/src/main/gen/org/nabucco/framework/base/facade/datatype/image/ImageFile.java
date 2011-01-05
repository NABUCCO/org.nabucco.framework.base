/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.image;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.file.File;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;

/**
 * ImageFile
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-12-06
 */
public class ImageFile extends File implements Datatype {

    private static final long serialVersionUID = 1L;

    /** Constructs a new ImageFile instance. */
    public ImageFile() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the ImageFile.
     */
    protected void cloneObject(ImageFile clone) {
        super.cloneObject(clone);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        return properties;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append(super.toString());
        return appendable.toString();
    }

    @Override
    public ImageFile cloneObject() {
        ImageFile clone = new ImageFile();
        this.cloneObject(clone);
        return clone;
    }
}
