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
import java.util.List;

/**
 * JsonElement
 * <p/>
 * Abstract class for JSON tree elements.
 * 
 * @see JsonMap
 * @see JsonList
 * @see JsonValue
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class JsonElement {

    /** Constant for json path seperation */
    private static final String PATH_SEPARATOR = ".";

    /** The JSON element type */
    private final JsonElementType elementType;

    /**
     * Creates a new {@link JsonElement} instance.
     * 
     * @param type
     *            the element type
     */
    protected JsonElement(JsonElementType type) {
        this.elementType = type;
    }

    /**
     * Getter for the elementType.
     * 
     * @return Returns the elementType.
     */
    public JsonElementType getElementType() {
        return this.elementType;
    }

    /**
     * Getter for the JSON element with the given key.
     * 
     * @param key
     *            the key of the element
     * 
     * @return the element with the given key, or null when the element does not exist
     */
    public abstract JsonElement get(String key);

    /**
     * Getter for all contained JSON elements.
     * 
     * @return all child elements of this element
     */
    public abstract List<JsonElement> getAll();

    /**
     * Getter for the value of this element.
     * 
     * @return Returns the value of the JSON elment or null if this is not a {@link JsonValue}
     *         instance.
     */
    public String getValue() {
        return null;
    }

    /**
     * Getter the string value for the JSON element with the given key..
     * 
     * @param path
     *            the path to the element
     * 
     * @return the value of the element with the given key, or null when the element does not exist
     */
    public final String getValue(String path) {
        if (path == null || path.isEmpty()) {
            if (this.getElementType() == JsonElementType.VALUE) {
                return ((JsonValue) this).getValue();
            }
            return null;
        }

        int seperatorIndex = path.indexOf(PATH_SEPARATOR);

        if (seperatorIndex == -1) {
            JsonElement element = this.get(path);
            if (element != null && element.getElementType() == JsonElementType.VALUE) {
                return ((JsonValue) element).getValue();
            }
            return null;
        }

        String token = path.substring(0, seperatorIndex);
        JsonElement element = this.get(token);
        if (element != null) {
            return element.getValue(path.substring(seperatorIndex));
        }

        return null;
    }

    /**
     * Print the current JSON element to string.
     * 
     * @return the JSON string
     */
    public String print() {
        try {
            StringBuilder result = new StringBuilder();
            this.print(result, 0);
            return result.toString();
        } catch (IOException ioe) {
            // Shallow Exception since StringBuilder does not raise IOExceptions
        }

        return null;
    }

    /**
     * Print the current JSON element to string.
     * 
     * @return the JSON string
     * 
     * @throws IOException
     *             when the appendable cannot be written
     */
    public void print(Appendable appendable) throws IOException {
        this.print(appendable, 0);
    }

    /**
     * Print the json element to the given string builder.
     * 
     * @param result
     *            the result to append the element
     * @param indent
     *            the print indent
     */
    protected abstract void print(Appendable result, int indent) throws IOException;

    @Override
    public String toString() {
        return this.print();
    }

    /**
     * Escape the string value.
     * 
     * @param value
     *            the value to escape
     * 
     * @return the escaped value
     */
    protected String escape(String value) {
        value = value.replace("\\", "\\\\");
        value = value.replace("\"", "\\\"");
        value = value.replace("/", "\\/");
        value = value.replace("\b", "\\b");
        value = value.replace("\f", "\\f");
        value = value.replace("\n", "\\n");
        value = value.replace("\r", "\\r");
        value = value.replace("\t", "\\t");
        // value = value.replace(":", "\\:");

        return value;
    }
}
