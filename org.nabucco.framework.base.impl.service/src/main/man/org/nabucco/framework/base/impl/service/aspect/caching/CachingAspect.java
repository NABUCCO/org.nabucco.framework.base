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
package org.nabucco.framework.base.impl.service.aspect.caching;

import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.base.impl.service.aspect.Aspect;

/**
 * Handles caching of responses of ServiceOperations.
 * 
 * @see <a
 *      href="http://www.nabuccosource.org/confluence/display/NBCF/Service+Aspects+Concept">Service
 *      Aspects Concept</a>
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public interface CachingAspect extends Aspect {

    /**
     * Setter for the service message context.
     * 
     * @param context
     *            the service context
     */
    void setContext(ServiceMessageContext context);

    /**
     * Calls the cache for a given request message and returns the appropriate response. When the
     * appropriate response is found in the cache, the service operation is not executed (bypass).
     * 
     * @param requestMessage
     *            the request message
     * 
     * @throws CachingException
     *             when caching fails
     */
    void before(ServiceMessage requestMessage) throws CachingException;

    /**
     * Put a response message into the cache, so the next executions of the service operation must
     * not be accomplished.
     * 
     * @param requestMessage
     *            the request message
     * @param responseMessage
     *            the response message
     * 
     * @throws CachingException
     *             when caching fails
     */
    void after(ServiceMessage requestMessage, ServiceMessage responseMessage) throws CachingException;

    /**
     * Check whether a service operation is cached and must not be executed.
     * 
     * @return <b>true</b> if the service operation response is cached, <b>false</b> if not
     */
    boolean isCached();

    /**
     * Getter for the cached service response.
     * 
     * @return the cached service response
     */
    ServiceMessage getResponseMessage();
}
