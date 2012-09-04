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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoMap;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContextType;
import org.nabucco.framework.base.facade.datatype.context.WorkflowTransitionContext;
import org.nabucco.framework.base.facade.datatype.context.WorkflowTransitionContextResponse;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.datatype.workflow.transition.TransitionContext;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.WebModel;
import org.nabucco.framework.base.ui.web.session.NabuccoWebSession;
import org.nabucco.framework.base.ui.web.session.NabuccoWebSessionFactory;

/**
 * Web Model for Workflow Widget.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class WorkflowModel extends WebModel {

    private static final String JSON_ENTRIES = "entries";

    private Map<String, WorkflowEntry> entryMap = new HashMap<String, WorkflowEntry>();

    @Override
    public void init() {
    }

    /**
     * Getter for the workflow entry with the given id.
     * 
     * @param id
     *            the workflow instance id
     * 
     * @return the workflow entry with for the workflow instance with the given id, or null if no
     *         entry is registered
     */
    public WorkflowEntry getEntry(String id) {
        return this.entryMap.get(id);
    }

    /**
     * Getter for the list of all workflow entries.
     * 
     * @return all workflow entries
     */
    public List<WorkflowEntry> getEntryList() {
        return new ArrayList<WorkflowEntry>(this.entryMap.values());
    }

    /**
     * Setter for the datatype holding the workflow instances. The existing workflow entries are
     * removed.
     * 
     * @param datatype
     *            the datatype to set into the workflow model
     */
    public void setDatatype(Datatype datatype) {

        // Skip when no open session exists!
        NabuccoWebSession session = NabuccoWebSessionFactory.getCurrentSession();
        if (session == null) {
            return;
        }

        ServiceSubContext context = session.getServiceContext().getResponseContext(
                ServiceSubContextType.WORKFLOW_TRANSITION);

        // Skip when no transition context exists!
        if (!(context instanceof WorkflowTransitionContext)) {
            return;
        }

        WorkflowTransitionContext workflowContext = (WorkflowTransitionContext) context;
        WorkflowTransitionContextResponse responseTransitionContext = workflowContext.getResponseTransitionContext();
        if (responseTransitionContext == null) {
            return;
        }
        NabuccoMap<TransitionContext> contextMap = responseTransitionContext.getTransitionContextMap();
        if (contextMap == null) {
            return;
        }

        List<TransitionContext> actuallTransitionContexts = contextMap.getAssignedElements();

        TransitionContext datatypeTransitionContext = null;
        for (TransitionContext transitionContext : actuallTransitionContexts) {
            try {
                WorkflowInstanceReferenceVisitor visitor = new WorkflowInstanceReferenceVisitor(
                        transitionContext.getInstanceId());

                datatype.accept(visitor);
                boolean hasReference = visitor.hasReference();
                if (hasReference) {
                    datatypeTransitionContext = transitionContext;
                    break;
                }

            } catch (VisitorException e) {
                throw new IllegalStateException(
                        "Cannot analyse datatype if it references the workflow from the transition context", e);
            }
        }

        // Skip when no transition context exists!
        if (datatypeTransitionContext == null) {
            return;
        }

        this.entryMap.clear();
        this.addEntry(datatypeTransitionContext);

        session.getServiceContext().putRequestContext(context);
    }

    /**
     * Create a new workflow entry for the given transition contxt and add it to the model.
     * 
     * @param transitionContext
     *            the transition context to create the entry for
     */
    private void addEntry(TransitionContext transitionContext) {
        this.addEntry(new WorkflowEntry(transitionContext));
    }

    /**
     * Add a new workflow entry to the list.
     * 
     * @param entry
     *            the workflow entry to add
     */
    private void addEntry(WorkflowEntry entry) {
        if (entry == null) {
            throw new IllegalArgumentException("Cannot add WorkflowEntry [null] to the model.");
        }

        this.entryMap.put(entry.getId(), entry);
    }

    @Override
    public JsonElement toJson() {
        JsonMap json = new JsonMap();

        JsonList jsonEntries = new JsonList();
        for (WorkflowEntry entry : this.entryMap.values()) {
            if (entry.hasSignals()) {
                jsonEntries.add(entry.toJson());
            }
        }
        json.add(JSON_ENTRIES, jsonEntries);

        return json;
    }
}
