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
package org.nabucco.framework.base.facade.component.application;

import java.util.List;

import org.nabucco.framework.base.facade.component.application.connector.Connector;
import org.nabucco.framework.base.facade.component.application.connector.ConnectorStrategy;
import org.nabucco.framework.base.facade.service.injection.Injectable;

/**
 * Application
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface Application extends Injectable {

    final String APPLICATION_ID = "Application";

    /**
     * Getter for the inter component connectors for the given strategy.
     * 
     * @param strategy
     *            the strategy
     * 
     * @return the list of connectors defined for this application
     */
    List<Connector> getConnectors(ConnectorStrategy strategy);

}
