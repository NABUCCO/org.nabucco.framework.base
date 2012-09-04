/*
 * Copyright 2012 PRODYNA AG
 * 
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.nabucco.framework.base.facade.exception.service;

import org.nabucco.framework.base.facade.exception.NabuccoException;

/**
 * ServiceException<p/>Exception for services within NABUCCO<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2010-01-20
 */
public class ServiceException extends NabuccoException {

    private static final long serialVersionUID = 1L;

    /** Constructs a new ServiceException instance. */
    public ServiceException() {
        super();
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
     * @param cause the Throwable.
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new ServiceException instance.
     *
     * @param cause the Throwable.
     * @param message the String.
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
