/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.message;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.NString;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.SimpleProperty;

/**
 * CodeNumber<p/>Identify the message<p/>
 *
 * @author Michael Krausse, PRODYNA AG, 2010-04-07
 */
public class CodeNumber extends NString implements Basetype {

    private static final long serialVersionUID = 1L;

    private static final String PROPERTY_NAME = "value";

    private static final String PROPERTY_CONSTRAINTS = "l1,500;";

    /** Constructs a new CodeNumber instance. */
    public CodeNumber() {
        super();
    }

    /**
     * Constructs a new CodeNumber instance.
     *
     * @param value the String.
     */
    public CodeNumber(String value) {
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
    public CodeNumber cloneObject() {
        CodeNumber clone = new CodeNumber();
        super.cloneObject(clone);
        return clone;
    }
}
