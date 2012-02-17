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
package org.nabucco.framework.base.ui.web.model.widget;

import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.WebModel;

/**
 * WidgetModel
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public abstract class WidgetModel extends WebModel {

    private WidgetType type;

    /**
     * 
     * Creates a new {@link WidgetModel} instance.
     * 
     * @param type
     *            Widget type
     */
    public WidgetModel(WidgetType type) {
        this.type = type;
    }

    @Override
    public final void init() {

    }

    /**
     * Setter for the type.
     * 
     * @param type
     *            The type to set.
     */
    protected void setType(WidgetType type) {
        this.type = type;
    }

    /**
     * Getter for the type.
     * 
     * @return Returns the type.
     */
    protected WidgetType getType() {
        return this.type;
    }

    @Override
    public JsonMap toJson() {
        JsonMap json = new JsonMap();
        json.add(JSON_TYPE, this.getType());

        return json;
    }
}
