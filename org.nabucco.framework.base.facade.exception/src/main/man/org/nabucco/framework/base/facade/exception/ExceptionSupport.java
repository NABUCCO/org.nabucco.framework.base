/*
 * Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved. Created 16.12.2010
 */
package org.nabucco.framework.base.facade.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * ExceptionSupport
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class ExceptionSupport extends Exception {

    /** The default serial version UID. */
    private static final long serialVersionUID = 1L;

    /** The nested cause. */
    private final NestedThrowable cause;

    /** The exception parameters. */
    private final Map<String, String> parameterMap = new HashMap<String, String>();

    /** The component identifier. */
    private String componentId;

    /** The error message. */
    private String message;

    /**
     * Creates a new {@link ExceptionSupport} instance.
     */
    public ExceptionSupport() {
        super();

        this.cause = null;
    }

    /**
     * Creates a new {@link ExceptionSupport} instance.
     * 
     * @param message
     *            the String.
     */
    public ExceptionSupport(String message) {
        super(message);

        this.cause = null;
    }

    /**
     * Constructs a new ExceptionSupport instance.
     * 
     * @param cause
     *            the causing throwable.
     */
    public ExceptionSupport(Throwable cause) {
        super((cause != null) ? cause.getMessage() : null);

        this.cause = wrapNestedThrowable(cause);
    }

    /**
     * Constructs a new {@link ExceptionSupport} instance.
     * 
     * @param cause
     *            the causing throwable.
     * @param message
     *            the exception message.
     */
    public ExceptionSupport(String message, Throwable cause) {
        super(message);

        this.cause = wrapNestedThrowable(cause);
    }

    @Override
    public NestedThrowable getCause() {
        return this.cause;
    }

    @Override
    public String getMessage() {
        if (this.message != null && this.message.length() > 0) {
            return this.message;
        }
        return super.getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        if (this.message != null && this.message.length() > 0) {
            return this.message;
        }
        return super.getMessage();
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
     * @param componentId
     *            the String.
     */
    public void setComponentId(String componentId) {
        this.componentId = componentId;
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
     * Returns the nested-throwable if set.
     * 
     * @return the nested throwable
     */
    public NestedThrowable getNestedThrowable() {
        return this.cause;
    }

    /**
     * Wraps a given pu-exception into a nested throwable.
     * 
     * @param cause
     *            the causing throwable
     * 
     * @return the nested throwable
     */
    private static NestedThrowable wrapNestedThrowable(Throwable cause) {
        if (cause != null) {
            if (cause instanceof ExceptionSupport) {
                return wrapNestedThrowable((ExceptionSupport) cause);
            }
            return new NestedThrowable(cause);
        }
        return null;
    }

    /**
     * Wraps a given throwable into a nested throwable.
     * 
     * @param exception
     *            the causing exception
     * 
     * @return the nested throwable
     */
    private static NestedThrowable wrapNestedThrowable(ExceptionSupport exception) {
        NestedThrowable nestedThrowable = exception.getNestedThrowable();
        if (nestedThrowable != null) {
            return new NestedThrowable(exception, nestedThrowable);
        }
        return new NestedThrowable(exception);
    }

}
