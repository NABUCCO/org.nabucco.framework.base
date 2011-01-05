/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.file;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.NText;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.SimpleProperty;

/**
 * TextFileContent<p/>Content of a file<p/>
 *
 * @author Lasse Asbach, PRODYNA AG, 2010-07-23
 */
public class TextFileContent extends NText implements Basetype {

    private static final long serialVersionUID = 1L;

    private static final String PROPERTY_NAME = "value";

    private static final String PROPERTY_CONSTRAINTS = "l0,n;";

    /** Constructs a new TextFileContent instance. */
    public TextFileContent() {
        super();
    }

    /**
     * Constructs a new TextFileContent instance.
     *
     * @param value the String.
     */
    public TextFileContent(String value) {
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
    public TextFileContent cloneObject() {
        TextFileContent clone = new TextFileContent();
        super.cloneObject(clone);
        return clone;
    }
}
