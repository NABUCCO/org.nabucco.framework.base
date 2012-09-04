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
package org.nabucco.framework.base.ui.web.component.dialog;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.DialogButtonExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.DialogExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.GridDialogExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.control.DateDialogControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.control.DialogControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.control.DropDownDialogControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.control.FileDialogControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.control.LinkDialogControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.dialog.control.TextDialogControlExtension;
import org.nabucco.framework.base.ui.web.component.WebComposite;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.dialog.controls.DialogControl;
import org.nabucco.framework.base.ui.web.component.dialog.controls.DialogDateControl;
import org.nabucco.framework.base.ui.web.component.dialog.controls.DialogDropDownControl;
import org.nabucco.framework.base.ui.web.component.dialog.controls.DialogFileControl;
import org.nabucco.framework.base.ui.web.component.dialog.controls.DialogLinkControl;
import org.nabucco.framework.base.ui.web.component.dialog.controls.DialogTextControl;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.dialog.DialogButton;
import org.nabucco.framework.base.ui.web.model.dialog.DialogControlType;
import org.nabucco.framework.base.ui.web.model.dialog.DialogModel;
import org.nabucco.framework.base.ui.web.model.dialog.GridDialogModel;
import org.nabucco.framework.base.ui.web.model.dialog.MessageDialogModel;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;

/**
 * Dialog
 * 
 * The dialog instance
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class Dialog extends WebComposite {

    private static final long serialVersionUID = 1L;

    private DialogExtension extension;

    private String instanceId;

    private DialogModel model;

    public static final String JSON_TITLE = "titel";

    public static final String JSON_MESSAGE = "message";

    public static final String JSON_DIALOGTYPE = "dialogtype";

    public static final String JSON_ACTIONS = "actions";

    public static final String JSON_IDENTIFIER = "identifier";

    public static final String JSON_ICON = "icon";

    private static final String JSON_INSTANCE_ID = "instanceId";

    /**
     * Creates a new {@link Dialog} instance.
     * 
     * Standard constructor for Dialog.
     * 
     * @param instanceId
     *            the unique instance id of the dialog
     * @param extension
     *            dialog extension
     */
    public Dialog(String instanceId, DialogExtension extension) {
        super(WebElementType.DIALOG, extension);
        this.extension = extension;
        this.instanceId = instanceId;
    }

    @Override
    public void init() throws ExtensionException {
        this.model = null;

        DialogType dialogType = PropertyLoader.loadProperty(DialogType.class, this.extension.getType());
        if (dialogType == null) {
            throw new ExtensionException("The Dialog cannot be initialized. The dialog type is 'null'");
        }

        switch (dialogType) {
        case MESSAGEDIALOG: {
            this.model = new MessageDialogModel(this.getMessage());
            break;
        }
        case GRIDDIALOG: {
            if ((this.extension instanceof GridDialogExtension) == false) {
                throw new IllegalArgumentException("Dialog Extension hat a not expected type. Cannot initialize.");
            }
            GridDialogExtension ext = (GridDialogExtension) this.extension;
            GridDialogModel gridModel = new GridDialogModel(this.getDialogId(), this.getMessage(), this.getGrid());
            gridModel.init();

            this.model = gridModel;

            for (DialogControlExtension controlExtension : ext.getControls()) {
                DialogControlType controlType = PropertyLoader.loadProperty(DialogControlType.class,
                        controlExtension.getType());

                DialogControl control = null;
                switch (controlType) {
                case FILE: {
                    control = new DialogFileControl((FileDialogControlExtension) controlExtension);
                    control.init();
                    break;
                }
                case TEXT: {
                    control = new DialogTextControl((TextDialogControlExtension) controlExtension);
                    control.init();
                    break;
                }
                case TEXTAREA: {
                    control = new DialogTextControl((TextDialogControlExtension) controlExtension);
                    control.init();
                    break;
                }
                case TEXTLINK: {
                    control = new DialogLinkControl((LinkDialogControlExtension) controlExtension);
                    control.init();
                    break;
                }
                case DATE: {
                    control = new DialogDateControl((DateDialogControlExtension) controlExtension);
                    control.init();
                    break;
                }
                case DROPDOWN: {
                    control = new DialogDropDownControl((DropDownDialogControlExtension) controlExtension);
                    control.init();
                    break;
                }
                default: {
                    throw new IllegalArgumentException("The dialog control type "
                            + controlType + " is not supported yet.");
                }
                }

                gridModel.addDialogControlModel(control.getModel());
            }

            break;
        }
        }

        if (this.model == null) {
            throw new ExtensionException("The dialog cannot be initialized initialized. The model is 'null'");
        }

        for (DialogButtonExtension buttonExt : this.extension.getButtons()) {
            String buttonId = PropertyLoader.loadProperty(buttonExt.getIdentifier());
            String label = PropertyLoader.loadProperty(buttonExt.getLabel());
            String buttonIcon = PropertyLoader.loadProperty(buttonExt.getIcon());
            String actionId = PropertyLoader.loadProperty(buttonExt.getActionId());
            String tooltip = PropertyLoader.loadProperty(buttonExt.getTooltip());
            boolean validation = PropertyLoader.loadProperty(buttonExt.getValidate());

            DialogButton button = new DialogButton(buttonId, label, tooltip, buttonIcon, actionId, validation);
            this.model.addButton(button);
        }

        this.extension.getType();
    }

    /**
     * Getter for the dialog type
     * 
     * @return the type of the dialog
     */
    public DialogType getDialogType() {
        return PropertyLoader.loadProperty(DialogType.class, this.extension.getType());
    }

    /**
     * Removes dialoginstance from the application
     */
    public void closeDialog() {
        NabuccoServletUtil.getApplication().getDialogController().createDialog(this.getInstanceId());
    }

    /**
     * Getter for the instanceId.
     * 
     * @return Returns the instanceId.
     */
    public String getInstanceId() {
        return this.instanceId;
    }

    /**
     * Getter for the model.
     * 
     * @return Returns the model.
     */
    public DialogModel getModel() {
        return this.model;
    }

    /**
     * Getter for the dialogId.
     * 
     * @return Returns the dialogId.
     */
    protected String getDialogId() {
        return PropertyLoader.loadProperty(this.extension.getIdentifier());
    }

    /**
     * Getter for the title.
     * 
     * @return Returns the title.
     */
    public String getTitle() {
        return PropertyLoader.loadProperty(this.extension.getTitle());
    }

    /**
     * Getter for the icon.
     * 
     * @return Returns the icon.
     */
    public String getIcon() {
        return PropertyLoader.loadProperty(this.extension.getIcon());
    }

    /**
     * Getter for the dialog message
     * 
     * @return the dialog message
     */
    public String getMessage() {
        return PropertyLoader.loadProperty(this.extension.getMessage());
    }

    /**
     * Getter for the grid by grid dialogs
     * 
     * @return
     */
    public String getGrid() {
        if (this.extension instanceof GridDialogExtension == false) {
            return null;
        }
        return PropertyLoader.loadProperty(((GridDialogExtension) this.extension).getGrid());
    }

    @Override
    public JsonElement toJson() {

        JsonMap json = new JsonMap();
        json.add(JSON_ID, this.getDialogId());
        json.add(JSON_INSTANCE_ID, this.getInstanceId());
        json.add(JSON_ICON, this.getIcon());
        json.add(JSON_TITLE, this.getTitle());
        json.add(JSON_TYPE, this.getDialogType().getValue());
        json.add(JSON_MODEL, this.getModel().toJson());
        return json;
    }

}
