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

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.visitor.DatatypeVisitor;
import org.nabucco.framework.base.facade.datatype.visitor.Visitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * DatatypeValidationVisitor
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class DatatypeValidationVisitor extends DatatypeVisitor implements Visitor {

    private ValidationResult result;

    private final ValidationType depth;

    /**
     * Creates a new {@link DatatypeValidationVisitor} instance.
     * 
     * @param result
     *            the validation result containing validation errors
     * @param depth
     *            the validation depth
     */
    public DatatypeValidationVisitor(ValidationResult result, ValidationType depth) {
        if (result == null) {
            throw new IllegalArgumentException("ValidationResult [null] is not valid.");
        }
        this.result = result;
        this.depth = depth;
    }

    @Override
    public void visit(Datatype datatype) throws VisitorException {

        for (NabuccoProperty property : datatype.getProperties()) {
            property.getConstraints().check(datatype, property, this.result);
        }

        if (this.depth != ValidationType.SHALLOW) {
            super.visit(datatype);
        }
    }

}
