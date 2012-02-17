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
package org.nabucco.framework.base.ui.web.model.dashboard.widget;

import java.util.LinkedHashMap;
import java.util.Map;

import org.nabucco.framework.base.ui.web.component.common.color.ColorScheme;
import org.nabucco.framework.base.ui.web.component.work.dashboard.widget.DashboardWidgetType;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.dashboard.widget.common.WidgetAnalyser;


/**
 * BargraphWidgetModel
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class BargraphWidgetModel extends GraphicDashboardWidgetModel {

    private static final String JSON_SKALE = "skale";
    /**
     * Skale with items (value=>label)
     */
    private Map<String, String> skale = new LinkedHashMap<String, String>();
    /**
     * Creates a new {@link BargraphWidgetModel} instance.
     * 
     * @param analyser
     *            the analyser instance
     * @param skaleType
     *            the skale type
     * @param colorSheme
     *            the colorSheme
     * @param minPercent
     *            the min percent to show
     */
    public BargraphWidgetModel(WidgetAnalyser analyser, ColorScheme colorSheme,
            int minPercent) {
        super(DashboardWidgetType.BARGRAPH, analyser, colorSheme, minPercent);
    }

    /**
     * Adds a new skale item to the model
     * 
     * @param value
     *            the value to represent
     * @param label
     *            the label to show
     */
    public void addSkaleItem(String value, String label) {
        this.skale.put(value, label);
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public JsonElement toJson() {
        JsonMap retVal = (JsonMap) super.toJson();
        
        JsonMap skaleList = new JsonMap();
        for(String value : this.skale.keySet()){
            String label = this.skale.get(value);
            skaleList.add(value, label);
        }
        retVal.add(JSON_SKALE, skaleList);
        return retVal;
    }

}
