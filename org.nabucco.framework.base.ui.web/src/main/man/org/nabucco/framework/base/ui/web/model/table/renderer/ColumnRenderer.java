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
package org.nabucco.framework.base.ui.web.model.table.renderer;

import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.json.Jsonable;
import org.nabucco.framework.base.ui.web.model.common.renderer.WebRenderer;
import org.nabucco.framework.base.ui.web.model.common.renderer.WebRendererSupport;

/**
 * Renderer for column cells.
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public abstract class ColumnRenderer extends WebRendererSupport implements Jsonable, WebRenderer {

    private static final String JSON_LAYOUT = "layout";

    /** Layout for the output */
    private ColumnLayoutType layout;

    /**
     * 
     * Creates a new {@link ColumnRenderer} instance.
     * 
     * @param layout
     */
    public ColumnRenderer(ColumnLayoutType layout) {
        this.setLayout(layout);
    }

    /**
     * Setter for the layout.
     * 
     * @param layout
     *            The layout to set.
     */
    public void setLayout(ColumnLayoutType layout) {
        this.layout = layout;
    }

    /**
     * Getter for the layout.
     * 
     * @return Returns the layout.
     */
    public ColumnLayoutType getLayout() {
        return this.layout;
    }

    /**
     * Getter for the default column renderer.
     * 
     * @param layout
     *            the column layout type
     * 
     * @return the default column renderer for the given column type
     */
    public static ColumnRenderer getDefaultRenderer(ColumnLayoutType layout) {
        ColumnRenderer retVal = null;

        if (layout != null) {
            retVal = new DefaultColumnRenderer(layout);
        } else {
            retVal = new DefaultColumnRenderer(ColumnLayoutType.TEXT);
        }

        return retVal;
    }

    @Override
    public abstract String render(NType value);

    @Override
    public JsonMap toJson() {
        JsonMap retVal = new JsonMap();
        retVal.add(JSON_LAYOUT, this.getLayout());
        return retVal;
    }

}
