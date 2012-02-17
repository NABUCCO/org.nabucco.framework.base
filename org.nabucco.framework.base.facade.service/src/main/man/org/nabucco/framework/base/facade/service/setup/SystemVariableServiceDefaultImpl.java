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
package org.nabucco.framework.base.facade.service.setup;

import org.nabucco.framework.base.facade.exception.service.SearchException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.context.ServiceContextFactory;
import org.nabucco.framework.base.facade.message.setup.sysvar.SystemVariableMsg;

/**
 * Default implementation returns an empty list of dynamic codes.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class SystemVariableServiceDefaultImpl implements SystemVariableService {

    private static final long serialVersionUID = 1L;

    private static final String SERVICE_NAME = "org.nabucco.framework.base.facade.service.setup.SystemVariableService";

    @Override
    public ServiceResponse<SystemVariableMsg> getSystemVariables(ServiceRequest<EmptyServiceMessage> rq)
            throws SearchException {
        ServiceResponse<SystemVariableMsg> rs = new ServiceResponse<SystemVariableMsg>(ServiceContextFactory
                .getInstance().newServiceMessageContext());

        rs.setResponseMessage(new SystemVariableMsg());
        return rs;
    }

    @Override
    public String getName() {
        return SERVICE_NAME;
    }

    @Override
    public String[] getAspects(String operation) {
        return new String[0];
    }
}
