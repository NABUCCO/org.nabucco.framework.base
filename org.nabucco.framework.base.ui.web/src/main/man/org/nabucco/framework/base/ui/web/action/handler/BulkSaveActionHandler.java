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

import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.property.CollectionProperty;
import org.nabucco.framework.base.facade.datatype.property.ComponentRelationProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyType;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.client.action.ActionException;
import org.nabucco.framework.base.ui.web.action.WebActionHandlerSupport;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.WorkItem;
import org.nabucco.framework.base.ui.web.component.work.WorkItemActionType;
import org.nabucco.framework.base.ui.web.component.work.bulkeditor.BulkEditorItem;
import org.nabucco.framework.base.ui.web.component.work.editor.EditorItem;
import org.nabucco.framework.base.ui.web.component.work.editor.RelationTab;
import org.nabucco.framework.base.ui.web.component.work.list.ListItem;
import org.nabucco.framework.base.ui.web.model.work.WorkItemModel;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * Action Handler Support for saving Datatypes.
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public abstract class BulkSaveActionHandler<D extends NabuccoDatatype> extends WebActionHandlerSupport {

    /** The Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(BulkSaveActionHandler.class);

    @Override
    public final WebActionResult execute(WebActionParameter parameter) throws ClientException {

        String instanceId = parameter.get(NabuccoServletPathType.BULKEDITOR);

        BulkEditorItem editor = (BulkEditorItem) NabuccoServletUtil.getWorkItem(instanceId);

        if (editor == null) {
            throw new ActionException("Cannot locate editor item with id '" + instanceId + "'.");
        }

        WebActionResult result = new WebActionResult();

        List<D> saveDatatypes = this.getDatatypes(editor);

        // Validate all datatypes and if any of them is vont valid according to the actual
        // configuration, than break saving
        boolean validateResult = true;
        for (Datatype datatype : saveDatatypes) {
            validateResult = editor.getModel().validate(datatype);
            if (!validateResult) {
                logger.warning("Datatype of editor '" + instanceId + "' is not valid.");
                break;
            }
        }

        List<Datatype> newContent = null;
        if (validateResult) {

            try {
                List<D> savedDatatypes = this.saveDatatypeList(saveDatatypes, editor, parameter);

                if (savedDatatypes == null) {
                    throw new IllegalStateException("Operation saveDatatype of '"
                            + this.getClass().getCanonicalName() + "' does not return a valid datatype [null].");
                }

                newContent = new ArrayList<Datatype>(savedDatatypes);
                editor.getModel().setContent(newContent);

                this.afterExecution(savedDatatypes, editor, parameter);

                // Add some custom result actions if any
                WebActionResult customResultActions = this.getCustomResultActions(savedDatatypes, editor, parameter);
                if (customResultActions != null) {
                    result.addResult(customResultActions);
                }

            } catch (ClientException ce) {
                logger.error(ce, "Error maintaining Datatype.");
                throw ce;
            } catch (Exception e) {
                logger.error(e, "Error maintaining Datatype.");
                throw new ClientException("Error maintaining Datatype.", e);
            }
        } else {
            logger.warning("Datatype of editor '" + instanceId + "' is not valid.");
        }

        result.addItem(new RefreshItem(WebElementType.WORK_AREA));
        result.addItem(new RefreshItem(WebElementType.BROWSER_AREA));

        if (editor.getSourceWebElement() != null && newContent != null) {
            // Replace content of source element. It is needed because of concurrency problem if
            // user wants to make editing one more time in current transaction withour reloading of
            // changed datatype
            result.addResult(this.addToParent(newContent, editor));

            if (editor.getSource() != null) {
                WorkItem source = editor.getSource();
                WorkItemModel sourceModel = source.getModel();
                sourceModel.refresh();

                // Workaround: Save the item by saving of a child
                // Should be replaced with another expected saving method
                if (sourceModel.isDirty()) {
                    WebActionParameter param = new WebActionParameter(parameter.getSession(),
                            parameter.getJsonRequest(), null);
                    param.setParameter(NabuccoServletPathType.EDITOR, source.getInstanceId());
                    param.setParameter(NabuccoServletPathType.SIGNAL, null);
                    param.setParameter(NabuccoServletPathType.WORKFLOW, null);
                    String saveAction = source.getWorkingItemAction(WorkItemActionType.SAVE);
                    this.executeAction(saveAction, param);
                }

                result.addItem(new RefreshItem(WebElementType.EDITOR_RELATION_AREA, editor.getSource().getInstanceId()));
            }

        }

        return result;
    }

    /**
     * Add the provision assignment to the parent datatype if exists.
     * 
     * @param provisionAssignment
     *            the provision assignment to save
     * @param editor
     *            the editor holding the source reference
     */
    private WebActionResult addToParent(List<Datatype> newContent, BulkEditorItem editor) {
        if (editor.getSource() == null) {
            return null;
        }

        WebActionResult retVal = new WebActionResult();
        WebElement source = editor.getSourceWebElement();
        if (source instanceof ListItem) {
            ListItem list = (ListItem) source;
            list.getTableModel().setContent(newContent);
            list.getModel().refresh();
            retVal.addItem(new RefreshItem(WebElementType.LIST, list.getInstanceId()));
        } else if (source instanceof RelationTab) {
            // RelationTab tab = (RelationTab) source;

            EditorItem sourceEditor = (EditorItem) editor.getSource();
            String property = ((RelationTab) source).getProperty();

            Datatype sourceDatatype = sourceEditor.getModel().getDatatype();

            if (sourceDatatype == null) {
                return null;
            }
            for (Datatype datatype : newContent) {
                this.updateProperty((NabuccoDatatype) datatype, sourceDatatype, property);
            }

            retVal.addItem(new RefreshItem(WebElementType.EDITOR_RELATION_AREA));
        }

        return retVal;
    }

    /**
     * Add the provision assignment to the source datatype.
     * 
     * @param datatype
     *            the provision assignment to add
     * @param source
     *            the source datatype
     * @param propertyName
     *            the property name of the provision assignment
     */
    private void updateProperty(NabuccoDatatype datatype, Datatype source, String propertyName) {

        NabuccoProperty property = source.getProperty(propertyName);

        if (property == null) {
            return;
        }

        if (property.getPropertyType() == NabuccoPropertyType.COMPONENT_RELATION) {

            ComponentRelationProperty componentRelationProperty = (ComponentRelationProperty) property;

            // Existing relation!
            for (ComponentRelation<?> relation : componentRelationProperty.getInstance()) {
                if (relation.getTarget() != null && relation.getTarget().getId() != null) {
                    if (relation.getTarget().getId().equals(datatype.getId())) {
                        relation.setTarget(datatype);
                        return;
                    }
                }
            }

            // New relation!
            this.addProperty(datatype, source, propertyName);

        } else if (property.getPropertyType() == NabuccoPropertyType.COLLECTION) {
            CollectionProperty collectionProperty = (CollectionProperty) property;

            @SuppressWarnings("unchecked")
            List<Datatype> list = (List<Datatype>) collectionProperty.getInstance();
            for (int i = 0; i < list.size(); i++) {
                NType elem = list.get(i);
                if (elem instanceof NabuccoDatatype) {
                    NabuccoDatatype existingDatatype = (NabuccoDatatype) elem;
                    if (existingDatatype.getId().equals(datatype.getId())) {
                        list.set(i, datatype);
                        return;
                    }
                } else {
                    throw new IllegalStateException("Cannot append non NabuccoDatatype saved element.");
                }
            }

            this.addProperty(datatype, source, propertyName);
        }
    }

    /**
     * Returns the list of the datatypes to be saved
     * 
     * @param editor
     *            the editor item
     * @return the list of datatypes
     */
    @SuppressWarnings("unchecked")
    private List<D> getDatatypes(BulkEditorItem editor) {
        List<D> retVal = new ArrayList<D>();
        List<Datatype> content = editor.getModel().getTableModel().getContent();

        for (Datatype datatype : content) {

            if (datatype instanceof ComponentRelation<?>) {
                Object target = ((ComponentRelation<?>) datatype).getTarget();
                retVal.add((D) target);
            } else {

                retVal.add((D) datatype);
            }
        }

        return retVal;
    }

    /**
     * Maintain the given datatype to the database.
     * 
     * @param datatypeList
     *            the datatype list to be maintained
     * @param editor
     *            the current editor
     * @param parameter
     *            the web action parameter
     * 
     * @return the maintained datatype
     * 
     * @throws ClientException
     *             when the maintain operation fails
     */
    protected abstract List<D> saveDatatypeList(List<D> datatypeList, BulkEditorItem editor,
            WebActionParameter parameter) throws ClientException;

    /**
     * Callback method that is called after the save execution that can be overwritten.
     * 
     * @param datatypeList
     *            the list of saved datatypes
     * @param editor
     *            the current editor
     * @param parameter
     *            the web action parameter
     * 
     * @throws ClientException
     *             when the finish fails
     */
    protected void afterExecution(List<D> datatypeList, BulkEditorItem editor, WebActionParameter parameter)
            throws ClientException {
    }

    /**
     * Hook method that gives the possibility to add some custom result actions of save operations
     * 
     * @param datatypeList
     *            the list datatype that was saved
     * @param editor
     *            the editor of the datatype
     * @param parameter
     *            the sent parameter
     * @return
     */
    protected WebActionResult getCustomResultActions(List<D> datatypeList, BulkEditorItem editor,
            WebActionParameter parameter) {
        return null;
    }
}
