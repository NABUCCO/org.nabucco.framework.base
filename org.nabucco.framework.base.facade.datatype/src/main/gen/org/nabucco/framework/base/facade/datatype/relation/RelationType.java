/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.relation;

import org.nabucco.framework.base.facade.datatype.Enumeration;

/**
 * RelationType
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-30
 */
public enum RelationType implements Enumeration {
    IS_A("is a"),
    HAS_A("has a"), ;

    private String id;

    /**
     * Constructs a new RelationType instance.
     *
     * @param id the String.
     */
    RelationType(String id) {
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
