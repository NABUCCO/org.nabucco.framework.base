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
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.client.action.ActionException;
import org.nabucco.framework.base.ui.web.action.WebActionHandler;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.WorkArea;
import org.nabucco.framework.base.ui.web.component.work.editor.EditorItem;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * Action Handler Support for opening Editors.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class OpenEditorActionHandler<D extends NabuccoDatatype> extends WebActionHandler {

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(OpenEditorActionHandler.class);

    @Override
    public final WebActionResult execute(WebActionParameter parameter) throws ClientException {

        String itemId = this.getEditorId(parameter);

        WebActionResult result = new WebActionResult();

        if (itemId == null || itemId.isEmpty()) {
            logger.warning("Cannot open Editor with id '" + itemId + "'.");
            return result;
        }


        D datatype = this.resolve(parameter);

        if (datatype == null) {
            return result;
        }

        WorkArea workArea = NabuccoServletUtil.getWorkArea();

        if (workArea == null) {
            throw new ActionException("Cannot locate current work area.");
        }

        try {
            EditorItem editor;

            if (datatype.getId() == null) {
                editor = workArea.newEditor(itemId);
            } else {
                editor = workArea.newEditor(itemId, String.valueOf(datatype.getId()));
            }

            this.addSource(parameter, editor);

            editor.getModel().setDatatype(datatype);

        } catch (ExtensionException ee) {
            throw new ActionException("Error opening editor with id '" + itemId + "'.", ee);
        }

        result.addItem(new RefreshItem(WebElementType.WORK_AREA));
        result.addItem(new RefreshItem(WebElementType.BROWSER_AREA));

        return result;
    }

    /**
     * Getter for the source work item.
     * 
     * @param parameter
     *            the web parameter
     * @param target
     *            the target item
     */
    private void addSource(WebActionParameter parameter, EditorItem target) {
        String editorId = parameter.get(NabuccoServletPathType.EDITOR);
        EditorItem source = NabuccoServletUtil.getEditor(editorId);

        target.setSource(source);
    }

    /**
     * Resolve the datatype.
     * 
     * @param parameter
     *            the web action parameter
     * 
     * @return the resolved datatype
     * 
     * @throws ClientException
     *             when the resolve fails
     */
    private D resolve(WebActionParameter parameter) throws ClientException {

        try {
            Long id = this.resolveDatatypeId(parameter);
            D datatype = null;

            datatype = this.loadDatatype(id, parameter);

            if (datatype == null) {
                logger.warning("No valid Datatype resolved 'null'.");
            }

            return datatype;

        } catch (ClientException ce) {
            logger.error(ce, "Error resolving Datatype.");
            throw ce;
        } catch (Exception e) {
            logger.error(e, "Error resolving Datatype.");
            throw new ClientException("Error resolving Datatype.");
        }
    }

    /**
     * Resolve the datatype id from the JSON request parameter.
     * 
     * @param parameter
     *            the request parameter
     * 
     * @return the resolved id
     * 
     * @throws ActionException
     *             when the id cannot be resovled
     */
    private Long resolveDatatypeId(WebActionParameter parameter) throws ActionException {

        String datatypeId = parameter.get(NabuccoServletPathType.INSTANCE);
        if (datatypeId == null || datatypeId.isEmpty()) {
            return null;
        }

        try {
            return Long.parseLong(datatypeId);
        } catch (NumberFormatException nfe) {
            throw new ActionException("Error resolving datatype id '" + datatypeId + "'.", nfe);
        }
    }

    /**
     * Resolve the editor id to open.
     * 
     * @param parameter
     *            the web action parameter
     * 
     * @return the editor id to open
     * 
     * @throws ClientException
     *             when the editor id cannot be resolved
     */
    protected abstract String getEditorId(WebActionParameter parameter) throws ClientException;

    /**
     * Resolve the given datatype from the database.
     * 
     * @param parameter
     *            the web action parameter
     * @param datatypeId
     *            the id of the datatype
     * 
     * @return the resolved datatype
     * 
     * @throws ClientException
     *             when the resolve operation fails
     */
    protected abstract D loadDatatype(Long datatypeId, WebActionParameter parameter) throws ClientException;

}
