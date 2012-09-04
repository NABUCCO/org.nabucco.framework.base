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
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.common.controller.PickerDialogController;
import org.nabucco.framework.base.ui.web.component.dialog.PickerDialog;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitor;
import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitorContext;
import org.nabucco.framework.base.ui.web.model.editor.control.ControlModel;
import org.nabucco.framework.base.ui.web.model.editor.control.relation.picker.PickerControlModel;
import org.nabucco.framework.base.ui.web.model.editor.control.relation.picker.QueryParameterMapper;
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
     *            the picker control exteneion
     * 
     * @throws ExtensionException
     *             when the extensions cannot be loaded
     */
    public PickerControl(PickerControlExtension extension) throws ExtensionException {
        super(extension, WebElementType.PICKER);

        String pickerDialogId = PropertyLoader.loadProperty(extension.getPickerDialog());
        if (pickerDialogId != null && pickerDialogId.isEmpty() == false) {
            PickerDialogController pickerDialogController = NabuccoServletUtil.getPickerDialogController();
            String pickerInstanceId = pickerDialogController.createPickerDialog(pickerDialogId, this);

            this.dialog = pickerDialogController.getPickerDialog(pickerInstanceId);
        }
    }

    @Override
    protected PickerControlExtension getExtension() {
        return (PickerControlExtension) super.getExtension();
    }

    @Override
    public ControlModel<?> instantiateModel(String id, String propertyPath) throws ExtensionException {
        boolean multiSelection = PropertyLoader.loadProperty(this.getExtension().getMultipleSelection());
        String openAction = PropertyLoader.loadProperty(this.getExtension().getOpenAction());
        String displayPath = PropertyLoader.loadProperty(this.getExtension().getDisplayPath());
        String autoCompletionFilter = PropertyLoader.loadProperty(this.getExtension().getAutoCompletionFilter());

        QueryParameterMapper queryParameterMapper = new QueryParameterMapper(this.getExtension()
                .getQueryParameterMapping());

        String dialogInstanceId = null;
        if (this.getDialog() != null) {
            dialogInstanceId = this.getDialog().getInstanceId();
        }

        PickerControlModel model = new PickerControlModel(id, propertyPath, dialogInstanceId, displayPath, openAction,
                super.getDependencySet(), queryParameterMapper, multiSelection, this.isEditable());

        model.setAutoCompletionFilter(autoCompletionFilter);

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

    /**
     * Getter for the auto completion filter id.
     * 
     * @return the configured auto-completion filter id
     */
    public String getAutoCompletionFilter() {
        PickerControlExtension extension = (PickerControlExtension) super.getExtension();
        return PropertyLoader.loadProperty(extension.getAutoCompletionFilter());
    }

    /**
     * Getter for the display path.
     * 
     * @return the configured display path
     */
    public String getDisplayPath() {
        PickerControlExtension extension = (PickerControlExtension) super.getExtension();
        return PropertyLoader.loadProperty(extension.getDisplayPath());
    }

    @Override
    public WebElement getElement(String id) {
        return null;
    }

    /**
     * Accepts the web element visitor. Overload this function to let element be visited
     * 
     * @param visitor
     *            visitor to be accepted
     * @param context
     *            context of the visitor
     */
    @Override
    public <T extends WebElementVisitorContext> void accept(WebElementVisitor<T> visitor, T context)
            throws VisitorException {
        if (visitor != null) {
            visitor.visit(this, context);
        }
    }

}
