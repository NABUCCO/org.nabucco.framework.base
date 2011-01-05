/*
 * Copyright 2010 PRODYNA AG
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
package org.nabucco.framework.base.facade.component.application.connector;

import java.util.List;

import org.nabucco.framework.base.facade.component.Component;
import org.nabucco.framework.base.facade.datatype.DatatypeAccessor;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelationContainer;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelationState;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelationType;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationListMsg;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationMsg;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationSearchRq;
import org.nabucco.framework.base.facade.service.componentrelation.ComponentRelationService;

/**
 * DatatypeConnectorSupport
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class DatatypeConnectorSupport extends ConnectorSupport implements
        DatatypeConnector {

    private static final long serialVersionUID = 1L;

    private NabuccoDatatype source;

    private NabuccoDatatype target;

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            DatatypeConnectorSupport.class);

    /**
     * Creates a new {@link DatatypeConnectorSupport} instance.
     */
    protected DatatypeConnectorSupport() {
        super(ConnectorStrategy.AFTER, ConnectorType.DATATYPE);
    }

    @Override
    public final <S extends NabuccoDatatype> void setSourceDatatype(S datatype) {
        this.source = datatype;
    }

    /**
     * Getter for the source datatype.
     * 
     * @return Returns the source datatype.
     */
    protected final NabuccoDatatype getSourceDatatype() {
        return this.source;
    }

    /**
     * Getter for the target datatype.
     * 
     * @return Returns the target datatype.
     */
    protected final NabuccoDatatype getTargetDatatype() {
        return this.target;
    }

    /**
     * Setter for the target datatype instance.
     * 
     * @param datatype
     *            the target datatype
     */
    public final <T extends NabuccoDatatype> void setTargetDatatype(T datatype) {
        this.target = datatype;
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public final void maintain() throws ConnectorException {

        ComponentRelationContainer relationContainer = DatatypeAccessor.getInstance()
                .getComponentRelation(this.getSourceDatatype());

        for (ComponentRelation relation : relationContainer.getAllComponentRelations()) {

            ComponentRelationType relationType = relation.getRelationType();

            try {
                if (relation.getRelationState() != ComponentRelationState.PERSISTENT) {

                    this.target = relation.getTarget();
                    this.internalMaintain(relation);

                    relation.setTarget(this.target);

                    relation.setSourceId(new Identifier(this.source.getId()));

                    ComponentRelationMsg requestMessage = new ComponentRelationMsg();
                    requestMessage.setComponentRelation(relation);

                    Component component = this.lookupTargetComponent(relationType);

                    if (component == null) {
                        logger.error("Cannot maintain Component Relation '",
                                relationType.toString(), "'. Target component lookup failed.");
                    } else {
                        this.invokeMaintainRelation(component, requestMessage);
                    }

                }

            } catch (NabuccoException e) {
                throw new ConnectorException("Error maintaining relation "
                        + relationType + " for datatype " + this.source.getClass() + ".", e);
            }
        }
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public final void resolve() throws ConnectorException {

        for (ComponentRelationType relationType : this.getRelationTypes()) {

            try {
                Identifier sourceId = new Identifier(this.getSourceDatatype().getId());
                Name relationClass = new Name(relationType.getRelation().getCanonicalName());

                ComponentRelationSearchRq requestMessage = new ComponentRelationSearchRq();
                requestMessage.setSourceId(sourceId);
                requestMessage.setRelationType(relationType);
                requestMessage.setComponentRelationClass(relationClass);

                Component component = this.lookupTargetComponent(relationType);

                if (component == null) {
                    logger.error("Cannot resolving Component Relation '", relationType.toString(),
                            "'. Target component lookup failed.");
                } else {
                    ComponentRelationListMsg responseMessage = this.invokeResolveRelation(
                            component, requestMessage);

                    for (ComponentRelation relation : responseMessage.getComponentRelationList()) {

                        this.target = relation.getTarget();
                        this.internalResolve(relation);
                        relation.setTarget(this.target);

                        ComponentRelationContainer relationContainer = DatatypeAccessor
                                .getInstance().getComponentRelation(this.getSourceDatatype());

                        relationContainer.putComponentRelation(relation);
                    }
                }

            } catch (NabuccoException e) {
                throw new ConnectorException("Error resolving relation "
                        + relationType + " for datatype " + this.source.getClass() + ".", e);
            }
        }
    }

    /**
     * Retrieve the list of relation types.
     * 
     * @return the list of relation types
     */
    protected abstract List<? extends ComponentRelationType> getRelationTypes();

    /**
     * Retrieves a the target component for a given relation type.
     * 
     * @param relationType
     *            the relation type
     * 
     * @return the target component
     */
    protected abstract Component lookupTargetComponent(ComponentRelationType relationType)
            throws NabuccoException;

    /**
     * Maintains the target of the component relation.
     * 
     * @param relation
     *            the component relation
     * 
     * @throws NabuccoException
     *             when an error during service invocation occurs
     */
    protected abstract void internalMaintain(ComponentRelation<?> relation) throws NabuccoException;

    /**
     * Resolves the target of the component relation.
     * 
     * @param relation
     *            the component relation
     * 
     * @throws NabuccoException
     *             when an error during service invocation occurs
     */
    protected abstract void internalResolve(ComponentRelation<?> relation) throws NabuccoException;

    /**
     * Invoke the maintain relation operation.
     * 
     * @param component
     *            the component holding the target datatype.
     * @param requestMessage
     *            the relation request message
     * 
     * @return the relation response message
     * 
     * @throws NabuccoException
     */
    private ComponentRelationMsg invokeMaintainRelation(Component component,
            ComponentRelationMsg requestMessage) throws NabuccoException {

        ComponentRelationService service = component.getComponentRelationService();

        ServiceRequest<ComponentRelationMsg> request = new ServiceRequest<ComponentRelationMsg>(
                super.getServiceContext());

        request.setRequestMessage(requestMessage);

        ServiceResponse<ComponentRelationMsg> response = service.maintainComponentRelation(request);

        return response.getResponseMessage();
    }

    /**
     * Invoke the maintain relation operation.
     * 
     * @param component
     *            the component holding the target datatype.
     * @param requestMessage
     *            the relation request message
     * 
     * @return the relation response message
     * 
     * @throws NabuccoException
     */
    private ComponentRelationListMsg invokeResolveRelation(Component component,
            ComponentRelationSearchRq requestMessage) throws NabuccoException {

        ComponentRelationService service = component.getComponentRelationService();

        ServiceRequest<ComponentRelationSearchRq> request = new ServiceRequest<ComponentRelationSearchRq>(
                super.getServiceContext());

        request.setRequestMessage(requestMessage);

        ServiceResponse<ComponentRelationListMsg> response = service
                .searchComponentRelation(request);

        return response.getResponseMessage();
    }
}
