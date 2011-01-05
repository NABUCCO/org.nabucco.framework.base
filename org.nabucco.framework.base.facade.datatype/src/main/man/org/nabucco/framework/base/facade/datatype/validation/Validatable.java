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

import org.nabucco.framework.base.facade.datatype.visitor.Visitable;

/**
 * Validatable
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface Validatable extends Visitable {

    /**
     * Validate this datatype and fills the result with errors/warnings.
     * 
     * @param result
     *            the result to append with errors/warnings
     * @param depth
     *            the depth of a validation
     * 
     * @throws ValidationException
     */
    void validate(ValidationResult result, ValidationType depth) throws ValidationException;

}
