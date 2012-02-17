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
package org.nabucco.framework.base.impl.service.aspect.journaling;

import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.impl.service.aspect.Aspect;

/**
 * JournalingAspect
 * 
 * @see <a
 *      href="http://www.nabuccosource.org/confluence/display/NBCF/Service+Aspects+Concept">Service
 *      Aspects Concept</a>
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public interface JournalingAspect extends Aspect {

    /**
     * Reports the reqest message to journal.
     * 
     * @param requestMessage
     *            the request message to report
     * 
     * @throws JournalingException
     *             when the journaling fails
     */
    void journalBefore(ServiceMessage requestMessage) throws JournalingException;

    /**
     * Reports the response message to journal.
     * 
     * @param requestMessage
     *            the request message to report
     * @param responseMessage
     *            the response message to report
     * 
     * @throws JournalingException
     *             when the journaling fails
     */
    void journalAfter(ServiceMessage requestMessage, ServiceMessage responseMessage) throws JournalingException;
}
