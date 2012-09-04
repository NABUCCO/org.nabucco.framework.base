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
package org.nabucco.framework.base.ui.web.component.work;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemWorkflowExtension;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.ui.web.component.WebComponent;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitor;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitorContext;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.work.workflow.WorkflowModel;

/**
 * Workfow Element of the Work Item.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class WorkItemWorkflow extends WebComponent {

    private static final long serialVersionUID = 1L;

    public static final String ELEMENT_ID = "workflow";

    private static final String JSON_WORKFLOW_ICON = "workflowIcon";

    private static final String JSON_SIGNAL_ICON = "signalIcon";

    private static final String JSON_NO_WORKFLOW = "noWorkflow";

    /** The Workflow Extension */
    private final WorkItemWorkflowExtension extension;

    /** The Workflow Model */
    private WorkflowModel workflowModel;

    /**
     * Creates a new {@link WorkItemWorkflow} instance.
     * 
     * @param extension
     *            the workflow extension
     */
    public WorkItemWorkflow(WorkItemWorkflowExtension extension) {
        super(WebElementType.WORKFLOW, extension);

        if (extension == null) {
            throw new IllegalArgumentException("Cannot create workflow for extension 'null'.");
        }

        this.extension = extension;
    }

    @Override
    public void init() throws ExtensionException {
        this.workflowModel = new WorkflowModel();
    }

    /**
     * Getter for the workflow label.
     * 
     * @return the workflow label
     */
    public String getLabel() {
        return PropertyLoader.loadProperty(this.extension.getLabel());
    }

    /**
     * Getter for the workflow tooltip.
     * 
     * @return the workflow tooltip
     */
    public String getTooltip() {
        return PropertyLoader.loadProperty(this.extension.getTooltip());
    }

    /**
     * Getter for the label when no workflow is available.
     * 
     * @return the workflow label
     */
    public String getNoWorkflowLabel() {
        return PropertyLoader.loadProperty(this.extension.getNoWorkflowLabel());
    }

    /**
     * Getter for the signal icon.
     * 
     * @return the signal icon
     */
    public String getSignalIcon() {
        return PropertyLoader.loadProperty(this.extension.getSignalIcon());
    }

    /**
     * Getter for the workflow icon.
     * 
     * @return the workflow icon
     */
    public String getWorkflowIcon() {
        return PropertyLoader.loadProperty(this.extension.getWorkflowIcon());
    }

    /**
     * Getter for the workflow icon.
     * 
     * @return the workflow icon
     */
    public String getActionId() {
        return PropertyLoader.loadProperty(this.extension.getActionId());
    }

    /**
     * Getter for the workflowModel.
     * 
     * @return Returns the workflowModel.
     */
    public WorkflowModel getModel() {
        return this.workflowModel;
    }

    /**
     * Accepts the web element visitor. Overload this function to let element be visited
     * 
     * @param visitor
     */
    @Override
    public <T extends WebElementVisitorContext> void accept(WebElementVisitor<T> visitor, T context)
            throws VisitorException {
        if (visitor != null) {
            visitor.visit(this, context);
        }
    }

    @Override
    public JsonElement toJson() {
        JsonMap json = new JsonMap();

        json.add(JSON_LABEL, this.getLabel());
        json.add(JSON_TOOLTIP, this.getTooltip());
        json.add(JSON_NO_WORKFLOW, this.getNoWorkflowLabel());
        json.add(JSON_SIGNAL_ICON, this.getSignalIcon());
        json.add(JSON_WORKFLOW_ICON, this.getWorkflowIcon());
        json.add(JSON_ACTION, this.getActionId());

        json.add(JSON_MODEL, this.getModel().toJson());

        return json;
    }
}
