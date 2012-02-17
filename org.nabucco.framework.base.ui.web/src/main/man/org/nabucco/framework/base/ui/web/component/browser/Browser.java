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
package org.nabucco.framework.base.ui.web.component.browser;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.browser.BrowserExtension;
import org.nabucco.framework.base.ui.web.component.WebComposite;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.browser.BrowserModel;

/**
 * A single Browser instance displaying multiple browser entries in a configured layout.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class Browser extends WebComposite {

    /** The default serial version UID */
    private static final long serialVersionUID = 1L;

    /** The Browser Extension. */
    private BrowserExtension extension;

    /** The Browser Model */
    private BrowserModel model;

    /**
     * Creates a new {@link Browser} instance.
     */
    public Browser(BrowserExtension extension) {
        super(WebElementType.BROWSER, extension);
        
        this.extension = extension;
    }

    @Override
    public void init() throws ExtensionException {
        if (this.extension == null) {
            throw new IllegalArgumentException("Cannot initialize browser for extension 'null'.");
        }
    }

    /**
     * Getter for the work item id.
     * 
     * @return the work item id
     */
    public String getId() {
        ExtensionId identifier = this.extension.getIdentifier();
        if (identifier != null) {
            return identifier.getValue();
        }
        return null;
    }

    /**
     * Getter for the browser label.
     * 
     * @return the browser label.
     */
    public String getLabel() {
        return PropertyLoader.loadProperty(this.extension.getLabel());
    }

    /**
     * Getter for the browser tooltip.
     * 
     * @return the browser tooltip.
     */
    public String getTooltip() {
        return PropertyLoader.loadProperty(this.extension.getTooltip());
    }

    /**
     * Getter for the model.
     * 
     * @return Returns the model.
     */
    public BrowserModel getModel() {
        return this.model;
    }

    /**
     * Setter for the model.
     * 
     * @param model
     *            The model to set.
     */
    protected void setModel(BrowserModel model) {
        this.model = model;
    }

    @Override
    public JsonElement toJson() {
        JsonMap json = new JsonMap();
        json.add(JSON_LABEL, this.getLabel());
        json.add(JSON_MODEL, this.model.toJson());
        return json;
    }

}
