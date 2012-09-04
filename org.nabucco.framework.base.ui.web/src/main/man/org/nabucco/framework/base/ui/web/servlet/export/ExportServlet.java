/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco-source.org/nabucco-license.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.ui.web.servlet.export;

import java.io.ByteArrayInputStream;

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.component.common.controller.resource.TemproraryResourceEntry;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServlet;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletRequest;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletResponse;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;

/**
 * ExportServlet
 * 
 * This servlet can be used to return the temprorary stored data (for example generated export or
 * reports)
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ExportServlet extends NabuccoServlet {

    private static final long serialVersionUID = 1L;

    private static final String DIGIT_REGEX_PATTERN = "\\d*";

    private static final String PATH_SEPARATOR = "/";

    @Override
    protected void restGet(NabuccoServletRequest request, NabuccoServletResponse response) throws ClientException {
        try {
            String uri = this.resolveUri(request);

            // Search via Resource controller
            String key = this.resolveKeyFromUri(uri);

            if (key == null) {
                return;
            }

            TemproraryResourceEntry entry = NabuccoServletUtil.getResourceController().resolveTemproraryReference(key);
            byte[] data = entry.getData();

            if (data != null) {
                ByteArrayInputStream in = new ByteArrayInputStream(data);
                String contentType = entry.getType().getContentTypeString();

                response.sendData(in, contentType);
            }
        } catch (ClientException ce) {
            // TODO: Error page!
            response.sendRedirect("/login");
            throw ce;
        }
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
        String[] split = url.split(PATH_SEPARATOR);

        for (String key : split) {
            if (key.matches(DIGIT_REGEX_PATTERN)) {
                return key;
            }
        }

        return null;
    }
}
