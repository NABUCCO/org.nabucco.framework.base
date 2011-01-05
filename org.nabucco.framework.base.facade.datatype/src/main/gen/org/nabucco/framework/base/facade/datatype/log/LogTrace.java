/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.log;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.NText;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.SimpleProperty;

/**
 * LogTrace<p/>Contains tracing messages for logging.<p/>
 *
 * @author Lasse Asbach, PRODYNA AG, 2010-01-04
 */
public class LogTrace extends NText implements Basetype {

    private static final long serialVersionUID = 1L;

    private static final String PROPERTY_NAME = "value";

    private static final String PROPERTY_CONSTRAINTS = "l0,n;";

    /** Constructs a new LogTrace instance. */
    public LogTrace() {
        super();
    }

    /**
     * Constructs a new LogTrace instance.
     *
     * @param value the String.
     */
    public LogTrace(String value) {
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
    public LogTrace cloneObject() {
        LogTrace clone = new LogTrace();
        super.cloneObject(clone);
        return clone;
    }
}
