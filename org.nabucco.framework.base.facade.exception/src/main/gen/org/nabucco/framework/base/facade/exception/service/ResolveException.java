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

import org.nabucco.framework.base.facade.exception.service.ServiceException;

/**
 * ResolveException<p/>Exception for resolve services within NABUCCO<p/>
 *
 * @version 1.0
 * @author Jens Wurm, PRODYNA AG, 2010-05-10
 */
public class ResolveException extends ServiceException {

    private static final long serialVersionUID = 1L;

    /** Constructs a new ResolveException instance. */
    public ResolveException() {
        super();
    }

    /**
     * Constructs a new ResolveException instance.
     *
     * @param message the String.
     */
    public ResolveException(String message) {
        super(message);
    }

    /**
     * Constructs a new ResolveException instance.
     *
     * @param cause the Throwable.
     */
    public ResolveException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new ResolveException instance.
     *
     * @param cause the Throwable.
     * @param message the String.
     */
    public ResolveException(String message, Throwable cause) {
        super(message, cause);
    }
}
