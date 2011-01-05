/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.code;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.NString;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.SimpleProperty;

/**
 * CodeFilter<p/>Basetype for code filter values<p/>
 *
 * @author Frank Ratschinski, PRODYNA AG, 2010-01-19
 */
public class CodeFilter extends NString implements Basetype {

    private static final long serialVersionUID = 1L;

    private static final String PROPERTY_NAME = "value";

    private static final String PROPERTY_CONSTRAINTS = "l1,32;";

    /** Constructs a new CodeFilter instance. */
    public CodeFilter() {
        super();
    }

    /**
     * Constructs a new CodeFilter instance.
     *
     * @param value the String.
     */
    public CodeFilter(String value) {
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
    public CodeFilter cloneObject() {
        CodeFilter clone = new CodeFilter();
        super.cloneObject(clone);
        return clone;
    }
}
