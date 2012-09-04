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
package org.nabucco.framework.base.ui.web.model.editor.gridelement;

import java.beans.PropertyChangeEvent;

import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.editor.ControlType;
import org.nabucco.framework.base.ui.web.model.editor.EditorGridElementModel;
import org.nabucco.framework.base.ui.web.model.editor.util.dependency.DependencyController;

/**
 * LinkElementModel
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class LinkElementModel extends EditorGridElementModel {

    private static final String JSON_ICON = "icon";

    private static final String JSON_URL = "url";

    private static final String JSON_LINK_TYPE = "linkType";

    private String label;

    private String tooltip;

    private String url;

    private String action;

    private String icon;

    private LinkElementType linktype;

    /**
     * 
     * Creates a new {@link LinkElementModel} instance.
     * 
     * @param id
     *            the id of the link element
     * @param dependencyController
     *            the dependency controller of the element
     * @param label
     *            the label of element
     * @param tooltip
     *            the tooltip
     * @param icon
     *            the icon to show
     * @param url
     *            the url to open by external links
     * @param action
     *            the action to call by internal links
     * @param linktype
     *            the type of the link (internal or external)
     */
    public LinkElementModel(String id, DependencyController dependencyController, String label, String tooltip,
            String icon, String url, String action, LinkElementType linktype) {
        super(id, dependencyController);
        this.label = label;
        this.tooltip = tooltip;
        this.url = url;
        this.action = action;
        this.icon = icon;
        this.linktype = linktype;
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        super.setRefreshNeeded(true);
    }

    @Override
    public JsonMap toJson() {
        JsonMap json = super.toJson();

        json.add(JSON_TYPE, ControlType.LINK);

        json.add(JSON_LABEL, label);
        json.add(JSON_TOOLTIP, tooltip);
        json.add(JSON_ICON, icon);

        json.add(JSON_ACTION, action);
        json.add(JSON_URL, url);
        json.add(JSON_LINK_TYPE, linktype.name());

        return json;
    }

}
