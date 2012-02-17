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
package org.nabucco.framework.base.ui.web.model.work.breadcrump;

import java.beans.PropertyChangeEvent;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.model.WebModel;
import org.nabucco.framework.base.ui.web.model.control.util.PropertyStringParser;

/**
 * BreadCrump
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class BreadCrump extends WebModel implements Iterable<BreadCrumpEntry> {

    private Deque<BreadCrumpEntry> entryStack = new ArrayDeque<BreadCrumpEntry>();

    @Override
    public void init() {
    }

    /**
     * Add a new bread crump entry to the front of the list.
     * 
     * @param label
     *            label of the bread crump
     */
    public void addFirst(String label) {
        this.addFirst(new BreadCrumpEntry(label));
    }

    /**
     * Add a new bread crump entry to the front of the list.
     * 
     * @param label
     *            label of the bread crump
     * @param tooltip
     *            tooltip of the bread crump
     */
    public void addFirst(String label, String tooltip) {
        this.addFirst(new BreadCrumpEntry(label, tooltip));
    }

    /**
     * Add a new bread crump entry to the front of the list.
     * 
     * @param label
     *            label of the bread crump
     * @param tooltip
     *            tooltip of the bread crump
     * @param itemId
     *            id of the related work item
     */
    public void addFirst(String label, String tooltip, String itemId) {
        this.addFirst(new BreadCrumpEntry(label, tooltip, itemId));
    }

    /**
     * Add a bread crump entry to the front of the list.
     * 
     * @param entry
     *            the entry to add
     */
    public void addFirst(BreadCrumpEntry entry) {
        this.entryStack.addFirst(entry);

        PropertyStringParser parser = new PropertyStringParser(entry.getLabel(), entry.getTooltip());
        for (String propertyName : parser.parseProperties()) {
            super.addPropertyChangeListener(propertyName, entry);
        }
    }

    /**
     * Add a list of bread crump entries to the front of the list.
     * 
     * @param entryList
     *            the entry to add in the given order
     */
    public void addFirst(List<BreadCrumpEntry> entryList) {
        for (int i = entryList.size() - 1; i >= 0; i--) {
            this.entryStack.addFirst(entryList.get(i));
        }
    }

    /**
     * Add a new bread crump entry to the end of the list.
     * 
     * @param label
     *            label of the bread crump
     */
    public void addLast(String label) {
        this.addLast(new BreadCrumpEntry(label));
    }

    /**
     * Add a new bread crump entry to the end of the list.
     * 
     * @param label
     *            label of the bread crump
     * @param tooltip
     *            tooltip of the bread crump
     */
    public void addLast(String label, String tooltip) {
        this.addLast(new BreadCrumpEntry(label, tooltip));
    }

    /**
     * Add a new bread crump entry to the end of the list.
     * 
     * @param label
     *            label of the bread crump
     * @param tooltip
     *            tooltip of the bread crump
     * @param itemId
     *            id of the related work item
     */
    public void addLast(String label, String tooltip, String itemId) {
        this.addLast(new BreadCrumpEntry(label, tooltip, itemId));
    }

    /**
     * Add a bread crump entry to the end of the list.
     * 
     * @param entry
     *            the entry to add
     */
    public void addLast(BreadCrumpEntry entry) {
        this.entryStack.addLast(entry);

        PropertyStringParser parser = new PropertyStringParser(entry.getLabel(), entry.getTooltip());
        for (String propertyName : parser.parseProperties()) {
            super.addPropertyChangeListener(propertyName, entry);
        }
    }

    /**
     * Add a list of bread crump entries to the end of the list.
     * 
     * @param entryList
     *            the entry to add in the given order
     */
    public void addLast(List<BreadCrumpEntry> entryList) {
        for (int i = 0; i < entryList.size(); i++) {
            this.entryStack.addLast(entryList.get(i));
        }
    }

    /**
     * Getter for a new copy of the breadcrump entry list.
     * 
     * @return the entry list
     */
    public List<BreadCrumpEntry> getEntries() {
        return new ArrayList<BreadCrumpEntry>(this.entryStack);
    }

    /**
     * Returns true if the breadcrump contains no elements.
     * 
     * @return <b>true</b> if the breadcrump is empty, <b>false</b> if not
     */
    public boolean isEmpty() {
        return this.entryStack.isEmpty();
    }

    @Override
    public Iterator<BreadCrumpEntry> iterator() {
        return this.entryStack.iterator();
    }

    @Override
    public JsonElement toJson() {
        JsonList jsonList = new JsonList();

        for (BreadCrumpEntry entry : this.entryStack) {
            jsonList.add(entry.toJson());
        }

        return jsonList;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        // Delegate to Entries
        super.updateProperty(event.getPropertyName(), event.getOldValue(), event.getNewValue());
    }
}
