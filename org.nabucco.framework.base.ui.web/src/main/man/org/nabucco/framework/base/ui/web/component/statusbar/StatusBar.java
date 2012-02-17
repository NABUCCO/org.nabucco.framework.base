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
package org.nabucco.framework.base.ui.web.component.statusbar;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.GridWidgetExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.statusbar.StatusBarExtension;
import org.nabucco.framework.base.ui.web.component.WebComposite;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.common.grid.GridWidgetElement;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;

/**
 * The statusbar holds technical and legal information.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class StatusBar extends WebComposite {

    /** The default serial version UID */
    private static final long serialVersionUID = 1L;

    private static final String JSON_GRIDS = "grids";

    private static final String JSON_HEIGHT = "height";

    /** The StatusBar Extension. */
    private StatusBarExtension extension;

    private int height;

    /**
     * Creates a new {@link StatusBar} instance.
     * 
     * @param extension
     *            the statusbar extension
     */
    public StatusBar(StatusBarExtension extension) {
        super(WebElementType.STATUSBAR, extension);
        if (extension == null) {
            throw new IllegalArgumentException("Cannot create statusbar for extension 'null'.");
        }
        this.extension = extension;

    }

    @Override
    public void init() throws ExtensionException {
        this.setHeight(PropertyLoader.loadProperty(this.extension.getHeight()));

        for (GridWidgetExtension gridExtension : this.extension.getGrids()) {
            String gridId = PropertyLoader.loadProperty(gridExtension.getIdentifier());

            GridWidgetElement statusbarPart = new GridWidgetElement(gridExtension);
            statusbarPart.init();
            this.addElement(gridId, statusbarPart);
        }
    }

    /**
     * Getter for the grid element.
     * 
     * @return the grid element, or null if none is contained
     */
    public GridWidgetElement getGrid() {
        for (WebElement element : this.getElements()) {
            if (element instanceof GridWidgetElement) {
                return (GridWidgetElement) element;
            }
        }

        return null;
    }

    /**
     * Setter for the height.
     * 
     * @param height
     *            The height to set.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Getter for the height.
     * 
     * @return Returns the height.
     */
    public int getHeight() {
        return this.height;
    }

    @Override
    public JsonElement toJson() {
        JsonMap retVal = new JsonMap();
        retVal.add(JSON_HEIGHT, this.height);
        JsonList gridList = new JsonList();
        for (WebElement element : this.getElements()) {
            gridList.add(element.toJson());
        }
        retVal.add(JSON_GRIDS, gridList);
        return retVal;
    }
}
