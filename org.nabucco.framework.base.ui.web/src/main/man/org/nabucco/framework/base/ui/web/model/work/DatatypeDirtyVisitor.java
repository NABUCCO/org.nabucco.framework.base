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
package org.nabucco.framework.base.ui.web.model.work;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.visitor.ExtendedDatatypeVisitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * DatatypeDirtyVisitor
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DatatypeDirtyVisitor extends ExtendedDatatypeVisitor {

    private boolean dirty = false;

    @Override
    public void visit(Datatype datatype) throws VisitorException {
        if (datatype == null) {
            return;
        }

        switch (datatype.getDatatypeState()) {

        case INITIALIZED:
            this.dirty = true;
            break;

        case MODIFIED:
            this.dirty = true;
            break;

        case DELETED:
            this.dirty = true;
            break;

        }

        if (!this.dirty) {
            super.visit(datatype);
        }
    }

    /**
     * Getter for the dirty.
     * 
     * @return Returns the dirty.
     */
    public boolean isDirty() {
        return this.dirty;
    }

}
