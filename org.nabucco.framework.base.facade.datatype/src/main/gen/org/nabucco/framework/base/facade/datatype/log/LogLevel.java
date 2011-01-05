/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.log;

import org.nabucco.framework.base.facade.datatype.Enumeration;

/**
 * LogLevel<p/>TODO<p/>
 *
 * @version 1.0
 * @author Lasse Asbach, PRODYNA AG, 2010-07-22
 */
public enum LogLevel implements Enumeration {
    FATAL("F"),
    ERROR("E"),
    WARNING("W"),
    INFO("I"),
    DEBUG("D"),
    TRACE("T"), ;

    private String id;

    /**
     * Constructs a new LogLevel instance.
     *
     * @param id the String.
     */
    LogLevel(String id) {
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
