/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.documentation;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.NText;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.SimpleProperty;

/**
 * Documentation<p/>Large NABUCCO documentation text.<p/>
 *
 * @author Nicolas Moser, PRODYNA AG, 2010-09-24
 */
public class Documentation extends NText implements Basetype {

    private static final long serialVersionUID = 1L;

    private static final String PROPERTY_NAME = "value";

    private static final String PROPERTY_CONSTRAINTS = "l0,100000;";

    /** Constructs a new Documentation instance. */
    public Documentation() {
        super();
    }

    /**
     * Constructs a new Documentation instance.
     *
     * @param value the String.
     */
    public Documentation(String value) {
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
    public Documentation cloneObject() {
        Documentation clone = new Documentation();
        super.cloneObject(clone);
        return clone;
    }
}
