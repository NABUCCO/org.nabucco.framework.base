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
package org.nabucco.framework.base.ui.web.component.common.controller.resource;

import java.util.HashMap;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.content.ContentEntry;
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

    Map<String, ContentEntry> uploadedResourcesMap = new HashMap<String, ContentEntry>();

    Map<String, TemproraryResourceEntry> temproraryResourcesMap = new HashMap<String, TemproraryResourceEntry>();

    /**
     * 
     * Creates a new {@link ResourceController} instance.
     * 
     */
    public ResourceController() {

    }

    /**
     * Adds the reference on the uploadere resource
     * 
     * @param instanceid
     *            instanceid of the resource
     * @param entry
     *            the entry to be stored in controller
     */
    public void addUploadedResource(String instanceid, ContentEntry entry) {
        if (instanceid == null) {
            throw new IllegalArgumentException("Cannot add a new resorce entry with instanceid 'null'");
        }
        if (entry == null) {
            throw new IllegalArgumentException("Cannot add a new resorce entry - 'null'");
        }

        uploadedResourcesMap.put(instanceid, entry);

        // Add a download reference for the item
        ContentEntryType type = entry.getType();
        this.addDownloadReferenceResource(entry.getId(), type);
    }

    /**
     * Adds temprorary resource to the controller
     * 
     * Is used in export servlet to get temprorary stored data (like export)
     * 
     * @param data
     *            the data to be sent
     * @param resourceType
     *            the mime type of the resource
     * @return instance id to access the content throw export servlet
     */
    public String addTemproraryResource(byte[] data, ResourceContentType resourceType) {

        String instanceid = NabuccoSystem.createUUID();

        if (instanceid == null) {
            throw new IllegalArgumentException("Cannot add a new resorce entry with instanceid 'null'");
        }
        if (data == null) {
            throw new IllegalArgumentException("Cannot add a new resorce entry - 'null'");
        }
        if (resourceType == null) {
            throw new IllegalArgumentException("Cannot add a new resource Type - 'null'");
        }

        TemproraryResourceEntry entry = new TemproraryResourceEntry(resourceType, data);
        temproraryResourcesMap.put(instanceid, entry);

        return instanceid;
    }

    /**
     * Returns the uploaded resource with given instance id
     * 
     * @param instanceid
     *            instance id for lookup
     * @return content entry or null if not found
     */
    public ContentEntry getUploadedResource(String instanceid) {
        if (instanceid == null) {
            throw new IllegalArgumentException("Cannot Resolve resource with instanceid 'null'");
        }

        if (uploadedResourcesMap.containsKey(instanceid)) {

            ContentEntry entry = uploadedResourcesMap.get(instanceid);
            uploadedResourcesMap.remove(instanceid);

            return entry;
        }

        return null;
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
    public String addDownloadReferenceResource(Long id, ContentEntryType type) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot add a new resorce entry with id 'null'");
        }
        if (type == null) {
            throw new IllegalArgumentException("Cannot add a new resorce entry with type 'null'");
        }
        ResourceControllerEntry entry = new ResourceControllerEntry(id, type);

        // try to get existing reference
        for (String entryReference : resourceMap.keySet()) {
            if (resourceMap.get(entryReference).equals(entry)) {
                return entryReference;
            }
        }
        String key = NabuccoSystem.createUUID();

        resourceMap.put(key, entry);

        return key;
    }

    /**
     * Resolves the key referenced temprorary resource
     * 
     * Is used in export servlet to get temprorary stored data
     * 
     * @param key
     *            the key to be resolved
     * @return resource or null if nothing found
     */
    public TemproraryResourceEntry resolveTemproraryReference(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot Resolve temprorary resource with id 'null'");
        }

        if (temproraryResourcesMap.containsKey(key)) {

            TemproraryResourceEntry resourceControllerEntry = temproraryResourcesMap.get(key);

            return resourceControllerEntry;
        }
        return null;
    }

    /**
     * Resolves the key referenced resource
     * 
     * @param key
     *            the key to be resolved
     * @return resource or null if nothing found
     */
    public ResourceControllerEntry resolveDownloadReference(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot Resolve resource with id 'null'");
        }

        if (resourceMap.containsKey(key)) {

            ResourceControllerEntry resourceControllerEntry = resourceMap.get(key);

            return resourceControllerEntry;
        }
        return null;
    }

}
