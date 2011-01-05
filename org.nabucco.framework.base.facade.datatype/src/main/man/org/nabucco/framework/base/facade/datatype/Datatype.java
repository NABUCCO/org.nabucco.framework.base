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
package org.nabucco.framework.base.facade.datatype;

import org.nabucco.framework.base.facade.datatype.validation.Validatable;
import org.nabucco.framework.base.facade.datatype.visitor.Visitable;

/**
 * Datatype
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface Datatype extends NType, Validatable, Visitable {

    /**
     * Initialize the datatype with default values.
     */
    public void init();

    /**
     * Getter for the current {@link DatatypeState}.
     * 
     * @return the state.
     */
    public DatatypeState getDatatypeState();

    /**
     * Setter for the current {@link DatatypeState}.
     * 
     * @param newState
     *            the new state.
     */
    public void setDatatypeState(DatatypeState newState);

    @Override
    public Datatype cloneObject();

}
