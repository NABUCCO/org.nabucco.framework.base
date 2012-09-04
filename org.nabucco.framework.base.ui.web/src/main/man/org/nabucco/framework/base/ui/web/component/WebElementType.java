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
package org.nabucco.framework.base.ui.web.component;

/**
 * Type of Web Elements.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public enum WebElementType {

    APPLICATION("application"),

    TITLEBAR("titlebar"),

    STATUSBAR("statusbar"),

    PERSPECTIVE_AREA("perspectivearea"),

    PERSPECTIVE("perspective"),

    NAVIGATION_AREA("navigationarea"),

    NAVIGATOR("navigator"),

    BROWSER_AREA("browserarea"),

    BROWSER("browser"),

    DIALOG("dialog"),

    PICKERDIALOG("pickerdialog"),

    CONTROL("control"),

    CONTROLLER("controller"),

    PICKER("picker"),

    WORK_AREA("workarea"),

    ERROR("error"),

    EDITOR("editor"),

    BULKEDITOR("bulkeditor"),

    BULKEDITOR_CONTROL("bulkeditorcontrol"),

    BULKEDITOR_COLUMN("bulkeditorcolumn"),

    EDITOR_EDIT_AREA("edit"),

    EDITOR_EDIT_TAB("tab"),

    EDITOR_RELATION_AREA("relation"),

    EDITOR_CONTROL("control"),

    EDITOR_RELATION_TAB("tab"),

    LIST("list"),

    LINK("link"),

    LABEL("label"),

    DASHBOARD("dashboard"),

    MENUBUTTON("menubutton"),

    BUTTON("button"),

    BUTTONGROUP("buttongroup"),

    WIDGET("widget"),

    GRID("grid"),

    DIRTYFLAG("dirtyflag"),

    MESSAGEQUEUE("messagequeue"),

    WORKFLOW("workflow");

    private String name;

    /**
     * Creates a new {@link WebElementType} instance.
     * 
     * @param name
     *            the element name
     */
    private WebElementType(String name) {
        this.name = name;
    }

    /**
     * Getter for the name.
     * 
     * @return Returns the name.
     */
    public String getName() {
        return this.name;
    }

}
