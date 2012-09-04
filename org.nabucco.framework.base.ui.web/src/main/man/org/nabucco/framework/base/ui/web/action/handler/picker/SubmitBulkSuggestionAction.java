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
package org.nabucco.framework.base.ui.web.action.handler.picker;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.component.Component;
import org.nabucco.framework.base.facade.component.connection.Connection;
import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.component.connection.ConnectionFactory;
import org.nabucco.framework.base.facade.component.locator.LookupUtility;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.queryfilter.QueryFilterExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.queryfilter.QueryFilterParameterExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.queryfilter.QueryFilterSetExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.property.ComponentRelationProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyType;
import org.nabucco.framework.base.facade.datatype.property.PropertyOwner;
import org.nabucco.framework.base.facade.datatype.queryfilter.QueryFilterParameter;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.DatatypeListMsg;
import org.nabucco.framework.base.facade.message.queryfilter.QueryFilterRq;
import org.nabucco.framework.base.ui.web.action.WebActionHandlerSupport;
import org.nabucco.framework.base.ui.web.action.handler.work.RefreshBulkEditorDependenciesHandler;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.communication.queryfilter.QueryFilterServiceDelegate;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.bulkeditor.column.BulkEditorPickerColumn;
import org.nabucco.framework.base.ui.web.component.work.util.QueryFilterExtensionUtil;
import org.nabucco.framework.base.ui.web.model.editor.control.ControlModel;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;
import org.nabucco.framework.base.ui.web.session.NabuccoWebSession;

/**
 * SubmitSuggestionAction
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class SubmitBulkSuggestionAction extends WebActionHandlerSupport {

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            SubmitBulkSuggestionAction.class);

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ClientException {
        WebActionResult result = new WebActionResult();

        BulkEditorPickerColumn column = (BulkEditorPickerColumn) parameter.getElement();
        String instanceid = parameter.get(NabuccoServletPathType.INSTANCE);

        String id = parameter.get(NabuccoServletPathType.VALUE);
        Datatype datatype = column.getEditorModel().getDatatype(instanceid);

        this.addToPicker(id, datatype, column, parameter.getSession());

        RefreshBulkEditorDependenciesHandler handler = new RefreshBulkEditorDependenciesHandler();
        WebActionResult dependencyResult = handler.execute(parameter);
        result.addResult(dependencyResult);

        result.addItem(new RefreshItem(WebElementType.BULKEDITOR_CONTROL, instanceid + "_" + column.getId()));
        result.addItem(new RefreshItem(WebElementType.BROWSER_AREA));

        return result;
    }

    /**
     * Add the value to the source picker.
     * 
     * @param value
     *            the selection value
     * @param pickerDialog
     *            the picker dialog
     * @param source
     *            the source picker
     * @param session
     *            the web session
     */
    private void addToPicker(String value, Datatype datatype, BulkEditorPickerColumn source, NabuccoWebSession session) {

        try {
            String filterId = source.getAutoCompletionFilter();
            NabuccoDatatype foundDatatype = this.findDatatype(value, filterId, session);

            ControlModel<?> controlModel = source.getModel().getControlModel(datatype);
            NabuccoProperty property = controlModel.getProperty();

            if (property.getPropertyType() == NabuccoPropertyType.COMPONENT_RELATION) {
                ComponentRelationProperty oldProperty = (ComponentRelationProperty) property;
                ComponentRelation<?> relation = oldProperty.newComponentRelationInstance();
                relation.setTarget(foundDatatype);

                NabuccoList<ComponentRelation<?>> relationList = oldProperty.getInstance();
                relationList.clear();
                relationList.add(relation);

                controlModel.setProperty(oldProperty);
            } else if (controlModel.getProperty().getPropertyType() == NabuccoPropertyType.DATATYPE) {
                NabuccoProperty newProperty = property.createProperty(foundDatatype);
                property.getParent().setProperty(newProperty);
            }

            PropertyOwner parent = property.getParent();

            if (parent instanceof Datatype) {
                Datatype instance = (Datatype) parent;

                // Only Persistent Datatypes must be set to MODIFIED.
                if (instance.getDatatypeState() == DatatypeState.PERSISTENT) {
                    instance.setDatatypeState(DatatypeState.MODIFIED);
                }
            }

        } catch (Exception e) {
            logger.error(e, "Error resolving auto completion suggestion with id '", value, "'.");
            return;
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
    private NabuccoDatatype findDatatype(String value, String filterId, NabuccoWebSession session)
            throws ExtensionException, ServiceException, ConnectionException {

        QueryFilterSetExtension filterSetExtension = QueryFilterExtensionUtil.getFilterSetExtension(filterId);
        QueryFilterExtension filterExtension = QueryFilterExtensionUtil.getFilterExtension(filterId);
        Class<Component> componentClass = PropertyLoader.loadProperty(filterSetExtension.getComponent());

        Connection connection = ConnectionFactory.getInstance().createConnection(session.getConnection());
        Component component = LookupUtility.getComponent(componentClass, connection);

        QueryFilterServiceDelegate queryFilterService = new QueryFilterServiceDelegate(
                component.getQueryFilterService());

        QueryFilterRq rq = new QueryFilterRq();
        rq.setFilterId(new ExtensionId(filterId));

        QueryFilterParameterExtension parameterExtension = filterExtension.getParameters().first();

        QueryFilterParameter parameter = new QueryFilterParameter();
        parameter.setName(PropertyLoader.loadProperty(parameterExtension.getName()));
        parameter.setValue(value);
        rq.getParameters().add(parameter);

        DatatypeListMsg rs = queryFilterService.filter(rq, session);

        return rs.getDatatypeList().first();
    }

}
