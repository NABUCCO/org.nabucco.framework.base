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
package org.nabucco.framework.base.ui.web.component;

import java.util.Collections;
import java.util.List;

import org.nabucco.framework.base.facade.datatype.extension.schema.ui.UiExtension;
import org.nabucco.framework.base.ui.web.json.JsonElement;
import org.nabucco.framework.base.ui.web.json.JsonValue;

/**
 * A web component is a {@link WebElement} that does not have any child elements. Any getter call
 * with return null or an empty list.
 * 
 * @author Frank Ratschinski, PRODYNA AG
 * @author Nicolas Moser, PRODYNA AG
 */
public abstract class WebComponent extends WebElement {

    /** The default serial version UID */
    private static final long serialVersionUID = 1L;

    private static final String[] EMPTY_IDS = new String[0];

    /**
     * Creates a new {@link WebComponent} instance.
     * 
     * @param type
     *            the web element type
     * @param extension
     *            the UI extension
     */
    public WebComponent(WebElementType type, UiExtension extension) {
        super(type, extension);
    }

    @Override
    public String[] getElementIds() {
        return EMPTY_IDS;
    }

    @Override
    public List<WebElement> getElements() {
        return Collections.emptyList();
    }

    @Override
    public WebElement getElement(String id) {
        return null;
    }

    @Override
    public JsonElement toJson() {
        return new JsonValue(null);
    }

    @Override
    public String toString() {
        return this.toJson().print();
    }

}
