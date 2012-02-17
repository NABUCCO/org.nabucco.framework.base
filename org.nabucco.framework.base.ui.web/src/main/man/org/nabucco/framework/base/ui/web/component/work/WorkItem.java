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

import java.util.ArrayList;
import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemActionExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemActionsExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemBrowserExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemWorkflowExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.dashboard.DashboardExtension;
import org.nabucco.framework.base.ui.web.component.WebComposite;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.browser.Browser;
import org.nabucco.framework.base.ui.web.component.work.dashboard.DashboardItem;
import org.nabucco.framework.base.ui.web.component.work.editor.EditorItem;
import org.nabucco.framework.base.ui.web.component.work.list.ListItem;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.browser.BrowserEntry;
import org.nabucco.framework.base.ui.web.model.control.util.PropertyStringParser;
import org.nabucco.framework.base.ui.web.model.dashboard.DashboardItemModel;
import org.nabucco.framework.base.ui.web.model.list.ListModel;
import org.nabucco.framework.base.ui.web.model.table.TableModel;
import org.nabucco.framework.base.ui.web.model.work.EditorModel;
import org.nabucco.framework.base.ui.web.model.work.WorkItemModel;
import org.nabucco.framework.base.ui.web.model.work.breadcrump.BreadCrump;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;

