/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.reporting.reportdata.base;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.NInteger;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.SimpleProperty;

/**
 * ReportInteger
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-15
 */
public class ReportInteger extends NInteger implements Basetype {

    private static final long serialVersionUID = 1L;

    private static final String PROPERTY_NAME = "value";

    private static final String PROPERTY_CONSTRAINTS = "l0,n;";

    /** Constructs a new ReportInteger instance. */
    public ReportInteger() {
        super();
    }

    /**
     * Constructs a new ReportInteger instance.
     *
     * @param value the Integer.
     */
    public ReportInteger(Integer value) {
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
    public ReportInteger cloneObject() {
        ReportInteger clone = new ReportInteger();
        super.cloneObject(clone);
        return clone;
    }
}
