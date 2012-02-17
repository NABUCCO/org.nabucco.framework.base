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

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.WebAction;
import org.nabucco.framework.base.ui.web.action.WebActionHandler;
import org.nabucco.framework.base.ui.web.action.WebActionRegistry;
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
 * SelectPickerFilter
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class SelectDashboardWidgetFilter extends WebActionHandler {

    public final static String ID = "System.SelectDashboardWidgetFilter";

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ClientException {
        String dashboardId = parameter.get(NabuccoServletPathType.DASHBOARD);
        String widgetId = parameter.get(NabuccoServletPathType.WIDGET);
        WorkItem item = NabuccoServletUtil.getWorkItem(dashboardId);

        if (item instanceof DashboardItem == false) {
            throw new ClientException("Cannot get dashboard item with id '" + dashboardId + "'.");
        }
        DashboardItem dashboard = (DashboardItem) item;
        DashboardWidget widget = (DashboardWidget) dashboard.getElement(widgetId);
        
        if (widget == null) {
            throw new ClientException("Cannot get widget item with id '" + widgetId + "'.");
        }
        
        String filterId = parameter.getJsonRequest().getValue();
        
        if (filterId == null) {
            throw new ClientException("Cannot set filter with id '" + filterId + "'.");
        }
        
        widget.setActiveFilterId(filterId);

        // Process filter
        WebAction action = WebActionRegistry.getInstance().newAction(ApplyDashboardWidgetFilter.ID);

        if (action == null) {
            throw new ClientException("Cannot instanciate filter action.");
        }
        action.execute(parameter);

        WebActionResult result = new WebActionResult();
        result.addItem(new RefreshItem(WebElementType.DASHBOARD, dashboardId, widgetId));

        return result;
    }
}
