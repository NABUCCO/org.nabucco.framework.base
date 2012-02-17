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
package org.nabucco.framework.base.facade.datatype.code;

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.visitor.DatatypeVisitor;
import org.nabucco.framework.base.facade.datatype.visitor.Visitable;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * CodeFacadeVisitor
 * 
 * @author Silas Schwarz, PRODYNA AG
 */
abstract class CodeFacadeVisitor<R> extends DatatypeVisitor {

    private R result;

    private int currentDepth = 0;

    /**
     * Creates a new {@link CodeFacadeVisitor} instance.
     */
    CodeFacadeVisitor() {
    }

    /**
     * @param result
     *            The result to set.
     */
    protected void setResult(R result) {
        this.result = result;
    }

    /**
     * @return Returns the result.
     */
    R getResult() {
        return result;
    }

    /**
     * Returns the current iteration depth.
     * 
     * @return Returns the currentDepth.
     */
    protected int getCurrentDepth() {
        return currentDepth;
    }

    @Override
    public final void visit(Datatype datatype) throws VisitorException {
        if (datatype instanceof CodeGroup) {
            CodeGroup codeGroup = (CodeGroup) datatype;
            visitCodeGroup(codeGroup);
        } else if (datatype instanceof Code) {
            Code code = (Code) datatype;
            visitCode(code);
        }
        if (!isDone()) {
            currentDepth += 1;
            super.visit(datatype);
        }
    }

    /**
     * To spot iterating the default behavior is to check it the Result object is not null.
     * 
     * @return <code>false</code> if the {@link #result} field is set. <code>true</code> otherwise.
     */
    protected boolean isDone() {
        if (getResult() != null) {
            return true;
        }
        return false;
    }

    protected void visitCode(Code code) throws VisitorException {
        return;
    }

    protected void visitCodeGroup(CodeGroup codeGroup) throws VisitorException {
        return;

    }

    @Override
    public void visit(Basetype basetype) throws VisitorException {
        super.visit(basetype);
    }

    @Override
    public final void visit(Visitable visitable) throws VisitorException {
        super.visit(visitable);
    }

    @Override
    protected final void visitChildren(Visitable visitable) throws VisitorException {
        super.visitChildren(visitable);
    }

}
