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
package org.nabucco.framework.base.ui.web.model.list;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.ui.web.component.work.list.FilterItem;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.table.TableModel;
import org.nabucco.framework.base.ui.web.model.work.WorkItemModel;

/**
 * ListModel
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ListModel extends WorkItemModel {

    private static final String JSON_ACTIVE_FILTER = "activeFilter";

    private static final String JSON_TABLE = "table";

    private String activeFilterId;

    private TableModel<Datatype> tableModel;

    private List<FilterItem> filterList;

    /** Actually selected value */
    private String selectedValue = null;

    /** Indicates that the state of the model changed and the ui refresh needed */
    private boolean refreshNeeded = false;

    /**
     * 
     * Creates a new {@link ListModel} instance.
     * 
     * @param label
     *            the label of the list
     * @param tooltip
     *            the tooltip
     * @param image
     *            the icon to show
     * @param tableModel
     *            the table model instance to use
     */
    public ListModel(String label, String tooltip, String image, TableModel<Datatype> tableModel) {
        super(label, tooltip, image);

        if (tableModel == null) {
            throw new IllegalArgumentException("Cannot create List Model. Table model is 'null'");
        }

        this.tableModel = tableModel;
    }

    @Override
    public void init() {

    }

    /**
     * Getter for the model.
     * 
     * @return Returns the model.
     */
    @SuppressWarnings("unchecked")
    public <T extends Datatype> TableModel<T> getTableModel() {
        return (TableModel<T>) this.tableModel;
    }

    /**
     * Setter for the filterList.
     * 
     * @param filterList
     *            The filterList to set.
     */
    public void setFilterList(List<FilterItem> filterList) {
        this.filterList = filterList;
    }

    /**
     * Getter for the filterList.
     * 
     * @return Returns the filterList.
     */
    public List<FilterItem> getFilterList() {
        return this.filterList;
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
        for (FilterItem item : this.getFilterList()) {
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
        List<FilterItem> filters = this.getFilterList();

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

    /**
     * Setter for the selectedValue.
     * 
     * @param selectedValue
     *            The selectedValue to set.
     */
    public void setSelectedValue(String selectedValue) {

        boolean before = this.hasSelectedValue();
        this.selectedValue = selectedValue;
        boolean after = this.hasSelectedValue();

        if (before != after) {
            this.refreshNeeded = true;
        }

    }

    /**
     * Returns true if any value is selected
     * 
     * @return true if selected, false if not
     */
    public boolean hasSelectedValue() {
        return this.selectedValue != null;
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
     * Getter for the isRefreshNeeded.
     * 
     * @return Returns the isRefreshNeeded.
     */
    public boolean isRefreshNeeded() {
        return this.refreshNeeded;
    }

    @Override
    public JsonElement toJson() {
        JsonMap json = (JsonMap) super.toJson();

        String activeFilterId = this.getActiveFilterId();

        json.add(JSON_ACTIVE_FILTER, activeFilterId);

        json.add(JSON_TABLE, this.getTableModel().toJson());

        json.add(JSON_VALUE, this.selectedValue);

        this.refreshNeeded = false;

        return json;
    }

}
