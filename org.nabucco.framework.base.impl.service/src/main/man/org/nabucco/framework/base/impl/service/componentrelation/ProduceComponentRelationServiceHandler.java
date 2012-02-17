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
package org.nabucco.framework.base.impl.service.componentrelation;

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationMsg;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationProduceRq;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandlerSupport;

/**
 * ProduceComponentRelationServiceHandler
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class ProduceComponentRelationServiceHandler extends PersistenceServiceHandlerSupport implements
        PersistenceServiceHandler {

    private static final String ID = "org.nabucco.framework.base.impl.service.componentrelation.ProduceComponentRelationServiceHandler";

    private static final long serialVersionUID = 1L;

    /** Constructs a new ProduceComponentRelationServiceHandler instance. */
    public ProduceComponentRelationServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     * 
     * @param rq
     *            the ServiceRequest<ComponentRelationMsg>.
     * @return the ServiceResponse<ComponentRelationMsg>.
     * @throws ProduceException
     */
    protected ServiceResponse<ComponentRelationMsg> invoke(ServiceRequest<ComponentRelationProduceRq> rq)
            throws ProduceException {

        ServiceResponse<ComponentRelationMsg> rs;
        ComponentRelationMsg msg;

        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());

            msg = this.produceComponentRelation(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }

            rs = new ServiceResponse<ComponentRelationMsg>(rq.getContext());
            rs.setResponseMessage(msg);
            return rs;

        } catch (ProduceException e) {
            super.getLogger().error(e);
            throw e;

        } catch (NabuccoException e) {
            super.getLogger().error(e);
            ProduceException wrappedException = new ProduceException(e);
            throw wrappedException;

        } catch (Exception e) {
            super.getLogger().error(e);
            throw new ProduceException(e.getMessage());
        }
    }

    /**
     * Produce the component relation
     * 
     * @param msg
     *            the ComponentRelationMsg.
     * 
     * @return the ComponentRelationMsg.
     * 
     * @throws ProduceException
     *             when the produce fails
     */
    protected abstract ComponentRelationMsg produceComponentRelation(ComponentRelationProduceRq msg)
            throws ProduceException;

    /**
     * Getter for the Id.
     * 
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
