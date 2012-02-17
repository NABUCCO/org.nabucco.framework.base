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

import java.util.List;

import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.json.Jsonable;

/**
 * WidgetAnalyseResultItem
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public interface WidgetAnalyserResult extends Jsonable {

    /**
     * Return the children elements of the item
     * 
     * @return list of elements
     */
    List<WidgetAnalyserResult> getChildren();

    /**
     * Set color
     */
    void setColor(String color);

    /**
     * Adds a new child to the element
     * 
     * @param item
     *            the item to be added
     */
    void addChild(WidgetAnalyserResult item);

    /**
     * Returns the parent element if any
     * 
     * @return parent
     */
    WidgetAnalyserResult getParent();

    /**
     * Produce the copy of itself
     * 
     * @return
     */
    WidgetAnalyserResult clone();

    /**
     * Set Parent
     * 
     * @param parent
     *            new parent element
     */
    void setParent(WidgetAnalyserResult parent);

    /**
     * Increment the count or exception of not a node
     * 
     * @param value
     *            the value to be added
     * @throws ClientException
     *             illegal increment
     */
    void incrementCount(int value) throws ClientException;

    /**
     * Appends one result tree to another
     * 
     * @param newResult
     *            result tree to append
     * @throws ClientException
     *             if something wrong
     */
    void appendResult(WidgetAnalyserResult newResult) throws ClientException;

    /**
     * Getter for the count
     * 
     * @return count
     */
    int getCount();

    /**
     * Getter for procentual weight of the tree part
     * 
     * @return percent relative to other children
     */
    double getWeight();

    /**
     * Getter for the label
     * 
     * @return label
     */
    String getLabel();

    /**
     * Returns if the element hat children
     * 
     * @return true if it has children
     */
    boolean isParent();
}
