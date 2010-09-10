/*
 * Copyright 2010 PRODYNA AG
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
package org.nabucco.framework.base.facade.datatype.validation.constraint.element;

/**
 * MultiplicityType
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public enum MultiplicityType {

    ZERO_TO_ONE("0..1"),

    ONE("1"),

    ZERO_TO_MANY("0..*"),

    ONE_TO_MANY("1..*");

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
        }
        return false;
    }

}
