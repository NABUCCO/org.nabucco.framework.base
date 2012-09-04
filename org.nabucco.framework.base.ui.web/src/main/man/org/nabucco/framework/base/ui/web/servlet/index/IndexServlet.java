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
package org.nabucco.framework.base.ui.web.servlet.index;

import java.util.List;
import java.util.Map.Entry;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.component.Component;
import org.nabucco.framework.base.facade.component.connection.Connection;
import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.component.connection.ConnectionFactory;
import org.nabucco.framework.base.facade.component.locator.LookupUtility;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.queryfilter.QueryFilterSetExtension;
import org.nabucco.framework.base.facade.datatype.index.NabuccoIndex;
import org.nabucco.framework.base.facade.datatype.index.NabuccoIndexRegistry;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.DatatypeListMsg;
import org.nabucco.framework.base.facade.message.queryfilter.QueryFilterRq;
import org.nabucco.framework.base.ui.web.communication.queryfilter.QueryFilterServiceDelegate;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.WorkItem;
import org.nabucco.framework.base.ui.web.component.work.bulkeditor.BulkEditorItem;
import org.nabucco.framework.base.ui.web.component.work.editor.EditTab;
import org.nabucco.framework.base.ui.web.component.work.editor.EditorItem;
import org.nabucco.framework.base.ui.web.component.work.editor.control.EditorControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.PickerControl;
import org.nabucco.framework.base.ui.web.component.work.util.QueryFilterExtensionUtil;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.bulkeditor.column.BulkEditorColumnModel;
import org.nabucco.framework.base.ui.web.model.common.renderer.DefaultWebRenderer;
import org.nabucco.framework.base.ui.web.model.common.renderer.WebLabelProvider;
import org.nabucco.framework.base.ui.web.model.common.renderer.WebLabelProviderFactory;
import org.nabucco.framework.base.ui.web.model.editor.control.ControlModel;
import org.nabucco.framework.base.ui.web.model.editor.control.relation.picker.PickerControlModel;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServlet;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletRequest;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletResponse;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPath;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;
import org.nabucco.framework.base.ui.web.session.NabuccoWebSession;

/**
 * IndexServlet
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class IndexServlet extends NabuccoServlet {

    private static final long serialVersionUID = 1L;

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(IndexServlet.class);

    private static final String JSON_ID = "id";

    private static final String JSON_VALUE = "value";

    private static final String JSON_RESULTS = "results";

    @Override
    protected void restPost(NabuccoServletRequest request, NabuccoServletResponse response) throws ClientException {

        NabuccoServletPath path = request.getServletPath();

        String indexName = path.getId(NabuccoServletPathType.INDEX);
        String query = path.getId(NabuccoServletPathType.QUERY);

        NabuccoIndex index = NabuccoIndexRegistry.getInstance().getIndex(indexName);

        if (index.isInitialized() == false) {
            this.initializeIndex(index, request.getSession(), request.getServletPath());
        }

        JsonMap json = new JsonMap();
        JsonList results = new JsonList();

        for (Entry<String, Long> result : index.search(query).entrySet()) {
            JsonMap entry = new JsonMap();
            entry.add(JSON_ID, String.valueOf(result.getValue()));
            entry.add(JSON_VALUE, result.getKey());
            results.add(entry);
        }

        json.add(JSON_RESULTS, results);

        response.sendResponseParameter(json);
    }

    /**
     * Initialize the index with the given name.
     * 
     * @param index
     *            the index to add the entries to
     * @param servletPath
     *            the servlet path
     */
    private void initializeIndex(NabuccoIndex index, NabuccoWebSession session, NabuccoServletPath servletPath) {

        try {

            List<NabuccoDatatype> datatypes = this.findDatatypes(index.getName(), session);
            String label = this.getIndexLabel(servletPath);

            for (NabuccoDatatype datatype : datatypes) {

                WebLabelProviderFactory<NabuccoDatatype> factory = new WebLabelProviderFactory<NabuccoDatatype>();
                WebLabelProvider<NabuccoDatatype> provider = factory.createLabelProvider(datatype);

                String value = provider.getLabelFromMessage(label, new DefaultWebRenderer());
                index.add(value, datatype.getId());
            }

            index.markAsInitialized();
        } catch (Exception e) {
            logger.error(e, "Error initializing index '", index.getName(), "'.");
        }
    }

    /**
     * Resolve the label to index.
     * 
     * @param path
     *            the servlet path
     * 
     * @return the index label
     */
    private String getIndexLabel(NabuccoServletPath path) {

        String editorId = path.getId(NabuccoServletPathType.EDITOR);
        String bulkEditorId = path.getId(NabuccoServletPathType.BULKEDITOR);

        if (editorId != null) {
            EditorItem editor = NabuccoServletUtil.getEditor(editorId);
            if (editor == null) {
                return null;
            }

            String tabId = path.getId(NabuccoServletPathType.TAB);
            String controlId = path.getId(NabuccoServletPathType.PICKER);
            EditTab tab = editor.getEditArea().getTab(tabId);
            if (tab == null) {
                return null;
            }
            EditorControl control = tab.getControl(controlId);
            if (control == null || control.getType() != WebElementType.PICKER) {
                return null;
            }

            return ((PickerControl) control).getDisplayPath();
        } else if (bulkEditorId != null) {
            WorkItem workItem = NabuccoServletUtil.getWorkItem(bulkEditorId);
            if (workItem == null) {
                return null;
            }
            BulkEditorItem bulkEditor = (BulkEditorItem) workItem;
            String columnId = path.getId(NabuccoServletPathType.COLUMN);
            BulkEditorColumnModel column = bulkEditor.getModel().getColumnById(columnId);
            ControlModel<?> controlModel = column.getControlModel(null);

            if (controlModel instanceof PickerControlModel) {
                PickerControlModel pcm = (PickerControlModel) controlModel;
                String displayPath = pcm.getDisplayPath();
                return displayPath;
            }

            return null;
        } else {
            return null;
        }
    }

    /**
     * Find the datatype with
     * 
     * @param filterId
     *            filter id to find the datatype for
     * @param session
     *            the web session
     * 
     * @return the found datatype, or null if nothing was found
     * 
     * @throws ExtensionException
     *             when the filter extension cannot be resolved
     * @throws ServiceException
     *             when the query filter service raises an exception
     * @throws ConnectionException
     *             when the server connection cannot be established
     */
    private List<NabuccoDatatype> findDatatypes(String filterId, NabuccoWebSession session) throws ExtensionException,
            ServiceException, ConnectionException {

        QueryFilterSetExtension filterSetExtension = QueryFilterExtensionUtil.getFilterSetExtension(filterId);
        Class<Component> componentClass = PropertyLoader.loadProperty(filterSetExtension.getComponent());

        Connection connection = ConnectionFactory.getInstance().createConnection(session.getConnection());
        Component component = LookupUtility.getComponent(componentClass, connection);

        QueryFilterServiceDelegate queryFilterService = new QueryFilterServiceDelegate(
                component.getQueryFilterService());

        QueryFilterRq rq = new QueryFilterRq();
        rq.setFilterId(new ExtensionId(filterId));
        DatatypeListMsg rs = queryFilterService.filter(rq, session);

        return rs.getDatatypeList();
    }

}
