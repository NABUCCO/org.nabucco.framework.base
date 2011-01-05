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
 * Uri<p/>Represents a Uniform Resource Identifier (URI) reference<p/>
 *
 * @author Oliver Teichmann, PRODYNA AG, 2010-07-23
 */
public class Uri extends NString implements Basetype {

    private static final long serialVersionUID = 1L;

    private static final String PROPERTY_NAME = "value";

    private static final String PROPERTY_CONSTRAINTS = "l0,255;";

    /** Constructs a new Uri instance. */
    public Uri() {
        super();
    }

    /**
     * Constructs a new Uri instance.
     *
     * @param value the String.
     */
    public Uri(String value) {
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
    public Uri cloneObject() {
        Uri clone = new Uri();
        super.cloneObject(clone);
        return clone;
    }
}
