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
package org.nabucco.framework.base.ui.web.component.common.grid.gridcontroller;

import java.util.HashMap;
import java.util.Map;

import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;

/**
 * GridController Controller for the Grid positions
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class GridController {

    private static final String JSON_ROWS = "rows";

    private static final String JSON_COLUMNS = "cols";

    Map<String, GridElementPosition> elements;

    private String gridId;

    /** Positions of the grid */
    private String gridStart;

    private String gridEnd;

    private int gridWidth;

    private int gridHeight;

    /**
     * 
     * Creates a new {@link GridController} instance.
     * 
     * @param gridDefinition
     */
    public GridController(String gridId, String gridDefinition) {
        this.elements = new HashMap<String, GridElementPosition>();
        this.setGridId(gridId);
        this.initGridPosition(gridDefinition);
    }

    /**
     * Add new element to the Grid
     * 
     * @param id
     *            id of the element
     * @param position
     *            position of the element
     * @param hint
     *            hint of the element
     */
    public void addElement(String id, String position, String hint) {
        GridElementPosition ep = new GridElementPosition(id, position, hint);
        this.elements.put(id, ep);
    }

    /**
     * Getter for the gridEnd.
     * 
     * @return Returns the gridEnd.
     */
    private String getGridEnd() {
        return this.gridEnd;
    }

    /**
     * Getter for the gridHeight.
     * 
     * @return Returns the gridHeight.
     */
    public int getGridHeight() {
        return this.gridHeight;
    }

    /**
     * Getter for the gridId.
     * 
     * @return Returns the gridId.
     */
    public String getGridId() {
        return this.gridId;
    }

    /**
     * Getter for the gridStart.
     * 
     * @return Returns the gridStart.
     */
    private String getGridStart() {
        return this.gridStart;
    }

    /**
     * Getter for the gridWidth.
     * 
     * @return Returns the gridWidth.
     */
    private int getGridWidth() {
        return this.gridWidth;
    }

    /**
     * Initialize grid position
     * 
     * @param position
     */
    private void initGridPosition(String position) {

        // Start and End
        String[] positions = position.split("-");

        if (positions.length != 2 || positions[0].isEmpty()) {
            throw new IllegalArgumentException("Cannot resolve grid of tab [" + this.getGridId() + "].");
        }

        this.setGridStart(positions[0]);
        this.setGridEnd(positions[1]);

        // Height
        String start = this.getGridStart();
        String end = this.getGridEnd();

        int startHeight = Integer.parseInt(start.substring(1));
        int endHeight = Integer.parseInt(end.substring(1));

        this.setGridHeight(endHeight - startHeight + 1);

        // Width
        Character startCharacter = start.charAt(0);
        Character endCharacter = end.charAt(0);

        this.setGridWidth(endCharacter.compareTo(startCharacter) + 1);
    }

    /**
     * Setter for the gridEnd.
     * 
     * @param gridEnd
     *            The gridEnd to set.
     */
    private void setGridEnd(String gridEnd) {
        this.gridEnd = gridEnd;
    }

    /**
     * Setter for the gridHeight.
     * 
     * @param gridHeight
     *            The gridHeight to set.
     */
    private void setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
    }

    /**
     * Setter for the gridId.
     * 
     * @param gridId
     *            The gridId to set.
     */
    private void setGridId(String gridId) {
        this.gridId = gridId;
    }

    /**
     * Setter for the gridStart.
     * 
     * @param gridStart
     *            The gridStart to set.
     */
    private void setGridStart(String gridStart) {
        this.gridStart = gridStart;
    }

    /**
     * Setter for the gridWidth.
     * 
     * @param gridWidth
     *            The gridWidth to set.
     */
    private void setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
    }

    public JsonElement toJson() {
        JsonMap retVal = new JsonMap();

        retVal.add(JSON_ROWS, this.getGridHeight());
        retVal.add(JSON_COLUMNS, this.getGridWidth());

        JsonList gridList = new JsonList();
        for (String elementKey : this.elements.keySet()) {
            gridList.add(this.elements.get(elementKey).toJson());
        }
        retVal.add("elements", gridList);

        return retVal;
    }
}
