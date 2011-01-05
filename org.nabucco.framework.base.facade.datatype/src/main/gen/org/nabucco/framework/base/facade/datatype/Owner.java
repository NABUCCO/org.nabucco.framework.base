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
 * Owner<p/>Common owner basetype for all NABUCCO datatypes<p/>
 *
 * @author Frank Ratschinski, PRODYNA AG, 2010-01-04
 */
public class Owner extends NString implements Basetype {

    private static final long serialVersionUID = 1L;

    private static final String PROPERTY_NAME = "value";

    private static final String PROPERTY_CONSTRAINTS = "l3,12;";

    /** Constructs a new Owner instance. */
    public Owner() {
        super();
    }

    /**
     * Constructs a new Owner instance.
     *
     * @param value the String.
     */
    public Owner(String value) {
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
    public Owner cloneObject() {
        Owner clone = new Owner();
        super.cloneObject(clone);
        return clone;
    }
}
