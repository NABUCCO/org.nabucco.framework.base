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

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletRequest;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletResponse;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathEntry;

/**
 * NabuccoGetHandler
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class RefreshHandler extends NabuccoServletHandler {

    /**
     * Creates a new {@link NabuccoGetHandler} instance.
     * 
     * @param request
     *            the servlet request
     * @param response
     *            the servlet response
     */
    public RefreshHandler(NabuccoServletRequest request, NabuccoServletResponse response) {
        super(request, response);
    }

    @Override
    protected void process(WebElement element, NabuccoServletPathEntry entry) throws ClientException {
        if (entry == null || entry.getNext() == null) {

            if (element != null) {
                // element.clear();
                super.getResponse().sendResponseParameter(element.toJson());
            }
        }
    }

}
