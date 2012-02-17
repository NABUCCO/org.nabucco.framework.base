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
package org.nabucco.framework.base.ui.web.servlet.util.path;

/**
 * NabuccoServletPathEntryType
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public enum NabuccoServletPathType {

    APPLICATION("application"),

    HEADER("header"),

    FOOTER("footer"),

    DIALOG("dialog"),

    PERSPECTIVE_AREA("perspectivearea"),

    PERSPECTIVE("perspective"),

    NAVIGATION_AREA("navigationarea"),

    NAVIGATOR("navigator"),

    BROWSER_AREA("browserarea"),

    BROWSER("browser"),

    WORK_AREA("workarea"),

    EDITOR("editor"),

    LIST("list"),

    DASHBOARD("dashboard"),

    AREA("area"),

    TAB("tab"),

    TABLE("table"),

    ENTRY("entry"),

    CONTROL("control"),

    WIDGET("widget"),

    ACTION("action"),
    
    WORKFLOW("workflow"),
    
    SIGNAL("signal"),

    INSTANCE("instance"),

    ROW("row"),
    
    PICKER("picker"),

    FILTERID("filterId"),

    PICKER_DIALOG("pickerDialog"),

    VALUE("value"),

    FILE("file"),

    PARAMETER("parameter"),

    ID("id");

    private String value;

    /**
     * Creates a new {@link NabuccoServletPathType} instance.
     * 
     * @param value
     *            the path entry value
     */
    private NabuccoServletPathType(String value) {
        this.value = value;
    }

    /**
     * Getter for the value.
     * 
     * @return Returns the value.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Resolve the servlet path entry type for the given string value.
     * 
     * @param value
     *            the string value to resolve
     * 
     * @return the appropriate type
     */
    public static NabuccoServletPathType type(String value) {

        for (NabuccoServletPathType type : NabuccoServletPathType.values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
        }

        return ID;
    }

}
