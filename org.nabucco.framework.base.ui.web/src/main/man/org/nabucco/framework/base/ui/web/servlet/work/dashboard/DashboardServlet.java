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
package org.nabucco.framework.base.ui.web.servlet.work.dashboard;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.component.work.WorkArea;
import org.nabucco.framework.base.ui.web.component.work.WorkItem;
import org.nabucco.framework.base.ui.web.component.work.dashboard.DashboardItem;
import org.nabucco.framework.base.ui.web.component.work.dashboard.widget.DashboardWidget;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServlet;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletRequest;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletResponse;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.handler.ActionHandler;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * Servlet for handling the initialization, data population and action execution of Dashboard
 * intances.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class DashboardServlet extends NabuccoServlet {

    private static final long serialVersionUID = 1L;

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(DashboardServlet.class);

    @Override
    protected void restGet(NabuccoServletRequest request, NabuccoServletResponse response) throws ClientException {

        String dashboardId = request.getServletPath().getId(NabuccoServletPathType.DASHBOARD);
        String widgetId = request.getServletPath().getId(NabuccoServletPathType.WIDGET);

        try {
            WorkItem item = NabuccoServletUtil.getWorkItem(dashboardId);

            if (item instanceof DashboardItem) {
                if (widgetId == null) {
                    response.sendResponseParameter(item.toJson());
                } else {
                    // Send only the data model
                    DashboardWidget widget = (DashboardWidget) item.getElement(widgetId);
                    response.sendResponseParameter(widget.getModel().toJson());
                }
                item.clear();
            }

        } catch (Exception e) {
            logger.fatal(e, "Cannot initialize Dashboard.");
            throw new ClientException("Cannot initialize Dashboard.", e);
        }
    }

    @Override
    protected void restPost(NabuccoServletRequest request, NabuccoServletResponse response) throws ClientException {

        WorkArea workArea = NabuccoServletUtil.getWorkArea();

        ActionHandler handler = new ActionHandler(request, response);
        handler.execute(workArea);

    }

}
