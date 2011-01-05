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
package org.nabucco.framework.base.impl.service.componentrelation;

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationListMsg;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationSearchRq;
import org.nabucco.framework.base.impl.service.handler.ServiceHandler;
import org.nabucco.framework.base.impl.service.handler.ServiceHandlerSupport;

/**
 * 
 * SearchComponentRelationServiceHandler
 * 
 * @author Dominic Trumpfheller, PRODYNA AG
 */
public abstract class SearchComponentRelationServiceHandler extends ServiceHandlerSupport implements
        ServiceHandler {

    private static final String ID = "org.nabucco.framework.base.impl.service.componentrelation.SearchComponentRelationServiceHandler";

    private static final long serialVersionUID = 1L;

    /** Constructs a new SearchComponentRelationServiceHandler instance. */
    public SearchComponentRelationServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     * 
     * @param rq
     *            the ServiceRequest<ComponentRelationSearchRq>.
     * @return the ServiceResponse<ComponentRelationListMsg>.
     * @throws SearchException
     */
    protected ServiceResponse<ComponentRelationListMsg> invoke(
            ServiceRequest<ComponentRelationSearchRq> rq) throws SearchException {
        
        ServiceResponse<ComponentRelationListMsg> rs;
        ComponentRelationListMsg msg;
      
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            
            msg = this.searchComponentRelation(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }
           
            rs = new ServiceResponse<ComponentRelationListMsg>(rq.getContext());
            rs.setResponseMessage(msg);
            return rs;
       
        } catch (SearchException e) {
            super.getLogger().error(e);
            throw e;
       
        } catch (NabuccoException e) {
            super.getLogger().error(e);
            SearchException wrappedException = new SearchException(e);
            throw wrappedException;
       
        } catch (Exception e) {
            super.getLogger().error(e);
            throw new SearchException(e.getMessage());
        }
    }

    /**
     * Missing description at method searchComponentRelation.
     * 
     * @param msg
     *            the ComponentRelationSearchRq.
     * @return the ComponentRelationListMsg.
     * @throws SearchException
     */
    protected abstract ComponentRelationListMsg searchComponentRelation(
            ComponentRelationSearchRq msg) throws SearchException;

    /**
     * Getter for the Id.
     * 
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
