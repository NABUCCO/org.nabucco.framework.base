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
package org.nabucco.framework.base.ui.web.servlet.setup;

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.model.editor.util.shortcuts.ControlShortcutResolver;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServlet;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletRequest;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletResponse;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * SetupServlet
 * 
 * Servlet that returns some custom setup data for the ui application. It use the key to determine
 * which controller or other element is to be jsoned.
 * 
 * It can be used by all JS usits who needs a custom initialization
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class SetupServlet extends NabuccoServlet {

    private static final String SHORTCUTS_KEY = "shortcuts";

    private static final long serialVersionUID = 1L;

    @Override
    protected void restGet(NabuccoServletRequest request, NabuccoServletResponse response) throws ClientException {

        WebActionParameter parameter = new WebActionParameter(request, null);
        String key = parameter.get(NabuccoServletPathType.VALUE);

        if (key == null || key.isEmpty()) {
            return;
        }

        if (key.equalsIgnoreCase(SHORTCUTS_KEY)) {
            response.sendResponseParameter(ControlShortcutResolver.getInstance().toJson());
        }

    }

}