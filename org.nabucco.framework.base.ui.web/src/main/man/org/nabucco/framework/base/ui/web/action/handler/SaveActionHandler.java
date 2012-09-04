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

import java.util.List;

import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.context.WorkflowTransitionContext;
import org.nabucco.framework.base.facade.datatype.context.WorkflowTransitionContextRequest;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.workflow.Signal;
import org.nabucco.framework.base.facade.datatype.workflow.transition.TransitionContext;
import org.nabucco.framework.base.facade.datatype.workflow.transition.TransitionParameter;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.client.action.ActionException;
import org.nabucco.framework.base.ui.web.action.WebActionHandlerSupport;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.WorkArea;
import org.nabucco.framework.base.ui.web.component.work.WorkItem;
import org.nabucco.framework.base.ui.web.component.work.WorkItemActionType;
import org.nabucco.framework.base.ui.web.component.work.editor.EditorItem;
import org.nabucco.framework.base.ui.web.model.work.WorkItemModel;
import org.nabucco.framework.base.ui.web.model.work.workflow.WorkflowEntry;
import org.nabucco.framework.base.ui.web.model.work.workflow.WorkflowModel;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * Action Handler Support for saving Datatypes.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class SaveActionHandler<D extends NabuccoDatatype> extends WebActionHandlerSupport {

    /** The Logger */
    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(SaveActionHandler.class);

    @Override
    public final WebActionResult execute(WebActionParameter parameter) throws ClientException {

        WebElement element = parameter.getElement();
        EditorItem editor = null;
        String instanceId = null;
        if (element == null || element.getType() != WebElementType.EDITOR) {
            instanceId = parameter.get(NabuccoServletPathType.EDITOR);
            editor = NabuccoServletUtil.getEditor(instanceId);
        } else {
            editor = (EditorItem) element;
            instanceId = editor.getInstanceId();
        }

        if (editor == null) {
            throw new ActionException("Cannot locate editor item with id '" + instanceId + "'.");
        }

        WebActionResult result = new WebActionResult();

        boolean validateResult = editor.getModel().validate();

        if (validateResult) {

            try {
                if (editor.getModel().getDatatype() == null) {
                    throw new IllegalStateException("No Datatype defined in Editor Model.");
                }

                this.processWorkflow(editor, parameter);

                @SuppressWarnings("unchecked")
                D datatype = (D) editor.getModel().getDatatype();
                datatype = this.saveDatatype(datatype, editor, parameter);
                if (datatype == null) {
                    throw new IllegalStateException("Operation saveDatatype of '"
                            + this.getClass().getCanonicalName() + "' does not return a valid datatype [null].");
                }

                WorkArea workArea = NabuccoServletUtil.getWorkArea();
                if (workArea != null) {

                    WorkItem newItem = workArea.changeInstanceId(instanceId, String.valueOf(datatype.getId()));

                    if (newItem instanceof EditorItem) {
                        editor = (EditorItem) newItem;
                    } else {
                        result.addItem(new RefreshItem(WebElementType.EDITOR));
                    }
                }

                editor.getModel().setDatatype(datatype);

                this.afterExecution(datatype, editor, parameter);

                // Add some custom result actions if any
                WebActionResult customResultActions = this.getCustomResultActions(datatype, editor, parameter);
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

        WorkItem source = editor.getSource();
        if (source != null) {
            WorkItemModel sourceModel = source.getModel();
            sourceModel.refresh();

            // Workaround: Save the item by saving of a child
            // Should be replaced with another expected saving method
            if (sourceModel.isDirty()) {
                WebActionParameter param = new WebActionParameter(parameter.getSession(), parameter.getJsonRequest(),
                        null);
                param.setParameter(NabuccoServletPathType.EDITOR, source.getInstanceId());
                param.setParameter(NabuccoServletPathType.SIGNAL, null);
                param.setParameter(NabuccoServletPathType.WORKFLOW, null);
                String saveAction = source.getWorkingItemAction(WorkItemActionType.SAVE);
                this.executeAction(saveAction, param);
            }

            result.addItem(new RefreshItem(WebElementType.EDITOR_RELATION_AREA, editor.getSource().getInstanceId()));
        }

        return result;
    }

    /**
     * Add the next signal to the workflow transition context when a signal is transmitted in the
     * request URL.
     * 
     * @param editor
     *            the editor holding the reference to the workflow
     * @param parameter
     *            the web action parameter
     */
    private void processWorkflow(EditorItem editor, WebActionParameter parameter) {
        if (editor.getWorkflow() == null || editor.getWorkflow().getModel() == null) {
            return;
        }

        String instanceId = parameter.get(NabuccoServletPathType.WORKFLOW);
        String signalId = parameter.get(NabuccoServletPathType.SIGNAL);

        if (instanceId == null || signalId == null) {

            List<WorkflowEntry> entries = editor.getWorkflow().getModel().getEntryList();
            if (!entries.isEmpty()) {

                WorkflowTransitionContext context = new WorkflowTransitionContext();
                WorkflowTransitionContextRequest request = new WorkflowTransitionContextRequest();
                // request.setTransitionContext(entries.get(0).getTransitionContext());
                context.setRequestTransitionContext(request);

                parameter.getSession().getServiceContext().putRequestContext(context);
            }

            return;
        }

        WorkflowModel model = editor.getWorkflow().getModel();
        WorkflowEntry entry = model.getEntry(instanceId);

        if (entry == null || entry.getTransitionContext() == null) {
            return;
        }

        TransitionContext transitionContext = entry.getTransitionContext();
        NabuccoList<TransitionParameter> transitions = transitionContext.getNextTransitions();

        for (TransitionParameter transition : transitions) {

            Signal signal = transition.getSignal();
            if (signal == null || signal.getName() == null) {
                continue;
            }

            long singalIdentifier = Long.parseLong(signalId);
            if (singalIdentifier == signal.getId()) {
                transitionContext.setSignal(signal);

                WorkflowTransitionContext context = new WorkflowTransitionContext();
                parameter.getSession().getServiceContext().putRequestContext(context);

                WorkflowTransitionContextRequest requestTransitionContext = new WorkflowTransitionContextRequest();
                requestTransitionContext.setTransitionContext(transitionContext);
                context.setRequestTransitionContext(requestTransitionContext);

                return;
            }
        }

    }

    /**
     * Maintain the given datatype to the database.
     * 
     * @param datatype
     *            the datatype to maintain
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
    protected abstract D saveDatatype(D datatype, EditorItem editor, WebActionParameter parameter)
            throws ClientException;

    /**
     * Callback method that is called after the save execution that can be overwritten.
     * 
     * @param datatype
     *            the saved datatype
     * @param editor
     *            the current editor
     * @param parameter
     *            the web action parameter
     * 
     * @throws ClientException
     *             when the finish fails
     */
    protected void afterExecution(D datatype, EditorItem editor, WebActionParameter parameter) throws ClientException {
    }

    /**
     * Hook method that gives the possibility to add some custom result actions of save operations
     * 
     * @param datatype
     *            the datatype that was saved
     * @param editor
     *            the editor of the datatype
     * @param parameter
     *            the sent parameter
     * @return
     */
    protected WebActionResult getCustomResultActions(D datatype, EditorItem editor, WebActionParameter parameter) {
        return null;
    }
}
