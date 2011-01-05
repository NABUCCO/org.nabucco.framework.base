/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.date;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.NDate;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.SimpleProperty;

/**
 * Date<p/>A specific time<p/>
 *
 * @author Oliver Teichmann, PRODYNA AG, 2010-07-26
 */
public class Date extends NDate implements Basetype {

    private static final long serialVersionUID = 1L;

    private static final String PROPERTY_NAME = "value";

    private static final String PROPERTY_CONSTRAINTS = "l0,n;";

    /** Constructs a new Date instance. */
    public Date() {
        super();
    }

    /**
     * Constructs a new Date instance.
     *
     * @param value the java.util.Date.
     */
    public Date(java.util.Date value) {
        super(value);
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new SimpleProperty<java.util.Date>(PROPERTY_NAME, java.util.Date.class,
                PROPERTY_CONSTRAINTS, super.getValue()));
        return properties;
    }

    @Override
    public Date cloneObject() {
        Date clone = new Date();
        super.cloneObject(clone);
        return clone;
    }
}
