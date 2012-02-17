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
package org.nabucco.framework.base.ui.web.model.control.property.action;

import org.nabucco.framework.base.facade.datatype.NString;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.control.ControlType;
import org.nabucco.framework.base.ui.web.model.control.property.PropertyControlModel;
import org.nabucco.framework.base.ui.web.model.control.util.dependency.DependencyController;

/**
 * LinkControlModel
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class LinkControlModel extends PropertyControlModel<NString> {

    private String action;

    /**
     * 
     * Creates a new {@link LinkControlModel} instance.
     * 
     * @param propertyPath
     *            path to the property
     * @param action
     *            action to fire
     * @param dependencyController
     *            the dependency controller
     */
    public LinkControlModel(String id, String propertyPath, String action,
            DependencyController dependencyController) {
        super(id, propertyPath, ControlType.LINK, null, dependencyController, false);
        this.setAction(action);
    }

    @Override
    public NString parse(String value) {
        // Link can not become another action as programmed
        return null;
    }

    /**
     * Setter for the action.
     * 
     * @param action
     *            The action to set.
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Getter for the action.
     * 
     * @return Returns the action.
     */
    public String getAction() {
        return this.action;
    }

    @Override
    public JsonMap toJson() {
        JsonMap json = super.toJson();

        json.add(JSON_VALUE, this.getAction());

        return json;
    }
}
