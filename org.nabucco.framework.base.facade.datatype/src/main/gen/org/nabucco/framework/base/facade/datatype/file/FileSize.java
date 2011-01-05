/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.file;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.NLong;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.SimpleProperty;

/**
 * FileSize<p/>Size of a file<p/>
 *
 * @author Oliver Teichmann, PRODYNA AG, 2010-07-23
 */
public class FileSize extends NLong implements Basetype {

    private static final long serialVersionUID = 1L;

    private static final String PROPERTY_NAME = "value";

    private static final String PROPERTY_CONSTRAINTS = "l0,n;";

    /** Constructs a new FileSize instance. */
    public FileSize() {
        super();
    }

    /**
     * Constructs a new FileSize instance.
     *
     * @param value the Long.
     */
    public FileSize(Long value) {
        super(value);
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new SimpleProperty<Long>(PROPERTY_NAME, Long.class, PROPERTY_CONSTRAINTS,
                super.getValue()));
        return properties;
    }

    @Override
    public FileSize cloneObject() {
        FileSize clone = new FileSize();
        super.cloneObject(clone);
        return clone;
    }
}
