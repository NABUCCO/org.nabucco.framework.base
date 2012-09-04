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
package org.nabucco.framework.base.ui.web.model.dashboard.widget;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.ui.web.component.work.dashboard.widget.DashboardWidgetType;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.table.TableModel;

/**
 * TableWidgetModel
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class TableWidgetModel extends DashboardWidgetModel {

    private TableModel<NabuccoDatatype> table;

    /**
     * Creates a new {@link TableWidgetModel} instance.
     * 
     * @param type
     */
    public TableWidgetModel(TableModel<NabuccoDatatype> table) {
        super(DashboardWidgetType.TABLE);
        this.table = table;
    }

    @Override
    public void setContent(String filterId, List<NabuccoDatatype> values, String viewName) {
        this.table.firstPage();
        this.table.setContent(values);
    }

    @Override
    public void init() {
        this.table.init();
    }

    @Override
    public JsonElement toJson() {
        JsonMap retVal = (JsonMap) super.toJson();
        retVal.add(JSON_VALUE, this.table.toJson());
        return retVal;
    }

}
