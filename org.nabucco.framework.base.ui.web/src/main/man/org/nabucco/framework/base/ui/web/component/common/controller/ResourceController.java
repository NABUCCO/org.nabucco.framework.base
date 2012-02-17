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
package org.nabucco.framework.base.ui.web.component.common.controller;

import java.util.HashMap;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.content.ContentEntryType;

/**
 * ResourceController
 * 
 * Controlls the soft references to the resources
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ResourceController {

    Map<String, ResourceControllerEntry> resourceMap = new HashMap<String, ResourceControllerEntry>();

    /**
     * 
     * Creates a new {@link ResourceController} instance.
     * 
     */
    public ResourceController() {

    }

    /**
     * Adds a new resource to the controller
     * 
     * @param id
     *            the id of the resource
     * @param type
     *            the type of resource
     * @return new generated id
     */
    public String addResource(Long id, ContentEntryType type) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot add a new resorce entry with id 'null'");
        }
        if (type == null) {
            throw new IllegalArgumentException("Cannot add a new resorce entry with type 'null'");
        }
        ResourceControllerEntry entry = new ResourceControllerEntry(id, type);
        String key = NabuccoSystem.createUUID();

        this.resourceMap.put(key, entry);

        return key;
    }

    /**
     * Resolves the key referenced resource and removes it from the map
     * 
     * @param key
     *            the key to be resolved
     * @return resource or null if nothing found
     */
    public ResourceControllerEntry resolveResource(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot Resolve resource with id 'null'");
        }

        if (this.resourceMap.containsKey(key)) {

            ResourceControllerEntry resourceControllerEntry = this.resourceMap.get(key);
            this.resourceMap.remove(key);

            return resourceControllerEntry;
        }
        return null;
    }

}
