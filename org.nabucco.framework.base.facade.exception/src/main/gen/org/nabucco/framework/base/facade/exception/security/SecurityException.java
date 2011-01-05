/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.exception.security;

import java.util.HashMap;
import java.util.Map;
import org.nabucco.framework.base.facade.exception.NabuccoException;

/**
 * SecurityException<p/>Exception for security violations within NABUCCO<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-04-20
 */
public class SecurityException extends NabuccoException {

    private static final long serialVersionUID = 1L;

    private Map<String, String> parameterMap = new HashMap<String, String>();

    /** Constructs a new SecurityException instance. */
    public SecurityException() {
        super();
    }

    /**
     * Constructs a new SecurityException instance.
     *
     * @param throwable the Throwable.
     * @param message the String.
     */
    public SecurityException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Constructs a new SecurityException instance.
     *
     * @param message the String.
     */
    public SecurityException(String message) {
        super(message);
    }

    /**
     * Constructs a new SecurityException instance.
     *
     * @param throwable the Throwable.
     */
    public SecurityException(Throwable throwable) {
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
