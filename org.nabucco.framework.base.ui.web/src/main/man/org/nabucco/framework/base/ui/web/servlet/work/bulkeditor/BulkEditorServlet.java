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
package org.nabucco.framework.base.ui.web.servlet.work.bulkeditor;

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.component.work.WorkArea;
import org.nabucco.framework.base.ui.web.component.work.WorkItem;
import org.nabucco.framework.base.ui.web.component.work.bulkeditor.BulkEditorItem;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServlet;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletRequest;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletResponse;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.handler.ActionHandler;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * Servlet for handling the initialization, data population and action execution of Editor intances.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class BulkEditorServlet extends NabuccoServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void restGet(NabuccoServletRequest request, NabuccoServletResponse response) throws ClientException {
        String bulkEditorId = request.getServletPath().getId(NabuccoServletPathType.BULKEDITOR);
        WorkItem item = NabuccoServletUtil.getWorkItem(bulkEditorId);

        if (item instanceof BulkEditorItem) {
            BulkEditorItem list = (BulkEditorItem) item;
            list.getModel().init();
        }

        WorkArea workArea = NabuccoServletUtil.getWorkArea();
        BulkEditorRefreshHandler handler = new BulkEditorRefreshHandler(request, response);
        handler.execute(workArea);
    }

    @Override
    protected void restPost(NabuccoServletRequest request, NabuccoServletResponse response) throws ClientException {
        WorkArea workArea = NabuccoServletUtil.getWorkArea();

        ActionHandler actionHandler = new ActionHandler(request, response);
        actionHandler.execute(workArea);

    }

}
