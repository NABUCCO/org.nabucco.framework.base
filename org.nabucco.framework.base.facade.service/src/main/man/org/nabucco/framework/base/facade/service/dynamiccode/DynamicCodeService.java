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
package org.nabucco.framework.base.facade.service.dynamiccode;

import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.dynamiccode.DynamicCodeIdRq;
import org.nabucco.framework.base.facade.message.dynamiccode.DynamicCodePathRq;
import org.nabucco.framework.base.facade.message.dynamiccode.DynamicCodeRs;
import org.nabucco.framework.base.facade.service.Service;

/**
 * Interface for resolving dynamic codes from dynamiccode component.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public interface DynamicCodeService extends Service {

    /** The DynamicCodeService JNDI name. */
    final String JNDI_NAME = "nabucco/crosscutting/DynamicCodeService";

    /**
     * Resolves a single dynamic code by the given reference ID.
     * 
     * @param rq
     *            the request message holding the code reference ID
     * 
     * @return the list of dynamic codes
     * 
     * @throws ResolveException
     *             when the resolve did not finish successful
     */
    ServiceResponse<DynamicCodeRs> resolveDynamicCode(ServiceRequest<DynamicCodeIdRq> rq) throws ResolveException;

    /**
     * Searches a list of dynamic codes by the given code path.
     * 
     * @param rq
     *            the request message holding the code path
     * 
     * @return the list of dynamic codes
     * 
     * @throws SearchException
     *             when the search did not finish successful
     */
    ServiceResponse<DynamicCodeRs> searchDynamicCode(ServiceRequest<DynamicCodePathRq> rq) throws SearchException;
}
