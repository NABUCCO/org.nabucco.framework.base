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
package org.nabucco.framework.base.ui.web.component.widgets;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.widget.WidgetExtension;
import org.nabucco.framework.base.ui.web.component.WebComponent;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.widget.WidgetModel;

/**
 * WidgetElement
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public abstract class WidgetElement extends WebComponent {

    private static final long serialVersionUID = 1L;

    private WidgetModel model;

    private WidgetExtension extension;

    private String widgetId;

    private String label;

    /**
     * Creates a new {@link WidgetElement} instance.
     * 
     * @param type
     */
    public WidgetElement(WidgetExtension extension) {
        super(WebElementType.WIDGET, extension);
        this.extension = extension;
    }

    @Override
    public void init() throws ExtensionException {
        this.setWidgetId(PropertyLoader.loadProperty(this.extension.getIdentifier()));
        this.setLabel(PropertyLoader.loadProperty(this.extension.getLabel()));
    }

    /**
     * Setter for the model.
     * 
     * @param model
     *            The model to set.
     */
    protected void setModel(WidgetModel model) {
        this.model = model;
    }

    /**
     * Getter for the model.
     * 
     * @return Returns the model.
     */
    protected WidgetModel getModel() {
        return this.model;
    }

    /**
     * Setter for the extension.
     * 
     * @param extension
     *            The extension to set.
     */
    public void setExtension(WidgetExtension extension) {
        this.extension = extension;
    }

    /**
     * Getter for the extension.
     * 
     * @return Returns the extension.
     */
    public WidgetExtension getExtension() {
        return this.extension;
    }

    /**
     * Setter for the widgetId.
     * 
     * @param widgetId
     *            The widgetId to set.
     */
    public void setWidgetId(String widgetId) {
        this.widgetId = widgetId;
    }

    /**
     * Getter for the widgetId.
     * 
     * @return Returns the widgetId.
     */
    public String getWidgetId() {
        return this.widgetId;
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
     * Getter for the label.
     * 
     * @return Returns the label.
     */
    public String getLabel() {
        return this.label;
    }

    @Override
    public JsonElement toJson() {
        JsonMap retVal = new JsonMap();

        retVal.add(JSON_ID, this.getWidgetId());
        retVal.add(JSON_LABEL, this.getLabel());
        retVal.add(JSON_MODEL, this.getModel().toJson());

        return retVal;
    }

}
