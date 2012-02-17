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

import org.nabucco.framework.base.facade.datatype.visitor.Visitable;

/**
 * NabuccoProperty
 * <p/>
 * A property defines dynamic reflective information about Basetype, Enumeration, Datatype and
 * ServiceMessage attributes.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface NabuccoProperty extends NabuccoPropertyDescriptor, Visitable {

    /**
     * Getter for the parent datatype which is holding this property.
     * 
     * @return the parent datatype
     */
    PropertyOwner getParent();

    /**
     * Getter for the concrete property instance.
     * 
     * @return Returns the instance.
     */
    Object getInstance();

    /**
     * Getter for the refId.
     * 
     * @return Returns the refId.
     */
    Long getReferenceId();

    /**
     * Creates the related property for the given instance.
     * 
     * @param instance
     *            the dynamic property instance
     * 
     * @return the new property instance
     */
    NabuccoProperty createProperty(Object instance);

}
