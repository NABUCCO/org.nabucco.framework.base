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
package org.nabucco.framework.base.facade.datatype.validation.constraint;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.Constraint;
import org.nabucco.framework.base.facade.datatype.visitor.DatatypeVisitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * ConstraintVisitor
 * <p/>
 * Recursively adds a constraint to a datatype tree.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class ConstraintVisitor extends DatatypeVisitor {

    private Constraint constraint;

    /**
     * Creates a new {@link ConstraintVisitor} instance.
     * 
     * @param constraint
     *            the constraint to add to each datatype node
     */
    public ConstraintVisitor(Constraint constraint) {
        if (constraint == null) {
            throw new IllegalArgumentException("Cannot create Visitor for constraint null.");
        }
        this.constraint = constraint;
    }

    @Override
    public void visit(Datatype datatype) throws VisitorException {
        datatype.addConstraint(this.constraint, false);

        super.visit(datatype);
    }

}
