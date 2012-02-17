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

import javax.management.relation.Relation;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.ColumnExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.ListButtonExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.EditorRelationExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.dependency.DependencySetExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.ui.web.component.WebComposite;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.common.button.Button;
import org.nabucco.framework.base.ui.web.component.common.button.ListButton;
import org.nabucco.framework.base.ui.web.component.work.list.TableElement;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.control.util.dependency.DependencyController;
import org.nabucco.framework.base.ui.web.model.relation.RelationTabModel;
import org.nabucco.framework.base.ui.web.model.table.TableColumn;
import org.nabucco.framework.base.ui.web.model.table.TableModel;
import org.nabucco.framework.base.ui.web.model.table.renderer.ColumnLayoutType;
import org.nabucco.framework.base.ui.web.model.table.renderer.ColumnRenderer;

/**
 * EditorRelation
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class RelationTab extends WebComposite implements TableElement {

    private static final long serialVersionUID = 1L;

    private static final String JSON_BUTTONS = "buttons";

    private static final String JSON_DOUBLECLICK_ACTION = "doubleclickAction";

    /** Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(RelationTab.class);

    private RelationTabModel model;

    /** The EditorRelation Extension */
    private EditorRelationExtension extension;

    private DependencyController dependencySet;

    /**
     * Creates a new {@link Relation} instance.
     * 
     * @param extension
     *            the editor relation tab extension
     */
    public RelationTab(EditorRelationExtension extension) {
        super(WebElementType.EDITOR_RELATION_TAB, extension);
        if (extension == null) {
            throw new IllegalArgumentException("Cannot create editor relation tab for extension 'null'.");
        }
        this.extension = extension;
    }

    @Override
    public void init() throws ExtensionException {

        TableModel<Datatype> tableModel = new TableModel<Datatype>(null, 5);

        for (ListButtonExtension actionExtension : this.extension.getButtons()) {
            ListButton button = new ListButton(actionExtension);
            this.addElement(button.getId(), button);
            button.init();
        }

        for (ColumnExtension columnExtension : this.extension.getColumns()) {
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

        DependencySetExtension dependencySetExtension = this.extension.getDependencySet();
        this.dependencySet = new DependencyController(dependencySetExtension);
        this.dependencySet.init();

        this.model = new RelationTabModel(this.getId(), this.getProperty(),
                this.getDependencyController(), tableModel);
        
    }

    /**
     * Initialize renderer.
     * 
     * @param columnExtension
     *            the column extension
     * @param column
     *            the column
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
    public void clear() {
        super.clear();

        this.model.init();
    }

    /**
     * Getter for the model.
     * 
     * @return Returns the model.
     */
    @Override
    public TableModel<Datatype> getTableModel() {
        return this.model.getTableModel();
    }



    /**
     * Getter for the model.
     * 
     * @return Returns the model.
     */
    public RelationTabModel getModel() {
        return this.model;
    }

    /**
     * Returns the configured dependency controller
     * 
     * @return dependency controller
     */
    public DependencyController getDependencyController() {
        return this.dependencySet;
    }

    /**
     * Getter for the editor relation tab id.
     * 
     * @return the editor relation tab id
     */
    public String getId() {
        if (this.extension.getIdentifier() == null) {
            return null;
        }

        return this.extension.getIdentifier().getValue();
    }

    /**
     * Getter for the editor relation tab property name.
     * 
     * @return the editor relation tab property name
     */
    public String getProperty() {
        return PropertyLoader.loadProperty(this.extension.getProperty());
    }

    /**
     * Getter for the editor relation tab label.
     * 
     * @return the editor relation tab label
     */
    public String getLabel() {
        return PropertyLoader.loadProperty(this.extension.getLabel());
    }

    /**
     * Getter for the editor relation tab tooltip.
     * 
     * @return the editor relation tab tooltip
     */
    public String getTooltip() {
        return PropertyLoader.loadProperty(this.extension.getTooltip());
    }

    /**
     * Getter for the open action configured to be fires by doubleclick of the item
     * 
     * @return the action to be fired to open item
     */
    public String getDoubleClickAction() {
        return PropertyLoader.loadProperty(this.extension.getDoubleclickAction());
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
        boolean isEditable = this.model.isEditable();
        boolean hasSelectedValue = this.model.hasSelectedValue();

        List<ListButton> buttonList = new ArrayList<ListButton>();
        for (WebElement child : super.getElements()) {
            if (child.getType() == WebElementType.BUTTON) {
                ListButton button = (ListButton) child;
                button.updateStatus(hasSelectedValue, isEditable);
                buttonList.add(button);
            }
        }
        return buttonList;
    }



    @Override
    public JsonElement toJson() {
        JsonMap json = new JsonMap();
        json.add(JSON_ID, this.getId());
        json.add(JSON_LABEL, this.getLabel());
        json.add(JSON_TOOLTIP, this.getTooltip());
        json.add(JSON_DOUBLECLICK_ACTION, this.getDoubleClickAction());

        json.add(JSON_MODEL, this.model.toJson());

        JsonList buttonList = new JsonList();
        for (Button button : this.getAllButtons()) {
            buttonList.add(button.toJson());
        }
        json.add(JSON_BUTTONS, buttonList);

        return json;
    }

}
