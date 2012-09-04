/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco-source.org/nabucco-license.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.ui.web.component.work.bulkeditor;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.BulkEditorButtonExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.BulkEditorButtonGroupExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.ListButtonExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.MenuButtonExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemBrowserEntryExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemBrowserExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.bulkeditor.BulkEditorColumnsExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.bulkeditor.BulkEditorExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.bulkeditor.columns.BulkEditorColumnExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.bulkeditor.columns.DateColumnExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.bulkeditor.columns.DropDownColumnExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.bulkeditor.columns.PickerColumnExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.bulkeditor.columns.TextColumnExtension;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.common.button.BulkEditorButton;
import org.nabucco.framework.base.ui.web.component.common.button.BulkEditorButtonGroup;
import org.nabucco.framework.base.ui.web.component.common.button.Button;
import org.nabucco.framework.base.ui.web.component.common.button.MenuButton;
import org.nabucco.framework.base.ui.web.component.work.WorkItem;
import org.nabucco.framework.base.ui.web.component.work.WorkItemType;
import org.nabucco.framework.base.ui.web.component.work.bulkeditor.column.BulkEditorColumn;
import org.nabucco.framework.base.ui.web.component.work.bulkeditor.column.BulkEditorDateColumn;
import org.nabucco.framework.base.ui.web.component.work.bulkeditor.column.BulkEditorDropDownColumn;
import org.nabucco.framework.base.ui.web.component.work.bulkeditor.column.BulkEditorPickerColumn;
import org.nabucco.framework.base.ui.web.component.work.bulkeditor.column.BulkEditorTextColumn;
import org.nabucco.framework.base.ui.web.component.work.list.TableElement;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitor;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitorContext;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.browser.BrowserEntry;
import org.nabucco.framework.base.ui.web.model.browser.CollectionBrowserEntry;
import org.nabucco.framework.base.ui.web.model.bulkeditor.BulkEditorModel;
import org.nabucco.framework.base.ui.web.model.table.TableModel;

