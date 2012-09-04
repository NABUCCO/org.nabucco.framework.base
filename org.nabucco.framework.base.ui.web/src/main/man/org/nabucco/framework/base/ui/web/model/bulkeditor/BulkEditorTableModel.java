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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.bulkeditor.column.BulkEditorColumnModel;
import org.nabucco.framework.base.ui.web.model.common.renderer.WebLabelProvider;
import org.nabucco.framework.base.ui.web.model.common.renderer.WebLabelProviderFactory;
import org.nabucco.framework.base.ui.web.model.table.TableColumn;
import org.nabucco.framework.base.ui.web.model.table.TableModel;
import org.nabucco.framework.base.ui.web.model.table.comparator.TableComparator;

/**
 * BulkEditorTableModel
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class BulkEditorTableModel extends TableModel<Datatype> {

    /** Containt the datatypes with theid instanceids (unique) */
    private Map<String, Datatype> contentMap = new HashMap<String, Datatype>();

    WebLabelProviderFactory<Datatype> labelProviderFactory = new WebLabelProviderFactory<Datatype>();

    /**
     * Internal model that holds the datatypes
     * 
     */
    BulkEditorTableModel() {
    }

    @Override
    public void setContent(List<Datatype> contentList) {
        contentMap.clear();

        for (Datatype datatype : contentList) {
            String instanceid = NabuccoSystem.createUUID();
            contentMap.put(instanceid, datatype);
        }

        super.setContent(contentList);
    }

    /**
     * Append the table body to the JSON map.
     * 
     * @param json
     *            the JSON map to append the table body
     */
    @Override
    protected void appendJsonTableBody(JsonMap json) {

        JsonList jsonRowList = new JsonList();

        int amount = this.getAmount();

        int startIndex = this.getIndex();
        int endIndex = this.getIndex() + amount;

        List<Datatype> currentList = this.getCurrentList();

        for (int rowId = startIndex; rowId < endIndex; rowId++) {

            JsonMap jsonRow = new JsonMap();

            Datatype datatype = currentList.get(rowId);

            JsonList jsonColumnList = new JsonList();
            for (TableColumn column : this.getColumnList()) {
                BulkEditorColumnModel bulkColumn = (BulkEditorColumnModel) column;
                if (bulkColumn.isEditable()) {
                    JsonMap renderedContent = bulkColumn.render(datatype);
                    jsonColumnList.add(renderedContent);
                } else {
                    WebLabelProvider<Datatype> labelProvider = labelProviderFactory.createLabelProvider(datatype);
                    String label = labelProvider.getLabel(column.getPropertyPath(), column.getRenderer());
                    jsonColumnList.add(label);
                }
            }

            jsonRow.add(JSON_COLUMNS, jsonColumnList);

            String instanceidOfDatatype = this.getInstanceidOfDatatype(datatype);
            if (datatype instanceof ComponentRelation<?>) {
                ComponentRelation<?> relation = (ComponentRelation<?>) datatype;
                if (relation.getTarget() != null) {
                    jsonRow.add(JSON_OBJECT_ID, instanceidOfDatatype);
                }
            } else if (datatype instanceof NabuccoDatatype) {
                jsonRow.add(JSON_OBJECT_ID, instanceidOfDatatype);
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
     * Returns the datatype by instance id
     * 
     * @param instanceid
     * @return
     */
    public Datatype getDatatypeByInstanceId(String instanceid) {
        return contentMap.get(instanceid);
    }

    /**
     * Searches the id of the given datatype
     * 
     * @param datatype
     * @return
     */
    private String getInstanceidOfDatatype(Datatype datatype) {
        for (Entry<String, Datatype> entry : contentMap.entrySet()) {
            if (entry.getValue().equals(datatype)) {
                return entry.getKey();
            }
        }

        return null;
    }
}
