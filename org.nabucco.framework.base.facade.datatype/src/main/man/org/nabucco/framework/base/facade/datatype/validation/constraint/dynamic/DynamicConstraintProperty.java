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

/**
 * DynamicConstraint
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface DynamicConstraintProperty {

    /**
     * Getter for the property name.
     * 
     * @return the name of the dynamic constraint property
     */
    String getName();

    /**
     * Checks whether an property is editable or not.
     * 
     * @return <b>true</b> if the property is editable, <b>false</b> if not
     */
    boolean isEditable();

    /**
     * Checks whether an property is visible or not.
     * 
     * @return <b>true</b> if the property is visible, <b>false</b> if not
     */
    boolean isVisible();

}