/**
 * BulkEditorItem
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class BulkEditorItem extends WorkItem implements TableElement {

    private static final long serialVersionUID = 1L;

    private static final String JSON_BUTTONS = "buttons";

    private static final String JSON_MENU_BUTTONS = "menuButtons";

    private MenuButton menuButtonContainer;

    /**
     * Creates a new {@link BulkEditorItem} instance.
     * 
     * @param instanceId
     *            the instance of the bulk editor
     * @param extension
     *            the extension of the editor
     */
    public BulkEditorItem(String instanceId, BulkEditorExtension extension) {
        super(instanceId, extension, WorkItemType.BULKEDITOR);

    }

    @Override
    public void init() throws ExtensionException {
        super.init();

        for (BulkEditorButtonExtension actionExtension : this.getExtension().getButtons()) {
            if (actionExtension instanceof BulkEditorButtonGroupExtension) {
                BulkEditorButtonGroup buttonGroup = new BulkEditorButtonGroup(
                        (BulkEditorButtonGroupExtension) actionExtension);
                this.addElement(buttonGroup.getId(), buttonGroup);
                buttonGroup.init();
            } else {
                BulkEditorButton button = new BulkEditorButton(actionExtension);
                this.addElement(button.getId(), button);
                button.init();
            }
        }

        MenuButtonExtension menuButton = this.getExtension().getMenuButton();
        if (menuButton != null) {
            menuButtonContainer = new MenuButton(menuButton);
            for (ListButtonExtension buttonExtension : menuButton.getButtons()) {
                Button button;

                if (buttonExtension instanceof BulkEditorButtonGroupExtension) {
                    button = new BulkEditorButtonGroup((BulkEditorButtonGroupExtension) buttonExtension);
                } else if (buttonExtension instanceof BulkEditorButtonExtension) {
                    button = new BulkEditorButton((BulkEditorButtonExtension) buttonExtension);
                } else {
                    throw new IllegalArgumentException(
                            "Cannot add a button to the bulk editor menu. Not supported extension type");
                }

                button.init();
                menuButtonContainer.addButton(button);
            }

        }

        BulkEditorColumnsExtension columns = this.getExtension().getColumns();
        BulkEditorModel bulkModel = this.getModel();

        bulkModel.setPagingActive(this.isPagingActive());
        bulkModel.setPagingSize(this.getPagingSize());

        for (BulkEditorColumnExtension extension : columns.getColumns()) {
            BuldEditorColumnControlType type = PropertyLoader.loadProperty(BuldEditorColumnControlType.class,
                    extension.getType());
            BulkEditorColumn control;

            switch (type) {
            case DATE: {
                control = new BulkEditorDateColumn((DateColumnExtension) extension, bulkModel);
                break;
            }
            case DROPDOWN: {
                control = new BulkEditorDropDownColumn((DropDownColumnExtension) extension, bulkModel);
                break;
            }
            case TEXT: {
                control = new BulkEditorTextColumn((TextColumnExtension) extension, bulkModel);
                break;
            }
            case PICKER: {
                control = new BulkEditorPickerColumn((PickerColumnExtension) extension, bulkModel);
                break;
            }
            default: {
                throw new IllegalArgumentException(
                        "Cannot create bulk column. The type of the column is not supported yet.");
            }
            }
            control.init();

            super.addElement(control.getId(), control);
        }
    }

    @Override
    public <T extends Datatype> TableModel<T> getTableModel() {
        BulkEditorModel bulkModel = this.getModel();
        return bulkModel.getTableModel();
    }

    /**
     * returns true if paging is active
     * 
     * @return true if active
     */
    private boolean isPagingActive() {
        return PropertyLoader.loadProperty(this.getExtension().getColumns().getPaging());
    }

    /**
     * returns true if paging is active
     * 
     * @return true if active
     */
    private int getPagingSize() {
        return PropertyLoader.loadProperty(this.getExtension().getColumns().getPagingSize());
    }

    @Override
    protected BrowserEntry initBrowserEntry(WorkItemBrowserExtension browserExtension) {
        BrowserEntry parent = super.initBrowserEntry(browserExtension);
        this.createBrowserEntryTree(parent, browserExtension.getElements());
        return parent;
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
    public List<BulkEditorButton> getAllButtons() {
        boolean hasSelectedValue = this.getModel().hasSelectedValue();
        boolean dirty = this.getModel().isDirty();

        List<BulkEditorButton> buttonList = new ArrayList<BulkEditorButton>();
        for (WebElement child : super.getElements()) {
            if (child.getType() == WebElementType.BUTTON) {
                BulkEditorButton button = (BulkEditorButton) child;
                button.updateStatus(dirty, hasSelectedValue, true);
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
    public List<BulkEditorButton> getMenuButtons() {
        boolean hasSelectedValue = this.getModel().hasSelectedValue();
        boolean dirty = this.getModel().isDirty();

        List<BulkEditorButton> buttonList = new ArrayList<BulkEditorButton>();
        if (menuButtonContainer == null) {
            return buttonList;
        }
        for (WebElement child : this.menuButtonContainer.getElements()) {
            if (child.getType() == WebElementType.BUTTON) {
                BulkEditorButton button = (BulkEditorButton) child;
                button.updateStatus(dirty, hasSelectedValue, true);
                buttonList.add(button);
            }
        }
        return buttonList;
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
            this.getModel().addPropertyChangeListener(BulkEditorModel.CONTENT_PROPERTY, browserEntry);
        }

        return null;
    }

    @Override
    protected BulkEditorExtension getExtension() {
        return (BulkEditorExtension) super.getExtension();
    }

    @Override
    public BulkEditorModel getModel() {
        return (BulkEditorModel) super.getModel();
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

        json.add(JSON_MODEL, this.getModel().toJson());

        return json;
    }

}
