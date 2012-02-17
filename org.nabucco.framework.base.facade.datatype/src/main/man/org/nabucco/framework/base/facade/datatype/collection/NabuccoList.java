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
package org.nabucco.framework.base.facade.datatype.collection;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.NType;

/**
 * NabuccoList
 * <p/>
 * Ordered collections in NABUCCO.
 * 
 * @param <E>
 *            the collection element type
 * 
 * @see List
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface NabuccoList<E extends NType> extends NabuccoCollection<E>, List<E> {

    /**
     * Returns the first element of the list when the list is neither empty nor LAZY.
     * 
     * @return the first list element, or null if the list is empty.
     */
    E first();

    /**
     * Returns the last element of the list when the list is neither empty nor LAZY.
     * 
     * @return the last list element, or null if the list is empty.
     */
    E last();

    @Override
    NabuccoList<E> cloneCollection();

}
