/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.exception.service;

import java.util.HashMap;
import java.util.Map;
import org.nabucco.framework.base.facade.exception.service.ServiceException;

/**
 * ProduceException<p/>Exception for produce services within NABUCCO<p/>
 *
 * @version 1.0
 * @author Stefanie Feld, PRODYNA AG, 2010-03-29
 */
public class ProduceException extends ServiceException {

    private static final long serialVersionUID = 1L;

    private Map<String, String> parameterMap = new HashMap<String, String>();

    /** Constructs a new ProduceException instance. */
    public ProduceException() {
        super();
    }

    /**
     * Constructs a new ProduceException instance.
     *
     * @param throwable the Throwable.
     * @param message the String.
     */
    public ProduceException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Constructs a new ProduceException instance.
     *
     * @param message the String.
     */
    public ProduceException(String message) {
        super(message);
    }

    /**
     * Constructs a new ProduceException instance.
     *
     * @param throwable the Throwable.
     */
    public ProduceException(Throwable throwable) {
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
