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

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.WebActionHandlerSupport;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.common.table.TableModelFilterManager;
import org.nabucco.framework.base.ui.web.component.dialog.Dialog;

/**
 * CreateGenericListFilterActionHandler
 * 
 * Creates a new manually Dialog wth the columns from the actuall List as filter parameter
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ApplyGenericListFilterActionHandler extends WebActionHandlerSupport {

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ClientException {
        WebActionResult result = new WebActionResult();

        WebElement element = parameter.getElement();
        if (element instanceof Dialog == false) {
            return result;
        }
        Dialog dialog = (Dialog) element;
        TableModelFilterManager.getInstance().createFilter(dialog.getInstanceId());

        result.addItem(new RefreshItem(WebElementType.LIST));

        return result;
    }

}
