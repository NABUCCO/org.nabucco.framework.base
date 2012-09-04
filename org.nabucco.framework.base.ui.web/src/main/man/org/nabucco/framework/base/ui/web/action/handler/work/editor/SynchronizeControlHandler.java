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
package org.nabucco.framework.base.ui.web.action.handler.work.editor;

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.client.action.ActionException;
import org.nabucco.framework.base.ui.web.action.WebActionHandler;
import org.nabucco.framework.base.ui.web.action.handler.work.RefreshBulkEditorDependenciesHandler;
import org.nabucco.framework.base.ui.web.action.handler.work.RefreshEditorDependenciesHandler;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.bulkeditor.column.BulkEditorColumn;
import org.nabucco.framework.base.ui.web.component.work.editor.control.EditorControl;
import org.nabucco.framework.base.ui.web.model.editor.control.ControlModel;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * Action handler to synchronize control elements
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class SynchronizeControlHandler extends WebActionHandler {

    public final static String ID = "System.SynchronizeControl";

    private final static String VALUE = "value";

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ActionException {
        WebActionResult result = new WebActionResult();
        try {

            WebElement element = parameter.getElement();

            if (element.getType() == WebElementType.EDITOR_CONTROL) {
                String value = parameter.getJsonRequest().getValue(VALUE);
                result = this.validateEditor((EditorControl) element, value);

                // Process dependencies check
                RefreshEditorDependenciesHandler handler = new RefreshEditorDependenciesHandler();
                WebActionResult dependencyResult = handler.execute(parameter);

                result.addResult(dependencyResult);
            } else if (element.getType() == WebElementType.BULKEDITOR_COLUMN) {
                String value = parameter.getJsonRequest().getValue(VALUE);
                String controlId = parameter.get(NabuccoServletPathType.COLUMN);
                String objectId = parameter.get(NabuccoServletPathType.INSTANCE);
                BulkEditorColumn bulkEditorColumn = (BulkEditorColumn) element;

                result = this.validateBulkEditor(bulkEditorColumn, controlId, objectId, value);

                RefreshBulkEditorDependenciesHandler handler = new RefreshBulkEditorDependenciesHandler();
                WebActionResult dependencyResult = handler.execute(parameter);
                result.addResult(dependencyResult);

            }

        } catch (ClientException e) {
            throw new ActionException("Cannot validate control", e);
        }
        return result;
    }

    /**
     * Validate the control.
     * 
     * @param control
     *            the editor control
     * @param editor
     *            the editor instance
     * @throws ClientException
     */
    private WebActionResult validateEditor(EditorControl control, String value) throws ClientException {
        WebActionResult result = new WebActionResult();

        if (value != null) {
            ControlModel<?> model = control.getModel();

            model.setValue(value);
            String id = model.getId();

            RefreshItem item = new RefreshItem(WebElementType.CONTROL, id);
            result.addItem(item);

        }

        return result;
    }

    /**
     * Validates the bulk control
     * 
     * @param bulkEditor
     *            the bulk editor instance
     * @param controlId
     *            the id of the control
     * @param value
     *            the value to be set
     * @return result to be appended
     * @throws ClientException
     */
    private WebActionResult validateBulkEditor(BulkEditorColumn bulkEditor, String controlId, String objectId,
            String value) throws ClientException {
        WebActionResult result = new WebActionResult();

        if (value != null) {
            bulkEditor.getEditorModel().setValue(controlId, objectId, value);

            String refreshId = objectId + "_" + controlId;

            RefreshItem item = new RefreshItem(WebElementType.BULKEDITOR_CONTROL, refreshId);
            result.addItem(item);
        }

        return result;
    }
}
