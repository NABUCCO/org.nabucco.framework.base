/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.reporting.reportdata.base;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.NDate;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.SimpleProperty;

/**
 * ReportDate
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-15
 */
public class ReportDate extends NDate implements Basetype {

    private static final long serialVersionUID = 1L;

    private static final String PROPERTY_NAME = "value";

    private static final String PROPERTY_CONSTRAINTS = "l0,n;";

    /** Constructs a new ReportDate instance. */
    public ReportDate() {
        super();
    }

    /**
     * Constructs a new ReportDate instance.
     *
     * @param value the java.util.Date.
     */
    public ReportDate(java.util.Date value) {
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
    public ReportDate cloneObject() {
        ReportDate clone = new ReportDate();
        super.cloneObject(clone);
        return clone;
    }
}
