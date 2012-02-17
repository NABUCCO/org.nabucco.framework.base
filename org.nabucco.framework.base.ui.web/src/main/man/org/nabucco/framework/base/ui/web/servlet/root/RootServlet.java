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
package org.nabucco.framework.base.ui.web.servlet.root;

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServlet;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletRequest;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletResponse;

/**
 * RootServlet
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class RootServlet extends NabuccoServlet {

    private static final long serialVersionUID = 1L;

    /** Login Servlet Path */
    private static final String LOGIN_PATH = "/login";

    @Override
    protected void restGet(NabuccoServletRequest request, NabuccoServletResponse response) throws ClientException {
        this.redirect(request, response);
    }

    @Override
    protected void restPost(NabuccoServletRequest request, NabuccoServletResponse response) throws ClientException {
        this.redirect(request, response);
    }

    /**
     * Redirect the request to the given URL.
     * 
     * @param request
     *            the request to redirect
     * @param response
     *            the response to redirect
     * 
     * @throws ClientException
     *             when the redirected resource raises an exception
     */
    private void redirect(NabuccoServletRequest request, NabuccoServletResponse response) throws ClientException {
        String path = request.getServletPath().getPath();
        if (path == null || path.isEmpty()) {
            super.redirect(LOGIN_PATH, request, response);
        } else {
            super.redirect(path, request, response);
        }
    }
}
