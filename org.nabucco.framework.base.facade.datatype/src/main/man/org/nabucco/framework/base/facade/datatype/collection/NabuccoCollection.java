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
package org.nabucco.framework.base.facade.datatype.collection;

import java.io.Serializable;
import java.util.Collection;

import org.nabucco.framework.base.facade.datatype.NType;

/**
 * NabuccoCollection
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface NabuccoCollection<E extends NType> extends Collection<E>, Serializable {

    /**
     * Getter for the current collection state.
     * 
     * @return the state
     */
    NabuccoCollectionState getState();

    /**
     * Setter for the current collection state.
     * 
     * @param state
     *            the state to set
     */
    void setState(NabuccoCollectionState state);

    /**
     * Removes all of the elements from this collection. The collection will be empty after this
     * method returns.
     * <p/>
     * Also the internal assignment and unassignment lists are cleared.
     */
    void clearAll();

    /**
     * Clones all elements of the collection deeply.
     * 
     * @return the cloned collection
     */
    NabuccoCollection<E> cloneCollection();

    /**
     * Indicates whether a {@link NabuccoCollection} is managed (connected to the DB) or not.
     * 
     * @return <b>true</b> if the collection is managed, <b>false</b> if not.
     */
    boolean isManaged();

}
