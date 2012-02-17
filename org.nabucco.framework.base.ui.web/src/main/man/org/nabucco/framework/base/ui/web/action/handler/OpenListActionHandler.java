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
import org.nabucco.framework.base.ui.web.action.WebAction;
import org.nabucco.framework.base.ui.web.action.WebActionHandler;
import org.nabucco.framework.base.ui.web.action.WebActionRegistry;
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
public abstract class OpenListActionHandler<D extends Datatype> extends WebActionHandler {

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
        WebAction action = WebActionRegistry.getInstance().newAction(ApplyTableFilterHandler.ID);

        if (action == null) {
            throw new ClientException("Cannot instanciate filter action.");
        }
        action.execute(parameter);

        WebActionResult result = this.postOpen(parameter, list);
        if (result == null) {
            result = new WebActionResult();
        }

        result.addItem(new OpenItem(WebElementType.LIST, list.getInstanceId()));
        result.addItem(new RefreshItem(WebElementType.WORK_AREA));
        result.addItem(new RefreshItem(WebElementType.BROWSER_AREA));

        return result;
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
