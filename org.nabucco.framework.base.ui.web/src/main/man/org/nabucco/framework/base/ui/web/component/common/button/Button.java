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

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.ButtonExtension;
import org.nabucco.framework.base.ui.web.component.WebComponent;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonMap;

/**
 * A pressable action with label, tooltip and icon.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class Button extends WebComponent {

    private static final long serialVersionUID = 1L;

    protected static final String JSON_DISABLED = "disabled";

    private static final String JSON_ACTION = "action";

    private static final String JSON_PARAMETER = "parameter";

    /** The action reference extension */
    private ButtonExtension extension;

    /**
     * Creates a new {@link Button} instance.
     * 
     * @param extension
     *            the button extension
     */
    public Button(ButtonExtension extension) {
        super(WebElementType.BUTTON, extension);
        if (extension == null) {
            throw new IllegalArgumentException("Cannot create Button for extension [null].");
        }
        this.extension = extension;
    }

    @Override
    public void init() throws ExtensionException {
    }

    /**
     * Getter for the button id.
     * 
     * @return the button id
     */
    @Override
    public String getId() {
        if (this.extension.getIdentifier() == null) {
            return null;
        }

        return this.extension.getIdentifier().getValue();
    }

    /**
     * Getter for the button label.
     * 
     * @return the button label
     */
    public String getLabel() {
        return PropertyLoader.loadProperty(this.extension.getLabel());
    }

    /**
     * Getter for the button tooltip.
     * 
     * @return the button tooltip
     */
    public String getTooltip() {
        return PropertyLoader.loadProperty(this.extension.getTooltip());
    }

    /**
     * Getter for the button icon.
     * 
     * @return the button icon
     */
    public String getImage() {
        return PropertyLoader.loadProperty(this.extension.getIcon());
    }

    /**
     * Returns the configured parameter
     * 
     * @return parameter
     */
    public String getParameter() {

        return PropertyLoader.loadProperty(this.extension.getParameter());
    }

    /**
     * Getter for the button action.
     * 
     * @return the button action
     */
    public String getActionId() {
        return PropertyLoader.loadProperty(this.extension.getActionId());
    }

    /**
     * Indicates the enabled/disabled state
     * 
     * @return Returns true if the button may not be clicked
     */
    public boolean isDisabled() {
        String actionId = this.getActionId();
        return (actionId == null) || (actionId.isEmpty());
    }

    @Override
    public JsonElement toJson() {
        JsonMap json = new JsonMap();
        json.add(JSON_ID, this.getId());
        json.add(JSON_LABEL, this.getLabel());
        json.add(JSON_TOOLTIP, this.getTooltip());
        json.add(JSON_IMAGE, this.getImage());
        json.add(JSON_ACTION, this.getActionId());
        json.add(JSON_PARAMETER, this.getParameter());
        json.add(JSON_DISABLED, this.isDisabled());
        json.add(JSON_TYPE, WebElementType.BUTTON.getName());
        return json;
    }
}
