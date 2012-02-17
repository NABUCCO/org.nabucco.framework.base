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
 * MultiplicityType
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public enum MultiplicityType {

    /**
     * Optional single multiplicity.
     */
    ZERO_TO_ONE("0..1"),

    /**
     * Mandatory single multiplicity.
     */
    ONE("1"),

    /**
     * Optional list multiplicity.
     */
    ZERO_TO_MANY("0..*"),

    /**
     * Mandatory list multiplicity..
     */
    ONE_TO_MANY("1..*"),

    /**
     * Custom multiplicity.
     */
    N_TO_M("n..m");

    /**
     * Creates a new {@link MultiplicityType} instance.
     * 
     * @param name
     *            the multiplicity as string
     */
    private MultiplicityType(String name) {
        this.name = name;
    }

    private String name;

    /**
     * Getter for the multiplicity name.
     * 
     * @return Returns the name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Checks whether the multiplicity is multiple or not.
     * 
     * @return <b>true</b> if the multiplicity allows multiple entries, <b>false</b> if not
     */
    public boolean isOptional() {
        switch (this) {
        case ZERO_TO_ONE:
            return true;
        case ZERO_TO_MANY:
            return true;
        case N_TO_M:
            return true;
        }
        return false;
    }

    /**
     * Checks whether the multiplicity is multiple or not.
     * 
     * @return <b>true</b> if the multiplicity allows multiple entries, <b>false</b> if not
     */
    public boolean isMultiple() {
        switch (this) {
        case ZERO_TO_MANY:
            return true;
        case ONE_TO_MANY:
            return true;
        case N_TO_M:
            return true;
        }
        return false;
    }

    /**
     * Parse the string value to the appropriate multiplicity type.
     * 
     * @param value
     *            the string value to parse
     * 
     * @return the multiplicity type
     */
    public static MultiplicityType parse(String value) {
        for (MultiplicityType type : MultiplicityType.values()) {
            if (type.getName().equalsIgnoreCase(value)) {
                return type;
            }
        }
        return MultiplicityType.ZERO_TO_MANY;
    }

    /**
     * Convert the multiplicity type into a multiplicity constraint string.
     * 
     * @return the multiplicity constraint string
     */
    public String toConstraintString() {

        switch (this) {
        case ZERO_TO_ONE:
            return "m0,1";

        case ONE:
            return "m1,1";

        case ZERO_TO_MANY:
            return "m0,n";

        case ONE_TO_MANY:
            return "m1,n";
        }

        return null;
    }

}
