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
package org.nabucco.framework.base.facade.datatype.validation.constraint.dynamic;

import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.validation.constraint.element.Constraint;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * DynamicConstraintContainer
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface DynamicConstraintContainer {

    /**
     * Add a dynamic constraint to the owning element.
     * 
     * @param constraint
     *            the constraint to add
     * @param derive
     *            <b>true</b> derives the constraint recursively through the datatype tree,
     *            <b>false</b> not
     * 
     * @throws VisitorException
     *             when the constraint cannot be added recursively
     */
    void addConstraint(Constraint constraint, boolean derive) throws VisitorException;

    /**
     * Remove a dynamic constraint from the owning element.
     * 
     * @param constraint
     *            the constraint to remove
     * @param derive
     *            <b>true</b> derives the constraint recursively through the datatype tree,
     *            <b>false</b> not
     * 
     * @throws VisitorException
     *             when the constraint cannot be added recursively
     */
    void removeConstraint(Constraint constraint, boolean derive) throws VisitorException;

    /**
     * Add a dynamic constraint to a property.
     * 
     * @param property
     *            the property to add the constraint to
     * @param constraint
     *            the constraint to add to the property
     */
    void addConstraint(NabuccoPropertyDescriptor property, Constraint constraint);

    /**
     * Remove a dynamic constraint from a property.
     * 
     * @param property
     *            the property to remove the constraint from
     * @param constraint
     *            the constraint to remove from the property
     */
    void removeConstraint(NabuccoPropertyDescriptor property, Constraint constraint);

    /**
     * Whether the dynamic constraint container is editable.
     * 
     * @return <b>true</b> if the element is editable, <b>false</b> if not
     */
    boolean editable();

    /**
     * Whether the dynamic constraint container is visible.
     * 
     * @return <b>true</b> if the element is visible, <b>false</b> if not
     */
    boolean visible();

}
