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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationListMsg;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationMsg;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationProduceRq;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationSearchRq;
import org.nabucco.framework.base.facade.service.componentrelation.ComponentRelationService;
import org.nabucco.framework.base.facade.service.componentrelation.ComponentRelationServiceLocal;
import org.nabucco.framework.base.facade.service.componentrelation.ComponentRelationServiceRemote;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;

/**
 * ComponentRelationServiceImpl
 * 
 * @author Dominic Trumpfheller, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class ComponentRelationServiceImpl extends ServiceSupport implements ComponentRelationService,
        ComponentRelationServiceLocal, ComponentRelationServiceRemote {

    private static final long serialVersionUID = 1L;

    private static final String ID = "ComponentRelationService";

    private static Map<String, String[]> ASPECTS;

    private EntityManager entityManager;

    private ProduceComponentRelationServiceHandler produceComponentRelationServiceHandler;

    private MaintainComponentRelationServiceHandler maintainComponentRelationServiceHandler;

    private MaintainComponentRelationListServiceHandler maintainComponentRelationListServiceHandler;

    private SearchComponentRelationServiceHandler searchComponentRelationServiceHandler;

    /** Constructs a new ComponentRelationServiceImpl instance. */
    public ComponentRelationServiceImpl() {
        super();
    }

    /** PostConstruct. */
    public void postConstruct() {
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager);

        InjectionProvider injector = InjectionProvider.getInstance(ID);

        this.produceComponentRelationServiceHandler = injector.inject(ProduceComponentRelationServiceHandler.getId());
        if ((this.produceComponentRelationServiceHandler != null)) {
            this.produceComponentRelationServiceHandler.setPersistenceManager(persistenceManager);
            this.produceComponentRelationServiceHandler.setLogger(super.getLogger());
        }

        this.maintainComponentRelationServiceHandler = injector.inject(MaintainComponentRelationServiceHandler.getId());
        if ((this.maintainComponentRelationServiceHandler != null)) {
            this.maintainComponentRelationServiceHandler.setPersistenceManager(persistenceManager);
            this.maintainComponentRelationServiceHandler.setLogger(super.getLogger());
        }

        this.maintainComponentRelationListServiceHandler = injector.inject(MaintainComponentRelationListServiceHandler
                .getId());
        if ((this.maintainComponentRelationListServiceHandler != null)) {
            this.maintainComponentRelationListServiceHandler.setPersistenceManager(persistenceManager);
            this.maintainComponentRelationListServiceHandler.setLogger(super.getLogger());
        }

        this.searchComponentRelationServiceHandler = injector.inject(SearchComponentRelationServiceHandler.getId());
        if ((this.searchComponentRelationServiceHandler != null)) {
            this.searchComponentRelationServiceHandler.setPersistenceManager(persistenceManager);
            this.searchComponentRelationServiceHandler.setLogger(super.getLogger());
        }
    }

    /** PreDestroy. */
    public void preDestroy() {
    }

    @Override
    public String[] getAspects(String operationName) {
        if ((ASPECTS == null)) {
            ASPECTS = new HashMap<String, String[]>();
            ASPECTS.put("produceComponentRelation", new String[] { "org.nabucco.aspect.validating",
                    "org.nabucco.aspect.permissioning" });
            ASPECTS.put("maintainComponentRelation", new String[] { "org.nabucco.aspect.validating",
                    "org.nabucco.aspect.permissioning", "org.nabucco.aspect.resolving" });
            ASPECTS.put("maintainComponentRelationList", new String[] { "org.nabucco.aspect.validating",
                    "org.nabucco.aspect.permissioning", "org.nabucco.aspect.resolving" });
            ASPECTS.put("searchComponentRelation", new String[] { "org.nabucco.aspect.validating",
                    "org.nabucco.aspect.permissioning", "org.nabucco.aspect.resolving" });
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<ComponentRelationMsg> produceComponentRelation(ServiceRequest<ComponentRelationProduceRq> rq)
            throws ProduceException {

        if ((this.produceComponentRelationServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for produceComponentRelation().");
            throw new InjectionException("No service implementation configured for produceComponentRelation().");
        }

        ServiceResponse<ComponentRelationMsg> rs;
        this.produceComponentRelationServiceHandler.init();
        rs = this.produceComponentRelationServiceHandler.invoke(rq);
        this.produceComponentRelationServiceHandler.finish();

        return rs;
    }

    @Override
    public ServiceResponse<ComponentRelationMsg> maintainComponentRelation(ServiceRequest<ComponentRelationMsg> rq)
            throws MaintainException {

        if ((this.maintainComponentRelationServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for maintainComponentRelation().");
            throw new InjectionException("No service implementation configured for maintainComponentRelation().");
        }

        ServiceResponse<ComponentRelationMsg> rs;
        this.maintainComponentRelationServiceHandler.init();
        rs = this.maintainComponentRelationServiceHandler.invoke(rq);
        this.maintainComponentRelationServiceHandler.finish();

        return rs;
    }

    @Override
    public ServiceResponse<ComponentRelationListMsg> maintainComponentRelationList(
            ServiceRequest<ComponentRelationListMsg> rq) throws MaintainException {

        if ((this.maintainComponentRelationListServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for maintainComponentRelationList().");
            throw new InjectionException("No service implementation configured for maintainComponentRelationList().");
        }

        ServiceResponse<ComponentRelationListMsg> rs;
        this.maintainComponentRelationListServiceHandler.init();
        rs = this.maintainComponentRelationListServiceHandler.invoke(rq);
        this.maintainComponentRelationListServiceHandler.finish();

        return rs;
    }

    @Override
    public ServiceResponse<ComponentRelationListMsg> searchComponentRelation(
            ServiceRequest<ComponentRelationSearchRq> rq) throws SearchException {

        if ((this.searchComponentRelationServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for searchComponentRelation().");
            throw new InjectionException("No service implementation configured for searchComponentRelation().");
        }

        ServiceResponse<ComponentRelationListMsg> rs;
        this.searchComponentRelationServiceHandler.init();
        rs = this.searchComponentRelationServiceHandler.invoke(rq);
        this.searchComponentRelationServiceHandler.finish();

        return rs;
    }
}
