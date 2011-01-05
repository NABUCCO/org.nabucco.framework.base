/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.date;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.NInteger;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.SimpleProperty;

/**
 * Millisecond<p/>The millisecond within a second (0 - 999)<p/>
 *
 * @author Oliver Teichmann, PRODYNA AG, 2010-07-26
 */
public class Millisecond extends NInteger implements Basetype {

    private static final long serialVersionUID = 1L;

    private static final String PROPERTY_NAME = "value";

    private static final String PROPERTY_CONSTRAINTS = "l0,n;";

    /** Constructs a new Millisecond instance. */
    public Millisecond() {
        super();
        this.setValue(0);
    }

    /**
     * Constructs a new Millisecond instance.
     *
     * @param value the Integer.
     */
    public Millisecond(Integer value) {
        super(value);
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new SimpleProperty<Integer>(PROPERTY_NAME, Integer.class,
                PROPERTY_CONSTRAINTS, super.getValue()));
        return properties;
    }

    @Override
    public Millisecond cloneObject() {
        Millisecond clone = new Millisecond();
        super.cloneObject(clone);
        return clone;
    }
}
