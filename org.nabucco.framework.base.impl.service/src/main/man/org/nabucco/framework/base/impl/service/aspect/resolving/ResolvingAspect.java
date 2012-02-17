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
package org.nabucco.framework.base.impl.service.aspect.resolving;

import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.base.impl.service.aspect.Aspect;

/**
 * Handels resolving of common references like User or Dynamiccodes.
 * 
 * @see <a
 *      href="http://www.nabuccosource.org/confluence/display/NBCF/Service+Aspects+Concept">Service
 *      Aspects Concept</a>
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public interface ResolvingAspect extends Aspect {

    /**
     * Setter for the service message context.
     * 
     * @param context
     *            the service context
     */
    void setContext(ServiceMessageContext context);

    /**
     * Setter for the service message context.
     * 
     * @return the service context
     */
    ServiceMessageContext getContext();

    /**
     * Datatype resolving callback before service operation.
     * 
     * @param requestMessage
     *            the service request message
     * 
     * @throws ResolvingException
     *             when the resolving fails
     */
    void resolveBefore(ServiceMessage requestMessage) throws ResolvingException;

    /**
     * Datatype resolving callback after service operation.
     * 
     * @param requestMessage
     *            the service request message
     * 
     * @throws ResolvingException
     *             when the resolving fails
     */
    void resolveAfter(ServiceMessage requestMessage, ServiceMessage responseMessage) throws ResolvingException;

}
