/*
 * Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved. Created 21.12.2010
 */
package org.nabucco.framework.base.facade.datatype.property;



/**
 * SingleProperty
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
abstract class SingleProperty<N extends Object> extends PropertySupport<N> implements NabuccoProperty<N> {

    /** The property instance. */
    private N instance;

    /**
     * Creates a new {@link SingleProperty} instance.
     * 
     * @param name
     *            the property name
     * @param type
     *            the property class
     * @param constraints
     *            the property constraint string
     * @param propertyType
     *            the property type
     */
    SingleProperty(String name, Class<N> type, String constraints, PropertyType propertyType,
            N instance) {
        super(name, type, constraints, propertyType);

        this.instance = instance;
    }

    @Override
    public N getInstance() {
        return this.instance;
    }
    
}
