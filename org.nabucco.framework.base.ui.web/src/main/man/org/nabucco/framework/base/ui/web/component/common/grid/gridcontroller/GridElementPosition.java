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

import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonMap;

/**
 * GridElementPosition
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 * @date 15.09.2011
 */
public class GridElementPosition {

    private static final String JSON_ID = "id";

    private static final String JSON_COLUMN = "col";

    private static final String JSON_ROW = "row";

    private static final String JSON_HEIGHT = "height";

    private static final String JSON_WIDTH = "width";

    private static final String JSON_HINT = "hint";

    private String elementId;

    private String position;

    private String hint;

    /** Positions of the element */
    private String elementStart;

    private String elementEnd;

    private int elementWidth;

    private int elementHeight;

    private Integer startRow;

    private Integer startColumn;

    /**
     * 
     * Creates a new {@link GridElementPosition} instance.
     * 
     * @param id
     *            Id of the element
     * @param position
     *            position of the element
     * @param hint
     *            hint notice
     */
    public GridElementPosition(String id, String position, String hint) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot create Grid Element position element. Id is 'null'");
        }
        if (position == null) {
            throw new IllegalArgumentException("Cannot create Grid Element position element. Position is 'null'");
        }
        this.position = position;
        this.hint = hint;
        this.elementId = id;

        this.initElementPosition();
    }

    /**
     * Getter for the elementEnd.
     * 
     * @return Returns the elementEnd.
     */
    public String getElementEnd() {
        return this.elementEnd;
    }

    /**
     * Getter for the gridHeight.
     * 
     * @return Returns the gridHeight.
     */
    public int getElementHeight() {
        return this.elementHeight;
    }



    /**
     * Getter for the elementId.
     * 
     * @return Returns the elementId.
     */
    public String getElementId() {
        return this.elementId;
    }



    /**
     * Getter for the elementStart.
     * 
     * @return Returns the elementStart.
     */
    public String getElementStart() {
        return this.elementStart;
    }

    /**
     * Getter for the gridWidth.
     * 
     * @return Returns the gridWidth.
     */
    public int getElementWidth() {
        return this.elementWidth;
    }

    /**
     * Getter for the editor control position hint.
     * 
     * @return the editor control position hint
     */
    public String getHint() {
        return this.hint;
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
     * Getter for the startColumn.
     * 
     * @return Returns the startColumn.
     */
    public Integer getStartColumn() {
        return this.startColumn;
    }

    /**
     * Getter for the startRow.
     * 
     * @return Returns the startRow.
     */
    public Integer getStartRow() {
        return this.startRow;
    }

    private void initElementPosition() {
        // Start and End
        String[] positions = this.position.split("-");

        if (positions.length != 2 || positions[0].isEmpty()) {
            throw new IllegalArgumentException("Cannot resolve position of control [" + this.getElementId() + "].");
        }

        this.setElementStart(positions[0]);
        this.setElementEnd(positions[1]);

        // Height
        String start = this.getElementStart();
        String end = this.getElementEnd();

        int startHeight = Integer.parseInt(start.substring(1));
        int endHeight = Integer.parseInt(end.substring(1));

        this.setElementHeight(endHeight - startHeight + 1);

        // Width
        Character startCharacter = start.charAt(0);
        Character endCharacter = end.charAt(0);

        this.setElementWidth(endCharacter.compareTo(startCharacter) + 1);

        // Start row and column
        this.setStartColumn(Character.getNumericValue(startCharacter) - 10);
        this.setStartRow(startHeight);
    }

    /**
     * Setter for the gridEnd.
     * 
     * @param gridEnd
     *            The gridEnd to set.
     */
    private void setElementEnd(String gridEnd) {
        this.elementEnd = gridEnd;
    }



    /**
     * Setter for the gridHeight.
     * 
     * @param gridHeight
     *            The gridHeight to set.
     */
    private void setElementHeight(int gridHeight) {
        this.elementHeight = gridHeight;
    }

    /**
     * Setter for the gridStart.
     * 
     * @param gridStart
     *            The gridStart to set.
     */
    private void setElementStart(String gridStart) {
        this.elementStart = gridStart;
    }

    /**
     * Setter for the gridWidth.
     * 
     * @param gridWidth
     *            The gridWidth to set.
     */
    private void setElementWidth(int gridWidth) {
        this.elementWidth = gridWidth;
    }

    /**
     * Setter for the startColumn.
     * 
     * @param startColumn
     *            The startColumn to set.
     */
    private void setStartColumn(Integer startColumn) {
        this.startColumn = startColumn;
    }

    /**
     * Setter for the startRow.
     * 
     * @param startRow
     *            The startRow to set.
     */
    private void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public JsonElement toJson() {
        JsonMap json = new JsonMap();
        json.add(JSON_ID, this.getElementId());
        json.add(JSON_ROW, this.getStartRow());
        json.add(JSON_COLUMN, this.getStartColumn());
        json.add(JSON_HEIGHT, this.getElementHeight());
        json.add(JSON_WIDTH, this.getElementWidth());
        json.add(JSON_HINT, this.getHint());

        return json;
    }
}
