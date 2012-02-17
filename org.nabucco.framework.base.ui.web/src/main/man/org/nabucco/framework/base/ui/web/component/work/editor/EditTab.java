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
package org.nabucco.framework.base.ui.web.component.work.editor;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.EditorTabExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.CheckBoxControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.CurrencyControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.DateControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.DropDownControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.EditorControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.ImageControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.LinkControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.PasswordControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.PickerControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.RadioControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.TextAreaControlExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.work.editor.control.TextControlExtension;
import org.nabucco.framework.base.ui.web.component.WebComposite;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.work.editor.control.CheckBoxControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.CurrencyControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.DateControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.DropDownControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.EditorControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.ImageControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.LinkControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.PasswordControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.PickerControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.RadioButtonControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.TextAreaInputControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.TextInputControl;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.control.ControlType;

/**
 * Tab of a single editor.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class EditTab extends WebComposite {

    /** The default serial version UID */
    private static final long serialVersionUID = 1L;

    private static final String JSON_ROWS = "rows";

    private static final String JSON_COLUMNS = "cols";

    private static final String JSON_CONTROLS = "controls";

    /** The Tab grid start */
    private String gridStart;

    /** The Tab grid end */
    private String gridEnd;

    /** The Editor Tab Extension */
    private EditorTabExtension extension;

    /**
     * Creates a new {@link EditTab} instance.
     * 
     * @param extension
     *            the editor tab extension
     */
    public EditTab(EditorTabExtension extension) {
        super(WebElementType.EDITOR_EDIT_TAB, extension);
        if (extension == null) {
            throw new IllegalArgumentException("Cannot create editor tab for extension 'null'.");
        }
        this.extension = extension;
    }

    @Override
    public void init() throws ExtensionException {
        for (EditorControlExtension controlExtension : this.extension.getFields()) {
            EditorControl control;

            ControlType type = PropertyLoader.loadProperty(ControlType.class, controlExtension.getType());

            switch (type) {
            case DROPDOWN:
                control = new DropDownControl((DropDownControlExtension) controlExtension);
                break;

            case TEXT:
                control = new TextInputControl((TextControlExtension) controlExtension);
                break;

            case DATE:
                control = new DateControl((DateControlExtension) controlExtension);
                break;

            case CURRENCY:
                control = new CurrencyControl((CurrencyControlExtension) controlExtension);
                break;

            case RADIO:
                control = new RadioButtonControl((RadioControlExtension) controlExtension);
                break;

            case CHECKBOX:
                control = new CheckBoxControl((CheckBoxControlExtension) controlExtension);
                break;

            case LINK:
                control = new LinkControl((LinkControlExtension) controlExtension);
                break;

            case PICKER:
                control = new PickerControl((PickerControlExtension) controlExtension);
                break;

            case IMAGE: {
                control = new ImageControl((ImageControlExtension) controlExtension);
                break;
            }
            case TEXTAREA: {
                control = new TextAreaInputControl((TextAreaControlExtension) controlExtension);
                break;
            }
            case PASSWORD: {
                control = new PasswordControl((PasswordControlExtension) controlExtension);
                break;
            }
            default:
                throw new IllegalArgumentException("Control model type [" + type + "] is not supported.");
            }

            super.addElement(control.getId(), control);
            control.init();
        }
    }


    /**
     * Indicates if the validation messages need to be sent to UI
     * 
     * @return
     */
    protected boolean isValidating() {
        for (EditorControl control : this.getAllControls()) {
            if (control.getModel().isValidating() == true) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns if the tab is valid: if all controls are valid
     * 
     * @return true if valid
     */
    private boolean isValid() {
        if (this.isValidating()) {
            for (EditorControl control : this.getAllControls()) {
                if (!control.getModel().isValid() && control.getModel().isValidating()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Getter for the editor tab id.
     * 
     * @return the editor tab id
     */
    public String getId() {
        if (this.extension.getIdentifier() == null) {
            return null;
        }

        return this.extension.getIdentifier().getValue();
    }

    /**
     * Getter for the editor tab label.
     * 
     * @return the editor tab label
     */
    public String getLabel() {
        return PropertyLoader.loadProperty(this.extension.getLabel());
    }

    /**
     * Getter for the editor tab tooltip.
     * 
     * @return the editor tab tooltip
     */
    public String getTooltip() {
        return PropertyLoader.loadProperty(this.extension.getTooltip());
    }

    /**
     * Getter for the editor tab icon.
     * 
     * @return the editor tab icon
     */
    public String getIcon() {
        return PropertyLoader.loadProperty(this.extension.getIcon());
    }

    /**
     * Getter for the editor control start position.
     * 
     * @return the editor control start position
     */
    public String getStartPosition() {
        if (this.gridStart == null) {
            String position = PropertyLoader.loadProperty(this.extension.getGrid());
            String[] positions = position.split("-");

            if (positions.length != 2 || positions[0].isEmpty()) {
                throw new IllegalArgumentException("Cannot resolve grid of tab [" + this.getId() + "].");
            }

            this.gridStart = positions[0];
        }

        return this.gridStart;
    }

    /**
     * Getter for the editor control end position.
     * 
     * @return the editor control end position
     */
    public String getEndPosition() {
        if (this.gridEnd == null) {
            String position = PropertyLoader.loadProperty(this.extension.getGrid());
            String[] positions = position.split("-");

            if (positions.length != 2 || positions[1].isEmpty()) {
                throw new IllegalArgumentException("Cannot resolve grid of tab [" + this.getId() + "].");
            }

            this.gridEnd = positions[1];
        }

        return this.gridEnd;
    }

    /**
     * Getter for the control height.
     * 
     * @return the height of the control
     */
    public Integer getHeight() {
        String start = this.getStartPosition();
        String end = this.getEndPosition();

        int startHeight = Integer.parseInt(start.substring(1));
        int endHeight = Integer.parseInt(end.substring(1));

        return endHeight - startHeight + 1;
    }

    /**
     * Getter for the control width.
     * 
     * @return the width of the control
     */
    public Integer getWidth() {
        String start = this.getStartPosition();
        String end = this.getEndPosition();

        Character startCharacter = start.charAt(0);
        Character endCharacter = end.charAt(0);

        return endCharacter.compareTo(startCharacter) + 1;
    }

    /**
     * Getter for the editor control with the given id.
     * 
     * @param id
     *            the editor control id
     * 
     * @return the editor control, or null if no editor control with the given id is registerd
     */
    public EditorControl getControl(String id) {
        WebElement element = super.getElement(id);

        if (element instanceof EditorControl) {
            return (EditorControl) element;
        }

        return null;
    }

    /**
     * Getter for the lsit of editor controls.
     * 
     * @return the list of registered editor controls
     */
    public List<EditorControl> getAllControls() {
        List<EditorControl> controls = new ArrayList<EditorControl>();
        String[] ids = super.getElementIds();
        for (String id : ids) {
            WebElement element = super.getElement(id);
            if (element instanceof EditorControl) {
                controls.add((EditorControl) element);
            }
        }
        return controls;
    }

    @Override
    public JsonElement toJson() {
        JsonMap json = new JsonMap();

        JsonList controlList = new JsonList();

        for (EditorControl control : this.getAllControls()) {
            if (control.isVisible()) {
                controlList.add(control.toJson());
            }
        }

        json.add(JSON_ID, this.getId());
        json.add(JSON_LABEL, this.getLabel());
        json.add(JSON_TOOLTIP, this.getTooltip());
        json.add(JSON_ROWS, this.getHeight());
        json.add(JSON_COLUMNS, this.getWidth());
        json.add(JSON_CONTROLS, controlList);

        json.add(JSON_VALID, this.isValid());

        return json;
    }

}
