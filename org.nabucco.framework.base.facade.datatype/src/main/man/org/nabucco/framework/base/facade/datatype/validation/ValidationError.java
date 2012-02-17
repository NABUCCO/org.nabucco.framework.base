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
package org.nabucco.framework.base.facade.datatype.validation;

import java.io.Serializable;

/**
 * ValidationError
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ValidationError implements Serializable {

    private static final long serialVersionUID = 1L;

    private Validatable parent;

    private String propertyName;

    private String message;

    /**
     * Creates a new {@link ValidationError} instance.
     * 
     * @param parent
     *            the validated parent
     * @param propertyName
     *            name of the invalid property
     * @param message
     *            the error message
     */
    public ValidationError(Validatable parent, String propertyName, String message) {
        this.parent = parent;
        this.propertyName = propertyName;
        this.message = message;
    }

    /**
     * Getter for the parent.
     * 
     * @return Returns the parent.
     */
    public Validatable getParent() {
        return this.parent;
    }

    /**
     * Getter for the propertyName.
     * 
     * @return Returns the propertyName.
     */
    public String getPropertyName() {
        return this.propertyName;
    }

    /**
     * Getter for the error message.
     * 
     * @return Returns the message.
     */
    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return this.getMessage();
    }

}
