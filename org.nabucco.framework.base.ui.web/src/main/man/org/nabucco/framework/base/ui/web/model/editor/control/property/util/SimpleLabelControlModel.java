/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco-source.org/nabucco-license.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.ui.web.model.editor.control.property.util;

import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.editor.ControlType;
import org.nabucco.framework.base.ui.web.model.editor.EditorGridElementModel;
import org.nabucco.framework.base.ui.web.model.editor.util.dependency.DependencyController;

/**
 * SimpleLabelControlModel
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class SimpleLabelControlModel extends EditorGridElementModel {

    /**
     * 
     * Creates a new {@link SimpleLabelControlModel} instance.
     * 
     * @param id
     *            id of the element
     * @param dependencyController
     *            the dependency controller instance
     */
    public SimpleLabelControlModel(String id, DependencyController dependencyController) {
        super(id, dependencyController);
    }

    @Override
    public JsonMap toJson() {

        JsonMap json = super.toJson();

        json.add(JSON_TYPE, ControlType.LABEL);

        return json;
    }
}