/**
 * A work item of the work area. Current sub-classes are {@link DashboardItem}, {@link EditorItem}
 * and {@link ListItem}.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public abstract class WorkItem extends WebComposite {

    /** The default serial version UID */
    private static final long serialVersionUID = 1L;

    private static final String JSON_BREADCRUMPS = "breadCrumps";

    private static final String JSON_WORKFLOW = "workflow";

    /** The Work Item Extension */
    private final WorkItemExtension extension;

    /** The Type of the Work Item */
    private final WorkItemType type;

    /** The Brad Crump */
    private BreadCrump breadCrump;

    /** The Work item Model */
    private WorkItemModel model;

    /** The Work item instance ID */
    private String instanceId;

    /** The Source Work Item */
    private WorkItem source;

    /** The id of the following element. Important by replacing of editors */
    private String followerId;

    /**
     * Creates a new {@link WorkItem} instance.
     * 
     * @param instanceId
     *            the work item instance id
     * @param extension
     *            the work item extension
     * @param type
     *            the work item type
     */
    public WorkItem(String instanceId, WorkItemExtension extension, WorkItemType type) {
        super(mapToElementType(type), extension);
        if (instanceId == null) {
            throw new IllegalArgumentException("Cannot create work item for instance 'null'.");
        }
        if (extension == null) {
            throw new IllegalArgumentException("Cannot create work item for extension 'null'.");
        }

        this.instanceId = instanceId;
        this.extension = extension;
        this.type = type;
    }

    /**
     * Map the item type to the appropriate element type.
     * 
     * @param type
     *            the item type
     * 
     * @return the element type
     */
    public static WebElementType mapToElementType(WorkItemType type) {
        if (type == null) {
            throw new IllegalArgumentException("Cannot create work item for type 'null'.");
        }

        switch (type) {
        case DASHBOARD:
            return WebElementType.DASHBOARD;
        case EDITOR:
            return WebElementType.EDITOR;
        case LIST:
            return WebElementType.LIST;
        }

        return null;
    }

    @Override
    public void init() throws ExtensionException {

        String label = PropertyLoader.loadProperty(this.extension.getLabel());
        String tooltip = PropertyLoader.loadProperty(this.extension.getTooltip());
        String icon = PropertyLoader.loadProperty(this.extension.getIcon());

        this.breadCrump = new BreadCrump();
        this.breadCrump.addLast(label, tooltip, this.getInstanceId());

        WorkItemWorkflowExtension workflowExtension = this.extension.getWorkflowExtension();

        if (workflowExtension != null) {
            WorkItemWorkflow workflow = new WorkItemWorkflow(workflowExtension);
            super.addElement(WorkItemWorkflow.ELEMENT_ID, workflow);
            workflow.init();
        }

        switch (this.type) {

        case EDITOR: {
            EditorModel editorModel = new EditorModel(label, tooltip, icon);

            PropertyStringParser parser = new PropertyStringParser(label, tooltip, icon);
            for (String property : parser.parseProperties()) {
                editorModel.addPropertyChangeListener(property, this.breadCrump);
            }

            if (workflowExtension != null) {
                editorModel.setWorkflowModel(this.getWorkflow().getModel());
            }

            this.model = editorModel;

            break;
        }

        case DASHBOARD: {
            DashboardExtension ext = (DashboardExtension) this.extension;
            String grid = PropertyLoader.loadProperty(ext.getGrid());
            String id = ext.getIdentifier().getValue();
            this.model = new DashboardItemModel(id, label, tooltip, icon, grid);

            this.model.init();
            break;
        }
        
        case LIST: {
            TableModel<Datatype> tableModel = new TableModel<Datatype>();
            this.model = new ListModel(label, tooltip, icon, tableModel);
            break;
        }
        default:
            this.model = new WorkItemModel(label, tooltip, icon);
        }

        this.addBrowserEntries();
    }

    /**
     * Create a new browser entry for the work item in the referenced browsers.
     * 
     * @param item
     *            the work item to create the browser entries for
     */
    private void addBrowserEntries() {
        for (WorkItemBrowserExtension browserExt : this.extension.getBrowsers()) {
            String browserId = PropertyLoader.loadProperty(browserExt.getBrowserId());

            Browser browser = NabuccoServletUtil.getBrowser(browserId);

            if (browser != null) {
                BrowserEntry parent = this.initBrowserEntry(browserExt);
                browser.getModel().add(parent);
            }
        }
    }

    /**
     * Instanciates a default Browser Entry element -> root element
     * 
     * Must be overloaded by the children elements if the element must have some another behavior
     * 
     * @return the new created browser entry
     */
    protected BrowserEntry initBrowserEntry(WorkItemBrowserExtension browserExtension) {
        if (browserExtension == null) {
            throw new IllegalArgumentException("Cannot init browsers, the extension is [null].");
        }

        String label = PropertyLoader.loadProperty(browserExtension.getLabel());
        String tooltip = PropertyLoader.loadProperty(browserExtension.getTooltip());
        String icon = PropertyLoader.loadProperty(browserExtension.getIcon());

        // Take defaults if not configured
        if (label == null || label.isEmpty()) {
            label = this.getLabel();
        }
        if (tooltip == null || tooltip.isEmpty()) {
            tooltip = this.getTooltip();
        }
        if (icon == null || icon.isEmpty()) {
            icon = this.getIcon();
        }

        BrowserEntry browserEntry = new BrowserEntry(this.instanceId, label, tooltip, icon, false, this.type,
                this.instanceId);

        PropertyStringParser propertyParser = new PropertyStringParser(label, tooltip, icon);
        for (String propertyName : propertyParser.parseProperties()) {
            this.getModel().addPropertyChangeListener(propertyName, browserEntry);
        }

        return browserEntry;
    }

    /**
     * Returns the working item specific configured action or null if not configured
     * 
     * @param actionType
     *            the type of the action
     * @return the actionId to be fired by given action type or null if not defined
     */
    public String getWorkingItemAction(WorkItemActionType actionType) {
        if (actionType == null) {
            throw new IllegalArgumentException(
                    "Can not get the action id of the work item because the actionType is 'null'");
        }

        WorkItemActionsExtension actionsExtension = this.extension.getActions();

        if (actionsExtension == null) {
            throw new IllegalArgumentException("Actions are not configured for the working item " + this.getId());
        }

        NabuccoList<WorkItemActionExtension> actions = actionsExtension.getActions();
        if (actions == null || actions.isEmpty()) {
            throw new IllegalArgumentException("Actions are not configured for the working item " + this.getId());
        }

        for (WorkItemActionExtension actionExt : this.extension.getActions().getActions()) {
            WorkItemActionType type = PropertyLoader.loadProperty(WorkItemActionType.class, actionExt.getActionType());
            if (actionType.equals(type)) {
                String actionId = PropertyLoader.loadProperty(actionExt.getActionId());
                return actionId;
            }
        }
        return null;
    }

    /**
     * Getter for the instanceId.
     * 
     * @return Returns the instanceId.
     */
    public String getInstanceId() {
        return this.instanceId;
    }

    /**
     * Setter for the instanceId.
     * 
     * @param instanceId
     *            The instanceId to set.
     */
    void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    /**
     * Getter for the extension.
     * 
     * @return Returns the extension.
     */
    protected WorkItemExtension getExtension() {
        return this.extension;
    }

    /**
     * Getter for the item type.
     * 
     * @return Returns the type.
     */
    public WorkItemType getItemType() {

        return this.type;
    }

    /**
     * Getter for the work item id.
     * 
     * @return the work item id
     */
    public String getId() {
        ExtensionId identifier = this.extension.getIdentifier();
        if (identifier != null) {
            return identifier.getValue();
        }
        return null;
    }

    /**
     * Getter for the editor label.
     * 
     * @return the editor label
     */
    public String getLabel() {
        return PropertyLoader.loadProperty(this.extension.getLabel());
    }

    /**
     * Getter for the editor tooltip.
     * 
     * @return the editor tooltip
     */
    public String getTooltip() {
        return PropertyLoader.loadProperty(this.extension.getTooltip());
    }

    /**
     * Getter for the editor icon.
     * 
     * @return the editor icon
     */
    public String getIcon() {
        return PropertyLoader.loadProperty(this.extension.getIcon());
    }

    /**
     * Getter for the model.
     * 
     * @return Returns the model.
     */
    public WorkItemModel getModel() {
        return this.model;
    }

    /**
     * Getter for the workflow.
     * 
     * @return the workflow or null
     */
    public WorkItemWorkflow getWorkflow() {
        return (WorkItemWorkflow) super.getElement(WorkItemWorkflow.ELEMENT_ID);
    }

    /**
     * Getter for the list of browser references.
     * 
     * @return the browser references
     */
    public List<String> getBrowserRefIds() {
        List<String> browserIds = new ArrayList<String>();
        for (WorkItemBrowserExtension browserExtension : this.extension.getBrowsers()) {
            String browserId = PropertyLoader.loadProperty(browserExtension.getBrowserId());
            browserIds.add(browserId);
        }
        return browserIds;
    }

    /**
     * Getter for the breadCrump.
     * 
     * @return Returns the breadCrump.
     */
    public BreadCrump getBreadCrump() {
        return this.breadCrump;
    }

    /**
     * Getter for the source work item.
     * 
     * @return Returns the source.
     */
    public WorkItem getSource() {
        return this.source;
    }

    /**
     * Setter for the source work item.
     * 
     * @param source
     *            The source to set.
     */
    public void setSource(WorkItem source) {
        this.source = source;

        if (source != null && source.getBreadCrump() != null) {
            this.breadCrump.addFirst(source.getBreadCrump().getEntries());
        }
    }

    /**
     * Setter for the followerId.
     * 
     * @param followerId
     *            The followerId to set.
     */
    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }

    /**
     * Getter for the followerId.
     * 
     * @return Returns the followerId.
     */
    public String getFollowerId() {
        return this.followerId;
    }

    @Override
    public JsonMap toJson() {
        JsonMap json = new JsonMap();

        json.add(JSON_BREADCRUMPS, this.breadCrump.toJson());

        WorkItemWorkflow workflow = this.getWorkflow();

        if (workflow != null) {
            json.add(JSON_WORKFLOW, this.getWorkflow().toJson());
        }

        return json;
    }

}
