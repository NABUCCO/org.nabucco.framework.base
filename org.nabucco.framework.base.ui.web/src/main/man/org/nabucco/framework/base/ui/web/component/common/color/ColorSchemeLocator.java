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

import java.util.HashMap;
import java.util.Map;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.colorsheme.ColorShemeExtension;

/**
 * ColorShemeLocator
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public final class ColorSchemeLocator {

    private static Map<String, ColorScheme> colorShemeMap = new HashMap<String, ColorScheme>();

    /**
     * Creates a new {@link ColorSchemeLocator} instance.
     */
    private ColorSchemeLocator() {
        throw new IllegalStateException("Color sheme locator may not be inilialized");
    }

    /**
     * Getter for the color sheme
     * 
     * @param id
     *            the id of the sheme
     * @return color sheme instance
     * 
     * @throws ExtensionException
     *             if not found
     */
    public static ColorScheme getColorSheme(String id) throws ExtensionException {

        if (id == null) {
            throw new IllegalArgumentException("Cannot find color sheme. id is 'null'");
        }

        if (colorShemeMap.containsKey(id)) {
            return colorShemeMap.get(id);
        }

        ColorShemeExtension colorExtension = (ColorShemeExtension) NabuccoSystem.getExtensionResolver()
                .resolveExtension(ExtensionPointType.ORG_NABUCCO_UI_COLORSCHEME, id);

        ColorScheme sheme = new ColorScheme(colorExtension);
        sheme.init();

        colorShemeMap.put(sheme.getId(), sheme);

        return sheme;
    }
}
