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
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.utils.StreamUtil;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.json.JsonElement;

/**
 * NabuccoServletResponse
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class NabuccoServletResponse {

    private HttpServletResponse httpResponse;

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(NabuccoServletRequest.class);

    /**
     * Creates a new {@link NabuccoServletResponse} instance.
     */
    NabuccoServletResponse(HttpServletResponse httpResponse) {
        if (httpResponse == null) {
            throw new IllegalArgumentException("Cannot create HTTP Response for [null].");
        }
        this.httpResponse = httpResponse;
    }

    /**
     * Getter for the httpResponse.
     * 
     * @return Returns the httpResponse.
     */
    HttpServletResponse getHttpResponse() {
        return this.httpResponse;
    }

    /**
     * Add a cookie to the HTTP Response.
     * 
     * @param key
     *            the cookie name
     * @param value
     *            the cookie value
     */
    public void addCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        this.httpResponse.addCookie(cookie);
    }

    /**
     * Send the JSON String to the client.
     * 
     * @param data
     *            the json string
     * 
     * @throws ClientException
     *             when the response cannot be sent
     */
    public void sendResponseParameter(JsonElement data) throws ClientException {

        String jsonText = (data == null) ? "" : data.print();

        try {
            this.httpResponse.setHeader("Access-Control-Allow-Origin", "*");
            this.httpResponse.setCharacterEncoding("UTF-8");
            this.httpResponse.setContentType("text/json");
            this.httpResponse.getWriter().print(jsonText);
            this.httpResponse.getWriter().flush();
        } catch (IOException ioe) {
            logger.error(ioe, "Error converting servlet request data to string.");
            throw new ClientException("Error converting servlet request data to string.", ioe);
        } catch (Exception e) {
            logger.error(e, "Error converting servlet request data to string.");
            throw new ClientException("Error converting servlet request data to string.", e);
        }

    }

    /**
     * Send Header Information to the client to redirect to the target location. This method should
     * be called in case a site is no longer available.
     * 
     * @param target
     *            the target location to redirect to
     * 
     * @throws ClientException
     *             when the target location is not valid or the redirection fails
     */
    public void sendRedirect(String target) throws ClientException {
        if (target == null) {
            throw new ClientException("Cannot redirect to target [null].");
        }

        try {
            this.httpResponse.sendRedirect(target);
        } catch (Exception e) {
            logger.error(e, "Error redirecting to target '", target, "'.");
            throw new ClientException("Error redirecting to target '" + target + "'.");
        }
    }

    /**
     * Send the given output stream to the HTTP Servlet stream. At the end of the operation both
     * streams are closed.
     * 
     * @param in
     *            the input stream to send
     * 
     * @throws ClientException
     *             when the input stream cannot be send to the servlet
     */
    public void sendData(InputStream in) throws ClientException {
        if (in == null) {
            throw new ClientException("Cannot send input [null].");
        }

        try {
            ServletOutputStream out = this.httpResponse.getOutputStream();
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
     * Close the servlet response.
     * 
     * @throws ClientException
     *             when the close failed
     */
    public synchronized void close() throws ClientException {
        try {
            this.httpResponse.getWriter().close();
        } catch (Exception e) {
            logger.error(e, "Error closing servlet request.");
            throw new ClientException("Error closing servlet request.", e);
        }
    }

    /**
     * Prints the given text to the http response.
     * 
     * @deprecated this method will be removed in further releases, use
     *             {@link NabuccoServletResponse#sendResponseParameter(JsonElement)} for
     *             communication
     * 
     * @param text
     *            the text to print
     * 
     * @throws ClientException
     *             when the text cannot be printed
     */
    @Deprecated
    public void print(String text) throws ClientException {
        try {
            this.httpResponse.getWriter().write(text);
        } catch (IOException e) {
            logger.error(e, "Error printing text to servlet request.");
            throw new ClientException("Error printing text to servlet request.", e);
        }
    }
}
