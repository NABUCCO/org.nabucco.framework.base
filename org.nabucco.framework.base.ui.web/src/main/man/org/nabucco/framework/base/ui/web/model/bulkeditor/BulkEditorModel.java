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
package org.nabucco.framework.base.ui.web.model.bulkeditor;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.bulkeditor.column.BulkEditorColumnModel;
import org.nabucco.framework.base.ui.web.model.editor.control.ControlModel;
import org.nabucco.framework.base.ui.web.model.table.TableModel;
import org.nabucco.framework.base.ui.web.model.work.DatatypeDirtyVisitor;
import org.nabucco.framework.base.ui.web.model.work.WorkItemModel;

/**
 * BulkEditorModel
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class BulkEditorModel extends WorkItemModel {

    public static final String CONTENT_PROPERTY = "content";

    private static final String JSON_TABLE = "table";

    private BulkEditorTableModel tableModel;

    private List<String> selectedObjectList = new ArrayList<String>();

    private boolean refreshNeeded;

    private Map<String, BulkEditorColumnModel> columnMap = new HashMap<String, BulkEditorColumnModel>();

    private NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(BulkEditorModel.class);

    private boolean isDirty;

    /**
     * Creates a new {@link BulkEditorModel} instance.
     * 
     * @param label
     *            the label to show
     * @param tooltip
     *            the tootip to display
     * @param image
     *            the image to use as icon
     */
    public BulkEditorModel(String label, String tooltip, String image) {
        super(label, tooltip, image);
        tableModel = new BulkEditorTableModel();
    }

    @Override
    public void init() {
        tableModel.init();
    }

    /**
     * Getter for the modelRefreshNeeded.
     * 
     * @return Returns the modelRefreshNeeded.
     */
    public boolean isModelRefreshNeeded() {
        boolean actualState = this.isDirty();
        if (actualState != isDirty) {
            isDirty = actualState;
            return true;
        }

        return false;
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
     * Setter for the content
     * 
     * @param contentList
     *            the datatypes to be modified bulk
     */
    public void setContent(List<Datatype> contentList) {
        if (contentList == null) {
            throw new IllegalArgumentException("Cannot set datatype list. New list is 'null'");
        }

        // List<Datatype> bulkContentList = new ArrayList<Datatype>();

        // for (Datatype datatype : contentList) {
        // if (datatype instanceof ComponentRelation<?>) {
        // ComponentRelation<?> cr = (ComponentRelation<?>) datatype;
        // bulkContentList.add(cr.getTarget());
        // } else {
        // bulkContentList.add(datatype);
        // }
        // }

        tableModel.setContent(contentList);
    }

    /**
     * Adds a column to the model
     * 
     * @param column
     *            column to be added
     */
    public void addColumn(BulkEditorColumnModel column) {
        if (column == null) {
            throw new IllegalArgumentException("Cannot add column. Column is 'null'");
        }

        this.tableModel.addColumn(column);
        columnMap.put(column.getId(), column);

        // Add notification by changes
        ControlModel<?> controlModel = column.getControlModel(null);
        controlModel.addPropertyChangeListener(controlModel.getPropertyPath(), this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        super.propertyChange(event);

        if (event.getSource() != this) {

            // Delegate Property Change Event
            super.updateProperty(event.getPropertyName(), event.getOldValue(), event.getNewValue());

        }
    }

    /**
     * Returns the column instance by given id
     * 
     * @param id
     *            the id of the column
     * @return the column instance with the id or null if not found
     */
    public BulkEditorColumnModel getColumnById(String id) {
        BulkEditorColumnModel bulkColumn = columnMap.get(id);

        return bulkColumn;
    }

    /**
     * Selects the row
     * 
     * @param row
     *            the datatype id to select
     */
    public void selectRow(String row) {
        boolean before = this.hasSelectedValue();
        if (!selectedObjectList.contains(row)) {
            selectedObjectList.add(row);
        }
        boolean after = this.hasSelectedValue();

        if (before != after) {
            this.refreshNeeded = true;
        }
    }

    /**
     * Deselects a row
     * 
     * @param row
     *            the datatype id to deselect
     */
    public void deselectRow(String row) {
        boolean before = this.hasSelectedValue();

        if (selectedObjectList.contains(row)) {
            selectedObjectList.remove(row);
        }
        boolean after = this.hasSelectedValue();

        if (before != after) {
            this.refreshNeeded = true;
        }
    }

    /**
     * returns the list of selected id's
     * 
     * @return
     */
    public List<String> getSelectedIds() {
        return selectedObjectList;
    }

    /**
     * Sets the new value to the current row element to property bound on the given column id
     * 
     * @param columnId
     *            the id of the column
     * @param datatypeInstanceId
     *            the id of the object
     * @param value
     *            the value to be set
     */
    public void setValue(String columnId, String datatypeInstanceId, String value) {
        if (columnId == null || columnId.isEmpty()) {
            return;
        }
        if (datatypeInstanceId == null || datatypeInstanceId.isEmpty()) {
            return;
        }
        if (value == null) {
            return;
        }

        Datatype datatype = this.tableModel.getDatatypeByInstanceId(datatypeInstanceId);

        if (datatype == null) {
            return;
        }

        BulkEditorColumnModel column = this.getColumnById(columnId);

        if (column != null) {
            column.applyNewValue(datatype, value);
        }
    }

    /**
     * Getter for the datatype with given id
     * 
     * @param id
     *            the id for search
     * @return found datatype
     */
    public Datatype getDatatype(String id) {
        Datatype retVal = this.tableModel.getDatatypeByInstanceId(id);

        return retVal;
    }

    @Override
    public boolean isDirty() {
        if (tableModel.getContent() == null || tableModel.getContent().isEmpty()) {
            return false;
        }

        DatatypeDirtyVisitor visitor = new DatatypeDirtyVisitor();

        try {
            for (Datatype datatype : tableModel.getContent()) {
                datatype.accept(visitor);
                if (visitor.isDirty() == true) {
                    return true;
                }
            }
        } catch (VisitorException ve) {
            logger.error(ve, "Error checking datatype state.");
        }

        return super.isDirty();
    }

    /**
     * Enables or disables paging functionality for the editor
     * 
     * @param pagingActive
     */
    public void setPagingActive(boolean pagingActive) {
        if (!pagingActive) {
            tableModel.setPageSize(9999);
        }
    }

    /**
     * Sets the size of the page to be shown
     * 
     * @param pagingSize
     */
    public void setPagingSize(int pagingSize) {
        tableModel.setPageSize(pagingSize);
    }

    /**
     * Returns true if minimum one value in the table is selected
     * 
     * @return true if selected
     */
    public boolean hasSelectedValue() {
        return selectedObjectList.isEmpty() == false;
    }

    /**
     * 
     * @return
     */
    public boolean isRefreshNeeded() {
        return this.refreshNeeded;
    }

    /**
     * Validate the given datatype according to the current configuration
     * 
     * @param datatype
     *            datatype to be validated
     */
    public boolean validate(Datatype datatype) {

        for (BulkEditorColumnModel columnModel : columnMap.values()) {
            ControlModel<?> controlModel = columnModel.getControlModel(datatype);

            controlModel.setValidating(true);
            if (!controlModel.validate().isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public JsonElement toJson() {
        JsonMap json = (JsonMap) super.toJson();
        JsonList selectedObjects = new JsonList();
        for (String object : this.selectedObjectList) {
            selectedObjects.add(object);
        }
        json.add(JSON_VALUE, selectedObjects);

        if (refreshNeeded == false) {
            json.add(JSON_TABLE, tableModel.toJson());
        }
        this.refreshNeeded = false;
        this.isDirty = this.isDirty();
        return json;
    }
}
