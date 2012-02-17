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
package org.nabucco.framework.base.facade.datatype.validation.constraint.element;

/**
 * ConstraintType
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public enum ConstraintType {

    /**
     * Defines whether a property is editable or not.
     */
    EDIT("Editable Constraint"),

    /**
     * Defines the min/max length of a textual property.
     */
    LENGTH("Length Constraint"),

    /**
     * Defines the min/max multiplicity of a property.
     */
    MULTIPLICITY("Multiplicity Constraint"),

    /**
     * Defines a pattern for a property content.
     */
    PATTERN("Pattern Constraint"),

    /**
     * Defines the min/max values of a number property.
     */
    VALUE("Value Constraint"),

    /**
     * Defines whether a property is visible or not.
     */
    VISIBILITY("Visibility Constraint");

    private String name;

    /**
     * Creates a new {@link ConstraintType} instance.
     * 
     * @param name
     *            the constraint name
     */
    private ConstraintType(String name) {
        this.name = name;
    }

    /**
     * Getter for the constraint name.
     * 
     * @return the name
     */
    public String getName() {
        return this.name;
    }

}
