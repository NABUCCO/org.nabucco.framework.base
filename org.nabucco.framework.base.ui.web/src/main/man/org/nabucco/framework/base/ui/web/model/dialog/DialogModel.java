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
package org.nabucco.framework.base.ui.web.model.dialog;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.WebModel;
import org.nabucco.framework.base.ui.web.servlet.util.path.NabuccoServletPathType;

/**
 * DialogModel
 * 
 * The dialog model represents the model for the generic dialog elements
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public abstract class DialogModel extends WebModel {

    private static final String JSON_BUTTONS = "buttons";

    private static final String JSON_PARAMETERS = "parameters";

    private Map<NabuccoServletPathType, String> parameters = new HashMap<NabuccoServletPathType, String>();

    /** The buttons that are shown in the dialog */
    private Map<String, DialogButton> buttons = new LinkedHashMap<String, DialogButton>();

    /**
     * Creates a new {@link DialogModel} instance.
     * 
     * Standard DialogModel
     * 
     * @param id
     *            the id of the dialof
     */
    public DialogModel() {
    }

    /**
     * Add a new parameter to the dialog model.
     * 
     * The given parameters will be included in the communication url as the parameters.
     * 
     * 
     * @param type
     *            the type of parameter
     * @param value
     *            the value
     */
    public void addParameter(NabuccoServletPathType type, String value) {
        this.parameters.put(type, value);
    }

    /**
     * Return the parameter for the given type
     * 
     * @param type
     *            the type to search for
     * @return the parameter
     */
    public String getParameter(NabuccoServletPathType type) {
        return this.parameters.get(type);
    }

    /**
     * Adds a new Button to the dialog
     * 
     * @param button
     *            the button to be added
     */
    public void addButton(DialogButton button) {
        if (button == null) {
            throw new IllegalArgumentException("Cannot add the button to the dialog, the instance is 'null'");
        }

        this.buttons.put(button.getId(), button);
    }

    /**
     * Return the button for the given button identifier (0,1,2...)
     * 
     * @param id
     *            the identifier (0,1,2...)
     * @return the Button instance
     */
    public DialogButton getButtonForId(String id) {
        return this.buttons.get(id);
    }

    /**
     * Checks if the dialog model is in the valid state
     * 
     * @return true if valid or false if not
     */
    public boolean isValid() {
        return true;
    }

    @Override
    public JsonElement toJson() {
        JsonMap retVal = new JsonMap();

        JsonList buttonsList = new JsonList();
        for (DialogButton button : this.buttons.values()) {
            buttonsList.add(button.toJson());
        }
        retVal.add(JSON_BUTTONS, buttonsList);

        JsonMap parameters = new JsonMap();
        for (Entry<NabuccoServletPathType, String> entry : this.parameters.entrySet()) {
            parameters.add(entry.getKey().getValue(), entry.getValue());
        }

        retVal.add(JSON_PARAMETERS, parameters);

        return retVal;
    }
}
