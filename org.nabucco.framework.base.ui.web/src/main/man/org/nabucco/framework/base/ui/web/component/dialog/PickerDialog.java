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
package org.nabucco.framework.base.ui.web.component.dialog;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.queryfilter.QueryFilterExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.ButtonExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.ColumnExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.filter.FilterReferenceExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.PickerDialogExtension;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.ui.web.component.WebComposite;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.common.button.Button;
import org.nabucco.framework.base.ui.web.component.work.list.FilterItem;
import org.nabucco.framework.base.ui.web.component.work.list.TableElement;
import org.nabucco.framework.base.ui.web.component.work.util.QueryFilterExtensionUtil;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.table.TableColumn;
import org.nabucco.framework.base.ui.web.model.table.TableModel;
import org.nabucco.framework.base.ui.web.model.table.renderer.ColumnLayoutType;
import org.nabucco.framework.base.ui.web.model.table.renderer.ColumnRenderer;

/**
 * PickerDialog
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class PickerDialog extends WebComposite implements TableElement {

    private static final long serialVersionUID = 1L;

    private static final String JSON_MESSAGE = "message";

    private static final String JSON_TITLE = "title";

    private static final String JSON_INSTANCE_ID = "instanceId";

    private static final String JSON_BUTTONS = "buttons";

    private static final String JSON_PICKER_TYPE = "pickerType";

    private static final String JSON_SELECTED_VALUE = "selectedValue";

    private static final String JSON_MULTI_SELECTION = "multiSelection";

    private static final String JSON_ACTIVE_FILTER = "activeFilter";

    private static final String JSON_FILTERS = "filters";

    private static final String JSON_DOUBLECLICK_ACTION = "doubleclickAction";

    /** Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(PickerDialog.class);

    /** The Table Model */
    private TableModel<Datatype> model;

    private PickerDialogExtension extension;

    private Map<String, FilterItem> filterMap = new LinkedHashMap<String, FilterItem>();

    private String activeFilterId;

    /** The unique instance id of the picker dialog */
    private String instanceId;

    /** The value that is selected by opening */
    private String selectedValue;

    /** Indicates if the picker dialog can select more than one value */
    private boolean multiSelection = false;

    /**
     * Creates a new {@link PickerDialog} instance.
     * 
     * @param pickerId
     *            the id of the picker
     * @param instanceId
     *            the id of the ?
     */
    public PickerDialog(PickerDialogExtension extension, String instanceId) {
        super(WebElementType.PICKERDIALOG, extension);

        this.instanceId = instanceId;
        this.extension = extension;
    }

    @Override
    public void init() throws ExtensionException {

        for (ButtonExtension actionExtension : this.getExtension().getButtons()) {
            Button button = new Button(actionExtension);
            super.addElement(button.getId(), button);
            button.init();
        }

        this.model = new TableModel<Datatype>();

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

            this.model.addColumn(column);
        }
    }

    /**
     * Initialize renderer
     * 
     * @param columnExtension
     * @param column
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

    /**
     * Getter for the model.
     * 
     * @return Returns the model.
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T extends Datatype> TableModel<T> getTableModel() {
        return (TableModel<T>) this.model;
    }

    /**
     * Getter for all configured editor buttons.
     * 
     * @return the list of buttons
     */
    public List<Button> getAllButtons() {
        List<Button> buttonList = new ArrayList<Button>();
        for (WebElement child : super.getElements()) {
            if (child.getType() == WebElementType.BUTTON) {
                buttonList.add((Button) child);
            }
        }
        return buttonList;
    }

    /**
     * Getter for the extension.
     * 
     * @return Returns the extension.
     */
    private PickerDialogExtension getExtension() {
        return this.extension;
    }

    /**
     * Return the type of the Picker dialog
     * 
     * @return
     */
    public String getPickerType() {
        return PropertyLoader.loadProperty(this.getExtension().getPickerType());
    }

    /**
     * Getter for the title.
     * 
     * @return Returns the title.
     */
    public String getTitle() {
        return PropertyLoader.loadProperty(this.getExtension().getTitle());
    }

    /**
     * Getter for the message.
     * 
     * @return Returns the message.
     */
    public String getMessage() {
        return PropertyLoader.loadProperty(this.getExtension().getMessage());
    }

    /**
     * Getter for the doubleclick action
     * 
     * @return doubleclick action
     */
    public String getDoubleclickAction() {
        return PropertyLoader.loadProperty(this.getExtension().getDoubleclickAction());
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
     * Setter for the selectedValue.
     * 
     * @param selectedValue
     *            The selectedValue to set.
     */
    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    /**
     * Getter for the selectedValue.
     * 
     * @return Returns the selectedValue.
     */
    public String getSelectedValue() {
        return this.selectedValue;
    }

    /**
     * Getter for the id.
     * 
     * @return Returns the id.
     */
    public String getId() {
        return PropertyLoader.loadProperty(this.extension.getIdentifier());
    }

    /**
     * Getter for the multiSelection.
     * 
     * @return Returns the multiSelection.
     */
    public boolean isMultiSelection() {
        return this.multiSelection;
    }

    /**
     * Returns the list of filters configured for the table
     * 
     * @return list of filter items
     */
    public List<FilterItem> getFilters() {

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
     * Setter selected filter id. Validates input.
     * 
     * @param filterId
     *            the id of the selected filter
     */
    public void setActiveFilterId(String filterId) {
        if (filterId == null) {
            throw new IllegalArgumentException("Cannot set active filter. The filterid is 'null'");
        }

        // Validate if the filter exists
        for (FilterItem item : this.getFilters()) {
            if (item.getRefId().equals(filterId)) {
                this.activeFilterId = filterId;
                break;
            }
        }
    }

    /**
     * Getter for the active filter or default filter if no custom filter selected
     * 
     * @return filter id or null if no filter exist
     */
    public String getActiveFilterId() {
        if (this.activeFilterId != null) {
            return this.activeFilterId;
        }

        // Search for default filter
        List<FilterItem> filters = this.getFilters();

        for (FilterItem item : filters) {
            if (item.isDefault()) {
                this.setActiveFilterId(item.getRefId());
                break;
            }
        }

        // If no default filter set, take simply the first one if any
        if (this.activeFilterId == null && !filters.isEmpty()) {
            this.setActiveFilterId(filters.get(0).getRefId());
        }

        return this.activeFilterId;
    }

    @Override
    public JsonMap toJson() {
        JsonMap json = new JsonMap();

        json.add(JSON_ID, this.getId());
        json.add(JSON_INSTANCE_ID, this.getInstanceId());

        json.add(JSON_DOUBLECLICK_ACTION, this.getDoubleclickAction());

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
        json.add(JSON_ACTIVE_FILTER, this.activeFilterId);

        json.add(JSON_PICKER_TYPE, this.getPickerType());
        json.add(JSON_MULTI_SELECTION, this.isMultiSelection());

        json.add(JSON_TITLE, this.getTitle());
        json.add(JSON_MESSAGE, this.getMessage());

        /** The selected value to show */
        json.add(JSON_SELECTED_VALUE, this.getSelectedValue());

        /** The values to show */
        json.add(JSON_MODEL, this.model.toJson());
        return json;
    }

}
