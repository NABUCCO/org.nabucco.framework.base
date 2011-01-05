/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.NInteger;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.SimpleProperty;

/**
 * Order<p/>Basetype for order number.<p/>
 *
 * @author Nicolas Moser, PRODYNA AG, 2010-09-22
 */
public class Order extends NInteger implements Basetype {

    private static final long serialVersionUID = 1L;

    private static final String PROPERTY_NAME = "value";

    private static final String PROPERTY_CONSTRAINTS = "l0,n;";

    /** Constructs a new Order instance. */
    public Order() {
        super();
    }

    /**
     * Constructs a new Order instance.
     *
     * @param value the Integer.
     */
    public Order(Integer value) {
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
    public Order cloneObject() {
        Order clone = new Order();
        super.cloneObject(clone);
        return clone;
    }
}
