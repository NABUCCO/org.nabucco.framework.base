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
package org.nabucco.framework.base.ui.web.model.editor.control.property.dropdown.selection;

import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.utils.I18N;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.json.Jsonable;

/**
 * SelectionItem
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class SelectionItem<N extends NType> implements Jsonable {

    private static final String POINT_DELIMITER = ".";

    private static final String EMPTY_STRING = "";

    private String literalName;

    private String localizedLiteralName;

    private N item;

    private String path;

    /**
     * 
     * Creates a new empty selection item instance
     * 
     */
    SelectionItem() {
        literalName = EMPTY_STRING;
        localizedLiteralName = EMPTY_STRING;
        item = null;
    }

    /**
     * 
     * Creates a new {@link SelectionItem} instance.
     * 
     * @param literalName
     *            the string representation of the literal
     * @param path
     *            the full qualified path
     */
    SelectionItem(N item, String literalName, String path) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot create a selection item 'null'");
        }

        if (path == null) {
            throw new IllegalArgumentException("Cannot create a selection item with path 'null'");
        }

        if (literalName == null) {
            throw new IllegalArgumentException("Cannot create a selection item without a string representation");
        }

        this.item = item;
        this.literalName = literalName;
        this.path = path;
    }

    /**
     * Get the Ntype element of the item
     * 
     * @return item
     */
    public N getItem() {
        return item;
    }

    /**
     * Getter for the literal String
     * 
     * @return
     */
    public String getLiteralName() {
        return literalName;
    }

    /**
     * Returns the internationalized representation of the string literal
     * 
     * @return locatized string
     */
    public String getI18n() {
        if (localizedLiteralName == null) {
            String replacedLiteral = literalName.replace(" ", "_");
            // Replace spaces in literal
            // with underscores
            localizedLiteralName = I18N.i18n(path + POINT_DELIMITER + replacedLiteral);
        }
        return localizedLiteralName;
    }

    @Override
    public JsonElement toJson() {
        JsonMap json = new JsonMap();
        json.add(JSON_ID, this.getLiteralName());
        json.add(JSON_LABEL, this.getI18n());
        return json;
    }
}
