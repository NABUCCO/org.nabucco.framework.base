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
import java.util.HashMap;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.Enumeration;
import org.nabucco.framework.base.facade.datatype.utils.MessageFormatter;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.WebModel;

/**
 * BreadCrumpEntry
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class BreadCrumpEntry extends WebModel {

    /** Breadcrump Label */
    private String label;

    /** Breadcrump Tooltip */
    private String tooltip;

    /** Breadcrump Work Item ID */
    private String workItemId;

    /** List of properties for this entry. */
    private Map<String, String> propertyMap = new HashMap<String, String>();

    /**
     * Creates a new {@link BreadCrumpEntry} instance.
     */
    public BreadCrumpEntry() {
    }

    @Override
    public void init() {
    }

    /**
     * Creates a new {@link BreadCrumpEntry} instance.
     * 
     * @param label
     *            the breadcrump label
     */
    public BreadCrumpEntry(String label) {
        this.label = label;
    }

    /**
     * Creates a new {@link BreadCrumpEntry} instance.
     * 
     * @param label
     *            the breadcrump label
     * @param label
     *            the breadcrump tooltip
     */
    public BreadCrumpEntry(String label, String tooltip) {
        this.label = label;
        this.tooltip = tooltip;
    }

    /**
     * Creates a new {@link BreadCrumpEntry} instance.
     * 
     * @param label
     *            the breadcrump label
     * @param tooltip
     *            the breadcrump tooltip
     * @param workItemId
     *            the referenced work item id
     */
    public BreadCrumpEntry(String label, String tooltip, String workItemId) {
        this.label = label;
        this.tooltip = tooltip;
        this.workItemId = workItemId;
    }

    /**
     * Getter for the label.
     * 
     * @return Returns the label.
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * Setter for the label.
     * 
     * @param label
     *            The label to set.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Getter for the tooltip.
     * 
     * @return Returns the tooltip.
     */
    public String getTooltip() {
        return this.tooltip;
    }

    /**
     * Setter for the tooltip.
     * 
     * @param tooltip
     *            The tooltip to set.
     */
    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * Getter for the workItemId.
     * 
     * @return Returns the workItemId.
     */
    public String getWorkItemId() {
        return this.workItemId;
    }

    /**
     * Setter for the workItemId.
     * 
     * @param workItemId
     *            The workItemId to set.
     */
    public void setWorkItemId(String workItemId) {
        this.workItemId = workItemId;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        super.propertyChange(event);

        if (event.getNewValue() instanceof Basetype) {
            this.propertyMap.put(event.getPropertyName(), String.valueOf(event.getNewValue()));
        } else if (event.getNewValue() instanceof Enumeration) {
            this.propertyMap.put(event.getPropertyName(), String.valueOf(event.getNewValue()));
        } else {
            this.propertyMap.put(event.getPropertyName(), DEFAULT_LABEL);
        }
    }

    @Override
    public JsonElement toJson() {
        JsonMap json = new JsonMap();
        json.add(JSON_ID, this.getWorkItemId());
        json.add(JSON_LABEL, MessageFormatter.format(this.getLabel(), this.propertyMap));
        json.add(JSON_TOOLTIP, MessageFormatter.format(this.getTooltip(), this.propertyMap));

        return json;
    }
}
