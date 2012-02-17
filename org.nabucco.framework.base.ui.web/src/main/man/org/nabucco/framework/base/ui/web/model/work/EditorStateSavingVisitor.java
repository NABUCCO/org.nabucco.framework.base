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

import java.util.HashMap;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.visitor.DatatypeVisitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * EditorStateSavingVisitor
 * <p/>
 * Saves the actually state of the net and make it possible to repair the state after needed
 * manipulation
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
class EditorStateSavingVisitor extends DatatypeVisitor {

    /** The saved datatype states by the datatypes instance id. */
    private final Map<Integer, DatatypeState> stateMap = new HashMap<Integer, DatatypeState>();

    /** Enables saving/applying of datatype states. */
    private boolean savingMode;

    @Override
    public void visit(Datatype datatype) throws VisitorException {
        if (datatype == null) {
            return;
        }

        int instanceId = System.identityHashCode(datatype);
        if (this.savingMode) {
            this.stateMap.put(instanceId, datatype.getDatatypeState());
        } else {
            DatatypeState state = this.stateMap.get(instanceId);
            if (state == null) {
                throw new VisitorException("Datatype State has not been saved before.");
            }
            datatype.setDatatypeState(state);
        }

        super.visit(datatype);
    }

    /**
     * Save the datatype states recursively into this visitor.
     * 
     * @param datatype
     *            the root datatype to be saved
     */
    public void save(Datatype datatype) {
        try {
            this.savingMode = true;
            datatype.accept(this);
        } catch (VisitorException ve) {
            throw new IllegalStateException("Error saving datatype states.", ve);
        }
    }

    /**
     * Apply the saved datatype states recurisvely into the given datatype net.
     * 
     * @param datatype
     *            the root datatype to apply the state to
     * 
     * @throws IllegalStateException
     *             when the saved datatype net does not fit
     */
    public void apply(Datatype datatype) {
        try {
            this.reset();
            this.savingMode = false;
            datatype.accept(this);
        } catch (VisitorException ve) {
            throw new IllegalStateException("Error applying datatype states.", ve);
        }
    }
}
