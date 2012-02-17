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

import java.util.HashMap;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.exceptionmsg.MessageOwner;
import org.nabucco.framework.base.facade.datatype.exceptionmsg.field.FieldMessageSet;
import org.nabucco.framework.base.facade.datatype.logger.LoggingBehaviour;

/**
 * ExceptionSupport
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class ExceptionSupport extends Exception implements LoggingBehaviour, MessageOwner {

    /** The default serial version UID. */
    private static final long serialVersionUID = 1L;

    /** The nested cause. */
    private final NestedThrowable cause;

    /** The exception parameters. */
    private final Map<String, String> parameterMap = new HashMap<String, String>();

    /** The component identifier. */
    private String componentId;

    /** The error message. */
    private String message;

    /** The error message set specific for each field. */
    private FieldMessageSet fieldMessageSet;

    /** Flag indicating whether the exception is already logged. */
    private boolean logged = false;

    /**
     * Creates a new {@link ExceptionSupport} instance.
     */
    public ExceptionSupport() {
        super();

        this.cause = null;
    }

    /**
     * Creates a new {@link ExceptionSupport} instance.
     * 
     * @param message
     *            the String.
     */
    public ExceptionSupport(String message) {
        super(message);

        this.cause = null;
    }

    /**
     * Constructs a new ExceptionSupport instance.
     * 
     * @param cause
     *            the causing throwable.
     */
    public ExceptionSupport(Throwable cause) {
        super((cause != null) ? cause.getMessage() : null);

        this.cause = wrapNestedThrowable(cause);
    }

    /**
     * Constructs a new {@link ExceptionSupport} instance.
     * 
     * @param cause
     *            the causing throwable.
     * @param message
     *            the exception message.
     */
    public ExceptionSupport(String message, Throwable cause) {
        super(message);

        this.cause = wrapNestedThrowable(cause);
    }

    @Override
    public boolean isLogged() {
        return this.logged;
    }

    @Override
    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    @Override
    public NestedThrowable getCause() {
        return this.cause;
    }

    @Override
    public String getMessage() {
        if (this.message != null && this.message.length() > 0) {
            return this.message;
        }
        return super.getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        if (this.message != null && this.message.length() > 0) {
            return this.message;
        }
        return super.getMessage();
    }

    @Override
    public FieldMessageSet getFieldMessageSet() {
        return this.fieldMessageSet;
    }

    @Override
    public void setFieldMessageSet(FieldMessageSet fieldMessageSet) {
        this.fieldMessageSet = fieldMessageSet;
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
     * @param componentId
     *            the String.
     */
    public void setComponentId(String componentId) {
        this.componentId = componentId;
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
     * Returns the nested-throwable if set.
     * 
     * @return the nested throwable
     */
    public NestedThrowable getNestedThrowable() {
        return this.cause;
    }

    /**
     * Wraps a given pu-exception into a nested throwable.
     * 
     * @param cause
     *            the causing throwable
     * 
     * @return the nested throwable
     */
    private static NestedThrowable wrapNestedThrowable(Throwable cause) {
        if (cause != null) {
            if (cause instanceof ExceptionSupport) {
                return wrapNestedThrowable((ExceptionSupport) cause);
            }
            return new NestedThrowable(cause);
        }
        return null;
    }

    /**
     * Wraps a given throwable into a nested throwable.
     * 
     * @param exception
     *            the causing exception
     * 
     * @return the nested throwable
     */
    private static NestedThrowable wrapNestedThrowable(ExceptionSupport exception) {
        NestedThrowable nestedThrowable = exception.getNestedThrowable();
        if (nestedThrowable != null) {
            return new NestedThrowable(exception, nestedThrowable);
        }
        return new NestedThrowable(exception);
    }

}
