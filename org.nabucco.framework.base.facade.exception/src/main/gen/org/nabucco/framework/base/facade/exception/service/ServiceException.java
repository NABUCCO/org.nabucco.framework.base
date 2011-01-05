/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.exception.service;

import java.util.HashMap;
import java.util.Map;
import org.nabucco.framework.base.facade.exception.NabuccoException;

/**
 * ServiceException<p/>Exception for services within NABUCCO<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2010-01-20
 */
public class ServiceException extends NabuccoException {

    private static final long serialVersionUID = 1L;

    private Map<String, String> parameterMap = new HashMap<String, String>();

    private String serviceId;

    /** Constructs a new ServiceException instance. */
    public ServiceException() {
        super();
    }

    /**
     * Constructs a new ServiceException instance.
     *
     * @param throwable the Throwable.
     * @param message the String.
     */
    public ServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Constructs a new ServiceException instance.
     *
     * @param message the String.
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new ServiceException instance.
     *
     * @param throwable the Throwable.
     */
    public ServiceException(Throwable throwable) {
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
     * Getter for the ServiceId.
     *
     * @return the String.
     */
    public String getServiceId() {
        return this.serviceId;
    }

    /**
     * Setter for the ServiceId.
     *
     * @param serviceId the String.
     */
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
