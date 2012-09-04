/*
 * Copyright 2012 PRODYNA AG
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
package org.nabucco.framework.base.facade.datatype.property;

import org.nabucco.framework.base.facade.datatype.Datatype;

/**
 * PropertyAccessor
 * 
 * Accesses the property of the given datatype
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public interface PropertyAccessor {

    /**
     * Getter for property of the given datatype.
     * 
     * @param propertyPath
     *            the path to the property
     * @param datatype
     *            the datatype holding the property
     * 
     * @return the referenced property
     */
    NabuccoProperty resolveProperty(String propertyPath, Datatype datatype);
}
