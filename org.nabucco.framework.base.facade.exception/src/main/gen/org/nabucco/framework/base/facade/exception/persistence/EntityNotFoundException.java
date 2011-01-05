/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.exception.persistence;

import java.util.HashMap;
import java.util.Map;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;

/**
 * EntityNotFoundException<p/>Thrown when a required entity does not exist in the database<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-04-20
 */
public class EntityNotFoundException extends PersistenceException {

    private static final long serialVersionUID = 1L;

    private Map<String, String> parameterMap = new HashMap<String, String>();

    /** Constructs a new EntityNotFoundException instance. */
    public EntityNotFoundException() {
        super();
    }

    /**
     * Constructs a new EntityNotFoundException instance.
     *
     * @param throwable the Throwable.
     * @param message the String.
     */
    public EntityNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Constructs a new EntityNotFoundException instance.
     *
     * @param message the String.
     */
    public EntityNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new EntityNotFoundException instance.
     *
     * @param throwable the Throwable.
     */
    public EntityNotFoundException(Throwable throwable) {
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
