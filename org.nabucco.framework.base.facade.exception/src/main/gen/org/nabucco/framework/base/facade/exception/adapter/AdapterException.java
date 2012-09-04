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
package org.nabucco.framework.base.facade.exception.adapter;

import org.nabucco.framework.base.facade.exception.NabuccoException;

/**
 * AdapterException<p/>Exception for adapters within NABUCCO<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-05-06
 */
public class AdapterException extends NabuccoException {

    private static final long serialVersionUID = 1L;

    private String detailMessage;

    /** Constructs a new AdapterException instance. */
    public AdapterException() {
        super();
    }

    /**
     * Constructs a new AdapterException instance.
     *
     * @param message the String.
     */
    public AdapterException(String message) {
        super(message);
    }

    /**
     * Constructs a new AdapterException instance.
     *
     * @param cause the Throwable.
     */
    public AdapterException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new AdapterException instance.
     *
     * @param cause the Throwable.
     * @param message the String.
     */
    public AdapterException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Getter for the DetailMessage.
     *
     * @return the String.
     */
    public String getDetailMessage() {
        return this.detailMessage;
    }

    /**
     * Setter for the DetailMessage.
     *
     * @param detailMessage the String.
     */
    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }
}
