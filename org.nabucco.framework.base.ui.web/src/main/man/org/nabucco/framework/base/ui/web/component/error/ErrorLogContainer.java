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
package org.nabucco.framework.base.ui.web.component.error;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.ui.error.ErrorLogExtension;
import org.nabucco.framework.base.ui.web.component.WebComponent;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonMap;
import org.nabucco.framework.base.ui.web.model.error.ErrorLogContainerModel;

/**
 * ErrorContainer
 * 
 * The container that controls all server side errors
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ErrorLogContainer extends WebComponent {

    private static final long serialVersionUID = 1L;

    private static final String JSON_CLEAR_ALLOWED = "clearAllowed";

    private static final String JSON_TITLE = "title";

    private ErrorLogContainerModel model;

    private ErrorLogExtension extension;

    /**
     * Creates a new {@link ErrorLogContainer} instance.
     */
    public ErrorLogContainer(ErrorLogExtension extension) {
        super(WebElementType.ERROR, extension);
        this.extension = extension;
    }

    /**
     * Getter for the title of the container
     * 
     * @return the title
     */
    public String getTitle() {
        return PropertyLoader.loadProperty(this.extension.getTitle());
    }

    /**
     * Indicates if the user may clear the error list
     * 
     * @return true if may
     */
    public boolean isClearAllowed() {
        return PropertyLoader.loadProperty(this.extension.getAllowClear());
    }

    /**
     * Returns the layout type of the error dialog
     * 
     * @return the type of dialog
     */
    public ErrorLogLayout getLayout() {
        return PropertyLoader.loadProperty(ErrorLogLayout.class, this.extension.getLayout());
    }

    /**
     * Getter for the model.
     * 
     * @return Returns the model.
     */
    public ErrorLogContainerModel getModel() {
        return this.model;
    }

    @Override
    public void init() throws ExtensionException {
        this.model = new ErrorLogContainerModel(this.isClearAllowed());
    }

    @Override
    public JsonElement toJson() {
        JsonMap json = new JsonMap();
        json.add(JSON_MODEL, this.model.toJson());
        json.add(JSON_CLEAR_ALLOWED, this.isClearAllowed());
        json.add(JSON_LAYOUT, this.getLayout());
        json.add(JSON_TITLE, this.getTitle());
        return json;
    }

}
