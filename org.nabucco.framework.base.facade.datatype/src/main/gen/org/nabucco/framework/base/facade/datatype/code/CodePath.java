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
 * CodePath<p/>Basetype for code path values<p/>
 *
 * @author Frank Ratschinski, PRODYNA AG, 2010-01-19
 */
public class CodePath extends NString implements Basetype {

    private static final long serialVersionUID = 1L;

    private static final String PROPERTY_NAME = "value";

    private static final String PROPERTY_CONSTRAINTS = "l1,1024;";

    /** Constructs a new CodePath instance. */
    public CodePath() {
        super();
    }

    /**
     * Constructs a new CodePath instance.
     *
     * @param value the String.
     */
    public CodePath(String value) {
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
    public CodePath cloneObject() {
        CodePath clone = new CodePath();
        super.cloneObject(clone);
        return clone;
    }
}
