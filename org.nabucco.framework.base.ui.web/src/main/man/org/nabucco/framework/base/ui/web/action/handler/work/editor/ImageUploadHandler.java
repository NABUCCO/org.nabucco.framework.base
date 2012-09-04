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

import java.util.ArrayList;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.property.ComponentRelationProperty;
import org.nabucco.framework.base.facade.exception.client.action.ActionException;
import org.nabucco.framework.base.ui.web.action.WebActionHandler;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.RefreshItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.dialog.Dialog;
import org.nabucco.framework.base.ui.web.component.work.editor.EditTab;
import org.nabucco.framework.base.ui.web.component.work.editor.EditorItem;
import org.nabucco.framework.base.ui.web.component.work.editor.control.EditorControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.ImageControl;
import org.nabucco.framework.base.ui.web.model.dialog.DialogModel;
import org.nabucco.framework.base.ui.web.model.dialog.GridDialogModel;
import org.nabucco.framework.base.ui.web.model.dialog.controls.DialogFileControlModel;
import org.nabucco.framework.base.ui.web.model.dialog.controls.DialogGridModelElement;
import org.nabucco.framework.base.ui.web.model.editor.control.property.image.SingleImageControlModel;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;
import org.nabucco.framework.base.ui.web.session.NabuccoWebSession;

/**
 * Action handler to synchronize image control elements
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public abstract class ImageUploadHandler<N extends NabuccoDatatype> extends WebActionHandler {

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ActionException {
        WebActionResult result = new WebActionResult();

        String controlId = parameter.get(NabuccoServletPathType.CONTROL);
        String editorId = parameter.get(NabuccoServletPathType.EDITOR);
        String tabId = parameter.get(NabuccoServletPathType.TAB);

        EditorItem editor = NabuccoServletUtil.getEditor(editorId);
        if (editor == null) {
            throw new ActionException("Cannot upload data. Editor with id '" + editorId + "' is not found");
        }
        EditTab tab = editor.getEditArea().getTab(tabId);
        if (tab == null) {
            throw new ActionException("Cannot upload data. Tab with id " + tabId + " is not found.");
        }
        EditorControl control = tab.getControl(controlId);
        if (control == null) {
            throw new ActionException("Cannot upload data. Control with id " + tabId + " is not found.");
        }
        // Only image controls at the moment have path parameter
        if (control instanceof ImageControl == false) {
            throw new ActionException("The target control is not able to accept uploaded data.");
        }
        ImageControl imageControl = (ImageControl) control;

        String dialogId = parameter.get(NabuccoServletPathType.DIALOG);
        Dialog dialog = NabuccoServletUtil.getDialog(dialogId);
        DialogModel model = dialog.getModel();
        if (model instanceof GridDialogModel == false) {
            throw new ActionException(
                    "Configuration error. The processing upload dialog is not a grid dialog and cannot be processed.");
        }
        GridDialogModel gridModel = (GridDialogModel) model;

        // Create list with uploading urls
        List<String> picList = new ArrayList<String>();

        for (String gridControlId : gridModel.getControlIds()) {
            DialogGridModelElement gridControl = gridModel.getControl(gridControlId);
            if (gridControl instanceof DialogFileControlModel) {
                DialogFileControlModel fileControl = (DialogFileControlModel) gridControl;
                String url = fileControl.getValue();
                picList.add(url);
            }
        }

        if (picList.isEmpty()) {
            throw new ActionException("Configuration error. The processing upload dialog has no data fields.");
        }

        ComponentRelationProperty property = (ComponentRelationProperty) control.getModel().getProperty();
        NabuccoList<ComponentRelation<?>> instance = property.getInstance();

        // resolve the properties if any in the upload path
        String uploadPath = ((SingleImageControlModel) imageControl.getModel()).getResolvedUploadPath();

        // Get elements and move them to the correct path
        List<NabuccoDatatype> uploadedTargets = this.uploadData(picList, uploadPath, parameter.getSession());
        if (uploadedTargets == null) {
            throw new ActionException("Cannot upload data, list of data is 'null'");
        }

        // Clear of content relation only if there is only one upload target
        if (uploadedTargets.size() == 1) {
            instance.clear();
        }
        for (NabuccoDatatype target : uploadedTargets) {
            ComponentRelation<?> componentRelation = property.newComponentRelationInstance();
            componentRelation.setTarget(target);
            instance.add(componentRelation);
        }
        control.getModel().setProperty(property);
        if (editor.getModel().getDatatype().getDatatypeState() == DatatypeState.PERSISTENT) {
            editor.getModel().getDatatype().setDatatypeState(DatatypeState.MODIFIED);
        }

        result.addItem(new RefreshItem(WebElementType.EDITOR));
        return result;
    }

    /**
     * Returns the uploaded nabucco datatype
     * 
     * @param tempPathList
     *            the list of the pathes to be uploaded
     * @param uploadPath
     *            the upload path
     * @param session
     *            the session instance
     * @return the list of data instances to be used as target of new component relation
     * @throws ActionException
     *             if cannot upload data
     */
    protected abstract List<NabuccoDatatype> uploadData(List<String> tempPathList, String uploadPath,
            NabuccoWebSession session) throws ActionException;

}
