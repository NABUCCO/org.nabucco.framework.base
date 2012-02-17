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
package org.nabucco.framework.base.facade.service.workflow;

import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.context.ServiceContextFactory;
import org.nabucco.framework.base.facade.message.workflow.InitWorkflowRq;
import org.nabucco.framework.base.facade.message.workflow.ProcessWorkflowRq;
import org.nabucco.framework.base.facade.message.workflow.WorkflowResultRs;
import org.nabucco.framework.base.facade.service.workflow.WorkflowService;

/**
 * WorkflowServiceDefaultImpl<p/>Service for transitioning workflows of the workflow engine.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-05-03
 */
public class WorkflowServiceDefaultImpl implements WorkflowService {

    private static final long serialVersionUID = 1L;

    /** Constructs a new WorkflowServiceDefaultImpl instance. */
    public WorkflowServiceDefaultImpl() {
        super();
    }

    @Override
    public String[] getAspects(String operation) {
        return new String[0];
    }

    /**
     * InitWorkflow.
     *
     * @param rq the ServiceRequest<InitWorkflowRq>.
     * @return the ServiceResponse<WorkflowResultRs>.
     */
    public ServiceResponse<WorkflowResultRs> initWorkflow(ServiceRequest<InitWorkflowRq> rq) {
        ServiceResponse<WorkflowResultRs> rs = new ServiceResponse<WorkflowResultRs>(ServiceContextFactory
                .getInstance().newServiceMessageContext());
        rs.setResponseMessage(new WorkflowResultRs());
        return rs;
    }

    /**
     * ProcessWorkflow.
     *
     * @param rq the ServiceRequest<ProcessWorkflowRq>.
     * @return the ServiceResponse<WorkflowResultRs>.
     */
    public ServiceResponse<WorkflowResultRs> processWorkflow(ServiceRequest<ProcessWorkflowRq> rq) {
        ServiceResponse<WorkflowResultRs> rs = new ServiceResponse<WorkflowResultRs>(ServiceContextFactory
                .getInstance().newServiceMessageContext());
        rs.setResponseMessage(new WorkflowResultRs());
        return rs;
    }

    @Override
    public final String getName() {
        return null;
    }
}
