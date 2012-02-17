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
package org.nabucco.framework.base.impl.service.interceptor.service.connector;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.component.NabuccoInstance;
import org.nabucco.framework.base.facade.component.application.Application;
import org.nabucco.framework.base.facade.component.application.connector.Connector;
import org.nabucco.framework.base.facade.component.application.connector.ConnectorException;
import org.nabucco.framework.base.facade.component.application.connector.ConnectorStrategy;
import org.nabucco.framework.base.facade.component.application.connector.ConnectorType;
import org.nabucco.framework.base.facade.component.application.connector.DatatypeConnector;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeAccessor;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelationContainer;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.visitor.ServiceMessageVisitor;

/**
 * DatatypeConnectorVisitor
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
final class DatatypeConnectorVisitor extends ServiceMessageVisitor {

    /** The list of datatype connectors. */
    private List<DatatypeConnector> connectors = new ArrayList<DatatypeConnector>();

    private ServiceRequest<?> request;

    private ServiceResponse<?> response;

    /**
     * Creates a new {@link DatatypeConnectorVisitor} instance.
     * 
     * @param request
     *            the service request
     * @param response
     *            the service response
     */
    public DatatypeConnectorVisitor(ServiceRequest<?> request, ServiceResponse<?> response) {
        this.request = request;
        this.response = response;

        this.init();
    }

    /**
     * Retrieve the list of datatype connectors from the application.
     */
    private void init() {
        Application application = NabuccoInstance.getInstance().getApplication();
        for (Connector connector : application.getConnectors(ConnectorStrategy.AFTER)) {
            if (connector.getType() == ConnectorType.DATATYPE) {
                this.connectors.add((DatatypeConnector) connector);
            }
        }
    }

    @Override
    public void visit(Datatype datatype) throws VisitorException {
        super.visit(datatype);

        if (!(datatype instanceof NabuccoDatatype)) {
            return;
        }

        for (DatatypeConnector connector : this.connectors) {
            if (datatype.getClass() == connector.getSourceDatatypeClass()) {
                this.connect(connector, (NabuccoDatatype) datatype);
            }
        }
    }

    /**
     * Connects the source datatype to its target.
     * 
     * @param connector
     *            the connector
     * @param datatype
     *            the source datatype
     * 
     * @throws VisitorException
     *             when the connection did not finish successful
     */
    private void connect(DatatypeConnector connector, NabuccoDatatype datatype) throws VisitorException {

        connector.setSourceDatatype(datatype);
        connector.setSourceRequest(this.request);
        connector.setSourceResponse(this.response);

        ComponentRelationContainer relationContainer = DatatypeAccessor.getInstance().getComponentRelation(datatype);

        try {
            if (relationContainer.isEmpty()) {
                connector.resolve();
            } else {
                connector.maintain();
            }
        } catch (ConnectorException ce) {
            throw new VisitorException("Error connecting datatype '" + connector.getSourceDatatypeClass() + "'.", ce);
        }
    }

}
