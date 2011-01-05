/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.image;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.NByteArray;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.SimpleProperty;

/**
 * ImageData<p/>NABUCCO representation of an image. Mapped to database as BLOB.<p/>
 *
 * @version 2
 * @author Steffen Schmidt, PRODYNA AG, 2010-11-09
 */
public class ImageData extends NByteArray implements Basetype {

    private static final long serialVersionUID = 1L;

    private static final String PROPERTY_NAME = "value";

    private static final String PROPERTY_CONSTRAINTS = "l0,n;";

    /** Constructs a new ImageData instance. */
    public ImageData() {
        super();
    }

    /**
     * Constructs a new ImageData instance.
     *
     * @param value the byte[].
     */
    public ImageData(byte[] value) {
        super(value);
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new SimpleProperty<byte[]>(PROPERTY_NAME, byte[].class,
                PROPERTY_CONSTRAINTS, super.getValue()));
        return properties;
    }

    @Override
    public ImageData cloneObject() {
        ImageData clone = new ImageData();
        super.cloneObject(clone);
        return clone;
    }
}
