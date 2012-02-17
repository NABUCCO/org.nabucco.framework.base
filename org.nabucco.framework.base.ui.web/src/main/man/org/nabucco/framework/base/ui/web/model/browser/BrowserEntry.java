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

import java.beans.PropertyChangeEvent;
import java.util.HashMap;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.utils.MessageFormatter;
import org.nabucco.framework.base.ui.web.component.work.WorkItemType;
import org.nabucco.framework.base.ui.web.json.JsonMap;

/**
 * Single entry of a browser, that may have child entries.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class BrowserEntry extends BrowserModel {

    private static final long serialVersionUID = 1L;

    private static final String EMPTY_STRING = "";

    private static final String JSON_IS_PARENT = "isParent";

    private static final String JSON_WORK_ITEM_TYPE = "workItemType";

    private static final String JSON_WORK_ITEM_INSTANCEID = "workItemInstanceId";

    /** The Browser Entry ID. */
    private String id;

    /** The Browser Entry Label. */
    private String label;

    /** The Browser Entry Tooltip. */
    private String tooltip;

    /** The Browser Entry Image Reference. */
    private String image;

    /** The action to be fired on click */
    private String action = null;

    /** The Type of the bound Work Item */
    private final WorkItemType workItemType;

    private final String workItemInstanceId;

    /** Indicates if the browser item (not its children) should be sent eager or lazy (if needed) */
    private boolean lazy;

    /** The Browser Properties */
    private Map<String, String> propertyMap = new HashMap<String, String>();

    /**
     * Creates a new {@link BrowserEntry} instance.
     * 
     * @param id
     *            the browser entry ID * @param lazy should the entry be avaliable only after
     *            resolving of tree item or immediately (true for lazy)
     */
    public BrowserEntry(String id, boolean lazy) {
        this(id, null, lazy);
    }

    /**
     * Creates a new {@link BrowserEntry} instance.
     * 
     * @param id
     *            the browser entry ID
     * @param label
     *            the browser entry label * @param lazy should the entry be avaliable only after
     *            resolving of tree item or immediately (true for lazy)
     */
    public BrowserEntry(String id, String label, boolean lazy) {
        this(id, label, null, lazy);
    }

    /**
     * Creates a new {@link BrowserEntry} instance.
     * 
     * @param id
     *            the browser entry ID
     * @param label
     *            the browser entry label
     * @param tooltip
     *            the browser entry tooltip * @param lazy should the entry be avaliable only after
     *            resolving of tree item or immediately (true for lazy)
     * @param workItemType
     *            type of bound working item
     * @param workItemInstanceId
     *            instanceid of the bound working item
     */
    public BrowserEntry(String id, String label, String tooltip, boolean lazy) {
        this(id, label, tooltip, null, lazy, null, null);
    }

    /**
     * Creates a new {@link BrowserEntry} instance.
     * 
     * @param id
     *            the browser entry ID
     * @param label
     *            the browser entry label
     * @param tooltip
     *            the browser entry tooltip
     * @param image
     *            the browser entry image
     * @param lazy
     *            should the entry be avaliable only after resolving of tree item or immediately
     *            (true for lazy)
     * @param workItemType
     *            type of bound working item
     * @param workItemInstanceId
     *            instanceid of the bound working item
     */
    public BrowserEntry(String id, String label, String tooltip, String image, boolean lazy, WorkItemType workItemType,
            String workItemInstanceId) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot create Browser Entry for ID [null].");
        }

        this.id = id;
        this.lazy = lazy;

        this.workItemType = workItemType;
        this.workItemInstanceId = workItemInstanceId;

        this.setLabel(label);
        this.setTooltip(tooltip);
        this.setImage(image);
    }

    /**
     * Getter for the id.
     * 
     * @return Returns the id.
     */
    public String getId() {
        return this.id;
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
     * Getter for the image.
     * 
     * @return Returns the image.
     */
    public String getImage() {
        return this.image;
    }

    /**
     * Setter for the image.
     * 
     * @param image
     *            The image to set.
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Getter for the lazy load indicator
     * 
     * @return false => eager load, true => lazy
     */
    public boolean isLazy() {
        return this.lazy;
    }

    /**
     * Return the map of the bound properties
     * 
     * @return
     */
    public Map<String, String> getPropertyMap() {
        return this.propertyMap;
    }

    /**
     * Setter for the action.
     * 
     * @param action
     *            The action to set.
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Getter for the action.
     * 
     * @return Returns the action.
     */
    public String getAction() {
        return this.action;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        super.propertyChange(event);

        this.propertyMap.put(event.getPropertyName(), String.valueOf(event.getNewValue()));
    }

    /**
     * Getter for the workItemType.
     * 
     * @return Returns the workItemType.
     */
    public WorkItemType getWorkItemType() {
        return this.workItemType;
    }

    /**
     * Getter for the workItemInstanceId.
     * 
     * @return Returns the workItemInstanceId.
     */
    public String getWorkItemInstanceId() {
        return this.workItemInstanceId;
    }

    @Override
    public JsonMap toJson() {
        JsonMap json = super.toJson();
        json.add(JSON_ID, this.id);

        String label = MessageFormatter.format(this.label, this.propertyMap);
        if (!label.equals("null")) { // TODO: Fix MessageFormatter to provide empty string and not
                                     // null is not resolved
            json.add(JSON_LABEL, label);
        } else {
            json.add(JSON_LABEL, EMPTY_STRING);
        }

        if (this.action != null) {
            json.add(JSON_ACTION, this.action);
        }

        if (this.tooltip != null) {
            json.add(JSON_TOOLTIP, MessageFormatter.format(this.tooltip, this.propertyMap));
        }

        if (this.image != null) {
            json.add(JSON_IMAGE, MessageFormatter.format(this.image, this.propertyMap));
        }

        json.add(JSON_IS_PARENT, !this.getEntryMap().isEmpty());

        if (this.workItemType != null && this.workItemInstanceId != null) {
            json.add(JSON_WORK_ITEM_TYPE, this.workItemType);
            json.add(JSON_WORK_ITEM_INSTANCEID, this.workItemInstanceId);
        }

        return json;
    }

    @Override
    public String toString() {

        return this.toJson().toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        BrowserEntry other = (BrowserEntry) obj;
        if (this.id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

}
