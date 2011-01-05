/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.file;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.NString;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.SimpleProperty;

/**
 * FileExtension<p/>Name of a file<p/>
 *
 * @author Oliver Teichmann, PRODYNA AG, 2010-07-23
 */
public class FileExtension extends NString implements Basetype {

    private static final long serialVersionUID = 1L;

    private static final String PROPERTY_NAME = "value";

    private static final String PROPERTY_CONSTRAINTS = "l0,n;";

    /** Constructs a new FileExtension instance. */
    public FileExtension() {
        super();
    }

    /**
     * Constructs a new FileExtension instance.
     *
     * @param value the String.
     */
    public FileExtension(String value) {
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
    public FileExtension cloneObject() {
        FileExtension clone = new FileExtension();
        super.cloneObject(clone);
        return clone;
    }
}
