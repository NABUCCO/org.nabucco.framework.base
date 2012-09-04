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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkAreaExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.bulkeditor.BulkEditorExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.dashboard.DashboardExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.EditorExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.list.ListExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.ui.web.component.WebComposite;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.browser.Browser;
import org.nabucco.framework.base.ui.web.component.work.bulkeditor.BulkEditorItem;
import org.nabucco.framework.base.ui.web.component.work.dashboard.DashboardItem;
import org.nabucco.framework.base.ui.web.component.work.editor.EditorItem;
import org.nabucco.framework.base.ui.web.component.work.list.ListItem;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitor;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitorContext;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;

/**
 * The actual working area of a NABUCCO web application.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class WorkArea extends WebComposite {

    /** The default serial version UID */
    private static final long serialVersionUID = 1L;

    private static final String JSON_INSTANCEID = "instanceId";

    private static final String JSON_TYPE = "type";

    private static final String JSON_CURRENT = "current";

    private static final String JSON_LAYOUT = "layout";

    private static final String JSON_ITEMS = "items";

    private static final String JSON_CLOSED = "closed";

    private static final String JSON_ORDER = "order";

    /** The WorkArea Extension */
    private WorkAreaExtension extension;

    /** The Tab Stack */
    private WorkItemStack stack = new WorkItemStack();

    /** The closed work items. */
    private Map<String, WorkItem> closedItems = new HashMap<String, WorkItem>();

    /** The work item history. */
    private Map<String, WorkItem> history = new HashMap<String, WorkItem>();

    /** The logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(WorkArea.class);

    /**
     * Creates a new {@link WorkArea} instance.
     * 
     * @param extension
     *            the work area extension
     */
    public WorkArea(WorkAreaExtension extension) {
        super(WebElementType.WORK_AREA, extension);
        if (extension == null) {
            throw new IllegalArgumentException("Cannot create work area for extension 'null'.");
        }
        this.extension = extension;
    }

    @Override
    public void init() throws ExtensionException {
    }

    @Override
    public void clear() {
        super.clear();
        closedItems.clear();
    }

    /**
     * Returns true if the working area has unsaved changes
     * 
     * @return true if dirty
     */
    public boolean isDirty() {
        for (WorkItem item : this.getAllItems()) {
            if (item == null || item.getModel() == null) {
                continue;
            }
            if (item.getModel().isDirty() == true) {
                return true;
            }
        }
        return false;
    }

    /**
     * Getter for the close dialog ID.
     * 
     * @return the id of the close dialog
     */
    public String getCloseDialogId() {
        return PropertyLoader.loadProperty(extension.getCloseDialog());
    }

    /**
     * Creates a new editor work item with the given id. A unique instance id is created for this
     * new editor item.
     * 
     * @param itemId
     *            the editor id
     * 
     * @return the newly created editor work item
     * 
     * @throws ExtensionException
     *             when a new editor work item with the given ids is not configured correctly
     */
    public EditorItem newEditor(String itemId) throws ExtensionException {
        return (EditorItem) this.newItem(WorkItemType.EDITOR, itemId, NabuccoSystem.createUUID());
    }

    /**
     * Creates a new editor work item with the given id.
     * 
     * @param itemId
     *            the editor id
     * @param instanceId
     *            the editor instance id
     * 
     * @return the newly created editor work item
     * 
     * @throws ExtensionException
     *             when a new editor work item with the given ids is not configured correctly
     */
    public EditorItem newEditor(String itemId, String instanceId) throws ExtensionException {
        return (EditorItem) this.newItem(WorkItemType.EDITOR, itemId, instanceId);
    }

    /**
     * Creates a new bulk editor work item with given id
     * 
     * @param itemId
     *            the id of the bulk editor
     * @param instanceId
     *            the instance id
     * @return the newly created bulk editor
     * @throws ExtensionException
     *             when a new bulk editor work item with the given ids is not configured correctly
     */
    public BulkEditorItem newBulkEditor(String itemId, String instanceId) throws ExtensionException {
        return (BulkEditorItem) this.newItem(WorkItemType.BULKEDITOR, itemId, instanceId);
    }

    /**
     * Creates a new list work item with the given id. A unique instance id is created for this new
     * list item.
     * 
     * @param itemId
     *            the list id
     * 
     * @return the newly created list work item
     * 
     * @throws ExtensionException
     *             when a new list work item with the given ids is not configured correctly
     */
    public ListItem newList(String itemId) throws ExtensionException {
        return (ListItem) this.newItem(WorkItemType.LIST, itemId, NabuccoSystem.createUUID());
    }

    /**
     * Creates a new list work item with the given id.
     * 
     * @param itemId
     *            the list id
     * @param instanceId
     *            the list instance id
     * 
     * @return the newly created list work item
     * 
     * @throws ExtensionException
     *             when a new list work item with the given ids is not configured correctly
     */
    public ListItem newList(String itemId, String instanceId) throws ExtensionException {
        return (ListItem) this.newItem(WorkItemType.LIST, itemId, instanceId);
    }

    /**
     * Creates a new dashboard work item with the given id. A unique instance id is created for this
     * new dashboard item.
     * 
     * @param itemId
     *            the dashboard id
     * 
     * @return the newly created dashboard work item
     * 
     * @throws ExtensionException
     *             when a new dashboard work item with the given ids is not configured correctly
     */
    public DashboardItem newDashboard(String itemId) throws ExtensionException {
        return (DashboardItem) this.newItem(WorkItemType.DASHBOARD, itemId, NabuccoSystem.createUUID());
    }

    /**
     * Creates a new dashboard work item with the given id.
     * 
     * @param itemId
     *            the dashboard id
     * @param instanceId
     *            the dashboard instance id
     * 
     * @return the newly created dashboard work item
     * 
     * @throws ExtensionException
     *             when a new dashboard work item with the given ids is not configured correctly
     */
    public DashboardItem newDashboard(String itemId, String instanceId) throws ExtensionException {
        return (DashboardItem) this.newItem(WorkItemType.DASHBOARD, itemId, instanceId);
    }

    /**
     * Creates a new work item with the given id.
     * 
     * @param type
     *            the item type
     * @param uniqueInstanceId
     *            the unique instance id
     * 
     * @return the newly created work item
     * 
     * @throws ExtensionException
     *             when a new work item with the given ids is not configured correctly
     */
    private WorkItem newItem(WorkItemType type, String uniqueInstanceId) throws ExtensionException {
        if (type == null) {
            logger.error("Cannot open work item for type [null].");
            throw new IllegalArgumentException("Cannot open work item for type [null].");
        }
        if (uniqueInstanceId == null) {
            logger.error("Cannot open work item with unique instance id '", uniqueInstanceId + "'.");
            throw new ExtensionException("Cannot open work item with unique instance id '" + uniqueInstanceId + "'.");
        }

        String itemId = UniqueInstanceIdGenerator.getItemId(uniqueInstanceId);
        try {

            // When item is already initialized.
            if (this.hasItem(itemId, uniqueInstanceId)) {
                return this.openItem(itemId, uniqueInstanceId);
            }

            WorkItem item;

            switch (type) {

            case EDITOR: {
                EditorExtension editorExtension = (EditorExtension) NabuccoSystem.getExtensionResolver()
                        .resolveExtension(ExtensionPointType.ORG_NABUCCO_UI_EDITOR, itemId);

                item = new EditorItem(uniqueInstanceId, editorExtension);
                this.addElement(uniqueInstanceId, item);
                item.init();

                break;
            }

            case LIST: {
                ListExtension listExtension = (ListExtension) NabuccoSystem.getExtensionResolver().resolveExtension(
                        ExtensionPointType.ORG_NABUCCO_UI_LIST, itemId);

                item = new ListItem(uniqueInstanceId, listExtension);
                this.addElement(uniqueInstanceId, item);
                item.init();

                break;
            }

            case DASHBOARD: {
                DashboardExtension dashboardExtension = (DashboardExtension) NabuccoSystem.getExtensionResolver()
                        .resolveExtension(ExtensionPointType.ORG_NABUCCO_UI_DASHBOARD, itemId);

                item = new DashboardItem(uniqueInstanceId, dashboardExtension);
                this.addElement(uniqueInstanceId, item);
                item.init();

                break;
            }

            case BULKEDITOR: {
                BulkEditorExtension bulkEditorExtension = (BulkEditorExtension) NabuccoSystem.getExtensionResolver()
                        .resolveExtension(ExtensionPointType.ORG_NABUCCO_UI_BULK_EDITOR, itemId);

                item = new BulkEditorItem(uniqueInstanceId, bulkEditorExtension);
                this.addElement(uniqueInstanceId, item);
                item.init();

                break;
            }

            default:
                throw new IllegalStateException("WorkItem Type [" + type + "] is not supported.");
            }

            stack.push(itemId, uniqueInstanceId);
            history.put(uniqueInstanceId, item);

            if (history.size() > 50) {
                String[] keys = history.keySet().toArray(new String[history.size()]);
                history.remove(keys[0]);
            }

            return item;

        } catch (ExtensionException ee) {
            logger.error(ee, "Error loading work item configuration '", itemId, "' : '", uniqueInstanceId, "'.");
            throw ee;
        } catch (Exception e) {
            logger.error(e, "Error opening new work item '", itemId, "' : '", uniqueInstanceId, "'.");
            throw new ExtensionException("Error opening new work item.", e);
        }
    }

    /**
     * Creates a new work item with the given id.
     * 
     * @param type
     *            the item type
     * @param itemId
     *            the work item id
     * @param instanceId
     *            the work item instance id
     * 
     * @return the newly created work item
     * 
     * @throws ExtensionException
     *             when a new work item with the given ids is not configured correctly
     */
    private WorkItem newItem(WorkItemType type, String itemId, String instanceId) throws ExtensionException {
        if (itemId == null || instanceId == null) {
            logger.error("Cannot open work item with id '", itemId, "' : '", instanceId, "'.");
            throw new ExtensionException("Cannot open work item with id '" + itemId + "' : '" + instanceId + "'.");
        }

        String uniqueInstanceId = UniqueInstanceIdGenerator.generateUniqueId(itemId, instanceId);

        WorkItem oldEditor = this.getItem(uniqueInstanceId);
        if (oldEditor != null) {
            this.selectItem(uniqueInstanceId);
            return oldEditor;
        }
        WorkItem retVal = this.newItem(type, uniqueInstanceId);

        return retVal;
    }

    /**
     * Change the instance id of a contained work item to a new one.
     * 
     * @param oldInstanceId
     *            the old instance id
     * @param newInstanceId
     *            the new instance id
     */
    public WorkItem changeInstanceId(String oldInstanceId, String newInstanceId) {
        if (oldInstanceId == null || newInstanceId == null) {
            return null;
        }
        if (oldInstanceId.equals(newInstanceId)) {
            return null;
        }
        if (!this.containsElement(oldInstanceId)) {
            return null;
        }

        WorkItem item = this.getItem(oldInstanceId);

        // TODO: Find another way to determine new instance id
        item.setFollowerId(newInstanceId);

        String uniqueInstanceId = UniqueInstanceIdGenerator.generateUniqueId(item.getId(), newInstanceId);

        if (oldInstanceId.equals(uniqueInstanceId)) {
            return null;
        }

        this.closeItem(oldInstanceId);

        try {
            WorkItem newItem = this.newItem(item.getItemType(), item.getId(), newInstanceId);
            newItem.setSource(item.getSource());
            newItem.setSourceWebElement(item.getSourceWebElement());

            return newItem;
        } catch (ExtensionException e) {
            logger.warning(e, "Error changing instance id from '", oldInstanceId, "' to '", uniqueInstanceId, "'.");
        }

        return null;
    }

    /**
     * Open the item when it is already initialized.
     * 
     * @param itemId
     *            the id of the item to open
     * @param instanceId
     *            the instanceId of the item to open
     * 
     * @return the opened item, or null if the item was not open
     */
    private WorkItem openItem(String itemId, String instanceId) {
        // When item is already initialized.
        if (this.hasItem(itemId, instanceId)) {
            stack.push(itemId, instanceId);
            return (WorkItem) this.getElement(instanceId);
        }
        return null;
    }

    /**
     * Checks whether the work area holds the item with the given item and instance id.
     * 
     * @param itemId
     *            the item id
     * @param instanceId
     *            the instance id
     * 
     * @return <b>true</b> if the work area holds the given item, <b>false</b> if not
     */
    private boolean hasItem(String itemId, String instanceId) {
        return stack.contains(itemId, instanceId);
    }

    /**
     * Open an existing work item with the given id.
     * 
     * @param uniqueInstanceId
     *            the unique work item instance id (must be generated by the
     *            {@link WorkArea#newItem(WebElementType, String, String)}
     */
    public void selectItem(String uniqueInstanceId) {
        if (uniqueInstanceId == null) {
            logger.error("Cannot open work item with instance id 'null'.");
            throw new IllegalArgumentException("Cannot open work item with instance id 'null'.");
        }

        WebElement element = this.getElement(uniqueInstanceId);
        if (element instanceof WorkItem) {
            stack.push(((WorkItem) element).getId(), uniqueInstanceId);
            return;
        }

        WorkItem item = history.get(uniqueInstanceId);

        if (item != null) {
            try {
                this.newItem(item.getItemType(), uniqueInstanceId);
                return;
            } catch (ExtensionException e) {
                logger.error(e, "Unexpected error initializing closed work item ", item.getId(), " with id '",
                        uniqueInstanceId, "'.");
            }
        }

        logger.warning("Cannot find work item with instance id '", uniqueInstanceId, "'.");
    }

    /**
     * Close the item with the given instance id.
     * 
     * @param uniqueInstanceId
     *            the unique work item instance id
     */
    public void closeItem(String uniqueInstanceId) {

        WebElement element = this.removeElement(uniqueInstanceId);

        if (element instanceof WorkItem) {

            WorkItem removedElement = (WorkItem) element;
            closedItems.put(uniqueInstanceId, removedElement);

            // Skip when no tab is selected.
            if (stack.peek() == null) {
                return;
            }

            stack.remove(removedElement.getId(), uniqueInstanceId);

            this.removeBrowserEntries(uniqueInstanceId, removedElement);
        }

    }

    /**
     * Getter for the currently opened work item.
     * 
     * @return the current work item, or null if none is open
     */
    public WorkItem getCurrentItem() {

        WorkItemStackEntry current = stack.peek();
        if (current != null) {
            WebElement currentItem = super.getElement(current.getInstanceId());
            if (currentItem instanceof WorkItem) {
                return (WorkItem) currentItem;
            }
        }

        String[] elementIds = super.getElementIds();
        for (String elementId : elementIds) {
            WebElement element = super.getElement(elementId);
            if (element instanceof WorkItem) {
                WorkItem item = (WorkItem) element;
                stack.push(item.getId(), elementId);
                return item;
            }
        }

        return null;
    }

    /**
     * Getter for the navigation area layout.
     * 
     * @return the layout
     */
    public String getLayout() {
        return PropertyLoader.loadProperty(extension.getLayout());
    }

    /**
     * Getter for the work item with the given id.
     * 
     * @param uniqueInstanceId
     *            unique instance id of the work item
     * 
     * @return the work item or null if no work item with the given name exists
     */
    public WorkItem getItem(String uniqueInstanceId) {
        WebElement child = super.getElement(uniqueInstanceId);
        if (child instanceof WorkItem) {
            return (WorkItem) child;
        }
        return null;
    }

    /**
     * Getter for the list of work items.
     * 
     * @return the list of registered work items
     */
    public List<WorkItem> getAllItems() {
        List<WorkItem> items = new ArrayList<WorkItem>();
        String[] ids = super.getElementIds();
        for (String id : ids) {
            WebElement element = super.getElement(id);
            if (element instanceof WorkItem) {
                items.add((WorkItem) element);
            }
        }
        return items;
    }

    /**
     * Removes the browser entry for the work item in the referenced browsers.
     * 
     * @param item
     *            the work item to create the browser entries for
     */
    private void removeBrowserEntries(String instanceId, WorkItem item) {
        for (String browserId : item.getBrowserRefIds()) {
            Browser browser = NabuccoServletUtil.getBrowser(browserId);

            if (browser != null) {
                browser.getModel().remove(instanceId);
            }
        }
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

        // Open Tabs

        JsonList tabList = new JsonList();
        String[] elementIds = super.getElementIds();
        for (String elementId : elementIds) {

            WorkItem item = (WorkItem) super.getElement(elementId);

            JsonMap tabEntry = new JsonMap();
            tabEntry.add(JSON_TYPE, item.getType());
            tabEntry.add(JSON_ID, item.getId());
            tabEntry.add(JSON_INSTANCEID, elementId);
            tabEntry.add(JSON_ORDER, stack.indexOf(item));
            tabEntry.add(JSON_MODEL, item.getModel().toJson());

            tabList.add(tabEntry);
        }

        // Closed Tabs

        JsonList closedList = new JsonList();
        for (Entry<String, WorkItem> entry : closedItems.entrySet()) {
            JsonMap closedEntry = new JsonMap();
            closedEntry.add(JSON_ID, entry.getValue().getId());
            closedEntry.add(JSON_INSTANCEID, entry.getKey());

            closedList.add(closedEntry);
        }

        // Current Tab

        WorkItemStackEntry current = stack.peek();
        if (current != null) {
            JsonMap currentTab = new JsonMap();
            currentTab.add(JSON_ID, current.getItemId());
            currentTab.add(JSON_INSTANCEID, current.getInstanceId());
            json.add(JSON_CURRENT, currentTab);
        }

        json.add(JSON_LAYOUT, this.getLayout());
        json.add(JSON_ITEMS, tabList);
        json.add(JSON_CLOSED, closedList);

        return json;
    }
}
