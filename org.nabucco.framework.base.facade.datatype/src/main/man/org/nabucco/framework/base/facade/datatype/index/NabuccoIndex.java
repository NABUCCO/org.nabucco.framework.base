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

import java.util.HashMap;
import java.util.Map;

/**
 * NabuccoIndex
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class NabuccoIndex {

    /** The index name */
    private final String name;

    /** The index node max size. */
    private final int maxSize;

    private boolean isInitialized;

    /**
     * Creates a new {@link NabuccoIndex} instance.
     * 
     * @param name
     *            the index name
     * @param maxSize
     *            the amount of values that are stored per index node
     */
    public NabuccoIndex(String name, int size) {
        this.maxSize = size;
        this.name = name;
        this.isInitialized = false;
    }

    /**
     * Add the given value to the index.
     * 
     * @param value
     *            value of the index entry
     * @param id
     *            id of the index entry
     */
    public void add(String value, long id) {
        if (value == null || value.isEmpty()) {
            return;
        }

        this.addToIndex(id, value);
    }

    /**
     * Indicates if the index is already initialized
     * 
     * @return
     */
    public boolean isInitialized() {
        return isInitialized;
    }

    /**
     * Marks index as initialited
     */
    public void markAsInitialized() {
        isInitialized = true;
    }

    /**
     * Add the given node value to the index.
     * 
     * @param id
     *            the id of the value
     * @param name
     *            the name of the value
     */
    protected abstract void addToIndex(long id, String name);

    /**
     * Getter for the name.
     * 
     * @return Returns the name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter for the maximum maxSize of an index node.
     * 
     * @return Returns the maximum index node maxSize.
     */
    public int getMaxSize() {
        return this.maxSize;
    }

    /**
     * Search the index for the given search string.
     * 
     * @param name
     *            the search query to search for
     * 
     * @return a result map of values and ids
     */
    public Map<String, Long> search(String name) {
        Map<String, Long> result = new HashMap<String, Long>();

        if (name == null || name.isEmpty()) {
            return result;
        }

        this.searchIndex(name, result);

        return result;
    }

    /**
     * Search the index for the given search string.
     * 
     * @param searchString
     *            the search query to search for
     * @param result
     *            the result map to add the search results
     */
    protected abstract void searchIndex(String searchString, Map<String, Long> result);

    /**
     * Reset the index by clearing all data.
     */
    public void reset() {
        this.resetIndex();
    }

    /**
     * Reset the whole index data.
     */
    protected abstract void resetIndex();

}
