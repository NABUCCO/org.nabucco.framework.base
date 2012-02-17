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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * JsonMap
 * <p/>
 * Holds a map of json elements.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class JsonMap extends JsonElement {

    /** Constant for JSON Open Map */
    private static final String MAP_OPEN = "{";

    /** Constant for JSON Close Map */
    private static final String MAP_CLOSE = "}";

    /** Constant for JSON Open Key */
    private static final String KEY_OPEN = "\"";

    /** Constant for JSON Close Key */
    private static final String KEY_CLOSE = "\"";

    /** Constant for JSON Key/Value Qualifier */
    private static final String MAP_QUALIFIER = " : ";

    /** Constant for JSON Map Entry Seperator */
    private static final String MAP_SEPERATOR = ", ";

    /** The map entries */
    private Map<String, JsonElement> childMap = new HashMap<String, JsonElement>();

    /**
     * Creates a new {@link JsonMap} instance.
     */
    public JsonMap() {
        super(JsonElementType.MAP);
    }

    /**
     * Creates a new {@link JsonMap} instance.
     * 
     * @param key
     *            the JSON key
     * @param value
     *            the JSON value as string
     */
    public JsonMap(String key, String value) {
        this(key, new JsonValue(value));
    }

    /**
     * Creates a new {@link JsonMap} instance.
     * 
     * @param key
     *            the JSON key
     * @param value
     *            the JSON value
     */
    public JsonMap(String key, JsonElement value) {
        super(JsonElementType.MAP);

        this.childMap.put(key, value);
    }

    /**
     * Add a entry to the JSON map.
     * 
     * @param key
     *            the entry key
     * @param value
     *            the entry value as string
     */
    public void add(String key, String value) {
        this.add(key, new JsonValue(value));
    }

    /**
     * Add a entry to the JSON map.
     * 
     * @param key
     *            the entry key
     * @param value
     *            the entry value as object
     */
    public void add(String key, Object value) {
        this.add(key, new JsonValue(value));
    }

    /**
     * Add a entry to the JSON map.
     * 
     * @param key
     *            the entry key
     * @param value
     *            the entry value
     */
    public void add(String key, JsonElement value) {
        this.childMap.put(key, value);
    }

    @Override
    public JsonElement get(String key) {
        return this.childMap.get(key);
    }

    @Override
    public List<JsonElement> getAll() {
        return new ArrayList<JsonElement>(this.childMap.values());
    }

    @Override
    protected void print(Appendable result, int indent) throws IOException {
        result.append(MAP_OPEN);

        for (Iterator<Entry<String, JsonElement>> iterator = this.childMap.entrySet().iterator(); iterator.hasNext();) {
            Entry<String, JsonElement> entry = iterator.next();
            result.append(KEY_OPEN);
            result.append(entry.getKey());
            result.append(KEY_CLOSE);
            result.append(MAP_QUALIFIER);
            entry.getValue().print(result, indent);

            if (iterator.hasNext()) {
                result.append(MAP_SEPERATOR);
            }
        }
        result.append(MAP_CLOSE);
    }

}
