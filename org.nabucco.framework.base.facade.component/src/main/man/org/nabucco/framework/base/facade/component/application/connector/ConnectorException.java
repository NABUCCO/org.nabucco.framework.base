/*
 * Copyright 2010 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco-source.org/nabucco-license.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.facade.component.application.connector;

import org.nabucco.framework.base.facade.exception.NabuccoException;

/**
 * ConnectorException
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ConnectorException extends NabuccoException {

    private static final long serialVersionUID = 1L;

    // TODO: Generate!

    /** Constructs a new NabuccoException instance. */
    public ConnectorException() {
        super();
    }

    /**
     * Constructs a new ConnectorException instance.
     * 
     * @param message
     *            the String.
     */
    public ConnectorException(String message) {
        super(message);
    }

    /**
     * Constructs a new ConnectorException instance.
     * 
     * @param throwable
     *            the Throwable.
     */
    public ConnectorException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Constructs a new ConnectorException instance.
     * 
     * @param throwable
     *            the Throwable.
     * @param message
     *            the String.
     */
    public ConnectorException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
