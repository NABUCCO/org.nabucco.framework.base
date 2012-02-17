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
package org.nabucco.framework.base.impl.service.aspect.validating;

import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.impl.service.aspect.Aspect;

/**
 * Validation of ServiceMessages and their datatypes.
 * 
 * @see <a
 *      href="http://www.nabuccosource.org/confluence/display/NBCF/Service+Aspects+Concept">Service
 *      Aspects Concept</a>
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public interface ValidatingAspect extends Aspect {

    /**
     * Validates the service message before service invocation.
     * 
     * @param requestMessage
     *            the request message to validate
     * 
     * @throws ValidatingException
     *             when the message is not valid
     */
    void validateBefore(ServiceMessage requestMessage) throws ValidatingException;

    /**
     * Validates the service messages after service invocation.
     * 
     * @param requestMessage
     *            the request message to validate
     * @param responseMessage
     *            the response message to validate
     * 
     * @throws ValidatingException
     *             when the message is not valid
     */
    void validateAfter(ServiceMessage requestMessage, ServiceMessage responseMessage) throws ValidatingException;

}
