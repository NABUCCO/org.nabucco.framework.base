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
package org.nabucco.framework.base.ui.web.model.work.workflow;

import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.workflow.instance.Instance;
import org.nabucco.framework.base.facade.datatype.workflow.transition.TransitionContext;
import org.nabucco.framework.base.facade.datatype.workflow.transition.TransitionParameter;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.WebModel;

/**
 * Represents a single workflow state.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class WorkflowEntry extends WebModel {

    private static final String JSON_STATE = "state";

    private static final String JSON_SIGNALS = "signals";

    private static final String JSON_COMMENT = "comment";

    /** Workflow Transition Context */
    private TransitionContext transitionContext;

    /**
     * Creates a new {@link WorkflowEntry} instance.
     * 
     * @param context
     *            the workflow transition context
     */
    public WorkflowEntry(TransitionContext context) {
        if (context == null) {
            throw new IllegalArgumentException("Cannot creat Workflow Entry for TransitionContext [null].");
        }
        if (context.getInstanceId() == null || context.getInstanceId().getValue() == null) {
            throw new IllegalArgumentException("Cannot creat Workflow Entry for Workflow Instance ID [null].");
        }

        this.transitionContext = context;
    }

    @Override
    public void init() {
    }

    /**
     * Getter for the workflow instance id.
     * 
     * @return the workflow instance id
     */
    public String getId() {
        return String.valueOf(this.transitionContext.getInstanceId().getValue());
    }

    /**
     * Getter for the transitionContext.
     * 
     * @return Returns the transitionContext.
     */
    public TransitionContext getTransitionContext() {
        return this.transitionContext;
    }

    /**
     * Check whether the workflow entry has signals or not.
     * 
     * @return <b>true</b> if the workflow has signals to display, <b>false</b> if not
     */
    public boolean hasSignals() {
        return !this.transitionContext.getNextTransitions().isEmpty();
    }

    @Override
    public JsonElement toJson() {
        JsonMap json = new JsonMap();

        json.add(JSON_ID, this.transitionContext.getInstanceId());
        json.add(JSON_STATE, this.transitionContext.getStateName());

        if (this.transitionContext.getName() != null && this.transitionContext.getName().getValue() != null) {
            json.add(JSON_LABEL, this.transitionContext.getName());
        }

        Instance instance = this.transitionContext.getInstance();
        if (instance != null) {
            if (instance.getSummary() != null && instance.getSummary().getValue() != null) {
                json.add(JSON_TOOLTIP, instance.getSummary());
            }
        }

        JsonList jsonSignals = new JsonList();
        for (TransitionParameter parameter : this.transitionContext.getNextTransitions()) {
            if (parameter == null || parameter.getSignal() == null) {
                continue;
            }

            Long signalId = parameter.getSignal().getId();
            Name signalName = parameter.getSignal().getName();
            Description signalDescription = parameter.getSignal().getDescription();

            JsonMap jsonSignal = new JsonMap();
            jsonSignal.add(JSON_ID, signalId);
            jsonSignal.add(JSON_LABEL, signalName);
            jsonSignal.add(JSON_TOOLTIP, signalDescription);
            jsonSignal.add(JSON_COMMENT, parameter.getCommentCardinality());

            jsonSignals.add(jsonSignal);
        }

        json.add(JSON_SIGNALS, jsonSignals);

        return json;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.transitionContext == null) ? 0 : this.transitionContext.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof WorkflowEntry)) {
            return false;
        }
        WorkflowEntry other = (WorkflowEntry) obj;
        if (this.transitionContext == null) {
            if (other.transitionContext != null) {
                return false;
            }
        } else if (!this.transitionContext.equals(other.transitionContext)) {
            return false;
        }
        return true;
    }
}
