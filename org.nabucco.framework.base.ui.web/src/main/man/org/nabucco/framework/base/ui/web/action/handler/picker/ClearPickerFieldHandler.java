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
package org.nabucco.framework.base.ui.web.action.handler.picker;

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.WebActionHandlerSupport;
import org.nabucco.framework.base.ui.web.action.handler.work.RefreshEditorDependenciesHandler;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.editor.control.PickerControl;

/**
 * ClearPickerFieldHandler
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ClearPickerFieldHandler extends WebActionHandlerSupport {

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ClientException {

        if (parameter.getElement().getType() != WebElementType.PICKER) {
            throw new ClientException("Element is not a Picker Control");
        }

        PickerControl element = (PickerControl) parameter.getElement();
        element.getModel().setValue(null); // Reset value

        WebActionResult result = new WebActionResult();
        result.addItem(new RefreshItem(WebElementType.CONTROL, element.getId()));
        result.addItem(new RefreshItem(WebElementType.BROWSER_AREA));

        WebActionResult refreshItems = super.executeAction(RefreshEditorDependenciesHandler.ID, parameter);
        result.addResult(refreshItems);
        return result;
    }

}
