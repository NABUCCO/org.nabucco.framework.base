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
package org.nabucco.framework.base.ui.web.component.work.list;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.queryfilter.QueryFilterExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.ColumnExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.ListButtonExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.ListButtonGroupExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.MenuButtonExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.filter.FilterReferenceExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemBrowserEntryExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemBrowserExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.list.ListExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.common.button.Button;
import org.nabucco.framework.base.ui.web.component.common.button.ListButton;
import org.nabucco.framework.base.ui.web.component.common.button.ListButtonGroup;
import org.nabucco.framework.base.ui.web.component.common.button.MenuButton;
import org.nabucco.framework.base.ui.web.component.work.WorkItem;
import org.nabucco.framework.base.ui.web.component.work.WorkItemType;
import org.nabucco.framework.base.ui.web.component.work.util.QueryFilterExtensionUtil;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitor;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitorContext;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.browser.BrowserEntry;
import org.nabucco.framework.base.ui.web.model.browser.CollectionBrowserEntry;
import org.nabucco.framework.base.ui.web.model.list.ListModel;
import org.nabucco.framework.base.ui.web.model.table.TableColumn;
import org.nabucco.framework.base.ui.web.model.table.TableModel;
import org.nabucco.framework.base.ui.web.model.table.renderer.ColumnLayoutType;
import org.nabucco.framework.base.ui.web.model.table.renderer.ColumnRenderer;

