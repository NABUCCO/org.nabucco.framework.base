/*
 * Copyright 2012 PRODYNA AG
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
package org.nabucco.framework.base.impl.service.workflow;

import javax.naming.InitialContext;

import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.code.CodePath;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.base.facade.message.dynamiccode.DynamicCodePathRq;
import org.nabucco.framework.base.facade.message.dynamiccode.DynamicCodeRs;
import org.nabucco.framework.base.facade.service.dynamiccode.DynamicCodeService;
import org.nabucco.framework.base.facade.service.dynamiccode.DynamicCodeServiceLocator;


/**
 * WorkflowInstanciableSupport
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public abstract class WorkflowInstantiableSupport{
    
    /**
     * Find the code with the given name and code path.
     * 
     * @param codePath
     *            the codes code path
     * @param name
     *            the codes name
     * 
     * @return the code or null
     * 
     * @throws ResolveException
     *             when an error occurs
     */
    protected Code findCode(String codePath, String name, ServiceMessageContext context) throws ResolveException {
        return findCode(new CodePath(codePath), name, context);
    }
    
    
    /**
     * Find the code with the given name and code path.
     * 
     * @param codePath
     *            the codes code path
     * @param name
     *            the codes name
     * 
     * @return the code or null
     * 
     * @throws ResolveException
     *             when an error occurs
     */
    protected Code findCode(CodePath codePath, String name, ServiceMessageContext context) throws ResolveException {

        try {
            DynamicCodeServiceLocator locator = new DynamicCodeServiceLocator();
            DynamicCodeService searchService = locator.getDynamicCodeService(new InitialContext());

            ServiceRequest<DynamicCodePathRq> rq = new ServiceRequest<DynamicCodePathRq>(context);

            DynamicCodePathRq msg = new DynamicCodePathRq();
            msg.setPath(codePath);
            rq.setRequestMessage(msg);

            ServiceResponse<DynamicCodeRs> rs = searchService.searchDynamicCode(rq);

            if (rs == null || rs.getResponseMessage() == null) {
                throw new IllegalStateException("Dynamic Code Search Request is not valid [null].");
            }

            NabuccoList<Code> codes = rs.getResponseMessage().getCodeList();

            for (Code code : codes) {
                if (code.getName().getValue().equals(name)) {
                    return code;
                }
            }

        } catch (Exception e) {
            throw new ResolveException("Error resolving dynamic code '" + name + "' on path '" + codePath + "'.");
        }

        return null;
    }
}
