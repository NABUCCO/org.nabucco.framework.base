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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.nabucco.framework.base.ui.web.component.common.grid.gridcontroller.GridController;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.dialog.controls.DialogGridModelElement;

/**
 * GridDialogModel
 * 
 * The model for the dialog with a grid
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class GridDialogModel extends MessageDialogModel {

    private static final String JSON_GRID = "grid";

    private static final String JSON_CONTROLS = "controls";

    private String grid;

    private String id;

    private GridController gridController;

    private Map<String, DialogGridModelElement> controls = new LinkedHashMap<String, DialogGridModelElement>();

    /**
     * 
     * Creates a new {@link GridDialogModel} instance.
     * 
     * @param id
     *            the id of the grid element
     * @param message
     *            the message to be shown in the dialog
     * @param grid
     *            the grid to use
     */
    public GridDialogModel(String id, String message, String grid) {
        super(message);

        this.grid = grid;
        this.id = id;
    }

    @Override
    public void init() {
        this.gridController = new GridController(this.id, this.grid);
    }

    /**
     * Returns control ids
     * 
     * @return the list with ids of containing controls
     */
    public List<String> getControlIds() {
        return new ArrayList<String>(this.controls.keySet());
    }

    /**
     * Getter for the control with given id
     * 
     * @param id
     *            the id of the control
     * @return model of control or null id not found
     */
    public DialogGridModelElement getControl(String id) {
        if (!this.controls.containsKey(id)) {
            return null;
        }
        return this.controls.get(id);
    }

    /**
     * Adds a dialogmodel to a grid
     * 
     * @param model
     */
    public void addDialogControlModel(DialogGridModelElement model) {
        if (this.gridController == null) {
            throw new IllegalStateException("Grid Dialog Model is not initialized. Cannot add control element.");
        }
        this.gridController.addElement(model.getId(), model.getPosition(), model.getHint());
        this.controls.put(model.getId(), model);
    }

    @Override
    public boolean isValid() {
        for (DialogGridModelElement control : this.controls.values()) {
            if (!control.isValid()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Getter for the grid.
     * 
     * @return Returns the grid.
     */
    public String getGrid() {
        return this.grid;
    }

    @Override
    public JsonElement toJson() {
        JsonMap json = (JsonMap) super.toJson();
        json.add(JSON_GRID, this.gridController.toJson());

        JsonList controls = new JsonList();
        for (DialogGridModelElement control : this.controls.values()) {
            controls.add(control.toJson());
        }
        json.add(JSON_CONTROLS, controls);
        return json;
    }

}
