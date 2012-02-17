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
package org.nabucco.framework.base.ui.web.servlet.util.handler;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletRequest;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletResponse;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathEntry;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * Abstract handler for servlet path executions.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class NabuccoServletHandler {

    private final NabuccoServletRequest request;

    private final NabuccoServletResponse response;

    private final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(NabuccoServletHandler.class);

    /**
     * Creates a new {@link WorkAreaPostHandler} instance.
     * 
     * @param request
     *            the servlet request
     * @param response
     *            the servlet response
     */
    public NabuccoServletHandler(NabuccoServletRequest request, NabuccoServletResponse response) {
        if (request == null) {
            throw new IllegalArgumentException("Cannot WorkArea Post Handler for servlet request [null].");
        }
        if (response == null) {
            throw new IllegalArgumentException("Cannot WorkArea Post Handler for servlet response [null].");
        }

        this.request = request;
        this.response = response;
    }

    /**
     * Getter for the request.
     * 
     * @return Returns the request.
     */
    protected NabuccoServletRequest getRequest() {
        return this.request;
    }

    /**
     * Getter for the response.
     * 
     * @return Returns the response.
     */
    protected NabuccoServletResponse getResponse() {
        return this.response;
    }

    /**
     * Execute the handler for the given servlet path entry.
     * 
     * @param servletElement
     *            the starting servlet web element
     * 
     * @throws ClientException
     *             when the execution fails
     */
    public final void execute(WebElement servletElement) throws ClientException {
        this.internalExecute(servletElement, this.request.getServletPath().getRootEntry());
    }

    /**
     * Recursive execution method that processes the servlet path and executes the concrete handler
     * implementation.
     * 
     * @param element
     *            the element to process
     * @param key
     *            the current servlet path key
     * 
     * @throws ClientException
     *             when the execution fails
     */
    private final void internalExecute(WebElement element, NabuccoServletPathEntry key) throws ClientException {
        if (key == null || key.getNext() == null || key.getNext().getType() != NabuccoServletPathType.ID) {
            return;
        }

        String id = key.getNext().getValue();
        WebElement child = null;

        if (element != null) {
            child = element.getElement(id);

            if (child == null) {
                child = element;
            }
        }

        NabuccoServletPathEntry next = key.getNext().getNext();

        try {
            this.process(child, next);
        } catch (ClientException ce) {
            logger.error(ce, "Error processing servlet path entry [", key, "].");
            throw ce;
        } catch (Exception e) {
            logger.error(e, "Error processing servlet path entry [", key, "].");
            throw new ClientException("Error processing servlet path entry [" + key + "].", e);
        }

        this.internalExecute(child, next);
    }

    /**
     * The internal execution method that executes the current element.
     * 
     * @param element
     *            the current web element
     * @param entry
     *            the current path entry.
     * 
     * @throws ClientException
     *             when the execution fails
     */
    protected abstract void process(WebElement element, NabuccoServletPathEntry entry) throws ClientException;

}
