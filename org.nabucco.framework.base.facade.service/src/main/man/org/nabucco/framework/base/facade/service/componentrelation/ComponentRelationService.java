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
package org.nabucco.framework.base.facade.service.componentrelation;

import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationListMsg;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationMsg;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationProduceRq;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationSearchRq;
import org.nabucco.framework.base.facade.service.Service;

/**
 * 
 * ComponentRelationService
 * 
 * @author Dominic Trumpfheller, PRODYNA AG
 */
public interface ComponentRelationService extends Service {

    String JNDI_NAME = "components/org.nabucco.framework.base/org.nabucco.framework.base.facade.service.componentrelation.ComponentRelationService";

    /**
     * Produce a new component relation instance.
     * 
     * @param rq
     *            the service request holding the component relation parameter
     * 
     * @return the service response holding the new component relation instance
     * 
     * @throws ProduceException
     *             when the produce fails
     */
    ServiceResponse<ComponentRelationMsg> produceComponentRelation(ServiceRequest<ComponentRelationProduceRq> rq)
            throws ProduceException;
    
    /**
     * Maintain a single component relation.
     * 
     * @param rq
     *            the service request holding the component relation message
     * 
     * @return the service response holding the maintained component relation message
     * 
     * @throws MaintainException
     *             when the maintain fails
     */
    ServiceResponse<ComponentRelationMsg> maintainComponentRelation(ServiceRequest<ComponentRelationMsg> rq)
            throws MaintainException;

    /**
     * Maintain a list of component relations.
     * 
     * @param rq
     *            the service request holding the component relation list message
     * 
     * @return the service response holding the maintained component relation list message
     * 
     * @throws MaintainException
     *             when the maintain fails
     */
    ServiceResponse<ComponentRelationListMsg> maintainComponentRelationList(ServiceRequest<ComponentRelationListMsg> rq)
            throws MaintainException;

    /**
     * Search for a list of component relations.
     * 
     * @param rq
     *            the service request holding the search request message
     * 
     * @return the service response holding the search response message
     * 
     * @throws SearchException
     *             when the search fails
     */
    ServiceResponse<ComponentRelationListMsg> searchComponentRelation(ServiceRequest<ComponentRelationSearchRq> rq)
            throws SearchException;
}
