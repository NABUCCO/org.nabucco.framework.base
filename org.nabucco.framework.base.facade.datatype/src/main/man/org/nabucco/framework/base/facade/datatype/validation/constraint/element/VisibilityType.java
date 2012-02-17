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
 * VisibilityType
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public enum VisibilityType {

    /**
     * The property is visible.
     */
    VISIBLE(1),

    /**
     * The property is not visible.
     */
    INVISIBLE(0),

    /**
     * The property is visible but obfuscated.
     */
    OBFUSCATED(2);

    private int id;

    /**
     * Creates a new {@link VisibilityType} instance.
     * 
     * @param id
     *            the visiblity id
     */
    private VisibilityType(int id) {
        this.id = id;
    }

    /**
     * Getter for the id.
     * 
     * @return Returns the id.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Whether the property is visible or not.
     * 
     * @return <b>true</b> if it is visible, <b>false</b> if not
     */
    public boolean isVisible() {
        if (this == INVISIBLE) {
            return false;
        }
        return true;
    }

}
