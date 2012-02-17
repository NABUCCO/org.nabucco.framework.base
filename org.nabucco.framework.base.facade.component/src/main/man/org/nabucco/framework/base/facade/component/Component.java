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
package org.nabucco.framework.base.facade.component;

import org.nabucco.framework.base.facade.component.locator.Locatable;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.service.componentrelation.ComponentRelationService;
import org.nabucco.framework.base.facade.service.queryfilter.QueryFilterService;

/**
 * Component
 * <p/>
 * Base interaface for all component facades. Holding all services that are offered by the
 * component.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public interface Component extends Locatable {

    /**
     * Returns the ID of the component.
     * 
     * @return the component id
     */
    String getId();

    /**
     * Returns the name of the component.
     * 
     * @return the component name
     */
    String getName();

    /**
     * Getter for the component relation service containing business operations about inter
     * component relations.
     * 
     * @return the service for component relations
     * 
     * @throws ServiceException
     *             when the service cannot be obtained
     */
    ComponentRelationService getComponentRelationService() throws ServiceException;

    /**
     * Getter for the filter service containing search operations about component specific filters.
     * 
     * @return the service for filtering
     * 
     * @throws ServiceException
     *             when the service cannot be obtained
     */
    QueryFilterService getQueryFilterService() throws ServiceException;

}
