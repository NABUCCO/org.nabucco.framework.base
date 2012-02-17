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
package org.nabucco.framework.base.ui.web.model.table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.WebModel;
import org.nabucco.framework.base.ui.web.model.common.renderer.WebLabelProvider;
import org.nabucco.framework.base.ui.web.model.common.renderer.WebLabelProviderFactory;
import org.nabucco.framework.base.ui.web.model.table.comparator.TableComparator;
import org.nabucco.framework.base.ui.web.model.table.filter.TableFilter;

/**
 * Web Model of a table for the NABUCCO User Interface.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class TableModel<T extends Datatype> extends WebModel {

    /** Default Amount of Rows of a Table Page */
    private static final int DEFAULT_PAGE_SIZE = 15;

    /**
     * The property name that can be used to bound on the content of the table model if no property
     * defined. Change events will be fired with the content as a new value
     */
    public static final String CONTENT_PROPERTY = "content";

    /** Label for counter seperation */
    private static final String LABEL_COUNTER_SEPERATOR = "von";

    private static final String JSON_HEAD = "head";

    private static final String JSON_SIZE = "size";

    private static final String JSON_MENU = "menu";

    private static final String JSON_PREVIOUS = "previous";

    private static final String JSON_NEXT = "next";

    private static final String JSON_HEAD_COLUMNS = "headColumns";

    private static final String JSON_ROWS = "rows";

    private static final String JSON_COLUMNS = "cols";

    private static final String JSON_OBJECT_ID = "objectId";

    private static final String JSON_ROW_ID = "rowId";

    private static final String JSON_COUNTER = "counter";

    private static final String JSON_SORT = "sort";

    private static final String JSON_ORDER = "order";

    private static final String ORDER_ASCENDING = "ASC";

    private static final String ORDER_DESCENDING = "DSC";

    /** The Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(TableModel.class);

    /** The table columns. */
    private List<TableColumn> columnList;

    /** The original list */
    private List<T> originalList;

    /** The current view of the original list */
    private List<T> currentView;

    /** List containing all filtered elemnts from the original list */
    private List<T> filteredList;

    /** List containing all filtered elements in a specific order */
    private List<T> sortedList;

    /** The comparator to sort the table, may be nested if needed */
    private TableComparator comparator;

    /** The label provider factory for creating label providers. */
    private WebLabelProviderFactory<T> labelProviderFactory = new WebLabelProviderFactory<T>();

    /** The filter to limit the table for the specified criterion. */
    private TableFilter filter;

    /** The actual index of the first visible element */
    private int index;

    /** The page size of the table */
    private int pageSize;

    /** The amount of table entries */
    private int tableSize;

    /** The property this table displays */
    private String propertyName;

    private boolean jsonHead = true;

    private boolean jsonMenu = true;

    private boolean jsonTableHead = true;

    private boolean jsonTableBody = true;

    /**
     * Creates a new {@link TableModel} instance.
     */
    public TableModel() {
        this(null, null, DEFAULT_PAGE_SIZE);
    }

    /**
     * Creates a new {@link TableModel} instance.
     * 
     * @param originalList
     *            the list of datatypes displayed in the table
     */
    public TableModel(List<T> contentList) {
        this(contentList, null, DEFAULT_PAGE_SIZE);
    }

    /**
     * Creates a new {@link TableModel} instance.
     * 
     * @param originalList
     *            the list of datatypes displayed in the table
     * @param pageSize
     *            the table page size
     */
    public TableModel(List<T> contentList, int pageSize) {
        this(contentList, null, pageSize);
    }

    /**
     * Creates a new {@link TableModel} instance.
     * 
     * @param originalList
     *            the list of datatypes displayed in the table
     * @param columnList
     *            the table column list
     */
    public TableModel(List<T> contentList, List<TableColumn> columnList) {
        this(contentList, columnList, DEFAULT_PAGE_SIZE);
    }

    /**
     * Creates a new {@link TableModel} instance.
     * 
     * @param originalList
     *            the list of datatypes displayed in the table
     * @param columnList
     *            the table column list
     * @param pageSize
     *            the table page size
     */
    public TableModel(List<T> contentList, List<TableColumn> columnList, int pageSize) {
        if (contentList == null) {
            contentList = Collections.emptyList();
        }
        if (columnList == null) {
            columnList = Collections.emptyList();
        }
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        this.originalList = contentList;
        // this.originalList = new ArrayList<T>(contentList);
        this.columnList = new ArrayList<TableColumn>(columnList);
        this.pageSize = pageSize;

        this.refresh();
    }

    /**
     * Getter for a clone of the originalList.
     * 
     * @return Returns the content.
     */
    public List<T> getContent() {
        return new ArrayList<T>(this.originalList);
    }

    /**
     * Setter for the table content list.
     * 
     * @param originalList
     *            the content list
     */
    public void setContent(List<T> contentList) {
        if (contentList == null) {
            contentList = Collections.emptyList();
        }

        this.originalList = contentList;

        this.refresh();
    }

    /**
     * Getter for the propertyName.
     * 
     * @return Returns the propertyName.
     */
    public String getPropertyName() {
        return this.propertyName;
    }

    /**
     * Setter for the propertyName.
     * 
     * @param propertyName
     *            The propertyName to set.
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * Refresh the table model.
     */
    private void refresh() {

        int oldSize = this.tableSize;

        try {
            int tableSize = this.originalList.size();
            this.tableSize = (tableSize > 0) ? tableSize : 0;
        } catch (Exception e) {
            logger.error(e, "Error determing table size. Reset table size to default '0'.");
            this.tableSize = 0;
        }

        if (this.propertyName != null && !this.propertyName.isEmpty()) {
            super.updateProperty(this.propertyName, oldSize, this.originalList.size());
        }

        // Inform all who listen for the content elements of the table model
        super.updateProperty(CONTENT_PROPERTY, null, new ArrayList<T>(this.originalList));

        this.setIndex(this.index);

        this.filter();
    }

    @Override
    public void init() {
        this.jsonHead = true;
        this.jsonMenu = true;
        this.jsonTableHead = true;
        this.jsonTableBody = true;
    }

    /**
     * Changes the index position to the specified index. Therefore the content provider must
     * deliver the appropriate entries. Finally the filtering and sorting is triggered.
     * 
     * @param index
     *            the new index position
     */
    private void setIndex(int index) {
        this.index = index;

        int amount = this.getAmount();

        try {
            this.currentView = this.originalList.subList(this.index, this.index + amount);
        } catch (Exception e) {
            logger.error(e, "Error derming table content for index '", index, "'.");
            this.currentView = new ArrayList<T>(amount);
        }

        if (this.currentView.size() < amount) {
            throw new IllegalStateException("Not the expected amount of entries provided, expected '"
                    + amount + "' but was '" + this.currentView.size() + "'.");
        }

        this.jsonTableBody = true;
    }

    /**
     * Calculate the amount of current entries.
     * 
     * @return the amount of currently displayed entries.
     */
    private int getAmount() {
        if (this.index + this.pageSize >= this.tableSize) {
            return this.tableSize - this.index;
        }
        return this.pageSize;
    }

    /**
     * Add a column to the table model.
     * 
     * @param column
     *            the column to add
     */
    public void addColumn(TableColumn column) {
        this.columnList.add(column);
    }

    /**
     * Sets the index to the next page
     */
    public void nextPage() {
        int newIndex = this.index + this.pageSize;
        if (newIndex < this.tableSize) {
            this.setIndex(newIndex);
        } else {
            this.setIndex(this.tableSize);
        }
    }

    /**
     * Sets the index to the prevois page or 0 if there is no previous page
     */
    public void previousPage() {
        int newIndex = this.index - this.pageSize;
        if (newIndex >= 0) {
            this.setIndex(newIndex);
        } else {
            this.setIndex(0);
        }
    }

    /**
     * Sets the index to 0.
     */
    public void firstPage() {
        this.setIndex(0);
    }

    /**
     * Sets the index to size - pagesize
     */
    public void lastPage() {
        int newIndex = this.tableSize - (this.tableSize % this.pageSize);
        if (newIndex < this.tableSize && newIndex > 0) {
            this.setIndex(newIndex);
        }
    }

    /**
     * Getter for the index.
     * 
     * @return Returns the index.
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * Getter for the page size.
     * 
     * @return Returns the page size.
     */
    public int getPageSize() {
        return this.pageSize;
    }

    /**
     * Getter for the whole table size.
     * 
     * @return Returns the table size.
     */
    public int getTableSize() {
        return this.tableSize;
    }

    /**
     * Getter for an unmodifiable list of the current view.
     * 
     * @return Returns the sortedList.
     */
    public List<T> getCurrentList() {
        return Collections.unmodifiableList(this.sortedList);
    }

    /**
     * Getter for the labelProviderFactory.
     * 
     * @return Returns the labelProviderFactory.
     */
    public WebLabelProviderFactory<T> getLabelProviderFactory() {
        return this.labelProviderFactory;
    }

    /**
     * Setter for the labelProviderFactory.
     * 
     * @param labelProviderFactory
     *            The labelProviderFactory to set.
     */
    public void setLabelProviderFactory(WebLabelProviderFactory<T> labelProviderFactory) {
        this.labelProviderFactory = labelProviderFactory;
    }

    /**
     * Getter for the filter.
     * 
     * @return Returns the filter.
     */
    public TableFilter getFilter() {
        return this.filter;
    }

    /**
     * Setter for the table filter.
     * 
     * @param filter
     *            The filter to set.
     */
    public void setFilter(TableFilter filter) {
        this.filter = filter;

        this.filter();
    }

    /**
     * Getter for the comparator.
     * 
     * @return Returns the comparator.
     */
    public TableComparator getComparator() {
        return this.comparator;
    }

    /**
     * Setter for the table comparator.
     * 
     * @param comparator
     *            The comparator to set.
     */
    public void setComparator(TableComparator comparator) {
        this.comparator = comparator;
    }

    /**
     * Sort the table by the given column id.
     * 
     * @param columnId
     *            the id of the column to sorty by
     */
    public void sort(String columnId) {
        for (TableColumn column : this.columnList) {

            if (column.getId().equals(columnId)) {
                TableComparator comparator = new TableComparator(column.getProperty());

                if (comparator.equals(this.getComparator())) {
                    this.getComparator().reverse();
                } else {
                    this.setComparator(comparator);
                }

                break;
            }
        }

        this.jsonTableHead = true;
        this.jsonTableBody = true;

        this.sort();
    }

    /**
     * Filters the original list and triggers a re-sort of the list.
     */
    private void filter() {
        if (this.filter == null) {
            this.filteredList = new ArrayList<T>(this.originalList);
        } else {
            this.filteredList = new ArrayList<T>();
            for (T datatype : this.originalList) {
                if (this.filter.accept(datatype)) {
                    this.filteredList.add(datatype);
                }
            }
        }

        this.sort();
    }

    /**
     * Re-sorts the sorted list.
     */
    private void sort() {

        this.sortedList = new ArrayList<T>(this.filteredList);

        if (this.comparator != null) {
            Collections.sort(this.sortedList, this.comparator);
        }
    }

    @Override
    public JsonElement toJson() {

        JsonMap json = new JsonMap();

        // Head

        if (this.jsonHead) {
            this.appendJsonHead(json);

            // Commented because of relation tabs refreshing
            // this.jsonHead = false;
        }

        // Menu

        if (this.jsonMenu) {
            this.appendJsonMenu(json);
            this.jsonMenu = false;
        }

        // Table Head

        if (this.jsonTableHead) {
            this.appendJsonTableHead(json);
            this.jsonTableHead = false;
        }

        // Table Body

        if (this.jsonTableBody) {
            this.appendJsonTableBody(json);
            this.jsonTableBody = false;
        }

        return json;
    }

    /**
     * Check whether the table collection is dirty or not.
     * 
     * @return <b>true</b> if the table model is dirty, <b>false</b> if not
     */
    public boolean isDirty() {
        if (this.tableSize != this.originalList.size()) {
            return true;
        }

        return false;
    }

    /**
     * Append the head to the JSON map.
     * 
     * @param json
     *            the JSON map to append the head
     */
    private void appendJsonHead(JsonMap json) {
        JsonMap jsonHead = new JsonMap();
        jsonHead.add(JSON_SIZE, this.tableSize);
        json.add(JSON_HEAD, jsonHead);

        json.add(JSON_COUNTER, this.createCounter());

        if (this.index > 0) {
            json.add(JSON_PREVIOUS, Boolean.TRUE);
        }

        if (this.index + this.pageSize < this.tableSize) {
            json.add(JSON_NEXT, Boolean.TRUE);
        }
    }

    /**
     * Append the table menu to the JSON map.
     * 
     * @param json
     *            the JSON map to append the table menu
     */
    private void appendJsonMenu(JsonMap json) {
        JsonMap jsonMenu = new JsonMap();
        json.add(JSON_MENU, jsonMenu);
    }

    /**
     * Append the table head to the JSON map.
     * 
     * @param json
     *            the JSON map to append the table head
     */
    private void appendJsonTableHead(JsonMap json) {
        JsonList columnList = new JsonList();
        for (TableColumn column : this.columnList) {
            columnList.add(column.toJson());
        }
        json.add(JSON_HEAD_COLUMNS, columnList);
    }

    /**
     * Append the table body to the JSON map.
     * 
     * @param json
     *            the JSON map to append the table body
     */
    private void appendJsonTableBody(JsonMap json) {

        JsonList jsonRowList = new JsonList();

        int amount = this.getAmount();

        int startIndex = this.index;
        int endIndex = this.index + amount;

        for (int rowId = startIndex; rowId < endIndex; rowId++) {

            JsonMap jsonRow = new JsonMap();

            T datatype = this.sortedList.get(rowId);
            WebLabelProvider<T> labelProvider = this.labelProviderFactory.createLabelProvider(datatype);

            JsonList jsonColumnList = new JsonList();
            for (TableColumn column : this.columnList) {
                String label = labelProvider.getLabel(column.getProperty(), column.getRenderer());
                jsonColumnList.add(label);
            }

            jsonRow.add(JSON_COLUMNS, jsonColumnList);

            if (datatype instanceof ComponentRelation<?>) {
                ComponentRelation<?> relation = (ComponentRelation<?>) datatype;
                if (relation.getTarget() != null) {
                    jsonRow.add(JSON_OBJECT_ID, relation.getTarget().getId());
                }
            } else if (datatype instanceof NabuccoDatatype) {
                jsonRow.add(JSON_OBJECT_ID, ((NabuccoDatatype) datatype).getId());
            }

            jsonRow.add(JSON_ROW_ID, rowId);

            jsonRowList.add(jsonRow);
        }

        json.add(JSON_ROWS, jsonRowList);



        TableComparator comparator = this.getComparator();
        if (comparator != null) {
            JsonMap sort = new JsonMap();

            TableColumn column = this.getColumnByProperty(comparator.getProperty());

            if (column != null) {
                sort.add(JSON_ID, column.getId());
                sort.add(JSON_ORDER, comparator.isReverse() ? ORDER_DESCENDING : ORDER_ASCENDING);
                json.add(JSON_SORT, sort);
            }
        }
    }

    /**
     * Create the counter string for the table model.
     * 
     * @return the current counter strin
     */
    private String createCounter() {
        StringBuilder counter = new StringBuilder();
        counter.append(this.getTableSize() == 0 ? 0 : this.index + 1);
        counter.append(" - ");
        counter.append(this.index + this.getAmount());
        counter.append(" ").append(LABEL_COUNTER_SEPERATOR).append(" ");
        counter.append(this.getTableSize());
        return counter.toString();
    }

    /**
     * Getter for the column with the given property name.
     * 
     * @param propertyName
     *            the property name to find the column for
     * 
     * @return the table column
     */
    private TableColumn getColumnByProperty(String propertyName) {
        for (TableColumn column : this.columnList) {
            if (column.getProperty().equals(propertyName)) {
                return column;
            }
        }
        return null;
    }

    /**
     * Returns a datatype from the current content with given row id, or null if not found.
     * 
     * @param id
     *            id of the row
     * 
     * @return T datatype instance of the specified row
     */
    public T getDatatypeByRowId(int id) {
        if (id >= this.sortedList.size()) {
            return null;
        }
        return this.sortedList.get(id);
    }

    /**
     * Returns a datatype from the current content with given object id, or null if not found.
     * 
     * @param id
     *            id of the element
     * 
     * @return T datatype instance
     */
    public T getDatatypeByObjectId(long id) {

        // TODO: Validate sorted vs. original list.
        for (T element : this.sortedList) {

            if (!(element instanceof NabuccoDatatype)) {
                continue;
            }

            if (element instanceof ComponentRelation<?>) {
                if (((ComponentRelation<?>) element).getTarget().getId().equals(id)) {
                    return element;
                }
            } else {
                if (((NabuccoDatatype) element).getId().equals(id)) {
                    return element;
                }
            }
        }

        return null;
    }

}
