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
package org.nabucco.framework.base.facade.datatype;

import org.nabucco.framework.base.facade.datatype.property.PropertyOwner;
import org.nabucco.framework.base.facade.datatype.validation.Validatable;

/**
 * A primitive type wrapper that owns a single value.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface Basetype extends NType, PropertyOwner, Validatable {

    /**
     * Getter for the basetype value.
     * 
     * @return the value
     */
    Object getValue();

    /**
     * Setter for the basetype value.
     * 
     * @param value
     *            the value as {@link Object}.
     * 
     * @throws IllegalArgumentException
     *             if the value is not of the expected primitive type
     */
    void setValue(Object value) throws IllegalArgumentException;

    /**
     * Getter for the basetype value as String.
     * 
     * @return the value as string
     */
    String getValueAsString();

    /**
     * Getter for the type of the basetype.
     * 
     * @return the basetype type
     */
    BasetypeType getType();

    @Override
    Basetype cloneObject();

}
