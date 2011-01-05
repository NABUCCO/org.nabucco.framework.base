/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.business.person;

import org.nabucco.framework.base.facade.datatype.Enumeration;

/**
 * Nationality
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-30
 */
public enum Nationality implements Enumeration {
    GERMAN("german"),
    BRITISH("british"), ;

    private String id;

    /**
     * Constructs a new Nationality instance.
     *
     * @param id the String.
     */
    Nationality(String id) {
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
