/*
 * Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved. Created 20.12.2010
 */
package org.nabucco.framework.base.facade.datatype.property;

import java.util.Collections;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * SimpleProperty
 * <p/>
 * PropertySupport object for simple types, like {@link java.lang.String} or
 * {@link java.lang.Integer}.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public final class SimpleProperty<N extends Object> extends SingleProperty<N> implements
        NabuccoProperty<N> {

    /**
     * Creates a new {@link SimpleProperty} instance.
     * 
     * @param name
     *            the property name
     * @param type
     *            the property type
     * @param constraints
     *            the property constraint string
     * @param instance
     *            the property instance
     */
    public SimpleProperty(String name, Class<N> type, String constraints, N instance) {
        super(name, type, constraints, PropertyType.SIMPLE, instance);
    }

    @Override
    public void accept(Visitor visitor) throws VisitorException {
        // Simple Properties cannot be visited!
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        return Collections.emptyList();
    }

}
