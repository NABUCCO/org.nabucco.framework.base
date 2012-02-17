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
package org.nabucco.framework.base.ui.web.action.handler.table;

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.WebAction;
import org.nabucco.framework.base.ui.web.action.WebActionHandler;
import org.nabucco.framework.base.ui.web.action.WebActionRegistry;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.WorkItem;
import org.nabucco.framework.base.ui.web.component.work.list.ListItem;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * SelectTableFilter
 * 
 * Sets the given filter id as selected filter to the given list item
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class SelectTableFilter extends WebActionHandler {

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ClientException {
        String listInstance = parameter.get(NabuccoServletPathType.LIST);
        String filterId = parameter.get(NabuccoServletPathType.FILTERID);

        WorkItem workItem = NabuccoServletUtil.getWorkItem(listInstance);

        if (workItem == null) {
            throw new ClientException("Cannot allocate the Work item with id " + listInstance);
        }
        if (workItem instanceof ListItem) {
            ((ListItem) workItem).getListModel().setActiveFilterId(filterId);
        }

        // Process filter
        WebAction action = WebActionRegistry.getInstance().newAction(ApplyTableFilterHandler.ID);

        if (action == null) {
            throw new ClientException("Cannot instanciate filter action.");
        }
        WebActionResult executeResult = action.execute(parameter);

        WebActionResult result = new WebActionResult();
        result.addResult(executeResult);
        result.addItem(new RefreshItem(WebElementType.LIST, listInstance));

        return result;
    }

}
