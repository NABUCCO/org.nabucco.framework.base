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
package org.nabucco.framework.base.ui.web.model.control.relation.picker.delegates;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollection;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionType;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.property.CollectionProperty;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.control.util.parser.ControlParserException;
import org.nabucco.framework.base.ui.web.model.table.TableModel;

/**
 * Delegate for datatype collections.
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class CollectionRelationDelegate extends
        RelationDelegateSupport<CollectionProperty, NabuccoCollection<Datatype>> {

    /**
     * Creates a new {@link CollectionRelationDelegate} instance.
     * 
     * @param tableModel
     *            the table model
     */
    public CollectionRelationDelegate(TableModel<Datatype> tableModel) {
        super(tableModel);
    }

    @Override
    public NabuccoCollection<Datatype> parse(CollectionProperty property, String stringValue, boolean multipleSelection)
            throws ControlParserException {

        if (property.getInstance().getType() != NabuccoCollectionType.LIST) {
            throw new IllegalStateException("Collections of type '"
                    + property.getInstance().getType() + "' are not supported yet.");
        }

        @SuppressWarnings("unchecked")
        NabuccoList<Datatype> values = (NabuccoList<Datatype>) property.getInstance();

        if (stringValue != null) {

            String[] rowIds = SPLIT_PATTERN.split(stringValue);
            List<Datatype> newValues = new ArrayList<Datatype>();

            if (!multipleSelection && newValues.size() > 1) {
                throw new ControlParserException(
                        "Error by parsing of picker control value. There is a multiple value sent, but only sigle value is allowed for the bound datatype.");
            }
            for (String rowId : rowIds) {

                Datatype element = super.getDatatypeById(rowId);
                if (element != null) {
                    newValues.add(element);
                }
            }

            values.retainAll(newValues);
        }

        return values;
    }

    @Override
    public JsonElement toJson(NabuccoCollection<Datatype> collection, String displayPath) {

        JsonList valueList = new JsonList();
        if (collection == null) {
            return valueList;
        }

        for (Datatype datatype : collection) {

            String displayValue = this.getLabel(datatype, displayPath);

            JsonMap map = new JsonMap();
            map.add(JSON_DISPLAY_VALUE, displayValue);
            map.add(JSON_ID, super.getId(datatype));

            valueList.add(map);
        }

        return valueList;
    }

}
