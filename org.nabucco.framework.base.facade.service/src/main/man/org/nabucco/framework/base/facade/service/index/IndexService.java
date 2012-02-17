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
package org.nabucco.framework.base.facade.service.index;

import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.search.IndexRequestMsg;
import org.nabucco.framework.base.facade.service.Service;

/**
 * Cross cutting service for indexing documents for full text search.
 * 
 * @author Frank Ratschinski
 */
public interface IndexService extends Service {

    final String JNDI_NAME = "nabucco/crosscutting/IndexServiceHandler";

    /**
     * Crosscutting service for indexing fulltext documents.
     * 
     * @param rq
     *            the service reqeuest
     * 
     * @return the service response
     * 
     * @throws SearchException
     *             when the indexing fails
     */
    ServiceResponse<EmptyServiceMessage> resolveExtension(ServiceRequest<IndexRequestMsg> rq) throws SearchException;

}
