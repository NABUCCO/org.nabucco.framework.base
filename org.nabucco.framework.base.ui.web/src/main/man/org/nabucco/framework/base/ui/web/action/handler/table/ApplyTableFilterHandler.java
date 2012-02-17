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

import java.util.Collections;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.handler.FilteringActionHandler;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.WorkItem;
import org.nabucco.framework.base.ui.web.component.work.list.ListItem;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * ApplyTableFilterHandler
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ApplyTableFilterHandler extends FilteringActionHandler {

    public static String ID = "System.ApplyTableFilter";

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ClientException {
        String listId = parameter.get(NabuccoServletPathType.LIST);

        try {
            WorkItem item = NabuccoServletUtil.getWorkItem(listId);

            if (item == null) {
                item = NabuccoServletUtil.getSelectedWorkItem();
            }
            if (item instanceof ListItem) {

                ListItem list = (ListItem) item;
                String activeFilterId = list.getListModel().getActiveFilterId();

                List<Datatype> values = this.getFilteredData(activeFilterId, parameter);
                if (values == null) {
                    values = Collections.<Datatype> emptyList();
                }

                list.getTableModel().firstPage();
                list.getTableModel().setContent(values);
            }

        } catch (Exception e) {
            throw new ClientException("Cannot apply filter to List '" + listId + "'", e);
        }

        WebActionResult retVal = new WebActionResult();
        retVal.addItem(new RefreshItem(WebElementType.BROWSER_AREA));
        return retVal;
    }

    @Override
    protected String getQueryFilterParameter(WebActionParameter requestParameter, String paramterName,
            Class<?> classType) throws ClientException {
        return null;
    }

}
