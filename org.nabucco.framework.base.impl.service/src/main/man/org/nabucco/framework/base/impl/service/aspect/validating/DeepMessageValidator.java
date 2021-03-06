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
package org.nabucco.framework.base.impl.service.aspect.validating;

import org.nabucco.framework.base.facade.datatype.extension.schema.property.PropertyExtension;
import org.nabucco.framework.base.facade.datatype.validation.ValidationResult;
import org.nabucco.framework.base.facade.datatype.validation.ValidationType;
import org.nabucco.framework.base.facade.exception.validation.ValidationException;
import org.nabucco.framework.base.facade.message.ServiceMessage;

/**
 * DeepMessageValidator
 * <p/>
 * Validates the whole datatype tree.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class DeepMessageValidator implements ValidatingAspect {

    @Override
    public void validateBefore(ServiceMessage message) throws ValidatingException {

        try {
            ValidationResult vr = new ValidationResult();
            message.validate(vr, ValidationType.DEEP);

            if (!vr.isEmpty()) {
                throw new ValidatingException(new ValidationException("Error validating service request.", vr));
            }

        } catch (org.nabucco.framework.base.facade.datatype.validation.ValidationException ve) {
            throw new ValidatingException(ve);
        }
    }

    @Override
    public void validateAfter(ServiceMessage requestMessage, ServiceMessage responseMessage) throws ValidatingException {
    }

    @Override
    public void configure(PropertyExtension properties) {
    }

}
