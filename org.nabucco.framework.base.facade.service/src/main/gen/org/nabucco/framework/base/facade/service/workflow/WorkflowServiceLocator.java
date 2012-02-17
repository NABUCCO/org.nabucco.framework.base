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

import javax.naming.Context;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.service.ServiceLocator;

/**
 * WorkflowServiceLocator<p/>Service for transitioning workflows of the workflow engine.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-05-03
 */
public class WorkflowServiceLocator extends ServiceLocator {

    private static final String JNDI_NAME = "nabucco/org.nabucco.framework.base/org.nabucco.framework.base.facade.service.workflow.WorkflowService/local";

    private static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(WorkflowService.class);

    /** Constructs a new WorkflowServiceLocator instance. */
    public WorkflowServiceLocator() {
        super();
    }

    /**
     * Getter for the Service.
     *
     * @param ctx the Context.
     * @return the WorkflowService.
     */
    public WorkflowService getService(Context ctx) {
        try {
            return ((WorkflowService) super.locateService(JNDI_NAME, ctx));
        } catch (Exception e) {
            logger.warning(e,
                    (("Cannot locate Service from JNDI \'" + JNDI_NAME) + "\', using a default implementation."));
            return new WorkflowServiceDefaultImpl();
        }
    }
}
