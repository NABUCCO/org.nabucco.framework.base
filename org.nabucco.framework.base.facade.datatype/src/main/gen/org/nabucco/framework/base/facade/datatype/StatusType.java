/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype;

import org.nabucco.framework.base.facade.datatype.Enumeration;

/**
 * StatusType
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-30
 */
public enum StatusType implements Enumeration {
    ACTIVE("active"),
    INACTIVE("inactive"), ;

    private String id;

    /**
     * Constructs a new StatusType instance.
     *
     * @param id the String.
     */
    StatusType(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public int getOrdinal() {
        return this.ordinal();
    }

    @Override
    public Enumeration cloneObject() {
        return this;
    }
}
