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

import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jetty.util.ajax.JSON;

/**
 * JsonParser
 * <p/>
 * Parses a string to the JSON element tree.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class JsonParser {

    /**
     * Singleton instance.
     */
    private static JsonParser instance = new JsonParser();

    /**
     * Private constructor.
     */
    private JsonParser() {
    }

    /**
     * Singleton access.
     * 
     * @return the JsonParser instance.
     */
    public static JsonParser getInstance() {
        return instance;
    }

    /**
     * Parse a JSON text to a JSON Element Tree.
     * 
     * @param text
     *            the JSON text to parse
     * 
     * @return the parsed JSON tree
     */
    public JsonElement parse(String text) {
        if (text == null || text.isEmpty()) {
            return new JsonValue("");
        }

        Object object = JSON.parse(text);

        if (object == null) {
            return null;
        }

        return this.parse(object);
    }

    /**
     * Convert a parsed object to the appropriate JSON element.
     * 
     * @param object
     *            the parsed object
     * 
     * @return the converted JSON element
     */
    @SuppressWarnings("unchecked")
    private JsonElement parse(Object object) {

        if (object instanceof Object[]) {
            return this.parseArray((Object[]) object);
        } else if (object instanceof Map) {
            return this.parseMap((Map<String, Object>) object);
        } else if (object instanceof String) {
            return this.parseValue(object);
        }

        throw new IllegalStateException("Cannot parse JSON object '" + String.valueOf(object) + "'.");
    }

    /**
     * Parse a JSON list.
     * 
     * @param list
     *            the list object to parse
     * 
     * @return the JSON list instance
     */
    private JsonList parseArray(Object[] array) {
        JsonList jsonList = new JsonList();

        for (Object entry : array) {
            jsonList.add(this.parse(entry));
        }

        return jsonList;
    }

    /**
     * Parse a JSON map.
     * 
     * @param map
     *            the map object to parse
     * 
     * @return the JSON map instance
     */
    private JsonMap parseMap(Map<String, Object> map) {
        JsonMap jsonMap = new JsonMap();

        for (Entry<String, Object> entry : map.entrySet()) {
            jsonMap.add(entry.getKey(), this.parse(entry.getValue()));
        }

        return jsonMap;
    }

    /**
     * Parse a JSON value.
     * 
     * @param object
     *            the value object to parse
     * 
     * @return the JSON value instance
     */
    private JsonValue parseValue(Object object) {
        return new JsonValue(object);
    }
}
