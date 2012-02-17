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
package org.nabucco.framework.base.ui.web.servlet.browser;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.component.browser.Browser;
import org.nabucco.framework.base.ui.web.component.browser.BrowserArea;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.browser.BrowserEntry;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServlet;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletRequest;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletResponse;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.handler.ActionHandler;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * Servlet for handling the initialization, data population and action execution of Browser
 * instances.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class BrowserServlet extends NabuccoServlet {

    private static final long serialVersionUID = 1L;

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(BrowserServlet.class);

    @Override
    protected void restGet(NabuccoServletRequest request, NabuccoServletResponse response) throws ClientException {

        String browserId = request.getServletPath().getId(NabuccoServletPathType.BROWSER);

        try {
            Browser browser = NabuccoServletUtil.getBrowser(browserId);

            if (browser == null) {
                logger.warning("Selected browser '", browserId, "' is not configured in current perspective.");
                return;
            }

            String instanceId = request.getServletPath().getId(NabuccoServletPathType.INSTANCE);
            if (instanceId == null) {
                response.sendResponseParameter(browser.toJson());
            } else {
                BrowserEntry element = browser.getModel().getChildRecursive(instanceId);
                if (element == null) {
                    response.sendResponseParameter(new JsonMap());
                } else {
                    JsonList elementList = new JsonList();
                    for (BrowserEntry entry : element.getEntryMap().values()) {
                        elementList.add(entry.toJson());
                    }
                    response.sendResponseParameter(elementList);
                }
            }

        } catch (Exception e) {
            logger.fatal(e, "Cannot initialize Browser '", browserId, "'.");
            throw new ClientException("Unexpected error loading Browser '" + browserId + "'.", e);
        }
    }

    @Override
    protected void restPost(NabuccoServletRequest request, NabuccoServletResponse response) throws ClientException {
        BrowserArea browserArea = NabuccoServletUtil.getBrowserArea();

        ActionHandler actionHandler = new ActionHandler(request, response);
        actionHandler.execute(browserArea);
    }
}
