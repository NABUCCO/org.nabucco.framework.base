/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.NFloat;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.SimpleProperty;

/**
 * Percentage<p/>A percentage<p/>
 *
 * @version 1.0
 * @author Steffen Schmidt, PRODYNA AG, 2010-06-25
 */
public class Percentage extends NFloat implements Basetype {

    private static final long serialVersionUID = 1L;

    private static final String PROPERTY_NAME = "value";

    private static final String PROPERTY_CONSTRAINTS = "l0,n;";

    /** Constructs a new Percentage instance. */
    public Percentage() {
        super();
    }

    /**
     * Constructs a new Percentage instance.
     *
     * @param value the Float.
     */
    public Percentage(Float value) {
        super(value);
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new SimpleProperty<Float>(PROPERTY_NAME, Float.class, PROPERTY_CONSTRAINTS,
                super.getValue()));
        return properties;
    }

    @Override
    public Percentage cloneObject() {
        Percentage clone = new Percentage();
        super.cloneObject(clone);
        return clone;
    }
}
