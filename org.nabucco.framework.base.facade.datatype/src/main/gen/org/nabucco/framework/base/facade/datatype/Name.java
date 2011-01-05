/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.NString;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.SimpleProperty;

/**
 * Name<p/>Common name basetype for all NABUCCO datatypes<p/>
 *
 * @author Frank Ratschinski, PRODYNA AG, 2010-01-04
 */
public class Name extends NString implements Basetype {

    private static final long serialVersionUID = 1L;

    private static final String PROPERTY_NAME = "value";

    private static final String PROPERTY_CONSTRAINTS = "l0,255;";

    /** Constructs a new Name instance. */
    public Name() {
        super();
    }

    /**
     * Constructs a new Name instance.
     *
     * @param value the String.
     */
    public Name(String value) {
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
    public Name cloneObject() {
        Name clone = new Name();
        super.cloneObject(clone);
        return clone;
    }
}
