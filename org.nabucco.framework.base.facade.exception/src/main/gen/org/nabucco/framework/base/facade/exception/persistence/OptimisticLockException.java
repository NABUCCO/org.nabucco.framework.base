/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.exception.persistence;

import java.util.HashMap;
import java.util.Map;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;

/**
 * OptimisticLockException<p/>Thrown when an optimistic locking conflict occurs<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-04-20
 */
public class OptimisticLockException extends PersistenceException {

    private static final long serialVersionUID = 1L;

    private Map<String, String> parameterMap = new HashMap<String, String>();

    /** Constructs a new OptimisticLockException instance. */
    public OptimisticLockException() {
        super();
    }

    /**
     * Constructs a new OptimisticLockException instance.
     *
     * @param throwable the Throwable.
     * @param message the String.
     */
    public OptimisticLockException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Constructs a new OptimisticLockException instance.
     *
     * @param message the String.
     */
    public OptimisticLockException(String message) {
        super(message);
    }

    /**
     * Constructs a new OptimisticLockException instance.
     *
     * @param throwable the Throwable.
     */
    public OptimisticLockException(Throwable throwable) {
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
