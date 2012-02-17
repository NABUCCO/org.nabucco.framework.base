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
package org.nabucco.framework.base.ui.web.model.widget;

/**
 * WidgetType
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public enum WidgetType {
    /**
     * Text widgets that can only show a given text/link
     */
    TEXT,

    /**
     * Indicator widgets can show indicatiors of errors, busy etc.
     */
    INDICATOR,

    /**
     * Dynamictext widgets shows some special dynamic text like version etc.
     */
    DYNAMICTEXT,

    /**
     * Action widget is used to run some system action
     */
    ACTION,

    /**
     * Search widget is used for searchfield and functionality
     */
    SEARCH,

    /**
     * Calendar widget to show some calendar actions on the client
     */
    CALENDAR,

    /**
     * Widget that opens a configured dialog on click
     */
    DIALOG,

    /**
     * Image widget is only used to show images
     */
    IMAGE;

}
