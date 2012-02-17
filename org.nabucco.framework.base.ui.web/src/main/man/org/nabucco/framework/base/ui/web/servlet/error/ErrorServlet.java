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
package org.nabucco.framework.base.ui.web.servlet.error;

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.component.error.ErrorLogContainer;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServlet;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletRequest;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletResponse;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.handler.ActionHandler;

/**
 * ErrorServlet
 * 
 * This servlet handles communication with error container
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ErrorServlet extends NabuccoServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void restGet(NabuccoServletRequest request, NabuccoServletResponse response) throws ClientException {

        ErrorLogContainer errorContainer = NabuccoServletUtil.getErrorContainer();
        if (errorContainer == null) {
            throw new ClientException("The error container is not initialized yet.");
        }
        response.sendResponseParameter(errorContainer.toJson());

    }

    @Override
    protected void restPost(NabuccoServletRequest request, NabuccoServletResponse response) throws ClientException {
        ErrorLogContainer errorContainer = NabuccoServletUtil.getErrorContainer();
        if (errorContainer == null) {
            throw new ClientException("The error container is not initialized yet.");
        }

        ActionHandler actionHandler = new ActionHandler(request, response);
        actionHandler.execute(errorContainer);
    }

}
