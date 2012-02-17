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
import java.util.Collections;
import java.util.List;

/**
 * JsonValue
 * <p/>
 * Holds a single json value.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class JsonValue extends JsonElement {

    /** Constant for JSON Open Value */
    private static final String VALUE_OPEN = "\"";

    /** Constant for JSON Close Value */
    private static final String VALUE_CLOSE = "\"";

    /** The Value String */
    private String value;

    /**
     * Creates a new {@link JsonValue} instance.
     * 
     * @param value
     *            the JSON value as object
     */
    public JsonValue(Object value) {
        this(value == null ? null : String.valueOf(value));
    }

    /**
     * Creates a new {@link JsonValue} instance.
     * 
     * @param value
     *            the JSON value as string
     */
    public JsonValue(String value) {
        super(JsonElementType.VALUE);

        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public JsonElement get(String key) {
        return null;
    }

    @Override
    public List<JsonElement> getAll() {
        return Collections.emptyList();
    }

    @Override
    protected void print(Appendable result, int indent) throws IOException {
        result.append(VALUE_OPEN);
        if (this.value != null) {
            // result.append(this.value);
            result.append(super.escape(this.value));
        }
        result.append(VALUE_CLOSE);
    }
}
