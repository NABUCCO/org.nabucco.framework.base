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
package org.nabucco.framework.base.facade.datatype.validation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ValidationResult
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public final class ValidationResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<ValidationError> errorList;

    /**
     * Getter for the validation error list.
     * 
     * @return the list of validation errors.
     */
    public List<ValidationError> getErrorList() {
        if (this.errorList == null) {
            this.errorList = new ArrayList<ValidationError>();
        }
        return this.errorList;
    }

    /**
     * Returns <tt>true</tt> if this result contains no errors.
     * 
     * @return <tt>true</tt> if this result contains no errors
     */
    public boolean isEmpty() {
        if (this.errorList == null) {
            return true;
        }
        return this.errorList.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Validation Result:\n");

        if (this.errorList == null || this.errorList.isEmpty()) {
            result.append(" - No validation errors.");
            return result.toString();
        }

        for (ValidationError error : errorList) {
            result.append(" - ");
            result.append(error);
            result.append("\n");
        }

        return result.toString();
    }

}
