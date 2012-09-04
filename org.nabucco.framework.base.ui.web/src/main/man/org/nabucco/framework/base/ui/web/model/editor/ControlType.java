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
package org.nabucco.framework.base.ui.web.model.editor;

/**
 * ControlType
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public enum ControlType {
    /**
     * Simple Label
     */
    LABEL,

    /**
     * Checkbox for booleans.
     */
    CHECKBOX,

    /**
     * Drop Down for enumerations.
     */
    DROPDOWN,

    /**
     * Datefield for dates
     */
    DATE,

    /**
     * Radio Button Group for enumerations.
     */
    RADIO,

    /**
     * Text input field.
     */
    TEXT,

    /**
     * Currency input field.
     */
    CURRENCY,

    /**
     * UI Link
     */
    LINK,

    /**
     * PICKER
     */
    PICKER,

    /**
     * Multirow text field
     */
    TEXTAREA,

    /**
     * Image element
     */
    IMAGE,

    /**
     * Password field
     */
    PASSWORD,

    /**
     * File Upload control
     */
    FILE,

    /**
     * Text File uploading control
     */
    TEXTFILE

}
