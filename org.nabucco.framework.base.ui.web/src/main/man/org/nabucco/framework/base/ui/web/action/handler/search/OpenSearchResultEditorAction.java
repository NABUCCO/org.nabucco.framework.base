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
package org.nabucco.framework.base.ui.web.action.handler.search;

import org.nabucco.framework.base.facade.datatype.search.fulltext.FulltextDocument;
import org.nabucco.framework.base.facade.datatype.search.fulltext.FulltextField;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.WebActionHandlerSupport;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.work.list.ListItem;
import org.nabucco.framework.base.ui.web.model.table.TableModel;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * OpenSearchResultAction
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class OpenSearchResultEditorAction extends WebActionHandlerSupport {

    private static final String FIELD_ID = "PK";

    private static final String FIELD_TYPE = "Datatype";

    @Override
    public final WebActionResult execute(WebActionParameter parameter) throws ClientException {
        FulltextDocument document = this.loadDocument(parameter);

        if (document == null) {
            return new WebActionResult();
        }

        Class<?> type = this.getDocumentType(document);
        Long id = this.getDocumentId(document);

        if (type == null) {
            throw new ClientException("No datatype Class defined in fulltext document.");
        }
        if (id == null) {
            throw new ClientException("No datatype ID defined in fulltext document.");
        }

        parameter.setParameter(NabuccoServletPathType.INSTANCE, String.valueOf(id));

        String actionId = this.getActionId(type);

        if (actionId == null || actionId.isEmpty()) {
            throw new ClientException("No Editor for search result configured.");
        }

        return super.executeAction(actionId, parameter);
    }

    /**
     * Resolve the action id for the given datatype class.
     * 
     * @param datatypeType
     *            the datatype class holding to execute the action for
     * 
     * @return the id of the action
     */
    protected abstract String getActionId(Class<?> datatypeType);

    /**
     * Load the fulltext document from the table model.
     * 
     * @param parameter
     *            the action parameter
     * 
     * @return the fulltext document or null if not possible to load
     * 
     * @throws ClientException
     *             when the document cannot be loaded
     */
    private FulltextDocument loadDocument(WebActionParameter parameter) throws ClientException {
        String listId = parameter.get(NabuccoServletPathType.LIST);
        ListItem list = NabuccoServletUtil.getList(listId);

        if (list == null) {
            throw new ClientException("SearchResult list not found.");
        }

        TableModel<FulltextDocument> model = list.getTableModel();
        FulltextDocument document = this.getElementByRowId(model, parameter);

        return document;
    }

    /**
     * Resolve the document type.
     * 
     * @param document
     *            the document holding the type
     * 
     * @return the document class
     * 
     * @throws ClientException
     *             when the datatype class cannot be loaded
     */
    private Class<?> getDocumentType(FulltextDocument document) throws ClientException {

        for (FulltextField field : document.getFieldList()) {
            if (field.getFieldName() == null && field.getFieldName().getValue() == null) {
                continue;
            }

            if (field.getFieldName().getValue().equalsIgnoreCase(FIELD_TYPE)) {
                try {
                    Class<?> datatypeClass = Class.forName(field.getFieldValue().getValue());
                    return datatypeClass;
                } catch (Exception e) {
                    throw new ClientException("No valid datatype Class defined in fulltext document.", e);
                }
            }
        }

        return null;
    }

    /**
     * Resolve the document type.
     * 
     * @param document
     *            the document holding the type
     * 
     * @return the id as long
     * 
     * @throws ClientException
     *             when the datatype id cannot be parsed
     */
    private Long getDocumentId(FulltextDocument document) throws ClientException {

        for (FulltextField field : document.getFieldList()) {
            if (field.getFieldName() == null && field.getFieldName().getValue() == null) {
                continue;
            }

            if (field.getFieldName().getValue().equalsIgnoreCase(FIELD_ID)) {
                try {
                    return Long.parseLong(field.getFieldValue().getValue());
                } catch (Exception e) {
                    throw new ClientException("No valid datatype ID defined in fulltext document.", e);
                }
            }
        }

        return null;
    }

    /**
     * Getter for the document with the given row id.
     * 
     * @param model
     *            the table model holding the rows
     * @param parameter
     *            the web parameter holding the row
     * 
     * @throws ClientException
     *             when the row id is not valid
     */
    private FulltextDocument getElementByRowId(TableModel<FulltextDocument> model, WebActionParameter parameter)
            throws ClientException {

            String row = parameter.get(NabuccoServletPathType.ROW);

            if (row == null) {
                return null;
            }
        try {

            int rowId = Integer.parseInt(row);
            FulltextDocument document = model.getDatatypeByRowId(rowId);
            return document;
        } catch (NumberFormatException nfe) {
            throw new ClientException("Cannot find search result for row id '" + row + "'.", nfe);
        }
    }
}
