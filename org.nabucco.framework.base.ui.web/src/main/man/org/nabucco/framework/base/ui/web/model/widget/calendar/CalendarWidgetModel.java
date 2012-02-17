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
package org.nabucco.framework.base.ui.web.model.widget.calendar;

import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.widget.WidgetModel;
import org.nabucco.framework.base.ui.web.model.widget.WidgetType;
import org.nabucco.framework.base.ui.web.model.widget.image.ImageWidgetModel;

/**
 * CalendarWidgetModel
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class CalendarWidgetModel extends WidgetModel {

    private static final String JSON_DISPLAY_TYPE = "displayType";

    private CalendarWidgetDisplayType displayType;

    /**
     * Creates a new {@link ImageWidgetModel} instance.
     * 
     * @param image
     *            text to show
     * @param link
     *            link for the text
     */
    public CalendarWidgetModel(CalendarWidgetDisplayType type) {
        super(WidgetType.CALENDAR);
        this.setDisplayType(type);
    }

    /**
     * Setter for the type.
     * 
     * @param type
     *            The type to set.
     */
    public void setDisplayType(CalendarWidgetDisplayType type) {
        this.displayType = type;
    }

    /**
     * Getter for the type.
     * 
     * @return Returns the type.
     */
    public CalendarWidgetDisplayType getDisplayType() {
        return this.displayType;
    }

    @Override
    public JsonMap toJson() {
        JsonMap retVal = super.toJson();
        retVal.add(JSON_DISPLAY_TYPE, this.getDisplayType());
        return retVal;
    }
}
