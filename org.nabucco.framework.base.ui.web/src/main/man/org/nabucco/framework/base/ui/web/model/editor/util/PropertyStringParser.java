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
package org.nabucco.framework.base.ui.web.model.editor.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser for the properties of a property afflicted string.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class PropertyStringParser {

    private String[] propertyStrings;

    private static final Pattern PATTERN = Pattern.compile("\\{(\\s|\\w|\\.)*\\}");

    /**
     * Creates a new {@link PropertyStringParser} instance.
     * 
     * @param propertyString
     *            the property string to parse
     */
    public PropertyStringParser(String... propertyString) {
        if (propertyString == null || propertyString.length == 0) {
            throw new IllegalArgumentException("Cannot parse property string [null].");
        }

        this.propertyStrings = propertyString;
    }

    /**
     * Parse the properties of a property label.
     * 
     * Example:
     * <p/>
     * <ul>
     * <li>"Person {name}" --> [name]</li>
     * <li>"{description}" --> [description]</li>
     * <li>"{name} : {description}" --> [name, description]</li>
     * <li>"{organization.title}" --> [organization.title]</li>
     * </ul>
     * 
     * @return the properties
     */
    public Set<String> parseProperties() {
        Set<String> properties = new HashSet<String>();

        for (String propertyString : this.propertyStrings) {
            if (propertyString == null || propertyString.isEmpty()) {
                continue;
            }

            Matcher matcher = PATTERN.matcher(propertyString);

            while (matcher.find()) {
                String property = matcher.group();
                properties.add(property.substring(1, property.length() - 1));
            }
        }

        return properties;
    }
}
