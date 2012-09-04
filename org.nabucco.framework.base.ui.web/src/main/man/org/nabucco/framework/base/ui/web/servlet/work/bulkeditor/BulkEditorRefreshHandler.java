/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco-source.org/nabucco-license.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.ui.web.servlet.work.bulkeditor;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.work.bulkeditor.column.BulkEditorColumn;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletRequest;
import org.nabucco.framework.base.ui.web.servlet.NabuccoServletResponse;
import org.nabucco.framework.base.ui.web.servlet.util.handler.ActionHandler;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathEntry;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * BulkEditorRefreshHandler
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class BulkEditorRefreshHandler extends ActionHandler {

    /**
     * Creates a new {@link BulkEditorRefreshHandler} instance.
     * 
     * @param request
     * @param response
     */
    public BulkEditorRefreshHandler(NabuccoServletRequest request, NabuccoServletResponse response) {
        super(request, response);
    }

    @Override
    protected void process(WebElement element, NabuccoServletPathEntry entry) throws ClientException {
        if (entry == null || entry.getNext() == null) {

            if (element != null) {

                if (element instanceof BulkEditorColumn) {
                    String datatypeId = this.getRequest().getServletPath().getId(NabuccoServletPathType.INSTANCE);
                    BulkEditorColumn column = (BulkEditorColumn) element;
                    Datatype datatype = column.getEditorModel().getDatatype(datatypeId);

                    if (datatype == null) {
                        return;
                    }

                    column.getModel().getControlModel(datatype);
                    JsonMap json = column.getModel().render(datatype);

                    super.getResponse().sendResponseParameter(json);
                } else {
                    super.getResponse().sendResponseParameter(element.toJson());

                }
            }
        }
    }
}
