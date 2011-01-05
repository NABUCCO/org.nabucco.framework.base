/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.reporting.reportdata.base;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.NLong;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.SimpleProperty;

/**
 * ReportLong
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-15
 */
public class ReportLong extends NLong implements Basetype {

    private static final long serialVersionUID = 1L;

    private static final String PROPERTY_NAME = "value";

    private static final String PROPERTY_CONSTRAINTS = "l0,n;";

    /** Constructs a new ReportLong instance. */
    public ReportLong() {
        super();
    }

    /**
     * Constructs a new ReportLong instance.
     *
     * @param value the Long.
     */
    public ReportLong(Long value) {
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
    public ReportLong cloneObject() {
        ReportLong clone = new ReportLong();
        super.cloneObject(clone);
        return clone;
    }
}
