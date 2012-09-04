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

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.exception.client.action.ActionException;
import org.nabucco.framework.base.ui.web.action.WebActionHandlerSupport;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.bulkeditor.BulkEditorItem;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * Action Handler Support for saving Datatypes.
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public abstract class BulkCreateNewItemActionHandler<D extends NabuccoDatatype> extends WebActionHandlerSupport {

    /** The Logger */
    protected static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            BulkCreateNewItemActionHandler.class);

    @Override
    public final WebActionResult execute(WebActionParameter parameter) throws ClientException {

        String instanceId = parameter.get(NabuccoServletPathType.BULKEDITOR);

        BulkEditorItem editor = (BulkEditorItem) NabuccoServletUtil.getWorkItem(instanceId);

        if (editor == null) {
            throw new ActionException("Cannot locate editor item with id '" + instanceId + "'.");
        }

        WebActionResult result = new WebActionResult();

        NabuccoDatatype newDatatype = this.createNewDatatype(parameter);
        newDatatype.setDatatypeState(DatatypeState.INITIALIZED);

        boolean contains = false;
        for (Datatype datatype : editor.getTableModel().getContent()) {
            if (datatype instanceof NabuccoDatatype == false) {
                continue;
            }

            NabuccoDatatype nabuccoDatatype = (NabuccoDatatype) datatype;
            if (nabuccoDatatype.equals(newDatatype)) {
                contains = true;
                break;
            }
        }
        if (!contains) {
            List<Datatype> content = editor.getTableModel().getContent();

            content.add(newDatatype);
            editor.getTableModel().setContent(content);
        }
        result.addItem(new RefreshItem(WebElementType.WORK_AREA));
        result.addItem(new RefreshItem(WebElementType.BROWSER_AREA));

        return result;
    }

    /**
     * creates a new Datatype
     * 
     * @param parameter
     *            parameter
     * @return new datatype to add
     */
    protected abstract D createNewDatatype(WebActionParameter parameter) throws ClientException;
}
