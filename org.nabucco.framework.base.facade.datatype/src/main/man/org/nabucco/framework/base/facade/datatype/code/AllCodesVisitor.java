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

import java.util.Arrays;

import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * CodesVisitor
 * 
 * @author Silas Schwarz, PRODYNA AG
 */
public class AllCodesVisitor extends CodeFacadeVisitor<Code[]> {

    /**
     * Creates a new {@link AllCodesVisitor} instance.
     */
    public AllCodesVisitor() {
        setResult(new Code[] {});
    }

    @Override
    protected boolean isDone() {
        return getCurrentDepth() == 1;
    }

    @Override
    protected void visitCodeGroup(CodeGroup codeGroup) throws VisitorException {

    }

    @Override
    protected void visitCode(Code code) throws VisitorException {
        int newlength = getResult().length + 1;
        Code[] newResult = Arrays.copyOf(getResult(), newlength);
        newResult[newlength - 1] = code;
        setResult(newResult);
    }

}
