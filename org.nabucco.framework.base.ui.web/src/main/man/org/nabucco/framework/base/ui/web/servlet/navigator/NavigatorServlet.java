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
package org.nabucco.framework.base.ui.web.servlet.navigator;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.component.WebApplication;
import org.nabucco.framework.base.ui.web.component.navigator.NavigationArea;
import org.nabucco.framework.base.ui.web.component.navigator.Navigator;
import org.nabucco.framework.base.ui.web.component.perspective.Perspective;
import org.nabucco.framework.base.ui.web.component.perspective.PerspectiveArea;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServlet;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletRequest;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletResponse;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.handler.ActionHandler;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * Servlet for handling the initialization, data population and action execution of Navigator
 * instances.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class NavigatorServlet extends NabuccoServlet {

    private static final long serialVersionUID = 1L;

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(NavigatorServlet.class);

    @Override
    protected void restGet(NabuccoServletRequest request, NabuccoServletResponse response) throws ClientException {

        String navigatorId = request.getServletPath().getId(NabuccoServletPathType.NAVIGATOR);

        try {
            WebApplication application = request.getSession().getWebApplication();
            PerspectiveArea perspectiveArea = application.getPerspectiveArea();
            Perspective perspective = perspectiveArea.getCurrentPerspective();

            if (perspective == null) {
                logger.warning("Currently no perspective selected.");
                return;
            }

            Navigator navigator = perspective.getNavigationArea().getNavigator(navigatorId);

            if (navigator == null) {
                logger.warning("Selected navigator '", navigatorId, "' is not configured in perspective '",
                        perspective.getId(), "'.");
                return;
            }

            response.sendResponseParameter(navigator.toJson());

        } catch (Exception e) {
            logger.fatal(e, "Cannot initialize NABUCCO Navigator '", navigatorId, "'.");
            throw new ClientException("Unexpected error loading Navigator '" + navigatorId + "'.", e);
        }
    }

    @Override
    protected void restPost(NabuccoServletRequest request, NabuccoServletResponse response) throws ClientException {
        NavigationArea navigationArea = NabuccoServletUtil.getNavigationArea();

        ActionHandler actionHandler = new ActionHandler(request, response);
        actionHandler.execute(navigationArea);
    }

}
