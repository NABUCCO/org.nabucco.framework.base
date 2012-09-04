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
package org.nabucco.framework.base.ui.web.model.editor.control.relation.picker.delegates;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.property.DatatypeProperty;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.editor.util.parser.ControlParserException;
import org.nabucco.framework.base.ui.web.model.table.TableModel;

/**
 * Delegate for datatype relations.
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class DatatypeRelationDelegate extends RelationDelegateSupport<DatatypeProperty, Datatype> {

    /**
     * Creates a new {@link DatatypeRelationDelegate} instance.
     * 
     * @param tableModel
     *            the table model
     */
    public DatatypeRelationDelegate(TableModel<Datatype> tableModel) {
        super(tableModel);
    }

    @Override
    public Datatype parse(DatatypeProperty property, String rowId, boolean multipleSelection)
            throws ControlParserException {
        if (rowId == null || rowId.isEmpty()) {
            return null;
        }

        String[] rowIds = SPLIT_PATTERN.split(rowId);

        if (!multipleSelection && rowIds.length > 1) {
            throw new ControlParserException(
                    "Error by parsing of picker control value. There is a multiple value sent, but only sigle value is allowed for the bound datatype.");
        }

        Datatype element = super.getDatatypeById(rowId);

        if (element == null) {
            throw new IllegalStateException("Element with id '" + rowId + "' does not exist in table.");
        }

        return element;
    }

    @Override
    public JsonElement toJson(Datatype value, String displayPath) {
        JsonList valueList = new JsonList();

        String displayValue;

        if (value != null) {
            displayValue = super.getLabel(value, displayPath);
        } else {
            displayValue = "";
        }

        JsonMap map = new JsonMap();
        map.add(JSON_DISPLAY_VALUE, displayValue);
        map.add(JSON_ID, super.getId(value));

        valueList.add(map);

        return valueList;
    }

    @Override
    public String toString(Datatype value, String displayPath) {
        StringBuilder valueList = new StringBuilder();
        if (value == null) {
            return valueList.toString();
        }

        String displayValue = this.getLabel(value, displayPath);
        valueList.append(displayValue);

        return valueList.toString();
    }

}
