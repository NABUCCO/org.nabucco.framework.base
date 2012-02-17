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
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.component.work.dashboard.widget.DashboardWidgetType;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.WebModel;

/**
 * DashboardWidgetModel
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public abstract class DashboardWidgetModel extends WebModel {

    private DashboardWidgetType type;

    /**
     * 
     * Creates a new {@link DashboardWidgetModel} instance.
     * 
     * @param analyser
     *            the analyser to use for the widget
     */
    public DashboardWidgetModel(DashboardWidgetType type) {
        if (type == null) {
            throw new IllegalArgumentException("Cannot instanciate Graphic Widget. The type is 'null'");
        }

        this.type = type;
    }
    
    public abstract void setContent(List<NabuccoDatatype> values) throws ClientException;

    /**
     * Getter for the type of the widget model
     * 
     * @return the type of the model
     */
    public DashboardWidgetType getType() {
        return this.type;
    }

    @Override
    public JsonElement toJson() {
        JsonMap jsonMap = new JsonMap();
        return jsonMap;
    }

}
