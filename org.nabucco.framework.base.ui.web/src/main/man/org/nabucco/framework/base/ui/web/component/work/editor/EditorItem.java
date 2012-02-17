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
package org.nabucco.framework.base.ui.web.component.work.editor;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.EditorButtonExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemBrowserEntryExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.WorkItemBrowserExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.EditorExtension;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.common.button.Button;
import org.nabucco.framework.base.ui.web.component.common.button.EditorButton;
import org.nabucco.framework.base.ui.web.component.work.WorkItem;
import org.nabucco.framework.base.ui.web.component.work.WorkItemType;
import org.nabucco.framework.base.ui.web.component.work.editor.control.EditorControl;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.browser.BindableBrowserEntry;
import org.nabucco.framework.base.ui.web.model.browser.BrowserEntry;
import org.nabucco.framework.base.ui.web.model.control.ControlModel;
import org.nabucco.framework.base.ui.web.model.control.util.dependency.DependencyController;
import org.nabucco.framework.base.ui.web.model.work.EditorModel;

/**
 * The editing of data of a specific business object.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public final class EditorItem extends WorkItem {

    /** The default serial version UID */
    private static final long serialVersionUID = 1L;

    private static final String JSON_BUTTONS = "buttons";

    private static final String JSON_EDIT_AREA = "edit";

    private static final String JSON_RELATION_AREA = "relation";

    /**
     * Creates a new {@link EditorItem} instance.
     * 
     * @param instanceId
     *            the editor instance id
     * @param extension
     *            the editor extension
     */
    public EditorItem(String instanceId, EditorExtension extension) {
        super(instanceId, extension, WorkItemType.EDITOR);
    }

    @Override
    public void init() throws ExtensionException {
        super.init();

        for (EditorButtonExtension actionExtension : this.getExtension().getButtons()) {
            EditorButton button = new EditorButton(actionExtension);
            this.addElement(button.getId(), button);
            button.init();
        }

        EditArea editArea = new EditArea(this.getExtension());
        this.addElement(EditArea.ID, editArea);
        editArea.init();

        RelationArea relationArea = new RelationArea(this.getExtension());
        this.addElement(RelationArea.ID, relationArea);
        relationArea.init();

        for (EditTab tab : editArea.getAllTabs()) {
            for (EditorControl control : tab.getAllControls()) {
                ControlModel<?> controlModel = control.getModel();
                this.getModel().addControl(controlModel);

                // Add dependency notifications
                DependencyController dependencyController = controlModel.getDependencyController();
                List<String> dependentProperties = dependencyController.getPropertyNames();
                for (String dependentProperty : dependentProperties) {
                    this.getModel().addPropertyChangeListener(dependentProperty, dependencyController);
                }
                if (dependentProperties.size() > 0) {
                    this.getModel().addPropertyChangeListener(DependencyController.RELOADING_DATATYPE_EVENT_NAME,
                            dependencyController);
                }
            }
        }

        for (RelationTab tab : relationArea.getAllTabs()) {
            if (tab.getProperty() != null) {
                this.getModel().addRelationTable(tab.getProperty(), tab.getModel());

                // Add dependency notifications
                DependencyController dependencyController = tab.getModel().getDependencyController();
                List<String> dependentProperties = dependencyController.getPropertyNames();
                for (String dependentProperty : dependentProperties) {
                    this.getModel().addPropertyChangeListener(dependentProperty, dependencyController);
                }
                if (dependentProperties.size() > 0) {
                    this.getModel().addPropertyChangeListener(DependencyController.RELOADING_DATATYPE_EVENT_NAME,
                            dependencyController);
                }
            }
        }

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
            String propertyPath = PropertyLoader.loadProperty(entryExtension.getProperty());
            String action = PropertyLoader.loadProperty(entryExtension.getAction());

            BindableBrowserEntry browserEntry = new BindableBrowserEntry(id, propertyLabel, propertyPath, action);
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
            this.getModel().addBrowserEntry(browserEntry);

            this.getModel().addPropertyChangeListener(propertyPath, browserEntry);
        }

        return parent;
    }

    @Override
    protected EditorExtension getExtension() {
        return (EditorExtension) super.getExtension();
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
    public List<EditorButton> getAllButtons() {
        boolean isDirty = this.getModel().isDirty();

        List<EditorButton> buttonList = new ArrayList<EditorButton>();
        for (WebElement child : super.getElements()) {
            if (child.getType() == WebElementType.BUTTON) {
                EditorButton button = (EditorButton) child;
                button.updateStatus(isDirty);
                buttonList.add(button);
            }
        }
        return buttonList;
    }

    /**
     * Getter for the editors edit area.
     * 
     * @return the edit area, or null if none exist
     */
    public EditArea getEditArea() {
        WebElement editArea = super.getElement(EditArea.ID);

        if (editArea instanceof EditArea) {
            return (EditArea) editArea;
        }
        return null;
    }

    /**
     * Getter for the editors relation area.
     * 
     * @return the relation area, or null if none exist
     */
    public RelationArea getRelationArea() {
        WebElement relationArea = super.getElement(RelationArea.ID);

        if (relationArea instanceof RelationArea) {
            return (RelationArea) relationArea;
        }
        return null;
    }

    /**
     * Getter for the model.
     * 
     * @return Returns the model.
     */
    @Override
    public EditorModel getModel() {
        return (EditorModel) super.getModel();
    }

    @Override
    public JsonMap toJson() {
        JsonMap json = super.toJson();

        EditArea editArea = this.getEditArea();
        RelationArea relationArea = this.getRelationArea();

        JsonList buttonList = new JsonList();
        for (Button button : this.getAllButtons()) {
            buttonList.add(button.toJson());
        }
        json.add(JSON_BUTTONS, buttonList);

        if (editArea != null) {
            json.add(JSON_EDIT_AREA, editArea.toJson());
        }
        if (relationArea != null) {
            json.add(JSON_RELATION_AREA, relationArea.toJson());
        }

        return json;
    }
}
