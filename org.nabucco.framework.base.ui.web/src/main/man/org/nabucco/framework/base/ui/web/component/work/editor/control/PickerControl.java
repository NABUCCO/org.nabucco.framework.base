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
package org.nabucco.framework.base.ui.web.component.work.editor.control;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.PickerControlExtension;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.common.controller.PickerDialogController;
import org.nabucco.framework.base.ui.web.component.dialog.PickerDialog;
import org.nabucco.framework.base.ui.web.model.control.ControlModel;
import org.nabucco.framework.base.ui.web.model.control.relation.RelationControlModel;
import org.nabucco.framework.base.ui.web.model.control.relation.picker.PickerControlModel;
import org.nabucco.framework.base.ui.web.model.control.relation.picker.QueryParameterMapper;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;

/**
 * PickerControl This control represents the input field that opens a dialog with list selection.
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class PickerControl extends EditorControl {

    private static final long serialVersionUID = 1L;

    private PickerDialog dialog;

    /**
     * Creates a new {@link PickerControl} instance.
     * 
     * @param extension
     * @throws ExtensionException
     */
    public PickerControl(PickerControlExtension extension) throws ExtensionException {
        super(extension, WebElementType.PICKER);

        String pickerDialogId = PropertyLoader.loadProperty(extension.getPickerDialog());
        PickerDialogController pickerDialogController = NabuccoServletUtil.getPickerDialogController();
        String pickerInstanceId = pickerDialogController.createPickerDialog(pickerDialogId, this);
        this.dialog = pickerDialogController.getPickerDialog(pickerInstanceId);
    }

    @Override
    protected PickerControlExtension getExtension() {
        return (PickerControlExtension) super.getExtension();
    }

    @Override
    public ControlModel<?> instantiateModel(String id, String propertyPath) throws ExtensionException {
        boolean multipleSelection = PropertyLoader.loadProperty(this.getExtension().getMultipleSelection());
        String openAction = PropertyLoader.loadProperty(this.getExtension().getOpenAction());
        String displayPath = PropertyLoader.loadProperty(this.getExtension().getDisplayPath());

        QueryParameterMapper queryParameterMapper = new QueryParameterMapper(this.getExtension()
                .getQueryParameterMapping());

        RelationControlModel<?> model = new PickerControlModel(id, propertyPath, this.getDialog().getTableModel(),
                displayPath, openAction, super.getDependencySet(), queryParameterMapper, multipleSelection,
                this.getEditable());
        return model;
    }

    /**
     * Setter for the dialog.
     * 
     * @param dialog
     *            The dialog to set.
     */
    public void setDialog(PickerDialog dialog) {
        this.dialog = dialog;
    }

    /**
     * Getter for the dialog.
     * 
     * @return Returns the dialog.
     */
    public PickerDialog getDialog() {
        return this.dialog;
    }

    @Override
    public WebElement getElement(String id) {
        return this.getDialog();
    }
}
