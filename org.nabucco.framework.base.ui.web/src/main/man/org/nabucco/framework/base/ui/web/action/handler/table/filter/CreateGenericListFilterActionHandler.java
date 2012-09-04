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
package org.nabucco.framework.base.ui.web.action.handler.table.filter;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.WebActionHandlerSupport;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.OpenItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.common.table.TableModelFilterManager;
import org.nabucco.framework.base.ui.web.component.work.editor.RelationTab;
import org.nabucco.framework.base.ui.web.component.work.list.ListItem;
import org.nabucco.framework.base.ui.web.model.table.TableModel;

/**
 * CreateGenericListFilterActionHandler
 * 
 * Creates a new manually Dialog wth the columns from the actuall List as filter parameter
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class CreateGenericListFilterActionHandler extends WebActionHandlerSupport {

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ClientException {
        WebElement element = parameter.getElement();

        TableModel<Datatype> tableModel = this.getTableModel(element);

        String dialogId = TableModelFilterManager.getInstance().generateFilterDialog(tableModel);

        WebActionResult result = new WebActionResult();

        result.addItem(new OpenItem(WebElementType.DIALOG, dialogId));

        return result;
    }

    private TableModel<Datatype> getTableModel(WebElement element) {
        if (element instanceof ListItem) {
            ListItem list = (ListItem) element;
            return list.getTableModel();
        } else if (element instanceof RelationTab) {
            RelationTab relationTab = (RelationTab) element;
            return relationTab.getTableModel();
        } else {
            return null;
        }
    }

}
