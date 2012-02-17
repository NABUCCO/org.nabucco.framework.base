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
package org.nabucco.framework.base.facade.exception.validation;

import java.util.HashMap;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.validation.ValidationError;
import org.nabucco.framework.base.facade.datatype.validation.ValidationResult;
import org.nabucco.framework.base.facade.exception.NabuccoException;

/**
 * ValidationException
 * <p/>
 * Exception for validating messages/dataypes within NABUCCO
 * <p/>
 * 
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2010-07-20
 */
public class ValidationException extends NabuccoException {

    private Map<String, String> parameterMap = new HashMap<String, String>();

    private ValidationResult validationResult;

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ValidationException instance.
     * 
     * @param message
     *            the String.
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * Constructs a new ValidationException instance.
     * 
     * @param message
     *            the String
     * @param validationResult
     *            the ValidationResult.
     */
    public ValidationException(String message, ValidationResult validationResult) {
        super(message);
        this.validationResult = validationResult;
    }

    /**
     * Constructs a new ValidationException instance.
     * 
     * @param message
     *            the String
     * @param cause
     *            the Throwable
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Getter for the Parameters.
     * 
     * @return the Map<String, String>.
     */
    public Map<String, String> getParameters() {
        return new HashMap<String, String>(this.parameterMap);
    }

    @Override
    public String getMessage() {
        StringBuilder message = new StringBuilder();
        message.append(super.getMessage());

        if (this.validationResult != null && !this.validationResult.isEmpty()) {
            if (message.length() > 0) {
                message.append("\n");
            }
            for (ValidationError error : validationResult.getErrorList()) {
                message.append(" - ");
                message.append(error.getMessage());
                message.append("\n");
            }
        }

        return message.toString();
    }
}
