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
package org.nabucco.framework.base.ui.web.model.work;

import java.beans.PropertyChangeEvent;
import java.util.HashMap;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.Enumeration;
import org.nabucco.framework.base.facade.datatype.utils.MessageFormatter;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.WebModel;
import org.nabucco.framework.base.ui.web.model.control.util.PropertyStringParser;

/**
 * WorkItemModel
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class WorkItemModel extends WebModel {

    private String label;

    private String tooltip;

    private String image;

    private Map<String, String> propertyMap = new HashMap<String, String>();

    /**
     * Creates a new {@link WorkItemModel} instance.
     * 
     * @param id
     *            the work item ID
     * @param label
     *            the work item label
     * @param tooltip
     *            the work item tooltip
     * @param image
     *            the work item image
     */
    public WorkItemModel(String label, String tooltip, String image) {
        this.label = label;
        this.tooltip = tooltip;
        this.image = image;

        PropertyStringParser propertyParser = new PropertyStringParser(label, tooltip, image);
        for (String propertyName : propertyParser.parseProperties()) {
            super.addPropertyChangeListener(propertyName, this);
        }
    }

    @Override
    public void init() {
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
     * Refresh the work item model.
     */
    public void refresh() {
        // Nothing to do here, subclass may override!
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
        json.add(JSON_LABEL, MessageFormatter.format(this.label, this.propertyMap));
        json.add(JSON_TOOLTIP, MessageFormatter.format(this.tooltip, this.propertyMap));
        json.add(JSON_IMAGE, this.image);
        json.add(JSON_DIRTY, this.isDirty());
        return json;
    }

    /**
     * Checks whether the work item model is dirty or not.
     * 
     * @return <b>true</b> if the work item model is dirty, <b>false</b> if not
     */
    public boolean isDirty() {
        return false;
    }

}
