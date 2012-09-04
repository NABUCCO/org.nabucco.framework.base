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
package org.nabucco.framework.base.ui.web.action.handler.work;

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.WebActionHandlerSupport;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.editor.EditTab;
import org.nabucco.framework.base.ui.web.component.work.editor.EditorGridElement;
import org.nabucco.framework.base.ui.web.component.work.editor.EditorItem;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * UpdateEditorDependenciesHandler
 * 
 * Analyses the editor and returns all items needed to be refreshed because of changed dependency
 * state
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class RefreshEditorDependenciesHandler extends WebActionHandlerSupport {

    public final static String ID = "System.RefreshEditorDependencies";

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ClientException {
        WebActionResult retVal = new WebActionResult();
        String editorId = parameter.get(NabuccoServletPathType.EDITOR);
        EditorItem editor = NabuccoServletUtil.getEditor(editorId);

        if (editor == null) {
            return retVal;
        }

        if (editor.getModel().isModelRefreshNeeded()) {
            RefreshItem modelRefresh = new RefreshItem(WebElementType.EDITOR, editor.getInstanceId());
            retVal.addItem(modelRefresh);
        } else {
            EditTab editTab = editor.getEditArea().getFocusedTab();

            // Add refresh items for dependent elements
            for (String controlId : editor.getModel().getRefreshNeededGridElementsIds()) {
                EditorGridElement editorElement = editTab.getGridElement(controlId);
                if (editorElement != null) {
                    RefreshItem dependentControl = new RefreshItem(WebElementType.CONTROL, controlId);
                    retVal.addItem(dependentControl);
                }
            }

            // Add for relations
            for (String relationId : editor.getRelationArea().getRefreshNeededRelationIds()) {
                RefreshItem dependentTab = new RefreshItem(WebElementType.EDITOR_RELATION_TAB, relationId);
                retVal.addItem(dependentTab);
            }
        }
        return retVal;
    }

}
