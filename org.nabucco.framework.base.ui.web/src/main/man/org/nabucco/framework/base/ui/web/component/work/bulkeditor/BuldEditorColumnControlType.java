/*
 * Copyright 2012 PRODYNA AG
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
package org.nabucco.framework.base.ui.web.component.work.bulkeditor;

/**
 * BuldEditorColumnType
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public enum BuldEditorColumnControlType {
    /**
     * Text field
     */
    TEXT,

    /**
     * Drop drown field for codes or enumerations
     */
    DROPDOWN,

    /**
     * The date field with date picker
     */
    DATE,

    /**
     * The picker field
     */
    PICKER

}
