/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.exception.client;

import java.util.HashMap;
import java.util.Map;
import org.nabucco.framework.base.facade.exception.NabuccoException;

/**
 * ClientException<p/>Thrown in case of a client-side exception<p/>
 *
 * @version 1.0
 * @author Silas Schwarz, PRODYNA AG, 2010-10-19
 */
public class ClientException extends NabuccoException {

    private static final long serialVersionUID = 1L;

    private Map<String, String> parameterMap = new HashMap<String, String>();

    /** Constructs a new ClientException instance. */
    public ClientException() {
        super();
    }

    /**
     * Constructs a new ClientException instance.
     *
     * @param throwable the Throwable.
     * @param message the String.
     */
    public ClientException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Constructs a new ClientException instance.
     *
     * @param message the String.
     */
    public ClientException(String message) {
        super(message);
    }

    /**
     * Constructs a new ClientException instance.
     *
     * @param throwable the Throwable.
     */
    public ClientException(Throwable throwable) {
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
