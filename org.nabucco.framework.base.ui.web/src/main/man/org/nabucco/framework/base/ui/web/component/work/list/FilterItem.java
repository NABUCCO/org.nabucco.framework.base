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
package org.nabucco.framework.base.ui.web.component.work.list;

import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.queryfilter.QueryFilterExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.common.filter.FilterReferenceExtension;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.json.Jsonable;

/**
 * FilterItem
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class FilterItem implements Jsonable {

    private static final String JSON_REF_ID = "refId";

    private FilterReferenceExtension extension;

    private QueryFilterExtension filter;

    private String id;

    private String viewName;

    /**
     * 
     * Creates a new {@link FilterItem} instance.
     * 
     * @param id
     *            the id
     * @param extension
     *            the filter reference extension
     * @param refFilterExt
     *            the referenced filter extension
     */
    public FilterItem(String id, FilterReferenceExtension extension, QueryFilterExtension refFilterExt) {
        if (extension == null) {
            throw new IllegalArgumentException("Cannot create filter item. extension is 'null'");
        }

        if (refFilterExt == null) {
            throw new IllegalArgumentException("Cannot create filter item. Filter extension is 'null'");
        }
        this.id = id;
        this.extension = extension;
        this.filter = refFilterExt;
    }

    /**
     * 
     * Creates a new {@link FilterItem} instance.
     * 
     * @param extension
     *            the filter reference extension
     */
    public FilterItem(String id, FilterReferenceExtension extension) {
        if (extension == null) {
            throw new IllegalArgumentException("Cannot create filter item. extension is 'null'");
        }
        this.id = id;
        this.extension = extension;
    }

    /**
     * Getter for the referenced filter id
     * 
     * @return id of the referenced filter
     */
    public String getRefId() {
        return PropertyLoader.loadProperty(this.extension.getRefId());
    }

    /**
     * Getter for the label to show
     * 
     * @return label
     */
    public String getLabel() {
        String label = PropertyLoader.loadProperty(this.extension.getLabel());

        if ((label == null || label.isEmpty()) && this.filter != null) {
            return PropertyLoader.loadProperty(this.filter.getName());
        }
        return label;
    }

    /**
     * Getter for the tooltip to show
     * 
     * @return tooltip
     */
    public String getTooltip() {
        String tooltip = PropertyLoader.loadProperty(this.extension.getTooltip());
        if ((tooltip == null || tooltip.isEmpty()) && this.filter != null) {
            return PropertyLoader.loadProperty(this.filter.getDescription());
        }
        return tooltip;
    }

    /**
     * Returns if the item is default
     * 
     * @return
     */
    public boolean isDefault() {
        boolean isDefault = PropertyLoader.loadProperty(this.extension.getIsDefault());
        return isDefault;
    }

    /**
     * Returns the action to use for loading of filter values
     * 
     * @return the custom action or null
     */
    public String getCustomLoadAction() {
        String retVal = PropertyLoader.loadProperty(this.extension.getLoadAction());

        return retVal;
    }

    /**
     * Setter for the viewName.
     * 
     * @param viewName
     *            The viewName to set.
     */
    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    /**
     * Getter for the viewName.
     * 
     * @return Returns the viewName.
     */
    public String getViewName() {
        return viewName;
    }

    /**
     * Getter for the id.
     * 
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }

    @Override
    public JsonElement toJson() {
        JsonMap json = new JsonMap();

        json.add(JSON_ID, this.getId());
        json.add(JSON_REF_ID, this.getRefId());
        json.add(JSON_LABEL, this.getLabel());
        json.add(JSON_TOOLTIP, this.getTooltip());

        return json;
    }

}
