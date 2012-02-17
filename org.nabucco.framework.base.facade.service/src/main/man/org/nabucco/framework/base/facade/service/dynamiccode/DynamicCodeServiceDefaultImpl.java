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
import org.nabucco.framework.base.facade.message.context.ServiceContextFactory;
import org.nabucco.framework.base.facade.message.dynamiccode.DynamicCodeIdRq;
import org.nabucco.framework.base.facade.message.dynamiccode.DynamicCodePathRq;
import org.nabucco.framework.base.facade.message.dynamiccode.DynamicCodeRs;

/**
 * Default implementation returns an empty list of dynamic codes.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class DynamicCodeServiceDefaultImpl implements DynamicCodeService {

    private static final long serialVersionUID = 1L;

    private static final String SERVICE_NAME = "org.nabucco.framework.base.facade.service.dynamiccode.DynamicCodeService";

    @Override
    public ServiceResponse<DynamicCodeRs> resolveDynamicCode(ServiceRequest<DynamicCodeIdRq> rq)
            throws ResolveException {
        ServiceResponse<DynamicCodeRs> rs = new ServiceResponse<DynamicCodeRs>(ServiceContextFactory.getInstance()
                .newServiceMessageContext());

        DynamicCodeRs msg = new DynamicCodeRs();
        rs.setResponseMessage(msg);

        return rs;
    }

    @Override
    public ServiceResponse<DynamicCodeRs> searchDynamicCode(ServiceRequest<DynamicCodePathRq> rq)
            throws SearchException {

        ServiceResponse<DynamicCodeRs> rs = new ServiceResponse<DynamicCodeRs>(ServiceContextFactory.getInstance()
                .newServiceMessageContext());

        DynamicCodeRs msg = new DynamicCodeRs();
        rs.setResponseMessage(msg);

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
