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
package org.nabucco.framework.base.facade.service.injection;

/**
 * InjectionException
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class InjectionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new {@link InjectionException} without parameters.
     */
    public InjectionException() {
        super();
    }

    /**
     * Creates a new {@link InjectionException} with message and cause.
     * 
     * @param message
     *            the message
     * @param throwable
     *            the cause
     */
    public InjectionException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Creates a new {@link InjectionException} with message.
     * 
     * @param messages
     *            the message
     */
    public InjectionException(String messages) {
        super(messages);
    }

    /**
     * Creates a new {@link InjectionException} with cause.
     * 
     * @param throwable
     *            the cause
     */
    public InjectionException(Throwable throwable) {
        super(throwable);
    }

}
