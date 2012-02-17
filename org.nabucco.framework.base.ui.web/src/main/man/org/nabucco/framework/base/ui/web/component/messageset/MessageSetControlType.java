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
package org.nabucco.framework.base.ui.web.component.messageset;

/**
 * MessageSetControlType
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public enum MessageSetControlType {

    /**
     * Checkbox for booleans.
     */
    CHECKBOX("checkbox"),

    /**
     * Drop Down for enumerations.
     */
    DROP_DOWN("dropdown"),

    /**
     * Datefield for dates
     */
    DATE("date"),

    /**
     * Radio Button Group for enumerations.
     */
    RADIO("radio"),

    /**
     * Text input field.
     */
    TEXT("text"),

    /**
     * Currency input field.
     */
    CURRENCY("currency"),

    /**
     * PICKER
     */
    PICKER("picker"),

    /**
     * Multirow text field
     */
    TEXTAREA("textarea"),

    /**
     * Nothing selected
     */
    NONE("*");

    String value;

    /**
     * 
     * Creates a new {@link MessageSetControlType} instance.
     * 
     * @param value
     *            value to use
     */
    private MessageSetControlType(String value) {
        this.value = value;
    }

    /**
     * returns value of enum
     * 
     * @return value of enum
     */
    public String getValue() {
        return this.value;
    }
}
