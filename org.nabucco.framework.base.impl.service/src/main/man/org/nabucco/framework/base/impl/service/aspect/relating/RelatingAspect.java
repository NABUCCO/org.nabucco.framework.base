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
package org.nabucco.framework.base.impl.service.aspect.relating;

import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.base.impl.service.aspect.Aspect;

/**
 * Handels the relations between business objects.
 * 
 * @see <a
 *      href="http://www.nabuccosource.org/confluence/display/NBCF/Service+Aspects+Concept">Service
 *      Aspects Concept</a>
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface RelatingAspect extends Aspect {

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
     * Datatype relating callback before service operation.
     * 
     * @param requestMessage
     *            the service request message
     * 
     * @throws RelatingException
     *             when the relating fails
     */
    void relateBefore(ServiceMessage requestMessage) throws RelatingException;

    /**
     * Datatype relating callback after service operation.
     * 
     * @param requestMessage
     *            the service request message
     * 
     * @throws RelatingException
     *             when the relating fails
     */
    void relateAfter(ServiceMessage requestMessage, ServiceMessage responseMessage) throws RelatingException;

}
