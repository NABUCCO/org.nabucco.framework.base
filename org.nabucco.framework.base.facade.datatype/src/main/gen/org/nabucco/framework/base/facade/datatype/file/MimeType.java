/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.file;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.NString;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.SimpleProperty;

/**
 * MimeType<p/>A media type<p/>
 *
 * @author Oliver Teichmann, PRODYNA AG, 2010-07-23
 */
public class MimeType extends NString implements Basetype {

    private static final long serialVersionUID = 1L;

    private static final String PROPERTY_NAME = "value";

    private static final String PROPERTY_CONSTRAINTS = "l0,128;";

    /** Constructs a new MimeType instance. */
    public MimeType() {
        super();
    }

    /**
     * Constructs a new MimeType instance.
     *
     * @param value the String.
     */
    public MimeType(String value) {
        super(value);
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new SimpleProperty<String>(PROPERTY_NAME, String.class,
                PROPERTY_CONSTRAINTS, super.getValue()));
        return properties;
    }

    @Override
    public MimeType cloneObject() {
        MimeType clone = new MimeType();
        super.cloneObject(clone);
        return clone;
    }
}
