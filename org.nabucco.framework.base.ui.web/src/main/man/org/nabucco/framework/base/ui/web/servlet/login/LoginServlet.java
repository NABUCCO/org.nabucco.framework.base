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
package org.nabucco.framework.base.ui.web.servlet.login;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.ui.login.LoginRequest;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.security.InvalidLoginException;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServlet;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletRequest;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletResponse;
import org.nabucco.framework.base.ui.web.session.NabuccoWebSession;

/**
 * Servlet for user authentication.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class LoginServlet extends NabuccoServlet {

    private static final long serialVersionUID = 1L;


    /** Target Location for Unauthorized Request */
    private static final String LOCATION_UNAUTHORIZED = "/login.jsp";

    /** Target Location for Authorized Request */
    private static final String LOCATION_AUTHORIZED = "/nabucco.html";

    private static final String ATTRIBUTE_ERROR = "org.nabucco.hr.authentication.errormsg";

    private static final String ERRORTEXT_LOGIN = "Anmeldedaten sind falsch! Bitte versuchen Sie es erneut.";

    private static final String ERRORTEXT_DEFAULT = "Es ist ein Fehler aufgetreten.";

    private static final String ERRORTEXT_BROWSER = "NABUCCO HR bietet keine Unterst�tzung f�r Ihren Browser. Bitte verwenden Sie Firefox oder Chrome.";

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(LoginServlet.class);

    @Override
    protected final void restGet(NabuccoServletRequest request, NabuccoServletResponse response) throws ClientException {
        NabuccoWebSession session = request.getSession();

        if (session != null && session.getSecurityContext().isAuthenticated()) {
            super.redirect(LOCATION_AUTHORIZED, request, response);
            return;
        }

        super.redirect(LOCATION_UNAUTHORIZED, request, response);
    }

    @Override
    protected final void restPost(NabuccoServletRequest request, NabuccoServletResponse response)
            throws ClientException {

        boolean browserAllowed = this.checkBrowserIsSupported(request);
        if (!browserAllowed) {
            request.setAttribute(ATTRIBUTE_ERROR, ERRORTEXT_BROWSER);
            super.redirect(LOCATION_UNAUTHORIZED, request, response);
        }

        LoginRequest login = this.loadLoginData(request);

        if (login == null) {
            logger.warning("Invalid login of user 'null'.");
            return;
        }

        logger.debug("Login of user '", login.getUsername(), "'.");

        try {
            NabuccoWebSession session = request.getSession();
            this.login(login, session);

            logger.debug("Login of User '", login.getUsername(), "' was successful.");

            super.redirect(LOCATION_AUTHORIZED, request, response);

            return;

        } catch (InvalidLoginException ile) {
            logger.warning("Login of user '", login.getUsername(), "' was not successful.");
            request.setAttribute(ATTRIBUTE_ERROR, ERRORTEXT_LOGIN);
        } catch (ClientException ce) {
            logger.error(ce, "Login of user '", login.getUsername(), "' was not successful.");
            request.setAttribute(ATTRIBUTE_ERROR, ERRORTEXT_DEFAULT);
        } catch (Exception e) {
            logger.error(e, "Unexpected error during login.");
            request.setAttribute(ATTRIBUTE_ERROR, ERRORTEXT_DEFAULT);
        }

        super.redirect(LOCATION_UNAUTHORIZED, request, response);
    }

    /**
     * Checks if the browser is supported
     * 
     * @param request
     *            request to be evaluated
     * @return true if browser is supported or false if not
     */
    private boolean checkBrowserIsSupported(NabuccoServletRequest rq) {
        String browser = rq.getHttpParameter("user_browser");

        if (browser == null) {
            return false;
        }

        if (browser.equalsIgnoreCase("Firefox") || browser.equalsIgnoreCase("Chrome")) {
            return true;
        }

        return false;
    }

    /**
     * Execute the login with the given request. When the login fails an
     * {@link InvalidLoginException} must is raised.
     * 
     * @param request
     *            the login request holding the user information
     * @param session
     *            the current web session
     * 
     * @throws ClientException
     *             the authentication fails
     * @throws InvalidLoginException
     *             when the login parameters are not valid and the user cannot be authenticated
     */
    protected abstract void login(LoginRequest request, NabuccoWebSession session) throws InvalidLoginException,
            ClientException;

    /**
     * Load the login request parameters.
     * 
     * @param rq
     *            the servlet request
     * 
     * @return the login request
     * 
     * @throws ClientException
     *             when the login data cannot be loaded
     */
    private LoginRequest loadLoginData(NabuccoServletRequest rq) throws ClientException {

        LoginRequest request = new LoginRequest();

        try {
            request.setUsername(rq.getHttpParameter("username"));
            request.setPassword(rq.getHttpParameter("password"));
            request.setTenant(rq.getHttpParameter("tenant"));
        } catch (Exception e) {
            logger.error(e, "Error deserializing login request for user '", request.getUsername(), "' of tenant ",
                    request.getTenant(), ".");
            throw new ClientException("Error deserializing login request.", e);
        }

        return request;
    }

}
