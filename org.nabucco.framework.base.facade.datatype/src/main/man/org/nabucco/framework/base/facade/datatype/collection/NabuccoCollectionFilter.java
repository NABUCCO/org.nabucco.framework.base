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

import org.nabucco.framework.base.facade.datatype.NType;

/**
 * Filter for {@link NabuccoCollection} instances that allow a to filter a given collection to a new
 * collection.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface NabuccoCollectionFilter<E extends NType> {

    /**
     * Check whether the given element is part of the filtered collection or not.
     * 
     * @param element
     *            the element to filter
     * 
     * @return <b>true</b> if the element must be part of the filtered collection, <b>false</b> if
     *         the element must be filtered out
     */
    boolean filter(E element);

}
