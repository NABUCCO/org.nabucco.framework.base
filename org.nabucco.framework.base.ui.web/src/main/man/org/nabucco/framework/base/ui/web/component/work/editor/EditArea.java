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
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.EditorExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.EditorTabExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.ui.web.component.WebComposite;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitor;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitorContext;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;

/**
 * Editing Area of the Editor.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class EditArea extends WebComposite {

    /** Default serial version UID */
    private static final long serialVersionUID = 1L;

    /** The Edit Area ID */
    public static final String ID = "edit";

    private static final String JSON_TABS = "tabs";

    private static final String JSON_FOCUS = "focus";

    /** The logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(EditorItem.class);

    /** ID of the currently focused Tab */
    private String focusedTabId;

    /** The Editor Extension */
    private EditorExtension extension;

    /**
     * Creates a new {@link EditArea} instance.
     * 
     * @param extension
     *            the editor extension
     */
    public EditArea(EditorExtension extension) {
        super(WebElementType.EDITOR_EDIT_AREA, extension);
        if (extension == null) {
            throw new IllegalArgumentException("Cannot create edit area for extension 'null'.");
        }
        this.extension = extension;
    }

    @Override
    public void init() throws ExtensionException {
        for (EditorTabExtension tabExtension : this.extension.getTabs()) {
            EditTab editorTab = new EditTab(tabExtension);
            super.addElement(editorTab.getId(), editorTab);
            editorTab.init();
        }
    }

    /**
     * Getter for the editor tab with the given id.
     * 
     * @param id
     *            the editor tab id
     * 
     * @return the editor tab, or null if no editor tab with the given id is registerd
     */
    public EditTab getTab(String id) {
        WebElement element = super.getElement(id);

        if (element instanceof EditTab) {
            return (EditTab) element;
        }

        return null;
    }

    /**
     * Getter for the currently opened work item.
     * 
     * @return the current work item, or null if none is open
     */
    public EditTab getFocusedTab() {

        WebElement current = super.getElement(this.focusedTabId);
        if (current instanceof EditTab) {
            return (EditTab) current;
        }

        String[] elementIds = super.getElementIds();
        for (int i = 0; i < elementIds.length; i++) {
            WebElement element = super.getElement(elementIds[i]);
            if (element instanceof EditTab) {
                EditTab tab = (EditTab) element;
                this.focusedTabId = tab.getId();
                return tab;
            }
        }

        return null;
    }

    /**
     * Open the editor tab with the given id when it exists in this editor.
     * 
     * @param tabId
     *            the editor tab id
     */
    public void focusTab(String tabId) {
        if (tabId == null) {
            logger.error("Cannot open editor tab with id 'null'.");
            throw new IllegalArgumentException("Cannot open editor tab with id 'null'.");
        }

        WebElement element = this.getElement(tabId);
        if (element instanceof EditTab) {
            this.focusedTabId = tabId;
            return;
        }

        logger.warning("Cannot find work item with instance id '", tabId, "'.");
    }

    /**
     * Getter for the lsit of editor tabs.
     * 
     * @return the list of registered editor tabs
     */
    public List<EditTab> getAllTabs() {
        List<EditTab> tabs = new ArrayList<EditTab>();
        String[] ids = super.getElementIds();
        for (String id : ids) {
            WebElement element = super.getElement(id);
            if (element instanceof EditTab) {
                tabs.add((EditTab) element);
            }
        }
        return tabs;
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
        JsonMap json = new JsonMap();
        JsonList tabList = new JsonList();

        for (EditTab tab : this.getAllTabs()) {
            tabList.add(tab.toJson());
        }

        json.add(JSON_TABS, tabList);

        EditTab focus = this.getFocusedTab();
        if (focus != null) {
            json.add(JSON_FOCUS, focus.getId());
        }

        return json;
    }

}
