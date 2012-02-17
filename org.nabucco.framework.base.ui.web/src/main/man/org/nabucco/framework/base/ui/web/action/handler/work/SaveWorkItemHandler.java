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
package org.nabucco.framework.base.ui.web.action.handler.work;

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.WebActionHandlerSupport;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.WorkArea;
import org.nabucco.framework.base.ui.web.component.work.WorkItem;
import org.nabucco.framework.base.ui.web.component.work.WorkItemActionType;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * SaveWorkItemHandler
 * 
 * The generic action handler that saves the CURRENT working item with the in the item configured
 * action
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class SaveWorkItemHandler extends WebActionHandlerSupport {

    public final static String ID = "System.SaveWorkItem";

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ClientException {

        WebActionResult result = new WebActionResult();

        try {

            WorkArea workArea = NabuccoServletUtil.getWorkArea();

            String workItemId = parameter.get(NabuccoServletPathType.WORK_AREA);

            WorkItem item = workArea.getItem(workItemId);
            String saveAction = item.getWorkingItemAction(WorkItemActionType.SAVE);

            // Try to save
            WebActionResult subResult = super.executeAction(saveAction, parameter);
            result.addResult(subResult);

            // Close Tab if saved
            if (!item.getModel().isDirty()) {

                // parameter.setParameter(NabuccoServletPathType.WORK_AREA, workItemId);
                subResult = super.executeAction(CloseWorkItemHandler.ID, parameter);
                result.addResult(subResult);

            } else {
                result.addItem(new RefreshItem(WebElementType.EDITOR, item.getInstanceId()));
            }

        } finally {
            // Close dialog
            WebActionResult subResult = super.executeAction(CloseDialogHandler.ID, parameter);
            result.addResult(subResult);
        }

        return result;
    }
}
