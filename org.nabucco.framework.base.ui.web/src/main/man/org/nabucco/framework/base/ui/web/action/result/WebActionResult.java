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
package org.nabucco.framework.base.ui.web.action.result;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.json.Jsonable;

/**
 * WebActionResult
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class WebActionResult implements Jsonable {

    private static final String JSON_ITEMS = "items";

    private Set<WebActionResultItem> itemList = new LinkedHashSet<WebActionResultItem>();

    /**
     * Add an item to the list.
     * 
     * @param item
     */
    public void addItem(WebActionResultItem item) {
        this.itemList.add(item);
    }

    /**
     * Appends one result to another
     * 
     * @param result
     *            the result element to be appended
     */
    public void addResult(WebActionResult result) {
        if (result == null || result.getItemList() == null) {
            throw new IllegalArgumentException("Cannot append the result. Resultlist is 'null'");
        }
        this.itemList.addAll(result.getItemList());
    }

    /**
     * Getter for the itemList. This method returns a copy of the original item list, so
     * modifications will not affect the original list.
     * 
     * @return Returns a copy of the item list.
     */
    public List<WebActionResultItem> getItemList() {
        return new ArrayList<WebActionResultItem>(this.itemList);
    }

    @Override
    public JsonElement toJson() {
        JsonList jsonList = new JsonList();

        for (WebActionResultItem item : this.itemList) {
            if (item.isValid()) {
                jsonList.add(item.toJson());
            }
        }

        return new JsonMap(JSON_ITEMS, jsonList);
    }


    @Override
    public String toString() {
        return this.toJson().print();
    }
}
