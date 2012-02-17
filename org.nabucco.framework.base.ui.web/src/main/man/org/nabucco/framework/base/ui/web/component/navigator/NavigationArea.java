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
package org.nabucco.framework.base.ui.web.component.navigator;

import java.util.ArrayList;
import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionMap;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.navigator.NavigationAreaExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.navigator.NavigatorExtension;
import org.nabucco.framework.base.ui.web.component.WebComposite;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;

/**
 * The navigator area defines the navigator layout of a perspective.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class NavigationArea extends WebComposite {

    /** The default serial version UID */
    private static final long serialVersionUID = 1L;

    private static final String JSON_NAVIGATORS = "navigators";

    /** The NavigatorArea Extension. */
    private NavigationAreaExtension extension;

    /** The ID of the perspective. */
    private String perspectiveId;

    /**
     * Creates a new {@link NavigationArea} instance.
     * 
     * @param navigatorAreaExtension
     */
    public NavigationArea(NavigationAreaExtension extension, String perspectiveId) {
        super(WebElementType.NAVIGATION_AREA, extension);
        if (extension == null) {
            throw new IllegalArgumentException("Cannot create navigator area for extension 'null'.");
        }
        this.extension = extension;
        this.perspectiveId = perspectiveId;
    }

    @Override
    public void init() throws ExtensionException {
        this.loadNavigators();
    }

    /**
     * Load the web navigators of the navigator area.
     * 
     * @throws ExtensionException
     *             when a navigator is not configured correctly
     */
    private void loadNavigators() throws ExtensionException {

        if (this.extension.getIdentifier() == null || this.extension.getIdentifier().getValue() == null) {
            return;
        }

        ExtensionMap navigatorMap = NabuccoSystem.getExtensionResolver().resolveExtensions(
                ExtensionPointType.ORG_NABUCCO_UI_NAVIGATOR);

        String[] navigatorIds = navigatorMap.getExtensionNames();

        for (String navigatorId : navigatorIds) {

            NavigatorExtension navigatorExtension = (NavigatorExtension) navigatorMap.getExtension(navigatorId);

            String perspective = PropertyLoader.loadProperty(navigatorExtension.getPerspective());

            if (perspective == null || perspective.isEmpty()) {
                continue;
            }

            if (perspective.equalsIgnoreCase(this.perspectiveId)) {
                Navigator navigator = new Navigator(navigatorExtension);
                this.addElement(navigatorId, navigator);
                navigator.init();
            }
        }
    }

    /**
     * Getter for the navigator with the given id.
     * 
     * @param name
     *            name of the navigator
     * 
     * @return the navigator or null if no navigator with the given name exists
     */
    public Navigator getNavigator(String id) {
        WebElement child = super.getElement(id);
        if (child instanceof Navigator) {
            return (Navigator) child;
        }
        return null;
    }

    /**
     * Getter for all navigators.
     * 
     * @return Returns the navigators.
     */
    public List<Navigator> getNavigators() {
        List<Navigator> navigators = new ArrayList<Navigator>();
        for (WebElement child : super.getElements()) {
            if (child instanceof Navigator) {
                navigators.add((Navigator) child);
            }
        }
        return navigators;
    }

    /**
     * Getter for the navigation area layout.
     * 
     * @return the layout
     */
    public String getLayout() {
        return PropertyLoader.loadProperty(this.extension.getLayout());
        // return NavigationAreaLayoutType.valueOf(layout);
    }

    @Override
    public JsonElement toJson() {

        JsonMap json = new JsonMap();
        JsonList jsonList = new JsonList();

        String[] elementIds = super.getElementIds();
        for (int i = 0; i < elementIds.length; i++) {

            Navigator navigator = (Navigator) super.getElement(elementIds[i]);

            JsonMap jsonEntry = new JsonMap();
            jsonEntry.add(JSON_ID, elementIds[i]);
            jsonEntry.add(JSON_LABEL, navigator.getLabel());

            jsonList.add(jsonEntry);
        }

        json.add(JSON_LAYOUT, this.getLayout());
        json.add(JSON_NAVIGATORS, jsonList);

        return json;
    }

}
