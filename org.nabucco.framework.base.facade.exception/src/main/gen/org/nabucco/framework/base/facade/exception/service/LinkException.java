/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.exception.service;

import java.util.HashMap;
import java.util.Map;
import org.nabucco.framework.base.facade.exception.service.ServiceException;

/**
 * LinkException<p/>Exception for link services within NABUCCO<p/>
 *
 * @author Nicolas Moser, PRODYNA AG, 2010-02-23
 */
public class LinkException extends ServiceException {

    private static final long serialVersionUID = 1L;

    private Map<String, String> parameterMap = new HashMap<String, String>();

    /** Constructs a new LinkException instance. */
    public LinkException() {
        super();
    }

    /**
     * Constructs a new LinkException instance.
     *
     * @param throwable the Throwable.
     * @param message the String.
     */
    public LinkException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Constructs a new LinkException instance.
     *
     * @param message the String.
     */
    public LinkException(String message) {
        super(message);
    }

    /**
     * Constructs a new LinkException instance.
     *
     * @param throwable the Throwable.
     */
    public LinkException(Throwable throwable) {
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
