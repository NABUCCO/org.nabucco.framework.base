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
import org.nabucco.framework.base.ui.web.component.navigator.NavigationArea;
import org.nabucco.framework.base.ui.web.component.perspective.Perspective;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServlet;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletRequest;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletResponse;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;

/**
 * Servlet for initializing the Navigator Area of the NABUCCO Web Application.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class NavigationAreaServlet extends NabuccoServlet {

    private static final long serialVersionUID = 1L;

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(NavigationAreaServlet.class);

    @Override
    protected void restGet(NabuccoServletRequest request, NabuccoServletResponse response) throws ClientException {

        try {
            Perspective perspective = NabuccoServletUtil.getSelectedPerspective();

            if (perspective == null) {
                logger.warning("Currently no perspective selected.");
                return;
            }

            NavigationArea navigationArea = perspective.getNavigationArea();
            response.sendResponseParameter(navigationArea.toJson());

        } catch (Exception e) {
            logger.fatal(e, "Cannot initialize NABUCCO NavigationArea.");
            throw new ClientException("Unexpected error loading Navigation Area.", e);
        }
    }

}
