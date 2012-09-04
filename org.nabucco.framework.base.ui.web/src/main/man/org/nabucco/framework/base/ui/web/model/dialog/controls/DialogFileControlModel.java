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
import org.nabucco.framework.base.ui.web.model.dialog.DialogControlType;

/**
 * Dialog File Control Model
 * 
 * The model of the dialog file control.
 * 
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DialogFileControlModel extends DialogControlModel {

    private static final String JSON_EXTFILTER = "extensionFilter";

    private String filterExtension;

    /**
     * 
     * Creates a new {@link DialogFileControlModel} instance.
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
     * @param mandatory
     *            is the field mandatory
     */
    public DialogFileControlModel(String id, String label, String tooltip, DialogControlType type, String position,
            boolean mandatory, String filterExtension) {

        super(id, label, tooltip, DialogControlType.FILE, position, mandatory, true);

        this.filterExtension = filterExtension;
    }

    @Override
    public JsonMap toJson() {
        JsonMap json = super.toJson();

        json.add(JSON_EXTFILTER, filterExtension);

        return json;
    }

}
