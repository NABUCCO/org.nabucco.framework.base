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
 * Description<p/>Common description basetype for all NABUCCO datatypes<p/>
 *
 * @author Frank Ratschinski, PRODYNA AG, 2010-01-04
 */
public class Description extends NString implements Basetype {

    private static final long serialVersionUID = 1L;

    private static final String PROPERTY_NAME = "value";

    private static final String PROPERTY_CONSTRAINTS = "l0,255;";

    /** Constructs a new Description instance. */
    public Description() {
        super();
    }

    /**
     * Constructs a new Description instance.
     *
     * @param value the String.
     */
    public Description(String value) {
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
    public Description cloneObject() {
        Description clone = new Description();
        super.cloneObject(clone);
        return clone;
    }
}
