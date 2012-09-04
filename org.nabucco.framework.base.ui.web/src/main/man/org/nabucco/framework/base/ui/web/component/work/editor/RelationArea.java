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
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.EditorRelationExtension;
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
 * RelationArea
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class RelationArea extends WebComposite {

    /** Default serial version UID */
    private static final long serialVersionUID = 1L;

    /** The Edit Area ID */
    public static final String ID = "relation";

    private static final String JSON_TABS = "tabs";

    private static final String JSON_FOCUS = "focus";

    private static final String JSON_SIZE = "size";

    /** The logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(EditorItem.class);

    /** ID of the currently focused Tab */
    private String focusedTabId;

    /** The Editor Extension */
    private EditorExtension extension;

    /** The actual configured page size */
    private int pageSize = 15;

    /**
     * Creates a new {@link EditArea} instance.
     * 
     * @param extension
     *            the editor extension
     */
    public RelationArea(EditorExtension extension) {
        super(WebElementType.EDITOR_RELATION_AREA, extension);
        if (extension == null) {
            throw new IllegalArgumentException("Cannot create edit area for extension 'null'.");
        }
        this.extension = extension;
    }

    @Override
    public void init() throws ExtensionException {
        for (EditorRelationExtension relationExtension : extension.getRelations()) {
            RelationTab relationTab = new RelationTab(relationExtension);
            super.addElement(relationTab.getId(), relationTab);
            relationTab.init();
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
    public RelationTab getTab(String id) {
        WebElement element = super.getElement(id);

        if (element instanceof RelationTab) {
            return (RelationTab) element;
        }

        return null;
    }

    /**
     * Returns the list of relation Tabs that need to be refreshed
     * 
     * @return list of ids
     */
    public List<String> getRefreshNeededRelationIds() {
        List<String> retVal = new ArrayList<String>();

        RelationTab focusedTab = this.getFocusedTab();

        if (focusedTab != null) {
            boolean refreshNeeded = focusedTab.getModel().isRefreshNeeded();
            if (refreshNeeded) {
                retVal.add(focusedTab.getId());
            }
        }

        return retVal;
    }

    /**
     * Getter for the currently opened work item.
     * 
     * @return the current work item, or null if none is open
     */
    public RelationTab getFocusedTab() {

        WebElement current = super.getElement(focusedTabId);
        if (current instanceof RelationTab) {
            return (RelationTab) current;
        }

        String[] elementIds = super.getElementIds();
        for (int i = 0; i < elementIds.length; i++) {
            WebElement element = super.getElement(elementIds[i]);
            if (element instanceof RelationTab) {
                RelationTab tab = (RelationTab) element;
                focusedTabId = tab.getId();
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
        if (element instanceof RelationTab) {
            focusedTabId = tabId;
            return;
        }

        logger.warning("Cannot find work item with instance id '", tabId, "'.");
    }

    /**
     * Getter for the lsit of editor tabs.
     * 
     * @return the list of registered editor tabs
     */
    public List<RelationTab> getAllTabs() {
        List<RelationTab> tabs = new ArrayList<RelationTab>();
        String[] ids = super.getElementIds();
        for (String id : ids) {
            WebElement element = super.getElement(id);
            if (element instanceof RelationTab) {
                tabs.add((RelationTab) element);
            }
        }
        return tabs;
    }

    /**
     * Setter for the pageSize.
     * 
     * @param pageSize
     *            The pageSize to set.
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;

        for (RelationTab tab : this.getAllTabs()) {
            tab.getModel().getTableModel().setPageSize(pageSize);
        }
    }

    /**
     * Getter for the pageSize.
     * 
     * @return Returns the pageSize.
     */
    public int getPageSize() {
        return pageSize;
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
    public JsonMap toJson() {
        JsonMap json = new JsonMap();
        JsonList tabList = new JsonList();

        for (RelationTab tab : this.getAllTabs()) {
            if (tab.getModel().isVisible()) {
                tabList.add(tab.toJson());
            }
        }

        json.add(JSON_TABS, tabList);

        RelationTab focus = this.getFocusedTab();
        if (focus != null) {
            json.add(JSON_FOCUS, focus.getId());
        }
        json.add(JSON_SIZE, this.getPageSize());

        return json;
    }

}
