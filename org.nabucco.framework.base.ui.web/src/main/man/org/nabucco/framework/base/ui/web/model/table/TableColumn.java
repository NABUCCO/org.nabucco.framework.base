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
package org.nabucco.framework.base.ui.web.model.table;

import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.json.Jsonable;
import org.nabucco.framework.base.ui.web.model.table.renderer.ColumnRenderer;

/**
 * Definition for one table colum.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class TableColumn implements Jsonable {

    private static final String JSON_RENDERER = "renderer";

    private static final String JSON_VISIBLE = "visible";

    private static final String JSON_SORTABLE = "sortable";

    private static final String JSON_WIDTH = "width";

    /** Table column id */
    private String id;

    /** Table column property name */
    private String property;

    /** Internationalized label of the table column */
    private String label;

    /** Internationalized tooltip of the table column */
    private String tooltip;

    /** Table colum is visible */
    private boolean visible;

    /** Table column is sortable */
    private boolean sortable;

    /** The width of the column in % */
    private String width;

    /** Renderer for the formating of output */
    private ColumnRenderer renderer;

    /**
     * Creates a new {@link TableColumn} instance.
     * 
     * @param id
     *            the column id
     */
    public TableColumn(String id, String property) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot create table column for id [null].");
        }
        if (property == null) {
            throw new IllegalArgumentException("Cannot create table column for property [null].");
        }
        this.id = id;
        this.property = property;
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
     * Getter for the property.
     * 
     * @return Returns the property.
     */
    public String getPropertyPath() {
        return this.property;
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
     * Setter for the label.
     * 
     * @param label
     *            The label to set.
     */
    public void setLabel(String label) {
        this.label = label;
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
     * Setter for the tooltip.
     * 
     * @param tooltip
     *            The tooltip to set.
     */
    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * Getter for the visible.
     * 
     * @return Returns the visible.
     */
    public boolean isVisible() {
        return this.visible;
    }

    /**
     * Setter for the visible.
     * 
     * @param visible
     *            The visible to set.
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Getter for the sortable.
     * 
     * @return Returns the sortable.
     */
    public boolean isSortable() {
        return this.sortable;
    }

    /**
     * Setter for the sortable.
     * 
     * @param sortable
     *            The sortable to set.
     */
    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    /**
     * Setter for the width.
     * 
     * @param width
     *            The width to set.
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     * Setter for the width.
     * 
     * @param width
     *            The width to set.
     */
    public void setWidth(int width) {
        this.width = String.valueOf(width);
    }

    /**
     * Getter for the width.
     * 
     * @return Returns the width.
     */
    public String getWidth() {
        return this.width;
    }

    /**
     * Setter for the renderer.
     * 
     * @param renderer
     *            The renderer to set.
     */
    public void setRenderer(ColumnRenderer renderer) {
        this.renderer = renderer;
    }

    /**
     * Getter for the renderer.
     * 
     * @return Returns the renderer.
     */
    public ColumnRenderer getRenderer() {
        return this.renderer;
    }

    @Override
    public JsonElement toJson() {
        JsonMap json = new JsonMap();
        json.add(JSON_ID, this.getId());
        json.add(JSON_LABEL, this.getLabel());
        json.add(JSON_TOOLTIP, this.getTooltip());
        json.add(JSON_VISIBLE, this.isVisible());
        json.add(JSON_SORTABLE, this.isSortable());
        if (this.getWidth() != null && !this.getWidth().isEmpty()) {
            json.add(JSON_WIDTH, this.getWidth());
        }
        if (this.getRenderer() != null) {
            json.add(JSON_RENDERER, this.getRenderer().toJson());
        }
        return json;
    }

    @Override
    public String toString() {
        return this.getId() + " [" + this.getLabel() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        TableColumn other = (TableColumn) obj;
        if (this.id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

}
