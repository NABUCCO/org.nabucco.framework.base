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

import java.util.Collections;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.handler.FilteringActionHandler;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.dialog.PickerDialog;
import org.nabucco.framework.base.ui.web.component.work.editor.RelationTab;
import org.nabucco.framework.base.ui.web.component.work.editor.control.PickerControl;
import org.nabucco.framework.base.ui.web.model.editor.control.relation.picker.PickerControlModel;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * ApplyPickerFilterHandler
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ApplyPickerFilterHandler extends FilteringActionHandler {

    public static String ID = "System.ApplyPickerFilter";

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ClientException {
        String pickerDialogId = parameter.get(NabuccoServletPathType.PICKER_DIALOG);

        try {
            PickerDialog item = NabuccoServletUtil.getPickerDialogController().getPickerDialog(pickerDialogId);

            if (item == null) {
                throw new ClientException("Cannot find picker dialog with id '" + pickerDialogId + "'");
            }

            String activeFilterId = item.getActiveFilterId();

            List<Datatype> values = super.getFilteredData(activeFilterId, parameter);

            if (values == null) {
                values = Collections.<Datatype> emptyList();
            }

            item.getTableModel().firstPage();
            item.getTableModel().setContent(values);

        } catch (Exception e) {
            throw new ClientException("Cannot apply filter to List '" + pickerDialogId + "'", e);
        }

        return new WebActionResult();
    }

    @Override
    protected String getQueryFilterParameter(WebActionParameter requestParameter, String paramterName,
            Class<?> classType) throws ClientException {
        String pickerDialogId = requestParameter.get(NabuccoServletPathType.PICKER_DIALOG);

        WebElement pickerDialogSource = NabuccoServletUtil.getPickerDialogController().getPickerDialogSource(
                pickerDialogId);

        if (pickerDialogSource == null) {
            throw new ClientException("Cannot find picker dialog with id '" + pickerDialogId + "'");
        }

        if (pickerDialogSource instanceof PickerControl) {
            PickerControl pickerControl = (PickerControl) pickerDialogSource;
            PickerControlModel model = (PickerControlModel) pickerControl.getModel();
            Object parameter = model.getQueryParameter(paramterName);

            if (parameter == null) {
                return null;
            }

            // Basetypes
            if (Basetype.class.isAssignableFrom(classType)) {
                if (!parameter.getClass().isAssignableFrom(classType)) {
                    throw new ClientException(
                            "Cannot resolve query filter parameter. The expected classtype is not same as the type of resolved parameter");
                }
                return ((Basetype) parameter).getValueAsString();
            }

            // Primitives and Enumerations
            return String.valueOf(parameter);

        } else if (pickerDialogSource instanceof RelationTab) {
            // TODO: Extend for relation tabs
            return null;
        }

        throw new IllegalArgumentException(
                "Unexpected picker dialog source element. Cannot resolve query filter parameters.");
    }
}
