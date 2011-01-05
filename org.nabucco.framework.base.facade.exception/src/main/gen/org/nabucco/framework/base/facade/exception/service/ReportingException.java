/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.exception.service;

import java.util.HashMap;
import java.util.Map;
import org.nabucco.framework.base.facade.exception.service.ServiceException;

/**
 * ReportingException<p/>Exception for reporting services within NABUCCO<p/>
 *
 * @version 1.0
 * @author Dominic Trumpfheller, PRODYNA AG, 2010-11-09
 */
public class ReportingException extends ServiceException {

    private static final long serialVersionUID = 1L;

    private Map<String, String> parameterMap = new HashMap<String, String>();

    /** Constructs a new ReportingException instance. */
    public ReportingException() {
        super();
    }

    /**
     * Constructs a new ReportingException instance.
     *
     * @param throwable the Throwable.
     * @param message the String.
     */
    public ReportingException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Constructs a new ReportingException instance.
     *
     * @param message the String.
     */
    public ReportingException(String message) {
        super(message);
    }

    /**
     * Constructs a new ReportingException instance.
     *
     * @param throwable the Throwable.
     */
    public ReportingException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Getter for the Parameters.
     *
     * @return the Map<String, String>.
     */
    public Map<String, String> getParameters() {
        return new HashMap<String, String>(this.parameterMap);
    }
}
