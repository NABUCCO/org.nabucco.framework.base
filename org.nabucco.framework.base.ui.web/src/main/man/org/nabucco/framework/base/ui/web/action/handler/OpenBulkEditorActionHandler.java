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

import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.client.action.ActionException;
import org.nabucco.framework.base.ui.web.action.WebActionHandlerSupport;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.WorkArea;
import org.nabucco.framework.base.ui.web.component.work.WorkItem;
import org.nabucco.framework.base.ui.web.component.work.bulkeditor.BulkEditorItem;
import org.nabucco.framework.base.ui.web.component.work.editor.RelationTab;
import org.nabucco.framework.base.ui.web.component.work.list.ListItem;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * Action Handler Support for opening Bulk Editors.
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public abstract class OpenBulkEditorActionHandler<D extends NabuccoDatatype> extends WebActionHandlerSupport {

    @Override
    public final WebActionResult execute(WebActionParameter parameter) throws ClientException {

        String bulkEditorId = this.getBulkEditorId();
        String instanceId = this.getBulkEditorInstanceId();

        BulkEditorItem bulkEditor = null;

        if (bulkEditorId == null || bulkEditorId.isEmpty()) {
            throw new IllegalStateException("Cannot open List 'null'.");
        }
        if (instanceId == null || instanceId.isEmpty()) {
            throw new IllegalStateException("Cannot open List '"
                    + bulkEditorId + "' with instance id '" + instanceId + "'.");
        }

        try {
            WorkArea workArea = NabuccoServletUtil.getWorkArea();
            bulkEditor = workArea.newBulkEditor(bulkEditorId, instanceId);
        } catch (ExtensionException e) {
            throw new ClientException("Cannot create a new Bulk Editor with id " + bulkEditorId, e);
        }

        // Process filter
        WebActionResult retVal = new WebActionResult();

        WebActionResult fillListData = this.addBulkEditingData(parameter, bulkEditor);
        if (fillListData != null) {
            retVal.addResult(fillListData);
        }

        WebActionResult postOpenResult = this.postOpen(parameter, bulkEditor);
        if (postOpenResult != null) {
            retVal.addResult(postOpenResult);
        }

        retVal.addItem(new RefreshItem(WebElementType.WORK_AREA));
        retVal.addItem(new RefreshItem(WebElementType.BROWSER_AREA));

        return retVal;
    }

    /**
     * Fills the bulk editor with list values. This hook can be overloaded to get special data (not
     * a standard way) in the table
     * 
     * @param parameter
     *            web parameter element
     * @param bulkEditorItem
     *            the bulk editor item to be filled
     * @return result will be appended to the agrregated summ result
     * @throws ClientException
     *             if problems by filling
     * @throws ActionException
     *             if action not found
     */
    protected WebActionResult addBulkEditingData(WebActionParameter parameter, BulkEditorItem bulkEditorItem)
            throws ClientException, ActionException {

        WebElement element = parameter.getElement();
        if (element instanceof ListItem) {
            ListItem sourceList = (ListItem) element;
            List<Datatype> content = sourceList.getListModel().getTableModel().getContent();
            bulkEditorItem.getModel().setContent(content);
            bulkEditorItem.setSource(sourceList);
            bulkEditorItem.setSourceWebElement(sourceList);
        } else if (element instanceof RelationTab) {
            RelationTab tab = (RelationTab) element;
            List<Datatype> content = tab.getTableModel().getContent();
            bulkEditorItem.getModel().setContent(content);
            bulkEditorItem.setSourceWebElement(tab);

            String editorId = parameter.get(NabuccoServletPathType.EDITOR);
            WorkItem workItem = NabuccoServletUtil.getWorkItem(editorId);
            bulkEditorItem.setSource(workItem);

        }

        return null;
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
    protected WebActionResult postOpen(WebActionParameter parameter, BulkEditorItem list) throws ClientException {
        return null;
    }

    /**
     * Resolve the list instance id to open. The default implementation returns the list id for
     * unique list instances. Override for different list id.
     * 
     * @return the list instance id to open
     */
    protected String getBulkEditorInstanceId() {
        return this.getBulkEditorId();
    }

    /**
     * Resolve the list id to open.
     * 
     * @return the list id to open
     */
    protected abstract String getBulkEditorId();

}
