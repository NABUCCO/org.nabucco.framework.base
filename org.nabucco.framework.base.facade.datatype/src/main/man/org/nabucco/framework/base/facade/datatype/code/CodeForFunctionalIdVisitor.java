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

import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * CodeForFunctionalIdVisitor
 * 
 * @author Silas Schwarz, PRODYNA AG
 */
class CodeForFunctionalIdVisitor extends CodeFacadeVisitor<Code> {

    private String functionalId;

    /**
     * Creates a new {@link CodeForFunctionalIdVisitor} instance.
     * 
     * @param functionalId
     *            the functional ID
     */
    CodeForFunctionalIdVisitor(String functionalId) {
        this.functionalId = functionalId;
    }

    @Override
    protected void visitCode(Code code) throws VisitorException {
        if (code.getFunctionalId() != null && code.getFunctionalId().getValue() != null) {
            if (code.getFunctionalId().getValue().compareTo(functionalId) == 0) {
                setResult(code);
            }
        }
    }

    @Override
    protected boolean isDone() {
        boolean result = super.isDone() || getCurrentDepth() > 1;
        return result;
    }

}
