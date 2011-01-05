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
 * Identifier<p/>Common identifing basetype for all NABUCCO datatypes.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2010-01-12
 */
public class Identifier extends NLong implements Basetype {

    private static final long serialVersionUID = 1L;

    private static final String PROPERTY_NAME = "value";

    private static final String PROPERTY_CONSTRAINTS = "l0,n;";

    /** Constructs a new Identifier instance. */
    public Identifier() {
        super();
    }

    /**
     * Constructs a new Identifier instance.
     *
     * @param value the Long.
     */
    public Identifier(Long value) {
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
    public Identifier cloneObject() {
        Identifier clone = new Identifier();
        super.cloneObject(clone);
        return clone;
    }
}
