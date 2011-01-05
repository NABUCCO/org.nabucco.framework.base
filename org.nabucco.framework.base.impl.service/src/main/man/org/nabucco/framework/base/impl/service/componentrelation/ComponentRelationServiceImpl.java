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

import javax.persistence.EntityManager;

import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationListMsg;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationMsg;
import org.nabucco.framework.base.facade.message.componentrelation.ComponentRelationSearchRq;
import org.nabucco.framework.base.facade.service.componentrelation.ComponentRelationService;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;

/**
 * ComponentRelationServiceImpl
 * 
 * @author Dominic Trumpfheller, PRODYNA AG
 */
public class ComponentRelationServiceImpl extends ServiceSupport implements
        ComponentRelationService {

    private static final String ID = "ComponentRelationService";

    private static final long serialVersionUID = 1L;

    private EntityManager entityManager;

    private MaintainComponentRelationServiceHandler maintainComponentRelationServiceHandler;

    private SearchComponentRelationServiceHandler searchComponentRelationServiceHandler;

    /** Constructs a new ComponentRelationServiceImpl instance. */
    public ComponentRelationServiceImpl() {
        super();
    }

    /** PostConstruct. */
    public void postConstruct() {

        InjectionProvider injector = InjectionProvider.getInstance(ID);

        this.maintainComponentRelationServiceHandler = injector
                .inject(MaintainComponentRelationServiceHandler.getId());
        if ((this.maintainComponentRelationServiceHandler != null)) {
            this.maintainComponentRelationServiceHandler.setEntityManager(this.entityManager);
            this.maintainComponentRelationServiceHandler.setLogger(super.getLogger());
        }

        this.searchComponentRelationServiceHandler = injector
                .inject(SearchComponentRelationServiceHandler.getId());
        if ((this.searchComponentRelationServiceHandler != null)) {
            this.searchComponentRelationServiceHandler.setEntityManager(this.entityManager);
            this.searchComponentRelationServiceHandler.setLogger(super.getLogger());
        }
    }

    /** PreDestroy. */
    public void preDestroy() {
    }

    @Override
    public ServiceResponse<ComponentRelationMsg> maintainComponentRelation(
            ServiceRequest<ComponentRelationMsg> rq) throws MaintainException {

        if ((this.maintainComponentRelationServiceHandler == null)) {
            super.getLogger().error(
                    "No service implementation configured for maintainComponentRelation().");
            throw new InjectionException(
                    "No service implementation configured for maintainComponentRelation().");
        }

        ServiceResponse<ComponentRelationMsg> rs;
        this.maintainComponentRelationServiceHandler.init();
        rs = this.maintainComponentRelationServiceHandler.invoke(rq);
        this.maintainComponentRelationServiceHandler.finish();

        return rs;
    }

    @Override
    public ServiceResponse<ComponentRelationListMsg> searchComponentRelation(
            ServiceRequest<ComponentRelationSearchRq> rq) throws SearchException {

        if ((this.searchComponentRelationServiceHandler == null)) {
            super.getLogger().error(
                    "No service implementation configured for searchComponentRelation().");
            throw new InjectionException(
                    "No service implementation configured for searchComponentRelation().");
        }

        ServiceResponse<ComponentRelationListMsg> rs;
        this.searchComponentRelationServiceHandler.init();
        rs = this.searchComponentRelationServiceHandler.invoke(rq);
        this.searchComponentRelationServiceHandler.finish();

        return rs;
    }
}
