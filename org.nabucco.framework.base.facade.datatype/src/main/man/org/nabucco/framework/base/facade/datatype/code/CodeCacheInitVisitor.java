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

import org.nabucco.common.cache.BasicCache;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.visitor.DatatypeVisitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * Visitor initializing the code cache.
 * 
 * @author Silas Schwarz, PRODYNA AG
 */
class CodeCacheInitVisitor extends DatatypeVisitor {

    private static final String PATH_SEPARATOR = ".";

    /** The cache */
    private final BasicCache<CodeGroup> cache;

    /** The cache */
    private String path;

    /**
     * Creates a new {@link CodeCacheInitVisitor} instance.
     * 
     * @param cache
     *            the code cache
     */
    CodeCacheInitVisitor(BasicCache<CodeGroup> cache) {
        this.cache = cache;
    }

    @Override
    public void visit(Datatype datatype) throws VisitorException {
        if (datatype instanceof CodeGroup) {
            CodeGroup codeGroup = (CodeGroup) datatype;

            String token = getToken(codeGroup);
            this.cache.store(this.path, codeGroup, CodeFacade.CACHE_TIMEOUT);

            super.visit(datatype);

            this.path = this.path.substring(0, this.path.lastIndexOf(token));
        }
    }

    /**
     * Resolve the current token of the path.
     * 
     * @param codeGroup
     *            the current code group
     * 
     * @return the token of the given code group
     */
    private String getToken(CodeGroup codeGroup) {
        String token = codeGroup.getName().getValue();
        if (this.path == null) {
            this.path = token;
        } else {
            token = PATH_SEPARATOR + token;
            this.path = this.path + token;
        }
        return token;
    }
}
