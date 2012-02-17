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
package org.nabucco.framework.base.ui.web.model.browser;

import java.util.LinkedHashMap;
import java.util.Map;

import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.WebModel;

/**
 * Model for browser content.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class BrowserModel extends WebModel {

    /** Constant for JSON key for attribute: <code>entries</code> */
    static final String JSON_ENTRIES = "entries";

    private static final String ID_SEPARATOR = ".";

    /** The maximum browser model size */
    private final int maxSize;

    private Map<String, BrowserEntry> entryMap = new LinkedHashMap<String, BrowserEntry>();

    /**
     * Creates a new {@link BrowserModel} instance.
     */
    public BrowserModel() {
        this(Integer.MAX_VALUE);
    }

    /**
     * Creates a new {@link BrowserModel} instance.
     * 
     * @param maxSize
     *            the browser model maximum size
     */
    public BrowserModel(int maxSize) {
        if (maxSize < 1) {
            maxSize = Integer.MAX_VALUE;
        }
        this.maxSize = maxSize;
    }

    @Override
    public void init() {
    }

    /**
     * Returns the child with given id or null if no found
     * 
     * @return browser entry or null if not found
     */
    public BrowserEntry getChildRecursive(String id) {
        BrowserEntry browserEntry = this.getEntryMap().get(id);

        if (browserEntry == null) {
            for (String key : this.getEntryMap().keySet()) {
                BrowserEntry child = this.getEntryMap().get(key);

                BrowserEntry childBrowserEntry = child.getChildRecursive(id);
                if (childBrowserEntry != null) {
                    browserEntry = childBrowserEntry;
                    break;
                }
            }
        }

        return browserEntry;
    }

    /**
     * Add a browser entry to the model.
     * 
     * @param browserEntry
     *            the browser entry to add
     */
    public void add(BrowserEntry browserEntry) {
        if (this.getEntryMap().size() >= this.maxSize) {
            String[] keys = this.getEntryMap().keySet().toArray(new String[this.getEntryMap().size()]);
            this.getEntryMap().remove(keys[0]);
        }
        this.getEntryMap().put(browserEntry.getId(), browserEntry);
    }

    /**
     * Remove the browser entry with the given id from the model.
     * 
     * @param id
     *            the browser entry id
     * 
     * @return the removed browser entry
     */
    public BrowserEntry remove(String id) {
        return this.getEntryMap().remove(id);
    }

    /**
     * Returns the number of browser entrys mappings in this model.
     * 
     * @return the size of the browser model
     */
    public int size() {
        return this.getEntryMap().size();
    }

    /**
     * Getter for the maxSize.
     * 
     * @return Returns the maxSize.
     */
    public int getMaxSize() {
        return this.maxSize;
    }

    /**
     * Generates a unique Identifier from given id's
     * 
     * @param ids
     *            the ids to be included
     * @return a,b,c => a.b.c
     */
    protected String generateUniqueId(String... ids) {
        StringBuilder retVal = new StringBuilder();

        for (String value : ids) {

            if (!value.isEmpty()) {

                if (retVal.length() == 0) {
                    retVal.append(value);
                } else {
                    retVal.append(ID_SEPARATOR);
                    retVal.append(value);
                }
            }
        }
        return retVal.toString();
    }

    /**
     * Getter for the entryMap.
     * 
     * @return Returns the entryMap.
     */
    public synchronized Map<String, BrowserEntry> getEntryMap() {
        return this.entryMap;
    }

    @Override
    public JsonMap toJson() {
        JsonMap json = new JsonMap();

        JsonList jsonEntryList = new JsonList();
        for (BrowserEntry entry : this.getEntryMap().values()) {
            // If the child has no label, that try to skip it and show only his children, if any
            if (entry.getLabel() == null || entry.getLabel().isEmpty()) {
                for (BrowserEntry child : entry.getEntryMap().values()) {
                    jsonEntryList.add(child.toJson());
                }
            } else {
                if (!entry.isLazy()) {
                    jsonEntryList.add(entry.toJson());
                }
            }
        }
        json.add(JSON_ENTRIES, jsonEntryList);

        return json;
    }

}
