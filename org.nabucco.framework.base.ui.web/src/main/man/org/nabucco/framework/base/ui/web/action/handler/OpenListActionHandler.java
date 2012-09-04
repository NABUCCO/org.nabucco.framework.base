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
package org.nabucco.framework.base.ui.web.action.handler;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.client.action.ActionException;
import org.nabucco.framework.base.ui.web.action.WebActionHandlerSupport;
import org.nabucco.framework.base.ui.web.action.handler.table.ApplyTableFilterHandler;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.OpenItem;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.WorkArea;
import org.nabucco.framework.base.ui.web.component.work.list.ListItem;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;

/**
 * Action Handler Support for opening Lists.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class OpenListActionHandler<D extends Datatype> extends WebActionHandlerSupport {

    @Override
    public final WebActionResult execute(WebActionParameter parameter) throws ClientException {

        String listId = this.getListId();
        String instanceId = this.getListInstanceId();

        ListItem list = null;

        if (listId == null || listId.isEmpty()) {
            throw new IllegalStateException("Cannot open List 'null'.");
        }
        if (instanceId == null || instanceId.isEmpty()) {
            throw new IllegalStateException("Cannot open List '" + listId + "' with instance id '" + instanceId + "'.");
        }

        try {
            WorkArea workArea = NabuccoServletUtil.getWorkArea();
            list = workArea.newList(listId, instanceId);
        } catch (ExtensionException e) {
            throw new ClientException("Cannot create a new List with id " + listId);
        }

        // Process filter
        WebActionResult retVal = new WebActionResult();

        WebActionResult fillListData = this.fillListData(parameter, list);
        if (fillListData != null) {
            retVal.addResult(fillListData);
        }

        WebActionResult postOpenResult = this.postOpen(parameter, list);
        if (postOpenResult != null) {
            retVal.addResult(postOpenResult);
        }

        retVal.addItem(new OpenItem(WebElementType.LIST, list.getInstanceId()));
        retVal.addItem(new RefreshItem(WebElementType.WORK_AREA));
        retVal.addItem(new RefreshItem(WebElementType.BROWSER_AREA));

        return retVal;
    }

    /**
     * Fills the list with values. Per default use the filter. This hook can be overloaded to get
     * special data (not a standard way) in the table
     * 
     * @param parameter
     *            web parameter element
     * @param listItem
     *            the list item to be filled
     * @return result will be appended to the agrregated summ result
     * @throws ClientException
     *             if problems by filling
     * @throws ActionException
     *             if action not found
     */
    protected WebActionResult fillListData(WebActionParameter parameter, ListItem listItem) throws ClientException,
            ActionException {

        return super.executeAction(ApplyTableFilterHandler.ID, parameter);
    }

    /**
     * Lifecylcle method called after opening the list item. May be overriden for list interaction.
     * 
     * @param parameter
     *            the web action parameter
     * @param list
     *            the opened list
     * 
     * @return a new web action result
     * 
     * @throws ClientException
     *             when the post open raises an exception
     */
    protected WebActionResult postOpen(WebActionParameter parameter, ListItem list) throws ClientException {
        return null;
    }

    /**
     * Resolve the list instance id to open. The default implementation returns the list id for
     * unique list instances. Override for different list id.
     * 
     * @return the list instance id to open
     */
    protected String getListInstanceId() {
        return this.getListId();
    }

    /**
     * Resolve the list id to open.
     * 
     * @return the list id to open
     */
    protected abstract String getListId();

}
