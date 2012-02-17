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
package org.nabucco.framework.base.ui.web.component.perspective;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionMap;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.perspective.PerspectiveAreaExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.perspective.PerspectiveExtension;
import org.nabucco.framework.base.facade.datatype.ui.permission.UiAccessType;
import org.nabucco.framework.base.ui.web.component.WebComposite;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;

/**
 * The perspective area contains multiple perspectives and defines their layout.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public class PerspectiveArea extends WebComposite {

    /** The default serial version UID */
    private static final long serialVersionUID = 1L;

    private static final String JSON_ID = "id";

    private static final String JSON_LABEL = "label";

    private static final String JSON_LAYOUT = "layout";

    private static final String JSON_CURRENT_PERSPECTIVE = "current";

    private static final String JSON_PERSPECTIVES = "perspectives";

    /** The perspective area Extension. */
    private PerspectiveAreaExtension extension;

    /** The current application perspective */
    private Perspective perspective;

    /**
     * Creates a new {@link PerspectiveArea} instance.
     * 
     * @param extension
     *            the perspective area extension
     */
    public PerspectiveArea(PerspectiveAreaExtension extension) {
        super(WebElementType.PERSPECTIVE_AREA, extension);
        if (extension == null) {
            throw new IllegalArgumentException("Cannot create perspective area for extension 'null'.");
        }

        this.extension = extension;
    }

    @Override
    public void init() throws ExtensionException {
        this.loadPerspectives();
    }

    /**
     * Load the web perspectives of the perspective area.
     * 
     * @throws ExtensionException
     *             when a perspective is not configured correctly
     */
    private void loadPerspectives() throws ExtensionException {
        ExtensionMap perspectiveMap = NabuccoSystem.getExtensionResolver().resolveExtensions(
                ExtensionPointType.ORG_NABUCCO_UI_PERSPECTIVE);

        String[] perspectiveIds = perspectiveMap.getExtensionNames();

        List<Perspective> perspectiveList = new ArrayList<Perspective>();

        for (String perspectiveId : perspectiveIds) {
            Perspective perspective = new Perspective((PerspectiveExtension) perspectiveMap.getExtension(perspectiveId));
            perspectiveList.add(perspective);
        }

        Collections.sort(perspectiveList, PerspectiveComparator.getInstance());

        for (Perspective perspective : perspectiveList) {
            this.addElement(perspective.getId(), perspective);
            perspective.init();
        }
    }

    /**
     * Getter for the perspective with the given name.
     * 
     * @param name
     *            name of the perspective
     * 
     * @return the perspective or null if no perspective with the given name exists
     */
    public Perspective getPerspective(String name) {
        WebElement child = super.getElement(name);

        if (child instanceof Perspective) {
            return (Perspective) child;
        }

        return null;
    }

    /**
     * Getter for the perspectives.
     * 
     * @return Returns the perspectives.
     */
    public List<Perspective> getPerspectives() {
        List<Perspective> perspectives = new ArrayList<Perspective>();
        for (WebElement child : super.getElements()) {
            if (child instanceof Perspective) {
                perspectives.add((Perspective) child);
            }
        }
        return perspectives;
    }

    /**
     * Getter for the current perspective.
     * 
     * @return Returns the current perspective.
     */
    public Perspective getCurrentPerspective() {
        return this.perspective;
    }

    /**
     * Setter for the current perspective.
     * 
     * @param perspective
     *            The current perspective to set.
     */
    public void setCurrentPerspective(Perspective perspective) {
        this.perspective = perspective;
    }

    /**
     * Getter for the current perspective id.
     * 
     * @return the current perspective id
     */
    private String getCurrentPerspectiveId() {
        if (this.getCurrentPerspective() != null) {
            return this.getCurrentPerspective().getId();
        }

        for (Perspective perspective : this.getPerspectives()) {
            if (perspective.getAccessType() == UiAccessType.ACTIVE) {
                this.setCurrentPerspective(perspective);
                return perspective.getId();
            }
        }

        return null;
    }

    @Override
    public JsonElement toJson() {

        String layout = PropertyLoader.loadProperty(this.extension.getLayout());
        String currentPerspectiveId = this.getCurrentPerspectiveId();

        JsonMap json = new JsonMap();
        json.add(JSON_LAYOUT, layout);

        JsonList jsonList = new JsonList();
        for (Perspective perspective : this.getPerspectives()) {
            JsonMap jsonEntry = new JsonMap();
            jsonEntry.add(JSON_ID, perspective.getId());
            jsonEntry.add(JSON_LABEL, perspective.getLabel());
            jsonEntry.add(JSON_TOOLTIP, perspective.getTooltip());
            jsonEntry.add(JSON_ACCESS, perspective.getAccessType());
            jsonList.add(jsonEntry);
        }
        json.add(JSON_PERSPECTIVES, jsonList);
        json.add(JSON_CURRENT_PERSPECTIVE, currentPerspectiveId);

        return json;
    }
}
