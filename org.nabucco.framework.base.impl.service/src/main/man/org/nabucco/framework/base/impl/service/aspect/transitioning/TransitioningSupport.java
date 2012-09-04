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
package org.nabucco.framework.base.impl.service.aspect.transitioning;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeAccessor;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelationContainer;
import org.nabucco.framework.base.facade.datatype.extension.schema.property.PropertyExtension;
import org.nabucco.framework.base.facade.datatype.workflow.context.Context;
import org.nabucco.framework.base.facade.datatype.workflow.instance.Instance;
import org.nabucco.framework.base.facade.datatype.workflow.transition.TransitionContext;
import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.base.facade.exception.service.WorkflowException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.message.workflow.InitWorkflowRq;
import org.nabucco.framework.base.facade.message.workflow.ProcessWorkflowRq;
import org.nabucco.framework.base.facade.message.workflow.WorkflowResultRs;
import org.nabucco.framework.base.facade.service.workflow.WorkflowService;
import org.nabucco.framework.base.facade.service.workflow.WorkflowServiceLocator;
import org.nabucco.framework.base.impl.service.aspect.AspectSupport;

/**
 * TransitioningSupport
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class TransitioningSupport extends AspectSupport {

    /**
     * Additional aspect properties are configured here.
     * 
     * @param properties
     *            the property extension
     */
    public void configure(PropertyExtension properties) {
        // Nothing configured here!
    }

    /**
     * Returns the list of a managing workflow instance of the given datatype.
     * 
     * @param datatype
     *            the datatype holding workflow relations
     * 
     * @return the workflow instances managed by a workflow
     */
    protected <T extends Datatype> List<Instance> getWorkflowInstances(List<T> datatypeList) {
        List<Instance> instances = new ArrayList<Instance>();
        if (datatypeList == null || datatypeList.isEmpty()) {
            return instances;
        }

        for (Datatype datatype : datatypeList) {
            instances.addAll(this.getWorkflowInstances(datatype));
        }
        return instances;
    }

    /**
     * Returns the list of a managing workflow instance of the given datatype.
     * 
     * @param datatype
     *            the datatype holding workflow relations
     * 
     * @return the workflow instances managed by a workflow
     */
    protected List<Instance> getWorkflowInstances(Datatype datatype) {
        ComponentRelationContainer container = DatatypeAccessor.getInstance().getComponentRelation(datatype);

        List<Instance> instances = new ArrayList<Instance>();
        for (ComponentRelation<?> relation : container.getAllComponentRelations()) {
            if (relation.getTarget() instanceof Instance) {
                instances.add((Instance) relation.getTarget());
            }
        }

        return instances;
    }

    /**
     * Start a new workflow instance for the given workflow name.
     * 
     * @param name
     *            the name of the workflow to start
     * @param type
     *            type of the workflow (must be a valid dynamic code of
     *            <code>nabucco.framework.workflow.functionaltype</code>)
     * 
     * @return the current transition context
     * 
     * @throws TransitioningException
     *             when the workflow cannot be started
     */
    protected TransitionContext startWorkflow(String name, String type) throws TransitioningException {
        return this.startWorkflow(name, type, null);
    }

    /**
     * Start a new workflow instance for the given workflow name.
     * 
     * @param name
     *            the name of the workflow to start
     * @param type
     *            type of the workflow (must be a valid dynamic code of
     *            <code>nabucco.framework.workflow.functionaltype</code>)
     * @param summary
     *            a summary of the new instance
     * 
     * @return the current transition context
     * 
     * @throws TransitioningException
     *             when the workflow cannot be started
     */
    protected TransitionContext startWorkflow(String name, String type, String summary) throws TransitioningException {
        return this.startWorkflow(name, type, summary, null);
    }

    /**
     * Start a new workflow instance for the given workflow name.
     * 
     * @param name
     *            the name of the workflow to start
     * @param type
     *            type of the workflow (must be a valid dynamic code of
     *            <code>nabucco.workflow.functionaltype</code>)
     * @param summary
     *            a summary of the new instance
     * @param context
     *            the workflow context
     * 
     * @return the transition context
     * 
     * @throws TransitioningException
     *             when the workflow cannot be started
     */
    protected TransitionContext startWorkflow(String name, String type, String summary, Context context)
            throws TransitioningException {

        InitWorkflowRq requestMessage = new InitWorkflowRq();
        requestMessage.setWorkflowName(new Name(name));
        requestMessage.setSummary(new Description(summary));

        try {
            requestMessage.setFunctionalType(super.findCode(Instance.getFunctionalTypeCodePath(), type));
        } catch (ResolveException re) {
            throw new TransitioningException("Error resolving Workflow Code.", re);
        }

        requestMessage.setContext(context);

        return this.startWorkflow(requestMessage);
    }

    /**
     * Start a new workflow instance for the given workflow name.
     * 
     * @param requestMessage
     *            the workflow init message
     * 
     * @return the transition context
     * 
     * @throws TransitioningException
     *             when the workflow cannot be started
     */
    protected TransitionContext startWorkflow(InitWorkflowRq requestMessage) throws TransitioningException {

        if (requestMessage == null) {
            throw new IllegalArgumentException("Cannot start workflow for request [null].");
        }

        Name name = requestMessage.getWorkflowName();
        Description summary = requestMessage.getSummary();

        if (name == null || name.getValue() == null || name.getValue().isEmpty()) {
            throw new IllegalArgumentException("Cannot start workflow with name [null].");
        }
        if (summary == null || summary.getValue() == null || summary.getValue().isEmpty()) {
            requestMessage.setSummary(new Description("Workflow '" + name + "'"));
        }

        try {
            WorkflowServiceLocator locator = new WorkflowServiceLocator();
            WorkflowService service = locator.getService(null);

            ServiceRequest<InitWorkflowRq> rq = new ServiceRequest<InitWorkflowRq>(super.getContext());
            rq.setRequestMessage(requestMessage);

            ServiceResponse<WorkflowResultRs> rs = service.initWorkflow(rq);

            if (rs == null || rs.getResponseMessage() == null) {
                throw new TransitioningException("Starting workflow did not finish successful.");
            }

            WorkflowResultRs responseMessage = rs.getResponseMessage();

            TransitionContext transitionContext = new TransitionContext();
            transitionContext.setInstance(responseMessage.getInstance());
            transitionContext.setInstanceId(responseMessage.getInstanceId());
            transitionContext.setName(responseMessage.getWorkflowName());
            transitionContext.setWorkflowContext(responseMessage.getContext());
            transitionContext.getNextTransitions().addAll(responseMessage.getNextTransitions());

            return transitionContext;

        } catch (WorkflowException we) {
            throw new TransitioningException("Error starting workflow with name " + name + ".", we);
        } catch (Exception e) {
            throw new TransitioningException("Unexpected error starting workflow with name " + name + ".", e);
        }
    }

    /**
     * Process a transition of an existing workflow instance.
     * 
     * @param context
     *            the current transition context
     * 
     * @return the new current transition context
     * 
     * @throws TransitioningException
     *             when the workflow cannot be processed
     */
    protected TransitionContext processTransition(TransitionContext context) throws TransitioningException {

        if (context == null) {
            throw new IllegalArgumentException("Cannot process workflow for context [null].");
        }

        try {
            WorkflowServiceLocator locator = new WorkflowServiceLocator();
            WorkflowService service = locator.getService(null);

            ServiceRequest<ProcessWorkflowRq> rq = new ServiceRequest<ProcessWorkflowRq>(super.getContext());

            ProcessWorkflowRq requestMessage = new ProcessWorkflowRq();
            requestMessage.setInstanceId(context.getInstanceId());
            requestMessage.setSignal(context.getSignal());
            requestMessage.setComment(context.getComment());
            requestMessage.getSubInstances().addAll(context.getSubInstances());
            requestMessage.setContext(context.getWorkflowContext());
            rq.setRequestMessage(requestMessage);

            ServiceResponse<WorkflowResultRs> rs = service.processWorkflow(rq);

            if (rs == null || rs.getResponseMessage() == null) {
                throw new TransitioningException("Starting workflow did not finish successful.");
            }

            WorkflowResultRs responseMessage = rs.getResponseMessage();

            TransitionContext newContext = new TransitionContext();
            newContext.setInstanceId(responseMessage.getInstanceId());
            newContext.setName(responseMessage.getWorkflowName());
            newContext.setWorkflowContext(responseMessage.getContext());
            newContext.setSignal(context.getSignal());
            newContext.setStateName(responseMessage.getStateName());
            newContext.getNextTransitions().addAll(responseMessage.getNextTransitions());

            return newContext;

        } catch (WorkflowException we) {
            throw new TransitioningException("Error processing workflow with name "
                    + context.getName() + " and id " + context.getInstanceId() + ".", we);
        } catch (Exception e) {
            throw new TransitioningException("Unexpected error processing workflow with name "
                    + context.getName() + " and id " + context.getInstanceId() + ".", e);
        }
    }

}
