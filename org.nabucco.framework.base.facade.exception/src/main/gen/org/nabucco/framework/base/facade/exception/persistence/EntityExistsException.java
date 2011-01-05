/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.exception.persistence;

import java.util.HashMap;
import java.util.Map;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;

/**
 * EntityExistsException<p/>Thrown when a new entity is persisted that already exists<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-04-20
 */
public class EntityExistsException extends PersistenceException {

    private static final long serialVersionUID = 1L;

    private Map<String, String> parameterMap = new HashMap<String, String>();

    /** Constructs a new EntityExistsException instance. */
    public EntityExistsException() {
        super();
    }

    /**
     * Constructs a new EntityExistsException instance.
     *
     * @param throwable the Throwable.
     * @param message the String.
     */
    public EntityExistsException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Constructs a new EntityExistsException instance.
     *
     * @param message the String.
     */
    public EntityExistsException(String message) {
        super(message);
    }

    /**
     * Constructs a new EntityExistsException instance.
     *
     * @param throwable the Throwable.
     */
    public EntityExistsException(Throwable throwable) {
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
