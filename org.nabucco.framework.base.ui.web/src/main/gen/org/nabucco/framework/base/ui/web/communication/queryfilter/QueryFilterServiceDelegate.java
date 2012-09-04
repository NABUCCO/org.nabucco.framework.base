/*
 * Copyright 2012 PRODYNA AG
 * 
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.nabucco.framework.base.ui.web.communication.queryfilter;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.datatype.session.NabuccoSession;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.DatatypeListMsg;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.queryfilter.QueryFilterRq;
import org.nabucco.framework.base.facade.service.queryfilter.QueryFilterService;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateSupport;

/**
 * QueryFilterServiceDelegate<p/>Service for filtering datatype queries.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-04-18
 */
public class QueryFilterServiceDelegate extends ServiceDelegateSupport {

    private QueryFilterService service;

    /**
     * Constructs a new QueryFilterServiceDelegate instance.
     *
     * @param service the QueryFilterService.
     */
    public QueryFilterServiceDelegate(QueryFilterService service) {
        super();
        this.service = service;
    }

    /**
     * Filter.
     *
     * @param subContexts the ServiceSubContext....
     * @param session the NabuccoSession.
     * @param message the QueryFilterRq.
     * @return the DatatypeListMsg.
     * @throws SearchException
     */
    public DatatypeListMsg filter(QueryFilterRq message, NabuccoSession session, ServiceSubContext... subContexts)
            throws SearchException {
        ServiceRequest<QueryFilterRq> request = new ServiceRequest<QueryFilterRq>(super.createServiceContext(session,
                subContexts));
        request.setRequestMessage(message);
        ServiceResponse<DatatypeListMsg> response = null;
        Exception exception = null;
        if ((this.service != null)) {
            super.handleRequest(request, session);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.filter(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(QueryFilterService.class, "filter", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }
        throw new SearchException("Cannot execute service operation: QueryFilterService.filter");
    }
}
