/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.exception.service;

import java.util.HashMap;
import java.util.Map;
import org.nabucco.framework.base.facade.exception.service.ServiceException;

/**
 * MaintainException<p/>Exception for search services within NABUCCO<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2010-01-20
 */
public class MaintainException extends ServiceException {

    private static final long serialVersionUID = 1L;

    private Map<String, String> parameterMap = new HashMap<String, String>();

    /** Constructs a new MaintainException instance. */
    public MaintainException() {
        super();
    }

    /**
     * Constructs a new MaintainException instance.
     *
     * @param throwable the Throwable.
     * @param message the String.
     */
    public MaintainException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Constructs a new MaintainException instance.
     *
     * @param message the String.
     */
    public MaintainException(String message) {
        super(message);
    }

    /**
     * Constructs a new MaintainException instance.
     *
     * @param throwable the Throwable.
     */
    public MaintainException(Throwable throwable) {
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
