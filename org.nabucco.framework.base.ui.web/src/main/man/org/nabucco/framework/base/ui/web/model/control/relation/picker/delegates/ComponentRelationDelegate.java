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
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.property.ComponentRelationProperty;
import org.nabucco.framework.base.facade.datatype.relation.RelationType;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.control.util.parser.ControlParserException;
import org.nabucco.framework.base.ui.web.model.table.TableModel;

/**
 * Delegate for component relations.
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class ComponentRelationDelegate extends
        RelationDelegateSupport<ComponentRelationProperty, NabuccoList<ComponentRelation<?>>> {



    /**
     * Creates a new {@link ComponentRelationDelegate} instance.
     * 
     * @param tableModel
     *            the table model
     */
    public ComponentRelationDelegate(TableModel<Datatype> tableModel) {
        super(tableModel);
    }

    @Override
    public NabuccoList<ComponentRelation<?>> parse(ComponentRelationProperty property, String stringValue,
            boolean multipleSelection)
            throws ControlParserException {

        NabuccoList<ComponentRelation<?>> relations = property.getInstance();

        if (relations == null) {
            throw new IllegalStateException("Component Relation List must not be null.");
        }

        if (stringValue != null) {

            List<ComponentRelation<?>> newRelations = new ArrayList<ComponentRelation<?>>();
            String[] rowIds = SPLIT_PATTERN.split(stringValue);

            if (!multipleSelection && rowIds.length > 1) {
                throw new ControlParserException(
                        "Error by parsing of picker control value. There is a multiple value sent, but only sigle value is allowed for the bound datatype.");
            }

            for (String rowId : rowIds) {

                Datatype element = super.getDatatypeById(rowId);
                if (element == null) {
                    continue;
                }

                ComponentRelation<?> oldRelation = this.getRelation(element, relations);

                if (oldRelation != null) {
                    newRelations.add(oldRelation);
                } else {
                    @SuppressWarnings("unchecked")
                    ComponentRelation<NabuccoDatatype> relation = (ComponentRelation<NabuccoDatatype>) property
                            .newComponentRelationInstance();

                    relation.setRelationType(RelationType.HAS);
                    relation.setDatatypeState(DatatypeState.INITIALIZED);
                    relation.setFunctionalType(property.getComponentRelationType());
                    relation.setTarget(element);

                    relations.add(relation);
                    newRelations.add(relation);
                }
            }

            relations.retainAll(newRelations);
        }

        return relations;
    }

    /**
     * Return the component relation of the given collection if it contains the given target
     * element.
     * 
     * @param element
     *            the target element to check for
     * @param relations
     *            the relation list
     */
    private ComponentRelation<?> getRelation(Datatype element, NabuccoList<ComponentRelation<?>> relations) {
        for (ComponentRelation<?> oldRelation : relations) {
            if (element.equals(oldRelation.getTarget())) {
                return oldRelation;
            }
        }
        return null;
    }

    @Override
    public JsonElement toJson(NabuccoList<ComponentRelation<?>> collection, String displayPath) {

        JsonList valueList = new JsonList();

        if (collection == null) {
            return valueList;
        }

        for (ComponentRelation<?> relation : collection) {

            String displayValue = this.getLabel(relation.getTarget(), displayPath);

            JsonMap map = new JsonMap();
            map.add(JSON_DISPLAY_VALUE, displayValue);
            map.add(JSON_ID, super.getId(relation.getTarget()));
            valueList.add(map);
        }

        return valueList;
    }

}
