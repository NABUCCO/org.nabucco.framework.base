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
import java.io.OutputStream;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.nabucco.framework.base.facade.datatype.logger.InvocationIdentifierThreadLocal;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.logger.UserIdThreadLocal;
import org.nabucco.framework.base.facade.datatype.security.UserId;
import org.nabucco.framework.base.facade.datatype.utils.StreamUtil;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonParser;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletStreamLoader;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPath;
import org.nabucco.framework.base.ui.web.session.NabuccoWebSession;
import org.nabucco.framework.base.ui.web.session.NabuccoWebSessionFactory;

/**
 * Request object for HTTP servlet requests.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class NabuccoServletRequest {

    /** The default user ID. */
    private static final String DEFAULT_USER = "Guest";

    /** The original HTTP Request. */
    private HttpServletRequest httpRequest;

    /** The Web Session. */
    private NabuccoWebSession session;

    /** The Servlet Path. */
    private NabuccoServletPath servletPath;

    /** The JSON request parameter. */
    private JsonElement jsonRequest;

    /** Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(NabuccoServletRequest.class);

    /**
     * Creates a new {@link NabuccoServletRequest} instance.
     * 
     * @param httpRequest
     *            the original HTTP request
     */
    NabuccoServletRequest(HttpServletRequest httpRequest) {
        if (httpRequest == null) {
            throw new IllegalArgumentException("Cannot create HTTP Request for [null].");
        }

        this.httpRequest = httpRequest;
        this.session = NabuccoWebSessionFactory.createSession(this.httpRequest.getSession());

        this.init();
    }

    /**
     * Initialize the request.
     */
    private void init() {

        String userId = DEFAULT_USER;

        if (this.getSession().getSecurityContext().isAuthenticated()) {
            UserId user = this.getSession().getSecurityContext().getSubject().getUserId();
            if (user != null && user.getValue() != null) {
                userId = user.getValue();
            }
        }

        UserIdThreadLocal.setUserId(userId);
        InvocationIdentifierThreadLocal.setInvocationIdentifier(this.getSessionId());
    }

    /**
     * Getter for the httpRequest.
     * 
     * @return Returns the httpRequest.
     */
    HttpServletRequest getHttpRequest() {
        return this.httpRequest;
    }

    /**
     * Getter for the session object.
     * 
     * @return the session object
     */
    public NabuccoWebSession getSession() {
        return this.session;
    }

    /**
     * Getter for the session id.
     * 
     * @return the session id
     */
    public String getSessionId() {
        if (this.httpRequest.getSession() == null) {
            return null;
        }
        return this.httpRequest.getSession().getId();
    }

    /**
     * Getter for the request context path.
     * 
     * @return the context path of this request
     */
    public String getContextPath() {
        return this.httpRequest.getContextPath();
    }

    /**
     * Getter for the servletPath.
     * 
     * @return Returns the servletPath.
     */
    public NabuccoServletPath getServletPath() {
        if (this.servletPath == null) {
            this.servletPath = new NabuccoServletPath(this.httpRequest.getServletPath(), this.httpRequest.getPathInfo());
        }
        return this.servletPath;
    }

    /**
     * Loads the transfered JSON request parameter as string.
     * 
     * @return the request parameter as JSON string
     * 
     * @throws ClientException
     *             when the JSON cannot be loaded
     */
    public JsonElement getRequestParameter() throws ClientException {
        if (this.jsonRequest == null) {
            NabuccoServletStreamLoader streamLoader = new NabuccoServletStreamLoader(this.httpRequest);
            String json = streamLoader.loadFromStream();

            this.jsonRequest = JsonParser.getInstance().parse(json);
        }

        return this.jsonRequest;
    }

    /**
     * Read the servlet input stream into the given output stream.
     * 
     * @param out
     *            the output stream to write into
     * 
     * @throws ClientException
     *             when the stream cannot be written
     */
    public void readData(OutputStream out) throws ClientException {
        if (out == null) {
            throw new ClientException("Cannot write into output [null].");
        }

        try {
            ServletInputStream in = this.httpRequest.getInputStream();
            try {
                StreamUtil.copy(in, out);
            } catch (Exception e) {
                logger.error(e, "Error sending input to servlet stream.");
                throw new ClientException("Error sending input to servlet stream.", e);
            } finally {
                in.close();
                out.close();
            }
        } catch (IOException ioe) {
            logger.error(ioe, "Error sending input to servlet stream.");
        }

    }

    /**
     * Getter for a send HTTP Parameter. For JSON parameter the method
     * {@link NabuccoServletRequest#getRequestParameter()} must be used.
     * 
     * @param name
     *            the parameter name
     * 
     * @return the parameter for the given name, or null if no parameter was sent
     */
    public String getHttpParameter(String name) {
        return this.httpRequest.getParameter(name);
    }

    /**
     * Setter for an HTTP Attribute. Attribute names should follow the same conventions as package
     * names. Names beginning with java.*, javax.*, and com.sun.*, are reserved for use by Sun
     * Microsystems.
     * 
     * @param attributeName
     *            name of the attribute to set
     * @param value
     *            the attribute value
     */
    public void setAttribute(String attributeName, Object value) {
        this.httpRequest.setAttribute(attributeName, value);
    }

    @Override
    public String toString() {
        return this.getServletPath().toString();
    }
}
