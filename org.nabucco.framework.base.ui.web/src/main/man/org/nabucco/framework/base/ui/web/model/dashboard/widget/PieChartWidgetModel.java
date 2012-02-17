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

import org.nabucco.framework.base.ui.web.component.common.color.ColorScheme;
import org.nabucco.framework.base.ui.web.component.work.dashboard.widget.DashboardWidgetType;
import org.nabucco.framework.base.ui.web.model.dashboard.widget.common.WidgetAnalyser;

/**
 * PieChartWidgetModel
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class PieChartWidgetModel extends GraphicDashboardWidgetModel {

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
    public PieChartWidgetModel(WidgetAnalyser analyser, ColorScheme colorSheme,
            int minPercent) {
        super(DashboardWidgetType.PIECHART, analyser, colorSheme, minPercent);
    }

    @Override
    public void init() {
        super.init();
    }
}
