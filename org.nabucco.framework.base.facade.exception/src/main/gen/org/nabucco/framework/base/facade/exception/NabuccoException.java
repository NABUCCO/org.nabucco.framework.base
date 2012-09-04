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
package org.nabucco.framework.base.facade.exception;

import org.nabucco.framework.base.facade.exception.ExceptionSupport;

/**
 * NabuccoException<p/>Adapter for platform exceptions to NABUCCO internal exception<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2010-01-20
 */
public class NabuccoException extends ExceptionSupport {

    private static final long serialVersionUID = 1L;

    private String componentId;

    /** Constructs a new NabuccoException instance. */
    public NabuccoException() {
        super();
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
     * @param cause the Throwable.
     */
    public NabuccoException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new NabuccoException instance.
     *
     * @param cause the Throwable.
     * @param message the String.
     */
    public NabuccoException(String message, Throwable cause) {
        super(message, cause);
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
