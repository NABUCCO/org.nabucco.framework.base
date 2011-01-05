/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.business.person;

import org.nabucco.framework.base.facade.datatype.Enumeration;

/**
 * FamilyState
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-30
 */
public enum FamilyState implements Enumeration {
    UNDEFINED("undefined"),
    DIVORCED("divorced"),
    MARRIED("married"),
    SINGLE("single"),
    WIDOWED("widowed"), ;

    private String id;

    /**
     * Constructs a new FamilyState instance.
     *
     * @param id the String.
     */
    FamilyState(String id) {
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
