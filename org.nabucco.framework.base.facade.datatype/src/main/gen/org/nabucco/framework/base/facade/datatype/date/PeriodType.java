/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.date;

import org.nabucco.framework.base.facade.datatype.Enumeration;

/**
 * PeriodType<p/>Defines a period of time<p/>
 *
 * @author Oliver Teichmann, PRODYNA AG, 2010-07-26
 */
public enum PeriodType implements Enumeration {
    YEARS("Years"),
    MONTHS("Months"),
    DAYS("Days"),
    HOURS("Hours"),
    MINUTES("Minutes"),
    SECONDS("Seconds"),
    MILLISECONDS("Milliseconds"), ;

    private String id;

    /**
     * Constructs a new PeriodType instance.
     *
     * @param id the String.
     */
    PeriodType(String id) {
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
