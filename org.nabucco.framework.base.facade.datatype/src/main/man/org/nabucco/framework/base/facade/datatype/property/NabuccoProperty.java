/*
 * Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved. Created 21.12.2010
 */
package org.nabucco.framework.base.facade.datatype.property;

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.visitor.Visitable;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

/**
 * NabuccoProperty
 * <p/>
 * A property defines reflective information about {@link Datatype}, {@link Basetype} and
 * {@link Message} attributes.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface NabuccoProperty<N extends Object> extends Visitable {

    /**
     * Getter for the name of the property.
     * 
     * @return Returns the name.
     */
    String getName();

    /**
     * Getter for the type of the property.
     * 
     * @return Returns the type.
     */
    Class<N> getType();

    /**
     * Getter for the constraints of the property.
     * 
     * @return Returns the constraints.
     */
    String getConstraints();

    /**
     * Getter for the property type.
     * 
     * @return Returns the propertyType.
     */
    PropertyType getPropertyType();

    /**
     * Getter for the concrete property instance.
     * 
     * @return Returns the instance.
     */
    Object getInstance();

}