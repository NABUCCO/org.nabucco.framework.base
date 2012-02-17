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
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationListMsg;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandlerSupport;

/**
 * MaintainComponentRelationListServiceHandler
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class MaintainComponentRelationListServiceHandler extends PersistenceServiceHandlerSupport implements
        PersistenceServiceHandler {

    private static final String ID = "org.nabucco.framework.base.impl.service.componentrelation.MaintainComponentRelationListServiceHandler";

    private static final long serialVersionUID = 1L;

    /** Constructs a new MaintainComponentRelationListServiceHandler instance. */
    public MaintainComponentRelationListServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     * 
     * @param rq
     *            the ServiceRequest<ComponentRelationListMsg>.
     * @return the ServiceResponse<ComponentRelationListMsg>.
     * @throws MaintainException
     */
    protected ServiceResponse<ComponentRelationListMsg> invoke(ServiceRequest<ComponentRelationListMsg> rq)
            throws MaintainException {

        ServiceResponse<ComponentRelationListMsg> rs;
        ComponentRelationListMsg msg;

        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());

            msg = this.maintainComponentRelationList(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }

            rs = new ServiceResponse<ComponentRelationListMsg>(rq.getContext());
            rs.setResponseMessage(msg);
            return rs;

        } catch (MaintainException e) {
            super.getLogger().error(e);
            throw e;

        } catch (NabuccoException e) {
            super.getLogger().error(e);
            MaintainException wrappedException = new MaintainException(e);
            throw wrappedException;

        } catch (Exception e) {
            super.getLogger().error(e);
            throw new MaintainException(e.getMessage());
        }
    }

    /**
     * Missing description at method maintainComponentRelationList.
     * 
     * @param msg
     *            the ComponentRelationListMsg.
     * @return the ComponentRelationListMsg.
     * @throws MaintainException
     */
    protected abstract ComponentRelationListMsg maintainComponentRelationList(ComponentRelationListMsg msg)
            throws MaintainException;

    /**
     * Getter for the Id.
     * 
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
