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
package org.nabucco.framework.base.ui.web.action.handler.perspective;

import java.util.List;

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.WebActionHandler;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebApplication;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.perspective.Perspective;
import org.nabucco.framework.base.ui.web.component.work.WorkItem;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;

/**
 * Checks if the application is dirty
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class CheckPerspectiveAreaIsDirtyActionHandler extends WebActionHandler {

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ClientException {
        boolean dirty = false;

        WebApplication application = NabuccoServletUtil.getApplication();
        for (Perspective perspective : application.getPerspectiveArea().getPerspectives()) {
            List<WorkItem> allItems = perspective.getWorkArea().getAllItems();
            for (WorkItem item : allItems) {
                boolean dirtyFlag = item.getModel().isDirty();
                if (dirtyFlag == true) {
                    dirty = true;
                    break;
                }
            }
        }

        WebActionResult retVal = new WebActionResult();
        retVal.addItem(new RefreshItem(WebElementType.DIRTYFLAG, null, String.valueOf(dirty)));
        return retVal;
    }
}
