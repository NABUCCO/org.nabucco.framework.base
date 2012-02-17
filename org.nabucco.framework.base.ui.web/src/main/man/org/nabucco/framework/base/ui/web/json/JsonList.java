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
package org.nabucco.framework.base.ui.web.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * JsonList
 * <p/>
 * Holds a list of json elements.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class JsonList extends JsonElement {

    /** Constant for JSON Open List */
    private static final String LIST_OPEN = "[";

    /** Constant for JSON Close List */
    private static final String LIST_CLOSE = "]";

    /** Constant for JSON List Entry Seperator */
    private static final String LIST_SEPERATOR = ",";

    /** The list entries */
    private List<JsonElement> children = new ArrayList<JsonElement>();

    /**
     * Creates a new {@link JsonList} instance.
     * 
     * @param elements
     *            the child elements
     */
    public JsonList(JsonElement... elements) {
        super(JsonElementType.LIST);

        for (JsonElement jsonElement : elements) {
            this.children.add(jsonElement);
        }
    }

    /**
     * Adds a new value to the list.
     * 
     * @param value
     *            the JSON value as string
     */
    public void add(String value) {
        this.add(new JsonValue(value));
    }

    /**
     * Adds a new key value pair to the list.
     * 
     * @param key
     *            the JSON key as string
     * @param value
     *            the JSON value as string
     */
    public void add(String key, String value) {
        this.add(new JsonMap(key, value));
    }

    /**
     * Adds a new key value pair to the list.
     * 
     * @param key
     *            the JSON key as string
     * @param value
     *            the JSON value
     */
    public void add(String key, JsonElement value) {
        this.add(new JsonMap(key, value));
    }

    /**
     * Add a JSON element to the list.
     * 
     * @param element
     *            the element to add
     */
    public void add(JsonElement element) {
        this.children.add(element);
    }

    @Override
    public JsonElement get(String key) {
        try {
            int index = Integer.parseInt(key);
            if (index < this.children.size()) {
                return this.children.get(index);
            }
        } catch (NumberFormatException nfe) {
            // Shallow exception!
        }
        return null;
    }

    @Override
    public List<JsonElement> getAll() {
        return new ArrayList<JsonElement>(this.children);
    }

    @Override
    protected void print(Appendable result, int indent) throws IOException {
        result.append(LIST_OPEN);

        for (Iterator<JsonElement> iterator = this.children.iterator(); iterator.hasNext();) {
            JsonElement element = iterator.next();

            element.print(result, indent);
            if (iterator.hasNext()) {
                result.append(LIST_SEPERATOR);
                result.append(' ');
            }
        }

        result.append(LIST_CLOSE);
    }

}
