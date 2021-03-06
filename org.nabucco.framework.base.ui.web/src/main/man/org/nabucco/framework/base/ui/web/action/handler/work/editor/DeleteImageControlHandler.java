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

import java.util.List;

import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.exception.client.action.ActionException;
import org.nabucco.framework.base.ui.web.action.WebActionHandler;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.editor.EditTab;
import org.nabucco.framework.base.ui.web.component.work.editor.EditorItem;
import org.nabucco.framework.base.ui.web.component.work.editor.control.EditorControl;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * Action handler to synchronize control elements
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DeleteImageControlHandler extends WebActionHandler {

    public final static String ID = "System.DeleteImageControl";

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ActionException {
        WebActionResult result = new WebActionResult();
        String controlId = parameter.get(NabuccoServletPathType.CONTROL);
        String editorId = parameter.get(NabuccoServletPathType.EDITOR);
        String tabId = parameter.get(NabuccoServletPathType.TAB);

        EditorItem editor = NabuccoServletUtil.getEditor(editorId);
        EditTab tab = editor.getEditArea().getTab(tabId);
        EditorControl control = tab.getControl(controlId);

        // TODO: Remove the old file if possible
        NabuccoProperty property = control.getModel().getProperty();
        List<?> instance = (List<?>) property.getInstance();

        if (instance.isEmpty() == false) {
            instance.clear();
            property.getParent().setProperty(property.createProperty(instance));

            control.getModel().setProperty(property);

            result.addItem(new RefreshItem(WebElementType.EDITOR));
        }

        return result;
    }

}
