/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.net;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.NString;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.SimpleProperty;

/**
 * Url<p/>A pointer to a resource on the World Wide Web<p/>
 *
 * @author Oliver Teichmann, PRODYNA AG, 2010-07-23
 */
public class Url extends NString implements Basetype {

    private static final long serialVersionUID = 1L;

    private static final String PROPERTY_NAME = "value";

    private static final String PROPERTY_CONSTRAINTS = "l0,255;";

    /** Constructs a new Url instance. */
    public Url() {
        super();
    }

    /**
     * Constructs a new Url instance.
     *
     * @param value the String.
     */
    public Url(String value) {
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
    public Url cloneObject() {
        Url clone = new Url();
        super.cloneObject(clone);
        return clone;
    }
}
