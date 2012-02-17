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
package org.nabucco.framework.base.ui.web.component.work;

import java.util.regex.Pattern;

/**
 * UniqueInstanceIdGenerator
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
class UniqueInstanceIdGenerator {

    private static final String SEPERATOR = "_";

    private static final Pattern SPLIT_PATTERN = Pattern.compile(SEPERATOR);

    /**
     * Creates a new {@link UniqueInstanceIdGenerator} instance.
     */
    private UniqueInstanceIdGenerator() {
    }

    public static String generateUniqueId(String itemId, String instanceId) {
        if (itemId == null) {
            throw new IllegalArgumentException("Cannot create unique ID for item ID 'null'.");
        }
        if (instanceId == null) {
            throw new IllegalArgumentException("Cannot create unique ID for instance 'null'.");
        }

        StringBuilder result = new StringBuilder();

        result.append(itemId);
        result.append(SEPERATOR);
        result.append(instanceId);

        return result.toString();
    }

    /**
     * Extracts the itemId from the given unique id
     * 
     * @param uniqueId
     *            unique id to use
     * @return itemId The id of the item
     */
    public static String getItemId(String uniqueId) {
        if (uniqueId == null) {
            throw new IllegalArgumentException("Cannot extract item id from unique id because unique id is 'null'");
        }

        String[] parts = SPLIT_PATTERN.split(uniqueId);

        if (parts.length != 2) {
            throw new IllegalArgumentException("UniqueId is not valid to be evaluated");
        }
        return parts[0];
    }

    /**
     * Extracts the instance id from the given unique id
     * 
     * @param uniqueId
     *            unique id to use
     * @return instanceId The id of the instance
     */
    public static String getInstanceId(String uniqueId) {
        if (uniqueId == null) {
            throw new IllegalArgumentException("Cannot extract item id from unique id because unique id is 'null'");
        }

        String[] parts = SPLIT_PATTERN.split(uniqueId);

        if (parts.length != 2) {
            throw new IllegalArgumentException("UniqueId is not valid to be evaluated");
        }

        return parts[1];
    }
}
