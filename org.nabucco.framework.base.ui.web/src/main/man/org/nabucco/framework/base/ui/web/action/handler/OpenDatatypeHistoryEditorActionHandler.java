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
import org.nabucco.framework.base.facade.datatype.history.DatatypeHistory;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.serialization.DeserializationData;
import org.nabucco.framework.base.facade.datatype.serialization.SerializationException;
import org.nabucco.framework.base.facade.datatype.serialization.xml.XmlSerializer;
import org.nabucco.framework.base.facade.datatype.xml.XmlDocument;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.client.action.ActionException;
import org.nabucco.framework.base.ui.web.action.WebActionHandlerSupport;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.WorkArea;
import org.nabucco.framework.base.ui.web.component.work.editor.EditorItem;
import org.nabucco.framework.base.ui.web.component.work.editor.RelationTab;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * Action Handler Support for opening Editors.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class OpenDatatypeHistoryEditorActionHandler extends WebActionHandlerSupport {

    NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(OpenDatatypeHistoryEditorActionHandler.class);

    @Override
    public final WebActionResult execute(WebActionParameter parameter) throws ClientException {

        WebActionResult result = new WebActionResult();

        DatatypeHistory historyContainer = (DatatypeHistory) this.resolve(parameter);

        if (historyContainer == null) {
            return result;
        }

        String itemId = this.getEditorId(parameter, historyContainer);
        if (itemId == null || itemId.isEmpty()) {
            logger.warning("Cannot open Editor with id '" + itemId + "'.");
            return result;
        }

        WorkArea workArea = NabuccoServletUtil.getWorkArea();

        if (workArea == null) {
            throw new ActionException("Cannot locate current work area.");
        }

        try {
            EditorItem editor;

            if (historyContainer.getId() == null) {
                throw new IllegalArgumentException("Cannot open history item with id 'null'");
            } else {
                editor = workArea.newEditor(itemId, String.valueOf(historyContainer.getId()));
            }

            // Add sources to the new editor
            this.addSourceItem(parameter, editor);
            editor.setSourceWebElement(parameter.getElement());

            Datatype historyDatatype = this.deserializeHistoryDatatype(historyContainer);

            editor.getModel().setDatatype(historyDatatype);

        } catch (ExtensionException ee) {
            throw new ActionException("Error opening editor with id '" + itemId + "'.", ee);
        }

        result.addItem(new RefreshItem(WebElementType.WORK_AREA));
        result.addItem(new RefreshItem(WebElementType.BROWSER_AREA));

        return result;
    }

    /**
     * Getter for the editor to open
     * 
     * @param parameter
     *            parameter
     * @param historyContainer
     *            container
     * @return id of editor to be opened
     */
    protected abstract String getEditorId(WebActionParameter parameter, DatatypeHistory historyContainer);

    /**
     * Deserializes the xml document from the history
     * 
     * @param datatypeHistory
     *            the history to be deserialized
     * @return Deserialized datatype
     */
    private Datatype deserializeHistoryDatatype(DatatypeHistory datatypeHistory) {
        try {

            XmlDocument xml = datatypeHistory.getXml();
            XmlSerializer serializer = new XmlSerializer();
            DeserializationData data = new DeserializationData(xml.getValue());
            List<Datatype> deserialize = serializer.deserialize(data);

            if (deserialize.isEmpty()) {
                throw new IllegalArgumentException("Cannot deserialize XML Document");
            }

            return deserialize.get(0);
        } catch (SerializationException e) {
            throw new IllegalArgumentException("Cannot deserialize XML Document", e);
        }
    }

    /**
     * Getter for the source work item.
     * 
     * @param parameter
     *            the web parameter
     * @param target
     *            the target item
     */
    private void addSourceItem(WebActionParameter parameter, EditorItem target) {
        String editorId = parameter.get(NabuccoServletPathType.EDITOR);
        EditorItem source = NabuccoServletUtil.getEditor(editorId);

        target.setSource(source);
    }

    /**
     * Returns the source editor or null if not fount
     * 
     * @param parameter
     *            parameter
     * @return editor or null
     */
    protected EditorItem getSourceEditor(WebActionParameter parameter) {
        String editorId = parameter.get(NabuccoServletPathType.EDITOR);
        EditorItem source = NabuccoServletUtil.getEditor(editorId);

        return source;
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
    private Datatype resolve(WebActionParameter parameter) throws ClientException {

        try {
            Long id = this.resolveDatatypeId(parameter);

            WebElement element = parameter.getElement();

            if (element instanceof RelationTab) {
                RelationTab tab = (RelationTab) element;
                Datatype datatype = tab.getModel().getTableModel().getDatatypeByObjectId(id);

                if (datatype == null) {
                    logger.warning("No valid Datatype resolved 'null'.");
                }

                return datatype;
            }

            return null;

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

}
