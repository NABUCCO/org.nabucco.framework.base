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
package org.nabucco.framework.base.ui.web.component.work.dashboard.widget;

/**
 * DashboardWidgetType
 * 
 * The types of dashboard widgets
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public enum DashboardWidgetType {
    TABLE("table"),

    PIECHART("piechart"),

    BARGRAPH("bargraph");
    
    private String value;
    
    /**
     * 
     * Creates a new {@link DashboardWidgetType} instance.
     *
     * @param value
     */
    private DashboardWidgetType(String value){
        this.setValue(value);
    }

    /**
     * Setter for the value.
     * 
     * @param value The value to set.
     */
    private void setValue(String value) {
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
}
