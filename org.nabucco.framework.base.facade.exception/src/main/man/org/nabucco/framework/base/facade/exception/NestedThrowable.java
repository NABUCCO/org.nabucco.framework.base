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
package org.nabucco.framework.base.facade.exception;

import org.nabucco.framework.base.facade.exception.adapter.AdapterException;

/**
 * NestedThrowable
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class NestedThrowable extends Exception {

    /** The serial-version-UID. */
    private static final long serialVersionUID = 1L;

    /** The simple-class-name. */
    private final String simpleClass;

    /** The qualified-class-name. */
    private final String qualifiedClass;

    /** The detail message. */
    private final String detailMessage;

    /**
     * Constructs this object.
     * 
     * @param cause
     *            the causing throwable
     */
    NestedThrowable(Throwable cause) {
        super(cause.getMessage(), (cause.getCause() != null ? new NestedThrowable(cause.getCause()) : null));

        this.simpleClass = cause.getClass().getSimpleName();
        this.qualifiedClass = cause.getClass().getCanonicalName();

        if (cause instanceof AdapterException) {
            AdapterException adapterException = (AdapterException) cause;
            this.detailMessage = adapterException.getDetailMessage();
        } else {
            this.detailMessage = null;
        }

        this.setStackTrace(cause.getStackTrace());
    }

    /**
     * Constructs this NestedThrowable.
     * 
     * @param cause
     *            the causing throwable
     * @param nestedCause
     *            the nested throwable
     */
    NestedThrowable(Throwable cause, NestedThrowable nestedCause) {
        super(cause.getMessage(), nestedCause);

        this.simpleClass = cause.getClass().getSimpleName();
        this.qualifiedClass = cause.getClass().getCanonicalName();

        if (cause instanceof AdapterException) {
            AdapterException adapterException = (AdapterException) cause;
            this.detailMessage = adapterException.getDetailMessage();
        } else {
            this.detailMessage = null;
        }

        this.setStackTrace(cause.getStackTrace());
    }

    /**
     * Getter for the simpleClass name.
     * 
     * @return Returns the simpleClass name.
     */
    public String getSimpleName() {
        return this.simpleClass;
    }

    /**
     * Getter for the qualifiedClass name.
     * 
     * @return Returns the qualifiedClass name.
     */
    public String getQualifiedName() {
        return this.qualifiedClass;
    }

    /**
     * Getter for the exception class.
     * 
     * @return the exception class or null if the class cannot be found by the classloader
     */
    public Class<?> getExceptionClass() {
        try {
            return NestedThrowable.class.getClassLoader().loadClass(this.qualifiedClass);
        } catch (ClassNotFoundException cnfe) {
            return null;
        }
    }

    /**
     * Getter for the detailMessage.
     * 
     * @return Returns the detailMessage.
     */
    public String getDetailMessage() {
        return this.detailMessage;
    }

    @Override
    public String toString() {
        String s = (this.qualifiedClass != null ? qualifiedClass : this.getClass().getName());
        String message = this.getLocalizedMessage();
        return (message != null) ? (s + ": " + message) : s;
    }

}
