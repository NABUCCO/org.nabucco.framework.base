/*
 * Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved. Created 14.12.2010
 */
package org.nabucco.framework.base.facade.datatype;

import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelationContainer;

/**
 * DatatypeAccessor
 * <p/>
 * Utility class for accessing protected, technical datatype fields.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class DatatypeAccessor {

    /**
     * Singleton instance.
     */
    private static DatatypeAccessor instance = new DatatypeAccessor();

    /**
     * Private constructor.
     */
    private DatatypeAccessor() {
    }

    /**
     * Singleton access.
     * 
     * @return the DatatypeAccessor instance.
     */
    public static DatatypeAccessor getInstance() {
        return instance;
    }

    /**
     * Returns the component relation container of a datatype.
     * 
     * @param datatype
     *            the datatype holding the component relations
     * 
     * @return the component relation container
     */
    public ComponentRelationContainer getComponentRelation(Datatype datatype) {
        if (!(datatype instanceof DatatypeSupport)) {
            return null;
        }
        return ((DatatypeSupport) datatype).getComponentRelationContainer();
    }

}
