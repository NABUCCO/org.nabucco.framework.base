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
package org.nabucco.framework.base.ui.web.action.parameter;

import java.util.HashMap;
import java.util.Map;

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletRequest;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;
import org.nabucco.framework.base.ui.web.session.NabuccoWebSession;

/**
 * Parameter for web action execution holding all necessary request information.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class WebActionParameter {

    private static final String VALUE_NULL = "null";

    private static final String VALUE_UNDEFINED = "undefined";

    /** The web session */
    private final NabuccoWebSession session;

    /** The web element that caused the action */
    private WebElement element;

    /** The JSON request */
    private final JsonElement jsonRequest;

    /** The Servlet Path URL Parameter Map */
    private Map<NabuccoServletPathType, String> servletParameter = new HashMap<NabuccoServletPathType, String>();

    /** Additional modifiable Servlet Path URL Parameter Map */
    private Map<NabuccoServletPathType, String> additionalParameter = new HashMap<NabuccoServletPathType, String>();

    /**
     * Creates a new {@link WebActionParameter} instance.
     * 
     * @param session
     *            the web session
     * @param jsonRequest
     *            the JSON request parameter
     * @param parameter
     *            the servlet path parameter
     */
    public WebActionParameter(NabuccoWebSession session, JsonElement jsonRequest,
            Map<NabuccoServletPathType, String> parameter) {
        if (session == null) {
            throw new IllegalArgumentException("Cannot create action parameter for session [null].");
        }

        this.session = session;
        this.jsonRequest = jsonRequest;

        if (parameter != null) {
            this.servletParameter.putAll(parameter);
        }
    }

    /**
     * Creates a new {@link WebActionParameter} instance.
     * 
     * @param request
     *            the servlet request
     * @param the
     *            current web element
     * 
     * @throws ClientException
     *             when the request cannot be serialized
     */
    public WebActionParameter(NabuccoServletRequest request, WebElement element) throws ClientException {
        if (request == null) {
            throw new IllegalArgumentException("Cannot create action parameter for request [null].");
        }

        this.session = request.getSession();
        this.jsonRequest = request.getRequestParameter();
        this.servletParameter.putAll(request.getServletPath().toMap());
        this.element = element;
    }

    /**
     * Getter for the session.
     * 
     * @return Returns the session.
     */
    public NabuccoWebSession getSession() {
        return this.session;
    }

    /**
     * Getter for the element that causes the action.
     * 
     * @return Returns the source element.
     */
    public WebElement getElement() {
        return this.element;
    }

    /**
     * Getter for the jsonRequest.
     * 
     * @return Returns the jsonRequest.
     */
    public JsonElement getJsonRequest() {
        return this.jsonRequest;
    }

    /**
     * Getter for the parameter with the given key.
     * 
     * @param key
     *            the parameter key
     * 
     * @return the parameter with the given id, or null if no parameter with the id exists
     */
    public String get(NabuccoServletPathType key) {
        String parameter = this.getOriginal(key);

        if (parameter == null || parameter.isEmpty()) {
            return this.additionalParameter.get(key);
        }

        if (parameter.equalsIgnoreCase(VALUE_UNDEFINED) || parameter.equalsIgnoreCase(VALUE_NULL)) {
            return this.additionalParameter.get(key);
        }

        // Make it possible to override parameters in the standard get
        if (this.additionalParameter.get(key) != null) {
            return this.additionalParameter.get(key);
        }

        return parameter;
    }

    /**
     * Getter for the parameter with the given key from the original parameter list.
     * 
     * @param key
     *            the parameter key
     * 
     * @return the parameter with the given id, or null if no parameter with the id exists
     */
    public String getOriginal(NabuccoServletPathType key) {
        return this.servletParameter.get(key);
    }

    /**
     * Getter for the parameter with the given key from the additional parameter list.
     * 
     * @param key
     *            the parameter key
     * 
     * @return the parameter with the given id, or null if no parameter with the id exists
     */
    public String getAdditional(NabuccoServletPathType key) {
        return this.additionalParameter.get(key);
    }

    /**
     * Manually add the kayValue pair to the action parameters. This method does not override
     * original parameters.
     * 
     * @param key
     *            the servlet path key
     * @param value
     *            the servlet path value
     */
    public void setParameter(NabuccoServletPathType key, String value) {
        this.additionalParameter.put(key, value);
    }

}
