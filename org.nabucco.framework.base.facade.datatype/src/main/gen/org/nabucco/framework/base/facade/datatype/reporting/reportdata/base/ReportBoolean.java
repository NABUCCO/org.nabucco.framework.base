/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.reporting.reportdata.base;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.NBoolean;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.SimpleProperty;

/**
 * ReportBoolean
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-15
 */
public class ReportBoolean extends NBoolean implements Basetype {

    private static final long serialVersionUID = 1L;

    private static final String PROPERTY_NAME = "value";

    private static final String PROPERTY_CONSTRAINTS = "l0,n;";

    /** Constructs a new ReportBoolean instance. */
    public ReportBoolean() {
        super();
    }

    /**
     * Constructs a new ReportBoolean instance.
     *
     * @param value the Boolean.
     */
    public ReportBoolean(Boolean value) {
        super(value);
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new SimpleProperty<Boolean>(PROPERTY_NAME, Boolean.class,
                PROPERTY_CONSTRAINTS, super.getValue()));
        return properties;
    }

    @Override
    public ReportBoolean cloneObject() {
        ReportBoolean clone = new ReportBoolean();
        super.cloneObject(clone);
        return clone;
    }
}
