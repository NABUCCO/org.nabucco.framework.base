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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.component.Component;
import org.nabucco.framework.base.facade.component.connection.Connection;
import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.component.connection.ConnectionFactory;
import org.nabucco.framework.base.facade.component.connection.ConnectionSpecification;
import org.nabucco.framework.base.facade.component.locator.LookupUtility;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.queryfilter.QueryFilterExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.queryfilter.QueryFilterParameterExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.queryfilter.QueryFilterSetExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.queryfilter.QueryFilterParameter;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.DatatypeListMsg;
import org.nabucco.framework.base.facade.message.queryfilter.QueryFilterRq;
import org.nabucco.framework.base.ui.web.action.WebActionHandler;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.communication.queryfilter.QueryFilterServiceDelegate;
import org.nabucco.framework.base.ui.web.component.work.util.QueryFilterExtensionUtil;
import org.nabucco.framework.base.ui.web.session.NabuccoWebSession;
import org.nabucco.framework.base.ui.web.session.NabuccoWebSessionFactory;

/**
 * FilteringActionHandler
 * 
 * The action handler for the action that need to process filter logic
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public abstract class FilteringActionHandler extends WebActionHandler {

    private NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(FilteringActionHandler.class);

    /**
     * Apply the filter search and set the content of the table model to the correct value
     * 
     * @param filterId
     *            the id of the filter to be applied (example "Project.Finished")
     * @param requestParameter
     *            given request parameter element
     * @return List with filtered elements or null if nothing found
     * 
     * @throws IllegalAccessException
     *             If cannot access filter service
     * @throws InstantiationException
     *             if cannot instanciate filter service
     * @throws ServiceException
     *             if exception if the filter service
     * 
     */
    protected List<Datatype> getFilteredData(String filterId, WebActionParameter requestParameter)
            throws ClientException {

        try {
            if (filterId == null) {
                this.logger.debug("No filters defined for the editor");
                return Collections.<Datatype> emptyList();
            }

            QueryFilterSetExtension filterSetExtension = QueryFilterExtensionUtil.getFilterSetExtension(filterId);

            if (filterSetExtension == null) {
                throw new ClientException("Cannot apply filter with id " + filterId + ". Filterset was not found");
            }

            QueryFilterExtension filterExtension = QueryFilterExtensionUtil.getFilterExtension(
                    filterSetExtension, filterId);

            if (filterExtension == null) {
                throw new ClientException("Cannot apply filter with id "
                        + filterId + "Filter was not found in the filter set");
            }
            Class<Component> componentClass = PropertyLoader.loadProperty(filterSetExtension.getComponent());
            QueryFilterRq msg = new QueryFilterRq();
            msg.setFilterId(new ExtensionId(filterId));

            // Try to fill parameters if the working item is editor
            NabuccoList<QueryFilterParameterExtension> parameters = filterExtension.getParameters();
            for (QueryFilterParameterExtension parameter : parameters) {
                QueryFilterParameter queryParameter = new QueryFilterParameter();

                String parameterName = PropertyLoader.loadProperty(parameter.getName());
                queryParameter.setName(parameterName);

                Class<?> propertyClassType = PropertyLoader.loadProperty(parameter.getType());
                String parameterValue = this
                        .getQueryFilterParameter(requestParameter, parameterName, propertyClassType);

                if (parameterValue != null) {
                    queryParameter.setValue(parameterValue);
                    msg.getParameters().add(queryParameter);
                }
            }

            try {
                NabuccoWebSession session = NabuccoWebSessionFactory.getCurrentSession();
                ConnectionSpecification spec = session.getConnection();
                if (spec == null) {
                    throw new IllegalStateException("Connection specification is null. Cannot continue.");
                }
                Connection connection = ConnectionFactory.getInstance().createConnection(spec);
                if (connection == null) {
                    throw new IllegalStateException("Connection is null. Cannot continue.");
                }

                Component component = LookupUtility.getComponent(componentClass, connection);
                QueryFilterServiceDelegate queryFilterService = new QueryFilterServiceDelegate(
                        component.getQueryFilterService());

                DatatypeListMsg rs = queryFilterService.filter(msg, session);

                NabuccoList<NabuccoDatatype> result = rs.getDatatypeList();

                return new ArrayList<Datatype>(result);

            } catch (ConnectionException ce) {
                throw new ClientException("Cannot locate service.", ce);
            } catch (SearchException sre) {
                throw new ClientException(sre);
            } catch (ServiceException se) {
                throw new ClientException(se);
            }

        } catch (ExtensionException e) {
            this.logger.debug("Cannot apply filter with id " + filterId, e);
        }

        return null;
    }

    /**
     * Hook to get the parameter for the queryfilter if any
     * 
     * @param
     * @param paramterName
     *            the name of the parameter to search for
     * @param classType
     *            the awaiting class of parameter
     * @return String representation of the parameter or null if cannot resolve or not mapped
     * 
     * @throws ClientException
     *             if the classtype is different from given classtype or is not a Basetype or
     *             Enumeration
     */
    protected abstract String getQueryFilterParameter(WebActionParameter requestParameter, String paramterName,
            Class<?> classType) throws ClientException;

}
