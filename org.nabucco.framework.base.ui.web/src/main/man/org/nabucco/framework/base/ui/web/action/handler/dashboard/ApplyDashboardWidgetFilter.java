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
package org.nabucco.framework.base.ui.web.action.handler.dashboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.handler.FilteringActionHandler;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.WorkItem;
import org.nabucco.framework.base.ui.web.component.work.dashboard.DashboardItem;
import org.nabucco.framework.base.ui.web.component.work.dashboard.widget.DashboardWidget;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * ApplyPickerFilterHandler
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ApplyDashboardWidgetFilter extends FilteringActionHandler {

    public static String ID = "System.ApplyDashboardWidgetFilter";

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ClientException {
        String dashboardId = parameter.get(NabuccoServletPathType.DASHBOARD);
        String widgetId = parameter.get(NabuccoServletPathType.WIDGET);
        try {
            WorkItem item = NabuccoServletUtil.getWorkItem(dashboardId);

            if (item instanceof DashboardItem == false) {
                throw new ClientException("Cannot get dashboard item with id '" + dashboardId + "'.");
            }
            DashboardItem dashboard = (DashboardItem) item;
            DashboardWidget widget = (DashboardWidget) dashboard.getElement(widgetId);

            if (widget == null) {
                throw new ClientException("Cannot get widget item with id '" + widgetId + "'.");
            }

            String activeFilterId = widget.getActiveFilterId();

            List<Datatype> values = this.getFilteredData(activeFilterId, parameter);
            if (values == null) {
                values = Collections.<Datatype> emptyList();
            }

            List<NabuccoDatatype> filteredData = new ArrayList<NabuccoDatatype>();
            for (Datatype value : values) {
                filteredData.add((NabuccoDatatype) value);
            }

            widget.getModel().setContent(filteredData);

        } catch (Exception e) {
            throw new ClientException("Cannot apply filter to Dashboard widget '" + widgetId + "'", e);
        }
        WebActionResult retVal = new WebActionResult();
        retVal.addItem(new RefreshItem(WebElementType.BROWSER_AREA));
        return retVal;
    }

    @Override
    protected String getQueryFilterParameter(WebActionParameter requestParameter, String paramterName,
            Class<?> classType) throws ClientException {
        return null;
    }

}
