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
package org.nabucco.framework.base.facade.component.adapter;

import org.nabucco.framework.base.facade.component.locator.Locatable;
import org.nabucco.framework.base.facade.message.ping.PingRequest;
import org.nabucco.framework.base.facade.message.ping.PingResponse;

/**
 * Adapter
 * <p/>
 * Base interaface for all adapter facades. Holding all services that are offered by the adapter.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface Adapter extends Locatable {

    /**
     * Returns the ID of the adapter.
     * 
     * @return the adapter id
     */
    String getId();

    /**
     * Returns the name of the adapter.
     * 
     * @return the adapter name
     */
    String getName();

    /**
     * Pings the adapter for availability.
     * 
     * @param request
     *            the ping request
     * 
     * @return the ping response
     */
    PingResponse ping(PingRequest request);

}
