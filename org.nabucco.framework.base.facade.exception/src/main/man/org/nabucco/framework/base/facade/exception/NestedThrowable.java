/*
 * Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved. Created 16.12.2010
 */
package org.nabucco.framework.base.facade.exception;

/**
 * NestedThrowable
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
class NestedThrowable extends Exception {

    /** The serial-version-UID. */
    private static final long serialVersionUID = 1L;

    /** The qualified-class-name. */
    private String qualifiedClass;

    /**
     * Constructs this object.
     * 
     * @param t
     *            the throwable
     */
    public NestedThrowable(Throwable t) {
        super(t.getMessage(), (t.getCause() != null ? new NestedThrowable(t.getCause()) : null));
        this.qualifiedClass = t.getClass().getName();
        this.setStackTrace(t.getStackTrace());
    }

    /**
     * Constructs this NestedThrowable.
     * 
     * @param t
     *            the throwable
     * @param nt
     *            the nested throwable
     */
    public NestedThrowable(Throwable cause, NestedThrowable nestedCause) {
        super(cause.getMessage(), nestedCause);
        this.qualifiedClass = cause.getClass().getName();
        this.setStackTrace(cause.getStackTrace());
    }

    @Override
    public String toString() {
        String s = (this.qualifiedClass != null ? qualifiedClass : this.getClass().getName());
        String message = this.getLocalizedMessage();
        return (message != null) ? (s + ": " + message) : s;
    }

}
