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
package org.nabucco.framework.base.ui.web.communication;

import org.nabucco.framework.base.facade.component.Component;
import org.nabucco.framework.base.facade.component.connection.Connection;
import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.component.connection.ConnectionFactory;
import org.nabucco.framework.base.facade.component.connection.ConnectionSpecification;
import org.nabucco.framework.base.facade.component.locator.ComponentLocator;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.ui.web.communication.queryfilter.QueryFilterServiceDelegate;
import org.nabucco.framework.base.ui.web.session.NabuccoWebSession;
import org.nabucco.framework.base.ui.web.session.NabuccoWebSessionFactory;

/**
 * ServiceDelegateFactorySupport
 * 
 * @author Silas Schwarz, PRODYNA AG
 */
public abstract class ServiceDelegateFactorySupport<C extends Component> {

    private C component;

    private ComponentLocator<C> locator;

    private ComponentRelationServiceDelegate componentRelationServiceDelegate;

    private QueryFilterServiceDelegate queryFilterServiceDelegate;

    /**
     * Creates a new {@link ServiceDelegateFactorySupport} instance.
     * 
     * @param locator
     *            the component locator
     */
    protected ServiceDelegateFactorySupport(ComponentLocator<C> locator) {
        this.locator = locator;
    }

    /**
     * Getter for the component.
     * 
     * @return Returns the component.
     */
    public C getComponent() throws ConnectionException {
        if (component == null) {
            this.component = initComponent();
        }
        return component;
    }

    /**
     * Establish the component connection.
     * 
     * @return the located component
     */
    private C initComponent() throws ConnectionException {
        ConnectionSpecification specification = ConnectionSpecification.getCurrentSpecification();

        if (specification == null) {
            NabuccoWebSession session = NabuccoWebSessionFactory.getCurrentSession();

            if (session != null) {
                specification = session.getConnection();
            }
        }

        Connection connection = ConnectionFactory.getInstance().createConnection(specification);
        return locator.getComponent(connection);
    }

    /**
     * Getter for the ComponentRelationServiceDelegate.
     * 
     * @return the ComponentRelationServiceDelegate.
     * 
     * @throws ClientException
     *             when the component relation service delegate is not locatable
     */
    public ComponentRelationServiceDelegate getComponentRelation() throws ClientException {
        try {
            if ((this.componentRelationServiceDelegate == null)) {
                this.componentRelationServiceDelegate = new ComponentRelationServiceDelegate(this.getComponent()
                        .getComponentRelationService());
            }
            return this.componentRelationServiceDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot connect to component: BaseComponent", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: ComponentRelationService", e);
        }
    }

    /**
     * Getter for the QueryFilterServiceDelegate.
     * 
     * @return the QueryFilterServiceDelegate.
     * 
     * @throws ClientException
     *             when the query filter service delegate is not locatable
     */
    public QueryFilterServiceDelegate getQueryFilterService() throws ClientException {
        try {
            if ((this.queryFilterServiceDelegate == null)) {
                this.queryFilterServiceDelegate = new QueryFilterServiceDelegate(this.getComponent()
                        .getQueryFilterService());
            }
            return this.queryFilterServiceDelegate;
        } catch (ConnectionException e) {
            throw new ClientException("Cannot connect to component: BaseComponent", e);
        } catch (ServiceException e) {
            throw new ClientException("Cannot locate service: QueryFilterService", e);
        }
    }

}
