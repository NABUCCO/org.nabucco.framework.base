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
package org.nabucco.framework.base.ui.web.model.control.relation.picker.delegates;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.model.control.util.parser.ControlParserException;

/**
 * RelationDelegate
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public interface RelationDelegate<P extends NabuccoProperty, N extends NType> {

    static final String JSON_DISPLAY_VALUE = "displayValue";

    static final String DEFAULT_LABEL = "UNDEFINED";

    /**
     * Parses String value to get the property instance
     * 
     * @param property
     *            the owning property
     * @param stringValue
     *            string value to be parsed
     * @param multipleSelection
     *            allow multiple selection or throw an exception non unique result
     * 
     * @return parsed object
     * 
     * @throws ControlParserException
     *             if parsing fails
     */
    N parse(P property, String stringValue, boolean multipleSelection) throws ControlParserException;

    /**
     * Produce JsonElement from given Object
     * 
     * @param value
     *            object to be converted
     * @param displayPath
     *            path for the display property
     * 
     * @return {@link JsonElement} element
     */
    JsonElement toJson(N value, String displayPath);

    /**
     * Search for display Path in given datatype
     * 
     * @param datatype
     *            datatype to be searched
     * @param displayPath
     *            path for search
     */
    String getLabel(Datatype datatype, String displayPath);

}