/**
 * The listing of data of a specific business object.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public final class ListItem extends WorkItem implements TableElement {

    /** The default serial version UID */
    private static final long serialVersionUID = 1L;

    private static final String JSON_BUTTONS = "buttons";

    private static final String JSON_FILTERS = "filters";

    private static final String JSON_DOUBLECLICK_ACTION = "doubleclickAction";

    private static final String JSON_MENU_BUTTONS = "menuButtons";

    private Map<String, FilterItem> filterMap = new LinkedHashMap<String, FilterItem>();

    private MenuButton menuButtonContainer;

    /** Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(ListItem.class);

    /**
     * Creates a new {@link ListItem} instance.
     * 
     * @param instanceId
     *            the list instance id
     * @param extension
     *            the list extension
     */
    public ListItem(String instanceId, ListExtension extension) {
        super(instanceId, extension, WorkItemType.LIST);
    }

    @Override
    public void init() throws ExtensionException {
        super.init();

        TableModel<Datatype> tableModel = this.getTableModel();

        for (ListButtonExtension actionExtension : this.getExtension().getButtons()) {
            if (actionExtension instanceof ListButtonGroupExtension) {
                ListButtonGroup buttonGroup = new ListButtonGroup((ListButtonGroupExtension) actionExtension);
                this.addElement(buttonGroup.getId(), buttonGroup);
                buttonGroup.init();
            } else {
                ListButton button = new ListButton(actionExtension);
                this.addElement(button.getId(), button);
                button.init();
            }
        }

        if (this.getExtension().getMenuButton() != null) {
            MenuButtonExtension menuButton = this.getExtension().getMenuButton();
            if (menuButton != null) {
                menuButtonContainer = new MenuButton(menuButton);
                for (ListButtonExtension buttonExtension : menuButton.getButtons()) {
                    Button button;

                    if (buttonExtension instanceof ListButtonGroupExtension) {
                        button = new ListButtonGroup((ListButtonGroupExtension) buttonExtension);
                    } else if (buttonExtension instanceof ListButtonExtension) {
                        button = new ListButton(buttonExtension);
                    } else {
                        throw new IllegalArgumentException(
                                "Cannot add a button to the relation tab menu. Not supported extension type");
                    }

                    button.init();
                    menuButtonContainer.addButton(button);
                }

            }
        }

        for (ColumnExtension columnExtension : this.getExtension().getColumns()) {
            String id = PropertyLoader.loadProperty(columnExtension.getIdentifier());
            String property = PropertyLoader.loadProperty(columnExtension.getProperty());

            TableColumn column = new TableColumn(id, property);
            column.setLabel(PropertyLoader.loadProperty(columnExtension.getLabel()));
            column.setTooltip(PropertyLoader.loadProperty(columnExtension.getTooltip()));
            column.setVisible(PropertyLoader.loadProperty(columnExtension.getVisible()));
            column.setSortable(PropertyLoader.loadProperty(columnExtension.getSortable()));
            column.setWidth(PropertyLoader.loadProperty(columnExtension.getWidth()));

            this.initRenderer(columnExtension, column);

            tableModel.addColumn(column);
        }

        // Fill List model
        this.getListModel().setFilterList(this.getFilters());
    }

    /**
     * Returns the list of filters configured for the table
     * 
     * @return list of filter items
     */
    private List<FilterItem> getFilters() {

        if (this.filterMap.isEmpty()) {

            NabuccoList<FilterReferenceExtension> filterExtList = this.getExtension().getFilters();

            for (FilterReferenceExtension filterExt : filterExtList) {
                String refId = PropertyLoader.loadProperty(filterExt.getRefId());
                try {
                    QueryFilterExtension filterExtension = QueryFilterExtensionUtil.getFilterExtension(refId);
                    FilterItem filterItem = null;

                    if (filterExtension == null) {
                        filterItem = new FilterItem(refId, filterExt);
                    } else {
                        filterItem = new FilterItem(refId, filterExt, filterExtension);
                    }

                    this.filterMap.put(filterItem.getRefId(), filterItem);

                } catch (ExtensionException e) {
                    logger.debug("Cannot initialize the filter with id " + refId, e);
                }
            }
        }

        return new ArrayList<FilterItem>(this.filterMap.values());
    }

    /**
     * Getter for the filter item with
     * 
     * @param filterId
     *            the id of the filter to search fot
     * @return filter item or null if nothing found
     */
    public FilterItem getFilter(String filterId) {
        return this.filterMap.get(filterId);
    }

    /**
     * Initialize renderer
     * 
     * @param columnExtension
     *            the extension of the column
     * @param column
     *            the column instance
     */
    private void initRenderer(ColumnExtension columnExtension, TableColumn column) {
        Class<ColumnRenderer> rendererClass = null;
        ColumnRenderer renderer = null;

        if (columnExtension.getRenderer() != null && !columnExtension.getRenderer().getValue().getValue().isEmpty()) {
            rendererClass = PropertyLoader.loadProperty(columnExtension.getRenderer());
        }

        if (rendererClass != null) {
            try {
                renderer = rendererClass.newInstance();
                column.setRenderer(renderer);
            } catch (InstantiationException e) {
                logger.error("Cannot instanciate renderer ", rendererClass.getName(), e);
            } catch (IllegalAccessException e) {
                logger.error("Cannot access renderer ", rendererClass.getName(), e);
            }
        } else {
            if (columnExtension.getLayout() != null && !columnExtension.getLayout().getValue().getValue().isEmpty()) {
                ColumnLayoutType loadProperty = PropertyLoader.loadProperty(ColumnLayoutType.class,
                        columnExtension.getLayout());
                renderer = ColumnRenderer.getDefaultRenderer(loadProperty);
            }
        }

        if (renderer == null) {
            renderer = ColumnRenderer.getDefaultRenderer(null);
        }

        column.setRenderer(renderer);
    }

    @Override
    protected BrowserEntry initBrowserEntry(WorkItemBrowserExtension browserExtension) {
        BrowserEntry parent = super.initBrowserEntry(browserExtension);
        this.createBrowserEntryTree(parent, browserExtension.getElements());
        return parent;
    }

    /**
     * Recursively creates the tree of browser elements
     * 
     * @param parent
     *            parent browser element
     * @param itemExtensions
     *            browser extensions
     * 
     * @return the browser entry tree
     */
    private BrowserEntry createBrowserEntryTree(BrowserEntry parent, List<WorkItemBrowserEntryExtension> itemExtensions) {

        for (WorkItemBrowserEntryExtension entryExtension : itemExtensions) {

            String id = PropertyLoader.loadProperty(entryExtension.getEntryId());
            String label = PropertyLoader.loadProperty(entryExtension.getEntryLabel());
            String tooltip = PropertyLoader.loadProperty(entryExtension.getEntryTooltip());
            String icon = PropertyLoader.loadProperty(entryExtension.getEntryIcon());

            String propertyLabel = PropertyLoader.loadProperty(entryExtension.getPropertyLabel());
            String propertyTooltip = PropertyLoader.loadProperty(entryExtension.getPropertyTooltip());
            String propertyGrouping = PropertyLoader.loadProperty(entryExtension.getPropertyGrouping());
            String action = PropertyLoader.loadProperty(entryExtension.getAction());

            CollectionBrowserEntry<Datatype> browserEntry;
            browserEntry = new CollectionBrowserEntry<Datatype>(id, propertyLabel, action);

            browserEntry.setLabel(label);
            browserEntry.setTooltip(tooltip);
            browserEntry.setImage(icon);
            browserEntry.setPropertyTooltip(propertyTooltip);
            browserEntry.setPropertyGrouping(propertyGrouping);

            parent.add(browserEntry);

            if (!entryExtension.getElements().isEmpty()) {
                this.createBrowserEntryTree(browserEntry, entryExtension.getElements());
            }

            // Binding
            this.getTableModel().addPropertyChangeListener(TableModel.CONTENT_PROPERTY, browserEntry);
        }

        return null;
    }

    /**
     * Getter for the list model
     */
    public ListModel getListModel() {
        return (ListModel) this.getModel();
    }

    @Override
    public <T extends Datatype> TableModel<T> getTableModel() {
        return this.getListModel().getTableModel();
    }

    @Override
    protected ListExtension getExtension() {
        return (ListExtension) super.getExtension();
    }

    /**
     * Getter for the button with the given id.
     * 
     * @param id
     *            the button id
     * 
     * @return the button with the given id or null if no button with the given id is configured
     */
    public Button getButton(String id) {
        WebElement element = super.getElement(id);
        if (element.getType() == WebElementType.BUTTON) {
            return (Button) element;
        }
        return null;
    }

    /**
     * Getter for all configured editor buttons.
     * 
     * @return the list of buttons
     */
    public List<ListButton> getAllButtons() {
        boolean hasSelectedValue = this.getListModel().hasSelectedValue();

        List<ListButton> buttonList = new ArrayList<ListButton>();
        for (WebElement child : super.getElements()) {
            if (child.getType() == WebElementType.BUTTON) {
                ListButton button = (ListButton) child;
                button.updateStatus(hasSelectedValue, true);
                buttonList.add(button);
            }
        }
        return buttonList;
    }

    /**
     * Getter for all configured editor buttons.
     * 
     * @return the list of buttons
     */
    public List<ListButton> getMenuButtons() {
        boolean hasSelectedValue = this.getListModel().hasSelectedValue();

        List<ListButton> buttonList = new ArrayList<ListButton>();
        if (menuButtonContainer == null) {
            return buttonList;
        }
        for (WebElement child : this.menuButtonContainer.getElements()) {
            if (child.getType() == WebElementType.BUTTON) {
                ListButton button = (ListButton) child;
                button.updateStatus(hasSelectedValue, true);
                buttonList.add(button);
            }
        }
        return buttonList;
    }

    /**
     * Getter for the doubleclick action
     * 
     * @return doubleclick action
     * 
     * */
    private String getDoubleclickAction() {
        ListExtension listExtension = this.getExtension();
        String doubleclickAction = PropertyLoader.loadProperty(listExtension.getDoubleclickAction());

        return doubleclickAction;
    }

    /**
     * Accepts the web element visitor. Overload this function to let element be visited
     * 
     * @param visitor
     *            visitor to be accepted
     * @param context
     *            context of the visitor
     */
    @Override
    public <T extends WebElementVisitorContext> void accept(WebElementVisitor<T> visitor, T context)
            throws VisitorException {
        if (visitor != null) {
            visitor.visit(this, context);
        }
    }

    @Override
    public JsonMap toJson() {
        JsonMap json = super.toJson();

        JsonList menuButtonList = new JsonList();
        for (Button button : this.getMenuButtons()) {
            menuButtonList.add(button.toJson());
        }
        json.add(JSON_MENU_BUTTONS, menuButtonList);

        JsonList buttonList = new JsonList();
        for (Button button : this.getAllButtons()) {
            buttonList.add(button.toJson());
        }
        json.add(JSON_BUTTONS, buttonList);

        JsonList filterList = new JsonList();
        for (FilterItem filter : this.getFilters()) {
            filterList.add(filter.toJson());
        }
        json.add(JSON_FILTERS, filterList);

        json.add(JSON_MODEL, this.getModel().toJson());

        json.add(JSON_DOUBLECLICK_ACTION, this.getDoubleclickAction());
        return json;
    }

}
