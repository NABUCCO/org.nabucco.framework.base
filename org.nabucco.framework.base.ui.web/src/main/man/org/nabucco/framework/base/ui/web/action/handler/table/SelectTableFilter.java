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
import org.nabucco.framework.base.ui.web.action.WebActionHandlerSupport;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.WorkItem;
import org.nabucco.framework.base.ui.web.component.work.list.FilterItem;
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
public class SelectTableFilter extends WebActionHandlerSupport {

    private static final String DEFAULT_APPLY_FILTER_ACTION = ApplyTableFilterHandler.ID;

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ClientException {
        String listInstance = parameter.get(NabuccoServletPathType.LIST);
        String filterId = parameter.get(NabuccoServletPathType.FILTERID);

        WorkItem workItem = NabuccoServletUtil.getWorkItem(listInstance);

        if (workItem == null) {
            throw new ClientException("Cannot allocate the Work item with id " + listInstance);
        }
        if (workItem instanceof ListItem == false) {
            throw new IllegalArgumentException("Cannot execute table filter. The instance if not a list.");
        }

        ListItem listItem = (ListItem) workItem;
        listItem.getListModel().setActiveFilterId(filterId);

        FilterItem filter = listItem.getFilter(filterId);
        if (filter == null) {
            throw new IllegalStateException("The configured filter is not found in the list.");
        }

        String filterLoadAction = filter.getCustomLoadAction();

        if (filterLoadAction == null || filterLoadAction.isEmpty()) {
            filterLoadAction = DEFAULT_APPLY_FILTER_ACTION;
        }

        // Process filter
        WebActionResult result = new WebActionResult();
        WebActionResult res = super.executeAction(filterLoadAction, parameter);

        result.addResult(res);
        result.addItem(new RefreshItem(WebElementType.LIST, listInstance));

        return result;
    }

}
