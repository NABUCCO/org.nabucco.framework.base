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
package org.nabucco.framework.base.facade.datatype.extension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Map for storing nabucco extensions by a given name.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 */
public class ExtensionMap implements Iterable<NabuccoExtension> {

    private ExtensionPointType extensionPoint;

    private Map<String, NabuccoExtension> extensionMap;

    /**
     * Creates a new {@link ExtensionMap} instance.
     * 
     * @param point
     *            the extension point of this map
     */
    public ExtensionMap(ExtensionPointType point) {
        this.extensionMap = new HashMap<String, NabuccoExtension>();
        this.extensionPoint = point;
    }

    /**
     * Getter for the extension point type.
     * 
     * @return the type of this map
     */
    public ExtensionPointType getExtensionPoint() {
        return this.extensionPoint;
    }

    /**
     * Add an extension with the given name to the map.
     * 
     * @param name
     *            the name of the extension
     * @param extension
     *            the extension to add
     */
    public void addExtension(String name, NabuccoExtension extension) {
        this.extensionMap.put(name, extension);
    }

    /**
     * Getter for the extension with the given name.
     * 
     * @param name
     *            the name of the extension
     * 
     * @return the extension or null
     */
    public NabuccoExtension getExtension(String name) {
        return this.extensionMap.get(name);
    }

    /**
     * Getter for extension names.
     * 
     * @return an array containing the extension names
     */
    public String[] getExtensionNames() {
        return this.extensionMap.keySet().toArray(new String[this.extensionMap.size()]);
    }

    @Override
    public Iterator<NabuccoExtension> iterator() {
        List<NabuccoExtension> list = new ArrayList<NabuccoExtension>(this.extensionMap.values());
        return list.iterator();
    }

}
