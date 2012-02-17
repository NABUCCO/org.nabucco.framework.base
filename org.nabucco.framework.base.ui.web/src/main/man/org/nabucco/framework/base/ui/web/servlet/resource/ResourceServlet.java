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
package org.nabucco.framework.base.ui.web.servlet.resource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.nabucco.framework.base.facade.datatype.content.ContentEntryType;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.component.common.controller.ResourceControllerEntry;
import org.nabucco.framework.base.ui.web.json.JsonValue;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServlet;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletRequest;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletResponse;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathEntry;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;
import org.nabucco.framework.base.ui.web.session.NabuccoWebSession;

/**
 * ResourceServlet
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class ResourceServlet extends NabuccoServlet {

    private static final String PATH_SEPARATOR = "/";

    private static final String ID_PREFIX = "id";

    private static final long serialVersionUID = 1L;

    private static final int MAX_SIZE = 512000; // 500KB

    @Override
    protected final void restGet(NabuccoServletRequest request, NabuccoServletResponse response) throws ClientException {

        try {
            String uri = this.resolveUri(request);

            byte[] data = null;

            NabuccoWebSession session = request.getSession();
            if (uri.startsWith(ID_PREFIX)) {
                // Search via Resource controller
                String key = this.resolveKeyFromUri(uri);
                ResourceControllerEntry resolveResource = NabuccoServletUtil.getResourceController().resolveResource(
                        key);
                Long resourceId = resolveResource.getId();

                ContentEntryType resourceType = resolveResource.getType();
                data = this.loadData(resourceId, resourceType, session);

            } else {
                // Search via URI
                data = this.loadDataFromUrl(uri, session);
            }

            ByteArrayInputStream in = new ByteArrayInputStream(data);

            response.sendData(in);

        } catch (ClientException ce) {
            // TODO: Error page!
            response.sendRedirect("/login");
            throw ce;
        }
    }

    @Override
    protected void restPost(NabuccoServletRequest request, NabuccoServletResponse response) throws ClientException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        request.readData(out);

        byte[] data = out.toByteArray();

        if (data.length > MAX_SIZE) {
            throw new ClientException("Data too large '" + data.length + "' bytes, allowed are " + MAX_SIZE + " bytes.");
        }

        String fileName = this.resolveFilename(request);
        String instanceid = this.resolveInstanceId(request);

        String tempUri = this.writeData(fileName, instanceid, data, request.getSession());
        response.sendResponseParameter(new JsonValue(tempUri));
    }

    /**
     * Resolves filename from request
     * 
     * @param request
     *            request to be used for resolving
     * @return filename xx.xx
     */
    private String resolveFilename(NabuccoServletRequest request) {
        NabuccoServletPathEntry requestParameter = request.getServletPath().getEntry(NabuccoServletPathType.FILE);
        String fileName = requestParameter.getNext().getValue();
        return fileName;
    }

    /**
     * Resolves filename from request
     * 
     * @param request
     *            request to be used for resolving
     * @return instance
     */
    private String resolveInstanceId(NabuccoServletRequest request) {
        NabuccoServletPathEntry requestParameter = request.getServletPath().getEntry(NabuccoServletPathType.INSTANCE);
        String instance = requestParameter.getNext().getValue();
        return instance;
    }

    /**
     * Resolves URI from request element
     * 
     * @param request
     *            the request for resolving
     * @return resolved uri string
     */
    private String resolveUri(NabuccoServletRequest request) {
        String url = request.getServletPath().getRootEntry().getNext().toString();

        if (url.startsWith(PATH_SEPARATOR)) {
            url = url.substring(1, url.length());
        }

        return url;
    }

    /**
     * Resolves Key from URI from request element
     * 
     * @param request
     *            the request for resolving
     * @return resolved uri string or null
     */
    private String resolveKeyFromUri(String url) {
        String retVal = null;
        if (url.contains(PATH_SEPARATOR)) {
            retVal = url.substring(url.lastIndexOf(PATH_SEPARATOR) + 1, url.length());
        }

        return retVal;
    }

    /**
     * Load the data from the content component.
     * 
     * @param url
     *            the url to resolve the content from
     * 
     * @return the loaded content as byte array
     * 
     * @throws ClientException
     *             when the content cannot be retrieved
     */
    protected abstract byte[] loadDataFromUrl(String url, NabuccoWebSession session) throws ClientException;

    /**
     * Load data ftom content component
     * 
     * @param id
     *            the id of data element
     * @param type
     *            the type of data element
     * @param session
     *            the session instance
     * @return the loaded content
     * @throws ClientException
     *             when the content cannot be retrieved
     */
    protected abstract byte[] loadData(Long id, ContentEntryType type, NabuccoWebSession session)
            throws ClientException;

    /**
     * Write the data to the content component.
     * 
     * @param filename
     *            original filename
     * @param instanceId
     *            the instance id to use for the name
     * @param data
     *            the serialized data to write
     * @param session
     * @return The Path string
     * @throws ClientException
     */
    protected abstract String writeData(String filename, String instanceId, byte[] data, NabuccoWebSession session)
            throws ClientException;

}
