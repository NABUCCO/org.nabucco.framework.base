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
package org.nabucco.framework.base.ui.web.model.widget.indicator;

import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.widget.WidgetModel;
import org.nabucco.framework.base.ui.web.model.widget.WidgetType;

/**
 * IndicatorWidgetModel
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class IndicatorWidgetModel extends WidgetModel {

    private static final String JSON_INDICATION = "indication";
    private IndicatorWidgetType indicatorType;

    /**
     * Creates a new {@link IndicatorWidgetModel} instance.
     * 
     */
    public IndicatorWidgetModel(IndicatorWidgetType indicatorType) {
        super(WidgetType.INDICATOR);
        this.setIndicatorType(indicatorType);
    }

    /**
     * Setter for the indicatorType.
     * 
     * @param indicatorType
     *            The indicatorType to set.
     */
    private void setIndicatorType(IndicatorWidgetType indicatorType) {
        this.indicatorType = indicatorType;
    }

    /**
     * Getter for the indicatorType.
     * 
     * @return Returns the indicatorType.
     */
    private IndicatorWidgetType getIndicatorType() {
        return this.indicatorType;
    }

    @Override
    public JsonMap toJson() {
        JsonMap retVal = super.toJson();
        retVal.add(JSON_INDICATION, this.getIndicatorType());
        return retVal;
    }
}
