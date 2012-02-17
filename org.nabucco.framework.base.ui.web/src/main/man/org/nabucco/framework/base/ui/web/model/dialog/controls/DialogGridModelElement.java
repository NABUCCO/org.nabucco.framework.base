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
package org.nabucco.framework.base.ui.web.model.dialog.controls;

import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.WebModel;
import org.nabucco.framework.base.ui.web.model.dialog.DialogControlType;

/**
 * DialogGridElement
 * 
 * The abstract class that need to be inherited for all dialog control element models
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public abstract class DialogGridModelElement extends WebModel {

    private String id;

    private String label;

    private String tooltip;

    private String position;

    private DialogControlType type;


    /**
     * 
     * Creates a new {@link DialogControlModel} instance.
     * 
     * @param id
     *            The id of the control
     * @param label
     *            the label of the control
     * @param tooltip
     *            the tooltip of the control
     * @param type
     *            the type of the control
     * @param position
     *            the position of the control
     */
    public DialogGridModelElement(String id, String label, String tooltip, DialogControlType type, String position) {
        if (id == null) {
            throw new IllegalArgumentException("The id of the dialog element is 'null'.");
        }
        if (type == null) {
            throw new IllegalArgumentException("The type of the dialog element is 'null'.");
        }
        if (position == null) {
            throw new IllegalArgumentException("The position of the dialog element is 'null'.");
        }

        this.id = id;
        this.type = type;
        this.position = position;

        this.label = label;
        this.tooltip = tooltip;


    }

    @Override
    public void init() {
    }

    /**
     * Checks if the control has a valid state
     * 
     * @return true if valid or false if not
     */
    public boolean isValid() {
        return true;
    }

    /**
     * Getter for the id.
     * 
     * @return Returns the id.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Getter for the label.
     * 
     * @return Returns the label.
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * Getter for the tooltip.
     * 
     * @return Returns the tooltip.
     */
    public String getTooltip() {
        return this.tooltip;
    }

    /**
     * Getter for the position.
     * 
     * @return Returns the position.
     */
    public String getPosition() {
        return this.position;
    }

    /**
     * Getter for the hint
     * 
     * @return Empty String because is not implemented yet
     */
    public String getHint() {
        return "";
    }

    /**
     * Getter for the type.
     * 
     * @return Returns the type.
     */
    public DialogControlType getType() {
        return this.type;
    }


    @Override
    public JsonMap toJson() {
        JsonMap retVal = new JsonMap();
        retVal.add(JSON_ID, this.id);
        retVal.add(JSON_LABEL, this.label);
        retVal.add(JSON_TOOLTIP, this.tooltip);
        retVal.add(JSON_TYPE, this.type);


        return retVal;
    }

}
