/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.exception.persistence;

import java.util.HashMap;
import java.util.Map;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;

/**
 * NonUniqueResultException<p/>Thrown when a query is executed and there is more than one result to return<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-04-20
 */
public class NonUniqueResultException extends PersistenceException {

    private static final long serialVersionUID = 1L;

    private Map<String, String> parameterMap = new HashMap<String, String>();

    /** Constructs a new NonUniqueResultException instance. */
    public NonUniqueResultException() {
        super();
    }

    /**
     * Constructs a new NonUniqueResultException instance.
     *
     * @param throwable the Throwable.
     * @param message the String.
     */
    public NonUniqueResultException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Constructs a new NonUniqueResultException instance.
     *
     * @param message the String.
     */
    public NonUniqueResultException(String message) {
        super(message);
    }

    /**
     * Constructs a new NonUniqueResultException instance.
     *
     * @param throwable the Throwable.
     */
    public NonUniqueResultException(Throwable throwable) {
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
