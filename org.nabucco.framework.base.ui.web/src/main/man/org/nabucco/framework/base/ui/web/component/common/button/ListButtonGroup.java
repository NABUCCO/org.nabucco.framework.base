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
package org.nabucco.framework.base.ui.web.component.common.button;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.ListButtonExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.ListButtonGroupExtension;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;

/**
 * A pressable action with label, tooltip and icon in the relation.
 * 
 * @author Leonid Agranovskiy PRODYNA AG
 */
public class ListButtonGroup extends ListButton {

    private static final long serialVersionUID = 1L;

    private static final String JSON_CHILDREN = "children";

    /** The action reference extension */
    private ListButtonGroupExtension extension;

    private List<ListButton> buttons = new ArrayList<ListButton>();

    /**
     * Creates a new {@link ListButtonGroup} instance.
     * 
     * @param extension
     *            the button extension
     */
    public ListButtonGroup(ListButtonGroupExtension extension) {
        super(extension);
        if (extension == null) {
            throw new IllegalArgumentException("Cannot create Button for extension [null].");
        }
        this.extension = extension;
    }

    @Override
    public void init() throws ExtensionException {
        super.init();

        for (ListButtonExtension buttonExtension : extension.getButtonList()) {
            ListButton button = new ListButton(buttonExtension);
            buttons.add(button);
            button.init();
        }

    }

    @Override
    public void updateStatus(boolean valueSelected, boolean isEditable) {
        super.updateStatus(valueSelected, isEditable);

        for (ListButton button : buttons) {
            button.updateStatus(valueSelected, isEditable);
        }
    }

    /**
     * Returns the first button if any
     * 
     * @return first button or exception
     */
    private ListButton getFirstButton() {
        if (buttons.isEmpty()) {
            throw new IllegalStateException("The Button group is not configured or has no children");
        }
        return buttons.get(0);
    }

    @Override
    public String getParameter() {

        String parameter = super.getParameter();
        if (parameter != null && !parameter.isEmpty()) {
            return parameter;
        } else {
            return this.getFirstButton().getParameter();
        }
    }

    @Override
    public String getActionId() {
        String parameter = super.getActionId();
        if (parameter != null && !parameter.isEmpty()) {
            return parameter;
        } else {
            return this.getFirstButton().getActionId();
        }
    }

    @Override
    public String getImage() {
        String parameter = super.getImage();
        if (parameter != null && !parameter.isEmpty()) {
            return parameter;
        } else {
            return this.getFirstButton().getImage();
        }
    }

    @Override
    public ButtonModificationType getModificationType() {
        ButtonModificationType parameter = super.getModificationType();
        if (parameter != null) {
            return parameter;
        } else {
            return this.getFirstButton().getModificationType();
        }
    }

    @Override
    public String getLabel() {
        String parameter = super.getLabel();
        if (parameter != null && !parameter.isEmpty()) {
            return parameter;
        } else {
            return this.getFirstButton().getLabel();
        }
    }

    @Override
    public String getTooltip() {
        String parameter = super.getTooltip();
        if (parameter != null && !parameter.isEmpty()) {
            return parameter;
        } else {
            return this.getFirstButton().getTooltip();
        }
    }

    @Override
    public JsonElement toJson() {
        JsonMap json = (JsonMap) super.toJson();

        json.add(JSON_TYPE, WebElementType.BUTTONGROUP.getName());

        JsonList childrenList = new JsonList();
        for (ListButton button : buttons) {
            childrenList.add(button.toJson());
        }

        json.add(JSON_CHILDREN, childrenList);

        return json;
    }
}
