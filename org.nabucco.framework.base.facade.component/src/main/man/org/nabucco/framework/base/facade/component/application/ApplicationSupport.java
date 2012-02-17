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
package org.nabucco.framework.base.facade.component.application;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.nabucco.framework.base.facade.component.application.connector.Connector;
import org.nabucco.framework.base.facade.component.application.connector.ConnectorStrategy;
import org.nabucco.framework.base.facade.service.injection.Injectable;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;

/**
 * ApplicationSupport
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class ApplicationSupport implements Application {

    private static final long serialVersionUID = 1L;

    /** Map of cached connectors by strategy. */
    private Map<ConnectorStrategy, List<Connector>> connectorMap;

    @Override
    public List<Connector> getConnectors(ConnectorStrategy strategy) {

        if (this.connectorMap == null) {
            this.initConnectors();
        }

        if (strategy != null) {
            List<Connector> serviceConnectors = this.connectorMap.get(strategy);
            if (serviceConnectors != null) {
                return new ArrayList<Connector>(serviceConnectors);
            }
        }

        return new ArrayList<Connector>();
    }

    /**
     * Initialize the application connectors.
     */
    private void initConnectors() {
        this.connectorMap = new EnumMap<ConnectorStrategy, List<Connector>>(ConnectorStrategy.class);

        InjectionProvider injectionProvider = InjectionProvider.getInstance(Connector.CONNECTOR_ID);
        List<Injectable> injections = injectionProvider.injectAll();

        for (Injectable injectable : injections) {
            if (injectable instanceof Connector) {

                Connector serviceConnector = (Connector) injectable;
                List<Connector> serviceConnectors = this.connectorMap.get(serviceConnector.getStrategy());

                if (serviceConnectors == null) {
                    serviceConnectors = new ArrayList<Connector>();
                    this.connectorMap.put(serviceConnector.getStrategy(), serviceConnectors);
                }

                serviceConnectors.add(serviceConnector);
            }
        }
    }

}
