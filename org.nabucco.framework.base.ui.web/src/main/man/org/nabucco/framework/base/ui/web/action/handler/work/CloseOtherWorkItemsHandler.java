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

import org.nabucco.framework.base.facade.exception.client.action.ActionException;
import org.nabucco.framework.base.ui.web.action.WebActionHandler;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.WorkArea;
import org.nabucco.framework.base.ui.web.component.work.WorkItem;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;

/**
 * Action handler responsible for closing all work area tabs.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class CloseOtherWorkItemsHandler extends WebActionHandler {

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ActionException {

        WorkArea workArea = NabuccoServletUtil.getWorkArea();

        if (workArea == null) {
            throw new ActionException("Cannot locate Work Area.");
        }

        WorkItem currentItem = workArea.getCurrentItem();

        for (WorkItem item : workArea.getAllItems()) {

            if (currentItem == null || !currentItem.getInstanceId().equals(item.getInstanceId())) {
                workArea.closeItem(item.getInstanceId());
            }
        }

        WebActionResult result = new WebActionResult();
        result.addItem(new RefreshItem(WebElementType.WORK_AREA));
        result.addItem(new RefreshItem(WebElementType.BROWSER_AREA));

        return result;
    }

}
