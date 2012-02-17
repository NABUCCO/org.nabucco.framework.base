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
package org.nabucco.framework.base.facade.datatype.property;

import org.nabucco.framework.base.facade.datatype.validation.constraint.parser.ConstraintContainer;

/**
 * NabuccoPropertyDescriptor
 * <p/>
 * A property descriptor defines static reflective information about Basetype, Enumeration, Datatype
 * and ServiceMessage attributes.
 * 
 * @author Oliver Teichmann, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public interface NabuccoPropertyDescriptor {

    /**
     * Getter for the name of the property.
     * 
     * @return Returns the name.
     */
    String getName();

    /**
     * Getter for the type of the property.
     * 
     * @return Returns the type.
     */
    Class<?> getType();

    /**
     * Getter for the index of the property in its parent.
     * 
     * @return Returns the index.
     */
    int getIndex();

    /**
     * Getter for the constraints of the property.
     * 
     * @return the constraint container holding the constraints
     */
    ConstraintContainer getConstraints();

    /**
     * Getter for the property type.
     * 
     * @return Returns the propertyType.
     */
    NabuccoPropertyType getPropertyType();

    /**
     * Getter for the property association type.
     * 
     * @return the association type
     */
    PropertyAssociationType getAssociationType();

    /**
     * Getter for the code path as string.
     * 
     * @return the code path or null if no is defined
     */
    String getCodePath();

    /**
     * Getter for the technical flag. Technical properties (id, version, etc.) must be
     * differenciated from functional properties.
     * 
     * @return <b>true</b> if the property is technial, <b>false</b> if not
     */
    boolean isTechnical();

    /**
     * Check whether the property holds a collection or a single value.
     * 
     * @return <b>true</b> if the property holds a collection, <b>false</b> otherwise
     */
    boolean isCollection();

    /**
     * Creates the related property for the given instance.
     * 
     * @param parent
     *            the property parent
     * @param instance
     *            the dynamic property instance
     * @param constraints
     *            the dynamic property constraints
     * 
     * @return the new property instance
     */
    NabuccoProperty createProperty(PropertyOwner parent, Object instance, String constraints);

    /**
     * Creates the related property for the given instance.
     * 
     * @param parent
     *            the property parent
     * @param instance
     *            the dynamic property instance
     * @param constraints
     *            the dynamic property constraints
     * @param refId
     *            the reference id
     * 
     * @return the new property instance
     */
    NabuccoProperty createProperty(PropertyOwner parent, Object instance, String constraints, Long refId);

}
