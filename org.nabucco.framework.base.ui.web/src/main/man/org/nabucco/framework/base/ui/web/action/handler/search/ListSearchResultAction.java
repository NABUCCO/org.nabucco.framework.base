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

import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionMap;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.search.result.SearchResultExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.search.result.SearchResultFieldExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.search.fulltext.FulltextDocument;
import org.nabucco.framework.base.facade.datatype.search.query.FulltextQuery;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.WebAction;
import org.nabucco.framework.base.ui.web.action.WebActionRegistry;
import org.nabucco.framework.base.ui.web.action.handler.OpenListActionHandler;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.widgets.search.SearchBoxQueryParser;
import org.nabucco.framework.base.ui.web.component.work.WorkItemActionType;
import org.nabucco.framework.base.ui.web.component.work.list.ListItem;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.model.table.TableColumn;
import org.nabucco.framework.base.ui.web.model.table.TableModel;
import org.nabucco.framework.base.ui.web.model.table.renderer.ColumnLayoutType;
import org.nabucco.framework.base.ui.web.model.table.renderer.ColumnRenderer;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;
import org.nabucco.framework.base.ui.web.session.NabuccoWebSession;

/**
 * OpenSearchResultListAction
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class ListSearchResultAction extends OpenListActionHandler<FulltextDocument> {

    private static final String FIRST_ROW = "0";

    private static final String JSON_QUERY = "query";

    private static final String LIST_ID = "SearchResultList";

    /** Unique Instance ID (incremented on each search) */
    private static long instanceId = 0;

    /** Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(ListSearchResultAction.class);

    @Override
    protected final String getListId() {
        return LIST_ID;
    }

    @Override
    protected final String getListInstanceId() {
        return String.valueOf(instanceId++);
    }

    @Override
    protected final WebActionResult postOpen(WebActionParameter parameter, ListItem list) throws ClientException {
        super.postOpen(parameter, list);

        // Parse Query

        FulltextQuery query = this.createQuery(parameter);
        if (query == null || query.getIndexName() == null || query.getIndexName().getValue() == null) {
            logger.warning("Skipping Fulltext-Search since no valid SearchQuery is defined.");
            return null;
        }

        // Execute Query

        String indexName = query.getIndexName().getValue();

        List<FulltextDocument> datatypes = this.loadDatatypeList(query, parameter.getSession());
        if (datatypes == null) {
            logger.warning("No valid Search Result returned 'null'.");
            return null;
        }

        // Modify Table

        TableModel<FulltextDocument> tableModel = list.getTableModel();
        this.configureColumns(tableModel, indexName);

        tableModel.setContent(datatypes);
        tableModel.setLabelProviderFactory(new SearchResultLabelProviderFactory());

        // Open Single Result

        // TODO: Configureable by ExtensionPoint!
        if (datatypes.size() == 1) {
            String openAction = list.getWorkingItemAction(WorkItemActionType.OPEN);

            parameter.setParameter(NabuccoServletPathType.LIST, list.getInstanceId());
            parameter.setParameter(NabuccoServletPathType.ROW, FIRST_ROW);

            WebActionResult result = this.executeAction(openAction, parameter);

            NabuccoServletUtil.getWorkArea().closeItem(list.getInstanceId());

            return result;
        }

        return null;
    }

    /**
     * Create the fulltext query for the given action parameter.
     * 
     * @param parameter
     *            the web action parameter holding the query
     * 
     * @return
     */
    private FulltextQuery createQuery(WebActionParameter parameter) {
        JsonElement element = parameter.getJsonRequest().get(JSON_QUERY);

        if (element == null || element.getValue() == null) {
            logger.warning("SearchQuery 'null' is not valid.");
            return null;
        }

        String queryString = element.getValue();

        SearchBoxQueryParser parser = new SearchBoxQueryParser();
        return parser.parseSearchBoxQuery(queryString);
    }

    /**
     * Configure the list columns of the search result list.
     * 
     * @throws ClientException
     *             when the search result configuration is not valid
     */
    private void configureColumns(TableModel<FulltextDocument> tableModel, String index) throws ClientException {

        try {
            ExtensionMap extensions = NabuccoSystem.getExtensionResolver().resolveExtensions(
                    ExtensionPointType.ORG_NABUCCO_FRAMEWORK_SEARCH_RESULT);

            for (String extensionName : extensions.getExtensionNames()) {
                SearchResultExtension extension = (SearchResultExtension) extensions.getExtension(extensionName);

                String indexName = PropertyLoader.loadProperty(extension.getIndexName());
                if (indexName == null || !indexName.equals(index)) {
                    continue;
                }

                for (SearchResultFieldExtension fieldExtension : extension.getFieldList()) {
                    String fieldName = PropertyLoader.loadProperty(fieldExtension.getFieldName());
                    TableColumn column = new TableColumn(fieldName, fieldName);

                    column.setLabel(PropertyLoader.loadProperty(fieldExtension.getFieldLabel()));
                    column.setTooltip(PropertyLoader.loadProperty(fieldExtension.getFieldDescription()));
                    column.setVisible(PropertyLoader.loadProperty(fieldExtension.getVisible()));
                    column.setSortable(PropertyLoader.loadProperty(fieldExtension.getSortable()));
                    column.setRenderer(ColumnRenderer.getDefaultRenderer(ColumnLayoutType.TEXT));

                    tableModel.addColumn(column);
                }
            }

        } catch (ExtensionException ee) {
            logger.error(ee, "Error resolving search result extensions.");
            throw new ClientException("Error resolving search result extensions.", ee);
        }
    }

    /**
     * Search for the datatypes.
     * 
     * @param parameter
     *            the web action parameter
     * 
     * @return the loaded datatypes
     * 
     * @throws ClientException
     *             when the search fails
     */
    private List<FulltextDocument> loadDatatypeList(FulltextQuery query, NabuccoWebSession session)
            throws ClientException {

        try {
            return this.search(query, session);
        } catch (ClientException ce) {
            logger.error(ce, "Error during fulltext search.");
            throw ce;
        } catch (Exception e) {
            logger.error(e, "Error during fulltext search.");
            throw new ClientException("Error during fulltext search.");
        }
    }

    /**
     * Execute the action with the given action id.
     * 
     * @param actionId
     *            id of the action to execute
     * @param parameter
     *            web action parameter for the action
     * 
     * @return the action result of the executed action
     * 
     * @throws ClientException
     *             when the action cannot be executed or raises an exception
     */
    public final WebActionResult executeAction(String actionId, WebActionParameter parameter) throws ClientException {

        if (actionId == null || actionId.isEmpty()) {
            logger.warning("Cannot execute action with id 'null'.");
        }

        try {
            WebAction action = WebActionRegistry.getInstance().newAction(actionId);

            if (action == null) {
                logger.warning("No action configured for ID '", actionId, "'.");
                return null;
            }

            WebActionResult result = action.execute(parameter);

            return result;

        } catch (ClientException ce) {
            logger.error(ce, "Error executing NABUCCO Action '", actionId, "'.");
            throw ce;
        } catch (Exception e) {
            logger.error(e, "Error executing NABUCCO Action '", actionId, "'.");
            throw new ClientException("Error executing NABUCCO Action '" + actionId + "'.", e);
        }
    }

    /**
     * Search the given datatypes from the fulltext.
     * 
     * @param parameter
     *            the web action parameter
     * 
     * @return the searched datatypes or null if no result exist
     * 
     * @throws ClientException
     *             when the search operation fails
     */
    protected abstract List<FulltextDocument> search(FulltextQuery query, NabuccoWebSession session)
            throws ClientException;

}
