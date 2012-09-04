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
package org.nabucco.framework.base.facade.exception.extension;

import org.nabucco.framework.base.facade.exception.ExceptionSupport;

/**
 * ExtensionException<p/>Exception for Extension Point handling.<p/>
 *
 * @version 1.0
 * @author Frank Ratschinski, PRODYNA AG, 2011-03-07
 */
public class ExtensionException extends ExceptionSupport {

    private static final long serialVersionUID = 1L;

    /** Constructs a new ExtensionException instance. */
    public ExtensionException() {
        super();
    }

    /**
     * Constructs a new ExtensionException instance.
     *
     * @param message the String.
     */
    public ExtensionException(String message) {
        super(message);
    }

    /**
     * Constructs a new ExtensionException instance.
     *
     * @param cause the Throwable.
     */
    public ExtensionException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new ExtensionException instance.
     *
     * @param cause the Throwable.
     * @param message the String.
     */
    public ExtensionException(String message, Throwable cause) {
        super(message, cause);
    }
}
