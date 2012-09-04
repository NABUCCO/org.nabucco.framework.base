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

import org.nabucco.framework.base.facade.exception.client.action.ActionException;
import org.nabucco.framework.base.ui.web.action.WebActionHandler;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.editor.EditorItem;
import org.nabucco.framework.base.ui.web.component.work.editor.RelationArea;

/**
 * Action handler to limit relation tab
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class LimitRelationTabHandler extends WebActionHandler {

    public final static String ID = "System.LimitRelationTab";

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ActionException {
        WebActionResult result = new WebActionResult();
        String limitString = parameter.getJsonRequest().getValue();
        if (limitString == null || limitString.isEmpty()) {
            return result;
        }
        int limitNumber = Integer.parseInt(limitString);

        if (parameter.getElement() == null || (parameter.getElement() instanceof EditorItem == false)) {
            return result;
        }

        EditorItem element = (EditorItem) parameter.getElement();
        RelationArea relationArea = element.getRelationArea();

        relationArea.setPageSize(limitNumber);

        result.addItem(new RefreshItem(WebElementType.EDITOR_RELATION_AREA));

        return result;
    }
}
