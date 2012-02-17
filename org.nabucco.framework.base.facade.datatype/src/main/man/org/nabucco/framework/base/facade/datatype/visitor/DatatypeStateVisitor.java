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
package org.nabucco.framework.base.facade.datatype.visitor;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeState;

/**
 * DatatypeStateVisitor
 * <p/>
 * Visitor traversing a datatype tree and setting the given datatype state recursively to each
 * visited datatatype node.
 * 
 * @see Datatype
 * @see DatatypeState
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class DatatypeStateVisitor extends DatatypeVisitor {

    /** The datatype state to set. */
    private DatatypeState state;

    /**
     * Creates a new {@link DatatypeStateVisitor} instance.
     * 
     * @param state
     *            the datatype state to set for each datatype
     */
    public DatatypeStateVisitor(DatatypeState state) {
        if (state == null) {
            throw new IllegalArgumentException("DatatypeState must not be [null].");
        }
        this.state = state;
    }

    @Override
    public void visit(Datatype datatype) throws VisitorException {
        super.visit(datatype);
        datatype.setDatatypeState(this.state);
    }

}
