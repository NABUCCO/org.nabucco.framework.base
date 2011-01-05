/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.exception;

import java.util.HashMap;
import java.util.Map;
import org.nabucco.framework.base.facade.exception.ExceptionSupport;

/**
 * NabuccoException<p/>Adapter for platform exceptions to NABUCCO internal exception<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2010-01-20
 */
public class NabuccoException extends ExceptionSupport {

    private static final long serialVersionUID = 1L;

    private Map<String, String> parameterMap = new HashMap<String, String>();

    private String componentId;

    /** Constructs a new NabuccoException instance. */
    public NabuccoException() {
        super();
    }

    /**
     * Constructs a new NabuccoException instance.
     *
     * @param throwable the Throwable.
     * @param message the String.
     */
    public NabuccoException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Constructs a new NabuccoException instance.
     *
     * @param message the String.
     */
    public NabuccoException(String message) {
        super(message);
    }

    /**
     * Constructs a new NabuccoException instance.
     *
     * @param throwable the Throwable.
     */
    public NabuccoException(Throwable throwable) {
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

    /**
     * Getter for the ComponentId.
     *
     * @return the String.
     */
    public String getComponentId() {
        return this.componentId;
    }

    /**
     * Setter for the ComponentId.
     *
     * @param componentId the String.
     */
    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }
}
