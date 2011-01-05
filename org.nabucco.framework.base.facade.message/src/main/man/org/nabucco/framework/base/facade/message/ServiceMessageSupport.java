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
package org.nabucco.framework.base.facade.message;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.validation.ValidationException;
import org.nabucco.framework.base.facade.datatype.validation.ValidationResult;
import org.nabucco.framework.base.facade.datatype.validation.ValidationType;
import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.message.validation.MessageConstraintValidationVisitor;

/**
 * ServiceMessageSupport
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "";
    }

    @Override
    public void validate(ValidationResult result, ValidationType depth) throws ValidationException {
        if (result == null) {
            throw new IllegalArgumentException("Validation result is not valid [null].");
        }
        if (depth == null) {
            depth = ValidationType.DEEP;
        }

        try {
            MessageConstraintValidationVisitor visitor = new MessageConstraintValidationVisitor(
                    result, depth);
            this.accept(visitor);
        } catch (VisitorException e) {
            throw new ValidationException("Error visiting message for validation.", e);
        }
    }

    @Override
    public void accept(Visitor visitor) throws VisitorException {
        visitor.visit(this);
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        return new ArrayList<NabuccoProperty<?>>();
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

}
