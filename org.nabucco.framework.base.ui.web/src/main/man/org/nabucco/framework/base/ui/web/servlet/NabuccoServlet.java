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
package org.nabucco.framework.base.ui.web.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionResolver;
import org.nabucco.framework.base.facade.datatype.extension.property.StringProperty;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.application.ApplicationExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.exception.NestedThrowable;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.model.error.ErrorModel;

/**
 * NabuccoServlet
 * <p/>
 * Servlets for the NABUCCO Web applications.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class NabuccoServlet extends HttpServlet implements Servlet {

    /** Servlet Path Name for actions. */
    protected static final String ACTION = "action";

    /** Default serial version UID */
    private static final long serialVersionUID = 1L;

    /** HTTP Error Status */
    private static final int ERROR_STATUS = 500;

    /** The servlet logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(NabuccoServlet.class);

    /**
     * <b>REST - HTTP GET</b>
     * <p/>
     * Requires a ressource from server.
     * 
     * @param request
     *            the HTTP request
     * @param response
     *            the HTTP response
     * 
     * @throws ClientException
     *             when the resource cannot be loaded
     */
    protected void restGet(NabuccoServletRequest request, NabuccoServletResponse response) throws ClientException {
        throw new ClientException("HTTP GET is not supported for this URL.");
    }

    /**
     * <b>REST - HTTP POST</b>
     * <p/>
     * Adds a new ressource to the current ressource.
     * 
     * @param request
     *            the HTTP request
     * @param response
     *            the HTTP response
     * 
     * @throws ClientException
     *             when the resource cannot be added
     */
    protected void restPost(NabuccoServletRequest request, NabuccoServletResponse response) throws ClientException {
        throw new ClientException("HTTP POST is not supported for this URL.");
    }

    /**
     * <b>REST - HTTP PUT</b>
     * <p/>
     * Adds or modifies the given resource.
     * 
     * @param request
     *            the HTTP request
     * @param response
     *            the HTTP response
     * 
     * @throws ClientException
     *             when the resource cannot be added/modified
     */
    protected void restPut(NabuccoServletRequest request, NabuccoServletResponse response) throws ClientException {
        throw new ClientException("HTTP PUT is not supported for this URL.");
    }

    /**
     * <b>REST - HTTP HEAD</b>
     * <p/>
     * Requires meta-information from the server.
     * 
     * @param request
     *            the HTTP request
     * @param response
     *            the HTTP response
     * 
     * @throws ClientException
     *             when the resource cannot be removed
     */
    protected void restHead(NabuccoServletRequest request, NabuccoServletResponse response) throws ClientException {
        throw new ClientException("HTTP HEAD is not supported for this URL.");
    }

    /**
     * <b>REST - HTTP OPTIONS</b>
     * <p/>
     * Checks which options are available on a resource.
     * 
     * @param request
     *            the HTTP request
     * @param response
     *            the HTTP response
     * 
     * @throws ClientException
     *             when the resource options cannot be retrieved
     */
    protected void restOptions(NabuccoServletRequest request, NabuccoServletResponse response) throws ClientException {
        throw new ClientException("HTTP OPTIONS is not supported for this URL.");
    }

    @Override
    public final void init() throws ServletException {
        super.init();

        try {
            this.initServlet();
        } catch (ClientException ce) {
            logger.error(ce, "NABUCCO Error during initialization of ", this.getClass().getSimpleName(), ".");
        } catch (Exception e) {
            logger.error(e, "Unexpected Error during initialization of ", this.getClass().getSimpleName(), ".");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Finished initialization of ", this.getClass().getSimpleName(), ".");
        }
    }

    /**
     * Initialization method of the servlet, called when the servlet is instantiated. The default
     * implementation does nothing and may be overridden by clients.
     * 
     * @throws ClientException
     *             when the inizialization fails
     */
    protected void initServlet() throws ClientException {
        // Nothing to do here!
    }

    @Override
    public final void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    public final void log(String message, Throwable t) {
        super.log(message, t);
        logger.info(t, message);
    }

    @Override
    public final void log(String message) {
        super.log(message);
        logger.info(message);
    }

    @Override
    protected final void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        this.validateHttp(request, response);

        if (logger.isDebugEnabled()) {
            String path = request.getServletPath() + ((request.getPathInfo() != null) ? request.getPathInfo() : "");
            logger.debug("Called HTTP GET on ", this.getClass().getSimpleName(), " '", path, "'.");
        }

        try {
            this.restGet(this.createRequest(request), this.createResponse(response));

        } catch (ClientException ce) {
            logger.error(ce, "NABUCCO Error during HTTP GET on ", this.getClass().getSimpleName(), ".");
            this.handleException(ce, response);
        } catch (Exception e) {
            logger.error(e, "Unexpected Error during HTTP GET on ", this.getClass().getSimpleName(), ".");
            this.handleException(e, response);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Finished HTTP GET on ", this.getClass().getSimpleName(), ".");
        }
    }

    @Override
    protected final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        this.validateHttp(request, response);

        if (logger.isDebugEnabled()) {
            String path = request.getServletPath() + ((request.getPathInfo() != null) ? request.getPathInfo() : "");
            logger.debug("Called HTTP POST on ", this.getClass().getSimpleName(), " '", path, "'.");
        }

        try {
            this.restPost(this.createRequest(request), this.createResponse(response));

        } catch (ClientException ce) {
            logger.error(ce, "NABUCCO Error during HTTP POST on ", this.getClass().getSimpleName(), ".");
            this.handleException(ce, response);
        } catch (Exception e) {
            logger.error(e, "Unexpected Error during HTTP POST on ", this.getClass().getSimpleName(), ".");
            this.handleException(e, response);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Finished HTTP POST on ", this.getClass().getSimpleName(), ".");
        }
    }

    @Override
    protected final void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        this.validateHttp(request, response);

        if (logger.isDebugEnabled()) {
            String path = request.getServletPath() + ((request.getPathInfo() != null) ? request.getPathInfo() : "");
            logger.debug("Called HTTP PUT on ", this.getClass().getSimpleName(), " '", path, "'.");
        }

        try {
            this.restPut(this.createRequest(request), this.createResponse(response));

        } catch (ClientException ce) {
            logger.error(ce, "NABUCCO Error during HTTP PUT on ", this.getClass().getSimpleName(), ".");
            this.handleException(ce, response);
        } catch (Exception e) {
            logger.error(e, "Unexpected Error during HTTP PUT on ", this.getClass().getSimpleName(), ".");
            this.handleException(e, response);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Finished HTTP PUT on ", this.getClass().getSimpleName(), ".");
        }
    }

    @Override
    protected final void doHead(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        this.validateHttp(request, response);

        if (logger.isDebugEnabled()) {
            String path = request.getServletPath() + ((request.getPathInfo() != null) ? request.getPathInfo() : "");
            logger.debug("Called HTTP HEAD on ", this.getClass().getSimpleName(), " '", path, "'.");
        }

        try {
            this.restHead(this.createRequest(request), this.createResponse(response));

        } catch (ClientException ce) {
            logger.error(ce, "NABUCCO Error during HTTP HEAD on ", this.getClass().getSimpleName(), ".");
            this.handleException(ce, response);
        } catch (Exception e) {
            logger.error(e, "Unexpected Error during HTTP HEAD on ", this.getClass().getSimpleName(), ".");
            this.handleException(e, response);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Finished HTTP HEAD on ", this.getClass().getSimpleName(), ".");
        }
    }

    @Override
    protected final void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        this.validateHttp(request, response);

        if (logger.isDebugEnabled()) {
            String path = request.getServletPath() + ((request.getPathInfo() != null) ? request.getPathInfo() : "");
            logger.debug("Called HTTP OPTIONS on ", this.getClass().getSimpleName(), " '", path, "'.");
        }

        try {
            this.restOptions(this.createRequest(request), this.createResponse(response));

        } catch (ClientException ce) {
            logger.error(ce, "NABUCCO Error during HTTP OPTIONS on ", this.getClass().getSimpleName(), ".");
            this.handleException(ce, response);
        } catch (Exception e) {
            logger.error(e, "Unexpected Error during HTTP OPTIONS on ", this.getClass().getSimpleName(), ".");
            this.handleException(e, response);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Finished HTTP OPTIONS on ", this.getClass().getSimpleName(), ".");
        }
    }

    @Override
    protected final void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String path = request.getServletPath() + ((request.getPathInfo() != null) ? request.getPathInfo() : "");
        logger.warning("Called HTTP DELETE on ", this.getClass().getSimpleName(), " '", path, "'.");
        logger.warning("HTTP DELETE is not supported on this system.");
    }

    @Override
    protected final void doTrace(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String path = request.getServletPath() + ((request.getPathInfo() != null) ? request.getPathInfo() : "");
        logger.warning("Called HTTP TRACE on ", this.getClass().getSimpleName(), " '", path, "'.");
        logger.warning("HTTP TRACE is not supported on this system.");
    }

    /**
     * Load the application extension.
     * 
     * @return the application extension
     * 
     * @throws ExtensionException
     *             when the extension cannot be loaded
     */
    protected final ApplicationExtension loadApplicationExtension() throws ExtensionException {
        return (ApplicationExtension) NabuccoSystem.getExtensionResolver().resolveExtension(
                ExtensionPointType.ORG_NABUCCO_UI_APPLICATION, ExtensionResolver.DEFAULT_EXTENSION);
    }

    /**
     * Load the string of a configured extension string property.
     * 
     * @param property
     *            the string property to load
     * 
     * @return the loaded string
     */
    protected final String loadProperty(StringProperty property) {
        if (property == null || property.getValue() == null) {
            return null;
        }

        return property.getValue().getValue();
    }

    /**
     * Redirect to the given target location.
     * 
     * @param target
     *            the target location
     * @param request
     *            the servlet request
     * @param response
     *            the servlet response
     * 
     * @throws ClientException
     *             when when the target location is not valid or the redirection fails
     */
    protected void redirect(String target, NabuccoServletRequest request, NabuccoServletResponse response)
            throws ClientException {

        StringBuilder url = new StringBuilder();
        url.append(target);

        try {
            ServletContext servletContext = super.getServletConfig().getServletContext();
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(url.toString());

            if (requestDispatcher == null) {
                logger.error("Cannot redirect to '", target, "'. The resource does not exist.");
                throw new ClientException("Cannot redirect to '" + target + "'. The resource does not exist.");
            }

            requestDispatcher.forward(request.getHttpRequest(), response.getHttpResponse());

        } catch (ServletException se) {
            logger.error(se, "Error redirecting to target '", target, "'.");
            throw new ClientException("Error redirecting to target '" + target + "'.", se);
        } catch (Exception e) {
            logger.error(e, "Error redirecting to target '", target, "'.");
            throw new ClientException("Error redirecting to target '" + target + "'.", e);
        }
    }

    /**
     * Validate the HTTP Request and Response parameter.
     * 
     * @param request
     *            the http request
     * @param response
     *            the http response
     * 
     * @throws IllegalStateException
     *             when the request/response parameter is not correct
     */
    private void validateHttp(HttpServletRequest request, HttpServletResponse response) throws IllegalStateException {
        if (request == null) {
            throw new IllegalStateException("Servlet Request is [null].");
        }
        if (response == null) {
            throw new IllegalStateException("Servlet Response is [null].");
        }

        try {
            request.setCharacterEncoding("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            logger.error(e, "Error setting servlet decoding to ISO-8859-1.");
        }
    }

    /**
     * Create the {@link NabuccoServletRequest} from the original {@link HttpServletRequest}.
     * 
     * @param httpRequest
     *            the original HTTP request
     * 
     * @return the new created NABUCCO request
     */
    private NabuccoServletRequest createRequest(HttpServletRequest httpRequest) {
        return new NabuccoServletRequest(httpRequest);
    }

    /**
     * Create the {@link NabuccoServletResponse} from the original {@link HttpServletResponse}.
     * 
     * @param httpRequest
     *            the original HTTP response
     * 
     * @return the new created NABUCCO response
     */
    private NabuccoServletResponse createResponse(HttpServletResponse httpResponse) {
        return new NabuccoServletResponse(httpResponse);
    }

    /**
     * Handle the exception.
     * 
     * @param exception
     *            the exception to handle
     * @param response
     *            the servlet response
     * 
     * @throws ServletException
     *             when the exception cannot be handled
     * @throws IOException
     *             when the servlet output stream is not writable
     */
    private void handleException(Exception exception, HttpServletResponse response) throws ServletException,
            IOException {

        if (!(exception instanceof NabuccoException)) {
            exception = new ClientException("Error processing request on servlet "
                    + this.getClass().getSimpleName() + ".", exception);
        }
        String jsonText = null;

        if (!this.isServiceException(exception)) {
            ErrorModel errorModel = new ErrorModel(exception);
            jsonText = errorModel.toJson().print();
            response.setStatus(ERROR_STATUS);
        } else {
            WebActionResult errorResult = new WebActionResult();
            errorResult.addItem(new RefreshItem(WebElementType.ERROR));
            jsonText = errorResult.toJson().print();
        }

        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setCharacterEncoding("ISO-8859-1");
            response.setContentType("text/json");
            response.getWriter().print(jsonText);
            response.getWriter().flush();
        } catch (IOException ioe) {
            logger.error(ioe, "Error converting servlet request data to string.");
            throw new ServletException("Error converting servlet request data to string.", ioe);
        } catch (Exception e) {
            logger.error(e, "Error converting servlet request data to string.");
            throw new ServletException("Error converting servlet request data to string.", e);
        }

    }

    /**
     * Recursive method to check if the exception has a service exception is its couse
     * 
     * @param exception
     *            the exception to be checked
     * @return true if it is a service exception or false if not
     */
    private boolean isServiceException(Exception exception) {
        if (!(exception instanceof NabuccoException)) {
            return false;
        }
        if (exception instanceof ServiceException) {
            return true;
        }

        NabuccoException nabuccoException = (NabuccoException) exception;
        NestedThrowable cause = nabuccoException.getCause();

        if (cause == null) {
            return false;
        }

        Class<?> causingClass = cause.getExceptionClass();
        if (causingClass == null) {
            return false;
        }
        if (ServiceException.class.isAssignableFrom(causingClass)) {
            return true;
        }

        return this.isServiceException(cause);
    }
}
