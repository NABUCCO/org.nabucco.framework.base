/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.exception.service;

import java.util.HashMap;
import java.util.Map;
import org.nabucco.framework.base.facade.exception.service.ServiceException;

/**
 * ResolveException<p/>Exception for resolve services within NABUCCO<p/>
 *
 * @version 1.0
 * @author Jens Wurm, PRODYNA AG, 2010-05-10
 */
public class ResolveException extends ServiceException {

    private static final long serialVersionUID = 1L;

    private Map<String, String> parameterMap = new HashMap<String, String>();

    /** Constructs a new ResolveException instance. */
    public ResolveException() {
        super();
    }

    /**
     * Constructs a new ResolveException instance.
     *
     * @param throwable the Throwable.
     * @param message the String.
     */
    public ResolveException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Constructs a new ResolveException instance.
     *
     * @param message the String.
     */
    public ResolveException(String message) {
        super(message);
    }

    /**
     * Constructs a new ResolveException instance.
     *
     * @param throwable the Throwable.
     */
    public ResolveException(Throwable throwable) {
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
