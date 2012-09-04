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
package org.nabucco.framework.base.impl.service.queryfilter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.DatatypeListMsg;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.queryfilter.QueryFilterRq;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.facade.service.queryfilter.QueryFilterService;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;

/**
 * QueryFilterServiceImpl<p/>Service for filtering datatype queries.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-04-18
 */
public class QueryFilterServiceImpl extends ServiceSupport implements QueryFilterService {

    private static final long serialVersionUID = 1L;

    private static final String ID = "QueryFilterService";

    private static Map<String, String[]> ASPECTS;

    private FilterServiceHandler filterServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new QueryFilterServiceImpl instance. */
    public QueryFilterServiceImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.filterServiceHandler = injector.inject(FilterServiceHandler.getId());
        if ((this.filterServiceHandler != null)) {
            this.filterServiceHandler.setPersistenceManager(persistenceManager);
            this.filterServiceHandler.setLogger(super.getLogger());
        }
    }

    @Override
    public void preDestroy() {
        super.preDestroy();
    }

    @Override
    public String[] getAspects(String operationName) {
        if ((ASPECTS == null)) {
            ASPECTS = new HashMap<String, String[]>();
            ASPECTS.put("filter", new String[] { "org.nabucco.aspect.validating", "org.nabucco.aspect.permissioning",
                    "org.nabucco.aspect.resolving" });
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<DatatypeListMsg> filter(ServiceRequest<QueryFilterRq> rq) throws SearchException {
        if ((this.filterServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for filter().");
            throw new InjectionException("No service implementation configured for filter().");
        }
        ServiceResponse<DatatypeListMsg> rs;
        this.filterServiceHandler.init();
        rs = this.filterServiceHandler.invoke(rq);
        this.filterServiceHandler.finish();
        return rs;
    }
}
