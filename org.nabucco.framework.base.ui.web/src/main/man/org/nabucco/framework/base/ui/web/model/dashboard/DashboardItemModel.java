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
package org.nabucco.framework.base.ui.web.model.dashboard;

import java.util.LinkedHashMap;
import java.util.Map;

import org.nabucco.framework.base.ui.web.component.common.grid.gridcontroller.GridController;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.dashboard.widget.DashboardWidgetModel;
import org.nabucco.framework.base.ui.web.model.work.WorkItemModel;

/**
 * DashboardItemModel
 * 
 * This model
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DashboardItemModel extends WorkItemModel {

    private static final String JSON_GRID = "grid";

    private String grid;
    
    private String id;

    private GridController gridController;

    private Map<String, DashboardWidgetModel> widgets = new LinkedHashMap<String, DashboardWidgetModel>();

    /**
     * Creates a new {@link DashboardItemModel} instance.
     * 
     * @param id
     *            the id of the dashboardItem
     * @param label
     *            the label of the dashboard
     * @param tooltip
     *            the tooltip of the dashboard
     * @param image
     *            the icon
     * @param grid
     *            the grid of the dashboard
     */
    public DashboardItemModel(String id, String label, String tooltip, String image, String grid) {
        super(label, tooltip, image);
        if (grid == null) {
            throw new IllegalArgumentException("Cannot create Dashboard Item. Grid is 'null'");
        }
        if (id == null) {
            throw new IllegalArgumentException("Cannot create Dashboard Item. Id is 'null'");
        }
        this.grid = grid;
        this.id = id;
    }

    @Override
    public void init() {
        this.gridController = new GridController(this.id, this.grid);
    }

    /**
     * Adds the new widget to the dashboard
     * 
     * @param id
     *            the id of the element on the dashboard
     * @param widgetModel
     *            the widget model
     * @param position
     *            the position within grid
     * @param hint
     *            the hind over the widget element
     */
    public void addWidget(String id, DashboardWidgetModel widgetModel, String position, String hint) {

        this.gridController.addElement(id, position, hint);

        this.widgets.put(id, widgetModel);
    }

    @Override
    public JsonElement toJson() {
        JsonMap retVal = (JsonMap) super.toJson();

        retVal.add(JSON_ID, this.id);
        retVal.add(JSON_GRID, this.gridController.toJson());

        return retVal;
    }

}
