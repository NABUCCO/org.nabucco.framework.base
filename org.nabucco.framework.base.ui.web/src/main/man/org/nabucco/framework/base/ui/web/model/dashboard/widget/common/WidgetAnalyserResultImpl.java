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
package org.nabucco.framework.base.ui.web.model.dashboard.widget.common;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonList;
import org.nabucco.framework.base.ui.web.json.JsonMap;

/**
 * WidgetAnalyserResult
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
class WidgetAnalyserResultImpl implements WidgetAnalyserResult {

    private static final String JSON_WEIGHT = "weight";

    private static final String JSON_COUNT = "count";

    private static final String JSON_CHILDREN = "children";

    private static final String JSON_COLOR = "color";

    private String label;

    private int count;

    private Map<String, WidgetAnalyserResult> childrenMap = new HashMap<String, WidgetAnalyserResult>();

    private WidgetAnalyserResult parent;

    private String color;

    /**
     * 
     * Creates a new parent {@link WidgetAnalyserResultImpl} instance.
     * 
     * @param label
     *            the label of result
     */
    public WidgetAnalyserResultImpl(String label) {
        if (label == null) {
            throw new IllegalArgumentException("Cannot create a result item. Label is 'null'.");
        }

        this.label = label;
    }

    /**
     * 
     * Creates a new child {@link WidgetAnalyserResultImpl} instance.
     * 
     * @param label
     *            the label of result
     * @param parent
     *            the parent of the result item
     */
    public WidgetAnalyserResultImpl(WidgetAnalyserResult parent, String label) {
        this(label);

        if (parent == null) {
            throw new IllegalArgumentException("Cannot create a result item. Parent is 'null'.");
        }

        this.parent = parent;
    }

    @Override
    public void setParent(WidgetAnalyserResult parent) {
        this.parent = parent;
    }

    @Override
    public void addChild(WidgetAnalyserResult item) {
        WidgetAnalyserResult clonedItem = item.clone();
        clonedItem.setParent(this);
        this.childrenMap.put(item.getLabel(), clonedItem);
    }

    @Override
    public final void appendResult(WidgetAnalyserResult newResult) throws ClientException {
        if (newResult.isParent()) {
            for (WidgetAnalyserResult child : newResult.getChildren()) {
                if (this.childrenMap.containsKey(child.getLabel())) {
                    WidgetAnalyserResult foundChild = this.childrenMap.get(child.getLabel());
                    foundChild.appendResult(child);
                } else {
                    this.addChild(child);
                }
            }
        } else {
            if (this.childrenMap.containsKey(newResult.getLabel())) {
                WidgetAnalyserResult foundChild = this.childrenMap.get(newResult.getLabel());
                foundChild.incrementCount(newResult.getCount());
            } else {
                this.addChild(newResult);
            }
        }
    }

    @Override
    public WidgetAnalyserResult clone() {
        WidgetAnalyserResultImpl retVal = null;

        if (this.parent != null) {
            retVal = new WidgetAnalyserResultImpl(this.parent, this.label);
        } else {
            retVal = new WidgetAnalyserResultImpl(this.label);
        }

        for (WidgetAnalyserResult child : this.getChildren()) {
            retVal.addChild(child.clone());
        }

        retVal.setCount(this.count);

        return retVal;
    }

    /**
     * Returns children of the result
     * 
     * @return the list of children
     */
    @Override
    public final List<WidgetAnalyserResult> getChildren() {
        return new ArrayList<WidgetAnalyserResult>(this.childrenMap.values());
    }

    /**
     * Getter for the count of elements.
     * 
     * @return Returns the count of children or the own count.
     */
    @Override
    public int getCount() {
        if (this.isParent()) {
            this.count = 0;
            for (WidgetAnalyserResult child : this.childrenMap.values()) {
                this.count += child.getCount();
            }
        }
        return this.count;
    }

    /**
     * Getter for the label.
     * 
     * @return Returns the label.
     */
    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public WidgetAnalyserResult getParent() {
        return this.parent;
    }

    /**
     * Getter for the weight.
     * 
     * @return Returns the weight.
     */
    @Override
    public double getWeight() {

        if (this.parent != null) {
            int parentCount = this.parent.getCount();
            if (parentCount == 0) {
                return 0;
            }
            BigDecimal summ = new BigDecimal(parentCount, MathContext.DECIMAL64);
            BigDecimal count = new BigDecimal(this.getCount(), MathContext.DECIMAL64);
            count = count.divide(summ, MathContext.DECIMAL64);
            count = count.setScale(2, RoundingMode.HALF_EVEN);
            return count.doubleValue();
        }
        
        BigDecimal retVal = new BigDecimal(100);
        retVal = retVal.setScale(2, RoundingMode.HALF_EVEN);
        return retVal.doubleValue();
    }

    @Override
    public void incrementCount(int value) throws ClientException {
        if (!this.isParent()) {
            this.count += value;
        } else {
            throw new ClientException("Cannot increment result item that has children.");
        }
    }

    @Override
    public boolean isParent() {
        return !this.childrenMap.isEmpty();
    }

    /**
     * Setter for count
     * 
     * @param value
     *            count
     */
    public void setCount(int value) {
        this.count = value;
    }

    @Override
    public void setColor(String colorId) {
        this.color = colorId;
    }

    @Override
    public JsonElement toJson() {
        JsonMap retVal = new JsonMap();

        retVal.add(JSON_LABEL, this.label);
        retVal.add(JSON_WEIGHT, this.getWeight());
        retVal.add(JSON_COUNT, this.getCount());
        retVal.add(JSON_COLOR, this.color);

        JsonList children = new JsonList();
        for (WidgetAnalyserResult child : this.childrenMap.values()) {
            children.add(child.toJson());
        }

        retVal.add(JSON_CHILDREN, children);
        return retVal;
    }

}
