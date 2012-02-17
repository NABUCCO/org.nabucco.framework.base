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
package org.nabucco.framework.base.ui.web.component.common.color;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.colorsheme.ColorShemeExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.colorsheme.ColorShemeItemExtension;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.json.Jsonable;

/**
 * ColorScheme
 * 
 * The sheme of colors
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ColorScheme implements Jsonable {

    private ColorShemeExtension extension;

    private Map<String, String> colorMap = new LinkedHashMap<String, String>();

    /**
     * 
     * Creates a new {@link ColorScheme} instance.
     * 
     * @param extension
     *            the color sheme extension
     */
    ColorScheme(ColorShemeExtension extension) {
        this.extension = extension;
    }

    /**
     * Initialize the map
     */
    public void init() {

        this.colorMap.clear();

        NabuccoList<ColorShemeItemExtension> colors = this.extension.getColors();
        for (ColorShemeItemExtension color : colors) {
            String colorValue = PropertyLoader.loadProperty(color.getValue());
            String id = color.getIdentifier().getValue();

            this.colorMap.put(id, colorValue);
        }
    }

    /**
     * Getter for the colorsheme id
     * 
     * @return the id
     */
    public String getId() {
        return this.extension.getIdentifier().getValue();
    }

    /**
     * Search for the color with given id in the map
     * 
     * @param id
     *            the id of the color
     * @return color or null if not found
     */
    public String getColorToId(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot find the color to the given id. Id is 'null'.");
        }

        return this.colorMap.get(id);
    }

    /**
     * Returns the colors as the list
     * 
     * @return list of colors
     */
    public List<String> getColors() {
        return new ArrayList<String>(this.colorMap.values());
    }

    /**
     * Returns the color ids as the list
     * 
     * @return list of colors
     */
    public List<String> getColorIds() {
        return new ArrayList<String>(this.colorMap.keySet());
    }

    @Override
    public JsonElement toJson() {
        JsonList retVal = new JsonList();
        for (String id : this.getColorIds()) {
            JsonMap color = new JsonMap();
            color.add(JSON_ID, id);
            color.add(JSON_VALUE, this.getColorToId(id));
            retVal.add(color);
        }
        return retVal;
    }
}
