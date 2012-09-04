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

import java.util.List;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.WebActionHandlerSupport;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.bulkeditor.BulkEditorItem;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * UpdateEditorDependenciesHandler
 * 
 * Selects all items from the actual page in bulk editor
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DeselectBulkEditorPageActionHandler extends WebActionHandlerSupport {

    public final static String ID = "System.DeselectBulkEditorPage";

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ClientException {
        WebActionResult retVal = new WebActionResult();
        String editorId = parameter.get(NabuccoServletPathType.BULKEDITOR);
        BulkEditorItem editor = (BulkEditorItem) NabuccoServletUtil.getWorkItem(editorId);

        if (editor == null) {
            return retVal;
        }

        List<Datatype> currentPage = editor.getModel().getTableModel().getCurrentPage();

        for (Datatype datatype : currentPage) {
            NabuccoDatatype ndatatype = (NabuccoDatatype) datatype;
            editor.getModel().deselectRow(String.valueOf(ndatatype.getId()));
        }

        retVal.addItem(new RefreshItem(WebElementType.BULKEDITOR, editorId));
        return retVal;
    }
}
