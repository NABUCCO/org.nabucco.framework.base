/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.mail;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.NString;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.SimpleProperty;

/**
 * Subject<p/>An email subject<p/>
 *
 * @author Oliver Teichmann, PRODYNA AG, 2010-07-27
 */
public class Subject extends NString implements Basetype {

    private static final long serialVersionUID = 1L;

    private static final String PROPERTY_NAME = "value";

    private static final String PROPERTY_CONSTRAINTS = "l0,255;";

    /** Constructs a new Subject instance. */
    public Subject() {
        super();
    }

    /**
     * Constructs a new Subject instance.
     *
     * @param value the String.
     */
    public Subject(String value) {
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
    public Subject cloneObject() {
        Subject clone = new Subject();
        super.cloneObject(clone);
        return clone;
    }
}
