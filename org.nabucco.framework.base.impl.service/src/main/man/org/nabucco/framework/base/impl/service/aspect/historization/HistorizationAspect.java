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
package org.nabucco.framework.base.impl.service.aspect.historization;

import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.impl.service.aspect.Aspect;

/**
 * ConstrainingAspect
 * 
 * @see <a
 *      href="http://www.nabuccosource.org/confluence/display/NBCF/Service+Aspects+Concept">Service
 *      Aspects Concept</a>
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public interface HistorizationAspect extends Aspect {

    /**
     * Process message historization logic like creating of a new history item or resolving of it
     * 
     * @param requestMessage
     *            The original request message without constraint modifications.
     * 
     * @return The modified message with constraints set.
     * 
     * @throws HistorizationException
     *             Thrown if historization process.
     */
    void contrainBefore(ServiceMessage requestMessage) throws HistorizationException;

    /**
     * Process message historization logic like creating of a new history item or resolving of it
     * 
     * @param requestMessage
     *            The original request message without constraint modifications.
     * @param responseMessage
     *            The original response message without constraint modifications.
     * 
     * 
     * @return The modified response message with constraints set.
     * 
     * @throws HistorizationException
     *             Thrown if historization process.
     */
    void contrainAfter(ServiceMessage requestMessage, ServiceMessage responseMessage) throws HistorizationException;
}
