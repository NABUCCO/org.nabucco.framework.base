/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.impl.service.aspect.constraining;

import org.nabucco.framework.base.impl.service.aspect.AspectException;

/**
 * Exception for reporting problems while adding dynamic constraints to datatypes.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 * 
 * @see <a
 *      href="http://www.nabuccosource.org/confluence/display/NBCF/Service+Aspects+Concept">Service
 *      Aspects Concept</a>
 */
public class ConstrainingException extends AspectException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new {@link ConstrainingException} instance.
     */
    public ConstrainingException() {
    }

    /**
     * Creates a new {@link ConstrainingException} instance.
     * 
     * @param message
     *            the error message
     * @param cause
     *            the error cause
     */
    public ConstrainingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new {@link ConstrainingException} instance.
     * 
     * @param message
     *            the error message
     */
    public ConstrainingException(String message) {
        super(message);
    }

    /**
     * Creates a new {@link ConstrainingException} instance.
     * 
     * @param cause
     *            the error cause
     */
    public ConstrainingException(Throwable cause) {
        super(cause);
    }

}
