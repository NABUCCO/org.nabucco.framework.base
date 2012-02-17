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

/**
 * Objects that are serializable to JSON.
 * 
 * @see JsonElement
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public interface Jsonable {

    /** Constant for JSON Attribute: <code>id</code> */
    final String JSON_ID = "id";

    /** Constant for JSON Attribute: <code>dirty</code> */
    final String JSON_DIRTY = "dirty";

    /** Constant for JSON Attribute: <code>layout</code> */
    final String JSON_LAYOUT = "layout";

    /** Constant for JSON Attribute: <code>label</code> */
    final String JSON_LABEL = "label";

    /** Constant for JSON Attribute: <code>tooltip</code> */
    final String JSON_TOOLTIP = "tooltip";

    /** Constant for JSON Attribute: <code>action</code> */
    final String JSON_ACTION = "action";

    /** Constant for JSON Attribute: <code>image</code> */
    final String JSON_IMAGE = "image";

    /** Constant for JSON Attribute: <code>model</code> */
    final String JSON_MODEL = "model";

    /** Constant for JSON Attribute: <code>type</code> */
    final String JSON_TYPE = "type";

    /** Constant for JSON Attribute: <code>value</code> */
    final String JSON_VALUE = "value";

    /** Constant for JSON Attribute: <code>grant</code> */
    final String JSON_ACCESS = "access";

    /** Constant for JSON Attribute: <code>valid</code> */
    final String JSON_VALID = "valid";

    /** Constant for JSON Attribute: <code>visible</code> */
    final String JSON_VISIBLE = "visible";

    /** Constant for JSON Attribute: <code>editable</code> */
    final String JSON_EDITABLE = "editable";

    /** Constant for JSON Attribute: <code>minLength</code> */
    final String JSON_MIN_LENGTH = "minLength";

    /** Constant for JSON Attribute: <code>maxLength</code> */
    final String JSON_MAX_LENGTH = "maxLength";

    /** Constant for JSON Attribute: <code>minMultiplicity</code> */
    final String JSON_MIN_MULTIPLICITY = "minMultiplicity";

    /** Constant for JSON Attribute: <code>maxMultiplicity</code> */
    final String JSON_MAX_MULTIPLICITY = "maxMultiplicity";

    /**
     * Serialize the current state of this element to JSON.
     * 
     * @return the JSON representation of this element
     * 
     * @see JsonElement
     */
    JsonElement toJson();
}
