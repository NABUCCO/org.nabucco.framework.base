/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.NByteArray;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.SimpleProperty;

/**
 * Data<p/>NABUCCO representation of a ByteArray. Mapped to database as BLOB.<p/>
 *
 * @author Silas Schwarz, PRODYNA AG, 2010-05-27
 */
public class Data extends NByteArray implements Basetype {

    private static final long serialVersionUID = 1L;

    private static final String PROPERTY_NAME = "value";

    private static final String PROPERTY_CONSTRAINTS = "l0,n;";

    /** Constructs a new Data instance. */
    public Data() {
        super();
    }

    /**
     * Constructs a new Data instance.
     *
     * @param value the byte[].
     */
    public Data(byte[] value) {
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
    public Data cloneObject() {
        Data clone = new Data();
        super.cloneObject(clone);
        return clone;
    }
}
