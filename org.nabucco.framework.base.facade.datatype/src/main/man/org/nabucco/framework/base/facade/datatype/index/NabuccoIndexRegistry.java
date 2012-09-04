/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco-source.org/nabucco-license.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.base.facade.datatype.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * NabuccoIndexRegistry
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class NabuccoIndexRegistry {

    /** Index Map */
    private Map<String, NabuccoIndex> indexMap = new HashMap<String, NabuccoIndex>();

    public static final String ALL_INDEX_STRING = "";

    /**
     * Singleton instance.
     */
    private static NabuccoIndexRegistry instance;

    /**
     * Private constructor.
     */
    private NabuccoIndexRegistry() {
    }

    /**
     * Singleton access.
     * 
     * @return the NabuccoIndexRegistry instance.
     */
    public synchronized static NabuccoIndexRegistry getInstance() {
        if (instance == null) {
            instance = new NabuccoIndexRegistry();
        }
        return instance;
    }

    /**
     * Retrieves the index with the given name.
     * 
     * @param name
     *            name of the index to retrieve
     * 
     * @return the index or null if no index with the given name is configured
     */
    public NabuccoIndex getIndex(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Cannot retrieve index for name [null].");
        }

        synchronized (this) {
            NabuccoIndex index = this.indexMap.get(name);
            if (index == null) {
                // TODO: Configuration!
                index = new MemoryIndex(name, 6);
                this.indexMap.put(name, index);
            }
            return index;
        }
    }

    /**
     * Retrieves a cloned map containing all indices by their name.
     * 
     * @return the map of nabucco index instances by their name
     */
    public Map<String, NabuccoIndex> getAllIndices() {
        return new HashMap<String, NabuccoIndex>(this.indexMap);
    }

    /**
     * Removes all data from the collected indexes that begin with given string e.g. "Customer" will
     * remove index "Customer.all"
     * 
     * Giving of empty String as argument removes all indexes from map
     * 
     * @param indexName
     *            the beginning of index name to be deleted.
     */
    public void resetRegistry(String indexName) {
        if (indexName == null) {
            return;
        }

        synchronized (this) {
            if (indexName.equals(ALL_INDEX_STRING)) {
                indexMap.clear();
                return;
            }

            List<String> resetingIndexes = new ArrayList<String>();

            for (String indexKey : indexMap.keySet()) {
                if (indexKey.startsWith(indexName)) {
                    resetingIndexes.add(indexKey);
                }
            }

            for (String indexKey : resetingIndexes) {
                indexMap.remove(indexKey);
            }
        }
    }

}
