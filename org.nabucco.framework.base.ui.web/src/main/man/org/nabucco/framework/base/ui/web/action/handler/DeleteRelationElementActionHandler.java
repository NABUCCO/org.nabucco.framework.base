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
package org.nabucco.framework.base.ui.web.action.handler;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.property.CollectionProperty;
import org.nabucco.framework.base.facade.datatype.property.ComponentRelationProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyType;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.WebActionHandler;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.WorkItem;
import org.nabucco.framework.base.ui.web.component.work.editor.EditorItem;
import org.nabucco.framework.base.ui.web.component.work.editor.RelationTab;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * DeleteActionHandler
 * 
 * Removes selected element from the relation
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DeleteRelationElementActionHandler extends WebActionHandler {

    public static final String ID = "System.DeleteRelationElement";

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ClientException {
        WebActionResult result = new WebActionResult();

        String instanceId = parameter.get(NabuccoServletPathType.EDITOR);
        EditorItem editor = NabuccoServletUtil.getEditor(instanceId);
        if (editor == null) {
            throw new ClientException("Cannot remove relation element. The editor is not defined.");
        }

        String tabId = parameter.get(NabuccoServletPathType.TAB);
        RelationTab relationTab = editor.getRelationArea().getTab(tabId);
        if (relationTab == null) {
            throw new ClientException("Cannot remove relation element. The Relation tab cannot be found.");
        }

        String propertyPath = relationTab.getProperty();

        String instance = parameter.get(NabuccoServletPathType.INSTANCE);
        if (instance == null || instance.length() == 0) {
            return result;
        }
        long removeElementInstanceId = Long.parseLong(instance);
        Datatype removeElement = relationTab.getTableModel().getDatatypeByObjectId(removeElementInstanceId);

        if (removeElement == null) {
            throw new ClientException("Cannot remove relation element. The element cannot be found.");
        }

        WorkItem selectedWorkItem = NabuccoServletUtil.getSelectedWorkItem();
        Datatype sourceDatatype = editor.getModel().getDatatype();

        if (sourceDatatype == null) {
            throw new ClientException("Cannot remove relation element. No Datatype is bound.");
        }

        NabuccoProperty property = sourceDatatype.getProperty(propertyPath);

        this.removeElement(removeElement, property);

        selectedWorkItem.getModel().refresh();
        result.addItem(new RefreshItem(WebElementType.EDITOR_RELATION_AREA, selectedWorkItem.getInstanceId()));
        result.addItem(new RefreshItem(WebElementType.WORK_AREA));
        return result;
    }

    /**
     * Removes the element from the given source
     * 
     * @param removeElement
     *            the element to be removed
     * @param property
     *            the property where the element should be removed from
     * 
     * @throws ClientException
     *             if cannot remove
     * 
     */
    public void removeElement(Datatype removeElement, NabuccoProperty property) throws ClientException {
        if (property == null) {
            throw new IllegalArgumentException("Cannot remove element from relation. Property is 'null'.");
        }

        NabuccoPropertyType propertyType = property.getPropertyType();

        if (propertyType.equals(NabuccoPropertyType.COMPONENT_RELATION)) {
            ComponentRelationProperty componentRelationProperty = (ComponentRelationProperty) property;

            NabuccoList<ComponentRelation<?>> relationList = componentRelationProperty.getInstance();

            // Search for a relation
            ComponentRelation<?> foundRelation = null;

            for (ComponentRelation<?> relation : relationList) {
                if (relation.getTarget() != null && relation.getTarget().getId() != null) {
                    if (removeElement instanceof ComponentRelation) {

                        // TODO: Make the component relation to be saved so that there id is not
                        // null

                        if (relation.getId() == null
                                && relation.getTarget().getId()
                                        .equals(((ComponentRelation<?>) removeElement).getTarget().getId())) {
                            foundRelation = relation;
                            break;
                        }
                        if (relation.getId().equals(((ComponentRelation<?>) removeElement).getId())) {
                            foundRelation = relation;
                            break;
                        }
                    }
                }
            }

            if (foundRelation != null) {
                relationList.remove(foundRelation);
            } else {
                throw new ClientException("Cannot remove element from relation. Element not found in bound list");
            }
        } else if (propertyType.equals(NabuccoPropertyType.COLLECTION)) {
            CollectionProperty collectionProperty = (CollectionProperty) property;
            NabuccoList<? extends NType> relationList = (NabuccoList<? extends NType>) collectionProperty.getInstance();

            if (relationList.contains(removeElement)) {
                relationList.remove(removeElement);
            } else {
                throw new ClientException("Cannot remove element from relation. Element not found in bound list.");
            }
        } else {
            throw new ClientException("Cannot remove element. The relation type is not supported yet.");
        }
    }

}
