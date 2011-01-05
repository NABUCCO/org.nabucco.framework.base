/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.NLong;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.SimpleProperty;

/**
 * Version<p/>Common version basetype for all NABUCCO datatypes<p/>
 *
 * @author Nicolas Moser, PRODYNA AG, 2010-01-22
 */
public class Version extends NLong implements Basetype {

    private static final long serialVersionUID = 1L;

    private static final String PROPERTY_NAME = "value";

    private static final String PROPERTY_CONSTRAINTS = "l0,n;";

    /** Constructs a new Version instance. */
    public Version() {
        super();
    }

    /**
     * Constructs a new Version instance.
     *
     * @param value the Long.
     */
    public Version(Long value) {
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
    public Version cloneObject() {
        Version clone = new Version();
        super.cloneObject(clone);
        return clone;
    }
}
