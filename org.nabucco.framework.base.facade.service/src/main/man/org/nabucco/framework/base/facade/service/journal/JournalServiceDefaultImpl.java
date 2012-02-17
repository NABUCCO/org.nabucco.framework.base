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
package org.nabucco.framework.base.facade.service.journal;

import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.message.EmptyServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.setup.journal.JournalMsg;

/**
 * @author Frank Ratschinski, PRODYNA AG
 * 
 */
public class JournalServiceDefaultImpl implements JournalService {

    private static final long serialVersionUID = 1L;

    private static final String SERVICE_NAME = "org.nabucco.framework.base.facade.service.journal.JournalService";

    @Override
    public ServiceResponse<EmptyServiceMessage> journal(ServiceRequest<JournalMsg> rq) throws MaintainException {
        EmptyServiceMessage resultMsg = new EmptyServiceMessage();

        // TODO No Subject available, using null as temporary solution.
        ServiceResponse<EmptyServiceMessage> result = new ServiceResponse<EmptyServiceMessage>(null);
        result.setResponseMessage(resultMsg);
        return result;
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
